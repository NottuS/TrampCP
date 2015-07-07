<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var='link_pesquisar' value='/tabelionato.do?action=pesquisarTabelionato' />
													
<script language="javascript">

	function voltar() {
		document.tabelionatoForm.action='${link_pesquisar}';
		document.tabelionatoForm.submit();
	}

</script>

<c:choose> 
		<c:when test='${tabelionatoForm.actionType == "excluir"}'>
			<c:set var="titulo" value="Excluir Tabelionato"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="titulo" value="Exibir Tabelionato"></c:set>
		</c:otherwise>
</c:choose>

<div id="conteudo">
	<div align="center">
	<h1>${titulo}</h1>
	
	<html:form action="/tabelionato.do?action=excluirTabelionato">
	<html:hidden property="codTabelionato"/>
	<html:hidden property="conDescricao"/>
	<html:hidden property="conUF"/>
	<html:hidden property="conMunicipio"/>

    <div id="conteudo_corpo">    
	<table class="form_tabela" cellspacing="0" width="100%">
		<tr>
			<td class="form_label">Descrição: </td>
			<td><c:out value="${tabelionatoForm.descricao}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">CEP: </td>
			<td><c:out value="${tabelionatoForm.cep}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">UF: </td>
			<td><c:out value="${tabelionatoForm.uf}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Município: </td>
			<td><c:out value="${tabelionatoForm.municipio}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Logradouro: </td>
			<td><c:out value="${tabelionatoForm.logradouro}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Número: </td>
			<td><c:out value="${tabelionatoForm.numero}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Bairro: </td>
			<td><c:out value="${tabelionatoForm.bairro}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Complemento: </td>
			<td><c:out value="${tabelionatoForm.complemento}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Contato: </td>
			<td colspan="3"><c:out value="${tabelionatoForm.nomeContato}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Telefone: </td>
			<td colspan="3">
			<c:if test="${!empty tabelionatoForm.telDdd && !empty tabelionatoForm.telNumero}">
				<c:out value="(${tabelionatoForm.telDdd}) ${tabelionatoForm.telNumero}"></c:out>
			</c:if>
			</td>
		</tr>
	</table>
  	<hr>
 	<div align="center"> 	
		<c:choose> 
			<c:when test='${tabelionatoForm.actionType == "excluir"}'>
	 			<html:submit value="Excluir" styleClass="form_botao" />
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