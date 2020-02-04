package inf112.skeleton.app;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@BeforeAll
	public static void first() {
		System.out.println("A");
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

}
