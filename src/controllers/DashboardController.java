package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import application.InformationExpert;
import application.PersistantFactory;
import application.Singleton;
import application.User;
import application.UserInformation;
import application.Student.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DashboardController {
	
	protected Stage stage;
	protected Scene scene;
	protected Parent root;
	@FXML
	TextField Userid;
    @FXML
	private TextField CGPAField;
    @FXML
    PasswordField password;
    @FXML
    private PasswordField PasswordConfirm;
    @FXML
    private TextField NameRegis;
    @FXML
    private TextField EmailRegis;
    @FXML
    private TextField Degree;
    protected static UserInformation user = null;
    protected static User currentUser = null;
    protected static UserController userController = null;
	@FXML
	public void switchtoSignUp(ActionEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/fxmlFiles/SignUp.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	public void switchtoLogin(ActionEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/fxmlFiles/Login.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public static void SetUser(UserInformation u, User us, UserController uc) {
		user = u;
		currentUser = us;
		userController = uc;
	}
	public void tryLogin(ActionEvent e) throws SQLException, IOException {
		if(Userid == null || password == null) {
			AlertSender.SendWarning("FIELDS MISSING","Enter all the required Fields").showAndWait();
			return;
		}
		String userID = Userid.getText();
		String pass = password.getText();
		UserInformation userinfo = InformationExpert.getInstance().getUser(userID);
		User curr = null;
		UserController uc = null;
		if(userinfo == null) {
			Alert alert = AlertSender.SendInformation( "INVALID Login", "Enter Fields correctly");
			alert.showAndWait();
		}
		else if(userinfo.Password.equals(pass)){
			curr = PersistantFactory.getInstance().getUser(userID);
			userinfo =  PersistantFactory.getInstance().getUserInformation(userID);
			uc =  PersistantFactory.getInstance().getUserController(userID);
			SceneController.SetUser(userinfo,curr,uc);
			uc.switchtoDashboard(e);
		}
		else {
			Alert alert = AlertSender.SendInformation("INVALID Password", "Enter Password correctly");
			alert.showAndWait();
		}
	}
	public void trySignUp(ActionEvent e) throws SQLException, IOException {
		if(NameRegis == null || password == null || PasswordConfirm == null || Degree == null 
				|| EmailRegis == null || CGPAField == null) {
			AlertSender.SendWarning("FIELDS MISSING","Enter all the required Fields").showAndWait();
			return;
		}
		String name = NameRegis.getText();
		String pass = password.getText();
		String passconf = PasswordConfirm.getText();
		String degree = Degree.getText();
		String email = EmailRegis.getText();
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if(!pattern.matcher(email).matches()) {
        	AlertSender.SendWarning("Invalid Email", "Email Format is not correct").showAndWait();
        	return;
        }
		float cgpa = Float.parseFloat(CGPAField.getText());
		if(pass.equals(passconf)) {
			Student s = Singleton.getInstance().getStudent(name, email, passconf, degree, cgpa);	
			AlertSender.SendInformation("Signed Up Successfully", "You have been signed up with ID: " + s.ID + ". Pending Admin Approval").showAndWait();
		}
		else 
			AlertSender.SendWarning("INCORRECT PASSWORD","Password does not match").showAndWait();
	}
}
