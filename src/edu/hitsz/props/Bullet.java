package edu.hitsz.props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Game;
import edu.hitsz.thread.AudioPlayerThread;
import edu.hitsz.thread.FireSupplyThread;

public class Bullet extends BaseProp{
    public Bullet(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);

    }

    @Override
    public void takingEffect(HeroAircraft Craft, boolean isMusic, Game game) {
        game.fireManageThread.fireAdd();
        if (isMusic) {
            new AudioPlayerThread("src/videos/get_supply.wav").start();
        }
        System.out.println("FireSupply active!");
    }
}
