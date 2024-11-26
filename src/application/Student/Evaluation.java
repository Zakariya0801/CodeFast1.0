package application.Student;

public class Evaluation {
	private String EvalID;
	private int score;
	public Evaluation(String evalID, int score) {
		EvalID = evalID;
		this.score = score;
	}
	public String getEvalID() {
		return EvalID;
	}
	public void setEvalID(String evalID) {
		EvalID = evalID;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}	
}
