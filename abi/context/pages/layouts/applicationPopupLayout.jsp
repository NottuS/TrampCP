<%@ page errorPage="/pages/ctlr_erro.jsp" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Mapeamento dos Arquivos Externos -->
<c:url var="link_css_default" value="/css/default.css" />
<c:url var="link_css_screen" value="/css/screen.css" />
<c:url var='js_prototype' value='/js/generic/prototype.js' />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>	
	<title>Administração de Bens Imóveis</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
	<link href="${link_css_default}" rel="stylesheet" type="text/css"/>
	<link href="${link_css_screen}" rel="stylesheet" type="text/css"/>
	<script language="JavaScript" type="text/JavaScript" src="${js_prototype}"></script>	
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

<c:set var="applicationException" value="${requestScope.APPLICATION_EXCEPTION}" scope="page"/>
<c:set var="exception" value="${requestScope.EXCEPTION}" scope="page"/>
<c:set var="msg" value="${requestScope.MENSAGEM}" scope="page"/>
<c:set var="alertMsg" value="${requestScope.ALERT_MESSAGE}" scope="page"/>

<c:url var='icon_aviso' value='/images/icon_msg_aviso.png' />
<c:url var='icon_erro' value='/images/icon_msg_erro.png' />
<!-- Avisos -->
<c:if test="${!empty pageScope.applicationException}">
 <c:set var="value_icon_erro" value="1" scope="page"/>
 <c:choose>
  <c:when test="${applicationException.icon == value_icon_erro}">
    <c:set var="icon" value="${icon_erro}" scope="page"/>
    <c:set var="msg_class" value="msg_erro" scope="page"/>
  </c:when>
  <c:otherwise>
    <c:set var="icon" value="${icon_aviso}" scope="page"/>
    <c:set var="msg_class" value="msg_aviso" scope="page"/>
  </c:otherwise>
 </c:choose>
 <div id="msg">
  <table align="center">
   <tr>
    <td><html:img src="${icon}"/></td>
    <td class="${msg_class}"><c:out value="${applicationException.message}"/></td>
   </tr>
   </table>
   <c:if test="${!empty applicationException.causaRaiz}">	
   <hr>
     <div class="msg_descricao">
        Data/Hora: <fmt:formatDate value="${agora}" /> <fmt:formatDate value="${agora}" type="time"/>
        <br><c:out value="${applicationException.causaRaiz}"/>
     </div>
   </c:if>
  </div>
</c:if>

<!-- Erros -->
<c:if test="${!empty pageScope.exception}">
<div id="msg">
  <table align="center">
   <tr>
    <td><html:img src="${icon_erro}"/></td>
    <td class="msg_erro"><c:out value="${exception.message}"/></td>
   </tr>
   </table>
   <hr>
     <div class="msg_descricao">
        Data/Hora: <fmt:formatDate value="${agora}" /> <fmt:formatDate value="${agora}" type="time"/>
      	<br><c:out value="${exception.cause}"/>
     </div>
 </div>
</c:if>

<body>
	<tiles:get name="headerPopup"/>
	<tiles:get name="body"/>
</body>
</html:html>