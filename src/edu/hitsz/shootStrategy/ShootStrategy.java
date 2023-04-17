package edu.hitsz.shootStrategy;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public interface ShootStrategy {
    List<BaseBullet> strategyApply(AbstractAircraft aircraft);
}
