package inf112.skeleton.app.cards;

import inf112.skeleton.app.Board;
import inf112.skeleton.app.object.Robot;

public class MoveCard implements ICard {

    public int moves;
    private int priority;
    private String name;
    
    public MoveCard(int moves, int priority, String name) {
        this.moves = moves;
        this.priority = priority;
        this.name = name;
    }
    
    @Override
    public void doStuff(Robot robot, Board board) {
        board.moveItem(robot, moves);
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
}
