package connection;

import java.io.IOException;
import java.net.URL;

/**
 * Created by HiekmaHe on 16.11.2016.
 *
 * SRP: Send message per POST-Request, save responseCode
 */
public class PostConnection implements Sendable
{
	GetConnectionable getConnectionable;
	private ConnectionResponse response;

	public PostConnection(URL url) throws IOException
	{
		getConnectionable = new ConnectionCreate(url, MyRequestMethod.POST);
		getConnectionable.connection.setDoOutput(true);
	}

	@Override
	public void send(String message) throws IOException
	{
		ConnectionSend connectionSend = new ConnectionSend(getConnectionable, message);
		response = new ConnectionResponse(connectionSend);
		new ConnectionClose(response);
	}

	@Override
	public int getResponseCode()
	{
		return response.getResponseCode();
	}
}