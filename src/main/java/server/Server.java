package server;

import game.chat.ChatRoom;

public class Server {
	private ServerFrame serverFrame;
	private	ChatRoom chatRoom;
	private	int port;
	
	public Server() {
		this.port = 30000;
	}
	
	public static void main(String args[]) {
		Server server = new Server();
		server.boot();
	}
	
	public void boot() {
		this.serverFrame = new ServerFrame(this);
		serverFrame
			.init()
			.setVisible(true);
	}
	
	public int getPort() {
		return this.port;
	}
	
	// ServerFrame에서 Start버튼 클릭 시 시작
	public void startReceive() {
		UserConnector userConnector = new UserConnector(this.port);
		userConnector.setChatRoom(new ChatRoom());
		userConnector.run();
	}
	
}
