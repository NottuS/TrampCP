function MascaraFone(campo,w)
{
   var Fone = SoNumero(campo.value);
   var FoneAux = ''; 
   var campo1 = campo.value;

  
   if (w.keyCode == 8) {      
     if (campo1.length == 3 || campo1.length == 7 || campo1.length == 11) {
        Fone = Fone.substr(0,Fone.length-1);
       } 
    }
     
   if ( (Fone.length > 8) && (Fone.substring(0,1) != '0'))
      Fone = '0' + Fone;
      
   if (Fone.length < 12) {
     
     for (var i=0; i< Fone.length; i++) {
       FoneAux = FoneAux + Fone.substr(i,1);     
       if (((i == 2) && (Fone.length <= 7)) || ((i == 3 ) && (Fone.length == 8))){
         FoneAux = FoneAux + "-";
        }
       if (Fone.length > 8){
         switch (i){
           case 1 : FoneAux = '(' + FoneAux; break;
           case 2 : FoneAux = FoneAux + ')'; break;
           case 5 : {
                     if ((Fone.length == 9) || (Fone.length == 10))
                       FoneAux = FoneAux + '-'; break;
                    }   
           case 6 : {
                     if (Fone.length == 11)
                       FoneAux = FoneAux + '-'; break;
                    }   
           default : '';
         } 
       }
      }
     campo.value = FoneAux;
    }
   else {
      campo.value = campo.value.substr(0,campo.value.length-1);
      if (campo.value.length > 13)
         campo.value = campo.value.substr(0,14);
     }    
}


function DesmascaraFone(nroFone)
{
     return SoNumero(nroFone);
}

