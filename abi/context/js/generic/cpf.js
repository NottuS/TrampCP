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


function MascaraCPF(campo,w)
{
   var CPF = SoNumero(campo.value);
   var CPFAux = ''; 
   var campo1 = campo.value;

   if (w.keyCode == 8) {      
     if (campo1.length == 3 || campo1.length == 7 || campo1.length == 11) {
        CPF = CPF.substr(0,CPF.length-1);
       } 
    }
     
   if (CPF.length < 12) {
     
     for (var i=0; i<CPF.length; i++) {
       CPFAux = CPFAux + CPF.substr(i,1);     
       if (i == 2 || i == 5) {
         CPFAux = CPFAux + ".";
        }
       if (i == 8) {
         CPFAux = CPFAux + "-";
        }
      }  
     campo.value = CPFAux;
    }
   else {
      campo.value = campo.value.substr(0,14);
     }    
}


function CPFsemMascara(campo)
{
   if (campo.value.length > 11)
     campo.value = campo.value.substring(0,campo.value.length -1);
   else 
     campo.value = SoNumero(campo.value);
}


function DesmascaraCPF(campo)
{
  campo.value = SoNumero(campo.value);
}