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
        <link href="css/estilo.css" rel="stylesheet"> 

        <!--Font-->
        <link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600' rel='stylesheet' type='text/css'>
        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <!-- Fav and touch icons -->
        <link rel="shortcut icon" href="img/justica.ico">
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
        <!--HEADER ROW-->
        <div id="header-row">
            <div class="container">
                <div class="row">
                    <!--LOGO-->
                    <div class="span2"><a class="brand" href="SistemaJuridico"><img src="img/coollogo.png"/></a></div>
                    <!-- /LOGO -->

                    <!-- MAIN NAVIGATION -->  
                    <div class="span10">
                        <div class="navbar  pull-left">
                            <div class="navbar-inner">
                                <a data-target=".navbar-responsive-collapse" data-toggle="collapse" class="btn btn-navbar"><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></a>
                                <div class="nav-collapse collapse navbar-responsive-collapse">
                                    <ul class="nav">
                                        <li class="active"><a href="SistemaJuridico"><i class="icon-home"></i>Home</a></li>
                                        <li class="dropdown">
                                            <a href="" class="dropdown-toggle" data-toggle="dropdown">Clientes<b class="caret"></b></a>
                                            <ul class="dropdown-menu">
                                                <li><a href="ControlePessoas?op=cadastracliente">Cadastrar</a></li>
                                                <li><a href="ControlePessoas?op=consultaPessoas">Consultar</a></li>
                                            </ul>
                                        </li>
                                        <c:if test="${sessionScope.usuario.tipo == 1}">
                                            <li class="dropdown">
                                                <a href="" class="dropdown-toggle" data-toggle="dropdown">Advogados<b class="caret"></b></a>
                                                <ul class="dropdown-menu">
                                                    <li><a href="ControlePessoas?op=cadastraAdvogado">Cadastrar</a></li>
                                                    <li><a href="ControlePessoas?op=consultaAdvogado">Consultar</a></li>
                                                </ul>
                                            </li>
                                        </c:if>
                                        <li class="dropdown">
                                            <a href="" class="dropdown-toggle" data-toggle="dropdown">Processos <b class="caret"></b></a>
                                            <ul class="dropdown-menu">
                                                <li><a href="ControleProcessos?op=novoProcesso">Novo</a></li>
                                                <li><a href="ControleProcessos?op=consultaProcesso">Consultar</a></li>
                                            </ul>
                                        </li>
                                        <li class="dropdown">
                                            <a href="" class="dropdown-toggle" data-toggle="dropdown">Audiências<b class="caret"></b></a>
                                            <ul class="dropdown-menu">
                                                <li><a href="ControleAudiencia?op=novaAudiencia">Nova</a></li>
                                                <li><a href="ControleAudiencia?op=consultarAudiencia">Consultar</a></li>
                                                    <c:if test="${sessionScope.usuario.tipo == 1}">
                                                    <li><a href="ControleAlerta?op=gerenciaAlertas">Gerenciar Alertas</a></li>
                                                    </c:if> 
                                            </ul>
                                        </li>
                                        <c:if test="${sessionScope.usuario.tipo == 1}">
                                            <li class="dropdown">
                                                <a href="" class="dropdown-toggle" data-toggle="dropdown">Configurações <b class="caret"></b></a>
                                                <ul class="dropdown-menu">
                                                    <li><a href="ControleUsuario?op=cadastrausuario">Cadastrar Usuários</a></li>
                                                    <li><a href="ControleUsuario?op=consultausuarios">Consultar Usuários</a></li>
                                                </ul>
                                            </li>
                                        </c:if>
                                        <li class="dropdown">
                                            <form style="margin-top: 10px" method="post" action="SistemaJuridico">
                                                <c:set var="firstName[]"  >
                                                    ${firstName = sessionScope.usuario.getPessoa().getNome().split(' ')}                
                                                </c:set>
                                                <a style="color: #040404" href="" class="dropdown-toggle" data-toggle="dropdown">
                                                    <i class="icon-user"></i> ${firstName[0]}<b class="caret"></b>
                                                </a>
                                                <ul class="dropdown-menu">
                                                    <li>
                                                        <a href="ControlePessoas?op=editaPF&cpf=${sessionScope.usuario.getPessoa().getCpf()}" >
                                                            <button type="button" class="btn-link">
                                                                Perfil
                                                            </button>
                                                        </a>
                                                    </li>
                                                    <li><a href="ControleUsuario?op=alterarsenha">
                                                            <button type="button" class="btn-link">
                                                                Alterar senha
                                                            </button></a>
                                                    </li>
                                                    <li>
                                                        <a>
                                                            <button class="btn-link">
                                                                Sair
                                                            </button>
                                                        </a>
                                                    </li>
                                                    <input type="hidden" value="sair" name="op">
                                                </ul>
                                            </form>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- MAIN NAVIGATION -->  
                </div>
            </div>
        </div>
        <!-- /HEADER ROW -->
        <div class="container">

            <div class="row">
                <div class="span12">
                    <div class="page-header">
                        <h3>Consulta de Audiências</h3>
                        <form action="ControleAudiencia" method="get">
                            Número do processo:
                            <input required type="text" name="numProcesso">
                            <button class="btn-success ">Buscar
                                <input type="hidden" value="listarAudienciaPorNumero" name="op">
                            </button>
                        </form>
                        ${mensagem}
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="span12">
                    <div class="media">
                        <div class="media-body">
                            <c:if test="${listaAudiencias != null}">
                                <%--    <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>Número do processo</th>
                                            <th>Tipo</th>
                                            <th>Assunto Principal</th>
                                            <th>Cliente</th>
                                            <th>Advogados</th>
                                            <th>Data</th> 
                                            <th>Horário</th>
                                            <th>Ata</th>
                                        </tr>
                                    </thead>
                                    <c:forEach items="${listaAudiencias}" var="l">
                                        <tr>
                                            <td><a class="btn-success btn-mini" href="ControleAudiencia?op=editarAudiencia&idAudiencia=${l.idAudiencia}">
                                                    <i class="icon-edit"></i></a></td>
                                                    <td><a href="ControleProcessos?op=editarProcesso&idProcesso=${l.getProcesso().getIdProcesso()}"> ${l.getProcesso().getNumProcesso()}</a></td>
                                            <td>${l.getProcesso().getTipoAcao()}</td>
                                            <td>${l.getProcesso().getAssuntoPrincipal()}</td>
                                            <td>
                                                <c:catch>
                                                    <c:if test="${!l.getProcesso().getPessoa().getNome().equals(null)}">
                                                        ${l.getProcesso().getPessoa().getNome()}
                                                    </c:if>
                                                </c:catch>  
                                                <c:catch>
                                                    <c:if test="${!l.getProcesso().getPessoa().getNomeFantasia().equals(null)}">
                                                        <${l.getProcesso().getPessoa().getNomeFantasia()}
                                                    </c:if>
                                                </c:catch>
                                            </td>
                                            <td>
                                                <c:forEach items="${l.getProcesso().getAdvogados()}" var="a" varStatus="p">
                                                    ${a.getNome()} 
                                                    <c:if test="${l.getProcesso().getAdvogados().size() > 1 && p.count <  l.getProcesso().getAdvogados().size()}">
                                                        - 
                                                    </c:if>
                                                </c:forEach>
                                            </td>
                                            <td style="font-weight: bolder;color: #002a80">${l.data}</td> 
                                            <td style="font-weight: bolder;color: #002a80">${l.horario}</td> 
                                        <td><a href="ControleAudiencia?op=exibirArquivo&nome=${l.nomeArquivo}" target="_blank">${l.nomeArquivo}<a/></td> 
                                        </tr>
                                    </c:forEach>
</table> --%>
                                <div class="row-fluid alert-success">
                                    <div class="span1"></div>
                                    <div class="span2">Processo</div>
                                    <div class="span2">Cliente</div>
                                    <div class="span2">Advogados</div>
                                    <div class="span1">Data</div>
                                    <div class="span1">Horário</div>
                                    <div class="span2">Ata</div>
                                </div>

                                <c:forEach items="${listaAudiencias}" var="l">
                                    <div style="margin-left: 0px" class="row-fluid">
                                        <div style="margin-left: 0px" class="span1"><a class="btn-success btn-mini" href="ControleAudiencia?op=editarAudiencia&idAudiencia=${l.idAudiencia}">
                                                <i class="icon-edit"></i></a></div>
                                        <div class="span2"><a href="ControleProcessos?op=editarProcesso&idProcesso=${l.getProcesso().getIdProcesso()}"> ${l.getProcesso().getNumProcesso()}</a></div> 
                                        <div class="span2">
                                            <c:catch>
                                                <c:if test="${!l.getProcesso().getPessoa().getNome().equals(null)}">
                                                    ${l.getProcesso().getPessoa().getNome()}
                                                </c:if>
                                            </c:catch>  
                                            <c:catch>
                                                <c:if test="${!l.getProcesso().getPessoa().getNomeFantasia().equals(null)}">
                                                    <${l.getProcesso().getPessoa().getNomeFantasia()}
                                                </c:if>
                                            </c:catch>
                                        </div> 
                                        <div class="span2">
                                            <c:forEach items="${l.getProcesso().getAdvogados()}" var="a" varStatus="p">
                                                ${a.getNome()} 
                                                <c:if test="${l.getProcesso().getAdvogados().size() > 1 && p.count <  l.getProcesso().getAdvogados().size()}">
                                                    - 
                                                </c:if>
                                            </c:forEach>
                                        </div> 
                                        <div style="font-weight: bolder" class="span1 text-warning">${l.data}</div>  
                                        <div style="font-weight: bolder" class="span1 text-warning">${l.horario}</div> 
                                        <div class="span2"><a href="ControleAudiencia?op=exibirArquivo&nome=${l.nomeArquivo}" target="_blank">${l.nomeArquivo}<a/></div> 
                                    </div>
                                </c:forEach>

                            </c:if>
                            <c:if test="${audiencia != null}">
                                <div class="row-fluid alert-success">
                                    <div class="span1"></div>
                                    <div class="span2">Processo</div>
                                    <div class="span2">Cliente</div>
                                    <div class="span2">Advogados</div>
                                    <div class="span1">Data</div>
                                    <div class="span1">Horário</div>
                                    <div class="span2">Ata</div>
                                </div>

                                    <div style="margin-left: 0px" class="row-fluid">
                                        <div style="margin-left: 0px" class="span1"><a class="btn-success btn-mini" href="ControleAudiencia?op=editarAudiencia&idAudiencia=${audiencia.idAudiencia}">
                                                <i class="icon-edit"></i></a></div>
                                        <div class="span2"><a href="ControleProcessos?op=editarProcesso&idProcesso=${audiencia.getProcesso().getIdProcesso()}"> ${audiencia.getProcesso().getNumProcesso()}</a></div> 
                                        <div class="span2">
                                            <c:catch>
                                                <c:if test="${!audiencia.getProcesso().getPessoa().getNome().equals(null)}">
                                                    ${audiencia.getProcesso().getPessoa().getNome()}
                                                </c:if>
                                            </c:catch>  
                                            <c:catch>
                                                <c:if test="${!audiencia.getProcesso().getPessoa().getNomeFantasia().equals(null)}">
                                                    <${audiencia.getProcesso().getPessoa().getNomeFantasia()}
                                                </c:if>
                                            </c:catch>
                                        </div> 
                                        <div class="span2">
                                            <c:forEach items="${audiencia.getProcesso().getAdvogados()}" var="a" varStatus="p">
                                                ${a.getNome()} 
                                                <c:if test="${audiencia.getProcesso().getAdvogados().size() > 1 && p.count <  audiencia.getProcesso().getAdvogados().size()}">
                                                    - 
                                                </c:if>
                                            </c:forEach>
                                        </div> 
                                        <div class="span1 text-warning">${audiencia.data}</div>  
                                        <div class="span1 text-warning">${audiencia.horario}</div> 
                                        <div class="span2"><a href="ControleAudiencia?op=exibirArquivo&nome=${audiencia.nomeArquivo}" target="_blank">${audiencia.nomeArquivo}<a/></div> 
                                    </div>

                            </c:if>
                            <label class="text-center">
                                <button type="button" class="btn-info" Onclick="window.history.go(-1)">
                                    <i class="icon-backward"></i>
                                    Voltar
                                </button>
                            </label>
                        </div>
                    </div>
                </div>
            </div>

        </div>


        <!-- /.Row View -->



        <!--Footer
         ==========================-->
        <footer style="margin-top: 0px">
            <div class="container">
                <div class="row">
                    <div class="span12">
                        <p class="text-center">IFSP - Campus São Carlos | ADS - PRJ | 2014-2<br>
                            <small class="text-center">Projeto Final de Curso</small></p>
                    </div>
                </div>
            </div>
        </footer>
        <!--/.Footer-->
    </body>
</html>
