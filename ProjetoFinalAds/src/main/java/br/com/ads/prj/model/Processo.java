package br.com.ads.prj.model;

import java.util.List;

/**
 *
 * @author Evandro
 */
public class Processo {

    private int idProcesso;
    private Pessoa pessoa;
    private String requerido;
    private String tipoAcao;
    private String assuntoPrincipal;
    private String situacao;
    private String dataPeticao;
    private Long numProcesso;
    private double valorCausa;
    private String vara;
    private List<Advogado> advogados;

    public Processo() {
    }

    public Processo(int idProcesso, Pessoa pessoa, String requerido, String tipoAcao, String assuntoPrincipal, String situacao, String dataPeticao, Long numProcesso, double valorCausa, List<Advogado> advogados, String vara) {
        this.idProcesso = idProcesso;
        this.pessoa = pessoa;
        this.requerido = requerido;
        this.tipoAcao = tipoAcao;
        this.assuntoPrincipal = assuntoPrincipal;
        this.situacao = situacao;
        this.dataPeticao = dataPeticao;
        this.numProcesso = numProcesso;
        this.valorCausa = valorCausa;
        this.advogados = advogados;
        this.vara = vara;
    }

    public Processo(Pessoa pessoa, String requerido, String tipoAcao, String assuntoPrincipal, String situacao, String dataPeticao, Long numProcesso, double valorCausa, List<Advogado> advogados, String vara) {
        this.pessoa = pessoa;
        this.requerido = requerido;
        this.tipoAcao = tipoAcao;
        this.assuntoPrincipal = assuntoPrincipal;
        this.situacao = situacao;
        this.dataPeticao = dataPeticao;
        this.numProcesso = numProcesso;
        this.valorCausa = valorCausa;
        this.advogados = advogados;
        this.vara = vara;
    }

    public int getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(int idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getRequerido() {
        return requerido;
    }

    public void setRequerido(String requerido) {
        this.requerido = requerido;
    }

    public String getTipoAcao() {
        return tipoAcao;
    }

    public void setTipoAcao(String tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

    public String getAssuntoPrincipal() {
        return assuntoPrincipal;
    }

    public void setAssuntoPrincipal(String assuntoPrincipal) {
        this.assuntoPrincipal = assuntoPrincipal;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getDataPeticao() {
        return dataPeticao;
    }

    public void setDataPeticao(String dataPeticao) {
        this.dataPeticao = dataPeticao;
    }

    public Long getNumProcesso() {
        return numProcesso;
    }

    public void setNumProcesso(long numProcesso) {
        this.numProcesso = numProcesso;
    }

    public double getValorCausa() {
        return valorCausa;
    }

    public void setValorCausa(double valorCausa) {
        this.valorCausa = valorCausa;
    }

    public List<Advogado> getAdvogados() {
        return advogados;
    }

    public void setAdvogados(List<Advogado> advogados) {
        this.advogados = advogados;
    }

    public String getVara() {
        return vara;
    }

    public void setVara(String vara) {
        this.vara = vara;
    }
}
