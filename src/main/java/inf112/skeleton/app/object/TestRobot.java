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

    @Override
    public void turn(LeftRight lr) {
        dir = Direction.turn(lr,dir);

    }
    
    /**
     * Replace later, does not check coalition, wall or valid move
     * @param i - Amount to move in direction
     */
    public void move(int i) {
        switch(dir) {
        case NORTH:
            xPos+=i;
            break;
        case SOUTH:
            xPos-=i;
            break;
        case EAST:
            yPos+=i;
            break;
        case WEST:
            yPos-=i;
            break;
            
        }
    }

    @Override
    public void turn(LeftRight lr, int turnss) {
        for(int i = 0;++i<=turnss;) {
            turn(lr);
        }
        
    }

}
