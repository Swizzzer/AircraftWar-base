package edu.hitsz.props.propFactory;

import edu.hitsz.props.BaseProp;
import edu.hitsz.props.Bullet;

public class BulletSupplyFactory implements PropFactory {
    @Override
    public BaseProp create(int locationX, int locationY, int speedX, int speedY) {
        return new Bullet(locationX, locationY, speedX, speedY);
    }
}
