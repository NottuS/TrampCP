<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:if test="${listItemDoacao != null && listItemDoacao.quantidade > 0}">
	<c:url var="icon_excluir" 				value="/images/icon_excluir.png"/>
	<c:url var="link_excluirItemDoacao" 	value="JavaScript:excluirItemDoacao(%1);" />
	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
		<ch:lista bean="${listItemDoacao}"  atributoId="codItemDoacao"/>
		<ch:campo atributo="tipoDescricao" label="<center>Tipo</center>" align="center"/>
		<ch:campo atributo="utilizacao" label="<center>Utilização</center>" align="center"/>
        <ch:campo atributo="doacaoMetros" label="Doação em m²" decorator="gov.pr.celepar.abi.util.Area" />
        <ch:campo atributo="doacaoPercentualFormatado" label="Doação em %"/>
		<ch:campo atributo="observacao" label="<center>Observação</center>" align="center"/>
		<ch:campo atributo="outrasInformacoes" label="<center>Outras Informações</center>" align="center"/>
		<ch:action imagem="${icon_excluir}" link="${link_excluirItemDoacao}" label="Excluir" width="3%" align="center" />
	</ch:table>
</c:if>
