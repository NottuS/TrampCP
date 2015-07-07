<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>



<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_data" value="/js/generic/data.js" />
<c:url var="js_util" value="/js/generic/util.js" />

<c:url var='icon_exibir' value='/images/icon_exibir.png' />
<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='icon_calendar' value='/images/icon_calendario.png' />
<c:url var='icon_apagar' value='/images/icon_apagar.png' />
<c:url var='icon_adicionar' value='/images/icon_adicionar1.png' />
<c:url var='icon_localizar' value='/images/icon_localizar.png' />
<c:url var='parametroCodBemImovel' value='&codBemImovel=${ocorrenciaDocumentacaoForm.codBemImovel}' />
<c:url var='link_visualizar_documento' value='/ocorrenciaDocumentacao.do?action=carregarAnexoDocumentacao&codDocumentacao=' />
<c:url var='link_editar_ocorrencia_documentacao' value='/ocorrenciaDocumentacao.do?action=carregarPgEditOcorrenciaDocumentacao${parametroCodBemImovel}' />

<c:url var='link_entrada' value='/entrada.do' />

<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">

function verificarVisibilidade(){
	
	var form = document.ocorrenciaDocumentacaoForm;
	
		
	if(form.relDocumentacao.checked)
	{
		document.getElementById("DocExistente").style.display = "block";
		document.getElementById("DocumentacaoOriginal").style.display = "block";
		document.getElementById("Arquivo").style.display = "block";
		if(form.docExistente.checked)
		{
			document.getElementById("Documentacao").style.display = "block";
			document.getElementById("DocExistente").style.display = "block";
			document.getElementById("Arquivo").style.display = "none";
			document.getElementById("DocumentacaoOriginal").style.display = "none";
			document.getElementById("Notificacao").style.display = "none";
		}
		else
		{
			document.getElementById("Documentacao").style.display = "none";
			if(form.tipoDocumentacao.options[form.tipoDocumentacao.options.selectedIndex].value== ${notificacao})//notificação
			{
				document.getElementById("Notificacao").style.display = "block";		
			}
			else
			{
				document.getElementById("Notificacao").style.display = "none";
			}
		}
	}
	else
	{
		document.getElementById("DocExistente").style.display = "none";
		document.getElementById("Documentacao").style.display = "none";
		document.getElementById("DocumentacaoOriginal").style.display = "none";
		document.getElementById("Notificacao").style.display = "none";
		document.getElementById("Arquivo").style.display = "none";
	}
}

function enviar()
{
	if (validarCampos())
	{
		document.ocorrenciaDocumentacaoForm.action="ocorrenciaDocumentacao.do?action=salvarOcorrenciaDocumentacao";
		document.ocorrenciaDocumentacaoForm.submit();
	}
}


function exibirDocumentacao(codigo){
	if (codigo==undefined){
		alert("Não existe anexo para essa ocorrência!");
	}
	else
	{
		var form = document.ocorrenciaDocumentacaoForm;
		document.ocorrenciaDocumentacaoForm.action="${link_visualizar_documento}"+codigo;
		form.submit();
	}
	
}

function window_open(theURL,winName,features) {
	  winName = window.open(theURL,winName,features);
	  winName.window.focus();
}

function apagar()
{
	document.ocorrenciaDocumentacaoForm.reset();
	verificarVisibilidade();
}

function voltar()
{
	document.ocorrenciaDocumentacaoForm.codBemImovel.value="";
	document.ocorrenciaDocumentacaoForm.action="bemImovel.do?action=pesquisarBemImovel";
	document.ocorrenciaDocumentacaoForm.submit();
}

function validarCampos()
{
	var form = document.ocorrenciaDocumentacaoForm;
	if(form.relDocumentacao.checked)
	{
		if(!form.docExistente.checked)
		{
			
			if(form.tipoDocumentacao.value == "")
			{
				alert("Campo 'Tipo de Documentação' é obrigatório!");
				form.tipoDocumentacao.focus();
				return false;	
				
			}
			if(form.anexo.value == "")
			{
				alert("Campo 'Arquivo' é obrigatório!");
				form.anexo.focus();
				return false;	
				
			}
			if(form.tipoDocumentacao.options[form.tipoDocumentacao.options.selectedIndex].value==${notificacao})//notificação
			{
				dataAux= new Date();
				dataAtual=dataAux.toLocaleDateString();
				 
			
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

				Trim(form.descricaoNotificacao);
				if (form.descricaoNotificacao.value == ""){
					alert("Campo 'Descrição da Notificação' é obrigatório!");
					form.descricaoNotificacao.focus();
					return false;
				}
				Trim(form.tsSolucao);
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
						alert("Campo 'Data da Solução' deve ser maior que 'Data da Notificação'");
						form.tsSolucao.focus();
						return false ;			
					}
					Trim(form.motivoSolucao);
					if (form.motivoSolucao.value == ""){
						alert("Campo 'Motivo da Solução' é obrigatório!");
						form.motivoSolucao.focus();
						return false;
					}
				}
			}
			
		}
		
	}
	Trim(form.descricaoOcorrencia);
	if(form.descricaoOcorrencia.value == "")
	{
		alert("Campo 'Descrição da Ocorrência' é obrigatório!");
		form.descricaoOcorrencia.focus();
		return false;
	}
	return true;
}


</script>

<body onload="verificarVisibilidade()">

<div id="conteudo">
	<div align="center">
	<h1>Manutenção de Ocorrência de Movimentação de Bem Imóvel</h1>

	<html:form action="/ocorrenciaDocumentacao.do?action=salvarOcorrenciaDocumentacao"  enctype="multipart/form-data" method="post"  onsubmit="return validarCampos() " >
	
	<html:hidden property="actionType" value="${ocorrenciaDocumentacaoForm.actionType}" />
	<html:hidden property="codBemImovel" value="${ocorrenciaDocumentacaoForm.codBemImovel}" />
	
	<!-- campos da tela de pesquisa de bens imoveis-->
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
			<td class="form_label" width="200">Identificação do Imóvel CPE: </td>
			<td>
				<c:out value="${ocorrenciaDocumentacaoForm.codBemImovel}"></c:out>
			</td>
		</tr>
	 	<tr id="lRelDocumentacao" style="display:none">
			<td class="form_label">Relacionado a Documentação :</td>
			<td colspan="3">
				<html:checkbox  property="relDocumentacao"  onchange="verificarVisibilidade();"/>
			</td>
		</tr>  
		</table>
		<div id="DocExistente" style="display:none">
			<table class="form_tabela" cellspacing="0" width="100%" >
				<tr >
					<td class="form_label"width="200" >Documentação Existente :</td>
					<td colspan="3">
						<html:checkbox property="docExistente"  styleId="docExistente" onchange="verificarVisibilidade();" />
					</td>
				</tr>
			</table>
		</div>
		
		<div id="Documentacao" style="display:none">
			<table class="form_tabela" cellspacing="0" width="100%" >
				<tr >
					<td class="form_label" width="200">*Documentação :</td>
					<td colspan="3"><html:select property="documentacao"
						styleId="documentacao">
						<html:option value="">-selecione-</html:option>
						<logic:present name="documentacaos">
							<html:options collection="documentacaos" property="codDocumentacao"
								labelProperty="descricao" />
						</logic:present>
					</html:select></td>
				</tr>
			</table>
		</div>
		<div id ="DocumentacaoOriginal" style="display:none">
			<table class="form_tabela" cellspacing="0" width="100%" >
				<tr  >
					<td class="form_label" width="200">Documentação Original :</td>
					<td colspan="3">
						<html:select property="documentacaoOriginal" styleId="documentacaoOriginal">
								<html:option value="">-selecione-</html:option>
								<logic:present name="documentacaosOriginal">
		 							<html:options collection="documentacaosOriginal" property="codDocumentacao" labelProperty="descricao" />
								</logic:present>
						</html:select>
								
					</td>
				</tr>
				<tr  >
					<td class="form_label">*Tipo Documentação :</td>
					<td colspan="3">
						<html:select property="tipoDocumentacao" onchange="verificarVisibilidade();">
								<html:option value="">-selecione-</html:option>
								<html:options collection="tiposDocumentacao" property="codTipoDocumentacao" labelProperty="descricao" />						
						</html:select>
								
					</td>
				</tr>
			</table>
		</div>
		<div id="Notificacao" style="display:none">
			<table class="form_tabela" cellspacing="0" width="100%" >
				<tr >
					<td class="form_label" width="200">*Data da Notificação :</td>
					<td>
						<html:text styleId="tsNotificacao" property="tsNotificacao"  size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));" />
						<img src="${icon_calendar}" width="16" height="15" id="btnCalendar" onclick="javascript:Calendario(document.getElementById('tsNotificacao'),this.id);" />
					</td>
				</tr>
				<tr  > 
		          <td class="form_label">*Prazo da Notificação :</td>
		          <td colspan="2"><html:text styleId="tsPrazoNotificacao" property="tsPrazoNotificacao"  size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));" />
		            <img src="${icon_calendar}" width="16" height="16" border="0"  id="btnCalendar1" onclick="javascript:Calendario(document.getElementById('tsPrazoNotificacao'),this.id);"></td>
		        </tr>
		        <tr > 
		          <td class="form_label">*Descrição da notificação :</td>
		          <td colspan="2"><html:textarea styleId="descricaoNotificacao" property="descricaoNotificacao" cols="120" rows="3"></html:textarea></td>
		        </tr>
		        <tr > 
		          <td class="form_label">Data da Solução :</td>
		          <td colspan="2"><html:text  styleId="tsSolucao" property="tsSolucao"  size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));"/> 
		           <img src="${icon_calendar}" width="16" height="16" border="0"  id="btnCalendar2" onclick="javascript:Calendario(document.getElementById('tsSolucao'),this.id);"></td>
		        </tr>
		        <tr > 
		          <td class="form_label">Motivo da Solução :</td>
		          <td colspan="2"><html:textarea styleId="motivoSolucao" property="motivoSolucao" cols="120" rows="3"></html:textarea></td>
		        </tr>
        	</table>
        </div>
        <div id="Arquivo" style="display:none">
	        <table class="form_tabela" cellspacing="0" width="100%" >
		        <tr > 
		          <td class="form_label" width="200">*Arquivo :</td>
		          
		          <td colspan="2"><html:file  maxlength="100" size="50"  property="anexo" styleId="anexo"  /> </td>
		        </tr>
	        </table>
        </div>
        
        <div>
	        <table class="form_tabela" cellspacing="0" width="100%" >
		        <tr> 
		          <td class="form_label" width="200">*Descrição da Ocorrência :</td>
		          <td colspan="2"><html:textarea styleId="descricaoOcorrencia" property="descricaoOcorrencia" cols="120" rows="3"></html:textarea></td>
		        </tr>
		        <tr> 
		          <td class="form_label">&nbsp;</td>
		          <td><a href="javascript:enviar();"><img src="${icon_adicionar}" width="16" height="16" border="0"> Adicionar </a></td>
		          <td>&nbsp;<a href="javascript:apagar()"><img src="${icon_apagar}" width="16" height="16" border="0" >Limpar</a></td>
		        </tr>
	      </table>
      </div>
      <hr>
      <p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
      <c:if test="${!empty requestScope.pagina.registros}">
  	  	<div id="conteudo_corpo">
		  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
			    <ch:lista bean="${pagina}" atributoId="codOcorrenciaDocumentacao,documentacao.codDocumentacao"/>
			    <ch:campo atributo="instanciaAtual" label="Visualizar Anexo" align="center" decorator="gov.pr.celepar.abi.util.DocumentacaoDecorator"/>
			    <ch:campo atributo="documentacao.anexo" label="Anexo" />
		        <ch:campo atributo="descricao" label="Descrição da Ocorrência" />
		        <ch:campo atributo="tsInclusao" label="Data de Inclusão" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data"/>
		        <ch:campo  atributo="nomeResponsavel" label="Responsável"  />
		        <ch:painel pagina="${link_editar_ocorrencia_documentacao}" classe="painel" atributoIndice="indice" />
		  	</ch:table>  
	  	</div>
	  	
  	</c:if>
      
      <div align="center"> 
        <input type="button" class="form_botao" name="btnCadastrar" value="Voltar" onMouseDown="voltar()">
      </div>
     
    </div>
    


  </html:form>
  

  </div>
</div>
</body>