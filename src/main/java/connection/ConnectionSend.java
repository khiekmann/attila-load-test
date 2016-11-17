package connection;

import java.io.IOException;


/**
 * Created by HiekmaHe on 17.11.2016.
 *
 * SRP: Send message
 */
public class ConnectionSend extends GetConnectionable
{
	public ConnectionSend (GetConnectionable getConnectionable, String message) throws IOException
	{
		super(getConnectionable);
		connection.getOutputStream().write(message.getBytes());
		connection.getOutputStream().flush();
		connection.getOutputStream().close();
	}
}