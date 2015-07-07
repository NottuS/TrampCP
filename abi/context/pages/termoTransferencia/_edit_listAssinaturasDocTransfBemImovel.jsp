<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:if test="${listAssinaturaDoc != null && listAssinaturaDoc.quantidade > 0}">
	<c:url var="icon_excluir" 				value="/images/icon_excluir.png"/>
	<c:url var="link_excluirAssinaturaDoc" 	value="JavaScript:excluirAssinatura(%1);" />
	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
		<ch:lista bean="${listAssinaturaDoc}"  atributoId="codigo" />
		<ch:campo atributo="orgao" label="<center>Órgão</center>" align="center"/>
		<ch:campo atributo="cargo" label="<center>Cargo</center>" align="center"/>
		<ch:campo atributo="nome" label="<center>Nome</center>" align="center"/>
		<ch:campo atributo="respMaximo" label="<center>Responsável Máximo</center>" align="center"/>
		<ch:action imagem="${icon_excluir}" link="${link_excluirAssinaturaDoc}" label="Excluir" width="3%" align="center" />
	</ch:table>
</c:if>
