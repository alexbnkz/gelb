<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IFRJ - GELB - Login</title>
        <link type="text/css" rel="stylesheet" href="css/gelb.css"/>
        <script type="text/javascript" src="js/funcs.js"></script>
    </head>
    <body>
        <div class="header">
            <div><img src="img/logo2.png" /></div>
        </div>
        
        <div class="bodier">
            <div class="section-mid">
                <div class="title-large">Sistema de Gerenciamento de Experiências</div>
                <div class="container">
                    <div id="panel-form-cadastro" class="panel-form" style="border-bottom: 1px #999999 solid;">
                                 
                        <div id="div-cadastro-left">
                            
                            <div class="title-small" style="margin-left: 200px">Faça o login</div>
                        </div>
                        
                        <div style="width: 620px; margin: 0px auto; margin-left: 240px; display: block;">
                            <form id="frm" name="frm" method="post" action="Auth" onsubmit="javascript:return validateForm(this);">
                                <input type="hidden" id="cmd" name="cmd" value="Login/auth"/>
                                <input type="hidden" id="id" name="id" value=""/>
                                <div class="row">
                                    <div class="xmedium">
                                        <label>Login:</label> 
                                            <input type="text" id="cd_login" name="cd_login" required="true" maxlength="20" style="text-align: center;" />
                                    </div>
                                </div>
				<div class="row">
                                    <div class="xmedium">
                                        <label>Senha:</label> 
                                            <input type="password" id="cd_senha" name="cd_senha" required="true" maxlength="20" style="text-align: center;" />
                                    </div>
                                </div>
				<div class="row">
                                    <div class="xmedium">
                                        <label>&#10;</label>
                                    </div>
                                </div>
				<div class="row">
                                    <div class="xmedium">
                                        <div class="small" style="float: right;">
                                            <label>&#10;</label>
                                            <button type="submit" id="bt_entrar" name="bt_entrar" title="Entrar">
                                                <span>Entrar</span>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
    </body>
</html>
