    
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" />
    <xsl:template match="/">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>IFRJ - GELB</title>
        <link type="text/css" rel="stylesheet" href="css/gelb.css"/>
        <script type="text/javascript" src="js/funcs.js"></script>
        <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.10.3.js"></script>
        <style>
            #div-cadastro-right {
                width: 180px; 
                float: right; 
            }
            #div-cadastro-left {
                width: 100%; 
                float: left;
            }
        </style>
    </head>
    <body>
        <div class="header">
            <div><img src="img/logo2.png" /></div>
        </div>
        <div class="neacker">
            <div class="menu">
                <div>| <span onclick="javascript:toBrowse('');">Painel</span></div>
                <div>| <span onclick="javascript:toBrowse('Experimentos');">Experimentos</span></div>
                <div>| <span onclick="javascript:toBrowse('Plantas');">Plantas</span></div>
                <div>| <span onclick="javascript:toBrowse('Meios');">Meios</span></div>
                <div>| <span onclick="javascript:toBrowse('Levantamentos');">Levantamentos</span></div>
                <div>| <span onclick="javascript:toBrowse('Usuarios');">Usuários</span></div>
            </div>
        </div>
        <div class="bodier">
            <div class="section-mid">
                <div class="title-large">Plantas</div>
                <div class="container">
                    <div id="panel-form-cadastro" class="panel-form" style="display: none;">
                                                
                        <div id="div-cadastro-left" style="display: none;">
                            <div id="icon-minus" class="icon" title="Encolher" onclick="javascript:ShowHideCadastro();">-</div>
                            <div class="title-small" style="margin-left: 10px">Novo cadastro</div>
                        </div>
                        
                        <div style="width: 620px; margin: 0px auto; display: block;">
                            <form id="frm" name="frm" method="post" action="Plantas" onsubmit="javascript:return validateForm(this);" style="display: none;">
                                <input type="hidden" id="cmd" name="cmd" value="Plantas/lst"/>
                                <input type="hidden" id="id" name="id" value=""/>
                                <div class="row">
                                    <div class="xsmall">
                                        <label>Escolha o Meio:</label> 
                                        <select type="text" id="id_meio" name="id_meio">
					<xsl:for-each select="/root/meio">
                                            <option>
                                                <xsl:attribute name="value"><xsl:value-of select="@id_meio"/></xsl:attribute>
                                                <xsl:value-of select="@nm_meio"/> - <xsl:value-of select="@dt_meio"/></option>
					</xsl:for-each>
                                        </select>
                                    </div>
                                
                                    <div class="xmedium">
                                        <label>Nome da planta:</label> 
                                            <input type="text" id="nm_planta" name="nm_planta" required="true" maxlength="100" />
                                    </div>
                                   
                                </div>
				<div class="row">
                                    <div class="xsmall">
                                        <label>Data de germinação:</label> 
                                        <input type="text" id="dt_planta" name="dt_planta" required="true" maxlength="10" onblur="javascript:validateDate(this);" />
                                    </div>
                                    <div class="xsmall">
                                        <label>Quantidade:</label> 
                                        <input type="text" id="qt_planta" name="qt_planta" required="true" maxlength="2" />
                                    </div>
                                    <div class="xsmall">
                                        <label>Último repique:</label> 
                                        <div class="texto">--/--/----</div>
                                        <input type="hidden" id="dt_repique" name="dt_repique" value="" />
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="xxmedium">
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
                        <xsl:if test="count(/root/planta) > 0">
                        <div class="title-small" style="margin-left: 10px; display: block;">Lista</div>
                        
                        <div class="grid">
                            <xsl:for-each select="/root/planta">
                                <div class="row">
                                    <div style="width: 600px; display: table-cell;">
                                        <xsl:value-of select="@nm_planta"/>
                                    </div>
                                    <div style="width: 100px; display: table-cell;">
                                        <xsl:value-of select="@dt_planta"/>
                                    </div>
                                    <div id="icon-excluir" class="icon" style="width: 20px; height: 20px; float: none; display: table-cell; font-size: 20px;">
                                        <xsl:attribute name="onclick">javascript:Excluir(<xsl:value-of select="@id_planta"/>, '<xsl:value-of select="@nm_planta"/>');</xsl:attribute>
                                        x</div>
                                </div>
                            </xsl:for-each>
                        </div>
                        </xsl:if>
                    </div>
                    
                </div>
            </div>
        </div>
        
    </body>
</html>

    </xsl:template>
</xsl:stylesheet>
