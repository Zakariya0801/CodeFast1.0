package controllers.TablesData;

public class LeaderboardData {
	public String Position;
	public String Student_id;
	public String Student_name;
	public String Email;
	public String CGPA;
	public String Performance;
	public LeaderboardData(String position, String student_id, String student_name, String email, String cGPA,
			String performance) {
		Position = position;
		Student_id = student_id;
		Student_name = student_name;
		Email = email;
		CGPA = cGPA;
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
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getCGPA() {
		return CGPA;
	}
	public void setCGPA(String cGPA) {
		CGPA = cGPA;
	}
	public String getPerformance() {
		return Performance;
	}
	public void setPerformance(String performance) {
		Performance = performance;
	}
	
}
