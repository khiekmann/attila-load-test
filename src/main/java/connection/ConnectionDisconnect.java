package connection;

/**
 * Created by HiekmaHe on 17.11.2016.
 *
 * SRP: Disconnect
 */
public class ConnectionDisconnect extends ConnectionAction
{

	public ConnectionDisconnect(ConnectionResponse response)
	{
		super(response);
		getHttpUrl().disconnect();
	}
}