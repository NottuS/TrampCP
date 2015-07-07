<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<c:url var='icon_excluir' value='/images/icon_excluir.png' />

<c:if test="${!empty pagina.registros}">
		<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
			<ch:lista bean="${pagina}" atributoId="email" />
			<ch:campo atributo="email" label="E-mail"align="left" />
			<ch:action imagem="${icon_excluir}" link="javascript:excluirEmail('%1')" label="Excluir" width="5%" align="center"/>
		</ch:table>
</c:if>
