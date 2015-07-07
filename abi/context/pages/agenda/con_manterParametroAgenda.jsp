<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<c:url var='link_entrada' value='/entrada.do' />

<script language="javascript" src="js/generic/maisinfo.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="JavaScript">
	function pesquisar() {
		if (form.conInstituicao.value == '' || form.conInstituicao.value == '0'){
			alert ('Selecione uma Instituição!');
			return false;
		}else{
			document.manterParametroAgendaForm.codInstituicao.value=document.manterParametroAgendaForm.conInstituicao.value;
			document.manterParametroAgendaForm.action="manterParametroAgenda.do?action=carregarInterfaceEditar";
			document.manterParametroAgendaForm.submit();
		}
		
	}
	
	function cancelar() {	
		document.manterParametroVistoriaForm.action="${link_entrada}";
		document.manterParametroVistoriaForm.submit();
	}

</script>

<body onload="carregarCamposPesquisaPadrao()">

<div id="conteudo">
	<div align="center">
	<h1>Pesquisar Parâmetros para a Agenda</h1>
	
    <div id="conteudo_corpo">    
    <html:form action="/manterParametroAgenda.do?action=pesquisar" method="post">
    	<html:hidden property="indGrupoSentinela"/>
    	<html:hidden property="adm"/>
    	<html:hidden property="codInstituicao"/>
		<table class="form_tabela" cellspacing="0">
			<tr>
				<td class="form_label" align="right">* Instituição:</td>
				<td colspan="2">
					<html:select property="conInstituicao">
				 	 <html:option value="">-Selecione-</html:option>
					 <html:options collection="listaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
				    </html:select>
				</td>	
			</tr>
		</table>
		<hr>
	  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
		<div align="center">
			<html:button styleClass="form_botao" value="Pesquisar" property="" onclick="javascript:pesquisar();"/>
			<html:button styleClass="form_botao" value="Voltar" property="" onclick="javascript:cancelar();"/>
		</div>  
    </html:form>
    </div>
  </div>
</div>
</body>