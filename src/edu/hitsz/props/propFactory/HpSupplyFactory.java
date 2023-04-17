package edu.hitsz.props.propFactory;

import edu.hitsz.props.BaseProp;
import edu.hitsz.props.Hp;

public class HpSupplyFactory implements PropFactory{
    @Override
    public BaseProp create(int locationX, int locationY, int speedX, int speedY) {
        return new Hp(locationX, locationY, speedX, speedY);
    }
}

