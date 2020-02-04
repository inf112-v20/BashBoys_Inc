package inf112.skeleton.app;

import java.util.ArrayList;
public class Game {
    private ArrayList<Player> players = new ArrayList<>();
    private int numPlayers;
    private Board board;

    /**
     * Create a new game
     * @param numPlayers
     * @param width
     * @param height
     */
    public Game(int numPlayers, int width, int height) {
        this.numPlayers = numPlayers;
        this.board = new Board(12, 12);
    }

    /**
     * Add player to game
     * @param player
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Remove player from game
     * @param player
     */
    public void removePlayer(Player player) {
        players.remove(player);
    }

    public ArrayList<IMapObject> getObjects() {
        return this.board.getObjects();
    }

    public int getWidth() {
        return this.board.width;
    }

    public int getHeight() {
        return this.board.height;
    }
}
