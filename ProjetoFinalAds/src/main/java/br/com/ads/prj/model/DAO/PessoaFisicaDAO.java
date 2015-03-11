package br.com.ads.prj.model.DAO;

import br.com.ads.prj.model.Data;
import br.com.ads.prj.model.PessoaFisica;
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
public class PessoaFisicaDAO {

    private static PessoaFisicaDAO instance;

    public static PessoaFisicaDAO getInstance() {
        if (instance == null) {
            instance = new PessoaFisicaDAO();
        }
        return instance;
    }

    public String cadastraClientePF(PessoaFisica pessoaFisica) {
        String retorno = "cadastrou";
        try {
            int id = 0;
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = null;
            //insere na tabela pessoa
            stmt = conexao.prepareStatement("INSERT INTO pessoa (logradouro,nro,bairro,cidade,estado,cep,telefone,celular,email)"
                    + " VALUES (?,?,?,?,?,?,?,?,?)", stmt.RETURN_GENERATED_KEYS);
            stmt.setString(1, pessoaFisica.getLogradouro());
            stmt.setInt(2, pessoaFisica.getNro());
            stmt.setString(3, pessoaFisica.getBairro());
            stmt.setString(4, pessoaFisica.getCidade());
            stmt.setString(5, pessoaFisica.getEstado());
            stmt.setString(6, pessoaFisica.getCep());
            stmt.setString(7, pessoaFisica.getTelefone());
            stmt.setString(8, pessoaFisica.getCelular());
            stmt.setString(9, pessoaFisica.getEmail());
            stmt.executeUpdate();

            //buscando a PK gerada automaticamente
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);

            //insere na tabela pessoa-fisica
            stmt = conexao.prepareStatement("INSERT INTO pessoa_fisica(id_pessoa, cpf, nome, data_nasc, nacionalidade,"
                    + " estado_civil, profissao, rg) VALUES (?,?,?,?,?,?,?,?)");
            stmt.setInt(1, id);
            stmt.setString(2, pessoaFisica.getCpf());
            stmt.setString(3, pessoaFisica.getNome());
            stmt.setString(4, pessoaFisica.getDataNasc());
            stmt.setString(5, pessoaFisica.getNacionalidade());
            stmt.setString(6, pessoaFisica.getEstadoCivil());
            stmt.setString(7, pessoaFisica.getProfissao());
            stmt.setString(8, pessoaFisica.getRg());
            stmt.executeUpdate();
            stmt.close();
            conexao.close();
            return retorno;
        } catch (SQLException ex) {
            retorno = ex.getMessage();
        }
        return retorno;
    }

    public void atualizaClientePF(PessoaFisica pessoaFisica) {
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt;
            //atualiza valores na tabela pessoa
            stmt = conexao.prepareStatement("UPDATE pessoa SET logradouro=?,nro=?,bairro=?,cidade=?,estado=?,"
                    + " cep=?,telefone=?,celular=?,email=? WHERE id_pessoa=?");
            stmt.setString(1, pessoaFisica.getLogradouro());
            stmt.setInt(2, pessoaFisica.getNro());
            stmt.setString(3, pessoaFisica.getBairro());
            stmt.setString(4, pessoaFisica.getCidade());
            stmt.setString(5, pessoaFisica.getEstado());
            stmt.setString(6, pessoaFisica.getCep());
            stmt.setString(7, pessoaFisica.getTelefone());
            stmt.setString(8, pessoaFisica.getCelular());
            stmt.setString(9, pessoaFisica.getEmail());
            stmt.setInt(10, pessoaFisica.getIdPessoa());
            stmt.executeUpdate();

            //atualiza valores na tabela pessoa-fisica
            stmt = conexao.prepareStatement("UPDATE pessoa_fisica SET cpf=?, rg=?, nome=?,data_nasc=?,"
                    + " nacionalidade=?, profissao=?, estado_civil=? WHERE id_pessoa=?");
            stmt.setString(1, pessoaFisica.getCpf());
            stmt.setString(2, pessoaFisica.getRg());
            stmt.setString(3, pessoaFisica.getNome());
            stmt.setString(4, pessoaFisica.getDataNasc());
            stmt.setString(5, pessoaFisica.getNacionalidade());
            stmt.setString(6, pessoaFisica.getProfissao());
            stmt.setString(7, pessoaFisica.getEstadoCivil());
            stmt.setInt(8, pessoaFisica.getIdPessoa());
            stmt.executeUpdate();
            stmt.close();
            conexao.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Boolean existeCpf(String cpf) {
        Boolean resp = false;
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = conexao.prepareStatement("SELECT cpf FROM pessoa_fisica pf"
                    + " WHERE pf.cpf=?");
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

    public String selecionaNomePorCpf(String cpf) {
        Connection conexao = DAO.getInstance().getConexao();
        String nome = null;
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT p.nome FROM pessoa p, pessoa_fisica pf"
                    + "WHERE p.id=pf.id AND pf.cpf=?");
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                nome = rs.getString(1);
            }
            stmt.close();
            conexao.close();
            return nome;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Integer selecionaIdPorCpf(String cpf) {
        Connection conexao = DAO.getInstance().getConexao();
        Integer id = null;
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT p.id_pessoa FROM pessoa_fisica p WHERE "
                    + " p.cpf=?");
            stmt.setString(1, cpf);
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

    public List<PessoaFisica> getClientesPF(String coluna, String dado) {
        List<PessoaFisica> listaClientes = new ArrayList<>();
        Connection conexao = DAO.getInstance().getConexao();
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM pessoa p, pessoa_fisica pf WHERE"
                    + " p.id_pessoa=pf.id_pessoa AND pf." + coluna + " LIKE ? ORDER BY pf.nome");
            // stmt.setString(1, coluna);
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
                String cpf = rs.getString(12);
                String rg = rs.getString(13);
                String nome = rs.getString(14);
                String dataNasc = rs.getString(15);
                String nacionalidade = rs.getString(16);
                String estadoCivil = rs.getString(17);
                String profissao = rs.getString(18);
                //verifica se a pessoa fisíca não é advogado
                if (!AdvogadoDAO.getInstance().existeAdv(cpf)) {
                    listaClientes.add(new PessoaFisica(id_pessoa, cpf, rg, nome, logradouro, nro, bairro, cidade, estado,
                            cep, telefone, celular, email, dataNasc, nacionalidade, estadoCivil, profissao));
                }
            }
            stmt.close();
            conexao.close();
            return listaClientes;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public PessoaFisica getClienteByCpf(String Cpf) {
        PessoaFisica pessoaFisica = null;
        Connection conexao = DAO.getInstance().getConexao();
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM pessoa p, pessoa_fisica pf WHERE"
                    + " p.id_pessoa=pf.id_pessoa AND pf.cpf=?");
            stmt.setString(1, Cpf);
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
            String cpf = rs.getString(12);
            String rg = rs.getString(13);
            String nome = rs.getString(14);
            String data = rs.getString(15);
            String nacionalidade = rs.getString(16);
            String estadoCivil = rs.getString(17);
            String profissao = rs.getString(18);
            pessoaFisica = new PessoaFisica(id_pessoa, cpf, rg, nome, logradouro, nro, bairro, cidade, estado,
                    cep, telefone, celular, email, Data.getInstance().getDataPadrao(data), nacionalidade, estadoCivil, profissao);
            stmt.close();
            conexao.close();
            return pessoaFisica;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public PessoaFisica getClienteById(int id) {
        PessoaFisica pessoaFisica = null;
        Connection conexao = DAO.getInstance().getConexao();
        try {
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM pessoa p, pessoa_fisica pf WHERE"
                    + " p.id_pessoa=pf.id_pessoa AND pf.id_pessoa = ? ORDER BY pf.nome");
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
            String cpf = rs.getString(12);
            String rg = rs.getString(13);
            String nome = rs.getString(14);
            String data = rs.getString(15);
            String nacionalidade = rs.getString(16);
            String estadoCivil = rs.getString(17);
            String profissao = rs.getString(18);
            pessoaFisica = new PessoaFisica(id_pessoa, cpf, rg, nome, logradouro, nro, bairro, cidade, estado,
                    cep, telefone, celular, email, Data.getInstance().getDataPadrao(data), nacionalidade, estadoCivil, profissao);
            stmt.close();
            conexao.close();
            return pessoaFisica;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
