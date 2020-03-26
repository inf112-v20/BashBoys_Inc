package inf112.skeleton.app;

import inf112.skeleton.app.cards.ICard;
import inf112.skeleton.app.interfaces.ICheckPoint;
import inf112.skeleton.app.object.Robot;

import java.util.ArrayList;

public class Player {
    private Boolean ready = false;
    private String name;
    private ArrayList<ICard> hand = new ArrayList<>();
    private int handSize = 9;
    private Robot robot;
    private ArrayList<ICard> programSheet = new ArrayList<>();
    private ICheckPoint spawn;

    /**
     * Create a new player
     * @param name - name of player
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Create a new player
     * @param name - name of player
     * @param r - robot assigned to player
     */
    public Player(String name, Robot r) {
        this.name = name;
        robot = r;
    }

    /**
     * Add card to hand
     * @param card - card to add to hand
     */
    public void giveCard(ICard card) {
        if (hand.size() == handSize)
            return;
        hand.add(card);
    }

    /**
     * Remove card from hand
     * @param card - card to remove from hand
     */
    public void removeCardFromHand(ICard card) {
        hand.remove(card);
    }

    /**
     * Returns name of the player
     * @return Player name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns list with all cards in hand
     * @return ArrayList with all cards in hand
     */
    public ArrayList<ICard> getHand(){
        return hand;
    }

    /**
     * Returns list with all cards in program sheet
     * @return ArrayList with all cards in program sheet
     */
    public ArrayList<ICard> getProgramSheet(){
        return programSheet;
    }

    /**
     * Add a card to sheet
     * @param card - card to add
     */
    public void addCardToSheet(ICard card) {
        programSheet.add(card);
    }

    /**
     * Get card from sheet at index or null if there is no card
     * @param index - index of card
     * @return Card from sheet at index
     */
    public ICard getCardFromSheet(int index) {
        try {
            programSheet.get(index);
        } catch ( IndexOutOfBoundsException e ) {
            return null;
        }
        return programSheet.get(index);
    }

    /**
     * Clear the sheet
     */
    public void clearSheet() {
        programSheet.clear();
    }

    /**
     * Get robot assigned to player
     * @return robot
     */
    public Robot getRobot() {
        return robot;
    }

    /**
     * Sets ready to false or true
     * @param r
     */
    public void setReady(boolean r) {
        ready = r;
    }

    /**
     * Returns if the player is ready or not
     * @return  ready
     */
    public boolean isReady() {
        return ready;
    }
    
    /**
     * gets spawnpoint
     * @return current spawnpoint
     */
    public ICheckPoint getSpawn() {
        return spawn;
    }
    
    /**
     * sets new spawn point for player
     * @param nSpawn - new valid spawn point
     */
    public void setSpawn(ICheckPoint nSpawn) {
        spawn = nSpawn;
    }
}