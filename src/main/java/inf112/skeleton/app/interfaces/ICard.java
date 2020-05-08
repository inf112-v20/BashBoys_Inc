package inf112.skeleton.app.interfaces;

import inf112.skeleton.app.Board;
import inf112.skeleton.app.Player;

/**
 * Interface for Card Objects
 */
public interface ICard {

    /**
     * Do given card ability
     * @param board Current board
     */
    void doStuff(Board board);

    /**
     * Get the priority of the card
     * @return int priority of this card
     */
    int getPriority();

    /**
     * Get name of card
     * @return String name of card
     */
    String getName();

    /**
     * Get extra feature of card
     * @return String feature
     */
    // Not being used, but is used by all ICard object so not removed
    String getExtra();

    Player getPlayer();
    void setPlayer(Player p);
}
