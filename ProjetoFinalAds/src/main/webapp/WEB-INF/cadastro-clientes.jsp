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
            <!--PAGE TITLE-->
            <div class="row">
                <div class="span12">
                    <div class="page-header">
                        <h3>
                            Cadastro de Clientes
                            <br>${mensagem}
                            <select onchange="tipoPessoa()" class="alert-success" id="tipo">
                                <option value="vazio">Selecione o tipo</option>
                                <option value="fisica">Pessoa Física</option>
                                <option value="juridica">Pessoa Jurídica</option>
                            </select>
                            ${cpf} ${cnpj}
                        </h3>
                    </div>
                </div>
            </div>

            <!-- /. PAGE TITLE-->
            <form class="form-horizontal"  action="ControlePessoas" method="post">
                <div class="row">
                    <div class="span4">
                        <div class="media">
                            <div class="media-body">
                                <div id="fisica"></div>
                                <div id="juridica"></div>
                            </div>
                        </div>
                    </div>
                    <div hidden id="geral">
                        <div class="span4">
                            <div class="media">
                                <div class="media-body">
                                    <h4 class="text-center">Endereço</h4>
                                    <label class="breadcrumb text-center">
                                        <input required onblur="buscaEndereco()" type="number" name="cep" id="cep" placeholder="CEP"/>
                                    </label>
                                    <label class="breadcrumb text-center">
                                        <input required type="text" name="logradouro" placeholder="Logradouro" />
                                    </label>
                                    <label class="breadcrumb text-center">
                                        <input required type="number" name="nro" placeholder="Número" />
                                    </label>
                                    <label class="breadcrumb text-center">
                                        <input type="text" name="bairro" placeholder="Bairro"/>
                                    </label>
                                    <label class="breadcrumb text-center" >
                                        <select required id="estado" name="estado"></select>
                                    </label>
                                    <label class="breadcrumb text-center">
                                        <select required id="cidade" name="cidade"></select>
                                    </label>
                                    <script language="JavaScript" type="text/javascript" charset="utf-8">
                                        window.onDomReady(function () {
                                            new dgCidadesEstados({
                                                cidade: document.getElementById('cidade'),
                                                estado: document.getElementById('estado')
                                            });
                                        });
                                    </script>
                                </div>
                            </div>
                        </div>
                        <div class="span4">
                            <div class="media">
                                <div class="media-body">
                                    <h4 class="text-center">Contatos:</h4>
                                    <label class="breadcrumb text-center">
                                        <input required type="text" name="telefone" data-mask="(99)9999-9999" placeholder="Telefone" />
                                    </label>
                                    <label class="breadcrumb text-center">
                                        <input required type="text" name="celular" data-mask="(99)99999-9999" placeholder="Celular" />
                                    </label>
                                    <label class="breadcrumb text-center">
                                        <input onblur="validarEmail()" id="email" required placeholder="E-Mail" type="email" name="email" />
                                        <br>
                                        <div id="divMsgEmail" style="font-size: 10pt"></div>
                                    </label> 
                                    <label class="accordion text-center">
                                        <button id="botao" class="btn-primary text-center">
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
                </div>
            </form>
        </div> <!-- final do container -->
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
