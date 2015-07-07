 <%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='link_pesquisar' value='/orgao.do?action=pesquisarOrgao' />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">

	function voltar(){	
		document.orgaoForm.descricao.value = "";
		document.orgaoForm.sigla.value = "";
		document.orgaoForm.indTipoAdministracao.value = "";
		document.orgaoForm.action="${link_pesquisar}";
		document.orgaoForm.submit();
	}
	
	function validarCampos(){
	
		var form = document.orgaoForm;
		Trim(form.descricao);
		Trim(form.sigla);

		if (document.forms[0].indGrupoSentinela.value == document.forms[0].adm.value &&document.forms[0].actionType.value != 'alterar'){
			if (form.conInstituicao.value == ""){
				alert("Campo 'Instituição' é obrigatório!");
				form.conInstituicao.focus();
				return false;
			}
		}
		if (form.sigla.value == ""){
			alert("Campo 'Sigla' é obrigatório!");
			form.sigla.focus();
			return false;
		}
		if (form.descricao.value == ""){
			alert("Campo 'Descrição' é obrigatório!");
			form.descricao.focus();
			return false;
		}
		inputs = document.getElementsByTagName("input");
		for (j = 0; j < inputs.length; j++ ) {	
			if (inputs[j].type == "radio" && inputs[j].name == "indTipoAdministracao" && inputs[j].checked) {
				possuiChecado = true;
			}
		}
		if (!possuiChecado) {
			document.getElementById("msg_ajax").innerHTML = "";
			alert("Campo 'Tipo de Administração' é obrigatório!");
			return false;	
		} 
		
		form.submit();
	}
	
</script>

	<c:choose> 
		<c:when test='${orgaoForm.actionType == "incluir"}'>
			<c:set var="acao" value="orgao.do?action=salvarOrgao"></c:set>
			<c:set var="titulo" value="Incluir"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="acao" value="orgao.do?action=alterarOrgao"></c:set>
			<c:set var="titulo" value="Alterar"></c:set>
		</c:otherwise>
	</c:choose>


<div id="conteudo">
	<div align="center">
	<h1>${titulo} Órgão</h1>

	<html:form action="${acao}" onsubmit="return validarCampos()">
	<html:hidden property="codOrgao" value="${orgaoForm.codOrgao}"/>
	<html:hidden property="actionType" value="${orgaoForm.actionType}" />
	<html:hidden property="indGrupoSentinela" />
    <html:hidden property="adm" />
    
	
     <div id="conteudo_corpo">	
	 <table class="form_tabela" cellspacing="0" width="100%">
		<c:if test="${orgaoForm.indGrupoSentinela == orgaoForm.adm && orgaoForm.actionType != 'alterar'}">
			<tr>
				<td class="form_label" align="right">* Instituição:</td>
				<td colspan="2">
			 		<html:select property="conInstituicao">
				 	 <html:option value="">-Qualquer-</html:option>
					 <html:options collection="listaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
				    </html:select>
				</td>	
			</tr>
		</c:if>
		<c:if test="${orgaoForm.indGrupoSentinela == orgaoForm.adm && orgaoForm.actionType == 'alterar'}">
			<tr>
				<td class="form_label" align="right">Instituição:</td>
				<td colspan="2">${orgaoForm.instituicao}</td>	
			</tr>
		</c:if>
		<tr>
			<td class="form_label">* Sigla:</td>
			<td><html:text  property="sigla" maxlength="50" size="30" styleId="sigla" /></td>
		</tr>
		<tr>
			<td class="form_label">* Descrição:</td>
			<td><html:text  property="descricao" maxlength="150" size="80" styleId="descricao" /></td>
		</tr>
		<tr>
			<td class="form_label">* Tipo de Administração: </td>
			<td colspan="2">				
				<input type="radio" value="1" name="indTipoAdministracao"     <c:if test="${orgaoForm.indTipoAdministracao=='1'}">checked="checked"</c:if> />Direta
				<input type="radio" value="2" name="indTipoAdministracao" <c:if test="${orgaoForm.indTipoAdministracao=='2'}">checked="checked"</c:if> />Indireta
				<input type="radio" value="3" name="indTipoAdministracao" <c:if test="${orgaoForm.indTipoAdministracao=='3'}">checked="checked"</c:if> />Terceiros
			</td>
		</tr>
	 </table>
  
   	<hr>
  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
 	<div align="center">
		<html:button property="btnSalvar" styleClass="form_botao" value="${titulo}" onclick="validarCampos();" />
		<html:button property="btnCancelar" styleClass="form_botao" value="Voltar" onclick="voltar();" />
	</div>	
	
  </div>

  </html:form>

  </div>
</div>