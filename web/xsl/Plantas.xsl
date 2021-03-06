    
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:include href="includes.xsl"/>
    <xsl:template match="/">

<html>
    <head>
        <title>IFRJ - GELB - Plantas</title>
        <xsl:call-template name="head"/>
    </head>
    <body>
    <xsl:call-template name="menu"/>
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
                            <form id="frm" name="frm" method="post" action="Plantas" onsubmit="javascript:return validateForm(this);">
                                <input type="hidden" id="cmd" name="cmd" value="Plantas/lst"/>
                                <input type="hidden" id="id" name="id" value=""/>
                                <div class="row">
                                    <div class="xxsmall">
                                        <label>Escolha o Meio:</label> 
                                        <select type="text" id="id_meio" name="id_meio">
					<xsl:for-each select="/root/meio">
                                            <option>
                                                <xsl:attribute name="value"><xsl:value-of select="@id_meio"/></xsl:attribute>
                                                <xsl:value-of select="@nm_meio"/> - <xsl:value-of select="@dt_meio"/></option>
					</xsl:for-each>
                                        </select>
                                    </div>
                                </div>
				<div class="row">
                                    <div class="medium">
                                        <label>Identificacão da planta:</label> 
                                            <input type="text" id="nm_planta" name="nm_planta" maxlength="100" />
                                    </div>
                                    <div class="xsmall">
                                        <label>Data de germinação:</label> 
                                        <input type="text" id="dt_planta" name="dt_planta" required="true" maxlength="10" onblur="javascript:validateDate(this);" />
                                    </div>
                                    <div class="small">
                                        <label>Quantidade:</label> 
                                        <input type="text" id="qt_planta" name="qt_planta" required="true" maxlength="3" />
                                    </div>
                                    <div class="small">
                                        <label>Último repique:</label> 
                                        <div class="texto">--/--/----</div>
                                        <input type="hidden" id="dt_repique" name="dt_repique" value="" />
                                    </div>
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
                        <xsl:if test="count(/root/planta) > 0">
                        <div class="title-small" style="margin-left: 10px; display: block;">Lista</div>
                        
                        <div class="grid">
                            <div class="legend" style="width: 20px;"></div>
                            <div class="legend" style="width: 200px;">Meio</div>
                            <div class="legend" style="width: 200px;">Identificação</div>
                            <div class="legend" style="width: 100px;">Quantidade</div>
                            <xsl:for-each select="/root/planta">
                                <div class="row">
                                    <div style="width: 200px; display: table-cell;">
                                        <xsl:variable name="id_meio"><xsl:value-of select="@id_meio"/></xsl:variable>
                                        <xsl:value-of select="/root/meio[@id_meio = $id_meio]/@nm_meio"/> - <xsl:value-of select="/root/meio[@id_meio = $id_meio]/@dt_meio"/>
                                        <input type="hidden" id="nm_planta_identificacao" name="nm_planta_identificacao">
                                            <xsl:attribute name="value">
                                                <xsl:if test="@dt_repique = '--/--/----'">
                                                    <xsl:value-of select="/root/meio[@id_meio = $id_meio]/@nm_meio"/> - <xsl:value-of select="/root/meio[@id_meio = $id_meio]/@dt_meio"/> - <xsl:value-of select="@dt_planta"/>*</xsl:if>
                                                <xsl:if test="@dt_repique != '--/--/----'">
                                                    <xsl:value-of select="/root/meio[@id_meio = $id_meio]/@nm_meio"/> - <xsl:value-of select="/root/meio[@id_meio = $id_meio]/@dt_meio"/> - <xsl:value-of select="@nm_planta"/> - <xsl:value-of select="@dt_repique"/></xsl:if>
                                            </xsl:attribute>
                                        </input>
                                    </div>
                                    <div style="width: 200px; display: table-cell;">
                                    <xsl:if test="@dt_repique = '--/--/----'">
                                        <xsl:value-of select="@nm_planta"/> - <xsl:value-of select="@dt_planta"/>*
                                    </xsl:if>
                                    <xsl:if test="@dt_repique != '--/--/----'">
                                        <xsl:value-of select="@nm_planta"/> - <xsl:value-of select="@dt_repique"/>
                                    </xsl:if>
                                    </div>
                                    <div style="width: 100px; display: table-cell; text-align: center;">
                                        <xsl:value-of select="@qt_planta"/>
                                    </div>
                                    <div style="width: 200px; display: table-cell; text-align: center;">
                                        <button type="submit" id="bt_salvar" name="bt_salvar" title="Salvar">
                                            <span>Transferência</span>
                                        </button>
                                    </div>
                                    <div class="icon icon-excluir">
                                        <xsl:attribute name="onclick">javascript:Excluir(<xsl:value-of select="@id_planta"/>, document.getElementById('nm_planta_identificacao').value);</xsl:attribute>
                                        x</div>
                                </div>
                            </xsl:for-each>
                            <div class="legend" style="width: 200px;">*Data de germinação</div>
                        </div>
                        <div style="padding-bottom: 10px;"></div>
                        </xsl:if>
                    </div>
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
