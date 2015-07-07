function ValidaCNPJ(campo)
{

 if (campo.value != '') {
   var CNPJ = campo.value;

   while (CNPJ.indexOf(".") != -1)
	 CNPJ = CNPJ.replace(".","");
   while (CNPJ.indexOf("-") != -1)
 	 CNPJ = CNPJ.replace("-","");
   while (CNPJ.indexOf(" ") != -1)
	 CNPJ = CNPJ.replace(" ","");
   while (CNPJ.indexOf("/") != -1)
	 CNPJ = CNPJ.replace("/","");

   var cnpjCalc = CNPJ.substr(0,12);  
   var cnpjSoma = 0;
   var cnpjDigit = 0;  
   var digit = "";    
  
   for (i = 0;  i < 4;  i++) {
     cnpjSoma = cnpjSoma + parseInt(cnpjCalc.charAt(i)) * (5 - i);
    } 
  
   for (i = 0;  i < 8;  i++) {
     cnpjSoma = cnpjSoma + parseInt(cnpjCalc.charAt(i+4)) * (9 - i);            
    }    
 
   cnpjDigit = 11 - cnpjSoma%11;       

   if ((cnpjDigit == 10) || (cnpjDigit == 11)){     
     cnpjCalc = cnpjCalc + "0";        
    } 
   else {     
     digit = digit + cnpjDigit; 
     cnpjCalc = cnpjCalc + (digit.charAt(0));
    }
  
   cnpjSoma = 0;
  
   for (i = 0;  i < 5;  i++){
     cnpjSoma = cnpjSoma + parseInt(cnpjCalc.charAt(i)) * (6 - i);    
    }  
  
   for (i = 0;  i < 8;  i++) {
     cnpjSoma = cnpjSoma + parseInt(cnpjCalc.charAt(i+5)) * (9 - i);    
    }

   cnpjDigit = 11 - cnpjSoma%11;         
    
   if ((cnpjDigit == 10) || (cnpjDigit == 11)) {    
     cnpjCalc = cnpjCalc + "0";    
    } 
   else {     
     digit = "";
     digit = digit + cnpjDigit; 
     cnpjCalc = cnpjCalc + (digit.charAt(0))        
    }
  
   if (CNPJ != cnpjCalc) { 
	 return false;
    }
 
   return true;
  } 
}


function MascaraCNPJ(campo,w)
{
   var CNPJ = SoNumero(campo.value);
   var CNPJAux = ''; 
   var campo1 = campo.value;

   if (w.keyCode == 8) { 
     if (campo1.length == 2 || campo1.length == 6 || campo1.length == 10 || campo1.length == 15) {
        CNPJ = CNPJ.substr(0,CNPJ.length -1);
       } 
    }
    
   if (CNPJ.length < 15) {
     for (var i=0; i<CNPJ.length; i++) {
       CNPJAux = CNPJAux + CNPJ.substr(i,1);     
       if (i == 1 || i == 4) {
         CNPJAux = CNPJAux + ".";
        }
       if (i == 7) {
         CNPJAux = CNPJAux + "/";
        }
       if (i == 11) {
         CNPJAux = CNPJAux + "-";
        }    
      }  
     campo.value = CNPJAux;
    }
   else {
      campo.value = campo.value.substr(0,18);
    }    
}


function CNPJsemMascara(campo)
{
   if (campo.value.length > 14)
     campo.value = campo.value.substring(0,campo.value.length -1);
   else 
     campo.value = SoNumero(campo.value);
}


function DesmascaraCNPJ(campo)
{
  campo.value = SoNumero(campo.value);
}	
	