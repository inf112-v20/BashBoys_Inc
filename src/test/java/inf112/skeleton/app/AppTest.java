package inf112.skeleton.app;

import org.junit.jupiter.api.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
