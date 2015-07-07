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
<c:url var='link_pesquisar_tipoConstrucao' value='/tipoConstrucao.do?action=pesquisarTipoConstrucao&descricao=${tipoConstrucaoForm.descricao}' />
<c:url var='link_editar_tipoConstrucao' value='/tipoConstrucao.do?action=carregarPgEditTipoConstrucao' />
<c:url var='link_excluir_tipoConstrucao' value='/tipoConstrucao.do?action=excluirTipoConstrucao' />


<script language="JavaScript" type="text/JavaScript" src="${js_maisinfo}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="JavaScript">
	
	function incluir() {

		document.tipoConstrucaoForm.action="${link_editar_tipoConstrucao}&actionType=incluir";
		document.tipoConstrucaoForm.submit();
	
	}

	function excluir(codigo,descricao){
		
		if(confirm('Tem certeza que deseja excluir?')) {
			document.tipoConstrucaoForm.action="${link_excluir_tipoConstrucao}&codTipoConstrucao="+codigo+"&actionType=excluir";
			document.tipoConstrucaoForm.submit();
		}
		
	}
	
	function cancelar() {	
		document.tipoConstrucaoForm.action="${link_entrada}";
		document.tipoConstrucaoForm.submit();
	}
	
</script>

<div id="conteudo">
	<div align="center">
	<h1>Pesquisar Tipo de Constru��o</h1>
	
    <div id="conteudo_corpo">    
    <html:form action="/tipoConstrucao.do?action=pesquisarTipoConstrucao" method="post">
		<table class="form_tabela" cellspacing="0">
			<tr>
				<td class="form_label">Descri��o: </td>
				<td>
					<html:text maxlength="60" size="60" property="descricao" styleId="descricao" />

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
	    <ch:lista bean="${pagina}" atributoId="codTipoConstrucao" />
        <ch:campo atributo="descricao" label="Descri��o" />
        <ch:action imagem="${icon_alterar}"  link="${link_editar_tipoConstrucao}&codTipoConstrucao=%1&actionType=alterar" label="Alterar" width="40" align="center" />
        <ch:action imagem="${icon_excluir}"  link="javascript:excluir(%1);" label="Excluir" width="40" align="center" />
        <ch:painel pagina="${link_pesquisar_tipoConstrucao}" classe="painel" atributoIndice="indice" />
  </ch:table>  
  </div>
  </c:if>
  
  </div>
</div>



