package edu.hitsz.thread;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shootStrategy.MultiShoot;
import edu.hitsz.shootStrategy.SingleShoot;

public class FireSupplyThread extends Thread {
    private HeroAircraft craft;

    public FireSupplyThread(HeroAircraft heroAircraft) {
        craft = heroAircraft;
    }

    @Override
    public void run() {
        craft.setFireStrategy(new MultiShoot());
        craft.strategyApply(craft);
        try {
            Thread.sleep(5000);
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }
        craft.setFireStrategy(new SingleShoot());
        craft.strategyApply(craft);

    }
}
