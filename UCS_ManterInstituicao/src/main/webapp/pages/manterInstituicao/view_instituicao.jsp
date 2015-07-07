<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>


<c:url var='link_pesquisar' value='/manterInstituicao.do?action=carregarPgListInstituicoes' />

<script language="javascript">

	function voltar(){
	
		document.manterInstituicaoForm.action="${link_pesquisar}";
		document.manterInstituicaoForm.submit();
	}
	
</script>

<c:choose> 
		<c:when test='${manterInstituicaoForm.actionType == "excluir"}'>
			<c:set var="titulo" value="Excluir Instituição"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="titulo" value="Exibir Instituição"></c:set>
		</c:otherwise>
</c:choose>

<div id="conteudo">
	<div align="center">
	<h1>${titulo}</h1>
	
	<html:form action="/manterInstituicao.do?action=excluirInstituicao">
	<html:hidden property="codInstituicao"/>

    <div id="conteudo_corpo">    
	<table class="form_tabela" cellspacing="0" width="100%">
		<tr>
			<td class="form_label"><b>CNPJ:</b></td>
			<td>${manterInstituicaoForm.cnpj}</td>
		</tr>
		<tr>
			<td class="form_label"><b>Razão Social:</b></td>
			<td>${manterInstituicaoForm.razaoSocial }</td>
		</tr>
		<tr>
			<td class="form_label"><b>Natureza Jurídica:</b></td>
			<td>${manterInstituicaoForm.naturezaJuridica }</td>
		</tr>
		<tr>
			<!--TODO da pra melhorar -->
			<td class="form_label"><b>Porte:</b></td>
			<td><c:forEach var="porte" items="${portes}">
				${manterInstituicaoForm.porte eq porte.codigo ? porte.descricao : ''}           
		    </c:forEach></td>
		</tr>

	</table>

  </div>
  <div id="conteudo_corpo">
	<table class="form_tabela"  width="100%">
	<tr>
		<td><b>Telefone:</b></td>
	</tr>
	<c:forEach var="telefone" items="${manterInstituicaoForm.telefones}">
		<tr>
			<td>${telefone}</td>
		</tr>
	</c:forEach>
	</table>
	
	<br>
	<table class="form_tabela"  width="100%">
	<tr>
		<td><b>Àrea de Interesse</b></td>
	</tr>
	<c:forEach var="areaInteresse" items="${manterInstituicaoForm.areasInteresseSelecionadas}">
		<tr>
			<td>${areaInteresse}</td>
		</tr>
	</c:forEach>
	</table>
  	<hr>
 	<div align="center"> 	
 		<c:choose> 
			<c:when test='${manterInstituicaoForm.actionType == "excluir"}'>
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