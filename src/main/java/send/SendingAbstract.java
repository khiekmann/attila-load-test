package send;

import java.net.HttpURLConnection;


/**
 * Created by HiekmaHe on 17.11.2016.
 *
 * SRP: Accessors for HttpURLConnection connection
 *
 */
public abstract class SendingAbstract
{
	private HttpURLConnection httpUrl;

	SendingAbstract(SendingAbstract getConnectionable)
	{
		httpUrl = getConnectionable.httpUrl;
	}

	public SendingAbstract(HttpURLConnection httpUrl)
	{
		this.httpUrl = httpUrl;
	}

	HttpURLConnection getHttpUrl() {return httpUrl;};
}
