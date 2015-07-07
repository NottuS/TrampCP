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
		document.doacaoBemImovelForm.action="doacaoBemImovel.do?action=voltar";
		document.doacaoBemImovelForm.submit();
	}

	function buscaBemImovel() {
		var form = document.doacaoBemImovelForm;
		if (form.nrBemImovel.value.length > 0 && form.instituicao.value.length > 0) {
			submitAjax('/abi/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.nrBemImovel.value+'&uc=doacao&codInstituicao='+form.instituicao.value+'&codDoacao='+form.codDoacao.value, document.forms[0], "dadosBemImovel",true);
		}
	}

	function habilitarCampos() {
		var form = document.doacaoBemImovelForm;
		if (document.getElementById("tipoRevogDev1").checked && !document.getElementById("tipoRevogDev2").checked){
			form.motivo.disabled=false;
			form.nrOficio.disabled=true;
			form.nrOficio.value="";
			form.nrOficio.focus();
		} else if (!document.getElementById("tipoRevogDev1").checked && document.getElementById("tipoRevogDev2").checked){
			form.nrOficio.disabled=false;
			form.motivo.disabled=true;
			form.motivo.value="";
			form.motivo.focus();
		}
	}

	function validarCampos() {
		limpaMensagens();
		var form = document.doacaoBemImovelForm;
		var erro = '';
		
		if(!document.getElementById("tipoRevogDev1").checked && !document.getElementById("tipoRevogDev2").checked){
			erro = erro + '- \"Tipo\".';
		}
		if(document.getElementById("tipoRevogDev1").checked && !document.getElementById("tipoRevogDev2").checked){
			Trim(form.motivo);
			if(form.motivo.value == '') {
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Motivo\".';
			}
		}
		if(!document.getElementById("tipoRevogDev1").checked && document.getElementById("tipoRevogDev2").checked){
			Trim(form.nrOficio);
			if(form.nrOficio.value == '') {
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Nº do Ofício\".';
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
	<c:when test='${doacaoBemImovelForm.actionType == "excluir"}'>
		<c:set var="acao" value="doacaoBemImovel.do?action=excluirDoacaoBemImovel"></c:set>
		<c:set var="titulo" value="Excluir"></c:set>
	</c:when>
	<c:when test='${doacaoBemImovelForm.actionType == "revogDev"}'>
		<c:set var="acao" value="doacaoBemImovel.do?action=revogDevDoacaoBemImovel"></c:set>
		<c:set var="titulo" value="Revogar/Devolver"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="acao" value="doacaoBemImovel.do"></c:set>
		<c:set var="titulo" value="Exibir"></c:set>
	</c:otherwise>
</c:choose>

<div id="conteudo">
	<div align="center">
	<h1 >${titulo} Doação de Bem Imóvel </h1>
		
	<div id="conteudo_corpo">
		<html:form action="${acao}" onsubmit="return validarCampos();">
			<html:hidden property="codDoacao" value="${doacaoBemImovelForm.codDoacao}"/>
			<html:hidden property="actionType" value="${doacaoBemImovelForm.actionType}"/>
			<html:hidden property="pesqExec" value="${doacaoBemImovelForm.pesqExec}"/>
			<html:hidden property="bemImovelSimplificado" value="${bemImovelSimplificado}"/>
			<html:hidden property="conNrTermo" value="${doacaoBemImovelForm.conNrTermo}"/>
			<html:hidden property="conCodBemImovel" value="${doacaoBemImovelForm.conCodBemImovel}"/>
			<html:hidden property="conNrBemImovel" value="${doacaoBemImovelForm.conNrBemImovel}"/>
			<html:hidden property="conInstituicao" value="${doacaoBemImovelForm.conInstituicao}"/>
			<html:hidden property="conProtocolo" value="${doacaoBemImovelForm.conProtocolo}"/>
			<html:hidden property="conSituacao" value="${doacaoBemImovelForm.conSituacao}"/>
			<html:hidden property="conAdministracao" value="${doacaoBemImovelForm.conAdministracao}"/>
			<html:hidden property="conOrgaoResponsavel" value="${doacaoBemImovelForm.conOrgaoResponsavel}"/>
			<html:hidden property="uf" value="${doacaoBemImovelForm.uf}"/>
			<html:hidden property="codMunicipio" value="${doacaoBemImovelForm.codMunicipio}"/>
			<html:hidden property="codBemImovel" value="${doacaoBemImovelForm.codBemImovel}"/>
			<html:hidden property="nrBemImovel" value="${doacaoBemImovelForm.nrBemImovel}"/>
			<html:hidden property="instituicao" value="${doacaoBemImovelForm.instituicao}"/>
			<html:hidden property="isGpAdmGeralUsuarioLogado" value="${doacaoBemImovelForm.isGpAdmGeralUsuarioLogado}"/>

			<table cellspacing="0" class="form_tabela" width="100%">
				<tr>
					<td class="form_label">Nº do Termo:</td>
					<td> <c:out value="${doacaoBemImovelForm.codDoacao}"></c:out> </td>
				</tr>
				<tr>
					<td class="form_label">Status:</td>
					<td> <c:out value="${doacaoBemImovelForm.status}"></c:out> </td>
				</tr>
				<c:if test='${doacaoBemImovelForm.instituicaoDesc != null }'>
					<tr>
						<td class="form_label">Instituição:</td>
						<td> <c:out value="${doacaoBemImovelForm.instituicaoDesc}"></c:out> </td>
					</tr>
				</c:if>
				<tr>
					<td class="form_label" width="250">Bem Imóvel:</td>
					<td> <c:out value="${doacaoBemImovelForm.nrBemImovel}"></c:out> </td>
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
			 		<td class="form_label">Administração: </td>
					<td> <c:out value="${doacaoBemImovelForm.administracaoDesc}"></c:out> </td>
				</tr>
				<tr>
				 	<td class="form_label">Órgão Responsável: </td>
					<td> <c:out value="${doacaoBemImovelForm.orgaoResponsavelDesc}"></c:out> </td>
				</tr>
				<tr> 
		          	<td class="form_label">Data de Início de Vigência: </td>
					<td> <c:out value="${doacaoBemImovelForm.dtInicioVigencia}"></c:out> </td>
			   	</tr>
				<tr> 
		          	<td class="form_label">Data de Fim de Vigência (Previsão): </td>
					<td>
						 <c:out value="${doacaoBemImovelForm.dtFimVigencia}"></c:out> 
					 </td>
			   	</tr>
				<tr>
					<td class="form_label">Protocolo:</td>
					<td> <c:out value="${doacaoBemImovelForm.protocolo}"></c:out> </td>
				</tr>
			</table>
			
			<c:if test="${listItemDoacao != null && listItemDoacao.quantidade > 0}">
				<table cellspacing="0" class="form_tabela" width="100%">
					<tr>
						<td width="100%">
						    <div id="divItensDoados"> 
								<h2>Itens Doados</h2>
								<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
									<ch:lista bean="${listItemDoacao}"  atributoId="codItemDoacao"/>
									<ch:campo atributo="tipoDescricao" label="<center>Tipo</center>" align="center"/>
									<ch:campo atributo="utilizacao" label="<center>Utilização</center>" align="center"/>
							        <ch:campo atributo="doacaoMetros" label="Doação em m²" decorator="gov.pr.celepar.abi.util.Area" />
							        <ch:campo atributo="doacaoPercentualFormatado" label="Doação em %"/>
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
									<td> <c:out value="${doacaoBemImovelForm.projetoLeiDesc}"></c:out> </td>
								</tr>
								<tr>
									<td class="form_label">Nº da Lei:</td>
									<td> <c:out value="${doacaoBemImovelForm.numeroLei}"></c:out> </td>
								</tr>
								<tr> 
						         	<td class="form_label">Data da Assinatura: </td>
									<td> <c:out value="${doacaoBemImovelForm.dataAssinaturaLei}"></c:out> </td>
							   	</tr>
								<tr> 
						         	<td class="form_label">Data da Publicação: </td>
									<td> <c:out value="${doacaoBemImovelForm.dataPublicacaoLei}"></c:out> </td>
							   	</tr>
								<tr>
									<td class="form_label">Nº do Diário Oficial:</td>
									<td> <c:out value="${doacaoBemImovelForm.nrDioeLei}"></c:out> </td>
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
									<ch:lista bean="${listAssinatura}"  atributoId="codAssinaturaDoacao"/>
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
			<c:if test='${doacaoBemImovelForm.actionType == "revogDev" || doacaoBemImovelForm.tipoRevogDevDesc != null }'>
				<table cellspacing="0" class="form_tabela" width="100%">
					<tr>
						<td width="100%">
						    <div id="divRevogDev"> 
								<h2>Revogação/Devolução</h2>
								<table width="100%">
									<c:choose> 
										<c:when test='${doacaoBemImovelForm.actionType == "revogDev"}'>
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
											<tr> 
									         	<td class="form_label">* Nº do Ofício: </td>
												<td> 
													<html:text property="nrOficio" styleId="nrOficio" disabled="true" maxlength="15" size="15" onkeyup="DigitaNumero(this);"/>
												</td>
										   	</tr>
										</c:when>
										<c:otherwise>
										 	<tr>
										 		<td class="form_label" width="250">Tipo: </td>
												<td> <c:out value="${doacaoBemImovelForm.tipoRevogDevDesc}"></c:out> </td>
											</tr>
											<c:if test='${doacaoBemImovelForm.motivo != null }'>
												<tr>
													<td class="form_label">Motivo:</td>
													<td> <c:out value="${doacaoBemImovelForm.motivo}"></c:out> </td>
												</tr>
											</c:if>
											<c:if test='${doacaoBemImovelForm.nrOficio != null }'>
												<tr> 
										         	<td class="form_label">Nº do Ofício: </td>
													<td> <c:out value="${doacaoBemImovelForm.nrOficio}"></c:out> </td>
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
				<c:if test="${doacaoBemImovelForm.incluidoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${doacaoBemImovelForm.incluidoPor}"></c:out></td>
					</tr>
				</c:if>
				<c:if test="${doacaoBemImovelForm.alteradoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${doacaoBemImovelForm.alteradoPor}"></c:out></td>
					</tr>
				</c:if>
				<c:if test="${doacaoBemImovelForm.excluidoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${doacaoBemImovelForm.excluidoPor}"></c:out></td>
					</tr>
				</c:if>

			</table>
        
		   	<hr>
			<c:if test='${doacaoBemImovelForm.actionType == "revogDev"}'>
		  		<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
		  	</c:if>
		 	<div align="center">
				<c:choose> 
					<c:when test='${doacaoBemImovelForm.actionType == "excluir"}'>
			 			<html:submit value="Confirmar" styleClass="form_botao" />
						<html:button property="" styleClass="form_botao" value="Voltar" onclick="voltar();" />
					</c:when>
					<c:when test='${doacaoBemImovelForm.actionType == "revogDev"}'>
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
	var form = document.doacaoBemImovelForm;
	if (form.nrBemImovel.value.length > 0 && form.instituicao.value.length > 0){
		buscaBemImovel();
	}
	habilitarCampos();
};
</script>   
