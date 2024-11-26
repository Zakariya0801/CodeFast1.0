package application;
	
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.Student.Student;
import application.Student.Contest.Contest;
import application.Student.Contest.Problem;
import databasepackage.DBHandler;
import databasepackage.MySqlHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application{
	
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxmlFiles/Login.fxml"));
			Scene scene = new Scene(root);
			Image logo = new Image("/img/Logo.png");
			stage.getIcons().add(logo);
			stage.setTitle("CodeFast");
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws NumberFormatException, SQLException {
		InformationExpert.getInstance();
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		ResultSet rs = handle.ExecuteQuery("Select Student_id from Students;", null, null);
		int maxCount = 0;
		while(rs.next()) {
			maxCount = Math.max(Integer.parseInt( rs.getString(1).substring(3)),maxCount);
		}
		Student.setCount(maxCount+1);
		rs = handle.ExecuteQuery("Select Contest_id from Contest;", null, null);
		maxCount = 0;
		while(rs.next()) {
			maxCount = Math.max(Integer.parseInt( rs.getString(1).substring(8)),maxCount);
		}
		Contest.SetCount(maxCount+1);
		
		rs = handle.ExecuteQuery("Select P_id from Problem;", null, null);
		maxCount = 0;
		while(rs.next()) {
			maxCount = Math.max(Integer.parseInt(rs.getString(1).substring(2)),maxCount);
		}
		Problem.setCount(maxCount+1);
		System.out.println("\n\n");
		launch(args);
//		InformationExpert.getInstance();
//		System.out.println("YESSSSSSSSS");
//		InformationExpert.getInstance().print();
	}

}
