package game.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import game.GameEngine;
import game.phase.Phase;

public class Timer implements Runnable {
	private ScheduledExecutorService scheduledExecutorService;
	private Phase phase;
	private GameEngine gameEngine;
	
	private int interval;
	
	// 특정 Phase에서 nextPhase를 인자로 넘김
	public Timer(Phase phase, GameEngine gameEngine) {
		this.phase = phase;
		this.interval = phase.getPhaseInterval();
		this.gameEngine = gameEngine;
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(this, 0, 1000, TimeUnit.MILLISECONDS);
	}
	
	// 1초마다 실행됨
	@Override
	public void run() {
		// 게임이 진행중이 아닐 경우(끝난 경우) 정지
		if (!gameEngine.getIsPlaying())
			scheduledExecutorService.shutdownNow();
		
		// 시간이 다 된 경우
		if( (this.interval--) <= 0 ) {
			// 현재 Phase 수행
			// 1. preExecute - 이전 Phase에서 수정한 메타 데이터를 바탕으로 유저에게 보낼 메시지를 조합
			phase.preExecutePhase();
			
			// 2. startPhase - preExecute에서 조합된 메시지를 유저에게 보냄 : 이전 Phase에서 수행한 메타 데이터에 대해 클라이언트에 반영
			phase.startPhase();
			
			// 3. postExecute - 시간이 지난 후 다음 Phase에 대한 결정이나, 다음 Phase에서 현재 Phase에서 연산한 값이 필요한 경우 이를 세팅, 다음 Phase에 대한 Timer 수행
			phase.postExecutePhase();
			scheduledExecutorService.shutdownNow();
		}
		

	}

}
