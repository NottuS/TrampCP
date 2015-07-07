function ValidaHora(campo, tipohora)
{
	var hor = campo.value.substr(0,2);
	var min = campo.value.substr(3,2);
	var seg = campo.value.substr(6,2);
	
	
	if( (campo.value.length != 5) && (campo.value.length != 8))
	{
	  return false;
	}
	
	if( campo.value.indexOf(':',0) < 0 )
	{
  	  return false;
	}

	if (campo.value.length == 8 && seg > 59) {
	  return false;
	}
	
	if (tipohora){
   	   if( (hor > 12) || (min > 59) )
  	    {
		  return false;
	    }
      }
    else {
	   if( (hor > 23) || (min > 59) )
	    {
		  return false;
	    }
	  }
	  	
	return true;
	
}


function MascaraHora(campo,w, segundo)
{
   var Hora = SoNumero(campo.value);
   var HoraAux = ''; 
   var campo1 = campo.value;   
   var tamvalidacao = 0;
   
   if (segundo){
    tamvalidacao = 6; 
   }
   else{
    tamvalidacao = 4;
   } 
   
   if (w.keyCode == 8) {  
     if (campo1.length == 2 || campo1.length == 5) {
        Hora= Hora.substr(0,Hora.length-1);
       } 
    }
    
   if (Hora.length < tamvalidacao) {
     for (var i=0; i<Hora.length; i++) {
       HoraAux = HoraAux + Hora.substr(i,1);     
       if (i == 1 || i == 3) {
         HoraAux = HoraAux + ":";
        }
      }  
     campo.value = HoraAux;
    }
   else {
      if (tamvalidacao == 4){
         campo.value = campo.value.substr(0,5);
      }
      else{
         campo.value = campo.value.substr(0,8);
      }   
     }    
}
