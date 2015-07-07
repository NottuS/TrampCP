<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"   prefix="fmt"  %>

<script type="text/javascript" src="js/mensagens.js" ></script>
<script type="text/javascript" src="js/generic/data.js" ></script>
<script type="text/javascript" src="js/generic/util.js" ></script>
<script type="text/javascript" src="js/generic/funcoes.js" ></script>
<script type="text/JavaScript" src="js/generic/maisinfo.js"></script>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<c:url var="js_maisinfo" value="/js/generic/maisinfo.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='icon_info' value='/images/icon_info.png' />
<c:url var='icon_adicionar' value='/images/icon_adicionar1.png' />
<c:url var='link_pesquisar' value='/manterParametroVistoria.do?action=carregarInterfaceInicial' />


<script language="JavaScript" type="text/JavaScript" src="${js_maisinfo}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>


<script language="javascript">
	
	function voltar(){	
		document.manterParametroVistoriaForm.action="manterParametroVistoria.do?action=carregarInterfaceInicial";
		document.manterParametroVistoriaForm.acao.value = "voltar";
		document.manterParametroVistoriaForm.submit();
	}
	
	function ativar() {
		document.manterParametroVistoriaForm.action="manterParametroVistoria.do?action=ativar";	
		document.manterParametroVistoriaForm.submit();	
	}

	function inativar() {
		document.manterParametroVistoriaForm.action="manterParametroVistoria.do?action=desativar";	
		document.manterParametroVistoriaForm.submit();	
	}
	
	function excluir() {
		document.manterParametroVistoriaForm.action="manterParametroVistoria.do?action=excluir";	
		document.manterParametroVistoriaForm.submit();	
	}
	
</script>

	<c:choose> 
		<c:when test='${manterParametroVistoriaForm.actionType == "ativar"}'>
			<c:set var="titulo" value="Ativar"></c:set>
		</c:when>
		<c:when test='${manterParametroVistoriaForm.actionType == "inativar"}'>
			<c:set var="titulo" value="Inativar"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="titulo" value="Excluir"></c:set>
		</c:otherwise>
	</c:choose>


<div id="conteudo">
<div align="center">
<h1>${titulo} parâmetro de Vistoria</h1>

<html:form action="manterParametroVistoria.do?action=iniciarProcesso">
	<html:hidden styleId="codParametroVistoria" property="codParametroVistoria" value="${manterParametroVistoriaForm.codParametroVistoria}"/>
	<html:hidden property="actionType" value="${manterParametroVistoriaForm.actionType}" />
	<html:hidden property="conDescricao" value="${manterParametroVistoriaForm.conDescricao}" />
	<html:hidden property="conDenominacaoImovel" value="${manterParametroVistoriaForm.conDenominacaoImovel}" />
	<html:hidden property="conAtivo" value="${manterParametroVistoriaForm.conAtivo}" />
	<html:hidden property="acao" />
	<html:hidden property="dominioPreenchido" />
	<html:hidden property="acao" />
	<html:hidden property="indGrupoSentinela" />
    <html:hidden property="adm" />
    <html:hidden property="conInstituicao" />
	
    <div id="conteudo_corpo">	
	 	<table class="form_tabela" cellspacing="0" width="100%">
			<tr>
				<td class="form_label" width="15%">Instituição:</td>
				<td>${manterParametroVistoriaForm.instituicao}</td>
			</tr>
			<tr>
				<td class="form_label" width="15%">Descrição:</td>
				<td>${manterParametroVistoriaForm.descricao}</td>
			</tr>
			<c:if test="${!empty manterParametroVistoriaForm.ordemApresentacao}">
				<tr>
					<td class="form_label">Ordem de apresentação:</td>
					<td>${manterParametroVistoriaForm.ordemApresentacao}</td>
				</tr>
			</c:if>
			<tr>
				<td class="form_label">Pode ser utilizado na vistoria de:</td>
				<td colspan="1">
					<c:if test="${!empty paginaDenominacao.registros}">
						<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
    						<ch:lista bean="${paginaDenominacao}" atributoId="descricao" />
       						<ch:campo atributo="descricao" label="Denominação Imóvel" width="100%" />
						</ch:table>  
  					</c:if>
				</td>
			</tr>
			<tr>
				<td class="form_label" width="15%">Tipo de resultado:</td>
				<td>${manterParametroVistoriaForm.indTipoParametro}</td>
			</tr>
			<c:if test="${!empty paginaDominio.registros}">
				<tr>
					<td class="form_label" width="15%">Domínios:</td>
					<td>
						<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
    						<ch:lista bean="${paginaDominio}" atributoId="descricao" />
       						<ch:campo atributo="descricao" label="Descrição" width="100%" />
						</ch:table>  
  					</td>
				</tr>
			</c:if>
		</table>
	
   		<hr>
 		<div align="center">
			<c:if test="${manterParametroVistoriaForm.actionType == 'excluir'}">
				<html:button property="btnSalvar" styleClass="form_botao" value="${titulo}" onclick="excluir();" />
			</c:if>
			<c:if test="${manterParametroVistoriaForm.actionType == 'ativar'}">
				<html:button property="btnSalvar" styleClass="form_botao" value="${titulo}" onclick="ativar();" />
			</c:if>
			<c:if test="${manterParametroVistoriaForm.actionType == 'inativar'}">
				<html:button property="btnSalvar" styleClass="form_botao" value="${titulo}" onclick="inativar();" />
			</c:if>
			<html:button property="btnCancelar" styleClass="form_botao" value="Voltar" onclick="voltar();" />
		</div>	
	</div>

</html:form>

</div>
</div>