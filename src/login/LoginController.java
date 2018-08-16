package login;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.mysql.jdbc.PreparedStatement;

import baseDeDonnées.ConnectionDB;
import javaBeansClass.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;;

public class LoginController implements Initializable{
	
	@FXML private AnchorPane paneLogin;
//	private static long temps = 0;
	@FXML private Label signError;
	@FXML public TextField loginnfild;
	@FXML private PasswordField psswFild;
	
	public TextField getLoginnFild() { return this.loginnfild; };

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		Utilisateur nr = new Utilisateur();
//		nr.setLogin(loginnfild.getText());
		
		setText(loginnfild.getText());
		
//		System.out.println(LoginController.);
		
		
	}
// ------------------------------------------------------------------------------	
		// REDIRECTION SUR ACCUEIL ! CAISSIER / DIRECTEUR / RESPONSABLE DE STOCKS
		@FXML
		private void validerConnexion(ActionEvent event) throws IOException {
			
			try {
				
				Connection connexion = ConnectionDB.maConnection();
				String sql = "SELECT * from Utilisateur WHERE (login=? || telephone=?) AND password=?";

				PreparedStatement pst = (PreparedStatement) connexion.prepareStatement(sql);
				pst.setString(1, loginnfild.getText().trim());
				pst.setString(2, loginnfild.getText().trim());
				pst.setString(3, psswFild.getText());
				ResultSet rs = pst.executeQuery();
				

				String logRole = null;
				
				if (rs.next()) {
					logRole = rs.getString("role");
//					// ------------------------------------
					
					//-------------------------------------
					if (logRole.equalsIgnoreCase("Administrateur")) {
						
//						Stage stg = new Stage();
						Parent pane = FXMLLoader.load(getClass().getResource("/DirecteurGeneral/Accueil.fxml"));
						paneLogin.getChildren().setAll(pane);
//						Scene scene = new Scene(pane);
//						scene.getStylesheets().add(getClass().getResource("AjoutArticle.css").toExternalForm());
//						stg.setScene(scene);
//						stg.show();
						
					} else if (logRole.equalsIgnoreCase("Responsable de stock")) {
						
						Parent pane = FXMLLoader.load(getClass().getResource("/caissier/Accueil.fxml"));
						paneLogin.getChildren().setAll(pane);
						
					} else if (logRole.equalsIgnoreCase("Responsable commercial")) {
//						
						Parent pane = FXMLLoader.load(getClass().getResource("/responsableDeStocks/Accueil.fxml"));
						paneLogin.getChildren().setAll(pane);

					} 
					else {
						System.out.println("Pas confome");
						AnchorPane pane = FXMLLoader.load(getClass().getResource("Login.fxml"));
						paneLogin.getChildren().setAll(pane);
						effacer();
					}
				
				} else {
					signError.setText("Veuillez vérifier vos identifiants.");
					effacer();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
}		
//////////////////////////////////////////
		//////////////////////////

public void effacer() {
	loginnfild.clear();
	psswFild.clear();
}		
//---------------------------------------------------
//---------------------------------------------------
public void setText(String loginnfild) {
	this.loginnfild.setText(loginnfild);
}


public void setTexttt() {
	String log = loginnfild.getText();
	System.out.println(log);
}
	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	///////// RECUPERER LA MARQUE DE L'ORDINATEUR
		public static String marqueDeLordinateur() {
			String markDeLOrdinateur = null;
			try {
				final InetAddress addr = InetAddress.getLocalHost();
				markDeLOrdinateur = new String(addr.getHostName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return markDeLOrdinateur;
		}
	//-----------------------------------------------------------------------
	//----------------------------------------------------------------------
		//---------------------------
}

