package testcase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import all.UseCase;
import time.Time;

import static junit.framework.TestCase.*;


/**
 * Created by HiekmaHe on 13.11.2016.
 *
 */
public class TestCaseRunnerTest
{
	private UseCase useCase;
	private DataForTestCase data;
	private TestCase testCase;
	private TestCaseRunner testCaseRunner;

	@Before
	public void before() {
		// arrange
		useCase = new UseCase();
		data = new DataForTestCase();
		data.expectedDuration = Time.seconds(60);
		testCase = new TestCase(useCase, data);

		// act
		testCaseRunner = new TestCaseRunner(testCase);

		// assert
		assertNotNull(testCase);
	}

	@After
	public void tearDown() throws Exception
	{
		// arrange

		// act
		useCase = null;
		data = null;
		testCase = null;
		testCaseRunner = null;

		// assert
	}


	@Test
	public void testCreation()
	{
		// arrange
		UseCase useCase = new UseCase();
		DataForTestCase data = new DataForTestCase();
		data.expectedDuration = Time.seconds(60);
		TestCase testCase = new TestCase(useCase, data);

		// act
		TestCaseRunner testCaseRunner = new TestCaseRunner(testCase);

		// assert
		assertNotNull(testCase);
	}

	@Test
	public void runTest() throws Exception
	{
		// arrange

		// act
		boolean isAlive1 = testCaseRunner.isAlive();
		testCaseRunner.start();
		boolean isAlive2 = testCaseRunner.isAlive();
		Thread.sleep(300);
		boolean isAlive3 = testCaseRunner.isAlive();
		Thread.sleep(300);
		boolean isAlive4 = testCaseRunner.isAlive();

		// assert
		assertFalse(isAlive1);
		assertTrue(isAlive2);
		assertTrue(isAlive3);
		assertTrue(isAlive4);
	}

	@Test
	public void runAndStopTestCase() throws Exception {
		// arrange

		// act
		boolean isAlive1 = testCaseRunner.isAlive();
		testCaseRunner.start();
		boolean isAlive2 = testCaseRunner.isAlive();
		Thread.sleep(300);
		testCaseRunner.stop();
		Thread.sleep(1000);
		boolean isAlive3 = testCaseRunner.isAlive();

		// assert
		assertFalse(isAlive1);
		assertTrue(isAlive2);
		assertFalse(isAlive3);
	}

	@Test
	public void measureHowLongToStop() throws Exception {
		// arrange

		// act
		testCaseRunner.start();
		Thread.sleep(200);
		testCaseRunner.stop();
		Time timestampStart = Time.now();
		Time timestampEnd = Time.ZERO;
		while(testCaseRunner.isAlive()) {
			timestampEnd = Time.now();
		}
		Time delta = timestampEnd.difference(timestampStart);

		// assert
		System.out.println("Stopping took " + delta.toMillis() + " ms.");
	}


	@Test
	public void start() throws Exception
	{

	}

	@Test
	public void stop() throws Exception
	{

	}

	@Test
	public void testToString() throws Exception
	{
		// arrange
		String expectedString = "TestCase:\n"
				+ "state: NEW\n"
				+ "ATestCase:\n"
				+ "DataForTestCase\n"
				+ "expectedDuration: 60000000000\n"
				+ "duration: 0\n"
				+ "timestampEnd: 0\n"
				+ "timestampStart: 0\n";

		// act
		String actualString = testCaseRunner.toString();

		// assert
		assertEquals(expectedString, actualString);
	}

	@Test
	public void testExecutor() {
		System.out.println("Implement java.util.concurrent.Executor");

	}
}