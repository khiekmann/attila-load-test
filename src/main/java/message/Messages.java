package message;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by HiekmaHe on 01.12.2016.
 *
 * SRP: List of messages.
 */
public class Messages
{
	private List<Message> messages;
	private int index;

	public Messages() {
		messages = new LinkedList<>();
		index = -1;
	}

	public boolean add(Message message)
	{
		return messages.add(message);
	}

	public int size()
	{
		return messages.size();
	}

	public Message next()
	{
		return messages.get(incrementIndex());
	}

	private int incrementIndex()
	{
		index++;
		return index % messages.size();
	}
}