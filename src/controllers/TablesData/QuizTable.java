package controllers.TablesData;

public class QuizTable {
public String QuizId;
public String Cname;
public String StudentID;
public String Score;
public String Total;
public QuizTable(String quizId, String cname, String studentID, String score, String total) {
	super();
	QuizId = quizId;
	Cname = cname;
	StudentID = studentID;
	Score = score;
	Total = total;
}
public String getQuizId() {
	return QuizId;
}
public void setQuizId(String quizId) {
	QuizId = quizId;
}
public String getCname() {
	return Cname;
}
public void setCname(String cname) {
	Cname = cname;
}
public String getStudentID() {
	return StudentID;
}
public void setStudentID(String studentID) {
	StudentID = studentID;
}
public String getScore() {
	return Score;
}
public void setScore(String score) {
	Score = score;
}
public String getTotal() {
	return Total;
}
public void setTotal(String total) {
	Total = total;
}


}
