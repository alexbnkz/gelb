    
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:include href="includes.xsl"/>
    <xsl:template match="/">

<html>
    <head>
        <title>IFRJ - GELB - Usuários</title>
        <xsl:call-template name="head"/>
    </head>
    <body>
    <xsl:call-template name="menu"/>
        <div class="bodier">
            <div class="section-mid">
                <div class="title-large">Usuários</div>
                <div class="container">
                    
                    <xsl:if test="/root/cmd = 'DET'">
                    <div id="panel-form-cadastro" class="panel-form" style="display: block;">
                                                
                        <div id="div-cadastro-left" style="display: block;">
                            <div id="icon-minus" class="icon" title="Voltar" onclick="javascript:history.back();">-</div>
                            <div class="title-small" style="margin-left: 10px">Alterar</div>
                        </div>
                        
                        <div style="width: 620px; margin: 0px auto; display: block;">
                            <form id="frm" name="frm" method="post" action="Usuarios" onsubmit="javascript:return validateForm(this);">
                                <input type="hidden" id="cmd" name="cmd" value="Usuarios/upd"/>
                                <input type="hidden" id="id" name="id">
                                    <xsl:attribute name="value"><xsl:value-of select="/root/usuario/@id_usuario"/></xsl:attribute>
                                </input>
                                <div class="row">
                                    <div class="xxxmedium">
                                        <label>Nome do usuário:</label> 
                                            <input type="text" id="nm_usuario" name="nm_usuario" required="true" maxlength="30">
                                                <xsl:attribute name="value"><xsl:value-of select="/root/usuario/@nm_usuario"/></xsl:attribute>
                                            </input>
                                    </div>
                                    <div class="xxsmall">
                                        <label>E-mail do usuário:</label> 
                                            <input type="text" id="cd_email" name="cd_email" required="true" maxlength="50">
                                                <xsl:attribute name="value"><xsl:value-of select="/root/usuario/@cd_email"/></xsl:attribute>
                                            </input>
                                    </div>
                                    <div class="medium">
                                        <label>Login no sistema:</label> 
                                            <input type="text" id="cd_login" name="cd_login" required="true" maxlength="20">
                                                <xsl:attribute name="value"><xsl:value-of select="/root/usuario/@cd_login"/></xsl:attribute>
                                            </input>
                                    </div>
                                    <div class="xsmall">
                                        <label>Privilégio:</label> 
                                        <select type="text" id="ct_privilegio" name="ct_privilegio">
                                            <option value="A">
                                                <xsl:if test="/root/usuario/@ct_privilegio = 'A'">
                                                    <xsl:attribute name="selected">true</xsl:attribute>
                                                </xsl:if>
                                                Administrador</option>
                                            <option value="P">
                                                <xsl:if test="/root/usuario/@ct_privilegio = 'P'">
                                                    <xsl:attribute name="selected">true</xsl:attribute>
                                                </xsl:if>
                                                Pesquisador</option>
                                            <option value="X">
                                                <xsl:if test="/root/usuario/@ct_privilegio = 'X'">
                                                    <xsl:attribute name="selected">true</xsl:attribute>
                                                </xsl:if>
                                                Inativo</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="xsmall">
                                        <label>Senha:</label> 
                                            <input type="password" id="pw_senha" name="pw_senha" required="true" maxlength="20">
                                                <xsl:attribute name="value"><xsl:value-of select="/root/usuario/@pw_senha"/></xsl:attribute>
                                            </input>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="xsmall">
                                        <label>Confirmar sua senha:</label> 
                                            <input type="password" id="pw_senha_confirm" name="pw_senha_confirm" required="true" maxlength="20">
                                                <xsl:attribute name="value"><xsl:value-of select="/root/usuario/@pw_senha"/></xsl:attribute>
                                            </input>
                                    </div>
                                </div>
                                <div class="row">
                                    <div style="color: transparent;">espaço</div>
                                </div>
                                <div class="row">
                                    <div class="xxlarge">
                                        <div class="small" style="float: right;">
                                            <label>&#10;</label>
                                            <button type="submit" id="bt_salvar" name="bt_salvar" title="Salvar">
                                                <span>Salvar</span>
                                            </button>
                                        </div>
                                        <div class="small" style="float: left;">
                                            <label>&#10;</label>
                                            <button type="button" id="bt_excluir" name="bt_excluir" title="Excluir">
                                                <xsl:attribute name="onclick">javascript:Excluir(<xsl:value-of select="/root/usuario/@id_usuario"/>, '<xsl:value-of select="/root/usuario/@nm_usuario"/>');</xsl:attribute>
                                                <span>Excluir</span>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    </xsl:if>
                    
                    <xsl:if test="/root/cmd != 'DET'">
                    <div id="panel-form-cadastro" class="panel-form" style="display: none;">
                                                
                        <div id="div-cadastro-left" style="display: none;">
                            <div id="icon-minus" class="icon" title="Encolher" onclick="javascript:ShowHideCadastro();">-</div>
                            <div class="title-small" style="margin-left: 10px">Novo cadastro</div>
                        </div>
                        
                        <div style="width: 620px; margin: 0px auto; display: block;">
                            <form id="frm" name="frm" method="post" action="Usuarios" onsubmit="javascript:return validateForm(this);">
                                <input type="hidden" id="cmd" name="cmd" value="Usuarios/lst"/>
                                <input type="hidden" id="id" name="id" value=""/>
                                <div class="row">
                                    <div class="xxxmedium">
                                        <label>Nome do usuário:</label> 
                                            <input type="text" id="nm_usuario" name="nm_usuario" required="true" maxlength="30" />
                                    </div>
                                    <div class="xxsmall">
                                        <label>E-mail do usuário:</label> 
                                            <input type="text" id="cd_email" name="cd_email" required="true" maxlength="50" />
                                    </div>
                                    <div class="medium">
                                        <label>Login no sistema:</label> 
                                            <input type="text" id="cd_login" name="cd_login" required="true" maxlength="20" />
                                    </div>
                                    <div class="xsmall">
                                        <label>Privilégio:</label> 
                                        <select type="text" id="ct_privilegio" name="ct_privilegio">
                                            <option value="A">
                                                Administrador</option>
                                            <option value="P">
                                                Pesquisador</option>
                                            <option value="X">
                                                Inativo</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="xsmall">
                                        <label>Senha:</label> 
                                            <input type="password" id="pw_senha" name="pw_senha" required="true" maxlength="20" />
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="xsmall">
                                        <label>Confirmar sua senha:</label> 
                                            <input type="password" id="pw_senha_confirm" name="pw_senha_confirm" required="true" maxlength="20" />
                                    </div>
                                </div>
                                <div class="row">
                                    <div style="color: transparent;">espaço</div>
                                </div>
                                <div class="row">
                                    <div class="xxlarge">
                                        <div class="small" style="float: right;">
                                            <label>&#10;</label>
                                            <button type="submit" id="bt_salvar" name="bt_salvar" title="Salvar">
                                                <span>Salvar</span>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    
                    <div class="panel-grid">
                        
                        <div id="div-cadastro-right" style="display: block;">
                            <div id="icon-plus" class="icon" title="Expandir" onclick="javascript:ShowHideCadastro();">+</div>
                            <div class="title-small" style="margin-left: 10px">Novo cadastro</div>
                        </div>
                        <xsl:if test="count(/root/usuario) > 0">
                        <div class="title-small" style="margin-left: 10px; display: block;">Lista</div>
                        
                        <div class="grid">
                            <div class="legend" style="width: 10px;"></div>
                            <div class="legend" style="width: 200px;">Nome</div>
                            <div class="legend" style="width: 300px;">E-mail</div>
                            <div class="legend" style="width: 100px;">Privilégio</div>
                            <xsl:for-each select="/root/usuario">
                                <div class="row">
                                    <xsl:attribute name="onclick">javascript:Editar(<xsl:value-of select="@id_usuario"/>);</xsl:attribute>
                                    <div class="cell icon icon-editar" style="display: none;">
                                        </div>
                                    <div class="cellpadd" style="width: 200px;">
                                        <div><xsl:value-of select="@nm_usuario"/></div>
                                    </div>
                                    <div class="cellpadd" style="width: 300px;">
                                        <xsl:value-of select="@cd_email"/>
                                    </div>
                                    <div class="cellpadd" style="width: 100px;">
                                        <xsl:value-of select="@nm_privilegio"/>
                                    </div>
                                </div>
                            </xsl:for-each>
                        </div>
                        <div style="padding-bottom: 10px;"></div>
                        </xsl:if>
                    </div>
                    </xsl:if>
                </div>
            </div>
        </div>
        <xsl:if test="count(/root/message) > 0">
            <xsl:call-template name="aviso"/>
        </xsl:if>
    </body>
</html>

    </xsl:template>
</xsl:stylesheet>
