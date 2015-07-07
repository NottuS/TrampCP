<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="agora" class="java.util.Date" />

<c:set var="applicationException" value="${requestScope.APPLICATION_EXCEPTION}" scope="page"/>
<c:set var="exception" value="${requestScope.EXCEPTION}" scope="page"/>
<c:set var="msg" value="${requestScope.MENSAGEM}" scope="page"/>
<c:set var="alertMsg" value="${requestScope.ALERT_MESSAGE}" scope="page"/>

<c:url var='icon_aviso' value='/images/icon_msg_aviso.png' />
<c:url var='icon_erro' value='/images/icon_msg_erro.png' />
<c:url var='icon_sucesso' value='/images/icon_msg_sucesso.png' />

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

<!-- Sucesso -->
<c:if test="${!empty pageScope.msg}">
<div id="msg">
  <table align="center">
   <tr>
    <td><html:img src="${icon_sucesso}"/></td>
    <td class="msg_sucesso"><c:out value="${msg}"/></td>
   </tr>
   </table>
 </div>
</c:if>

<!-- Struts Validator -->
<logic:messagesPresent>
<div id="msg">
  <table align="center">
   <tr>
    <td><html:img src="${icon_aviso}"/></td>
    <td class="msg_aviso" align="left">
    	<ul>
    	  <html:messages id="error">
    	    <li> <c:out value="${error}"/></li>
    	  </html:messages>
    	</ul>
    </td>
   </tr>
   </table>
 </div>
</logic:messagesPresent>

<!-- Mensagens em Alert -->
<c:if test="${!empty pageScope.alertMsg}">
<script language="javascript">
	function loadMessage () {
		alert('<c:out value="${alertMsg}"/>');
	}
	window.onload = loadMessage;
</script>
</c:if>
