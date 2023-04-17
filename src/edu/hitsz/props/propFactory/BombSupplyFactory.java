package edu.hitsz.props.propFactory;

import edu.hitsz.props.BaseProp;
import edu.hitsz.props.Bomb;

public class BombSupplyFactory implements PropFactory{
    @Override
    public BaseProp create(int locationX, int locationY, int speedX, int speedY) {
        return new Bomb(locationX, locationY, speedX, speedY);
    }
}
