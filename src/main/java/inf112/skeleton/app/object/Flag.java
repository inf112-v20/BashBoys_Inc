package inf112.skeleton.app.object;

import inf112.skeleton.app.interfaces.ICheckPoint;

public class Flag implements ICheckPoint {

    private int x;
    private int y;
    private String name;
    private int value;

    public Flag() {
    }
    public Flag(String name,int value) {
        this.name = name;
        this.value = value;
    }
    public Flag(int x, int y, String name,int value) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.value = value;
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
