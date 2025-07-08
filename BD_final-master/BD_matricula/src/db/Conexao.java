package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	private static final String URL = "jdbc:postgresql://localhost:5432/bd_teste";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}