package testcase;

import org.junit.Before;
import org.junit.Test;

import all.UseCaseDummy;
import time.Time;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 13.11.2016.
 */
public class TestCaseTest
{

	UseCaseDummy useCase;
	DataForTestCase data;
	TestCase testCase;

	@Before
	public void before() {
		useCase = new UseCaseDummy();
		data = new DataForTestCase();
		testCase = new TestCase(useCase, data);
	}

//	@Test
	public void run() throws Exception
	{
		// arrange

		// act
		testCase.run();
		Thread.sleep(1000);
		testCase.stop();

		// assert
	}

//	@Test
	public void stop() throws Exception
	{
		// arrange

		// act
		boolean isRunning1 = testCase.isRunning();
		testCase.run();
		boolean isRunning2 = testCase.isRunning();
		testCase.stop();
		boolean isRunning3 = testCase.isRunning();

		// assert
		assertEquals(false, isRunning1);
		assertEquals(true, isRunning2);
		assertEquals(false, isRunning3);
	}

	@Test
	public void testToString() throws Exception
	{
		// arrange

		// act
		String toString = testCase.toString();
		String expected = "ATestCase:\n"
				+ "DataForTestCase\n"
				+ "expectedDuration: 0\n"
				+ "duration: 0\n"
				+ "timestampEnd: 0\n"
				+ "timestampStart: 0\n";

		// assert
		assertEquals(expected, toString);
	}

	@Test
	public void getDuration() throws Exception
	{
		// arrange

		// act
		Time duration = testCase.getDuration();

		// assert
		assertEquals(Time.ZERO, duration);
	}

	@Test
	public void isRunning_directAccess() throws Exception {
		// arrange

		// act
		boolean isRunning1 = testCase.isRunning();
		testCase.isRunning(true);
		boolean isRunning2 = testCase.isRunning();
		testCase.isRunning(false);
		boolean isRunning3 = testCase.isRunning();

		// assert
		assertFalse(isRunning1);
		assertTrue(isRunning2);
		assertFalse(isRunning3);
	}

	@Test
	public void isRunning_accessByRunning() throws Exception {
		// arrange

		// act
		boolean isRunning1 = testCase.isRunning();

		Thread thread = new Thread(
			new Runnable() {
				@Override
				public void run()
				{
					testCase.run();
				}
			}
		);
		thread.start();
		Thread.sleep(500);
		boolean isRunning2 = testCase.isRunning();
		testCase.isRunning(false);
		boolean isRunning3 = testCase.isRunning();
		testCase.isRunning(false);

		// assert
		assertFalse(isRunning1);
		assertTrue(isRunning2);
		assertFalse(isRunning3);
	}
}