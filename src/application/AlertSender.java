package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class AlertSender {
	
	public static Alert SendWarning(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.setHeaderText("REJECTED");
//		alert.showAndWait();
		return alert;
	}
	public static Alert SendInformation( String title, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.setHeaderText("");
//		alert.showAndWait();
		return alert;
	}
}
