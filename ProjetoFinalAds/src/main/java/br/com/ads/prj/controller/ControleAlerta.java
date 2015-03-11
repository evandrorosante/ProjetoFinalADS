package br.com.ads.prj.controller;

import br.com.ads.prj.model.ConfigAlerta;
import br.com.ads.prj.model.DAO.ConfigAlertaDAO;
import br.com.ads.prj.model.Usuario;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Evandro
 */
@WebServlet(name = "ControleAlerta", urlPatterns = {"/ControleAlerta"})
public class ControleAlerta extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String[]> parametros = request.getParameterMap();

        try {
            //  PessoaFisica usuario = (PessoaFisica) request.getSession().getAttribute("sessao");
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
                    String op = parametros.get("op")[0];
                    switch (op) {
                        case "gerenciaAlertas":
                            ConfigAlerta ca = ConfigAlertaDAO.getInstance().getConfigAlerta();
                            request.setAttribute("ca", ca);
                            request.getRequestDispatcher("/WEB-INF/edita-alerta.jsp").forward(request, response);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            response.sendRedirect("SistemaJuridico");

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String[]> parametros = request.getParameterMap();

        try {
            switch (parametros.get("op")[0]) {
                case "editaAlerta":
                    String horario = parametros.get("horario")[0];
                    int dias = Integer.parseInt(parametros.get("dias")[0]);
                    ConfigAlerta ca = new ConfigAlerta(horario, dias);
                    String retorno = ConfigAlertaDAO.getInstance().atualizaConfigAlerta(ca);

                    if (retorno.equals("ok")) {
                        request.setAttribute("mensagem", "Dados atualizados com sucesso");
                        ConfigAlerta configAlerta = ConfigAlertaDAO.getInstance().getConfigAlerta();
                        request.setAttribute("ca", configAlerta);
                        request.getRequestDispatcher("/WEB-INF/edita-alerta.jsp").forward(request, response);
                    } else {
                        request.setAttribute("mensagem", retorno);
                        ConfigAlerta configAlerta = ConfigAlertaDAO.getInstance().getConfigAlerta();
                        request.setAttribute("ca", configAlerta);
                        request.getRequestDispatcher("/WEB-INF/edita-alerta.jsp").forward(request, response);
                    }

                    break;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendRedirect("SistemaJuridico");
        }

    }

}
