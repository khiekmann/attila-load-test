package send;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import _framework.TestHelper;
import attila.AttilaMockWrapper;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import testcase.TestCaseRunnable;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * Created by HiekmaHe on 15.11.2016.
 */
public class SendingTest
{

	private AttilaMockWrapper mock = new AttilaMockWrapper();
	@Rule public WireMockClassRule rule = mock.getWireMockClassRule();
	private TestCaseRunnable runner;

	@Before
	public void before() throws Exception
	{
		rule.givenThat(mock.receivesAnyRequestThenReturn200TextplainContent());
		runner = TestHelper.createAttilaRunner();
	}

	@Test
	public void testSending() throws Exception
	{
		// arrange

		// act
		boolean isRunning1_false = runner.isRunning();
		runner.startRun();
		Thread.sleep(1000);
		boolean isRunning2_true = runner.isRunning();
		runner.stopRun();
		boolean isRunning3_false = runner.isRunning();

		// assert
		assertFalse(isRunning1_false);
		assertTrue(isRunning2_true);
		assertFalse(isRunning3_false);
	}
}