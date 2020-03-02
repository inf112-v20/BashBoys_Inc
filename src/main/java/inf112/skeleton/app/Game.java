package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.IMapObject;
import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.Wall;

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
        Robot r = new Robot("MainRobot");
        Robot r2 = new Robot("PushedRobot");
        place(r);
        board.addItem(r2, 11, 5);
        LwjglApplication app = new LwjglApplication(new ViewEngine(board), cfg);

        Wall w = new Wall(5, 8, Direction.NORTH, 1);

        board.addItem(w, 5, 8);
        board.addItem(new Wall(Direction.EAST, 1), 11, 3);
        board.addItem(new Wall(Direction.EAST), 11, 8);
        board.addItem(new Wall(Direction.SOUTH, 1), 11, 0);

        r.turn(LeftRight.RIGHT, 2);
        r2.turn(LeftRight.LEFT);

        while (true) {

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            board.fireLasers();
            for (IMapObject rb : board.getObjects()) {
                if (rb instanceof Robot)
                    System.out.println(rb.getName() + " has " + ((Robot) rb).getHp() + " hp left");
            }
            if (!board.moveItem(r, 1)) {
                r.turn(LeftRight.RIGHT);
            }
        }
    }
}
