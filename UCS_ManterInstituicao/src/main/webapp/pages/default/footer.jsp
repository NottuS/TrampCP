<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="icon_topo" value="/images/icon_topo.png" />
<c:url var="link_help" value="/help/indice.jsp" />
<c:url var="icon_ajuda" value="/images/icon_ajuda.png" />

<div id="barra_acao_bottom">
	<table align="right" cellspacing="0">
		<tr>
			<td><a href="javascript: window.scrollTo(0, 0); void 0">topo</a></td><td><a href="javascript: window.scrollTo(0, 0); void 0"><img src="${icon_topo}" border="0"></a></td>
			<td>&nbsp;</td>
			<td><a href="#" onClick="MM_openBrWindow('${link_help}','help','scrollbars=yes,resizable=yes,width=500,height=500')">ajuda</a></td><td><a href="#" onClick="MM_openBrWindow('${link_help}','help','scrollbars=yes,resizable=yes,width=500,height=500')"><img src="${icon_ajuda}" border="0"></a></td>
		</tr>
	</table>
</div>