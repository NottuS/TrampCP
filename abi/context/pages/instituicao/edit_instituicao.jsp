<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_email" value="/js/generic/email.js" />
<c:url var='link_pesquisar' value='/instituicao.do?action=pesquisar' />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_email}"></script>

<script language="javascript">

	function MascaraTelefone(campo) {
		campo.value = campo.value.replace(/[^0-9]+/g, '').replace(/^([0-9]+)([0-9]{4})$/, '$1-$2');
	}

	function MascaraCep(campo){
		campo.value = campo.value.replace(/[^0-9]+/g, '').replace(/^([0-9]+)([0-9]{3})$/, '$1-$2');
			
	}
	
	function validarCampos(){
	
		var form = document.instituicaoForm;
	
		
		Trim(form.sigla);
		if (form.sigla.value == ""){
			alert("Campo 'Sigla' é obrigatório!");
			form.sigla.focus();
			return false;
		}
		Trim(form.nome);
		if (form.nome.value == ""){
			alert("Campo 'Nome' é obrigatório!");
			form.nome.focus();
			return false;
		}
		Trim(form.descricaoRelatorio);
		if (form.descricaoRelatorio.value == ""){
			alert("Campo 'Descricao para Relatórios' é obrigatório!");
			form.descricaoRelatorio.focus();
			return false;
		}
		if (form.cep.value == ""){
			alert("Campo 'CEP' é obrigatório!");
			form.cep.focus();
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
		if (form.email.value != "" && !ValidaEmail(form.email)){
			alert("Campo 'Email' é inválido!");
			form.email.focus();
			return false;
		}
		if ('${imagemSessao}' == '' ){
			alert("Campo 'Logotipo da Instituição' é obrigatório!");
			return false;
		}

		form.municipio.value = form.codMunicipio.options[form.codMunicipio.selectedIndex].text;
		form.submit();
	}

	function voltar(){	
		var form = document.instituicaoForm;
		form.action="${link_pesquisar}";
		form.submit();
	}

	function carregarCamposPesquisaPadrao() {

		var form = document.instituicaoForm;			

		if (form.uf.value==''){
			form.uf.value='PR';
		}
	}

	function atualizarImagemLogo(){
		document.instituicaoForm.action="instituicao.do?action=atualizarImagemLogotipo";
		document.instituicaoForm.submit();
	}

		
		
</script>

<body onload="carregarCamposPesquisaPadrao()">

<c:choose> 
	<c:when test='${instituicaoForm.actionType == "incluir"}'>
		<c:set var="acao" value="instituicao.do?action=salvar"></c:set>
		<c:set var="titulo" value="Incluir"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="acao" value="instituicao.do?action=alterar"></c:set>
		<c:set var="titulo" value="Alterar"></c:set>
	</c:otherwise>
</c:choose>

<div id="conteudo">
	<div align="center">
	<h1>${titulo} Instituição</h1>

	<html:form action="${acao}" enctype="multipart/form-data" method="post" >
	<cep:main>
	<cep:form findOnType="true" codificacao="C" textoBusca="procurando...">

	<html:hidden property="codInstituicao" value="${instituicaoForm.codInstituicao}"/>
	<html:hidden property="actionType" value="${instituicaoForm.actionType}" />
	<html:hidden property="municipio" value="${instituicaoForm.municipio}" />
	<html:hidden property="conSigla" value="${instituicaoForm.conSigla}"/>
	<html:hidden property="conNome" value="${instituicaoForm.conNome}"/>
	

	<div id="conteudo_corpo">
	<table class="form_tabela" cellspacing="0" width="100%">
		<tr>
			<td class="form_label">* Sigla: </td>
			<td>
				<html:text property="sigla" maxlength="10" size="15"styleId="sigla" />
			</td>
		</tr>
		<tr>
			<td class="form_label">* Nome: </td>
			<td>
				<html:text property="nome" maxlength="200" size="80"styleId="nome" />
			</td>
		</tr>
		<tr>
			<td class="form_label">* Descrição para relatórios: </td>
			<td>
				<html:textarea property="descricaoRelatorio"  cols="80" rows="3" styleId="descricaoRelatorio" />
			</td>
		</tr>
		<tr>
			<td class="form_label">* CEP: </td>
			<td><cep:cep  name="cep" maxlength="8" value="${instituicaoForm.cep}" onkeyup="javascript:DigitaNumero(this);"  /></td>
		</tr>
		<tr>
			<td class="form_label">* UF: </td>
			<td><cep:uf name="uf" value="${instituicaoForm.uf}"/></td>

		</tr>
		<tr>
			<td class="form_label">* Município: </td>
			<td><cep:municipio name="codMunicipio" value="${instituicaoForm.codMunicipio}" /></td>
		</tr>
		<tr>
			<td class="form_label">Logradouro: </td>
			<td><cep:endereco name="logradouro" maxlength="100" size="60" value="${instituicaoForm.logradouro}" /></td>
		</tr>

		<tr>
			<td class="form_label">Número: </td>
			<td colspan="3"><cep:numero name="numero"
				value="${instituicaoForm.numero}" maxlength="15" size="15"/></td>
		</tr>
		<tr>
			<td class="form_label">Bairro: </td>
			<td><cep:bairro name="bairro" maxlength="60" size="60" value="${instituicaoForm.bairro}"/></td>
		</tr>
		<tr>
			<td class="form_label">Complemento: </td>
			<td colspan="3"><cep:complemento name="complemento"
				value="${instituicaoForm.complemento}" maxlength="60" size="60" /></td>
		</tr>
		<tr>
			<td class="form_label">Contato: </td>
			<td><html:text property="contato" maxlength="100" size="60" styleId="contato" /></td>
		</tr>
		<tr>
			<td class="form_label">Telefone: </td>
			<td>(<html:text property="telDdd" styleId="telDdd" maxlength="2" size="2" onkeyup="javascript:DigitaNumero(this);"/>) <html:text property="telNumero" styleId="telNumero" maxlength="9" size="9" onkeyup="javascript:MascaraTelefone(this);"/></td>
		</tr>
		<tr>
			<td class="form_label">Email: </td>
			<td>
				<html:text property="email" maxlength="150" size="80" styleId="email"/>
			</td>
		</tr>
		<tr> 
			<td class="form_label">* Logotipo da Instituição (tamanho máximo 20Kb) :</td>
        	<td ><html:file size="60"  property="logotipo" styleId="logotipo" onchange="atualizarImagemLogo()"/></td>
		</tr>
		<tr>
				<td nowrap class="form_label">Logotipo (imagem)</td>
				<td>
					<c:if test="${imagemSessao == null}">
						<html:img src="exibirFoto.jpg?codInstituicao=${instituicaoForm.codInstituicao}" border="1"/>
					</c:if>
					<c:if test="${imagemSessao != null}">
						<html:img src="exibirFoto.jpg?codInstituicao=imgSessao" border="1"/>
					</c:if>
				</td>
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