package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import application.PersistantFactory;
import application.User;
import application.UserInformation;
import databasepackage.DBHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingsController {
	protected Stage stage;
	protected Scene scene;
	protected Parent root;
	protected static UserInformation user = null;
	protected static User currentUser = null;
	protected static UserController userController = null;
	public static void SetUser(UserInformation u, User us, UserController uc) {
		user = u;
		currentUser = us;
		userController = uc;
	}
	public void updateName(ActionEvent e, TextField NameRegis) throws SQLException {
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(NameRegis.getText()); types.add("string");
		values.add(user.ID); types.add("string");
		handle.ExecuteUpdate("UPDATE " + currentUser.getTableName()+ " "
				+ "SET Name = ? "
				+ "WHERE " + currentUser.getIDName() + " = ?;", values,types);
		handle.CloseConnection();
		user.Name = NameRegis.getText();
	}
	public void updateEmail(ActionEvent e, TextField EmailRegis) throws SQLException {
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		String email = EmailRegis.getText();
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if(!pattern.matcher(email).matches()) {
        	AlertSender.SendWarning("Invalid Email", "Email Format is not correct").showAndWait();
        	return;
        }
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(EmailRegis.getText()); types.add("string");
		values.add(user.ID); types.add("string");
		handle.ExecuteUpdate("UPDATE " + currentUser.getTableName()+ " "
				+ "SET Email = ? "
				+ "WHERE " + currentUser.getIDName() + " = ?;", values,types);
		handle.CloseConnection();
		user.Email = EmailRegis.getText();
	}
	public void updatePassword(ActionEvent e, PasswordField password, PasswordField PasswordConfirm) throws SQLException {
		if(!password.getText().equals( PasswordConfirm.getText())) {
			Alert alert = AlertSender.SendWarning( "Incorrect Password", "Password Entered is Incorrect");
			alert.showAndWait();
			return;
		}
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(password.getText()); types.add("string");
		values.add(user.ID); types.add("string");
		handle.ExecuteUpdate("UPDATE " + currentUser.getTableName()+ " "
				+ "SET password = ? "
				+ "WHERE " + currentUser.getIDName() + " = ?;", values,types);
		handle.CloseConnection();
		user.Password = password.getText();
	}
	@FXML
	public void switchtoSettings(ActionEvent e) throws IOException {
		userController.switchtoSettings(e);
	}
}
