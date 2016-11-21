package testcase;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import _framework.TestWireMockClassRule;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import time.Time;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 13.11.2016.
 */
public class TestCaseTest
{
	@Rule
	public WireMockClassRule instanceRule = TestWireMockClassRule.createInstance();
	private TestCase testCase;

	@Before
	public void before() throws IOException
	{
		instanceRule.stubFor(TestWireMockClassRule.stubFor());
		testCase = TestCaseTestHelper.createTestCase(TestWireMockClassRule.createURL());
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
	public void testGetResult_WithoutRunning() {
		// arrange

		// act
		DataForTestCase result = testCase.getResult();
		String resultToString = "DataForTestCase\n"
				+ "expectedDuration: 0\n"
				+ "duration: 0\n"
				+ "timestampEnd: 0\n"
				+ "timestampStart: 0\n";

		// assert
		assertEquals(resultToString, result.toString());
	}

	@Test
	public void getDuration() throws Exception
	{
		// arrange

		// act

		// assert
		assertEquals(Time.ZERO, testCase.getDuration());
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
		Thread thread = new Thread(testCase);

		// act
		boolean isRunning1 = testCase.isRunning();
		boolean isAlive1 = thread.isAlive();

		thread.start();
		Time.millis(300).sleep();

		boolean isRunning2 = testCase.isRunning();
		boolean isAlive2 = thread.isAlive();

		testCase.isRunning(false);
		Time.millis(500).sleep();

		boolean isRunning3 = testCase.isRunning();
		boolean isAlive3 = thread.isAlive();

		// assert
		assertFalse(isRunning1);
		assertFalse(isAlive1);
		assertTrue(isRunning2);
		assertTrue(isAlive2);
		assertFalse(isRunning3);
		assertFalse(isAlive3);
	}
}