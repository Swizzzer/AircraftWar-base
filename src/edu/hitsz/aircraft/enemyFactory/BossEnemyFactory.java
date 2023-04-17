package edu.hitsz.aircraft.enemyFactory;

import edu.hitsz.aircraft.*;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;


public class BossEnemyFactory implements EnemyFactory {
    @Override
    public AbstractEnemy create() {
        return new BossEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                3,
                0,
                3000,
                60
        );
    }

}
