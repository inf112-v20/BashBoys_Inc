package inf112.skeleton.app.object;

import inf112.skeleton.app.enums.Direction;

public class Wall implements IDirectionalObject {

    private int x;
    private int y;
    private Direction dir;

    public Wall(int x, int y, Direction dir ) {
        this.dir = dir;
        this.x = x;
        this.y = y;
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
        return "Wall" + dir;
    }

    @Override
    public Direction getDir() {
        return dir;
    }
    
    
}
