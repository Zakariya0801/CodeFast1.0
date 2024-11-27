package controllers;

import application.InformationExpert;
import application.PersistantFactory;
import application.User;
import application.UserInformation;
import application.Admin.CourseRegistrationRequest;
import application.Admin.PremiumPlanRequests;
import application.Admin.Request;
import application.Admin.StudentRegistrationRequest;
import application.Student.Evaluation;
import application.Student.Student;
import application.Student.StudentInformation;
import application.Student.Contest.Contest;
import controllers.TablesData.CourseRequest;
import controllers.TablesData.JobTableData;
import controllers.TablesData.LeaderboardData;
import controllers.TablesData.OffersData;
import controllers.TablesData.QuizTable;
import controllers.TablesData.RankingData;
import controllers.TablesData.StudentMainData;
import controllers.TablesData.UpComingContest;
import databasepackage.*;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneController {
	protected Stage stage;
	protected Scene scene;
	protected Parent root;
	protected static UserInformation user = null;
	protected static User currentUser = null;
	protected static UserController userController = null;
	@FXML
	private Button changeable;
	@FXML
	private Label UserName, DegreeName,UserName1,
				  DegreeName1, email1, STID, 
				  Sperformance, plan, CGPA, email,remarks, string1, string2, ContName;
	@FXML
	private Label Contest11Name, Contest12Name, Contest13Name, Contest14Name, Contest1Name, Contest2Name, Contest3Name, Contest4Name;
	@FXML
	private Button Contest1Join, Contest2Join, Contest3Join, Contest4Join;
    @FXML
    PasswordField password, PasswordConfirm;
    @FXML
    private TextField NameRegis, EmailRegis, Userid;
    @FXML
    private AnchorPane Anchor2,Anchor3;
    @FXML
    private ToggleGroup q1,q2,q3,q4,q5;
	@FXML
    private PieChart pieChart;	
	@FXML
    private TableView<JobTableData> JobTable, OfferTable;	
	@FXML
    private TableView<QuizTable> QuizTable;
	@FXML
    private TableColumn<QuizTable, String> QID,CName,SID,score,total;
	@FXML
    private TableColumn<JobTableData, String> Description,Description1, CompID,CompID1, Name, Name1, Type,Type1;	
	@FXML
	private BarChart<String, Integer> StudentPart, Monitor;
    @FXML
    private TableView<StudentMainData> StudentRegistrationTable,StudentRegistrationTable2;
    @FXML
    private TableColumn<StudentMainData, String> StudentCGPA,StudentEmail,StudentName,StudentID, StudentDegree, 
    											 StudentCGPA2,StudentEmail2,StudentName2,StudentID2, StudentDegree2;
	@FXML
	private TableView<OffersData>OffersTable, AppliedTable;
	@FXML
	private TableColumn<OffersData, String> id, name, emails, cgpa, performance, id1, name1, emails1, cgpa1, performance1;
    @FXML
    private TableView<CourseRequest> CourseRegistrationTable;
    @FXML
    private TableColumn<CourseRequest, String> StudentID1, StudentName1, StudentCourse, StudentCourseName;
    @FXML
    private TableView<UpComingContest> ContestTable;
    @FXML
    private TableColumn<UpComingContest, String> ContestID, EndDate, StartDate,ContestName;
    @FXML
    private TableColumn<RankingData, String> CodePerformance, ContPercentage, ContScore, Position, StID, StName;
    @FXML
    private TextField ContesName;
    @FXML
    private TableView<RankingData> RankingsTable;
    @FXML
    private TextArea TestCaseInput, TestCaseOutput, ProblemStatement;
    @FXML
    private DatePicker StartTime, EndTime;

    
    @FXML
    public void switchtoDashBoard(ActionEvent e) throws IOException, SQLException {
    	userController.switchtoDashboard(e);
    }
    @FXML
    public void switchtoCourse(ActionEvent e) throws IOException, SQLException {
    	userController.switchtoCourses(e);
    }
    @FXML
    public void switchtoContests(ActionEvent e) throws IOException, SQLException {
    	userController.switchtoContests(e);
    }
    @FXML
    public void switchtoAccount(ActionEvent e) throws IOException {
    	userController.switchtoAccount(e);
    }
    @FXML
    public void switchtoJobInternship(ActionEvent e) throws IOException, SQLException {
    	userController.switchtoJobInternship(e);
    }
    @FXML
    public void switchtoStudyMaterial(ActionEvent e) throws IOException, SQLException {
    	userController.switchtoStudyMaterial(e, user);
    }
    @FXML
    public void switchtoCareer(ActionEvent e) throws IOException, SQLException {
    	userController.switchtoCareer(e);
    }
    @FXML
    public void switchtoSettings(ActionEvent e) throws IOException {
    	new SettingsController().switchtoSettings(e);
    }
    @FXML
    public void switchtoFeedback(ActionEvent e) throws IOException, SQLException {
    	userController.switchtoFeedback(e);
    }
	public void UpdatePerformance(float performance)
	{
		user.UpdatePerformance(remarks, performance);
	}
	@FXML
	public void acceptOffer(ActionEvent e) throws SQLException, IOException {
		currentUser.acceptOffer(e,OfferTable,user);
		switchtoCareer(e);
	}
	@FXML
	public void rejectOffer(ActionEvent e) throws SQLException, IOException {
		currentUser.rejectOffer(e,OfferTable,user);
		switchtoCareer(e);
	}
	@FXML
	public void offerStudent(ActionEvent e) throws SQLException, IOException {
		currentUser.offerInternship(e, OffersTable);
		userController.switchtoCourses(e);
	}
	@FXML
	public void acceptApplyingStudent(ActionEvent e) throws SQLException, IOException {
		currentUser.acceptStudentRequest(e, AppliedTable);
		userController.switchtoCourses(e);
	}
	@FXML
	public void rejectApplyingStudent(ActionEvent e) throws SQLException, IOException {
		currentUser.rejectStudentRequest(e, AppliedTable);
		userController.switchtoCourses(e);
	}
	@FXML
	public void viewLeaderboard(ActionEvent e) throws IOException, SQLException {
		currentUser.viewLeaderboard(e);
	}
	public void setButton() {
		changeable.setText("Offers");
	}
	public void setPieChart() throws SQLException {
		 ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
					 new PieChart.Data("SGPA = " + Float.toString(user.getPerformance()), user.getPerformance()),
					 new PieChart.Data("Deducted", 5.0 - user.getPerformance())
					 );
		 UpdatePerformance( user.getPerformance());
		 pieChart.setData(pieChartData);
		 pieChart.setStartAngle(90);
	 }
	void SetInfo() {
		UserName.setText(user.Name);
		email.setText(user.Email);
	}
	void setStudent() {
		UserName.setText(user.Name);
		DegreeName.setText(user.getDegree());
		CGPA.setText(Float.toString(user.getCgpa()));		
	}
	public void setBar() throws SQLException {
		userController.setBar(StudentPart);
	}
	public void setAccount() {
		List<Label> labels = new ArrayList<>();
		labels.add(UserName);labels.add(UserName1);labels.add(email);labels.add(plan);labels.add(DegreeName);
		labels.add(DegreeName1);labels.add(CGPA);labels.add(STID);labels.add(Sperformance);
		user.setAccount(labels);
	}
	public void setIndustry_Account() {
		List<Label> labels = new ArrayList<>();
		labels.add(UserName);labels.add(UserName1);labels.add(DegreeName1);
		labels.add(email);labels.add(plan);labels.add(STID);
		user.setAccount(labels);
	}
	@FXML
	public void registerDataStructures(ActionEvent e) throws SQLException, IOException {
		currentUser.registerCourse(e,"C_1");
		switchtoCourse(e);
	}
	@FXML
	public void registerAlgo(ActionEvent e) throws SQLException, IOException {
		currentUser.registerCourse(e,"C_2");
		switchtoCourse(e);
	}
	@FXML
	public void openDataQuiz1(ActionEvent e) throws IOException, SQLException {
		currentUser.openQuiz(e, "Data_Quiz1", "Quiz_1");
		
	}
	@FXML
	public void openDataQuiz2(ActionEvent e) throws IOException, SQLException {
		currentUser.openQuiz(e, "Data_Quiz2", "Quiz_2");
	}
	@FXML
	public void openAlgoQuiz1(ActionEvent e) throws IOException, SQLException {
		currentUser.openQuiz(e, "DSA_Quiz1", "Quiz_3");			
	}
	@FXML
	public void openAlgoQuiz2(ActionEvent e) throws IOException, SQLException {
		currentUser.openQuiz(e, "DSA_Quiz2", "Quiz_4");
	}
	public void submitQuizGeneric(String QuizID) throws IOException, SQLException {
		List<ToggleGroup> buttons = new ArrayList<>();
		buttons.add(q1);buttons.add(q2);buttons.add(q3);buttons.add(q4);buttons.add(q5);
		currentUser.submitQuiz(QuizID,buttons,user);
	}
	@FXML
	public void submitQuiz1(ActionEvent e) throws IOException, SQLException {
		submitQuizGeneric("Quiz_1");
		switchtoCourse(e);
	}
	@FXML
	public void submitQuiz2(ActionEvent e) throws IOException, SQLException {
		submitQuizGeneric("Quiz_2");
		switchtoCourse(e);
	}
	@FXML
	public void submitQuiz3(ActionEvent e) throws IOException, SQLException {
		submitQuizGeneric("Quiz_3");
		switchtoCourse(e);
	}
	@FXML
	public void submitQuiz4(ActionEvent e) throws IOException, SQLException {
		submitQuizGeneric("Quiz_4");
		switchtoCourse(e);
	}	
	@FXML
	public void acceptStudentRegistration(ActionEvent e) throws SQLException, IOException {
		currentUser.acceptStudentRegistration(e,StudentRegistrationTable);
		switchtoCourse(e);
	}
	@FXML 
	public void rejectStudentRegistration(ActionEvent e) throws SQLException, IOException {
		currentUser.rejectStudentRegistration(e,StudentRegistrationTable);
		switchtoCourse(e);
	}
	@FXML
	public void rejectPremiumPlan(ActionEvent e) throws SQLException, IOException {
		currentUser.rejectPremiumPlan(e, StudentRegistrationTable2);
		switchtoCourse(e);
	}
	@FXML
	public void acceptPremiumPlan(ActionEvent e) throws SQLException, IOException {
		currentUser.acceptPremiumPlan(e, StudentRegistrationTable2);
		switchtoCourse(e);
	}
	@FXML
	public void acceptCourseRegistration(ActionEvent e) throws SQLException, IOException {
		currentUser.acceptCourseRegistration(e, CourseRegistrationTable);
		switchtoCourse(e);
	}
	@FXML
	public void rejectCourseRegistration(ActionEvent e) throws SQLException, IOException {
		currentUser.rejectCourseRegistration(e, CourseRegistrationTable);
		switchtoCourse(e);
	}
	@FXML
	public void acceptStudentRegistration2(ActionEvent e) throws SQLException, IOException {
		currentUser.acceptStudentRegistration(e,StudentRegistrationTable);
		switchtoCourse(e);
	}
	@FXML 
	public void rejectStudentRegistration2(ActionEvent e) throws SQLException, IOException {
		currentUser.rejectStudentRegistration(e,StudentRegistrationTable);
		switchtoCourse(e);
	}
	@FXML 
	public void offerStudentRegistration(ActionEvent e) throws SQLException, IOException {
		currentUser.rejectStudentRegistration(e,StudentRegistrationTable);
		switchtoCourse(e);
	}
	public void updateName(ActionEvent e) throws SQLException {new SettingsController().updateName(e,NameRegis);}
	public void updateEmail(ActionEvent e) throws SQLException {new SettingsController().updateEmail(e,EmailRegis);}
	public void updatePassword(ActionEvent e) throws SQLException {new SettingsController().updatePassword(e,password,PasswordConfirm);}
	public void switchTOlogin(ActionEvent e) throws SQLException, IOException{new DashboardController().switchtoLogin(e);};
	@FXML
	public void applySystems(ActionEvent e) throws SQLException {
		currentUser.apply("ID_1");
	}
	@FXML
	public void applyDubbizle(ActionEvent e) throws SQLException {
		currentUser.apply("ID_2");
	}
	@FXML
	public void applyDevsinc(ActionEvent e) throws SQLException {
		currentUser.apply("ID_3");
	}
	@FXML
	public void runLink(ActionEvent e) throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URI("https://cp-algorithms.com/"));
	}
	@FXML
	public void runLink1(ActionEvent e) throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URI("https://cses.fi/problemset/list/"));
	}
	@FXML
	public void runLink2(ActionEvent e) throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URI("https://www.cet.edu.in/noticefiles/280_DS%20Complete.pdf"));
	}
	@FXML
	public void runLink3(ActionEvent e) throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=0IAPZzGSbME&list=PLAXnLdrLnQpRcveZTtD644gM9uzYqJCwr"));
	}
	@FXML
	public void runLink4(ActionEvent e) throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URI("https://vssut.ac.in/lecture_notes/lecture1428551222.pdf"));
	}
	@FXML
	public void runLink5(ActionEvent e) throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=u8JZ9gU5o4g&list=PLxCzCOWd7aiHcmS4i14bI0VrMbZTUvlTa"));
	}
	@FXML
	public void SendRequestPremium(ActionEvent e) throws IOException, URISyntaxException, SQLException {
		currentUser.SendRequestPremium(e,user);
	}
	@FXML
	public void openContest1(ActionEvent e) throws IOException, SQLException {
		openContest(e,"Contest_1");
	}
	@FXML
	public void openContest2(ActionEvent e) throws IOException, SQLException {
		openContest(e,"Contest_2");
	}
	public static void SetUser(UserInformation u, User us, UserController uc) {
		user = u;
		currentUser = us;
		userController = uc;
		DashboardController.SetUser(u,us,uc);
		SettingsController.SetUser(u,us,uc);
		StudentController.setUser(u, us);
	}
	@FXML
    public void AddNewContest(ActionEvent e) throws SQLException, IOException {
		if(ContesName == null || TestCaseInput == null || TestCaseOutput== null || ProblemStatement== null 
				|| StartTime== null || EndTime == null) {
			AlertSender.SendWarning("FIELDS MISSING","Enter all the required Fields").showAndWait();
			return;
		}
    	String ContName = ContesName.getText();
    	String TestCaseInp = TestCaseInput.getText();
    	String TestCaseOut = TestCaseOutput.getText();
    	String Statement = ProblemStatement.getText();
    	String Start = StartTime.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	String End = EndTime.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	boolean confirmation = currentUser.MakeNewContest(ContName,Start,End,TestCaseInp,TestCaseOut,Statement);
    	if(confirmation) 
    		AlertSender.SendInformation("Contest Added Successfully", "You contest was added successfully").showAndWait();
    	else
    		AlertSender.SendWarning("Error Occured", "There was an error in creating Contest").showAndWait();
    	switchtoFeedback(e);
    }
	@FXML
	public void removeStudent(ActionEvent e) throws IOException, SQLException {
		OffersData of = AppliedTable.getSelectionModel().getSelectedItem();
		if(of == null) return;
		currentUser.removeStudent(of.getStudent_ID());
		switchtoStudyMaterial(e);
	}
	List<Label> contestNames(){
		List<Label> names = new ArrayList<>();
		names.add(Contest1Name);names.add(Contest2Name);names.add(Contest3Name);names.add(Contest4Name);
		return names;
	}
	List<Button> contestButtons(){
		List<Button> buttons = new ArrayList<>();
		buttons.add(Contest1Join);buttons.add(Contest2Join);buttons.add(Contest3Join);buttons.add(Contest4Join);
		return buttons;
	}
	public void setLiveContests() throws SQLException {
		List<Label> names = contestNames();
		List<Button> buttons = contestButtons();
		DBHandler db = PersistantFactory.getInstance().getDatabase();
		ResultSet rs = db.ExecuteQuery("Contest", "isLive=1");
		int i=0;
		while(rs != null && rs.next() && i<4) {
			names.get(i).setText(rs.getString("Name"));
			i++;
		}
		db.CloseConnection();
		while(i<4) {
			names.get(i).setText("");
			buttons.get(i).setVisible(false);
			i++;
		}
	}
	public void setPastContests() throws SQLException {
		List<Label> names = contestNames();
		List<Button> buttons = contestButtons();
		DBHandler db = PersistantFactory.getInstance().getDatabase();
		ResultSet rs = db.ExecuteQuery("Contest", "isLive=1");
		int i=0;
		while(rs != null && rs.next() && i<4) {
			names.get(i).setText(rs.getString("Name"));
			i++;
		}
		db.CloseConnection();
		while(i<4) {
			names.get(i).setText("");
			buttons.get(i).setVisible(false);
			i++;
		}
	}
	void setVisibility() throws SQLException {
		Anchor2.setVisible(false);Anchor3.setVisible(false);
		Anchor2.setDisable(true);Anchor3.setDisable(true);
		if(InformationExpert.getInstance().getStudentInformation(currentUser.ID).isRegistered("C_1")) {
			Anchor2.setVisible(true);Anchor2.setDisable(false);
		}
		if (InformationExpert.getInstance().getStudentInformation(currentUser.ID).isRegistered("C_2")){
			Anchor3.setVisible(true);Anchor3.setDisable(false);
		}
		
	}
	public void setJobTable() throws SQLException {
		CompID.setCellValueFactory(new PropertyValueFactory<JobTableData, String>("Company_ID"));
		Name.setCellValueFactory(new PropertyValueFactory<JobTableData, String>("Company_Name"));
		Description.setCellValueFactory(new PropertyValueFactory<JobTableData, String>("Description"));
		Type.setCellValueFactory(new PropertyValueFactory<JobTableData, String>("JobType"));
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(user.ID);types.add("string");
		ResultSet rs = handle.ExecuteQuery("SELECT "
				+ "i.Name AS industry_name, i.Description AS industry_description, i.Industry_Id AS I_id "
				+ "FROM Industry i JOIN StudentIndustry si "
				+ "ON i.Industry_Id = si.I_Id "
				+ "WHERE si.Status = 1 AND si.S_Id=?; "
				+ "", values,types);
		while(rs != null && rs.next()) {
			String jobType = (rs.getString("I_id").equals("ID_1") || rs.getString("I_id").equals("ID_3")) ? "Internship" : "Job";
			JobTable.getItems().add(new JobTableData(rs.getString(3), rs.getString(1),rs.getString(2),jobType));
		}
	}
	public void setUpcomingTable() throws SQLException {
		ContestID.setCellValueFactory(new PropertyValueFactory<UpComingContest, String>("Contest_ID"));
		ContestName.setCellValueFactory(new PropertyValueFactory<UpComingContest, String>("Contest_Name"));
		StartDate.setCellValueFactory(new PropertyValueFactory<UpComingContest, String>("StartDate"));
		EndDate.setCellValueFactory(new PropertyValueFactory<UpComingContest, String>("EndDate"));
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		ResultSet rs = handle.ExecuteQuery("Select Contest_Id,Name,StartDate,EndDate from Contest where isLive=0;", values,types);
		while(rs != null && rs.next()) {
			ContestTable.getItems().add(new UpComingContest(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
		}
	}
	public void setAdminUpcomingTable() throws SQLException {
		ContestID.setCellValueFactory(new PropertyValueFactory<UpComingContest, String>("Contest_ID"));
		ContestName.setCellValueFactory(new PropertyValueFactory<UpComingContest, String>("Contest_Name"));
		StartDate.setCellValueFactory(new PropertyValueFactory<UpComingContest, String>("StartDate"));
		EndDate.setCellValueFactory(new PropertyValueFactory<UpComingContest, String>("EndDate"));
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		ResultSet rs = handle.ExecuteQuery("Select Contest_Id,Name,StartDate,EndDate from Contest;", values,types);
		while(rs != null && rs.next()) {
			ContestTable.getItems().add(new UpComingContest(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
		}
	}
	public void setOfferTable() throws SQLException {
		CompID1.setCellValueFactory(new PropertyValueFactory<JobTableData, String>("Company_ID"));
		Name1.setCellValueFactory(new PropertyValueFactory<JobTableData, String>("Company_Name"));
		Description1.setCellValueFactory(new PropertyValueFactory<JobTableData, String>("Description"));
		Type1.setCellValueFactory(new PropertyValueFactory<JobTableData, String>("JobType"));
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(user.ID);types.add("string");
		ResultSet rs = handle.ExecuteQuery("SELECT "
				+ "i.Name AS industry_name, i.Description AS industry_description, i.Industry_Id AS I_id "
				+ "FROM Industry i JOIN StudentIndustry si "
				+ "ON i.Industry_Id = si.I_Id "
				+ "WHERE si.Status = 0 and si.Request=1 AND si.S_Id=?;"
				+ "", values,types);
		String jobType = "Job";
		while(rs.next()) {
			OfferTable.getItems().add(new JobTableData(rs.getString(3), rs.getString(1),rs.getString(2),jobType));
		}
	}
	@FXML
	public void viewRankingsTable(ActionEvent e) throws IOException, SQLException {
		UpComingContest con = ContestTable.getSelectionModel().getSelectedItem();
		if(con == null) return;
		currentUser.viewRankings(e,con.Contest_ID);
	}
	@FXML
	public void viewRankingsC1(ActionEvent e) throws IOException, SQLException {
		currentUser.viewRankings(e,"Contest_1");
	}
	@FXML
	public void viewRankingsC2(ActionEvent e) throws IOException, SQLException {
		currentUser.viewRankings(e,"Contest_2");
	}
	public void SetRankingTable(String ContestID) throws SQLException {
		ContName.setText("View " + ContestID + " Ranking");
		CodePerformance.setCellValueFactory(new PropertyValueFactory<RankingData, String>("Performance"));
		ContPercentage.setCellValueFactory(new PropertyValueFactory<RankingData, String>("Percentage")); 
		ContScore.setCellValueFactory(new PropertyValueFactory<RankingData, String>("Contest_score")); 
		Position.setCellValueFactory(new PropertyValueFactory<RankingData, String>("Position")); 
		StID.setCellValueFactory(new PropertyValueFactory<RankingData, String>("Student_id")); 
		StName.setCellValueFactory(new PropertyValueFactory<RankingData, String>("Student_name"));
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(ContestID);types.add("string");
		ResultSet rs = handle.ExecuteQuery("SELECT S_id, Students.Name, Score, Students.SPerformance "
				+"FROM EvaluateStudentContest INNER JOIN Students ON EvaluateStudentContest.S_id = Students.Student_id "
				+" WHERE Contest_id=? ORDER BY SPerformance DESC;", values, types);			
		int i=1;
		while(rs != null && rs.next()) {
			RankingsTable.getItems().add(new RankingData(Integer.toString(i), 
					rs.getString(1), 
					rs.getString(2),
					Integer.toString(rs.getInt(3)),
					"100",
					Float.toString(rs.getFloat(4))
					));
			i++;
		}
	}
	public void setFeedbackText() {
		List<String> lis = currentUser.setFeedbackText(user);
		if(lis == null) return;
	 	UserName.setText(lis.get(0));
	    string1.setText(lis.get(1));
	    string2.setText(lis.get(2));
	}
	public void openContest(ActionEvent e, String ContestID) throws IOException, SQLException {
		if(currentUser.openContest(ContestID)) {
			userController.switchtoContestGeneric(e,ContestID);
		}
	}
	@FXML 
	public void openContestTable(ActionEvent e) throws IOException, SQLException {
		UpComingContest con = ContestTable.getSelectionModel().getSelectedItem();
		if(con == null) return;
		openContest(e,con.Contest_ID);
	}
	private void setData(List<TableColumn<StudentMainData, String>> columns) {
		columns.get(0).setCellValueFactory(new PropertyValueFactory<StudentMainData, String>("StudentID"));
		columns.get(1).setCellValueFactory(new PropertyValueFactory<StudentMainData, String>("Name"));
		columns.get(2).setCellValueFactory(new PropertyValueFactory<StudentMainData, String>("Degree"));
		columns.get(3).setCellValueFactory(new PropertyValueFactory<StudentMainData, String>("CGPA"));
		columns.get(4).setCellValueFactory(new PropertyValueFactory<StudentMainData, String>("Email"));
	}
	void setRequestTables() throws SQLException {
		List<TableColumn<StudentMainData, String>> columns = new ArrayList<>();
		columns.add(StudentID);columns.add(StudentName);columns.add(StudentDegree);
		columns.add(StudentCGPA);columns.add(StudentEmail);
		setData(columns);
		columns = new ArrayList<>();
		columns.add(StudentID2);columns.add(StudentName2);columns.add(StudentDegree2);
		columns.add(StudentCGPA2);columns.add(StudentEmail2);
		setData(columns);
		
		StudentID1.setCellValueFactory(new PropertyValueFactory<CourseRequest, String>("Student_id"));
		StudentName1.setCellValueFactory(new PropertyValueFactory<CourseRequest, String>("Student_name"));
		StudentCourse.setCellValueFactory(new PropertyValueFactory<CourseRequest, String>("Course_id")); 
		StudentCourseName.setCellValueFactory(new PropertyValueFactory<CourseRequest, String>("Course_name"));;
		
		Request<StudentMainData> r = new StudentRegistrationRequest();
		List<StudentMainData> lis= r.getRequestData();
		for(StudentMainData s : lis) 
			StudentRegistrationTable.getItems().add(s);
		r = new PremiumPlanRequests();
		lis= r.getRequestData();
		for(StudentMainData s : lis) 
			StudentRegistrationTable2.getItems().add(s);
		Request<CourseRequest> re = new CourseRegistrationRequest();
		List<CourseRequest> list = re.getRequestData();
		for(CourseRequest s : list) 
			CourseRegistrationTable.getItems().add(s);
	}
	@FXML
    private PieChart pieChart1;
	@FXML
	public void setAdminPieChart() throws SQLException {
		int ContestCount=InformationExpert.getInstance().getNumberofContestEvaluations();
		int QuizCount=InformationExpert.getInstance().getNumberofQuizEvaluations();
		 ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
					 new PieChart.Data("Quiz="+Integer.toString(QuizCount),QuizCount ),
					 new PieChart.Data("Contest="+Integer.toString(ContestCount),ContestCount)
					 );
		 pieChart1.setData(pieChartData);
		 pieChart1.setStartAngle(90);
	 }
	@SuppressWarnings("unchecked")
	public void setAdminBar1() throws SQLException {
		userController.setBar(StudentPart);
	}
	@SuppressWarnings("unchecked")
	public void setAdminBar2() throws SQLException {
		XYChart.Series<String,Integer> series1 = new XYChart.Series<String, Integer>();
		series1.setName("Student Performance");
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		
		ResultSet rs = handle.ExecuteQuery("SELECT MAX(SPerformance) AS MaxPerformance FROM Students;", null,null);
		if(rs.next()) {
			series1.getData().add(new XYChart.Data<String, Integer>("Max",rs.getInt(1)));
		}
		rs = handle.ExecuteQuery("SELECT AVG(SPerformance) AS AvgPerformance FROM Students;", null,null);
		if(rs.next()) {
			series1.getData().add(new XYChart.Data<String, Integer>("Average",rs.getInt(1)));
		}
		rs = handle.ExecuteQuery("SELECT MIN(SPerformance) AS MinPerformance FROM Students;", null,null);
		if(rs.next()) {
			series1.getData().add(new XYChart.Data<String, Integer>("Min",rs.getInt(1)));
		}
		
		handle.CloseConnection();
		
		Monitor.getData().addAll(series1);
	}
	void SetInfo1() {
		UserName1.setText(user.Name);
		email1.setText(user.Email);
	}
	public void setQuizTable() throws SQLException {
		QID.setCellValueFactory(new PropertyValueFactory<QuizTable, String>("QuizId"));
		CName.setCellValueFactory(new PropertyValueFactory<QuizTable, String>("Cname"));
		SID.setCellValueFactory(new PropertyValueFactory<QuizTable, String>("StudentID"));
		score.setCellValueFactory(new PropertyValueFactory<QuizTable, String>("Score"));
		total.setCellValueFactory(new PropertyValueFactory<QuizTable, String>("Total"));
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		
		ResultSet rs = handle.ExecuteQuery("SELECT " +
			    "e.Quiz_Id AS QuizID, " +
			    "c.CName AS CourseName, " +
			    "s.Student_Id AS StudentID, " +
			    "e.Score AS Scores, " +
			    "q.TotalMarks AS TotalMarks " +
			    "FROM EvaluateStudentQuiz e " +
			    "JOIN Students s ON e.S_Id = s.Student_Id " +
			    "JOIN Quiz q ON e.Quiz_Id = q.Quiz_Id " +
			    "JOIN Course c ON q.C_Id = c.Course_Id;", null, null);
		while(rs != null && rs.next()) {
			QuizTable.getItems().add(new QuizTable(rs.getString(1), 
					rs.getString(2),
					rs.getString(3),
					Integer.toString(rs.getInt(4)),
					Integer.toString(rs.getInt(5))
					));
		}
	}
	@FXML
	public void removeStudentAdmin(ActionEvent e) throws SQLException, IOException {
		OffersData student = OffersTable.getSelectionModel().getSelectedItem();
		if(student == null) AlertSender.SendWarning("NOT SELECTED", "No Student Selected").showAndWait();
		currentUser.removeStudentAdmin(student.getStudent_ID());
		AlertSender.SendInformation("REMOVED SUCCESSFULLY", "Student with ID: " + student.Student_ID + " removed successfully").showAndWait();
		switchtoStudyMaterial(e);
	}
	public void setOffersTable() throws SQLException {
		id.setCellValueFactory(new PropertyValueFactory<OffersData, String>("Student_ID"));
		name.setCellValueFactory(new PropertyValueFactory<OffersData, String>("Student_Name"));
		emails.setCellValueFactory(new PropertyValueFactory<OffersData, String>("Student_Email"));
		cgpa.setCellValueFactory(new PropertyValueFactory<OffersData, String>("Student_cgpa"));
		performance.setCellValueFactory(new PropertyValueFactory<OffersData, String>("Student_Performance"));
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(user.ID);types.add("string");
		ResultSet rs = handle.ExecuteQuery("SELECT s.Student_Id, s.Name, s.Email, s.CGPA, s.SPerformance "
				+"FROM Students s LEFT JOIN StudentIndustry si ON s.Student_Id = si.S_Id AND si.I_Id =? "
				+"WHERE si.S_Id IS NULL and s.Status=1;",values,types);
		while(rs.next()) {
			OffersTable.getItems().add(new OffersData(
					rs.getString("Student_Id"), 
					rs.getString("Name"),
					rs.getString("Email"),
					rs.getString("CGPA"),
					rs.getString("SPerformance")
					));
		}
	}
	
	public void setIndustry_Dashboard() {
		UserName.setText(user.Name);
		DegreeName.setText(user.Email);
		DegreeName1.setText(user.getDescription());
	}
	public void setAppliedTable() throws SQLException {
		id1.setCellValueFactory(new PropertyValueFactory<OffersData, String>("Student_ID"));
		name1.setCellValueFactory(new PropertyValueFactory<OffersData, String>("Student_Name"));
		emails1.setCellValueFactory(new PropertyValueFactory<OffersData, String>("Student_Email"));
		cgpa1.setCellValueFactory(new PropertyValueFactory<OffersData, String>("Student_cgpa"));
		performance1.setCellValueFactory(new PropertyValueFactory<OffersData, String>("Student_Performance"));
		
		HashMap<String, Student> pending = InformationExpert.getInstance().getIndustryInformation(currentUser.ID).getPendingStudents();
		for(Map.Entry<String, Student> entry : pending.entrySet()) {
			StudentInformation s = InformationExpert.getInstance().getStudentInformation(entry.getValue().ID);
			AppliedTable.getItems().add(new OffersData(s.ID,s.Name,s.Email,Float.toString(s.cgpa),Float.toString(s.performance)));
		}
		
		
	}
	@FXML
	private TableView<LeaderboardData> LeaderboardTable;
	@FXML
	private TableColumn<LeaderboardData, String> StudCGPA, StudEmail, StudID, StudName, StudPerformance, StudPosition;
	public void setLeaderboard() throws SQLException {
	    StudCGPA.setCellValueFactory(new PropertyValueFactory<>("CGPA"));
	    StudEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
	    StudID.setCellValueFactory(new PropertyValueFactory<>("Student_id"));
	    StudName.setCellValueFactory(new PropertyValueFactory<>("Student_name"));
	    StudPerformance.setCellValueFactory(new PropertyValueFactory<>("Performance"));
	    StudPosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
	    DBHandler handle = PersistantFactory.getInstance().getDatabase();
	    String sql = "SELECT s.Student_Id,s.Name, s.email, s.cgpa,s.SPerformance, IFNULL(cq.ContestQuizCount, 0) AS TotalContestsAndQuizzes "
	    		+ "FROM Students s LEFT JOIN ( SELECT S_Id, SUM(CASE WHEN Contest_Id IS NOT NULL THEN 1 ELSE 0 END) + "
	    		+ "SUM(CASE WHEN Quiz_Id IS NOT NULL THEN 1 ELSE 0 END) AS ContestQuizCount FROM (SELECT S_Id, Contest_Id, NULL AS Quiz_Id "
	    		+ "FROM EvaluateStudentContest UNION ALL SELECT S_Id, NULL AS Contest_Id, Quiz_Id FROM EvaluateStudentQuiz) Combined GROUP BY S_Id) cq "
	    		+ "ON s.Student_Id = cq.S_Id ORDER BY s.SPerformance DESC,s.CGPA DESC,TotalContestsAndQuizzes DESC;";
	    ResultSet rs = handle.ExecuteQuery(
	        sql,null,null
	    );
	    
	    int i=1;
	    if (rs != null) {
	        while (rs.next()) {
	            LeaderboardTable.getItems().add(
	                new LeaderboardData(
	                    Integer.toString(i),rs.getString("Student_Id"),rs.getString("Name"),rs.getString("Email"),
	                    Float.toString(rs.getFloat("CGPA")),Float.toString(rs.getFloat("Sperformance"))
	                )
	            );
	            i++;
	        }
	    }
	}
	
	public void setStudentsTable() throws SQLException {
		List<TableColumn<OffersData, String>> columns = new ArrayList<>();
		columns.add(id);columns.add(name);columns.add(emails);
		columns.add(cgpa);columns.add(performance);
		DBHandler handle = PersistantFactory.getInstance().getDatabase();

		ResultSet rs = handle.ExecuteQuery("SELECT "
				+ "Student_Id, Name, Email, CGPA, SPerformance FROM students "
				+ "ORDER BY SPerformance DESC", null,null);
		HashMap<String, Student> students = new HashMap<>();
		while(rs != null && rs.next()) {
			students.put(rs.getString(1), new Student(rs.getString(1)));
		}
		currentUser.setEnrolledTable(columns, OffersTable,students);
		handle.CloseConnection();
	}
	public void setContestsTable() throws SQLException {
		List<TableColumn<UpComingContest, String>> columns = new ArrayList<>();
    	columns.add(ContestID);columns.add(ContestName);columns.add(StartDate);columns.add(EndDate);
		Contest.setContestsTable(columns, ContestTable);
	}
	public void setEnrolledTable() throws SQLException {
		List<TableColumn<OffersData, String>> columns = new ArrayList<>();
		columns.add(id1);columns.add(name1);columns.add(emails1);
		columns.add(cgpa1);columns.add(performance1);
		HashMap<String, Student> enrolled = InformationExpert.getInstance().getIndustryInformation(currentUser.ID).getEnrolledStudents();
		
		currentUser.setEnrolledTable(columns, AppliedTable,enrolled);
	}
}
