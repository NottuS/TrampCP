<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='icon_apagar' value='/images/icon_apagar.png' />
<c:url var='icon_adicionar' value='/images/icon_adicionar1.png' />
<c:url var='icon_localizar' value='/images/icon_localizar.png' />
<c:url var='icon_salvar' value='/images/icon_salvar.png' />

<c:url var='parametroCodBemImovel' value='&codBemImovel=${quadraLoteForm.codBemImovel}&administracao=${quadraLoteForm.administracao}' />
<c:url var='link_aba_identificacao' value='/bemImovel.do?action=carregarPgEditBemImovel&actionType=alterar${parametroCodBemImovel}' />
<c:url var='link_aba_lei' value='/leiBemImovel.do?action=carregarPgEditLeiBemImovel${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_quadraLote' value='/quadraLote.do?action=carregarPgEditQuadraLote${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_confrontante' value='/confrontante.do?action=carregarPgEditConfrontante${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_avaliacao' value='/avaliacao.do?action=carregarPgEditAvaliacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_coordenadaUtm' value='/coordenadaUtm.do?action=carregarPgEditCoordenadaUtm${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_edificacao' value='/edificacao.do?action=carregarPgEditEdificacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_documentacao' value='/documentacao.do?action=carregarPgEditDocumentacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_anexo' value='/anexoBemImovel.do?action=carregarPgEditAnexo${parametroCodBemImovel}&actionType=incluir' />

<c:url var='link_editar' value='/quadraLote.do?action=carregarPgEditQuadraLote${parametroCodBemImovel}' />
<c:url var='link_pesquisar' value='/quadraLote.do?action=carregarPgEditQuadraLote${parametroCodBemImovel}&actionType=incluir' />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">
	
	function incluir() {
		document.quadraLoteForm.action="quadraLote.do?action=salvarQuadraLote";
		if (validarCampos()){
			document.quadraLoteForm.submit();
		}
	}
	
	function alterar() {
		document.quadraLoteForm.action="quadraLote.do?action=alterarQuadraLote";
		if (validarCampos()){
			document.quadraLoteForm.submit();
		}
	}
	
	function editarQuadra(codigo) {
		document.quadraLoteForm.action="quadraLote.do?action=carregarPgEditQuadraLote&codQuadra="+codigo+"&codBemImovel="+document.quadraLoteForm.codBemImovel.value+"&actionType=alterar";
		document.quadraLoteForm.submit();
	}

	function excluirQuadra(codigo){
		if(confirm('Tem certeza que deseja excluir?')) {
			document.quadraLoteForm.action="quadraLote.do?action=excluirQuadra&codQuadra="+codigo+"&actionType=excluir";
			document.quadraLoteForm.submit();
		}
	}

	function editarLote(codigo) {
		document.quadraLoteForm.action="quadraLote.do?action=carregarPgEditQuadraLote&codLote="+codigo+"&codBemImovel="+document.quadraLoteForm.codBemImovel.value+"&actionType=alterar";
		document.quadraLoteForm.submit();
	}

	function excluirLote(codigo,descricao){
		if(confirm('Tem certeza que deseja excluir?')) {
			document.quadraLoteForm.action="quadraLote.do?action=excluirLote&codLote="+codigo+"&actionType=excluir";
			document.quadraLoteForm.submit();
		}
	}

	function limparCampos() {
		var form = document.quadraLoteForm;
		form.descricaoQuadra.value="";
		form.codQuadra.value="";
		form.descricaoLote.value="";
		form.action="${link_aba_quadraLote}&actionType=incluir";
		form.submit();
	}
	
	function validarCampos(){
		var form = document.quadraLoteForm;
		Trim(form.descricaoQuadra);
		if (form.descricaoQuadra.value == "" && form.codQuadra.value == ""){
			alert("Campo 'quadra' é obrigatório!");
			form.descricaoQuadra.focus();
			return false;
		}
		return true;
	}

	function trocarAba(aba){
		var form = document.quadraLoteForm;
		document.quadraLoteForm.action=aba;
		form.submit();
	}

	function habilitarAbas() {
		var form = document.quadraLoteForm;
		if (form.somenteTerreno.value == 'S') {
			document.getElementById("abaEdificacao").style.display="none";
			document.getElementById("abaEdificacaoDesabilitada").style.display="block";
		} else if (form.somenteTerreno.value == 'N'){
			document.getElementById("abaEdificacao").style.display="block";
			document.getElementById("abaEdificacaoDesabilitada").style.display="none";
		}
		if (document.quadraLoteForm.actionType == "incluir") {
			habilitarDesabilitarTextoQuadra();
			habilitarDesabilitarComboQuadra();
		}
	}

	function habilitarDesabilitarTextoQuadra() {
		var form = document.quadraLoteForm;
		if (form.codQuadra.value != "") {
			form.descricaoQuadra.disabled=true;
			form.descricaoQuadra.value = "";
		} else {
			form.descricaoQuadra.disabled=false;
		}
	}
	
	function habilitarDesabilitarComboQuadra() {
		var form = document.quadraLoteForm;
		Trim(form.descricaoQuadra);
		if (form.descricaoQuadra.value != "") {
			form.codQuadra.disabled=true;
			form.codQuadra.value = "";
		} else {
			form.codQuadra.disabled=false;
		}
	}

</script>

<body onload="habilitarAbas();">

<div id="conteudo">
	<div align="center">
	<h1>Alterar Bem Imóvel - Quadra/Lote</h1>

	<html:form action="quadraLote.do?action=salvarQuadra" method="post">
	<html:hidden property="codBemImovel" value="${quadraLoteForm.codBemImovel}"/>
	<html:hidden property="actionType" value="${quadraLoteForm.actionType}" />
	<html:hidden property="codLote" value="${quadraLoteForm.codLote}" />
	<html:hidden property="somenteTerreno" value="${quadraLoteForm.somenteTerreno}" />
	
		
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
					<c:out value="${quadraLoteForm.nrBemImovel}"></c:out>
				</td>
			</tr>

			<tr>
				<td style="text-align: center;">
					<span class="form_label_infBI">Administração: </span>
					<c:out value="${quadraLoteForm.descricaoAdministracao}"></c:out>
				</td>
			</tr>
 		</table>
   </div>

    <a name="abas"></a> 
	<table class="abas" cellspacing="0">
      <tr> 
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_identificacao}')">Identificação</a></td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_documentacao}')">Documentação</a></td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_lei}')">Lei/Decreto</a></td>
        <td class="aba_selecionada">Quadra/Lote</td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_confrontante}')">Confrontantes</a></td>
        <td class="aba_desselecionada">
        	<div id="abaEdificacao"> 
        		<a href="javascript:trocarAba('${link_aba_edificacao}')">Edificação</a>
        	</div>
        	<div id="abaEdificacaoDesabilitada" style="display:none">Edificação</div>
        </td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_avaliacao}')">Avaliação</a></td>
		<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_coordenadaUtm}')">Coordenada UTM</a></td>        
		<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_anexo}')">Anexos</a></td>        
      </tr>
	</table>

    <div id="conteudo_abas">
	 <table class="form_tabela" cellspacing="0">
		<tr>
			<td colspan="2"> 
				<fieldset dir="ltr">
				    <legend>&ensp;*Quadra:&ensp;</legend>
						<table id="tableCombo" class="form_tabela" width="100%">
							<c:choose> 
								<c:when test='${quadraLoteForm.actionType == "incluir"}'>
									<tr>
										<td class="form_label" width="150">Selecione:</td>
										<td>
											<html:select property="codQuadra" styleId="codQuadra" onchange="habilitarDesabilitarTextoQuadra()">
												<html:option value="">-Selecione-</html:option>
												<html:options collection="quadras" property="codQuadra" labelProperty="descricao" />
											</html:select>
										</td>
									</tr>
									<tr>
										<td class="form_label">Informe para cadastro: </td>
										<td> 
											<html:textarea  property="descricaoQuadra" cols="85" rows="3"  styleId="descricao" onkeypress="habilitarDesabilitarComboQuadra()"/>
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<tr>
										<td class="form_label" width="150">Selecione:</td>
										<td>
											<html:select property="quadra" styleId="quadra" disabled="true">
												<html:option value="">-Selecione-</html:option>
											</html:select>
											<html:hidden property="codQuadra" value="${quadraLoteForm.codQuadra}" />
										</td>
									</tr>
									<tr>
										<td class="form_label">Informe para cadastro: </td>
										<td> 
											<html:textarea  property="descricaoQuadra" cols="85" rows="3"  styleId="descricao"/>
										</td>
									</tr>
								</c:otherwise>
							</c:choose>         
						</table>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td class="form_label" width="155">*Lote:</td>
			<td><html:textarea  property="descricaoLote" cols="85" rows="3"  styleId="descricao" /></td>			
		</tr>
	 </table>

 	<div align="center">
		<table class="form_tabela" cellspacing="0" width="100%">
				<tr> 
				<td width="30%">&ensp;&ensp;&ensp;&ensp;</td>
				<c:choose> 
					<c:when test='${quadraLoteForm.actionType == "incluir"}'>
						<td width="20%"><a href="javascript:incluir()"><img src="${icon_adicionar}" width="16" height="16" border="0"> Adicionar </a></td>
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
     
   	<hr>
  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
   	<hr>
 	
	<c:if test="${!empty requestScope.paginaQuadra.registros}">
 	 	<h2>Quadra sem cadastro de Lote</h2>
	  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
		    <ch:lista bean="${paginaQuadra}" atributoId="codQuadra" />
	        <ch:campo atributo="descricao" label="Quadra" />
	        <ch:action imagem="${icon_alterar}"  link="javascript:editarQuadra(%1);" label="Alterar" width="40" align="center" />
	        <ch:action imagem="${icon_excluir}"  link="javascript:excluirQuadra(%1);" label="Excluir" width="40" align="center" />
	  	</ch:table>  
  	</c:if>

	<c:if test="${!empty requestScope.paginaLote.registros}">
 	 	<h2>Quadra e Lote</h2>
	  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
		    <ch:lista bean="${paginaLote}" atributoId="codLote,quadra.codQuadra" />
	        <ch:campo atributo="quadra.descricao" label="Quadra" width="35%" />
	        <ch:campo atributo="descricao" label="Lote" width="36%"/>
	        <ch:action imagem="${icon_alterar}"  link="javascript:editarLote(%1);" label="Alterar Quadra/Lote" width="10%" align="center" />
	        <ch:action imagem="${icon_excluir}"  link="javascript:excluirLote(%1);" label="Excluir Lote" width="7%" align="center" />
	        <ch:action imagem="${icon_excluir}"  link="javascript:excluirQuadra(%2);" label="Excluir Quadra e Lote(s)" width="12%" align="center" />
	  	</ch:table>  
  	</c:if>
  </div>

  </html:form>

  </div>
</div>
