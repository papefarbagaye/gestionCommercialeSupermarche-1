package caissier;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class DetailsFactureMain extends Application {
	@Override
	public void start(Stage StgDetailsFacture) {

		StgDetailsFacture.setTitle("DETAILS FACTURE");
		StgDetailsFacture.setResizable(false);

		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("DetailsFacture.fxml"));
			Scene scene = new Scene(root, 950, 500);
			scene.getStylesheets().add(getClass().getResource("DetailsFacture.css").toExternalForm());
			StgDetailsFacture.setScene(scene);
			StgDetailsFacture.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
