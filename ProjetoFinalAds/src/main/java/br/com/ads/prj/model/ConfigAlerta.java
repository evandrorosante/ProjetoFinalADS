package br.com.ads.prj.model;

/**
 *
 * @author Evandro
 */
public class ConfigAlerta {
    
    private String horario;
    private int dias;

    public ConfigAlerta(String horario, int dias) {
        this.horario = horario;
        this.dias = dias;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }
    
    
    
}