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
import inf112.skeleton.app.enums.LeftRight;
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
        robot.turn(LeftRight.LEFT);
        assertFalse(robot.getDir() == Direction.NORTH);
        assertEquals(robot.getDir(), Direction.WEST);
    }
    
    @Test
    @DisplayName("Move test")
    public void moveTest() {
        TestRobot robot = new TestRobot();
        assertEquals(robot.getX(), 0);
        robot.turn(LeftRight.RIGHT);
        robot.move(3);
        assertFalse(robot.getX()==3);
        assertEquals(robot.getY(), 3);
    }
    
    @Test
    @DisplayName("Board Test")
    public void boardTest() {
        Board board = new Board(10,10);
        TestRobot robot = new TestRobot();
        board.addItem(robot, 5, 5);
        assertEquals(board.getItem(5, 5),robot);
        assertEquals(board.getObjects().size(),1);
        assertFalse(board.getObjects().size() == 2);
    }

}
