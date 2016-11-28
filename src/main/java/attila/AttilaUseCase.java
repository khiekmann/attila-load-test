package attila;

import java.io.IOException;
import java.util.List;

import send.Sendable;
import useCase.UseCaseable;


/**
 * Created by HiekmaHe on 18.11.2016.
 */
public class AttilaUseCase implements UseCaseable
{

	private final List<String> messages;
	private final Sendable sender;
	private int counter = 0;

	public AttilaUseCase(List<String> messages, Sendable sender)
	{
		this.messages = messages;
		this.sender = sender;
	}

	@Override
	public void executeOnce() throws IOException
	{
		sender.send(getMessage());
	}

	@Override
	public int getResponseCode()
	{
		return sender.getResponseCode();
	}

	private String getMessage()
	{
		return messages.get(counter++ % messages.size());
	}
}