<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='link_entrada' value='/entrada.do' />
<c:url var='link_pesquisar_orgao' value='/orgao.do?action=pesquisarOrgao&descricao=${orgaoForm.descricao}&sigla=${orgaoForm.sigla}' />
<c:url var='link_editar_orgao' value='/orgao.do?action=carregarPgEditOrgao' />
<c:url var='link_excluir_orgao' value='/orgao.do?action=excluirOrgao' />

<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="JavaScript">
	
	function incluir() {

		document.orgaoForm.action="${link_editar_orgao}&actionType=incluir";
		document.orgaoForm.submit();
	
	}

	function excluir(codigo,descricao){
		
		if(confirm('Tem certeza que deseja excluir?')) {
			document.orgaoForm.action="${link_excluir_orgao}&codOrgao="+codigo+"&actionType=excluir";
			document.orgaoForm.submit();
		}
		
	}
	
	function cancelar() {	
		document.orgaoForm.action="${link_entrada}";
		document.orgaoForm.submit();
	}
	
</script>

<div id="conteudo">
	<div align="center">
	<h1>Pesquisar Órgão</h1>
	
    <div id="conteudo_corpo">    
    <html:form action="/orgao.do?action=pesquisarOrgao" method="post">
		<table class="form_tabela" cellspacing="0">
			<c:if test="${orgaoForm.indGrupoSentinela == orgaoForm.adm}">
				<tr>
					<td class="form_label" align="right">Instituição:</td>
					<td colspan="2">
						<html:select property="conInstituicao">
					 	 <html:option value="">-Qualquer-</html:option>
						 <html:options collection="listaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
					    </html:select>
					</td>	
				</tr>
			</c:if>
			<tr>
				<td class="form_label">Sigla : </td>
				<td>
					<html:text maxlength="50" size="30" property="sigla" styleId="sigla" />

				</td>
			</tr>
			
			<tr>
				<td class="form_label">Descrição : </td>
				<td>
					<html:text maxlength="150" size="80" property="descricao" styleId="descricao"/>

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
	    <ch:lista bean="${pagina}" atributoId="codOrgao" />
	    <ch:campo atributo="sigla" label="Sigla" />
        <ch:campo atributo="descricao" label="Descrição" />
        <ch:campo atributo="descricaoTipoAdministracao" label="Tipo de Administração" />
        <ch:action imagem="${icon_alterar}" link="${link_editar_orgao}&codOrgao=%1&actionType=alterar" label="Alterar" width="40" align="center" />
        <ch:action imagem="${icon_excluir}" link="javascript:excluir(%1);" label="Excluir" width="40" align="center" />
        <ch:painel pagina="${link_pesquisar_orgao}" classe="painel" atributoIndice="indice" />
  </ch:table>  
  </div>
  </c:if>
  
  </div>
</div>



