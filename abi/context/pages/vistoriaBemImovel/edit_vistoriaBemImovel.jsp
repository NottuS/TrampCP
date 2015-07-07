<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<c:url var="icon_localizar" value="/images/icon_localizar.png" scope="request" />

<script language="javascript">
	window.onload = function(){
		var form = document.vistoriaBemImovelForm;
		desabilitaCampos();
		if (document.forms[0].indGrupoSentinela.value == document.forms[0].adm.value){
			form.numeroBemImovel.disabled=true;
		}
		if (form.numeroBemImovel.value.length > 0){
			buscaBemImovel();
		}
	};
   
		

	function voltar() {
		document.vistoriaBemImovelForm.action="vistoriaBemImovel.do?action=carregarInterfaceConsultar";
		document.vistoriaBemImovelForm.tipoAcao.value = "voltar";
		document.vistoriaBemImovelForm.submit();
	}

	function incluir() {
		limpaMensagens();
		if (document.vistoriaBemImovelForm.indGrupoSentinela.value == document.vistoriaBemImovelForm.adm.value){
        	if (document.vistoriaBemImovelForm.codInstituicao.value == ""){
    			alert("Campo 'Instituição' é obrigatório!");
    			document.vistoriaBemImovelForm.conInstituicao.focus();
    			return false;
    		}
        }    
		if (document.getElementById("numeroBemImovel").value == "" || document.getElementById("vistoriador").value == "") {
			exibirMsg("Favor preencher campos obrigatórios.", tipoNormal, "");
		} else {
			document.vistoriaBemImovelForm.action="vistoriaBemImovel.do?action=incluir&numeroBemImovel="+document.vistoriaBemImovelForm.numeroBemImovel.value;
			document.vistoriaBemImovelForm.tipoAcao.value = "incluir";
			document.vistoriaBemImovelForm.submit();
		}
	}

	function carregarComboEdificacao(codBemImovel, codInstituicao){
		var url = 'combosAjax.do';
		var form = document.vistoriaBemImovelForm;
		var pars = "action=carregarComboEdificacao&codBemImovel=" + codBemImovel+ "&codInstituicao="+ codInstituicao;

		form.especificacaoEdificacao.options.length = 0;
		
		var ajaxRequest = new Ajax.Request(url, {method:'post', parameters: pars, onComplete: popularComboEdificacao});
	}

	function popularComboEdificacao(originalRequest) {
		var xmlobject;
		var form = document.vistoriaBemImovelForm;
		if (document.implementation.createDocument) {
			xmlobject = (new DOMParser()).parseFromString(
					originalRequest.responseText, "text/xml");
		} else if (window.ActiveXObject) {
			xmlobject = new ActiveXObject("Microsoft.XMLDOM");
			xmlobject.async = "false";
			xmlobject.loadXML(originalRequest.responseText);
		}
		var root = xmlobject.getElementsByTagName('itens')[0];
		var itens = root.getElementsByTagName("item");

		for ( var i = 0; i < itens.length; i++) {
			var it = itens[i];
			form.especificacaoEdificacao.options[form.especificacaoEdificacao.options.length] = new Option(
					it.getAttribute("descr"), it.getAttribute("cod"), false,
					false);
		}
	}

	function buscaBemImovel() {
		var form = document.vistoriaBemImovelForm;
		if (document.vistoriaBemImovelForm.indGrupoSentinela.value == document.vistoriaBemImovelForm.adm.value){
        	if (document.vistoriaBemImovelForm.codInstituicao.value == ""){
    			alert("Campo 'Instituição' é obrigatório!");
    			document.vistoriaBemImovelForm.conInstituicao.focus();
    			return false;
    		}
        } 
		if (form.numeroBemImovel.value.length > 0) {
			submitAjax('/abi/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.numeroBemImovel.value, document.forms[0], "dadosBemImovel",true);
			habilitaCampos();
		} else {
			if (document.getElementById("bemImovelSimplificado") != null) {
				ajaxHTMLLoad('dadosBemImovel','/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.numeroBemImovel.value,true);
			}
			desabilitaCampos();
		}
		if (form.numeroBemImovel.value != ''){
			if (document.vistoriaBemImovelForm.indGrupoSentinela.value == document.vistoriaBemImovelForm.adm.value){
				carregarComboEdificacao(form.numeroBemImovel.value, document.vistoriaBemImovelForm.codInstituicao.value);
				carregarComboVistoriador(document.vistoriaBemImovelForm.codInstituicao.value);
			}else{
				carregarComboEdificacao(form.numeroBemImovel.value, '');
				carregarComboVistoriador('');
			}	
			submitAjax('vistoriaBemImovel.do?action=ajaxListarItensVistoria&codBemImovel='+form.numeroBemImovel.value, document.forms[0], 'divItensVistoria',false);
		}
		
	}

	function carregarComboVistoriador(codInstituicao){
		var url = 'combosAjax.do';
		var form = document.vistoriaBemImovelForm;
		var pars = "action=carregarComboVistoriador&codInstituicao="+ codInstituicao;

		form.vistoriador.options.length = 0;
		
		var ajaxRequest = new Ajax.Request(url, {method:'post', parameters: pars, onComplete: popularComboVistoriador});
	}

	function popularComboVistoriador(originalRequest) {
		var xmlobject;
		var form = document.vistoriaBemImovelForm;
		if (document.implementation.createDocument) {
			xmlobject = (new DOMParser()).parseFromString(
					originalRequest.responseText, "text/xml");
		} else if (window.ActiveXObject) {
			xmlobject = new ActiveXObject("Microsoft.XMLDOM");
			xmlobject.async = "false";
			xmlobject.loadXML(originalRequest.responseText);
		}
		var root = xmlobject.getElementsByTagName('itens')[0];
		var itens = root.getElementsByTagName("item");

		for ( var i = 0; i < itens.length; i++) {
			var it = itens[i];
			form.vistoriador.options[form.vistoriador.options.length] = new Option(
					it.getAttribute("descr"), it.getAttribute("cod"), false,
					false);
		}
	}

	
	function localizarBemImovel() {
		var form = document.vistoriaBemImovelForm;

		var camposPesquisaUCOrigem = "";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conNumeroBemImovel.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conDataInicialVistoria.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conDataFinalVistoria.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conSituacao.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + "I;";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.tipoAcao.value + ";";

		var strParametros="dialogHeight: 1000px; dialogWidth: 700px; dialogleft: 100px; dialogtop: 100px; Help:no; Status:no; Center:yes;";
		window.showModalDialog ('/abi/localizarBemImovelSimplificado.do?action=carregarInterfaceInicial&camposPesquisaUCOrigem='+camposPesquisaUCOrigem+"&actionUCOrigem=vistoriaBemImovel",strParametros);
	}

	function desabilitaCampos() {
		var form = document.vistoriaBemImovelForm;
		document.getElementById("localizarBI").style.display="block";
		form.numeroBemImovel.disabled=false;
		form.especificacaoEdificacao.disabled=true;
		form.vistoriador.disabled=true;
		document.getElementById("divItensVistoria").style.display="none";
	}
	
	function habilitaCampos() {
		var form = document.vistoriaBemImovelForm;
		document.getElementById("localizarBI").style.display="none";
		form.numeroBemImovel.disabled=true;
		form.especificacaoEdificacao.disabled=false;
		form.vistoriador.disabled=false;
		document.getElementById("divItensVistoria").style.display="block";
	}

	

	function habilita() {
		var form = document.vistoriaBemImovelForm;		
		if(form.codInstituicao.value == "") {
			form.numeroBemImovel.disabled=true;	
		}
		else{
			form.numeroBemImovel.disabled=false;	
			buscaBemImovel();
		}
	}

	function limpaMensagens() {
		if(document.getElementById("__msgVerificador")!=null)
		 	document.getElementById("__msgVerificador").innerHTML = "";
		if(document.getElementById("msg")!=null) 
			document.getElementById("msg").innerHTML = "";
		if(document.getElementById("msgs_servidor")!=null)
			document.getElementById("msgs_servidor").innerHTML = "";
	}

</script>

<div id="conteudo">
	<div align="center">
	<h1>Nova Vistoria de Bem Imóvel</h1>

	<html:form action="vistoriaBemImovel.do?action=iniciar">
		<html:hidden property="codVistoria"/>
		<html:hidden property="tipoAcao"/>
		<html:hidden property="conNumeroBemImovel"/>
		<html:hidden property="conDataInicialVistoria"/>
		<html:hidden property="conDataFinalVistoria" />
		<html:hidden property="conSituacao" />
		<html:hidden property="indOperadorOrgao"/>
		<html:hidden property="indGrupoSentinela"/>
		<html:hidden property="adm"/>
		<html:hidden property="conInstituicao"/>
		
		<div id="conteudo_corpo">	
			 <table class="form_tabela" cellspacing="0" width="100%">
				 <c:if test="${vistoriaBemImovelForm.indGrupoSentinela == vistoriaBemImovelForm.adm}">
					<tr>
						<td class="form_label" align="right">* Instituição:</td>
						<td colspan="2">
							<html:select property="codInstituicao" onchange="habilita()">
					 			<html:option value="">-Selecione-</html:option>
						 		<html:options collection="listaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
					    	</html:select>
						</td>	
					</tr>
				</c:if>
				<tr>
					<td class="form_label" width="10%">* Bem Imóvel:</td>
					<td width="90%">
						<table>
							<tr>
								<td>
									<html:text property="numeroBemImovel" styleId="numeroBemImovel" maxlength="10" size="10" onkeyup="DigitaNumero(this);" onblur="buscaBemImovel();" />
								</td>
								<td>
									<div id="localizarBI"> 
										<a href="javascript:localizarBemImovel();"><img src="${icon_localizar}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:localizarBemImovel();">Localizar</a>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="form_label"></td>
					<td> 
						<div id="dadosBemImovel"> 
							<c:if test="${bemImovelSimplificado != null}">
								<tiles:insert definition="viewDadosBemImovelSimplificadoAjaxDef"/>
							</c:if>
						</div>
					</td>
				</tr>
				<tr>
					<td class="form_label" width="10%">Edificação Específica:</td>
					<td width="90%">
						<html:select property="especificacaoEdificacao" styleId="especificacaoEdificacao">
							<html:option value="">- Todas -</html:option>
							<c:if test="${vistoriaBemImovelForm.tipoAcao == 'voltar'}">
							<html:options collection="listaEdificacao" property="codEdificacao" labelProperty="especificacao" />
							</c:if>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="form_label" width="10%">* Vistoriador:</td>
					<td width="90%">
						<html:select property="vistoriador" styleId="vistoriador">
							<html:option value="">- Selecione -</html:option>
							<c:if test="${vistoriaBemImovelForm.tipoAcao == 'voltar'}">
							<html:options collection="listaVistoriador" property="codVistoriador" labelProperty="nome" />
							</c:if>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="form_label">Itens da Vistoria: </td>
					<td class="form_label"></td>
				</tr>
				<tr>
					<td></td>
					<td> 
						<div id="divItensVistoria">	<tiles:insert page="/pages/vistoriaBemImovel/_edit_vistoriaBemImovelParametros.jsp" />	</div>
					</td>
				</tr>
			 </table>
	   	<hr>
	  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
	 	<div align="center">
			<html:button property="btnSalvar" styleClass="form_botao" value="Salvar" onclick="incluir();" />
			<html:button property="btnCancelar" styleClass="form_botao" value="Voltar" onclick="voltar();" />
		</div>	

		</div>	
		  
  </html:form>

  </div>
</div>
