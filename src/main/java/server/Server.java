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
	
	public void start() {
		System.out.println("서버 시작 ㅎ");
	}
	
}
