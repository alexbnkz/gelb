    
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
                <div class="title-large">Experimentos</div>
                <div class="container">
                    <div class="panel-form">
                        
                        <div id="div-cadastro-right" style="display: block;">
                            <div id="icon-plus" class="icon" title="Expandir" onclick="javascript:ShowHide('#div-cadastro-right');ShowHide('#div-cadastro-left');ShowHide('#frm');">+</div>
                            <div class="title-small" style="margin-left: 10px">Novo cadastro</div>
                        </div>
                        
                        <div id="div-cadastro-left" style="display: none;">
                            <div id="icon-minus" class="icon" title="Encolher" onclick="javascript:ShowHide('#frm');ShowHide('#div-cadastro-left');ShowHide('#div-cadastro-right');">-</div>
                            <div class="title-small" style="margin-left: 10px">Novo cadastro</div>
                        </div>
                        
                        <div style="width: 620px; margin: 0px auto; display: block;">
                            <form id="frm" name="frm" method="post" action="Experimentos" onsubmit="javascript:return validateForm(this);" style="display: none;">
                                <input type="hidden" id="cmd" name="cmd" value="Experimentos/lst"/>
                                <input type="hidden" id="id_experimento" name="id_experimento" value=""/>
                                <div class="row">
                                    <div class="xmedium">
                                        <label>Nome do experimento:</label> 
                                            <input type="text" id="nm_experimento" name="nm_experimento" required="true" />
                                    </div>
                                    <div class="xsmall">
                                        <label>Data de início:</label> 
                                        <input type="text" id="dt_experimento" name="dt_experimento" required="true" maxlength="10" onblur="javascript:validateDate(this);"  />
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
                                    <div class="xxmedium">
                                        <label>Observações:</label> 
                                        <textarea id="de_experimento" name="de_experimento" rows="5"></textarea>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="xxmedium">
                                        <div class="small" style="float: right;">
                                            <label>&#10;</label>
                                            <button type="submit" id="bt_salvar" name="bt_salvar">
                                                <span>Salvar</span>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="panel-grid">
                        <div class="title-small" style="margin-left: 10px; display: block;">Lista</div>
                        
                        <div class="grid">
                            
                            <div class="row"><div>Indução de brotos de Vrísea Botafogensis 2014.1</div></div>
                            <div class="row"><div>Pesquisa de enzima de controle da hipertensão em ratos</div></div>
                            <div class="row"><div>Teste de shampoo em Beagles </div></div>
                            <div class="row"><div>Desenvolvimento de fotossíntese in vitro</div></div>
                            <div class="row"><div>Tratamento oncológico com remédios homeopáticos</div></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
    </body>
</html>

    </xsl:template>
</xsl:stylesheet>
