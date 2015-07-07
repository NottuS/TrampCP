function ValidaPIS(nro)
{
	var localPis = SoNumero(nro.value);
	var soma = 0;
	var digit_pis = 0;
	var ret = 0;
	var aux = 0;

	if( localPis.length>0 && localPis.length!=11 ) // Se o nro for incompleto
	{
		return(false);
	}
	else
	{
		if( localPis.length==0 )
		{
			return(true);
		}
	}
	
	ret = localPis;
	localPis = (localPis.substring(0,10));
	var cont = 0;
	
	for(cont=0; cont<2; cont++)
	{
		aux = parseInt(localPis.substring(cont,(cont+1)),10);
		soma = soma + aux*(4-(cont+1));
	}
	
	for(cont=0; cont<8; cont++)
	{
		aux = parseInt(localPis.substring((cont+2),(cont+3)),10);
		soma = soma + aux*(10-(cont+1));
	}
	
	digit_pis = (11-(soma%11));
	localPis = (parseInt(ret.substring(10,11),10));
	
	if( digit_pis == 11 )
	{
		digit_pis = 0;
	}
	
	if( digit_pis == localPis )
	{
		return(true);
	}
	else
	{
		return(false);
	}
}


function MascaraPIS(campo,w)
{
   var PIS = SoNumero(campo.value);
   var PISAux = ''; 
   var campo1 = campo.value;

   if (w.keyCode == 8) {      
     
     if (campo1.length == 3 || campo1.length == 9 || campo1.length == 12) {
        PIS = PIS.substr(0,PIS.length-1);
       } 
    }
     
   if (PIS.length < 12) {
     
     for (var i=0; i< PIS.length; i++) {
       PISAux = PISAux + PIS.substr(i,1);     
       if (i == 2 || i == 7) {
         PISAux = PISAux + ".";
        }
       if (i == 9) {
         PISAux = PISAux + "-";
        }
      }  
     campo.value = PISAux;
    }
   else {
      campo.value = campo.value.substr(0,14);
     }    
}



