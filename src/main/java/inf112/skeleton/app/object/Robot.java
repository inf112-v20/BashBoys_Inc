package inf112.skeleton.app.object;

import inf112.skeleton.app.IDirectionalObject;
import inf112.skeleton.app.IMapObject;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;

public class Robot implements IDirectionalObject {
    private int xPos = 5;
    private int yPos = 5;
    private String name = "RobotFaceSouth";
    Direction dir = Direction.SOUTH;
    

    public Robot() {
    }
    
    public Robot(int x, int y) {
        this.xPos = x;
        this.yPos = y;
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
    public void remove() {

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Direction getDir() {
        return dir;
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
