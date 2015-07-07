
//var regExpHora = /^([01]?[0-9]|[2][0-3])(:[0-5][0-9])(:[0-5][0-9])?$/;

var regExpHora = /^([0-1][0-9]|[2][0-3])(:[0-5][0-9])(:[0-5][0-9])?$/;

verificador.incluirVerificadorDeMascaras();
function obrigatorio_hora(e) {
	var source = pegaCampoFonte(e);
 
	validaRegExpCores(regExpHora, source);
}
function mascara_hora(e) {
	return fazMascaraOnLine("00:00:00", regExpHora, e);
}