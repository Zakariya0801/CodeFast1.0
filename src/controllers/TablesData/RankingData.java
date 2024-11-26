package controllers.TablesData;

public class RankingData {
	public String Position;
	public String Student_id;
	public String Student_name;
	public String Contest_score;
	public String Percentage;
	public String Performance;
	public RankingData(String position, String student_id, String student_name, String contest_score, String percentage, String performance) {
		Position = position;
		Student_id = student_id;
		Student_name = student_name;
		Contest_score = contest_score;
		Percentage = percentage;
		Performance = performance;
	}
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		Position = position;
	}
	public String getStudent_id() {
		return Student_id;
	}
	public void setStudent_id(String student_id) {
		Student_id = student_id;
	}
	public String getStudent_name() {
		return Student_name;
	}
	public void setStudent_name(String student_name) {
		Student_name = student_name;
	}
	public String getContest_score() {
		return Contest_score;
	}
	public void setContest_score(String contest_score) {
		Contest_score = contest_score;
	}
	public String getPercentage() {
		return Percentage;
	}
	public void setPercentage(String percentage) {
		Percentage = percentage;
	}
	public String getPerformance() {
		return Performance;
	}
	public void setPerformance(String performance) {
		Performance = performance;
	}
	
	
}
