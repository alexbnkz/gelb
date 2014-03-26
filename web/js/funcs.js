function id(obj){
    return document.getElementById(obj);
}
function toBrowse(cmd){
    document.location = document.URL + cmd.split('/')[0];
}
function toSubmit(cmd){
    id('cmd').value = cmd;
    id('frm').action = document.URL + cmd.split('/')[0];
    id('frm').submit();
}
function validateDate(obj) {
    var vlr = obj.value;

    if (vlr != "") {
        var date = vlr;
        var DtArray = new Array;
        var ExpReg = new RegExp("(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)[0-9][0-9]");
        var ExpReg2 = new RegExp("([1-9]|[12][0-9]|3[01])/([1-9]|1[012])/(19|20)[0-9][0-9]");
        DtArray = date.split("/");
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
            alert("\"" + vlr + "\" não é uma data válida.");
            obj.value = "";
            obj.focus();
            return false;
        }
    }
    return true;
}

// Só funciona no IE
function validateForm(frm) {
    var valida = true;
    var max = frm.all.length;

    // verifica todos os input-text, input-password que são obrigatórios se estão vazios
    for (i = 0; i < max; i++) {
        if (valida && (frm.all.item(i).type == 'text' || frm.all.item(i).type == 'password') 
            && (frm.all.item(i).value == '') && (frm.all.item(i).required == 'true')) {
            alert('Campo \"' + frm.all.item(i-1).innerHTML.replace(':', '') + '\" inválido!');
            frm.all.item(i).focus();
            valida = false;
        }
    }
    return valida;
}
