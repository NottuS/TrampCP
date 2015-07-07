/**
 * Fun??es utilizada pela script verificador.js
 */
verificador.incluirVerificadorDeMascaras();
//var regExpData = /^((0[1-9])|([1-2][0-9])|(3[0-1]))\/((0[1-9])|(1[0-2]))\/[1-2][0-9][0-9][0-9]$/;
var regExpData = /^((0?[1-9]|[12]\d)\/(0?[1-9]|1[0-2])|30\/(0?[13-9]|1[0-2])|31\/(0?[13578]|1[02]))\/[1-2][0-9][0-9][0-9]$/;
var regExpDataMasc = /^[0-9]*([/]*[0-9]*)*$/;
function obrigatorio_data(e) { 
	
	var source = pegaCampoFonte(e);
	
	if(source.value == "") {
		source.validado = false;
	} 

	validaRegExpCores(regExpData, source);
	

	if(source.validado && source.data_atual != null) {
		data1 = new Date();
		data1.setFullYear(parseInt(source.value.substr(6,4),10),parseInt(source.value.substr(3,2),10), parseInt(source.value.substr(0,2),10));
		
		dataAtual = new Date();
		dia = dataAtual.getDate();
		if(dia < 10)
			dia = "0" + dia;
		mes = parseInt(dataAtual.getMonth()) + 1;
		if(mes < 10)
			mes = "0" + mes;
		ano = dataAtual.getFullYear()
		
		data1EmNumeros = source.value.substr(6,4) + source.value.substr(3,2) + source.value.substr(0,2);
		dataAtualEmNumeros = ano + "" + mes + "" + dia;
		
		if(eval("data1EmNumeros " + source.data_atual + " dataAtualEmNumeros == false")) {
			exibirMsg(source.mensagem);
			source.validado = false;
		} else {
			limparMsg(tipoNormal);
		}
	}	
	trataBackground(source.validado, source);
	
}
function mascara_data(e) {
	var source = pegaCampoFonte(e);
	var i = source.value.length;
	var resultado = fazMascaraOnLine("01/01/1900", regExpDataMasc, e);
	if(i == source.value.length){
		resultado = fazMascaraOnLine("01/10/1900", regExpDataMasc, e);
	}
	return resultado;
}