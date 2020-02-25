package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.Collections;

import inf112.skeleton.app.object.ICard;

public class Player {
    private String name;
    private ArrayList<ICard> hand = new ArrayList<>();
    private int handSize = 9;
    private ArrayList<ICard> programSheet = new ArrayList<>();

    /**
     * Create a new player
     * @param name
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Add card to hand
     * @param card
     */
    public void giveCard(ICard card) {
        if (hand.size() == handSize)
            return;
        hand.add(card);
    }

    /**
     * Remove card from hand
     * @param card
     */
    public void removeCardFromHand(ICard card) {
        if (hand.contains(card))
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
     * @param card
     * @param index
     */
    public void addCardToSheet(ICard card, int index) {
        if(index > 4 || index < 0) {
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
    }

    /**
     * Get card from sheet at index or null if there is no card
     * @param index
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
     * @param card
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
}