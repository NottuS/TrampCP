/**
 * Funcoes utilizada pela script verificador.js
 */
verificador.incluirVerificadorDeMascaras();
var regExpCNPJ = /^[0-9]{2}.[0-9]{3}.[0-9]{3}\/[0-9]{4}-[0-9]{2}$/;
function obrigatorio_cnpj(e) {
	var source = pegaCampoFonte(e);
	validaRegExpCores(regExpCNPJ, source);
	trataBackground(validacaoCNPJ(source), source);
}
function mascara_cnpj(e) {
	return fazMascaraNumerica("00.000.000/0000-00", regExpCNPJ, e);
}


function validacaoCNPJ(campo) {
    if (campo.value != "") {
        var CNPJ = campo.value;
        while (CNPJ.indexOf(".") != -1) {
            CNPJ = CNPJ.replace(".", "");
        }
        while (CNPJ.indexOf("-") != -1) {
            CNPJ = CNPJ.replace("-", "");
        }
        while (CNPJ.indexOf(" ") != -1) {
            CNPJ = CNPJ.replace(" ", "");
        }
        while (CNPJ.indexOf("/") != -1) {
            CNPJ = CNPJ.replace("/", "");
        }
        var cnpjCalc = CNPJ.substr(0, 12);
        var cnpjSoma = 0;
        var cnpjDigit = 0;
        var digit = "";
        for (i = 0; i < 4; i++) {
            cnpjSoma = cnpjSoma + parseInt(cnpjCalc.charAt(i)) * (5 - i);
        }
        for (i = 0; i < 8; i++) {
            cnpjSoma = cnpjSoma + parseInt(cnpjCalc.charAt(i + 4)) * (9 - i);
        }
        cnpjDigit = 11 - cnpjSoma % 11;
        if ((cnpjDigit == 10) || (cnpjDigit == 11)) {
            cnpjCalc = cnpjCalc + "0";
        } else {
            digit = digit + cnpjDigit;
            cnpjCalc = cnpjCalc + (digit.charAt(0));
        }
        cnpjSoma = 0;
        for (i = 0; i < 5; i++) {
            cnpjSoma = cnpjSoma + parseInt(cnpjCalc.charAt(i)) * (6 - i);
        }
        for (i = 0; i < 8; i++) {
            cnpjSoma = cnpjSoma + parseInt(cnpjCalc.charAt(i + 5)) * (9 - i);
        }
        cnpjDigit = 11 - cnpjSoma % 11;
        if ((cnpjDigit == 10) || (cnpjDigit == 11)) {
            cnpjCalc = cnpjCalc + "0";
        } else {
            digit = "";
            digit = digit + cnpjDigit;
            cnpjCalc = cnpjCalc + (digit.charAt(0));
        }
        if (CNPJ != cnpjCalc) {
            return false;
        }
        return true;
    }
}