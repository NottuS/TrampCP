<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_util" value="/js/generic/util.js" />

<c:url var='link_entrada' value='/entrada.do' />

<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="JavaScript">
	
		
	function cancelar() {	
		document.migracaoForm.action="${link_entrada}";
		document.migracaoForm.submit();
	}
	
</script>

<div id="conteudo">
	<div align="center">
	<h1>Migração de Dados</h1>
	
    <div id="conteudo_corpo">    
    <html:form action="/migracao.do?action=validarSenhaMigracao" method="post">
    <html:hidden property="page" value="1"/>
		<table class="form_tabela" cellspacing="0">
			<tr>
				<td class="form_label">Senha: </td>
				<td>
					<html:password maxlength="30" size="30" property="senha" styleId="senha"  />

				</td>
			</tr>
		</table>
		<hr>
		<div align="center">
			<html:submit styleClass="form_botao" value="Enviar" property=""/>
			<html:button styleClass="form_botao" value="Voltar" property="" onclick="cancelar();"/>
		</div>  
    </html:form>
    </div>
  
  
  </div>
</div>



