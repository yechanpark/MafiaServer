package game.chat;

public class MessageConvertorFactory {
	private static MessageConvertor messageConvertor;
	
	private MessageConvertorFactory() {
		
	}
	
	public static MessageConvertor getMessageConvertor() {
		if(messageConvertor == null)
			return new MessageConvertor();
		return messageConvertor;
	} 
}
