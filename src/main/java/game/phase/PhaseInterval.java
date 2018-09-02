package game.phase;

public enum PhaseInterval {
	MORNING_INTERVAL(15)
	, NIGHT_INTERVAL(15)
	, PLEAD_INTERVAL(15)
	, MAFIAVOTE_INTERVAL(15)
	, EXECUTEVOTE_INTERVAL(15)
	, EXECUTE_INTERVAL(15);
	
	private final int value;
	
    private PhaseInterval(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
