package learning;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import attila.AttilaConnectionCreate;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import connection.Send_Response_Disconnect;
import connection.Sendable;
import testcase.DataForTestCase;
import testcase.TestCase;
import useCase.UseCaseDummy;
import useCase.UseCaseable;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


/**
 * Created by HiekmaHe on 15.11.2016.
 */
public class ExecutorTest
{

	private static int port = 8080;
	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(port);
	@Rule
	public WireMockClassRule instanceRule = wireMockRule;
	private String host = "http://localhost:";
	private String urlPath = "/cai/rtm/v1/d";

	@Before
	public void before() throws MalformedURLException
	{
		stubFor(post(urlEqualTo(urlPath)).willReturn(
				aResponse()
						.withStatus(202)
				)
		);
	}

	@Test
	public void testExecutor() throws Exception{
		Runnable r1 = new Runnable() {
			@Override public void run() {
				System.out.println( "A1 " + Thread.currentThread() );
				System.out.println( "A2 " + Thread.currentThread() );
			}
		};

		Runnable r2 = new Runnable() {
			@Override public void run() {
				System.out.println( "B1 " + Thread.currentThread() );
				System.out.println( "B2 " + Thread.currentThread() );
			}
		};

		ExecutorService executor = Executors.newCachedThreadPool();

		executor.execute( r1 );
		executor.execute( r2 );

		Thread.sleep( 500 );

		executor.execute( r1 );
		executor.execute( r2 );

		executor.shutdown();
	}

	@Test
	public void testExecutorWithTestCase() throws Exception{
		List<String> messages = new ArrayList<>();
		messages.add("A");
		messages.add("B");
		messages.add("C");
		String urlPath = "/somewhere";
		URL url = new URL("http://localhost:" + port + urlPath);
		HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
		httpUrl.setDoOutput(true);
		Sendable sender = new Send_Response_Disconnect(AttilaConnectionCreate.createInstance(url));
		UseCaseable useCase = new UseCaseDummy(messages, sender);
		DataForTestCase data = new DataForTestCase();
		Runnable r1 = new TestCase(useCase, data);

		ExecutorService executor = Executors.newSingleThreadExecutor();

		executor.execute( r1 );

		Thread.sleep( 500 );

		executor.execute( r1 );

		executor.shutdown();
	}
}
