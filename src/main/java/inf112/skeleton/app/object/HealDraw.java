package inf112.skeleton.app.object;

import inf112.skeleton.app.interfaces.IMapObject;

public class HealDraw implements IMapObject {

    private int x;
    private int y;
    private boolean draw;
    
    public HealDraw(boolean draw) {
        this.draw = draw;
    }
    
    public HealDraw(int x, int y, boolean draw) {
        this.x = x;
        this.y = y;
        this.draw = draw;
    }
    
    @Override
    public int getX(){
        return x;
    }

    @Override
    public int getY(){
        return y;
    }

    @Override
    public void setX(int x){
        this.x = x;
    }

    @Override
    public void setY(int y){
        this.y = y;
    }

    @Override
    public String getName(){
        return draw ? "heal" : "healDraw";
    }

    public boolean draw() {
        return draw;
    }
}
