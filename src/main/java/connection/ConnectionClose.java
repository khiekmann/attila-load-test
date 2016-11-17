package connection;

/**
 * Created by HiekmaHe on 17.11.2016.
 */
public class ConnectionClose extends GetConnectionable
{

	public ConnectionClose(GetConnectionable getConnectionable)
	{
		super(getConnectionable);
		connection.disconnect();
	}
}
