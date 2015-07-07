/**
 * Fun??es utilizada pela script verificador.js
 */
var regExpCombo = /^[1-9][0-9]*$/;
function obrigatorio_combo(e) {
	source = pegaCampoFonte(e);
	validaRegExpCores(regExpCombo, source);
}