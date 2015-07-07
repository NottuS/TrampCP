/**
 * Funcoes utilizada pela script verificador.js
 */	
function trim(str) {
		return str.replace(/^\s+|\s+$/g,"");
}

function obrigatorio_padrao(e) {
	var source = pegaCampoFonte(e);
	source.validado = trim(source.value).length>0 && source.value != '0';
	trataBackground(source.validado, source);
}

function mascara_padrao(e) {
}