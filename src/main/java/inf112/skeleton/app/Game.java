package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inf112.skeleton.app.cards.ICard;
import inf112.skeleton.app.cards.MoveCard;
import inf112.skeleton.app.cards.RotateCard;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.Belt;
import inf112.skeleton.app.object.IMapObject;
import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.Wall;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Game {

    private Board board;
    private Player player1;
    private Player player2;
    private ArrayList<Player> players;

    public Game() {
        this.board = new Board(12, 12);

        player1 = new Player("player1");
        player2 = new Player("player2");
        players = new ArrayList<Player>();
        players.add(player1);
        players.add(player2);

        ICard move1 = new MoveCard(1, 1, "m1");
        ICard move2 = new MoveCard(2, 2, "m2");
        ICard move3 = new MoveCard(3, 3, "m3");
        ICard rotL = new RotateCard(LeftRight.LEFT, 4, false, "rL");
        ICard rotR = new RotateCard(LeftRight.RIGHT, 5, false, "rR");
        player1.addCardToSheet(move2, 0);
        player1.addCardToSheet(rotL, 1);
        player1.addCardToSheet(move1, 2);
        player1.addCardToSheet(rotR, 3);
        player1.addCardToSheet(move3, 4);

        player2.addCardToSheet(rotL, 0);
        player2.addCardToSheet(move3, 1);
        player2.addCardToSheet(rotR, 2);
        player2.addCardToSheet(move2, 3);
        player2.addCardToSheet(move1, 4);
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
        Robot r3 = new Robot("Robot2");

        player1.addRobot(r);
        player2.addRobot(r3);
        board.addItem(r3, 4, 4);

        Robot r2 = new Robot("PushedRobot");
        place(r);
        board.addItem(r2, 11, 5);
        LwjglApplication app = new LwjglApplication(new ViewEngine(board), cfg);


        board.addItem(new Belt(Direction.NORTH,2), 5, 5);
        board.addItem(new Belt(Direction.NORTH,2), 5, 6);
        board.addItem(new Belt(Direction.NORTH,1), 5, 7);
        board.addItem(new Belt(Direction.EAST,1,LeftRight.RIGHT), 5, 8);
        //board.addItem(new Belt(Direction.NORTH), 5, 6);
        
        /*
        board.addItem(new Wall(5Direction.NORTH, 1), 5, 8);
        board.addItem(new Wall(Direction.EAST, 1), 11, 3);
        board.addItem(new Wall(Direction.EAST), 11, 8);
        board.addItem(new Wall(Direction.SOUTH, 1), 11, 0);

        r.turn(LeftRight.RIGHT, 2);
        r2.turn(LeftRight.LEFT);
         */
        boolean t = true;
        while (t) {

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            programmingPhase();
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

    public void programmingPhase() {
        for(int cardnr = 0; cardnr < 5; cardnr++){
            for(Player player : players) {
                player.getCardFromSheet(cardnr).getPriority();
                player.getCardFromSheet(cardnr).doStuff(player.getRobot(), board);
            }

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            board.moveBelts();
        }

    }
}
