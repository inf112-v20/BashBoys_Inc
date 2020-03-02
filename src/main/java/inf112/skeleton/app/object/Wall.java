package inf112.skeleton.app.object;

import inf112.skeleton.app.enums.Direction;

public class Wall implements IDirectionalObject {

    private int x;
    private int y;
    private Direction dir;
    private int laserDmg;

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
        if (laserDmg == 0)return dir + "Wall";
        else if (laserDmg == 1) return dir + "Wall \\w laser";
        else return dir + "Wall \\w double laser";
    }

    @Override
    public Direction getDir() {
        return dir;
    }
    
    public int getDmg() {
        return laserDmg;
    }
    
}
