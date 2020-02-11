package inf112.skeleton.app;

import java.util.ArrayList;

import inf112.skeleton.app.object.iCard;

public class Player {
    private String name;
    private ArrayList<iCard> hand = new ArrayList<>();
    private int handSize = 9;

    public Player(String name) {
        this.name = name;
    }

    public void giveCard(iCard card) {
        if (hand.size() == handSize)
            return;
        hand.add(card);
    }

    public void removeCard(iCard card) {
        if (hand.contains(card))
            hand.remove(card);
    }
    
    public String getName() {
        return name;
    }
    
    public ArrayList<iCard> getHand(){
        return hand;
    }

}
