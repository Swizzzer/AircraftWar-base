package edu.hitsz.props.propFactory;

import edu.hitsz.props.BaseProp;

public interface PropFactory {
    BaseProp create(int locationX, int locationY, int speedX, int speedY);

}
