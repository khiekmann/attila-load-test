package connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by HiekmaHe on 17.11.2016.
 */
public class MySpecialConnection extends PostConnection
{
	public MySpecialConnection(URL toThisURL) throws IOException
	{
		super(toThisURL);
		getConnectionable.connection.setConnectTimeout(0);
		getConnectionable.connection.setDoInput(true);
	}
}
