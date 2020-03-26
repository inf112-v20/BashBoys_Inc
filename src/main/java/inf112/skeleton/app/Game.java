package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inf112.skeleton.app.cards.Deck;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.*;
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
    public Deck deck = new Deck();
    public boolean all_moves_done = false;

    public Game() {
        this.board = new Board(12, 12);
        players = new ArrayList<Player>();
    }

    /**
     * Returns currentPlayer
     * @return currentPlayer as player
     */
    public Player getPlayer(){
        return players.get(currentPlayer);
    }

    /**
     * Makes currentPlayer too the next player on the player list
     */
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

        // Shortcuts
        Direction west = Direction.WEST;
        Direction east = Direction.EAST;
        Direction north = Direction.NORTH;
        Direction south = Direction.SOUTH;

        // Add robots
        board.addItem(r, 2, 8);
        board.addItem(r2, 11, 5);
        board.addItem(r3, 10, 0);

        board.addItem(new Wall(north, 2), 1, 1);
        board.addItem(new Wall(south), 1, 1);

        // Real tings - Working on map designer for easier adding of elements
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
        board.addItem(new MergeBelt(north, 2),5,5);
        board.addItem(new CornerJoinBelt(west, LeftRight.LEFT),5,6);

        int phase = 0;
        boolean gameBool = true;
        while (gameBool) {
            programmingPhase();
            sleep(1);
            board.turnStuff((phase++ % 5) + 1);
            System.out.println(bob.getRobot().isDead());
            respawn();
            all_moves_done = true;
        }
    }

    /**
     * Checks if all players are done with their programming and runs programmingMove
     */
    public void programmingPhase() {
        boolean all_ready = false;
        all_moves_done = false;
        while (!all_ready) {
            all_ready = true;
            for (Player player : players) {
                if (!player.isReady()) {
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

        if(!players.get(0).getRobot().isDead()){
            all_moves_done = true;
        }
}

    /**
     * Goes through all players card for each phase (card 1 through 5) and executes the card with
     * highest priority first and so on until each robot has moved.
     */
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

    /**
     * returns list of all players in game
     * @return list of players in game
     */
    public ArrayList<Player> players(){
        return players;
    }

    /**
     * Board that is played on
     * @return the board being played on
     */
    public Board getBoard(){
        return board;
    }

    /**
     * Pauses program for given time
     * @param seconds - Time too pause for in second
     */
    public void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Respawns all player robots that are dead
     */
    public void respawn(){
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

    /**
     * Respawns given players robot if dead
     * @param p - player too revive robot
     */
    // Not being used yet, but is need in the future
    public void respawn(Player p){
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
