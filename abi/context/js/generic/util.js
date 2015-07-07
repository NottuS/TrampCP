function SoNumero(nro) {
	return nro.replace(/[^0-9]+/g, '');
}

function DigitaLetra(obj) {
	var valid = ' abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ\u00C0\u00C1\u00C2\u00C3\u00C4\u00C5\u00C7\u00C8\u00C9\u00CA\u00CB\u00CC\u00CD\u00CE\u00CF\u00D1\u00D2\u00D3\u00D4\u00D5\u00D6\u00D9\u00DA\u00DB\u00DC\u00DD\u00E0\u00E1\u00E2\u00E3\u00E4\u00E5\u00E7\u00E8\u00E9\u00EA\u00EB\u00EC\u00ED\u00EE\u00EF\u00F0\u00F1\u00F2\u00F3\u00F4\u00F5\u00F6\u00F9\u00FA\u00FB\u00FC\u00FD\u00FF';
	var numerook = '';
	var temp;

	for(var i = 0; i < obj.value.length; i++) {
		temp = obj.value.substr(i, 1);
		if(valid.indexOf(temp) != -1) {
			numerook = numerook + temp;
		}
	} 
	obj.value = numerook;
}

function DigitaAlfanumerico(obj) {
	var valid = ' 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ\u00C0\u00C1\u00C2\u00C3\u00C4\u00C5\u00C7\u00C8\u00C9\u00CA\u00CB\u00CC\u00CD\u00CE\u00CF\u00D1\u00D2\u00D3\u00D4\u00D5\u00D6\u00D9\u00DA\u00DB\u00DC\u00DD\u00E0\u00E1\u00E2\u00E3\u00E4\u00E5\u00E7\u00E8\u00E9\u00EA\u00EB\u00EC\u00ED\u00EE\u00EF\u00F0\u00F1\u00F2\u00F3\u00F4\u00F5\u00F6\u00F9\u00FA\u00FB\u00FC\u00FD\u00FF';
	var numerook = '';
	var temp;

	for(var i = 0; i < obj.value.length; i++) {
		temp = obj.value.substr(i, 1);
		if(valid.indexOf(temp) != -1) {
			numerook = numerook + temp;
		}
	} 
	obj.value = numerook;
}

function DigitaNumero(campo) {
	campo.value = campo.value.replace(/[^0-9]+/g, '');
}

function TamanhoMax(campo, TamanhoMaximo) {
	if(campo.value.length > TamanhoMaximo)	{
	  campo.value = campo.value.substring(0, TamanhoMaximo);
    }
}

function DigitaNumMascara(campo, maximo) {
	var numero = campo.value.replace(/[^0-9]+/g, '');
	if(numero != '') {
		if(maximo > -1) {
			var maxv100 = maximo * 100;
			if(parseInt(numero, 10) > maxv100) {
				numero = maxv100;
			}
		}
		numero = ('00' + numero).replace(/^0+(.{3,})$/, '$1').replace(/^(.+)(.{2})$/, '$1,$2');
		while(/^[0-9]{4}/.test(numero)) {
			numero = numero.replace(/^([0-9]+)([0-9]{3}[.,])/, '$1.$2');
		}
	}
	campo.value = numero;
}

function DigitaNumeroMascaraCincoDig(campo, maximo) {
	var valid = '0123456789.';
	var numero = campo.value;
	var numeroAux = '';
	var temp;

	campo.value = '';
	for(var i = 0; i < numero.length; i++) {
		temp = numero.substr(i, 1);
		if(valid.indexOf(temp) != -1) {
			numeroAux = numeroAux + temp;
		}
	} 

	if(numeroAux != '') {
		if(maximo > -1) {
			if(parseInt(numeroAux, 10) > 100) {
				numeroAux = maximo;
			}
		}
	}
	campo.value = numeroAux;
	
}

function FormataNumeroCincoDig(campo) {
	var valor = campo.value;
	var valorA = '';
	var valorB = ''; 

	if (campo.value.indexOf('.') > 0) {
		valorA = campo.value.substr(0,campo.value.indexOf('.'));
		valorB = campo.value.substr(campo.value.indexOf('.'),campo.value.length);
	} else {
		valorA = valor;
	}
	campo.value = '';
	if (valorA.length == 1) {
		valor = '00' + valorA + valorB;
	} else if (valorA.length == 2) {
		valor = '0' + valorA + valorB;
	}
	if (valor.length < 9) {
		for(var i = (valor.length-1); i < 8; i++) {
			if((i == 2)) {
				valor = valor + '.';
			} else {
				valor = valor + '0';
			}
		}  
	}

	campo.value = valor;
}

function FormatoMoeda(campo) {
	var Moeda = SoNumero(campo.value);
	var MoedaAux = '';
	var campo1 = campo.value;
	var ponto = 3;

	for(var i = Moeda.length; i > 0; i--) {
		if((i == Moeda.length - 2) && (Moeda.length > 2)) {
			MoedaAux = ',' + MoedaAux;
			ponto = 3;
		}
		if((ponto == 0) && (Moeda.length > 5)) {
			MoedaAux =  '.' + MoedaAux;
			ponto = 3;
        }               
		MoedaAux = Moeda.substr(i - 1, 1) + MoedaAux;     
		ponto--;
	}  
	campo.value = MoedaAux;
}

function bloqueio()
{ 
	var botoes = document.getElementsByTagName("input"); 
  	for (var i = 0 ; i < botoes.length; i++ ) { 
  		if (botoes[i].type == "button") { 
  			botoes[i].disabled = true; 
  		} 
  	}
}

function desbloqueio() {
	var botoes = document.getElementsByTagName("input");
	for (var i = 0; i < botoes.length; i++) {
		if (botoes[i].type == "button" || botoes[i].type == "submit") {
			botoes[i].disabled = false;
		}
	}
}
