package inf112.skeleton.app;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.object.TestRobot;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @BeforeAll
    public static void first() {
        System.out.println("A");// Placeholder
    }

    @BeforeEach
    public void init(TestInfo testInfo) {
        System.out.println("Start..." + testInfo.getDisplayName());
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        System.out.println("Finished..." + testInfo.getDisplayName());
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    @DisplayName("AssertTrue(true)")
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    @DisplayName("AssertFalse(false)")
    public void shouldAnswerWithFalse() {
        assertFalse(false);
    }

    @Test
    @DisplayName("Direction test")
    public void dirTest() {
        TestRobot robot = new TestRobot();
        assertEquals(robot.getDir(), Direction.NORTH);
        robot.turn(Direction.SOUTH);
        assertFalse(robot.getDir() == Direction.NORTH);
        assertEquals(robot.getDir(), Direction.SOUTH);
    }
    
    @Test
    @DisplayName("Move test")
    public void moveTest() {
        TestRobot robot = new TestRobot();
        assertEquals(robot.getX(), 0);
        robot.turn(Direction.SOUTH);
        assertFalse(robot.getDir() == Direction.NORTH);
        assertEquals(robot.getDir(), Direction.SOUTH);
    }

}
