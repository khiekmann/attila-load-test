package message;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;


/**
 * Created by HiekmaHe on 01.12.2016.
 *
 * SRP: Messages container. They are generated before the run.
 */
public class MessageTest
{
	private Messages messages;
	private Messages attilaMessage;

	@Before
	public void before() {
		messages = new Messages();
		messages.add(new Message("message 1"));
		messages.add(new Message("message 2"));
		messages.add(new Message("message 3"));
		attilaMessage = new Messages();
		attilaMessage.add(new Message("<?xml version=\"1.0\" xtraceid=\"1\" encoding=\"UTF-8\"?>"));
		attilaMessage.add(new Message("<?xml version=\"1.0\" xtraceid=\"2\" encoding=\"UTF-8\"?>"));
		attilaMessage.add(new Message("<?xml version=\"1.0\" xtraceid=\"3\" encoding=\"UTF-8\"?>"));
	}

	@Test
	public void testCreation() {
		// arrange

		// act
		Messages messages = new Messages();

		// assert
		assertNotNull(messages);
	}

	@Test
	public void testAddSimpleMessage() throws Exception
	{
		// arrange
		// xtraceid = dude;
		messages = new Messages();

		// act
		boolean added1 = messages.add(new Message("message 1"));
		boolean added2 = messages.add(new Message("message 2"));
		boolean added3 = messages.add(new Message("message 3"));

		// assert
		assertTrue(added1);
		assertTrue(added2);
		assertTrue(added3);
		assertEquals(3, messages.size());
	}

	@Test
	public void testAddAttilaMessage() throws Exception
	{
		// arrange
		// xtraceid = dude;
		attilaMessage = new Messages();

		// act
		boolean added1 = attilaMessage.add(new Message("message 1"));
		boolean added2 = attilaMessage.add(new Message("message 2"));
		boolean added3 = attilaMessage.add(new Message("message 3"));

		// assert
		assertTrue(added1);
		assertTrue(added2);
		assertTrue(added3);
		assertEquals(3, messages.size());
	}

	@Test
	public void testNext() {
		// arrange

		// act
		Message message1 = messages.next();
		Message message2 = messages.next();
		Message message3 = messages.next();
		Message message4 = messages.next();

		// assert
		assertEquals(new Message("message 1"), message1);
		assertEquals(new Message("message 2"), message2);
		assertEquals(new Message("message 3"), message3);
		assertEquals(message1, message4);
	}
}