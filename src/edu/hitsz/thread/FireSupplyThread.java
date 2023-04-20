package edu.hitsz.thread;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shootStrategy.MultiShoot;
import edu.hitsz.shootStrategy.SingleShoot;

import java.util.concurrent.atomic.AtomicBoolean;

public class FireSupplyThread implements Runnable {
    private Thread worker;
    private final AtomicBoolean running=new AtomicBoolean(true);
    private final HeroAircraft craft;
    /**
     * 火力道具的持续时长，按millisecond计算
     */
    protected static int fireTimer=5000;

    public FireSupplyThread(HeroAircraft heroAircraft) {
        craft = heroAircraft;
    }
    public void start(){
        worker=new Thread(this);
        worker.start();
    }
    public boolean isAlive(){
        return running.get();
    }

    @Override
    public void run() {
        while (running.get()){
            craft.setFireStrategy(new MultiShoot());
            craft.strategyApply(craft);
            try {
                Thread.sleep(fireTimer);
            }catch(InterruptedException e){
                throw new RuntimeException(e);
            }
            running.set(false);

        }
    }
    public void stop(){
        running.set(false);
    }
}
