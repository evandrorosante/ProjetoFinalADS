package br.com.ads.prj.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Evandro
 */
public class Data {

    private static Data instance;

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    public String getDataPadrao(String data) {
        try {
            String dataFormatada[] = data.split(" ");

            String dataSeps[] = dataFormatada[0].split("-");
            String dia = dataSeps[2];
            String mes = dataSeps[1];
            String ano = dataSeps[0];
            return dia + "/" + mes + "/" + ano;

        } catch (Exception e) {
            return "";
        }

    }

    public String getDataMySql(String data) {
        try {
            String dataSeps[] = data.split("/");
            String ano = dataSeps[2];
            String mes = dataSeps[1];
            String dia = dataSeps[0];
            return ano + "-" + mes + "-" + dia;
        } catch (Exception e) {
            return null;
        }
    }

    public String getDataAtualPadraoBrasil() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getDatePadraoInternacional() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    public String getHoraAtual() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public String formataHora(String horario) {
        String hora[] = horario.split(":");
        String h = hora[0]+":"+hora[1];
        return h;
    }
    
    

    public Date retornaDataJava(String data) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            Date date = null;
            date = new Date(dateFormat.parse(data).getTime());
            return date;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
     public Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    

    public String getDataPorExtenso() {

        Calendar c = Calendar.getInstance();

        return String.valueOf(c.get(Calendar.DAY_OF_MONTH) + " de " + this.getMes(c.get(Calendar.MONTH)) + " de " + c.get(Calendar.YEAR));
    }

    public String getMes(int mes) {
        String retorno = null;
        switch (mes) {
            case 0:
                retorno = "Janeiro";
                break;
            case 1:
                retorno = "Fevereiro";
                break;
            case 2:
                retorno = "Março";
                break;
            case 3:
                retorno = "Abril";
                break;
            case 4:
                retorno = "Maio";
                break;
            case 5:
                retorno = "Junho";
                break;
            case 6:
                retorno = "Julho";
                break;
            case 7:
                retorno = "Agosto";
                break;
            case 8:
                retorno = "Setembro";
                break;
            case 9:
                retorno = "Outubro";
                break;
            case 10:
                retorno = "Novembro";
                break;
            case 11:
                retorno = "Dezembro";
                break;
        }
        return retorno;
    }
}
