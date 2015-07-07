<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />

<c:url var='link_entrada' value='/entrada.do' />
<c:url var='link_pesquisar' value='/cargoAssinatura.do?action=pesquisarCargoAssinatura&descricao=${cargoAssinaturaForm.descricao}' />
<c:url var='link_editar' value='/cargoAssinatura.do?action=carregarPgEditCargoAssinatura' />
<c:url var='link_excluir' value='/cargoAssinatura.do?action=excluirCargoAssinatura' />

<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="JavaScript">
	
	function incluir() {

		document.cargoAssinaturaForm.action="${link_editar}&actionType=incluir";
		document.cargoAssinaturaForm.submit();
	
	}

	function excluir(codigo,descricao){
		
		if(confirm('Tem certeza que deseja excluir?')) {
			document.cargoAssinaturaForm.action="${link_excluir}&codCargoAssinatura="+codigo+"&actionType=excluir";
			document.cargoAssinaturaForm.submit();
		}
		
	}
	
	function cancelar() {	
		document.cargoAssinaturaForm.action="${link_entrada}";
		document.cargoAssinaturaForm.submit();
	}
	
</script>

<div id="conteudo">
	<div align="center">
	<h1>Pesquisar Cargo da Assinatura</h1>
	
    <div id="conteudo_corpo">
    <html:form action="/cargoAssinatura.do?action=pesquisarCargoAssinatura" method="post">
		<table class="form_tabela" cellspacing="0">
			<c:if test="${cargoAssinaturaForm.indGrupoSentinela == cargoAssinaturaForm.adm}">
				<tr>
					<td class="form_label" align="right">* Instituição:</td>
					<td colspan="2">
						<html:select property="conInstituicao">
							<html:option value="">-Qualquer-</html:option>		
							<html:options collection="listaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />
						</html:select>
					</td>
				</tr>
			</c:if>
			<tr>
				<td class="form_label">Descrição : </td>
				<td>
					<html:text maxlength="60" size="60" property="descricao" styleId="descricao" />

				</td>
			</tr>
		</table>

		<hr>
		<c:if test="${cargoAssinaturaForm.indGrupoSentinela == cargoAssinaturaForm.adm}">
		  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
		</c:if>    
		<div align="center">
			<html:submit styleClass="form_botao" value="Pesquisar" property=""/>
			<html:button styleClass="form_botao" value="Incluir" property="" onclick="incluir();"/>
			<html:button styleClass="form_botao" value="Voltar" property="" onclick="cancelar();"/>
		</div>  
    </html:form>
    </div>
  
  
  <c:if test="${!empty pagina.registros}">
  
  <div id="conteudo_corpo">
  <ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
	    <ch:lista bean="${pagina}" atributoId="codCargoAssinatura" />
        <ch:campo atributo="instituicao.siglaDescricao" label="Instituição" />
        <ch:campo atributo="descricao" label="Descrição" />
        <ch:action imagem="${icon_alterar}"  link="${link_editar}&codCargoAssinatura=%1&actionType=alterar" label="Alterar" width="40" align="center" />
        <ch:action imagem="${icon_excluir}"  link="javascript:excluir(%1);" label="Excluir" width="40" align="center" />
        <ch:painel pagina="${link_pesquisar}" classe="painel" atributoIndice="indice" />
  </ch:table>  
  </div>
  </c:if>
  
  </div>
</div>



