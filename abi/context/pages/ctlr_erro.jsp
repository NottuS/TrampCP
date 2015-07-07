<%@page isErrorPage="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<fmt:setBundle basename="gov.pr.celepar.util.properties.ApplicationResources" var="bundle"/>
<c:url var='icon_erro' value='/images/icon_msg_erro.png' />
<c:url var="link_css_default" value="/css/default.css" />
<c:url var="link_css_screen" value="/css/screen.css" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Controle de Erro</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
	<link href="${link_css_default}" rel="stylesheet" type="text/css"/>
	<link href="${link_css_screen}" rel="stylesheet" type="text/css"/>
  </head>
  
  <body>
  	<!--<jsp:include page="default/header.jsp"/>-->
	<div id="msg">
	  <table align="center">
	   <tr>
	    <td><img src="${icon_erro}"/></td>
	    <td class="msg_erro" align="left">
	    	<fmt:message key="mensagem.padrao" bundle="${bundle}"/>
	    </td>
	   </tr>
	   </table>
	 </div>
	 <br>
  </body>
  
</html>




