function DigitaNumero(campo)
{
 var valid    = "0123456789";
 var numerook = "";
 var temp;
  
    
  for (var i=0; i< campo.value.length; i++) {
    temp = campo.value.substr(i, 1);
    if (valid.indexOf(temp) != "-1") 
      numerook = numerook + temp;
  }
   
 campo.value = numerook;

}

function FormatoMoeda(campo)
{
   
   var Moeda = SoNumero(campo.value);
   var MoedaAux = ''; 
   var campo1 = campo.value;
   var ponto = 3;

   
     for (var i=Moeda.length; i > 0; i--) {
     
       if ((i == Moeda.length - 2) && (Moeda.length > 2)){
         
         MoedaAux =  "," + MoedaAux;
         ponto = 3;
        }
       if ((ponto == 0) && (Moeda.length > 5)){
         MoedaAux =  '.' + MoedaAux;
         ponto = 3;
        }               
       MoedaAux = Moeda.substr(i-1,1) + MoedaAux;     
       ponto --;
      }  
     campo.value = MoedaAux;

}


