package game.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import game.phase.Phase;

public class Timer implements Runnable {
	private int interval;
	private ScheduledExecutorService scheduledExecutorService;
	private Phase phase;
	
	public Timer(int interval) {
		this.interval = interval;
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(this, 0, 1000, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public void run() {
		if( (this.interval--) <= 0 ) {
			scheduledExecutorService.shutdownNow();
			phase.executeNextPhase();
		}
	}

}
