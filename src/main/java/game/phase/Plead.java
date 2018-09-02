package game.phase;

import game.GameEngine;
import game.chat.protocol.phase.PleadPhaseProtocol;
import game.timer.Timer;
import game.user.User;

// State Pattern - 최후의 변론 Phase
public class Plead implements Phase {
	private Phase nextPhase;
	private GameEngine gameEngine;
	private PleadPhaseProtocol phaseProtocol;
	
	public Plead(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		this.phaseProtocol = new PleadPhaseProtocol();
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
		this.nextPhase = new ExecuteVote(gameEngine);
		Timer timer = new Timer(this.nextPhase, gameEngine);
	}

	@Override
	public int getPhaseInterval() {
		return PhaseInterval.PLEAD_INTERVAL.getValue();
	}
	
	public void setPleadUser(User pleadUser) {
		this.phaseProtocol.setPleadUserId(pleadUser.getUserId());
	}

}
