var reqExpContaBco = /^[0-9]{2}.[0-9]{3}-[0-9]$/;
verificador.incluirVerificadorDeMascaras();
function obrigatorio_conta_bancaria(e) {
	var source = pegaCampoFonte(e);
	validaRegExpCores(reqExpContaBco, source);
}
function mascara_conta_bancaria(e) {
	return fazMascaraNumerica("00.000-0", reqExpContaBco, e);
}