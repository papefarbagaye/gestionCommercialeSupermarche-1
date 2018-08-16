package DirecteurGeneral;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javaBeansClass.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import login.LoginController;

public class FactureController implements Initializable{
	
	@FXML private AnchorPane rootPane;
	@FXML private TableView<Article> tbViewFacture;
	@FXML private TextField idArticl;
	@FXML private TextField nomArticle;
	@FXML private TextField qtite;
	@FXML private TextField prixUnitairee;
	@FXML private TextField codeBarr;
	@FXML private TextField refPrixTotal;
	
	@FXML private TextField montantverser;
	@FXML private TextField montantReduu;
	@FXML private TextField refNamCashier;
	
	@FXML ImageView imgrtr;  // RETOUR SUR LE MENU
	@FXML ComboBox<Integer> comboBoxQuatite;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboBoxQuantite(); // CHOIX DU NOMBRE DE PRODUIT QUE LE VEUX
		//		viderLesCambre();  //VIDE LES CHAMPS AVANT D'AJOUT
		
	}
// ---------------------------------------------------
//---------------------------------------
	
	
	public void adf() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/DirecteurGeneral/Accueil.fxml"));
		rootPane.getChildren().setAll(pane);
	}
	//------------------------------------------
	// --------------------------------------------
	public void viderLesCambre(){
//		nomArticle.setText("");
//		qtite.setText("");
//		prixUnitairee.setText("");
//		codeBarr.setText("");
//		refPrixTotal.setText("");
//		montantverser.setText("");
//		montantReduu.setText("");
	}

	final ObservableList<String> listPurchase = FXCollections.observableArrayList();
	final ObservableList<Double> listOfPrice = FXCollections.observableArrayList();
	Double total = 0.0;
	
	public void ajouterArticle() {
		   String product;
		   Double price,amount;
		   int nombre, nombreProduit = 0;
		   
		   product = nomArticle.getText() ;
		   price = Double.parseDouble(prixUnitairee.getText());
		   nombre = Integer.parseInt(qtite.getText());
		   
		   amount = price * nombre;
		   
		   listPurchase.add(product);
		   listOfPrice.add(amount); //amount is a double! This is a collection of <Article> objects
		   nombreProduit = (listPurchase.get(listPurchase.indexOf(nombreProduit))).length();//note that indexOf() will get the FIRST index of the object
//		   nombreProduit = listOfPrice.getItemCount();
//		   nombreProduit = listPurchase.get(String.valueOf(nombreProduit));
		   
		   
		   qtite.setText(String.valueOf(nombreProduit));
		   total += amount;
		   refPrixTotal.setText(String.valueOf(total));
	   }
	   
	   public void annulerArticle() {
		   int index, nombre;
		   Double montant, prix;
		   
		   /*nombre = Integer.parseInt(qtite.getText());
		   index = listAchat.getSelectedInde();
		   montant = Double.parseDouble(listPrix.getItem(index));
		   nombre = nombre - 1;
		   total = total - montant;
		   
		   listAchat.remove(index);
		   listPrix.remove(index);
		   qtite.setText(String.valueOf(nombre));
		   refPrixTotal.setText(String.valueOf(total));   
		   */
	   }

	//------------------------------------------
	// --------------------CHOIX DU NOMBRE DE PRODUIT QUE L'ON VEUT
	public void comboBoxQuantite() {
		comboBoxQuatite.getItems().addAll(1,2,3,4,5,6,7);
	
	}
//-------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------
	
}
