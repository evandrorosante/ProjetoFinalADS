package br.com.ads.prj.controller;

import br.com.ads.prj.model.Advogado;
import br.com.ads.prj.model.DAO.AdvogadoDAO;
import br.com.ads.prj.model.DAO.PessoaDAO;
import br.com.ads.prj.model.DAO.PessoaFisicaDAO;
import br.com.ads.prj.model.DAO.PessoaJuridicaDAO;
import br.com.ads.prj.model.DAO.ProcessoDAO;
import br.com.ads.prj.model.Processo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Evandro
 */
@WebServlet(name = "ValidacaoClientes", urlPatterns = {"/ValidacaoClientes"})
public class ValidacaoClientes extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        String op = request.getParameter("op");

        switch (op) {
            case "email":
                String email = request.getParameter("variavel");
                boolean existeEmail = PessoaDAO.getInstance().existeEmail(email);
                //validação do e-mail e verificação se já está cadastrado
                if (email.equals("")) {
                    out.write("Campo obrigatório");
                } else if (!this.verificaEmail(email)) {
                    out.write("E-mail fora de padrão!");
                } else if (existeEmail) {
                    out.write("E-mail já cadastrado");
                } else {
                    out.write("<i class='icon-ok-sign'></i>");
                }
                break;
            case "cpf":
                String cpf = request.getParameter("variavel");
                boolean cpfValido = ValidacaoCpf.getInstance().isCPF(this.formataCpf(cpf));
                boolean existeCPF = PessoaFisicaDAO.getInstance().existeCpf(cpf);
                if (cpf.equals("___.___.___-__")) {
                    out.write("Campo obrigatório.");
                } else if (cpfValido) {
                    if (existeCPF) {
                        out.write("CPF já cadastrado");
                    } else {
                        out.write("<i class='icon-ok-sign'></i>");
                    }
                } else {
                    out.write("CPF inválido.");
                }
                break;
            case "cnpj":
                String cnpj = request.getParameter("variavel");
                boolean existeCNPJ = PessoaJuridicaDAO.getInstance().existeCnpj(cnpj);
                if (cnpj.equals("__.___.___/____-__")) {
                    out.write("Campo obrigatório.");
                } else if (existeCNPJ) {
                    out.write("CNPJ já cadastrado");
                } else {
                    out.write("<i class='icon-ok-sign'></i>");
                }
                break;
            case "oab":
                try {
                    String oab = request.getParameter("variavel");
                    if (oab.equals("______")) {
                        out.write("Campo obrigatório.");
                    } else if (AdvogadoDAO.getInstance().existeOab(Integer.parseInt(oab))) {
                        out.write("OAB já cadastrada");
                    } else {
                        out.write("<i class='icon-ok-sign'></i>");
                    }
                } catch (Exception e) {
                    out.write("");
                }
                break;
            case "cpfProcesso":
                String cpfProcesso = request.getParameter("variavel");
                boolean existeCPFCad = PessoaFisicaDAO.getInstance().existeCpf(cpfProcesso);
                if (existeCPFCad) {
                    out.write("<i class='icon-ok-sign'></i>");
                } else if (cpfProcesso.equals("___.___.___-__")) {
                    out.write("Campo obrigatório.");
                } else {
                    out.write("CPF não encontrado.");
                }
                break;
            case "cnpjProcesso":
                String cnpjProcesso = request.getParameter("variavel");
                boolean existeCNPJCad = PessoaJuridicaDAO.getInstance().existeCnpj(cnpjProcesso);
                if (existeCNPJCad) {
                    out.write("<i class='icon-ok-sign'></i>");
                } else if (cnpjProcesso.equals("__.___.___/____-__")) {
                    out.write("Campo obrigatório.");
                } else {
                    out.write("CNPJ não encontrado.");
                }
                break;
            case "carregaAdvogados":
                List<Advogado> listaAdv = AdvogadoDAO.getInstance().getListAdvogados();
                StringBuilder sb = new StringBuilder("");
                for (Advogado adv : listaAdv) {
                    sb.append(adv.getNome() + "-" + adv.getOab() + ":");
                }
                out.write(sb.toString());
                break;
            case "carregaProcessos":
                List<Processo> listaProcessos = ProcessoDAO.getInstance().getListProcessos();
                StringBuilder stringBuilder = new StringBuilder("");
                for (Processo processo : listaProcessos) {
                    stringBuilder.append(processo.getIdProcesso() + "-" + processo.getNumProcesso() + ":");
                }
                out.write(stringBuilder.toString());
                break;
        }
    }

    public boolean verificaEmail(String email) {
        boolean retorno;
        //verifica padrao de email
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        retorno = pattern.matcher(email).find();
        return retorno;

    }

    public String formataCpf(String cpf) {
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");
        return cpf;
    }
}
