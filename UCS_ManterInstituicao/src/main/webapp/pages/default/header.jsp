<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="img_logo_sistema" value="/images/logo_sistema_cabecalho.jpg" />
<c:url var="img_logo_cliente" value="/images/logo_cliente_cabecalho.png" />

<div id="cabecalho">
	<table cellspacing="0">
		<tr>
			<td width="148"><a href="#"><img src="${img_logo_sistema}" width="148" height="27" border="0"></a></td>
			<td class="cabec_background">&nbsp;</td>
			<td width="62"><a href="http://www.celepar.br/" target="_blank"><img src="${img_logo_cliente}" width="62" height="26" border="0"></a></td>
		</tr>
	</table>
</div>