function id(obj){
    return document.getElementById(obj);
}
function toBrowse(cmd){
    document.location = 'http://alex-work:8080/GELB/' + cmd.split('/')[0];
}
function ShowHide(obj){
    if($(obj).attr('style') == 'display: none;') {
        $(obj).show();   
    } else {
        $(obj).hide();
    }
}
function ShowHideSlide(obj, dirc){
    $(obj).toggle('slide', {direction: dirc}, 1000);
}
function validateDate(obj) {
    var vlr = obj.value;

    if (vlr != '') {
        var date = vlr;
        var DtArray = new Array;
        var ExpReg = new RegExp('(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)[0-9][0-9]');
        var ExpReg2 = new RegExp('([1-9]|[12][0-9]|3[01])/([1-9]|1[012])/(19|20)[0-9][0-9]');
        DtArray = date.split('/');
        erro = false;
        if (date.search(ExpReg) == -1 && date.search(ExpReg2) == -1) {
            erro = true;
        }
        else if (date.search(ExpReg2) != -1 && date.length > 8) {
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
            alert('\"' + vlr + '\" não é uma data válida.');
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
                    alert('Campo \"' + label + '\" inválido!');
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
