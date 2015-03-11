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
                    <div class="span12 text-center"><a class="brand" href="SistemaJuridico"><img src="img/coollogo.png"/></a></div>
                </div>
            </div>
        </div>
        <!-- /HEADER ROW -->


        <div class="container">
            <!--PAGE TITLE-->

            <div class="row">
                <div class="span12">
                    <div class="page-header">
                        <h2 class="text-center">
                            Acesso ao Sistema
                        </h2>
                    </div>
                </div>
            </div>
            <!-- /. PAGE TITLE-->
            <form class="form-horizontal text-center" action="SistemaJuridico" method="post">
                <input type="hidden" name="op" value="logar"/>
                <div class="input-prepend">
                    <span class="add-on"><i class="icon-user"></i></span> 
                    <input required="" type="email" name="login" placeholder="Login/E-mail" />
                    <br>
                    <span class="add-on"><i class="icon-lock"></i></span>    
                    <input required="" type="password" name="senha" placeholder="Senha" />
                </div>
                <div class="clearfix">
                    <label class="inline">
                        <input type="checkbox" class="ace" name="_spring_security_remember_me" />
                        <span class="lbl"> Continuar Conectado</span>
                    </label>
                    <button class="btn-primary">
                        <i class="icon-ok-sign"></i>
                        Entrar
                    </button><br>
                    <span style="color: red"> ${erro}</span>
                    <span style="color: blue">${mensagem}</span>
                </div>
            </form>

            <form class="text-center" action="ControleUsuario" method="post">
                <input type="hidden" name="op" value="paginarecuperasenha">
                <button class="btn-info">
                    <span class="icon-question-sign"></span> Esqueci minha senha
                </button> 
            </form>


        </div> <!-- final do container -->
        <!--Footer
        ==========================-->
        <%--       <footer>
            <div class="container">
                <div class="row">
                    <div class="span12">
                        <p class="text-center">IFSP - Campus São Carlos | ADS - PRJ | 2014-2<br>
                            <small class="text-center">Projeto Final de Curso</small></p>
                    </div>
                </div>
            </div>
        </footer>
<!--/.Footer--> --%>    
    </body>
</html>
