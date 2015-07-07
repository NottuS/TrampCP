function MascaraFone(campo,w)
{
   var FoneAux = campo.value;
  
   FoneAux=FoneAux.replace(/\D/g,""); //Remove tudo o que não é dígito
   FoneAux=FoneAux.replace(/^(\d{2})(\d)/g,"($1) $2"); //Coloca parênteses em volta dos dois primeiros dígitos
   FoneAux=FoneAux.replace(/(\d)(\d{4})$/,"$1-$2"); //Coloca hífen entre o quarto e o quinto dígitos
   
   campo.value = FoneAux;
    
}

function DesmascaraFone(nroFone)
{
     return SoNumero(nroFone);
}

