package br.com.ads.prj.controller;

import br.com.ads.prj.model.Advogado;
import br.com.ads.prj.model.DAO.AdvogadoDAO;
import br.com.ads.prj.model.DAO.PessoaFisicaDAO;
import br.com.ads.prj.model.DAO.PessoaJuridicaDAO;
import br.com.ads.prj.model.DAO.UsuarioDAO;
import br.com.ads.prj.model.Data;
import br.com.ads.prj.model.PessoaFisica;
import br.com.ads.prj.model.PessoaJuridica;
import br.com.ads.prj.model.Usuario;
import java.io.IOException;
import java.util.List;
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
@WebServlet(name = "ControlePessoas", urlPatterns = {"/ControlePessoas"})
public class ControlePessoas extends HttpServlet {

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
            }//se não for primeiro acesso 
            else {
                Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
                if (usuario.isAtivo()) {
                    String op = parametros.get("op")[0];
                    switch (op) {
                        case "cadastracliente":
                            request.getRequestDispatcher("/WEB-INF/cadastro-clientes.jsp").forward(request, response);
                            break;
                        case "cadastraAdvogado":
                            request.getRequestDispatcher("/WEB-INF/cadastro-advogados.jsp").forward(request, response);
                            break;
                        case "consultaPessoas":
                            request.getRequestDispatcher("/WEB-INF/consulta-pessoas.jsp").forward(request, response);
                            break;
                        case "ConsultaPF":
                            String colunaPF = request.getParameter("coluna");
                            String dadoPF = request.getParameter("dado");
                            List<PessoaFisica> listaPF = PessoaFisicaDAO.getInstance().getClientesPF(colunaPF, dadoPF);
                            request.setAttribute("listaPF", listaPF);
                            request.getRequestDispatcher("/WEB-INF/consulta-pessoas.jsp").forward(request, response);
                            break;
                        case "ConsultaPJ":
                            String colunaPJ = request.getParameter("coluna");
                            String dadoPJ = request.getParameter("dado");
                            List<PessoaJuridica> listaPJ = PessoaJuridicaDAO.getInstance().getClientesPJ(colunaPJ, dadoPJ);
                            request.setAttribute("listaPJ", listaPJ);
                            request.getRequestDispatcher("/WEB-INF/consulta-pessoas.jsp").forward(request, response);
                            break;
                        case "consultaAdvogado":
                            List<Advogado> listaAdv = AdvogadoDAO.getInstance().getListAdvogados();
                            request.setAttribute("listaAdv", listaAdv);
                            request.getRequestDispatcher("/WEB-INF/consulta-advogados.jsp").forward(request, response);
                            break;
                        case "consultaAdvPorOab":
                            int oab = Integer.parseInt(parametros.get("oab")[0]);
                            Advogado advOab = AdvogadoDAO.getInstance().getAdvogadoByOab(oab);
                            request.setAttribute("AdvOab", advOab);
                            request.getRequestDispatcher("/WEB-INF/consulta-advogados.jsp").forward(request, response);
                            break;
                        case "consultaAdvPorNome":
                            String nome = parametros.get("nome")[0];
                            List<Advogado> advnome = AdvogadoDAO.getInstance().getAdvogadoByNome(nome);
                            request.setAttribute("listaAdv", advnome);
                            request.getRequestDispatcher("/WEB-INF/consulta-advogados.jsp").forward(request, response);
                            break;
                        case "editaPF":
                            String cpf = parametros.get("cpf")[0];
                            PessoaFisica pessoaFisica = PessoaFisicaDAO.getInstance().getClienteByCpf(cpf);
                            pessoaFisica.setDataNasc(Data.getInstance().getDataMySql(pessoaFisica.getDataNasc()));
                            request.setAttribute("pessoaFisica", pessoaFisica);
                            request.getRequestDispatcher("/WEB-INF/edita-pessoaFisica.jsp").forward(request, response);
                            break;
                        case "editaPJ":
                            String cnpj = parametros.get("cnpj")[0];
                            PessoaJuridica pessoaJuridica = PessoaJuridicaDAO.getInstance().getClienteByCnpj(cnpj);
                            request.setAttribute("pessoaJuridica", pessoaJuridica);
                            request.getRequestDispatcher("/WEB-INF/edita-pessoaJuridica.jsp").forward(request, response);
                            break;
                        case "editaAdv":
                            int oabEditaAdv = Integer.parseInt(parametros.get("oab")[0]);
                            Advogado advEdita = AdvogadoDAO.getInstance().getAdvogadoByOab(oabEditaAdv);
                            advEdita.setDataNasc(Data.getInstance().getDataMySql(advEdita.getDataNasc()));
                            request.setAttribute("advogado", advEdita);
                            request.getRequestDispatcher("/WEB-INF/edita-advogado.jsp").forward(request, response);
                            break;
                        default:
                            response.sendRedirect("SistemaJuridico");
                            break;
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Erro: " + e);
            response.sendRedirect("SistemaJuridico");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            response.sendRedirect("SistemaJuridico");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String[]> parametros = request.getParameterMap();
        String logradouro = parametros.get("logradouro")[0];
        int nro = Integer.parseInt(parametros.get("nro")[0]);
        String bairro = parametros.get("bairro")[0];
        String estado = parametros.get("estado")[0];
        String cidade = parametros.get("cidade")[0];
        String cep = parametros.get("cep")[0];
        String telefone = parametros.get("telefone")[0];
        String celular = parametros.get("celular")[0];
        String email = parametros.get("email")[0];
        try {
            switch (parametros.get("op")[0]) {
                case "cadastrarClientePF":
                    String cpf = parametros.get("cpf")[0];
                    String rg = parametros.get("rg")[0];
                    String nome = parametros.get("nome")[0];
                    String dataNasc = parametros.get("datanasc")[0];
                    String nacionalidade = parametros.get("nacionalidade")[0];
                    String estadoCivil = parametros.get("estadoCivil")[0];
                    String profissao = parametros.get("profissao")[0];
                    PessoaFisica pessoaFisica = new PessoaFisica(cpf, rg, nome, logradouro, nro, bairro,
                            cidade, estado, cep, telefone, celular, email, dataNasc, nacionalidade, estadoCivil, profissao);
                    String cadastrouPF = PessoaFisicaDAO.getInstance().cadastraClientePF(pessoaFisica);
                    if (cadastrouPF.equals("cadastrou")) {
                        request.setAttribute("mensagem", "Cadastro realizado com sucesso!");
                        request.getRequestDispatcher("/WEB-INF/cadastro-clientes.jsp").forward(request, response);
                    } else {
                        request.setAttribute("mensagem", cadastrouPF);
                        request.getRequestDispatcher("/WEB-INF/cadastro-clientes.jsp").forward(request, response);
                    }
                    break;
                case "cadastrarClientePJ":
                    //armazenando valores dos parametros pessoa jurídica
                    String cnpj = parametros.get("cnpj")[0];
                    String inscricaoEstadual = parametros.get("inscricaoEstadual")[0];
                    String razaoSocial = parametros.get("razaoSocial")[0];
                    String nomeFantasia = parametros.get("nomeFantasia")[0];
                    PessoaJuridica pessoaJuridica = new PessoaJuridica(cnpj, inscricaoEstadual, razaoSocial, nomeFantasia,
                            logradouro, nro, bairro, cidade, estado, cep, telefone, celular, email);
                    String cadastrouPJ = PessoaJuridicaDAO.getInstance().cadastraClientePJ(pessoaJuridica);
                    if (cadastrouPJ.equals("cadastrou")) {
                        request.setAttribute("mensagem", "Cadastro realizado com sucesso!");
                        request.getRequestDispatcher("/WEB-INF/cadastro-clientes.jsp").forward(request, response);
                    } else {
                        request.setAttribute("mensagem", cadastrouPJ);
                        request.getRequestDispatcher("/WEB-INF/cadastro-clientes.jsp").forward(request, response);
                    }
                    break;
                case "cadastrarAdvogado":
                    //armazenando valores dos paramentros advogado
                    String oabAdv = parametros.get("oab")[0];
                    String ufAdv = parametros.get("ufOab")[0];
                    String cpfAdv = parametros.get("cpf")[0];
                    String rgAdv = parametros.get("rg")[0];
                    String nomeAdv = parametros.get("nome")[0];
                    String dataNascAdv = parametros.get("datanasc")[0];
                    String nacionalidadeAdv = parametros.get("nacionalidade")[0];
                    String estadoCivilAdv = parametros.get("estadoCivil")[0];
                    Advogado advogado = new Advogado(Integer.parseInt(oabAdv), ufAdv, cpfAdv, rgAdv, nomeAdv, logradouro,
                            nro, bairro, cidade, estado, cep, telefone, celular, email, dataNascAdv, nacionalidadeAdv, estadoCivilAdv);
                    String cadastrouAdv = AdvogadoDAO.getInstance().cadastraAdvogado(advogado);
                    if (cadastrouAdv.equals("cadastrou")) {
                        request.setAttribute("mensagem", "Cadastro realizado com sucesso!");
                        request.getRequestDispatcher("/WEB-INF/cadastro-advogados.jsp").forward(request, response);
                    } else {
                        request.setAttribute("mensagem", cadastrouAdv);
                        request.getRequestDispatcher("/WEB-INF/cadastro-advogados.jsp").forward(request, response);
                    }
                    break;
                case "atualizarClientePF":
                    String cpfPF = parametros.get("cpf")[0];
                    String rgPF = parametros.get("rg")[0];
                    String nomePF = parametros.get("nome")[0];
                    String dataNascPF = parametros.get("datanasc")[0];
                    String nacionalidadePF = parametros.get("nacionalidade")[0];
                    String estadoCivilPF = parametros.get("estadoCivil")[0];
                    String profissaoPF = parametros.get("profissao")[0];
                    //busca pessoa para recuperar o ID
                    PessoaFisica pfId = PessoaFisicaDAO.getInstance().getClienteByCpf(cpfPF);
                    //cria nova pessoa com informações atualizadas
                    PessoaFisica pf = new PessoaFisica(pfId.getIdPessoa(), cpfPF, rgPF, nomePF, logradouro, nro,
                            bairro, cidade, estado, cep, telefone, celular, email,dataNascPF,
                            nacionalidadePF, estadoCivilPF, profissaoPF);
                    PessoaFisicaDAO.getInstance().atualizaClientePF(pf);
                    /*Usuario u = UsuarioDAO.getInstance().retornarUsuario(pf.getEmail());
                    request.getSession().setAttribute("usuario", u);*/
                    request.setAttribute("mensagem", "Dados atualizados com sucesso.");
                    request.getRequestDispatcher("/WEB-INF/consulta-pessoas.jsp").forward(request, response);
                    break;
                case "atualizarClientePJ":
                    String cnpjPJ = parametros.get("cnpj")[0];
                    String inscricaoEstadualPJ = parametros.get("inscricaoEstadual")[0];
                    String razaoSocialPJ = parametros.get("razaoSocial")[0];
                    String nomeFantasiaPJ = parametros.get("nomeFantasia")[0];
                    //busca pessoa para recuperar o ID
                    PessoaJuridica pjId = PessoaJuridicaDAO.getInstance().getClienteByCnpj(cnpjPJ);
                    //cria nova pessoa com informações atualizadas                    
                    PessoaJuridica pj = new PessoaJuridica(pjId.getIdPessoa(), cnpjPJ, inscricaoEstadualPJ, razaoSocialPJ,
                            nomeFantasiaPJ, logradouro, nro, bairro, cidade, estado, cep, telefone, celular, email);
                    PessoaJuridicaDAO.getInstance().atualizaClientePF(pj);
                    request.setAttribute("mensagem", "Dados atualizados com sucesso.");
                    request.getRequestDispatcher("/WEB-INF/consulta-pessoas.jsp").forward(request, response);
                    break;
                case "atualizarAdv":
                    int oab = Integer.parseInt(parametros.get("oab")[0]);
                    String uf = parametros.get("uf")[0];
                    String cpfAd = parametros.get("cpf")[0];
                    String rgAd = parametros.get("rg")[0];
                    String nomeAd = parametros.get("nome")[0];
                    String dataNascAd = parametros.get("datanasc")[0];
                    String nacionalidadeAd = parametros.get("nacionalidade")[0];
                    String estadoCivilAd = parametros.get("estadoCivil")[0];
                    int idAdv = Integer.parseInt(parametros.get("idAdvogado")[0]);
                    //cria nova pessoa com informações atualizadas 
                    Advogado adv = new Advogado(oab, uf, cpfAd, rgAd, nomeAd, logradouro, nro, bairro, cidade,
                            estado, cep, telefone, celular, email, dataNascAd,nacionalidadeAd, estadoCivilAd);
                    adv.setIdPessoa(idAdv);
                    AdvogadoDAO.getInstance().atualizaAdvogado(adv);
                    request.setAttribute("mensagem", "Dados atualizados com sucesso.");
                    request.getRequestDispatcher("/WEB-INF/consulta-advogados.jsp").forward(request, response);
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
