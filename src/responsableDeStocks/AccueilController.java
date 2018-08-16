package responsableDeStocks;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class AccueilController implements Initializable{

	@FXML private AnchorPane rootPane;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
	// REDIRECTION LOGIN
		@FXML
		private void deconnexion(ActionEvent event) throws IOException {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
			rootPane.getChildren().setAll(pane);
		}
}
