<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='icon_apagar' value='/images/icon_apagar.png' />
<c:url var='icon_adicionar' value='/images/icon_adicionar1.png' />
<c:url var='icon_salvar' value='/images/icon_salvar.png' />

<c:url var='parametroCodBemImovel' value='&codBemImovel=${documentacaoForm.codBemImovel}&administracao=${documentacaoForm.administracao}' />
<c:url var='link_aba_identificacao' value='/bemImovel.do?action=carregarPgEditBemImovel&actionType=alterar${parametroCodBemImovel}' />
<c:url var='link_aba_lei' value='/leiBemImovel.do?action=carregarPgEditLeiBemImovel${parametroCodBemImovel}' />
<c:url var='link_aba_quadraLote' value='/quadraLote.do?action=carregarPgEditQuadraLote${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_confrontante' value='/confrontante.do?action=carregarPgEditConfrontante${parametroCodBemImovel}' />
<c:url var='link_aba_avaliacao' value='/avaliacao.do?action=carregarPgEditAvaliacao${parametroCodBemImovel}' />
<c:url var='link_aba_coordenadaUtm' value='/coordenadaUtm.do?action=carregarPgEditCoordenadaUtm${parametroCodBemImovel}' />
<c:url var='link_aba_edificacao' value='/edificacao.do?action=carregarPgEditEdificacao${parametroCodBemImovel}' />
<c:url var='link_aba_anexo' value='/anexoBemImovel.do?action=carregarPgEditAnexo${parametroCodBemImovel}&actionType=incluir' />

<c:url var='link_pesquisar_documentacao' value='/documentacao.do?action=carregarPgEditDocumentacao&actionType=incluir${parametroCodBemImovel}' />
<c:url var='link_alterar_documentacao' value='/documentacao.do?action=carregarPgEditDocumentacao&actionType=alterar${parametroCodBemImovel}' />
<c:url var='link_visualizar_documento' value='/documentacao.do?action=carregarAnexoDocumentacao&codDocumentacao=' />

<script language="javascript">
	
	function adicionar() {
		if (validarCampos()){
			document.documentacaoForm.action="documentacao.do?action=adicionarDocumentacao";
			document.documentacaoForm.submit();
		}
	}

	function alterar() {
		if (validarCampos()){
			document.documentacaoForm.action="documentacao.do?action=alterarDocumentacao";
			document.documentacaoForm.submit();
		}
	}

	function limparCampos() {	
		document.documentacaoForm.action="documentacao.do?action=carregarPgEditDocumentacao${parametroCodBemImovel}&actionType=incluir";
		document.documentacaoForm.submit();
	}

	function editar(codigo1) {
		document.documentacaoForm.action="${link_alterar_documentacao}&codDocumentacao="+codigo1;
		document.documentacaoForm.submit();
	}

	function excluir(cDocumentacao){
		if(confirm('Tem certeza que deseja excluir?')) {
			document.documentacaoForm.action="documentacao.do?action=excluirDocumentacao&codDocumentacao="+cDocumentacao+"&actionType=excluir";
			document.documentacaoForm.submit();
		}
	}
	
	function validarCampos(){
	
		var form = document.documentacaoForm;

		if (form.cartorio.value ==  "" && form.tabelionato.value ==  "" ){
			alert("Informe o campo 'Nome do Cartório de Registro de Imóveis' ou o campo 'Nome do Tabelionato'!");
			form.cartorio.focus();
			return false;
		}

		if (form.cartorio.value != "" ){
			if (form.numeroDocumentoCartorial.value ==  "" ){
				alert("Campo 'N° do Documento Cartorial' é obrigatório!");
				form.numeroDocumentoCartorial.focus();
				return false;
			}
		}

		if (form.tabelionato.value != "" ){
			if (form.numeroDocumentoTabelional.value ==  "" ){
				alert("Campo 'N° do Documento Tabelional' é obrigatório!");
				form.numeroDocumentoTabelional.focus();
				return false;
			}
		}	
		
		return true;
	}

	function habilitarCampos(){
		var form = document.documentacaoForm;
		if (form.cartorio.value != "" ){
			form.numeroDocumentoCartorial.disabled=false;
			try{
				form.niif.disabled=false;
			}catch (e){}
			try{
				form.nirf.disabled=false;
				form.incra.disabled=false;
			}catch (e){}

			form.tabelionato.value="";
			form.numeroDocumentoTabelional.value="";
			form.tabelionato.disabled=true;
			form.numeroDocumentoTabelional.disabled=true;
		} else if (form.tabelionato.value != "" ){
			form.cartorio.value="";
			form.numeroDocumentoCartorial.value="";
			try{
				form.niif.value="";
				form.niif.disabled=true;
			}catch (e){}
			try{
				form.nirf.value="";
				form.incra.value="";
				form.incra.disabled=true;
				form.nirf.disabled=true;
			}catch (e){}
			form.cartorio.disabled=true;
			form.numeroDocumentoCartorial.disabled=true;
			form.tabelionato.disabled=false;
			form.numeroDocumentoTabelional.disabled=false;
		}	
	}
	
	function trocarAba(aba){
		var form = document.documentacaoForm;
		form.action=aba;
		form.submit();
	}

	window.onload = function(){
		habilitarCampos();
		habilitarAbas();
	};
	
	function habilitarAbas() {
		var form = document.documentacaoForm;
		if (form.somenteTerreno.value == 'S') {
			document.getElementById("abaEdificacao").style.display="none";
			document.getElementById("abaEdificacaoDesabilitada").style.display="block";
		} else if (form.somenteTerreno.value == 'N'){
			document.getElementById("abaEdificacao").style.display="block";
			document.getElementById("abaEdificacaoDesabilitada").style.display="none";
		}
	}
	
	
</script>

<body onload="habilitarAbas();">

<div id="conteudo">
	<div align="center">
	<h1>Alterar Bem Imóvel - Documentação</h1>

	<html:form action="documentacao.do?action=salvarDocumentacao" onsubmit="return validarCampos() ">
		<html:hidden property="actionType" value="${documentacaoForm.actionType}" />
		<html:hidden property="codBemImovel" value="${documentacaoForm.codBemImovel}" />
		<html:hidden property="codDocumentacao" value="${documentacaoForm.codDocumentacao}" />
		<html:hidden property="somenteTerreno" value="${documentacaoForm.somenteTerreno}" />
	
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
		<html:hidden property="conCodUf"/>
		<html:hidden property="conCodMunicipio"/>
		<html:hidden property="conOcupante"/>
	 
		<div id="conteudo_corpo" style="padding: 4px">	
	 		<table class="form_tabela" cellspacing="0" width="100%" style="">
				<tr>
					<td style="text-align: center;">
						<span class="form_label_infBI">Identificação do Imóvel CPE: </span>
						<c:out value="${documentacaoForm.nrBemImovel}"></c:out>
					</td>
				</tr>
	
				<tr>
					<td style="text-align: center;">
						<span class="form_label_infBI">Administração: </span>
						<c:out value="${documentacaoForm.descricaoAdministracao}"></c:out>
					</td>
				</tr>
	 		</table>
	   	</div>

	   	<a name="abas"></a> 
		<table class="abas" cellspacing="0">
		     <tr> 
		       <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_identificacao}')">Identificação</a></td>
		        <td class="aba_selecionada">Documentação</td>
		      	<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_lei}')">Lei/Decreto</a></td>
        	  	<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_quadraLote}')">Quadra/Lote</a></td>
		      	<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_confrontante}')">Confrontantes</a></td>
	          	<td class="aba_desselecionada">
	        	  <div id="abaEdificacao"> 
	        		<a href="javascript:trocarAba('${link_aba_edificacao}')">Edificação</a>
	        	  </div>
	        	  <div id="abaEdificacaoDesabilitada" style="display:none">Edificação</div>
	          	</td>
		      	<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_avaliacao}')">Avaliação</a> </td>
		      	<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_coordenadaUtm}')">Coordenada UTM </a></td>
				<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_anexo}')">Anexos</a></td>        
		     </tr>
		</table>
	 
     	<div id="conteudo_abas" style="padding: 4px">		
			<div id ="DocumentacaoOriginal" >
				<table class="form_tabela" cellspacing="0" width="100%" >
					<tr height="10"></tr>
					<tr>
						<td colspan="3">
							<h2>Dados do Cartório</h2>
						</td>
					</tr>
					<tr>
						<td class="form_label">*Nome do Cartório de Registro de Imóveis: </td>
							<td colspan="2">
								<html:select property="cartorio"  styleId="cartorio" onchange="habilitarCampos();">
									<html:option value="">-selecione-</html:option>
									<html:options collection="cartorios" property="codCartorio" labelProperty="descricao" />						
								</html:select>
							</td>		
					</tr>
					<tr>
						<td class="form_label">*N° do Documento Cartorial: </td>
						<td>
							<html:text maxlength="100" size="60" styleId="numeroDocumentoCartorial" property="numeroDocumentoCartorial" />
						</td>
					</tr>
					<c:if test="${CLASSIFICACAO_RURAL}">
						<tr>
						 	<td class="form_label">NIRF - Número Inscrição Imóvel Rural na S.R.F.: </td>
						 	<td>
								<html:text maxlength="255" size="60" property="nirf" styleId="nirf"  />
							</td>
						</tr>
						<tr>
						 	<td class="form_label">INCRA - Cadastro de Imóvel Rural: </td>
						 	<td>
								<html:text maxlength="255" size="60" property="incra"  styleId="incra"  />
							</td>
						</tr>
					</c:if>
					<c:if test="${!CLASSIFICACAO_RURAL}">
						<tr>
						 	<td class="form_label">Número Inscrição Imobiliária/Indicação Fiscal: </td>
						 	<td>
								<html:text maxlength="255" size="60" property="niif" styleId="niif"  />
							</td>
						</tr>
					</c:if>
					<tr height="10"></tr>
					<tr>
						<td colspan="3">
							<h2>Dados do Tabelionato</h2>
						</td>
					</tr>
					<tr>
						<td class="form_label">*Nome do Tabelionato: </td>
						<td colspan="2">
							<html:select property="tabelionato" onchange="habilitarCampos();" styleId="tabelionato">
								<html:option value="">-selecione-</html:option>
								<html:options collection="tabelionatos" property="codTabelionato" labelProperty="descricao" />						
							</html:select>
						</td>				
					</tr>
					<tr>
						<td class="form_label">*N° do Documento Tabelional: </td>
						<td>
							<html:text maxlength="100" size="60"  styleId="numeroDocumentoTabelional" property="numeroDocumentoTabelional" />
						</td>
					</tr>
					<tr height="10"></tr>
				</table>
				</div>
		 	<div align="center">
				<table class="form_tabela" cellspacing="0" width="100%">
 					<tr> 
						<td width="30%">&ensp;&ensp;&ensp;&ensp;</td>
  						<c:choose> 
							<c:when test='${documentacaoForm.actionType == "incluir"}'>
								<td width="20%"><a href="javascript:adicionar();"><img src="${icon_adicionar}" width="16" height="16" border="0"> Adicionar </a></td>
							</c:when>
							<c:otherwise>
								<td width="20%"><a href="javascript:alterar()"><img src="${icon_salvar}" width="16" height="16" border="0">Salvar</a> </td>
							</c:otherwise>
						</c:choose>
						<td width="3%"></td>
						<td width="*"><a href="javascript:limparCampos()"><img src="${icon_apagar}" width="16" height="16" border="0" >Limpar</a></td>
					</tr>
				</table>
			</div>
        	<br>
		   	<hr>
		  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
			<c:if test="${!empty requestScope.pagina.registros}">
				<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
					<ch:lista bean="${pagina}" atributoId="codDocumentacao,codNotificacao"/>
					<ch:campo atributo="detalhes" label="Documentação" width="60%"/>
					<ch:campo atributo="tsInclusao" label="Data de Inclusão" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" width="9%"/>
					<ch:campo atributo="tsAtualizacao" label="Data de Alteração" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" width="9%"/>
					<ch:campo  atributo="responsavelDocumentacao" label="Responsável" width="14%"  />
					<ch:action imagem="${icon_alterar}"  link="javascript:editar(%1);" label="Alterar"  align="center" width="3%"/>
					<ch:action imagem="${icon_excluir}"  link="javascript:excluir(%1);" label="Excluir" align="center" width="3%"/>
					<ch:painel pagina="${link_pesquisar_documentacao}" classe="painel" atributoIndice="indice" />
				</ch:table>  
			</c:if>
		</div>
	</html:form>
  </div>
</div>
</body>