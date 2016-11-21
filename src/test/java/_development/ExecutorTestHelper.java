package _development;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import attila.AttilaSendingCreate;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import send.Sendable;
import send.Sending;
import testcase.DataForTestCase;
import testcase.TestCase;
import useCase.TestUseCase;
import useCase.UseCaseable;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;


/**
 * Created by HiekmaHe on 21.11.2016.
 */
public class ExecutorTestHelper
{
	private static WireMock wm = new WireMock();
	private static int port = 8080;
	private static String host = "http://localhost";
	private static String urlPath = "/cai/rtm/v1/d";

	public static Runnable createRunnable1() throws IOException
	{
		List<String> messages = new ArrayList<>();
		messages.add("A " + Thread.currentThread());
		messages.add("B " + Thread.currentThread());
		URL url = createURL();
		HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
		httpUrl.setDoOutput(true);
		Sendable sender = new Sending(AttilaSendingCreate.createInstance(url));
		UseCaseable useCase = new TestUseCase(sender);
		DataForTestCase data = new DataForTestCase();

		return new TestCase(useCase, data);
	}

	private static URL createURL() throws MalformedURLException
	{
		return new URL(host + ":" + port + urlPath);
	}

	public static Runnable createRunnable2() throws IOException
	{
		List<String> messages = new ArrayList<>();
		messages.add("A " + Thread.currentThread());
		messages.add("B " + Thread.currentThread());
		HttpURLConnection httpUrl = (HttpURLConnection) createURL().openConnection();
		httpUrl.setDoOutput(true);
		Sendable sender = new Sending(AttilaSendingCreate.createInstance(createURL()));
		UseCaseable useCase = new TestUseCase(sender);
		DataForTestCase data = new DataForTestCase();

		return new TestCase(useCase, data);
	}

	public static int getPort()
	{
		return port;
	}

	public static MappingBuilder buildMappingInbound()
	{
		return wm.post(urlEqualTo(urlPath)).willReturn(
				aResponse()
						.withStatus(202)
		);
	}

	public static WireMockRule createWireMockRule()
	{
		return new WireMockRule(options().port(ExecutorTestHelper.getPort()), false);
	}
}