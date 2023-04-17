package edu.hitsz.shootStrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class MultiShoot implements ShootStrategy {
    private final int shootNum = 3;
    /**
     * 子弹发射方向,向上为-1,向下为1
     */
    private int direction;

    @Override
    public List<BaseBullet> strategyApply(AbstractAircraft aircraft) {
        /**
         * 条件语句用于判断子弹发射方向及子弹种类
         */
        if (aircraft instanceof HeroAircraft) {
            direction = -1;
            List<BaseBullet> res = new LinkedList<>();
            int x = aircraft.getLocationX();
            int y = aircraft.getLocationY() + direction * 2;
            int speedX = 0;
            int speedY = aircraft.getSpeedY() + direction * 5;
            BaseBullet bullet;
            for (int i = 0; i < shootNum; i++) {
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                speedX = 2 * i - shootNum + 1;
                bullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, aircraft.getPower());
                res.add(bullet);
            }
            return res;
        }
        /**
         * Boss敌机的射击逻辑.XY轴速度调整得更快
         */
        else {
            direction = 1;
            List<BaseBullet> res = new LinkedList<>();
            int x = aircraft.getLocationX();
            int y = aircraft.getLocationY() + direction * 5;
            int speedX = 0;
            int speedY = aircraft.getSpeedY() + direction * 10;
            BaseBullet bullet;
            for (int i = 0; i < shootNum; i++) {
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                speedX = 2 * i - shootNum + 1;
                bullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, aircraft.getPower());
                res.add(bullet);
            }
            return res;
        }

    }
}
