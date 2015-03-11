package br.com.ads.prj.model.DAO;

import br.com.ads.prj.model.Advogado;
import br.com.ads.prj.model.PessoaFisica;
import br.com.ads.prj.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Evandro
 */
public class UsuarioDAO {

    private static UsuarioDAO instance;

    public static UsuarioDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioDAO();
        }
        return instance;
    }

    public void cadastraUsuarioAdv(Advogado advogado) {
        try {
            int id = 0;
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt;

            //insere na tabela pessoa
            stmt = conexao.prepareStatement("INSERT INTO pessoa(email) VALUES (?)");
            stmt.setString(1, advogado.getEmail());
            stmt.executeUpdate();
            //busca id da pessoa inserida
            stmt = conexao.prepareStatement("SELECT id_pessoa FROM pessoa where email = ?");
            stmt.setString(1, advogado.getEmail());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                id = (resultado.getInt(1));
            }
            //insere na tabela pessoa-fisica
            stmt = conexao.prepareStatement("INSERT INTO pessoa_fisica(id_pessoa,cpf, nome) VALUES (?,?,?)");
            stmt.setInt(1, id);
            stmt.setString(2, advogado.getCpf());
            stmt.setString(3, advogado.getNome());
            stmt.executeUpdate();
            //insere na tabela advogado
            stmt = conexao.prepareStatement("INSERT INTO advogado(id_pessoa,oab, id_estado) VALUES (?,?,?)");
            stmt.setInt(1, id);
            stmt.setInt(2, advogado.getOab());
            stmt.setString(3, advogado.getUf());
            stmt.executeUpdate();

            //insere na tabela usuario
            stmt = conexao.prepareStatement("INSERT INTO usuario(id_pessoa,login, tipo, senha, ativo) VALUES (?,?,?,?,?)");
            stmt.setInt(1, id);
            stmt.setString(2, advogado.getEmail());
            //tipo 2 é advogado
            stmt.setInt(3, 2);
            stmt.setString(4, advogado.getCpf());
            stmt.setByte(5,Byte.parseByte("1"));
            stmt.executeUpdate();

            stmt.close();
            conexao.close();
        } catch (SQLException ex) {
            System.out.println("Erro na persistência: " + ex);
        }
    }

    public void cadastraUsuarioSec(PessoaFisica secretaria) {
        try {
            int id = 0;
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt;

            //insere na tabela pessoa
            stmt = conexao.prepareStatement("INSERT INTO pessoa(email) VALUES (?)");
            stmt.setString(1, secretaria.getEmail());
            stmt.executeUpdate();
            //busca id da pessoa inserida
            stmt = conexao.prepareStatement("SELECT id_pessoa FROM pessoa where email = ?");
            stmt.setString(1, secretaria.getEmail());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                id = (resultado.getInt(1));
            }
            //insere na tabela pessoa-fisica
            stmt = conexao.prepareStatement("INSERT INTO pessoa_fisica(id_pessoa,cpf, nome) VALUES (?,?,?)");
            stmt.setInt(1, id);
            stmt.setString(2, secretaria.getCpf());
            stmt.setString(3, secretaria.getNome());
            stmt.executeUpdate();

            //insere na tabela usuario
            stmt = conexao.prepareStatement("INSERT INTO usuario(id_pessoa,login, tipo, senha, ativo) VALUES (?,?,?,?,?)");
            stmt.setInt(1, id);
            stmt.setString(2, secretaria.getEmail());
            //tipo 3 é secretaria
            stmt.setInt(3, 3);
            stmt.setString(4, secretaria.getCpf());
            stmt.setByte(5,Byte.parseByte("1"));
            stmt.executeUpdate();

            stmt.close();
            conexao.close();
        } catch (SQLException ex) {
            System.out.println("Erro na persistência: " + ex);
        }
    }

    public Usuario retornarUsuario(String login) {
        Usuario usuario = null;
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM usuario where login = ?");
            stmt.setString(1, login);
            ResultSet resultado = stmt.executeQuery();
            resultado.next();
            int id = (resultado.getInt(1));
            String l = (resultado.getString(2));
            int tipo = (resultado.getInt(3));
            String senha = (resultado.getString(4));
            PessoaFisica p = PessoaFisicaDAO.getInstance().getClienteById(id);
            int ativo = resultado.getByte(5);
            boolean a = false;
            if (ativo == 1) {
                a = true;
            }
            usuario = new Usuario(p, l, tipo, senha, a);
            conexao.close();
            return usuario;
        } catch (SQLException ex) {
            System.out.println("erro da consulta" + ex);
        }
        return null;
    }

    public Usuario retornarUsuario(int id_pessoa) {
        Usuario usuario = null;
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM usuario where id_pessoa = ?");
            stmt.setInt(1, id_pessoa);
            ResultSet resultado = stmt.executeQuery();
            resultado.next();
            int id = (resultado.getInt(1));
            String l = (resultado.getString(2));
            int tipo = (resultado.getInt(3));
            String senha = (resultado.getString(4));
            PessoaFisica p = PessoaFisicaDAO.getInstance().getClienteById(id);
            int ativo = resultado.getByte(5);
            boolean a;
            a = ativo == 1;
            usuario = new Usuario(p, l, tipo, senha, a);
            conexao.close();
            return usuario;
        } catch (SQLException ex) {
            System.out.println("erro da consulta" + ex);
        }
        return null;
    }

    public PessoaFisica retornaPessoaFisica(String login) {
        PessoaFisica pessoaFisica = new PessoaFisica();
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = conexao.prepareStatement("SELECT pf.id_pessoa, cpf, nome FROM pessoa_fisica pf,"
                    + " usuario u WHERE pf.id_pessoa=u.id_pessoa AND u.login=?");
            stmt.setString(1, login);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                pessoaFisica.setIdPessoa(resultado.getInt(1));
                pessoaFisica.setCpf(resultado.getString(2));
                pessoaFisica.setNome(resultado.getString(3));
            }
            conexao.close();
            return pessoaFisica;
        } catch (SQLException ex) {
            System.out.println("Erro" + ex);
        }
        return null;
    }

    public void atualizaSenha(String senha, int id) {
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt;

            stmt = conexao.prepareStatement("UPDATE usuario SET senha=? WHERE id_pessoa=?");
            stmt.setString(1, senha);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String atualizaStatus(int id, byte ativo) {
        String retorno = "ok";
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt;

            stmt = conexao.prepareStatement("UPDATE usuario SET ativo=? WHERE id_pessoa=?");
            stmt.setByte(1, ativo);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();
            conexao.close();
            return retorno;
        } catch (SQLException ex) {
            retorno = ex.getMessage();
        }
        return retorno;
    }

    public List<Usuario> getListUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        try {
            Connection conexao = DAO.getInstance().getConexao();
            PreparedStatement stmt = conexao.prepareStatement("SELECT u.id_pessoa, u.login, u.tipo, "
                    + "u.senha, pf.nome, u.ativo FROM usuario u INNER JOIN pessoa_fisica pf ON "
                    + "pf.id_pessoa=u.id_pessoa ORDER BY pf.nome");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String login = rs.getString(2);
                int tipo = rs.getInt(3);
                int ativo = rs.getByte(6);
                boolean a;
                a = ativo == 1;
                PessoaFisica p = PessoaFisicaDAO.getInstance().getClienteById(id);
                lista.add(new Usuario(p, login, tipo, null, a));
            }
            stmt.close();
            conexao.close();
            return lista;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
