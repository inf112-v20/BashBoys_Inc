package inf112.skeleton.app.cards;

import inf112.skeleton.app.Board;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.interfaces.ICard;

public class MoveCard implements ICard {

    private int moves;
    private int priority;
    private String name;
    private Player p;
    
    public MoveCard(int moves, int priority, String name, Player p) {
        this.moves = moves;
        this.priority = priority;
        this.name = name;
        this.p = p;
    }
    
    @Override
    public void doStuff(Board board) {
        board.moveItem(p.getRobot(), moves);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getExtra() {
        return ""+moves;
    }
    
    public Player getPlayer() {
        return p;
    }
    public void setPlayer(Player p) {
        this.p = p;
    }
}
