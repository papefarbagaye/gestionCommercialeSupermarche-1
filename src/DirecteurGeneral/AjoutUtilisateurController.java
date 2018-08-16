package DirecteurGeneral;


import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.PreparedStatement;

import baseDeDonnées.ConnectionDB;
import javaBeansClass.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class AjoutUtilisateurController implements Initializable{
	
	@FXML private AnchorPane utilisateurPane;
	@FXML private TableView<Utilisateur> tableViewUtilisateur;
	
//	@FXML private TextField randomIdUz;
	@FXML private TextField refNom;
	@FXML private TextField refPrenom;
	@FXML private TextField refAdress;
	@FXML private TextField refTelephone;
	@FXML private TextField refEmail;
	@FXML private TextField refeLoginUSer;
	@FXML private PasswordField refpassword;
	@FXML private Label duJour;
	
	Stage primaryStage;
	
	@FXML private TableColumn<Utilisateur, String> colonnePrenom;
	@FXML private TableColumn<Utilisateur, String> colonneId;
	ObservableList<Utilisateur> UtilisateurList = FXCollections.observableArrayList();
	
	@FXML private CheckBox CaissierChechbox;
	@FXML private CheckBox AdminChechbox;
	@FXML private CheckBox RespoStokChechbox;
	
	@FXML Label LabTof;
	@FXML ImageView imageSet;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ActualiserDonneesFournisseurTableau();	
		tableViewUtilisateur.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		dateDuJourMethode();  // DATE & HEURE ACTUEL
		genereRandom(); // METHODE RANDON GENERE ID IRTICLE
		ControlChiffPhone(); // CONTROLE DE SAISIT NUMERO TELEPHONE
		
	}
	
	//////////////////////////////////////////////////
	
	
	public String genereRandom() {	
		
		final Random RANDOM = new SecureRandom();
//		String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";
		String letters = "0123456789";
		String pw = "";
		
	      for (int i = 0; i < 6; i++)
	      {
	    	  int index = (int)(RANDOM.nextDouble()*letters.length());
	          pw += letters.substring(index, index+1);     
	      }
	      
	      refeLoginUSer.setText(""+ pw);
		return pw;
}
	
	//-----------------------------------------------------------------------------------
	public void dateDuJourMethode() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
//		duJour.setText("Date : "+reportDate);
}
	//-----------------------------------------------------------------------------------

	// METHODE VALIDER NOM
	@SuppressWarnings("unused")
	private boolean validerNom() {

		Pattern p = Pattern.compile("[a-zA-Z]+", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(refNom.getText());

		if (m.find() && m.group().equals(refNom.getText())) {
			return true;

		} else {
			Alert alerte = new Alert(AlertType.WARNING);
			alerte.setTitle("Attention");

			alerte.setContentText("Entrer un Nom valide SVT!!");
			alerte.showAndWait();
		}
		return false;
	}
	//----------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
		@SuppressWarnings("unused")
	private boolean validerPrenom() {
			
		Pattern p = Pattern.compile("[a-zA-Z]+", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(refPrenom.getText());

		if (m.find() && m.group().equals(refPrenom.getText())) {
			return true;

		} else {
			Alert alerte = new Alert(AlertType.WARNING);
			alerte.setTitle("Attention");
			alerte.setContentText("Entrer un Prénom valide SVT!!");
			alerte.showAndWait();
		}
		return false;
	}
	//----------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
		// ---------------------------------------------------------------------------------
		@SuppressWarnings("unused")
	private boolean validerAdresse() {
			
		Pattern p = Pattern.compile("[a-zA-Z]+", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(refPrenom.getText());

		if (m.find() && m.group().equals(refPrenom.getText())) {
			return true;

		} else {
			Alert alerte = new Alert(AlertType.WARNING);
			alerte.setTitle("Attention");
			alerte.setContentText("Entrer un Prénom valide SVT!!");
			alerte.showAndWait();
		}
		return false;
	}
	@SuppressWarnings("unused")
	private boolean validerTelephone() {

		Pattern p = Pattern.compile("^?[0-9]{2}\\-?[0-9]{3}\\-?[0-9]{2}\\-?[0-9]{2}$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(refTelephone.getText());

		if (m.find() && m.group().equals(refTelephone.getText())) {
			return true;

		} else {
			Alert alerte = new Alert(AlertType.WARNING);
			alerte.setTitle("Attention");
			alerte.setContentText("Entrer un bon Numero Téléphone SVT!!");
			alerte.showAndWait();
		}
		return false;
	}
	//----------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
		// ACTUALISER LES DONNEES SUR TABLEAU
		public void ActualiserDonneesFournisseurTableau() {
			UtilisateurList.clear(); // EFFACE REPERTION DONNEES TABLEAU
			Connection connexion = ConnectionDB.maConnection();
			
			String requetteIni = "SELECT login, prenom FROM Utilisateur"; 
			
			try {
				PreparedStatement pst = (PreparedStatement) connexion.prepareStatement(requetteIni);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					UtilisateurList.addAll(new Utilisateur(rs.getInt(1), rs.getString(2) ));
				}
				tableViewUtilisateur.setItems(UtilisateurList);
				colonneId.setCellValueFactory(new PropertyValueFactory<>("id"));
				colonnePrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
				
				
			} catch (Exception exActualiserDonneesFournisseurTableau) {
				Logger.getLogger(AjoutFournisseurController.class.getName()).log(Level.SEVERE, null, exActualiserDonneesFournisseurTableau);
			}
		}
		
		// CHOISIR UNE PHOTO
		@FXML
			private void prendrePhoto(ActionEvent event) throws IOException {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("FileChooser Example");
			
			File homeDir = new File(System.getProperty("user.home"));
			fileChooser.setInitialDirectory(homeDir);
			
			fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
			
			File selectedFile = fileChooser.showOpenDialog(primaryStage);
			if (selectedFile != null) {
			try {
			//--- Open the file with associated application
			Desktop.getDesktop().open(selectedFile);
			}
			catch (Exception e) {
			System.err.println("ERROR: Unable to open the file");
			}
			}
		}
		//----------------------------------------------------------------------------------
		// ---------------------------------------------------------------------------------
		@SuppressWarnings("unused")  // CONTROLE DE SAISIE NUMERO DE TELEPHONE
		private boolean validerTelephoneMethode() {

			Pattern p = Pattern.compile("^?[0-9]{2}\\-?[0-9]{3}\\-?[0-9]{2}\\-?[0-9]{2}$", Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(refTelephone.getText());

			if (m.find() && m.group().equals(refTelephone.getText())) {
				return true;

			} else {
				Alert alerte = new Alert(AlertType.WARNING);
				alerte.setTitle("Attention");
				alerte.setContentText("Entrer un bon Numero Téléphone SVT!!");
				alerte.showAndWait();
			}
			return false;
		}
		//----------------------------------------------------------------------------------
		public void ControlChiffPhone() {  // CE GENRE DE METHODE ON LES APPELLE DIRECTEMENT DANS LA METHODE QUI RECHARGE LES DONNEES AUTOMATIQUE
			refTelephone.setOnKeyTyped(e -> {
				String ch = e.getCharacter();
				if (!(ch.equals("0") || ch.equals("1") || ch.equals("2") | ch.equals("3") || ch.equals("4")
						|| ch.equals("5") || ch.equals("6") || ch.equals("7") || ch.equals("8") || ch.equals("9")
						|| ch.equals("BACK_SPACE")) || (!(refTelephone.getText().length() < 9))) {
					e.consume();
					java.awt.Toolkit.getDefaultToolkit().beep();
				}
			});
		}
	//-----------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------
		@SuppressWarnings("unused")
		private boolean validerEmail() { // CONTROLE DE SAISIE ADRESSE EMAIL

			Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(refEmail.getText());

			if (m.find() && m.group().equals(refEmail.getText())) {
				return true;

			} else {
				Alert alerte = new Alert(AlertType.WARNING);
				alerte.setTitle("Attention");

				alerte.setContentText("Entrer un E-mail valide SVT!!");
				alerte.showAndWait();
			}
			return false;
		}
		//----------------------------------------------------------------------------------
		//--------------------------------------------------------------------------------	
			// APPEERSU D'UNE LIGNE SUR LE TABLEAU UNE FOIS CLIQUER
			public void AfficheTableViewChampsAjoutUtilsateur(ActionEvent event) {
				
				Connection connexion = ConnectionDB.maConnection();
				String requetteTAb = "SELECT nom, prenom, adresse, telephone, login, password, email, image, role from Utilisateur";
				
				try {
					PreparedStatement pst = (PreparedStatement) connexion.prepareStatement(requetteTAb);
					ResultSet rs = pst.executeQuery();
					while (rs.next()) {
						Utilisateur mat = tableViewUtilisateur.getSelectionModel().getSelectedItem();
						
						refNom.setText(rs.getString(1));		
						refPrenom.setText(rs.getString(2));									
						refAdress.setText(rs.getString(3));						
						refTelephone.setText(rs.getString(4));				
						refEmail.setText(rs.getString(5));				
						refeLoginUSer.setText(mat.getLogin());		
						
						refTelephone.setText(rs.getString(6));				
//						refEmail.setText(rs.getString(7));				
//						refeLoginUSer.setText(rs.getString(8));
//						refeLoginUSer.setText(rs.getString(9));
						
					}
				} catch (Exception e) {
					
				}
				
				
			}
//---------------------------------------------------------------------------------
//---------------------------------------------------------------------------------
			// CONTROLE CHECKBOX CQISSIER
			@FXML 
			public void checkBoxCaissier() {
				if(CaissierChechbox.isSelected()) {
					AdminChechbox.setSelected(false);
					RespoStokChechbox.setSelected(false);
				}
			}
			
			// CONTROLE CHECKBOX CQISSIER
			@FXML 
			public void checkBoxAdmin() {
				if(AdminChechbox.isSelected()) {
					CaissierChechbox.setSelected(false);
					RespoStokChechbox.setSelected(false);
				}
			}
			
			// CONTROLE CHECKBOX CQISSIER
			@FXML 
			public void checkBoxResponsableStock() {
				if(RespoStokChechbox.isSelected()) {
					CaissierChechbox.setSelected(false);
					AdminChechbox.setSelected(false);
				}
			}		
//-----------------------------------------------------------------------------------	
//-----------------------------------------------------------------------------------
		public void retourMnu() throws IOException {  // UTILISATEUR RETOUR MENU - CLIIQUE SUR LA PHOTO
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/DirecteurGeneral/Accueil.fxml"));
		utilisateurPane.getChildren().setAll(pane);
		}			
		// ----------------------------------------------------------------------------------	
		//-----------------------------------------------------------------------------------
		@FXML
		public void UploadImage() throws IOException {  
			
			FileChooser fileChooser = new FileChooser();
			File file = fileChooser.showOpenDialog(primaryStage);
			if(file != null) {
				LabTof.setText(file.getAbsolutePath());
				Image imgae = new Image(file.toURI().toString(), 100, 150, true, true);
				
				ImageView imageView = new ImageView(imgae);
				imageView.setFitWidth(100);
				imageView.setFitHeight(150);
				imageView.setPreserveRatio(true);
//				imageSet.      	// IMAGEVIEW A AJOUTER DANS ... RESTE
				
				
			}
			
			
		}			
			// ----------------------------------------------------------------------------------	
			//-----------------------------------------------------------------------------------
		public void ajoutUtilsateur() throws FileNotFoundException, SQLException {
			Connection connexion = ConnectionDB.maConnection();
			
			String rekett = "UPDATE Utilsateur set nom=?, prenom=?,adresse=?,telephone=?,login=?,password=?,email=?,image=? WHERE id=?";
			PreparedStatement pst = (PreparedStatement) connexion.prepareStatement(rekett);
			
			// IMAGE USING
			FileChooser fileChooser = new FileChooser();
			File file = fileChooser.showOpenDialog(primaryStage);
			FileInputStream fis = new FileInputStream(file);
			
			pst.setString(1, refNom.getText());
			pst.setString(2, refPrenom.getText());
			pst.setString(3, refAdress.getText());
			pst.setString(4, refTelephone.getText());
			pst.setString(5, refeLoginUSer.getText());
			pst.setString(6, refpassword.getText());
			pst.setString(7, refEmail.getText());
			pst.setBinaryStream(8, (InputStream)fis, (int)file.length());  // PONITER SUR LA CELLULE IMAGE
			
			
		}
		
		
// ---------------------------------------------------
//---------------------------------------
}
