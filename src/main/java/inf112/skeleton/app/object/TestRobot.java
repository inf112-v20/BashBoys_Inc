package inf112.skeleton.app.object;

import inf112.skeleton.app.IDirectionalObject;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;

//Prototype robot used for testing
public class TestRobot implements IDirectionalObject {

    int xPos = 0;
    int yPos = 0;
    Direction dir = Direction.SOUTH;
    
    
    @Override
    public int getX() {
        return xPos;
    }

    @Override
    public int getY() {
        return yPos;
    }

    @Override
    public void setX(int x) {
        xPos = x;

    }

    @Override
    public void setY(int y) {
        yPos = y;

    }

    @Override
    public void remove() {
        // TODO

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Direction getDir() {
        return dir;
    }

    /**
     * Only changes x/y depending on direction
     * does not check validity of move
     * @param amount - Amount to move in direction
     */
    public void move(int amount) {
        switch(dir) {
        case NORTH:
            yPos+=amount;
            break;
        case SOUTH:
            yPos-=amount;
            break;
        case EAST:
            xPos+=amount;
            break;
        case WEST:
            xPos-=amount;
            break;
            
        }
    }
    
    @Override
    public void turn(LeftRight lr) {
        dir = Direction.turn(lr,dir);

    }

    @Override
    public void turn(LeftRight lr, int turnss) {
        for(int i = 0;++i<=turnss;) {
            turn(lr);
        }
        
    }

}
