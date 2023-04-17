package edu.hitsz.shootStrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

public class NullShoot implements ShootStrategy{
    @Override
    public List<BaseBullet> strategyApply(AbstractAircraft aircraft){
        return new LinkedList<>();
    }
}
