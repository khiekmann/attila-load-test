package connection;

import java.io.IOException;


/**
 * Created by HiekmaHe on 17.11.2016.
 *
 * SRP: Provide responseCode
 */
public class ConnectionResponse extends ConnectionAction
{
	private int responseCode;

	public ConnectionResponse(ConnectionWrite send) throws IOException
	{
		super(send);
		responseCode = getHttpUrl().getResponseCode();
	}

	public int code()
	{
		return responseCode;
	}
}
