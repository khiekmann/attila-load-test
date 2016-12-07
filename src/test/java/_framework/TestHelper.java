package _framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import attila.AttilaMockWrapper;
import attila.AttilaSendingCreate;
import attila.AttilaUseCase;
import send.Sendable;
import send.Sending;
import testCase.DataForTestCase;
import testCase.TestCase;
import testCase.TestCaseExecutor;
import testCase.TestCaseRunnable;
import time.Time;
import useCase.UseCaseable;


/**
 * Created by HiekmaHe on 27.11.2016.
 *
 * SRP: Offering cretors for attila's use case.
 */
public class TestHelper
{
	private static AttilaMockWrapper mock = new AttilaMockWrapper();

	public static TestCaseRunnable createAttilaRunner() throws IOException
	{
		TestCase testCase = createAttilaTestCase();
		return new TestCaseExecutor(testCase);
	}

	public static AttilaUseCase createAttilaUseCase() throws IOException
	{
		Sendable sender = new Sending(AttilaSendingCreate.createInstance(mock.createUrlExitOnException()));
		return new AttilaUseCase(getMessages(), sender);
	}

	public static List<String> getMessages()
	{
		List<String> messages = new ArrayList<>();
		messages.add("<?xml version=\"1.0\" type=\"A\" encoding=\"UTF-8\"?>");
		messages.add("<?xml version=\"1.0\" type=\"B\" encoding=\"UTF-8\"?>");
		messages.add("<?xml version=\"1.0\" type=\"C\" encoding=\"UTF-8\"?>");
		return messages;
	}

	public static TestCase createAttilaTestCase() throws IOException
	{
		UseCaseable useCase = createAttilaUseCase();
		DataForTestCase data = new DataForTestCase();
		data.expectedDuration = Time.seconds(5);
		return new TestCase(useCase, data);
	}

	public static String codeLocation()
	{
		StackTraceElement element = new Exception().getStackTrace()[1];
		return element.getClassName() + "." + element.getMethodName();
	}
}