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
        <script src="js/jquery.price_format2.0.min.js"></script>
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
                        <h3>
                            Editar Processo
                        </h3>
                        <br>${mensagem}
                    </div>
                </div>
            </div>

            <!-- /. PAGE TITLE-->
            <div class="row">
                <form class="form-horizontal"  action="ControleProcessos" method="post">
                    <div class="row">
                        <div class="span4">
                            <div class="media">
                                <div class="media-body">
                                    <label class="breadcrumb text-center">
                                        Cliente<br>
                                        <c:catch>
                                            <c:if test="${!processo.getPessoa().getNome().equals(null)}">
                                                <input readonly type="text" name="cpf" value="${processo.getPessoa().getCpf()}">
                                            </c:if>
                                        </c:catch>
                                        <c:catch>
                                            <c:if test="${!processo.getPessoa().getNomeFantasia().equals(null)}">
                                                <input readonly type="text" name="cnpj" value="${processo.getPessoa().getCnpj()}">
                                            </c:if>
                                        </c:catch>
                                    </label>
                                    <label class="breadcrumb text-center">
                                        Advogados<br>
                                        <a title="Adiciona Advogado" style="font-size: 16pt" o onclick="adicionaAdv();
                                                carregaAdvogadosEditar()">+</a>
                                        <br>
                                        <c:forEach items="${processo.getAdvogados()}" var="adv">
                                            <select name="oab" >
                                                <option value="">Remover</option>
                                                <option selected="" value="${adv.oab}">${adv.nome}</option>
                                            </select>
                                        </c:forEach>
                                        <div id="advogado"></div>
                                    </label>
                                    <label class="breadcrumb text-center">
                                        Requerido<br>
                                        <input type="text" name="requerido"  value="${processo.requerido}">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="span4">
                            <div class="media">
                                <div class="media-body">
                                    <label class="breadcrumb text-center">
                                        Tipo de Ação<br>
                                        <select required name="tipoAcao">
                                            <option value="${processo.tipoAcao}">${processo.tipoAcao}</option>
                                            <c:if test="${!processo.getTipoAcao().equals('Cível')}">
                                                <option value="Cível">Cível</option>
                                            </c:if>
                                            <c:if test="${!processo.getTipoAcao().equals('Criminal')}">
                                                <option value="Criminal">Criminal</option>
                                            </c:if>   
                                            <c:if test="${!processo.getTipoAcao().equals('Previdenciário')}">
                                                <option value="Previdenciário">Previdenciário</option>
                                            </c:if> 
                                            <c:if test="${!processo.getTipoAcao().equals('Trabalhista')}">
                                                <option value="Trabalhista">Trabalhista</option>
                                            </c:if> 
                                            <c:if test="${!processo.getTipoAcao().equals('Fazenda pública')}">
                                                <option value="Fazenda pública">Fazenda pública</option>
                                            </c:if> 
                                        </select>
                                    </label>
                                    <label class="breadcrumb text-center">
                                        Assunto Principal<br>
                                        <input required type="text" name="assunto" value="${processo.assuntoPrincipal}">
                                    </label>
                                    <label class="breadcrumb text-center">
                                        Situação<br>
                                        <select name="situacao">
                                            <option value="${processo.situacao}">${processo.situacao}</option>
                                            <c:if test="${!processo.getSituacao().equals('Espera')}">
                                                <option value="Espera">Em Espera</option>
                                            </c:if>
                                            <c:if test="${!processo.getSituacao().equals('Peticonado')}">
                                                <option value="Peticonado">Peticionado</option>
                                            </c:if> 
                                            <c:if test="${!processo.getSituacao().equals('Aguardando Audiência')}">
                                                <option value="Aguardando Audiência">Aguardando Audiência</option>
                                            </c:if> 
                                            <c:if test="${!processo.getSituacao().equals('Julgado em 1° Grau')}">
                                                <option value="Julgado em 1° Grau">Julgado em 1° Grau</option>
                                            </c:if> 
                                            <c:if test="${!processo.getSituacao().equals('Julgado em 2° Grau')}">
                                                <option value="Julgado em 2° Grau">Julgado em 2° Grau</option>
                                            </c:if> 
                                            <c:if test="${!processo.getSituacao().equals('Julgado em 3° Grau')}">
                                                <option value="Julgado em 3° Grau">Julgado em 3° Grau</option>
                                            </c:if> 
                                            <c:if test="${!processo.getSituacao().equals('Transitado em Julgado')}">
                                                <option value="Transitado em Julgado">Transitado em Julgado</option>
                                            </c:if>
                                        </select>
                                    </label>
                                    <label class="breadcrumb text-center">
                                        Data da petição<br>
                                        <input type="date" name="dataPeticao" value="${processo.dataPeticao}"/>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="span4">
                            <div class="media">
                                <div class="media-body">
                                    <label class="breadcrumb text-center">
                                        Valor da Causa<br>
                                        <input id="valorCausa" required type="text" name="valorCausa" value="${processo.valorCausa}0">
                                    </label>
                                    <script>
                                        $('#valorCausa').priceFormat({
                                            prefix: 'R$ ',
                                            centsSeparator: ',',
                                            thousandsSeparator: '.'
                                        });
                                    </script>
                                    <label class="breadcrumb text-center">
                                        Número do Processo<br>
                                        <input type="number" name="nroProcesso" value="${processo.numProcesso}">
                                    </label>
                                    <label class="breadcrumb text-center">
                                        Vara<br>
                                        <input type="text" name="vara" placeholder="Vara" value="${processo.vara}">
                                    </label>
                                    <input type="hidden" name="op" value="atualizaProcesso">
                                    <input type="hidden" name="idProcesso" value="${processo.idProcesso}">
                                    <label class="text-center">
                                        <button id="botao" class="btn-primary">
                                            <i class="icon-ok-sign"></i>
                                            Salvar
                                        </button>
                                        <button type="button" class="btn-info" Onclick="window.history.go(-1)">
                                            <i class="icon-backward"></i>
                                            Voltar
                                        </button>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div> <!-- final do container -->


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
