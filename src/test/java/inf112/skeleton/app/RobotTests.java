package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.TestRobot;

class RobotTests {

    @Test
    @DisplayName("Direction test")
    public void dirTest() {
        TestRobot robot = new TestRobot(); // SOUTH (0,0)
        assertEquals(robot.getDir(), Direction.SOUTH);
        robot.turn(LeftRight.LEFT); // EAST
        assertFalse(robot.getDir() == Direction.NORTH);
        assertEquals(robot.getDir(), Direction.EAST);
    }

    @Test
    @DisplayName("Robot move test")
    public void robotMoveTest() {
        TestRobot robot = new TestRobot();// Faces SOUTH, (0,0)
        assertEquals(robot.getX(), 0);
        robot.turn(LeftRight.LEFT); // Now EAST
        robot.move(3); // Now (3,0)
        assertFalse(robot.getY() == 3); // Not (0,3)
        assertEquals(robot.getX(), 3);
    }
}
