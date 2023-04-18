package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    private HeroAircraft hero;

    @BeforeEach
    void setUp() {
        hero = new HeroAircraft(0, 0, 0, 0, 100, 30);
    }

    @Test
    void testShoot() {
        List<BaseBullet> bullets = hero.shoot();
        assertEquals(1, bullets.size()); // shootNum默认为1，应该只产生一个子弹

        // 确认产生的子弹的位置
        BaseBullet bullet = bullets.get(0);
        assertEquals(hero.getLocationX(), bullet.getLocationX());
        assertEquals(hero.getLocationY() - 2, bullet.getLocationY());
    }

    @Test
    void testGetInstance() {
        HeroAircraft instance1 = HeroAircraft.getInstance();
        HeroAircraft instance2 = HeroAircraft.getInstance();
        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2); // 应该是同一个实例
    }
}
