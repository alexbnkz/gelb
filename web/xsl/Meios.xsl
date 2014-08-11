    
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:include href="includes.xsl"/>
    <xsl:template match="/">

<html>
    <head>
        <title>IFRJ - GELB - Meios</title>
        <xsl:call-template name="head"/>
    </head>
    <body>
    <xsl:call-template name="menu"/>
        <div class="bodier">
            <div class="section-mid">
                <div class="title-large">Meios</div>
                <div class="container">
                    <div id="panel-form-cadastro" class="panel-form" style="display: none;">
                                                
                        <div id="div-cadastro-left" style="display: none;">
                            <div id="icon-minus" class="icon" title="Encolher" onclick="javascript:ShowHideCadastro();">-</div>
                            <div class="title-small" style="margin-left: 10px">Novo cadastro</div>
                        </div>
                        
                        <div style="width: 620px; margin: 0px auto; display: block;">
                            <form id="frm" name="frm" method="post" action="Meios" onsubmit="javascript:return validateForm(this);">
                                <input type="hidden" id="cmd" name="cmd" value="Meios/lst"/>
                                <input type="hidden" id="id" name="id" value=""/>
                                <div class="row">
                                    <div class="xmedium">
                                        <label>Escolha o Experimento:</label> 
                                        <select type="text" id="id_experimento" name="id_experimento">
					<xsl:for-each select="/root/experimento">
                                            <option>
                                                <xsl:attribute name="value"><xsl:value-of select="@id_experimento"/></xsl:attribute>
                                                <xsl:value-of select="@nm_experimento"/></option>
					</xsl:for-each>
                                        </select>
                                    </div>
                                    <div class="xxsmall">
                                        <label>Nome do meio:</label> 
                                            <input type="text" id="nm_meio" name="nm_meio" required="true" maxlength="100" />
                                    </div>
                                    <div class="small">
                                        <label>Data de preparo:</label> 
                                        <input type="text" id="dt_meio" name="dt_meio" required="true" maxlength="10" onblur="javascript:validateDate(this);" />
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="xxlarge">
                                        <label>Composição:</label> 
                                        <textarea id="de_meio" name="de_meio" rows="5" maxlength="1000"></textarea>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="xxlarge">
                                        <xsl:if test="count(/root/experimento) > 0">
                                        <div class="small" style="float: right;">
                                            <label>&#10;</label>
                                            <button type="submit" id="bt_salvar" name="bt_salvar" title="Salvar">
                                                <span>Salvar</span>
                                            </button>
                                        </div>
                                        </xsl:if>
                                        <xsl:if test="count(/root/experimento) = 0">
                                        <div class="legend" style="text-align: center;">
                                           Não existem experimentos cadastrados!
                                        </div>
                                        </xsl:if>

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
                        <xsl:if test="count(/root/meio) > 0">
                        <div class="title-small" style="margin-left: 10px; display: block;">Lista</div>
                        
                        <div class="grid">
                            <xsl:for-each select="/root/meio">
                                <xsl:if test="@nm_experimento != ''">
                                <div>
                                    <div class="title-small" style="margin-left: 10px; font-size: 14px;">
                                        <xsl:value-of select="@nm_experimento"/></div>
                                </div>
                            <div class="legend" style="width: 20px;"></div>
                            <div class="legend" style="width: 200px;">Nome - Data de preparo</div>
                                </xsl:if>
                                <div class="row">
                                    <div class="cell icon icon-editar">
                                        </div>
                                    <div style="width: 200px; display: table-cell;">
                                        <xsl:variable name="id_meio"><xsl:value-of select="@id_meio"/></xsl:variable>
                                        <xsl:value-of select="/root/meio[@id_meio = $id_meio]/@nm_meio"/> - <xsl:value-of select="/root/meio[@id_meio = $id_meio]/@dt_meio"/>
                                        
                                        <input type="hidden" id="nm_meio_identificacao" name="nm_meio_identificacao">
                                            <xsl:attribute name="value">
                                                <xsl:value-of select="/root/meio[@id_meio = $id_meio]/@nm_meio"/> - <xsl:value-of select="/root/meio[@id_meio = $id_meio]/@dt_meio"/> 
                                            </xsl:attribute>
                                        </input>
                                    </div>
                                    <div style="width: 500px; display: table-cell;">
                                        
                                    </div>
                                    <div class="icon icon-excluir">
                                        <xsl:attribute name="onclick">javascript:Excluir(<xsl:value-of select="@id_meio"/>, document.getElementsByName('nm_meio_identificacao')[<xsl:value-of select="position()-1"/>].value);</xsl:attribute>
                                        x</div>
                                </div>
                            </xsl:for-each>
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
