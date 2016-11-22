package attila;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import _framework.Context;
import _framework.WireMockAttila;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import testcase.TestCaseRunnable;
import time.Time;

import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 19.11.2016.
 *
 * SRP:
 */
public class AttilaTest
{
	private Context context;
	private WireMockAttila mock;
	@Rule
	public WireMockRule rule;
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
	public void doAttila() throws Exception
	{
		// arrange

		// act
		runner.startRun();
		Time.seconds(3).sleep();
		runner.stopRun();

		// assert
		List<ServeEvent> allServeEvents = rule.getAllServeEvents();
		for (ServeEvent aEvent : allServeEvents) {
			assertEquals(202, aEvent.getResponse().getStatus());
		}
	}
}