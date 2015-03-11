package br.com.ads.prj.model.DAO;

import br.com.ads.prj.model.Advogado;
import br.com.ads.prj.model.Data;
import br.com.ads.prj.model.Pessoa;
import br.com.ads.prj.model.PessoaFisica;
import br.com.ads.prj.model.PessoaJuridica;
import br.com.ads.prj.model.Processo;
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
public class ProcessoDAO {

    private static ProcessoDAO instance;

    public static ProcessoDAO getInstance() {
        if (instance == null) {
            instance = new ProcessoDAO();
        }
        return instance;
    }

    public String cadastraProcesso(Processo processo) {
        String retorno = "cadastrou";
        try {
            int id;
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = null;
            //insere na tabela processo
            stmt = conexao.prepareStatement("INSERT INTO processo(id_pessoa,requerido,tipo_acao,assunto_principal,"
                    + "situacao,valor_causa) VALUES (?,?,?,?,?,?)", stmt.RETURN_GENERATED_KEYS);
            stmt.setInt(1, processo.getPessoa().getIdPessoa());
            stmt.setString(2, processo.getRequerido());
            stmt.setString(3, processo.getTipoAcao());
            stmt.setString(4, processo.getAssuntoPrincipal());
            stmt.setString(5, processo.getSituacao());
            stmt.setDouble(6, processo.getValorCausa());
            stmt.execute();
            //buscando a PK gerada automaticamente
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);

            //insere na tabela processo-advogado conforme a quantidade de advogados
            for (int i = 0; i < processo.getAdvogados().size(); i++) {
                stmt = conexao.prepareStatement("INSERT INTO processo_advogado VALUES (?,?)");
                stmt.setInt(1, id);
                stmt.setInt(2, processo.getAdvogados().get(i).getOab());
                stmt.executeUpdate();
            }
            stmt.close();
            conexao.close();
            return retorno;
        } catch (SQLException ex) {
            retorno = ex.getMessage();
        }
        return retorno;
    }

    public String editarProcesso(Processo processo) {
        String retorno = "ok";
        try {

            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt;
            if (processo.getDataPeticao().equals("")) {
                stmt = conexao.prepareStatement("UPDATE processo SET requerido=?, tipo_acao=?, assunto_principal=?,"
                        + " situacao=?, num_processo=?, valor_causa=?, vara=? WHERE id_processo=?");
                stmt.setString(1, processo.getRequerido());
                stmt.setString(2, processo.getTipoAcao());
                stmt.setString(3, processo.getAssuntoPrincipal());
                stmt.setString(4, processo.getSituacao());
                stmt.setLong(5, processo.getNumProcesso());
                stmt.setDouble(6, processo.getValorCausa());
                stmt.setString(7, processo.getVara());
                stmt.setInt(8, processo.getIdProcesso());
                stmt.executeUpdate();
            } else {
                stmt = conexao.prepareStatement("UPDATE processo SET requerido=?, tipo_acao=?, assunto_principal=?,"
                        + " situacao=?, data_peticao=?, num_processo=?, valor_causa=?, vara=? WHERE id_processo=?");
                stmt.setString(1, processo.getRequerido());
                stmt.setString(2, processo.getTipoAcao());
                stmt.setString(3, processo.getAssuntoPrincipal());
                stmt.setString(4, processo.getSituacao());
                stmt.setString(5, processo.getDataPeticao());
                stmt.setLong(6, processo.getNumProcesso());
                stmt.setDouble(7, processo.getValorCausa());
                stmt.setString(8, processo.getVara());
                stmt.setInt(9, processo.getIdProcesso());
                stmt.executeUpdate();
            }

            stmt = conexao.prepareStatement("DELETE FROM processo_advogado WHERE id_processo=?");
            stmt.setInt(1, processo.getIdProcesso());
            stmt.executeUpdate();

            //insere na tabela processo-advogado conforme a quantidade de advogados
            for (int i = 0; i < processo.getAdvogados().size(); i++) {
                stmt = conexao.prepareStatement("INSERT INTO processo_advogado VALUES (?,?)");
                stmt.setInt(1, processo.getIdProcesso());
                stmt.setInt(2, processo.getAdvogados().get(i).getOab());
                stmt.executeUpdate();
            }

            stmt.close();
            conexao.close();
            return retorno;
        } catch (SQLException ex) {
            retorno = ex.getMessage();
        }
        return retorno;
    }

    public List<Processo> getListProcessos() {
        List<Processo> lista = new ArrayList<>();
        Connection conexao = DAO.getInstance().getConexao();
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM processo");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idProcesso = rs.getInt(1);
                int idPessoa = rs.getInt(2);
                String requerido = rs.getString(3);
                String tipoAcao = rs.getString(4);
                String assuntoPrincipal = rs.getString(5);
                String situacao = rs.getString(6);
                String dataPeticao = rs.getString(7);
                long numProcesso = rs.getLong(8);
                double valorCausa = rs.getDouble(9);
                String vara = rs.getString(10);
                //buscar informações do cliente
                Pessoa pf = PessoaFisicaDAO.getInstance().getClienteById(idPessoa);
                if (pf instanceof PessoaFisica) {
                    lista.add(new Processo(idProcesso, pf, requerido, tipoAcao, assuntoPrincipal, situacao,
                            Data.getInstance().getDataPadrao(dataPeticao), numProcesso, valorCausa, null, vara));
                } else {
                    PessoaJuridica pj = PessoaJuridicaDAO.getInstance().getClienteById(idPessoa);
                    lista.add(new Processo(idProcesso, pj, requerido, tipoAcao, assuntoPrincipal, situacao,
                            Data.getInstance().getDataPadrao(dataPeticao), numProcesso, valorCausa, null, vara));
                }
            }

            //gerando lista de advogados dos processos e adicionando na lista de processos
            for (Processo p : lista) {
                List<Advogado> listaAdv = new ArrayList<>();
                stmt = conexao.prepareStatement("SELECT oab FROM processo_advogado WHERE "
                        + "id_processo=?");
                stmt.setInt(1, p.getIdProcesso());
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int oab = rs.getInt(1);
                    Advogado adv = AdvogadoDAO.getInstance().getAdvogadoByOab(oab);
                    listaAdv.add(adv);
                }
                p.setAdvogados(listaAdv);

            }

            stmt.close();
            conexao.close();
            return lista;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Processo getProcessosByNumero(Long numProc) {
        Connection conexao = DAO.getInstance().getConexao();
        Processo processo = null;
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM processo WHERE num_processo=?");
            stmt.setLong(1, numProc);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idProcesso = rs.getInt(1);
                int idPessoa = rs.getInt(2);
                String requerido = rs.getString(3);
                String tipoAcao = rs.getString(4);
                String assuntoPrincipal = rs.getString(5);
                String situacao = rs.getString(6);
                String dataPeticao = rs.getString(7);
                long numProcesso = rs.getLong(8);
                double valorCausa = rs.getDouble(9);
                String vara = rs.getString(10);
                //buscar informações do cliente
                Pessoa pf = PessoaFisicaDAO.getInstance().getClienteById(idPessoa);
                //gerando lista de advogados do processo
                List<Advogado> listaAdv = new ArrayList<>();
                PreparedStatement stmt2 = conexao.prepareStatement("SELECT oab FROM processo_advogado WHERE "
                        + "id_processo=?");
                stmt2.setInt(1, idProcesso);
                ResultSet rs2 = stmt2.executeQuery();
                while (rs2.next()) {
                    int oab = rs2.getInt(1);
                    Advogado adv = AdvogadoDAO.getInstance().getAdvogadoByOab(oab);
                    listaAdv.add(adv);
                }
                stmt2.close();

                if (pf instanceof PessoaFisica) {
                    processo = new Processo(idProcesso, pf, requerido, tipoAcao, assuntoPrincipal, situacao,
                            Data.getInstance().getDataPadrao(dataPeticao), numProcesso, valorCausa, listaAdv, vara);
                } else {
                    PessoaJuridica pj = PessoaJuridicaDAO.getInstance().getClienteById(idPessoa);
                    processo = new Processo(idProcesso, pj, requerido, tipoAcao, assuntoPrincipal, situacao,
                            Data.getInstance().getDataPadrao(dataPeticao), numProcesso, valorCausa, listaAdv, vara);
                }
            }
            stmt.close();
            conexao.close();
            return processo;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Processo getProcessosById(int id) {
        Connection conexao = DAO.getInstance().getConexao();
        Processo processo = null;
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM processo WHERE id_processo=?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int idProcesso = rs.getInt(1);
            int idPessoa = rs.getInt(2);
            String requerido = rs.getString(3);
            String tipoAcao = rs.getString(4);
            String assuntoPrincipal = rs.getString(5);
            String situacao = rs.getString(6);
            String dataPeticao = rs.getString(7);
            long numProcesso = rs.getLong(8);
            double valorCausa = rs.getDouble(9);
            String vara = rs.getString(10);
            //buscar informações do cliente
            Pessoa pf = PessoaFisicaDAO.getInstance().getClienteById(idPessoa);
            //gerando lista de advogados do processo
            List<Advogado> listaAdv = new ArrayList<>();
            PreparedStatement stmt2 = conexao.prepareStatement("SELECT oab FROM processo_advogado WHERE "
                    + "id_processo=?");
            stmt2.setInt(1, idProcesso);
            ResultSet rs2 = stmt2.executeQuery();
            while (rs2.next()) {
                int oab = rs2.getInt(1);
                Advogado adv = AdvogadoDAO.getInstance().getAdvogadoByOab(oab);
                listaAdv.add(adv);
            }

            if (pf instanceof PessoaFisica) {
                processo = new Processo(idProcesso, pf, requerido, tipoAcao, assuntoPrincipal, situacao,
                        Data.getInstance().getDataPadrao(dataPeticao), numProcesso, valorCausa, listaAdv, vara);
            } else {
                PessoaJuridica pj = PessoaJuridicaDAO.getInstance().getClienteById(idPessoa);
                processo = new Processo(idProcesso, pj, requerido, tipoAcao, assuntoPrincipal, situacao,
                        Data.getInstance().getDataPadrao(dataPeticao), numProcesso, valorCausa, listaAdv, vara);
            }
            stmt.close();
            conexao.close();
            return processo;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
