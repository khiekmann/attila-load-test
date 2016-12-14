package attila;

import java.io.IOException;

import message.Messages;
import send.Sendable;
import send.Sending;
import testCase.DataForTestCase;
import testCase.TestCase;
import testCase.TestCaseExecutor;
import testCase.TestCaseRunnable;
import time.Time;
import useCase.UseCaseable;


/**
 * Created by HiekmaHe on 13.12.2016.
 */
public class AttilaHelper
{
	public static Messages getMessages()
	{
		Messages messages = new Messages();
		AttilaMessage message;
		for (int i = 0; i < 10; i++) {
			message = new AttilaMessage("<?xml version=\"1.0\" type=\"" + i + "\" encoding=\"UTF-8\"?>", i);
			messages.add(message);
		}
		return messages;
	}

	public static AttilaUseCase createAttilaUseCase() throws IOException
	{
		Sendable sender = new Sending(AttilaSendingCreate.createInstance(mock.createUrlExitOnException()));
		return new AttilaUseCase(getMessages(), sender);
	}

	public static TestCase createAttilaTestCase() throws IOException
	{
		UseCaseable useCase = createAttilaUseCase();
		DataForTestCase data = new DataForTestCase();
		data.expectedDuration = Time.seconds(5);
		return new TestCase(useCase, data);
	}

	public static TestCaseRunnable createAttilaRunner() throws IOException
	{
		TestCase testCase = createAttilaTestCase();
		return new TestCaseExecutor(testCase);
	}

	private static AttilaMockWrapper mock = new AttilaMockWrapper();
}
