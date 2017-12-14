package dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ajudantes.Conexao;
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
	
	public void insert(String imagem, FileInputStream file) {
		
		String sql = "insert into imagem (nome , imagem) values (?,?)";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, imagem);
			stmt.setBinaryStream(2,(InputStream) file);
			
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void atualizar(Imagem imagem) {
		
		String sql = "update imagem set nome = ?, imagem = ? where id = ?";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, imagem.getNome());
			stmt.setBytes(2, imagem.getImagem());
			stmt.setLong(3, imagem.getId());
			
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
			PreparedStatement stmt = this.connection.prepareStatement("select * from imagem");
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				Imagem imagem = new Imagem();
				
				imagem.setId(rs.getLong("id"));
				imagem.setNome(rs.getString("nome"));
				
				//imagem.setImagem(imagem);(rs.getBinaryStream("imagem"));
				
				lstImagem.add(imagem);
			}
			
			rs.close();
			stmt.close();
			
			return lstImagem;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}

	
	
}
