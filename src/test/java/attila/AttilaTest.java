package _development;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import _framework.TestHelper;
import attila.AttilaMockWrapper;
import attila.AttilaSendingCreate;
import attila.AttilaUseCase;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import send.Sendable;
import send.Sending;
import send.SendingCreate;
import testcase.DataForTestCase;
import testcase.TestCase;
import testcase.TestCaseExecutor;
import testcase.TestCaseRunnable;
import time.Time;
import useCase.UseCaseable;

import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 16.11.2016.
 *
 * SRP:
 */
public class AttilaTest
{
	private AttilaMockWrapper mock = new AttilaMockWrapper();
	@Rule	public WireMockClassRule rule = mock.getWireMockClassRule();
	private TestCaseRunnable runner;

	@Before
	public void before() throws IOException
	{
		givenThat(mock.receivesPostRequestWithContent_ThenReturn201());
		givenThat(mock.receivesPostRequestNoContent_ThenReturn406());
		givenThat(mock.receivesGetRequest_ThenReturn406());

		runner = TestHelper.createAttilaRunner();
	}

	@Test
	public void runFor3Seconds() throws Exception
	{
		// arrange

		// act
		runner.startRun();
		Time.seconds(3).sleep();
		runner.stopRun();

		// assert
		List<ServeEvent> allServeEvents = rule.getAllServeEvents();
		for (ServeEvent aEvent : allServeEvents) {
			assertEquals(HttpURLConnection.HTTP_ACCEPTED, aEvent.getResponse().getStatus());
		}
	}

	@Test
	public void stopAfter2Seconds() throws Exception
	{
		// arrange
		Time aShort = Time.seconds(2);
		Time range = Time.millis(200);

		// act
		runner.startRun();
		aShort.sleep();
		runner.stopRun();

		// assert
		assertTrue(runner.getResult().actualDuration.inRange(aShort, range));
	}

	@Test
	public void runForExpectedDurationStopOneself() throws Exception
	{
		// arrange
		DataForTestCase data = new DataForTestCase();
		data.expectedDuration = Time.seconds(2);
		SendingCreate attilaConnectionCreate = AttilaSendingCreate.createInstance(mock.createUrlExitOnException());
		Sendable attilaSender = new Sending(attilaConnectionCreate);
		UseCaseable useCase = new AttilaUseCase(TestHelper.getMessages(), attilaSender);
		TestCase testCase = new TestCase(useCase, data);
		TestCaseRunnable runner = new TestCaseExecutor(testCase);

		// act
		boolean isNotRunningYet = ! runner.isRunning();
		runner.startRun();
		Time.millis(1000).sleep();
		boolean isRunning = runner.isRunning();
		Time.seconds(3).sleep();
		boolean stoppedRunning = ! runner.isRunning();

		// assert
		assertTrue(isNotRunningYet);
		assertTrue(isRunning);
		assertTrue(stoppedRunning);
	}
}