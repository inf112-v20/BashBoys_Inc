package inf112.skeleton.app.object;

public class Floor implements IMapObject {

    private int x = 0;
    private int y = 0;
    
    public Floor(int x, int y) {
        
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
        return "Floor at (" + x + ", " + y + ")";
    }

}
