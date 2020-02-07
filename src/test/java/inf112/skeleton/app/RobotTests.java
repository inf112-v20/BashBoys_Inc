package inf112.skeleton.app;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.Robot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

class RobotTests {

    @Test
    @DisplayName("Direction test")
    public void dirTest() {
        Robot robot = new Robot(); // SOUTH (0,0)
        assertEquals(robot.getDir(), Direction.SOUTH);
        robot.turn(LeftRight.LEFT); // EAST
        assertFalse(robot.getDir() == Direction.NORTH);
        assertEquals(robot.getDir(), Direction.EAST);
    }

    @Test
    @DisplayName("Robot move test")
    public void robotMoveTest() {
        Robot robot = new Robot();// Faces SOUTH, (0,0)
        robot.setX(0);
        robot.setY(0);
        assertEquals(robot.getX(), 0);
        robot.turn(LeftRight.LEFT); // Now EAST
        robot.move(3); // Now (3,0)
        assertFalse(robot.getY() == 3); // Not (0,3)
        assertEquals(robot.getX(), 3);
    }
}
