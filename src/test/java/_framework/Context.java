package _framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import attila.AttilaSendingCreate;
import attila.AttilaUseCase;
import send.Sendable;
import send.Sending;
import send.SendingCreate;
import testcase.DataForTestCase;
import testcase.TestCase;
import testcase.TestCaseExecutor;
import testcase.TestCaseRunnable;
import useCase.UseCaseable;


/**
 * Created by HiekmaHe on 22.11.2016.
 *
 * SRP: Offer functionality for testing
 */
public class Context
{

	public List<String> getMessages() {
		List<String> messages = new ArrayList<>();
		messages.add("A");
		messages.add("B");
		messages.add("C");
		return messages;
	}

	public AttilaUseCase getAttilaUseCase() throws IOException
	{
		WireMockAttila mock = new WireMockAttila();
		SendingCreate attilaConnectionCreate = AttilaSendingCreate.createInstance(mock.getURL());
		Sendable attilaSender = new Sending(attilaConnectionCreate);
		return new AttilaUseCase(getMessages(), attilaSender);
	}

	public TestCase getTestCaseVanilla() throws IOException
	{
		UseCaseable useCase = new VanillaUseCase();
		DataForTestCase data = new DataForTestCase();
		return new TestCase(useCase, data);
	}

	public TestCaseRunnable getTestCaseRunnerVanilla() throws IOException
	{
		return new TestCaseExecutor(getTestCaseVanilla());
	}

	public DataForTestCase getData()
	{
		return new DataForTestCase();
	}

	public TestCaseRunnable getAttilaRunner() throws IOException
	{
		return new TestCaseExecutor(getTestCaseAttila());
	}

	public TestCase getTestCaseAttila() throws IOException
	{
		UseCaseable useCase = getAttilaUseCase();
		DataForTestCase data = new DataForTestCase();
		return new TestCase(useCase, data);
	}
}
