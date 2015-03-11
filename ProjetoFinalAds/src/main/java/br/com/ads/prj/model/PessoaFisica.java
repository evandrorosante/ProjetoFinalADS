package br.com.ads.prj.model;

/**
 *
 * @author Evandro
 */
public class PessoaFisica extends Pessoa {

    private String cpf;
    private String rg;
    private String nome;
    private String dataNasc;
    private String nacionalidade;
    private String estadoCivil;
    private String profissao;

    public PessoaFisica(int IdPessoa, String cpf, String rg, String nome, String logradouro, Integer nro, String bairro, String cidade, String estado, String cep, String telefone, String celular, String email, String dataNasc) {
        super(IdPessoa, logradouro, nro, bairro, cidade, estado, cep, telefone, celular, email);
        this.cpf = cpf;
        this.rg = rg;
        this.nome = nome;
        this.dataNasc = dataNasc;
    }

    public PessoaFisica(String cpf, String rg, String nome, String logradouro, Integer nro, String bairro, String cidade,
            String estado, String cep, String telefone, String celular, String email, String dataNasc, String nacionalidade,
            String estadoCivil, String profissao) {
        super(logradouro, nro, bairro, cidade, estado, cep, telefone, celular, email);
        this.cpf = cpf;
        this.rg = rg;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.nacionalidade = nacionalidade;
        this.estadoCivil = estadoCivil;
        this.profissao = profissao;
    }
    
       public PessoaFisica(String cpf, String rg, String nome, String logradouro, Integer nro, String bairro, String cidade,
            String estado, String cep, String telefone, String celular, String email, String dataNasc, String nacionalidade,
            String estadoCivil) {
        super(logradouro, nro, bairro, cidade, estado, cep, telefone, celular, email);
        this.cpf = cpf;
        this.rg = rg;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.nacionalidade = nacionalidade;
        this.estadoCivil = estadoCivil;
    }
    
    public PessoaFisica(int IdPessoa,String cpf, String rg, String nome, String logradouro, Integer nro, String bairro, String cidade,
            String estado, String cep, String telefone, String celular, String email, String dataNasc, String nacionalidade,
            String estadoCivil, String profissao) {
        super(IdPessoa,logradouro, nro, bairro, cidade, estado, cep, telefone, celular, email);
        this.cpf = cpf;
        this.rg = rg;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.nacionalidade = nacionalidade;
        this.estadoCivil = estadoCivil;
        this.profissao = profissao;
    }
    
    public PessoaFisica(String cpf, String rg, String nome, String logradouro, Integer nro, String bairro, String cidade,
            String estado, String cep, String telefone, String celular, String email, String dataNasc) {
        super(logradouro, nro, bairro, cidade, estado, cep, telefone, celular, email);
        this.cpf = cpf;
        this.rg = rg;
        this.nome = nome;
        this.dataNasc = dataNasc;
    }

    public PessoaFisica(String email, String cpf, String nome) {
        super(email);
        this.cpf = cpf;
        this.nome = nome;
    }

    public PessoaFisica() {

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }
    

}
