package game.phase;

import java.util.List;

import game.GameEngine;
import game.chat.protocol.phase.MafiaVotePhaseProtocol;
import game.timer.Timer;
import game.user.User;

// State Pattern - 마피아 투표 Phase
public class MafiaVote implements Phase {
	private Phase nextPhase;
	private GameEngine gameEngine;
	private MafiaVotePhaseProtocol phaseProtocol;

	public MafiaVote(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		this.phaseProtocol = new MafiaVotePhaseProtocol();
		this.phaseProtocol.setPhase(this.getClass().getSimpleName());
	}

	@Override
	public void preExecutePhase() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void startPhase() {
		this.gameEngine.sendProtocol(phaseProtocol);
	}

	@Override
	public void postExecutePhase() {
		this.setNextPhase();
		
		// MafiaVote Phase가 끝나면, 마피아 투표 유효/무효에 관계없이 마피아 투표 결과 초기화
		this.gameEngine.initMafiaVote();
		
		Timer timer = new Timer(this.nextPhase, gameEngine);
	}

	private void setNextPhase() {
		// 생존 인원 수
		int surviverCount = this.gameEngine.getSurviverCount();

		// 투표를 가장 많이 받은 유저 리스트
		List<User> mostVotedUsers = this.gameEngine.getUsersByMostMafiaVoted();

		// 마피아 투표를 한 유저의 수
		int totalMafiaVoteCount = this.gameEngine.getTotalMafiaVoteCount();

		/** 투표가 유효하지 않은 경우 **/
		// 1. 생존 인원이 짝수인 경우 :
		// 		마피아 투표에서 과반수 투표를 안했을때 (8명 이면 5명부터 투표 유효) 무효이므로 Night Phase 이동
		// 2. 생존 인원이 홀수인 경우 :
		// 		마피아 투표에서 과반수 투표를 안했을때 (3명 이면 (3/2)+1명부터 투표 유효) 무효이므로 Night Phase 이동
		// 3. 투표가 동률인 경우
		if (
			( (surviverCount % 2 == 0) && (totalMafiaVoteCount <= (int) (surviverCount / 2)) ) // 
			|| ( (surviverCount % 2 == 1) && (totalMafiaVoteCount < ((int) (surviverCount / 2) + 1)) )
			|| (mostVotedUsers.size() >= 2) 
			) {
			// 투표가 유효하지 않아 Night Phase로 이동
			Night nightPhase = new Night(this.gameEngine); 
			
			// Night 입장에서 어디서 넘어왔는지 알아야 하므로
			// MafiaVote -> Night : 과반수가 마피아 투표를 진행하지 않거나 동률이라서 바로 밤이 되는 경우 (this case)
			// ExecuteVote -> Night : 마피아 투표가 진행되었으나, 과반수가 처형 투표를 하지 않거나 동률이라서 처형없이 밤이 되는 경우
			nightPhase.setValidMafiaVote(false);
			this.nextPhase = nightPhase;
			return;
		}

		/** 투표가 유효한 경우 **/
		// 최후의 반론(Plead) 을 하는 유저를 구함, 투표가 유효한 경우 mostVotedUsers에는 1명의 유저만 존재
		User pleadUser = mostVotedUsers.get(0);
		
		// 다음 Phase는 Plead Phase : 마피아 투표가 유효하고, 지정당한 유저가 최후의 반론을 하는 Phase
		Plead pleadPhase = new Plead(this.gameEngine);
		pleadPhase.setPleadUser(pleadUser);
		this.nextPhase = pleadPhase;

	}

	@Override
	public int getPhaseInterval() {
		return PhaseInterval.MAFIAVOTE_INTERVAL.getValue();
	}



}
