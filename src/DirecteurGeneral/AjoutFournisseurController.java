package DirecteurGeneral;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.List;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.PreparedStatement;

import BoiteAlert.Confirmation;
import baseDeDonnées.ConnectionDB;
import javaBeansClass.Fournisseur;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AjoutFournisseurController implements Initializable{
	
	@FXML private AnchorPane paneFournisseur;
	
	@FXML private TextField textFieldRaisonSociale;
	@FXML private TextField TextFieldSigle;
	@FXML private TextField TextFieldTelephone;
	@FXML private TextField TextFieldAdresse;
	@FXML private TextField TextFieldCourriel;
	@FXML private Label duJour;
	
	@FXML private TextField recherch;
	Fournisseur fourni;
	
	@FXML private TableView<Fournisseur> tableViewFournisseur;
	
	@FXML private TableColumn<Fournisseur, String> colonneRaisonSociale;
	@FXML private TableColumn<Fournisseur, String> colonneSigle;
	@FXML private TableColumn<Fournisseur, String> colonneTelephone;
	@FXML private TableColumn<Fournisseur, String> colonneAdesse;
	@FXML private TableColumn<Fournisseur, String> colonneCourriel;
	
	String alertMesaz = "";
	
	private ObservableList<Fournisseur> fournisseurList = FXCollections.observableArrayList();
	
	protected ImageView bottom_bar_dt;
	
//---------------------------------------------------------------------------------
//---------------------------------------------------------------------------------	
//---------------------------------------------------------------------------------
//--------------------------------------------------------------------------------
	// CHARGEMENT AUTOMATIQUE
	@Override  
	public void initialize(URL location, ResourceBundle resources) {

		ActualiserDonneesFournisseurTableau();	
		//----------------GENERER LES MOTS AUTOMATIQUE
		dateDuJourMethode();   // GERE LA DATE ET HEURE AUTOMATIQUE
		genereRandom();		   // GERE LES ID AUTOMATIK
		ControlChiffPhone();  //  GERE LE CONTROLE CHIFFRES NUM PHONE
		rechercheFiltr();
		//--------------------------------------------------
		selctionAuto();
		//-------------------------------------------------------
//-------------------------------------------------------
		try {
			genererMot();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
//---------------------------------------------------------------------------------	
//---------------------------------------------------------------------------------

	// INSERTION FOURNISSEUR
	public void ajouterFournsseur() {
		
		Connection connexion = ConnectionDB.maConnection();
		
		String RaisonSocial = textFieldRaisonSociale.getText().trim();
		String Sigl = TextFieldSigle.getText().trim();
		String Telephon = TextFieldTelephone.getText();
		String Adess = TextFieldAdresse.getText().trim();
		String Couriel = TextFieldCourriel.getText();	
		
		if( validerTelephone() && validerEmail()) {
		
		try {
			String requetteInsertion = "INSERT INTO `Fournisseur`(`raisonSociale`, `sigle`, `telephone`, `adresse`, `email`) VALUES ('"+RaisonSocial+"','"+Sigl+"','"+Telephon+"','"+Adess+"','"+Couriel+ "')";
			
			int statut = connexion.createStatement().executeUpdate(requetteInsertion);
			if (statut != 0) {
				
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Fournisseur ajouté");
				alert.setHeaderText("Fournisseur a été bien ajouté");
				alert.showAndWait();
				
				new notificationThred().start();
				ActualiserDonneesFournisseurTableau();	
				EffacerLesChamps();
				
				// ------------------------------------------------------
		
				
/////////////////////////////////PDF TEST ///////////// PDF TEST /////////
/////////////////////////////////PDF TEST ////////////// PDF TEST /////////
				String chemin="BonsEntrant/pdfTest.pdf";				
				Document document = new Document();
			        
				try {

		            PdfWriter.getInstance(document, new FileOutputStream(new File(chemin)));
		            document.open();

		            Paragraph p = new Paragraph();
		            p.add("GESTION COMMERCIALE SUPERMARCHÉ \n");
		            p.add("----------------- \n");
		            p.add("DAKAR - 772774465 / 773667724");
		            p.add("\n ******************************************************* \n");
		            
		           
		            bottom_bar_dt = new ImageView( new Image( new File("../resources/icons/bottom_bar_dt.png").toURI().toString(), true));
//		            p.add(bottom_bar_dt);
//		            -------------------------------
		            for (int i = 0; i < 5; i++) {
		            	p.add("\n");
					}
		            p.setAlignment(Element.ALIGN_CENTER);
		            document.add(p);
		            // ------------------------------------ENTETE TABLEAU
		            
		            //-----------------------------------------
		            PdfPTable table = new PdfPTable(5);
		            table.setWidthPercentage(100);
		         //   table.getDefaultCell().setBorder(Rectangle.NO_BORDER); // ELIMIME LE BORDER
		            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table.getDefaultCell().setFixedHeight(30);
		            
		            PdfPCell enteteTatb = new PdfPCell(new Paragraph("header with colspan 3"));
		            enteteTatb.setColspan(5);
		            
//		            enteteTatb.setBorderColor(new Color(0xC0, 0xC0, 0xC0, 0xC0));
		            enteteTatb.setHorizontalAlignment(Element.ALIGN_CENTER);
		            table.addCell(enteteTatb);
		            //--------------------------------------- 
		            
		            table.addCell(new Phrase(new Chunk("RAISON SOCIALE", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD))));
				    table.addCell(new Phrase(new Chunk("SIGLE", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD))));
				    table.addCell(new Phrase(new Chunk("ADRESSE", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD))));
				    table.addCell(new Phrase(new Chunk("TÉLEPHONE", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD))));
				    table.addCell(new Phrase(new Chunk("E-mail", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD))));
			
				    //	    
				    
		            table.addCell(new Phrase(new Chunk(RaisonSocial)));
				    table.addCell(new Phrase(new Chunk(Sigl)));
				    table.addCell(new Phrase(new Chunk(Telephon)));
				    table.addCell(new Phrase(new Chunk(Adess)));
				    table.addCell(new Phrase(new Chunk(Couriel)));
				    document.add(table);
		            
		            //-------------------------------------------------------
		            Paragraph paragraphList = new Paragraph("A to E:");
		            List list = new List(false, 10);
		            list.add("A");
		            list.add("B");
		            list.add("C");
		            list.add("D");
		            list.add("E");
		            paragraphList.add(list);
		            document.add(paragraphList);
		            
		            //--------------------------------------------------------
		            Paragraph espaceVide = new Paragraph(" \n");
		            for (int i = 0; i < 7; i++) {
		            	p.add(espaceVide);
					}
		           //------------------------------------------------------
		            String tabulation = null;
		            for (int i = 0; i < 20; i++) {
						 tabulation ="\t";
					}
		            Paragraph piedDePageGauche = new Paragraph("Directeur " + tabulation + " Client");
		            piedDePageGauche.setAlignment(Element.ALIGN_LEFT);
		            document.add(piedDePageGauche);
		            //-------------------------------------------------------
		            //-------------------------------------------------------
		            
		            Font f = new Font();
		            f.setStyle(Font.BOLD);
		            f.setSize(8);

//		            document.add(new Paragraph("Fournisseur Ajouter ", f));
		            document.close();
		         
		        } catch (DocumentException | IOException e) {
		            e.printStackTrace();
		        
		        }
////////////////////////////////////////////////////////
////////////////////////////////////////////////////////
				
			} else { 
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Insertion Fournisseur n'est pas réussie");
				alert.setHeaderText("Echec Ajout Fournisseur");
				alert.showAndWait();
			}
			connexion.close();
			
		} catch (Exception exInsertFournisseur) {
			Logger.getLogger(AjoutFournisseurController.class.getName()).log(Level.SEVERE, null, exInsertFournisseur);
		}
	}
	}
//---------------------------------------------------------------------------------
	// EFFACER LES CHAMPS DU TABLEAU
	public void EffacerLesChamps() {
		textFieldRaisonSociale.clear();
		TextFieldSigle.clear();
		TextFieldTelephone.clear();
		TextFieldAdresse.clear();
		TextFieldCourriel.clear();
	}
//--------------------------------------------------------------------------------
//---------------------------------------------------------------------------------	
	// APPEERSU D'UNE LIGNE SUR LE TABLEAU UNE FOIS CLIQUER
	public void AfficheTableViewChampsFournisseur(ActionEvent event) {
		
		Fournisseur mat = tableViewFournisseur.getSelectionModel().getSelectedItem();
		
		textFieldRaisonSociale.setText(mat.getRaisonSociale());		
		TextFieldSigle.setText(mat.getSigle());						
		TextFieldTelephone.setText(mat.getTelephone());				
		TextFieldAdresse.setText(mat.getAdresse());					
		TextFieldCourriel.setText(mat.getEmail());					
	}
//---------------------------------------------------------------------------------
	
	public void ModifierFournisseur() {
		Connection connexion = ConnectionDB.maConnection();
		
		String RaisonSocial = textFieldRaisonSociale.getText();
		String Sigl = TextFieldSigle.getText();
		String Telephon = TextFieldTelephone.getText();
		String Adess = TextFieldAdresse.getText();
		String Couriel = TextFieldCourriel.getText();	
		
		try {	
			
			String updateFournisseur = "UPDATE Fournisseur SET raisonSociale='"+RaisonSocial+"', sigle='"+Sigl+"', telephone='"+Telephon+"', adresse='"+Adess+"', email='"+Couriel+"' WHERE raisonSociale='"+RaisonSocial+"' ";
			System.out.println(updateFournisseur);
				int statut = connexion.createStatement().executeUpdate(updateFournisseur);
				if (statut != 0) {
					System.out.println("Modification reuussii");
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Fournisseur modifié");
					alert.setHeaderText("Fournisseur a été bien modifié");
					alert.showAndWait();
				}
	//-------------------------------- APPEL METHODES --------------------------
			new notificationThred().start();
			ActualiserDonneesFournisseurTableau();
			EffacerLesChamps();
	//--------------------------------------------------------------------------		
		} catch (Exception exModifierFournisseur) {
			Logger.getLogger(AjoutFournisseurController.class.getName()).log(Level.SEVERE, null, exModifierFournisseur);
		}
	}

//-----------------------------------------------------------------------------------
	// ACTUALISER LES DONNEES SUR TABLEAU
	public void ActualiserDonneesFournisseurTableau() {
		fournisseurList.clear(); // EFFACE REPERTION DONNEES TABLEAU
		Connection connexion = ConnectionDB.maConnection();
		
		String requetteIni = "SELECT `raisonSociale`, `sigle`, `telephone`, `adresse`, `email` FROM `Fournisseur` "; 
		
		try {
			PreparedStatement pst = (PreparedStatement) connexion.prepareStatement(requetteIni);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				fournisseurList.addAll(new Fournisseur(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
			}
			tableViewFournisseur.setItems(fournisseurList);
			colonneRaisonSociale.setCellValueFactory(new PropertyValueFactory<>("raisonSociale"));
			colonneSigle.setCellValueFactory(new PropertyValueFactory<>("sigle"));
			colonneTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
			colonneAdesse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
			colonneCourriel.setCellValueFactory(new PropertyValueFactory<>("email"));
			
			
		} catch (Exception exActualiserDonneesFournisseurTableau) {
			Logger.getLogger(AjoutFournisseurController.class.getName()).log(Level.SEVERE, null, exActualiserDonneesFournisseurTableau);
		}
	}
	
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

	public class notificationThred extends Thread {
		@Override
		public void run(){
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
				ex.printStackTrace();
			}
			
			Image img = new Image("/images/YES.png");
			Notifications notificationBuilder = Notifications.create()
					.title("Action Réussie")
					.text("Requette bien effectuée")
					.graphic(new ImageView(img))
					.hideAfter(Duration.seconds(5))
					.position(Pos.TOP_RIGHT)
					.onAction(new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent event) {
							System.out.println("Notification valide");							
						}
					});
//			notificationBuilder.darkStyle();
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					notificationBuilder.show();
				}			
		});
	  }
	}
//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------

//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------
	public void exporter() {
	
	}
//---------------------------------------------------------------------------------	
//--------------------------------------------------------------------------------
	private AjoutFournisseurMain mainApp;
	// BOUTON SUPPRESSION AU NIVEAU DU TABLEAU
		public void suppressionSurLeTableau() throws SQLException {
			
			Fournisseur mat = tableViewFournisseur.getSelectionModel().getSelectedItem();
			int selectedIndex = tableViewFournisseur.getSelectionModel().getSelectedIndex();
			Connection connexion = ConnectionDB.maConnection();
			
			try{
					String sql = "DELETE FROM `Fournisseur` WHERE raisonSociale = '"+ mat.getRaisonSociale() +"' " ;
				
					PreparedStatement pst = (PreparedStatement) connexion.prepareStatement(sql);
					pst.executeUpdate();
					pst.close();
					connexion.close();
					// --------------------------------------------------------
					Confirmation.alerter();
					// --------------------------------------------------------
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Confirmation");
					alert.setContentText("Fournisseur a été bien supprimé");
					alert.showAndWait();
					// --------------------------------------------------------				
					new notificationThred().start();
							//----------------------------
	
//	SUPPRESSION SUR LE TABLEAU
			ObservableList<Fournisseur> FournisseurSelection, toutFournisseur;
			toutFournisseur = tableViewFournisseur.getItems();
			FournisseurSelection = tableViewFournisseur.getSelectionModel().getSelectedItems();
//			tableViewFournisseur.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			FournisseurSelection.forEach(toutFournisseur::remove);

			}  catch (SQLException ex) {
				Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
				ex.printStackTrace();
			}
			
			 
		}
// -------------------------------------------------------------------------------
// -------------------------------------------------------------------------------
		public void dateDuJourMethode() {
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					Date today = Calendar.getInstance().getTime();
					String reportDate = df.format(today);
					duJour.setText("Date : " + reportDate);
		}		

//---------------------------------------------------------------------------------
//---------------------------------------------------------------------------------
public String genereRandom() {	
	
	final Random RANDOM = new SecureRandom();
//	String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";
	String letters = "N0123456789";
	String pw = "";
	
      for (int i = 0; i < 8; i++)
      {
    	  int index = (int)(RANDOM.nextDouble()*letters.length());
          pw += letters.substring(index, index+1);     
      }
      textFieldRaisonSociale.setText(pw);
	return pw;
}

//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------

// METHODE VALIDER NOM
@SuppressWarnings("unused")
private boolean validerAdresse() {

	Pattern p = Pattern.compile("[a-zA-Z]+", Pattern.CASE_INSENSITIVE);
	Matcher m = p.matcher(TextFieldAdresse.getText());

	if (m.find() && m.group().equals(TextFieldAdresse.getText())) {
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
private boolean validerTelephone() {

	Pattern p = Pattern.compile("^?[0-9]{2}\\-?[0-9]{3}\\-?[0-9]{2}\\-?[0-9]{2}$", Pattern.CASE_INSENSITIVE);
	Matcher m = p.matcher(TextFieldTelephone.getText());

	if (m.find() && m.group().equals(TextFieldTelephone.getText())) {
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
private boolean validerEmail() {

	Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	Matcher m = p.matcher(TextFieldCourriel.getText());

	if (m.find() && m.group().equals(TextFieldCourriel.getText())) {
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
//----------------------------------------------------------------------------------
	public void ControlChiffPhone() {  // CE GENRE DE METHODE ON LES APPELLE DIRECTEMENT DANS LA METHODE QUI RECHARGE LES DONNEES AUTOMATIQUE
		TextFieldTelephone.setOnKeyTyped(e -> {
			String ch = e.getCharacter();
			if (!(ch.equals("0") || ch.equals("1") || ch.equals("2") | ch.equals("3") || ch.equals("4")
					|| ch.equals("5") || ch.equals("6") || ch.equals("7") || ch.equals("8") || ch.equals("9")
					|| ch.equals("BACK_SPACE")) || (!(TextFieldTelephone.getText().length() < 9))) {
				e.consume();
				java.awt.Toolkit.getDefaultToolkit().beep();
			}
		});
	}
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
		// REDIRECTION SUR ACCUEIL - IMAGE
		@FXML
		private void retourAuMenu() throws IOException {
			Parent pane = FXMLLoader.load(getClass().getResource("/DirecteurGeneral/Accueil.fxml"));
			paneFournisseur.getChildren().setAll(pane);

		}
//----------------------------------------------------------------------------------
//----------------------------------------------------------------------------------
		public void exporterEnExcel() {
			
		}
//----------------------------------------------------------------------------------
//----------------------------------------------------------------------------------
	final ObservableList<Fournisseur> data = FXCollections.observableArrayList();
	
	public void rechercheFiltr() {
		// RECHERCHE - RECHERCHE - RECHERCHE - RECHERCHE - RECHERCHE - RECHERCHE
		
			FilteredList<Fournisseur> filtrFournisseur = new FilteredList<>(data, e -> true);
			
			recherch.setOnKeyReleased(e -> {
				recherch.textProperty().addListener((observableValue, oldValue, newValue) ->{
					filtrFournisseur.setPredicate((Predicate<? super Fournisseur>) fourniseurReserch->{
						if(newValue == null || newValue.isEmpty()) {
						return true;
						}
						String lowerCaseFiltrer = newValue.toLowerCase();
//						if(fourniseurReserch.getID().contains(newValue)){
//						} else
						if (fourniseurReserch.getRaisonSociale().toLowerCase().contains(lowerCaseFiltrer)) {
							return true;
						} else if( fourniseurReserch.getSigle().toLowerCase().contains(lowerCaseFiltrer) ) {
							return true;
						}
						return false;
					});
				});
				SortedList<Fournisseur>  sortData = new SortedList<>(filtrFournisseur);
				sortData.comparatorProperty().bind(tableViewFournisseur.comparatorProperty());
				tableViewFournisseur.setItems(sortData);
			});		
	}
//----------------------------------------------------------------------------------
//----------------------------------------------------------------------------------
	public void selctionAuto() {
		ObservableList<Fournisseur> select = tableViewFournisseur.getSelectionModel().getSelectedItems();
		System.out.println(select);
	}
//----------------------------------------------------------------
	public void genererMot() throws SQLException {
		
		Connection connexion = ConnectionDB.maConnection();
		
		String sql = "SELECT raisonSociale from Fournisseur";

		PreparedStatement pst = (PreparedStatement) connexion.prepareStatement(sql);
//		pst.setString(1, loginnfild.getText().trim());
		ResultSet rs = pst.executeQuery();
		
		String logRole = null;
		
		if (rs.next()) {
			logRole = rs.getString("raisonSociale");
		}
		
//		String[] mot = {"Hello", "Hello How are u","ok"};
		TextFields.bindAutoCompletion(textFieldRaisonSociale, logRole );
	}
	//--------------------------------------------------------------------
	//------------------------------------------------------------------------
	@FXML 
	private void delectSelection(ActionEvent even) {
		ObservableList<Fournisseur> selectedIndex = tableViewFournisseur.getSelectionModel().getSelectedItems();
		
		ActualiserDonneesFournisseurTableau();	
	}
	
	//------------------------------------
	public void alertBoite() {
		Alert alerte = new Alert(AlertType.WARNING);
		alerte.setTitle("Attention");

		alerte.setContentText(alertMesaz);
		alerte.showAndWait();
	}
	
}









