package inf112.skeleton.app.cards;

import inf112.skeleton.app.Board;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.Robot;

public class RotateCard implements ICard {

    private String name;
    private int rotates;
    private int priority;
    private LeftRight direction;

    public RotateCard(LeftRight lr, int priority, boolean uTurn, String name) {
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

    @Override
    public String getName() {
        return name;
    }

    /**
     * Get direction of rotation
     * @return get direction of rotation
     */
    public LeftRight getDirection() {
        return direction;
    }

    @Override
    public String getExtra() {
        if (rotates==1)return direction.toString();
        return "U-Turn";
    }
    
}
