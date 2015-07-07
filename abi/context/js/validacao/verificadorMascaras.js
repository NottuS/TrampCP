function SoNumero(nro) {
	var valid="0123456789";
	var numerook="";
	for (var i=0;i< nro.length;i++) {
		var temp=nro.substr(i,1);
		if(valid.indexOf(temp)!="-1") numerook=numerook+temp;
	} 
	return(numerook);
}

function posicaoRelativaCursor(source,mascara) {
	var inicioSel = source.selectionStart;
	var fimSel = source.selectionEnd;
	var zeros = SoNumero(mascara) + "";	
	var valid    = "0123456789";
	var numerook = "";
	var temp;
	var count = 0;
	for (var i=0; i < inicioSel; i++) {
	  temp = mascara.substr(i, 1);
	  if (valid.indexOf(temp) == "-1") 
	  	count++;
	} 	
	inicioSel -= count;
	return inicioSel;
}
/**
	Função que cria uma mascara on line, permite a edição desque q não descaraterize a mascara.
**/
function fazMascaraNumerica(mascara, regExp, e) {
	var codTecla = null;
	if(e.which != null) {
		codTecla = e.which;
	} else {
		codTecla = e.keyCode;	
	}
	var chr      = String.fromCharCode(codTecla);
	var source   = pegaCampoFonte(e);
	var inicioSel = getCursorPosition(source);
	var valor    = source.value;	
	if(codTecla == 8) {
		if(valor.length == 0) 
			return false;
		else {
			valor = removeEm(valor,inicioSel-1);
		}
	} else if(codTecla < 32) return true;


	var posCursor = inicioSel;
	if(valor.length == 0)
		posCursor = mascara.length;
	var num = null;
	if(inicioSel < mascara.length){
		valor = setCharEm(valor,inicioSel,chr);
		num = (SoNumero(valor) * 1) + "";	
	} else{
		num = (SoNumero(valor + "" + chr) * 1) + "";
	}
	
	var zeros = SoNumero(mascara) + "";
	var txt = "" + zeros.substr(0, zeros.length - num.length) + num;
	var ret = "", cc = 0;
	for(c=0; c<mascara.length; c++) {
		var ch = mascara.substr(c, 1);
		if(ch == "0") ch = txt.substr(cc++, 1);
		ret += ch;
	}
	source.value = ret;
	setCursorPosition(source,posCursor+1);
	return false;
}

/**
	Função que cria uma mascara on line, permite a edição desque q não descaraterize a mascara.
**/
function fazMascaraOnLine(mascara, regExp, e) {

	var codTecla = null;
	if(e.which != null) {
		codTecla = e.which;
	} else {
		codTecla = e.keyCode;	
	}
	var source   = pegaCampoFonte(e);
	if(codTecla < 32) {
		return true;
	}	
	var inicioSel = getCursorPosition(source);

	var posCursor = inicioSel;
	var validos  = "0123456789";
	
	var chr      = String.fromCharCode(codTecla);
	var txt      = source.value;
	var texto = mascara.substring(txt.length);
	
	if (validos.indexOf(texto.substring(0,1)) == "-1") {
		txt += texto.substring(0,1);
			if(posCursor != null)
				posCursor+=1;
	}
	var len = txt.length + 1;
	var campoOK = null;

	if(posCursor == null){
		posCursor = txt.length;
	}
	campoOK = setCharEm(txt + mascara.substr(len, mascara.length),posCursor,chr);	
	
	if(regExp.test(campoOK)) {
		source.value = campoOK.substr(0,len);

	}
	setCursorPosition(source,posCursor+1);		
	return false;
	
}

function setCharEm(str, index, ch) {
   return str.substr(0, index) + ch + str.substr(index);
}
function removeEm(str, index) {
   return str.substr(0, index) + str.substr(index+1);
}

function getCursorPosition(field) {
   var cursorPos = -1;
   if (document.selection && document.selection.createRange) {
     var range = document.selection.createRange().duplicate();
     if (range.parentElement() == field) {
       range.moveStart('textedit', -1);
       cursorPos = range.text.length;
     }
   } else {
     cursorPos = field.selectionStart;
   }
   return cursorPos;
}

function setCursorPosition(field, pos) {
   if (field.createTextRange) {
      var range = field.createTextRange();
      range.collapse(true);
      range.moveEnd('character', pos);
      range.moveStart('character', pos);
      range.select();
   } else {
      field.selectionEnd = pos;
   }
}
