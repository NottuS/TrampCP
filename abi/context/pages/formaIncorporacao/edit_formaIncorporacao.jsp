 <%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='link_pesquisar' value='/formaIncorporacao.do?action=pesquisarFormaIncorporacao' />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>


<script language="javascript">

	function voltar(){	
		document.formaIncorporacaoForm.descricao.value = "";
		document.formaIncorporacaoForm.action="${link_pesquisar}";
		document.formaIncorporacaoForm.submit();
	}
	
	function validarCampos(){
	
		var form = document.formaIncorporacaoForm;
		Trim(form.descricao);

		if (form.descricao.value == ""){
			alert("Campo 'Descrição' é obrigatório!");
			form.descricao.focus();
			return false;
		}

		form.submit();
	}
	
</script>

	<c:choose> 
		<c:when test='${formaIncorporacaoForm.actionType == "incluir"}'>
			<c:set var="acao" value="formaIncorporacao.do?action=salvarFormaIncorporacao"></c:set>
			<c:set var="titulo" value="Incluir"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="acao" value="formaIncorporacao.do?action=alterarFormaIncorporacao"></c:set>
			<c:set var="titulo" value="Alterar"></c:set>
		</c:otherwise>
	</c:choose>


<div id="conteudo">
	<div align="center">
	<h1>${titulo} Forma de Incorporação</h1>

	<html:form action="${acao}" onsubmit="return validarCampos()">
	<html:hidden property="codFormaIncorporacao" value="${formaIncorporacaoForm.codFormaIncorporacao}"/>
	<html:hidden property="actionType" value="${formaIncorporacaoForm.actionType}" />
	
     <div id="conteudo_corpo">	
	 <table class="form_tabela" cellspacing="0" width="100%">
		<tr>
			<td class="form_label">* Descrição:</td>
			<td><html:text  property="descricao" maxlength="60" size="60" styleId="descricao"  /></td>
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