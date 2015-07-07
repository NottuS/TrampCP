/**
 * Tipos disponiveis: padrao(tipo vazio), email, numerico, placa
 */
//var ie = /msie/i.test(navigator.userAgent);

//tipos de mensagem
var tipoMemsagemDeslizante = 14;
var tipoNormal = 10;
 
var colorValidado = " #FFFFFF";
var colorInValidado = " #FFE4DC";
var debug = null;
var mensagemDebug = null;

//-----------------------//

function Verificador(titulo)
{
	var jssIncluidos = new Array();
	var validacaOk = false;
	var camposEncontrados = new Array();
	var validacoesUsuario = new Array();
	var mensagensUsuario = new Array();
	var obrigatorios = new Array();
	var mascaras = new Array();
	var funcoesNoEnvio = new Array();
	this.mensagemPadrao = "Os campos em vermelho est&atilde;o incorretos.";	
	this.jaExibiuMensagemPadrao = false;
	this.verificar = function(){		
		JSFX_FloatDiv("__divTopoDirMsgFlutua", -350, 10).flt();
		
		this.carregaNodos();			
		for (var i = 0; i < obrigatorios.length; i++) {
			this.verificaTipos(obrigatorios[i].campo);
			this.trataIncluisaoJS(obrigatorios[i], false);
			
			this.verificaOutrosTipos(obrigatorios[i].campo);
			
			
			obrigatorios[i].campo.validado=false;			
		}
		for (var i = 0; i < mascaras.length; i++){
			this.verificaTipos(mascaras[i].campo);
			this.trataIncluisaoJS(mascaras[i], true);
			
			this.verificaOutrosTipos(mascaras[i].campo);
			
			mascaras[i].campo.validado=true;				
		}
	}
	this.setarEventos = function() {
		for(var c=0; c< camposEncontrados.length; c++) {
			this.setKeyPress(camposEncontrados[c]);
		}
	}
	
	this.verificaTipos  = function(node) {		
		if(node.tipo == null)
			node.tipo = "padrao";		
		return node.tipo;
	}
	this.trataIncluisaoJS = function(nodePrincipal,podeValorBranco) {
		if (nodePrincipal != null) {
			node = nodePrincipal.campo;											
			if((nodePrincipal.tipo == "padrao" )&& (node.nodeName == "SELECT")) {
				nodePrincipal.tipo = "combo";
			}
			
			for(var i = 0 ; i < nodePrincipal.attributes.length; i++){
				if((nodePrincipal.attributes[i].nodeValue != null)&&(nodePrincipal.attributes[i].nodeValue != "")){	
					eval("node."+nodePrincipal.attributes[i].nodeName+" = '"+nodePrincipal.attributes[i].nodeValue+"'");
				}
			}						
			node.podeBranco = podeValorBranco;			
			this.incluirArquivoJS(node.tipo);													
			if(node.mensagem == null){
				eval("node.mensagem = '"+this.mensagemPadrao+"'");
			}
			camposEncontrados[camposEncontrados.length] = node;

		}
	}
	this.verificaOutrosTipos  = function(node) {
		if(node.outrostipos != null) {
			lista = node.outrostipos.split(",");
			for(cnt=0; cnt<lista.length; cnt++) {
				if(lista[cnt] != "") {
					this.incluirArquivoJS(lista[cnt]);
				}
			}
		}
	}
	this.setKeyPress = function(nodo) {
		if(nodo.tipo != "") {
			if(nodo.eventos == null)
				nodo.eventos = new Array();
			if(nodo.mascaras == null)
				nodo.mascaras = new Array();				
			nodo.eventos[nodo.eventos.length] = "obrigatorio_" + nodo.tipo + "(event)";								
			if(nodo.tipo != "combo") {				
				nodo.mascaras[nodo.mascaras.length] = "mascara_" + nodo.tipo + "(event)";
				if(nodo.caixaalta == "true") {
					nodo.mascaras[nodo.mascaras.length] = "fazCaixaAlta(source)";
					nodo.eventos[nodo.eventos.length] = "fazCaixaAlta(source)";
				}
			} 

		      switch (nodo.tipo){
				case "combo" : 
					if(nodo.onchange != null)
						nodo.eventos[nodo.eventos.length] = nodo.onchange;
					nodo.onchange = function(event) { return verificador.invocaMetodoNaDigitacao(event) };
					nodo.eventos[nodo.eventos.length] = nodo.onchange;
			           	break;
				default : 
           				nodo.onkeypress = function(event) { return verificador.invocaMetodoNaDigitacao(event) };
           				nodo.eventos[nodo.eventos.length] = nodo.onkeypress;
			           	break;
		      }
		}
	}
	
	this.invocaMetodoNaDigitacao = function(event){

			if(event == null)
				event = window.event;				
			var source = pegaCampoFonte(event);				
			if(source != null)
				return this.acionaMascaras(event) ;
			else 
				return true;
	
	}
	this.incluirArquivoJS  = function(tipo) {
		if(jssIncluidos[tipo] == null) {
			
			document.write("<script type='text/javascript' src='js/validacao/ver_"+tipo+".js' ></script>");
			jssIncluidos[tipo] = true;
		}
	}
	
	//**Incluir fun??es padr?o que s?o usadas dentro dos arquivos ver_***.js que fazem mascaras **/
	this.incluirVerificadorDeMascaras = function(){
			if(jssIncluidos["js_mascaras"] == null) {
				document.write("<script language='JavaScript' type='text/JavaScript' src='js/validacao/verificadorMascaras.js' ></script>");
				jssIncluidos["js_mascaras"] = true;
			}
	}
		
	this.exibirMensagemAviso = function(mensagem, funcao) {
	
		var encontrou = false;
		for(h=0; h<validacoesUsuario.length; h++) { 
			if(validacoesUsuario[h] == funcao) {
				encontrou = true;
			}
		}	
		if(!encontrou) {
			validacoesUsuario[validacoesUsuario.length] = funcao;
			mensagensUsuario[mensagensUsuario.length] = mensagem;				
		}
	}
	
	this.noEnvio = function() {
		document.getElementById("__msgVerificador").innerHTML= "";
		this.jaExibiuMensagemPadrao = false;
		var ret=true;
		var msg = "";
		for(h=0; h < camposEncontrados.length; h++) {							
				this.acionaValidacao(camposEncontrados[h]);				 
				if(!camposEncontrados[h].validado) {
					ret = false;					
					if(camposEncontrados[h].mensagem == this.mensagemPadrao) {
						if(! this.jaExibiuMensagemPadrao){
							msg += "<p><b>"+camposEncontrados[h].mensagem+"</b></p>";
							this.jaExibiuMensagemPadrao = true;
						}
					} else {
						msg += "<li>"+camposEncontrados[h].mensagem+"</li>";
						
					}
				}
		}
		for(h=0; h<validacoesUsuario.length; h++) {
			if(eval(validacoesUsuario[h]+" == false")) {
				msg = msg + "<li>" + mensagensUsuario[h] + "</li>";
				ret = false;
			}
		}
		
		if(!ret) {
			dsp = document.getElementById("msg");

			//limpar mensagem dos struts validator.
			if(dsp !=null)
				dsp.style.display ="none";
				
			document.getElementById("__msgVerificador").innerHTML=
				"<div id='msg'><table align='center'><tr><td width='30'><img src='images/icon_msg_aviso.png'/></td><td class='msg_aviso' align='left'>" + msg + "</td></tr></table></div>";
		} else
			this.chamaEventosNoEnvio();
		return ret;
	}
	this.chamaEventosNoEnvio = function() {
		for(ccc=0; ccc<funcoesNoEnvio.length; ccc++) {
			alert(funcoesNoEnvio[ccc]);
			ret &= eval(funcoesNoEnvio[ccc]);
		}
	}
	this.addEventoNoEnvio = function(str) {
		funcoesNoEnvio[funcoesNoEnvio.length] = str;
	}
	this.addEvent = function(obj, evType, fn) {
		if (obj.addEventListener) obj.addEventListener(evType, fn, true);
		if (obj.attachEvent) obj.attachEvent("on"+evType, fn);
	}

	this.acionaValidacao = function(event){
		var source = pegaCampoFonte(event);
		
		if(source.podeBranco && source.value == "") {
			source.validado = true;
			return true;
		}
		
		var podeExecutar = (source.obrigatorio_se == null);		
		if(!podeExecutar)
			podeExecutar = (eval(source.obrigatorio_se));			
					
		if(podeExecutar){				
			for(var i = 0 ; i < source.eventos.length; i++){
				if(source.eventos[i] != null){
					eval(source.eventos[i]);
				}
			}
		} else {
			source.validado = true;
		}
		return true;
    		
	}
    	
	this.acionaMascaras = function(event) {
		var source = pegaCampoFonte(event);
		var retorno = true;
	
		for(var i = 0 ; i < source.mascaras.length; i++){			
			if(source.mascaras[i] != null){					
				retorno = eval(source.mascaras[i]) && retorno;								
			}
		}
		return retorno;    		
    }
    	
	this.carregaNodos = function() {
		elementos = document.getElementsByTagName("*");    		
		if(elementos != null){    		    		
    		for(var i = 0 ; i < elementos.length; i++ ) {
    			if(elementos[i].tagName == "OBRIGATORIO"){
    				obrigatorios[obrigatorios.length] = elementos[i];
    				obrigatorios[obrigatorios.length-1].campo = elementos[i+1];
    			}
    			if(elementos[i].tagName == "MASCARA"){
    				mascaras[mascaras.length] = elementos[i];
    				mascaras[mascaras.length-1].campo = elementos[i+1];
    			}
    		}
		}
	}
    	
    this.trocaTipo = function(nodo, novoTipo) {
	    if(novoTipo != null && novoTipo != "") {
	    	nodo.tipo = novoTipo;
	    	nodo.eventos = null;
	    	nodo.mascaras = null;
	    	this.setKeyPress(nodo);
	    	this.acionaMascaras(nodo);
	    } else {
	    	alert("PARA DESENVOLVIMENTO: Informe o Novo Tipo!");
	    }
    }
    
	this.redefinirCores = function() {	
		elementos = document.getElementsByTagName("*");    		
		if(elementos != null){    		    		
    		for(var i = 0 ; i < elementos.length; i++ ) {
    			if(elementos[i].tagName == "OBRIGATORIO"){
    				if(elementos[i+1].disabled == false) {
	    				elementos[i+1].validado = false;
	    				elementos[i+1].style.background = colorValidado;
    				} else {
    					elementos[i+1].style.background = null;
    				}
    			}
    			if(elementos[i].tagName == "MASCARA"){
    				if(elementos[i+1].disabled == false) {
	    				elementos[i+1].style.background = colorValidado;
    				} else {
    					elementos[i+1].style.background = null;
    				}
    			} 
    		}
		}	
	} 
	
	this.refinirDesabilitados = function() {
		elementos = document.getElementsByTagName("*");    		
		if(elementos != null){    		    		
    		for(var i = 0 ; i < elementos.length; i++ ) {
    			if(elementos[i].tagName == "OBRIGATORIO" || elementos[i].tagName == "MASCARA"){
    				if(elementos[i+1].disabled) {
	    				elementos[i+1].style.background = null;
					} 
    			}
    		}
		}		
	}   
	
};

function fazCaixaAlta(source){
	if(source !=null){
		if(source.value !=null){
			source.value= source.value.toUpperCase();
		}
	}
}

function trataBackground(validado, source) {
	source.validado=validado;
	if(source.dependente){
		source.style.background = colorInValidado;
		return false;
	}	
	if(source.value == "" && source.podeBranco) {
		source.style.background = colorValidado;
		return true;
	} else {
		if(!source.disabled) {
			if(validado) {
				source.style.background = colorValidado;
				return true;
			} else {
				source.style.background = colorInValidado;
				return false;
			}
		}
	}
}

function validaRegExpCores(regexp, source) {	
	var isValidado = regexp.test(source.value);
	trataBackground(isValidado, source);
	return isValidado;
}
function pegaCampoFonte(e){
	if(typeof(e)=='undefined')
		var e=window.event;
	source=e.target?e.target:e.srcElement;
	if(source == null)
		source = e;
	return source;
}

function focoNoPrimeiroCampo(form) {
	var elementos = form.elements;

	for ( var i = 0; i < elementos.length; i++)
		if ((elementos[i].type != "hidden") && (elementos[i].type != "button") && !(elementos[i].readOnly) && !(elementos[i].disabled)) {
			elementos[i].focus();
			return true;
		}
	
	return false;
}

function limparMsg(tipo) {
	if(tipo == tipoNormal){
		 document.getElementById("__msgVerificador").innerHTML = "";
	}
	
	if(tipo == tipoMemsagemDeslizante) {
		document.getElementById("__divTopoDirMsgFlutua").innerHTML = "";
		document.getElementById("__divTopoDirMsgFlutua").style.display = "none";
	}
}

function alerta(msg) {

	exibirMsg(msg, tipoMemsagemDeslizante);
	mensagemDebug = msg;
	document.getElementById("__divTopoDirMsgFlutua").style.background="yellow";
}
function alertaMais(msg) {

	exibirMsg(msg+'\n'+mensagemDebug, tipoMemsagemDeslizante);
	mensagemDebug = msg+'\n'+mensagemDebug;
	document.getElementById("__divTopoDirMsgFlutua").style.background="yellow";
}
function exibirMsg(msg, tipo, parametros) {
	//caso a mensagem seja "" ou nula limpar mensagem anteriores;
	if(msg == null || msg == "") {
		limparMsg(tipo);
		return; // tipo um "goto"
	}  

	if(tipo == null) 
		tipo = tipoNormal;

	var img = "<img src='images/icon_msg_aviso.png'/>";		
	
	if(parametros != null){
		parm1 = parametros.split("|")[0];
		parm2 = parametros.split("|")[1];
		parm3 = parametros.split("|")[2];
	
		msg = mensagem(msg, parm1);
	}

	if(tipo == tipoNormal) {
		var html = "<div id='msg'><table align='center'><tr><td width='30'>" + img
			+ "</td><td class='msg_aviso' align='left'>" + msg + "</td></tr></table></div>";
		document.getElementById("__msgVerificador").innerHTML = html;
		//dsp = document.getElementById("msg");
		//if(dsp !=null)
		//	dsp.style.display ="none";
	} 
	if(tipo == tipoMemsagemDeslizante) {
		document.getElementById("__divTopoDirMsgFlutua").innerHTML = img + "&nbsp;&nbsp;&nbsp;&nbsp;" + msg;
		document.getElementById("__divTopoDirMsgFlutua").style.display = "block";
		dsp = document.getElementById("msg");
		if(dsp !=null)
			dsp.style.display ="none";
	}	
}

var ns = (navigator.appName.indexOf("Netscape") != -1);
var d = document;
var px = document.layers ? "" : "px";
function JSFX_FloatDiv(id, sx, sy)
{
	var el=d.getElementById?d.getElementById(id):d.all?d.all[id]:d.layers[id];
	window[id + "_obj"] = el;
	if(d.layers)el.style=el;
	el.cx = el.sx = sx;el.cy = el.sy = sy;
	el.sP=function(x,y){this.style.left=x+px;this.style.top=y+px;};
	el.flt=function()
	{
		var pX, pY;
		pX = (this.sx >= 0) ? 0 : ns ? innerWidth : 
		document.documentElement && document.documentElement.clientWidth ? 
		document.documentElement.clientWidth : document.body.clientWidth;
		pY = ns ? pageYOffset : document.documentElement && document.documentElement.scrollTop ? 
		document.documentElement.scrollTop : document.body.scrollTop;
		if(this.sy<0) 
		pY += ns ? innerHeight : document.documentElement && document.documentElement.clientHeight ? 
		document.documentElement.clientHeight : document.body.clientHeight;
		this.cx += (pX + this.sx - this.cx)/8;this.cy += (pY + this.sy - this.cy)/8;
		this.sP(this.cx, this.cy);
		setTimeout(this.id + "_obj.flt()", 40);
	}
	return el;
}
verificador=new Verificador();
