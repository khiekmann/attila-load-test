package attila;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import send.SendingCreate;
import send.Sending;
import send.Sendable;
import testcase.DataForTestCase;
import testcase.TestCase;
import testcase.TestCaseExecutor;
import testcase.TestCaseRunnable;
import useCase.UseCaseable;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;


/**
 * Created by HiekmaHe on 20.11.2016.
 */
public class AttilaTestHelper
{
	private static WireMock wm = new WireMock();
	private static int port = 8080;
	private static String host = "http://localhost";
	private static String urlPath = "/cai/rtm/v1/d/";

	public static URL getURL() throws MalformedURLException
	{
		String strUrl = host + ":" + port + urlPath;
		return new URL(strUrl);
	}

	public static MappingBuilder buildMappingInbound()
	{
		MappingBuilder builder = wm.post(urlEqualTo(urlPath)).willReturn(
				aResponse()
						.withStatus(202)
		);
		return builder;
	}

	public static int getPort()
	{
		return port;
	}

	public static List<String> createMessages()
	{
		List<String> messages = new ArrayList<>();
		messages.add("A");
		messages.add("B");
		messages.add("C");
		return messages;
	}

	public static TestCaseRunnable createRunner() throws IOException
	{
		UseCaseable attilaUseCase = createUseCase();
		DataForTestCase data = new DataForTestCase();
		TestCase testCase = new TestCase(attilaUseCase, data);
		return new TestCaseExecutor(testCase);
	}

	public static AttilaUseCase createUseCase() throws IOException
	{
		SendingCreate attilaConnectionCreate = AttilaSendingCreate.createInstance(getURL());
		Sendable attilaSender = new Sending(attilaConnectionCreate);
		return new AttilaUseCase(createMessages(), attilaSender);
	}

	public static WireMockRule wireMockRule()
	{
		return new WireMockRule(options().port(AttilaTestHelper.getPort()));
	}
}