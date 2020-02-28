package inf112.skeleton.app.object;

import inf112.skeleton.app.IDirectionalObject;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;

public class Robot implements IDirectionalObject {
    private int xPos = 5; //Base position
    private int yPos = 5;
    private String name = "RobotFaceSouth";
    public Direction dir = Direction.SOUTH;

    public Robot() {
    }
    
    public Robot(String s) {
        this.name = s;
    }
    
    public Robot(int x, int y, String s) {
        this.xPos = x;
        this.yPos = y;
        this.name = s;
    }
    
    @Override
    public int getX() {
        return this.xPos;
    }

    @Override
    public int getY() {
        return this.yPos;
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
    public String getName() {
        return this.name;
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
        move(amount, dir);
    }
    
    public void move(int amount, Direction dir) {
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

    
    /**
     * turn direction by 1
     * @param lr direction to turn
     */    
    public void turn(LeftRight lr) {
        dir = Direction.turn(lr,dir);
    }

    /**
     * 
     * @param lr LEFT or RIGHT
     * @param amount times to turn in lr direction
     */
    public void turn(LeftRight lr, int amount) {
        for(int i = 0;++i<=amount;) {
            turn(lr);
        }
        
    }
    
    /**
     * 
     * @param dir - Direction to set to
     */
    public void setDir(Direction dir) {
        this.dir = dir;
    }
}
