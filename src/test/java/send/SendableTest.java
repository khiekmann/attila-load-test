package send;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 20.11.2016.
 *
 * SRP:
 */
public class SendableTest
{
	private Sendable sendable;

	@Before
	public void before() {
		sendable = SendableTestHelper.createFakeSender();
	}

	@Test
	public void testSendStringMessage() throws Exception
	{
		// arrange

		// act
		sendable.send(null);

		// assert
		assertEquals(1, SendableTestHelper.size());
		assertTrue(SendableTestHelper.wasSendStringMessageExecuted());
	}

	@Test
	public void testGetResponseCode() throws Exception
	{
		// arrange

		// act
		int responseCode = sendable.getResponseCode();

		// assert
		assertEquals(0, responseCode);
	}
}
