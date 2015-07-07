<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<c:url var='icon_excluir' value='/images/icon_excluir.png' />

<c:if test="${!empty paginaDominio.registros}">
		<script>
			document.forms[0].dominioPreenchido.value = "true";
		</script>
		<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
			<ch:lista bean="${paginaDominio}" atributoId="descricao" />
			<ch:campo atributo="descricao" label="Domínio"align="left" />
			<ch:action imagem="${icon_excluir}" link="javascript:excluirDominio('%1')" label="Excluir" width="5%" align="center"/>
		</ch:table>
</c:if>
<c:if test="${empty paginaDominio.registros}">
	<script>
		document.forms[0].dominioPreenchido.value = "false";
	</script>
</c:if>
