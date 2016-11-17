package all;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by HiekmaHe on 10.11.2016.
 *
 * SRP: Execute one iteration of the use Case
 */
public class UseCaseMySpecial implements UseCaseable
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UseCaseable.class);

	private List<String> messages;
	private String message;
	private Connectionable connection;
	private int counter;

	public UseCaseMySpecial(List<String> useTheseMessages, Connectionable useThisConnector) {
		messages = useTheseMessages;
		message = new String();
		connection = useThisConnector;
		counter = -1;
	}

	public void doOneIteration()
	{
		prepareMessage();
		try
		{
			sendMessage();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		log();
		stdOut();
	}

	@Override
	public int getResponseCode()
	{
		return connection.getResponseCode();
	}

	private void prepareMessage()
	{
		counter++;
		message = messages.get(counter % messages.size());
	}

	private void sendMessage() throws IOException
	{
		connection.send(message);
	}

	private void log()
	{
		LOGGER.info(message);
	}

	private void stdOut() {

		System.out.println(counter + " " + message);
	}
}