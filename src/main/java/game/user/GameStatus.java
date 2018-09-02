package game.user;

import game.job.jobs.Job;

public class GameStatus {
	private boolean isKillTagged; // 마피아가 살인을 시도했는지 여부
	private boolean isHealTagged; // 의사가 힐을 시도했는지 여부
	private boolean isReportTagged; // 기자가 조사를 시도했는지 여부
	private boolean isLived; // 현재 생존 여부
	private boolean reportable; // 한 판에 기자가 1회 조사를 성공했는지 여부
	
	private int mafiaVotedCount; // 마피아 투표에서 지명당한 횟수
	private boolean executeVoteDeadTagged; // 처형 투표에서 처형이 됐는지 여부
	private boolean executeAgree; // 최후의 반론에서 해당 유저가 찬성했는지 여부 
	
	private Job job; // 할당된 직업
	
	public GameStatus() {
		init();
	}
	
	public void init() {
		this.isKillTagged 
		= this.isHealTagged
		= this.isReportTagged
		= this.executeAgree
		= this.executeVoteDeadTagged
		= false;
		
		this.isLived
		= this.reportable
		= true;
	}
	
	public boolean getIsKillTagged() {
		return isKillTagged;
	}
	
	public void setIsKillTagged(boolean isKillTagged) {
		this.isKillTagged = isKillTagged;
	}
	
	public boolean getIsHealTagged() {
		return isHealTagged;
	}
	
	public void setIsHealTagged(boolean isHealTagged) {
		this.isHealTagged = isHealTagged;
	}
	
	public boolean getIsReportTagged() {
		return isReportTagged;
	}
	
	public void setIsReportTagged(boolean isReportTagged) {
		this.isReportTagged = isReportTagged;
	}

	public void death() {
		this.isLived = false;
	}
	
	public boolean getIsLived() {
		return this.isLived;
	}
	
	public Job getJob() {
		return this.job;
	}
	
	public boolean getReportable() {
		return this.reportable;
	}
	
	public int getMafiaVotedCount() {
		return this.mafiaVotedCount;
	}
	
	public void setMafiaVotedCount(int mafiaVotedCount) {
		this.mafiaVotedCount = mafiaVotedCount;
	}
	
	public boolean getExecuteAgree() {
		return this.executeAgree;
	}
	
	public boolean setExecuteAgree(boolean executeAgree) {
		return this.executeAgree = executeAgree;
	}
	
	public boolean getExecuteVoteTagged() {
		return this.executeVoteDeadTagged;
	}
	
	public void setExecuteVoteTagged(boolean executeVoteDeadTagged) {
		this.executeVoteDeadTagged = executeVoteDeadTagged;
	}
	
}
