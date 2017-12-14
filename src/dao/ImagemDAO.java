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
	
	public ImagemDAO() {
		try {
			this.connection = new Conexao().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insert(String nome, InputStream imagem, int i) {
		
		String sql = "insert into imagem (nome , imagem) values (?,?)";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);
			stmt.setBinaryStream(2, imagem);
			
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void atualizar(String nome, InputStream imagem, int i, Long id) {
		
		String sql = "update imagem set nome = ?, imagem = ? where id = ?";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);
			stmt.setBinaryStream(2, imagem);
			stmt.setLong(3, id);
			
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Imagem imagem) {
		
		try {
			PreparedStatement stmt = connection.prepareStatement("delete from imagem where id = ?");
			stmt.setLong(1, imagem.getId());
		
			stmt.execute();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Imagem> visualizar() {

		List<Imagem> lstImagem = new ArrayList<>();
		
		try {
			PreparedStatement stmt = this.connection.prepareStatement("select * from imagem where id = 2");
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				Imagem i = new Imagem();
				
				i.setId(rs.getLong("id"));
				i.setNome(rs.getString("nome"));
				
				//trás a imagem do bando de dados
				InputStream is = rs.getBinaryStream("imagem");
				OutputStream os = new FileOutputStream(new File("photo.png"));
				
				byte[] content = new byte[1024];
				int size = 0;
				
				while ((size = is.read(content)) != -1) {
					
					os.write(content, 0, size);
				}
				
				Image image = new Image("file:photo.jpg");
				
				i.setImagem(content);
				
				os.close();
				is.close();

				lstImagem.add(i);
			}
			
			rs.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lstImagem;
	}
	
}
