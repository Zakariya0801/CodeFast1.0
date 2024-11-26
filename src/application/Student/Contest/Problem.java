package application.Student.Contest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.PersistantFactory;
import databasepackage.DBHandler;
import databasepackage.MySqlHandler;

public class Problem {
	private static int IDCount = 0;
	private String ProblemID;
	private String Statement;
	private String TestCaseInput;
	private String TestCaseOutput;
	private int timeLimit;
	private int MemoryLimit;
	public static void setCount(int c) {
		IDCount = c;
	}
	
	public Problem(String problemID, String statement, String testCaseInput, String testCaseOutput, int timeLimit,
			int memoryLimit) {
		super();
		ProblemID = problemID;
		Statement = statement;
		TestCaseInput = testCaseInput;
		TestCaseOutput = testCaseOutput;
		this.timeLimit = timeLimit;
		MemoryLimit = memoryLimit;
	}
	
	public Problem(String contestID, String statement, String input, String output) throws SQLException {
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
    	List<String> values = new ArrayList<>();
    	List<String> types = new ArrayList<>();
    	values.add("P_" + Integer.toString(IDCount));types.add("string");
    	IDCount++;
    	values.add(contestID);types.add("string");
    	values.add(statement);types.add("string");
    	values.add(input);types.add("string");
    	values.add(output);types.add("string");
    	handle.ExecuteUpdate("INSERT INTO Problem (P_ID, Contest_ID, Statement, TestCaseInput, TestCaseOutput, TimeLimit, MemoryLimit)\r\n"
    			+ "VALUES (?, ?,?, ?, ? , 1, 256);", values, types);
    	handle.CloseConnection();
	}

	public String getProblemID() {
		return ProblemID;
	}

	public void setProblemID(String problemID) {
		ProblemID = problemID;
	}

	public String getStatement() {
		return Statement;
	}

	public void setStatement(String statement) {
		Statement = statement;
	}

	public String getTestCaseInput() {
		return TestCaseInput;
	}

	public void setTestCaseInput(String testCaseInput) {
		TestCaseInput = testCaseInput;
	}

	public String getTestCaseOutput() {
		return TestCaseOutput;
	}

	public void setTestCaseOutput(String testCaseOutput) {
		TestCaseOutput = testCaseOutput;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public int getMemoryLimit() {
		return MemoryLimit;
	}

	public void setMemoryLimit(int memoryLimit) {
		MemoryLimit = memoryLimit;
	}
	
	
}
