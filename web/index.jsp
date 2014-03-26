<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IFRJ - GELB</title>
        <link type="text/css" rel="stylesheet" href="css/gelb.css"/>
        <script type="text/javascript" src="js/funcs.js"></script>
    </head>
    <body>
        <div class="header">
            <div><img src="img/logo2.png" /></div>
        </div>
        <div class="neacker">
            <div class="menu">
                <div>| <span>Painel</span></div>
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
                    <strong>Lista</strong>
                    <div class="panel">
                        <form id="frm" name="frm" method="post" action="" style="display: none">
                            <input type="hidden" id="cmd" name="cmd" value="Experimentos/lst"/>
                            <div class="row">
                                <div class="xmedium">
                                    <label>Nome do experimento:</label> 
                                    <input type="text" id="nm_experimento" name="nm_experimento" required="true" />
                                </div>
                                <div class="small">
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
                                <div class="xmedium">
                                    <label>Observações:</label> 
                                    <textarea id="de_experimento" name="de_experimento"></textarea>
                                </div>
                                <div class="medium">

                                </div>
                                <div class="small">
                                    <label>&nbsp;</label>
                                    <button type="butmit" id="bt_salvar" name="bt_salvar">Salvar</button>
                                </div>
                            </div>
                        </form>
                        <div class="grid">
                            <div class="row"></div>
                            <div class="row"><div>Indução de brotos de Vrísea Botafogensis 2014.1</div></div>
                            <div class="row"><div>Pesquisa de enzima de controle da hipertensão em ratos</div></div>
                            <div class="row"><div>Teste de shampoo em Beagles </div></div>
                            <div class="row"><div>Desenvolvimento de fotossíntese in vitro</div></div>
                            <div class="row"><div>Tratamento oncológico com remédios homeopáticos</div></div>
                            <div class="row"><div>Indução de brotos de Vrísea Botafogensis 2014.1</div></div>
                            <div class="row"><div>Pesquisa de enzima de controle da hipertensão em ratos</div></div>
                            <div class="row"><div>Teste de shampoo em Beagles </div></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
