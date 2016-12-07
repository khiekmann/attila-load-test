package send;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import attila.AttilaSendingCreate;
import attila.AttilaUseCase;
import send.Sendable;
import send.Sending;
import testcase.DataForTestCase;
import testcase.TestCase;
import testcase.TestCaseExecutor;


/**
 * Created by HiekmaHe on 22.11.2016.
 */
public class SendingTestHelper
{

	private static int port = 8080;
	private static String host = "http://localhost";
	private static String urlPath = "/cai/rtm/v1/d/";
	private static TestCase testCase;
	private static AttilaUseCase attilaUseCase;
	private static DataForTestCase data;
	private static List<String> messages;


	public static List<String> createMessages() {
		ArrayList<String> messages = new ArrayList<>();
		messages.add("<?xml version=\"1.0\" encoding=\"UTF-8\" name=\"1\">");
		messages.add("<?xml version=\"1.0\" encoding=\"UTF-8\" name=\"2\">");
		messages.add("<?xml version=\"1.0\" encoding=\"UTF-8\" name=\"3\">");
		return messages;
	}

	public static TestCaseExecutor createRunner() throws IOException
	{
		messages = createMessages();
		URL url = new URL(host + ":" + port + urlPath);
		Sendable sender = new Sending(AttilaSendingCreate.createInstance(url));
		AttilaUseCase attilaUseCase = new AttilaUseCase(messages, sender);
		data = new DataForTestCase();
		TestCase testCase = new TestCase(attilaUseCase, data);
		return new TestCaseExecutor(testCase);
	}
}
