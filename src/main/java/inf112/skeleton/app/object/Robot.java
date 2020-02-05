package inf112.skeleton.app.object;

import inf112.skeleton.app.IMapObject;

public class Robot implements IMapObject {
    private int x = 5;
    private int y = 5;
    private String name = "RobotFaceSouth";

    public Robot() {
    }
    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setX(int x) {

    }

    @Override
    public void setY(int y) {

    }

    @Override
    public void remove() {

    }

    @Override
    public String getName() {
        return this.name;
    }
}
