package connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by HiekmaHe on 17.11.2016.
 *
 * SRP: Create HTTPURLConnection
 */
public class ConnectionCreate extends GetConnectionable
{
	public ConnectionCreate(URL url, MyRequestMethod method) throws IOException
	{
		super((HttpURLConnection) url.openConnection());
		connection.setRequestMethod(method.name());
	}
}