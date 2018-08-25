package game.phase;

import game.GameEngine;

// State Pattern - State
public interface Phase {
	public void executeThisPhase();
	public void executeNextPhase();
	public void setNextPhase(Phase nextPhase);
	public void setGameEngine(GameEngine gameEngine);
	public int getPhaseInterval();
}
