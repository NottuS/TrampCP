<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="link_voltar" value="/assinatura.do?action=voltar"/>

<c:url var="js_cpf" value="/js/generic/cpf.js"/>
<c:url var="js_funcoes" value="/js/generic/funcoes.js"/>
<c:url var="js_util" value="/js/generic/util.js"/>

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_cpf}"></script>

<script language="javascript">

	function validarCampos() {
		var form = document.assinaturaForm;

		Trim(form.nome);
		Trim(form.cpf);

		erro = '';
		if(form.instituicao.value == '' || form.instituicao.value == '0') {
			erro = erro + '- \"Instituição\".';
		}
		if(form.nome.value == '') {
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Nome\".';
		}
		if(!document.getElementById("indRespMaximo1").checked && !document.getElementById("indRespMaximo2").checked){
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Responsável Máximo\".';
			}
		if(!document.getElementById("administracaoDireta2").checked && !document.getElementById("administracaoDireta3").checked
			&& !document.getElementById("administracaoDireta1").checked){
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Administração\".';
		}
		if(form.orgao.value == '') {
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Órgão\".';
		}
		if(form.cargo.value == '') {
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Cargo\".';
		}

		if(erro != '') {
			msg = "Os seguintes campos são obrigatórios: \n";
			msg = msg + erro;  
			alert(msg);
			return false;
		}

		var form = document.forms[0];
		Trim(form.cpf);
		if(form.cpf.value != '') {
			if (!ValidaCPF(form.cpf)){
				alert ('CPF inválido!');
				return false;
			}
		}
	}

	function voltar() {
		var form = document.assinaturaForm;
		form.action='${link_voltar}';
		form.submit();
	}

	function carregarOrgao(){
		submitAjax('/abi/assinatura.do?action=carregarComboOrgao', document.forms[0], 'divComboOrgao',true);
	}

</script>

<body>

<c:choose> 
	<c:when test='${assinaturaForm.actionType == "incluir"}'>
		<c:set var="acao" value="assinatura.do?action=salvarAssinatura"></c:set>
		<c:set var="titulo" value="Incluir"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="acao" value="assinatura.do?action=alterarAssinatura"></c:set>
		<c:set var="titulo" value="Alterar"></c:set>
	</c:otherwise>
</c:choose>

<div id="conteudo">
	<div align="center">
	<h1>${titulo} Assinatura</h1>

	<html:form action="${acao}" onsubmit="return validarCampos();">
	
	<html:hidden property="codAssinatura" value="${assinaturaForm.codAssinatura}"/>
	<html:hidden property="actionType" value="${assinaturaForm.actionType}"/>
	<html:hidden property="pesqExec" value="${assinaturaForm.pesqExec}"/>
	<html:hidden property="isGpAdmGeralUsuarioLogado" value="${assinaturaForm.isGpAdmGeralUsuarioLogado}"/>
	<html:hidden property="conInstituicao"/>
	<html:hidden property="conCpf"/>
	<html:hidden property="conNome"/>

	<c:choose> 
		<c:when test='${assinaturaForm.actionType == "alterar"}'>
			<html:hidden property="cpf" value="${assinaturaForm.cpf}"/>
		</c:when>
	</c:choose>
	
     <div id="conteudo_corpo">	
	 <table class="form_tabela" cellspacing="0" width="100%">
		<tr>
			<c:choose> 
				<c:when test="${assinaturaForm.isGpAdmGeralUsuarioLogado == 'S'}">
					<td class="form_label">
						<c:choose> 
							<c:when test='${assinaturaForm.actionType == "incluir"}'>
								* Instituição: 
							</c:when>
							<c:otherwise>
								Instituição: 
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose> 
							<c:when test='${assinaturaForm.actionType == "incluir"}'>
								<html:select property="instituicao" onchange="javascript:carregarOrgao();">
								 	<html:options collection="listaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
							    </html:select>
							</c:when>
							<c:otherwise>
								<c:out value="${assinaturaForm.instituicaoDesc}"></c:out>
								<html:hidden property="instituicao" value="${assinaturaForm.instituicao}"/>
							</c:otherwise>
						</c:choose>
					</td>
				</c:when>
				<c:otherwise>
					<td colspan="2">
						<html:hidden property="instituicao" value="${assinaturaForm.instituicao}"/>
					</td>
				</c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<td class="form_label">CPF:</td>
			<td>
				<c:choose> 
					<c:when test='${assinaturaForm.actionType == "incluir"}'>
						<html:text property="cpf" size="17" maxlength="14" onkeyup="DigitaNumero(this);MascaraCPF(this,event);" onblur="DigitaNumero(this);MascaraCPF(this,event);"/>
					</c:when>
					<c:otherwise>
						<c:out value="${assinaturaForm.cpf}"></c:out>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="form_label" width="15%">* Nome: </td>
			<td><html:text property="nome" maxlength="150" size="100" /></td>
		</tr>
		<tr>
			<td class="form_label" width="15%">* Responsável Máximo: </td>
			<td>
				<html:radio value="1" property="indRespMaximo" styleId ="indRespMaximo1">Sim</html:radio>
				<html:radio value="2" property="indRespMaximo" styleId ="indRespMaximo2">Não</html:radio>
			</td>
		</tr>
	 	<tr>
	 		<td class="form_label">* Administração: </td>
	 		<td>
				<html:radio value="1" property="administracao" styleId ="administracaoDireta1" onchange="javascript:carregarOrgao();">Direta</html:radio>
				<html:radio value="2" property="administracao" styleId ="administracaoDireta2" onchange="javascript:carregarOrgao();">Indireta</html:radio>
				<html:radio value="3" property="administracao" styleId ="administracaoDireta3" onchange="javascript:carregarOrgao();">Terceiros</html:radio>
			</td>
		</tr>
	 	<tr>
	 		<td colspan="2">
				<div id="divComboOrgao">
					<jsp:include page="/pages/assinatura/_edit_comboOrgao.jsp"/>
				</div>
	 		</td>
	 	</tr>
		<c:if test="${assinaturaForm.incluidoPor != null}">
			<tr>
	        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${assinaturaForm.incluidoPor}"></c:out></td>
			</tr>
		</c:if>
		<c:if test="${assinaturaForm.alteradoPor != null}">
			<tr>
	        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${assinaturaForm.alteradoPor}"></c:out></td>
			</tr>
		</c:if>
		<c:if test="${assinaturaForm.excluidoPor != null}">
			<tr>
	        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${assinaturaForm.excluidoPor}"></c:out></td>
			</tr>
		</c:if>
	 </table>
  
   	<hr>
  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
 	<div align="center">
		<html:submit property="btnSalvar" styleClass="form_botao" value="${titulo}"/>
		<html:button property="btnCancelar" styleClass="form_botao" value="Voltar" onclick="voltar();"/>
	</div>	
  </div>

  </html:form>

  </div>
</div>
</body>
