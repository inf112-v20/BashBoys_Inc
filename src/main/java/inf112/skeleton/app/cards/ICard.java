package inf112.skeleton.app.cards;

import inf112.skeleton.app.Board;
import inf112.skeleton.app.object.Robot;

/**
 * Interface for Card Objects
 */
public interface ICard {

    /**
     * Do given card ability
     * @param robot Which robot to affect
     * @param board Current board
     */
    void doStuff(Robot robot, Board board);

    /**
     * Get the priority of the card
     * @return int priority of this card
     */
    // Not being used, but is used by all ICard object so not removed
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

}
