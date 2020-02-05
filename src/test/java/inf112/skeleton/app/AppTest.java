package inf112.skeleton.app;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

    // Once at start
    @BeforeAll
    public static void first() {
        // System.out.println("A");// Placeholder
    }

    // Before each test
    @BeforeEach
    public void init(TestInfo testInfo) {
        System.out.println("Start..." + testInfo.getDisplayName());
    }

    // After tests are done
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
        assertTrue(true); // True
    }

    @Test
    @DisplayName("AssertFalse(false)")
    public void shouldAnswerWithFalse() {
        assertFalse(false); // False
    }

}
