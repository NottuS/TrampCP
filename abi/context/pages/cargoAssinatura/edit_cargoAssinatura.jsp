<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='link_pesquisar' value='/cargoAssinatura.do?action=pesquisarCargoAssinatura' />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">

	function voltar(){	
		document.cargoAssinaturaForm.descricao.value = "";
		document.cargoAssinaturaForm.action="${link_pesquisar}";
		document.cargoAssinaturaForm.submit();
	}
	
	function validarCampos(){
		
		var form = document.cargoAssinaturaForm;
		/*** Valida descricao da forma de incorporacao  ***/
		if(form.indGrupoSentinela == form.adm){
			if(form.instituicao.value == "0" || form.instituicao.value == ""){
				alert("Campo 'Instituição' é obrigatório!");
				form.instituicao.focus();
				return false;
			}
		}
		if (form.descricao.value == ""){
			alert("Campo 'Descrição' é obrigatório!");
			form.descricao.focus();
			return false;
		}
		// minimo 5 caracteres na descrição
		if (form.descricao.value.length < 5){
			alert("Campo 'Descrição' têm que ter no mínimo 5 caracteres!!");
			form.descricao.focus();
			return false;
		}
		
		/*** Envia o formulário para o servidor ***/
		form.submit();
	}
	
</script>

	<c:choose> 
		<c:when test='${cargoAssinaturaForm.actionType == "incluir"}'>
			<c:set var="acao" value="cargoAssinatura.do?action=salvarCargoAssinatura"></c:set>
			<c:set var="titulo" value="Incluir"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="acao" value="cargoAssinatura.do?action=alterarCargoAssinatura"></c:set>
			<c:set var="titulo" value="Alterar"></c:set>
		</c:otherwise>
	</c:choose>


<div id="conteudo">
	<div align="center">
	<h1>${titulo} Cargo da Assinatura</h1>
	
     <div id="conteudo_corpo">	
	 <html:form action="${acao}" method="post">
	 	<html:hidden property="codCargoAssinatura" value="${cargoAssinaturaForm.codCargoAssinatura}"/>
		<html:hidden property="actionType" value="${cargoAssinaturaForm.actionType}" />
	 
		<table class="form_tabela" cellspacing="0">
			<c:if test='${cargoAssinaturaForm.indGrupoSentinela == cargoAssinaturaForm.adm}'>
				<c:choose> 
					<c:when test='${titulo == "Incluir"}'>
						<tr>
							<td class="form_label" align="right">* Instituição:</td>
							<td>
								<html:select property="instituicao" >
									<html:options collection="listaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />
								</html:select>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td class="form_label" align="right">Instituição:</td>
							<td>
								<c:out value="${cargoAssinaturaForm.instituicaoDesc}"></c:out>
								<html:hidden property="instituicao" value="${cargoAssinaturaForm.instituicao}"/>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test='${cargoAssinaturaForm.indGrupoSentinela != cargoAssinaturaForm.adm}'>
				<html:hidden property="instituicao" value="${cargoAssinaturaForm.instituicao}"/>
			</c:if>
			<tr>
				<td class="form_label">* Descrição:</td>
				<td><html:text  property="descricao" maxlength="60" size="60" styleId="descricao" /></td>
			</tr>

		</table>
  
  </html:form>

   	<hr>
  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
 	<div align="center">
		<html:button property="btnSalvar" styleClass="form_botao" value="${titulo}" onclick="validarCampos();" />
		<html:button property="btnCancelar" styleClass="form_botao" value="Voltar" onclick="voltar();" />
	</div>	
	
  </div>

  </div>
</div>