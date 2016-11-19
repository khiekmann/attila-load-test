package connection;

/**
 * Created by HiekmaHe on 17.11.2016.
 *
 * SRP: Disconnect
 */
public class ConnectionDisconnect extends ConnectionAbstract
{

	public ConnectionDisconnect(ConnectionResponse connection)
	{
		super(connection);
		getHttpUrl().disconnect();
	}
}