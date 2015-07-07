<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="icon_selecionar" 			value="/images/icon_selecionar.png"/>
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="JavaScript">

	function selecionar(id) {
		var action = document.forms[0].actionUCOrigem.value;
		window.opener.location = "/abi/"+action+".do?action=retornoLocalizarLeiBemImovel&codLeiBemImovel="+id+"&codInstituicao="+codInstituicao+"&camposPesquisaUCOrigem="+document.forms[0].camposPesquisaUCOrigem.value;
	    window.close();
	}
	
	function fechar() {
		  window.close();
	}
	
	
</script>


<div id="conteudo">
	<div align="center">
	<h1>Pesquisar Lei Bem Imóvel</h1>
    <div id="conteudo_corpo">
	    <html:form action="/leiBemImovel.do?action=carregarListaLeiBemImovelLocalizar">
	    <html:hidden property="actionUCOrigem" value="${leiBemImovelForm.actionUCOrigem}" />
	    <html:hidden property="camposPesquisaUCOrigem" value="${leiBemImovelForm.camposPesquisaUCOrigem}" />
	    <html:hidden property="codInstituicao" value="${leiBemImovelForm.codInstituicao}" />
    
		<c:if test="${!empty pagina.registros}">
			<div id="conteudo_corpo">
			  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
				    <ch:lista bean="${pagina}" atributoId="codLeiBemImovel" />
			        <ch:campo atributo="tipoLeiBemImovel.descricao" label="Tipo da Lei" />
			        <ch:campo atributo="numero" label="Nº da Lei" />
			        <ch:campo atributo="dataAssinatura" label="Data de Assinatura" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data"/>
			        <ch:campo atributo="dataPublicacao" label="Data de Publicação"  decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data"/>
			        <ch:campo atributo="nrDioe" label="Nº do Diário Oficial" />
			        <ch:action imagem="${icon_selecionar}" link="javascript:selecionar(%1)" label="Selecionar" width="3%" align="center"/>
			  	</ch:table>  
			</div>
		</c:if>
		<hr>
		<div align="center">
			<html:button styleClass="form_botao" value="Fechar" property="" onclick="fechar();"/>
		</div>  
    </html:form>
    </div>
  </div>
</div>


