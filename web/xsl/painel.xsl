    
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
                <div class="title-large">Seja bem vindo!</div>
                <div class="container">
                    
                    
                    <div id="panel-form-cadastro" class="panel-form" style="display: block;">
                                                
                        <div id="div-cadastro-left" style="display: block;">
                            
                            <div class="title-small" style="margin-left: 10px">Crie um novo experimento!</div>
                        </div>
                        
                        
                        <div style="width: 620px; margin: 0px auto; display: block;">
                            <form id="frm" name="frm" method="post" action="Experimentos" onsubmit="javascript:return validateForm(this);">
                                <input type="hidden" id="cmd" name="cmd" value="Experimentos/lst"/>
                                <input type="hidden" id="id" name="id" value=""/>
                                <div class="row">
                                    <div class="large">
                                        <label>Nome do experimento:</label> 
                                            <input type="text" id="nm_experimento" name="nm_experimento" required="true" maxlength="100" />
                                    </div>
                                    <div class="xsmall">
                                        <label>Data de início:</label> 
                                        <input type="text" id="dt_experimento" name="dt_experimento" required="true" maxlength="10" onblur="javascript:validateDate(this);" />
                                    </div>
                                    <div class="xsmall" style="display: none;">
                                        <label>Tipo:</label> 
                                        <select type="text" id="tp_experimento" name="tp_experimento">
                                            <option value="V">Vegetal</option>
                                            <option value="A">Animal</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="xxlarge">
                                        <label>Observações:</label> 
                                        <textarea id="de_experimento" name="de_experimento" rows="5"></textarea>
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
