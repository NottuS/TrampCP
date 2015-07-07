<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var='icon_adicionar' value='/images/icon_adicionar1.png'/>
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
	
	function buscaBemImovel() {
		var form = document.gerarTermoTransfBemImovelForm;
		if (form.nrBemImovel.value.length > 0 && form.instituicao.value.length > 0) {
			submitAjax('/abi/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.nrBemImovel.value+'&uc=transferencia&codInstituicao='+form.instituicao.value, document.forms[0], "dadosBemImovel",true);
		}
	}

	function voltar(){	
		document.gerarTermoTransfBemImovelForm.tipoAcao.value = "voltar";
		document.gerarTermoTransfBemImovelForm.action="gerarTermoTransfBemImovel.do?action=concluir";
		document.gerarTermoTransfBemImovelForm.submit();
	}

	function confirmar(){
		document.gerarTermoTransfBemImovelForm.action="gerarTermoTransfBemImovel.do?action=confirmarTermoTransferenciaDefinitivo";
		document.gerarTermoTransfBemImovelForm.submit();
	}

	function gerar(){
		document.gerarTermoTransfBemImovelForm.action="gerarTermoTransfBemImovel.do?action=gerarRelatorio";
		document.gerarTermoTransfBemImovelForm.submit();
	}

	function adicionarAssinatura() {
		var form = document.gerarTermoTransfBemImovelForm;

		if (form.codAssinatura.value.length == 0) {
			alert ('Selecione uma a assinatura do Documento do Informação para adicionar!');
			return false;
		}		
		document.gerarTermoTransfBemImovelForm.action='gerarTermoTransfBemImovel.do?action=adicionarAssinatura';
		document.gerarTermoTransfBemImovelForm.submit();
	}

	function excluirAssinatura(codigo){
		if (codigo != "") {
			if(confirm('Tem certeza que deseja excluir?')) {
				document.gerarTermoTransfBemImovelForm.action='gerarTermoTransfBemImovel.do?action=excluirAssinatura&codAssinatura='+codigo;
				document.gerarTermoTransfBemImovelForm.submit();
			}
		} else {
			alert ('Selecione um item para excluir!');
		}
	}
	
</script>

<body>

<div id="conteudo">
	<div align="center">
	<h1> Gerar Termo de Transferência de Uso de Bem Imóvel </h1>
		
	<div id="conteudo_corpo">
		<html:form action="gerarTermoTransfBemImovel.do?action=carregarInterfaceInicial">
			<html:hidden property="tipoAcao" value="${gerarTermoTransfBemImovelForm.tipoAcao}"/>
			<html:hidden property="codTransferencia" value="${gerarTermoTransfBemImovelForm.codTransferencia}"/>
			<html:hidden property="status" value="${gerarTermoTransfBemImovelForm.status}"/>
			<html:hidden property="ucsRetorno" value="${gerarTermoTransfBemImovelForm.ucsRetorno}"/>
			<html:hidden property="codBemImovel" value="${gerarTermoTransfBemImovelForm.codBemImovel}"/>
			<html:hidden property="nrBemImovel" value="${gerarTermoTransfBemImovelForm.nrBemImovel}"/>
			<html:hidden property="instituicao" value="${gerarTermoTransfBemImovelForm.instituicao}"/>
			<html:hidden property="isGpAdmGeralUsuarioLogado" value="${gerarTermoTransfBemImovelForm.isGpAdmGeralUsuarioLogado}"/>
			<html:hidden property="actionType" value="${gerarTermoTransfBemImovelForm.actionType}"/>

			<table cellspacing="0" class="form_tabela" width="100%">
				<tr>
					<td class="form_label">Nº do Termo:</td>
					<td> <c:out value="${gerarTermoTransfBemImovelForm.numTermo}"></c:out> </td>
				</tr>
				<tr>
					<td class="form_label">Status:</td>
					<td> <c:out value="${gerarTermoTransfBemImovelForm.status}"></c:out> </td>
				</tr>
				<c:if test='${gerarTermoTransfBemImovelForm.instituicaoDesc != null }'>
					<tr>
						<td class="form_label">Instituição:</td>
						<td> <c:out value="${gerarTermoTransfBemImovelForm.instituicaoDesc}"></c:out> </td>
					</tr>
				</c:if>
				<tr>
					<td class="form_label" width="250">Bem Imóvel:</td>
					<td> <c:out value="${gerarTermoTransfBemImovelForm.nrBemImovel}"></c:out> </td>
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
					<td class="form_label">Órgão de Destino:</td>
					<td> 
						<c:choose> 
							<c:when test='${gerarTermoTransfBemImovelForm.orgao != null}'>
								<c:out value="${gerarTermoTransfBemImovelForm.orgao}"></c:out>
							</c:when>
							<c:otherwise>
								<font color="red"><b>Pendente</b></font>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr> 
		          	<td class="form_label">Data de Início de Vigência: </td>
					<td> 
						<c:choose> 
							<c:when test='${gerarTermoTransfBemImovelForm.dtInicioVigencia != ""}'>
								<c:out value="${gerarTermoTransfBemImovelForm.dtInicioVigencia}"></c:out>
							</c:when>
							<c:otherwise>
								<font color="red"><b>Pendente</b></font>
							</c:otherwise>
						</c:choose>
					</td>
			   	</tr>
				<tr> 
		          	<td class="form_label">Data de Fim de Vigência (Previsão): </td>
					<td> 
						<c:choose> 
							<c:when test='${gerarTermoTransfBemImovelForm.dtFimVigencia != ""}'>
								<c:out value="${gerarTermoTransfBemImovelForm.dtFimVigencia}"></c:out>
							</c:when>
							<c:otherwise>
								<font color="red"><b>Não informada</b></font>
							</c:otherwise>
						</c:choose>
					</td>
			   	</tr>
				<tr>
					<td class="form_label">Protocolo:</td>
					<td> 
						<c:choose> 
							<c:when test='${gerarTermoTransfBemImovelForm.protocolo != ""}'>
								<c:out value="${gerarTermoTransfBemImovelForm.protocolo}"></c:out>
							</c:when>
							<c:otherwise>
								<font color="red"><b>Pendente</b></font>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
			<h2>Itens:</h2>
			<table cellspacing="0" class="form_tabela" width="100%">
				<tr>
					<td width="100%">
						<c:if test="${listaItemTransferencia != null && listaItemTransferencia.quantidade > 0}">
						    <div id="divItensDoados"> 
								<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
									<ch:lista bean="${listaItemTransferencia}"  atributoId="codItemTransferencia"/>
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
						</c:if>
						<c:if test="${listaItemTransferencia == null || listaItemTransferencia.quantidade == 0}">
							<font color="red"><b>Pendente</b></font>
						</c:if>
					</td>
				</tr>
			</table>
			<h2>Assinaturas:</h2>
			<table cellspacing="0" class="form_tabela" width="100%">
				<tr>
					<td width="100%">
						<c:if test="${listaAssinatura != null && listaAssinatura.quantidade > 0}">
						    <div id="divAssinaturas"> 
								<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
									<ch:lista bean="${listaAssinatura}"  atributoId="codAssinaturaTransferencia"/>
									<ch:campo atributo="assinatura.orgao.siglaDescricao" label="<center>Órgão</center>" align="center"/>
									<ch:campo atributo="assinatura.cargoAssinatura.descricao" label="<center>Cargo</center>" align="center"/>
									<ch:campo atributo="assinatura.nome" label="<center>Nome</center>" align="center"/>
									<ch:campo atributo="ordem" label="<center>Ordem</center>" align="center"/>
								</ch:table>
							</div>
						</c:if>
						<c:if test="${listaAssinatura == null || listaAssinatura.quantidade == 0}">
							<font color="red"><b>Pendente</b></font>
						</c:if>
					</td>
				</tr>
			</table>
			<c:if test='${gerarTermoTransfBemImovelForm.actionType == "confirmar"}'>
				<br>
				<h2>Texto do Documento de Informação:</h2>
				<table cellspacing="0" class="form_tabela" width="100%">
					<tr>
						<td align="center"><html:textarea  style="font-size :medium;" property="textoDocInformacao" cols="100" rows="15" styleId="textoDocInformacao" onkeyup="TamanhoMax(this, 3000)"/></td>
					</tr>
				</table>
				<br>
				<h2>Assinaturas do Documento de Informação:</h2>
				<table cellspacing="0" class="form_tabela" width="100%">
					<tr>
						<td colspan="2" width="100%">
						    <div id="divAssinaturas"> 
								<table width="100%">
									<tr>
									 	<td class="form_label">Selecione: </td>
									 	<td>
											<html:select property="codAssinatura" styleId="codAssinatura">
												<html:option value="">-Selecione-</html:option>
												<c:if test="${nomesAssinatura != null}">
													<html:options collection="nomesAssinatura" property="codigo" labelProperty="descricao" />
												</c:if>
											</html:select>
											<a href="javascript:adicionarAssinatura();" id="linkAddAssinatura"><img src="${icon_adicionar}" width="16" id="iconAddAssinatura" height="16" border="0"></a>&nbsp;<a href="javascript:adicionarAssinatura();">Adicionar</a>						
											<a href="javascript:limparCamposAssinatura();"><img src="${icon_limpar}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:limparCamposAssinatura();">Limpar</a>
										</td>
									</tr>
									<tr>
										<td colspan="2"> 
											<div id="divListAssinaturaDoc">
												<tiles:insert page="/pages/termoTransferencia/_edit_listAssinaturasDocTransfBemImovel.jsp"/>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
				<br>
			   	<hr>
				<table cellspacing="0" class="form_tabela" width="100%">
					<tr>
			        	<td align="center" class="msg_descricao">
							<font color="red"><b>
							Obs.: Após a confirmação do Termo de Transferência o mesmo não poderá sofrer alterações, somente poderá ser revogado ou devolvido.
							</b></font>
						</td>
					</tr>
				</table>
		  	</c:if>
		   	<hr>
		 	<div align="center">
				<c:choose> 
					<c:when test='${gerarTermoTransfBemImovelForm.actionType == "confirmar"}'>
						<html:button property="" styleClass="form_botao" value="Confirmar" onclick="confirmar();" />
						<html:button property="" styleClass="form_botao" value="Gerar" onclick="gerar();" />
						<html:button property="" styleClass="form_botao" value="Voltar" onclick="voltar();" />
					</c:when>
					<c:otherwise>
						<html:button property="" styleClass="form_botao" value="Gerar" onclick="gerar();" />
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
	var form = document.gerarTermoTransfBemImovelForm;
	if (form.nrBemImovel.value.length > 0 && form.instituicao.value.length > 0){
		buscaBemImovel();
	}
};
</script>   
