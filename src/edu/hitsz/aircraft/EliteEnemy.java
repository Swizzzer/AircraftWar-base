package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.shootStrategy.ShootStrategy;
import edu.hitsz.shootStrategy.SingleShoot;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends AbstractEnemy {


    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int power) {
        super(locationX, locationY, speedX, speedY, hp,power);
        setFireStrategy(new SingleShoot());
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }
    @Override
    public List<BaseBullet> shoot(){

        return strategyApply(this);
    }

}
