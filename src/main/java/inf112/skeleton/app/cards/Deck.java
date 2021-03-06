package inf112.skeleton.app.cards;

import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.interfaces.ICard;

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
                cards.push(new RotateCard(LeftRight.LEFT,i*10,true,"U-turn",null));
            } else if (i <= 42){
                if(i % 2 == 0) { // Right Rotation
                    cards.push(new RotateCard(LeftRight.RIGHT,i*10,false,"Rotate Right",null));
                } else {        // Left Rotation
                    cards.push(new RotateCard(LeftRight.LEFT,i*10,false,"Rotate Left",null));
                }
            } else if (i <= 48) {
                cards.push(new MoveCard(-1,i*10,"Back Up",null));
            } else if (i <= 66) {
                cards.push(new MoveCard(1,i*10,"Move 1",null));
            } else if (i <= 78) {
                cards.push(new MoveCard(2,i*10,"Move 2",null));
            } else {
                cards.push(new MoveCard(3,i*10,"Move 3",null));
            }
        }
    }

    /**
     * Shuffles the current deck
     */
    public void shuffleDeck(){
        Collections.shuffle(cards);
    }


    /**
     * Return the first "amount" cards in deck and remove it from deck
     * @param amount - amount of cards to pull
     * @return ArrayList of all drawn cards
     */
    public ArrayList<ICard> getCards(int amount){
        if(amount > cards.size()){
            refill();
        }

        ArrayList<ICard> temp = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            temp.add(cards.poll());
            if(cards.size()==0)refill();
        }
        return temp;
    }

    private void refill(){
        cards = new LinkedList<>();
        initializeDeck();
        shuffleDeck();
    }

    public ICard getCard(){
        ICard c = cards.poll();
        if(cards.size()==0)refill();
        return c;
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
     * @param card - card to add
     */
    public void addCard(ICard card){
        if(getCurrentDeckSize() < DECK_SIZE){
            cards.push(card);
        } else {
            throw new IllegalArgumentException("Can't add more cards to deck");
        }

    }


    /**
     * Return the current deck size
     * @return current deck size
     */
    public int getCurrentDeckSize(){
        return cards.size();
    }

}
