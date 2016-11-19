package attila;

import java.io.IOException;
import java.util.List;

import connection.Sendable;
import useCase.UseCaseable;


/**
 * Created by HiekmaHe on 18.11.2016.
 */
public class AttilaUseCase implements UseCaseable
{

	private final List<String> messages;
	private final Sendable sender;
	private int responseCode = 0;

	public AttilaUseCase(List<String> messages, Sendable sender)
	{
		this.messages = messages;
		this.sender = sender;
	}

	@Override
	public void doOneIteration()
	{
		try
		{
			sender.send("hallo");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public int getResponseCode()
	{

		System.out.println("Not implemented!");
		return -1;
	}
}
