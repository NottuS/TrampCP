<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='link_entrada' value='/entrada.do' />
<c:url var='link_pesquisar' value='/instituicao.do?action=pesquisar&conNome=${instituicaoForm.conNome}&conSigla=${instituicaoForm.conSigla}' />
<c:url var='link_editar' value='/instituicao.do?action=carregarPgEdit' />
<c:url var='link_excluir' value='/instituicao.do?action=excluir' />

<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="JavaScript">
	
	function incluir() {

		document.instituicaoForm.action="${link_editar}&actionType=incluir";
		document.instituicaoForm.submit();
	
	}

	function alterar(codigo) {

		document.instituicaoForm.action="${link_editar}&actionType=alterar&codInstituicao="+codigo;
		document.instituicaoForm.submit();
	
	}

	function excluir(codigo){
		
		if(confirm('Tem certeza que deseja excluir?')) {
			document.instituicaoForm.action="${link_excluir}&codInstituicao="+codigo+"&actionType=excluir";
			document.instituicaoForm.submit();
		}
		
	}
	
	function cancelar() {	
		document.instituicaoForm.action="${link_entrada}";
		document.instituicaoForm.submit();
	}
	
</script>

<div id="conteudo">
	<div align="center">
	<h1>Pesquisar Instituição</h1>
	
    <div id="conteudo_corpo">    
    <html:form action="/instituicao.do?action=pesquisar" method="post">
		<table class="form_tabela" cellspacing="0">
			<tr>
				<td class="form_label">Sigla : </td>
				<td>
					<html:text maxlength="10" size="20" property="conSigla" styleId="conSigla"value="${instituicaoForm.conSigla}" />

				</td>
			</tr>
			
			<tr>
				<td class="form_label">Nome : </td>
				<td>
					<html:text maxlength="200" size="80" property="conNome" styleId="conNome" value="${instituicaoForm.conNome}"/>

				</td>
			</tr>
		</table>
		<hr>
		<div align="center">
			<html:submit styleClass="form_botao" value="Pesquisar" property=""/>
			<html:button styleClass="form_botao" value="Incluir" property="" onclick="incluir();"/>
			<html:button styleClass="form_botao" value="Voltar" property="" onclick="cancelar();"/>
		
		</div>  
    </html:form>
    </div>
  
  
  <c:if test="${!empty requestScope.pagina.registros}">
  
  <div id="conteudo_corpo">
  <ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
	    <ch:lista bean="${pagina}" atributoId="codInstituicao" />
	    <ch:campo atributo="sigla" label="Sigla" />
        <ch:campo atributo="nome" label="Nome" />
        <ch:action imagem="${icon_alterar}" link="javascript:alterar(%1);" label="Alterar" width="40" align="center" />
        <ch:action imagem="${icon_excluir}" link="javascript:excluir(%1);" label="Excluir" width="40" align="center" />
        <ch:painel pagina="${link_pesquisar}" classe="painel" atributoIndice="indice" />
  </ch:table>  
  </div>
  </c:if>
  
  </div>
</div>



