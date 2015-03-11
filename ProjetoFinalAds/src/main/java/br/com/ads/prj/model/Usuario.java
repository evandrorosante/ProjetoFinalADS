package br.com.ads.prj.model;

/**
 *
 * @author Evandro
 */
public class Usuario {
    private Pessoa pessoa;
    private String login;
    private int tipo;
    private String senha;
    private boolean ativo;
    
    public Usuario(){
        
    }

    public Usuario(Pessoa pessoa, String login, int tipo, String senha, boolean ativo) {
        this.pessoa = pessoa;
        this.login = login;
        this.tipo = tipo;
        this.senha = senha;
        this.ativo = ativo;
    }

    
    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    
    
    
    
    
}
