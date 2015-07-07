<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">

<%@ page errorPage="/pages/ctlr_erro.jsp" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Mapeamento dos Arquivos Externos -->
<c:url var="link_css_default" value="/css/default.css" />
<c:url var="link_css_screen" value="/css/screen.css" />
<c:url var='js_jquery' value='/js/generic/jquery-1.3.2.js' />
<c:url var='js_prototype' value='/js/generic/prototype.js' />
<c:url var='js_thickbox' value='/js/generic/thickbox.js' />
<c:url var="js_verificador" value="/js/validacao/verificador.js" />
<c:url var="js_mensagens" value="/js/mensagens.js" />

<html:html>
	<head>	
		<title>Administração de Bens Imóveis</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<script language="JavaScript" type="text/javascript" src="${js_jquery}"></script>
		<script language="JavaScript" type="text/javascript" src="${js_prototype}"></script>
		<script language="JavaScript" type="text/javascript" src="${js_thickbox}"></script>
		<script language="JavaScript" type="text/JavaScript" src="${js_verificador}"></script>
		<script language="JavaScript" type="text/JavaScript" src="${js_mensagens}"></script>
	
		<link rel="stylesheet" href="css/thickbox.css" type="text/css" media="screen" />
		
		<link href="${link_css_default}" rel="stylesheet" type="text/css"/>
		<link href="${link_css_screen}" rel="stylesheet" type="text/css"/>
		
		<script>
			var count = 0;
			function manterLogado(){
				var tempo = 120000;
				if(Ajax.activeRequestCount == 0){	
					new Ajax.Request('entrada.do', { method: 'get', onComplete: function(){
								if(document.getElementById('contadorLogado')!=null){ 
									document.getElementById('contadorLogado').innerHTML = count++;
								} 
							} });
				}
				setTimeout("manterLogado()",tempo);
			}
			manterLogado();
		</script>
		
	</head>
	
	<body>
	
		<tiles:get name="header"/>
		<tiles:get name="menu"/>
		<tiles:get name="location"/>
			
		<div align="center">
			<div id="msg_ajax"></div>
			<div id="__msgVerificador"></div>
			<div id="__divTopoDirMsgFlutua" class="flutua"></div>
			
			<jsp:include page="/pages/ctlr_mensagens.jsp" />
			<tiles:get name="body"/>
		</div>
			
		<tiles:get name="footer"/>
		
		<script language=Javascript>
			// Script para o Verificador
			verificador.verificar();
			verificador.setarEventos();
			var _formularioZero = document.forms[0];
			form = _formularioZero;
			if(_formularioZero) focoNoPrimeiroCampo(_formularioZero);
			// FIM do Script para o Verificador			
		</script>
	
	</body>
</html:html>