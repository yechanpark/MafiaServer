package server;

public class Server {
	ServerFrame serverFrame;
	int port;
	
	public Server() {
		this.port = 30000;
	}
	
	public static void main(String args[]) {
		Server server = new Server();
		server.boot();
	}
	
	public void boot() {
		this.initGraphic();
	}
	
	private void initGraphic() {
		this.serverFrame = new ServerFrame(this, this.port);
		serverFrame
			.init()
			.setVisible(true);
	}
	
	// ServerFrame에서 Start버튼 클릭 시 시작
	public void start() {
		UserConnector userConnector = new UserConnector();
	}
	
}
