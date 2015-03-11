package br.com.ads.prj.controller;

import br.com.ads.prj.model.Advogado;
import br.com.ads.prj.model.DAO.PessoaFisicaDAO;
import br.com.ads.prj.model.DAO.PessoaJuridicaDAO;
import br.com.ads.prj.model.DAO.ProcessoDAO;
import br.com.ads.prj.model.Data;
import br.com.ads.prj.model.Pessoa;
import br.com.ads.prj.model.Processo;
import br.com.ads.prj.model.PessoaFisica;
import br.com.ads.prj.model.PessoaJuridica;
import br.com.ads.prj.model.Usuario;
import java.io.IOException;
import java.util.ArrayList;
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
@WebServlet(name = "ControleProcessos", urlPatterns = {"/ControleProcessos"})
public class ControleProcessos extends HttpServlet {

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
                        case "novoProcesso":
                            request.getRequestDispatcher("/WEB-INF/cadastro-processo.jsp").forward(request, response);
                            break;
                        case "consultaProcesso":
                            List<Processo> listaProcessos = ProcessoDAO.getInstance().getListProcessos();
                            request.setAttribute("listaProcessos", listaProcessos);
                            request.getRequestDispatcher("/WEB-INF/consulta-processos.jsp").forward(request, response);
                            break;
                        case "consultaProcessoPorNum":
                            Long nro = Long.parseLong(parametros.get("numProcesso")[0]);
                            Processo processo = ProcessoDAO.getInstance().getProcessosByNumero(nro);
                            request.setAttribute("processo", processo);
                            request.getRequestDispatcher("/WEB-INF/consulta-processos.jsp").forward(request, response);
                            break;
                        case "editarProcesso":
                            int idProcesso = Integer.parseInt(parametros.get("idProcesso")[0]);
                            Processo processoEdita = ProcessoDAO.getInstance().getProcessosById(idProcesso);
                            processoEdita.setDataPeticao(Data.getInstance().getDataMySql(processoEdita.getDataPeticao()));
                            request.setAttribute("processo", processoEdita);
                            request.getRequestDispatcher("/WEB-INF/edita-processo.jsp").forward(request, response);
                            break;
                        case "procuracao":
                            int id = Integer.parseInt(request.getParameter("idProcesso"));
                            Processo pp = ProcessoDAO.getInstance().getProcessosById(id);
                            request.setAttribute("pp", pp);
                            //verificar se é processo de PF ou PJ
                            if (pp.getPessoa() instanceof PessoaFisica) {
                                request.getRequestDispatcher("/WEB-INF/procuracaoPf.jsp").forward(request, response);
                            } else {
                                request.getRequestDispatcher("/WEB-INF/procuracaoPj.jsp").forward(request, response);
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
        List<Advogado> listaAdv = new ArrayList<>();
        Processo processo;

        String requerido = parametros.get("requerido")[0];
        String tipoAcao = parametros.get("tipoAcao")[0];
        String assunto = parametros.get("assunto")[0];
        String situacao = parametros.get("situacao")[0];

        //retirando caracteres da mascára para adicionar valor no banco
        String valor = this.formataValorMySql(parametros.get("valorCausa")[0]);
        double valorCausa = Double.parseDouble(valor);
        //   String vara = parametros.get("vara")[0];
        try {
            switch (parametros.get("op")[0]) {
                case "cadastraProcessoPF":
                    //verificando a quantidade de advogados e inserindo na lista sem repetir oab
                    for (int i = 0; i < request.getParameterValues("oab").length; i++) {
                        boolean adiciona = false;
                        for (Advogado l : listaAdv) {
                            if (parametros.get("oab")[i].equals(String.valueOf(l.getOab()))) {
                                adiciona = true;
                            }
                        }
                        if (!adiciona) {
                            listaAdv.add(new Advogado(Integer.parseInt(parametros.get("oab")[i])));
                        }
                    }
                    String cpf = parametros.get("cpf")[0];
                    PessoaFisica pf = new PessoaFisica();
                    Integer id = PessoaFisicaDAO.getInstance().selecionaIdPorCpf(cpf);
                    pf.setIdPessoa(id);
                    pf.setCpf(cpf);
                    //verifica se já tem numero do processo
                    // if (!parametros.get("nroProcesso")[0].equals("")) {
                    //    nroProcesso = Long.parseLong(parametros.get("nroProcesso")[0]);
                    // }
                    processo = new Processo(pf, requerido, tipoAcao, assunto, situacao, null,
                            null, valorCausa, listaAdv, null);
                    String cadastrou = ProcessoDAO.getInstance().cadastraProcesso(processo);
                    if (cadastrou.equals("cadastrou")) {
                        request.setAttribute("mensagem", "Processo cadastrado com sucesso.");
                        request.getRequestDispatcher("/WEB-INF/cadastro-processo.jsp").forward(request, response);
                    } else {
                        request.setAttribute("mensagem", cadastrou);
                        request.getRequestDispatcher("/WEB-INF/cadastro-processo.jsp").forward(request, response);
                    }
                    break;
                case "cadastraProcessoPJ":
                    //verificando a quantidade de advogados e inserindo na lista sem repetir oab
                    for (int i = 0; i < request.getParameterValues("oab").length; i++) {
                        boolean adiciona = false;
                        for (Advogado l : listaAdv) {
                            if (parametros.get("oab")[i].equals(String.valueOf(l.getOab()))) {
                                adiciona = true;
                            }
                        }
                        if (!adiciona) {
                            listaAdv.add(new Advogado(Integer.parseInt(parametros.get("oab")[i])));
                        }
                    }
                    String cnpj = parametros.get("cnpj")[0];
                    PessoaJuridica pj = new PessoaJuridica();
                    id = PessoaJuridicaDAO.getInstance().selecionaIdPorCNPJ(cnpj);
                    pj.setIdPessoa(id);
                    pj.setCnpj(cnpj);
                    //verifica se já tem numero do processo
                    //  if (!parametros.get("nroProcesso")[0].equals("")) {
                    //     nroProcesso = Long.parseLong(parametros.get("nroProcesso")[0]);
                    // }
                    processo = new Processo(pj, requerido, tipoAcao, assunto, situacao, null,
                            null, valorCausa, listaAdv, null);
                    String cadastrouPJ = ProcessoDAO.getInstance().cadastraProcesso(processo);
                    if (cadastrouPJ.equals("cadastrou")) {
                        request.setAttribute("mensagem", "Processo cadastrado com sucesso.");
                        request.getRequestDispatcher("/WEB-INF/cadastro-processo.jsp").forward(request, response);
                    } else {
                        request.setAttribute("mensagem", cadastrouPJ);
                        request.getRequestDispatcher("/WEB-INF/cadastro-processo.jsp").forward(request, response);
                    }
                    break;
                case "atualizaProcesso":
                    String retorno;
                    Long nroProcesso = null;
                    Processo atualizaProcesso;
                    String vara = parametros.get("vara")[0];
                    String dataPeticao = parametros.get("dataPeticao")[0];
                    if (!parametros.get("nroProcesso")[0].equals("")) {
                        nroProcesso = Long.parseLong(parametros.get("nroProcesso")[0]);
                    }
                    ///verificando a quantidade de advogados e inserindo na lista sem repetir oab
                    for (int i = 0; i < request.getParameterValues("oab").length; i++) {
                        boolean adiciona = true;
                        for (Advogado l : listaAdv) {
                            if (parametros.get("oab")[i].equals(String.valueOf(l.getOab()))
                                    || parametros.get("oab")[i].equals("")) {
                                adiciona = false;
                            }
                        }
                        if (adiciona && !parametros.get("oab")[i].equals("")) {
                            listaAdv.add(new Advogado(Integer.parseInt(parametros.get("oab")[i])));
                        }
                    }
                    //verificando pelo idPessoa do processo se é pessoa fisica ou pessoa jurídica
                    int idProcesso = Integer.parseInt(parametros.get("idProcesso")[0]);
                    Processo p = ProcessoDAO.getInstance().getProcessosById(idProcesso);
                    Pessoa pessoa = PessoaFisicaDAO.getInstance().getClienteById(p.getPessoa().getIdPessoa());
                    if (pessoa instanceof PessoaFisica) {
                        atualizaProcesso = new Processo(idProcesso, pessoa, requerido, tipoAcao, assunto, situacao, dataPeticao,
                                nroProcesso, valorCausa, listaAdv, vara);
                        retorno = ProcessoDAO.getInstance().editarProcesso(atualizaProcesso);
                        if (retorno.equals("ok")) {
                            request.setAttribute("mensagem", "Dados atualizados com sucesso.");
                            request.getRequestDispatcher("/WEB-INF/consulta-processos.jsp").forward(request, response);
                        } else {
                            request.setAttribute("mensagem", retorno);
                            request.getRequestDispatcher("/WEB-INF/consulta-processos.jsp").forward(request, response);
                        }
                    } else {
                        PessoaJuridica pjProcesso = PessoaJuridicaDAO.getInstance().getClienteById(p.getPessoa().getIdPessoa());
                        atualizaProcesso = new Processo(idProcesso, pjProcesso, requerido, tipoAcao, assunto, situacao, dataPeticao,
                                nroProcesso, valorCausa, listaAdv, vara);
                        retorno = ProcessoDAO.getInstance().editarProcesso(atualizaProcesso);
                        if (retorno.equals("ok")) {
                            request.setAttribute("mensagem", "Dados atualizados com sucesso.");
                            request.getRequestDispatcher("/WEB-INF/consulta-processos.jsp").forward(request, response);
                        } else {
                            request.setAttribute("mensagem", retorno);
                            request.getRequestDispatcher("/WEB-INF/consulta-processos.jsp").forward(request, response);
                        }
                    }
                    break;
            }
        } catch (NullPointerException e) {
            System.out.println("Erro: " + e);
            response.sendRedirect("ControleProcessos");
        } catch (Exception e) {
            System.out.println("Erro: " + e);
            response.sendRedirect("ControleProcessos");
        }
    }

    public String formataValorMySql(String valor) {
        valor = valor.replace("R$", "");
        valor = valor.replace(" ", "");
        valor = valor.replace(".", "");
        valor = valor.replace(",", ".");
        return valor;
    }

    public Double formataValorJsp(String valor) {
        String novo = (valor + "0");
        double nro = Double.parseDouble(novo);
        return nro;
    }
}
