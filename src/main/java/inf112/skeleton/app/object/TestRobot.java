package inf112.skeleton.app.object;

import inf112.skeleton.app.IDirectionalObject;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;

public class TestRobot implements IDirectionalObject {

    int xPos = 0;
    int yPos = 0;
    Direction dir = Direction.NORTH;
    
    
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
    public Direction getDir() {
        return dir;
    }

    /**
     * Replace later, does not check collision, wall or valid move
     * Game could move instead easier to check collisions and stuff
     * @param amount - Amount to move in direction
     */
    public void move(int amount) {
        switch(dir) {
        case NORTH:
            xPos+=amount;
            break;
        case SOUTH:
            xPos-=amount;
            break;
        case EAST:
            yPos+=amount;
            break;
        case WEST:
            yPos-=amount;
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
