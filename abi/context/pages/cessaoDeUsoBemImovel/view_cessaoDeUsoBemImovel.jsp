<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var='icon_calendar' value='/images/icon_calendario.png' />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_data" value="/js/generic/data.js" />
<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var="js_prototype" value="/js/generic/prototype.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />

<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_prototype}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>

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
		document.cessaoDeUsoBemImovelForm.action="cessaoDeUsoBemImovel.do?action=voltar";
		document.cessaoDeUsoBemImovelForm.submit();
	}

	function buscaBemImovel() {
		var form = document.cessaoDeUsoBemImovelForm;
		if (form.actionType.value == "exibir" || (form.nrBemImovel.value.length > 0 && form.instituicao.value.length > 0)) {
			submitAjax('/abi/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.nrBemImovel.value+'&uc=cessao&codIInstituicao='+form.instituicao.value+'&codCessaoUso='+form.codCessaoDeUso.value, document.forms[0], "dadosBemImovel",true);
		}
	}

	function habilitarCampos() {
		var form = document.cessaoDeUsoBemImovelForm;
		if (document.getElementById("tipoRevogDev1").checked || document.getElementById("tipoRevogDev2").checked){
			form.motivo.disabled=false;
			form.motivo.focus();
		}
	}

	function validarCampos() {
		limpaMensagens();
		var form = document.cessaoDeUsoBemImovelForm;
		var erro = '';
		
		if(!document.getElementById("tipoRevogDev1").checked && !document.getElementById("tipoRevogDev2").checked){
			erro = erro + '- \"Tipo\".';
		}
		if(document.getElementById("tipoRevogDev1").checked || document.getElementById("tipoRevogDev2").checked){
			Trim(form.motivo);
			if(form.motivo.value == '') {
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Motivo\".';
			}
		}
		
		if(erro != '') {
			msg = "Os seguintes campos são obrigatórios: \n";
			msg = msg + erro;  
			alert(msg);
			return false;
		}

		return true;

	}

</script>

<body>

<c:choose> 
	<c:when test='${cessaoDeUsoBemImovelForm.actionType == "excluir"}'>
		<c:set var="acao" value="cessaoDeUsoBemImovel.do?action=excluirCessaoDeUsoBemImovel"></c:set>
		<c:set var="titulo" value="Excluir"></c:set>
	</c:when>
	<c:when test='${cessaoDeUsoBemImovelForm.actionType == "revogDev"}'>
		<c:set var="acao" value="cessaoDeUsoBemImovel.do?action=revogDevCessaoDeUsoBemImovel"></c:set>
		<c:set var="titulo" value="Revogar/Devolver"></c:set>
	</c:when>
	<c:when test='${cessaoDeUsoBemImovelForm.actionType == "renovar"}'>
		<c:set var="acao" value="cessaoDeUsoBemImovel.do?action=renovarCessaoDeUsoBemImovel"></c:set>
		<c:set var="titulo" value="Renovar"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="acao" value="cessaoDeUsoBemImovel.do"></c:set>
		<c:set var="titulo" value="Exibir"></c:set>
	</c:otherwise>
</c:choose>

<div id="conteudo">
	<div align="center">
	<h1 >${titulo} Cessão De Uso de Bem Imóvel </h1>
		
	<div id="conteudo_corpo">
		<html:form action="${acao}" onsubmit="return validarCampos();">
			<html:hidden property="codCessaoDeUso" value="${cessaoDeUsoBemImovelForm.codCessaoDeUso}"/>
			<html:hidden property="actionType" value="${cessaoDeUsoBemImovelForm.actionType}"/>
			<html:hidden property="bemImovelSimplificado" value="${bemImovelSimplificado}"/>
			<html:hidden property="pesqExec" value="${cessaoDeUsoBemImovelForm.pesqExec}"/>
			<html:hidden property="conNrTermo" value="${cessaoDeUsoBemImovelForm.conNrTermo}"/>
			<html:hidden property="conCodBemImovel" value="${cessaoDeUsoBemImovelForm.conCodBemImovel}"/>
			<html:hidden property="conNrBemImovel" value="${cessaoDeUsoBemImovelForm.conNrBemImovel}"/>
			<html:hidden property="conInstituicao" value="${cessaoDeUsoBemImovelForm.conInstituicao}"/>
			<html:hidden property="conProtocolo" value="${cessaoDeUsoBemImovelForm.conProtocolo}"/>
			<html:hidden property="conSituacao" value="${cessaoDeUsoBemImovelForm.conSituacao}"/>
			<html:hidden property="conOrgaoCessionario" value="${cessaoDeUsoBemImovelForm.conOrgaoCessionario}"/>
			<html:hidden property="uf" value="${cessaoDeUsoBemImovelForm.uf}"/>
			<html:hidden property="codMunicipio" value="${cessaoDeUsoBemImovelForm.codMunicipio}"/>
			<html:hidden property="conDtInicioVigencia" value="${cessaoDeUsoBemImovelForm.conDtInicioVigencia}"/>
			<html:hidden property="conDtFimVigencia" value="${cessaoDeUsoBemImovelForm.conDtFimVigencia}"/>
			<html:hidden property="codBemImovel" value="${cessaoDeUsoBemImovelForm.codBemImovel}"/>
			<html:hidden property="nrBemImovel" value="${cessaoDeUsoBemImovelForm.nrBemImovel}"/>
			<html:hidden property="instituicao" value="${cessaoDeUsoBemImovelForm.instituicao}"/>
			<html:hidden property="isGpAdmGeralUsuarioLogado" value="${cessaoDeUsoBemImovelForm.isGpAdmGeralUsuarioLogado}"/>

			<table cellspacing="0" class="form_tabela" width="100%">
				<tr>
					<td class="form_label">Nº do Termo:</td>
					<td> <c:out value="${cessaoDeUsoBemImovelForm.codCessaoDeUso}"></c:out> </td>
				</tr>
				<tr>
					<td class="form_label">Status:</td>
					<td> <c:out value="${cessaoDeUsoBemImovelForm.status}"></c:out> </td>
				</tr>
				<c:if test='${cessaoDeUsoBemImovelForm.instituicaoDesc != null }'>
					<tr>
						<td class="form_label">Instituição:</td>
						<td> <c:out value="${cessaoDeUsoBemImovelForm.instituicaoDesc}"></c:out> </td>
					</tr>
				</c:if>
				<tr>
					<td class="form_label" width="250">Bem Imóvel:</td>
					<td> <c:out value="${cessaoDeUsoBemImovelForm.nrBemImovel}"></c:out> </td>
				</tr>
				<tr>
					<td class="form_label"></td>
					<td> 
						<div id="dadosBemImovel"> 
							<c:if test="${bemImovelSimplificado != null}">
								<tiles:insert definition="viewDadosBemImovelSimplificadoAjaxDef"/>
							</c:if>
						</div>
					</td>
				</tr>
				<tr>
				 	<td class="form_label">Órgão Cessionário: </td>
					<td> <c:out value="${cessaoDeUsoBemImovelForm.orgaoCessionario}"></c:out> </td>
				</tr>
				<tr> 
		          	<td class="form_label">Data de Início de Vigência: </td>
					<td> <c:out value="${cessaoDeUsoBemImovelForm.dtInicioVigencia}"></c:out> </td>
			   	</tr>
				<tr> 
		          	<td class="form_label">Data de Fim de Vigência (Previsão): </td>
					<td>
						 <c:out value="${cessaoDeUsoBemImovelForm.dtFimVigencia}"></c:out> 
					 </td>
			   	</tr>
				<tr>
					<td class="form_label">Protocolo:</td>
					<td> <c:out value="${cessaoDeUsoBemImovelForm.protocolo}"></c:out> </td>
				</tr>
			</table>
			<c:if test="${listItemCessaoDeUso != null && listItemCessaoDeUso.quantidade > 0}">
				<table cellspacing="0" class="form_tabela" width="100%" >
					<tr>
						<td width="100%">
						    <div id="divItensCessao"> 
								<h2>Itens Cessionados</h2>
								<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
									<ch:lista bean="${listItemCessaoDeUso}"  atributoId="codItemCessaoDeUso"/>
									<ch:campo atributo="tipoDescricao" label="<center>Tipo</center>" align="center"/>
									<ch:campo atributo="caracteristica" label="<center>Característica</center>" align="center"/>
									<ch:campo atributo="situacaoDominial" label="<center>Situação Dominial</center>" align="center"/>
									<ch:campo atributo="utilizacao" label="<center>Utilização</center>" align="center"/>
							        <ch:campo atributo="areaMetroQuadrado" label="Cessão de Uso em m²" decorator="gov.pr.celepar.abi.util.Area" />
							        <ch:campo atributo="areaPercentualFormatado" label="Cessão de Uso em %"/>
									<ch:campo atributo="observacao" label="<center>Observação</center>" align="center"/>
									<ch:campo atributo="outrasInformacoes" label="<center>Outras Informações</center>" align="center"/>
								</ch:table>
							</div>
						</td>
					</tr>
				</table>
			</c:if>
			<table cellspacing="0" class="form_tabela" width="100%">
				<tr>
					<td width="100%">
					    <div id="divDadosLei"> 
							<h2>Dados da Lei</h2>
							<table width="100%">
							 	<tr>
							 		<td class="form_label" width="250">Projeto de Lei: </td>
									<td> <c:out value="${cessaoDeUsoBemImovelForm.projetoLeiDesc}"></c:out> </td>
								</tr>
								<tr>
									<td class="form_label">Nº da Lei:</td>
									<td> <c:out value="${cessaoDeUsoBemImovelForm.numeroLei}"></c:out> </td>
								</tr>
								<tr> 
						         	<td class="form_label">Data da Assinatura: </td>
									<td> <c:out value="${cessaoDeUsoBemImovelForm.dataAssinaturaLei}"></c:out> </td>
							   	</tr>
								<tr> 
						         	<td class="form_label">Data da Publicação: </td>
									<td> <c:out value="${cessaoDeUsoBemImovelForm.dataPublicacaoLei}"></c:out> </td>
							   	</tr>
								<tr>
									<td class="form_label">Nº do Diário Oficial:</td>
									<td> <c:out value="${cessaoDeUsoBemImovelForm.nrDioeLei}"></c:out> </td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
			<c:if test="${listAssinatura != null && listAssinatura.quantidade > 0}">
				<table cellspacing="0" class="form_tabela" width="100%">
					<tr>
						<td width="100%">
						    <div id="divAssinaturas"> 
								<h2>Assinaturas</h2>
								<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
									<ch:lista bean="${listAssinatura}"  atributoId="codAssinaturaCessaoDeUso"/>
									<ch:campo atributo="assinatura.orgao.siglaDescricao" label="<center>Órgão</center>" align="center"/>
									<ch:campo atributo="assinatura.cargoAssinatura.descricao" label="<center>Cargo</center>" align="center"/>
									<ch:campo atributo="assinatura.nome" label="<center>Nome</center>" align="center"/>
									<ch:campo atributo="ordem" label="<center>Ordem</center>" align="center"/>
								</ch:table>
							</div>
						</td>
					</tr>
				</table>
			</c:if>
			<c:if test='${cessaoDeUsoBemImovelForm.actionType == "revogDev" || cessaoDeUsoBemImovelForm.tipoRevogDevDesc != null }'>
				<table cellspacing="0" class="form_tabela" width="100%">
					<tr>
						<td width="100%">
						    <div id="divRevogDev"> 
								<h2>Revogação/Devolução</h2>
								<table width="100%">
									<c:choose> 
										<c:when test='${cessaoDeUsoBemImovelForm.actionType == "revogDev"}'>
										 	<tr>
										 		<td class="form_label" width="250">* Tipo: </td>
										 		<td>
													<html:radio value="1" property="tipoRevogDev" styleId ="tipoRevogDev1" onchange="javascript:habilitarCampos();">Revogação</html:radio>
													<html:radio value="2" property="tipoRevogDev" styleId ="tipoRevogDev2" onchange="javascript:habilitarCampos();">Devolução</html:radio>
												</td>
											</tr>
											<tr>
												<td class="form_label">* Motivo:</td>
												<td><html:textarea  property="motivo" cols="100" rows="10" disabled="true" styleId="motivo" onkeyup="TamanhoMax(this, 3000)"/></td>
											</tr>
										</c:when>
										<c:otherwise>
										 	<tr>
										 		<td class="form_label" width="250">Tipo: </td>
												<td> <c:out value="${cessaoDeUsoBemImovelForm.tipoRevogDevDesc}"></c:out> </td>
											</tr>
											<c:if test='${cessaoDeUsoBemImovelForm.motivo != null }'>
												<tr>
													<td class="form_label">Motivo:</td>
													<td> <c:out value="${cessaoDeUsoBemImovelForm.motivo}"></c:out> </td>
												</tr>
											</c:if>
										</c:otherwise>
									</c:choose>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</c:if>
			<table cellspacing="0" class="form_tabela" width="100%">
				<c:if test="${cessaoDeUsoBemImovelForm.incluidoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${cessaoDeUsoBemImovelForm.incluidoPor}"></c:out></td>
					</tr>
				</c:if>
				<c:if test="${cessaoDeUsoBemImovelForm.alteradoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${cessaoDeUsoBemImovelForm.alteradoPor}"></c:out></td>
					</tr>
				</c:if>
				<c:if test="${cessaoDeUsoBemImovelForm.excluidoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${cessaoDeUsoBemImovelForm.excluidoPor}"></c:out></td>
					</tr>
				</c:if>
			</table>
			<c:if test='${cessaoDeUsoBemImovelForm.actionType == "renovar"}'>
			   	<hr>
				<table cellspacing="0" class="form_tabela" width="100%">
					<tr>
			        	<td align="center" class="msg_descricao">
							<font color="red"><b>
							Confirma a geração da renovação do Termo de Cessão de Uso para o Bem Imóvel <c:out value="${cessaoDeUsoBemImovelForm.nrBemImovel}"/> ?
							</b></font>
						</td>
					</tr>
				</table>
		  	</c:if>
		   	<hr>
			<c:if test='${cessaoDeUsoBemImovelForm.actionType == "revogDev"}'>
		  		<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
		  	</c:if>
		 	<div align="center">
				<c:choose> 
					<c:when test='${cessaoDeUsoBemImovelForm.actionType == "excluir"}'>
			 			<html:submit value="Confirmar" styleClass="form_botao" />
						<html:button property="" styleClass="form_botao" value="Voltar" onclick="voltar();" />
					</c:when>
					<c:when test='${cessaoDeUsoBemImovelForm.actionType == "revogDev"}'>
			 			<html:submit value="Confirmar" styleClass="form_botao" />
						<html:button property="" styleClass="form_botao" value="Voltar" onclick="voltar();" />
					</c:when>
					<c:when test='${cessaoDeUsoBemImovelForm.actionType == "renovar"}'>
			 			<html:submit value="Confirmar" styleClass="form_botao" />
						<html:button property="" styleClass="form_botao" value="Voltar" onclick="voltar();" />
					</c:when>
					<c:otherwise>
						<html:button property="" styleClass="form_botao" value="Voltar" onclick="voltar();" />
					</c:otherwise>
				</c:choose>
			</div>	
			</html:form>
		</div>
	</div>
</div>
   
</body>
<script language="javascript">
window.onload = function(){
	var form = document.cessaoDeUsoBemImovelForm;
	if (form.nrBemImovel.value.length > 0 && form.instituicao.value.length > 0){
		buscaBemImovel();
	}
	habilitarCampos();
};
</script>   
