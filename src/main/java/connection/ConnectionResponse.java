package connection;

import java.io.IOException;


/**
 * Created by HiekmaHe on 17.11.2016.
 *
 * SRP: Provide responseCode
 */
public class ConnectionResponse extends ConnectionAbstract
{
	private int responseCode;

	public ConnectionResponse(ConnectionSend connection) throws IOException
	{
		super(connection);
		responseCode = getHttpUrl().getResponseCode();
	}

	public int code()
	{
		return responseCode;
	}
}
