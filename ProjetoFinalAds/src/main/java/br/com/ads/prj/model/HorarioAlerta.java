package br.com.ads.prj.model;

import br.com.ads.prj.model.DAO.ConfigAlertaDAO;
import java.util.Date;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import org.quartz.CronTrigger;
import org.quartz.Job;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Evandro
 */
public class HorarioAlerta implements Job {

    ConfigAlerta ca;
    String horario[];
    String hora;
    String minuto;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {

        //Aqui executamos nossa tarefa
        System.out.println("Executando tarefa: " + new Date());

        //verifica os dados de configuração no banco
        ca = ConfigAlertaDAO.getInstance().getConfigAlerta();
        horario = ca.getHorario().split(":");
        hora = horario[0];
        minuto = horario[1];
        
        
    }

    public void teste() {
        try {

            //Uso do framework Quartz para criar e agendar tarefas
            //Obtem a referencia do scheduler
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler sched = sf.getScheduler();
            //Criando um jobdetail
            JobDetail job = newJob(AlertaAudiencia.class)
                    .withIdentity("AlertaAudiencia", "grupo")
                    .build();

            //Criando um crontrigger disparado uma vez por dia
            CronTrigger trigger = newTrigger()
                    .withIdentity("trigger", "grupo")
                    .withSchedule(cronSchedule("0 " + minuto + " " + hora + " * * ?"))
                    .build();

            //Adicionamos o job e cron
            sched.scheduleJob(job, trigger);

            //Iniciamos scheduler
            sched.start();

        } catch (Exception x) {
            System.out.println(x.getMessage());
        }
    }
}
