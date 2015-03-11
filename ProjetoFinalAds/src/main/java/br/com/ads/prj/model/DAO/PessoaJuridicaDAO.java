package br.com.ads.prj.model.DAO;

import br.com.ads.prj.model.PessoaJuridica;
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
public class PessoaJuridicaDAO {

    private static PessoaJuridicaDAO instance;

    public static PessoaJuridicaDAO getInstance() {
        if (instance == null) {
            instance = new PessoaJuridicaDAO();
        }
        return instance;
    }

    public String cadastraClientePJ(PessoaJuridica pessoaJuridica) {
        String retorno = "cadastrou";
        try {
            int id;
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = null;
            //insere na tabela pessoa
            stmt = conexao.prepareStatement("INSERT INTO pessoa (logradouro,nro,bairro,cidade,estado,cep,telefone,celular,email)"
                    + " VALUES (?,?,?,?,?,?,?,?,?)", stmt.RETURN_GENERATED_KEYS);
            stmt.setString(1, pessoaJuridica.getLogradouro());
            stmt.setInt(2, pessoaJuridica.getNro());
            stmt.setString(3, pessoaJuridica.getBairro());
            stmt.setString(4, pessoaJuridica.getCidade());
            stmt.setString(5, pessoaJuridica.getEstado());
            stmt.setString(6, pessoaJuridica.getCep());
            stmt.setString(7, pessoaJuridica.getTelefone());
            stmt.setString(8, pessoaJuridica.getCelular());
            stmt.setString(9, pessoaJuridica.getEmail());
            stmt.execute();
            //buscando a PK gerada automaticamente
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);

            //insere na tabela pessoa-fisica
            stmt = conexao.prepareStatement("INSERT INTO pessoa_juridica(id_pessoa,cnpj, inscricao_estadual,razao_social, nome_fantasia) VALUES (?,?,?,?,?)");
            stmt.setInt(1, id);
            stmt.setString(2, pessoaJuridica.getCnpj());
            stmt.setString(3, pessoaJuridica.getInscricaoEstadual());
            stmt.setString(4, pessoaJuridica.getRazaoSocial());
            stmt.setString(5, pessoaJuridica.getNomeFantasia());
            stmt.executeUpdate();
            stmt.close();
            conexao.close();
            return retorno;
        } catch (SQLException ex) {
            retorno = ex.getMessage();
        }
        return retorno;
    }
    
    public void atualizaClientePF(PessoaJuridica pessoaJuridica) {
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt;
            //atualiza valores na tabela pessoa
            stmt = conexao.prepareStatement("UPDATE pessoa SET logradouro=?,nro=?,bairro=?,cidade=?,estado=?,"
                    + " cep=?,telefone=?,celular=?,email=? WHERE id_pessoa=?");
            stmt.setString(1, pessoaJuridica.getLogradouro());
            stmt.setInt(2, pessoaJuridica.getNro());
            stmt.setString(3, pessoaJuridica.getBairro());
            stmt.setString(4, pessoaJuridica.getCidade());
            stmt.setString(5, pessoaJuridica.getEstado());
            stmt.setString(6, pessoaJuridica.getCep());
            stmt.setString(7, pessoaJuridica.getTelefone());
            stmt.setString(8, pessoaJuridica.getCelular());
            stmt.setString(9, pessoaJuridica.getEmail());
            stmt.setInt(10, pessoaJuridica.getIdPessoa());
            stmt.executeUpdate();

            //atualiza valores na tabela pessoa_juridica
            stmt = conexao.prepareStatement("UPDATE pessoa_juridica SET cnpj=?, inscricao_estadual=?,"
                    + " razao_social=?,nome_fantasia=? WHERE id_pessoa=?");
            stmt.setString(1, pessoaJuridica.getCnpj());
            stmt.setString(2, pessoaJuridica.getInscricaoEstadual());
            stmt.setString(3, pessoaJuridica.getRazaoSocial());
            stmt.setString(4, pessoaJuridica.getNomeFantasia());
            stmt.setInt(5, pessoaJuridica.getIdPessoa());
            stmt.executeUpdate();
            stmt.close();
            conexao.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    

    public Boolean existeCnpj(String cnpj) {
        Boolean resp = false;
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = conexao.prepareStatement("SELECT cnpj FROM pessoa_juridica pj WHERE pj.cnpj=?");
            stmt.setString(1, cnpj);
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

    public Integer selecionaIdPorCNPJ(String cnpj) {
        Connection conexao = DAO.getInstance().getConexao();
        Integer id = null;
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT p.id_pessoa FROM pessoa_juridica p WHERE "
                    + " p.cnpj=?");
            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            stmt.close();
            conexao.close();
            return id;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<PessoaJuridica> getClientesPJ(String coluna, String dado) {
        List<PessoaJuridica> listaClientes = new ArrayList<>();
        Connection conexao = DAO.getInstance().getConexao();
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM pessoa p, pessoa_juridica pj WHERE"
                    + " p.id_pessoa=pj.id_pessoa AND pj." + coluna + " LIKE ? ORDER BY pj.nome_fantasia");
            stmt.setString(1, "%" + dado + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id_pessoa = rs.getInt(1);
                String logradouro = rs.getString(2);
                int nro = rs.getInt(3);
                String bairro = rs.getString(4);
                String cidade = rs.getString(5);
                String estado = rs.getString(6);
                String cep = rs.getString(7);
                String telefone = rs.getString(8);
                String celular = rs.getString(9);
                String email = rs.getString(10);
                String cnpj = rs.getString(12);
                String ie = rs.getString(13);
                String razaoSocial = rs.getString(14);
                String nomeFantasia = rs.getString(15);
                listaClientes.add(new PessoaJuridica(id_pessoa, cnpj, ie, razaoSocial, nomeFantasia, logradouro, nro, bairro,
                        cidade, estado, cep, telefone, celular, email));
            }
            stmt.close();
            conexao.close();
            return listaClientes;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public PessoaJuridica getClienteByCnpj(String cnpj) {
        PessoaJuridica pessoaJuridica = null;
        Connection conexao = DAO.getInstance().getConexao();
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM pessoa p, pessoa_juridica pj WHERE"
                    + " p.id_pessoa=pj.id_pessoa AND pj.cnpj=?");
            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id_pessoa = rs.getInt(1);
            String logradouro = rs.getString(2);
            int nro = rs.getInt(3);
            String bairro = rs.getString(4);
            String cidade = rs.getString(5);
            String estado = rs.getString(6);
            String cep = rs.getString(7);
            String telefone = rs.getString(8);
            String celular = rs.getString(9);
            String email = rs.getString(10);
            String cnpj2 = rs.getString(12);
            String ie = rs.getString(13);
            String razaoSocial = rs.getString(14);
            String nomeFantasia = rs.getString(15);
            pessoaJuridica = new PessoaJuridica(id_pessoa, cnpj2, ie, razaoSocial, nomeFantasia, logradouro, nro, bairro,
                    cidade, estado, cep, telefone, celular, email);
            stmt.close();
            conexao.close();
            return pessoaJuridica;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public PessoaJuridica getClienteById(int id) {
        PessoaJuridica pessoaJuridica = null;
        Connection conexao = DAO.getInstance().getConexao();
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM pessoa p, pessoa_juridica pj WHERE"
                    + " p.id_pessoa=pj.id_pessoa AND pj.id_pessoa=?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id_pessoa = rs.getInt(1);
            String logradouro = rs.getString(2);
            int nro = rs.getInt(3);
            String bairro = rs.getString(4);
            String cidade = rs.getString(5);
            String estado = rs.getString(6);
            String cep = rs.getString(7);
            String telefone = rs.getString(8);
            String celular = rs.getString(9);
            String email = rs.getString(10);
            String cnpj2 = rs.getString(12);
            String ie = rs.getString(13);
            String razaoSocial = rs.getString(14);
            String nomeFantasia = rs.getString(15);
            pessoaJuridica = new PessoaJuridica(id_pessoa, cnpj2, ie, razaoSocial, nomeFantasia, logradouro, nro, bairro,
                    cidade, estado, cep, telefone, celular, email);
            stmt.close();
            conexao.close();
            return pessoaJuridica;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
