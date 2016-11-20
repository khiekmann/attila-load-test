package connection;

import java.net.HttpURLConnection;


/**
 * Created by HiekmaHe on 17.11.2016.
 *
 * SRP: Accessors for HttpURLConnection connection
 *
 */
public abstract class ConnectionAction
{
	private HttpURLConnection httpUrl;

	ConnectionAction(ConnectionAction getConnectionable)
	{
		httpUrl = getConnectionable.httpUrl;
	}

	public ConnectionAction(HttpURLConnection httpUrl)
	{
		this.httpUrl = httpUrl;
	}

	HttpURLConnection getHttpUrl() {return httpUrl;};
}
