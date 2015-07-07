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
		var form = document.gerarTermoDoacaoBemImovelForm;
		if (form.nrBemImovel.value.length > 0 && form.instituicao.value.length > 0) {
			submitAjax('/abi/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.nrBemImovel.value+'&uc=doacao&codInstituicao='+form.instituicao.value, document.forms[0], "dadosBemImovel",true);
		}
	}

	function voltar(){	
		document.gerarTermoDoacaoBemImovelForm.tipoAcao.value = "voltar";
		document.gerarTermoDoacaoBemImovelForm.action="gerarTermoDoacaoBemImovel.do?action=concluir";
		document.gerarTermoDoacaoBemImovelForm.submit();
	}

	function confirmar(){
		document.gerarTermoDoacaoBemImovelForm.action="gerarTermoDoacaoBemImovel.do?action=confirmarTermoDoacaoDefinitivo";
		document.gerarTermoDoacaoBemImovelForm.submit();
	}

	function gerar(){
		document.gerarTermoDoacaoBemImovelForm.action="gerarTermoDoacaoBemImovel.do?action=gerarRelatorio";
		document.gerarTermoDoacaoBemImovelForm.submit();
	}
	
</script>

<body>

<div id="conteudo">
	<div align="center">
	<h1> Gerar Termo de Doação de Bem Imóvel </h1>
		
	<div id="conteudo_corpo">
		<html:form action="gerarTermoDoacaoBemImovel.do?action=carregarInterfaceInicial">
			<html:hidden property="tipoAcao" value="${gerarTermoDoacaoBemImovelForm.tipoAcao}"/>
			<html:hidden property="codDoacao" value="${gerarTermoDoacaoBemImovelForm.codDoacao}"/>
			<html:hidden property="status" value="${gerarTermoDoacaoBemImovelForm.status}"/>
			<html:hidden property="ucsRetorno" value="${gerarTermoDoacaoBemImovelForm.ucsRetorno}"/>
			<html:hidden property="codBemImovel" value="${gerarTermoDoacaoBemImovelForm.codBemImovel}"/>
			<html:hidden property="nrBemImovel" value="${gerarTermoDoacaoBemImovelForm.nrBemImovel}"/>
			<html:hidden property="instituicao" value="${gerarTermoDoacaoBemImovelForm.instituicao}"/>
			<html:hidden property="isGpAdmGeralUsuarioLogado" value="${gerarTermoDoacaoBemImovelForm.isGpAdmGeralUsuarioLogado}"/>
			<html:hidden property="actionType" value="${gerarTermoDoacaoBemImovelForm.actionType}"/>
		
			<table cellspacing="0" class="form_tabela" width="100%">
				<tr>
					<td class="form_label">Nº do Termo:</td>
					<td> <c:out value="${gerarTermoDoacaoBemImovelForm.numTermo}"></c:out> </td>
				</tr>
				<tr>
					<td class="form_label">Status:</td>
					<td> <c:out value="${gerarTermoDoacaoBemImovelForm.status}"></c:out> </td>
				</tr>
				<c:if test='${gerarTermoDoacaoBemImovelForm.instituicaoDesc != null }'>
					<tr>
						<td class="form_label">Instituição:</td>
						<td> <c:out value="${gerarTermoDoacaoBemImovelForm.instituicaoDesc}"></c:out> </td>
					</tr>
				</c:if>
				<tr>
					<td class="form_label" width="250">Bem Imóvel:</td>
					<td> <c:out value="${gerarTermoDoacaoBemImovelForm.nrBemImovel}"></c:out> </td>
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
					<td class="form_label">Administração:</td>
					<td> 
						<c:choose> 
							<c:when test='${gerarTermoDoacaoBemImovelForm.administracao != null}'>
								<c:out value="${gerarTermoDoacaoBemImovelForm.administracao}"></c:out>
							</c:when>
							<c:otherwise>
								<font color="red"><b>Pendente</b></font>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="form_label">Órgão Responsável:</td>
					<td> 
						<c:choose> 
							<c:when test='${gerarTermoDoacaoBemImovelForm.orgao != null}'>
								<c:out value="${gerarTermoDoacaoBemImovelForm.orgao}"></c:out>
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
							<c:when test='${gerarTermoDoacaoBemImovelForm.dtInicioVigencia != ""}'>
								<c:out value="${gerarTermoDoacaoBemImovelForm.dtInicioVigencia}"></c:out>
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
							<c:when test='${gerarTermoDoacaoBemImovelForm.dtFimVigencia != ""}'>
								<c:out value="${gerarTermoDoacaoBemImovelForm.dtFimVigencia}"></c:out>
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
							<c:when test='${gerarTermoDoacaoBemImovelForm.protocolo != ""}'>
								<c:out value="${gerarTermoDoacaoBemImovelForm.protocolo}"></c:out>
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
						<c:if test="${listItemDoacao != null && listItemDoacao.quantidade > 0}">
						    <div id="divItensDoados"> 
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
						</c:if>
						<c:if test="${listItemDoacao == null || listItemDoacao.quantidade == 0}">
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
											<c:when test='${gerarTermoDoacaoBemImovelForm.projetoLei != null}'>
												<c:out value="${gerarTermoDoacaoBemImovelForm.projetoLei}"></c:out>
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
											<c:when test='${gerarTermoDoacaoBemImovelForm.numeroLei != null}'>
												<c:out value="${gerarTermoDoacaoBemImovelForm.numeroLei}"></c:out>
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
											<c:when test='${gerarTermoDoacaoBemImovelForm.dataAssinaturaLei != null}'>
												<c:out value="${gerarTermoDoacaoBemImovelForm.dataAssinaturaLei}"></c:out>
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
											<c:when test='${gerarTermoDoacaoBemImovelForm.dataPublicacaoLei != null}'>
												<c:out value="${gerarTermoDoacaoBemImovelForm.dataPublicacaoLei}"></c:out>
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
											<c:when test='${gerarTermoDoacaoBemImovelForm.nrDioeLei != null}'>
												<c:out value="${gerarTermoDoacaoBemImovelForm.nrDioeLei}"></c:out>
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
									<ch:lista bean="${listAssinatura}"  atributoId="codAssinaturaDoacao"/>
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
			<c:if test='${gerarTermoDoacaoBemImovelForm.actionType == "confirmar"}'>
			   	<hr>
				<table cellspacing="0" class="form_tabela" width="100%">
					<tr>
			        	<td align="center" class="msg_descricao">
							<font color="red"><b>
							Obs.: Após a confirmação do Termo de Doação o mesmo não poderá sofrer alterações, somente poderá ser revogado ou devolvido.
							</b></font>
						</td>
					</tr>
				</table>
		  	</c:if>
		   	<hr>
		 	<div align="center">
				<c:choose> 
					<c:when test='${gerarTermoDoacaoBemImovelForm.actionType == "confirmar"}'>
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
	var form = document.gerarTermoDoacaoBemImovelForm;
	if (form.nrBemImovel.value.length > 0 && form.instituicao.value.length > 0){
		buscaBemImovel();
	}
};
</script>   
