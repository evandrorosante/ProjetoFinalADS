package br.com.ads.prj.controller;

import br.com.ads.prj.model.Advogado;
import br.com.ads.prj.model.DAO.RecuperaSenhaDAO;
import br.com.ads.prj.model.DAO.SenhaTempDAO;
import br.com.ads.prj.model.DAO.UsuarioDAO;
import br.com.ads.prj.model.PessoaFisica;
import br.com.ads.prj.model.Usuario;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Evandro
 */
@WebServlet(name = "ControleUsuario", urlPatterns = {"/ControleUsuario"})
public class ControleUsuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String[]> parametros = request.getParameterMap();
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

                    switch (parametros.get("op")[0]) {
                        case "cadastrausuario":
                            request.getRequestDispatcher("/WEB-INF/cadastro-usuarios.jsp").forward(request, response);
                            break;
                        case "alterarsenha":
                            request.getRequestDispatcher("/WEB-INF/primeiro-acesso.jsp").forward(request, response);
                            break;
                        case "consultausuarios":
                            List<Usuario> listaUsuarios = UsuarioDAO.getInstance().getListUsuarios();
                            request.setAttribute("listaUsuarios", listaUsuarios);
                            request.getRequestDispatcher("/WEB-INF/consulta-usuarios.jsp").forward(request, response);
                        case "editaUsuario":
                            String ativo = parametros.get("ativo")[0];
                            int idPessoa = Integer.parseInt(parametros.get("idPessoa")[0]);
                            String retorno = UsuarioDAO.getInstance().atualizaStatus(idPessoa, Byte.parseByte(ativo));
                            if (retorno.equals("ok")) {
                                List<Usuario> listaU = UsuarioDAO.getInstance().getListUsuarios();
                                request.setAttribute("listaUsuarios", listaU);
                                request.setAttribute("mensagem", "Status alterado com sucesso.");
                                request.getRequestDispatcher("/WEB-INF/consulta-usuarios.jsp").forward(request, response);
                            } else {
                                List<Usuario> listaUs = UsuarioDAO.getInstance().getListUsuarios();
                                request.setAttribute("listaUsuarios", listaUs);
                                request.setAttribute("mensagem", retorno);
                                request.getRequestDispatcher("/WEB-INF/consulta-usuarios.jsp").forward(request, response);
                            }
                            break;
                        default:
                            response.sendRedirect("SistemaJuridico");
                            break;
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Erro: " + e);
           // response.sendRedirect("SistemaJuridico");
        } catch (ServletException | IOException | NumberFormatException e) {
            System.out.println("Erro: " + e);
            response.sendRedirect("SistemaJuridico");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String[]> parametros = request.getParameterMap();
        String mail;
        String cpf;
        String nome;
        int nrooab;
        String uf;
        Usuario usuario;

        try {
            switch (parametros.get("op")[0]) {
                case "cadastraAdvogado":
                    mail = parametros.get("email")[0];
                    cpf = parametros.get("cpf")[0];
                    nome = parametros.get("nome")[0];
                    nrooab = Integer.parseInt(parametros.get("oab")[0]);
                    uf = parametros.get("uf")[0];
                    //cadastra usuário do tipo advogado
                    Advogado advogado = new Advogado(mail, cpf, nome, nrooab, uf);
                    UsuarioDAO.getInstance().cadastraUsuarioAdv(advogado);
                    request.setAttribute("cadastro", "Cadastro realizado com sucesso!");
                    request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                    break;
                case "cadastraSecretaria":
                    mail = parametros.get("email")[0];
                    cpf = parametros.get("cpf")[0];
                    nome = parametros.get("nome")[0];
                    //cadastra usuário do tipo secretária
                    PessoaFisica secretaria = new PessoaFisica(mail, cpf, nome);
                    UsuarioDAO.getInstance().cadastraUsuarioSec(secretaria);
                    request.setAttribute("cadastro", "Cadastro realizado com sucesso!");
                    request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                    break;
                //atualiza a senha no primeiro acesso ou quando o usuário quiser trocar a senha
                case "atualizaSenhaAcesso1":
                    boolean primeiroAcesso = (boolean) request.getSession().getAttribute("primeiroAcesso");
                    usuario = (Usuario) request.getSession().getAttribute("usuario");
                    //se for o primeiro acesso a verificação da senha é diferente
                    if (primeiroAcesso) {
                        //confere se a senha atual está correta e se a nova senha foi digitada corretamente 2 vezes e é maior que 5
                        if (usuario.getSenha().equals(parametros.get("senhaAtual")[0])
                                && parametros.get("senha1")[0].equals(parametros.get("senha2")[0])
                                && parametros.get("senha1")[0].length() > 5) {
                            SenhaTempDAO.getInstance().cadastraSenhaTemp(usuario.getPessoa().getIdPessoa(), BCrypt.hashpw(parametros.get("senha1")[0], BCrypt.gensalt(14)));
                            String idTemp = SenhaTempDAO.getInstance().retornaIdTemp(usuario.getPessoa().getIdPessoa());
                            JavaMailApp.enviaMail(usuario.getLogin(), "Confirmação de alteração de senha.", "Sistema de Gestão Jurídica - Clique no link para confirmar sua nova senha: "
                                    + "http://localhost:8084/_ProjetoFinal/ControleSenha?param=A" + idTemp);
                            request.getSession().setAttribute("emailEnviado", true);
                            request.getRequestDispatcher("/WEB-INF/primeiro-acesso.jsp").forward(request, response);
                        } else {
                            request.setAttribute("mensagem", "Erro - A senha deve ter o tamanho mínimo de 6 e deve ser digitada igualmente nos dois últimos campos.");
                            request.getRequestDispatcher("/WEB-INF/primeiro-acesso.jsp").forward(request, response);
                        }
                    } else {
                        //verificação da senha quando não é o primeiro acesso
                        if (BCrypt.checkpw(parametros.get("senhaAtual")[0], usuario.getSenha())
                                && parametros.get("senha1")[0].equals(parametros.get("senha2")[0])
                                && parametros.get("senha1")[0].length() > 5) {
                            SenhaTempDAO.getInstance().cadastraSenhaTemp(usuario.getPessoa().getIdPessoa(), BCrypt.hashpw(parametros.get("senha1")[0], BCrypt.gensalt(14)));
                            String idTemp = SenhaTempDAO.getInstance().retornaIdTemp(usuario.getPessoa().getIdPessoa());
                            JavaMailApp.enviaMail(usuario.getLogin(), "Confirmação de alteração de senha.", "Sistema de Gestão Jurídica - Clique no link para confirmar sua nova senha: "
                                    + "http://localhost:8084/_ProjetoFinal/ControleSenha?param=A" + idTemp);
                            request.getSession().setAttribute("emailEnviado", true);
                            request.getRequestDispatcher("/WEB-INF/primeiro-acesso.jsp").forward(request, response);
                        } else {
                            request.setAttribute("mensagem", "Erro - A senha deve ter o tamanho mínimo de 6 e deve ser digitada igualmente nos dois últimos campos.");
                            request.getRequestDispatcher("/WEB-INF/primeiro-acesso.jsp").forward(request, response);
                        }
                    }
                    break;
                //encaminha o usuário para a pagina de recuperação de senha
                case "paginarecuperasenha":
                    request.getRequestDispatcher("/WEB-INF/recuperasenha.jsp").forward(request, response);
                    break;
                //o usuário digita seu e-mail e recebe um link para alterar a senha(isso porque o usuário não a lembra mais)
                case "recuperasenha":
                    usuario = UsuarioDAO.getInstance().retornarUsuario(parametros.get("login")[0]);
                    RecuperaSenhaDAO.getInstance().cadastraRecSenha(usuario);
                    String idRecSenha = RecuperaSenhaDAO.retornaIdRecSenha(usuario.getPessoa().getIdPessoa());
                    JavaMailApp.enviaMail(usuario.getLogin(), "Recuperação de Senha", "Sistema de Gestão Jurídica - Clique no link para criar uma nova senha: "
                            + "http://localhost:8084/_ProjetoFinal/ControleSenha?param=R" + idRecSenha);
                    request.setAttribute("mensagem", "Link enviado para seu e-mail!");
                    request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                    break;
                //atualiza a senha no banco quando o usuário esqueceu e recebeu o link no e-mail
                case "atualizasenha":
                    String id = request.getParameter("idRecSenha");
                    int idUser = RecuperaSenhaDAO.getInstance().retornaIdUserRecSenha(id);
                    if (parametros.get("senha1")[0].equals(parametros.get("senha2")[0])
                            && parametros.get("senha1")[0].length() > 5) {
                        UsuarioDAO.getInstance().atualizaSenha(BCrypt.hashpw(parametros.get("senha1")[0], BCrypt.gensalt(14)), idUser);
                        request.setAttribute("mensagem", "Senha alterada com sucesso!");
                        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                    } else {
                        request.setAttribute("mensagem", "Erro - A senha deve ter o tamanho mínimo de 6 e deve ser digitada igualmente nos dois últimos campos.");
                        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                    }
                    break;
            }
        } catch (NullPointerException e) {
            System.out.println("Erro: " + e);
            response.sendRedirect("SistemaJuridico");
        } catch (NumberFormatException e) {
            System.out.println("Erro: " + e);
            response.sendRedirect("SistemaJuridico");
        } catch (Exception e) {
            System.out.println("Erro: " + e);
            response.sendRedirect("SistemaJuridico");
        }
    }

}
