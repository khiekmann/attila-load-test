package _thirdparty.travis;

import java.net.HttpURLConnection;
import java.util.List;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import attila.AttilaMockWrapper;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import testCase.TestCaseRunnable;
import time.Time;

import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 20.11.2016.
 *
 * SRP: Travis will make these fail.
 */
public class FailsInTravis
{
	@Ignore
	@Test
	public void testFormat_yearBlaBlaSeconds() {
		// arrange
		Time timeObject = Time.seconds(5);

		// act
		String yearBlaBlaSeconds = timeObject.format("yyyy-MM-dd-HH-mm-ss");

		// assert
		System.err.println("1970-01-01-01-00-05" + " - " + yearBlaBlaSeconds);
		assertEquals("1970-01-01-01-00-05", yearBlaBlaSeconds);
	}

	@Ignore
	@Test
	public void testFormat_dateAndNanos() {
		// arrange
		Time timeObject = Time.seconds(5);

		// act
		String dateAndNanos = timeObject.format("dd MMM yyyy HH:mm:ss,SSS");

		// assert
		System.err.println("01 Jan 1970 01:00:05,000" + " - " + dateAndNanos);
		assertEquals("01 Jan 1970 01:00:05,000", dateAndNanos);
	}


	private AttilaMockWrapper mock = new AttilaMockWrapper();
	@Rule
	public WireMockClassRule rule = mock.getWireMockClassRule();
	private TestCaseRunnable runner;

	@Ignore
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
}