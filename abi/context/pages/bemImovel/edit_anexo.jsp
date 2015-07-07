<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_data" value="/js/generic/data.js" />
<c:url var="js_util" value="/js/generic/util.js" />

<c:url var='icon_exibir' value='/images/icon_exibir.png' />
<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='icon_calendar' value='/images/icon_calendario.png' />
<c:url var='icon_apagar' value='/images/icon_apagar.png' />
<c:url var='icon_adicionar' value='/images/icon_adicionar1.png' />
<c:url var='icon_salvar' value='/images/icon_salvar.png' />

<c:url var='parametroCodBemImovel' value='&codBemImovel=${anexoBemImovelForm.codBemImovel}&administracao=${anexoBemImovelForm.administracao}' />
<c:url var='link_aba_identificacao' value='/bemImovel.do?action=carregarPgEditBemImovel&actionType=alterar${parametroCodBemImovel}' />
<c:url var='link_aba_lei' value='/leiBemImovel.do?action=carregarPgEditLeiBemImovel${parametroCodBemImovel}' />
<c:url var='link_aba_quadraLote' value='/quadraLote.do?action=carregarPgEditQuadraLote${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_confrontante' value='/confrontante.do?action=carregarPgEditConfrontante${parametroCodBemImovel}' />
<c:url var='link_aba_avaliacao' value='/avaliacao.do?action=carregarPgEditAvaliacao${parametroCodBemImovel}' />
<c:url var='link_aba_coordenadaUtm' value='/coordenadaUtm.do?action=carregarPgEditCoordenadaUtm${parametroCodBemImovel}' />
<c:url var='link_aba_edificacao' value='/edificacao.do?action=carregarPgEditEdificacao${parametroCodBemImovel}' />
<c:url var='link_aba_documentacao' value='/documentacao.do?action=carregarPgEditDocumentacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_anexo' value='/anexoBemImovel.do?action=carregarPgEditAnexo${parametroCodBemImovel}&actionType=incluir' />

<c:url var='link_pesquisar_anexoBemImovel' value='/anexoBemImovel.do?action=carregarPgEditAnexo&actionType=incluir${parametroCodBemImovel}' />
<c:url var='link_alterar_anexoBemImovel' value='/anexoBemImovel.do?action=carregarPgEditAnexo&actionType=alterar${parametroCodBemImovel}' />
<c:url var='link_visualizar_documento' value='/anexoBemImovel.do?action=carregarAnexo&codDocumentacao=' />

<jsp:useBean id="agora" class="java.util.Date" />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">

	function adicionar() {
		if (validarCampos()){
			document.anexoBemImovelForm.action="anexoBemImovel.do?action=adicionarAnexo";
			document.anexoBemImovelForm.submit();
		}
	}

	function alterar() {
		if (validarCampos()){
			document.anexoBemImovelForm.action="anexoBemImovel.do?action=alterarAnexo";
			document.anexoBemImovelForm.submit();
		}
	}

	function limparCampos() {	
		document.anexoBemImovelForm.action="anexoBemImovel.do?action=carregarPgEditAnexo${parametroCodBemImovel}&actionType=incluir";
		document.anexoBemImovelForm.submit();
	}

	function exibirDocumentacao(codigo){
		if (codigo==undefined){
			alert("Não existe anexo para essa documentação!");
		} else {
			var form = document.anexoBemImovelForm;
			document.anexoBemImovelForm.action="${link_visualizar_documento}"+codigo;
			form.submit();
		}
	}

	function editar(codigo1) {
		document.anexoBemImovelForm.action="${link_alterar_anexoBemImovel}&codDocumentacao="+codigo1;
		document.anexoBemImovelForm.submit();
	}

	function excluir(cAnexoBemImovel){
		if(confirm('Tem certeza que deseja excluir?')) {
			document.anexoBemImovelForm.action="anexoBemImovel.do?action=excluirAnexo&codDocumentacao="+cAnexoBemImovel+"&actionType=excluir";
			document.anexoBemImovelForm.submit();
		}
	}

	function voltar() {
		document.anexoBemImovelForm.action="bemImovel.do?action=carregarPgListBemImovel";
		document.anexoBemImovelForm.submit();
	}
	
	function validarCampos(){
	
		var form = document.anexoBemImovelForm;
		dataAux= new Date();
		dataAtual=dataAux.toLocaleDateString();

		if(document.getElementById("selBemImovel2").checked) {
			if (form.edificacao.value == "0"){
				alert("Campo 'Edificação' é obrigatório!");
				form.edificacao.focus();
				return false;
			}
		}

		if (form.descricaoAnexo.value == ""){
			alert("Campo 'Descrição' é obrigatório!");
			form.descricaoAnexo.focus();
			return false;
		}

		if(form.tipoDocumentacaoAnexo.options[form.tipoDocumentacaoAnexo.options.selectedIndex].value== ${notificacao})//notificação
		{
			if (form.tsNotificacao.value == ""){
				alert("Campo 'Data da Notificação' é obrigatório!");
				form.tsNotificacao.focus();
				return false;
			}
			if (!ValidaData(form.tsNotificacao)){
				alert("Campo 'Data da Notificação' deve ser uma data válida!");
				form.tsNotificacao.focus();
				return false;			
			}
			if (DataMaior(form.tsNotificacao.value, dataAtual )){
				alert("Campo 'Data da Notificação' não pode ser maior que a data atual!");
				form.tsNotificacao.focus();
				return false;			
			}
			if (!DataMaior(form.tsNotificacao.value,'03/12/1889')){
				alert("Campo 'Data da Notificação' deve ser igual ou maior que 04/12/1889!");
				form.tsNotificacao.focus();
				return false;			
			}
			if (form.tsPrazoNotificacao.value == ""){
				alert("Campo 'Prazo da Notificação' é obrigatório!");
				form.tsPrazoNotificacao.focus();
				return false;
			}
			if (!ValidaData(form.tsPrazoNotificacao)){
				alert("Campo 'Prazo da Notificação' deve ser uma data válida!");
				form.tsPrazoNotificacao.focus();
				return false;			
			}
			if (!DataMaior(form.tsPrazoNotificacao.value,'03/12/1889')){
				alert("Campo 'Prazo da Notificação' deve ser igual ou maior que 04/12/1889!");
				form.tsPrazoNotificacao.focus();
				return false;			
			}
			if (!DataMaior(form.tsPrazoNotificacao.value,form.tsNotificacao.value)){
				alert("Campo 'Prazo da Notificação' deve ser maior que 'Data da Notificação'!");
				form.tsPrazoNotificacao.focus();
				return false;			
			}
			Trim(form.descricaoNotificacao);
			if (form.descricaoNotificacao.value == ""){
				alert("Campo 'Descrição da Notificação' é obrigatório!");
				form.descricaoNotificacao.focus();
				return false;
			}	
			if (form.tsSolucao.value != ""){
				if (!ValidaData(form.tsSolucao)){
					alert("Campo 'Data da Solução' deve ser uma data válida!");
					form.tsSolucao.focus();
					return false;			
				}
				if (DataMaior(form.tsSolucao.value, dataAtual )){
					alert("Campo 'Data da Solução' não pode ser maior que a data atual!");
					form.tsSolucao.focus();
					return false;			
				}
				if (!DataMaior(form.tsSolucao.value,'03/12/1889')){
					alert("Campo 'Data da Solução' deve ser igual ou maior que 04/12/1889!");
					form.tsSolucao.focus();
					return false;			
				}
				if (!DataMaior(form.tsSolucao.value,form.tsNotificacao.value)){
					alert("Campo 'Data da Solução' deve ser maior que 'Data da Notificação'!");
					form.tsSolucao.focus();
					return false;			
				}
				Trim(form.motivoSolucao);
				if (form.motivoSolucao.value == ""){
					alert("Campo 'Motivo da Solução' é obrigatório!");
					form.motivoSolucao.focus();
					return false;
				}	
			}	
		}
				
		return true;
	}

	function verificarVisibilidade(){
		var form= document.anexoBemImovelForm;
		if(document.getElementById("selBemImovel1").checked) {
			document.getElementById("Edificacao").style.display = "none";
		} else {
			document.getElementById("Edificacao").style.display = "block";
		}
		if(form.tipoDocumentacaoAnexo.options[form.tipoDocumentacaoAnexo.options.selectedIndex].value== ${notificacao}) {
			document.getElementById("Notificacao").style.display = "block";		
		} else {
			document.getElementById("Notificacao").style.display = "none";
		}
	}
	
	function trocarAba(aba){
		var form = document.anexoBemImovelForm;
		form.action = aba;
		form.submit();
	}

	window.onload = function(){
		verificarVisibilidade();	
		habilitarAbas();
	};
	
	function habilitarAbas() {
		var form = document.anexoBemImovelForm;
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
	<h1>Alterar Bem Imóvel - Anexos</h1>

	<html:form action="anexoBemImovel.do?action=adicionarAnexo"  enctype="multipart/form-data" method="post"  onsubmit="return validarCampos() " >
		<html:hidden property="actionType" value="${anexoBemImovelForm.actionType}" />
		<html:hidden property="codBemImovel" value="${anexoBemImovelForm.codBemImovel}" />
		<html:hidden property="codDocumentacao" value="${anexoBemImovelForm.codDocumentacao}" />
		<html:hidden property="codNotificacao" value="${anexoBemImovelForm.codNotificacao}" />
		<html:hidden property="somenteTerreno" value="${anexoBemImovelForm.somenteTerreno}" />
			
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
						<c:out value="${anexoBemImovelForm.nrBemImovel}"></c:out>
					</td>
				</tr>
	
				<tr>
					<td style="text-align: center;">
						<span class="form_label_infBI">Administração: </span>
						<c:out value="${anexoBemImovelForm.descricaoAdministracao}"></c:out>
					</td>
				</tr>
	 		</table>
	   </div>

	   	<a name="abas"></a> 
		<table class="abas" cellspacing="0">
		     <tr> 
		       <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_identificacao}')">Identificação</a></td>
		       <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_documentacao}')">Documentação</a> </td>
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
				<td class="aba_selecionada">Anexos</td>        
		     </tr>
		</table>
     
	    <div id="conteudo_abas">
			<div align="center">
			     <div id="conteudo_corpo" style="padding: 4px">		
					<table class="form_tabela" cellspacing="0" width="100%" >
						<tr>
							<td class="form_label" width="280">Tipo Documentação :</td>
							<td colspan="3">
								<html:select property="tipoDocumentacaoAnexo" onchange="verificarVisibilidade();">
									<html:option value="">-selecione-</html:option>
									<html:options collection="tiposDocumentacao" property="codTipoDocumentacao" labelProperty="descricao" />						
								</html:select>
							</td>
						</tr>
						<tr>
							<td class="form_label">*Descrição: </td>
							<td><html:textarea  property="descricaoAnexo" cols="100" rows="5"  styleId="descricaoAnexo" onkeyup="TamanhoMax(this, 500)"/></td>
						</tr>
						<tr> 
							<td class="form_label">* Por:</td>
							<td colspan="6">
								<html:radio property="selBemImovel" styleId="selBemImovel1" value="1" onchange="verificarVisibilidade();carregarListaDocumentacao()" >Bem Im&oacute;vel</html:radio>
								<html:radio property="selBemImovel" styleId="selBemImovel2" value="2" onchange="verificarVisibilidade();carregarListaDocumentacao()">Edifica&ccedil;&atilde;o</html:radio>
							</td>
						</tr>
	       			</table>
	       			<div id ="Edificacao" style="display:none">
	        			<table class="form_tabela" cellspacing="0" width="100%">
							<tr>
								<td class="form_label" width="280">*Edificação :</td>
								<td colspan="3">
									<html:select property="edificacao" styleId="edificacao" onchange="javascript:carregarListaDocumentacao()">
										<html:option value="0">-selecione-</html:option>
										<logic:present name="edificacoes">
											<html:options collection="edificacoes" property="codEdificacao" labelProperty="especificacao" />
										</logic:present>
									</html:select>
								</td>
							</tr>
						</table>
					</div>
					<div id="Arquivo" >
	       				<table class="form_tabela" cellspacing="0" width="100%" >
							<tr>
								<td class="form_label" width="280">Documentação Original :</td>
								<td colspan="4">
									<html:select property="documentacao" styleId="documentacao">
										<html:option value="0">-selecione-</html:option>
										<logic:present name="documentacaosOriginal">
		 									<html:options collection="documentacaosOriginal" property="codDocumentacao" labelProperty="descricao" />
										</logic:present>
									</html:select>
								</td>
							</tr>
							<tr> 
	          					<td class="form_label" width="280">Arquivo :</td>
						        <td colspan="2" height="25"><html:file  maxlength="100" size="70"  property="anexo" styleId="anexo"/></td>
	        				</tr>
						</table>
						<div id="Notificacao" style="display:none">
							<table class="form_tabela" cellspacing="0" width="100%" >
								<tr>
									<td colspan="3"><h2>Dados da Notificação</h2></td>
								</tr>
								<tr>
									<td class="form_label" width="280">*Data da Notificação :</td>
									<td>
										<html:text styleId="tsNotificacao" property="tsNotificacao"  size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));" />
										<img src="${icon_calendar}" width="16" height="15" id="btnCalendar" onclick="javascript:Calendario(document.getElementById('tsNotificacao'),this.id);" />
									</td>
								</tr>
								<tr> 
		          					<td class="form_label">*Prazo da Notificação :</td>
		          					<td colspan="2"><html:text styleId="tsPrazoNotificacao" property="tsPrazoNotificacao"  size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));" />
		            				<img src="${icon_calendar}" width="16" height="16" border="0"  id="btnCalendar1" onclick="javascript:Calendario(document.getElementById('tsPrazoNotificacao'),this.id);"></td>
		        				</tr>
		        				<tr> 
		          					<td class="form_label">*Descrição da notificação :</td>
		          					<td colspan="2"><html:textarea styleId="descricaoNotificacao" property="descricaoNotificacao" cols="120" rows="3"></html:textarea></td>
		        				</tr>
		        				<tr> 
		          					<td class="form_label">Data da Solução :</td>
		          					<td colspan="2"><html:text  styleId="tsSolucao" property="tsSolucao"  size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));"/> 
		           					<img src="${icon_calendar}" width="16" height="16" border="0"  id="btnCalendar2" onclick="javascript:Calendario(document.getElementById('tsSolucao'),this.id);"></td>
		        				</tr>
		        				<tr> 
		          					<td class="form_label">Motivo da Solução :</td>
		          					<td colspan="2"><html:textarea styleId="motivoSolucao" property="motivoSolucao" cols="120" rows="3"></html:textarea></td>
		        				</tr>
	      						</table>
							</div>
						</div>
		      			<br>
					 	<div align="center">
							<table class="form_tabela" cellspacing="0" width="100%">
									<tr> 
									<td width="30%">&ensp;&ensp;&ensp;&ensp;</td>
									<c:choose> 
										<c:when test='${anexoBemImovelForm.actionType == "incluir"}'>
											<td width="20%"><a href="javascript:adicionar();"><img src="${icon_adicionar}" width="16" height="16" border="0"> Adicionar </a></td>
										</c:when>
										<c:otherwise>
											<td width="20%"><a href="javascript:alterar();"><img src="${icon_salvar}" width="16" height="16" border="0">Salvar</a> </td>
										</c:otherwise>
									</c:choose>
									<td width="3%"></td>
									<td width="*"><a href="javascript:limparCampos()"><img src="${icon_apagar}" width="16" height="16" border="0" >Limpar</a></td>
								</tr>
							</table>
						</div>

    					<hr>
						<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
    					<c:if test="${!empty requestScope.pagina.registros}">
	 						<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
	 	 						<ch:lista bean="${pagina}" atributoId="codDocumentacao,codNotificacao"/>
			   						<ch:campo atributo="instanciaAtual" label="Visualizar Documento"  align="center" decorator="gov.pr.celepar.abi.util.DocumentacaoDecorator" width="3%"/>
		  							<ch:campo atributo="tipoDocumentacao" label="Tipo" width="10%"/>
		  							<ch:campo atributo="descricaoAnexo" label="Descrição" width="40%"/>
		      						<ch:campo atributo="tsInclusao" label="Data de Inclusão" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" width="5%"/>
		      						<ch:campo atributo="tsNotificacao" label="Data da Notificação" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" width="5%"/>
		      						<ch:campo atributo="tsPrazoNotificacao" label="Prazo da Notificação" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" width="5%"/>
		      						<ch:campo atributo="tsAtualizacao" label="Data de Alteração" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" width="5%"/>
		      						<ch:campo  atributo="responsavelDocumentacao" label="Responsável" width="14%"  />
		      						<ch:action imagem="${icon_alterar}"  link="javascript:editar(%1);" label="Alterar"  align="center" width="3%"/>
		      						<ch:action imagem="${icon_excluir}"  link="javascript:excluir(%1);" label="Excluir" align="center" width="3%"/>
		      						<ch:painel pagina="${link_pesquisar_documentacao}" classe="painel" atributoIndice="indice" />
	 						</ch:table>  
						</c:if>
		    		</div>
	    		</div>
    		</div>
		</html:form>
	</div>
</div>
</body>
