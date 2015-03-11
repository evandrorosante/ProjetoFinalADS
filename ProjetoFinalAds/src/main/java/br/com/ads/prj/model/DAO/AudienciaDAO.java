package br.com.ads.prj.model.DAO;

import br.com.ads.prj.model.Audiencia;
import br.com.ads.prj.model.Data;
import br.com.ads.prj.model.Processo;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Evandro
 */
public class AudienciaDAO {

    private static AudienciaDAO instance;

    public static AudienciaDAO getInstance() {
        if (instance == null) {
            instance = new AudienciaDAO();
        }
        return instance;
    }

    public String cadastraAudiencia(Audiencia audiencia) {
        String retorno = "cadastrou";
        Connection conexao = DAO.getInstance().getConexao();
        try {
            PreparedStatement stmt = conexao.prepareStatement("INSERT INTO audiencia(data,horario,processo) "
                    + "VALUES(?,?,?)");
            stmt.setString(1, audiencia.getData());
            stmt.setString(2, audiencia.getHorario());
            stmt.setInt(3, audiencia.getProcesso().getIdProcesso());
            stmt.executeUpdate();
            return retorno;
        } catch (Exception e) {
            retorno = e.getMessage();
        }
        return retorno;
    }

    public String editarAudiencia(Audiencia audiencia) {
        String retorno = "ok";
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = conexao.prepareStatement("UPDATE audiencia SET data=?, horario=?, ata=?,"
                    + " nome_arquivo=? WHERE id_audiencia=?");
            stmt.setString(1, audiencia.getData());
            stmt.setString(2, audiencia.getHorario());
            stmt.setBlob(3, audiencia.getAta());
            stmt.setString(4, audiencia.getNomeArquivo());
            stmt.setInt(5, audiencia.getIdAudiencia());
            stmt.executeUpdate();
            stmt.close();
            conexao.close();
            return retorno;
        } catch (SQLException ex) {
            retorno = ex.getMessage();
        }
        return retorno;
    }

    public List<Audiencia> getListAudiencia() {
        List<Audiencia> listaAudiencia = new ArrayList<>();
        Connection conexao = DAO.getInstance().getConexao();
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT a.id_audiencia, a.data, a.horario,a.ata,a.processo, p.num_processo,"
                    + " a.nome_arquivo FROM audiencia a, processo p WHERE a.processo=p.id_processo ORDER BY  a.data");
            // stmt.setLong(1, numProcesso);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idAudiencia = rs.getInt(1);
                String data = rs.getString(2);
                String horario = rs.getString(3);
                InputStream ata = rs.getBinaryStream(4);
                int idProcesso = rs.getInt(5);
                String nomeArquivo = rs.getString(7);
                Processo processo = ProcessoDAO.getInstance().getProcessosById(idProcesso);
                listaAudiencia.add(new Audiencia(idAudiencia, processo, Data.getInstance().getDataPadrao(data),
                        horario, nomeArquivo, ata));
            }
            stmt.close();
            conexao.close();
            return listaAudiencia;

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return null;
    }

    public Audiencia getAudienciaById(int idAudiencia) {
        Audiencia audiencia;
        Connection conexao = DAO.getInstance().getConexao();
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT a.id_audiencia, a.data, a.horario,a.ata,a.processo, p.num_processo,"
                    + " a.nome_arquivo FROM audiencia a, processo p WHERE a.processo=p.id_processo AND a.id_audiencia=?");
            stmt.setInt(1, idAudiencia);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            String data = rs.getString(2);
            String horario = rs.getString(3);
            InputStream ata = rs.getBinaryStream(4);
            int idProcesso = rs.getInt(5);
           // long numPro = rs.getLong(6);
            String nomeArquivo = rs.getString(7);
            Processo processo = ProcessoDAO.getInstance().getProcessosById(idProcesso);
           // processo.setIdProcesso(idProcesso);
           // processo.setNumProcesso(numPro);
            audiencia = new Audiencia(id, processo, Data.getInstance().getDataPadrao(data), horario, nomeArquivo, ata);
            stmt.close();
            conexao.close();
            return audiencia;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Audiencia getAudienciaByNumProcesso(long numProcesso) {
        Audiencia audiencia;
        Connection conexao = DAO.getInstance().getConexao();
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT a.id_audiencia, a.data, a.horario,a.ata,a.processo, p.num_processo,"
                    + " a.nome_arquivo FROM audiencia a, processo p WHERE a.processo=p.id_processo AND p.num_processo=?");
            stmt.setLong(1, numProcesso);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            String data = rs.getString(2);
            String horario = rs.getString(3);
            InputStream ata = rs.getBinaryStream(4);
            int idProcesso = rs.getInt(5);
          //  long numPro = rs.getLong(6);
            String nomeArquivo = rs.getString(7);
            Processo processo = ProcessoDAO.getInstance().getProcessosById(idProcesso);
          //  processo.setIdProcesso(idProcesso);
          //  processo.setNumProcesso(numPro);
            audiencia = new Audiencia(id, processo, Data.getInstance().getDataPadrao(data), horario, nomeArquivo, ata);
            stmt.close();
            conexao.close();
            return audiencia;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public InputStream getAtaByNome(String nomeArquivo) {
        InputStream ata;
        Connection conexao = DAO.getInstance().getConexao();
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT ata FROM audiencia WHERE nome_arquivo=?");
            stmt.setString(1, nomeArquivo);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            ata = rs.getBinaryStream(1);
            stmt.close();
            conexao.close();
            return ata;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
