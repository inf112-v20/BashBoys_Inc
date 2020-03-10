package inf112.skeleton.app.object;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.interfaces.IMapObject;

public class Laser implements IMapObject {

    private int x;
    private int y;
    private Direction dir;
    private int strength;
    
    public Laser(int x,int y,Direction dir,int strength) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.strength = strength;
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
        if(dir == Direction.NORTH || dir == Direction.SOUTH)return strength+"VERTICAL";
        else return strength+"HORIZONTAL";
    }

}
