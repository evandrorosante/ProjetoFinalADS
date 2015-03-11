package br.com.ads.prj.controller;

import br.com.ads.prj.model.AlertaAudiencia;
import br.com.ads.prj.model.DAO.UsuarioDAO;
import br.com.ads.prj.model.PessoaFisica;
import br.com.ads.prj.model.Usuario;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import org.quartz.CronTrigger;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Administrador
 */
@WebServlet(name = "Controlador", urlPatterns = {"/SistemaJuridico", "/index.html"})
public class Controlador extends HttpServlet {

    @Override
    public void init() throws ServletException {

        try {
            //verifica os dados de configuração no banco
            /*    ConfigAlerta ca = ConfigAlertaDAO.getInstance().getConfigAlerta();
             String horario[] = ca.getHorario().split(":");
             String hora = horario[0];
             String minuto = horario[1];*/

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
                    //  .withSchedule(cronSchedule("0 " + minuto + " " + hora + " * * ?"))
                    .withSchedule(cronSchedule("0/60 * * * * ?"))
                    .build();

            //Adicionamos o job e cron
            sched.scheduleJob(job, trigger);

            //Iniciamos scheduler
            sched.start();

        } catch (SchedulerException x) {
            System.out.println(x.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            boolean primeiroAcesso = (boolean) request.getSession().getAttribute("primeiroAcesso");
            //verifica se é o primeiro acesso e força mudança de senha
            if (primeiroAcesso) {
                //verifica se a senha já foi alterada e e-mail enviado
                boolean emailEnviado = (boolean) request.getSession().getAttribute("emailEnviado");
                if (emailEnviado) {
                    request.getRequestDispatcher("/WEB-INF/primeiro-acesso.jsp").forward(request, response);
                } else {
                    request.getSession().setAttribute("emailEnviado", false);
                    request.getRequestDispatcher("/WEB-INF/primeiro-acesso.jsp").forward(request, response);
                }
            } else {
                Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
                if (usuario.isAtivo()) {
                    //se não for o primeiro acesso encaminha o usuário para a página principal
                    request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                }
            }
        } catch (NullPointerException e) {
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String[]> parametros = request.getParameterMap();
        Usuario usuario;
        PessoaFisica pessoaFisica;
        try {
            switch (parametros.get("op")[0]) {
                case "logar":
                    //retorna usuario de acordo com o login informado
                    usuario = UsuarioDAO.getInstance().retornarUsuario(parametros.get("login")[0]);
                    pessoaFisica = UsuarioDAO.getInstance().retornaPessoaFisica(parametros.get("login")[0]);
                    //verifica se é primeiro acesso, caso seja o usuário precisa modificar a senha padrão
                    if (pessoaFisica.getCpf().equals(usuario.getSenha())) {
                        if (parametros.get("senha")[0].equals(usuario.getSenha())) {
                            request.getSession().setAttribute("emailEnviado", false);
                            request.getSession().setAttribute("primeiroAcesso", true);
                            request.getSession().setAttribute("usuario", usuario);
                            request.getRequestDispatcher("/WEB-INF/primeiro-acesso.jsp").forward(request, response);
                        } else {
                            request.setAttribute("erro", "Usuário ou senha incorreta");
                            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                        }
                    } else {
                        request.getSession().setAttribute("primeiroAcesso", false);
                        //verifica se a senha está correta e se o usuário está ativo
                        if (BCrypt.checkpw(parametros.get("senha")[0], usuario.getSenha())) {
                            if (usuario.isAtivo()) {
                                request.getSession().setAttribute("emailEnviado", false);
                                request.getSession().setAttribute("usuario", usuario);
                                //tempo de 20 minutos para a sessão
                                request.getSession().setMaxInactiveInterval(20 * 60);
                                request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                            } else {
                                request.setAttribute("erro", "Usuário inativo.");
                                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                            }
                        } else {
                            request.setAttribute("erro", "Usuário ou senha incorreta");
                            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                        }
                    }
                    break;
                case "sair":
                    request.getSession().invalidate();
                    response.sendRedirect("SistemaJuridico");
                    break;
            }
        } catch (NullPointerException e) {
            System.out.println("Erro: " + e);
            response.sendRedirect("SistemaJuridico");
        } catch (Exception e) {
            System.out.println("Erro: " + e);
            response.sendRedirect("SistemaJuridico");
        }

    }
}
