package br.com.ads.prj.model.DAO;

import br.com.ads.prj.model.ConfigAlerta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Evandro
 */
public class ConfigAlertaDAO {

    private static ConfigAlertaDAO instance;

    public static ConfigAlertaDAO getInstance() {
        if (instance == null) {
            instance = new ConfigAlertaDAO();
        }
        return instance;
    }

    public ConfigAlerta getConfigAlerta() {
        ConfigAlerta configAlerta = null;
        Connection conexao = DAO.getInstance().getConexao();
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM config_alerta");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            String horario = rs.getString(1);
            int dias = rs.getInt(2);
            configAlerta = new ConfigAlerta(horario, dias);
            stmt.close();
            conexao.close();
            return configAlerta;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String atualizaConfigAlerta(ConfigAlerta configAlerta) {
        String retorno = "ok";
        Connection conexao = DAO.getInstance().getConexao();
        try {

            PreparedStatement stmt = conexao.prepareStatement("UPDATE config_alerta "
                    + "SET horario=?, dias=?");
            stmt.setString(1, configAlerta.getHorario());
            stmt.setInt(2, configAlerta.getDias());
            stmt.executeUpdate();
            stmt.close();
            conexao.close();

        } catch (Exception e) {
            retorno = e.getMessage();
        }
        return retorno;
    }

}
