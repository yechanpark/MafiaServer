package game.phase;

import game.GameEngine;
import game.timer.Timer;
import game.user.User;

// State Pattern - 
public class Morning implements Phase {
	private Phase nextPhase;
	private GameEngine gameEngine;
	
	// 아침이 된 직후 수행, 밤에 일어난 살인/힐 결과를 종합하여 유저들에게 통보
	@Override
	public void executeThisPhase() {
		
		String resultMessage = "MORNING:";
		
		// 마피아가 살인을 시도한 유저를 가져옴
		User user = gameEngine.getKillTaggedUser();
		
		// 해당 유저가 존재하는 경우
		if(user != null) {
			// 의사가 힐을 시도한 유저와 동일한 경우 무효 - 메시지 전송
			if(user.equals(gameEngine.getHealTaggedUser())) {
				resultMessage += "HEAL:" + user.getUserId() + ":";
			}
			
			// 의사가 힐을 시도한 유저가 없는 경우는 마피아의 의한 살인 성립 - 메시지 전송
			else {
				resultMessage += "MDIE:" + user.getUserId() + ":";
				user.death();
			}
			
		}
		
		this.gameEngine.broadCast(resultMessage);
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
		return Interval.MORNING_INTERVAL.getValue();
	}

}
