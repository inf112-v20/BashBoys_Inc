package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.MoveCard;
import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.RotateCard;

class CardEffectOnRobotOnEmptyBoardTest {

    @Test
    @DisplayName("Using cards to move the robot Test")
    void CardsEffectingRobotTest() {
        //All the required objects
        Board board = new Board(12,12);
        Robot robot = new Robot();
        RotateCard rCard1 = new RotateCard(LeftRight.LEFT,900,false,"Once Left");
        RotateCard rCard2 = new RotateCard(LeftRight.RIGHT,900,true,"Once Right");
        MoveCard mCard1 = new MoveCard(3,900,"Hmm");
        MoveCard mCard2 = new MoveCard(3,900,"Hmm");
        
        //Adds robot to board and uses cards on robot
        //Also returns to original place
        board.addItem(robot, 5, 5);
        
        rCard1.doStuff(robot, board);
        assertEquals(robot.getDir(),Direction.EAST);
        
        mCard1.doStuff(robot, board);
        assertEquals(robot.getX(),8);
        
        rCard2.doStuff(robot, board);
        mCard2.doStuff(robot, board);
        assertEquals(robot.getDir(),Direction.WEST);
        assertEquals(robot.getX(),5);
        
    }

}
