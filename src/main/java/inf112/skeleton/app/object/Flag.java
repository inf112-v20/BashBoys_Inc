package inf112.skeleton.app.object;

import inf112.skeleton.app.interfaces.ICheckPoint;

public class Flag implements ICheckPoint {

    private int x;
    private int y;
    String name;

    public Flag(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
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
        return name;
    }

}
