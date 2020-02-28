package inf112.skeleton.app;

import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.MoveCard;
import inf112.skeleton.app.object.RotateCard;
import inf112.skeleton.app.object.ICard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Deck {
    private LinkedList<ICard> cards = new LinkedList<>();
    public int DECK_SIZE = 84;

    public Deck(){
        initializeDeck();
        shuffleDeck();
    }

    /**
     * Initializes a normal deck of robo-rally cards
     */
    private void initializeDeck(){
        for(int i = 1; i <= DECK_SIZE; i++){
            if(i <= 6){
                cards.push(new RotateCard(LeftRight.LEFT,i*10,true,"U-turn"));
            } else if (i <= 42){
                if(i % 2 == 0) { // Right Rotation
                    cards.push(new RotateCard(LeftRight.RIGHT,i*10,false,"Rotate Right"));
                } else {        // Left Rotation
                    cards.push(new RotateCard(LeftRight.LEFT,i*10,false,"Rotate Left"));
                }
            } else if (i <= 48) {
                cards.push(new MoveCard(-1,i*10,"Back Up"));
            } else if (i <= 66) {
                cards.push(new MoveCard(1,i*10,"Move 1"));
            } else if (i <= 78) {
                cards.push(new MoveCard(2,i*10,"Move 2"));
            } else {
                cards.push(new MoveCard(3,i*10,"Move 3"));
            }
        }
        // Priority: 10 -> 60 | 6: U turn cards
        // Priority: 70 -> 420 | 18: Left turn | 18: Right turn
        // Priority: 430 -> 480 | 6: Back up cards
        // Priority: 490 -> 660 | 18: Move 1
        // Priority: 670 -> 780 | 12: Move 2
        // Priority: 790 -> 840 | 6: Move 3
        // TOTAL NORMAL CARDS = 84
        /* Option cards: 26 cards
           Ablative Coat, Abort Switch, Brakes, Circuit Breaker, Conditional Program, Crab Legs
           Double-Barreled Laser, Dual Processor, Extra Memory, Fire Control, Flywheel, Fourth Gear
           Gyroscopic Stabilizer, High-Powered Laser, Mechanical Arm, Mini Howitzer, Power-Down Shield
           Pressor Beam, Radio Control, Ramming Gear, Rear-Firing Laser, Recompile, Reverse Gear
           Scrambler, Superior Archive, Tractor Beam
           TOTAL CARDS = 110
         */
    }

    /**
     * Shuffles the current deck
     */
    public void shuffleDeck(){
        Collections.shuffle(cards);
    }

    /**
     * Resets the current deck into re-shuffled re-created deck
     */
    public void resetDeck(){
        initializeDeck();
        shuffleDeck();
    }

    /**
     * Return the first "amount" cards in deck and remove it from deck
     * @param amount
     * @return ArrayList of all drawn cards
     */
    public ArrayList<ICard> getCards(int amount){
        if(amount > cards.size()){
            amount = cards.size();
        }

        ArrayList<ICard> temp = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            temp.add(cards.poll());
        }
        return temp;
    }

    /**
     * Look at all cards currently in the deck, but don't delete them
     * @return LinkedList of all cards
     */
    public LinkedList<ICard> peekAllCards(){
        return cards;
    }

    /**
     * Add a single card at the bottom of the deck
     * @param card
     */
    public void addCard(ICard card){
        if(getCurrentDeckSize() < DECK_SIZE){
            cards.push(card);
        } else {
            throw new IllegalArgumentException("Can't add more cards to deck");
        }

    }

    /**
     * Add a list of cards at the bottom of the deck
     * @param newCards
     */
    public void addListOfCards(ArrayList<ICard> newCards){
        for(ICard card : newCards){
            addCard(card);
        }
    }

    /**
     * Return the current deck size
     * @return int
     */
    public int getCurrentDeckSize(){
        return cards.size();
    }

}
