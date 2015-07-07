<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='icon_exibir' value='/images/icon_exibir.png' />
<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />

<c:url var='link_entrada' value='/entrada.do' />
<c:url var='link_pesquisar_cartorio' value='/cartorio.do?action=pesquisarCartorio' />
<c:url var='link_editar_cartorio' value='/cartorio.do?action=carregarPgEditCartorio' />
<c:url var='link_view_cartorio' value='/cartorio.do?action=carregarPgViewCartorio' />
<c:url var='link_excluir_cartorio' value='/cartorio.do?action=excluirCartorio' />

<c:url var="var_paginacao"	 value="&indice=%pagina&totalRegistros=%total" /> 
<c:url var="func_navegacao" value="JavaScript:pesquisarPaginado('${link_pesquisar_cartorio}${var_paginacao}');" />

<script language="javascript" src="js/generic/maisinfo.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="JavaScript">
	function pesquisar() {
		document.manterParametroVistoriaForm.conDescricao.value=document.manterParametroVistoriaForm.conDescricao.value;
		document.manterParametroVistoriaForm.conDenominacaoImovel.value=document.manterParametroVistoriaForm.conDenominacaoImovel.value;
		document.manterParametroVistoriaForm.conAtivo.value=document.manterParametroVistoriaForm.conAtivo.value;
		document.manterParametroVistoriaForm.action="manterParametroVistoria.do?action=pesquisar";
		document.manterParametroVistoriaForm.submit();
	}
	
	function cancelar() {	
		document.manterParametroVistoriaForm.action="${link_entrada}";
		document.manterParametroVistoriaForm.submit();
	}

	function incluirAlterar(id) {
		document.manterParametroVistoriaForm.action="manterParametroVistoria.do?action=carregarInterfaceIncluirAlterar";
		document.manterParametroVistoriaForm.codParametroVistoria.value = id;
		document.manterParametroVistoriaForm.submit();
	}
	function carregarInterfaceExibir(id, operacao) { 
		document.manterParametroVistoriaForm.action="manterParametroVistoria.do?action=carregarInterfaceExibir";
		document.manterParametroVistoriaForm.codParametroVistoria.value = id;
		document.manterParametroVistoriaForm.actionType.value = operacao;
		document.manterParametroVistoriaForm.submit();
	}
</script>

<body onload="carregarCamposPesquisaPadrao()">

<div id="conteudo">
	<div align="center">
	<h1>Pesquisar Parâmetros para Vistoria de Bem Imóvel</h1>
	
    <div id="conteudo_corpo">    
    <html:form action="/manterParametroVistoria.do?action=pesquisar" method="post">
    	<html:hidden property="codParametroVistoria"/>
    	<html:hidden property="actionType"/>
    	<html:hidden property="indGrupoSentinela"/>
    	<html:hidden property="adm"/>
		<table class="form_tabela" cellspacing="0">
			<c:if test="${manterParametroVistoriaForm.indGrupoSentinela == manterParametroVistoriaForm.adm}">
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
					<td class="form_label">Descrição: </td>
					<td>
						<html:text maxlength="100" size="100" property="conDescricao"/>					
					</td>
				</tr>
				<tr>
					<td class="form_label">Denominação:</td>
					<td colspan="2">
						<html:select property="conDenominacaoImovel">
							<html:option value="">-Qualquer-</html:option>
							<html:options collection="denominacaoImovels" property="codDenominacaoImovel" labelProperty="descricao" />						
						</html:select>
					</td>					
				</tr>
				<tr>
					<td class="form_label">Ativo:</td>
					<td colspan="2">
						<input name="conAtivo" type="radio" value="1" <c:if test="${manterParametroVistoriaForm.conAtivo == 1}">checked="checked"</c:if>>Sim 
  			 			<input name="conAtivo" type="radio" value="2" <c:if test="${manterParametroVistoriaForm.conAtivo == 2}">checked="checked"</c:if>>Não 
  			 			<input name="conAtivo" type="radio" value="3" <c:if test="${manterParametroVistoriaForm.conAtivo == 3}">checked="checked"</c:if>>Ambos
					</td>					
				</tr>
		</table>
	   	<hr>
		<div align="center">
			<html:button styleClass="form_botao" value="Pesquisar" property="" onclick="javascript:pesquisar();"/>
			<html:button styleClass="form_botao" value="Incluir" property="" onclick="javascript:incluirAlterar(null);"/>
			<html:button styleClass="form_botao" value="Voltar" property="" onclick="javascript:cancelar();"/>
		</div>  
    </html:form>
    </div>
  
  <c:if test="${!empty pagina.registros}">
  <div id="conteudo_corpo">
  <ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
	    <ch:lista bean="${pagina}" atributoId="codParametroVistoria" />
        	<ch:campo atributo="instituicao.siglaDescricao" label="Instituição"  width="10%" align="left"/>
        	<ch:campo atributo="descricao" label="Descrição" width="20%" />
        	<ch:campo atributo="instanciaAtual" label="Denominação"  width="20%" align="left" decorator="gov.pr.celepar.abi.util.DenominacaoImovelDecorator"/>
        	<ch:campo atributo="instanciaAtual" label="Tipo do Domínio"  width="20%" align="left" decorator="gov.pr.celepar.abi.util.TipoDominioDecorator"/>
        	<ch:campo atributo="instanciaAtual" label="Domínio"  width="20%" align="left" decorator="gov.pr.celepar.abi.util.DominioDecorator"/>
        	<ch:campo atributo="instanciaAtual" label="Situação"  width="5%" align="left" decorator="gov.pr.celepar.abi.util.SituacaoDecorator"/>
        	<ch:campo atributo="instanciaAtual" label="Ativar/ Desativar"  width="3%" align="center" decorator="gov.pr.celepar.abi.util.AtivarDesativarParametroVistoriaDecorator"/>
        	<ch:action imagem="${icon_alterar}" link="javascript:incluirAlterar(%1)" label="Alterar" width="3%" align="center"/>
        	<ch:campo atributo="instanciaAtual" label="Excluir"  width="3%" align="center" decorator="gov.pr.celepar.abi.util.ExcluirParametroVistoriaDecorator"/>
  </ch:table>  
  </div>
  </c:if>
  
  </div>
</div>



</body>