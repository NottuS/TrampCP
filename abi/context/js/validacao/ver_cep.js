/**
 * Funcoes utilizada pela script verificador.js
 */
verificador.incluirVerificadorDeMascaras();
var regCEPSemPontos = /^[0-9]{5}[0-9]{3}$/;
var regCEP = /^[0-9]{5}-[0-9]{3}$/;
function obrigatorio_cep(e) {
	var source = pegaCampoFonte(e);
	validaRegExpCores(regCEP, source);
}
function mascara_cep(e) {
	return fazMascaraNumerica2("00000000", e);
}

function completa_mascara_cep(e) {
	
	var source = pegaCampoFonte(e);
	
	// retirar a máscara
	source.value = tiraMascara(source.value);

	// colocar zeros a esquerda
	if(source.value != "" && source.value.length <= 6) {	
		
		while (source.value.length < 6) { 
			source.value = "0" + source.value;
		}	
		source.value =  mascaraGenerica(source.value, "###-###");
	} else {
		if(source.value != "" && source.value.length <= 8) {	
			while (source.value.length < 8) {
				source.value = "0" + source.value;
			}	
			source.value =  mascaraGenerica(source.value, "#####-###");
		}		
	}
	return;
}