package inf112.skeleton.app;

import inf112.skeleton.app.interfaces.ICard;
import inf112.skeleton.app.interfaces.ICheckPoint;
import inf112.skeleton.app.interfaces.IPlayer;
import inf112.skeleton.app.object.Flag;
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
    private String ip;
    private int port;
    private int id;
    private ArrayList<Flag> flags = new ArrayList<>();
    private int shutDown;

    /**
     * Create a new player
     * @param name - name of player
     */
    public Player(String name, String ip, int port,Robot r,int id) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.robot=r;
        this.id = id;
        r.setPlayer(this);
    }
    
    public Player(String name) {
        this.name = name;
    }
    
    public Player(String name, Robot r) {
        this.name = name;
        robot = r;
        r.setPlayer(this);
    }

    /**
     * Add card to hand
     * @param card - card to add to hand
     */
    public void giveCard(ICard card) {
        if (hand.size() == handSize)
            return;
        card.setPlayer(this);
        hand.add(card);
    }
    
    public void giveCard(ArrayList<ICard> cards) {
        if (hand.size()+cards.size() > handSize)
            return;
        hand.addAll(cards);
        for(ICard card : cards)card.setPlayer(this);
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
     * Clear the hand
     */
    public void clearHand() {
        hand.clear();
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
        if(nSpawn instanceof Flag) {
            addFlag(((Flag)nSpawn));
        }
    }
    
    public String getIP() {
        return ip;
    }
    
    public int getPort() {
        return port;
    }
    
    public int getID() {
        return id;
    }
    
    public String toString() {
        return name+", player id: " +id+", Robot:"+robot.getName();
    }
    public void addFlag(Flag f) {
        if(flags.contains(f)) {
            return;
        }
        else if(flags.isEmpty() || f.getNr() == 1) {
            flags.add(f);
        }else if(flags.get(flags.size()-1).getNr() == f.getNr()-1) {
            flags.add(f);
        }
    }
    public ArrayList<Flag> getFlags(){
        return flags;
    }
    public void setShutDow(int shutDown) {
        this.shutDown = shutDown;
    }
    public int getShutdown() {
        return shutDown;
    }
}