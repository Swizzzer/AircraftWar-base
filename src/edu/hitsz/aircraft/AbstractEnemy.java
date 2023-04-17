package edu.hitsz.aircraft;
import edu.hitsz.props.*;
import edu.hitsz.props.propFactory.*;

import java.util.List;

/**
 * @author swizzer
 */
public abstract class AbstractEnemy extends AbstractAircraft{
    private PropFactory propFactory;

    public AbstractEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int power) {
        super(locationX, locationY, speedX, speedY, hp, power);
    }
    public void dropProps(List<BaseProp> Props){
        int propOption = ((int) (Math.random() * 10) + 1);
        // 测试时可将数字调整为更小的数字实现更大的掉落几率
        if (propOption >= 8) {
            // 掉落加血道具
            propFactory = new HpSupplyFactory();
            Props.add(propFactory.create(locationX, locationY, 0, 5));
        } else if (propOption >= 6) {
            // 掉落火力道具
            propFactory = new BulletSupplyFactory();
            Props.add(propFactory.create(locationX, locationY, 0, 5));
        } else if (propOption >= 4) {
            // 掉落炸弹道具
            propFactory = new BombSupplyFactory();
            Props.add(propFactory.create(locationX, locationY, 0, 5));
        }
    }

}
