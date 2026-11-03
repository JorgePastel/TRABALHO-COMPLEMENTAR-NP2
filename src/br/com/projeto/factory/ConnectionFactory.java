package br.com.projeto.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=sistema_crud;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASS = "SuaSenhaAqui";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void main(String[] args) {
        try {
            Connection conexao = getConnection();
            if (conexao != null) {
                System.out.println("SUCESSO! Conectado com sucesso à base de dados sistema_crud!");
                conexao.close();
            }
        } catch (SQLException e) {
            System.err.println("FALHA NA CONEXÃO: " + e.getMessage());
        }
    }
}