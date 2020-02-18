package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.Collections;

import inf112.skeleton.app.object.iCard;

public class Player {
    private String name;
    private ArrayList<iCard> hand = new ArrayList<>();
    private int handSize = 9;
    private ArrayList<iCard> programSheet = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void giveCard(iCard card) {
        if (hand.size() == handSize)
            return;
        hand.add(card);
    }

    public void removeCardFromHand(iCard card) {
        if (hand.contains(card))
            hand.remove(card);
    }
    
    public String getName() {
        return name;
    }
    
    public ArrayList<iCard> getHand(){
        return hand;
    }

    public ArrayList<iCard> getProgramSheet(){
        return programSheet;
    }

    public void addCardToSheet(iCard card, int index) {
        if(index > 4 || index < 0) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        if(programSheet.contains(card)) {
            if(getCardFromSheet(index) instanceof iCard) {
                Collections.swap(programSheet, index, programSheet.indexOf(card));
            }
            else {
                programSheet.remove(card);
                addCardToSheet(card, index);
            }
        }
        else if(getCardFromSheet(index) instanceof iCard) {
            programSheet.add(index, card);
            giveCard(programSheet.get(index+1));
            programSheet.remove(index+1);
        }
        else{
            programSheet.add(card);
        }
        removeCardFromHand(card);
    }

    public iCard getCardFromSheet(int index) {
        try {
            programSheet.get(index);
        } catch ( IndexOutOfBoundsException e ) {
            return null;
        }
        return programSheet.get(index);
    }

    public void moveCardFromSheetToHand(iCard card) {
        if (programSheet.contains(card)) {
            hand.add(card);
            programSheet.remove(card);
        }
    }

    public void clearSheet() {
        programSheet.clear();
    }
}
