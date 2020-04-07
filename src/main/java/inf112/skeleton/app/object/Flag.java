package inf112.skeleton.app.object;

import inf112.skeleton.app.interfaces.ICheckPoint;

public class Flag implements ICheckPoint {

    private int x;
    private int y;
    private String name;
    private int value;

    public Flag() {
    }
    public Flag(String name,int val) {
        this.name = name;
        value = val;
    }
    public Flag(int x, int y, String name,int val) {
        this.x = x;
        this.y = y;
        this.name = name;
        value = val;
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
    
    public int getNr() {
        return value;
    }

}
