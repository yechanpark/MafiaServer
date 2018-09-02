package game.phase;

import game.GameEngine;
import game.chat.protocol.phase.ExecuteVotePhaseProtocol;
import game.timer.Timer;
import game.user.User;

// State Pattern - 마피아 투표 결과에 따른 처형투표 Phase
public class ExecuteVote implements Phase {
	private Phase nextPhase;
	private GameEngine gameEngine;
	private ExecuteVotePhaseProtocol phaseProtocol;
	
	public ExecuteVote(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		this.phaseProtocol = new ExecuteVotePhaseProtocol();
		this.phaseProtocol.setPhase(this.getClass().getSimpleName()); // VOTE2:
	}
	
	@Override
	public void preExecutePhase() {
		User deadPleadUser = this.gameEngine.getDeadPleadUser();
		this.phaseProtocol.setDeadPleadUser(deadPleadUser.getUserId());
	}
	
	@Override
	public void startPhase() {
		this.gameEngine.sendProtocol(this.phaseProtocol);
	}
	
	@Override
	public void postExecutePhase() {
		this.nextPhase = new Night(this.gameEngine);
		Timer timer = new Timer(this.nextPhase, this.gameEngine);
	}

	@Override
	public int getPhaseInterval() {
		return PhaseInterval.EXECUTEVOTE_INTERVAL.getValue();
	}



}
