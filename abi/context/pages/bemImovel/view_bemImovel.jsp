<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<fmt:setLocale value="pt_BR" scope="application"/>

<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_data" value="/js/generic/data.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var='icon_info' value='/images/icon_info.png' />
<c:url var='icon_mais' value='/images/icon_mais.png' />
<c:url var='icon_menos' value='/images/icon_menos.png' />
<c:url var='icon_exibir' value='/images/icon_exibir.png' />
<c:url var='icon_lupa_planeta' value='/images/icon_lupa_planeta.png' />

<c:url var='link_pesquisar' value='/bemImovel.do?action=carregarPgListBemImovel' />
<!-- <c:url var='link_visualizar_documento' value='/documentacao.do?action=carregarAnexoDocumentacao&codDocumentacao=' /> -->
<c:url var='link_visualizar_documento' value='/bemImovel.do?action=carregarAnexoDocumentacao&codDocumentacao=' />
<c:url var='link_view_edificacao' value='/bemImovel.do?action=carregarPgViewEdificacao&codEdificacao=' />
<c:url var='sem_info' value=' - ' />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>



<script language="javascript">

	function excluir(){
		
		if(confirm('Tem certeza que deseja excluir?')) {

			document.bemImovelForm.submit();
		}
		
	}

	function VerNoMapa(){
		
			var endereco = '${bemImovel.logradouro}';
			endereco=endereco+", "+'${bemImovel.numero}';
			endereco=endereco+", "+"${bemImovel.municipio}";
			endereco=endereco+", "+'${bemImovel.uf}'+", brasil";
			endereco=endereco.replace(/[ ]/g,"+");
			var url="http://maps.google.com/maps?f=q&source=s_q&hl=en&geocode=&q="+endereco+"&sll=37.0625,-95.677068&sspn=0.00863,0.015814&ie=ISO88591&hq=&hnear="+endereco+"&z=16";
			window_open(url,"mapa","");
		
	}
	function window_open(theURL,winName,features) {
		  winName = window.open(theURL,winName,features);
		  winName.window.focus();
	}

	function validarCampos(){
	
		var form = document.bemImovelForm;

		form.municipio.value = form.codMunicipio.options[form.codMunicipio.selectedIndex].text;
	
		form.submit();
	}

	function baixar(){
		
		var form = document.bemImovelForm;
		var codBemImovel = document.bemImovelForm.codBemImovel.value;
		
		if(confirm('Deseja realmente baixar?')) {
			document.bemImovelForm.submit();
		}
		form.submit();
	}

	function voltar(){
		document.bemImovelForm.action="${link_pesquisar}";
		document.bemImovelForm.submit();
	}
	

	function mostrarSecao(secao){
        document.getElementById(secao+"_menos").style.display="";
        document.getElementById(secao+"_mais").style.display="none";

    }
    function ocultarSecao(secao){
        document.getElementById(secao+"_menos").style.display="none";
        document.getElementById(secao+"_mais").style.display="";       
    }

    
	function exibirDocumentacao(codigo){
		if (codigo==undefined){
			alert("Não existe anexo para essa documentação!");
		}
		else
		{
			var form = document.bemImovelForm;
			form.action="${link_visualizar_documento}"+codigo;
			form.submit();
		}
		
	}
</script>


<c:choose> 
	<c:when test='${bemImovelForm.actionType == "excluir"}'>
		<html:hidden property="codBemImovel" value="${bemImovelForm.codBemImovel}"/>
		<c:set var="acao" value="bemImovel.do?action=excluirBemImovel&administracao=${bemImovelForm.administracao}&codBemImovel=${bemImovelForm.codBemImovel}"></c:set>
		<c:set var="titulo" value="Excluir"></c:set>
		<c:set var="disable" value="false"></c:set>
		<c:set var="funcao" value="excluir();"></c:set>
	</c:when>
	<c:when test='${bemImovelForm.actionType == "baixar"}'>
		<html:hidden property="codBemImovel" value="${bemImovelForm.codBemImovel}"/>
		<c:set var="acao" value="bemImovel.do?action=baixarBemImovel&administracao=${bemImovelForm.administracao}&codBemImovel=${bemImovelForm.codBemImovel}"></c:set>
		<c:set var="titulo" value="Baixar"></c:set>
		<c:set var="disable" value="false"></c:set>
		<c:set var="funcao" value="baixar();"></c:set>
	</c:when>
	<c:otherwise>
		<html:hidden property="codBemImovel" value="${bemImovelForm.codBemImovel}"/>
		<c:set var="acao" value="bemImovel.do?action=alterarBemImovel"></c:set>
		<c:set var="titulo" value="Exibir"></c:set>
		<c:set var="disable" value="true"></c:set>
	</c:otherwise>
</c:choose>

<div id="conteudo">
	<div align="center">
	<h1>${titulo} Bem Imóvel</h1>

	<html:form action="${acao}" onsubmit="return validarCampos()">
		
	<html:hidden property="actionType" value="${bemImovelForm.actionType}" />
	<!-- campos da tela de pesquisa -->
	<html:hidden property="conCodBemImovel"/>
	<html:hidden property="conNrBemImovel"/>
	<html:hidden property="conInstituicao"/>
	<html:hidden property="conAdministracao"/>
	<html:hidden property="conOrgao"/>
	<html:hidden property="conOrgaoResp"/>
	<html:hidden property="conClassificacaoBemImovel"/>
	<html:hidden property="conNirf"/>
	<html:hidden property="conNiif"/>
	<html:hidden property="conIncra"/>
	<html:hidden property="conMunicipio"/>
	<html:hidden property="conLogradouro"/>
	<html:hidden property="conBairroDistrito"/>
	<html:hidden property="conSituacaoLegalCartorial"/>
	<html:hidden property="conCartorio"/>
	<html:hidden property="conNumeroDocumentoCartorial"/>
	<html:hidden property="conTabelionato"/>
	<html:hidden property="conNumeroDocumentoTabelional"/>
	<html:hidden property="conFormaIncorporacao"/>
	<html:hidden property="conAreaTerrenoIni"/>
	<html:hidden property="conAreaTerrenoFim"/>
	<html:hidden property="conTipoConstrucao"/>
	<html:hidden property="conTipoEdificacao"/>
	<html:hidden property="conTipoDocumentacao"/>
	<html:hidden property="conSituacaoOcupacao"/>
	<html:hidden property="conLote"/>
	<html:hidden property="conQuadra"/>
	<html:hidden property="conDenominacaoImovel"/>
	<html:hidden property="conAverbado"/>
	<html:hidden property="conSituacaoImovel"/>
	<html:hidden property="conOcupante"/>
	<html:hidden property="codBemImovel" value="${bemImovel.codBemImovel}"/>
	<html:hidden property="nrBemImovel" value="${bemImovel.nrBemImovel}"/>
	<html:hidden property="instituicao" value="${bemImovel.instituicao}"/>

     <div id="conteudo_corpo" style="padding: 4px">	
	 <table class="form_tabela" cellspacing="0" width="100%" style="">
		<tr>
			<td style="text-align: center;">
				<span class="form_label_infBI">Identificação do Imóvel CPE: </span>
				<c:out value="${bemImovel.nrBemImovel}"></c:out>
			</td>
		</tr>

		<tr>
			<td style="text-align: center;">
				<span class="form_label_infBI">Administração: </span>
				<c:out value="${bemImovel.descricaoAdministracao}"></c:out>
			</td>
		</tr>
	 </table>
   </div>

    <a name="abas"></a> 

    <div id="conteudo_abas">
	 <h2>Identificação do Imóvel</h2>
	 <table class="form_tabela" cellspacing="0" >
		<tr>
			<td class="form_label" width="280">Instituição: </td>
			<td>
				<c:out value="${bemImovel.instituicao.siglaDescricao}"></c:out>
			</td>
		</tr>
 		<tr>
			<td class="form_label" width="280">Identificação do Imóvel CPE: </td>
			<td>
				<c:out value="${bemImovel.nrBemImovel}"></c:out>
			</td>
		</tr>
	 	<tr>
	 		<td class="form_label">Administração: </td>
	 		<td>
	 			<c:out value="${bemImovel.descricaoAdministracao}"></c:out>
	 		</td>
		</tr>
		<tr>
		 	<td class="form_label">Órgão Proprietário: </td>
		 	<td>
		 	
		 	<c:choose>
		 		<c:when test="${bemImovel.orgao.descricao == null}">
					${sem_info}
		 		</c:when>
		 		<c:otherwise>
					<c:out value="${bemImovel.orgao.descricao}"></c:out>
		 		</c:otherwise>
		 	</c:choose>
		 	</td>
		</tr>
		<tr>
		 	<td class="form_label">Classificação do Imóvel: </td>
		 	<td>
				<c:choose>
			 		<c:when test="${bemImovel.classificacaoBemImovel.descricao == null || bemImovel.classificacaoBemImovel.descricao == ''}">
						${sem_info}
			 		</c:when>
			 		<c:otherwise>
						<c:out value="${bemImovel.classificacaoBemImovel.descricao}"></c:out>
			 		</c:otherwise>
			 	</c:choose>
		 	</td>
		 </tr>
	
	 	<tr>
	 		<td class="form_label">Situação de Localização: </td>
	 		<td>
				<c:choose>
			 		<c:when test="${bemImovel.situacaoLocal == null || bemImovel.situacaoLocal == ''}">
						${sem_info}
			 		</c:when>
			 		<c:otherwise>
			 			<c:out value="${bemImovel.descricaoSituacaoLocal}"></c:out>
			 		</c:otherwise>
			 	</c:choose>			
			</td>
		</tr>

		<tr>
		 	<td class="form_label">Situação Legal - Cartorial: </td>
		 	<td>
				<c:choose>
			 		<c:when test="${bemImovel.situacaoLegalCartorial.descricao == null || bemImovel.situacaoLegalCartorial.descricao == ''}">
						${sem_info}
			 		</c:when>
			 		<c:otherwise>
			 			<c:out value="${bemImovel.situacaoLegalCartorial.descricao}"></c:out>
			 		</c:otherwise>
			 	</c:choose>			
		 	</td>
		</tr>
		 
		 
		<tr>
			<td class="form_label">N° do Processo SPI: </td>
			<td>
				<c:choose>
			 		<c:when test="${bemImovel.numeroProcessoSpi == null || bemImovel.numeroProcessoSpi == ''}">
						${sem_info}
			 		</c:when>
			 		<c:otherwise>
			 			<c:out value="${bemImovel.numeroProcessoSpi}"></c:out>
			 		</c:otherwise>
			 	</c:choose>			
			</td>
		</tr>

		<tr>
			<td class="form_label">CEP: </td>
			<td>
			 	<c:choose>
			 		<c:when test="${bemImovel.cep == null || bemImovel.cep == ''}">
						${sem_info}
			 		</c:when>
			 		<c:otherwise>
			 			<c:out value="${bemImovel.cep}"></c:out>
			 		</c:otherwise>
			 	</c:choose>		 		
			</td>
		</tr>

		<tr>
			<td class="form_label">Estado/Município: </td>
			<td>

			 	<c:choose>
			 		<c:when test="${bemImovel.uf == null}">
						${sem_info}
			 		</c:when>
			 		<c:otherwise>
						<c:out value="${bemImovel.municipio}/${bemImovel.uf}"></c:out>
			 		</c:otherwise>
			 	</c:choose>		 		
				
			</td>
			
		</tr>
		<tr>
			<td class="form_label">Bairro/Distrito: </td>
			<td>
				<c:choose>
			 		<c:when test="${bemImovel.bairroDistrito == null}">
						${sem_info}
			 		</c:when>
			 		<c:otherwise>
						<c:out value="${bemImovel.bairroDistrito}"></c:out>
			 		</c:otherwise>
			 	</c:choose>			
			</td>
		</tr>
	
		<tr>
			<td class="form_label">Forma de Incorporação: </td>
			<td>
				<c:choose>
			 		<c:when test="${bemImovel.formaIncorporacao.descricao == null || bemImovel.formaIncorporacao.descricao == ''}">
						${sem_info}
			 		</c:when>
			 		<c:otherwise>
						<c:out value="${bemImovel.formaIncorporacao.descricao}"></c:out>			 		
			 		</c:otherwise>
			 	</c:choose>	
			</td>				
		</tr>
		<tr> 
          <td class="form_label">Data da Incorporação: </td>
          <td>
				<c:choose>
			 		<c:when test="${bemImovel.dataIncorporacao == null || bemImovel.dataIncorporacao == ''}">
						${sem_info}
			 		</c:when>
			 		<c:otherwise>
          				<fmt:formatDate value="${bemImovel.dataIncorporacao}" type="both" pattern="dd/MM/yyyy"/>			 		
			 		</c:otherwise>
			 	</c:choose>	
          </td>
          
	   </tr>
	   <tr>
			<td class="form_label">Situação do Imóvel:</td>
			<td>
				<c:choose>
			 		<c:when test="${bemImovel.situacaoImovel.descricao == null || bemImovel.situacaoImovel.descricao == ''}">
						${sem_info}
			 		</c:when>
			 		<c:otherwise>
						<c:out value="${bemImovel.situacaoImovel.descricao}"></c:out>			 		
			 		</c:otherwise>
			 	</c:choose>	
			</td>				
		</tr>
		<tr>
			<td class="form_label">Observações da Migração: </td>
			<td>
				<c:choose>
			 		<c:when test="${bemImovel.observacoesMigracao == null || bemImovel.observacoesMigracao == '' }">
						${sem_info}
			 		</c:when>
			 		<c:otherwise>
						<c:out value="${bemImovel.observacoesMigracao}"></c:out>			 		
			 		</c:otherwise>
			 	</c:choose>
			</td>
		</tr>

		</table>
		
		<h2>Terreno</h2>
		<table class="form_tabela" cellspacing="0" >
		<tr>
			<td class="form_label" width="280">Logradouro: </td>
			<td>
				<c:choose>
			 		<c:when test="${bemImovel.logradouro == null}">
						${sem_info}
			 		</c:when>
			 		<c:otherwise>
						<c:out value="${bemImovel.logradouro}"></c:out>	
						<td>
			 				<a href="javascript:VerNoMapa()">&nbsp;&nbsp;<img src="${icon_lupa_planeta}" width="16" height="16" border="0" >&nbsp; Ver no mapa</a>
						</td>		 		
			 		</c:otherwise>
			 	</c:choose>
			</td>
		</tr>
		
		<tr>
			<td class="form_label">Número: </td>
			<td>
				<c:choose>
			 		<c:when test="${bemImovel.numero == null}">
						${sem_info}
			 		</c:when>
			 		<c:otherwise>
						<c:out value="${bemImovel.numero}"></c:out>			 		
			 		</c:otherwise>
			 	</c:choose>
			</td>
		</tr>
		<tr>
			<td class="form_label">Complemento: </td>
			<td>
				<c:choose>
			 		<c:when test="${bemImovel.complemento == null}">
						${sem_info}
			 		</c:when>
			 		<c:otherwise>
						<c:out value="${bemImovel.complemento}"></c:out>			 		
			 		</c:otherwise>
			 	</c:choose>
			</td>		
		</tr>
		<tr>
			<td class="form_label">Denominação: </td>
			<td>
				<c:choose>
			 		<c:when test="${bemImovel.denominacaoImovel.descricao == null}">
						${sem_info}
			 		</c:when>
			 		<c:otherwise>
						<c:out value="${bemImovel.denominacaoImovel.descricao}"></c:out>			 		
			 		</c:otherwise>
			 	</c:choose>
			</td>
		</tr>
		
		<tr>
			<td class="form_label">Área do Terreno (m²): </td>
			<td>
				<c:choose>
			 		<c:when test="${bemImovel.areaTerreno == null}">
						${sem_info}
			 		</c:when>
			 		<c:otherwise>
						<fmt:formatNumber value="${bemImovel.areaTerreno}" minFractionDigits="2"/>			 		
			 		</c:otherwise>
			 	</c:choose>
			

			</td>
		</tr>
	
		<tr>
			<td class="form_label">Somente terreno: </td>
			<td>
				<c:choose>
			 		<c:when test="${bemImovel.somenteTerreno == 'S'}">
						Sim
			 		</c:when>
			 		<c:otherwise>
						Não			 		
			 		</c:otherwise>
			 	</c:choose>
			</td>
		</tr>

		<c:if test='${bemImovelForm.actionType == "alterar"}'>
			<tr>
				<td class="form_label">Construída (m²): </td>
				<td>
					<c:out value="${bemImovelForm.areaConstruida}"></c:out>
				</td>
				<td class="form_label">Disponível (m²): </td>
				<td>
					<c:out value="${bemImovelForm.areaDispoNivel}"></c:out>
				</td>
			</tr>
		</c:if>
			

	</table>
	
	
	<c:choose>
	 	<c:when test="${!empty listOcupOrgaoResponsavel.registros}">
	  	  	<div id="listOcupOrgaoResponsavel_mais">
	  	  		<h2><img src="${icon_mais}" onclick="javascript:mostrarSecao('listOcupOrgaoResponsavel')"> Responsável pelo Terreno</h2>
		  	</div>
	  	  	<div id="listOcupOrgaoResponsavel_menos" style="display: none">
	  	  		<h2><img src="${icon_menos}" onclick="javascript:ocultarSecao('listOcupOrgaoResponsavel')"> Responsável pelo Terreno</h2>
			  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao" >
					<ch:lista bean="${listOcupOrgaoResponsavel}"  atributoId="codOcupacao"/>
					<ch:campo atributo="orgao" label="<center>Órgão Responsável</center>" align="center"/>
					<ch:campo atributo="descricao" label="<center>Descrição</center>" align="center"/>
			        <ch:campo atributo="ocupacaoMetroQuadrado" label="Ocupação em m²" decorator="gov.pr.celepar.abi.util.Area" />
			        <ch:campo atributo="ocupacaoPercentual" label="Ocupação em %" decorator="gov.pr.celepar.abi.util.Area"/>
			  	</ch:table>
		  	</div>
		  	
		</c:when>
		
		<c:otherwise>
	  	  	<div>
  		  		<h2><img src="${icon_menos}"> Responsável pelo Terreno</h2>
	  		</div>
	  		<i>
	  		<c:out value="Sem informação"></c:out>
	  		</i>		
 		</c:otherwise>
 	</c:choose>

	<c:choose>		
	 	<c:when test="${leiBemImovels.registros!=null && !empty leiBemImovels.registros}">		
		
  	  	<div id="leiBemImovel_mais">
  	  		<h2><img src="${icon_mais}" onclick="javascript:mostrarSecao('leiBemImovel')"> Leis e Decretos</h2>
	  	</div>
  	  	<div id="leiBemImovel_menos" style="display: none">
  	  		<h2><img src="${icon_menos}" onclick="javascript:ocultarSecao('leiBemImovel')"> Leis e Decretos </h2>
		  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao" >
			    <ch:lista bean="${leiBemImovels}" atributoId="dataAssinatura" />
		        <ch:campo atributo="tipoLei" label="Tipo" />
		        <ch:campo atributo="numero" label="Número" />
		        <ch:campo atributo="dataAssinatura" label="Data de Assinatura" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data"/>
		        <ch:campo atributo="dataPublicacao" label="Data de Publicação"  decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data"/>
		  	</ch:table>  
	  	</div>
		</c:when>
		
		<c:otherwise>
	  	  	<div id="leiBemImovel_sem_info">
  		  		<h2><img src="${icon_menos}"> Leis e Decretos</h2>
	  		</div>
	  		<i>
	  		<c:out value="Sem informação"></c:out>
	  		</i>		
	  		
 		</c:otherwise>
 		
 	</c:choose>
			
	<c:choose>		
	 	<c:when test="${!empty lotes.registros}">		
  	  	

  	  	<div id="lotes_mais">
  	  		<h2><img src="${icon_mais}" onclick="javascript:mostrarSecao('lotes')"> Lotes e Quadras</h2>
	  	</div>
  	  	<div id="lotes_menos" style="display: none">
  	  		<h2><img src="${icon_menos}" onclick="javascript:ocultarSecao('lotes')"> Lotes e Quadras</h2>
		  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao" >
			    <ch:lista bean="${lotes}" atributoId="codLote" />
		        <ch:campo atributo="lote" label="Lote"/>
		        <ch:campo atributo="quadra" label="Quadra"/>
		  	</ch:table>  
	  	</div>
	  	
		</c:when>
		
		<c:otherwise>
	  	  	<div id="leiBemImovel_sem_info">
  		  		<h2><img src="${icon_menos}"> Lotes e Quadras</h2>
	  		</div>
	  		<i>
	  		<c:out value="Sem informação"></c:out>
	  		
	  		</i>		
	  		
 		</c:otherwise>
 	</c:choose>
 	
  		
	<c:choose>		
	 	<c:when test="${!empty confrontantes.registros}">		
  	  	<div id="confrontantes_mais">
  	  		<h2><img src="${icon_mais}" onclick="javascript:mostrarSecao('confrontantes')"> Confrontantes</h2>
	  	</div>
  	  	<div id="confrontantes_menos" style="display: none">
  	  		<h2><img src="${icon_menos}" onclick="javascript:ocultarSecao('confrontantes')"> Confrontantes</h2>
		  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao" >
			    <ch:lista bean="${confrontantes}" atributoId="descricao" />
		        <ch:campo atributo="descricao" label="Confrontante"/>		        
		  	</ch:table>  
	  	</div>
		</c:when>
		
		<c:otherwise>
	  	  	<div id="leiBemImovel_sem_info">
  		  		<h2><img src="${icon_menos}"> Confrontantes</h2>
	  		</div>
	  		<i>
	  		<c:out value="Sem informação"></c:out>
	  		</i>		
 		</c:otherwise>
 	</c:choose>

	<c:choose>		
	 	<c:when test="${!empty avaliacaos.registros}">		

  	  	<div id="avaliacaos_mais">
  	  		<h2><img src="${icon_mais}" onclick="javascript:mostrarSecao('avaliacaos')"> Avaliações</h2>
	  	</div>
  	  	<div id="avaliacaos_menos" style="display: none">
  	  		<h2><img src="${icon_menos}" onclick="javascript:ocultarSecao('avaliacaos')"> Avaliações</h2>
		  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao" >
			    <ch:lista bean="${avaliacaos}" atributoId="codAvaliacao"  />
		        <ch:campo atributo="dataAvaliacao" label="Data da Avaliação" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data"/>		        
		        <ch:campo atributo="valor" label="Avaliação do Imóvel (R$)" align="right" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Moeda"/>
		        <ch:campo atributo="indTipoAvaliacao" label="Tipo de Avaliacao" decorator="gov.pr.celepar.abi.util.AvaliacaoImovelDecor"/>
		        
		  	</ch:table>  
	  	</div>
		</c:when>
		
		<c:otherwise>
	  	  	<div id="leiBemImovel_sem_info">
  		  		<h2><img src="${icon_menos}"> Confrontantes</h2>
	  		</div>
	  		<i>
	  		<c:out value="Sem informação"></c:out>
	  		</i>		
 		</c:otherwise>
 	</c:choose>
  		
  		
	<c:choose>		
	 	<c:when test="${!empty coordenadaUtms.registros}">		
  		
  	  	<div id="coordenadaUtms_mais">
  	  		<h2><img src="${icon_mais}" onclick="javascript:mostrarSecao('coordenadaUtms')"> Coordenadas UTM</h2>
	  	</div>
  	  	<div id="coordenadaUtms_menos" style="display: none">
  	  		<h2><img src="${icon_menos}" onclick="javascript:ocultarSecao('coordenadaUtms')"> Coordenadas UTM</h2>
		  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao" >
			    <ch:lista bean="${coordenadaUtms}" atributoId="coordenadaX"  />
		        <ch:campo atributo="coordenadaX" label="Coordenada X"/>		        
		        <ch:campo atributo="coordenadaY" label="Coordenada Y"/>
		  	</ch:table>  
	  	</div>
	  	
		</c:when>
		
		<c:otherwise>
	  	  	<div>
  		  		<h2><img src="${icon_menos}"> Coordenadas UTM</h2>
	  		</div>
	  		<i>
	  		<c:out value="Sem informação"></c:out>
	  		</i>		
 		</c:otherwise>
 	</c:choose>	
  		
	<c:choose>		
	 	<c:when test="${!empty edificacaoOcupacaos}">
  	  	<div id="edificacaoOcupacaos_mais">
  	  		<h2><img src="${icon_mais}" onclick="javascript:mostrarSecao('edificacaoOcupacaos')"> Edificações e Ocupações</h2>
	  	</div>
  	  	<div id="edificacaoOcupacaos_menos" style="display: none">
  	  		<h2><img src="${icon_menos}" onclick="javascript:ocultarSecao('edificacaoOcupacaos')"> Edificações e Ocupações</h2>
			
	 					 
			 <c:forEach var="item" items="${edificacaoOcupacaos}">
			 
			 <h3><strong>Edificação:</strong> ${item.especificacao}</h3>
			<table cellspacing="0" width="100%" style="" class="lista_tabela">
				
				
				<tr>
					<th>Tipo de Construção</th>
					<th>Tipo de Edificação</th>
					<th>Especificação</th>
					<th>Área</th>

				</tr>
			 	
			 	<tr>
			 		<td>${item.tipoConstrucao}</td>	 	
			 		<td>${item.tipoEdificacao}</td>
					<td>${item.especificacao}</td>
					<td><fmt:formatNumber value="${item.areaConstruida}" type="number" minFractionDigits="2"/> </td>
				</tr>
				
  	  			<tr><td colspan="4">
			  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao" >
				    <ch:lista bean="${item.paginaOcupacao}" atributoId="codOcupacao"/>
			        <ch:campo atributo="orgaoSiglaDescricao" label="Órgão"/>
			        <ch:campo atributo="descricao" label="Descrição"/>		        
			        <ch:campo atributo="ocupacaoMetroQuadrado" label="Ocupação em m²" align="right" decorator="gov.pr.celepar.abi.util.Area"/>
			        <ch:campo atributo="ocupacaoPercentual" label="Ocupação em %"  align="right" decorator="gov.pr.celepar.abi.util.Area"/>
			        <ch:campo atributo="outrasInformacoes" label="Outras Informações"/>
			  	</ch:table>  
  	  			</td></tr>
		</table>  	  			
  	  		</c:forEach>
  	  		
			 
			

	  	</div>

	 	
		</c:when>
		<c:otherwise>
	  	  	<div>
  		  		<h2><img src="${icon_menos}"> Edificações e Ocupações</h2>
	  		</div>
	  		<i>
	  		<c:out value="Sem informação"></c:out>
	  		</i>		
		
 		</c:otherwise>
 	</c:choose>
  

 	
	<c:choose>
	 	<c:when test="${!empty documentacaoNotificacaos.registros}">
  	  	<div id="docNotificacaos_mais">
  	  		<h2><img src="${icon_mais}" onclick="javascript:mostrarSecao('docNotificacaos')"> Documentação com Notificação</h2>
	  	</div>
  	  	<div id="docNotificacaos_menos" style="display: none">
  	  		<h2><img src="${icon_menos}" onclick="javascript:ocultarSecao('docNotificacaos')"> Documentação com Notificação</h2>
		  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao" >
			    <ch:lista bean="${documentacaoNotificacaos}" atributoId="codDocumentacao"  />
		        <ch:campo atributo="instanciaAtual" label="Visualizar Anexo" align="center" decorator="gov.pr.celepar.abi.util.DocumentacaoDecorator" width="3%"/>        
		        <ch:campo atributo="descricaoFormatada" label="Descrição" width="20%"/>
		        <ch:campo atributo="detalhes" label="Detalhes"/>	     
		        <ch:campo atributo="tsInclusao" label="Data de Inclusão" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" width="7%"/>
		        <ch:campo atributo="dataNotificacao" label="Data Notificação" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" width="7%"/>
		        <ch:campo atributo="prazoNotificacao" label="Prazo Notificação" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" width="7%"/>
		        <ch:campo atributo="dataSolucao" label="Data Solução" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" width="7%"/>
		        <ch:campo atributo="responsavelDocumentacao" label="Responsável última alteração" width="15%"/>
		  	</ch:table>
	  	</div>
	  	</c:when>
		<c:otherwise>
	  	  	<div>
  		  		<h2><img src="${icon_menos}"> Documentações com Notificação</h2>
	  		</div>
	  		<i>
	  		<c:out value="Sem informação"></c:out>
	  		</i>		
 		</c:otherwise>
 	</c:choose>
	  	
	<c:choose>
	 	<c:when test="${!empty documentacaoSemNotificacaos.registros}">
  	  	<div id="docSemNotificacaos_mais">
  	  		<h2><img src="${icon_mais}" onclick="javascript:mostrarSecao('docSemNotificacaos')"> Documentações - Outras</h2>
	  	</div>
  	  	<div id="docSemNotificacaos_menos" style="display: none">
  	  		<h2><img src="${icon_menos}" onclick="javascript:ocultarSecao('docSemNotificacaos')"> Documentações - Outras</h2>
		  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao" >
			    <ch:lista bean="${documentacaoSemNotificacaos}" atributoId="codDocumentacao"  />
		        <ch:campo atributo="instanciaAtual" label="Visualizar Documento"  align="center" decorator="gov.pr.celepar.abi.util.DocumentacaoDecorator" width="3%"/>
		        <ch:campo atributo="descricaoFormatada" label="Descrição" width="20%"/>	
		        <ch:campo atributo="detalhes" label="Detalhes" width="30%"/>     
		        <ch:campo atributo="tsInclusao" label="Data de Inclusão" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" width="8%" />
		        <ch:campo atributo="tsAtualizacao" label="Data de Alteração" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" width="8%"/>
		        <ch:campo atributo="responsavelDocumentacao" label="Responsável pela última alteração" width="15%"/>
		  	</ch:table>
	  	</div>
		</c:when>
		
		<c:otherwise>
	  	  	<div>
  		  		<h2><img src="${icon_menos}"> Documentações - Outras</h2>
	  		</div>
	  		<i>
	  		<c:out value="Sem informação"></c:out>
	  		</i>		
 		</c:otherwise>
 	</c:choose>
  		
	<c:choose>
	 	<c:when test="${!empty ocorrenciaDocumentacaos.registros}">
	  	  	<div id="ocorrenciaDocs_mais">
	  	  		<h2><img src="${icon_mais}" onclick="javascript:mostrarSecao('ocorrenciaDocs')"> Ocorrências de Movimentação</h2>
		  	</div>
	  	  	<div id="ocorrenciaDocs_menos" style="display: none">
	  	  		<h2><img src="${icon_menos}" onclick="javascript:ocultarSecao('ocorrenciaDocs')"> Ocorrências de Movimentação</h2>
			  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao" >
				    <ch:lista bean="${ocorrenciaDocumentacaos}" atributoId="codOcorrenciaDocumentacao"  />
			        <ch:campo atributo="instanciaAtual" label="Visualizar Anexo" align="center" decorator="gov.pr.celepar.abi.util.DocumentacaoDecorator"/>
			        <ch:campo atributo="descricao" label="Descrição Ocorrência"/>	
			        <ch:campo atributo="descricaoFormatada" label="Descrição Documentação"/>
			         <ch:campo atributo="detalhes" label="Detalhes Documentação"/>		        
			        <ch:campo atributo="tsInclusao" label="Data de Inclusão" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data"/>			        
			  	</ch:table>
		  	</div>
		  	
		</c:when>
		
		<c:otherwise>
	  	  	<div>
  		  		<h2><img src="${icon_menos}"> Ocorrências de Movimentação</h2>
	  		</div>
	  		<i>
	  		<c:out value="Sem informação"></c:out>
	  		</i>		
 		</c:otherwise>
 	</c:choose>
  			  	  		
  	
  		  			
   	<hr>
  	
 	<div align="center">
		<c:if test='${E && bemImovelForm.actionType == "excluir"}'>
			<html:button property="btnAcao" styleClass="form_botao" value="${titulo}" disabled="${disable}" onclick="${funcao}"/>
		</c:if> 
		<c:if test='${B && bemImovelForm.actionType == "baixar"}'>
			<html:button property="btnAcao" styleClass="form_botao" value="${titulo}" disabled="${disable}" onclick="${funcao}"/>
		</c:if> 
		<html:button property="btnVoltar" styleClass="form_botao" value="Voltar" onclick="javascript:history.back();" />
	</div>	
	
  </div>
	
  
  </html:form>

  </div>
</div>
