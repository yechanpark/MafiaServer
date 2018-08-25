package game.chat;

public class MessageConvertor {
	public String[] parseMessage(String unParsedMessage) {
		return unParsedMessage.split(":",3);
	}
}
