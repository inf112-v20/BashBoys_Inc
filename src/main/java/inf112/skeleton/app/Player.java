package inf112.skeleton.app;

import inf112.skeleton.app.cards.ICard;
import inf112.skeleton.app.object.Robot;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
    private Boolean ready = false;
    private String name;
    private ArrayList<ICard> hand = new ArrayList<>();
    private int handSize = 9;
    private Robot robot;
    private ArrayList<ICard> programSheet = new ArrayList<>();

    /**
     * Create a new player
     * @param name - name of player
     */
    public Player(String name) {
        this.name = name;
    }
    
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
     * Add a card to sheet at chosen index
     * If there already is a card there it gets moved to hand
     * @param card - card to add
     */
    public void addCardToSheet(ICard card) {
        programSheet.add(card);
        /*if(index > 4 || index < 0) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        if(programSheet.contains(card)) {
            if(getCardFromSheet(index) instanceof ICard) {
                Collections.swap(programSheet, index, programSheet.indexOf(card));
            }
            else {
                programSheet.remove(card);
                addCardToSheet(card, index);
            }
        }
        else if(getCardFromSheet(index) instanceof ICard) {
            programSheet.add(index, card);
            giveCard(programSheet.get(index+1));
            programSheet.remove(index+1);
        }
        else{
            programSheet.add(card);
        }
        removeCardFromHand(card);

         */
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
     * Move a card from the sheet to the hand
     * @param card - card to move
     */
    public void moveCardFromSheet(ICard card) {
        if (programSheet.contains(card)) {
            hand.add(card);
            programSheet.remove(card);
        }
    }

    /**
     * Clear the sheet
     */
    public void clearSheet() {
        programSheet.clear();
    }
    
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
}