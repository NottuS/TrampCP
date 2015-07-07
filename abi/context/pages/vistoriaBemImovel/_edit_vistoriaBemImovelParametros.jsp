<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:if test="${!empty listaParametroVistoria.registros}">				
    <ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
		<ch:lista bean="${listaParametroVistoria}" atributoId="codParametroVistoria" />
		<ch:campo atributo="instanciaAtual" label="Selecionar" align="center" decorator="gov.pr.celepar.abi.util.CheckBoxParametroVistoriaDecorator" />
		<ch:campo atributo="descricao" label="Descrição" align="left"/>
		<ch:campo atributo="instanciaAtual" label="Domínio" align="left" decorator="gov.pr.celepar.abi.util.ExibirDominioParametroVistoriaDecorator" />
	</ch:table>		
</c:if>

<c:if test="${empty listaParametroVistoria.registros}">
	Nenhum item para o Bem Imóvel.
</c:if>