package inf112.skeleton.app.interfaces;

import inf112.skeleton.app.object.Robot;

import java.util.ArrayList;

public interface IPlayer {

    void giveCard(ICard card);

    /**
     * Add card to hand
     * @param cards - card to add to hand
     */
    void giveCard(ArrayList<ICard> cards);

    /**
     * Remove card from hand
     * @param card - card to remove from hand
     */
    void removeCardFromHand(ICard card);

    /**
     * Returns name of the player
     * @return Player name
     */
    String getName();

    /**
     * Returns list with all cards in hand
     * @return ArrayList with all cards in hand
     */
    ArrayList<ICard> getHand();

    /**
     * Returns list with all cards in program sheet
     * @return ArrayList with all cards in program sheet
     */
    ArrayList<ICard> getProgramSheet();

    /**
     * Add a card to sheet
     * @param card - card to add
     */
    void addCardToSheet(ICard card);

    /**
     * Get card from sheet at index or null if there is no card
     * @param index - index of card
     * @return Card from sheet at index
     */
    ICard getCardFromSheet(int index);

    /**
     * Clear the sheet
     */
    void clearSheet();

    /**
     * Get robot assigned to player
     * @return robot
     */
    Robot getRobot();

    /**
     * Sets ready to false or true
     * @param r
     */
    void setReady(boolean r);

    /**
     * Returns if the player is ready or not
     * @return  ready
     */
    boolean isReady();

    /**
     * gets spawnpoint
     * @return current spawnpoint
     */
    ICheckPoint getSpawn();

    /**
     * sets new spawn point for player
     * @param nSpawn - new valid spawn point
     */
    void setSpawn(ICheckPoint nSpawn);
}