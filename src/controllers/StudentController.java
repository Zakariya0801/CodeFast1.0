package controllers;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.InformationExpert;
import application.PersistantFactory;
import application.User;
import application.UserInformation;
import databasepackage.DBHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class StudentController implements UserController {
	
	protected Stage stage;
	protected Scene scene;
	protected Parent root;
	@FXML
    private TextArea CodeArea;
	private static UserInformation user = null;
	private static User currentUser = null;
	
	public static void setUser(UserInformation u, User us) {
		user = u;
		currentUser = us;
	}
	@Override
	public void switchtoDashboard(ActionEvent e) throws IOException, SQLException {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader (getClass().getResource("/fxmlFiles/studentfxml/DashBoard.fxml"));
		root = loader.load();
		SceneController contr = loader.getController();
		contr.setPieChart();
		contr.setStudent();
		contr.setBar();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	public void switchtoCareer(ActionEvent e) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader (getClass().getResource("/fxmlFiles/studentfxml/Career.fxml"));
		root = loader.load();
		
		SceneController contr = loader.getController();
		contr.setJobTable();
		contr.setOfferTable();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	public void switchtoContests(ActionEvent e) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader (getClass().getResource("/fxmlFiles/studentfxml/Contests.fxml"));
		root = loader.load();
		SceneController contr = loader.getController();
		contr.setUpcomingTable();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@Override
	@FXML
	public void switchtoAccount(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader (getClass().getResource("/fxmlFiles/studentfxml/Accounts.fxml"));
		root = loader.load();
		
		SceneController contr = loader.getController();
		contr.setAccount();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@Override
	public void switchtoSettings(ActionEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/fxmlFiles/studentfxml/settings.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@Override
	public void switchtoJobInternship(ActionEvent e) throws IOException {
		// TODO Auto-generated method stub
		root = FXMLLoader.load(getClass().getResource("/fxmlFiles/studentfxml/JobInternship.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@Override
	public void switchtoCourses(ActionEvent e) throws IOException, SQLException {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader (getClass().getResource("/fxmlFiles/studentfxml/Courses.fxml"));
		root = loader.load();
		SceneController contr = loader.getController();
		contr.setVisibility();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@Override
	public void switchtoStudyMaterial(ActionEvent e, UserInformation user) throws IOException {
		// TODO Auto-generated method stub
		System.out.println(user.getPlan().getStudyLink());
		root = FXMLLoader.load(getClass().getResource(user.getPlan().getStudyLink()));
		System.out.println("herer");
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@Override
	public void switchtoFeedback(ActionEvent e) throws IOException {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader (getClass().getResource("/fxmlFiles/studentfxml/Feedback.fxml"));
		root = loader.load();
		SceneController contr = loader.getController();
		contr.setFeedbackText();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	public void submitContest(ActionEvent e) throws IOException, SQLException {
		String code = CodeArea.getText();
		if(currentUser.submitContest(code, currentContest)) {
			Alert alert = AlertSender.SendInformation( "ACCEPTED", "Your code was accepted");
			alert.showAndWait();
			DBHandler handle = PersistantFactory.getInstance().getDatabase();
			
			List<String> values = new ArrayList<>();
			List<String> types = new ArrayList<>();
			values.add(user.ID);types.add("string");
			System.out.println("contest assinedasdsa = " + currentContest);
			values.add(currentContest);types.add("string");
			handle.ExecuteUpdate("INSERT INTO EvaluateStudentContest (S_Id, Contest_Id, Score) "
					+ "VALUES (?, ?, 15);", values, types);
			InformationExpert.getInstance().getStudentInformation(currentUser.ID).setPerformance( 
					InformationExpert.getInstance().getStudent(currentUser.ID).UpdateSetPerformance(user.getPerformance(), 15
					, InformationExpert.getInstance().getContestDescription(currentContest).getTotalMarks()));
			InformationExpert.getInstance().addEvaluation(currentUser.ID, currentContest,15);
			currentContest = ""; 
			switchtoContests(e);
		}
		else {
			Alert alert = AlertSender.SendWarning( "INCORRECT OUPUT", "Your output failed the test Cases");
			alert.showAndWait();
		}
	}
	@FXML
	private TextArea ContestQuestion;
	private void setQuestion(String ContestID) throws SQLException {
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(ContestID);types.add("string");
		ResultSet rs = handle.ExecuteQuery("""
				SELECT Statement FROM Problem Where Contest_ID = ?;
				""", values, types);
		if(rs!=null && rs.next())
			ContestQuestion.setText(rs.getString(1));
		
	}
	private static String currentContest = "";
	public void switchtoContestGeneric(ActionEvent e, String ContestID) throws IOException, SQLException {
		currentContest = ContestID;
		System.out.println("contest assined = " + currentContest);
		FXMLLoader loader = new FXMLLoader (getClass().getResource("/fxmlFiles/studentfxml/ContestGeneric.fxml"));
		root = loader.load();
		StudentController contr = loader.getController();
		contr.setQuestion(ContestID);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@SuppressWarnings("unchecked")
	public void setBar(BarChart<String, Integer> StudentPart) throws SQLException {
		XYChart.Series<String,Integer> series1 = new XYChart.Series<String, Integer>();
		series1.setName("Number of Contests");
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		
		series1.getData().add(new XYChart.Data<String, Integer>("Contests",
				InformationExpert.getInstance().getStudentInformation(currentUser.ID).getContestEvaluationCount()));
		series1.getData().add(new XYChart.Data<String, Integer>("Quiz",
				InformationExpert.getInstance().getStudentInformation(currentUser.ID).getQuizEvaluationCount()));
		
		StudentPart.getData().addAll(series1);
	}
	
}
