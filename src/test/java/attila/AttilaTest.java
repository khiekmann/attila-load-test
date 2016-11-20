package attila;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import testcase.TestCaseRunnable;
import time.Time;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 19.11.2016.
 *
 * SRP:
 */
public class AttilaTest
{
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(options().port(AttilaTestHelper.getPort()), false);
	private TestCaseRunnable attilaRunner;

	@Before
	public void before() throws IOException
	{
		wireMockRule.stubFor(AttilaTestHelper.buildMappingInbound());
		attilaRunner = AttilaTestHelper.createRunner();
	}

	@Test
	public void doAttila() throws Exception
	{
		// arrange

		// act
		attilaRunner.startRun();
		Time.seconds(3).sleep();
		attilaRunner.stopRun();

		// assert
		List<ServeEvent> allServeEvents = wireMockRule.getAllServeEvents();
		for (ServeEvent aEvent : allServeEvents) {
			assertEquals(202, aEvent.getResponse().getStatus());
		}
	}
}