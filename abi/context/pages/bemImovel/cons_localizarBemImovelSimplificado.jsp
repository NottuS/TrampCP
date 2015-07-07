<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />

<c:url var="link_pesquisar"	 value="/bemImovel.do?action=pesquisarBemImovel" /> 
<c:url var="var_paginacao"	 value="&indice=%pagina&totalRegistros=%total" /> 
<c:url var="func_navegacao" value="JavaScript:pesquisarPaginado('${link_pesquisar}${var_paginacao}');" />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="JavaScript">

	function capturarDesMunicipio(item){
		document.forms[0].conMunicipio.value = item.options[item.selectedIndex].text;
	}

	function carregarCamposPesquisaPadrao() {
		var form = document.localizarBemImovelSimplificadoForm;			
		if (form.uf.value=='0'){
			form.uf.value='PR';
		}
		form.conOcupacao[2].checked = true;
	}

	function limparCampos(){

		var form = document.localizarBemImovelSimplificadoForm;			
			form.uf.value="PR";
			form.conMunicipio.value="";
			form.codMunicipio.value="0";			
			form.conLogradouro.value="";			
			form.conBairroDistrito.value="";
			form.conDenominacaoImovel.value="";
			form.conOcupacaoT.value="";
			form.conOcupacaoE.value="";
			form.conOcupacaoA.value="";
			form.conOcupacao[2].checked = true;					
	}	

	
	window.onload = function(){
		carregarCamposPesquisaPadrao();
	};
	
	function limpaMensagens() {
		if(document.getElementById("__msgVerificador")!=null)
		 	document.getElementById("__msgVerificador").innerHTML = "";
		if(document.getElementById("msg")!=null) 
			document.getElementById("msg").innerHTML = "";
		if(document.getElementById("msgs_servidor")!=null)
			document.getElementById("msgs_servidor").innerHTML = "";
	}

	function pesquisar(){
		limpaMensagens();
		document.forms[0].conCodUf.value=document.forms[0].uf.value;
		document.forms[0].conCodMunicipio.value=document.forms[0].codMunicipio.value;
		if (document.forms[0].indGrupoSentinela.value == document.forms[0].adm.value){
			if (document.forms[0].conInstituicao.value == ''){
				alert("Favor selecionar a Instituição.");
				return false;
			}	
		}
		submitAjax("localizarBemImovelSimplificado.do?action=pesquisarBemImovel", document.forms[0], 'pesquisaAjax', true);
	}

	
	function selecionar(id, nrBemImovel, codInstituicao) {
		var action = document.forms[0].actionUCOrigem.value;
		window.opener.location = "/abi/"+action+".do?action=retornoLocalizarBemImovel&codBemImovelSimpl="+id+"&camposPesquisaUCOrigem="+document.forms[0].camposPesquisaUCOrigem.value+"&codInstituicao="+codInstituicao+"&nrBemImovel="+nrBemImovel;
		window.focus();
		window.close();
	}
	
	function fechar() {
		 window.focus();
		 window.close();
	}
	
	
</script>


<div id="conteudo">
	<div align="center">
	
	<h1>Pesquisar Bem Imóvel</h1>
	
    <div id="conteudo_corpo">
	    
    <html:form action="/localizarBemImovelSimplificado.do?action=iniciarProcesso">
    <html:hidden property="actionType" value="${localizarBemImovelSimplificadoForm.actionType}" />
    <html:hidden property="actionUCOrigem" value="${localizarBemImovelSimplificadoForm.actionUCOrigem}" />
    <html:hidden property="camposPesquisaUCOrigem" value="${localizarBemImovelSimplificadoForm.camposPesquisaUCOrigem}" />
    <html:hidden property="indGrupoSentinela" />
    <html:hidden property="adm" />
    
    
	<cep:main>
	<cep:form findOnType="false" codificacao="C" textoBusca="procurando...">

		<div style="display:none" align="right">
			<cep:cep name="cep" value="${localizarBemImovelSimplificadoForm.cep}" readonly="true"/>
			<cep:endereco name="endereco" value="${fornecedorForm.endereco}" maxlength="72" size="46"/>
			<cep:bairro name="bairro" value="${fornecedorForm.bairro}" maxlength="72" size="25"/>
		</div>
					
		<table class="form_tabela" cellspacing="0">
			<c:if test="${localizarBemImovelSimplificadoForm.indGrupoSentinela == localizarBemImovelSimplificadoForm.adm}">
				<tr>
					<td class="form_label" align="right">* Instituição:</td>
					<td colspan="2">
						<html:select property="conInstituicao">
					 	 <html:option value="">-Qualquer-</html:option>
						 <html:options collection="listaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
					    </html:select>
					</td>	
				</tr>
			</c:if>
			<tr>
				<td class="form_label" align="right">Estado:</td>
				<td><cep:uf name="uf" value="${localizarBemImovelSimplificadoForm.uf}" /><cep:box/>
				<html:hidden property="conCodUf"/>   
				</td>
				
			</tr>
			<tr>
				<td class="form_label"  align="right">Município:</td>
				<td>
					<cep:municipio name="codMunicipio" value="${localizarBemImovelSimplificadoForm.conCodMunicipio}" onchange="capturarDesMunicipio(this)"/>
					<html:hidden name="conMunicipio" property="conMunicipio" value="${localizarBemImovelSimplificadoForm.conMunicipio}"/>
					<html:hidden property="conCodMunicipio"/>
					<html:hidden property="conLogradouro"/> 		
					<html:hidden property="conBairroDistrito"/> 		 					
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
				<td class="form_label">Ocupação:</td>
				<td colspan="2">
					<html:radio value="1" property="conOcupacao" styleId ="conOcupacaoT" >Terreno</html:radio>
					<html:radio value="2" property="conOcupacao" styleId ="conOcupacaoE" >Edificação</html:radio>
					<html:radio value="3" property="conOcupacao" styleId ="conOcupacaoA" >Ambos</html:radio>
				</td>
			</tr>
			<tr>
				<td class="form_label" align="right" width="210">Observação registrada:</td>
				<td> 
					<html:text property="conObservacao" styleId="conObservacao" maxlength="100" size="100" />
				</td>
			</tr>
		</table>
				
		<hr>
		<c:if test="${localizarBemImovelSimplificadoForm.indGrupoSentinela == localizarBemImovelSimplificadoForm.adm}">
		  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
		</c:if>
		<div align="center">
			<html:button styleClass="form_botao" value="Pesquisar" property=""  onclick="pesquisar();" />
			<html:button styleClass="form_botao" value="Limpar" property="" onclick="limparCampos();"/>			
			<html:button styleClass="form_botao" value="Fechar" property="" onclick="fechar();"/>
		</div>  

  	
  </cep:form>
    </cep:main>
    </html:form>
    
    <div id="pesquisaAjax">
		<tiles:insert definition="listDadosBemImovelSimplificadoAjaxDef">
		</tiles:insert>
	</div>
	
    </div>
  </div>
</div>

