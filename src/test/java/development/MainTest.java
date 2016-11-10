package development;

import org.junit.Test;

import all.*;

import static junit.framework.TestCase.*;


/**
 * Created by HiekmaHe on 10.11.2016.
 *
 */
public class MainTest
{
	@Test
	public void easySetup() {
		// arrange
		UseCase useCase = new useCaseA();
		DataForTestCase data = new DataForTestCase();
		ATestCase useCaseA_10min = new ATestCase(useCase, data);

		// act
		useCaseA_10min.start();
		Result result = useCaseA_10min.getResult();

		// assert
		assertNotNull(result);
	}

	@Test
	public void setExpectedDurationAndCheckItsValueInResult() {
		// arrange
		UseCase useCase = new useCaseA();
		long expectedDurationInNanos = Converter.secondsToNanos(5);
		DataForTestCase data = new DataForTestCase();
		data.expectedDurationInNanos = expectedDurationInNanos;
		ATestCase useCaseA_10min = new ATestCase(useCase, data);

		// act
		useCaseA_10min.start();
		Result result = useCaseA_10min.getResult();
		long actualExpectedDurationInNanos = result.expectedDurationInNanos;

		// assert
		assertEquals(expectedDurationInNanos, actualExpectedDurationInNanos);
	}

	@Test
	public void actualDurationInNanosIfUseCaseWasNotRunReturnsNull() {
		// arrange
		UseCase useCase = new useCaseA();
		DataForTestCase data = new DataForTestCase();
		data.actualDurationInNanos = 0;
		ATestCase useCaseA_10min = new ATestCase(useCase, data);

		// act
		Result result = useCaseA_10min.getResult();

		// assert
		assertNull(result);
	}

	@Test
	public void runUseCaseFor2Seconds() {
		// arrange
		UseCase useCase = new useCaseA();
		DataForTestCase data = new DataForTestCase();
		data.expectedDurationInNanos = Converter.secondsToNanos(2);
		ATestCase useCaseA_10min = new ATestCase(useCase, data);
		long acceptedDeviation = Converter.secondsToNanos(1);
		long max = data.expectedDurationInNanos + acceptedDeviation;

		// act
		useCaseA_10min.start();
		Result result = useCaseA_10min.getResult();
		long expectedActualDurationInNanos = Converter.secondsToNanos(2);
		long actualActualDurationInNanos = result.actualDurationInNanos;

		// assert
		assertFalse("Run takes not long enough time.", expectedActualDurationInNanos > actualActualDurationInNanos);
		assertTrue("Run takes too long (including deviation).",  max > actualActualDurationInNanos);
	}
}