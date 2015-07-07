<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div id="conteudo">
	<div align="center">
	<h1>Exibir Cartório</h1>

    <div id="conteudo_corpo">    
	<table class="form_tabela" cellspacing="0" width="80%">
		<tr>
			<td class="form_label">Descrição: </td>
			<td><c:out value="${cartorio.descricao}" ></c:out></td>
		</tr>
		<tr>
			<td class="form_label">CEP: </td>
			<td><c:out value="${cartorio.cep}" ></c:out></td>
		</tr>
		<tr>
			<td class="form_label">UF: </td>
			<td><c:out value="${cartorio.uf}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Município: </td>
			<td><c:out value="${cartorio.municipio}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Logradouro: </td>
			<td><c:out value="${cartorio.logradouro}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Número: </td>
			<td><c:out value="${cartorio.numero}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Bairro: </td>
			<td><c:out value="${cartorio.bairro}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Complemento: </td>
			<td><c:out value="${cartorio.complemento}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Contato: </td>
			<td><c:out value="${cartorio.nomeContato}"></c:out></td>
		</tr>
		<tr>
			<td class="form_label">Telefone: </td>
			<td>
			<c:if test="${!empty cartorio.telDdd && !empty cartorio.telNumero}">
				<c:out value="(${cartorio.telDdd}) ${cartorio.telNumero}"></c:out>
			</c:if>
			</td>
		</tr>
	</table>

  	<hr>
 	<div align="center" > 	
	
	<html:button property="" styleClass="form_botao" value="Voltar" onclick="javascript:history.back(1);"/>
			
	</div>
</div>

</div>
</div>