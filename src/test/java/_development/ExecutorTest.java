package _development;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;


/**
 * Created by HiekmaHe on 15.11.2016.
 */
public class ExecutorTest
{

	@Rule
	public WireMockRule wireMockRule = ExecutorTestHelper.createWireMockRule();

	@Before
	public void before() throws MalformedURLException
	{
		wireMockRule.stubFor(ExecutorTestHelper.buildMappingInbound());
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
		// arrange
		Runnable r1 = ExecutorTestHelper.createRunnable1();
		Runnable r2 = ExecutorTestHelper.createRunnable2();

		// act
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(r1);
		executor.execute(r2);
		Thread.sleep(500);
		executor.execute(r1);
		executor.execute(r2);
		executor.shutdown();

		// assert
	}
}