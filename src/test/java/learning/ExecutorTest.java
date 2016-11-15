package learning;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import all.UseCase;
import testcase.DataForTestCase;
import testcase.TestCase;


/**
 * Created by HiekmaHe on 15.11.2016.
 */
public class ExecutorTest
{
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
		UseCase useCase = new UseCase();
		DataForTestCase data = new DataForTestCase();
		Runnable r1 = new TestCase(useCase, data);

		ExecutorService executor = Executors.newSingleThreadExecutor();

		executor.execute( r1 );

		Thread.sleep( 500 );

		executor.execute( r1 );

		executor.shutdown();
	}
}
