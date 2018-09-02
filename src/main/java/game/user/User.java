package game.user;

import java.net.Socket;

import game.chat.protocol.MessageConvertor;
import game.chat.protocol.MessageConvertorFactory;
import game.chat.protocol.MessageSender;

public class User extends Thread {
	private final int MAX_MESSAGE_LENGTH = 128;
	private String id;
	private String password;

	private MessageConvertor messageConvertor;
	private MessageSender messageSender;
	private GameStatus gameStatus;
	

	public User() {
		this.gameStatus = new GameStatus();
		this.messageConvertor = MessageConvertorFactory.getMessageConvertor();
	}
	
	public String getUserId() {
		return this.id;
	}

	public String getUserPassword() {
		return this.password;
	}
	
	public GameStatus getGameStatus() {
		return this.gameStatus;
	}

	public void setSocket(Socket socket) {
		this.messageSender = new MessageSender();
		this.messageSender.setSocket(socket);
	}

	private String read() {
		byte[] bytes = new byte[MAX_MESSAGE_LENGTH];
		this.messageSender.read(bytes);
		return new String(bytes);
	}

	public void connect() {
		String[] parsedMessage = this.messageConvertor.parseMessage(this.read());
		this.id = parsedMessage[0].trim();
		this.password = parsedMessage[1].trim();
	}

	public void disconnect() {
		this.messageSender.close();
	}

	public void sendMessage(String sendMessage) {
		byte[] realMessage = sendMessage.getBytes();
		byte[] resultMessage = new byte[MAX_MESSAGE_LENGTH];

		for (int i = 0; i < MAX_MESSAGE_LENGTH; i++)
			resultMessage[i] = 'X';

		for (int i = 0; i < realMessage.length; i++)
			resultMessage[i] = realMessage[i];
	}
	
	public void run() {
		while (true) {

		}
	}
}
