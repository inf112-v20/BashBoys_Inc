package inf112.skeleton.app.object;

import inf112.skeleton.app.Board;

public class moveCard implements iCard {

    int moves;
    int priority;
    String name;
    
    public moveCard(int moves, int priority,String name) {
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
