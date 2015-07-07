<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_util" value="/js/generic/util.js"/>
<c:url var="js_funcoes" value="/js/generic/funcoes.js"/>
<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='link_entrada' value='/entrada.do' />
<c:url var="js_cpf" value="/js/generic/cpf.js"/>
<c:url var='link_pesquisar_vistoriador' value='/vistoriador.do?carregarListaVistoriador&nome=${vistoriadorForm.conNome}&cpf=${vistoriadorForm.conCpf}' />
<c:url var='link_incluir_vistoriador' value='/vistoriador.do?action=carregarPgIncluirVistoriador' />
<c:url var='link_editar_vistoriador' value='/vistoriador.do?action=carregarPgEditarVistoriador' />
<c:url var='link_excluir_vistoriador' value='/vistoriador.do?action=excluirVistoriador' />

<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_cpf}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>

<script language="JavaScript">
	
	function pesquisar() {
		var form = document.forms[0];
		Trim(form.conCpf);
		if(form.conCpf.value != '') {
			if (!ValidaCPF(form.conCpf)){
				alert ('CPF inválido!');
				return false;
			}
		}
	
		document.assinaturaForm.action="${link_pesquisar}";
		document.assinaturaForm.submit();
	}

	function incluir() {

		document.vistoriadorForm.action="${link_incluir_vistoriador}";
		document.vistoriadorForm.submit();
	
	}

	function excluir(codigo){
		
		if(confirm('Tem certeza que deseja excluir?')) {
			document.vistoriadorForm.action="${link_excluir_vistoriador}&codVistoriador="+codigo+"&actionType=excluir";
			document.vistoriadorForm.submit();
		}
		
	}
	
	function cancelar() {	
		document.vistoriadorForm.action="${link_entrada}";
		document.vistoriadorForm.submit();
	}
	
</script>

<div id="conteudo">
	<div align="center">
	<h1>Pesquisar Vistoriador</h1>
	
    <div id="conteudo_corpo">    
    <html:form action="/vistoriador.do?action=pesquisarVistoriador" method="post">
    
		<table class="form_tabela" cellspacing="0">
			<c:if test="${vistoriadorForm.indGrupoSentinela == vistoriadorForm.adm}">
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
				<td class="form_label">CPF : </td>
				<td>
					<html:text property="conCpf" styleId="conCpf" size="17" maxlength="14" onkeyup="DigitaNumero(this);MascaraCPF(this,event);" onblur="DigitaNumero(this);MascaraCPF(this,event);"/>
				</td>
			</tr>
			<tr>
				<td class="form_label">Nome : </td>
				<td>
					<html:text maxlength="150" size="100" property="conNome" styleId="conNome"/>
				</td>
			</tr>
		</table>
		<hr>
		<div align="center">
			<html:submit styleClass="form_botao" value="Pesquisar" property="" onclick="pesquisar();"/>
			<html:button styleClass="form_botao" value="Incluir" property="" onclick="incluir();"/>
			<html:button styleClass="form_botao" value="Voltar" property="" onclick="cancelar();"/>
		</div>  
    </html:form>
    </div>
  
  
  <c:if test="${!empty requestScope.pagina.registros}">
  
  <div id="conteudo_corpo">
  <ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
	    <ch:lista bean="${pagina}" atributoId="codVistoriador" />
	    <ch:campo atributo="instituicao.siglaDescricao" label="Instituição" />
	    <ch:campo atributo="cpf" label="CPF" decorator="gov.pr.celepar.abi.util.CPFDecorator"/>
        <ch:campo atributo="nome" label="Nome" />
        <ch:action imagem="${icon_alterar}" link="${link_editar_vistoriador}&codVistoriador=%1" label="Alterar" width="40" align="center" />
        <ch:action imagem="${icon_excluir}" link="javascript:excluir(%1);" label="Excluir" width="40" align="center" />
        <ch:painel pagina="${link_pesquisar_vistoriador}" classe="painel" atributoIndice="indice" />
  </ch:table>  
  </div>
  </c:if>
  
  </div>
</div>