package game.user;

public class GameStatus {
	private boolean isKillTagged; // 마피아가 살인을 시도했는지 여부
	private boolean isHealTagged; // 의사가 힐을 시도했는지 여부
	private boolean isLived; // 현재 생존 여부
	public GameStatus() {
		init();
	}
	
	public void init() {
		this.isKillTagged 
			= this.isHealTagged
			= false;
		
		this.isLived
			= true;
	}
	
	public boolean getIsKillTagged() {
		return isKillTagged;
	}
	
	public boolean getIsHealTagged() {
		return isHealTagged;
	}
	
	public void setIsKillTagged() {
		this.isKillTagged = true;
	}
	
	public void setIsHealTagged() {
		this.isHealTagged = true;
	}

	public void death() {
		this.isLived = false;
	}
	
}
