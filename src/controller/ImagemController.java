package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import dao.ImagemDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Imagem;

public class ImagemController implements Initializable{
	
	@FXML
	private AnchorPane panePrinc;
	@FXML
	private AnchorPane paneImg;
	@FXML
	private ImageView imgView;
	@FXML
	private ImageView imgReceber;
	@FXML
	private Button btnProcurar;
	@FXML
	private TextField txtNome;
	@FXML
	private Button btnSalvar;
	@FXML
	private Button btnVisualizar;
	@FXML
	private Button btnUp;
	@FXML
	private Button btnLeft;
	@FXML
	private Button btnRight;
	@FXML
	private Button btnDown;
	@FXML
	private ListView<Imagem> listNome;
	@FXML
	private Button btnDeletar;
	@FXML
	private Label lblCaminho;
	
	private FileChooser fileChooser;
	private File file;
	private Image image;
	private FileInputStream fis;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		lblCaminho.setText("");
		
		btnUp.setOnAction(i -> rodar(0));
		btnLeft.setOnAction(i -> rodar(-90));
		btnRight.setOnAction(i -> rodar(90));
		btnDown.setOnAction(i -> rodar(180));
	
		btnProcurar.setOnAction(o -> abrirFotos());
		preencher();
		
		
		btnDeletar.setOnAction(o -> pega());
		
		btnSalvar.setOnAction(i -> cadastar());
		
	}
	
	public void abrirFotos() {
		
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("imagens", "*.jpg", "*.png"));
		
		file = fileChooser.showOpenDialog(panePrinc.getScene().getWindow()); 
		
		if (file != null) {
			
			lblCaminho.setText(file.getAbsolutePath());
			
			image = new Image(file.toURI().toString());
			
			imgView.setImage(image);
			
			try {
				fis = new FileInputStream(file);
			
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void cadastar() {
		
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ImagemDAO imagemDAO = new ImagemDAO();
		imagemDAO.insert(txtNome.getText() , (InputStream)fis, (int)file.length());
		
		preencher();
		
		imgView.setImage(null);
		
	}
	
	private void preencher() {
		
		listNome.getItems().clear();
		
		ImagemDAO imagemDAO = new ImagemDAO();
		
		//imagemDAO.visualizar();
		
		listNome.getItems().addAll(imagemDAO.visualizar());
		
	}
	
	public void pega() {
		
		Imagem i = new Imagem();
		
		i = listNome.getSelectionModel().getSelectedItem();
		System.out.println("Item selecionado " + i.getId());
		
		
		
		
	}
	
	public void rodar(int rodar) {
		imgView.setRotate(rodar);
	}
	

}
