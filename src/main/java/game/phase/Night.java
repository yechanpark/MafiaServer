package game.phase;

import game.GameEngine;
import game.timer.Timer;

// State Pattern -
public class Night implements Phase {
	private Phase nextPhase;
	private GameEngine gameEngine;
	
	@Override
	public void executeThisPhase() {
		String resultMessage = "NIGHT:";
		this.gameEngine.broadCast(resultMessage);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void executeNextPhase() {
		Timer timer = new Timer(nextPhase.getPhaseInterval());
	}

	@Override
	public void setNextPhase(Phase nextPhase) {
		this.nextPhase = nextPhase;
	}

	@Override
	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	@Override
	public int getPhaseInterval() {
		return Interval.NIGHT_INTERVAL.getValue();
	}

}
