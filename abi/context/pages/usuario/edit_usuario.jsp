<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_ajaxutil" value="/js/ajaxutil.js" />
<c:url var="js_prototype" value="/js/generic/prototype.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_cpf" value="/js/generic/cpf.js"/>

<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_ajaxutil}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_prototype}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_cpf}"></script>

<c:url var='icon_adicionar' value='/images/icon_adicionar1.png'/>
<c:url var='icon_excluir' value='/images/icon_excluir.png'/>
<c:url var='icon_localizar' value='/images/icon_localizar.png' />
<c:url var='icon_limpar' value='/images/icon_apagar.png' />

<script language="javascript">
	function limpaMensagens() {
		if(document.getElementById("__msgVerificador")!=null)
		 	document.getElementById("__msgVerificador").innerHTML = "";
		if(document.getElementById("msg")!=null) 
			document.getElementById("msg").innerHTML = "";
		if(document.getElementById("msgs_servidor")!=null)
			document.getElementById("msgs_servidor").innerHTML = "";
	}

	function voltar() {
		document.manterUsuarioForm.action="manterUsuario.do?action=voltar";
		document.manterUsuarioForm.submit();
	}

	function carregarComboOrgao(){
		var form = document.manterUsuarioForm;
		if((document.getElementById("administracao1").checked || document.getElementById("administracao2").checked || document.getElementById("administracao3").checked) && document.getElementById("instituicao").value!=""){
			form.orgao.disabled=false;
			submitAjax('/abi/manterUsuario.do?action=carregarComboOrgao', document.forms[0], 'divComboOrgao',true);
		}else{
			form.orgao.disabled=true;
			form.orgao.value="";
		}
	}

	function validarCamposGrupo() {
		if(validarCampos()) {
			var form = document.manterUsuarioForm;
			var erro = '';
			
			if(form.grupo.value == '') {
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Grupo\".';
			}
			
			if(erro != '') {
				msg = "Os seguintes campos são obrigatórios: \n";
				msg = msg + erro;  
				alert(msg);
				return false;
			}
	
			return true;
		}

	}
	
	function validarCamposOrgao() {
		if(validarCampos()) {
			var form = document.manterUsuarioForm;
			var erro = '';
			
			if(form.orgao.value == '') {
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Órgão\".';
			}
			
			if(erro != '') {
				msg = "Os seguintes campos são obrigatórios: \n";
				msg = msg + erro;  
				alert(msg);
				return false;
			}
			return true;
		}
	}
	
	function validarCampos() {
		limpaMensagens();
		var form = document.manterUsuarioForm;
		var erro = '';
		
		if(form.cpf.value == '') {
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"CPF\".';
		}
		Trim(form.nome);
		if(form.nome.value == " ") {
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Nome\".';
		}
		Trim(form.login);
		if(form.login.value == " ") {
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Login\".';
		}
		Trim(form.mail);
		if(form.mail.value == " ") {
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"E-mail\".';
		}
		
		if(erro != '') {
			msg = "Os seguintes campos são obrigatórios: \n";
			msg = msg + erro;  
			alert(msg);
			return false;
		}

		return true;

	}

	
	
	function excluirGrupo(codGrupo){
		if (codGrupo != "") {
			if(confirm('Tem certeza que deseja excluir?')) {
				submitAjax('/abi/manterUsuario.do?action=excluirGrupoManterUsuario&grupo='+codGrupo, document.manterUsuarioForm, 'divListaGrupo',false);
			}
		} else {
			alert ('Selecione um item para excluir!');
		}
	}
	
	function adicionarOrgao() {
		if (validarCamposOrgao()){
			submitAjax('/abi/manterUsuario.do?action=adicionarOrgaoManterUsuario&orgao='+document.manterUsuarioForm.orgao.value, document.manterUsuarioForm, 'divListaGrupo',false);
		}

		
	}

	function excluirOrgao(codOrgao){
		if (codOrgao != "") {
			if(confirm('Tem certeza que deseja excluir?')) {
				
				submitAjax('/abi/manterUsuario.do?action=excluirOrgaoManterUsuario&orgao='+codOrgao, document.manterUsuarioForm, 'divListaGrupo',false);
			}
		} else {
			alert ('Selecione um item para excluir!');
		}
	}

	function salvarUsuario() {
		if (validarCampos()){
			var form = document.manterUsuarioForm;
			document.manterUsuarioForm.action='manterUsuario.do?action=salvarManterUsuario';
			document.manterUsuarioForm.submit();
		}
	}

	function alterarUsuario() {
		if (validarCampos()){
			var form = document.manterUsuarioForm;
			document.manterUsuarioForm.action='manterUsuario.do?action=alterarManterUsuario';
			document.manterUsuarioForm.submit();
		}
	}

	function limparCamposGrupo() {
		var form = document.manterUsuarioForm;
		form.grupo.value="";
	}
	
	function limparCamposOrgao() {
		var form = document.manterUsuarioForm;
		form.orgao.value="";
	}

	function validarCPF() {
		var form = document.manterUsuarioForm;
		if(form.cpf.value != "") {
			if (!ValidaCPF(form.cpf)){
				alert ('CPF inválido!');
				return false;
			}
			document.manterUsuarioForm.action='manterUsuario.do?action=verificaUsuario';
			document.manterUsuarioForm.submit();
		}
	}
	
	function validarLogin() {
		var form = document.manterUsuarioForm;
		if(form.login.value != "") {
			document.manterUsuarioForm.action='manterUsuario.do?action=verificaLoginUsuario';
			document.manterUsuarioForm.submit();
		}
	}

	function desabilitaCampos() {
		var form = document.manterUsuarioForm;
		form.nome.setAttribute('readonly',true);
		form.login.setAttribute('readonly',true);
		form.mail.setAttribute('readonly',true);
	}
	
	function habilitaCampos() {
		var form = document.manterUsuarioForm;
		form.nome.removeAttribute('readonly');
		form.login.removeAttribute('readonly');
		form.mail.removeAttribute('readonly');
		form.nome.focus();
	}

	window.onload = function(){
		var form = document.manterUsuarioForm;
		if (form.desabilitaOrgao.value == 'false') {
			document.getElementById("administracao1").disabled=false;
			document.getElementById("administracao2").disabled=false;
			document.getElementById("administracao3").disabled=false;
			form.orgao.disabled=false;
			document.getElementById("divBtOrgao").style.display="block";
		} else {
			document.getElementById("administracao1").disabled=true;
			document.getElementById("administracao2").disabled=true;
			document.getElementById("administracao3").disabled=true;
			form.orgao.disabled=true;
			form.orgao.value="";
			document.getElementById("divBtOrgao").style.display="none";
		}
		if (form.desabilitaCampo.value == 'true') {
			desabilitaCampos();
		} else {
			habilitaCampos();
		} 
	};

</script>

<c:choose> 
	<c:when test='${manterUsuarioForm.actionType == "incluir"}'>
		<c:set var="acao" value="manterUsuario.do?action=salvarManterUsuario"></c:set>
		<c:set var="titulo" value="Incluir"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="acao" value="manterUsuario.do?action=alterarManterUsuario"></c:set>
		<c:set var="titulo" value="Alterar"></c:set>
	</c:otherwise>
</c:choose>

<div id="conteudo">
	<div align="center">
	<h1 >${titulo} Usuário </h1>
		
	<div id="conteudo_corpo">
		<html:form action="${acao}" onsubmit="return validarCampos()">
			<html:hidden property="codUsuario" value="${manterUsuarioForm.codUsuario}"/>
			<html:hidden property="actionType" value="${manterUsuarioForm.actionType}"/>
			
			
			<html:hidden property="idSentinela"/>
			<html:hidden property="pesqExec"/>
			<html:hidden property="conGrupo"/>
			<html:hidden property="conAdministracao"/>
			<html:hidden property="conOrgao"/>
			<html:hidden property="conCpf"/>
			<html:hidden property="conNome"/>
			<html:hidden property="conSituacao"/>

			<table cellspacing="0" class="form_tabela" width="100%">
				<tr>
					<td colspan="2" width="100%">
						<table width="100%">
							<tr>
								<td class="form_label" width="200">*CPF: </td>
								<td>
									<c:choose> 
										<c:when test='${manterUsuarioForm.actionType == "incluir"}'>
											<html:text property="cpf" size="17" maxlength="14" onkeyup="DigitaNumero(this);MascaraCPF(this,event);" onblur="DigitaNumero(this);MascaraCPF(this,event);" onchange="validarCPF();" />
										</c:when>
										<c:otherwise>
											<html:text property="cpf" readonly="true" />
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<td class="form_label">*Nome: </td>
								<td>
									<html:text maxlength="100" size="100" property="nome" />
								</td>
							</tr>
							<tr>
								<td class="form_label">*Login: </td>
								<td>
									<html:text maxlength="30" size="30" property="login" onchange="validarLogin();" />
								</td>
							</tr>
							<tr>
								<td class="form_label">*E-mail: </td>
								<td>
									<html:text maxlength="150" size="100" property="mail" />
								</td>
							</tr>
							<tr>
								<td class="form_label">*Situação: </td>
								<td>
									<html:radio value="1" property="situacao" styleId ="situacao1">Ativo</html:radio>
									<html:radio value="0" property="situacao" styleId ="situacao2">Inativo</html:radio>
								</td>
							</tr>
						</table>
					</td>
				</tr>						
				<tr>
					<td colspan="2" width="100%">
							<div id="divListaGrupo">
								<tiles:insert page="/pages/usuario/_edit_listGrupoUsuario.jsp"/>
							</div>
					</td>
				</tr>
				<c:if test="${manterUsuarioForm.incluidoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${manterUsuarioForm.incluidoPor}"></c:out></td>
					</tr>
				</c:if>
				<c:if test="${manterUsuarioForm.alteradoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${manterUsuarioForm.alteradoPor}"></c:out></td>
					</tr>
				</c:if>
				<c:if test="${manterUsuarioForm.excluidoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${manterUsuarioForm.excluidoPor}"></c:out></td>
					</tr>
				</c:if>
			</table>
        
		   	<hr>
		  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
		 	<div align="center">
				<c:choose> 
					<c:when test='${manterUsuarioForm.actionType == "incluir"}'>
			 			<html:button property="incluir" value="Confirmar" styleClass="form_botao" onclick="salvarUsuario();" />
					</c:when>
					<c:when test='${manterUsuarioForm.actionType == "alterar"}'>
			 			<html:button property="alterar" value="Confirmar" styleClass="form_botao" onclick="alterarUsuario();" />
					</c:when>
				</c:choose>
				<html:button property="btnCancelar" styleClass="form_botao" value="Voltar" onclick="voltar();"/>
			</div>	
			</html:form>
		</div>
	</div>
</div>
