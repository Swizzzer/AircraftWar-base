package edu.hitsz.aircraft;

import static org.junit.jupiter.api.Assertions.*;

import edu.hitsz.aircraft.EliteEnemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;

public class EliteEnemyTest {

    private EliteEnemy object1;
    private EliteEnemy object2;

    @BeforeEach
    public void setUp() {
        object1 = new EliteEnemy(50, 50,0,5,100,30);
        object2 = new EliteEnemy(100, 100,0,5,100,30);
    }

    @Test
    public void testForward() {
        int x1 = object1.getLocationX();
        int y1 = object1.getLocationY();
        int x2 = object2.getLocationX();
        int y2 = object2.getLocationY();
        object1.forward();
        object2.forward();
        assertAll("forward",
                () -> assertEquals(x1, object1.getLocationX()),
                () -> assertEquals(y1+5, object1.getLocationY()),
                () -> assertEquals(x2, object2.getLocationX()),
                () -> assertEquals(y2+5, object2.getLocationY())
        );
    }

    @Test
    public void testCrash() {
        assertFalse(object1.crash(object2));
        object1.setLocation(100, 100);
        assertTrue(object1.crash(object2));
    }

    @Test
    public void testGetImage() {
        assertNotNull(object1.getImage());
        assertNotNull(object2.getImage());
    }

    @Test
    public void testGetWidth() {
        assertEquals(ImageManager.get(object1).getWidth(), object1.getWidth());
        assertEquals(ImageManager.get(object2).getWidth(), object2.getWidth());
    }

    @Test
    public void testGetHeight() {
        assertEquals(ImageManager.get(object1).getHeight(), object1.getHeight());
        assertEquals(ImageManager.get(object2).getHeight(), object2.getHeight());
    }

    @Test
    public void testNotValid() {
        assertFalse(object1.notValid());
        object1.vanish();
        assertTrue(object1.notValid());
    }

}
