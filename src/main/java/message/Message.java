package message;

/**
 * Created by HiekmaHe on 01.12.2016.
 *
 * SRP: Message superclass. Content will be sent.
 */
public class Message
{
	private String content;

	public Message(String content)
	{
		this.content = content;
	}

	@Override
	public boolean equals(Object other) {
		boolean isEqual = false;
		if (other instanceof Message) {
			isEqual = (this.hashCode() == other.hashCode());
		}
		return isEqual;
	}

	@Override
	public int hashCode() {
		return content.hashCode();
	}

	@Override
	public String toString() {
		return content;
	}

	public byte[] getBytes()
	{
		return content.getBytes();
	}
}