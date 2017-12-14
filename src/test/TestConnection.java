package test;

import java.sql.Connection;
import java.sql.SQLException;

import ajudantes.Conexao;

public class TestConnection {

	public static void main(String[] args) throws SQLException {
		
		Connection connection = new Conexao().getConnection();
		System.out.println("Connectou");
		connection.close();
	}
}
