package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.Collections;

import inf112.skeleton.app.object.ICard;

public class Player {
    private String name;
    private ArrayList<ICard> hand = new ArrayList<>();
    private int handSize = 9;
    private ArrayList<ICard> programSheet = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void giveCard(ICard card) {
        if (hand.size() == handSize)
            return;
        hand.add(card);
    }

    public void removeCardFromHand(ICard card) {
        if (hand.contains(card))
            hand.remove(card);
    }
    
    public String getName() {
        return name;
    }
    
    public ArrayList<ICard> getHand(){
        return hand;
    }

    public ArrayList<ICard> getProgramSheet(){
        return programSheet;
    }

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

    public ICard getCardFromSheet(int index) {
        try {
            programSheet.get(index);
        } catch ( IndexOutOfBoundsException e ) {
            return null;
        }
        return programSheet.get(index);
    }

    public void moveCardFromSheetToHand(ICard card) {
        if (programSheet.contains(card)) {
            hand.add(card);
            programSheet.remove(card);
        }
    }

    public void clearSheet() {
        programSheet.clear();
    }
}
