    
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:include href="includes.xsl"/>
    <xsl:template match="/">

<html>
    <head>
        <title>IFRJ - GELB</title>
        <xsl:call-template name="head"/>
        <style>
            .hormonios {
                margin-bottom: 5px;
                border-collapse: collapse;
            }
            .hormonios td{
                width: 140px;
                padding: 5px;
                text-align: center;
                border: 1px solid #666666;
            }
        </style>
    </head>
    <body>
    <xsl:call-template name="menu"/>
        <div class="bodier">
            <div class="section-mid">
                <div class="title-large">Indução de brotos em Vriesea Botafogensis 2014.1</div>
                <div class="container">
                    
                    
                    <div id="panel-form-cadastro" class="panel-form" style="display: none;">
                                                
                        <div id="div-cadastro-left" style="display: block;">
                            
                            <div class="title-small" style="margin-left: 10px">Informações para preparo de Meio</div>
                        </div>
                        
                        <div style="width: 620px; margin: 0px auto; display: block;">
                            <form id="frm" name="frm" method="post" action="Painel" onsubmit="javascript:return validateForm(this);">
                                <input type="hidden" id="cmd" name="cmd" value="Painel/lst"/>
                                <input type="hidden" id="id" name="id" value=""/>
                                
                                <div class="row">
                                    <table class="hormonios"> 
                                        <tr>
                                            <td colspan="4"><strong>Hormônios brotamento</strong></td>
                                        </tr>
                                        <tr>
                                            <td><strong></strong></td>
                                            <td><strong>ANA 0,5 mg/L</strong></td>
                                            <td><strong>ANA 0,2 mg/L</strong></td>
                                            <td><strong>ANA 0 mg/L</strong></td>
                                        </tr>
                                        <tr>
                                            <td><strong>BAP 0,5 mg/L</strong></td>
                                            <td>TM01</td>
                                            <td>TM03</td>
                                            <td>TM05</td>
                                        </tr>
                                        <tr>
                                            <td><strong>BAP 1,0 mg/L</strong></td>
                                            <td>TM02</td>
                                            <td>TM04</td>
                                            <td>TM06</td>
                                        </tr>
                                        <tr>
                                            <td><strong>BAP 0 mg/L</strong></td>
                                            <td>TM07</td>
                                            <td>TM08</td>
                                            <td>TM09</td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="row">
                                    <div style="margin: 10px; margin-left: 0px;"><strong>Cálculo</strong></div>
                                </div>
                                <div class="row">
                                    <div class="xsmall">
                                        <label>Volume de meio (ml):</label> 
                                        <input type="text" id="volume" name="volume" maxlength="10" /> 
                                    </div>
                                    
                                    <div class="small">
                                        <label>Quantidade:</label> 
                                        <input type="text" id="quantidade" name="quantidade" maxlength="10" /> 
                                    </div>
                                    
                                    <div class="small" >
                                        <label><strong style="color: transparent;">&#10;calcular</strong></label>
                                        <button type="button" id="bt_calcular" name="bt_calcular" title="Calcular" onclick="javascript:CalculaMeio();">
                                            <span>Calcular</span>
                                        </button>
                                    </div>
                                </div>
                                
                                <div class="row">
                                    <div class="small">
                                        <label>Água (ml):</label> 
                                        <input type="text" id="solucao" name="solucao" maxlength="10" /> 
                                    </div>
                                    

                                    <div class="small">
                                        <label>Sais MS (g/L):</label> 
                                        <input type="text" id="sais" name="sais" maxlength="10" /> 
                                    </div>
                                    <div class="small">
                                        <label>Vitaminas (ml):</label> 
                                        <input type="text" id="vitaminas" name="vitaminas" maxlength="10" /> 
                                    </div>
                                    <div class="small">
                                        <label>Sacarose (g):</label> 
                                        <input type="text" id="sacarose" name="sacarose" maxlength="10" /> 
                                    </div>
                                    <div class="small">
                                        <label>Ágar (g):</label> 
                                        <input type="text" id="agar" name="agar" maxlength="10" /> 
                                    </div>
                                </div>
                                
                                
                            </form>
                        </div>
                    </div>
                    
                    
                    <xsl:if test="count(/root/meio) > 0">
                    <div class="panel-grid">
                        
                        <div class="title-small" style="margin-left: 10px; display: block;">Meios de cultura</div>
                        
                        <div class="table">
                            <xsl:for-each select="/root/meio">
                                <xsl:if test="@nm_experimento != ''">
                               
                            <div class="legend" style="width: 10px;"></div>
                            <div class="legend" style="width: 40px;">Nome</div>
                            <div class="legend" style="width: 100px;">Data de preparo</div>
                            <div class="legend" style="width: 220px;">Validade</div>
                            <div class="legend" style="width: 90px;">Data de repique</div>
                            <div class="legend" style="width: 100px;">Quantidade</div>
                                </xsl:if>
                                <div class="row">
                                    <!-- <xsl:attribute name="onclick">javascript:TrocaComando('Meios/lst');Editar(<xsl:value-of select="@id_meio"/>);</xsl:attribute> -->
                                    <div class="cell icon icon-editar" style="display: none;">
                                        </div>
                                    <div class="cellpadd" style="width: 40px;">
                                        <xsl:value-of select="@nm_meio"/>
                                        <input type="hidden" id="id_meio" name="id_meio">
                                            <xsl:attribute name="value"><xsl:value-of select="@id_meio"/></xsl:attribute>
                                        </input>
                                    </div>
                                    <div class="cellpadd" style="width: 100px;">
                                        <xsl:value-of select="@dt_meio"/>
                                    </div>
                                    <div class="cellpadd" style="width: 120px;">
                                        <xsl:value-of select="@nu_validade"/>
                                    </div>
                                    <div class="cell" style="width: 110px;">
                                        <button type="button" id="bt_repicar" name="bt_repicar" title="Repicar">
                                            <xsl:attribute name="onclick">javascript:Repicar(<xsl:value-of select="position()"/>);</xsl:attribute>
                                            <span>Repicar</span>
                                        </button>
                                    </div>
                                    <div class="cell" style="width: 100px;">
                                        <input type="text" id="dt_repique" name="dt_repique" disabled="true" required="true" maxlength="10" onblur="javascript:validateDate(this);" style="width: 90px;">
                                            <xsl:attribute name="value"><xsl:value-of select="@dt_hoje"/></xsl:attribute>
                                        </input>
                                    </div>
                                    <div class="cell" style="width: 100px;">
                                        <input type="text" id="qt_planta" name="qt_planta" disabled="true" required="true" maxlength="10" style="width: 80px;" />
                                    </div>
                                    <div class="cell" style="width: 80px;">
                                        <button type="button" id="bt_ok" name="bt_ok" title="Ok" style="display: none;">
                                            <xsl:attribute name="onclick">javascript:RepicarOK(<xsl:value-of select="position()"/>);</xsl:attribute>
                                            <span>Ok</span>
                                        </button>
                                    </div>
                                    <div class="cell" style="width: 50px;">
                                        <button type="button" id="bt_cancelar" name="bt_cancelar" title="Cancelar" style="display: none;">
                                            <xsl:attribute name="onclick">javascript:Repicar(<xsl:value-of select="position()"/>);</xsl:attribute>
                                            <span style="color: #603030;">X</span>
                                        </button>
                                    </div>

                                </div>
                            </xsl:for-each>
                        </div>
                        <div style="padding-bottom: 10px;"></div>
                    </div>
                    </xsl:if>
                    
                    
                    
                    <xsl:if test="count(/root/login) > 0">
                    <div class="panel-grid">
                        
                        <div class="title-small" style="margin-left: 10px; display: block;">Usuário logado</div>
                        
                        <div class="table">
                            <div class="legend" style="width: 10px;"></div>
                            <div class="legend" style="width: 200px;">Nome</div>
                            <div class="legend" style="width: 300px;">E-mail</div>
                            <div class="legend" style="width: 300px;">Privilégio</div>
                            <div class="row">
                                <div class="cell icon icon-editar" style="display: none;">
                                    </div>
                                <div class="cellpadd" style="width: 200px;">
                                    <div><xsl:value-of select="/root/login/@nm_usuario"/></div>
                                </div>
                                <div class="cellpadd" style="width: 300px;">
                                    <xsl:value-of select="/root/login/@cd_email"/>
                                </div>
                                <div class="cellpadd" style="width: 300px;">
                                    <xsl:value-of select="/root/login/@nm_privilegio"/>
                                </div>
                            </div>
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
