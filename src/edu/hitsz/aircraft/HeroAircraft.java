package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.shootStrategy.SingleShoot;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 *
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    /**攻击方式 */


    /**
     * 子弹伤害
     */
    private final int power = 30;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private final int direction = -1;
    /**
     * 子弹发射频率(ms)
     */
    private final int duration = 300;

    private volatile static HeroAircraft instance = null;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp        初始生命值
     */
    public HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp,int power) {
        super(locationX, locationY, speedX, speedY, hp,power);
        setFireStrategy(new SingleShoot());
    }

    public static HeroAircraft getInstance() {
        if (instance == null) {
            synchronized (HeroAircraft.class) {
                if (instance == null) {
                    instance = new HeroAircraft(
                            Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                            0, 0, 10000000,30);
                }
            }
        }
        return instance;
    }
    /**
     * 获得子弹发射的间隔(ms)
     * @return duration 子弹发射间隔
     */
    public int getDuration() {
        return duration;
    }
    public void destroyHeroCraft(){
        instance=null;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    public List<BaseBullet> shoot(){
        return strategyApply(this);
    }
}
