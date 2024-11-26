package application.Student.Contest;

public class ContestDescription {
	private String contestID;
	private Problem questions;
	private String Name;
	private String startTime;
	private String endTime;
	private int TotalMarks;
	private boolean isLive;
	public ContestDescription(String contestID, Problem questions, String name, String startTime, String endTime,
			int totalMarks, boolean isLive) {
		this.contestID = contestID;
		this.questions = questions;
		Name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		TotalMarks = totalMarks;
		this.isLive = isLive;
	}
	
	public String getContestID() {
		return contestID;
	}
	public void setContestID(String contestID) {
		this.contestID = contestID;
	}
	public Problem getQuestions() {
		return questions;
	}
	public void setQuestions(Problem questions) {
		this.questions = questions;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getTotalMarks() {
		return TotalMarks;
	}
	public void setTotalMarks(int totalMarks) {
		TotalMarks = totalMarks;
	}
	public boolean isLive() {
		return isLive;
	}
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
	
}
