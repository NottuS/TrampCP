/**
 * Funcoes utilizada pela script verificador.js
 */
verificador.incluirVerificadorDeMascaras();
var regExpCPF = /^[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}$/;

function ValidaCPF(campo)
{
  if (campo.value != '') {
     var CPF = campo.value;

     while (CPF.indexOf(".") != -1)
    	CPF = CPF.replace(".","");
     while (CPF.indexOf("-") != -1)
	    CPF = CPF.replace("-","");
     while (CPF.indexOf(" ") != -1)
	    CPF = CPF.replace(" ","");

     var cpfCalc = CPF.substr(0,9);  
     var cpfSoma = 0;
     var cpfDigit = 0;
     var digit = "";      
    
     for (i = 0; i < 9; i++) {
       cpfSoma = cpfSoma + parseInt(cpfCalc.charAt(i)) * (10 - i)
      }
  
     cpfDigit = 11 - cpfSoma%11;
    
     if (cpfDigit > 9) {
       cpfCalc = cpfCalc + "0";
      } 
     else {
       digit = digit + cpfDigit;
       cpfCalc = cpfCalc + digit.charAt(0);
      }
  
     cpfSoma = 0;
  
     for (i = 0; i < 10; i++) {
       cpfSoma = cpfSoma + parseInt(cpfCalc.charAt(i)) * (11 - i)
      }
  
     cpfDigit = 11 - cpfSoma%11;
  
     if (cpfDigit > 9) {
       cpfCalc = cpfCalc + "0";
      } 
     else {
       digit = "";
       digit = digit + cpfDigit;
       cpfCalc = cpfCalc + digit.charAt(0);
      }  
   
     if (CPF != cpfCalc){
  	   return false;
	  } 

     return true;
    }  
}

function obrigatorio_cpf(e) {
	var source = pegaCampoFonte(e);
	if(validaRegExpCores(regExpCPF, source)) {
		var valido = ValidaCPF(source);
		trataBackground(valido, source);
		if(!valido) source.mensagem = "Informe um CPF válido.";
		else source.mensagem = "";
	}
}

function mascara_cpf(e) {
	return fazMascaraNumerica("000.000.000-00", regExpCPF, e);
}