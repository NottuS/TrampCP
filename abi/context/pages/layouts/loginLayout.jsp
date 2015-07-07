<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Mapeamento dos Arquivos Externos -->
<c:url var="link_css_login" value="/css/login.css" />
<c:url var="img_secretaria" value="/images/secretaria.png" />

<html:html>
	<head>
		<title>Administração de Bens Imóveis</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<link href="${link_css_login}" rel="stylesheet" type="text/css" />
	
	</head>
	<div id="idncv_sessionexp"></div>
	<div id="cabecalho">
		<table align="center" border="0" >
	      	<tr align="center">
	        	<td width="250"><a href="http://www.pr.gov.br/" target="_blank">
	        		<img src="http://www.pr.gov.br/logos/brasao_212x93.png" width="212" height="93" border="0" /></a>
	        	</td>
	        	<td align="left"><img src="${img_secretaria}" width="424"/></td>
	    	</tr>
		</table>
	</div>
	<jsp:include page="/pages/ctlr_mensagens.jsp" />
	<tiles:get name="body"/>
	<tiles:get name="footer"/>
</html:html>