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
    private int lifes = 3;

    /**
     * Create a new player
     * 
     * @param name - name of player
     */
    public Player(String name, String ip, int port, Robot r, int id) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.robot = r;
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
     * 
     * @param card - card to add to hand
     */
    public void giveCard(ICard card){
        if (hand.size() == handSize)
            return;
        card.setPlayer(this);
        hand.add(card);
    }

    /**
     * Gives multiple cards
     */
    public void giveCard(ArrayList<ICard> cards){
        if (hand.size() + cards.size() > handSize)
            return;
        hand.addAll(cards);
        for (ICard card : cards)
            card.setPlayer(this);
    }

    /**
     * Remove card from hand
     * 
     * @param card - card to remove from hand
     */
    public void removeCardFromHand(ICard card){
        hand.remove(card);
    }

    /**
     * Returns name of the player
     * 
     * @return Player name
     */
    public String getName(){
        return name;
    }

    /**
     * Returns list with all cards in hand
     * 
     * @return ArrayList with all cards in hand
     */
    public ArrayList<ICard> getHand(){
        return hand;
    }

    /**
     * Returns list with all cards in program sheet
     * 
     * @return ArrayList with all cards in program sheet
     */
    public ArrayList<ICard> getProgramSheet(){
        return programSheet;
    }

    /**
     * Add a card to sheet
     * 
     * @param card - card to add
     */
    public void addCardToSheet(ICard card){
        if (programSheet.size() < 6)
            programSheet.add(card);
    }

    /**
     * Get card from sheet at index or null if there is no card
     * 
     * @param index - index of card
     * @return Card from sheet at index
     */
    public ICard getCardFromSheet(int index){
        try {
            programSheet.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return programSheet.get(index);
    }

    /**
     * Clear the sheet
     */
    public void clearSheet(){
        programSheet.clear();
    }

    /**
     * Clear the hand
     */
    public void clearHand(){
        hand.clear();
    }

    /**
     * Get robot assigned to player
     * 
     * @return robot
     */
    public Robot getRobot(){
        return robot;
    }

    /**
     * Sets ready to false or true
     * 
     * @param r
     */
    public void setReady(boolean r){
        ready = r;
    }

    /**
     * Returns if the player is ready or not
     * 
     * @return ready
     */
    public boolean isReady(){
        return ready;
    }

    /**
     * gets spawnpoint
     * 
     * @return current spawnpoint
     */
    public ICheckPoint getSpawn(){
        return spawn;
    }

    /**
     * sets new spawn point for player
     * 
     * @param nSpawn - new valid spawn point
     */
    public void setSpawn(ICheckPoint nSpawn){
        spawn = nSpawn;
        if (nSpawn instanceof Flag) {
            addFlag(((Flag) nSpawn));
        }
    }

    /**
     * returns ip of player
     * 
     * @return ip in string form
     */
    public String getIP(){
        return ip;
    }

    /**
     * returns the port of the player
     * 
     * @return the port of player in int
     */
    public int getPort(){
        return port;
    }

    /**
     * gets ID of player
     * 
     * @return ID in int form
     */
    public int getID(){
        return id;
    }

    /**
     * player in string form
     */
    public String toString(){
        return name + ", player id: " + id + ", Robot:" + robot.getName();
    }

    /**
     * attempt too add flag to player
     * 
     * @param f - flag to attempt to add
     */
    public void addFlag(Flag f){
        if (flags.contains(f)) {
            return;
        } else if (flags.isEmpty() && f.getNr() == 1) {
            flags.add(f);
        } else if (!flags.isEmpty() && flags.get(flags.size() - 1).getNr() == f.getNr() - 1) {
            flags.add(f);
        }
    }

    /**
     * gets all flags visited
     * 
     * @return ArrayList of falgs visited
     */
    public ArrayList<Flag> getFlags(){
        return flags;
    }

    /**
     * sets shutdown for rounds
     * 
     * @param shutDown - rounds to shut down
     */
    public void setShutDow(int shutDown){
        this.shutDown = shutDown;
    }

    /**
     * gets rounds left of shutdown
     * 
     * @return int with rounds left of shutdown
     */
    public int getShutdown(){
        return shutDown;
    }

    /**
     * Get lives of player
     * @return lives
     */
    public int getLifes(){
        return lifes;
    }

    /**
     * Gives i amount of lives to player
     * @param i - amount of lives
     * @return new amount of lives
     */
    public int giveLife(int i){
        return lifes = Math.min(++lifes, 3);
    }

    public int giveLife(){
        return giveLife(1);
    }

    /**
     * Takes i amount of lives
     * @param i - amount of lives to take
     * @return - new amount of lives
     */
    public int takeLife(int i){
        return lifes = Math.max(--lifes, 0);
    }

    public int takeLife(){
        return takeLife(1);
    }
}