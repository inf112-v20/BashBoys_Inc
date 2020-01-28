package inf112.skeleton.app;

import java.util.ArrayList;

public class Game {
    ArrayList<Player> players = new ArrayList<>();
    Player currentPLayer;
    Board board;

    /**
     * Create a new game
     * @param numPlayers
     * @param width
     * @param height
     */
    public Game(int numPlayers, int width, int height) {
        this.board = new Board(width, height);
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

    /**
     * Display game on screen
     */
    private void renderGame() {

    }
}
