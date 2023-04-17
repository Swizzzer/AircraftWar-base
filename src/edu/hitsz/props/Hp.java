package edu.hitsz.props;

import edu.hitsz.aircraft.*;

public class Hp extends BaseProp{
    /**
     * 一个道具可补充的生命值
     */
    private final int hpNum=-500;

    public Hp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);

    }
    public void takingEffect(HeroAircraft Craft){
        Craft.decreaseHp(hpNum);
        if (Craft.getHp() > Craft.getMaxHp()) {
            Craft.decreaseHp(Craft.getHp() - Craft.getMaxHp()); // 溢出判断
        }
        System.out.println("HpSupply active!");

    }
}
