package application.Student;

public abstract class Plan {
	private int MaxContests;
	private int currentContests;
	private boolean Requested = false;
	public Plan(int max)
	{
		MaxContests=max;
		currentContests=0;
	}
	
	public boolean hasRequested() {
		return Requested;
	}

	public void setRequested(boolean requested) {
		Requested = requested;
	}

	void Particpated() {
		currentContests++;
	}
	public abstract String getStudyLink();
	public abstract boolean isPremium();
}
