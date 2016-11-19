package connection;

import java.io.IOException;


/**
 * Created by HiekmaHe on 17.11.2016.
 *
 * SRP: Send message
 */
public class ConnectionSend extends ConnectionAbstract
{
	public ConnectionSend (ConnectionCreate connection, String message) throws IOException
	{
		super(connection);
		getHttpUrl().getOutputStream().write(message.getBytes());
		getHttpUrl().getOutputStream().flush();
		getHttpUrl().getOutputStream().close();
	}
}