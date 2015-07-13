<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div id="conteudo">
	<div align="center">
	<h1 align="left">Exibir Colaborador</h1>
	
	<html:form action="/manterColaborador.do?action=carregarPgConColaboradores">
    <div id="conteudo_corpo">    
	<table class="form_tabela" cellspacing="0" width="100%">
		<tr>
			<td class="form_label"><b>CPF:</b>${manterColaboradorForm.cpf}</td>
			<td class="form_label"><b>RG:</b> ${manterColaboradorForm.rg}</td>
		</tr>
		<tr>
			<td class="form_label"><b>Nome:</b> ${manterColaboradorForm.nome}</td>
		</tr>
		<tr>
			<td class="form_label"><b>Sexo:</b> ${manterColaboradorForm.sexo eq 'false' ? 'Feminino' : 'Masculino' }</td>
			<td class="form_label"><b>Data Nascimento:</b> ${manterColaboradorForm.dataNascimento}</td>
		</tr>
		<tr>
			<td class="form_label"><b>Telefone:</b> ${manterColaboradorForm.telefone}</td>
		</tr>
		<tr>
			<td class="form_label"><b>Celular:</b> ${manterColaboradorForm.celular}</td>
		</tr>
		<tr>
			<td class="form_label"><b>E-mail:</b> ${manterColaboradorForm.email}</td>
		</tr>
		<tr>
			<td class="form_label"><b>Instituição:</b> ${manterColaboradorForm.instituicao}</td>
		</tr>
		<tr>
			<td class="form_label"><b>Data Admissão:</b> ${manterColaboradorForm.dataAdmissao}</td>
		</tr>
		<tr>
			<td class="form_label"><b>Data Demissão:</b> ${manterColaboradorForm.dataDemissao}</td>
		</tr>
	</table>
 	<div align="center"> 	
		<html:submit property="" styleClass="form_botao" value="Voltar" onclick="voltar();" />
	</div>
  </div>
</html:form>

</div>
</div>