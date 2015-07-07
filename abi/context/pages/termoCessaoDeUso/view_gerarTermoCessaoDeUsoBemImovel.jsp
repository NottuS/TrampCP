<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

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
		var form = document.gerarTermoCessaoDeUsoBemImovelForm;
		if (form.nrBemImovel.value.length > 0 && form.instituicao.value.length > 0) {
			submitAjax('/abi/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.nrBemImovel.value+'&uc=cessao&codInstituicao='+form.instituicao.value+'&codCessaoUso='+form.codCessaoDeUso.value, document.forms[0], "dadosBemImovel",true);
		}
	}

	function voltar(){	
		document.gerarTermoCessaoDeUsoBemImovelForm.tipoAcao.value = "voltar";
		document.gerarTermoCessaoDeUsoBemImovelForm.action="gerarTermoCessaoDeUsoBemImovel.do?action=concluir";
		document.gerarTermoCessaoDeUsoBemImovelForm.submit();
	}

	function confirmar(){
		document.gerarTermoCessaoDeUsoBemImovelForm.action="gerarTermoCessaoDeUsoBemImovel.do?action=confirmarTermoCessaoDeUsoDefinitivo";
		document.gerarTermoCessaoDeUsoBemImovelForm.submit();
	}

	function gerar(){
		document.gerarTermoCessaoDeUsoBemImovelForm.action="gerarTermoCessaoDeUsoBemImovel.do?action=gerarRelatorio";
		document.gerarTermoCessaoDeUsoBemImovelForm.submit();
	}

</script>

<body>

<div id="conteudo">
	<div align="center">
	<h1> Gerar Termo de Cessão De Uso de Bem Imóvel </h1>
		
	<div id="conteudo_corpo">
		<html:form action="gerarTermoCessaoDeUsoBemImovel.do?action=carregarInterfaceInicial">
			<html:hidden property="tipoAcao" value="${gerarTermoCessaoDeUsoBemImovelForm.tipoAcao}"/>
			<html:hidden property="codCessaoDeUso" value="${gerarTermoCessaoDeUsoBemImovelForm.codCessaoDeUso}"/>
			<html:hidden property="status" value="${gerarTermoCessaoDeUsoBemImovelForm.status}"/>
			<html:hidden property="ucsRetorno" value="${gerarTermoCessaoDeUsoBemImovelForm.ucsRetorno}"/>
			<html:hidden property="codBemImovel" value="${gerarTermoCessaoDeUsoBemImovelForm.codBemImovel}"/>
			<html:hidden property="nrBemImovel" value="${gerarTermoCessaoDeUsoBemImovelForm.nrBemImovel}"/>
			<html:hidden property="instituicao" value="${gerarTermoCessaoDeUsoBemImovelForm.instituicao}"/>
			<html:hidden property="isGpAdmGeralUsuarioLogado" value="${gerarTermoCessaoDeUsoBemImovelForm.isGpAdmGeralUsuarioLogado}"/>
			<html:hidden property="actionType" value="${gerarTermoCessaoDeUsoBemImovelForm.actionType}"/>

			<table cellspacing="0" class="form_tabela" width="100%">
				<tr>
					<td class="form_label">Nº do Termo:</td>
					<td> <c:out value="${gerarTermoCessaoDeUsoBemImovelForm.numTermo}"></c:out> </td>
				</tr>
				<tr>
					<td class="form_label">Status:</td>
					<td> <c:out value="${gerarTermoCessaoDeUsoBemImovelForm.status}"></c:out> </td>
				</tr>
				<c:if test='${gerarTermoCessaoDeUsoBemImovelForm.instituicaoDesc != null }'>
					<tr>
						<td class="form_label">Instituição:</td>
						<td> <c:out value="${gerarTermoCessaoDeUsoBemImovelForm.instituicaoDesc}"></c:out> </td>
					</tr>
				</c:if>
				<tr>
					<td class="form_label" width="250">Bem Imóvel:</td>
					<td> <c:out value="${gerarTermoCessaoDeUsoBemImovelForm.nrBemImovel}"></c:out> </td>
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
					<td> 
						<c:choose> 
							<c:when test='${gerarTermoCessaoDeUsoBemImovelForm.orgao != null}'>
								<c:out value="${gerarTermoCessaoDeUsoBemImovelForm.orgao}"></c:out>
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
							<c:when test='${gerarTermoCessaoDeUsoBemImovelForm.dtInicioVigencia != ""}'>
								<c:out value="${gerarTermoCessaoDeUsoBemImovelForm.dtInicioVigencia}"></c:out>
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
							<c:when test='${gerarTermoCessaoDeUsoBemImovelForm.dtFimVigencia != ""}'>
								<c:out value="${gerarTermoCessaoDeUsoBemImovelForm.dtFimVigencia}"></c:out>
							</c:when>
							<c:otherwise>
								<font color="red"><b>Pendente</b></font>
							</c:otherwise>
						</c:choose>
					</td>
			   	</tr>
				<tr>
					<td class="form_label">Protocolo:</td>
					<td> 
						<c:choose> 
							<c:when test='${gerarTermoCessaoDeUsoBemImovelForm.protocolo != ""}'>
								<c:out value="${gerarTermoCessaoDeUsoBemImovelForm.protocolo}"></c:out>
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
						<c:if test="${listItemCessaoDeUso != null && listItemCessaoDeUso.quantidade > 0}">
						    <div id="divItensDoados"> 
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
						</c:if>
						<c:if test="${listItemCessaoDeUso == null || listItemCessaoDeUso.quantidade == 0}">
							<font color="red"><b>Pendente</b></font>
						</c:if>
					</td>
				</tr>
			</table>
			<table cellspacing="0" class="form_tabela" width="100%">
				<tr>
					<td width="100%">
					    <div id="divDadosLei"> 
							<h2>Dados da Lei</h2>
							<table width="100%">
							 	<tr>
							 		<td class="form_label" width="250">Projeto de Lei: </td>
									<td> 
										<c:choose> 
											<c:when test='${gerarTermoCessaoDeUsoBemImovelForm.projetoLei != null}'>
												<c:out value="${gerarTermoCessaoDeUsoBemImovelForm.projetoLei}"></c:out>
											</c:when>
											<c:otherwise>
												<font color="red"><b>Pendente</b></font>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td class="form_label">Nº da Lei:</td>
									<td> 
										<c:choose> 
											<c:when test='${gerarTermoCessaoDeUsoBemImovelForm.numeroLei != null}'>
												<c:out value="${gerarTermoCessaoDeUsoBemImovelForm.numeroLei}"></c:out>
											</c:when>
											<c:otherwise>
												<font color="red"><b>Pendente</b></font>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr> 
						         	<td class="form_label">Data da Assinatura: </td>
									<td> 
										<c:choose> 
											<c:when test='${gerarTermoCessaoDeUsoBemImovelForm.dataAssinaturaLei != null}'>
												<c:out value="${gerarTermoCessaoDeUsoBemImovelForm.dataAssinaturaLei}"></c:out>
											</c:when>
											<c:otherwise>
												<font color="red"><b>Pendente</b></font>
											</c:otherwise>
										</c:choose>
									</td>
							   	</tr>
								<tr> 
						         	<td class="form_label">Data da Publicação: </td>
									<td> 
										<c:choose> 
											<c:when test='${gerarTermoCessaoDeUsoBemImovelForm.dataPublicacaoLei != null}'>
												<c:out value="${gerarTermoCessaoDeUsoBemImovelForm.dataPublicacaoLei}"></c:out>
											</c:when>
											<c:otherwise>
												<font color="red"><b>Pendente</b></font>
											</c:otherwise>
										</c:choose>
									</td>
							   	</tr>
								<tr>
									<td class="form_label">Nº do Diário Oficial:</td>
									<td> 
										<c:choose> 
											<c:when test='${gerarTermoCessaoDeUsoBemImovelForm.nrDioeLei != null}'>
												<c:out value="${gerarTermoCessaoDeUsoBemImovelForm.nrDioeLei}"></c:out>
											</c:when>
											<c:otherwise>
												<font color="red"><b>Pendente</b></font>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
			<h2>Assinaturas:</h2>
			<table cellspacing="0" class="form_tabela" width="100%">
				<tr>
					<td width="100%">
						<c:if test="${listAssinatura != null && listAssinatura.quantidade > 0}">
						    <div id="divAssinaturas"> 
								<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
									<ch:lista bean="${listAssinatura}"  atributoId="codAssinaturaCessaoDeUso"/>
									<ch:campo atributo="assinatura.orgao.siglaDescricao" label="<center>Órgão</center>" align="center"/>
									<ch:campo atributo="assinatura.cargoAssinatura.descricao" label="<center>Cargo</center>" align="center"/>
									<ch:campo atributo="assinatura.nome" label="<center>Nome</center>" align="center"/>
									<ch:campo atributo="ordem" label="<center>Ordem</center>" align="center"/>
								</ch:table>
							</div>
						</c:if>
						<c:if test="${listAssinatura == null || listAssinatura.quantidade == 0}">
							<font color="red"><b>Pendente</b></font>
						</c:if>
					</td>
				</tr>
			</table>
			<c:if test='${gerarTermoCessaoDeUsoBemImovelForm.actionType == "confirmar"}'>
			   	<hr>
				<table cellspacing="0" class="form_tabela" width="100%">
					<tr>
			        	<td align="center" class="msg_descricao">
							<font color="red"><b>
							Obs.: Após a confirmação do Termo de Cessão o mesmo não poderá sofrer alterações, somente poderá ser revogado, devolvido ou 
							renovado (nova numeração).
							</b></font>
						</td>
					</tr>
				</table>
		  	</c:if>
		   	<hr>
		 	<div align="center">
				<c:choose> 
					<c:when test='${gerarTermoCessaoDeUsoBemImovelForm.actionType == "confirmar"}'>
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
	var form = document.gerarTermoCessaoDeUsoBemImovelForm;
	if (form.nrBemImovel.value.length > 0 && form.instituicao.value.length > 0){
		buscaBemImovel();
	}
};
</script>   
