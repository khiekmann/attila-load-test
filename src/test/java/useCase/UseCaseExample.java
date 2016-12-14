package useCase;

import java.io.IOException;

import _framework.TestHelper;
import message.Message;
import send.Sendable;


/**
 * Created by HiekmaHe on 20.11.2016.
 *
 * SRP: Example of implementing UseCaseable
 */
public class UseCaseExample implements UseCaseable
{
	private int counter = 0;
	private Sendable sender;

	public UseCaseExample(Sendable sender)
	{
		this.sender = sender;
	}

	@Override
	public void executeOnce() throws IOException
	{
		Message message = new Message(TestHelper.codeLocation() +  ": Executes " + counter++ + " times.");
		sender.send(message);
	}

	@Override
	public int getResponseCode()
	{
		return sender.getResponseCode();
	}
}