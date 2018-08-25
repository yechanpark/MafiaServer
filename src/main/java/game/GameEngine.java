package game;

import game.chat.ChatRoom;
import game.user.User;

public class GameEngine {
	private ChatRoom chatRoom;
	
	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}
	
	public User getKillTaggedUser() {
		for(User user : chatRoom.getUserList()) {
			if(user.getGameStatus().getIsKillTagged())
				return user;
		}
		return null;
	}
	
	public User getHealTaggedUser() {
		for(User user : chatRoom.getUserList()) {
			if(user.getGameStatus().getIsHealTagged())
				return user;
		}
		return null;
	}
	
	public void broadCast(String broadCastMessage) {
		this.chatRoom.broadCast(broadCastMessage);
	}
	
	public boolean getIsPlaying() {
		return this.chatRoom.getIsPlaying();
	}

}
