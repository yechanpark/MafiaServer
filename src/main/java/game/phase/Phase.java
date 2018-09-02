package game.phase;

// State Pattern - State
public interface Phase {
	// 이전 Phase에서 수정된 메타 데이터를 유저에 의해 입력된 값을 바탕으로 결과를 계산하여 유저에게 보낼 Protocol을 생성
	// 또는 해당 State 하나에서만 쓰이는 Tag 등의 플래그용도 변수 초기화
	public void preExecutePhase();
	
	// 유저에게 Phase가 변경되었다는 사실과, 이전 페이즈에서 처리하면서 조합된 사항들을 Protocol을 통해 통보
	public void startPhase(); 
	
	// startPhase() 에서 getPhaseInterval() 시간동안 유저에 의해 입력된 값을 바탕으로 게임의 메타 데이터를 수정
	public void postExecutePhase(); 
	
	public int getPhaseInterval(); // Phase의 지속시간 취득
}
