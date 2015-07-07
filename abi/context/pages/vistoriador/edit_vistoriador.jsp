 <%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='link_pesquisar' value='/vistoriador.do?action=pesquisarVistoriador' />
<c:url var="js_cpf" value="/js/generic/cpf.js"/>

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_cpf}"></script>

<script language="javascript">

	function voltar(){	
		document.vistoriadorForm.action="${link_pesquisar}";
		document.vistoriadorForm.submit();
	}
	
	function validarCampos(){

		var form = document.vistoriadorForm;
		Trim(form.nome);
		Trim(form.cpf);
		if (document.forms[0].indGrupoSentinela.value == document.forms[0].adm.value &&document.forms[0].actionType.value != 'alterar'){
			if (form.instituicao.value == "0"){
				alert("Campo 'Instituição' é obrigatório!");
				form.instituicao.focus();
				return false;
			}
		}
		if (form.cpf.value == ""){
			alert("Campo 'CPF' é obrigatório!");
			form.cpf.focus();
			return false;
		}
		if (!ValidaCPF(form.cpf)){
			alert ('CPF inválido!');
			form.cpf.focus();
			return false;
		}
		if (form.nome.value == ""){
			alert("Campo 'Nome' é obrigatório!");
			form.nome.focus();
			return false;
		} 
			
		form.submit();
	}
	
</script>

	<c:choose> 
		<c:when test='${vistoriadorForm.actionType == "incluir"}'>
			<c:set var="acao" value="vistoriador.do?action=salvarVistoriador"></c:set>
			<c:set var="titulo" value="Incluir"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="acao" value="vistoriador.do?action=alterarVistoriador"></c:set>
			<c:set var="titulo" value="Alterar"></c:set>
		</c:otherwise>
	</c:choose>


<div id="conteudo">
	<div align="center">
	<h1>${titulo} Vistoriador</h1>

	<html:form action="${acao}" onsubmit="return validarCampos()">
	<html:hidden property="codVistoriador" value="${vistoriadorForm.codVistoriador}"/>
	<html:hidden property="actionType" value="${vistoriadorForm.actionType}" />
	<html:hidden property="indGrupoSentinela" />
    <html:hidden property="adm" />
    
     <div id="conteudo_corpo">	
	 <table class="form_tabela" cellspacing="0" width="100%">
		<c:if test="${vistoriadorForm.indGrupoSentinela == vistoriadorForm.adm && vistoriadorForm.actionType != 'alterar'}">
			<tr>
				<td class="form_label" align="right">* Instituição:</td>
				<td>
			 		<html:select property="instituicao">
					 <html:options collection="listaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
				    </html:select>
				</td>	
			</tr>
		</c:if>
		<c:if test="${vistoriadorForm.indGrupoSentinela == vistoriadorForm.adm && vistoriadorForm.actionType == 'alterar'}">
			<tr>
				<td class="form_label" align="right">Instituição:</td>
				<td>
					<c:out value="${vistoriadorForm.instituicaoDesc}"></c:out>
					<html:hidden property="instituicao" value="${vistoriadorForm.instituicao}"/>
				</td>
			</tr>
		</c:if>
		<tr>
			<c:if test='${vistoriadorForm.actionType == "incluir"}'>
				<td class="form_label">* CPF:</td>
				<td><html:text property="cpf" styleId="cpf" size="17" maxlength="14" onkeyup="DigitaNumero(this);MascaraCPF(this,event);" onblur="DigitaNumero(this);MascaraCPF(this,event);"/></td>
			</c:if>
			<c:if test='${vistoriadorForm.actionType == "alterar"}'>
			<html:hidden property="cpf" value="${vistoriadorForm.cpf}" />
				<td class="form_label"> CPF:</td>
				<td>${vistoriadorForm.cpf}</td>
			</c:if>
		</tr>
		<tr>
			<td class="form_label">* Nome:</td>
			<td><html:text  property="nome" maxlength="150" size="100" styleId="nome" /></td>
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