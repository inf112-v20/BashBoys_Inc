package inf112.skeleton.app.cards;

import inf112.skeleton.app.Board;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.interfaces.ICard;

public class Nothing implements ICard {

    private Player p;
    
    public Nothing(Player p) {
        setPlayer(p);
    }
    @Override
    public void doStuff(Board board){
        // Nothing
    }

    @Override
    public int getPriority(){
        return 0;
    }

    @Override
    public String getName(){
        return "Nothing";
    }

    @Override
    public String getExtra(){
        return null;
    }

    @Override
    public Player getPlayer(){
        return p;
    }

    @Override
    public void setPlayer(Player p){
        this.p = p;
    }

}
