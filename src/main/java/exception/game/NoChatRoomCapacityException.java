package exception.game;

import game.user.User;

public class NoChatRoomCapacityException extends RuntimeException {

	// ChatRoom이 풀방이라 접속 못하는 경우
	public NoChatRoomCapacityException(User user) {
		user.sendMessage("ERROR:FULL:");
	}
}
