function SoNumero(nro)
{
 var valid    = "0123456789";
 var numerook = "";
 var temp;

  for (var i=0; i< nro.length; i++) {
    temp = nro.substr(i, 1);
    if (valid.indexOf(temp) != -1) 
      numerook = numerook + temp;
   } 
 return(numerook);
}


function DigitaLetra(obj)
{
 var valid    = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ\u00C0\u00C1\u00C2\u00C3\u00C4\u00C5\u00C7\u00C8\u00C9\u00CA\u00CB\u00CC\u00CD\u00CE\u00CF\u00D1\u00D2\u00D3\u00D4\u00D5\u00D6\u00D9\u00DA\u00DB\u00DC\u00DD\u00E0\u00E1\u00E2\u00E3\u00E4\u00E5\u00E7\u00E8\u00E9\u00EA\u00EB\u00EC\u00ED\u00EE\u00EF\u00F0\u00F1\u00F2\u00F3\u00F4\u00F5\u00F6\u00F9\u00FA\u00FB\u00FC\u00FD\u00FF";
 var numerook = "";
 var temp;

  for (var i=0; i< obj.value.length; i++) {
    temp = obj.value.substr(i, 1);
    if (valid.indexOf(temp) != -1) 
      numerook = numerook + temp;
   } 
 obj.value = numerook;
}


function DigitaNumero(obj)
{
 var valid    = "1234567890";
 var numerook = "";
 var temp;

  for (var i=0; i< obj.value.length; i++) {
    temp = obj.value.substr(i, 1);
    if (valid.indexOf(temp) != -1) 
      numerook = numerook + temp;
   } 
 obj.value = numerook;
}


function TamanhoMax(campo, TamanhoMaximo)
{
	if (campo.value.length > TamanhoMaximo)	{
	  campo.value = campo.value.substring(0,TamanhoMaximo);
    }
	
}
