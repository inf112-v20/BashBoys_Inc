package inf112.skeleton.app.object;

import inf112.skeleton.app.Board;
import inf112.skeleton.app.enums.LeftRight;

public class rotateCard implements iCard {

    private String name;
    private int rotates;
    private int priority;
    private LeftRight direction;

    public rotateCard(LeftRight lr, int priority, boolean uTurn, String name) {
        direction = lr;
        if (uTurn)
            this.rotates = 2;
        else
            this.rotates = 1;
        this.priority = priority;
        this.name = name;
    }

    @Override
    public void doStuff(Robot robot, Board board) {
        robot.turn(direction, rotates);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }
    public LeftRight getDirection() {
        return direction;
    }

    @Override
    public String getExtra() {
        if (rotates==1)return direction.toString();
        return "U-Turn";
    }
    
}
