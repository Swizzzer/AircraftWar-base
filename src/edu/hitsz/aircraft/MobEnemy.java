package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.props.BaseProp;
import edu.hitsz.props.propFactory.BulletSupplyFactory;
import edu.hitsz.props.propFactory.PropFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractEnemy {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int power) {
        super(locationX, locationY, speedX, speedY, hp,power);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return strategyApply(this);
    }


    @Override
    public void dropProps(List<BaseProp> Props){
        // 普通敌机不掉落道具

    }

}
