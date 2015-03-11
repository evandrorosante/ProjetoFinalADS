package br.com.ads.prj.model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {

    private Connection conexao;
    private static DAO instance;

    private final String url = "jdbc:mysql://127.0.0.1:3306/bd_processos";
    private final String user = "root";
    private final String password = "root";

    private DAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro: " + e);
        }

    }

    public static DAO getInstance() {
        if (instance == null) {
            instance = new DAO();
        }
        return instance;
    }

    protected Connection getConexao() {
        try {
            this.conexao = DriverManager.getConnection(this.url, this.user, this.password);
        } catch (SQLException e) {
            System.out.println("Erro: " + e);
        }
        return this.conexao;
    }

    public void desconecta() throws SQLException {
        this.conexao.close();
    }

}
