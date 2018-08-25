package game.chat;

import java.util.ArrayList;
import java.util.List;

import exception.game.AlreadyLoginException;
import exception.game.AlreadyPlayingException;
import exception.game.IdPasswordNotMatchException;
import exception.game.NoChatRoomCapacityException;
import game.user.User;

public class ChatRoom {
	private final int FULL_CAPACITY = 8;
	private List<User> userList;
	private boolean isPlaying;

	public ChatRoom() {
		this.userList = new ArrayList<User>(8);
	}
	
	public List<User> getUserList() {
		return userList;
	}
	
	// 유저가 게임방에 입장
	public void joinUser(User user) {
		String userId = user.getUserId();
		String userPassword = user.getUserPassword();

		// 1. user id와 pw가 매치되는지 확인
		// TODO : DB랑 커넥션 맺어서 id, pw 확인
		if (!userId.equals(userPassword))
			throw new IdPasswordNotMatchException(user);

		// 2. 플레이중이면 접속 불가
		if (this.getIsPlaying())
			throw new AlreadyPlayingException(user);

		// 3. 이미 접속한 아이디면 접속 불가
		for (User listedUser : userList) {
			if (listedUser.getUserId().equals(user.getUserId()))
				throw new AlreadyLoginException(user);
		}

		// 4. 풀방이면 접속 불가
		if (userList.size() >= FULL_CAPACITY)
			throw new NoChatRoomCapacityException(user);

		// 유저 스레드 실행하여 통신 시작
		user.start();

		// 기존 유저 수와 id 리스트를 작성 "ULIST:1:u1" 또는 "ULIST:0"
		String messageForJoinUser = "ULIST:" + Integer.toString(userList.size()) + ":";
		for (User listedUser : userList)
			messageForJoinUser += listedUser.getUserId() + ":";
		user.sendMessage(messageForJoinUser);

		userList.add(user);
	}
	
	public void broadCast(String braodCastMeassage) {
		for(User listedUser : this.userList)
			listedUser.sendMessage(braodCastMeassage);
	}
	
	public boolean getIsPlaying() {
		return this.isPlaying;
	}

	public void setIsPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

}
