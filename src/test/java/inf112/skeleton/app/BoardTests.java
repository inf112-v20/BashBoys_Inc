package inf112.skeleton.app;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.Robot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTests {

    @Test
    @DisplayName("Move robot on board test")
    public void moveRobotOnBoardTest() {
        Robot robot = new Robot(); // Faces SOUTH, (0,0)
        Board board = new Board(10, 10); // Has robot at (5,5)
        board.addItem(robot, 5, 4); // Robot now at (5,4)
        robot.turn(LeftRight.LEFT, 2); // Turns NORTH
        board.addItem(new Robot(), 5, 5);//Crash To Do
        assertThrows(IllegalArgumentException.class, () -> board.moveItem(robot, 2));// Should crash with pre placed
                                                     
        robot.turn(LeftRight.LEFT, 2); // Turns SOUTH
        robot.move(2);// Moves down
        assertTrue(robot.getX() == 5 && robot.getY() == 2);
    }
    
    @Test
    @DisplayName("Reverse robot on board test")
    public void reverseRobotOnBoardTest() {
        Robot robot = new Robot(); // Faces SOUTH, (0,0)
        Board board = new Board(10, 10); // Has robot at (5,5)
        board.addItem(robot, 5, 4); // Robot now at (5,4)
        board.addItem(new Robot(), 5, 5);//Crash To Do
        assertThrows(IllegalArgumentException.class, () -> board.moveItem(robot, -1));// Should crash with pre placed
        robot.turn(LeftRight.LEFT, 2); // Turns north
        board.moveItem(robot, -2);// Moves down
        assertTrue(robot.getX() == 5 && robot.getY() == 2);
    }

}
