<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_util" value="/js/generic/util.js" />

<c:url var='link_entrada' value='/entrada.do' />

<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="JavaScript">
	

	function executar(){
		
		if(confirm('Tem certeza que deseja executar a migração?')) {
			document.migracaoForm.action="migracao.do?action=executarArquivo";
			document.migracaoForm.submit();
		}
		
	}
	
	function cancelar() {	
		document.migracaoForm.action="${link_entrada}";
		document.migracaoForm.submit();
	}
	
</script>

<div id="conteudo">
	
	<h1>Migração de Dados</h1>
	
    <div id="conteudo_corpo">    
    <html:form action="/migracao.do?action=validarArquivo" enctype="multipart/form-data" method="post"  onsubmit="return validarCampos() ">
    <html:hidden property="page" value="2"/>
    <html:hidden property="senhaOK"/>
    <html:hidden property="senha"/>
		<table class="form_tabela" cellspacing="0">
			 <tr> 
		          <td class="form_label" width="280">Arquivo :</td>
		          
		          <td colspan="2"><html:file  maxlength="100" size="50"  property="arquivo" styleId="arquivo"  /></td>
		        </tr>
		</table>
		<hr>
		<div align="center">
			<c:if test="${senhaOK}">
				<html:submit styleClass="form_botao" value="Verificar Arquivo" property=""/>
				<html:button styleClass="form_botao" value="Executar Migração" property="" onclick="executar();"/>
			</c:if>
			<html:button styleClass="form_botao" value="Voltar" property="" onclick="cancelar();"/>
		</div>  
    </html:form>
    </div>
</div>




