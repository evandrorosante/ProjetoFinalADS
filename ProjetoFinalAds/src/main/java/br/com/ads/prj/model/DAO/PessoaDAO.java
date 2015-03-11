/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ads.prj.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Evandro
 */
public class PessoaDAO {

    private static PessoaDAO instance;

    public static PessoaDAO getInstance() {
        if (instance == null) {
            instance = new PessoaDAO();
        }
        return instance;
    }

    public Boolean existeEmail(String email) {
        Boolean resp = false;
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = conexao.prepareStatement("SELECT email FROM pessoa p"
                    + " WHERE p.email=?");
            stmt.setString(1, email);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                resp = true;
            }
            conexao.close();
            return resp;
        } catch (SQLException ex) {
            System.out.println("Erro" + ex);
        }
        return resp;
    }

}
