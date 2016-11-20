package useCase;

import java.io.IOException;

import send.Sendable;


/**
 * Created by HiekmaHe on 20.11.2016.
 */
public class TestUseCase implements UseCaseable
{
	private int i = 0;
	private Sendable sender;

	public TestUseCase(Sendable sender)
	{
		this.sender = sender;
	}

	@Override
	public void doOneIteration() throws IOException
	{
		sender.send(String.valueOf(i));
	}

	@Override
	public int getResponseCode()
	{
		return 0;
	}
}
