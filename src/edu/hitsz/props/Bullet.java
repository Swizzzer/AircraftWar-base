package edu.hitsz.props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.thread.FireSupplyThread;

public class Bullet extends BaseProp{
    public Bullet(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);

    }

    @Override
    public void takingEffect(HeroAircraft Craft) {
        new FireSupplyThread(Craft).start();
        System.out.println("FireSupply active!");
    }
}
