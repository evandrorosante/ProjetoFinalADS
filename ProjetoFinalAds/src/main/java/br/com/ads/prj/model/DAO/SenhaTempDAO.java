package br.com.ads.prj.model.DAO;

import br.com.ads.prj.model.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Evandro
 */
public class SenhaTempDAO {

    private static SenhaTempDAO instance;

    public static SenhaTempDAO getInstance() {
        if (instance == null) {
            instance = new SenhaTempDAO();
        }
        return instance;
    }

    public void cadastraSenhaTemp(int idUser, String novaSenha) {
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt;
            String id = null;
            
            //removendo senhas temporárias anteriores
            stmt = conexao.prepareStatement("DELETE FROM senhatemp WHERE iduser=?");
            stmt.setInt(1, idUser);
            stmt.executeUpdate();
            
            stmt = conexao.prepareStatement("select UUID();");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            id = rs.getString(1);

            stmt = conexao.prepareStatement("INSERT INTO senhatemp(id,iduser,senha,data) VALUES (?,?,?,?)");
            stmt.setString(1, id);
            stmt.setInt(2, idUser);
            stmt.setString(3, novaSenha);
            stmt.setString(4, Data.getInstance().getDatePadraoInternacional());
            stmt.executeUpdate();
            stmt.close();

            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String retornaIdTemp(int iduser) {
        String id = null;
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = conexao.prepareStatement("SELECT id FROM senhatemp where iduser=?");
            stmt.setInt(1, iduser);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                id = rs.getString(1);
            }
            conexao.close();
            return id;
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex);
        }
        return null;
    }

    public String buscaNovaSenha(String id) {
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = conexao.prepareStatement("SELECT senha FROM senhatemp where id=?");
            stmt.setString(1, id);
            String senha = null;
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                senha = rs.getString(1);
            }
            conexao.close();
            return senha;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Integer buscaIdUser(String id) {
        Integer iduser = null;
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = conexao.prepareStatement("SELECT iduser FROM senhatemp where id=?");
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                iduser = rs.getInt(1);
            }
            conexao.close();
            return iduser;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

   

}
