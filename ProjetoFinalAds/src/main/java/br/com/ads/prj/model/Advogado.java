package br.com.ads.prj.model;

/**
 *
 * @author Evandro
 */
public class Advogado extends PessoaFisica {

    private int oab;
    private String uf;

    public Advogado(int oab, String uf, String cpf, String rg, String nome, String logradouro, Integer nro, String bairro, String cidade, String estado, String cep, String telefone, String celular, String email, String dataNasc) {
        super(cpf, rg, nome, logradouro, nro, bairro, cidade, estado, cep, telefone, celular, email, dataNasc);
        this.oab = oab;
        this.uf = uf;
    }

    public Advogado(int oab, String uf, String cpf, String rg, String nome, String logradouro, Integer nro, String bairro, String cidade, String estado, String cep, String telefone, String celular, String email, String dataNasc, String nacionalidade, String estadoCivil) {
        super(cpf, rg, nome, logradouro, nro, bairro, cidade, estado, cep, telefone, celular, email, dataNasc, nacionalidade, estadoCivil);
        this.oab = oab;
        this.uf = uf;
    }
    
    public Advogado(String email, String cpf, String nome, int oab, String uf) {
        super(email, cpf, nome);
        this.oab = oab;
        this.uf = uf;
    }
    
    public Advogado(int oab){
        this.oab = oab;
    }

    public int getOab() {
        return oab;
    }

    public void setOab(int oab) {
        this.oab = oab;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

}
