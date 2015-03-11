package br.com.ads.prj.model;

import br.com.ads.prj.controller.*;
import br.com.ads.prj.model.DAO.AudienciaDAO;
import br.com.ads.prj.model.DAO.ConfigAlertaDAO;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Evandro
 */
public class AlertaAudiencia implements Job {

    private PessoaFisica pf = null;
    private PessoaJuridica pj = null;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        
        System.out.println("Executando tarefa: "+new Date());
        
        //buscando coonfigurações de horas e dias no banco
        ConfigAlerta configAlerta = ConfigAlertaDAO.getInstance().getConfigAlerta();
        
        //verificar se é hora configurada pelo usuário
        String horarioBanco = Data.getInstance().formataHora(configAlerta.getHorario());
        String horaAtual = Data.getInstance().formataHora(Data.getInstance().getHoraAtual());

        if (horarioBanco.equals(horaAtual)) {

            //busca a quantidade de dias antes da audiência
            int dias = configAlerta.getDias();

            //busca lista de datas de audiências
            List<Audiencia> listaAudiencias = AudienciaDAO.getInstance().getListAudiencia();
            //recebe a data atual sem horas
            String d = Data.getInstance().getDataAtualPadraoBrasil();
            Date dataAtual = Data.getInstance().retornaDataJava(d);
            //converte a dataAtual em calendar
            Calendar dataAtualCal = Data.getInstance().DateToCalendar(dataAtual);
            //laço para verificar a lista de audiencias 
            for (Audiencia l : listaAudiencias) {
                //converte a data da audiencia tipo string em java.util.Date
                Date dataAudiencia = Data.getInstance().retornaDataJava(l.getData());
                //verifica se a data da audiência é menor que a data atual
                if (dataAudiencia.compareTo(dataAtual) >= 0) {
                    //se verdadeiro gera um objeto do tipo calendar com essa data da audiência
                    Calendar dataAudienciaCal = Data.getInstance().DateToCalendar(dataAudiencia);
                    //adiciona 5 dias a data atual
                    dataAtualCal.add(Calendar.DAY_OF_MONTH, dias);
                    //verifica se a data atual somada com 5 dias é igual a data da audência
                    if (dataAtualCal.compareTo(dataAudienciaCal) == 0) {
                    //código para 5 dias antes da audiência
                        //envia o e-mail para o cliente informando que faltam 5 dias para a audiência
                        JavaMailApp.enviaMail(l.getProcesso().getPessoa().getEmail(), "Lembrete de Audiência", "O Sistema de Gestão Juridica"
                                + " informa que falta(m) apenas "+configAlerta.getDias()+" dia(s) para a sua audiência referente"
                                + " ao Processo número " + l.getProcesso().getNumProcesso() + ","
                                + l.getProcesso().getTipoAcao() + ", marcada para as " + l.getHorario()
                                + " de " + l.getData() + ".");
                        //percorre lista de advogados para avisá-los pro email da audiência
                        for (Advogado adv : l.getProcesso().getAdvogados()) {
                            //verificando se o processo é de cliente pf ou pj
                            if (l.getProcesso().getPessoa() instanceof PessoaFisica) {
                                pf = (PessoaFisica) l.getProcesso().getPessoa();
                                //envia o e-mail para o advogado informando que faltam 5 dias para a audiência
                                JavaMailApp.enviaMail(adv.getEmail(), "Lembrete de Audiência", "O Sistema de Gestão Juridica"
                                        + " informa que falta(m) apenas "+configAlerta.getDias()+" dia(s) para a audiência referente"
                                        + " ao Processo número " + l.getProcesso().getNumProcesso() + "," + l.getProcesso().getTipoAcao() + ", "
                                        + "do cliente " + pf.getNome() + ", marcada para as " + l.getHorario() + " de " + l.getData() + ".");
                            } else {
                                pj = (PessoaJuridica) l.getProcesso().getPessoa();
                                JavaMailApp.enviaMail(adv.getEmail(), "Lembrete de Audiência", "O Sistema de Gestão Juridica"
                                        + " informa que falta(m) apenas "+configAlerta.getDias()+" dia(s) para a audiência referente"
                                        + " ao Processo número " + l.getProcesso().getNumProcesso() + "," + l.getProcesso().getTipoAcao() + ", "
                                        + "da empresa " + pj.getRazaoSocial() + ", marcada para as " + l.getHorario() + " de " + l.getData() + ".");
                            }
                        }

                    } else {
                        //remove os cinco dias adicionados a data atual
                        dataAtualCal.add(Calendar.DAY_OF_MONTH, -dias);
                        //verifica se data atual é a data da audiência
                        if (dataAtualCal.compareTo(dataAudienciaCal) == 0) {
                        //código para mesmo dia da audiência
                            //envia o e-mail para o cliente informando que já é o dia da audiência
                            JavaMailApp.enviaMail(l.getProcesso().getPessoa().getEmail(), "Lembrete de Audiência", "O Sistema de Gestão Juridica"
                                    + " informa que hoje as " + l.getHorario() + ", acontecerá  a audiência referente"
                                    + " ao Processo número " + l.getProcesso().getNumProcesso() + ","
                                    + l.getProcesso().getTipoAcao() + ".");
                            //percorre lista de advogados para avisá-los pro email da audiência
                            for (Advogado adv : l.getProcesso().getAdvogados()) {
                                //verificando se o processo é de cliente pf ou pj
                                if (l.getProcesso().getPessoa() instanceof PessoaFisica) {
                                    pf = (PessoaFisica) l.getProcesso().getPessoa();
                                    //envia o e-mail para o advogado informando que já é o dia da audiência
                                    JavaMailApp.enviaMail(adv.getEmail(), "Lembrete de Audiência", "O Sistema de Gestão Juridica"
                                            + " informa que hoje as " + l.getHorario() + " acontecerá a audiência referente"
                                            + " ao Processo número " + l.getProcesso().getNumProcesso() + "," + l.getProcesso().getTipoAcao() + ", "
                                            + "do cliente " + pf.getNome() + ".");
                                } else {
                                    pj = (PessoaJuridica) l.getProcesso().getPessoa();
                                    JavaMailApp.enviaMail(adv.getEmail(), "Lembrete de Audiência", "O Sistema de Gestão Juridica"
                                            + " informa que hoje as " + l.getHorario() + " acontecerá a audiência referente"
                                            + " ao Processo número " + l.getProcesso().getNumProcesso() + "," + l.getProcesso().getTipoAcao() + ", "
                                            + "da empresa " + pj.getRazaoSocial() + ".");
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
