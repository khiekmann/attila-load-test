package _development;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import static junit.framework.TestCase.assertNotNull;


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
		// arrange
		StringBuilder aSB = new StringBuilder();
		Runnable r1 = new Runnable() {
			@Override public void run() {
				aSB.append("A1 " + Thread.currentThread() + "\n" );
				aSB.append("A2 " + Thread.currentThread() + "\n" );
			}
		};

		Runnable r2 = new Runnable() {
			@Override public void run() {
				aSB.append("B1 " + Thread.currentThread() + "\n" );
				aSB.append("B2 " + Thread.currentThread() + "\n" );
			}
		};

		// act
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute( r1 );
		executor.execute( r2 );
		Thread.sleep( 500 );
		executor.execute( r1 );
		executor.execute( r2 );
		executor.shutdown();

		// assert
		assertNotNull(aSB.toString());
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