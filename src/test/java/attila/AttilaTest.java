package attila;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import _framework.TestHelper;
import _thirdparty.wiremock.MockWrapper;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import testcase.TestCaseRunnable;
import time.Time;

import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 19.11.2016.
 *
 * SRP:
 */
public class AttilaTest
{
	private MockWrapper mock = new AttilaMockWrapper();
	@Rule public WireMockClassRule rule = mock.getWireMockClassRule();
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
		int i = 0;
		for (ServeEvent aEvent : allServeEvents) {
			System.out.println(i++);
			assertEquals(HttpURLConnection.HTTP_ACCEPTED, aEvent.getResponse().getStatus());
		}
	}
}