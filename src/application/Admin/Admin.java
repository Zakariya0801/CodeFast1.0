package application.Admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.InformationExpert;
import application.PersistantFactory;
import application.Singleton;
import application.User;
import application.UserInformation;
import application.Student.Contest.Contest;
import controllers.TablesData.CourseRequest;
import controllers.TablesData.StudentMainData;
import databasepackage.DBHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;

public class Admin extends User {
	private static int IDCount = 0;
	public Admin() {
		super("AD_" + String.valueOf(IDCount));
		IDCount++;
	}
	public Admin(String id) {
		
	}
	public UserInformation getInformation() throws SQLException {
		return new UserInformation(ID);
	}
	@Override
	public String getTableName() {
		return "Admin";
	}
	@Override
	public String getIDName() {
		return "Admin_Id";
	}
	@Override
	public void acceptStudentRegistration(ActionEvent e, TableView<StudentMainData> StudentRegistrationTable) throws SQLException {
		StudentMainData selectedData = StudentRegistrationTable.getSelectionModel().getSelectedItem();
		if(selectedData == null) return;
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(selectedData.getStudentID()); types.add("string");
		
		handle.ExecuteUpdate("Update Students "
				+ "SET Status=1 "
				+ "Where Student_id= ?;", values, types);
		
		InformationExpert.getInstance().getStudentInformation(selectedData.getStudentID()).setAsRegistered();
		handle.CloseConnection();
	}
	@Override
	public void rejectStudentRegistration(ActionEvent e, TableView<StudentMainData> StudentRegistrationTable) throws SQLException {
		StudentMainData selectedData = StudentRegistrationTable.getSelectionModel().getSelectedItem();
		if(selectedData == null) return;
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(selectedData.getStudentID()); types.add("string");
		
		handle.ExecuteUpdate("DELETE from "
				+ "Students "
				+ "Where Student_id= ?;", values, types);
		handle.CloseConnection();
	}
	@Override
	public void acceptPremiumPlan(ActionEvent e, TableView<StudentMainData> StudentRegistrationTable) throws SQLException {
		StudentMainData selectedData = StudentRegistrationTable.getSelectionModel().getSelectedItem();
		if(selectedData == null) return;
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(selectedData.getStudentID()); types.add("string");
		
		handle.ExecuteUpdate("Update Students "
				+ "SET SubscribedPlan=1 "
				+ "Where Student_id= ?;", values, types);
		handle.CloseConnection();
	}
	@Override
	public void rejectPremiumPlan(ActionEvent e, TableView<StudentMainData> StudentRegistrationTable) throws SQLException {
		StudentMainData selectedData = StudentRegistrationTable.getSelectionModel().getSelectedItem();
		if(selectedData == null) return;
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(selectedData.getStudentID()); types.add("string");
		
		handle.ExecuteUpdate("Update Students "
				+ "SET SubscribedPlan=-1 "
				+ "Where Student_id= ?;", values, types);
		handle.CloseConnection();
	}
	@Override
	public void rejectCourseRegistration(ActionEvent e, TableView<CourseRequest> StudentRegistrationTable) throws SQLException {
		CourseRequest selectedData = StudentRegistrationTable.getSelectionModel().getSelectedItem();
		if(selectedData == null) return;
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(selectedData.getStudent_id()); types.add("string");
		values.add(selectedData.getCourse_id()); types.add("string");
		
		handle.ExecuteUpdate("Update CourseRegistration "
				+ "SET Status=-1 "
				+ "Where S_id= ? and C_id= ? ;", values, types);
		handle.CloseConnection();
	}
	@Override
	public void acceptCourseRegistration(ActionEvent e, TableView<CourseRequest> StudentRegistrationTable) throws SQLException {
		CourseRequest selectedData = StudentRegistrationTable.getSelectionModel().getSelectedItem();
		if(selectedData == null) return;
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(selectedData.getStudent_id()); types.add("string");
		values.add(selectedData.getCourse_id()); types.add("string");
		
		handle.ExecuteUpdate("Update CourseRegistration "
				+ "SET Status=1 "
				+ "Where S_id= ? and C_id= ? ;", values, types);
		InformationExpert.getInstance().getStudent(selectedData.getStudent_id()).getInformation().setRegistered(selectedData.getCourse_id());
	}
	@Override 
	public boolean MakeNewContest(String ContName,String Start,String End,String TestCaseInp,String TestCaseOut,String Statement) throws SQLException {
		Contest c = Singleton.getInstance().createNewContest(ContName, Start, End);
		return this.addProblem(c, TestCaseInp, TestCaseOut, Statement);
	}
	@Override
	public boolean addProblem(Contest c,String TestCaseInp,String TestCaseOut,String Statement) throws SQLException {
		Singleton.getInstance().createProblem(c, Statement, TestCaseInp, TestCaseOut);
		return true;
	}
	@Override
	public void removeStudentAdmin(String StudentID) throws SQLException{
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(StudentID);types.add("string");
		String[] queries = {
				"DELETE FROM StudentIndustry WHERE S_Id =?",
				"DELETE FROM evaluatestudentcontest WHERE S_Id =?",
				"DELETE FROM evaluatestudentquiz WHERE S_Id =?",
				"DELETE FROM CourseRegistration WHERE S_Id=?",
				"DELETE FROM Students WHERE Student_id=?"
		};
		for(String q : queries) {
			
			handle.ExecuteUpdate(q, values, types);
			System.out.println("Removed successfully");
		}
		InformationExpert.getInstance().removeStudent(StudentID);
	}
}