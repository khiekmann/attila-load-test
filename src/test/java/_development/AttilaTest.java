package _development;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import _framework.Context;
import _framework.WireMockAttila;
import attila.AttilaSendingCreate;
import attila.AttilaUseCase;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import send.Sendable;
import send.Sending;
import send.SendingCreate;
import testcase.DataForTestCase;
import testcase.TestCase;
import testcase.TestCaseExecutor;
import testcase.TestCaseRunnable;
import time.Time;
import useCase.UseCaseable;

import static junit.framework.TestCase.assertTrue;


/**
 * Created by HiekmaHe on 16.11.2016.
 */
public class AttilaTest
{
	private Context context;
	private WireMockAttila mock;
	@Rule	public WireMockRule rule;
	private TestCaseRunnable runner;

	@Before
	public void before() throws IOException
	{
		context = new Context();
		mock = new WireMockAttila();
		rule = mock.getRule();
		rule.givenThat(mock.whenPostRequestReceivedThenReturn202());
		runner = context.getAttilaRunner();
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
		SendingCreate attilaConnectionCreate = AttilaSendingCreate.createInstance(mock.getURL());
		Sendable attilaSender = new Sending(attilaConnectionCreate);
		UseCaseable useCase = new AttilaUseCase(context.getMessages(), attilaSender);
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