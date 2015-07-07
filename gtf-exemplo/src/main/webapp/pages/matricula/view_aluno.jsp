<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:url var='link_pesquisar' value='/matricula.do?action=carregarPgListAlunos' />

<script language="javascript">

	function voltar(){
	
		document.matriculaForm.action="${link_pesquisar}";
		document.matriculaForm.submit();
	}
	
</script>

<c:choose> 
		<c:when test='${matriculaForm.actionType == "excluir"}'>
			<c:set var="titulo" value="Excluir Aluno"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="titulo" value="Exibir Aluno"></c:set>
		</c:otherwise>
</c:choose>

<div id="conteudo">
	<div align="center">
	<h1>${titulo}</h1>
	
	<html:form action="/matricula.do?action=excluirAluno">
	<html:hidden property="idAluno"/>

    <div id="conteudo_corpo">    
	<table class="form_tabela" cellspacing="0" width="100%">
		<tr>
			<td class="form_label"><b>Nome</b></td>
			<td><c:out value="${matriculaForm.nomeAluno}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label"><b>Data Nascimento</b></td>
			<td><c:out value="${matriculaForm.dtNascAluno}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label"><b>Nome do Pai</b></td>
			<td><c:out value="${matriculaForm.nomePaiAluno}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label"><b>Nome da Mãe</b></td>
			<td><c:out value="${matriculaForm.nomeMaeAluno}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label"><b>CPF</b></td>
			<td><c:out value="${matriculaForm.cpfAluno}"></c:out></td>
		</tr>
	</table>

  </div>
  <div id="conteudo_corpo">
  
    <table class="form_tabela" cellspacing="0" width="100%">
	<tr>
		<td class="form_label"><b>UF</b></td>
		<td><c:out value="${matriculaForm.uf}"></c:out></td>
	</tr>
	<tr>
		<td class="form_label"><b>Cidade</b></td>
		<td><c:out value="${matriculaForm.cidade}"></c:out></td>
	</tr>
	<tr>
		<td class="form_label"><b>CEP</b></td>
		<td><c:out value="${matriculaForm.cep}"></c:out></td>
	</tr>
	<tr>
		<td class="form_label"><b>Logradouro</b></td>
		<td><c:out value="${matriculaForm.logradouro}"></c:out></td>
	</tr>	
	<tr>
		<td class="form_label"><b>Número</b></td>
		<td><c:out value="${matriculaForm.numero}"></c:out></td>
	</tr>
	<tr>
		<td class="form_label"><b>Complemento</b></td>
		<td><c:out value="${matriculaForm.complemento}"></c:out></td>
	</tr>
	<tr>
		<td class="form_label"><b>Bairro</b></td>
		<td><c:out value="${matriculaForm.bairro}"></c:out></td>
	</tr>	
  </table>
 
  	<hr>
 	<div align="center"> 	
 		<c:choose> 
			<c:when test='${matriculaForm.actionType == "excluir"}'>
	 			<html:submit value="Excluir" styleClass="form_botao" />
				<html:button property="" styleClass="form_botao" value="Cancelar" onclick="voltar();" />
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