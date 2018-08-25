package game.phase;

public enum Interval {
	MORNING_INTERVAL(15)
	, NIGHT_INTERVAL(15)
	, PLEAD_INTERVAL(15)
	, VOTE_INTERVAL(15);
	
	private final int value;
	
    private Interval(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
