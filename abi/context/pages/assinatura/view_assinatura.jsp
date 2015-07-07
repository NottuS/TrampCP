<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="link_voltar" value="assinatura.do?action=voltar"/>
													
<script language="javascript">

	function voltar() {
		document.assinaturaForm.action='${link_voltar}';
		document.assinaturaForm.submit();
	}

</script>

<c:choose> 
		<c:when test='${assinaturaForm.actionType == "excluir"}'>
			<c:set var="acao" value="assinatura.do?action=excluirAssinatura"></c:set>
			<c:set var="titulo" value="Excluir"></c:set>
		</c:when>
		<c:when test='${assinaturaForm.actionType == "inativar"}'>
			<c:set var="acao" value="assinatura.do?action=inativarAssinatura"></c:set>
			<c:set var="titulo" value="Inativar"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="acao" value="assinatura.do"></c:set>
			<c:set var="titulo" value="Exibir"></c:set>
		</c:otherwise>
</c:choose>

<div id="conteudo">
	<div align="center">
	<h1>${titulo} Assinatura</h1>
	
	<html:form action="${acao}" >
	<html:hidden property="codAssinatura" value="${assinaturaForm.codAssinatura}"/>
	<html:hidden property="actionType" value="${assinaturaForm.actionType}"/>
	<html:hidden property="pesqExec" value="${assinaturaForm.pesqExec}"/>
	<html:hidden property="isGpAdmGeralUsuarioLogado" value="${assinaturaForm.isGpAdmGeralUsuarioLogado}"/>
	<html:hidden property="conInstituicao"/>
	<html:hidden property="conCpf"/>
	<html:hidden property="conNome"/>

    <div id="conteudo_corpo">    
	<table class="form_tabela" cellspacing="0" width="100%">
		<tr>
			<c:choose> 
				<c:when test="${assinaturaForm.isGpAdmGeralUsuarioLogado == 'S'}">
					<td class="form_label">
						Instituição: 
					</td>
					<td>
						<c:out value="${assinaturaForm.instituicaoDesc}"></c:out>
						<html:hidden property="instituicao" value="${assinaturaForm.instituicao}"/>
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
			<td class="form_label">CPF: </td>
			<td><c:out value="${assinaturaForm.cpf}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Nome: </td>
			<td><c:out value="${assinaturaForm.nome}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Responsável Máximo: </td>
			<td><c:out value="${assinaturaForm.indRespMaximoDesc}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Administração: </td>
			<td><c:out value="${assinaturaForm.administracaoDescricao}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Órgão: </td>
			<td><c:out value="${assinaturaForm.orgaoDescricao}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Cargo: </td>
			<td><c:out value="${assinaturaForm.cargoDescricao}"></c:out></td>
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
 	<div align="center"> 	
		<c:choose> 
			<c:when test='${assinaturaForm.actionType == "excluir"}'>
	 			<html:submit value="Excluir" styleClass="form_botao" />
				<html:button property="" styleClass="form_botao" value="Voltar" onclick="voltar();" />
			</c:when>
			<c:when test='${assinaturaForm.actionType == "inativar"}'>
	 			<html:submit value="Inativar" styleClass="form_botao" />
				<html:button property="" styleClass="form_botao" value="Voltar" onclick="voltar();" />
			</c:when>
			<c:otherwise>
				<html:button property="" styleClass="form_botao" value="Voltar" onclick="voltar();" />
			</c:otherwise>
		</c:choose>
	</div>
</div>
</html:form>

</div>
</div>