<%--<%@page contentType="text/html" pageEncoding="UTF-8"%> --%>
<%@page import="br.com.ads.prj.model.Data"%>
<%@page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="UTF-8" import="java.sql.*" errorPage="" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestão Jurídica</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <!-- Bootstrap -->
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-responsive.css" rel="stylesheet">
        <link href="css/jasny-bootstrap.min.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet"> 

        <!--Font-->
        <link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600' rel='stylesheet' type='text/css'>
        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <!-- Fav and touch icons -->
        <link rel="shortcut icon" href="ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="ico/apple-touch-icon-57-precomposed.png">
        <!-- SCRIPT 
        ============================================================-->  
        <script src="http://code.jquery.com/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jasny-bootstrap.min.js"></script>
        <script type="text/javascript" src="main.js"></script>
        <script type="text/javascript" src="js/cidades-estados-1.0.js"></script>
    </head>
    <body>

        <div class="row">
            <div class="span6 offset4">
                <div class="media">
                    <div class="media-body">
                        <h2 style="margin-left: 50px; margin-right: 50px; font-weight: bold" class="text-center">Procuração</h2>

                        <p style="text-indent: 5em; margin-left: 50px; margin-right: 50px" align="justify">
                            <span style="font-weight: bold"> ${pp.getPessoa().getRazaoSocial()}</span>, também denominada ${pp.getPessoa().getNomeFantasia()},
                            CNPJ: ${pp.getPessoa().getCnpj()}, sita à ${pp.getPessoa().getLogradouro()}, n°${pp.getPessoa().getNro()} -
                            ${pp.getPessoa().getBairro()} - CEP: ${pp.getPessoa().getCep()} - 
                            ${pp.getPessoa().getCidade()}/${pp.getPessoa().getEstado()}, pelo presente instrumento de procuração 
                            nomeia e constitui 
                            <c:if test="${pp.getAdvogados().size() == 1}">
                                seu bastante procurador(a) e advogado(a) o(a) Dr.(a) <span style="font-weight: bold"> ${pp.getAdvogados().get(0).getNome()}</span>,
                                ${pp.getAdvogados().get(0).getNacionalidade()}, ${pp.getAdvogados().get(0).getEstadoCivil()},
                                regularmente inscrito na OAB sob o nº ${pp.getAdvogados().get(0).getOab()}/${pp.getAdvogados().get(0).getUf()},
                            </c:if>
                            <c:if test="${pp.getAdvogados().size() > 1}">
                                seus  procuradores e advogados o(a) Dr.(a)
                                <c:forEach items="${pp.getAdvogados()}" var="adv" varStatus="p">
                                    <span style="font-weight: bold">${adv.getNome()}</span>, ${adv.getNacionalidade()}, ${adv.getEstadoCivil()},
                                    regularmente inscrito na OAB sob o nº ${adv.getOab()}/${adv.getUf()}, 
                                    <c:if test="${p.count != pp.getAdvogados().size() }">
                                        e o(a) Dr.(a)
                                    </c:if>

                                </c:forEach>
                            </c:if>      
                            com escritório profissional localizado na Av. Dr. Teixeira de Barros, nº 906, SALA A- CEP: 13574-033- Vila Prado,
                            fone (16) 3419-8285, nesta cidade de São Carlos SP, a quem confere amplos poderes para o foro em geral,
                            com a cláusula ad-judicia, em qualquer Juízo, Instância ou Tribunal, podendo propor, contra quem de direito as ações
                            competentes e defendê-la nas contrárias, seguindo umas e outras, até final decisão, usando os recursos legais e acompanhando-os,
                            conferindo-lhe ainda, poderes especiais para confessar, desistir, transigir, firmar compromissos ou acordos, receber e dar quitação,
                            podendo ainda substabelecer esta em outrem, com ou sem reserva de iguais, dando tudo por bom firme e valioso,
                            em especial para representar o(a) outorgante na <span style="font-weight: bold"> Ação ${pp.tipoAcao} do Processo de ${pp.assuntoPrincipal}.</span>
                        </p>
                        <br>
                        <p style="text-align: right; margin-left: 50px; margin-right: 50px">
                            São Carlos, <%out.println(Data.getInstance().getDataPorExtenso());%>
                        </p>
                        <br>
                        <br>
                        <p style="text-align: center; margin-left: 50px; margin-right: 50px">_________________________________</p>
                        <p style="text-align: center; margin-left: 50px; margin-right: 50px">${pp.getPessoa().getRazaoSocial()}</p>
                    </div>
                </div>
            </div>	

    </body>
</html>
