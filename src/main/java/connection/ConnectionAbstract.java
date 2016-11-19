package connection;

import java.net.HttpURLConnection;


/**
 * Created by HiekmaHe on 17.11.2016.
 */
public abstract class ConnectionAbstract
{
	private HttpURLConnection connection;

	public ConnectionAbstract(HttpURLConnection useThisConnection) {
		connection = useThisConnection;
	}

	public ConnectionAbstract(ConnectionAbstract getConnectionable)
	{
		connection = getConnectionable.connection;
	}

	public HttpURLConnection getHttpUrl() {return connection;};
}
