<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:msxsl="urn:schemas-microsoft-com:xslt" exclude-result-prefixes="msxsl">
  <xsl:output method="html" encoding="ISO-8859-1" indent="yes"/>
   <xsl:template match="/" name="head">  
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
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
  </xsl:template>
  <xsl:template match="/" name="menu">  
    <div class="header">
        <div><img src="img/logo2.png" /></div>
    </div>
    <div class="neacker">
        <div class="menu">
            <div>| <span onclick="javascript:toBrowse('');">Painel</span></div>
            <div>| <span onclick="javascript:toBrowse('Experimentos');">Experimentos</span></div>
            <div>| <span onclick="javascript:toBrowse('Meios');">Meios</span></div>
            <div>| <span onclick="javascript:toBrowse('Plantas');">Plantas</span></div>
            <div>| <span onclick="javascript:toBrowse('Clones');">Clones</span></div>
            <div>| <span onclick="javascript:toBrowse('Repiques');">Repiques</span></div>
            <div>| <span onclick="javascript:toBrowse('Levantamentos');">Levantamentos</span></div>
            <div>| <span onclick="javascript:toBrowse('Usuarios');">Usuários</span></div>
        </div>
    </div>
  </xsl:template>
 
</xsl:stylesheet>