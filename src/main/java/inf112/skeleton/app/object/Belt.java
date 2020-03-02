package inf112.skeleton.app.object;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;

public class Belt implements IDirectionalObject {

    private int x = 0;
    private int y = 0;
    private int strength = 1;
    private Direction dir;
    private LeftRight corner;
    
    
    public Belt(Direction dir) {
        this.dir = dir;
    }
    
    public Belt(Direction dir, LeftRight corner) {
        this.dir = dir;
        this.corner = corner;
    }
    
    public Belt(Direction dir, int strength) {
        this.dir = dir;
        this.strength = strength;
    }
    
    public Belt(Direction dir, int strength, LeftRight corner) {
        this.dir = dir;
        this.strength = strength;
        this.corner = corner;
    }
    
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;

    }

    @Override
    public String getName() {
        return strength == 1 ? "Single belt" : "Double belt";
    }

    @Override
    public Direction getDir() {
        return dir;
    }
    
    public int getStrength() {
        return strength;
    }
    
    public LeftRight corner() {
        return corner;
    }

}
