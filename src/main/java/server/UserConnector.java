package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import exception.network.UserConnectionFailedException;
import game.chat.ChatRoom;
import game.user.User;

public class UserConnector implements Runnable {
	private ChatRoom chatRoom;
	private int port;

	public UserConnector(int port) {
		this.port = port;
	}

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}

	public void run() {
		while (true) {
			try {
				ServerSocket serverSocket = new ServerSocket(this.port);
				
				// 사용자가 요청할 때 까지 기다림
				Socket socket = serverSocket.accept();
		
				User user = new User();
				user.setSocket(socket);
				user.connect();

				this.chatRoom.joinUser(user);

			} catch (IOException e) {
				throw new UserConnectionFailedException();
			}
		}
	}

}
