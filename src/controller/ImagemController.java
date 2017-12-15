package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import ajudantes.Alert;
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
	private Button btnProcurar;
	@FXML
	private TextField txtNome;
	@FXML
	private Button btnSalvar;
	@FXML
	private Button btnAtualizar;
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
	
		btnProcurar.setOnAction(o -> buscarFotos());
		btnSalvar.setOnAction(i -> cadastar());
		btnDeletar.setOnAction(i -> deletar());
		btnAtualizar.setOnAction(o -> atualizar());
		
		preencher();
		exibir();	
	}
	
	private void buscarFotos() {
		
		imgView.setImage(null);
		
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
	
	private void cadastar() {
		
		if (txtNome.getText().isEmpty() || file == null) {		
			Alert.showAlertWarning("Preencha todos os campos");
		} else {
			
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			ImagemDAO imagemDAO = new ImagemDAO();

			if (imagemDAO.insert(txtNome.getText() , fis)) {
					
				preencher();
					
				imgView.setImage(null);
				txtNome.clear();
				lblCaminho.setText(null);
					
				Alert.showAlertInformation("Sucesso");
					
			} else {	
				Alert.showAlertError("Erro ao cadastrar");
			}	
		}
	}
	
	private void atualizar() {
		
		Imagem imagem = listNome.getSelectionModel().getSelectedItem();
		
		if (imagem == null) {
			Alert.showAlertWarning("Selecione um um item");	
		} else {
			
			btnSalvar.setText("Atualizar");
			
			ImagemDAO imagemDAO = new ImagemDAO();	
			
			imagemDAO.getImagem(imagem.getId());
			
			txtNome.setText(imagem.getNome());
			
			imgView.setImage(null);
			
			btnSalvar.setOnAction( p -> update());
		}
		
	}
	
	private void update() {
		
		Imagem imagem = listNome.getSelectionModel().getSelectedItem();
		
		if (txtNome.getText().isEmpty() || file == null) {		
			Alert.showAlertWarning("Preencha todos os campos");
		} else {
			
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			ImagemDAO imagemDAO = new ImagemDAO();

			if (imagemDAO.atualizar(txtNome.getText() , fis, imagem.getId())) {
					
				preencher();
					
				imgView.setImage(null);
				txtNome.clear();
				lblCaminho.setText(null);
				btnSalvar.setText("Salvar");
					
				Alert.showAlertInformation("Sucesso");
					
			} else {	
				Alert.showAlertError("Erro ao Atualizar");
			}	
		}
		
	}
	
	private void preencher() {
		
		listNome.getItems().clear();
		
		ImagemDAO imagemDAO = new ImagemDAO();
		
		listNome.getItems().addAll(imagemDAO.listar());	
	}
	
	private void exibir(){
		
		listNome.setOnMouseClicked(i -> {
			
			Imagem imagem = listNome.getSelectionModel().getSelectedItem();
			
			if (imagem == null) {
				
				Alert.showAlertWarning("Nenhum item selecionado");
				
			} else {
				
				ImagemDAO dao = new ImagemDAO();
	
				image = dao.visualizar(imagem.getId());
		
				imgView.setImage(image);
			}
		});
	}
	
	private void deletar() {
		
		Imagem imagem = listNome.getSelectionModel().getSelectedItem();
		
		if (imagem == null) {
			Alert.showAlertWarning("Selecione um um item");
		} else {
			
			ImagemDAO imagemDAO = new ImagemDAO();
			
			if (imagemDAO.delete(imagem)) {
				
				Alert.showAlertInformation("Item deletado com sucesso");
				preencher();
				imgView.setImage(null);
				
			} else {	
				Alert.showAlertError("Erro ao deletar");
			}		
		}
	}
	
	public void rodar(int rodar) {
		imgView.setRotate(rodar);	
	}
}
