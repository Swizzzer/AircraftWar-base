/**
 * 用于控制火力道具失效的线程
 * 避免英雄机在第一个火力道具未失效时获得第二个火力道具的情况下,第二个火力道具随第一个火力道具一起失效的问题
 */
package edu.hitsz.thread;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shootStrategy.SingleShoot;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class FireManageThread {
    private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final List<FireSupplyThread> fireList = new LinkedList<>();
    private HeroAircraft craft;

    public FireManageThread(HeroAircraft craft) {
        this.craft = craft;
    }

    public void fireAdd() {
        FireSupplyThread tmp = new FireSupplyThread(craft);
        tmp.start();
        System.out.print(tmp.isAlive());
        fireList.add(tmp);
    }

    public void start() {
        worker = new Thread(() -> {
            running.set(true);
            while (running.get()) {
                if (!fireList.isEmpty()) {
                    fireList.removeIf(fireSupplyThread -> !fireSupplyThread.isAlive());
                    /**
                     * 休眠,降低CPU使用
                     */
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    craft.setFireStrategy(new SingleShoot());
                    craft.strategyApply(craft);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        worker.start();
    }

    public void stop() {
        running.set(false);
    }

}
