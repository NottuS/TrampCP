function MascaraFone(campo,w)
{
   var FoneAux = campo.value;
  
   FoneAux=FoneAux.replace(/\D/g,""); //Remove tudo o que n�o � d�gito
   FoneAux=FoneAux.replace(/^(\d{2})(\d)/g,"($1) $2"); //Coloca par�nteses em volta dos dois primeiros d�gitos
   FoneAux=FoneAux.replace(/(\d)(\d{4})$/,"$1-$2"); //Coloca h�fen entre o quarto e o quinto d�gitos
   
   campo.value = FoneAux;
    
}

function DesmascaraFone(nroFone)
{
     return SoNumero(nroFone);
}
