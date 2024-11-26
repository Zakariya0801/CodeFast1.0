package application.Industry;

import databasepackage.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.InformationExpert;
import application.PersistantFactory;
import application.User;
import application.UserInformation;
import controllers.AlertSender;
import controllers.TablesData.OffersData;
import controllers.TablesData.StudentMainData;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

public class Industry extends User {
	private static int IDCount = 0;
	public Industry() {
		super("ID_" + String.valueOf(IDCount));
		IDCount++;
	}
	public Industry(String id) {
		ID = id;
	}
	public IndustryInformation getInformation() {
		try {
			return InformationExpert.getInstance().getIndustryInformation(ID);
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String getTableName() {
		return "Industry";
	}
	@Override
	public String getIDName() {
		return "Industry_id";
	}
	@Override
	public void acceptStudentRequest(ActionEvent e, TableView<OffersData> StudentRegistrationTable) throws SQLException {
		OffersData selectedData = StudentRegistrationTable.getSelectionModel().getSelectedItem();
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(selectedData.getStudent_ID()); types.add("string");
		values.add(ID); types.add("string");
		
		handle.ExecuteUpdate("UPDATE StudentIndustry "
				+ "SET Status = 1 "
				+ "WHERE S_Id = ? AND I_Id = ?;\r\n"
				+ "", values, types);
		InformationExpert.getInstance().setAsAccepted(ID, selectedData.getStudent_ID());
	}
	@Override
	public void rejectStudentRequest(ActionEvent e, TableView<OffersData> StudentRegistrationTable) throws SQLException {
		OffersData selectedData = StudentRegistrationTable.getSelectionModel().getSelectedItem();
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(selectedData.getStudent_ID()); types.add("string");
		values.add(ID); types.add("string");
		handle.ExecuteUpdate("UPDATE StudentIndustry "
				+ "SET Status = -1 "
				+ "WHERE S_Id = ? AND I_Id = ?;"
				+ "", values, types);
		InformationExpert.getInstance().setAsRejected(ID, selectedData.getStudent_ID());
	}
	@Override
	public void offerInternship(ActionEvent e, TableView<OffersData> StudentRegistrationTable) throws SQLException {
		OffersData selectedData = StudentRegistrationTable.getSelectionModel().getSelectedItem();
		InformationExpert.getInstance().setAsRequested(ID, selectedData.getStudent_ID());
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(selectedData.getStudent_ID()); types.add("string");
		values.add(ID); types.add("string");
		handle.ExecuteUpdate("INSERT INTO StudentIndustry (S_Id, I_Id, Request, Status)\r\n"
				+ "VALUES (?, ?, 1, 0);", values, types);
		Alert alert = AlertSender.SendInformation( "SUCCESSFULL", "The Offer has been sent Successfully");
		alert.showAndWait();
	}
	@Override
	public void removeStudent(String studentID) throws SQLException {
		if(!this.getInformation().isEnrolled(studentID)) {
			AlertSender.SendWarning("Not Enrolled", "Student not enrolled and can't be removed").showAndWait();
			return;
		}
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(studentID); types.add("string");
		values.add(ID); types.add("string");
		handle.ExecuteUpdate("DELETE FROM StudentIndustry "
				+ "Where S_id = ? and I_id = ?", values, types);
		InformationExpert.getInstance().removeStudent(ID, studentID);
		Alert alert = AlertSender.SendInformation( "SUCCESSFULL", "Student has been removed successfully");
		alert.showAndWait();
	}
}
