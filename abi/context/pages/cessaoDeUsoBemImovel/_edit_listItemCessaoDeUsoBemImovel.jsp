<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:if test="${listItemCessaoDeUso != null && listItemCessaoDeUso.quantidade > 0}">
	<c:url var="icon_excluir" 				value="/images/icon_excluir.png"/>
	<c:url var="link_excluirItemCessaoDeUso" 	value="JavaScript:excluirItemCessaoDeUso(%1);" />
	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
		<ch:lista bean="${listItemCessaoDeUso}"  atributoId="codItemCessaoDeUso"/>
		<ch:campo atributo="tipoDescricao" label="<center>Tipo</center>" align="center"/>
		<ch:campo atributo="caracteristica" label="<center>Característica</center>" align="center"/>
		<ch:campo atributo="situacaoDominial" label="<center>Situação Dominial</center>" align="center"/>
		<ch:campo atributo="utilizacao" label="<center>Utilização</center>" align="center"/>
        <ch:campo atributo="areaMetroQuadrado" label="Cessão de Uso em m²" decorator="gov.pr.celepar.abi.util.Area" />
        <ch:campo atributo="areaPercentualFormatado" label="Cessão de Uso em %"/>
		<ch:campo atributo="observacao" label="<center>Observação</center>" align="center"/>
		<ch:campo atributo="outrasInformacoes" label="<center>Outras Informações</center>" align="center"/>
		<ch:action imagem="${icon_excluir}" link="${link_excluirItemCessaoDeUso}" label="Excluir" width="3%" align="center" />
	</ch:table>
</c:if>
