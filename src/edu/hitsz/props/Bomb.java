package edu.hitsz.props;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Game;
import edu.hitsz.thread.*;

public class Bomb extends BaseProp {
    public Bomb(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);

    }

    

    public void takingEffect(HeroAircraft Craft, boolean isMusic, Game game) {
        if (isMusic) {
            new AudioPlayerThread("src/videos/get_supply.wav").start();
            new BombSupplyThread(game).start();

        }
        System.out.println("BombSupply active!");

    }
}
