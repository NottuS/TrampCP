/**
 * Fun??es utilizada pela script verificador.js
 *
 * 12/07/2006 : Adicionado tratamento para valores decimais. 
 				Para utilizar decimal setar na TAG o atributo isFloat="true".
 */	
var regExpNumerico = /^[0-9][0-9]*$/;
var regExpNumericoFlutuante = /^[0-9][0-9]*$|^[[0-9][0-9]*,[0-9]{1,2}]*$/;
var regExpNumericoFlutuanteMasc = /^[0-9][0-9]*$|^[[0-9][0-9]*,[0-9]{0,2}]*$/;
var regExp = null;
var tipoNormal = 10;

function obrigatorio_numerico(e) {
	var source = pegaCampoFonte(e);
	if(source.isfloat != null )
		regExp = regExpNumericoFlutuante;
	else
		regExp = regExpNumerico;		
		
	var val1 = regExp.test(source.value);
	if(source.value != null)
		var valor = source.value.replace(",",".");
	else
		valor = 0;
	var val2 = (source.min) ? ((valor*1) >= (eval(source.min)*1)) : true;
	var val3 = (source.max) ? ((valor*1) <= (eval(source.max)*1)) : true;
	val1 = trataBackground((val1 & val2 & val3), source);
	
	if(val1) 
		limparMsg(tipoNormal);
	else {		
		if(!(val2 && val3) && source.min && source.max) 
			exibirMsg("O valor deve estar entre " + eval(source.min) + " e " + eval(source.max), tipoMemsagemDeslizante);	

		if((!val2 && val3) && source.min && source.max) 
			exibirMsg("O valor deve ser maior ou igual a " + eval(source.min), tipoMemsagemDeslizante);
			
		if((val2 && !val3) && source.min && source.max) 
			exibirMsg("O valor deve ser menor ou igual a " + eval(source.max), tipoMemsagemDeslizante);

		if((val2 && val3) && source.min && source.max && regExpNumericoFlutuanteMasc.test(source.value) )
			exibirMsg("O n?mero deve ser no formato 999,00", tipoMemsagemDeslizante);
	}
	
}
function mascara_numerico(e) {
	//var codTecla = (window.Event) ? e.which : e.keyCode;
	var codTecla = null;

	if(e.which != null) {
		codTecla = e.which;
	} else {
		codTecla = e.keyCode;	
	}

	var chr      = String.fromCharCode(codTecla);
	
	if(codTecla < 32) return true;
	
	var source = pegaCampoFonte(e);
	
	if(source.isfloat != null)
		regExp = regExpNumericoFlutuanteMasc;
	else	
		regExp = regExpNumerico;		
		
	return(regExp.test(source.value + chr));
}