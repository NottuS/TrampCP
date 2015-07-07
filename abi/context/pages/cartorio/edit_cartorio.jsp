<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>


<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='link_pesquisar' value='/cartorio.do?action=pesquisarCartorio' />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">

	function MascaraTelefone(campo) {
		campo.value = campo.value.replace(/[^0-9]+/g, '').replace(/^([0-9]+)([0-9]{4})$/, '$1-$2');
	}
	
	function validarCampos(){
	
		var form = document.cartorioForm;
	
		Trim(form.descricao);
		if (form.descricao.value == ""){
			alert("Campo 'Descrição' é obrigatório!");
			form.descricao.focus();
			return false;
		}
		if (form.uf.value == "0" || form.uf.value == ""){
			alert("Campo 'UF' é obrigatório!");
			form.uf.focus();
			return false;
		}
		if (form.codMunicipio.value == "0" || form.codMunicipio.value == ""){
			alert("Campo 'Município' é obrigatório!");
			form.codMunicipio.focus();
			return false;
		}

		form.municipio.value = form.codMunicipio.options[form.codMunicipio.selectedIndex].text;
		
		if((form.telDdd.value == '') != (form.telNumero.value == '')) {
			alert('Campo \"Telefone\" inválido.');
			return false;
		}
		if(!/^([0-9]{2})?$/.test(form.telDdd.value)) {
			alert('Campo \"DDD do Telefone\" inválido.');
			return false;
		}
		if(!/^([0-9]{4}-[0-9]{4})?$/.test(form.telNumero.value)) {
			alert('Campo \"Número do Telefone\" inválido.');
			return false;
		}

		form.submit();
	}

	function voltar(){	
		var form = document.cartorioForm;
		form.descricao.value = "";
		form.uf.value = "0";
		form.codMunicipio.value = "0";
		form.action="${link_pesquisar}";
		form.submit();
	}

	function carregarCamposPesquisaPadrao() {

		var form = document.cartorioForm;			

		if (form.uf.value==''){
			form.uf.value='PR';
		}
	}

		
	
		
</script>

<body onload="carregarCamposPesquisaPadrao()">

<c:choose> 
	<c:when test='${cartorioForm.actionType == "incluir"}'>
		<c:set var="acao" value="cartorio.do?action=salvarCartorio"></c:set>
		<c:set var="titulo" value="Incluir"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="acao" value="cartorio.do?action=alterarCartorio"></c:set>
		<c:set var="titulo" value="Alterar"></c:set>
	</c:otherwise>
</c:choose>

<div id="conteudo">
	<div align="center">
	<h1>${titulo} Cartório</h1>

	<html:form action="${acao}" >
	<cep:main>
	<cep:form findOnType="true" codificacao="C" textoBusca="procurando...">

	<html:hidden property="codCartorio" value="${cartorioForm.codCartorio}"/>
	<html:hidden property="actionType" value="${cartorioForm.actionType}" />
	<html:hidden property="municipio" value="${cartorioForm.municipio}" />
	<html:hidden property="conUf"/>
	<html:hidden property="conCodMunicipio"/>
	<html:hidden property="conDescricao"/>

	<div id="conteudo_corpo">
	<table class="form_tabela" cellspacing="0" width="100%">
		<tr>
			<td class="form_label">* Descrição: </td>
			<td><html:text property="descricao" maxlength="255" size="100"
				styleId="descricao" /></td>
		</tr>
		<tr>
			<td class="form_label">CEP: </td>
			<td><cep:cep name="cep" maxlength="8" value="${cartorioForm.cep}" onkeyup="javascript:DigitaNumero(this);"/></td>
		</tr>
		<tr>
			<td class="form_label">* UF: </td>
			<td><cep:uf name="uf" value="${cartorioForm.uf}"/></td>

		</tr>
		<tr>
			<td class="form_label">* Município: </td>
			<td><cep:municipio name="codMunicipio"
				value="${cartorioForm.codMunicipio}" /></td>
		</tr>
		<tr>
			<td class="form_label">Logradouro: </td>
			<td><cep:endereco name="logradouro" maxlength="100" size="60" value="${cartorioForm.logradouro}" /></td>
		</tr>

		<tr>
			<td class="form_label">Número: </td>
			<td colspan="3"><cep:numero name="numero"
				value="${cartorioForm.numero}" maxlength="15" size="15"/></td>
		</tr>
		<tr>
			<td class="form_label">Bairro: </td>
			<td><cep:bairro name="bairro" maxlength="50" size="50" value="${cartorioForm.bairro}"/></td>
		</tr>
		<tr>
			<td class="form_label">Complemento: </td>
			<td colspan="3"><cep:complemento name="complemento"
				value="${cartorioForm.complemento}" maxlength="60" size="60" /></td>
		</tr>
		<tr>
			<td class="form_label">Contato: </td>
			<td><html:text property="nomeContato" maxlength="100" size="60" styleId="nomeContato" /></td>
		</tr>
		<tr>
			<td class="form_label">Telefone: </td>
			<td>(<html:text property="telDdd" styleId="telDdd" maxlength="2" size="2" onkeyup="javascript:DigitaNumero(this);"/>) <html:text property="telNumero" styleId="telNumero" maxlength="9" size="9" onkeyup="javascript:MascaraTelefone(this);"/></td>
		</tr>
	</table>

   	<hr>
  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
 	<div align="center">
		<html:button property="btnSalvar" styleClass="form_botao" value="${titulo}" onclick="javascript:validarCampos();"/>
		<html:button property="btnCancelar" styleClass="form_botao" value="Voltar" onclick="voltar();" />
	</div>	
	<cep:box/>
  </div>
 
 </cep:form>
 </cep:main>
  </html:form>

  </div>
</div>
</body>