package br.com.ads.prj.model.DAO;

import br.com.ads.prj.model.Advogado;
import br.com.ads.prj.model.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Evandro
 */
public class AdvogadoDAO {

    private static AdvogadoDAO instance;

    public static AdvogadoDAO getInstance() {
        if (instance == null) {
            instance = new AdvogadoDAO();
        }
        return instance;
    }

    public String cadastraAdvogado(Advogado advogado) {
        String retorno = "cadastrou";
        try {
            int id;
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = null;
            //insere na tabela pessoa
            stmt = conexao.prepareStatement("INSERT INTO pessoa (logradouro,nro,bairro,cidade,estado,cep,telefone,celular,email)"
                    + " VALUES (?,?,?,?,?,?,?,?,?)", stmt.RETURN_GENERATED_KEYS);
            stmt.setString(1, advogado.getLogradouro());
            stmt.setInt(2, advogado.getNro());
            stmt.setString(3, advogado.getBairro());
            stmt.setString(4, advogado.getCidade());
            stmt.setString(5, advogado.getEstado());
            stmt.setString(6, advogado.getCep());
            stmt.setString(7, advogado.getTelefone());
            stmt.setString(8, advogado.getCelular());
            stmt.setString(9, advogado.getEmail());
            stmt.execute();

            //buscando a PK gerada automaticamente
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);

            //insere na tabela pessoa-fisica
            stmt = conexao.prepareStatement("INSERT INTO pessoa_fisica(id_pessoa,cpf, nome,data_nasc,"
                    + " nacionalidade, estado_civil, rg) VALUES (?,?,?,?,?,?,?)");
            stmt.setInt(1, id);
            stmt.setString(2, advogado.getCpf());
            stmt.setString(3, advogado.getNome());
            stmt.setString(4, advogado.getDataNasc());
            stmt.setString(5, advogado.getNacionalidade());
            stmt.setString(6, advogado.getEstadoCivil());
            stmt.setString(7, advogado.getRg());
            stmt.executeUpdate();

            //insere na tabela advogado
            stmt = conexao.prepareStatement("INSERT INTO advogado (id_pessoa,oab,id_estado) VALUES "
                    + "(?,?,?)");
            stmt.setInt(1, id);
            stmt.setInt(2, advogado.getOab());
            stmt.setString(3, advogado.getUf());
            stmt.executeUpdate();
            stmt.close();
            conexao.close();
            return retorno;
        } catch (SQLException ex) {
            retorno = ex.getMessage();
        }
        return retorno;
    }

    public void atualizaAdvogado(Advogado advogado) {
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt;
            //atualiza valores na tabela pessoa
            stmt = conexao.prepareStatement("UPDATE pessoa SET logradouro=?,nro=?,bairro=?,cidade=?,estado=?,"
                    + " cep=?,telefone=?,celular=?,email=? WHERE id_pessoa=?");
            stmt.setString(1, advogado.getLogradouro());
            stmt.setInt(2, advogado.getNro());
            stmt.setString(3, advogado.getBairro());
            stmt.setString(4, advogado.getCidade());
            stmt.setString(5, advogado.getEstado());
            stmt.setString(6, advogado.getCep());
            stmt.setString(7, advogado.getTelefone());
            stmt.setString(8, advogado.getCelular());
            stmt.setString(9, advogado.getEmail());
            stmt.setInt(10, advogado.getIdPessoa());
            stmt.executeUpdate();

            //atualiza valores na tabela pessoa-fisica
            stmt = conexao.prepareStatement("UPDATE pessoa_fisica SET cpf=?, rg=?, nome=?,data_nasc=?,"
                    + " nacionalidade=?, estado_civil=? WHERE id_pessoa=?");
            stmt.setString(1, advogado.getCpf());
            stmt.setString(2, advogado.getRg());
            stmt.setString(3, advogado.getNome());
            stmt.setString(4, advogado.getDataNasc());
            stmt.setString(5, advogado.getNacionalidade());
            stmt.setString(6, advogado.getEstadoCivil());
            stmt.setInt(7, advogado.getIdPessoa());
            stmt.executeUpdate();

            stmt = conexao.prepareStatement("UPDATE advogado SET oab=?, id_estado=? "
                    + "WHERE id_pessoa=?");
            stmt.setInt(1, advogado.getOab());
            stmt.setString(2, advogado.getUf());
            stmt.setInt(3, advogado.getIdPessoa());
            stmt.close();
            conexao.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Boolean existeOab(int oab) {
        Boolean resp = false;
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = conexao.prepareStatement("SELECT oab FROM advogado adv"
                    + " WHERE adv.oab=?");
            stmt.setInt(1, oab);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                resp = true;
            }
            conexao.close();
            return resp;
        } catch (SQLException ex) {
            System.out.println("Erro" + ex);
        }
        return null;
    }

    public Boolean existeAdv(String cpf) {
        Boolean resp = false;
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = conexao.prepareStatement("SELECT oab FROM advogado a INNER JOIN"
                    + " pessoa_fisica pf ON pf.id_pessoa=a.id_pessoa WHERE pf.cpf=?");
            stmt.setString(1, cpf);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                resp = true;
            }
            conexao.close();
            return resp;
        } catch (SQLException ex) {
            System.out.println("Erro" + ex);
        }
        return null;
    }

    public List<Advogado> getListAdvogados() {

        Connection conexao = DAO.getInstance().getConexao();
        List<Advogado> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM pessoa p, pessoa_fisica pf, advogado a"
                    + " WHERE p.id_pessoa=pf.id_pessoa AND pf.id_pessoa=a.id_pessoa ORDER BY pf.nome");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String logradouro = rs.getString("logradouro");
                int nro = rs.getInt("nro");
                String bairro = rs.getString("bairro");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");
                String cep = rs.getString("cep");
                String telefone = rs.getString("telefone");
                String celular = rs.getString("celular");
                String email = rs.getString("email");
                String cpf = rs.getString("cpf");
                String nome = rs.getString("nome");
                String data_nasc = rs.getString("data_nasc");
                int oab = rs.getInt("oab");
                String uf = rs.getString("id_estado");
                String rg = rs.getString("rg");
                String nacionalidade = rs.getString("nacionalidade");
                String estadoCivil = rs.getString("estado_civil");
                lista.add(new Advogado(oab, uf, cpf, rg, nome, logradouro, nro, bairro, cidade, estado,
                        cep, telefone, celular, email, data_nasc, nacionalidade, estadoCivil));
            }
            stmt.close();
            return lista;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Advogado getAdvogadoByOab(int oab) {
        Advogado adv = null;
        Connection conexao = DAO.getInstance().getConexao();
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM pessoa p, pessoa_fisica pf, advogado a"
                    + " WHERE p.id_pessoa=pf.id_pessoa AND pf.id_pessoa=a.id_pessoa AND a.oab=?");
            stmt.setInt(1, oab);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            String logradouro = rs.getString("logradouro");
            int nro = rs.getInt("nro");
            String bairro = rs.getString("bairro");
            String cidade = rs.getString("cidade");
            String estado = rs.getString("estado");
            String cep = rs.getString("cep");
            String telefone = rs.getString("telefone");
            String celular = rs.getString("celular");
            String email = rs.getString("email");
            String cpf = rs.getString("cpf");
            String nome = rs.getString("nome");
            int oabAdv = rs.getInt("oab");
            String uf = rs.getString("id_estado");
            String rg = rs.getString("rg");
            String data = rs.getString("data_nasc");
            int idPessoa = rs.getInt("id_pessoa");
            String nacionalidade = rs.getString("nacionalidade");
            String estadoCivil = rs.getString("estado_civil");

            adv = new Advogado(oabAdv, uf, cpf, rg, nome, logradouro, nro, bairro, cidade, estado,
                    cep, telefone, celular, email, Data.getInstance().getDataPadrao(data), nacionalidade, estadoCivil);
            adv.setIdPessoa(idPessoa);
            stmt.close();
            return adv;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Advogado> getAdvogadoByNome(String nome) {
        List<Advogado> listaAdv = new ArrayList<>();
        Connection conexao = DAO.getInstance().getConexao();
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM pessoa p, pessoa_fisica pf, advogado a"
                    + " WHERE p.id_pessoa=pf.id_pessoa AND pf.id_pessoa=a.id_pessoa AND pf.nome like ? ORDER BY pf.nome");
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String logradouro = rs.getString("logradouro");
                int nro = rs.getInt("nro");
                String bairro = rs.getString("bairro");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");
                String cep = rs.getString("cep");
                String telefone = rs.getString("telefone");
                String celular = rs.getString("celular");
                String email = rs.getString("email");
                String cpf = rs.getString("cpf");
                String nomeAdv = rs.getString("nome");
                String data_nasc = rs.getString("data_nasc");
                int oabAdv = rs.getInt("oab");
                String uf = rs.getString("id_estado");
                String rg = rs.getString("rg");
                String nacionalidade = rs.getString("nacionalidade");
                String estadoCivil = rs.getString("estado_civil");
                listaAdv.add( new Advogado(oabAdv, uf, cpf, rg, nomeAdv, logradouro, nro, bairro, cidade, estado,
                        cep, telefone, celular, email, data_nasc, nacionalidade, estadoCivil));
            }
            stmt.close();
            conexao.close();
            return listaAdv;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
