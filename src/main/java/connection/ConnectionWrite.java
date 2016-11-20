package connection;

import java.io.IOException;


/**
 * Created by HiekmaHe on 17.11.2016.
 *
 * SRP: Send message
 */
public class ConnectionWrite extends ConnectionAction
{
	public ConnectionWrite(ConnectionCreate create, String message) throws IOException
	{
		super(create);
		getHttpUrl().getOutputStream().write(message.getBytes());
		getHttpUrl().getOutputStream().flush();
		getHttpUrl().getOutputStream().close();
	}
}