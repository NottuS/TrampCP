<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<c:url var="js_util" value="/js/generic/util.js"/>
<c:url var="js_funcoes" value="/js/generic/funcoes.js"/>

<c:url var="icon_exibir" value="/images/icon_exibir.png"/>
<c:url var="icon_alterar" value="/images/icon_alterar.png"/>
<c:url var="icon_excluir" value="/images/icon_excluir.png"/>
<c:url var="icon_info" value="/images/icon_info.png"/>
<c:url var="js_maisinfo" value="/js/generic/maisinfo.js"/>
<c:url var="link_entrada" value="/entrada.do"/>
<c:url var="link_pesquisar_tabelionato" value="/tabelionato.do?action=pesquisarTabelionato"/>
<c:url var="link_editar_tabelionato" value="/tabelionato.do?action=carregarPgEditTabelionato"/>
<c:url var="link_view_tabelionato" value="/tabelionato.do?action=carregarPgViewTabelionato"/>

 
<c:url var="var_paginacao"	 value="&indice=%pagina&totalRegistros=%total" /> 
<c:url var="func_navegacao" value="JavaScript:pesquisarPaginado('${link_pesquisar_tabelionato}${var_paginacao}');" />

<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_maisinfo}"></script>

<script language="JavaScript">

	var id_crono;
	var cont_exec = 0;
	function selecionarMunicipio() {
		var form = document.tabelionatoForm;
	
		if(form.codMunicipio.length <= 1) return;
	
		var municipioUC = form.municipio.value.toUpperCase();
		var i;
		for(i = 0; i < form.codMunicipio.length; i++) {
			if(form.codMunicipio.options[i].text.toUpperCase() == municipioUC) {
				form.codMunicipio.selectedIndex = i;
				window.clearInterval(id_crono);
				return;
			}
		}
		if(++cont_exec == 50) {
			window.clearInterval(id_crono);
		}
	}
	
	function capturarDescrMunicipio(indice) {
		var form = document.tabelionatoForm;
		form.municipio.value = (indice == 0 ? '' : form.codMunicipio.options[indice].text);
	}
	
	function incluir() {
		document.tabelionatoForm.action='${link_editar_tabelionato}&actionType=incluir';
		document.tabelionatoForm.submit();
	}

	function alterar(codigo) {
		document.tabelionatoForm.action='${link_editar_tabelionato}&actionType=alterar&codTabelionato='+codigo;
		document.tabelionatoForm.submit();
	}

	function excluir(codigo) {
		document.tabelionatoForm.action='${link_view_tabelionato}&actionType=excluir&codTabelionato='+codigo;
		document.tabelionatoForm.submit();
	}
	function exibir(codigo) {
		document.tabelionatoForm.action='${link_view_tabelionato}&actionType=exibir&codTabelionato='+codigo;
		document.tabelionatoForm.submit();
	}
	function pesquisar() {
		document.tabelionatoForm.conUF.value=document.tabelionatoForm.uf.value;
		document.tabelionatoForm.conMunicipio.value=document.tabelionatoForm.codMunicipio.value;
		document.tabelionatoForm.action="${link_pesquisar_tabelionato}";
		document.tabelionatoForm.submit();
	}

	function pesquisarPaginado(acao){
				document.tabelionatoForm.conUF.value=document.tabelionatoForm.uf.value;
		document.tabelionatoForm.conMunicipio.value=document.tabelionatoForm.codMunicipio.value;
		document.tabelionatoForm.action=acao;
		document.tabelionatoForm.submit();
	}
	
	
	function cancelar() {
		document.tabelionatoForm.action='${link_entrada}';
		document.tabelionatoForm.submit();
	}

	function carregarCamposPesquisaPadrao() {

		var form = document.tabelionatoForm;			
		
		if (form.uf.value=='0'){
			form.uf.value='PR';
		}

		
	}
	
	function execucaoPeriodica() {
		if (form.uf.value=='0'){
			form.uf.value='PR';
		}
		
	}
		
</script>

<body onload="carregarCamposPesquisaPadrao()">
	

<div id="conteudo">
	<div align="center">
	<h1>Pesquisar Tabelionato</h1>
	
    <div id="conteudo_corpo">    
    <html:form action="/tabelionato.do?action=pesquisarTabelionato" method="post" onsubmit="capturarDescrMunicipio(document.tabelionatoForm.codMunicipio.selectedIndex);">
    <cep:main>
	<cep:form findOnType="false" codificacao="C" textoBusca="procurando...">
		<div style="display:none">
			<cep:cep name="cep" value="" readonly="true"/>
			<cep:endereco name="endereco" value=""/>
			<cep:bairro name="bairro" value=""/>
		</div>
		<table class="form_tabela" cellspacing="0">
			<tr>
				<td class="form_label">Descrição: </td>
				<td>
					<html:text maxlength="255" size="100" property="conDescricao" />
				</td>
			</tr>
			<tr>
				<td class="form_label">Estado: </td>
				<td>
					<cep:uf name="uf" value="${tabelionatoForm.uf}" onchange="capturarDescrMunicipio(0);"/>
					<html:hidden property="conUF"/>
				</td>
			</tr>
			<tr>
				<td class="form_label">Município: </td>
				<td>
					<cep:municipio name="codMunicipio" onchange="capturarDescrMunicipio(this.selectedIndex);"  value="${tabelionatoForm.codMunicipio}"/><cep:box/>
					<html:hidden property="municipio" value="${tabelionatoForm.municipio}"/>
					<html:hidden property="conMunicipio"/>	
				</td>
			</tr>
		</table>
		<hr>
		<div align="center">
			<html:button styleClass="form_botao" value="Pesquisar" property="" onclick="pesquisar();"/>
			<html:button styleClass="form_botao" value="Incluir" property="" onclick="incluir();"/>
			<html:button styleClass="form_botao" value="Voltar" property="" onclick="cancelar();"/>
		</div>
	</cep:form>
    </cep:main>
    </html:form>
    </div>
  
	<c:if test="${!empty requestScope.pagina.registros}">
	<div id="conteudo_corpo">
	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
	    <ch:lista bean="${pagina}" atributoId="codTabelionato" />
        <ch:action imagem="${icon_exibir}" link="javascript:exibir(%1);" label="Exibir" width="40" align="center" />
        
        <ch:campo atributo="descricao" label="Descrição" />
        <ch:campo atributo="logradouro" label="Logradouro" />
        <ch:campo atributo="municipio" label="Município" />
        <ch:campo atributo="uf" label="UF" />

        <ch:action imagem="${icon_alterar}"  link="javascript:alterar(%1);" label="Alterar" width="40" align="center" />
        <ch:action imagem="${icon_excluir}" link="javascript:excluir(%1);" label="Excluir" width="40" align="center" />
        <ch:painel pagina="${func_navegacao}" classe="painel" atributoIndice="indice" />
	</ch:table>  
	</div>
	</c:if>
  
  </div>
</div>
</body>
