package edu.hitsz.props;

import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.aircraft.*;

public abstract class BaseProp extends AbstractFlyingObject {
    protected AbstractAircraft Craft=null;
    public BaseProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);

    }
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }
    public abstract void takingEffect(HeroAircraft Craft,boolean isMusic);
}
