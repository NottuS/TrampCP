<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='icon_exibir' value='/images/icon_exibir.png' />
<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='icon_info' value='/images/icon_info.png' />
<c:url var="js_maisinfo" value="/js/generic/maisinfo.js" />
<c:url var='link_entrada' value='/entrada.do' />
<c:url var='link_pesquisar_alunos' value='/matricula.do?action=pesquisarAlunos&nomeAluno=${matriculaForm.nomeAluno}' />
<c:url var='link_editar_aluno' value='/matricula.do?action=carregarPgEditAluno' />
<c:url var='link_view_aluno' value='/matricula.do?action=carregarPgViewAluno' />

<script language="JavaScript" type="text/JavaScript" src="${js_maisinfo}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="JavaScript">
	
	function incluir() {
		document.matriculaForm.action="${link_editar_aluno}&actionType=incluir";
		document.matriculaForm.submit();
	}
	
	function cancelar() {	
		document.matriculaForm.action="${link_entrada}";
		document.matriculaForm.submit();
	}
	
</script>

<div id="conteudo">
	<div align="center">
	<h1>Pesquisar Alunos</h1>
	
    <div id="conteudo_corpo">    
    <html:form action="/matricula.do?action=pesquisarAlunos" method="post">
		<table class="form_tabela" cellspacing="0">
			<tr>
				<td class="form_label">Nome do Aluno</td>
				<td>
					<html:text size="50" property="nomeAluno" styleId="nomeAluno" onkeyup="javascript:DigitaLetra(document.getElementById('nomeAluno'))" />
					<img src="${icon_info}" onMouseOver="maisinfo('Insira o nome e selecione a opção');" onMouseOut="menosinfo();">
				</td>
			</tr>
		</table>
		<hr>
		<div align="center">
			<html:submit styleClass="form_botao" value="Pesquisar" property=""/>
			<html:button styleClass="form_botao" value="Incluir" property="" onclick="incluir();"/>
			<html:button styleClass="form_botao" value="Cancelar" property="" onclick="cancelar();"/>
		</div>  
    </html:form>
    </div>
  
  <c:if test="${!empty requestScope.pagina}">
  <div id="conteudo_corpo">
  <ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
	    <ch:lista bean="${pagina}" atributoId="idAluno,nomeAluno" />
        <ch:action imagem="${icon_exibir}" link="${link_view_aluno}&idAluno=%1&actionType=exibir" label="Exibir" width="40" align="center" />
        <ch:campo atributo="nomeAluno" label="Nome Aluno" />
        <ch:campo atributo="nascimentoAluno" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" label="Nascimento" width="60" />
        <ch:campo atributo="endereco.cidadeEndereco" label="Cidade" align="right" />
		<ch:campo atributo="endereco.bairroEndereco" label="Bairro" align="right" />
        <ch:action imagem="${icon_alterar}"  link="${link_editar_aluno}&idAluno=%1&actionType=alterar" label="Alterar" width="40" align="center" />
        <ch:action imagem="${icon_excluir}" link="${link_view_aluno}&idAluno=%1&actionType=excluir" label="Excluir" width="40" align="center" />
        <ch:painel pagina="${link_pesquisar_alunos}" classe="painel" atributoIndice="indice" />
  </ch:table>  
  </div>
  </c:if>
  
  </div>
</div>



