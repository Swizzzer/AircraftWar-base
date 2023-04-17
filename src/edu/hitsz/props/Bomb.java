package edu.hitsz.props;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.thread.*;

public class Bomb extends BaseProp {
    public Bomb(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);

    }

    @Override
    public void takingEffect(HeroAircraft Craft, boolean isMusic) {
        if (isMusic) {
             new AudioPlayerThread("src/videos/get_supply.wav").start();
             new BombSupplyThread().start();

        }
        System.out.println("BombSupply active!");

    }
}
