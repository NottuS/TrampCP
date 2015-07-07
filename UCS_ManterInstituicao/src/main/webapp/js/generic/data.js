function ValidaData(campo)
{
	if( campo != '' )
	{
		var strData = campo.value;
		var pos = strData.indexOf("/",0);
		if (pos < 0){
			return false
		}
		dia = strData.slice(0,pos);

		pos2 = strData.indexOf("/",pos+1);
		if (pos < 0){
			return false
		}
		mes = strData.slice(pos+1,pos2);
	
		ano = strData.substr(pos2+1);

		dia= "00" + dia;
		dia = dia.substring(dia.length-2,dia.length);
   		mes= "00" + mes;
		mes = mes.substring(mes.length-2,mes.length);

		if (strData.value != '' && strData.length != 10) {
			return false;
		}
			
		if (isNaN(dia) || isNaN(mes) || isNaN(ano)) {
			return false;
		}
	
		if (ano.length < 4){
			return false;
		}

		if (mes < 1 || mes > 12) {
			return false;
		}

		if (dia < 1 || dia > 31) {
			return false;
		}

		if ((mes==4 || mes==6 || mes==9 || mes==11) && dia==31) {
			return false;
		}

		if (strData.indexOf("_") != -1) {
			return false;
		}

		if (mes == 2) {
			var isleap = (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0))
			if (dia>29 || (dia==29 && !isleap)) {
				return false;
			}
		}
	}
	return true;
}


function MascaraData(campo,w)
{
   var Data = SoNumero(campo.value);
   var DataAux = ''; 
   var campo1 = campo.value;

   if (w.keyCode == 8) {      
     if (campo1.length == 2 || campo1.length == 5) {
        Data = Data.substr(0,Data.length-1);
       } 
    }
    
   if (Data.length < 9) {
     for (var i=0; i < Data.length; i++) {
       DataAux = DataAux + Data.substr(i,1);     
       if (i == 1 || i == 3) {
         DataAux = DataAux + "/";
        }
      }  
     campo.value = DataAux;
    }
   else {
      campo.value = campo.value.substr(0,10);
     }    
}


function DataMaior(dataini, datafim)
{   
   var Dt_inicial = dataini.substr(6,4) + dataini.substr(3,2) + dataini.substr(0,2);
   var Dt_final   = datafim.substr(6,4) + datafim.substr(3,2) + datafim.substr(0,2);
   if (Dt_inicial > Dt_final) {
      return (true);
    }  
   else {
      return (false);
     } 
}


function DataMenor(dataini, datafim)
{   
   var Dt_inicial = dataini.substr(6,4) + dataini.substr(3,2) + dataini.substr(0,2);
   var Dt_final   = datafim.substr(6,4) + datafim.substr(3,2) + datafim.substr(0,2);
   if (Dt_inicial < Dt_final) {
      return (true);
    }  
   else {
      return (false);
     } 
}

function DataIgual(dataini, datafim)
{
  if (!DataMaior(dataini, datafim) && !DataMenor(dataini, datafim)) {
      return (true);
    }  
   else {
      return (false);
     }   
}

function GetDia(Data)
{  
   return (Data.substr(0,2));
}


function GetMes(strData)
{
   return (strData.substr(3,2));
}


function GetAno(strData)
{  
   return (strData.substr(6,4));
}


function DataInversa(strData)
{
    var aaaa = "0000";
    var mm = "00";
    var dd = "00";

    if (strData.length == 10){
        aaaa = GetAno(strData);
        mm   = GetMes(strData);
        dd   = GetDia(strData);
    }
    return (aaaa + mm + dd);
}

/**
 * @author	Willian R. Medeiros
 * @param strDataInformada:	data Informada no formato String
 * @param intDias:	Quantidade de dias a serem incrementados 
 * @return:	data com a quantidade de dias incrementada no formato String
 */
function IncrementaData(strDataInformada, intDias){

	if(isNaN(intDias)){
		return strDataInformada;
	}
	else {
	
		var dia = parseInt(strDataInformada.substr(0,2), 10);
		var mes = parseInt(strDataInformada.substr(3,2), 10);
		var ano = parseInt(strDataInformada.substr(6,4), 10);

		if(isNaN(dia) || isNaN(dia) || isNaN(dia)){
			return strDataInformada;
		}
		else {
			var data = new Date(ano, (mes - 1), dia);
			var acrescimoMillisec = (86400000 * intDias);
			var strData = "";
			
			data.setMilliseconds(acrescimoMillisec);
			
			if(data.getDate() > 9){
				strData = data.getDate() + "/";
			}
			else {
				strData = "0" + data.getDate() + "/";
			}
			
			if((data.getMonth() + 1) > 9){
				strData += (data.getMonth() + 1) + "/";
			}
			else {
				strData += "0" + (data.getMonth() + 1) + "/";
			}
			
			strData += data.getFullYear();
			
			return strData;
		}
	}
}