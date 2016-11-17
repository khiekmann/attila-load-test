package connection;

import java.io.IOException;


/**
 * Created by HiekmaHe on 17.11.2016.
 */
public class ConnectionResponse extends GetConnectionable
{
	private int responseCode;

	public ConnectionResponse(GetConnectionable getConnectionable) throws IOException
	{
		super(getConnectionable);
		responseCode = connection.getResponseCode();
	}

	public int getResponseCode()
	{
		return responseCode;
	}
}
