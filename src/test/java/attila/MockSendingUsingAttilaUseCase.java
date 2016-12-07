package attila;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import send.Sendable;
import send.Sending;
import testCase.DataForTestCase;
import testCase.TestCase;
import testCase.TestCaseExecutor;
import testCase.TestCaseRunnable;
import useCase.UseCaseable;


/**
 * Created by HiekmaHe on 27.11.2016.
 *
 * SRP: As soon as usecase for attila is done, this is its test
 */
public class MockSendingUsingAttilaUseCase
{

	private AttilaMockWrapper mock = new AttilaMockWrapper();
	@Rule public WireMockClassRule rule = mock.getWireMockClassRule();
	private TestCaseRunnable runner;

	@Before
	public void before() throws IOException
	{
		List<String> messages = new ArrayList<>();
		URL attilaURL = mock.createUrlExitOnException();
		AttilaSendingCreate creator = AttilaSendingCreate.createInstance(attilaURL);
		Sendable sender = new Sending(creator);
		UseCaseable useCase = new AttilaUseCase(messages, sender);
		DataForTestCase data = new DataForTestCase();
		TestCase testCase = new TestCase(useCase, data);
		runner = new TestCaseExecutor(testCase);
	}

}