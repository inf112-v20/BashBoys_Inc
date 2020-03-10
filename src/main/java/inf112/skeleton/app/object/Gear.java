package inf112.skeleton.app.object;

import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.interfaces.IMapObject;

public class Gear implements IMapObject {

    private LeftRight lr;
    private int x = 0;
    private int y = 0;
    
    public Gear(LeftRight lr) {
        this.lr = lr;
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
        return "gear"+lr;
    }
    
    public LeftRight getLR() {
        return lr;
    }

}
