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

<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="JavaScript">
	
	function incluir() {
		document.cartorioForm.action="${link_editar_cartorio}&actionType=incluir";
		document.cartorioForm.submit();
	}

	function pesquisar() {
		document.cartorioForm.conUf.value=document.cartorioForm.uf.value;
		document.cartorioForm.conCodMunicipio.value=document.cartorioForm.codMunicipio.value;
		document.cartorioForm.action="${link_pesquisar_cartorio}";
		document.cartorioForm.submit();
	}

	function pesquisarPaginado(acao){
		document.cartorioForm.conUf.value=document.cartorioForm.uf.value;
		document.cartorioForm.conCodMunicipio.value=document.cartorioForm.codMunicipio.value;
		document.cartorioForm.action=acao;
		document.cartorioForm.submit();
	}

	function alterar(codigo) {
		document.cartorioForm.action="${link_editar_cartorio}&actionType=alterar&codCartorio="+codigo;
		document.cartorioForm.submit();
	}
	function exibir(codigo) {
		document.cartorioForm.action="${link_view_cartorio}&actionType=exibir&codCartorio="+codigo;
		document.cartorioForm.submit();
	}

	
	
	function cancelar() {	
		document.cartorioForm.action="${link_entrada}";
		document.cartorioForm.submit();
	}

	function excluir(codigo){
		
		if(confirm('Tem certeza que deseja excluir?')) {
			document.cartorioForm.action="${link_excluir_cartorio}&codCartorio="+codigo+"&actionType=excluir";
			document.cartorioForm.submit();
		}
		
	}

	function carregarCamposPesquisaPadrao() {

		var form = document.cartorioForm;			
		
		
		if (form.uf.value=='0'){
			form.uf.value='PR';
		}
	}

		
	
		
</script>

<body onload="carregarCamposPesquisaPadrao()">

<div id="conteudo">
	<div align="center">
	<h1>Pesquisar Cartório</h1>
	
    <div id="conteudo_corpo">    
    <html:form action="/cartorio.do?action=pesquisarCartorio" method="post">
    	<cep:main>
		<cep:form findOnType="true" codificacao="C" textoBusca="procurando...">
		<div style="display:none" align="right">
			<cep:cep name="cep" readonly="true"/>
			<cep:endereco name="endereco"  maxlength="72" size="46"/>
			<cep:bairro name="bairro"  maxlength="72" size="25"/>
		</div>
		<table class="form_tabela" cellspacing="0">
			<tr>
				<td class="form_label">Descrição: </td>
				<td>
					<html:text maxlength="255" size="100" property="conDescricao" styleId="conDescricao" />
					<html:hidden property="conUf"/>
					<html:hidden property="conCodMunicipio"/>

				</td>
			</tr>
			<tr>
				<td class="form_label" align="right">Estado:</td>
				<td><cep:uf name="uf"/></td>
				
			</tr>
			<tr>
				<td class="form_label"  align="right">Município:</td>
				<td>
				<cep:municipio name="codMunicipio" value="${cartorioForm.codMunicipio}" />
				</td>
			</tr>
		</table>
		<hr>
		<div align="center">
			<html:button styleClass="form_botao" value="Pesquisar" property="" onclick="javascript:pesquisar();"/>
			<html:button styleClass="form_botao" value="Incluir" property="" onclick="javascript:incluir();"/>
			<html:button styleClass="form_botao" value="Voltar" property="" onclick="javascript:cancelar();"/>
		</div>  
		<cep:box />
	 	</cep:form>
    	</cep:main>
    </html:form>
    </div>
  
  <c:if test="${!empty requestScope.pagina.registros}">
  <div id="conteudo_corpo">
  <ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
	    <ch:lista bean="${pagina}" atributoId="codCartorio" />
        <ch:action imagem="${icon_exibir}" link="javascript:exibir(%1);" label="Exibir" width="40" align="center" />
        <ch:campo atributo="descricao" label="Descrição" />
        <ch:campo atributo="logradouro" label="Logradouro" />
        <ch:campo atributo="municipio" label="Município" />
        <ch:campo atributo="uf" label="Estado" />
        <ch:action imagem="${icon_alterar}"  link="javascript:alterar(%1);" label="Alterar" width="40" align="center" />
        <ch:action imagem="${icon_excluir}" link="javascript:excluir(%1);" label="Excluir" width="40" align="center" />
         <ch:painel pagina="${func_navegacao}" classe="painel" atributoIndice="indice" />
  </ch:table>  
  </div>
  </c:if>
  
  </div>
</div>



</body>