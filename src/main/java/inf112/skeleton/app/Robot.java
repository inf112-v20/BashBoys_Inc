package inf112.skeleton.app;

public class Robot implements IMapObject {
    private int x = 5;
    private int y = 5;
    private String name = "Robot";

    public void Robot() {
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
