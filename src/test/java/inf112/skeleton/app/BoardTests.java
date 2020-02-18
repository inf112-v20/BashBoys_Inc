package inf112.skeleton.app;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.Robot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class BoardTests {

    @Test
    @DisplayName("Board move test")
    public void moveRobotOnBoardTest() {
        Robot robot = new Robot(); // Faces SOUTH, (0,0)
        robot.dir = Direction.SOUTH;
        Board board = new Board(10, 10); // Has robot at (5,5)
        board.addItem(robot, 5, 4); // Robot now at (5,4)
        robot.turn(LeftRight.LEFT, 2); // Turns NORTH
        //assertThrows(IllegalArgumentException.class, () -> board.moveItem(robot, 2));// Should crash with pre placed
        // robot
        robot.turn(LeftRight.LEFT, 2); // Turns SOUTH
        robot.move(2);// Moves down
        assertTrue(robot.getX() == 5 && robot.getY() == 2);
    }

    @Test
    @DisplayName("Board placement Test")
    public void placeRobotOnBoardTest() {
        Board board = new Board(10, 10); // 10x10 board with robot at (5,5)
        Robot robot = new Robot(); // SOUTH (0,0) (Not on board)
        board.addItem(robot, 5, 5); // Overwrites old robot
        assertEquals(board.getItem(5, 5), robot); // Checks if right robot
        assertEquals(board.getObjects().size(), 1); // Only that robot on board
    }

}
