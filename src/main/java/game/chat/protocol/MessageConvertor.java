package game.chat.protocol;

public class MessageConvertor {
	public String[] parseMessage(String unParsedMessage) {
		return unParsedMessage.split(":",3);
	}
}
