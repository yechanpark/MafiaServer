package game.phase;

import game.GameEngine;
import game.chat.protocol.phase.NightPhaseProtocol;
import game.timer.Timer;

// State Pattern - 밤 Phase
public class Night implements Phase {
	private Phase nextPhase;
	private GameEngine gameEngine;
	private NightPhaseProtocol phaseProtocol;
	
	public Night(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		this.phaseProtocol = new NightPhaseProtocol();
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
		Timer timer = new Timer(this.nextPhase, gameEngine);
	}

	@Override
	public int getPhaseInterval() {
		return PhaseInterval.NIGHT_INTERVAL.getValue();
	}
	
	public void setValidMafiaVote(boolean isValidMafiaVote) {
		this.phaseProtocol.setValidMafiaVote(isValidMafiaVote);
	}

}
