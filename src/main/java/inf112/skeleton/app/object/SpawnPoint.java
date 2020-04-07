package inf112.skeleton.app.object;

import inf112.skeleton.app.interfaces.ICheckPoint;

public class SpawnPoint implements ICheckPoint {

    private int x;
    private int y;
    
    @Override
    public int getX(){
        return x;
    }

    @Override
    public int getY(){
        // TODO Auto-generated method stub
        return y;
    }

    @Override
    public void setX(int x){
        this.x = x;
    }

    @Override
    public void setY(int y){
        this.y=y;
    }

    @Override
    public String getName(){
        return "1NORTHbelt";
    }

}
