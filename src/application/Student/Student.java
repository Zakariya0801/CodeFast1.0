package application.Student;

import databasepackage.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.InformationExpert;
import application.PersistantFactory;
import application.User;
import application.UserInformation;
import application.Industry.IndustryInformation;
import application.Student.Contest.ContestDescription;
import application.Student.Course.MCQ;
import application.Student.Course.Quiz;
import controllers.AlertSender;
import controllers.TablesData.JobTableData;
import controllers.TablesData.StudentData;

public class Student extends User {
	private static int IDCount = 1;
	public Student(String name, String email, String pass, String degree, float cgpa) throws SQLException {
		super("ST_" + String.valueOf(IDCount));
		IDCount++;
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		handle.ExecuteCreate("Students", ID, new StudentData(name,email,pass,degree,cgpa));
	}
	public static void setCount(int count) {
		IDCount = count;
	}
	public Student(String stID) {
		ID = stID;
	}
	public float UpdateSetPerformance(float performance, int score, int total)
	{
		System.out.println("Score = " + score);
		System.out.println("total = " + total);
		System.out.println("Score = " + score);
		if(performance==0.0)
			return (float)(score/(total*1.0))*5;
		return (performance + (float)((score/(total*1.0)*5)))/(float)2.0;
	}
	public StudentInformation getInformation() {
		try {
			return InformationExpert.getInstance().getStudentInformation(ID);
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String getTableName() { return "Students";}
	public boolean openContest(String ContestID) throws IOException, SQLException {
		if(this.getInformation().getEvaluations().containsKey(ContestID)) {
			AlertSender.SendInformation("Already Pariticipated", "You have already participated in the contest").showAndWait();
			return false;
		}
		return true;
	}
	public void SendRequestPremium(ActionEvent e, UserInformation user) throws IOException, URISyntaxException, SQLException {
		if(this.getInformation().getPlan().hasRequested()) {
			AlertSender.SendInformation( "Request Pending", "Your Application is Pending Approval").showAndWait();
			return;
		}
		
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(user.ID); types.add("string");
		handle.ExecuteUpdate("Update Students "
				+ "SET SubscribedPlan = 0 "
				+ "WHERE Student_Id = ?;",values,types);
		user.getPlan().setRequested(true);
		AlertSender.SendInformation( "Request Sent", "Request sent Sucessfully to the Admin for Approval").showAndWait();
			
	}
	public List<String> setFeedbackText(UserInformation user) {
		float performance = user.getPerformance();
		String str1,str2;
		if (performance >= 4.0 && performance <= 5.0) {
	        str1 = "Excellent work! Your dedication and mastery of the material are evident, and you have consistently demonstrated.";
	       str2 = "exceptional understanding. Keep up the outstanding effort and continue to challenge yourself.";
	    }
	    else if (performance >= 3.0 && performance < 4.0) {
	        str1 = "Good job! Your efforts are paying off, and you have shown a solid understanding of the material.";
	        str2= "With continued focus and dedication, you can achieve even higher standards."; 
	    }
	    else if (performance >= 2.0 && performance < 3.0) {
	       str1 = "You’re on the right track and making progress. Consider engaging in additional exercises or discussions";
	       str2= "to strengthen your understanding further. Keep pushing forward!"; 
	    }
	    else if (performance >= 1.0 && performance < 2.0) {
	        str1 = "Your effort is appreciated, though there is room for improvement. With consistent practice";
	        str2="and a focused approach, you can enhance your performance significantly. Stay determined!"; 
	    }
	    else {
	        str1 = "Consider dedicating more time to understanding the material and participating in supplementary activities.";
	        str2 = "Each step you take will contribute to improvement. Keep pushing forward and seeking support when needed.";
	    }
		List<String> lis = new ArrayList<>();
		lis.add(user.Name);
		lis.add(str1);
		lis.add(str2);
		return lis;
	}
	@Override
	public void rejectOffer(ActionEvent e, TableView<JobTableData> OfferTable, UserInformation user) throws SQLException, IOException {
		JobTableData selectedData = OfferTable.getSelectionModel().getSelectedItem();
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(user.ID); types.add("string");
		values.add(selectedData.getCompany_ID()); types.add("string");
		
		handle.ExecuteUpdate("Update studentindustry "
				+ "SET Status=-1 "
				+ "Where S_Id= ? and I_id = ?;", values, types);
		handle.CloseConnection();
		InformationExpert.getInstance().setAsRejected(selectedData.getCompany_ID(), ID);
	}
	@Override
	public void acceptOffer(ActionEvent e, TableView<JobTableData> OfferTable, UserInformation user) throws SQLException, IOException {
		JobTableData selectedData = OfferTable.getSelectionModel().getSelectedItem();
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(user.ID); types.add("string");
		values.add(selectedData.getCompany_ID()); types.add("string");
		handle.ExecuteUpdate("Update studentindustry "
				+ "SET Status=1 "
				+ "Where S_Id= ? and I_id = ?;", values, types);
		handle.CloseConnection();
		InformationExpert.getInstance().setAsAccepted(selectedData.getCompany_ID(), ID);
	}
	@Override
	public String getIDName() {
		return "Student_id";
	}
	public boolean submitContest(String code, String contestID) throws IOException, SQLException {
		ContestDescription cd = InformationExpert.getInstance().getContestDescription(contestID);
		String input = cd.getQuestions().getTestCaseInput(),output = cd.getQuestions().getTestCaseOutput();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("input.txt"))) {
            writer.write(input);
            System.out.println("Content written");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
		
		String conc = "\nfreopen(\"input.txt\", \"r\", stdin);\n"
				+ "freopen(\"output.txt\", \"w\", stdout);\n";
		int ind = code.indexOf("main()");
		while(code.charAt(ind) != '{') {
			ind++;
		}
		String cppFilePath = "submission.cpp";
		StringBuilder stringBuilder = new StringBuilder(code);
	    stringBuilder.insert(ind+1, conc);
	        
	    // Convert back to String if needed
	    code = stringBuilder.toString();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(cppFilePath))) {
            writer.write(code);
            System.out.println("Content written");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
		
		String outputFileName = "q";         
        String compileCommand = "g++ -o " + outputFileName + " " + cppFilePath;
        String runCommand = "./" + outputFileName; // Use "example.exe" directly on Windows
        
        try {
            // Compile the C++ file
            System.out.println("Compiling " + cppFilePath + "...");
            executeCommand(compileCommand);

            // Run the compiled executable
            System.out.println("Running the compiled program...");
            executeCommand(runCommand);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
        String fileContent = Files.readString(Path.of("output.txt"));
        fileContent.concat("\n");
        System.out.println(fileContent);
        
        for(int i=0 ; i<fileContent.length() && i < output.length() ; i++) {
        	if(fileContent.charAt(i) != output.charAt(i)) {
        		System.out.println("False at " + Integer.toString(i) + " for characters " + (int)fileContent.charAt(i) + "  " + (int)output.charAt(i) );
        		return false;
        	}
        }
        return true;
	}
	private static void executeCommand(String command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(command.split(" "));
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        System.out.println("Command exited with code " + exitCode);
    }
	
	@Override
	public void submitQuiz(String quizID, List<ToggleGroup> radios, UserInformation user) throws IOException, SQLException {
		int score = InformationExpert.getInstance().getCourse((quizID.equals("Quiz_1") || quizID.equals("Quiz_2")) 
										? "C_1" : "C_2").calculateScore(quizID,radios);
		user.setPerformance(UpdateSetPerformance(user.getPerformance(), score*2 ,10));
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(ID);types.add("string");
		values.add(quizID);types.add("string");
		values.add(Integer.toString(score));types.add("int");
		handle.ExecuteUpdate("INSERT INTO EvaluateStudentQuiz (S_Id, Quiz_Id, Score) "
				+ "VALUES (?, ?, ?);", values, types);
		InformationExpert.getInstance().addEvaluation(this.ID, quizID,score*2);
	}
	@Override
	public void registerCourse(ActionEvent e, String CourID) throws SQLException {
		if(this.getInformation().isRegistered(CourID)) {
			AlertSender.SendInformation("Already registered", "You are already registered").showAndWait();
			return;
		}
		
		if(this.getInformation().isPending(CourID)) {
			AlertSender.SendInformation("Request Pending", "Your Application is Pending Approval").showAndWait();
			return;
		}
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		
		values.add(ID); types.add("string");
		values.add(CourID); types.add("string");
		handle.ExecuteUpdate("INSERT INTO CourseRegistration (S_Id, C_Id, Status) "
				+ "VALUES (?, ?, 0);",values,types);
		AlertSender.SendInformation("Request Sent", "Your Application Request has been sent").showAndWait();
		this.getInformation().AddtoPending(CourID);
		
	}
	public void openQuiz(ActionEvent e, String str, String quizid) throws IOException, SQLException {
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(ID);types.add("string");
		values.add(quizid);types.add("string");
		ResultSet rs = handle.ExecuteQuery("SELECT * from EvaluateStudentQuiz "
				+ "where S_Id = ? and Quiz_id = ?", values, types);
		if(rs.next()) {
			Alert alert = AlertSender.SendInformation( "Already Given", "You have already participated Score = " + rs.getString("score"));
			alert.showAndWait();
		}
		else {
			Stage stage;
			Scene scene;
			Parent root;
			root = FXMLLoader.load(getClass().getResource("/fxmlFiles/studentfxml/" + str + ".fxml"));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		
	}
	@Override
	public void apply(String indusID) throws SQLException {
		IndustryInformation indusI = InformationExpert.getInstance().getIndustryInformation(indusID);
		int status = indusI.getStatus(indusID);
		switch(status) {
		case 2:
			AlertSender.SendWarning("Request Rejected", "You Applied for it before and got rejected").showAndWait();			
			break;
		case 1:
			AlertSender.SendInformation("Request Pending", "You Application is Pending").showAndWait();			
			break;
		case 0:
			AlertSender.SendInformation("Already Working", "You are already a part of this industry").showAndWait();			
			break;
		case 3:
			AlertSender.SendInformation("Request Received", "You already have a request from this industry").showAndWait();			
			break;
		}
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(ID); types.add("string");
		values.add(indusID); types.add("string");
		handle.ExecuteUpdate("INSERT INTO StudentIndustry (S_Id, I_Id, Request, Status) "
				+ "VALUES (?, ?, 0, 0);",values,types);
		InformationExpert.getInstance().setAsPending(indusID, this);
		AlertSender.SendInformation("REQUEST SENT", "Your request has been sent successfully").showAndWait();
		
	}
}
