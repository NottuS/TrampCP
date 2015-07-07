<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:if test="${!empty pagina.registros}">
	<div id="conteudo_corpo">		  
		<c:url var="var_ordenacao"	 	value="&ordenacao=${ordenacao}" />
		<c:url var="var_paginacao"	 	value="&indice=%pagina&totalRegistros=%total" />
				
		<c:url var="link_pesquisar" 	value="/vistoriaBemImovel.do?action=consultar" />		
		<c:url var="func_navegacao" 	value="JavaScript:submitAjax('${link_pesquisar}${var_ordenacao}${var_paginacao}', document.vistoriaBemImovelForm ,'pesquisaAjax',true);" />
		<c:url var="link_exibir" 		value="JavaScript:exibir(%1);" />
		<c:url var="link_alterar" 		value="JavaScript:alterar(%1);" />
		<c:url var="link_imprimir" 		value="JavaScript:imprimir(%1);" />
		<c:url var="link_excluir" 		value="JavaScript:excluir(%1);" />
		<c:url var="icon_exibir" 		value="/images/icon_exibir.png" scope="request" />
		<c:url var="icon_imprimir" 		value="/images/icon_print.png" scope="request" />

		<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
			<ch:lista bean="${pagina}" atributoId="codVistoria" />
			<ch:action imagem="${icon_exibir}" link="${link_exibir}" label="Exibir" width="3%" align="center" />
			<ch:campo atributo="bemImovel.instituicao.siglaDescricao" label="Instituição" align="left"/>
			<ch:campo atributo="bemImovel.nrBemImovel" label="Bem Imóvel" align="left"/>
			<ch:campo atributo="dataVistoria" label="Data da Vistoria" align="left" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" />
			<ch:campo atributo="statusVistoria.descricao" label="Situação" align="left" />		
			<ch:campo atributo="vistoriador.nome" label="Vistoriador" align="left" />					
			<ch:campo atributo="instanciaAtual" label="Alterar/Finalizar" align="center" width="3%" decorator="gov.pr.celepar.abi.util.BotaoAlterarVistoriaDecorator" />
			<ch:action imagem="${icon_imprimir}" link="${link_imprimir}" label="Imprimir" width="3%" align="center" />
			<ch:campo atributo="instanciaAtual" label="Excluir" align="center" width="3%" decorator="gov.pr.celepar.abi.util.BotaoExcluirVistoriaDecorator" />
			<ch:painel pagina="${func_navegacao}" classe="painel" atributoIndice="indice" />
		</ch:table>			  
	</div>
</c:if>
