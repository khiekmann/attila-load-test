package attila;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import connection.ConnectionCreate;
import connection.Send_Response_Disconnect;
import connection.Sendable;
import testcase.DataForTestCase;
import testcase.TestCase;
import testcase.TestCaseExecutor;
import testcase.TestCaseRunnable;
import time.Time;
import useCase.UseCaseable;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


/**
 * Created by HiekmaHe on 19.11.2016.
 */
public class AttilaTest
{
	private static int port = 8080;
	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(port);
	@Rule
	public WireMockClassRule instanceRule = wireMockRule;
	private String host = "http://localhost:";
	private static URL url;
	private String urlPath = "/cai/rtm/v1/d";

	@Before
	public void before() throws MalformedURLException
	{
		url = new URL(host + port + urlPath);
		stubFor(post(urlEqualTo(urlPath)).willReturn(
				aResponse()
						.withStatus(202)
				)
		);
	}

	@Test
	public void doAttila() throws Exception
	{
		// arrange
		List<String> messages = new ArrayList<String>();
		messages.add("A");
		messages.add("B");
		messages.add("C");
		URL url = new URL(host + port + urlPath);
		ConnectionCreate create = AttilaConnectionCreate.createInstance(url);
		Sendable sender = new Send_Response_Disconnect(create);
		UseCaseable useCase = new AttilaUseCase(messages, sender);
		DataForTestCase data = new DataForTestCase();
		TestCase testCase = new TestCase(useCase, data);
		TestCaseRunnable runner = new TestCaseExecutor(testCase);

		// act
		runner.startRun();
		Time.seconds(3).sleep();
		runner.stopRun();

		// assert
	}
}
