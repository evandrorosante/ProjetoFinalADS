package br.com.ads.prj.model;

/**
 *
 * @author Evandro
 */
public class Pessoa {

    private int idPessoa;
    private String logradouro;
    private Integer nro;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String telefone;
    private String celular;
    private String email;

    public Pessoa(int idPessoa, String logradouro, Integer nro, String bairro, String cidade, String estado, String cep, String telefone, String celular, String email) {
        this.idPessoa = idPessoa;
        this.logradouro = logradouro;
        this.nro = nro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
    }
    public Pessoa( String logradouro, Integer nro, String bairro, String cidade, String estado, String cep, String telefone, String celular, String email) {
        this.logradouro = logradouro;
        this.nro = nro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
    }
    

    public Pessoa(String email) {
        this.logradouro = null;
        this.nro = null;
        this.bairro = null;
        this.cidade = null;
        this.estado = null;
        this.email = email;
    }

    public Pessoa() {

    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int id_pessoa) {
        this.idPessoa = id_pessoa;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNro() {
        return nro;
    }

    public void setNro(Integer nro) {
        this.nro = nro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
    

}
