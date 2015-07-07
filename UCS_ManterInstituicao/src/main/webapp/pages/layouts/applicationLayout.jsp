<%@ page errorPage="/pages/ctlr_erro.jsp" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Mapeamento dos Arquivos Externos -->
<c:url var="link_css_default" value="/css/default.css" />
<c:url var="link_css_screen" value="/css/screen.css" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>	
	<title>UCS Manter Instituição</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
	<link href="${link_css_default}" rel="stylesheet" type="text/css"/>
	<link href="${link_css_screen}" rel="stylesheet" type="text/css"/>
</head>

<body>

<tiles:get name="header"/>
<tiles:get name="menu"/>
<tiles:get name="toolbar"/>
<tiles:get name="location"/>
<jsp:include page="/pages/ctlr_mensagens.jsp" />
<tiles:get name="body"/>
<tiles:get name="footer"/>

</body>
</html:html>