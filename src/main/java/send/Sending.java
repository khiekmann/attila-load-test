package send;

import java.io.IOException;
import java.net.ConnectException;


/**
 * Created by HiekmaHe on 16.11.2016.
 *
 * SRP: Send message, save responseCode
 */
public class Sending implements Sendable
{
	private final SendingCreate creator;
	private int responseCode;

	public Sending(SendingCreate create) throws IOException
	{
		this.creator = create;
	}

	@Override
	public void send(String message) throws IOException
	{
		try {
			SendingCreate create = creator.create();
			SendingWrite write = new SendingWrite(create, message);
			SendingResponse response = new SendingResponse(write);
			responseCode = response.code();
			new SendingDisconnect(response);
		} catch (ConnectException e) {
			throw new ConnectException(e.getMessage() + "\n" + creator.getHttpUrl());
		}
	}

	@Override
	public int getResponseCode()
	{
		return responseCode;
	}
}