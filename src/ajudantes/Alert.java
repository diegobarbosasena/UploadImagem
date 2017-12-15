package ajudantes;

import javafx.scene.control.Alert.AlertType;

public class Alert {

	public static void showAlertInformation(String messagem) {
		
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.INFORMATION);
		
		alert.setContentText(messagem);
		alert.showAndWait();
	}
	
	public static void showAlertError(String messagem) {
		
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.ERROR);
		
		alert.setContentText(messagem);
		alert.showAndWait();
	}
	
	public static void showAlertWarning(String messagem) {
		
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.WARNING);
		
		alert.setContentText(messagem);
		alert.showAndWait();
	}
	
}
