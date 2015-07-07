<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:if test="${!empty listaItemVistoria.registros}">				
    <ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
		<ch:lista bean="${listaItemVistoria}" atributoId="codItemVistoria" />
		<ch:campo atributo="descricao" label="Descrição" align="left" width="45%" />
		<ch:campo atributo="instanciaAtual" label="Domínio" align="left" width="50%" decorator="gov.pr.celepar.abi.util.ExibirCampoItemVistoriaDecorator" />
		<ch:campo atributo="codItemVistoria" label="Excluir" align="center" width="5%" decorator="gov.pr.celepar.abi.util.ExcluirItemVistoriaDecorator" />
	</ch:table>		
</c:if>

<c:if test="${empty listaItemVistoria.registros}">
	Nenhum item para o Bem Imóvel.
</c:if> 