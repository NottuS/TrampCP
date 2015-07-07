<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_data" value="/js/generic/data.js" />
<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var="js_windowopen" value="/js/generic/windowopen.js"/>

<c:url var='icon_exibir' value='/images/icon_exibir.png' />
<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='icon_baixar' value='/images/icon_desligar.png' />
<c:url var='icon_imprimir' value='/images/icon_pdf.png' />
<c:url var='icon_incluir_ocorrencia' value='/images/icon_anotacao2.png' />
<c:url var='icon_info' value='/images/icon_info.png' />
<c:url var="js_maisinfo" value="/js/generic/maisinfo.js" />
<c:url var='link_entrada' value='/entrada.do' />
<c:url var='icon_lupa_planeta' value='/images/icon_lupa_planeta.png' />

<c:url var='link_exibir_bemImovel' value='/bemImovel.do?action=carregarPgViewBemImovel' />
<c:url var='link_editar_bemImovel' value='/bemImovel.do?action=carregarPgEditBemImovel' />
<c:url var='link_excluir_bemImovel' value='/bemImovel.do?action=carregarPgViewBemImovel' />
<c:url var='link_baixar_bemImovel' value='/bemImovel.do?action=carregarPgViewBemImovel' />
<c:url var='link_imprimir_bemImovel' value='/impressaoBemImovel.do?action=carregarPgEditImpressaoBemImovel' />
<c:url var='link_incluir_ocorrencia_bemImovel' value='/ocorrenciaDocumentacao.do?action=carregarPgEditOcorrenciaDocumentacao' />

<c:url var="link_pesquisar"	 value="/bemImovel.do?action=pesquisarBemImovel" /> 
<c:url var="var_paginacao"	 value="&indice=%pagina&totalRegistros=%total" /> 
<c:url var="func_navegacao" value="JavaScript:pesquisarPaginado('${link_pesquisar}${var_paginacao}');" />
 

<script language="JavaScript" type="text/JavaScript" src="${js_maisinfo}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_windowopen}"> </script>

<script language="JavaScript">

	function capturarDesMunicipio(item){
		document.forms[0].conMunicipio.value = item.options[item.selectedIndex].text;
	}

	function incluir() {
		document.bemImovelForm.action="${link_editar_bemImovel}&actionType=incluir";
		document.bemImovelForm.submit();
	}
	
	function cancelar() {	
		document.bemImovelForm.action="${link_entrada}";
		document.bemImovelForm.submit();
	}

	function trocarAba(abaDestino) {
		alert("aba");
	}


	function VerNoMapa(endereco){
		if (endereco !=''){
			var url="http://maps.google.com/maps?f=q&source=s_q&hl=en&geocode=&q="+endereco+"&sll=37.0625,-95.677068&sspn=0.00863,0.015814&ie=ISO88591&hq=&hnear="+endereco+"&z=16";
			window_open(url,"mapa","");
		}
	}
	
	function window_open(theURL,winName,features) {
		  winName = window.open(theURL,winName,features);
		  winName.window.focus();
	}
	

	function alterar(codigo){
		document.bemImovelForm.action="${link_editar_bemImovel}&actionType=alterar&codBemImovel="+codigo;
		document.bemImovelForm.submit();
	}

	function exibir(codigo){
		document.bemImovelForm.action="${link_exibir_bemImovel}&actionType=exibir&codBemImovel="+codigo;
		document.bemImovelForm.submit();
	}

	function excluir(codigo){
		document.bemImovelForm.action="${link_exibir_bemImovel}&actionType=excluir&codBemImovel="+codigo;
		document.bemImovelForm.submit();
	}

	function incluirOcorrencia(codigo){
		document.bemImovelForm.action="${link_incluir_ocorrencia_bemImovel}&actionType=incluirOcorrencia&codBemImovel="+codigo;
		document.bemImovelForm.submit();
	}

	

	function baixar(codigo){
		document.bemImovelForm.action="${link_baixar_bemImovel}&actionType=baixar&codBemImovel="+codigo;
		document.bemImovelForm.submit();
	}
	
	function pesquisarPaginado(acao){
		document.bemImovelForm.conCodUf.value=document.bemImovelForm.uf.value;
		document.bemImovelForm.conCodMunicipio.value=document.bemImovelForm.codMunicipio.value;
		document.bemImovelForm.action=acao;
		document.bemImovelForm.submit();
	}

	function pesquisar(){
		if (document.forms[0].isGpAdmGeralUsuarioLogado.value == 'S'){
			if (form.conInstituicao.value == '' || form.conInstituicao.value == '0'){
				alert("Campo 'Instituição' é obrigatório!");
				return false;
			}
		}
		document.bemImovelForm.conCodUf.value=document.bemImovelForm.uf.value;
		document.bemImovelForm.conCodMunicipio.value=document.bemImovelForm.codMunicipio.value;
		document.bemImovelForm.action="bemImovel.do?action=pesquisarBemImovel";
		document.bemImovelForm.submit();
	}

	function MascaraArea(campo) {
		var area = campo.value.replace(/[^0-9]+/g, '').replace(/([0-9]+)/, '00$1').replace(/^0+([0-9]{3,})$/, '$1').replace(/^([0-9]+)([0-9]{2})$/, '$1,$2');
		while(/^[0-9]{4}/.test(area)) {
			area = area.replace(/^([0-9]+)([0-9]{3}[.,])/, '$1.$2');
		}
		campo.value = area;
	}

	function habilitarOrgao(){
		var form = document.bemImovelForm;
		if (form.conInstituicao.value != '' && form.conInstituicao.value != '0') {
			if(document.getElementById("administracaoDireta2").checked || document.getElementById("administracaoDireta3").checked){
				submitAjax('/abi/bemImovel.do?action=carregarComboOrgaoPesq', document.forms[0], 'divComboOrgao',false);
				form.conOrgao.disabled=false;
			}else{
				form.conOrgao.value="";
				form.conOrgao.disabled=true;
			}
		}else{
			form.conOrgao.value="";
			form.conOrgao.disabled=true;
		}
	}	

	function habilitarOrgaoResp(){
		var form = document.bemImovelForm;
		if (form.conInstituicao.value != '' && form.conInstituicao.value != '0') {
			if(document.getElementById("administracaoDireta2").checked || document.getElementById("administracaoDireta3").checked){
				submitAjax('/abi/bemImovel.do?action=carregarComboOrgaoRespPesq', document.forms[0], 'divComboOrgaoResp',false);
				form.conOrgaoResp.disabled=false;
			}else{
				form.conOrgaoResp.value="";
				form.conOrgaoResp.disabled=true;
			}
		}else{
			form.conOrgaoResp.value="";
			form.conOrgaoResp.disabled=true;
		}
	}	

	function habilitarCampos() {
		var form = document.bemImovelForm;			

		if(form.conNrBemImovel.value == "") {
			form.administracaoDireta1.disabled=false;
			form.administracaoDireta2.disabled=false;
			form.administracaoDireta3.disabled=false;
			form.administracaoDiretaA.disabled=false;
			form.conOrgao.disabled=false;
			form.conOrgaoResp.disabled=false;
			form.conClassificacaoBemImovel.disabled=false;
			form.nirf.disabled=false;
			form.niif.disabled=false;
			form.incra.disabled=false;
			form.uf.disabled=false;
			form.conMunicipio.disabled=false;
			form.codMunicipio.disabled=false;			
			form.conLogradouro.disabled=false;			
			form.conBairroDistrito.disabled=false;
			form.conSituacaoLegalCartorial.disabled=false;
			form.conCartorio.disabled=false;
			form.conNumeroDocumentoCartorial.disabled=false;
			form.conTabelionato.disabled=false;
			form.conNumeroDocumentoTabelional.disabled=false;
			form.conFormaIncorporacao.disabled=false;
			form.conAreaTerrenoIni.disabled=false;
			form.conAreaTerrenoFim.disabled=false;
			form.conTipoConstrucao.disabled=false;
			form.conTipoEdificacao.disabled=false;			
			form.conTipoDocumentacao.disabled=false;
			form.conSituacaoOcupacao.disabled=false;
			form.conLote.disabled=false;
			form.conQuadra.disabled=false;
			form.conDenominacaoImovel.disabled=false;
			form.averbadoT.disabled=false;
			form.averbadoF.disabled=false;
			form.averbadoA.disabled=false;
			form.conSituacaoImovel.disabled=false;
			form.conOcupacaoT.disabled=false;
			form.conOcupacaoE.disabled=false;
			form.conOcupacaoA.disabled=false;

		}
		else
		{
			form.administracaoDireta1.disabled=true;
			form.administracaoDireta2.disabled=true;
			form.administracaoDireta3.disabled=true;
			form.administracaoDiretaA.disabled=true;
			form.conOrgao.disabled=true;
			form.conOrgaoResp.disabled=true;
			form.conClassificacaoBemImovel.disabled=true;
			form.nirf.disabled=true;
			form.niif.disabled=true;
			form.incra.disabled=true;
			form.uf.disabled=true;
			form.conMunicipio.disabled=true;
			form.codMunicipio.disabled=true;			
			form.conLogradouro.disabled=true;			
			form.conBairroDistrito.disabled=true;
			form.conSituacaoLegalCartorial.disabled=true;
			form.conCartorio.disabled=true;
			form.conNumeroDocumentoCartorial.disabled=true;
			form.conTabelionato.disabled=true;
			form.conNumeroDocumentoTabelional.disabled=true;
			form.conFormaIncorporacao.disabled=true;
			form.conAreaTerrenoIni.disabled=true;
			form.conAreaTerrenoFim.disabled=true;
			form.conTipoConstrucao.disabled=true;
			form.conTipoEdificacao.disabled=true;			
			form.conTipoDocumentacao.disabled=true;
			form.conSituacaoOcupacao.disabled=true;
			form.conLote.disabled=true;
			form.conQuadra.disabled=true;			
			form.conDenominacaoImovel.disabled=true;
			form.averbadoT.disabled=true;
			form.averbadoF.disabled=true;
			form.averbadoA.disabled=true;
			form.conSituacaoImovel.disabled=true;
			form.conOcupacaoT.disabled=true;
			form.conOcupacaoE.disabled=true;
			form.conOcupacaoA.disabled=true;
			
		}		
		
	}

	function carregarPagina(){
		habilitarCampos();		
		carregarCamposPesquisaPadrao();
	}

	function carregarCamposPesquisaPadrao() {

		var form = document.bemImovelForm;			
		if (form.uf.value=='0'){
			form.uf.value='PR';
		}
	}

	function mostraOrgao(valor) {
		
		document.getElementById("orgao1").style.display=valor;
		document.getElementById("orgao2").style.display=valor;
				
	}
	
	function limparCampos(){

		var form = document.bemImovelForm;			
			form.conNrBemImovel.value="";
			form.conInstituicao.value="0";
			habilitarCampos();
			
			form.administracaoDireta1.checked=false;
			form.administracaoDireta2.checked=false;
			form.administracaoDireta3.checked=false;
			
			form.administracaoDiretaA.checked = true;
			form.conOrgao.value = "";
			form.conOrgaoResp.value = "";
			form.conClassificacaoBemImovel.value="";
			form.nirf.value="";
			form.niif.value="";
			form.incra.value="";
			form.uf.value="PR";
			form.conMunicipio.value="";
			form.codMunicipio.value="0";			
			form.conLogradouro.value="";			
			form.conBairroDistrito.value="";
			form.conSituacaoLegalCartorial.value="";
			form.conCartorio.value="";
			form.conNumeroDocumentoCartorial.value="";
			form.conTabelionato.value="";
			form.conNumeroDocumentoTabelional.value="";
			form.conFormaIncorporacao.value="";
			form.conAreaTerrenoIni.value="";
			form.conAreaTerrenoFim.value="";
			form.conTipoConstrucao.value="";
			form.conTipoEdificacao.value="";			
			form.conTipoDocumentacao.value="";
			form.conSituacaoOcupacao.value="";
			form.conLote.value="";
			form.conQuadra.value="";
			form.conDenominacaoImovel.value="";
			form.averbadoT.value="";
			form.averbadoF.value="";
			form.averbadoA.value="";
			form.conSituacaoImovel.value="";	
			form.conOcupacaoT.value="";
			form.conOcupacaoE.value="";
			form.conOcupacaoA.value="";
		
	}	

	
	window.onload = function(){
		carregarPagina();
		habilitarOrgao();
		habilitarOrgaoResp();
	};

</script>


<div id="conteudo">
	<div align="center">
	
	<h1>Pesquisar Bem Imóvel</h1>
	
    <div id="conteudo_corpo">
	    
    <html:form action="bemImovel.do?action=carregarPgListBemImovel">
	    <html:hidden property="actionType" value="${bemImovelForm.actionType}" />
	    <html:hidden property="isGpAdmGeralUsuarioLogado" value="${bemImovelForm.isGpAdmGeralUsuarioLogado}" />
	    <html:hidden property="indOperadorOrgao" />
	<cep:main>
	<cep:form findOnType="false" codificacao="C" textoBusca="procurando...">

		<div style="display:none" align="right">
			<cep:cep name="cep" value="${bemImovelForm.cep}" readonly="true"/>
			<cep:endereco name="endereco" value="${fornecedorForm.endereco}" maxlength="72" size="46"/>
			<cep:bairro name="bairro" value="${fornecedorForm.bairro}" maxlength="72" size="25"/>
		</div>
					
		<table class="form_tabela" cellspacing="0">
			<c:if test="${bemImovelForm.isGpAdmGeralUsuarioLogado == 'S'}">
				<tr>
					<td class="form_label" align="right">* Instituição:</td>
					<td colspan="2">
						<html:select property="conInstituicao" onchange="javascript:habilitarOrgao();habilitarOrgaoResp();">
						 	<html:options collection="listaPesquisaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
					    </html:select>
					</td>	
				</tr>
			</c:if>
			<c:if test="${bemImovelForm.isGpAdmGeralUsuarioLogado != 'S'}">
			    <html:hidden property="conInstituicao" value="${bemImovelForm.conInstituicao}" />
			</c:if>
			<tr>
				<td class="form_label">Identificação do Imóvel CPE: </td>
				<td>
					<html:text maxlength="15" size="15" property="conNrBemImovel" onkeyup="javascript:DigitaNumero(this);" onchange="javascript:habilitarCampos()" />					
					<html:hidden property="conCodBemImovel"/>
				</td>
			</tr>

			<tr>
				<td class="form_label">Administração: </td>
				<td colspan="2">				
					<html:radio value="1" property="conAdministracao" styleId ="administracaoDireta1" onchange="javascript:habilitarOrgao();habilitarOrgaoResp();">Direta</html:radio>
					<html:radio value="2" property="conAdministracao" styleId ="administracaoDireta2" onchange="javascript:habilitarOrgao();habilitarOrgaoResp();">Indireta</html:radio>
					<html:radio value="3" property="conAdministracao" styleId ="administracaoDireta3" onchange="javascript:habilitarOrgao();habilitarOrgaoResp();">Terceiros</html:radio>
					<html:radio value="" property="conAdministracao" styleId ="administracaoDiretaA" onchange="javascript:habilitarOrgao();habilitarOrgaoResp();">Todos</html:radio>
				</td>
			</tr>

			<tr>
				<td class="form_label">Órgão Proprietário:</td>
				<td colspan="2">
					<div id="divComboOrgao">
						<jsp:include page="/pages/bemImovel/_list_comboOrgao.jsp"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="form_label">Órgão Responsável pelo Imóvel (ocupação e terreno):</td>
				<td colspan="2">
					<div id="divComboOrgaoResp">
						<jsp:include page="/pages/bemImovel/_list_comboOrgaoResponsavel.jsp"/>
					</div>
				</td>
			</tr>

			<tr>
				<td class="form_label">Classificação do imóvel:</td>
				<td colspan="2">
					<html:select property="conClassificacaoBemImovel">
						<html:option value="">-Qualquer-</html:option>
						<html:options collection="classificacaoBemImovels" property="codClassificacaoBemImovel" labelProperty="descricao" />						
					</html:select>
				</td>				
			</tr>
			<tr>
			 	<td class="form_label">NIRF - Número Inscrição Imóvel Rural na S.R.F.: </td>
			 	<td>
					<html:text maxlength="255" size="60" property="conNirf" styleId="nirf"  />
				</td>
			</tr>
			<tr>
			 	<td class="form_label">INCRA - Cadastro de Imóvel Rural: </td>
			 	<td>
					<html:text maxlength="255" size="60" property="conIncra"  styleId="incra"  />
				</td>
			</tr>
			<tr>
			 	<td class="form_label">Número Inscrição Imobiliária/Indicação Fiscal: </td>
			 	<td>
					<html:text maxlength="255" size="60" property="conNiif" styleId="niif"  />
				</td>
			</tr>
			<tr>
				<td class="form_label" align="right">Estado:</td>
				<td><cep:uf name="uf" value="${bemImovelForm.uf}" /><cep:box/>
				<html:hidden property="conCodUf"/>   
				</td>
				
			</tr>
			<tr>
				<td class="form_label"  align="right">Município:</td>
				<td>
					<cep:municipio name="codMunicipio" value="${bemImovelForm.conCodMunicipio}" onchange="capturarDesMunicipio(this)"/>
					<html:hidden name="conMunicipio" property="conMunicipio" value="${bemImovelForm.conMunicipio}"/>
					<html:hidden property="conCodMunicipio"/> 					
				</td>
			</tr>

			<tr>
				<td class="form_label">Logradouro:</td>
				<td><html:text maxlength="100" size="100" property="conLogradouro"  /></td>
			</tr>

			<tr>
				<td class="form_label">Bairro/Distrito:</td>
				<td><html:text maxlength="60" size="60" property="conBairroDistrito" /></td>
			</tr>

			<tr>
				<td class="form_label">Situação Legal - Cartorial:</td>
				<td colspan="2">
					<html:select property="conSituacaoLegalCartorial">
						<html:option value="">-Qualquer-</html:option>
						<html:options collection="situacaoLegalCartorials" property="codSituacaoLegalCartorial" labelProperty="descricao" />						
					</html:select>
				</td>				
				
			</tr>

			<tr>
				<td class="form_label">Nome do Cartório de Registro de Imóveis: </td>
				<td colspan="2">
					<html:select property="conCartorio">
						<html:option value="">-Qualquer-</html:option>
						<c:forEach var="item" items="${cartorios}" >
							<html:option value="${item.codCartorio}">${item.descricao} (${item.municipio}-${item.uf})</html:option>
						</c:forEach>
												
					</html:select>
				</td>				
			</tr>
			

			<tr>
				<td class="form_label">Nº do Documento Cartorial: </td>
				<td>
					<html:text maxlength="100" size="60" property="conNumeroDocumentoCartorial"  />
				</td>																							
			</tr>

			<tr>
				<td class="form_label">Nome do Tabelionato: </td>
				<td colspan="2">
					<html:select property="conTabelionato">
						<html:option value="">-Qualquer-</html:option>
						<c:forEach var="item" items="${tabelionatos}" >
							<html:option value="${item.codTabelionato}">${item.descricao} (${item.municipio}-${item.uf})</html:option>
						</c:forEach>
												
					</html:select>
				</td>				
			</tr>


			<tr>
				<td class="form_label">Nº do Documento Tabelional: </td>
				<td>
					<html:text maxlength="100" size="60" property="conNumeroDocumentoTabelional"  />
				</td>																							
			</tr>

			

			<tr>
				<td class="form_label">Forma de Incorporação: </td>
				<td colspan="2">
					<html:select property="conFormaIncorporacao">
						<html:option value="">-Qualquer-</html:option>
						<html:options collection="formaIncorporacaos" property="codFormaIncorporacao" labelProperty="descricao" />						
					</html:select>
				</td>				
				
			</tr>

			<tr>
				<td class="form_label">Área do Terreno (m²):</td>
				<td>
					Valor mínimo:<html:text maxlength="19" size="19" property="conAreaTerrenoIni" onkeyup="javascript:MascaraArea(this)" />
					Valor máximo:<html:text maxlength="19" size="19" property="conAreaTerrenoFim" onkeyup="javascript:MascaraArea(this)" />
				</td>				
			</tr>


			<tr>
				<td class="form_label">Tipo de Construção: </td>
				<td colspan="2">
					<html:select property="conTipoConstrucao">
						<html:option value="">-Qualquer-</html:option>
						<html:options collection="tipoConstrucaos" property="codTipoConstrucao" labelProperty="descricao" />						
					</html:select>
				</td>				
			</tr>

 			
			<tr>
				<td class="form_label">Tipo de Edificação:</td>
				<td colspan="2">
					<html:select property="conTipoEdificacao">
						<html:option value="">-Qualquer-</html:option>
						<html:options collection="tipoEdificacaos" property="codTipoEdificacao" labelProperty="descricao" />						
					</html:select>
				</td>
			</tr>
			
			<tr>
				<td class="form_label">Tipo de Documentação:</td>
				<td colspan="2">
					<html:select property="conTipoDocumentacao">
						<html:option value="">-Qualquer-</html:option>
						<html:options collection="tipoDocumentacaos" property="codTipoDocumentacao" labelProperty="descricao" />						
					</html:select>
				</td>
			</tr>


			<tr>
				<td class="form_label">Situação de Ocupação:</td>
				<td colspan="2">
					<html:select property="conSituacaoOcupacao">
						<html:option value="">-Qualquer-</html:option>
						<html:options collection="situacaoOcupacaos" property="codSituacaoOcupacao" labelProperty="descricao" />						
					</html:select>
				</td>
			</tr>

			<tr>
				<td class="form_label">Lote:</td>
				<td><html:text maxlength="5" size="5" property="conLote"  /></td>
			</tr>
			<tr>		
				<td class="form_label">Quadra:</td>
				<td><html:text maxlength="5" size="5" property="conQuadra"  /></td>
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
				<td class="form_label">Averbado:</td>
				<td colspan="2">
					<html:radio value="TRUE" property="conAverbado" styleId ="averbadoT" >Sim</html:radio>
					<html:radio value="FALSE" property="conAverbado" styleId ="averbadoF" >Não</html:radio>
					<html:radio value="" property="conAverbado" styleId ="averbadoA" >Ambos</html:radio>
				</td>
			</tr>



			<tr>
				<td class="form_label">Situação do Imóvel:</td>
				<td colspan="2">
					<html:select property="conSituacaoImovel">
						<html:option value="">-Qualquer-</html:option>
						<html:options collection="situacaoImovels" property="codSituacaoImovel" labelProperty="descricao" />						
					</html:select>
				</td>				
			</tr>
			<!-- tr>
				<td class="form_label">Ocupante:</td>
				<td><html:text maxlength="120" size="100" property="conOcupante" styleId="conOcupante" /></td>
			</tr-->


			<tr>
				<td class="form_label">Ocupação:</td>
				<td colspan="2">
					<html:radio value="T" property="conOcupacao" styleId ="conOcupacaoT" >Terreno</html:radio>
					<html:radio value="E" property="conOcupacao" styleId ="conOcupacaoE" >Edificação</html:radio>
					<html:radio value="" property="conOcupacao" styleId ="conOcupacaoA" >Ambos</html:radio>
				</td>
			</tr>

		</table>
				
	   	<hr>
		<c:if test="${bemImovelForm.isGpAdmGeralUsuarioLogado == 'S'}">
		  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
		</c:if>    
  	
		<div align="center">
			<html:button property="" styleClass="form_botao" styleId="btnPesquisar" value="Pesquisar" onclick="pesquisar()"/>	
			<html:button styleClass="form_botao" value="Limpar" property="" onclick="javascript:limparCampos();"/>			
			<c:if test="${I}">
				<c:if test="${!bemImovelForm.indOperadorOrgao || bemImovelForm.isGpAdmGeralUsuarioLogado == 'S'}">
					<html:button styleClass="form_botao" value="Incluir" property="" onclick="javascript:incluir();"/>
				</c:if>
			</c:if>
			<html:button styleClass="form_botao" value="Voltar" property="" onclick="javascript:cancelar();"/>
		</div>  
	
  </cep:form>
    </cep:main>
    </html:form>
    </div>

  <c:if test="${!empty requestScope.pagina}">
  <div id="conteudo_corpo">
 
   
  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
	    <ch:lista bean="${pagina}" atributoId="codBemImovel, enderecoMapa" />
        <ch:action imagem="${icon_exibir}" link="javascript:exibir(%1)" label="Exibir" width="3%" align="center" />
        <ch:action imagem="${icon_lupa_planeta}" link="javascript:VerNoMapa(%2)" label="Mapa" width="3%" align="center" />
        <ch:campo atributo="nrBemImovel" label="Pasta" width="4%" />
        <ch:campo atributo="classificacaoBemImovel.descricao" label="Classificação" width="6%"/>
        <ch:campo atributo="municipioUF" label="Município/UF<br>Logradouro" width="17%"/>
        <ch:campo atributo="descricaoOcupacaos" label="Ocupação" width="15%"/>
        <ch:campo atributo="areaTerreno" label="Área do Terreno(m2)" decorator="gov.pr.celepar.abi.util.Area" width="5%"/>
      	<ch:campo atributo="numDoctoCartorial" label="Nº Docto. Cartorial" width="6%"/>
        <ch:campo atributo="observacoesMigracao" label="Observações" width="25%" />
        <ch:campo atributo="descricaoAdministracao" label="Administração" width="7%" />
        <ch:campo atributo="orgao.sigla" label="Órgão" width="15%" />
		<ch:campo atributo="instanciaAtual" label="<center>Terreno</center>" decorator="gov.pr.celepar.abi.util.OrgaoOcupanteTerrenoDecorator" align="center" width="15" />
		<c:if test="${!bemImovelForm.indOperadorOrgao}">
			<ch:campo atributo="instanciaAtual" label="<center>Edificação</center>" decorator="gov.pr.celepar.abi.util.OrgaoOcupanteEdificacaoDecorator" align="center" width="15" />
        	<c:if test="${A}"><ch:action imagem="${icon_alterar}"  link="javascript:alterar(%1)" label="Alterar" width="3%" align="center" /></c:if>
        	<c:if test="${E}"><ch:action imagem="${icon_excluir}" link="javascript:excluir(%1)" label="Excluir" width="3%" align="center" /></c:if>
        </c:if>
     	<ch:campo atributo="codBemImovel" label="Imprimir" decorator="gov.pr.celepar.abi.util.Impressao" align="center" width="3%"/>
   		<ch:painel pagina="${func_navegacao}" classe="painel" atributoIndice="indice" />
	</ch:table>
  </div>
  </c:if>

  </div>
</div>
