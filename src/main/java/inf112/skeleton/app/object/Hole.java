package inf112.skeleton.app.object;

import inf112.skeleton.app.interfaces.IMapObject;

public class Hole implements IMapObject {

    private int x = 0;
    private int y = 0;
    private String name;
    
    public Hole() {
        name = "hole";
    }
    
    public Hole(String s) {
        name = s;
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
