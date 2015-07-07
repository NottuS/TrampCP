<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%> 

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_data" value="/js/generic/data.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_prototype" value="/js/generic/prototype.js" />
<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var='icon_calendar' value='/images/icon_calendario.png' />
<c:url var='icon_calculadora' value='/images/icon_calculadora.png' />
<c:url var='icon_lupa_planeta' value='/images/icon_lupa_planeta.png' />
<c:url var='icon_adicionar' value='/images/icon_adicionar1.png'/>
<c:url var='icon_excluir' value='/images/icon_excluir.png'/>
<c:url var='icon_reativar' value='/images/icon_desfazer.png'/>

<c:url var='parametroCodBemImovel' value='&codBemImovel=${bemImovelForm.codBemImovel}&administracao=${bemImovelForm.administracao}&instituicao=${bemImovelForm.instituicao}' />
<c:url var='link_aba_leiBemImovel' value='/leiBemImovel.do?action=carregarPgEditLeiBemImovel${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_quadraLote' value='/quadraLote.do?action=carregarPgEditQuadraLote${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_confrontante' value='/confrontante.do?action=carregarPgEditConfrontante${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_avaliacao' value='/avaliacao.do?action=carregarPgEditAvaliacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_coordenadaUtm' value='/coordenadaUtm.do?action=carregarPgEditCoordenadaUtm${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_edificacao' value='/edificacao.do?action=carregarPgEditEdificacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_documentacao' value='/documentacao.do?action=carregarPgEditDocumentacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_anexo' value='/anexoBemImovel.do?action=carregarPgEditAnexo${parametroCodBemImovel}&actionType=incluir' />

<c:url var='link_pesquisar' value='/bemImovel.do?action=pesquisarBemImovel' />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_prototype}"></script>

<script language="javascript">


	function habilitarAlterar(){
		var form = document.bemImovelForm;
		if (form.actionType.value=="alterar") {
			form.btnSalvar.disabled=false;
		}
	}

	function habilitarOrgao(){
		var form = document.bemImovelForm;
		if (form.instituicao.value != '' && form.instituicao.value != '0') {
			if(document.getElementById("administracaoDireta2").checked || document.getElementById("administracaoDireta3").checked){
				form.orgao.disabled=false;
				submitAjax('/abi/bemImovel.do?action=carregarComboOrgao', document.forms[0], 'divComboOrgao',false);
			}else{
				form.orgao.disabled=true;
				form.orgao.value="";
			}
		}else{
			form.orgao.disabled=true;
			form.orgao.value="";
		}
	}

	function habilitarOrgaoResp(){
		var form = document.bemImovelForm;
		if (form.instituicao.value != '' && form.instituicao.value != '0') {
			if(document.getElementById("administracaoDireta2").checked || document.getElementById("administracaoDireta3").checked){
				form.or_codOrgao.disabled=false;
				submitAjax('/abi/bemImovel.do?action=carregarComboOrgaoResp', document.forms[0], 'divComboOrgaoResp',false);
			}else{
				form.or_codOrgao.disabled=true;
				form.or_codOrgao.value="";
			}
		}else{
			form.or_codOrgao.disabled=true;
			form.or_codOrgao.value="";
		}
	}

	function habilitarOrgaoSemAjax(){
		var form = document.bemImovelForm;
		if (form.instituicao.value != '' && form.instituicao.value != '0') {
			if(document.getElementById("administracaoDireta2").checked || document.getElementById("administracaoDireta3").checked){
				form.orgao.disabled=false;
				form.or_codOrgao.disabled=false;
			}else{
				form.orgao.disabled=true;
				form.orgao.value="";
				form.or_codOrgao.disabled=true;
				form.or_codOrgao.value="";
			}
		}else{
			form.orgao.disabled=true;
			form.orgao.value="";
			form.or_codOrgao.disabled=true;
			form.or_codOrgao.value="";
		}
	}
	
	function habilitarRuralUrbano(){
		var form = document.bemImovelForm;
		var classificacao = document.getElementById("classificacaoBemImovel").value;
		if( classificacao == ""){
			form.nirf.disabled=true;
			form.niif.disabled=true;
			form.incra.disabled=true;
			form.nirf.value="";
			form.niif.value="";
			form.incra.value="";
		}else{
			if(classificacao == ${rural}){
				form.niif.disabled=true;
				form.niif.value="";
				form.nirf.disabled=false;
				form.incra.disabled=false;
			}else if (classificacao == ${urbano}){
				form.niif.disabled=false;
				form.nirf.disabled=true;
				form.incra.disabled=true;
				form.nirf.value="";
				form.incra.value="";
			}
		}
	}

	function habilitarCampos()
	{
		var form = document.bemImovelForm;

		if (form.actionType.value=="incluir") {
			
			Trim(form.nrBemImovel);
			if (form.nrBemImovel.value=="") {
				form.administracaoDireta1.disabled=true;
				form.administracaoDireta2.disabled=true;
				form.administracaoDireta3.disabled=true;
				form.orgao.disabled=true;
				form.classificacaoBemImovel.disabled=true;
				form.situacaoLocal1.disabled=true;
				form.situacaoLocal2.disabled=true;
				form.situacaoLegalCartorial.disabled=true;
				form.numeroProcessoSpi.disabled=true;
				form.cep.disabled=true;
				form.uf.disabled=true;
				form.codMunicipio.disabled=true;
				form.bairroDistrito.disabled=true;
				form.formaIncorporacao.disabled=true;
				form.dataIncorporacao.disabled=true;
				form.situacaoImovel.disabled=true;
				form.observacoesMigracao.disabled=true;
				form.logradouro.disabled=true;
				form.numero.disabled=true;
				form.complemento.disabled=true;
				form.denominacaoImovel.disabled=true;
				form.areaTerreno.disabled=true;
				form.somenteTerrenoS.disabled=true;
				form.somenteTerrenoN.disabled=true;
				form.areaOriginal.disabled=true;
				form.medidaOriginal.disabled=true;
			} else {
				form.administracaoDireta1.disabled=false;
				form.administracaoDireta2.disabled=false;
				form.administracaoDireta3.disabled=false;
				form.classificacaoBemImovel.disabled=false;
				form.situacaoLocal1.disabled=false;
				form.situacaoLocal2.disabled=false;
				form.situacaoLegalCartorial.disabled=false;
				form.numeroProcessoSpi.disabled=false;
				form.cep.disabled=false;
				form.uf.disabled=false;
				form.codMunicipio.disabled=false;
				form.bairroDistrito.disabled=false;
				form.formaIncorporacao.disabled=false;
				form.dataIncorporacao.disabled=false;
				form.situacaoImovel.disabled=false;
				form.observacoesMigracao.disabled=false;
				form.logradouro.disabled=false;
				form.numero.disabled=false;
				form.complemento.disabled=false;
				form.denominacaoImovel.disabled=false;
				form.areaTerreno.disabled=false;
				form.somenteTerrenoS.disabled=false;
				form.somenteTerrenoN.disabled=false;
				form.areaOriginal.disabled=false;
				form.medidaOriginal.disabled=false;
			}
		}

		if(document.getElementById("somenteTerrenoS").checked){
			document.getElementById("divResponsavel").style.display="block";
			document.getElementById("abaEdificacao").style.display="none";
			document.getElementById("abaEdificacaoDesabilitada").style.display="block";
			if (document.getElementById("lblListaOrgaoOcupante") != null) {
				document.getElementById("lblListaOrgaoOcupante").style.display="none";
			}
		}else{
			document.getElementById("divResponsavel").style.display="none";
			document.getElementById("abaEdificacao").style.display="block";
			document.getElementById("abaEdificacaoDesabilitada").style.display="none";
			if (document.getElementById("lblListaOrgaoOcupante") != null) {
				document.getElementById("lblListaOrgaoOcupante").style.display="block";
			}
		}
		
		habilitaLabelReativar();
		habilitarOrgaoSemAjax();
		
	}

	function VerNoMapa(){
		var form = document.bemImovelForm;
		if (form.logradouro.value == "")
		{
			alert("Campo 'Logradouro' é obrigatório!");
			form.logradouro.focus();
		
		}
		else if(form.codMunicipio.value=="0"){
			alert("Campo 'Municipio' é obrigatório!");
			form.codMunicipio.focus();
		}
		else
		{
			var endereco = form.logradouro.value + " " + form.numero.value;
			endereco=endereco+", "+form.codMunicipio.options[form.codMunicipio.selectedIndex].text;
			endereco=endereco+", "+form.uf.value+", brasil";
			endereco=endereco.replace(/[ ]/g,"+");
			var url="http://maps.google.com/maps?f=q&source=s_q&hl=en&geocode=&q="+endereco+"&sll=37.0625,-95.677068&sspn=0.00863,0.015814&ie=ISO88591&hq=&hnear="+endereco+"&z=16";
			window_open(url,"mapa","");
		}
	}

	function window_open(theURL,winName,features) {
		  winName = window.open(theURL,winName,features);
		  winName.window.focus();
	}
		
	function validarCampos(){
	
		var form = document.bemImovelForm;
		if (document.forms[0].isGpAdmGeralUsuarioLogado.value == 'S'){
			if (form.instituicao.value == '' || form.instituicao.value == '0'){
				alert("Campo 'Instituição' é obrigatório!");
				form.instituicao.focus();
				return false;
			}
		}
		Trim(form.nrBemImovel);
		if (form.nrBemImovel.value == ""){
			alert("Campo 'Identificação do Imóvel CPE' é obrigatório!");
			form.nrBemImovel.focus();
			return false;
		}
		
		if(document.getElementById("administracaoDireta2").checked || document.getElementById("administracaoDireta3").checked){
			if (form.orgao.value == ""){
				alert("Campo 'Órgão Proprietário' é obrigatório!");
				form.orgao.focus();
				return false;
			}
		}
		if (form.classificacaoBemImovel.value == ""){
			alert("Campo 'Classificação do Imóvel' é obrigatório!");
			form.classificacaoBemImovel.focus();
			return false;
		}
		
		if (form.situacaoLegalCartorial.value == ""){
			alert("Campo 'Situação Legal - Cartorial' é obrigatório!");
			form.situacaoLegalCartorial.focus();
			return false;
		}
		if (form.uf.value == "0" || form.uf.value == ""){
			alert("Campo 'Estado' é obrigatório!");
			form.uf.focus();
			return false;
		}
		if (form.codMunicipio.value == "0" || form.codMunicipio.value == "" ){
			alert("Campo 'Município' é obrigatório!");
			form.codMunicipio.focus();
			return false;
		}
		
		if (form.formaIncorporacao.value != "" ){
			if (form.dataIncorporacao.value ==  "" ){
				alert("Campo 'Data da Incorporação' é obrigatório!");
				form.dataIncorporacao.focus();
				return false;
			}
			if (!ValidaData(form.dataIncorporacao)){
				alert("Campo 'Data da Incorporação' deve ser uma data válida!");
				form.dataIncorporacao.focus();
				return false;			
			}
		}
		
		Trim(form.areaTerreno);
		if (form.areaTerreno.value == ""){
			alert("Campo 'Área do Terreno (m²)' é obrigatório!");
			form.areaTerreno.focus();
			return false;
		}
		
		
		form.municipio.value = form.codMunicipio.options[form.codMunicipio.selectedIndex].text;
	
		form.submit();
	}

	function trocarAba(aba){
		
		var form = document.bemImovelForm;
		if (form.actionType.value=="incluir") {
			alert("Você deve salvar o bem imóvel antes de preencher as abas!");
		} else {
			document.bemImovelForm.action=aba;
			form.submit();
		}
	}

	function TransformarM()
	{
		var form = document.bemImovelForm;
		if (form.areaOriginal.value == ""){
			alert("Favor preencher Área Original e Unidade Original para efetuar o cálculo em m2");
			form.areaOriginal.focus();
		}
		else {
			submitAjax("bemImovel.do?action=transformarMetroQuadrado", document.forms[0], 'areaBemImovel', false);
		}
		
	}

	function voltar()
	{
		if(confirm('Tem certeza que deseja sair?')) {
			document.bemImovelForm.uf.value="";
			document.bemImovelForm.codMunicipio.value="";
			document.bemImovelForm.action='${link_pesquisar}';
			document.bemImovelForm.submit();
		}
	}
	
	function habilitarResponsavel(){
		var form = document.bemImovelForm;
		var acao = "";
		form.municipio.value = form.codMunicipio.options[form.codMunicipio.selectedIndex].text;
		habilitaLabelReativar();
		if(document.getElementById("somenteTerrenoS").checked){
			if (form.qtdRegEdificacao.value > 0) {
				if(confirm('Tem certeza que deseja excluir as edificações desse bem imóvel?')) {
					acao='bemImovel.do?action=removerEdificacoesDoBemImovel';
				} else {
					document.getElementById("somenteTerrenoS").checked = false;
					document.getElementById("somenteTerrenoN").checked = true;
				}
			} else {
				document.getElementById("divResponsavel").style.display="block";
				document.getElementById("abaEdificacao").style.display="none";
				document.getElementById("abaEdificacaoDesabilitada").style.display="block";
				if (document.getElementById("lblListaOrgaoOcupante") != null) {
					document.getElementById("lblListaOrgaoOcupante").style.display="none";
				}
				if (form.actionType.value!="incluir") {
					acao = 'bemImovel.do?action=alterarBemImovel';
				}
			}
		}else{
			if (form.qtdRegResponsavelAtivo.value > 0) {
				if(confirm('Tem certeza que deseja inativar os órgãos responsáveis ocupantes desse bem imóvel?')) {
					acao='bemImovel.do?action=inativarOrgaoOcupanteDoBemImovel';
				} else {
					document.getElementById("somenteTerrenoS").checked = true;
					document.getElementById("somenteTerrenoN").checked = false;
				}
			} else {
				document.getElementById("divResponsavel").style.display="none";
				document.getElementById("abaEdificacao").style.display="block";
				document.getElementById("abaEdificacaoDesabilitada").style.display="none";
				if (document.getElementById("lblListaOrgaoOcupante") != null) {
					document.getElementById("lblListaOrgaoOcupante").style.display="block";
				}
				if (form.actionType.value!="incluir") {
					acao = 'bemImovel.do?action=alterarBemImovel';
				}
			}
		}
		if (acao != "" ) {
			document.bemImovelForm.action=acao;
			document.bemImovelForm.submit();
		}
	}
	
	function adicionarOcupacaoOrgaoResponsavel() {
		if (validarCamposOcupacaoOrgaoResponsavel()){
			document.bemImovelForm.action='bemImovel.do?action=adicionarOcupacaoOrgaoResponsavel';
			document.bemImovelForm.submit();
		}
	}
	
	function removerOcupacaoOrgaoResponsavel(codigo){
		if (codigo != "") {
			if(confirm('Tem certeza que deseja excluir?')) {
				document.bemImovelForm.action='bemImovel.do?action=removerOcupacaoOrgaoResponsavel&or_codOcupacao='+codigo;;
				document.bemImovelForm.submit();
			}
		} else {
			alert ('Selecione um item para excluir!');
		}
	}

	function validarCamposOcupacaoOrgaoResponsavel() {
		var form = document.bemImovelForm;

		Trim(form.nrBemImovel);
		if (form.nrBemImovel.value == ""){
			alert("Campo 'Identificação do Imóvel CPE' é obrigatório!");
			form.nrBemImovel.focus();
			return false;
		}
		
		if(document.getElementById("administracaoDireta2").checked || document.getElementById("administracaoDireta3").checked){
			if (form.orgao.value == ""){
				alert("Campo 'Órgão Proprietário' é obrigatório!");
				form.orgao.focus();
				return false;
			}
		}
		if (form.classificacaoBemImovel.value == ""){
			alert("Campo 'Classificação do Imóvel' é obrigatório!");
			form.classificacaoBemImovel.focus();
			return false;
		}
		if (form.situacaoLegalCartorial.value == ""){
			alert("Campo 'Situação Legal - Cartorial' é obrigatório!");
			form.situacaoLegalCartorial.focus();
			return false;
		}
		if (form.uf.value == "0" || form.uf.value == ""){
			alert("Campo 'Estado' é obrigatório!");
			form.uf.focus();
			return false;
		}
		if (form.codMunicipio.value == "0" || form.codMunicipio.value == "" ){
			alert("Campo 'Município' é obrigatório!");
			form.codMunicipio.focus();
			return false;
		}
		
		if (form.formaIncorporacao.value != "" ){
			if (form.dataIncorporacao.value ==  "" ){
				alert("Campo 'Data da Incorporação' é obrigatório!");
				form.numeroDocumentoTabelional.focus();
				return false;
			}
			if (!ValidaData(form.dataIncorporacao)){
				alert("Campo 'Data da Incorporação' deve ser uma data válida!");
				form.dataIncorporacao.focus();
				return false;			
			}
		}
		
		Trim(form.areaTerreno);
		if (form.areaTerreno.value == ""){
			alert("Campo 'Área do Terreno (m²)' é obrigatório!");
			form.areaTerreno.focus();
			return false;
		}
		
		form.municipio.value = form.codMunicipio.options[form.codMunicipio.selectedIndex].text;

		if (form.or_situacaoOcupacao.value == ""){
			alert("Campo 'Situação da Ocupação' é obrigatório!");
			form.or_situacaoOcupacao.focus();
			return false;
		}
		
		Trim(form.or_descricao);
		if(form.or_descricao.value == "") {
			alert("Campo 'Descrição' é obrigatório!");
			form.or_descricao.focus();
			return false;
		}

		if(form.or_ocupacaoMetroQuadrado.value == "" && form.or_ocupacaoPercentual.value == "") {
			alert("A Ocupação em m2 ou a Ocupação em % deverá ser preenchida!");
			form.or_ocupacaoMetroQuadrado.focus();
			return false;
		}

		if(form.or_codOrgao.value == '') {
			alert('Campo \'Órgão\' é obrigatório!');
			form.or_descricao.focus();
			return false;
		}
		
		return true;
	}
	
	function calcularOcupacao() {
		var form = document.bemImovelForm;
		if(form.or_ocupacaoMetroQuadrado.value == "" && form.or_ocupacaoPercentual.value == "") {
			alert("Para efetuar o cálculo a Ocupação em m² ou a Ocupação em % deverá ser preenchida!");
			form.or_ocupacaoMetroQuadrado.focus();
		} else {
			submitAjax('/abi/bemImovel.do?action=calcularOcupacao', document.forms[0], 'divOcupacaoResponsavel',false);
		}
	}

	function reativar() {
		var form = document.bemImovelForm;
		if (form.qtdRegResponsavelInativo.value > 0 && form.permReativar.value == "true") {
			if(confirm('Deseja reativar os órgãos responsáveis ocupantes desse bem imóvel?')) {
				document.bemImovelForm.action='bemImovel.do?action=reativarOrgaoOcupanteDoBemImovel';
				document.bemImovelForm.submit();
			} else {
				document.getElementById("somenteTerrenoS").checked = false;
				document.getElementById("somenteTerrenoN").checked = true;
			}
		}
	}

	function habilitaLabelReativar () {
		var form = document.bemImovelForm;
		
		if (document.getElementById("btReativar") != null) {
			document.getElementById("btReativar").style.display="none";
		}
		if (form.qtdRegResponsavelInativo.value > 0 && form.permReativar.value == "true" &&
				document.getElementById("somenteTerrenoS").checked) {
			if (document.getElementById("btReativar") != null) {
				document.getElementById("btReativar").style.display="block";
			}
		} 

	}
	
</script>



<c:choose> 
	<c:when test='${bemImovelForm.actionType == "incluir"}'>
		<c:set var="acao" value="bemImovel.do?action=salvarBemImovel"></c:set>
		<c:set var="titulo" value="Incluir"></c:set>
		<c:set var="disable" value="false"></c:set>
		
	</c:when>
	<c:otherwise>
		<c:set var="acao" value="bemImovel.do?action=alterarBemImovel"></c:set>
		<c:set var="titulo" value="Alterar"></c:set>
		<c:set var="disable" value="true"></c:set>
	</c:otherwise>
</c:choose>
<body onload="habilitarCampos();">
<div id="conteudo">
	<div align="center">
	<h1>${titulo} Bem Imóvel</h1>

	<html:form action="${acao}" onsubmit="return validarCampos()">
	<cep:main >
	<cep:form findOnType="false" codificacao="C" textoBusca="procurando...">
	
	<html:hidden property="actionType" value="${bemImovelForm.actionType}" />
	<html:hidden property="actionTypeOcupResp" value="${bemImovelForm.actionTypeOcupResp}" />
	<html:hidden property="qtdRegEdificacao" value="${bemImovelForm.qtdRegEdificacao}" />
	<html:hidden property="qtdRegResponsavelAtivo" value="${bemImovelForm.qtdRegResponsavelAtivo}" />
	<html:hidden property="qtdRegResponsavelInativo" value="${bemImovelForm.qtdRegResponsavelInativo}" />
	<html:hidden property="permReativar" value="${bemImovelForm.permReativar}" />
    <html:hidden property="codBemImovel" value="${bemImovelForm.codBemImovel}" />
    <html:hidden property="isGpAdmGeralUsuarioLogado" value="${bemImovelForm.isGpAdmGeralUsuarioLogado}" />
	
	<input type="hidden" name="<%= Constants.TOKEN_KEY %>" value="<%= session.getAttribute(Globals.TRANSACTION_TOKEN_KEY) %>" > 	
	<!-- campos da tela de pesquisa -->
	<html:hidden property="conCodBemImovel"/>
	<html:hidden property="conInstituicao"/>
	<html:hidden property="conNrBemImovel"/>
	<html:hidden property="conAdministracao"/>
	<html:hidden property="conOrgao"/>
	<html:hidden property="conOrgaoResp"/>
	<html:hidden property="conClassificacaoBemImovel"/>
	<html:hidden property="conNirf"/>
	<html:hidden property="conNiif"/>
	<html:hidden property="conIncra"/>
	<html:hidden property="conMunicipio"/>
	<html:hidden property="conLogradouro"/>
	<html:hidden property="conBairroDistrito"/>
	<html:hidden property="conSituacaoLegalCartorial"/>
	<html:hidden property="conCartorio"/>
	<html:hidden property="conNumeroDocumentoCartorial"/>
	<html:hidden property="conTabelionato"/>
	<html:hidden property="conNumeroDocumentoTabelional"/>
	<html:hidden property="conFormaIncorporacao"/>
	<html:hidden property="conAreaTerrenoIni"/>
	<html:hidden property="conAreaTerrenoFim"/>
	<html:hidden property="conTipoConstrucao"/>
	<html:hidden property="conTipoEdificacao"/>
	<html:hidden property="conTipoDocumentacao"/>
	<html:hidden property="conSituacaoOcupacao"/>
	<html:hidden property="conLote"/>
	<html:hidden property="conQuadra"/>
	<html:hidden property="conDenominacaoImovel"/>
	<html:hidden property="conAverbado"/>
	<html:hidden property="conSituacaoImovel"/>
	<html:hidden property="conCodUf"/>
	<html:hidden property="conCodMunicipio"/>
	<html:hidden property="conOcupante"/>
	 
     <div id="conteudo_corpo" style="padding: 4px">	
	 <table class="form_tabela" cellspacing="0" width="100%" style="">
		<tr>
			<td style="text-align: center;">
			<span class="form_label_infBI">Identificação do Imóvel CPE: </span>
			<c:out value="${bemImovelForm.nrBemImovel}"></c:out>
			</td>
		</tr>
		<tr>
			<td style="text-align: center;">
			<span class="form_label_infBI">Administração: </span>
			<c:out value="${bemImovelForm.descricaoAdministracao}"></c:out>
			</td>
		</tr>
	 </table>
   </div>

    <a name="abas"></a> 
	<table class="abas" cellspacing="0" id="abas">
      <tr> 
        <td class="aba_selecionada">Identificação</td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_documentacao}')">Documentação</a> </td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_leiBemImovel}')">Lei/Decreto</a></td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_quadraLote}')">Quadra/Lote</a></td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_confrontante}')">Confrontantes</a></td>
        <td class="aba_desselecionada">
        	<div id="abaEdificacao"> 
        		<a href="javascript:trocarAba('${link_aba_edificacao}')">Edificação</a>
        	</div>
        	<div id="abaEdificacaoDesabilitada" style="display:none">Edificação</div>
        </td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_avaliacao}')">Avaliação</a></td>
        <td class="aba_desselecionada" id="aba"><a href="javascript:trocarAba('${link_aba_coordenadaUtm}')" >Coordenada UTM </a></td>
		<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_anexo}')">Anexos</a></td>        
      </tr>
	</table>

    <div id="conteudo_abas">
	 <table class="form_tabela" cellspacing="0" >
		<c:if test="${bemImovelForm.isGpAdmGeralUsuarioLogado == 'S'}">
			<tr>
				<td class="form_label" align="right">* Instituição:</td>
				<td colspan="2">
					<c:choose> 
						<c:when test='${bemImovelForm.actionType == "incluir"}'>
							<html:select property="instituicao" onchange="javascript:habilitarOrgao();habilitarOrgaoResp();">
							 	<html:options collection="listaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
						    </html:select>
						</c:when>
						<c:otherwise>
							<html:hidden property="nrBemImovel" value="${bemImovelForm.instituicao}"/>
							<c:out value="${bemImovelForm.instituicaoDesc}"></c:out>
						</c:otherwise>
					</c:choose>
				</td>	
			</tr>
		</c:if>
		<c:if test="${bemImovelForm.isGpAdmGeralUsuarioLogado != 'S'}">
		    <html:hidden property="instituicao" value="${bemImovelForm.instituicao}" />
		</c:if>
 		<tr>
			<td class="form_label" width="280">* Identificação do Imóvel CPE: </td>
			<td>
				<c:choose> 
					<c:when test='${bemImovelForm.actionType == "incluir"}'>
						<html:text maxlength="15" size="15" property="nrBemImovel" onkeyup="javascript:DigitaNumero(this);" onchange="javascript:habilitarCampos()" />
					</c:when>
					<c:otherwise>
						<html:hidden property="nrBemImovel" value="${bemImovelForm.nrBemImovel}"/>
						<c:out value="${bemImovelForm.nrBemImovel}"></c:out>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	 	<tr>
	 		<td class="form_label">* Administração: </td>
	 		<td>
				<html:radio value="1" property="administracao" styleId ="administracaoDireta1" onchange="javascript:habilitarOrgao();habilitarOrgaoResp();habilitarAlterar();">Direta</html:radio>
				<html:radio value="2" property="administracao" styleId ="administracaoDireta2" onchange="javascript:habilitarOrgao();habilitarOrgaoResp();habilitarAlterar();">Indireta</html:radio>
				<html:radio value="3" property="administracao" styleId ="administracaoDireta3" onchange="javascript:habilitarOrgao();habilitarOrgaoResp();habilitarAlterar();">Terceiros</html:radio>
			</td>
		</tr>
		<tr>
		 	<td class="form_label">Órgão Proprietário: </td>
		 	<td>
				<div id="divComboOrgao">
					<jsp:include page="/pages/bemImovel/_edit_comboOrgao.jsp"/>
				</div>
			</td>
		</tr>
		<tr>
		 	<td class="form_label">* Classificação do Imóvel: </td>
		 	<td>
				<html:select property="classificacaoBemImovel" onchange="javascript:habilitarAlterar();" styleId="classificacaoBemImovel">
					<html:option value="">-selecione-</html:option>
					<html:options collection="classificacaoBemImovels" property="codClassificacaoBemImovel" labelProperty="descricao" />
				</html:select>
			</td>
		</tr>
				
	 	<tr>
	 		<td class="form_label">* Situação de Localização: </td>
	 		<td>
				<html:radio value="1" property="situacaoLocal"  styleId="situacaoLocal1" onchange="javascript:habilitarAlterar()">Localizado</html:radio>
				<html:radio value="2" property="situacaoLocal"  styleId="situacaoLocal2" onchange="javascript:habilitarAlterar()">Não Localizado</html:radio>
			</td>
		</tr>
		<tr>
		 	<td class="form_label">* Situação Legal - Cartorial: </td>
		 	<td>
				<html:select property="situacaoLegalCartorial" onchange="javascript:habilitarAlterar()" styleId="situacaoLegalCartorial">
					<html:option value="">-selecione-</html:option>
					<html:options collection="situacaoLegalCartorials" property="codSituacaoLegalCartorial" labelProperty="descricao"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="form_label">N° do Processo SPI: </td>
			<td>
				<html:text maxlength="15" size="15" property="numeroProcessoSpi" styleId="numeroProcessoSpi" onkeyup="javascript:DigitaNumero(this);" onchange="javascript:habilitarAlterar()"/>
			</td>
		</tr>

		<tr>
			<td class="form_label">CEP: </td>
			<td>
				<cep:cep name="cep" value="${bemImovelForm.cep}"  size="10" maxlength="8" onblur="javascript:habilitarAlterar();"/><cep:box/>
			</td>
		</tr>
		<tr>
			<td class="form_label">* Estado/Município: </td>
			<td>
				<cep:uf name="uf" value="${bemImovelForm.uf}" onchange="javascript:habilitarAlterar()"/> / <cep:municipio  name="codMunicipio" value="${bemImovelForm.codMunicipio}" onchange="javascript:habilitarAlterar();"/>
				<html:hidden name="municipio" property="municipio" value="${bemImovelForm.municipio}"/>	
			</td>
		</tr>
		<tr>
			<td class="form_label">Bairro/Distrito: </td>
			<td>
				<cep:bairro name="bairroDistrito" value="${bemImovelForm.bairroDistrito}" maxlength="60" size="60" onchange="javascript:habilitarAlterar()"/>
			</td>
		</tr>
		
		
		<tr>
			<td class="form_label">Forma de Incorporação: </td>
			<td colspan="2">
				<html:select property="formaIncorporacao" onchange="javascript:habilitarAlterar()" styleId="formaIncorporacao">
					<html:option value="">-selecione-</html:option>
					<html:options collection="formaIncorporacaos" property="codFormaIncorporacao" labelProperty="descricao" />						
				</html:select>
			</td>				
				
		</tr>
		<tr> 
          <td class="form_label">* Data da Incorporação: </td>
          <td colspan="6">
				<html:text styleId="dataIncorporacao" property="dataIncorporacao"  size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));" onblur="javascript:habilitarAlterar()" />
				<img src="${icon_calendar}" width="16" height="15" id="btnCalendar" onclick="javascript:Calendario(document.getElementById('dataIncorporacao'),this.id); javascript:habilitarAlterar()" />
			</td>
	   </tr>
	   <tr>
			<td class="form_label">Situação do Imóvel:</td>
			<td colspan="2">
				<html:select property="situacaoImovel" onchange="javascript:habilitarAlterar()" styleId="situacaoImovel">
					<html:option value="">-selecione-</html:option>
					<html:options collection="situacaoImovels" property="codSituacaoImovel" labelProperty="descricao" />						
				</html:select>
			</td>				
		</tr>

		<tr>
			<td class="form_label">Observações: </td>
			<td>
			<html:textarea styleId="observacoesMigracao" property="observacoesMigracao" cols="80" rows="4" onchange="javascript:habilitarAlterar()"></html:textarea></td>
		</tr>

		</table>
		
		<h2>Terreno</h2>
		<table class="form_tabela" cellspacing="0" >
		<tr>
			<td class="form_label" width="280">Logradouro: </td>
			<td colspan="3">
				<cep:endereco name="logradouro" value="${bemImovelForm.logradouro}" maxlength="100" size="60" onchange="javascript:habilitarAlterar()"/> </td>
			 <td>
			 	<a href="javascript:VerNoMapa()"><img src="${icon_lupa_planeta}" width="16" height="16" border="0" >Ver no mapa</a>
			</td>
		</tr>
		<tr>
			<td class="form_label">Número: </td>
			<td colspan="4">
				<cep:numero name="numero" value="${bemImovelForm.numero}" maxlength="15" size="15" onchange="javascript:habilitarAlterar()"/>
			</td>
		</tr>
		<tr>
			<td class="form_label">Complemento: </td>
			<td colspan="4">
				<cep:complemento name="complemento" value="${bemImovelForm.complemento}" maxlength="60" size="60" onchange="javascript:habilitarAlterar()"/>
			</td>
		</tr>
		<tr>
			<td class="form_label">Denominação:</td>
			<td colspan="4">
				<html:select property="denominacaoImovel" onchange="javascript:habilitarAlterar()" styleId="denominacaoImovel">
					<html:option value="">-selecione-</html:option>
					<html:options collection="denominacaoImovels" property="codDenominacaoImovel" labelProperty="descricao" />						
				</html:select> 
			</td>					
		</tr>
		<tr>
			<td class="form_label">* Área do Terreno (m²): </td>
			<td colspan="4">
				<div id="areaBemImovel">
					<jsp:include page="/pages/bemImovel/_edit_areaBemImovel.jsp" />
				</div>
			</td>
		</tr>
		<tr>
			<td colspan=5 align="center">
			<table class="form_tabela" cellspacing="0" width="100%">
				<tr>
					<td class="form_label" width="37%">Área Original:</td>
					<td class=""><html:text maxlength="19" size="20" property="areaOriginal" styleId="areaOriginal" onchange="javascript:habilitarAlterar();" /></td>
					<td class="form_label">&nbsp;&nbsp;&nbsp;Unidade Original:</td>
					<td ><html:select property="medidaOriginal" onchange="javascript:habilitarAlterar()" styleId="medidaOriginal">
						<html:option value="0">m²</html:option>
						<html:option value="1">Alqueire Paulista</html:option>
						<html:option value="2">Are</html:option>
						<html:option value="3">Hectare</html:option>
						<html:option value="4">Litro</html:option>
					</html:select>  </td>
					<td>
						<a href="javascript:TransformarM()"><img src="${icon_calculadora}" width="16" height="16" border="0" >Transformar m²</a>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	
		<c:if test='${bemImovelForm.actionType == "Alterar"}'>
			<tr>
				<td class="form_label">Construída (m²): </td>
				<td>
					<c:out value="${bemImovelForm.areaConstruida}"></c:out>
					<html:hidden property="areaConstruida" value="${bemImovelForm.areaConstruida}" />
				</td>
				<td class="form_label">Disponível (m²): </td>
				<td>
					<c:out value="${bemImovelForm.areaDispoNivel}"></c:out>
					<html:hidden property="areaDispoNivel" value="${bemImovelForm.areaDispoNivel}" />
				</td>
			</tr>
		</c:if>
			
	 	<tr>
	 		<td class="form_label">Somente terreno: </td>
	 		<td colspan="4">
				<html:radio value="S" property="somenteTerreno" styleId="somenteTerrenoS" onchange="javascript:habilitarResponsavel()">Sim</html:radio>
				<html:radio value="N" property="somenteTerreno" styleId="somenteTerrenoN" onchange="javascript:habilitarResponsavel()">Não</html:radio>
			</td>
		</tr>
	</table>
	<div id="divResponsavel" style="display:none">
		<html:hidden property="or_situacaoOcupacao" value="${bemImovelForm.or_situacaoOcupacao}" />
		<h2>Responsável pelo Terreno</h2>
       	<table class="form_tabela" cellspacing="0">
       		<tr>
	            <td class="form_label" width="280">* Descrição: </td>
	            <td colspan="3"><html:text maxlength="120" size="80" name="bemImovelForm" property="or_descricao" styleId="or_descricao"/></td>
       		</tr>
       	</table>
        
		<div id="divOcupacaoResponsavel">
			<jsp:include page="/pages/bemImovel/_edit_ocupacaoOrgaoResponsavel.jsp"/>
		</div>
		<table class="form_tabela" cellspacing="0">
			<tr>
				<td class="form_label" width="280">* Órgão Responsável:</td>
			 	<td>
					<div id="divComboOrgaoResp">
						<jsp:include page="/pages/bemImovel/_edit_comboOrgaoResp.jsp"/>
					</div>
				</td>
		 	    <td align="left"><a href="javascript:adicionarOcupacaoOrgaoResponsavel();"><img src="${icon_adicionar}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:adicionarOcupacaoOrgaoResponsavel()">Adicionar</a></td>
			</tr>
        </table>
	</div>
	<table class="form_tabela" cellspacing="0" width="100%">
		<tr>
			<td> 
				<c:if test="${listOcupOrgaoResponsavel != null && listOcupOrgaoResponsavel.quantidade > 0}">
					<h2 id="lblListaOrgaoOcupante">Lista de Órgãos Responsáveis pelo Terreno</h2>
					<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">	
						<ch:lista bean="${listOcupOrgaoResponsavel}"  atributoId="codOcupacao"/>
						<ch:campo atributo="orgao" label="<center>Órgão Responsável</center>" align="center"/>
						<ch:campo atributo="descricao" label="<center>Descrição</center>" align="center"/>
				        <ch:campo atributo="ocupacaoMetroQuadrado" label="Ocupação em m²" decorator="gov.pr.celepar.abi.util.Area" />
				        <ch:campo atributo="ocupacaoPercentual" label="Ocupação em %" decorator="gov.pr.celepar.abi.util.Area"/>
						<ch:campo atributo="instanciaAtual" label="<center>Excluir</center>" decorator="gov.pr.celepar.abi.util.ExcluirOrgaoOcupanteTerrenoDecorator" align="center" width="15" />
					</ch:table>
					<div id="btReativar" style="display:none" align="center" >
						<a href="javascript:reativar();"><img src="${icon_reativar}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:reativar()">Reativar Órgãos</a>
					</div>
				</c:if>
			</td>
		</tr>
    </table>

   	<hr>
  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
 	<div align="center">
		<!-- <html:button property="btnSalvar" styleClass="form_botao" value="${titulo}" onclick="validarCampos();" /> -->
		<html:submit property="btnSalvar" styleClass="form_botao" value="${titulo}" disabled="${disable}"/>
		<html:button property="btnVoltar" styleClass="form_botao" value="Voltar" onclick="voltar();"/>
	</div>	
	
  </div>
	
  </cep:form></cep:main>
  </html:form>

  </div>
</div>
</body>