package _framework;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import _thirdparty.wiremock.MockWrapper;
import message.Message;
import message.Messages;
import send.Sendable;
import send.Sending;
import send.SendingCreate;
import send.SendingCreateExample;
import testCase.DataForTestCase;
import testCase.TestCase;
import testCase.TestCaseExecutor;
import testCase.TestCaseRunnable;
import time.Time;
import useCase.UseCaseExample;
import useCase.UseCaseable;


/**
 * Created by HiekmaHe on 27.11.2016.
 *
 * SRP: Offering cretors for attila's use case.
 */
public class TestHelper
{
	private static MockWrapper mock = new MockWrapper("/c/r/v/d", null);

	public static String codeLocation()
	{
		StackTraceElement element = new Exception().getStackTrace()[1];
		return element.getClassName() + "." + element.getMethodName();
	}

	public static Messages createMessages()
	{
		Messages messages = new Messages();
		Message message;
		for (int i = 0; i < 10; i++) {
			message = new Message("<?xml version=\"1.0\" encoding=\"UTF-8\" name=\"" + i + "\">");
			messages.add(message);
		}
		return messages;
	}

	public static TestCase createTestCase()
	{
		URL url = mock.createUrlExitOnException();
		HttpURLConnection httpUrl = createHttpUrlConnection(url);
		SendingCreate creator = new SendingCreateExample(httpUrl);
		Sendable sender = new Sending(creator);
		UseCaseable useCase = new UseCaseExample(sender);
		DataForTestCase data = new DataForTestCase();
		data.expectedDuration = Time.seconds(5);
		return new TestCase(useCase, data);
	}

	private static HttpURLConnection createHttpUrlConnection(URL url)
	{
		HttpURLConnection httpUrl;
		try {
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.setDoOutput(true);
			httpUrl.setDoInput(true);
		}
		catch (IOException e)
		{
			httpUrl = null;
		}
		return httpUrl;
	}

	public static TestCaseRunnable createRunner()
	{
		return new TestCaseExecutor(createTestCase());
	}
}