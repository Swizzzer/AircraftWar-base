package edu.hitsz.props;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;

public class Bomb extends BaseProp {
    public Bomb(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);

    }

    @Override
    public void takingEffect(HeroAircraft Craft) {
        System.out.println("BombSupply active!");
    }
}
