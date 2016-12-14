package send;

import java.io.IOException;
import java.net.URL;

import _framework.TestHelper;
import attila.AttilaSendingCreate;
import attila.AttilaUseCase;
import message.Messages;
import testCase.DataForTestCase;
import testCase.TestCase;
import testCase.TestCaseExecutor;


/**
 * Created by HiekmaHe on 22.11.2016.
 */
public class SendingTestHelper
{

	private static int port = 8080;
	private static String host = "http://0.0.0.0";
	private static String urlPath = "/cai/rtm/v1/d/";
	private static TestCase testCase;
	private static AttilaUseCase attilaUseCase;
	private static DataForTestCase data;
	private static Messages messages;

	public static TestCaseExecutor createRunner() throws IOException
	{
		messages = TestHelper.createMessages();
		URL url = new URL(host + ":" + port + urlPath);
		Sendable sender = new Sending(AttilaSendingCreate.createInstance(url));
		AttilaUseCase attilaUseCase = new AttilaUseCase(messages, sender);
		data = new DataForTestCase();
		TestCase testCase = new TestCase(attilaUseCase, data);
		return new TestCaseExecutor(testCase);
	}
}
