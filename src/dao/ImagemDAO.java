package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ajudantes.Conexao;
import javafx.scene.image.Image;
import model.Imagem;

public class ImagemDAO {
	
	private Connection connection;
	
	private Image image;
	
	public ImagemDAO() {
		try {
			this.connection = new Conexao().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean insert(String nome, InputStream imagem) {
		
		String sql = "insert into imagem (nome , imagem) values (?,?)";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);
			stmt.setBinaryStream(2, imagem);
			
			stmt.execute();
			stmt.close();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean atualizar(String nome, InputStream imagem, Long id) {
		
		String sql = "update imagem set nome = ?, imagem = ? where id = ?";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);
			stmt.setBinaryStream(2, imagem);
			stmt.setLong(3, id);
			
			stmt.execute();
			stmt.close();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean delete(Imagem imagem) {
		
		try {
			PreparedStatement stmt = connection.prepareStatement("delete from imagem where id = ?");
			stmt.setLong(1, imagem.getId());
		
			stmt.execute();
			stmt.close();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public List<Imagem> listar(){
		
		List<Imagem> lstImagens = new ArrayList<Imagem>();
		
		try {
			PreparedStatement stmt = this.connection.prepareStatement("select * from imagem");
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				Imagem imagem = new Imagem();
				
				imagem.setId(rs.getLong("id"));
				imagem.setNome(rs.getNString("nome"));
				
				lstImagens.add(imagem);
			}
			
			rs.close();
			stmt.close();
			
			return lstImagens;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Image visualizar(Long id) {

		String sql = "select * from imagem where id = ?";
		
		try {
			
			PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
			pst.setLong(1, id);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
							
				InputStream is = rs.getBinaryStream("imagem");
				OutputStream os = new FileOutputStream(new File("photo.jpg"));
				
				byte[] content = new byte[1024];
				int size = 0;
				
				while ((size = is.read(content)) != -1) {
					os.write(content, 0, size);
				}
				os.close();
				is.close();
				
				image = new Image("file:photo.jpg", true);
			}
			
			pst.close();
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
}
