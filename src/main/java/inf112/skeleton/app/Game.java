package inf112.skeleton.app;

import java.util.ArrayList;
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

}
