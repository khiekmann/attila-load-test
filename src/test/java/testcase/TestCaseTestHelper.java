package testcase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import _framework.TestWireMockClassRule;
import attila.AttilaSendingCreate;
import send.Sendable;
import send.Sending;
import time.Time;
import useCase.TestUseCase;


/**
 * Created by HiekmaHe on 21.11.2016.
 */
public class TestCaseTestHelper
{

	public static TestCase createTestCase(URL url) throws IOException
	{
		List<String> messages = new ArrayList<>();
		messages.add("A");
		messages.add("B");
		messages.add("C");
		String urlPath = "/somewhere";
		HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
		httpUrl.setDoOutput(true);
		Sendable sender = new Sending(AttilaSendingCreate.createInstance(url));
		DataForTestCase data = createData();
		TestUseCase useCase = new TestUseCase(sender);
		return new TestCase(useCase, data);
	}

	public static TestCaseRunnable createTestCaseRunnable() throws IOException
	{
		TestCase testCase = TestCaseTestHelper.createTestCase(TestWireMockClassRule.createURL());
		return new TestCaseExecutor(testCase);
	}

	public static DataForTestCase createData()
	{
		DataForTestCase data = new DataForTestCase();
		data.expectedDuration = Time.seconds(5);
		return data;
	}
}
