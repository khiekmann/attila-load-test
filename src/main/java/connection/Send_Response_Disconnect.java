package connection;

import java.io.IOException;


/**
 * Created by HiekmaHe on 16.11.2016.
 *
 * SRP: Send message, save responseCode
 */
public class Send_Response_Disconnect implements Sendable
{
	private final ConnectionCreate creator;
	private int responseCode;

	public Send_Response_Disconnect(ConnectionCreate create) throws IOException
	{
		this.creator = create;
	}

	@Override
	public void send(String message) throws IOException
	{
		ConnectionCreate create = creator.create();
		ConnectionSend send = new ConnectionSend(create, message);
		ConnectionResponse response = new ConnectionResponse(send);
		responseCode = response.code();
		new ConnectionDisconnect(response);
	}

	@Override
	public int getResponseCode()
	{
		return responseCode;
	}
}