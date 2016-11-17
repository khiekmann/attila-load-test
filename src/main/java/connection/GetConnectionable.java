package connection;

import java.net.HttpURLConnection;


/**
 * Created by HiekmaHe on 17.11.2016.
 */
public abstract class GetConnectionable
{
	HttpURLConnection connection;

	public GetConnectionable(HttpURLConnection useThisConnection) {
		connection = useThisConnection;
	}

	public GetConnectionable(GetConnectionable getConnectionable)
	{
		connection = getConnectionable.connection;
	}
}
