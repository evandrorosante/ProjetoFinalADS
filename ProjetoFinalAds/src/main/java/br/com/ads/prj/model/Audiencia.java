package br.com.ads.prj.model;

import java.io.InputStream;

/**
 *
 * @author Evandro
 */
public class Audiencia {
    
    private int idAudiencia;
    private Processo processo;
    private String data;
    private String horario;
    private String nomeArquivo;
    private InputStream ata;

    public Audiencia(Processo processo, String data, String horario) {
        this.processo = processo;
        this.data = data;
        this.horario = horario;
    }

    public Audiencia(int idAudiencia, Processo processo, String data, String horario) {
        this.idAudiencia = idAudiencia;
        this.processo = processo;
        this.data = data;
        this.horario = horario;
    }

    public Audiencia(int idAudiencia, Processo processo, String data, String horario,String nomeArquivo, InputStream parecer) {
        this.idAudiencia = idAudiencia;
        this.processo = processo;
        this.data = data;
        this.horario = horario;
        this.nomeArquivo = nomeArquivo;
        this.ata = parecer;
    }

    public Audiencia(int idAudiencia, String data, String horario, String nomeArquivo, InputStream ata) {
        this.idAudiencia = idAudiencia;
        this.data = data;
        this.horario = horario;
        this.nomeArquivo = nomeArquivo;
        this.ata = ata;
    }
    
    public Audiencia(int idAudiencia, String data, String horario) {
        this.idAudiencia = idAudiencia;
        this.data = data;
        this.horario = horario;
    }

    
    public int getIdAudiencia() {
        return idAudiencia;
    }

    public void setIdAudiencia(int idAudiencia) {
        this.idAudiencia = idAudiencia;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public InputStream getAta() {
        return ata;
    }

    public void setAta(InputStream ata) {
        this.ata = ata;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
    
    
    
    
}
