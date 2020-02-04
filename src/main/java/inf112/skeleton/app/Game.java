package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private numPlayers;
    private Board board;
    private Deck deck;

    private ViewEngine viewEngine;

    /**
     * Create a new game
     * @param numPlayers
     * @param width
     * @param height
     */
    public Game(int numPlayers, int width, int height, ViewEngine viewEngine) {
        this.viewEngine = viewEngine;
        this.numPlayers = numPlayers;
        this.board = new Board(width, height);
        this.deck = new Deck();
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
     * Display game on screen by passing the viewEngine to all render functions
     */
    private void renderGame() {
        board.render(viewEngine);
    }
}
