package br.com.ads.prj.model;

/**
 *
 * @author Evandro
 */
public class PessoaJuridica extends Pessoa {

    private String cnpj;
    private String inscricaoEstadual;
    private String razaoSocial;
    private String nomeFantasia;

    public PessoaJuridica(int idPessoa, String cnpj, String inscricaoEstadual, String razaoSocial, String nomeFantasia, String logradouro, Integer nro, String bairro, String cidade, String estado, String cep, String telefone, String celular, String email) {
        super(idPessoa, logradouro, nro, bairro, cidade, estado, cep, telefone, celular, email);
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
    }

    public PessoaJuridica(String cnpj, String inscricaoEstadual, String razaoSocial, String nomeFantasia, String logradouro, Integer nro, String bairro, String cidade, String estado, String cep, String telefone, String celular, String email) {
        super(logradouro, nro, bairro, cidade, estado, cep, telefone, celular, email);
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
    }

    public PessoaJuridica() {

    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
}
