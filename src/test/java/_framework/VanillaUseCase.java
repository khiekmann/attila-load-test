package _framework;

import java.io.IOException;

import send.Sendable;
import send.Sending;
import useCase.UseCaseable;


/**
 * Created by HiekmaHe on 22.11.2016.
 */
public class VanillaUseCase implements UseCaseable
{
	private final Sendable sender;

	public VanillaUseCase() throws IOException
	{
		this.sender = new Sending(SendingCreateVanilla.createInstance());
	}

	@Override
	public void doOneIteration() throws IOException
	{
		sender.send("UseCaseVanilla.doOneIteration");
	}

	@Override
	public int getResponseCode()
	{
		return sender.getResponseCode();
	}
}
