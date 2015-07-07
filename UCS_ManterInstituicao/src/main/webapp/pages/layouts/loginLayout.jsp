<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Mapeamento dos Arquivos Externos -->
<c:url var="link_css_default" value="/css/default.css" />
<c:url var="link_css_screen" value="/css/screen.css" />
<c:url var="img_logo_sistema" value="/images/logo_sistema.jpg" />
<c:url var="img_logo_cliente" value="/images/logo_cliente.jpg" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
<title>Nome do Sistema</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link href="${link_css_default}" rel="stylesheet" type="text/css" />
<link href="${link_css_screen}" rel="stylesheet" type="text/css" />
</head>
<body>

<div class="login_logo_sistema">
   <img src="${img_logo_sistema}"/>
</div>
<div class="login_logo_cliente">
   <a href="#" target="_blank"><img src="${img_logo_cliente}" border="0"></a>
</div>


<tiles:get name="body" />

<tiles:get name="footer" />


</body>
</html:html>