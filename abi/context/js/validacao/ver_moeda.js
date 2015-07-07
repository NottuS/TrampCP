//Desenvolvido por ericpb

function SoNumero(nro) {
	var valid="0123456789";
	var numerook="";
	for (var i=0;i< nro.length;i++) {
		var temp=nro.substr(i,1);
		if(valid.indexOf(temp)!="-1") numerook=numerook+temp;
	} 
	return(numerook);
}

/**
 * Funcoes utilizada pela script verificador.js
 */	
var regExpPlaca = /(^[0-9][0-9].[0-9]{3}.[0-9]{3},[0-9]{2})|(^[0-9].[0-9]{3}.[0-9]{3},[0-9]{2})|(^[0-9]*.[0-9]{3},[0-9]{2})|(^[0-9]*,[0-9]{2})$/;

function obrigatorio_moeda(e) {
	var source = pegaCampoFonte(e);
	validaRegExpCores(regExpPlaca, source);
}

//ericpb
function mascara_moeda(e) {
	var codTecla = null;
	if(	e.which != null)
		codTecla = e.which;
	else 
		codTecla = e.keyCode;
	var chr      = String.fromCharCode(codTecla);
	var source   = pegaCampoFonte(e);	
	var campo    = source.value;

	if(codTecla == 8) {
		if(campo.length == 0) 
			return false;
		campo = campo.substr(0, valor.length-1);
	} else if(codTecla < 32) 
		return true;
	if(campo.length > 12)
		return false;
	if(SoNumero(chr)=="")
		return false;
	campo = campo.replace(',','');
	campo = campo.replace('.','');
	campo = campo.replace('.','');
	if(campo.length == 0){
		campo = "0,0"+campo;
	}else if(campo.length == 1){
		campo = "0,"+campo;
	}else if(campo.length == 2){
		if(campo.substr(0,1) == "0"){
			campo = "0,"+campo.substr(1);
		}else{
			campo = campo.substr(0,campo.length-1)+","+campo.substr(campo.length-1);
		}
	}else{
		if(campo.substr(0,1) == "0"){
			campo = campo.substr(1);
		}
		campo = campo.substr(0,campo.length-1)+","+campo.substr(campo.length-1);
	}
	if(campo.length > 5 )
		campo = campo.substr(0,campo.length-5)+"."+campo.substr(campo.length-5)
	if(campo.length > 9 )
		campo = campo.substr(0,campo.length-9)+"."+campo.substr(campo.length-9)
	source.value = campo
	return true;

}