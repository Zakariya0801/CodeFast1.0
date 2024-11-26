package application.Student.Course;

public class MCQ {
	private int QNumber;
	private String Statement;
	private String Options;
	private String CorrectOption;
	
	public MCQ(int qNumber, String statement, String options, String correctOption) {
		super();
		QNumber = qNumber;
		Statement = statement;
		Options = options;
		CorrectOption = correctOption;
	}
	
	public int getQNumber() {
		return QNumber;
	}

	public void setQNumber(int qNumber) {
		QNumber = qNumber;
	}

	public String getStatement() {
		return Statement;
	}
	public void setStatement(String statement) {
		Statement = statement;
	}
	public String getOptions() {
		return Options;
	}
	public void setOptions(String options) {
		Options = options;
	}
	public String getCorrectOption() {
		return CorrectOption;
	}
	public void setCorrectOption(String correctOption) {
		CorrectOption = correctOption;
	}
	
}
