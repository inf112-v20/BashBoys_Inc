package inf112.skeleton.app;

import inf112.skeleton.app.cards.MoveCard;
import inf112.skeleton.app.cards.RotateCard;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.Robot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class CardEffectOnRobotOnEmptyBoardTest {

    @Test
    @DisplayName("Using cards to move the robot Test")
    void cardsEffectingRobotTest() {
        //All the required objects
        Board board = new Board(12,12);
        Robot robot = new Robot();
        Player p = new Player("name","0",0,robot,0);
        RotateCard rCard1 = new RotateCard(LeftRight.LEFT,900,false,"Once Left",p);
        RotateCard rCard2 = new RotateCard(LeftRight.RIGHT,900,true,"Once Right",p);
        MoveCard mCard1 = new MoveCard(3,900,"Hmm",p);
        MoveCard mCard2 = new MoveCard(3,900,"Hmm",p);
        
        //Adds robot to board and uses cards on robot
        //Also returns to original place
        board.addItem(robot, 5, 5);
        
        rCard1.doStuff(board);
        assertEquals(robot.getDir(),Direction.EAST);
        
        mCard1.doStuff(board);
        assertEquals(robot.getX(),8);
        
        rCard2.doStuff(board);
        mCard2.doStuff(board);
        assertEquals(robot.getDir(),Direction.WEST);
        assertEquals(robot.getX(),5);
        
    }

}
