package exception.game;

import game.user.User;

public class AlreadyLoginException extends RuntimeException {

	public AlreadyLoginException(User user) {
		// 이미 접속중인 ID인 경우
		user.sendMessage("USINGID:");
	}
}
