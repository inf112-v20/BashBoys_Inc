package inf112.skeleton.app.object;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.interfaces.IDirectionalObject;

public class Wall implements IDirectionalObject {

    private int x = 0;
    private int y = 0;
    private Direction dir;
    private int laserDmg = 0;
    
    public Wall(Direction dir, int dmg ) {
        this.dir = dir;
        laserDmg = 0;
        this.laserDmg = dmg;
    }
    
    public Wall(Direction dir ) {
        this.dir = dir;
        laserDmg = 0;
    }

    public Wall(int x, int y, Direction dir ) {
        this.dir = dir;
        this.x = x;
        this.y = y;
        laserDmg = 0;
    }
    public Wall(int x, int y, Direction dir, int dmg) {
        this.dir = dir;
        this.x = x;
        this.y = y;
        laserDmg = dmg;
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
        if (laserDmg == 0)return "wall"+dir;
        else if (laserDmg == 1) return dir + "wall \\w laser";
        else return dir + "wall \\w double laser";
    }

    @Override
    public Direction getDir() {
        return dir;
    }
    
    public int getDmg() {
        return laserDmg;
    }
    
}
