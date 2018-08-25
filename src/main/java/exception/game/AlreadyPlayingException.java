package exception.game;

import game.user.User;

public class AlreadyPlayingException extends RuntimeException {

	// 이미 게임이 시작돼서 들어가지 못하는 경우
	public AlreadyPlayingException(User user) {
		user.sendMessage("ERROR:PLAYING:");
	}
}
