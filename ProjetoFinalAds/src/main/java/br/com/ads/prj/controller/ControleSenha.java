package br.com.ads.prj.controller;

import br.com.ads.prj.model.DAO.SenhaTempDAO;
import br.com.ads.prj.model.DAO.UsuarioDAO;
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
@WebServlet(name = "ControleSenha", urlPatterns = {"/ControleSenha"})
public class ControleSenha extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String[]> parametros = request.getParameterMap();
        String primeiraLetra = parametros.get("param")[0].substring(0, 1);
        String id = parametros.get("param")[0].substring(0, 0) + parametros.get("param")[0].substring(0 + 1);
        try {
            //altera senha quando o usuário não esqueceu
            if (primeiraLetra.equals("A") && !id.equals("")) {
                String novaSenha = SenhaTempDAO.getInstance().buscaNovaSenha(id);
                Integer idUser = SenhaTempDAO.getInstance().buscaIdUser(id);
                UsuarioDAO.getInstance().atualizaSenha(novaSenha, idUser);
                request.setAttribute("mensagem", "Senha alterada com sucesso!Por favor, faça o login novamente.");
                request.getSession().invalidate();
                request.getRequestDispatcher("SistemaJuridico").forward(request, response);
            }
            //encaminha o usuário para criar uma nova senha quando ele esqueceu
            else if (primeiraLetra.equals("R") && !id.equals("")) {
                request.setAttribute("idRecSenha", id);
                request.getRequestDispatcher("/WEB-INF/recuperasenha.jsp").forward(request, response);
            } else{
                response.sendRedirect("SistemaJuridico");
            }
        } catch (NullPointerException e) {
            System.out.println("Erro: " + e);
            response.sendRedirect("SistemaJuridico");
        } catch (ServletException | IOException e) {
            System.out.println("Erro: " + e);
            response.sendRedirect("SistemaJuridico");
        }

    }

}
