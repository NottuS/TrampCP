<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />

<c:url var='link_pesquisar' value='/tipoEdificacao.do?action=pesquisarTipoEdificacao' />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">

	function voltar(){	
		document.tipoEdificacaoForm.descricao.value = "";
		document.tipoEdificacaoForm.action="${link_pesquisar}";
		document.tipoEdificacaoForm.submit();
	}
	
	function validarCampos(){
	
		var form = document.tipoEdificacaoForm;
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
		<c:when test='${tipoEdificacaoForm.actionType == "incluir"}'>
			<c:set var="acao" value="tipoEdificacao.do?action=salvarTipoEdificacao"></c:set>
			<c:set var="titulo" value="Incluir"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="acao" value="tipoEdificacao.do?action=alterarTipoEdificacao"></c:set>
			<c:set var="titulo" value="Alterar"></c:set>
		</c:otherwise>
	</c:choose>


<div id="conteudo">
	<div align="center">
	<h1>${titulo} Tipo de Edificação</h1>

	<html:form action="${acao}" onsubmit="return validarCampos()">
	<html:hidden property="codTipoEdificacao" value="${tipoEdificacaoForm.codTipoEdificacao}"/>
	<html:hidden property="actionType" value="${tipoEdificacaoForm.actionType}" />
	
     <div id="conteudo_corpo">	
	 <table class="form_tabela" cellspacing="0" width="100%">
		<tr>
			<td class="form_label">* Descrição:</td>
			<td><html:text  property="descricao" maxlength="60" size="60" styleId="descricao"/></td>
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