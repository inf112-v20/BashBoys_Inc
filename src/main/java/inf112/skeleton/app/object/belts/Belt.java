package inf112.skeleton.app.object.belts;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.interfaces.IDirectionalObject;

public class Belt implements IDirectionalObject {

    protected int x = 0;
    protected int y = 0;
    protected int strength = 1;
    protected Direction dir;
    
    
    public Belt(Direction dir) {
        this.dir = dir;
    }
    
    
    public Belt(Direction dir, int strength) {
        this.dir = dir;
        this.strength = strength;
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
        return strength+""+dir+"belt";
    }

    @Override
    public Direction getDir() {
        return dir;
    }
    
    public int getStrength() {
        return strength;
    }
}
