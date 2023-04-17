package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.aircraft.enemyFactory.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.props.BaseProp;
import edu.hitsz.scoreDAO.*;
import edu.hitsz.swing.CardLayoutDemo;
import edu.hitsz.swing.SimpleTable;
import edu.hitsz.thread.*;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class Game extends JPanel implements InputCallback {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractEnemy> enemyAircrafts;
    private EnemyFactory enemyFactory;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;

    private final List<BaseProp> Props;

    /**
     * 屏幕中出现的敌机最大数量
     */
    private final int enemyMaxNumber = 5;

    /**
     * 当前得分
     */
    private int score = 0;
    /**
     * 记录Boss敌机数量用于判断是否生成
     */
    private int bossFlag = 0;
    /**
     * 记录分数除counter的商,用以判断是否生成boss敌机
     */
    private int quotient = 0;
    private final int counter = 1000;

    /**
     * 当前时刻
     */
    private int time = 0;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private final int cycleDuration = 600;
    private int heroShootCycleTime = 0;
    private int cycleTime = 0;
    /**
     * 用于询问并存储用户名以更新排行榜
     */

    private String userInput;

    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;
    private boolean isMusic;

    /**
     * 用于播放音乐音效的线程及判断是否播放的bool型变量
     */
    private AudioPlayerThread bgmAudioThread;
    private AudioPlayerThread bossAudioThread;
    private AudioPlayerThread heroBulletAudioThread;
    private AudioPlayerThread overAudioThread;
    private final List<Thread> bulletAudioThread;


    public Game(boolean isMusic) {
        this.isMusic=isMusic;
        heroAircraft = HeroAircraft.getInstance();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        Props = new LinkedList<>();
        bulletAudioThread = new LinkedList<>();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        // 启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * callback的具体实现,用于接收到用户输入后写入文件并打印排行榜.
     * 之所以采用回调是为了避免用户返回空的输入.
     * 若不采用回调则必须使用循环检查用户输入是否为空,造成无意义的CPU时间浪费.
     */
    public void onInput(QuestionGUI questionBox) {
        /**
         * 当输入非空时回调函数才会被调用,确保了获取用户输入时返回值非空,避免了使用循环.
         */
        userInput = questionBox.getUserInput();

        System.out.println("----------------积分排行榜--------------------");
        System.out.println("============================================");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        String nowTime = formatter.format(new Date(System.currentTimeMillis()));

        ScoreStructure info = new ScoreStructure();
        info.setScore(score);
        info.setPlayTime(nowTime);
        info.setUserName(userInput);

        ScoreDAO dao = new ScoreStream("result.txt");
        dao.addScoreInfo(info);
        dao.printByScores();
        System.out.println("Game Over!");
        CardLayoutDemo.cardPanel.add(new SimpleTable(dao,dao.getScoreInfos()).getMainPanel());
        CardLayoutDemo.cardLayout.next(CardLayoutDemo.cardPanel);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */

    public void action() {
        // 防止下面调用isValid()时陷入空判断而卡死
        if (isMusic) {
            bgmAudioThread = new AudioPlayerThread("src/videos/bgm.wav");
            bgmAudioThread.start();
        }

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;
            // 播放bgm
            if ((isMusic) && (bossFlag == 0) && (!bgmAudioThread.isAlive())) {
                bgmAudioThread = new AudioPlayerThread("src/videos/bgm.wav");
                bgmAudioThread.start();
            }
            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);

                // 新敌机产生
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    int randomNumber = ((int) (Math.random() * 17) + 1) % 7;
                    if (randomNumber != 0) {
                        enemyFactory = new MobEnemyFactory();
                        enemyAircrafts.add(enemyFactory.create());
                    } else {
                        enemyFactory = new EliteEnemyFactory();
                        enemyAircrafts.add(enemyFactory.create());
                    }
                    /*
                     * Boss敌机的生成逻辑
                     */
                    if (((score / counter) > quotient) && (bossFlag == 0)) {
                        ++bossFlag;
                        quotient = score / counter;
                        enemyFactory = new BossEnemyFactory();
                        enemyAircrafts.add(enemyFactory.create());
                        // 播放boss音效
                        if (isMusic) {
                            bossAudioThread = new AudioPlayerThread("src/videos/bgm_boss.wav");
                            bossAudioThread.start();
                            bgmAudioThread.stop();
                        }
                    }

                }
                // 飞机射出子弹
                enemyShootAction();
            }
            if (heroShootJudge()) {
                heroShootAction();
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            // 每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                gameOverFlag = true;
                heroAircraft.destroyHeroCraft();
                if (isMusic) {
                    overAudioThread=new AudioPlayerThread("src/videos/game_over.wav");
                    overAudioThread.start();
                    if (bgmAudioThread.isAlive()) {
                        bgmAudioThread.stop();
                    }
                    if (bossFlag == 1 && bossAudioThread.isAlive()) {
                        bossAudioThread.stop();
                    }
                    if (heroBulletAudioThread.isAlive()) {
                        heroBulletAudioThread.stop();
                    }
                    if (!bulletAudioThread.isEmpty()) {
                        for (Thread thread : bulletAudioThread) {
                            if (thread.isAlive()) {
                                thread.stop();
                            }

                        }

                    }
                }

                /**
                 * 弹窗询问用户
                 * 等待回调完成后处理
                 */
                QuestionGUI questionBox = new QuestionGUI("What is your name?", this);

            }
        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    // ***********************
    // Action 各部分
    // ***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private boolean heroShootJudge() {
        int duration = heroAircraft.getDuration();
        heroShootCycleTime += timeInterval;
        if (heroShootCycleTime >= duration && heroShootCycleTime - timeInterval < heroShootCycleTime) {
            // 跨越到新的周期
            heroShootCycleTime %= duration;
            return true;
        } else {
            return false;
        }
    }

    private void enemyShootAction() {
        // 敌机射击
        for (AbstractAircraft enemies : enemyAircrafts) {
            enemyBullets.addAll(enemies.shoot());
            if (isMusic) {
                AudioPlayerThread enemyBulletAudioThread = new AudioPlayerThread("src/videos/bullet.wav");
                bulletAudioThread.add(enemyBulletAudioThread);
                enemyBulletAudioThread.start();
            }
        }
    }

    private void heroShootAction() {
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
        if (isMusic) {
            heroBulletAudioThread = new AudioPlayerThread("src/videos/bullet.wav");
            heroBulletAudioThread.start();
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (BaseProp prop : Props) {
            prop.forward();
        }

    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }

            if (heroAircraft.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                // 英雄机撞击到敌机子弹
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
                if (isMusic) {
                    new AudioPlayerThread("src/videos/bullet_hit.wav").start();
                }
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractEnemy enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {

                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (isMusic) {
                        new AudioPlayerThread("src/videos/bullet_hit.wav").start();
                    }
                    if (enemyAircraft.notValid()) {
                        // 获得分数，产生道具补给
                        enemyAircraft.dropProps(Props);
                        if (enemyAircraft instanceof MobEnemy) {
                            score += 10;
                        } else if (enemyAircraft instanceof EliteEnemy) {
                            score += 50;
                        } else if (enemyAircraft instanceof BossEnemy) {
                            score += 100;
                            --bossFlag;
                            if (isMusic) {
                                bossAudioThread.stop();
                                bgmAudioThread = new AudioPlayerThread("src/videos/bgm.wav");
                                bgmAudioThread.start();
                            }
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    if (enemyAircraft instanceof BossEnemy) {
                        --bossFlag;
                    }
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // 我方获得道具，道具生效
        for (BaseProp prop : Props) {
            if (heroAircraft.crash(prop) || prop.crash(heroAircraft) && !prop.notValid()) {
                if (isMusic) {
                    new AudioPlayerThread("src/videos/get_supply.wav").start();
                }
                prop.takingEffect(heroAircraft);
                prop.vanish();// 销毁已生效的道具
            }
        }

    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        Props.removeIf(AbstractFlyingObject::notValid);
    }

    // ***********************
    // Paint 各部分
    // ***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, Props);

        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        // 绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

    /**
     * 用于和QuestionGUI中的方法一同,实现用户输入的字符串即时发送到主线程,避免
     *
     * @param s
     * @param questionBox
     */

}
