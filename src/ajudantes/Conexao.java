package ajudantes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	String jdbc = "jdbc:mysql://"; 
	String host = "localhost";
	String database = "dbimagem";
	String user_database = "root";
	String senha_database = "1234";
	

	public Connection getConnection() throws SQLException {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection(jdbc + host + "/" + database, user_database, senha_database);
	}
}
