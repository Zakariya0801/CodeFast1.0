package application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Student.Student;
import application.Student.StudentInformation;
import application.Student.Contest.Contest;
import controllers.SceneController;
import controllers.TablesData.CourseRequest;
import controllers.TablesData.JobTableData;
import controllers.TablesData.OffersData;
import controllers.TablesData.StudentMainData;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public abstract class User {
	public String ID;
	public User(String id) {
		ID = id;
	}
	public User() {
		
	}
	public UserInformation getInformation() throws SQLException {
		return new UserInformation(ID);
	}
	public void rejectStudentRegistration(ActionEvent e, TableView<StudentMainData> StudentRegistrationTable) throws SQLException {}
	public void acceptPremiumPlan(ActionEvent e, TableView<StudentMainData> StudentRegistrationTable) throws SQLException {}
	public void rejectPremiumPlan(ActionEvent e, TableView<StudentMainData> StudentRegistrationTable) throws SQLException {}
	public void acceptStudentRegistration(ActionEvent e, TableView<StudentMainData> StudentRegistrationTable) throws SQLException {}
	public void acceptCourseRegistration(ActionEvent e, TableView<CourseRequest> StudentRegistrationTable) throws SQLException {}
	public void rejectCourseRegistration(ActionEvent e, TableView<CourseRequest> StudentRegistrationTable) throws SQLException {}
	public abstract String getTableName();
	public abstract String getIDName();
	public void SendRequestPremium(ActionEvent e, UserInformation user) throws IOException, URISyntaxException, SQLException {}
	public float UpdateSetPerformance(float performance, int score, int total) {return 0;}
	public List<String> setFeedbackText(UserInformation user) {return null;}
	public boolean submitContest(String code, String contestID) throws IOException, SQLException {return false;}
	public void rejectOffer(ActionEvent e, TableView<JobTableData> OfferTable, UserInformation user) throws SQLException, IOException {}
	public void acceptOffer(ActionEvent e, TableView<JobTableData> OfferTable, UserInformation user) throws SQLException, IOException {}
	/////////////////////////////
	//////////Changes///////////
	///////////////////////////
	public void acceptStudentRequest(ActionEvent e, TableView<OffersData> StudentRegistrationTable) throws SQLException {}
	public void rejectStudentRequest(ActionEvent e, TableView<OffersData> StudentRegistrationTable) throws SQLException {}
	public void offerInternship(ActionEvent e, TableView<OffersData> StudentRegistrationTable) throws SQLException {}
	public void submitQuiz(String str, List<ToggleGroup> radios, UserInformation user) throws IOException, SQLException {}
	public void registerCourse(ActionEvent e, String CourID) throws SQLException {}
	public void openQuiz(ActionEvent e, String str, String quizid) throws IOException, SQLException {}
	public void apply(String indusID) throws SQLException {}
	public void removeStudent(String studentID) throws SQLException {}
	public boolean MakeNewContest(String ContName,String Start,String End,String TestCaseInp,String TestCaseOut,String Statement) throws SQLException  {return false;}
	public boolean addProblem(Contest c,String TestCaseInp,String TestCaseOut,String Statement) throws SQLException {return false;}

	public boolean openContest(String ContestID) throws IOException, SQLException {return false;}
	public void setEnrolledTable(List<TableColumn<OffersData, String>> columns,TableView<OffersData> AppliedTable, HashMap<String,Student> enrolled) throws SQLException {
		columns.get(0).setCellValueFactory(new PropertyValueFactory<OffersData, String>("Student_ID"));
		columns.get(1).setCellValueFactory(new PropertyValueFactory<OffersData, String>("Student_Name"));
		columns.get(2).setCellValueFactory(new PropertyValueFactory<OffersData, String>("Student_Email"));
		columns.get(3).setCellValueFactory(new PropertyValueFactory<OffersData, String>("Student_cgpa"));
		columns.get(4).setCellValueFactory(new PropertyValueFactory<OffersData, String>("Student_Performance"));
		for(Map.Entry<String, Student> entry : enrolled.entrySet()) {
			StudentInformation s = InformationExpert.getInstance().getStudentInformation(entry.getValue().ID);
			AppliedTable.getItems().add(new OffersData(s.ID, 
					s.Name,
					s.getEmail(),
					Float.toString(s.cgpa),
					Float.toString(s.performance)));
		}
		
	}
	public void viewRankings(ActionEvent e, String ContestID) throws IOException, SQLException {
		Stage stage;
		Scene scene;
		Parent root;
		FXMLLoader loader = new FXMLLoader (getClass().getResource("/fxmlFiles/studentfxml/ViewContestRanking.fxml"));
		root = loader.load();
		SceneController contr = loader.getController();
		contr.SetRankingTable(ContestID);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void viewLeaderboard(ActionEvent e) throws IOException, SQLException {
		Stage stage;
		Scene scene;
		Parent root;
		FXMLLoader loader = new FXMLLoader (getClass().getResource("/fxmlFiles/studentfxml/ViewLeaderBoards.fxml"));
		root = loader.load();
		SceneController contr = loader.getController();
		contr.setLeaderboard();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
