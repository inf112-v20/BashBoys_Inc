package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.Wall;
public class Game {
    private int numPlayers;
    private Board board;
    private int width;
    private int height;

    /**
     * Create a new game
     * @param numPlayers
     * @param width
     * @param height
     */
    public Game(int numPlayers, int width, int height) {
        this.numPlayers = numPlayers;
        this.width = width;
        this.height = height;
        this.board = new Board(12, 12);
    }

    /**
     * returns all objects on game-board
     * @return ArrayList with Map-objects
     */
    public ArrayList<IMapObject> getObjects() {
        return this.board.getObjects();
    }
    
    public void place(Robot r) {
        board.addItem(r, 5, 5);
    }
    
    public void play(LwjglApplicationConfiguration cfg) {
        Robot r = new Robot("MainRobot");
        Robot r2 = new Robot("PushedRobot");
        place(r);
        board.addItem(r2, 11, 5);
        LwjglApplication app = new LwjglApplication(new ViewEngine(board), cfg);
        
        Wall w = new Wall(5,8,Direction.NORTH);
        
        board.addItem(w, 5, 8);
        
        r.turn(LeftRight.RIGHT, 2);
        r2.turn(LeftRight.LEFT);
        
        
        while (true) {
            
            try{
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(!board.moveItem(r, 1)) {
                r.turn(LeftRight.RIGHT);
            }
            
        }
    }
}
