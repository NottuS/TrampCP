<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:if test="${listAssinatura != null && listAssinatura.quantidade > 0}">
	<c:url var="icon_excluir" 				value="/images/icon_excluir.png"/>
	<c:url var="link_excluirAssinatura" 	value="JavaScript:excluirAssinaturaTransferencia(%1);" />
	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
		<ch:lista bean="${listAssinatura}"  atributoId="codAssinaturaTransferencia"/>
		<ch:campo atributo="assinatura.orgao.siglaDescricao" label="<center>Órgão</center>" align="center"/>
		<ch:campo atributo="assinatura.cargoAssinatura.descricao" label="<center>Cargo</center>" align="center"/>
		<ch:campo atributo="assinatura.nome" label="<center>Nome</center>" align="center"/>
		<ch:campo atributo="ordem" label="<center>Ordem</center>" align="center"/>
		<ch:action imagem="${icon_excluir}" link="${link_excluirAssinatura}" label="Excluir" width="3%" align="center" />
	</ch:table>
</c:if>
