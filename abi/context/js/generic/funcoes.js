function strZero(obj,nBytes)
{
   var strRetorno = obj.value.toString();
   if (obj.value.length != nBytes)
      for (var i=1;i<= nBytes - obj.value.length;i++)
         strRetorno = "0" + strRetorno;
  obj.value = strRetorno;
}


function Trim(obj)
{	
    Ltrim(obj);
    Rtrim(obj);
}


function Rtrim(obj) 
{
   varx = obj.value;
   while (varx.substr(varx.length - 1,1) == " ") 
   {
      varx = varx.substr(0, varx.length -1);
   }   
   obj.value = varx;
}


function Ltrim(obj) 
{
   varx = obj.value;
   while (varx.substr(0,1) == " ") 
   {
      varx = varx.substr(1, varx.length -1);
   }
  obj.value = varx;
}

