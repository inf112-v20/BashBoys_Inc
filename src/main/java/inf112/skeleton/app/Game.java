package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.object.Hole;
import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.belts.Belt;

import java.util.concurrent.TimeUnit;

public class Game {

    private Board board;

    public Game() {
        this.board = new Board(12, 12);
    }

    /**
     * Place Robot on board
     * 
     * @param robot - robot to add
     */
    public void place(Robot robot) {
        board.addItem(robot, 5, 5);
    }

    public void play(LwjglApplicationConfiguration cfg) {
        Robot r = new Robot("robot 1",Direction.EAST);
        Robot r2 = new Robot("robot 5");
        place(r);
        board.addItem(r2, 11, 5);
        LwjglApplication app = new LwjglApplication(new ViewEngine(board), cfg);

        board.addItem(new Belt(Direction.NORTH,2), 5, 5);
        board.addItem(new Belt(Direction.NORTH,2), 5, 6);
        board.addItem(new Belt(Direction.NORTH,1), 5, 7);
        board.addItem(new Belt(Direction.EAST,1), 5, 8);
        board.addItem(new Hole(), 6, 8);
        //board.addItem(new Belt(Direction.NORTH), 5, 6);
        

        //board.addItem(new Wall(5Direction.NORTH, 1), 5, 8);
        //board.addItem(new Wall(Direction.EAST, 1), 11, 3);
        //board.addItem(new Wall(Direction.EAST), 11, 8);
        //board.addItem(new Wall(Direction.SOUTH, 1), 11, 0);

        //r.turn(LeftRight.RIGHT, 2);
        //r2.turn(LeftRight.LEFT);

        
        boolean t = true;
        while (t) {

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            //board.moveItem(r, 1);
            board.moveBelts();
            
            /*
            board.fireLasers();
            for (IMapObject rb : board.getObjects()) {
                if (rb instanceof Robot)
                    System.out.println(rb.getName() + " has " + ((Robot) rb).getHp() + " hp left");
            }
            if (!board.moveItem(r, 1)) {
                r.turn(LeftRight.RIGHT);
            }
            */
            //t=false;
        }
    }
}
