package attila;

import java.io.IOException;

import message.Message;
import message.Messages;
import send.Sendable;
import useCase.UseCaseable;


/**
 * Created by HiekmaHe on 18.11.2016.
 */
public class AttilaUseCase implements UseCaseable
{

	private final Messages messages;
	private final Sendable sender;
	private int counter = 0;

	public AttilaUseCase(Messages messages, Sendable sender)
	{
		this.messages = messages;
		this.sender = sender;
	}

	@Override
	public void executeOnce() throws IOException
	{
		sender.send(nextMessage());
	}

	@Override
	public int getResponseCode()
	{
		return sender.getResponseCode();
	}

	private Message nextMessage()
	{
		return messages.next();
	}
}