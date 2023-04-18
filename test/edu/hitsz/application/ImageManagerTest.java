package edu.hitsz.application;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.image.BufferedImage;

import edu.hitsz.aircraft.HeroAircraft;
import org.junit.jupiter.api.Test;

class ImageManagerTest {

    @Test
    void testBackgroundImage() {
        BufferedImage image = ImageManager.BACKGROUND_IMAGE;
        assertNotNull(image);
    }

    @Test
    void testHeroImage() {
        BufferedImage image = ImageManager.HERO_IMAGE;
        assertNotNull(image);
    }

    @Test
    void testHeroBulletImage() {
        BufferedImage image = ImageManager.HERO_BULLET_IMAGE;
        assertNotNull(image);
    }

    @Test
    void testEnemyBulletImage() {
        BufferedImage image = ImageManager.ENEMY_BULLET_IMAGE;
        assertNotNull(image);
    }

    @Test
    void testMobEnemyImage() {
        BufferedImage image = ImageManager.MOB_ENEMY_IMAGE;
        assertNotNull(image);
    }

    @Test
    void testBossEnemyImage() {
        BufferedImage image = ImageManager.BOSS_ENEMY_IMAGE;
        assertNotNull(image);
    }

    @Test
    void testEliteEnemyImage() {
        BufferedImage image = ImageManager.ELITE_ENEMY_IMAGE;
        assertNotNull(image);
    }

    @Test
    void testPropBloodImage() {
        BufferedImage image = ImageManager.PROP_BLOOD_IMAGE;
        assertNotNull(image);
    }

    @Test
    void testPropBombImage() {
        BufferedImage image = ImageManager.PROP_BOMB_IMAGE;
        assertNotNull(image);
    }

    @Test
    void testPropBulletImage() {
        BufferedImage image = ImageManager.PROP_BULLET_IMAGE;
        assertNotNull(image);
    }

    @Test
    void testGetWithClassName() {
        BufferedImage image = ImageManager.get(HeroAircraft.class.getName());
        assertNotNull(image);
    }

    @Test
    void testGetWithObject() {
        HeroAircraft hero = new HeroAircraft(0, 0, 0, 0, 100,3);
        BufferedImage image = ImageManager.get(hero);
        assertNotNull(image);
    }

}
