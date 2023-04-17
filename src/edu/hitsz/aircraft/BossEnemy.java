package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.props.BaseProp;
import edu.hitsz.props.propFactory.BombSupplyFactory;
import edu.hitsz.props.propFactory.BulletSupplyFactory;
import edu.hitsz.props.propFactory.HpSupplyFactory;
import edu.hitsz.props.propFactory.PropFactory;
import edu.hitsz.shootStrategy.*;

import java.util.LinkedList;
import java.util.List;

public class BossEnemy extends AbstractEnemy {




    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int power) {
        super(locationX, locationY, speedX, speedY, hp,power);
        setFireStrategy(new MultiShoot());


    }

    @Override
    public List<BaseBullet> shoot(){

        return strategyApply(this);
    }

    @Override
    public void dropProps(List<BaseProp> Props) {
        PropFactory propFactory;
        // 使用循环随机掉落3个道具.允许道具重复
        // 道具生成的X坐标不同,使得道具显示时不会重叠

        for (int i = 0; i < 3; i++) {
            int propOption = ((int) ((Math.random() * 53) % 3));
            switch (propOption) {
                case 0 -> {
                    propFactory = new HpSupplyFactory();
                    Props.add(propFactory.create(locationX + 5*i, locationY, 0, 3));
                }
                case 1 -> {
                    propFactory = new BulletSupplyFactory();
                    Props.add(propFactory.create(locationX + 5*i, locationY, 0, 3));
                }
                case 2 -> {
                    propFactory = new BombSupplyFactory();
                    Props.add(propFactory.create(locationX + 5*i, locationY, 0, 3));
                }
            }
        }
    }


}


