package testcase;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import _framework.TestHelper;
import attila.AttilaMockWrapper;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import time.Time;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 13.11.2016.
 *
 * SRP:
 */
public class TestCaseTest
{
	public AttilaMockWrapper mock = new AttilaMockWrapper();
	@Rule
	public WireMockClassRule rule = mock.getWireMockClassRule();
	private TestCase testCase;
	private String expectedToStringOfTestCase = "DataForTestCase\n"
			+ "expectedDuration: 5000000000\n"
			+ "duration: 0\n"
			+ "timestampEnd: 0\n"
			+ "timestampStart: 0\n";


	@Before
	public void before() throws IOException
	{
		rule.givenThat(mock.receivesAnyRequestThenReturn200TextplainContent());
		testCase = TestHelper.createAttilaTestCase();
	}

	@Test
	public void testToString_WithoutRunning() throws Exception
	{
		// assert
		assertEquals(expectedToStringOfTestCase, testCase.toString());
	}

	@Test
	public void testGetResult_WithoutRunning() {
		// assert
		assertEquals(expectedToStringOfTestCase, testCase.getResult().toString());
	}

	@Test
	public void getDuration() throws Exception
	{
		// assert
		assertEquals(Time.ZERO, testCase.getDuration());
	}

	@Test
	public void isRunning_directAccess() throws Exception {
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

		testCase.timestampStartNow();
		thread.start();
		Time.millis(500).sleep();

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