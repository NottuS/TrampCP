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
		document.transferenciaBemImovelForm.action="transferenciaBemImovel.do?action=voltar";
		document.transferenciaBemImovelForm.submit();
	}

	function buscaBemImovel() {
		var form = document.transferenciaBemImovelForm;
		if (form.nrBemImovel.value.length > 0 && form.instituicao.value.length > 0) {
			submitAjax('/abi/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.nrBemImovel.value+'&uc=transferencia&codInstituicao='+form.instituicao.value+'&codTransferencia='+form.codTransferencia.value, document.forms[0], "dadosBemImovel",true);
		}
	}

	function habilitarCampos() {
		var form = document.transferenciaBemImovelForm;
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
		var form = document.transferenciaBemImovelForm;
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
	<c:when test='${transferenciaBemImovelForm.actionType == "excluir"}'>
		<c:set var="acao" value="transferenciaBemImovel.do?action=excluirTransferenciaBemImovel"></c:set>
		<c:set var="titulo" value="Excluir"></c:set>
	</c:when>
	<c:when test='${transferenciaBemImovelForm.actionType == "revogDev"}'>
		<c:set var="acao" value="transferenciaBemImovel.do?action=revogDevTransferenciaBemImovel"></c:set>
		<c:set var="titulo" value="Revogar/Devolver"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="acao" value="transferenciaBemImovel.do"></c:set>
		<c:set var="titulo" value="Exibir"></c:set>
	</c:otherwise>
</c:choose>

<div id="conteudo">
	<div align="center">
	<h1 >${titulo} Transferência de Uso de Bem Imóvel </h1>
		
	<div id="conteudo_corpo">
		<html:form action="${acao}" onsubmit="return validarCampos();">
			<html:hidden property="codTransferencia" value="${transferenciaBemImovelForm.codTransferencia}"/>
			<html:hidden property="actionType" value="${transferenciaBemImovelForm.actionType}"/>
			<html:hidden property="pesqExec" value="${transferenciaBemImovelForm.pesqExec}"/>
			<html:hidden property="bemImovelSimplificado" value="${bemImovelSimplificado}"/>
			<html:hidden property="conNrTermo" value="${transferenciaBemImovelForm.conNrTermo}"/>
			<html:hidden property="conCodBemImovel" value="${transferenciaBemImovelForm.conCodBemImovel}"/>
			<html:hidden property="conNrBemImovel" value="${transferenciaBemImovelForm.conNrBemImovel}"/>
			<html:hidden property="conInstituicao" value="${transferenciaBemImovelForm.conInstituicao}"/>
			<html:hidden property="conProtocolo" value="${transferenciaBemImovelForm.conProtocolo}"/>
			<html:hidden property="conSituacao" value="${transferenciaBemImovelForm.conSituacao}"/>
			<html:hidden property="conOrgaoCessionario" value="${transferenciaBemImovelForm.conOrgaoCessionario}"/>
			<html:hidden property="uf" value="${transferenciaBemImovelForm.uf}"/>
			<html:hidden property="codMunicipio" value="${transferenciaBemImovelForm.codMunicipio}"/>
			<html:hidden property="codBemImovel" value="${transferenciaBemImovelForm.codBemImovel}"/>
			<html:hidden property="nrBemImovel" value="${transferenciaBemImovelForm.nrBemImovel}"/>
			<html:hidden property="instituicao" value="${transferenciaBemImovelForm.instituicao}"/>
			<html:hidden property="isGpAdmGeralUsuarioLogado" value="${transferenciaBemImovelForm.isGpAdmGeralUsuarioLogado}"/>
			<html:hidden property="indOperadorOrgao" />

			<table cellspacing="0" class="form_tabela" width="100%">
				<tr>
					<td class="form_label">Nº do Termo:</td>
					<td> <c:out value="${transferenciaBemImovelForm.codTransferencia}"></c:out> </td>
				</tr>
				<tr>
					<td class="form_label">Status:</td>
					<td> <c:out value="${transferenciaBemImovelForm.status}"></c:out> </td>
				</tr>
				<c:if test='${transferenciaBemImovelForm.instituicaoDesc != null }'>
					<tr>
						<td class="form_label">Instituição:</td>
						<td> <c:out value="${transferenciaBemImovelForm.instituicaoDesc}"></c:out> </td>
					</tr>
				</c:if>
				<tr>
					<td class="form_label" width="250">Bem Imóvel:</td>
					<td> <c:out value="${transferenciaBemImovelForm.nrBemImovel}"></c:out> </td>
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
				 	<td class="form_label">Órgão de Destino: </td>
					<td> <c:out value="${transferenciaBemImovelForm.orgaoCessionario}"></c:out> </td>
				</tr>
				<tr> 
		          	<td class="form_label">Data de Início de Vigência: </td>
					<td> <c:out value="${transferenciaBemImovelForm.dtInicioVigencia}"></c:out> </td>
			   	</tr>
				<tr> 
		          	<td class="form_label">Data de Fim de Vigência (Previsão): </td>
					<td><c:out value="${transferenciaBemImovelForm.dtFimVigencia}"></c:out> </td>
			   	</tr>
				<tr>
					<td class="form_label">Protocolo:</td>
					<td> <c:out value="${transferenciaBemImovelForm.protocolo}"></c:out> </td>
				</tr>
			</table>
			<c:if test="${listItemTransferencia != null && listItemTransferencia.quantidade > 0}">
				<table cellspacing="0" class="form_tabela" width="100%">
					<tr>
						<td width="100%">
						    <div id="divItensTransferidos"> 
								<h2>Itens Transferidos</h2>
								<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
									<ch:lista bean="${listItemTransferencia}"  atributoId="codItemTransferencia"/>
									<ch:campo atributo="tipoDescricao" label="<center>Tipo</center>" align="center"/>
									<ch:campo atributo="caracteristica" label="<center>Característica</center>" align="center"/>
									<ch:campo atributo="situacaoDominial" label="<center>Situação Dominial</center>" align="center"/>
									<ch:campo atributo="utilizacao" label="<center>Utilização</center>" align="center"/>
							        <ch:campo atributo="transferenciaMetros" label="Transferência em m²" decorator="gov.pr.celepar.abi.util.Area" />
							        <ch:campo atributo="transferenciaPercentualFormatado" label="Transferência em %"/>
									<ch:campo atributo="observacao" label="<center>Observação</center>" align="center"/>
									<ch:campo atributo="outrasInformacoes" label="<center>Outras Informações</center>" align="center"/>
								</ch:table>
							</div>
						</td>
					</tr>
				</table>
			</c:if>
			<c:if test="${listAssinatura != null && listAssinatura.quantidade > 0}">
				<table cellspacing="0" class="form_tabela" width="100%">
					<tr>
						<td width="100%">
						    <div id="divAssinaturas"> 
								<h2>Assinaturas</h2>
								<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
									<ch:lista bean="${listAssinatura}"  atributoId="codAssinaturaTransferencia"/>
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
			<c:if test='${transferenciaBemImovelForm.codStatus == "1"}'>
				<h2>Documento de Informação</h2>
				<table cellspacing="0" class="form_tabela" width="100%">
					<tr>
						<td class="form_label" width="250">Texto:</td>
						<td><html:textarea  style="font-size:medium;" property="textoDocInformacao" cols="100" rows="15" readonly="true" styleId="textoDocInformacao"/> </td>
					</tr>
					<tr>
						<td colspan="2">
							<c:if test="${listAssinaturaDoc != null && listAssinaturaDoc.quantidade > 0}">
								<table cellspacing="0" class="form_tabela" width="100%">
									<tr>
										<td width="100%">
										    <div id="divAssinaturasDoc"> 
												<h2>Assinaturas do Documento de Informação</h2>
												<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
													<ch:lista bean="${listAssinaturaDoc}"  atributoId="codigo" />
													<ch:campo atributo="orgao" label="<center>Órgão</center>" align="center"/>
													<ch:campo atributo="cargo" label="<center>Cargo</center>" align="center"/>
													<ch:campo atributo="nome" label="<center>Nome</center>" align="center"/>
													<ch:campo atributo="respMaximo" label="<center>Responsável Máximo</center>" align="center"/>
												</ch:table>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
						</td>
					</tr>
				</table>
				<br>
			</c:if>
			<c:if test='${transferenciaBemImovelForm.actionType == "revogDev" || transferenciaBemImovelForm.tipoRevogDevDesc != null }'>
				<table cellspacing="0" class="form_tabela" width="100%">
					<tr>
						<td width="100%">
						    <div id="divRevogDev"> 
								<h2>Revogação/Devolução</h2>
								<table width="100%">
									<c:choose> 
										<c:when test='${transferenciaBemImovelForm.actionType == "revogDev"}'>
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
												<td> <c:out value="${transferenciaBemImovelForm.tipoRevogDevDesc}"></c:out> </td>
											</tr>
											<c:if test='${transferenciaBemImovelForm.motivo != null }'>
												<tr>
													<td class="form_label">Motivo:</td>
													<td> <c:out value="${transferenciaBemImovelForm.motivo}"></c:out> </td>
												</tr>
											</c:if>
											<c:if test='${transferenciaBemImovelForm.nrOficio != null }'>
												<tr> 
										         	<td class="form_label">Nº do Ofício: </td>
													<td> <c:out value="${transferenciaBemImovelForm.nrOficio}"></c:out> </td>
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
			
				<c:if test="${transferenciaBemImovelForm.incluidoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${transferenciaBemImovelForm.incluidoPor}"></c:out></td>
					</tr>
				</c:if>
				<c:if test="${transferenciaBemImovelForm.alteradoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${transferenciaBemImovelForm.alteradoPor}"></c:out></td>
					</tr>
				</c:if>
				<c:if test="${transferenciaBemImovelForm.excluidoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${transferenciaBemImovelForm.excluidoPor}"></c:out></td>
					</tr>
				</c:if>

			</table>
        
		   	<hr>
			<c:if test='${transferenciaBemImovelForm.actionType == "revogDev"}'>
		  		<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
		  	</c:if>
		 	<div align="center">
				<c:choose> 
					<c:when test='${transferenciaBemImovelForm.actionType == "excluir"}'>
			 			<html:submit value="Confirmar" styleClass="form_botao" />
						<html:button property="" styleClass="form_botao" value="Voltar" onclick="voltar();" />
					</c:when>
					<c:when test='${transferenciaBemImovelForm.actionType == "revogDev"}'>
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
	var form = document.transferenciaBemImovelForm;
	if (form.nrBemImovel.value.length > 0 && form.instituicao.value.length > 0){
		buscaBemImovel();
	}
	habilitarCampos();
};
</script>   
