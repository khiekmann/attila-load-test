package connection;

import java.io.IOException;
import java.net.HttpURLConnection;


/**
 * Created by HiekmaHe on 17.11.2016.
 *
 */
public abstract class ConnectionCreate extends ConnectionAction
{
	public ConnectionCreate(HttpURLConnection httpUrl)
	{
		super(httpUrl);
	}

	public abstract ConnectionCreate create() throws IOException;
}