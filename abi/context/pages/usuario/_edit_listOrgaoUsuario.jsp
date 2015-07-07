<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:if test="${listOrgaos != null && listOrgaos.totalRegistros > 0}">
	<c:url var="icon_excluir" 				value="/images/icon_excluir.png"/>
	<c:url var="link_excluirOrgao" 	value="JavaScript:excluirOrgao(%1);" />
	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
		<ch:lista bean="${listOrgaos}"  atributoId="orgao.codOrgao"/>
		<ch:campo atributo="orgao.siglaDescricao" label="<center>Descrição</center>" align="center"/>
		<c:if test="${manterUsuarioForm.desabilitaOrgao == 'false'}">
			<ch:action imagem="${icon_excluir}" link="${link_excluirOrgao}" label="Excluir" width="3%" align="center" />
		</c:if>
	</ch:table>
</c:if>
