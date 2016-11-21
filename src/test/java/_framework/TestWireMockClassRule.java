package _framework;

import java.net.MalformedURLException;
import java.net.URL;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import testcase.DataForTestCase;
import testcase.TestCase;
import useCase.TestUseCase;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


/**
 * Created by HiekmaHe on 21.11.2016.
 */
public class TestWireMockClassRule
{

	TestUseCase useCase;
	DataForTestCase data;
	TestCase testCase;

	private static int port = 8080;
	private static String host = "http://localhost:";
	private static String urlPath = "/cai/rtm/v1/d";

	public static URL createURL() throws MalformedURLException
	{
		return new URL(host + port + urlPath);
	}

	public static WireMockClassRule createInstance()
	{
		return new WireMockClassRule(port);
	}

	public static MappingBuilder stubFor()
	{
		return post(urlEqualTo(urlPath)).willReturn(
				aResponse()
						.withStatus(202)
		);
	}
}
