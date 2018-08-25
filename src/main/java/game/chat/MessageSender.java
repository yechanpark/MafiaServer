package game.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import exception.io.IOStreamCloseFailedException;
import exception.io.IOStreamOpenFailedException;
import exception.io.MessageReadFailedException;

public class MessageSender {
	private InputStream inputStream;
	private OutputStream outputStream;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	private Socket socket;

	public void setSocket(Socket socket) {
		this.socket = socket;
		initIOStream();
	}

	private void initIOStream() {
		try {
			this.inputStream = this.socket.getInputStream();
			this.dataInputStream = new DataInputStream(this.inputStream);
			this.outputStream = this.socket.getOutputStream();
			this.dataOutputStream = new DataOutputStream(this.outputStream);
		} catch (IOException e) {
			throw new IOStreamOpenFailedException();
		}
	}

	public void read(byte[] bytes) {
		try {
			this.dataInputStream.read(bytes, 0, 128);
		} catch (IOException e) {
			throw new MessageReadFailedException();
		}
	}

	public void close() {
		try {
			this.inputStream.close();
			this.outputStream.close();
			this.dataInputStream.close();
			this.dataOutputStream.close();
		} catch (IOException e) {
			throw new IOStreamCloseFailedException();
		}

	}
}
