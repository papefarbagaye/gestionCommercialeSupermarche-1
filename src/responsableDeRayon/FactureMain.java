package responsableDeRayon;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class FactureMain extends Application {
	@Override
	public void start(Stage StgFacture) {
		
		StgFacture.setTitle("FACTURE");
		StgFacture.setResizable(false);
		
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Facture.fxml"));
			Scene scene = new Scene(root,950,500);
			scene.getStylesheets().add(getClass().getResource("Facture.css").toExternalForm());
			StgFacture.setScene(scene);
			StgFacture.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
