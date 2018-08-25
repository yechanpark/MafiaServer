package exception.game;

import game.user.User;

public class IdPasswordNotMatchException extends RuntimeException {

	// 아이디 혹은 비밀번호가 매치되지 않아 로그인 불가
	public IdPasswordNotMatchException(User user) {
		user.sendMessage("WRONGID:");
		user.disconnect();
	}

}
