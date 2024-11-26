package controllers;

import java.io.IOException;
import java.sql.SQLException;

import application.UserInformation;
import javafx.event.ActionEvent;
import javafx.scene.chart.BarChart;

public interface UserController {
	void switchtoDashboard(ActionEvent e) throws IOException, SQLException;
	void switchtoCareer(ActionEvent e) throws IOException, SQLException;
	void switchtoContests(ActionEvent e)throws IOException, SQLException ;
	void switchtoSettings(ActionEvent e) throws IOException;
	void switchtoJobInternship(ActionEvent e) throws IOException, SQLException;
	void switchtoCourses(ActionEvent e) throws IOException, SQLException;
	void switchtoFeedback(ActionEvent e) throws IOException, SQLException;
	void switchtoStudyMaterial(ActionEvent e, UserInformation user) throws IOException, SQLException;
	void switchtoAccount(ActionEvent e) throws IOException;
	public void switchtoContestGeneric(ActionEvent e, String ContestID) throws IOException, SQLException;
	public void setBar(BarChart<String, Integer> StudentPart) throws SQLException;
}
