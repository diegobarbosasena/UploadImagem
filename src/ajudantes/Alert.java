package ajudantes;

import javafx.scene.control.Alert.AlertType;

public class Alert {

	public static void showAlertInformation() {
		
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.INFORMATION);
		
		alert.setContentText("Selecione um arquivo");
		alert.showAndWait();
	}
	
}
