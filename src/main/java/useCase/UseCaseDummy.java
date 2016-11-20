package useCase;

import java.io.IOException;
import java.util.List;

import connection.Sendable;


/**
 * Created by HiekmaHe on 18.11.2016.
 */
public class UseCaseDummy implements UseCaseable
{

	private final List<String> messages;
	private final Sendable sender;
	private int counter = 0;
	private int responseCode = 0;

	public UseCaseDummy(List<String> messages, Sendable sender)
	{
		this.messages = messages;
		this.sender = sender;
	}

	@Override
	public void doOneIteration()
	{
		try
		{
			System.out.println(counter);
			sender.send(String.valueOf(counter++));
			responseCode = sender.getResponseCode();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public int getResponseCode()
	{
		return  responseCode;
	}
}