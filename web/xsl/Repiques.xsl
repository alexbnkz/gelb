    
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:include href="includes.xsl"/>
    <xsl:template match="/">

<html>
    <head>
        <title>IFRJ - GELB - Repiques</title>
        <xsl:call-template name="head"/>
    </head>
    <body>
    <xsl:call-template name="menu"/>
        <div class="bodier">
            <div class="section-mid">
                <div class="title-large">Repiques</div>
                <div class="container">
                    
                    <xsl:if test="/root/cmd = 'DET'">
                    <div id="panel-form-cadastro" class="panel-form" style="display: block;">
                                                
                        <div id="div-cadastro-left" style="display: block;">
                            <div id="icon-minus" class="icon" title="Voltar" onclick="javascript:history.back();">-</div>
                            <div class="title-small" style="margin-left: 10px">Alterar</div>
                        </div>
                        
                        <div style="width: 620px; margin: 0px auto; display: block;">
                            <form id="frm" name="frm" method="post" action="Repiques" onsubmit="javascript:return validateForm(this);">
                                <input type="hidden" id="cmd" name="cmd" value="Repiques/upd"/>
                                <input type="hidden" id="id" name="id">
                                    <xsl:attribute name="value"><xsl:value-of select="/root/repique/@id_repique"/></xsl:attribute>
                                </input>
                                <div class="row">
                                    <div class="xmedium">
                                        <label>Escolha o Meio:</label> 
                                        <select type="text" id="id_meio" name="id_meio">
					<xsl:for-each select="/root/meio">
                                            <option>
                                                <xsl:attribute name="value"><xsl:value-of select="@id_meio"/></xsl:attribute>
                                                <xsl:value-of select="@nm_meio"/></option>
					</xsl:for-each>
                                        </select>
                                    </div>
                                    <div class="small">
                                        <label>Nome do meio:</label> 
                                            <input type="text" id="nm_meio" name="nm_meio" required="true" maxlength="10">
                                                <xsl:attribute name="value"><xsl:value-of select="/root/meio/@nm_meio"/></xsl:attribute>
                                            </input>
                                    </div>
                                    <div class="small">
                                        <label>Data de preparo:</label> 
                                        <input type="text" id="dt_meio" name="dt_meio" required="true" maxlength="10" onblur="javascript:validateDate(this);" onfocus="javascript:$(this).mask('99/99/9999');">
                                                <xsl:attribute name="value"><xsl:value-of select="/root/meio/@dt_meio"/></xsl:attribute>
                                            </input>
                                    </div>
                                    <div class="small">
                                        <label>Bloquear:</label> 
                                        <select type="text" id="ct_bloqueio" name="ct_bloqueio">
                                            <xsl:if test="/root/meio/@ct_bloqueio = 'S'">
                                                <option value="S" selected="true">Sim</option>
                                                <option value="">Não</option>
                                            </xsl:if>    
                                            <xsl:if test="/root/meio/@ct_bloqueio != 'S'">
                                                <option value="S">Sim</option>
                                                <option value="" selected="true">Não</option>
                                            </xsl:if>    
                                        </select>
                                    </div>

                                </div>
                                <div class="row">
                                    <div class="xxlarge">
                                        <label>Composição:</label> 
                                        <textarea id="de_meio" name="de_meio" rows="5" maxlength="1000"><xsl:value-of select="/root/meio/@de_meio"/></textarea>
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
                                        <div class="small" style="float: left;">
                                            <label>&#10;</label>
                                            <button type="button" id="bt_excluir" name="bt_excluir" title="Excluir">
                                                <xsl:attribute name="onclick">javascript:Excluir(<xsl:value-of select="/root/meio/@id_meio"/>, '<xsl:value-of select="/root/meio/@nm_meio"/>');</xsl:attribute>
                                                <span>Excluir</span>
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
                    </xsl:if>
                    
                    <xsl:if test="/root/cmd = 'DET'">
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
                                    <div class="small">
                                        <label>Nome do meio:</label> 
                                            <input type="text" id="nm_meio" name="nm_meio" required="true" maxlength="10" />
                                    </div>
                                    <div class="small">
                                        <label>Data de preparo:</label> 
                                        <input type="text" id="dt_meio" name="dt_meio" required="true" maxlength="10" onblur="javascript:validateDate(this);" onfocus="javascript:$(this).mask('99/99/9999');" />
                                    </div>
                                    <div class="small">
                                        <label>Bloquear:</label> 
                                        <select type="text" id="ct_bloqueio" name="ct_bloqueio">
                                            <option value="S">Sim</option>
                                            <option value="" selected="true">Não</option>
                                        </select>
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
                            <div class="legend" style="width: 10px;"></div>
                            <div class="legend" style="width: 200px;">Nome - Data de preparo</div>
                                </xsl:if>
                                <div class="row">
                                    <xsl:attribute name="onclick">javascript:Editar(<xsl:value-of select="@id_meio"/>);</xsl:attribute>
                                    <div class="cell icon icon-editar" style="display: none;">
                                        </div>
                                    <div class="cellpadd" style="width: 200px;">
                                        <xsl:variable name="id_meio"><xsl:value-of select="@id_meio"/></xsl:variable>
                                        <xsl:value-of select="@nm_meio"/> - <xsl:value-of select="@dt_meio"/>
                                        
                                        <input type="hidden" id="nm_meio_identificacao" name="nm_meio_identificacao">
                                            <xsl:attribute name="value">
                                                <xsl:value-of select="@nm_meio"/> - <xsl:value-of select="@dt_meio"/> 
                                            </xsl:attribute>
                                        </input>
                                    </div>
                                    
                                    <!--<div class="cell icon icon-excluir">
                                        <xsl:attribute name="onclick">javascript:Excluir(<xsl:value-of select="@id_meio"/>, document.getElementsByName('nm_meio_identificacao')[<xsl:value-of select="position()-1"/>].value);</xsl:attribute>
                                        x</div>-->
                                </div>
                            </xsl:for-each>
                        </div>
                        <div style="padding-bottom: 10px;"></div>
                        </xsl:if>
                    </div>
                    </xsl:if>
                    
                    <xsl:if test="count(/root/meio) > 0">
                    <div class="panel-grid" style="border-bottom: none;">
                        
                        <div class="title-small" style="margin-left: 10px; display: block;">Repiques</div>
                        
                        <div class="table">
                            <xsl:for-each select="/root/meio">
                                <xsl:variable name="meio_id_meio"><xsl:value-of select="@id_meio"/></xsl:variable>
                                <xsl:variable name="dt_repique"><xsl:value-of select="/root/repique[@id_meio = $meio_id_meio]/@dt_repique"/></xsl:variable>
                                <xsl:if test="@nm_experimento != ''">
                               
                            <div class="legend" style="width: 10px;"></div>
                            <div class="legend" style="width: 50px;">Nome</div>
                            <xsl:for-each select="/root/distinct">
                                <div class="legend" style="width: 60px; text-align: center;"><xsl:value-of select="@dia"/>/<xsl:value-of select="@mes"/></div>
                            </xsl:for-each>
                                </xsl:if>
                                <div class="row">
                                    <!-- <xsl:attribute name="onclick">javascript:TrocaComando('Meios/lst');Editar(<xsl:value-of select="@id_meio"/>);</xsl:attribute> -->
                                    <div class="cell icon icon-editar" style="display: none;">
                                        </div>
                                    <div class="cellpadd" style="width: 50px;">
                                        <xsl:value-of select="@nm_meio"/>
                                        <input type="hidden" id="id_meio" name="id_meio">
                                            <xsl:attribute name="value"><xsl:value-of select="@id_meio"/></xsl:attribute>
                                        </input>
                                    </div>
                                    <xsl:for-each select="/root/distinct">
                                        <xsl:variable name="distinct_dt_repique"><xsl:value-of select="@dt_repique"/></xsl:variable>
                                                <div class="cellpadd" style="width: 60px; text-align: center;">
                                        <xsl:for-each select="/root/repique">
                                            <xsl:if test="@dt_repique = $distinct_dt_repique and @id_meio = $meio_id_meio">
                                                    <xsl:value-of select="@qt_planta"/>
                                            </xsl:if>
                                        </xsl:for-each>
                                                </div>
                                    </xsl:for-each>
                                    

                                </div>
                            </xsl:for-each>
                        </div>
                        <div style="padding-bottom: 10px;"></div>
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