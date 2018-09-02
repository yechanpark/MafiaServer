package game;

import java.util.ArrayList;
import java.util.List;

import game.chat.ChatRoom;
import game.chat.protocol.Protocol;
import game.job.jobs.Mafia;
import game.job.jobtype.JobType;
import game.user.User;

public class GameEngine {
	private ChatRoom chatRoom;

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}

	public User getKillTaggedUser() {
		for (User user : chatRoom.getUserList()) {
			if (user.getGameStatus().getIsKillTagged())
				return user;
		}
		return null;
	}

	public User getHealTaggedUser() {
		for (User user : chatRoom.getUserList()) {
			if (user.getGameStatus().getIsHealTagged())
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

	public void initHealTag() {
		for (User user : this.chatRoom.getUserList())
			user.getGameStatus().setIsHealTagged(false);
	}

	public void initKillTag() {
		for (User user : this.chatRoom.getUserList()) {
			user.getGameStatus().setIsKillTagged(false);
		}
	}

	// 클래스 정보를 넘기면 해당 클래스를 Job으로 가지고 있는 유저를 반환
	public User getUserByJob(Class<?> classType) {
		for (User user : this.chatRoom.getUserList()) {
			if (user.getGameStatus().getJob().getClass() == classType) {
				return user;
			}
		}
		return null;
	}

	public User getReportTaggedUser() {
		for (User user : this.chatRoom.getUserList()) {
			if (user.getGameStatus().getIsReportTagged())
				return user;
		}
		return null;
	}

	public JobType checkGameOver() {

		// Mafia가 Job이면서 살아있는 유저 수
		int mafiaUserCount = 0;

		// 살아있는 시민 팀 유저 수
		int civilTeamUserCount = 0;

		for (User user : this.chatRoom.getUserList()) {
			if (user.getGameStatus().getJob().getClass() == Mafia.class) {
				if (!user.isAlive())
					continue;
				mafiaUserCount++;
			}

			if (user.getGameStatus().getJob().getJobType() == JobType.CIVIL) {
				if (!user.isAlive())
					continue;
				civilTeamUserCount++;
			}

		}

		// 마피아 승리
		if (mafiaUserCount >= civilTeamUserCount) {
			this.chatRoom.setIsPlaying(false);
			return JobType.MAFIA;
		}

		// 시민 승리
		else if (mafiaUserCount == 0) {
			this.chatRoom.setIsPlaying(false);
			return JobType.CIVIL;
		}
		
		return null;

	}

	public int getSurviverCount() {
		int surviverCount = 0;

		for (User user : this.chatRoom.getUserList()) {
			if (user.isAlive())
				surviverCount++;
		}

		return surviverCount;
	}

	// Vote Phase에서 가장 마피아라는 투표를 많이 받은 유저 리스트를 구함
	public List<User> getUsersByMostMafiaVoted() {
		List<User> mostVotedUserList = new ArrayList<User>();
		int mostVotedCount = 0;

		// 모든 유저를 돌면서, 마피아 투표에서 가장 많은 투표수를 받은 유저의 투표수를 구함
		for (User user : this.chatRoom.getUserList()) {
			if (user.getGameStatus().getMafiaVotedCount() > mostVotedCount)
				mostVotedCount = user.getGameStatus().getMafiaVotedCount();
		}

		// 가장 많은 투표수를 가진 유저를 구해서 리스트에 담아 리턴
		for (User user : this.chatRoom.getUserList()) {
			if (user.getGameStatus().getMafiaVotedCount() == mostVotedCount)
				mostVotedUserList.add(user);
		}

		return mostVotedUserList;
	}

	// 마피아 투표를 한 사람의 수 (각 유저가 받은 투표수의 총합)
	public int getTotalMafiaVoteCount() {
		int totalMafiaVoteCount = 0;

		for (User user : this.chatRoom.getUserList()) {
			totalMafiaVoteCount += user.getGameStatus().getMafiaVotedCount();
		}

		return totalMafiaVoteCount;
	}

	// 모든 유저에 대해 마피아 투표 결과 초기화
	public void initMafiaVote() {
		for (User user : this.chatRoom.getUserList()) {
			user.getGameStatus().setMafiaVotedCount(0);
		}
	}

	public User getDeadPleadUser() {
		User deadPleadUser = null;

		for (User user : this.chatRoom.getUserList()) {
			if (user.getGameStatus().getIsLived()) {
				if (user.getGameStatus().getExecuteVoteTagged())
					deadPleadUser = user;
			}
		}

		return deadPleadUser;

	}

	public void sendProtocol(Protocol protocol) {
		String phaseProtocolMessage = protocol.toJSONString();
	}

	public void killUser(User killTaggedUser) {
		killTaggedUser.getGameStatus().death();
	}

}
