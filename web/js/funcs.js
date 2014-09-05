function id(obj){
    return document.getElementById(obj);
}
function name(obj, i){
    return document.getElementsByName(obj)[i];
}
function toBrowse(cmd){
    document.location = cmd.split('/')[0];
}
function ShowHide(obj){
    if(id(obj).style.display == 'none') {
        id(obj).style.display = 'block';   
    } else {
        id(obj).style.display = 'none';
    }
}
function jShowHide(obj){
    if($(obj).attr('style') == 'display: none;') {
        $(obj).show();   
    } else {
        $(obj).hide();
    }
}
function ShowHideSlide(obj, dirc){
    $(obj).toggle('slide', {direction: dirc}, 1000);
}
function ShowHideCadastro(){
    ShowHide('div-cadastro-left');
    ShowHide('div-cadastro-right');  
    ShowHide('panel-form-cadastro');  
}
function slideAlert(text){
    if(text != ''){
        $('#slideAlert').text(encod_(text));
    }
    $('#slideAlert').toggle('slide', {direction: 'up'}, 1000);
}
function encod_(txt) {
  return decodeURIComponent(escape(txt));
}
function validateDate(obj) {
    var vlr = obj.value;

    if (vlr != '') {
        var date = vlr;
        var DtArray = new Array;
        var ExpReg = new RegExp('((0[1-9]|[1-9])|[12][0-9]|3[01])/((0[1-9]|[1-9])|1[012])/(19|20)[0-9][0-9]');
        DtArray = date.split('/');
        erro = false;
        if (date.search(ExpReg) == -1) {
            erro = true;
        }
        else if (((DtArray[1] == 4) || (DtArray[1] == 6) || (DtArray[1] == 9) || (DtArray[1] == 11)) && (DtArray[0] > 30)) {
            erro = true;
        }
        else if (DtArray[1] == 2) {
            if ((DtArray[0] > 28) && ((DtArray[2] % 4) != 0)) {
                erro = true;
            }
            if ((DtArray[0] > 29) && ((DtArray[2] % 4) == 0)) {
                erro = true;
            }
        }
        if (erro) {
            alert(encod_('\"' + vlr + '\" não é uma data válida.'));
            obj.value = '';
            obj.focus();
            return false;
        }
    }
    return true;
}

function validateForm(frm){
    var valida = true;
    
    var i = 0; 
    while (valida && i < frm.childNodes.length) {
        var j = 0; 
        while (valida && j < frm.childNodes[i].childNodes.length) {
            var k = 0; 
            var label = '';
            while (valida && k < frm.childNodes[i].childNodes[j].childNodes.length) {
                var elem = frm.childNodes[i].childNodes[j].childNodes[k];
                
                if(elem.tagName == 'LABEL'){
                    label = elem.innerHTML.replace(':', '');
                }
                if(elem.required && elem.value == ''){
                    alert(encod_('Campo \"' + label + '\" inválido!'));
                    elem.focus();
                    valida = false;
                }
                k++;
            } 
            j++;
        }
        i++;
    }
    
    return valida;
}
function TrocaComando(cmd){
    id('frm').action = 'Meios';
    id('cmd').value = cmd;
}
function Editar(ident){
    id('id').value = ident;
    id('cmd').value = id('cmd').value.split('/')[0] + '/det';
    id('frm').submit();
}
function Excluir(ident, nome){
    if (confirm('Tem certeza que deseja excuir "' + nome + '"?')) {
        id('id').value = ident;
        id('cmd').value = id('cmd').value.split('/')[0] + '/del';
        id('frm').submit();
    } 
}
function CalculaMeio(){
    if(id('volume').value != '' && id('quantidade').value != ''){
        id('solucao').value = id('volume').value * id('quantidade').value; 
        id('sais').value = id('solucao').value * 4.3 / 1000; // Sais MS (g/L)
        id('vitaminas').value = id('solucao').value * 1.0 / 1000; // Vitaminas
        id('sacarose').value = id('solucao').value * 30 / 1000; // Sacarose
        id('agar').value = id('solucao').value * 8 / 1000; // Ágar
    } else {
        id('solucao').value = ''; 
        id('sais').value = ''; 
        id('vitaminas').value = ''; 
        id('sacarose').value = ''; 
        id('agar').value = ''; 
    }
}
function Repicar(i){
    if(name('bt_ok', i-1).style.display == 'none') {
        name('bt_ok', i-1).style.display = 'block';   
        name('bt_cancelar', i-1).style.display = 'block';      
        name('bt_repicar', i-1).style.display = 'none';      
        name('dt_repique', i-1).disabled = false;   
        name('qt_planta', i-1).disabled = false;   
        name('qt_planta', i-1).focus();   
    } else {
        name('bt_ok', i-1).style.display = 'none';
        name('bt_cancelar', i-1).style.display = 'none';      
        name('bt_repicar', i-1).style.display = 'block';      
        name('dt_repique', i-1).disabled = true;   
        name('qt_planta', i-1).disabled = true;   
        name('qt_planta', i-1).value = '';   
    }
}
function RepicarOK(i){
    if(name('dt_repique', i-1).value != '') {
        if(name('qt_planta', i-1).value != '') {
            var sparam = 'id_meio=' + name('id_meio', i-1).value + '&dt_repique=' + name('dt_repique', i-1).value + '&qt_planta=' + name('qt_planta', i-1).value + '';
            slideAlert(sparam);
        } else {
            alert(encod_('Campo Quantidade inválido!'));
            name('qt_planta', i-1).focus();
        }
    } else {
        alert(encod_('Campo Data de repique inválido!'));
        name('dt_repique', i-1).focus();
    }
}