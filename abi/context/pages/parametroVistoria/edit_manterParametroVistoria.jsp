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
	window.onload = function() {
		if (document.getElementById("codParametroVistoria").value == ""){
			if (document.forms[0].dominioPreenchido.value == 'false'){
				mostraOcultaDominio(1);
			}else{
				mostraOcultaDominio(2);
			}
		}else{
			
		}
	}
	
	function selecionarTodos(objListBoxName) {
		var s = document.getElementsByTagName("select");			
		var s1;
		if (s.length > 0) {
			window.select_current = new Array();
			for (var i = 0, select; select = s[i]; i++) {
				if(select.name == objListBoxName){
				  	s1 = select;
			      	for (var intLoop=0; intLoop < s1.length; intLoop++) {															      
			         	s1[intLoop].selected = true;
			      	}
												
				}
			}
		}			
	}

	function desmarcarTodos(objListBoxName) {
		var s = document.getElementsByTagName("select");			
		var s1;
		if (s.length > 0) {
			window.select_current = new Array();
			for (var i = 0, select; select = s[i]; i++) {
				if(select.name == objListBoxName){
				  	s1 = select;
			      	for (var intLoop=0; intLoop < s1.length; intLoop++) {															      
			         	s1[intLoop].selected = false;
			      	}
												
				}
			}
		}			
	}


	function Trim(obj)
	{	
		Ltrim(obj);
	    Rtrim(obj);
	}


	function Rtrim(obj) 
	{
		varx = obj.value;
		while (varx.substr(varx.length - 1,1) == " ") 
		{
			varx = varx.substr(0, varx.length -1);
		}   
		obj.value = varx;
	}


	function Ltrim(obj) 
	{
		varx = obj.value;
		while (varx.substr(0,1) == " ") 
	{
		varx = varx.substr(1, varx.length -1);
		}
		obj.value = varx;
	}
	
	function voltar(){	
		document.manterParametroVistoriaForm.descricao.value = "";
		document.manterParametroVistoriaForm.actionType.value = "pesquisar";
		document.manterParametroVistoriaForm.action="${link_pesquisar}";
		document.manterParametroVistoriaForm.submit();
	}
	
	function validarCampos(){
		
		var form = document.manterParametroVistoriaForm;
		Trim(form.descricao);
		if (document.forms[0].indGrupoSentinela.value == document.forms[0].adm.value &&document.forms[0].actionType.value != 'alterar'){
			if (form.conInstituicao.value == ""){
				alert("Campo 'Instituição' é obrigatório!");
				form.conInstituicao.focus();
				return false;
			}
		}
		if (form.descricao.value == ""){
			alert("Campo 'Descrição' é obrigatório!");
			form.descricao.focus();
			return false;
		}
		var s = document.getElementsByTagName("select");			
		var s1;
		var ok = 0;
		if (s.length > 0) {
			window.select_current = new Array();
			for (var i = 0, select; select = s[i]; i++) {
				objList = 'listaDenominacaoImovelSelecionada';
				if(select.name == objList){
				  	s1 = select;
				  	if (s1.length > 0){
					  	ok = 1;
				  	} 
				}
			}
		}	
		if (ok != 1){
			alert("Campo 'Denominações de Imóveis associadas' é obrigatório!");
			return false;
		}

		var s = document.getElementsByTagName("input");
		var resultado = "";
		var inputs = document.getElementsByTagName("input");
		for (j = 0; j < inputs.length; j++ ) {
			if(inputs[j].type == "radio" && inputs[j].name == "indTipoParametro") {
			check = inputs[j];
				if(check.checked){
					resultado = check.value;
				}						
			}
		}
		if (resultado == ""){
			alert("Campo 'Tipo de resultado' é obrigatório!");
			return false;
		}
		if (resultado != '1'){
			if (document.forms[0].dominioPreenchido.value == 'false'){
				alert("Campo 'Domínio' é obrigatório!");
				return false;
			}
		}		
		return true;
	}

	function mostraOcultaDominio(id) {
		if (id == '1'){
			document.getElementById("dominioDiv").style.display = "none";
		}else{
			document.getElementById("dominioDiv").style.display = "block";
		}
	}

	function atualizarDominio(){
		if(document.manterParametroVistoriaForm.dominio.value != ""){
			submitAjax("manterParametroVistoria.do?action=atualizarDominio&dominio="+document.manterParametroVistoriaForm.dominio.value, document.forms[0], 'pesquisaAjax', false);
		}else{
			alert("Favor informar o domínio!");
		}
		
	}		

	function incluirAlterar() {
		if (validarCampos()){
			disponiveis(true);    
		  	selecionadas(true);
			if (document.manterParametroVistoriaForm.codParametroVistoria.value == ''){
				document.manterParametroVistoriaForm.action="manterParametroVistoria.do?action=incluir";	
			}else{
				document.manterParametroVistoriaForm.action="manterParametroVistoria.do?action=alterar";
			}
			document.manterParametroVistoriaForm.submit();	
		}
	}

	function disponiveis(arg){	
		for(i = 0; i < document.forms[0].listaDenominacaoImovelDisponivel.length; i++)
			document.forms[0].listaDenominacaoImovelDisponivel[i].selected = arg;
	}

	function selecionadas(arg){
		for(i = 0; i < document.forms[0].listaDenominacaoImovelSelecionada.length; i++)
			document.forms[0].listaDenominacaoImovelSelecionada[i].selected = arg;
	}
	
	function excluirDominio(id){
		submitAjax("manterParametroVistoria.do?action=excluirDominio&dominio="+id, document.forms[0], 'pesquisaAjax', false);
	}		
	
</script>

	<c:choose> 
		<c:when test='${manterParametroVistoriaForm.actionType == "incluir"}'>
			<c:set var="acao" value="manterParametroVistoria.do?action=incluir"></c:set>
			<c:set var="titulo" value="Incluir"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="acao" value="manterParametroVistoria.do?action=alterar"></c:set>
			<c:set var="titulo" value="Alterar"></c:set>
		</c:otherwise>
	</c:choose>


<div id="conteudo">
<div align="center">
<h1>${titulo} parâmetro de Vistoria</h1>

<html:form action="manterParametroVistoria.do?action=iniciarProcesso">
	<html:hidden styleId="codParametroVistoria" property="codParametroVistoria" value="${manterParametroVistoriaForm.codParametroVistoria}"/>
	<html:hidden property="actionType" />
	<html:hidden property="conDescricao" />
	<html:hidden property="conDenominacaoImovel"  />
	<html:hidden property="conAtivo"/>
	<html:hidden property="dominioPreenchido" />
	<html:hidden property="acao" />
	<html:hidden property="indGrupoSentinela" />
    <html:hidden property="adm" />
	
    <div id="conteudo_corpo">	
	 	<table class="form_tabela" cellspacing="0" width="100%">
			<c:if test="${manterParametroVistoriaForm.indGrupoSentinela == manterParametroVistoriaForm.adm && manterParametroVistoriaForm.actionType != 'alterar'}">
				<tr>
					<td class="form_label" align="right">* Instituição:</td>
					<td colspan="2">
				 		<html:select property="conInstituicao">
				 	 	<html:option value="">-Selecione-</html:option>
					 	<html:options collection="listaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
				    	</html:select>
					</td>	
				</tr>
			</c:if>
			<c:if test="${manterParametroVistoriaForm.indGrupoSentinela == manterParametroVistoriaForm.adm && manterParametroVistoriaForm.actionType == 'alterar'}">
				<tr>
					<td class="form_label" align="right">Instituição:</td>
					<td colspan="2">${manterParametroVistoriaForm.instituicao} <html:hidden property="conInstituicao" /></td>	
				</tr>
			</c:if>
			<tr>
				<td class="form_label" width="15%">* Descrição:</td>
				<td><html:text  property="descricao" maxlength="255" size="150" styleId="descricao" /></td>
			</tr>
			<tr>
				<td class="form_label">Ordem de apresentação:</td>
				<td><html:text  property="ordemApresentacao" maxlength="5" size="5" styleId="ordemApresentacao" onkeyup="javascript:DigitaNumero(this);"/></td>
			</tr>
			<tr>
				<td colspan="2" class="form_label">
					<h2>Pode ser utilizado na vistoria de:</h2>
					<table cellspacing="0" class="form_tabela" width=100% align="center">
						<tr>
							<td valign="top" align="center">
								<div align="center">
									<ch:select classe="form_tabela" ordenada="true">
										<ch:esquerda combo="listaDenominacaoImovelDisponivel" label="Denominações de Imóveis NÃO associadas" onError="alert('Selecione uma Denominação!');" classe="lista_select">
											<c:if test="${listaDenominacaoImovelDisponivel != null}">
												<ch:opcoes colecao="listaDenominacaoImovelDisponivel" nome="descricao" valor="codDenominacaoImovel"/>
											</c:if>
										</ch:esquerda>
										<ch:direita  combo="listaDenominacaoImovelSelecionada" label="Denominações de Imóveis associadas" onError="alert('Selecione uma Denominação!');" classe="lista_select">
											<c:if test="${listaDenominacaoImovelSelecionada != null}">
												<ch:opcoes colecao="listaDenominacaoImovelSelecionada" nome="descricao" valor="codDenominacaoImovel"/>
											</c:if>
										</ch:direita>
									</ch:select>
								</div>
							</td>
						</tr>
					</table>
					<table cellspacing="0" class="form_tabela" width=100% align="center">
						<tr>
							<td valign="top" align="center">
								<div align="right">
									<a href="javascript:selecionarTodos('listaDenominacaoImovelDisponivel');">Selecionar Todos</a>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="javascript:desmarcarTodos('listaDenominacaoImovelDisponivel');">Desmarcar Todos</a>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</div>
							</td>
							<td width="30"></td>
							<td valign="top" align="center">
								<div align="left">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="javascript:selecionarTodos('listaDenominacaoImovelSelecionada');">Selecionar Todos</a>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="javascript:desmarcarTodos('listaDenominacaoImovelSelecionada');">Desmarcar Todos</a>
								</div>
							</td>
    					</tr>
    				</table> 
			</tr>
		</table>
		<h2>Resultado do parâmetro</h2>
		<table class="form_tabela" cellspacing="0" width="100%">
			<tr>
				<td class="form_label" width="15%">* Tipo de resultado:</td>
				<td>
					<table>
						<tr>
							<td><html:radio property="indTipoParametro" value="1" onclick="mostraOcultaDominio(1)"/>Texto livre
							</td>
						</tr>
						<tr>
							<td><html:radio property="indTipoParametro" value="2" onclick="mostraOcultaDominio(2)"/>Domínio unitário <i>(ex.Padrão de acabamento: Luxo <b>OU</b> Normal)</i>
							</td>
						</tr>
						<tr>
							<td><html:radio property="indTipoParametro" value="3" onclick="mostraOcultaDominio(2)"/>Domínio múltiplo <i>(ex.Revestimento interno: Cerâmico <b>E</b> PVA)</i>
							</td>
						</tr>
					</table>
  				</td>
			</tr>
			<tr>
				<td class="form_label" colspan="2">
					<div id="dominioDiv" style="block" align="left">
						<h2>Relação de Domínios</h2>
						<table cellspacing="0" class="form_tabela" width=100% align="left">
							<tr>
								<td class="form_label" width="15%">* Domínio:</td>
								<td><html:text  property="dominio" maxlength="155" size="100" styleId="dominio"/> <a href="javascript:atualizarDominio();"><img src="${icon_adicionar}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:atualizarDominio()">Adicionar</a></td>
							</tr>
							<tr>
								<td class="form_label"></td>
								<td>
									<div id="pesquisaAjax">
										<tiles:insert page="/pages/parametroVistoria/_edit_manterParametroVistoria.jsp" />
									</div>
								</td>
							</tr>
						</table>
					</div>
				</td>
				<td class="form_label"></td>
			</tr>
		</table>
	
   		<hr>
  		<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
 		<div align="center">
			<html:button property="btnSalvar" styleClass="form_botao" value="${titulo}" onclick="incluirAlterar();" />
			<html:button property="btnCancelar" styleClass="form_botao" value="Voltar" onclick="voltar();" />
		</div>	
	</div>

</html:form>

</div>
</div>