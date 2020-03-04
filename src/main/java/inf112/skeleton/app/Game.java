package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.Hole;
import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.Wall;
import inf112.skeleton.app.object.belts.Belt;
import inf112.skeleton.app.object.belts.CornerBelt;
import inf112.skeleton.app.object.belts.CornerJoinBelt;
import inf112.skeleton.app.object.belts.MergeBelt;

import java.util.concurrent.TimeUnit;

public class Game {

    private Board board;

    public Game() {
        this.board = new Board(12, 12);
    }

    public void play(LwjglApplicationConfiguration cfg) {
        LwjglApplication app = new LwjglApplication(new ViewEngine(board), cfg);
        
        Direction west = Direction.WEST;
        Direction east = Direction.EAST;
        Direction north = Direction.NORTH;
        Direction south = Direction.SOUTH;
        
        LeftRight left = LeftRight.LEFT;
        LeftRight right = LeftRight.RIGHT;
        
        Robot r = new Robot("robot 1",Direction.EAST);
        Robot r2 = new Robot("robot 5");
        board.addItem(r, 7, 5);
        board.addItem(r2, 11, 5);

        board.addItem(new CornerBelt(north,2,right), 0, 0);
        board.addItem(new CornerBelt(south,2,right), 0, 1);
        board.addItem(new CornerBelt(east,2,right), 0, 2);
        board.addItem(new CornerBelt(west,2,right), 0, 3);
        board.addItem(new CornerBelt(north,2,left), 0, 4);
        board.addItem(new CornerBelt(south,2,left), 0, 5);
        board.addItem(new CornerBelt(east,2,left), 0, 6);
        board.addItem(new CornerBelt(west,2,left), 0, 7);
        
        board.addItem(new CornerJoinBelt(north,2,right), 1, 0);
        board.addItem(new CornerJoinBelt(south,2,right), 1, 1);
        board.addItem(new CornerJoinBelt(east,2,right), 1, 2);
        board.addItem(new CornerJoinBelt(west,2,right), 1, 3);
        board.addItem(new CornerJoinBelt(north,2,left), 1, 4);
        board.addItem(new CornerJoinBelt(south,2,left), 1, 5);
        board.addItem(new CornerJoinBelt(east,2,left), 1, 6);
        board.addItem(new CornerJoinBelt(west,2,left), 1, 7);
        
        board.addItem(new Belt(north,2), 2, 0);
        board.addItem(new Belt(south,2), 2, 1);
        board.addItem(new Belt(east,2), 2, 2);
        board.addItem(new Belt(west,2), 2, 3);
        board.addItem(new MergeBelt(north,2), 2, 4);
        board.addItem(new MergeBelt(south,2), 2, 5);
        board.addItem(new MergeBelt(east,2), 2, 6);
        board.addItem(new MergeBelt(west,2), 2, 7);
        
        board.addItem(new CornerBelt(north,1,right), 3, 0);
        board.addItem(new CornerBelt(south,1,right), 3, 1);
        board.addItem(new CornerBelt(east,1,right), 3, 2);
        board.addItem(new CornerBelt(west,1,right), 3, 3);
        board.addItem(new CornerBelt(north,1,left), 3, 4);
        board.addItem(new CornerBelt(south,1,left), 3, 5);
        board.addItem(new CornerBelt(east,1,left), 3, 6);
        board.addItem(new CornerBelt(west,1,left), 3, 7);
        
        board.addItem(new CornerJoinBelt(north,1,right), 4, 0);
        board.addItem(new CornerJoinBelt(south,1,right), 4, 1);
        board.addItem(new CornerJoinBelt(east,1,right), 4, 2);
        board.addItem(new CornerJoinBelt(west,1,right), 4, 3);
        board.addItem(new CornerJoinBelt(north,1,left), 4, 4);
        board.addItem(new CornerJoinBelt(south,1,left), 4, 5);
        board.addItem(new CornerJoinBelt(east,1,left), 4, 6);
        board.addItem(new CornerJoinBelt(west,1,left), 4, 7);
        
        board.addItem(new Belt(north,1), 5, 0);
        board.addItem(new Belt(south,1), 5, 1);
        board.addItem(new Belt(east,1), 5, 2);
        board.addItem(new Belt(west,1), 5, 3);
        board.addItem(new MergeBelt(north,1), 5, 4);
        board.addItem(new MergeBelt(south,1), 5, 5);
        board.addItem(new MergeBelt(east,1), 5, 6);
        board.addItem(new MergeBelt(west,1), 5, 7);
        
        //Real tings
        board.addItem(new Belt(north,2), 7, 5);
        board.addItem(new Belt(north,2), 7, 6);
        board.addItem(new Belt(north,1), 7, 7);
        board.addItem(new CornerBelt(east,2,LeftRight.RIGHT), 7, 8);
        board.addItem(new Hole(), 8, 8);
        board.addItem(new Wall(north), 7, 7);
        
        board.addItem(new Belt(west,2), 11, 8); //Display under wall
        board.addItem(new Belt(east,2), 10, 8); //Display under wall
        
        board.addItem(new Wall(east), 11, 8);
        board.addItem(new Wall(west), 10, 8);
                
        board.addItem(new Wall(north), 11, 7);
        board.addItem(new Wall(north), 10, 7);
        
        board.addItem(new Wall(south), 11, 9);
        board.addItem(new Wall(south), 10, 9);
        
        boolean t = true;
        while (t) {

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            board.moveBelts();
            board.fireLasers();
            
        }
    }
}
