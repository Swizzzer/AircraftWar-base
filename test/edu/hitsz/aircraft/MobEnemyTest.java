package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


public class MobEnemyTest {

    private MobEnemy aircraft;

    @BeforeEach
    public void setUp() {
        // Initialize AbstractAircraft object
        aircraft = new MobEnemy(0, 0, 0, 0, 100,0) {
            @Override
            public List<BaseBullet> shoot() {
                return null;
            }
        };
    }

    @Test
    public void testDecreaseHp() {
        aircraft.decreaseHp(50);
        Assertions.assertEquals(50, aircraft.getHp());
    }

    @Test
    public void testGetHp(){
        setUp();
        Assertions.assertEquals(100, aircraft.getHp());
    }
}


