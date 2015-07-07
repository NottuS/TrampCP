<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="link_pesquisar" value="/classificacaoBemImovel.do?action=pesquisarClassificacaoBemImovel" />

<jsp:useBean id="agora" class="java.util.Date" />


<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">

	function Trim(obj) {
		Ltrim(obj);
	    Rtrim(obj);
	}

	function Rtrim(obj) {
		varx = obj.value;
		while(varx.substr(varx.length - 1, 1) == ' ') {
			varx = varx.substr(0, varx.length - 1);
		}
		obj.value = varx;
	}

	function Ltrim(obj) {
		varx = obj.value;
		while(varx.substr(0,1) == ' ') {
			varx = varx.substr(1, varx.length - 1);
		}
		obj.value = varx;
	}

	function voltar() {
		document.classificacaoBemImovelForm.descricao.value="";
		document.classificacaoBemImovelForm.action = '${link_pesquisar}';
		document.classificacaoBemImovelForm.submit();
	}

	function validarCampos() {

		var form = document.classificacaoBemImovelForm;
		Trim(form.descricao);

		if(form.descricao.value == '') {
			alert('Campo \"Descrição\" é obrigatório!');
			form.descricao.focus();
			return false;
		}

		form.submit();
	}

</script>

<c:choose>
	<c:when test='${classificacaoBemImovelForm.actionType == "incluir"}'>
		<c:set var="acao" value="classificacaoBemImovel.do?action=salvarClassificacaoBemImovel"></c:set>
		<c:set var="titulo" value="Incluir"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="acao" value="classificacaoBemImovel.do?action=alterarClassificacaoBemImovel"></c:set>
		<c:set var="titulo" value="Alterar"></c:set>
	</c:otherwise>
</c:choose>

<div id="conteudo">
	<div align="center">
	<h1>${titulo} Classificação de Bem Imóvel</h1>

	<html:form action="${acao}" onsubmit="return validarCampos()">
	<html:hidden property="codClassificacaoBemImovel" value="${classificacaoBemImovelForm.codClassificacaoBemImovel}"/>
	<html:hidden property="actionType" value="${classificacaoBemImovelForm.actionType}" />

	<div id="conteudo_corpo">
		 <table class="form_tabela" cellspacing="0" width="100%">
			<tr>
				<td class="form_label">* Descrição:</td>
				<td><html:text property="descricao" maxlength="30" size="30" styleId="descricao" /></td>
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
