package br.com.ads.prj.controller;

import br.com.ads.prj.model.Audiencia;
import br.com.ads.prj.model.DAO.AudienciaDAO;
import br.com.ads.prj.model.DAO.ProcessoDAO;
import br.com.ads.prj.model.Data;
import br.com.ads.prj.model.Processo;
import br.com.ads.prj.model.Usuario;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Evandro
 */
@WebServlet(name = "ControleAudiencia", urlPatterns = {"/ControleAudiencia"})
@MultipartConfig(maxFileSize = 1024 * 1024 * 10) //máximo 10 megabytes
public class ControleAudiencia extends HttpServlet {

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
                        case "novaAudiencia":
                            List<Processo> listaProcessos = ProcessoDAO.getInstance().getListProcessos();
                            request.setAttribute("listaProcessos", listaProcessos);
                            request.getRequestDispatcher("/WEB-INF/consulta-processos-audiencias.jsp").forward(request, response);
                            break;
                        case "consultaProcessoPorNum":
                            Long nro = Long.parseLong(parametros.get("numProcesso")[0]);
                            Processo processo = ProcessoDAO.getInstance().getProcessosByNumero(nro);
                            request.setAttribute("processo", processo);
                            request.getRequestDispatcher("/WEB-INF/consulta-processos-audiencias.jsp").forward(request, response);
                            break;
                        case "cadastrarAudiencia":
                            int idProcesso = Integer.parseInt(parametros.get("idProcesso")[0]);
                            Processo p = ProcessoDAO.getInstance().getProcessosById(idProcesso);
                            request.setAttribute("processo", p);
                            request.getRequestDispatcher("/WEB-INF/cadastro-audiencia.jsp").forward(request, response);
                            break;
                        case "consultarAudiencia":
                            List<Audiencia> l = AudienciaDAO.getInstance().getListAudiencia();
                            request.setAttribute("listaAudiencias", l);
                            request.getRequestDispatcher("/WEB-INF/consulta-audiencias.jsp").forward(request, response);
                            break;
                        case "listarAudienciaPorNumero":
                            long numProcesso = Long.parseLong(parametros.get("numProcesso")[0]);
                            Audiencia aud = AudienciaDAO.getInstance().getAudienciaByNumProcesso(numProcesso);
                            request.setAttribute("audiencia", aud);
                            request.getRequestDispatcher("/WEB-INF/consulta-audiencias.jsp").forward(request, response);
                            break;
                        case "editarAudiencia":
                            int idAudiencia = Integer.parseInt(parametros.get("idAudiencia")[0]);
                            Audiencia audiencia = AudienciaDAO.getInstance().getAudienciaById(idAudiencia);
                            audiencia.setData(Data.getInstance().getDataMySql(audiencia.getData()));
                            request.setAttribute("audiencia", audiencia);
                            request.getRequestDispatcher("/WEB-INF/edita-audiencia.jsp").forward(request, response);
                            break;
                        case "exibirArquivo":
                            String nomeArq = parametros.get("nome")[0];
                            InputStream ata = AudienciaDAO.getInstance().getAtaByNome(nomeArq);
                            OutputStream saida = response.getOutputStream();
                            byte[] buffer = new byte[1024];
                            int count = 0;
                            while ((count = ata.read(buffer)) >= 0) {
                                saida.write(buffer, 0, count);
                            }
                            saida.close();
                            ata.close();
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
        Part ata = request.getPart("ata");
        try {
            switch (parametros.get("op")[0]) {
                case "cadastraAudiencia":
                    int idProcesso = Integer.parseInt(parametros.get("idProcesso")[0]);
                    String data = parametros.get("data")[0];
                    String horario = parametros.get("horario")[0];
                    Processo processo = new Processo();
                    processo.setIdProcesso(idProcesso);
                    Audiencia audiencia = new Audiencia(processo, data, horario);
                    String cadastrou = AudienciaDAO.getInstance().cadastraAudiencia(audiencia);
                    if (cadastrou.equals("cadastrou")) {
                        request.setAttribute("mensagem", "Audiência cadastrada com sucesso.");
                        request.getRequestDispatcher("/WEB-INF/consulta-processos-audiencias.jsp").forward(request, response);
                    } else {
                        request.setAttribute("mensagem", cadastrou);
                        request.getRequestDispatcher("/WEB-INF/consulta-processos-audiencias.jsp").forward(request, response);
                    }
                    break;
                case "atualizarAudiencia":
                    int idAudiencia = Integer.parseInt(parametros.get("idAudiencia")[0]);
                    String dataAudiencia = parametros.get("data")[0];
                    String horarioAudiencia = parametros.get("horario")[0];
                    InputStream input = null;
                    Audiencia a = null;
                    String retorno = "";
                    //verifica se existe arquivo anexado
                    if (ata.getSize() != 0) {
                        //verifica se é do tipo PDF
                        if (ata.getContentType().equals("application/pdf")) {
                            input = ata.getInputStream();
                            a = new Audiencia(idAudiencia, dataAudiencia, horarioAudiencia,"Ata "+Data.getInstance().getDataPadrao(dataAudiencia), input);
                            retorno = AudienciaDAO.getInstance().editarAudiencia(a);
                            input.close();
                        } else {
                            request.setAttribute("mensagem", "Escolha um arquivo PDF.");
                            request.getRequestDispatcher("/WEB-INF/consulta-audiencias.jsp").forward(request, response);
                        }

                    } else {
                        a = new Audiencia(idAudiencia, dataAudiencia, horarioAudiencia);
                        retorno = AudienciaDAO.getInstance().editarAudiencia(a);
                    }
                    if (retorno.equals("ok")) {
                        request.setAttribute("mensagem", "Audiência atualizada com sucesso.");
                        request.getRequestDispatcher("/WEB-INF/consulta-audiencias.jsp").forward(request, response);
                    } else {
                        request.setAttribute("mensagem", retorno);
                        request.getRequestDispatcher("/WEB-INF/consulta-audiencias.jsp").forward(request, response);
                    }
                    break;
            }
        } catch (NullPointerException e) {
            System.out.println("Erro: " + e);
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }
}
