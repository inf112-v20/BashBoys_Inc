package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.Flag;
import inf112.skeleton.app.object.Gear;
import inf112.skeleton.app.object.HealDraw;
import inf112.skeleton.app.object.Hole;
import inf112.skeleton.app.object.Pusher;
import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.Wall;
import inf112.skeleton.app.object.belts.Belt;
import inf112.skeleton.app.object.belts.CornerBelt;
import inf112.skeleton.app.object.belts.CornerJoinBelt;
import inf112.skeleton.app.object.belts.MergeBelt;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Game {

    private Board board;
    private int currentPlayer;
    private ArrayList<Player> players;

    public Game() {
        this.board = new Board(12, 12);
        players = new ArrayList<Player>();
    }

    public Player getPlayer(){
        return players.get(currentPlayer);
    }

    public void nextPlayer(){
        currentPlayer = ++currentPlayer % players.size();
    }

    public void play(LwjglApplicationConfiguration cfg){
        new LwjglApplication(new ViewEngine(this), cfg);

        // Robots
        Robot r = new Robot("robot 1", Direction.EAST);
        Robot r2 = new Robot("robot 5");
        Robot r3 = new Robot("robot 2");

        Flag flag1 = new Flag(0, 0, "flag1");
        // Players
        Player bob = new Player("Bob", r);
        bob.setSpawn(flag1);
        players.add(bob);
        // players.add(new Player("Jon", r2));

        // Shortcuts
        Direction west = Direction.WEST;
        Direction east = Direction.EAST;
        Direction north = Direction.NORTH;
        Direction south = Direction.SOUTH;

        LeftRight left = LeftRight.LEFT;
        LeftRight right = LeftRight.RIGHT;

        // Add robots
        board.addItem(r, 2, 8);
        board.addItem(r2, 11, 5);
        board.addItem(r3, 10, 0);

        board.addItem(new Wall(north, 2), 1, 1);
        board.addItem(new Wall(south), 1, 1);

        // Real tings
        // board.addItem(new Belt(north,2), 7, 5);
        board.addItem(new Belt(north, 2), 7, 6);
        board.addItem(new Belt(north, 1), 7, 7);
        board.addItem(new CornerBelt(east, 2, LeftRight.RIGHT), 7, 8);
        board.addItem(new Hole(), 8, 8);
        board.addItem(new Wall(north, 3), 7, 7);
        board.addItem(new Pusher(west, true), 7, 4);
        board.addItem(new Wall(south), 7, 3);

        board.addItem(new Belt(west, 2), 11, 8); // Display under wall
        board.addItem(new Belt(east, 2), 10, 8); // Display under wall

        board.addItem(new Wall(east), 11, 8);
        board.addItem(new Wall(west), 10, 8);

        board.addItem(new Wall(north, 1), 11, 7);
        board.addItem(new Wall(north), 10, 7);
        board.addItem(new Wall(north), 11, 0);

        board.addItem(new Wall(south), 11, 9);
        board.addItem(new Wall(south), 10, 9);

        board.addItem(new Pusher(west, true), 8, 5);
        board.addItem(new Pusher(east, false), 5, 5);
        board.addItem(new Gear(LeftRight.RIGHT), 7, 5);

        board.addItem(new HealDraw(false), 0, 0);
        board.addItem(new HealDraw(true), 0, 1);
        board.addItem(flag1, 0, 11);

        // test items
        board.addItem(new Belt(south, 1), 3, 8);
        board.addItem(new Belt(south, 1), 3, 7);
        board.addItem(new Belt(south, 1), 3, 6);

        int phase = 0;
        boolean t = true;
        while (t) {
            programmingPhase();
            sleep(1);
            board.turnStuff((phase++ % 5) + 1);
            System.out.println(bob.getRobot().isDead());
            respawn();
        }
    }

    private void gameLoop(){
        boolean gameWon = false;
        Player winner = null;
        while (!gameWon) {

            // Upgrade phase

            // Program phase

            for (int i = 1; i < 6; i++) {
                // Cards in phase i

                board.turnStuff(i);

            }

        }
        System.out.println(winner.getName() + " won the game");
    }

    public void programmingPhase(){
        boolean all_ready = false;
        while (all_ready == false) {
            all_ready = true;
            for (Player player : players) {
                if (player.isReady() == false) {
                    all_ready = false;
                }
            }
            sleep(1);
        }
        programmingMove();
        for (Player player : players) {
            player.clearSheet();
            player.setReady(false);
        }
    }

    public void programmingMove(){
        for (int card_nr = 0; card_nr < 5; card_nr++) {
            ArrayList<Player> players_left = (ArrayList<Player>) players.clone();
            for (Player player : players) {
                Player top_priority = null;
                for (Player player_left : players_left) {
                    if (player_left.getCardFromSheet(card_nr) != null) {
                        if (top_priority == null) {
                            top_priority = player_left;
                        } else if (player_left.getCardFromSheet(card_nr).getPriority() < top_priority
                                .getCardFromSheet(card_nr).getPriority()) {
                            top_priority = player_left;
                        }
                    }
                }
                if (top_priority != null) {
                    top_priority.getCardFromSheet(card_nr).doStuff(top_priority.getRobot(), board);
                    players_left.remove(top_priority);
                }
                sleep(1);
            }
            board.turnStuff(card_nr + 1);
            sleep(1);
        }
    }

    public ArrayList<Player> players(){
        return players;
    }

    public Board getBoard(){
        return board;
    }

    public void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void respawn(){
        for (Player p : players) {
            if (p.getRobot().isDead() && p.getSpawn() != null) {
                if (board.getRobots().contains(p.getRobot()))
                    board.removeItem(p.getRobot());
                p.getRobot().setHp(9);
                int x = p.getSpawn().getX();
                int y = p.getSpawn().getY();
                p.getRobot().setX(x);
                p.getRobot().setY(y);
                board.addItem(p.getRobot(), x, y);
            }
        }
    }
}
