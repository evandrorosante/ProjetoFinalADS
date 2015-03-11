<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestão Jurídica</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">


        <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
        <link href="css/bootstrap-responsive.min.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>
        <!--HEADER ROW-->
        <div id="header-row">
            <div class="container">
                <div class="row">
                    <!--LOGO-->
                    <div class="span2"><a class="brand" href="SistemaJuridico"><img src="img/coollogo.png"/></a></div>
                </div>
            </div>
        </div>
        <!-- /HEADER ROW -->


        <div class="container">
            <!--PAGE TITLE-->

            <div class="row">
                <div class="span12">
                    <div class="page-header">
                        <h2>
                            Recuperar Senha
                        </h2>
                    </div>
                </div>
            </div>


            <!-- /. PAGE TITLE-->
            <!-- Se o usuário ainda não recebeu o e-mail-->
            <c:if test="${idRecSenha == null}">
                <form class="form-horizontal" action="ControleUsuario" method="post">
                    <input type="hidden" name="op" value="recuperasenha"/>
                    <label class="brand clearfix">Digite seu e-mail e um link será enviado para o seu e-mail.<br>
                        <span>
                            <input required="" type="email" name="login" placeholder="E-mail" />
                            <i class="icon-user"></i>
                        </span>
                    </label>
                    <label>
                        <button id="botao" disabled class="btn-primary">
                            <i class="icon-ok-sign"></i>
                            Enviar
                        </button>
                        <button type="button" class="btn-info" Onclick="window.history.go(-1)">
                            <i class="icon-backward"></i>
                            Voltar
                        </button>
                    </label>
                </form>
            </c:if>

            <!-- Se o usuário já recebeu o e-mail com o código-->
            <c:if test="${idRecSenha != null}">
                Digite sua nova senha.
                <form class="form-horizontal" method="post" action="ControleUsuario">
                    <label class="brand clearfix">
                        <span>
                            <input required="" type="password" name="senha1" placeholder="Senha" />
                            <i class="icon-lock"></i>
                        </span>
                    </label>
                    <label class="'brand clearfix">
                        <span>
                            <input required="" type="password" name="senha2" placeholder="Repita a Senha" />
                            <i class="icon-lock"></i>
                        </span>
                    </label>

                    <input type="hidden" name="op" value="atualizasenha">
                    <input type="hidden" name="idRecSenha" value="${idRecSenha}">
                    <button class="btn-primary">
                        <i class="icon-ok-sign"></i>
                        Entrar
                    </button>
                </form>
            </c:if>
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
