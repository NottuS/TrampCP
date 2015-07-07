<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:if test="${!empty paginaLocBemImovel.registros}">
	<div id="conteudo_corpo">
		
		<c:url var="icon_selecionar" 			value="/images/icon_selecionar.png"/>
		<c:url var="var_ordenacao"	 			value="&ordenacao=${ordenacao}" />
		<c:url var="var_paginacao"	 			value="&indice=%pagina&totalRegistros=%total" />
		
		<c:url var="link_selecionar" 			value="/localizarBemImovelSimplificado.do?action=finalizarProcesso&codigo=%1" />		
		<c:url var="link_pesquisar" 			value="/localizarBemImovelSimplificado.do?action=pesquisarBemImovel" />		
		<c:url var="func_navegacao" 		value="JavaScript:submitAjax('${link_pesquisar}${var_ordenacao}${var_paginacao}', document.localizarBemImovelSimplificadoForm ,'pesquisaAjax',true);" />
		
		<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
	    <ch:lista bean="${paginaLocBemImovel}" atributoId="codBemImovel, nrBemImovel, instituicao.codInstituicao" />
        <ch:campo atributo="nrBemImovel" label="Pasta" width="4%" />
       	<ch:campo atributo="instituicao.sigla" label="Instituição" width="4%" />
        <ch:campo atributo="classificacaoBemImovel.descricao" label="Classificação" width="6%"/>
        <ch:campo atributo="municipioUF" label="Município/UF<br>Logradouro" width="17%"/>
        <ch:campo atributo="descricaoOcupacaos" label="Ocupação" width="15%"/>
        <ch:campo atributo="areaTerreno" label="Área do Terreno(m2)" decorator="gov.pr.celepar.abi.util.Area" width="5%"/>
        <ch:campo atributo="observacoesMigracao" label="Observações" width="25%" />
        <ch:campo atributo="descricaoAdministracao" label="Administração" width="7%" />
        <ch:campo atributo="orgao.sigla" label="Órgão" width="15%" />
        <ch:campo atributo="codBemImovel" label="Cessões de Uso" decorator="gov.pr.celepar.abi.util.CessaoDeUsoDecorator" width="15%"/>
        <ch:campo atributo="codBemImovel" label="Doação" decorator="gov.pr.celepar.abi.util.DoacaoDecorator" width="15%"/>
        <ch:campo atributo="codBemImovel" label="Transferência" decorator="gov.pr.celepar.abi.util.TransferenciaDecorator" width="15%"/>
        <ch:action imagem="${icon_selecionar}" link="javascript:selecionar(%1, %2, %3)" label="Selecionar" width="3%" align="center"/>
        <ch:painel pagina="${func_navegacao}" classe="painel" atributoIndice="indice" />
		</ch:table>
	</div>
</c:if>
