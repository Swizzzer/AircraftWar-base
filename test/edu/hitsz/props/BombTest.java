package edu.hitsz.props;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BombTest {

    private Bomb bomb;
    private HeroAircraft aircraft;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    

    @BeforeEach
    public void setUp() {
        bomb = new Bomb(10, 20, 0, 5);
        aircraft = new HeroAircraft(5, 10, 0, 5, 100,30);
    }

    @Test
    public void testTakingEffect() {
        outContent = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(outContent);
        System.setOut(ps);
        bomb.takingEffect(aircraft,false);
        originalOut = System.out;
        Assertions.assertEquals("BombSupply active!", outContent.toString().trim());
    }
    @Test
    public void testForward() {
        int x1 = bomb.getLocationX();
        int y1 = bomb.getLocationY();
        bomb.forward();
        assertAll("forward",
                () -> assertEquals(x1, bomb.getLocationX()),
                () -> assertEquals(y1+5, bomb.getLocationY())

        );
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

}
