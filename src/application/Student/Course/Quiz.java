package application.Student.Course;
import java.io.File;
import java.util.List;

public class Quiz {
	private String QuizID;
	private List<MCQ> mcqs;
	public Quiz(String quizID, List<MCQ> mcqs) {
		super();
		QuizID = quizID;
		this.mcqs = mcqs;
	}
	public String getQuizID() {
		return QuizID;
	}
	public void setQuizID(String quizID) {
		QuizID = quizID;
	}
	public List<MCQ> getMcqs() {
		return mcqs;
	}
	public void setMcqs(List<MCQ> mcqs) {
		this.mcqs = mcqs;
	}
	
	
}
