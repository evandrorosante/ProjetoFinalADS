package br.com.ads.prj.model.DAO;

import br.com.ads.prj.model.Usuario;
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
public class RecuperaSenhaDAO {

    private static RecuperaSenhaDAO instance;

    public static RecuperaSenhaDAO getInstance() {
        if (instance == null) {
            instance = new RecuperaSenhaDAO();
        }
        return instance;
    }

    public void cadastraRecSenha(Usuario user) {
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt;

              //removendo senhas temporárias anteriores
            stmt = conexao.prepareStatement("DELETE FROM recuperasenha WHERE iduser=?");
            stmt.setInt(1, user.getPessoa().getIdPessoa());
            stmt.executeUpdate();
            
            String id = null;
            stmt = conexao.prepareStatement("select UUID();");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            id = rs.getString(1);
            stmt = conexao.prepareStatement("INSERT INTO recuperasenha(id,iduser,data) VALUES (?,?,?)");
            stmt.setString(1, id);
            stmt.setInt(2, user.getPessoa().getIdPessoa());
            stmt.setString(3, getDateTime());
            stmt.executeUpdate();
            stmt.close();
            conexao.close();
        } catch (SQLException ex) {
            System.out.println("erro" + ex);
        }
    }

    public static String retornaIdRecSenha(int iduser) {
        String id = null;
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = conexao.prepareStatement("SELECT id FROM recuperasenha where iduser=?");
            stmt.setInt(1, iduser);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                id = rs.getString(1);
            }
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public Integer retornaIdUserRecSenha(String idtemp) {
        Integer id = null;
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = conexao.prepareStatement("SELECT iduser FROM recuperasenha where id=?");
            stmt.setString(1, idtemp);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            conexao.close();
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
