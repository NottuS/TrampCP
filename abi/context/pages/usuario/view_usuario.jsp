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

	function voltar() {
		document.manterUsuarioForm.action="manterUsuario.do?action=voltar";
		document.manterUsuarioForm.submit();
	}

	function excluirUsuario() {
		if(confirm('Tem certeza que deseja excluir?')) {
			var form = document.manterUsuarioForm;
			document.manterUsuarioForm.action='manterUsuario.do?action=excluirManterUsuario';
			document.manterUsuarioForm.submit();
		}
	}

</script>

<div id="conteudo">
	<div align="center">
	<h1 >Excluir Usuário </h1>
		
	<div id="conteudo_corpo">
		<html:form action="manterUsuario.do?action=excluirManterUsuario" onsubmit="return excluirUsuario()">
			<html:hidden property="codUsuario" value="${manterUsuarioForm.codUsuario}"/>
			<html:hidden property="actionType" value="${manterUsuarioForm.actionType}"/>
			<html:hidden property="desabilitaCampo"/>
			<html:hidden property="desabilitaOrgao"/>
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
								<td class="form_label" width="200">CPF: </td>
								<td> <c:out value="${manterUsuarioForm.cpf}"></c:out> </td>
							</tr>
							<tr>
								<td class="form_label">Nome: </td>
								<td> <c:out value="${manterUsuarioForm.nome}"></c:out> </td>
							</tr>
							<tr>
								<td class="form_label">Login: </td>
								<td> <c:out value="${manterUsuarioForm.login}"></c:out> </td>
							</tr>
							<tr>
								<td class="form_label">E-mail: </td>
								<td> <c:out value="${manterUsuarioForm.mail}"></c:out> </td>
							</tr>
							<tr>
								<td class="form_label">Situação: </td>
								<td> <c:out value="${manterUsuarioForm.situacaoDesc}"></c:out> </td>
							</tr>
						</table>
					</td>
				</tr>						
				<tr>
					<td colspan="2" width="100%">
					    <div id="divGrupo"> 
							<h2>Grupo</h2>
							<c:if test="${listGrupos != null && listGrupos.totalRegistros > 0}">
								<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
									<ch:lista bean="${listGrupos}"  atributoId="usuario.codUsuario, grupoSentinela.codGrupoSentinela"/>
									<ch:campo atributo="grupoSentinela.descricaoGrupo" label="<center>Descrição</center>" align="center"/>
								</ch:table>
							</c:if>
						</div>
					</td>
				</tr>
				
				<tr>
					<td colspan="2" width="100%">
						<table width="100%" >
							<tr>
								<td class="form_label" width="200">Instituição: </td>
								<td> <c:out value="${manterUsuarioForm.instituicaoDesc}"></c:out> </td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%">
					    <div id="divOrgao"> 
							<h2>Órgão</h2>
							<c:if test="${listOrgaos != null && listOrgaos.totalRegistros > 0}">
								<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
									<ch:lista bean="${listOrgaos}"  atributoId="usuario.codUsuario, orgao.codOrgao"/>
									<ch:campo atributo="orgao.siglaDescricao" label="<center>Descrição</center>" align="center"/>
								</ch:table>
							</c:if>
						</div>
					</td>
				</tr>
				<tr>
					<td height="10"> </td>
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
		 	<div align="center">
	 			<html:button property="excluir" value="Confirmar" styleClass="form_botao" onclick="excluirUsuario();" />
				<html:button property="btnCancelar" styleClass="form_botao" value="Voltar" onclick="voltar();"/>
			</div>	
			</html:form>
		</div>
	</div>
</div>
