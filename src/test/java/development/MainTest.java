package development;

import org.junit.Test;

import all.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


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
		long expectedDurationInMinutes = 10;
		DataForTestCase data = new DataForTestCase();
		data.expectedDurationInMinutes = expectedDurationInMinutes;
		ATestCase useCaseA_10min = new ATestCase(useCase, data);

		// act
		useCaseA_10min.start();
		Result result = useCaseA_10min.getResult();
		long actualDurationInMinutes = result.expectedDurationInMinutes;

		// assert
		assertEquals(expectedDurationInMinutes, actualDurationInMinutes);
	}

	@Test
	public void actualDurationInNanosIfUseCaseWasNotRun() {
		// arrange
		UseCase useCase = new useCaseA();
		DataForTestCase data = new DataForTestCase();
		data.actualDurationInNanos = 0;
		ATestCase useCaseA_10min = new ATestCase(useCase, data);

		// act
		Result result = useCaseA_10min.getResult();
		long expectedActualDurationInNanos = 0;
		long actualActualDurationInNanos = result.actualDurationInNanos;

		// assert
		assertEquals(expectedActualDurationInNanos, actualActualDurationInNanos);
	}

	@Test
	public void runUseCaseFor2Seconds() {
		// arrange
		UseCase useCase = new useCaseA();
		DataForTestCase data = new DataForTestCase();
		data.actualDurationInNanos = Converter.secondsToNanos(2);
		ATestCase useCaseA_10min = new ATestCase(useCase, data);

		// act
		Result result = useCaseA_10min.getResult();
		long expectedActualDurationInNanos = 0;
		long actualActualDurationInNanos = result.actualDurationInNanos;

		// assert
		assertEquals(expectedActualDurationInNanos, actualActualDurationInNanos);
	}
}