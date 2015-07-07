<%@ taglib uri="http://celepar.pr.gov.br/taglibs/informacao.tld" prefix="info" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="icon_voltar" value="/images/icon_voltar.png" />
<c:url var="link_ajuda" value="/help/indice.jsp" />
<c:url var="icon_ajuda" value="/images/icon_ajuda.png" />

<div id="barra_acao_top">
	<div id="barra_localizacao"><info:pathLastFunction/></div>
	<table align="right" cellspacing="0">
		<tr>
			<td><a href="javascript:history.back();">voltar</a></td><td><a href="javascript:history.back();"><img src="${icon_voltar}" border="0"></a></td>
			<td>&nbsp;</td>
			<td><a href="#" onClick="MM_openBrWindow('${link_ajuda}','help','scrollbars=yes,resizable=yes,width=500,height=500')">ajuda</a></td><td><a href="#" onClick="MM_openBrWindow('${link_ajuda}','help','scrollbars=yes,resizable=yes,width=500,height=500')"><img src="${icon_ajuda}" border="0"></a></td>
		</tr>
	</table>	
</div>