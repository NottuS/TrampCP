<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<c:url var="js_maisinfo" value="/js/generic/maisinfo.js"/>
<c:url var="js_funcoes" value="/js/generic/funcoes.js"/>
<c:url var="js_util" value="/js/generic/util.js"/>
<c:url var="icon_info" value="/images/icon_info.png"/>
<c:url var="link_pesquisar" value="/tabelionato.do?action=pesquisarTabelionato"/>

<script language="JavaScript" type="text/JavaScript" src="${js_maisinfo}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">

	var id_crono;
	var cont_exec = 0;
	
	function selecionarMunicipio() {
		var form = document.tabelionatoForm;

		if(form.codMunicipio.length <= 1) return;

		var municipioUC = form.municipio.value.toUpperCase();
		var i;
		for(i = 0; i < form.codMunicipio.length; i++) {
			if(form.codMunicipio.options[i].text.toUpperCase() == municipioUC) {
				form.codMunicipio.selectedIndex = i;
				window.clearInterval(id_crono);
				return;
			}
		}
		if(++cont_exec == 50) {
			window.clearInterval(id_crono);
		}
	}

	function capturarDescrMunicipio(indice) {
		var form = document.tabelionatoForm;
		form.municipio.value = (indice == 0 ? '' : form.codMunicipio.options[indice].text);
	}

	function MascaraTelefone(campo) {
		campo.value = campo.value.replace(/[^0-9]+/g, '').replace(/^([0-9]+)([0-9]{4})$/, '$1-$2');
	}

	function validarCampos() {
		var form = document.tabelionatoForm;

		Trim(form.descricao);
		Trim(form.cep);
		Trim(form.logradouro);
		Trim(form.numero);
		Trim(form.bairro);
		Trim(form.complemento);
		Trim(form.nomeContato);
		Trim(form.telDdd);
		Trim(form.telNumero);

		erro = '';
		if(form.descricao.value == '') {
			erro = 'Campo \"Descrição\" é obrigatório.';
		}
		else if(form.cep.value != '' && !/^[0-9]{8}$/.test(form.cep.value)) {
			erro = 'Campo \"CEP\" inválido.';
		}
		else if(/^0?$/.test(form.uf.value)) {
			erro = 'Campo \"UF\" é obrigatório.';
		}
		else if(form.municipio.value == '') {
			erro = 'Campo \"Município\" é obrigatório.';
		}
		else if((form.telDdd.value == '') != (form.telNumero.value == '')) {
			erro = 'Campo \"Telefone\" inválido.';
		}
		else if(!/^([0-9]{2})?$/.test(form.telDdd.value)) {
			erro = 'Campo \"DDD do Telefone\" inválido.';
		}
		else if(!/^([0-9]{4}-[0-9]{4})?$/.test(form.telNumero.value)) {
			erro = 'Campo \"Número do Telefone\" inválido.';
		}
		if(erro != '') {
			alert(erro);
			return false;
		}
	}

	function voltar() {
		var form = document.tabelionatoForm;
		form.descricao.value = '';
		form.uf.value = '0';
		form.codMunicipio.value = '';
		form.municipio.value = '';
		form.action='${link_pesquisar}';
		form.submit();
	}

	function carregarCamposPesquisaPadrao() {

		var form = document.tabelionatoForm;			

		if (form.uf.value==''){
			form.uf.value='PR';
		}
	}

	function execucaoPeriodica() {
		id_crono = window.setInterval(selecionarMunicipio, 1000);
	}
	
	execucaoPeriodica();
	
		
</script>

<body onload="carregarCamposPesquisaPadrao()">

<c:choose> 
	<c:when test='${tabelionatoForm.actionType == "incluir"}'>
		<c:set var="acao" value="tabelionato.do?action=salvarTabelionato"></c:set>
		<c:set var="titulo" value="Incluir"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="acao" value="tabelionato.do?action=alterarTabelionato"></c:set>
		<c:set var="titulo" value="Alterar"></c:set>
	</c:otherwise>
</c:choose>

<div id="conteudo">
	<div align="center">
	<h1>${titulo} Tabelionato</h1>

	<html:form action="${acao}" onsubmit="capturarDescrMunicipio(document.tabelionatoForm.codMunicipio.selectedIndex); return validarCampos();">
	<cep:main>
	<cep:form findOnType="false" codificacao="C" textoBusca="procurando...">
	
	<html:hidden property="codTabelionato" value="${tabelionatoForm.codTabelionato}"/>
	<html:hidden property="actionType" value="${tabelionatoForm.actionType}"/>
	<html:hidden property="conDescricao"/>
	<html:hidden property="conUF"/>
	<html:hidden property="conMunicipio"/>
	
     <div id="conteudo_corpo">	
	 <table class="form_tabela" cellspacing="0" width="100%">
		<tr>
			<td class="form_label">* Descrição: </td>
			<td><html:text property="descricao" value="${tabelionatoForm.descricao}" maxlength="255" size="100" /></td>
		</tr>
		<tr>
			<td class="form_label">CEP: </td>
			<td><cep:cep name="cep" value="${tabelionatoForm.cep}" size="8" maxlength="8" onkeyup="javascript:DigitaNumero(this);"/> <cep:box/></td>
		</tr>
		<tr>
			<td class="form_label">* UF: </td>
			<td><cep:uf name="uf" value="${tabelionatoForm.uf}" onchange="capturarDescrMunicipio(0);"/></td>
		</tr>
		<tr>
			<td class="form_label">* Município: </td>
			<td>
				<cep:municipio name="codMunicipio" onchange="capturarDescrMunicipio(this.selectedIndex);"/>
				<html:hidden property="municipio" value="${tabelionatoForm.municipio}"/>
			</td>
		</tr>
		<tr>
			<td class="form_label">Logradouro: </td>
			<td><cep:endereco name="logradouro" value="${tabelionatoForm.logradouro}" maxlength="100" size="100"/></td>
		</tr>
		<tr>
			<td class="form_label">Número: </td>
			<td><cep:numero name="numero" value="${tabelionatoForm.numero}" maxlength="15" size="15" /></td>
		</tr>
		<tr>
			<td class="form_label">Bairro: </td>
			<td><cep:bairro name="bairro" value="${tabelionatoForm.bairro}" maxlength="50" size="50"/></td>
		</tr>
		<tr>
			<td class="form_label">Complemento: </td>
			<td><cep:complemento name="complemento" value="${tabelionatoForm.complemento}" maxlength="60" size="60"/></td>
		</tr>
		<tr>
			<td class="form_label">Contato: </td>
			<td><html:text property="nomeContato" maxlength="100" size="60" styleId="nomeContato"/></td>
		</tr>
		<tr>
			<td class="form_label">Telefone: </td>
			<td>(<html:text property="telDdd" styleId="telDdd" maxlength="2" size="2" onkeyup="javascript:DigitaNumero(this);"/>) <html:text property="telNumero" styleId="telNumero" maxlength="9" size="9" onkeyup="javascript:MascaraTelefone(this);"/></td>
		</tr>
	 </table>
   	<hr>
  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
 	<div align="center">
		<html:submit property="btnSalvar" styleClass="form_botao" value="${titulo}"/>
		<html:button property="btnCancelar" styleClass="form_botao" value="Voltar" onclick="voltar();"/>
	</div>	
   </div>
  
  </cep:form>
  </cep:main>
  </html:form>

  </div>
</div>
</body>
