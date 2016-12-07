package attila;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import _framework.TestHelper;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import send.Sendable;
import send.Sending;
import send.SendingCreate;
import testCase.DataForTestCase;
import testCase.TestCase;
import testCase.TestCaseExecutor;
import testCase.TestCaseRunnable;
import time.Time;
import useCase.UseCaseable;

import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static junit.framework.TestCase.assertTrue;


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