package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import ajudantes.Conexao;
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
		//preencher();
		
		
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
		imagemDAO.insert(txtNome.getText() , fis);
		
	}
	
	private void preencher() {
		
		listNome.getItems().clear();
		
		Conexao c = new Conexao();
		
		try {
			Connection conn = c.getConnection();
			
			ResultSet rs = conn.createStatement().executeQuery("select * from imagem");
			
			while (rs.next()) {
				
				Imagem i = new Imagem();
				
				i.setId(rs.getLong("id"));
				i.setNome(rs.getString("nome"));
				
				//trás a imagem do bando de dados
				InputStream is = rs.getBinaryStream("imagem");
				OutputStream os = new FileOutputStream(new File("photo.jpg"));
				
				byte[] content = new byte[1024];
				int size = 0;
				
				while ((size = is.read(content)) != -1) {
					os.write(content, 0, size);
				}
				
				System.out.println("IS " + is);
				System.out.println("OS " + os);
				
				Image image = new Image("file:photo");
				
				
				
				os.close();
				is.close();

				listNome.getItems().add(i);
			}
			
			rs.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void pega() {
		
		Imagem i = new Imagem();
		
		i = listNome.getSelectionModel().getSelectedItem();
		System.out.println("Item selecionado " + i);
	}
	
	public void rodar(int rodar) {
		imgView.setRotate(rodar);
	}
	

}
