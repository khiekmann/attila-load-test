package send;

import java.io.IOException;
import java.util.Collection;
import java.util.Vector;


/**
 * Created by HiekmaHe on 20.11.2016.
 *
 * SRP: Wrapping interface for more readable testing
 */
public class SendableTestHelper
{

	private static Collection<String> holdingExecutedMethodMessages = new Vector<>();
	private static String sendStringMessage= "send(String message)";

	public static Sendable createSendable()
	{
		return new Sendable()
		{
			@Override
			public void send(String message) throws IOException
			{
				holdingExecutedMethodMessages.add(sendStringMessage);
			}

			@Override
			public int getResponseCode()
			{
				return 0;
			}
		};
	}

	public static int size()
	{
		return holdingExecutedMethodMessages.size();
	}

	public static boolean wasSendStringMessageExecuted()
	{
		return holdingExecutedMethodMessages.contains(sendStringMessage);
	}
}
