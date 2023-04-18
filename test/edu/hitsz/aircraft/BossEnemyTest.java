package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.props.BaseProp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class BossEnemyTest {
    BossEnemy bossEnemy = new BossEnemy(10, 20, 2, 5, 100,30);

    @Test
    public void testShoot() {

        List<BaseBullet> bullets = bossEnemy.shoot();
        Assertions.assertEquals(3, bullets.size());
        for (int i = 0; i < bullets.size(); i++) {
            BaseBullet bullet = bullets.get(i);
            Assertions.assertTrue(bullet instanceof EnemyBullet);
            Assertions.assertEquals(bossEnemy.getLocationX() + (i * 2 - 2) * 10, bullet.getLocationX());
            Assertions.assertEquals(bossEnemy.getLocationY() + 2, bullet.getLocationY());
            Assertions.assertEquals(2*i-2, bullet.getSpeedX());
            Assertions.assertEquals(bossEnemy.getSpeedY() + 3, bullet.getSpeedY());
            Assertions.assertEquals(30, bullet.getPower());
        }
    }
    @Test
    public void testDropProps(){
        List<BaseProp> props=new LinkedList<>();
        bossEnemy.dropProps(props);
        //掉落3个道具.
        Assertions.assertEquals(3,props.size());
        for(int i=0;i<props.size();i++){
            BaseProp prop = props.get(i);

            //测试时boss敌机掉落道具速度均设置为5.默认情况下3种道具的掉落速度不同以便区分掉落道具的种类.
            //Assertions.assertEquals(5,prop.getSpeedY());

            Assertions.assertEquals(0,prop.getSpeedX());
            Assertions.assertEquals(10,prop.getLocationX());
            Assertions.assertEquals(20,prop.getLocationY());

        }


    }

}
