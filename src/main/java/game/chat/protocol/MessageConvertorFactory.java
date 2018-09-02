package game.chat.protocol;

public class MessageConvertorFactory {
	private static MessageConvertor messageConvertor = new MessageConvertor();
	
	private MessageConvertorFactory() {
		
	}
	
	public static MessageConvertor getMessageConvertor() {
		return messageConvertor;
	} 
}
