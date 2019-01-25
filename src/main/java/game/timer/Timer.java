package game.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import game.GameEngine;
import game.chat.protocol.timer.TimerProtocol;
import game.phase.Phase;

public class Timer implements Runnable {
	private ScheduledExecutorService scheduledExecutorService;
	private Phase phase;
	private GameEngine gameEngine;
	private TimerProtocol timerProtocol;
	
	// 특정 Phase에서 nextPhase를 인자로 넘김
	public Timer(Phase phase, GameEngine gameEngine) {
		this.phase = phase;
		this.gameEngine = gameEngine;
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(this, 0, 1000, TimeUnit.MILLISECONDS);
		timerProtocol = new TimerProtocol(phase.getPhaseInterval());
	}
	
	// 1초마다 실행됨
	@Override
	public void run() {
		// 게임이 진행중이 아닐 경우(끝난 경우) 정지
		if (!gameEngine.getIsPlaying())
			scheduledExecutorService.shutdownNow();
		
		// 시간이 다 된 경우
		if( (timerProtocol.getRemainTime()) <= 0 ) {
			// 현재 Phase 수행
			// 1. preExecute - 이전 Phase에서 수정한 메타 데이터를 바탕으로 유저에게 보낼 메시지를 조합
			phase.preExecutePhase();
			
			// 2. startPhase - preExecute에서 조합된 메시지를 유저에게 보냄 : 이전 Phase에서 수행한 메타 데이터에 대해 클라이언트에 반영
			phase.startPhase();
			
			// 3. postExecute - 시간이 지난 후 다음 Phase에 대한 결정이나, 다음 Phase에서 현재 Phase에서 연산한 값이 필요한 경우 이를 세팅, 다음 Phase에 대한 Timer 수행
			phase.postExecutePhase();
			scheduledExecutorService.shutdownNow();
		}
		
		// 초마다 남은 시간을 보냄
		gameEngine.sendProtocol(timerProtocol.processTime());
		

	}

}
