package game.phase;

import game.GameEngine;
import game.chat.protocol.phase.MorningPhaseProtocol;
import game.job.jobs.Reporter;
import game.job.jobtype.JobType;
import game.timer.Timer;
import game.user.User;

// State Pattern - 아침 Phase
public class Morning implements Phase {
	private Phase nextPhase; // 다음 Phase State
	private GameEngine gameEngine;
	private MorningPhaseProtocol phaseProtocol;
	
	public Morning(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		this.phaseProtocol= new MorningPhaseProtocol();
		this.phaseProtocol.setPhase(this.getClass().getSimpleName());
	}

	// Morning Phase 시작 전에 Night Phase에서 수행했던 로직을 종합해서 초기화 및 게임 종료 여부 확인 
	@Override
	public void preExecutePhase() {
		/** 우선순위 1. 마피아 VS 의사 **/
		this.mafiaAndDoctorLogic();

		/** 우선순위 2. 기자 **/
		this.reporterLogic();
		
		/** 게임 메타 데이터 초기화 **/
		// 마피아 vs 의사 Tag관련 로직 수행 후 모든 유저 heal,kill 태그 초기화
		this.gameEngine.initHealTag();
		this.gameEngine.initKillTag();
		
		JobType winnerGroup = this.gameEngine.checkGameOver();
		if(winnerGroup != null) {
			phaseProtocol.setWinnerGroup(winnerGroup.toString());
		}
			
	}
	
	// 아침이 된 직후 수행, 밤에 일어난 살인/힐 및 기자 조사결과를 종합하여 유저들에게 통보
	@Override
	public void startPhase() {
		this.gameEngine.sendProtocol(phaseProtocol);
	}

	// // 아침 로직 후처리
	@Override
	public void postExecutePhase() {
		// 아침 -> 마피아 투표 Phase 전환
		this.nextPhase = new MafiaVote(gameEngine);
		Timer timer = new Timer(this.nextPhase, gameEngine);
	}

	@Override
	public int getPhaseInterval() {
		return PhaseInterval.MORNING_INTERVAL.getValue();
	}
	
	private void mafiaAndDoctorLogic() {
		// 마피아가 살인을 시도한 유저
		User killTaggeduser = gameEngine.getKillTaggedUser();

		// 존재하지 않는 경우 패스
		if (killTaggeduser == null)
			return;

		phaseProtocol.setKillTaggeduserId(killTaggeduser.getUserId());
		
		// 의사가 힐을 시도한 유저와 동일한 경우 무효 - 의사가 특정 유저를 살렸다는 메시지 전송
		if (killTaggeduser.equals(gameEngine.getHealTaggedUser()))
			phaseProtocol.setHealed(true);
		
		// 의사가 힐을 시도한 유저가 아닌 경우는 마피아의 의한 살인 성립
		else
			this.gameEngine.killUser(killTaggeduser);

	}

	private void reporterLogic() {

		// Job이 Reporter인 유저를 가지고 옴
		User reporterUser = gameEngine.getUserByJob(Reporter.class);

		// Reporter가 Job인 User가 없으면 정지
		if (reporterUser == null)
			return;

		// Reporter가 살아있지 않으면 정지
		if (!reporterUser.isAlive())
			return;

		// 이미 1회 조사를 했으면 더 이상 수행할 수 없으므로 정지
		if (!reporterUser.getGameStatus().getReportable())
			return;

		User reportTaggedUser = gameEngine.getReportTaggedUser();

		// Reporter가 조사를 시도하지 않은 경우
		if (reportTaggedUser == null)
			return;
			
		// Reporter가 조사를 시도해서 태그를 남긴 경우 Id, JobName 세팅
		phaseProtocol.setReportTaggedUserId(reportTaggedUser.getUserId());
		phaseProtocol.setReportTaggedUserJobName(
				reportTaggedUser.getGameStatus().getJob().getJobName()
		);
		
		// 게임에서 1회만 조사 가능
		reportTaggedUser.getGameStatus().setIsReportTagged(false);

	}
	
}
