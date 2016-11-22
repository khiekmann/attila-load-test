package useCase;

import java.io.IOException;

import _development.Eris;
import send.Sendable;


/**
 * Created by HiekmaHe on 20.11.2016.
 */
public class TestUseCase implements UseCaseable
{
	private int counter = 0;
	private Sendable sender;

	public TestUseCase(Sendable sender)
	{
		Eris.printThisMethod();
		this.sender = sender;
	}

	@Override
	public void doOneIteration() throws IOException
	{
		sender.send(String.valueOf(counter++));
	}

	@Override
	public int getResponseCode()
	{
		Eris.printThisMethod();
		return -1337;
	}
}
