package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import game.chat.ChatRoom;
import game.chat.protocol.Protocol;
import game.job.jobs.Civil;
import game.job.jobs.Doctor;
import game.job.jobs.Job;
import game.job.jobs.Mafia;
import game.job.jobs.Police;
import game.job.jobs.Politician;
import game.job.jobs.Reporter;
import game.job.jobs.Spy;
import game.job.jobtype.JobType;
import game.user.User;

public class GameEngine {
	private ChatRoom chatRoom;

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}

	// 마피아가 killTagged한 유저를 구함
	public User getKillTaggedUser() {
		for (User user : chatRoom.getUserList()) {
			if (user.getGameStatus().getIsKillTagged())
				return user;
		}
		return null;
	}

	// 의사가 healTagged한 유저를 구함
	public User getHealTaggedUser() {
		User healTaggedUser = null;
		
		for (User user : chatRoom.getUserList()) {
			if (user.getGameStatus().getIsHealTagged())
				healTaggedUser = user;
		}
		
		return healTaggedUser;
	}

	// 현재 chatRoom에서 게임이 진행중인지 체크
	public boolean getIsPlaying() {
		return this.chatRoom.getIsPlaying();
	}

	// 의사가 한 healTag를 초기화
	public void initHealTag() {
		for (User user : this.chatRoom.getUserList())
			user.getGameStatus().setIsHealTagged(false);
	}

	// 마피아가 한 killTag를 초기화
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

	// 리포터가 태그한 유저를 구해서 리턴
	public User getReportTaggedUser() {
		User reportTaggedUser = null;
		
		for (User user : this.chatRoom.getUserList()) {
			if (user.getGameStatus().getIsReportTagged())
				reportTaggedUser = user;
		}
		
		return reportTaggedUser;
	}

	// 게임이 끝났는지 체크, 마피아 혹은 시민의 승리 여부를 결정
	// MAFIA 리턴 시 MAFIA 승리
	// CIVIL 리턴 시 CIVIL 승리
	// null 리턴 시 게임 종료되지 않음
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

	// 생존한 사람의 숫자를 구함
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

	// chatRoom에 있는 생존 유저들 중, 최근 ExecuteVote Phase에서 처형당한 유저를 리턴
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

	// 인자로 넘어온 Protocol을 json포맷으로 만들어서 모든 유저에게 전송
	public void sendProtocol(Protocol protocol) {
		String phaseProtocolMessage = protocol.toJSONString();
		// TODO : phaseProtocolMessage를 모든 유저에게 보내는 로직
	}

	// killTagged된 유저를 죽은 상태로 전환하도록 처리
	public void killUser(User killTaggedUser) {
		killTaggedUser.getGameStatus().death();
	}
	
	
	// 선정된 직업들을 각 User에게 세팅
	public void allocateJob() {
		List<Job> pickedJobList = getPickedJobList();
		
		// 랜덤으로 섞음
		Collections.shuffle(pickedJobList);
		
		Iterator<Job> iterator = pickedJobList.iterator();
		for(User user : chatRoom.getUserList())
			user.getGameStatus().setJob(iterator.next());
		
	}
	
	// 인원수에 따라 사용될 직업들의 리스트를 선정하여 리턴
	private List<Job> getPickedJobList() {

		List<Job> pickedJobList = new ArrayList<Job>();
		
		// 기본 직업 세팅
		pickedJobList.addAll(this.getDefaultJobList());
		
		// 4명 이상인 경우 시민 직업 추가
		if(chatRoom.getUserList().size() >= 4)
			pickedJobList.add
		// TODO : 인원수에 따라 마피아, 시민 밸런스 맞춰서 직업 할당
		
		return pickedJobList;
		
	}
	
	// 필수 마피아 직업(마피아)를 제외한 마피아팀 직업 리스트 반환
	private List<Job> getDefaultJobList() {
		List<Job> defaultJobList = new ArrayList<Job>();
		
		// 기본 직업 3개 세팅
		defaultJobList.add(new Mafia());
		defaultJobList.add(new Police());
		defaultJobList.add(new Doctor());
		
		return defaultJobList;
	}
	
	// 필수 시민 직업(의사, 경찰)을 제외한 시민팀 직업 리스트 반환
	private List<Job> getCivilTeamJobList() {
		List<Job> civilTeamJobList = new ArrayList<Job>();
		
		civilTeamJobList.add(new Civil());
		civilTeamJobList.add(new Politician());
		civilTeamJobList.add(new Reporter());
		
		return civilTeamJobList;
	}
	
	// 필수 마피아 직업(마피아)를 제외한 마피아팀 직업 리스트 반환
	private List<Job> getMafiaTeamJobList() {
		List<Job> mafiaTeamJobList = new ArrayList<Job>();
		
		mafiaTeamJobList.add(new Spy());
		
		return mafiaTeamJobList;
	}
	

	

}
