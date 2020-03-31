package inf112.skeleton.app;

import inf112.skeleton.app.cards.ICard;
import inf112.skeleton.app.interfaces.ICheckPoint;
import inf112.skeleton.app.interfaces.IPlayer;
import inf112.skeleton.app.object.Robot;

import java.util.ArrayList;

public class Player implements IPlayer {
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

    @Override
    public void giveCard(ICard card) {
        if (hand.size() == handSize)
            return;
        hand.add(card);
    }

    @Override
    public void giveCard(ArrayList<ICard> cards) {
        if (hand.size() >= handSize || cards.size() > handSize)
            return;
        hand.addAll(cards);
    }

    @Override
    public void removeCardFromHand(ICard card) {
        hand.remove(card);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArrayList<ICard> getHand(){
        return hand;
    }

    @Override
    public ArrayList<ICard> getProgramSheet(){
        return programSheet;
    }

    @Override
    public void addCardToSheet(ICard card) {
        programSheet.add(card);
    }

    @Override
    public ICard getCardFromSheet(int index) {
        try {
            programSheet.get(index);
        } catch ( IndexOutOfBoundsException e ) {
            return null;
        }
        return programSheet.get(index);
    }

    @Override
    public void clearSheet() {
        programSheet.clear();
    }

    @Override
    public Robot getRobot() {
        return robot;
    }

    @Override
    public void setReady(boolean r) {
        ready = r;
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public ICheckPoint getSpawn() {
        return spawn;
    }

    @Override
    public void setSpawn(ICheckPoint nSpawn) {
        spawn = nSpawn;
    }
}