<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_data" value="/js/generic/data.js" />

<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='icon_calendar' value='/images/icon_calendario.png' />
<c:url var='icon_apagar' value='/images/icon_apagar.png' />
<c:url var='icon_adicionar' value='/images/icon_adicionar1.png' />
<c:url var='icon_salvar' value='/images/icon_salvar.png' />
<c:url var='icon_lupa_planeta' value='/images/icon_lupa_planeta.png' />
<c:url var='icon_ocupacao_imovel' value='/images/icon_ocupacao_imovel.png' />

<c:url var='parametroCodBemImovel' value='&codBemImovel=${edificacaoForm.codBemImovel}&administracao=${edificacaoForm.administracao}' />
<c:url var='link_aba_identificacao' value='/bemImovel.do?action=carregarPgEditBemImovel&actionType=alterar${parametroCodBemImovel}' />
<c:url var='link_aba_lei' value='/leiBemImovel.do?action=carregarPgEditLeiBemImovel${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_quadraLote' value='/quadraLote.do?action=carregarPgEditQuadraLote${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_confrontante' value='/confrontante.do?action=carregarPgEditConfrontante${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_avaliacao' value='/avaliacao.do?action=carregarPgEditAvaliacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_coordenadaUtm' value='/coordenadaUtm.do?action=carregarPgEditCoordenadaUtm${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_edificacao' value='/edificacao.do?action=carregarPgEditEdificacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_documentacao' value='/documentacao.do?action=carregarPgEditDocumentacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_anexo' value='/anexoBemImovel.do?action=carregarPgEditAnexo${parametroCodBemImovel}&actionType=incluir' />

<c:url var='link_editar_edificacao' value='/edificacao.do?action=carregarPgEditEdificacao${parametroCodBemImovel}' />
<c:url var='link_editar_ocupacao' value='/ocupacao.do?action=carregarPgEditOcupacao${parametroCodBemImovel}' />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>

<script language="javascript" >

	
	function validarCampos(){
	
		var form = document.edificacaoForm;
	
		
		if (form.tipoConstrucao.value == ""){
			alert("Campo 'Tipo de Construção' é obrigatório!");
			form.tipoConstrucao.focus();
			return false;
		}
		if (form.tipoEdificacao.value == ""){
			alert("Campo 'Tipo de Edificação' é obrigatório!");
			form.tipoEdificacao.focus();
			return false;
		}
		Trim(form.especificacao);
		Trim(form.logradouro);
		if (form.logradouro.value == ""){
			alert("Campo 'Logradouro/N.°' é obrigatório!");
			form.logradouro.focus();
			return false;
		}
		Trim(form.areaConstruida);
		if (form.areaConstruida.value == ""){
			alert("Campo 'Área construída (m²)' é obrigatório!");
			form.areaConstruida.focus();
			return false;
		}
		Trim(form.areaUtilizada);
		if (form.areaUtilizada.value == ""){
			alert("Campo 'Área utilizada (m²)' é obrigatório!");
			form.areaUtilizada.focus();
			return false;
		}
		Trim(form.dataAverbacao);
	
		form.submit();
	}

	function VerNoMapa(){
		var form = document.edificacaoForm;
		if (form.logradouro.value == "")
		{
			alert("Campo 'Logradouro' é obrigatório!");
			form.logradouro.focus();
		}
		else
		{
			var endereco = form.logradouro.value;
			endereco=endereco+", "+form.municipio.value;
			endereco=endereco+", "+form.uf.value+", brasil";
			endereco=endereco.replace(/[ ]/g,"+");
			var url="http://maps.google.com/maps?f=q&source=s_q&hl=en&geocode=&q="+endereco+"&sll=37.0625,-95.677068&sspn=0.00863,0.015814&ie=UTF8&hq=&hnear="+endereco+"&z=16";
			window_open(url,"mapa","");
		}
		
	}

	function window_open(theURL,winName,features) {
		  winName = window.open(theURL,winName,features);
		  winName.window.focus();
	}

	function trocarAba(aba){
		
		var form = document.edificacaoForm;
		document.edificacaoForm.action=aba;
		form.submit();
	}


	function incluir() {
		
		document.edificacaoForm.action="edificacao.do?action=salvarEdificacao&actionType=incluir";
		if (validarCampos()){
			document.edificacaoForm.submit();
		}
	}


	function incluirLote() {
		var form = document.edificacaoForm;
		form.action="edificacao.do?action=incluirLote";
		
		if (form.lote.value ==""){
			alert("Campo 'Lote' é obrigatório!");
			form.lote.focus();
		}
		else
		{
			document.edificacaoForm.submit();
		}
	}

	
	function ocupacao(codigo) {
		document.edificacaoForm.action="edificacao.do?action=carregarPgEditEdificacao${parametroCodBemImovel}&actionType=incluirOcupacao&codEdificacao="+codigo;
		document.edificacaoForm.submit();
	}

	function editar(codigo) {
		document.edificacaoForm.action="${link_editar_edificacao}&actionType=alterar&codEdificacao="+codigo;
		document.edificacaoForm.submit();
	}

	

	function excluirLote(codigo) {
		var form = document.edificacaoForm;
		form.action="edificacao.do?action=excluirLote&codLote="+codigo;
		document.edificacaoForm.submit();
	}

	

	function alterar() {
		document.edificacaoForm.action="edificacao.do?action=alterarEdificacao";
		
		if (validarCampos()){
			document.edificacaoForm.submit();
		}
	}


	function ocupacao(codigo) {
		document.edificacaoForm.action="${link_editar_ocupacao}&actionType=incluir&codEdificacao="+codigo;
		document.edificacaoForm.submit();
		
	}

	

	function excluir(codigo){
		
		if(confirm('Tem certeza que deseja excluir?')) {
			document.edificacaoForm.action="edificacao.do?action=excluirEdificacao&codEdificacao="+codigo+"&actionType=excluir";
			document.edificacaoForm.submit();
		}
		
	}

	function limpar()
	{	
		document.edificacaoForm.action='${link_editar_edificacao}&actionType=incluir';
		document.edificacaoForm.submit();	
	}

	
</script>




    
<div id="conteudo"  >
	<div align="center">
	<h1>Alterar Bem Imóvel - Edificação</h1>

	
	<html:form action="edificacao.do?action=salvarEdificacao">
	<html:hidden property="codBemImovel" value="${edificacaoForm.codBemImovel}"/>
	<html:hidden property="actionType" value="${edificacaoForm.actionType}"/>
	<html:hidden property="municipio" value="${edificacaoForm.municipio}"/>
	<html:hidden property="uf" value="${edificacaoForm.uf}"/>
		
		<!-- campos da tela de pesquisa -->
	<html:hidden property="conCodBemImovel"/>
	<html:hidden property="conNrBemImovel"/>
	<html:hidden property="conInstituicao"/>
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
	 <table class="form_tabela" cellspacing="0" width="100%" style="" >
	 	<tr>
			<td style="text-align: center;">
				<span class="form_label_infBI">Identificação do Imóvel CPE: </span>
				<c:out value="${edificacaoForm.nrBemImovel}"></c:out>
			</td>
		</tr>
		<tr>
			<td style="text-align: center;">
				<span class="form_label_infBI" >Administração: </span>
				<c:out value="${edificacaoForm.descricaoAdministracao}"></c:out>
			</td>
		</tr>
	 </table>
   </div>

    <a name="abas"></a> 
	<table class="abas" cellspacing="0">
      <tr> 
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_identificacao}')">Identificação</a></td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_documentacao}')">Documentação</a></td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_lei}')">Lei/Decreto</a></td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_quadraLote}')">Quadra/Lote</a></td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_confrontante}')">Confrontantes</a></td>
        <td class="aba_selecionada">Edificação</td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_avaliacao}')">Avaliação</a></td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_coordenadaUtm}')">Coordenada UTM </a></td>
		<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_anexo}')">Anexos</a></td>        
      </tr>
	</table>
     
    <div id="conteudo_abas">
		<table class="form_tabela"  width="80%" cellspacing="0"  >
	        <tr > 
	          <td class="form_label" width="20%">* Tipo de Edifica&ccedil;&atilde;o: </td>
	          <td width="80%" >
				<html:select property="tipoEdificacao"  styleId="tipoEdificacao">
					<html:option value="">-selecione-</html:option>
					<html:options collection="tipoEdificacaos" property="codTipoEdificacao" labelProperty="descricao" />						
				</html:select>
			</td>
			</tr>
			<tr> 
	          <td class="form_label">* Tipo de Construção: </td>
	          <td >
					<html:select property="tipoConstrucao"  styleId="tipoConstrucao">
						<html:option value="" >-selecione-</html:option>
						<html:options collection="tipoConstrucaos" property="codTipoConstrucao" labelProperty="descricao" />						
					</html:select>
				</td>
	        </tr>
	       
	        <tr> 
	          <td class="form_label">Especificar: </td>
	          <td >
					<html:textarea styleId="especificacao" property="especificacao" cols="80" rows="3" ></html:textarea>
			 </td>
	        </tr>
	        <tr> 
	          <td class="form_label">* Logradouro / N.&deg;: </td>
	          <td ><html:text maxlength="100" size="80" property="logradouro" styleId="logradouro"  />
	            <a href="javascript:VerNoMapa()"><img src="${icon_lupa_planeta}"  width="16" height="16" border="0">&nbsp;Ver no Mapa</a></td>
	        </tr>
	        <tr> 
	          <td class="form_label">* &Aacute;rea constru&iacute;da (m²): </td>
	          <td ><html:text maxlength="19" size="20" property="areaConstruida" styleId="areaConstruida" onkeyup="javascript:DigitaNumMascara(this, -1);"/></td>
	         </tr>
	        <tr> 
	          <td class="form_label">* &Aacute;rea utilizada (m²): </td>
	          <td ><html:text maxlength="19" size="20" property="areaUtilizada" styleId="areaUtilizada" onkeyup="javascript:DigitaNumMascara(this, -1);"/></td>
	        </tr>
	        <tr> 
	          <td class="form_label">Data de Averba&ccedil;&atilde;o: </td>
	          <td ><html:text styleId="dataAverbacao" property="dataAverbacao"  size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));"  />
				<img src="${icon_calendar}" width="16" height="15" id="btnCalendar1" onclick="javascript:Calendario(document.getElementById('dataAverbacao'),this.id);" />
			  </td>
	        </tr>
	        <tr> 
	          <td class="form_label" >Quadra / Lote: </td>
	          <td >
					<html:select property="lote"  styleId="lote" >
						<html:option value="">-selecione-</html:option>
						<logic:present name="lotes">
							<html:options collection="lotes" property="codigo" labelProperty="descricao"  />	
						</logic:present>					
					</html:select>
					<a href="javascript:incluirLote()" ><img src="${icon_adicionar}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:incluirLote()">Adicionar Lote</a>
				</td>
			</tr>
			<tr>
			<td colspan="2">
				<c:if test="${!empty requestScope.pagina1.registros}">
		  	  	<div id="conteudo_corpo" >
				  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao" >
					    <ch:lista bean="${pagina1}" atributoId="codLote" />
				        <ch:campo atributo="descricao" label="Lote" />
				        <ch:action imagem="${icon_excluir}"  link="javascript:excluirLote(%1);" label="Excluir" width="40" align="center" />
				        <ch:painel pagina="${link_editar_edificacao}" classe="painel" atributoIndice="indice1" />
				  	</ch:table>  
			  	</div>
		  	</c:if>
		  	</td>
			</tr>
		</table>

	 	<div align="center">
			<table class="form_tabela" cellspacing="0" width="100%">
					<tr> 
					<td width="30%">&ensp;&ensp;&ensp;&ensp;</td>
					<c:choose> 
						<c:when test='${edificacaoForm.actionType == "incluir"}'>
							<td width="20%"><a href="javascript:incluir()"><img src="${icon_adicionar}" width="16" height="16" border="0"> Adicionar </a></td>
						</c:when>
						<c:otherwise>
							<html:hidden property="codEdificacao" value="${edificacaoForm.codEdificacao}" />
							<td width="20%"><a href="javascript:alterar()"><img src="${icon_salvar}" width="16" height="16" border="0">Salvar</a> </td>
						</c:otherwise>
					</c:choose>
					<td width="3%"></td>
					<td width="*"><a href="javascript:limpar()"><img src="${icon_apagar}" width="16" height="16" border="0" >Limpar</a></td>
				</tr>
			</table>
		</div>

  	 	<hr>
  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
 	
 	<c:if test="${!empty requestScope.pagina.registros}">
 	<h2>Edificações Cadastradas</h2>
  	  	<div id="conteudo_corpo">
		  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
			    <ch:lista bean="${pagina}" atributoId="codEdificacao" />
		        <ch:campo atributo="tipoEdificacao" label="Tipo de Edificação" />
		        <ch:campo atributo="tipoConstrucao" label="Tipo de Construção" />		        
		        <ch:campo atributo="especificacao" label="Especificação" />
		        <ch:campo atributo="areaConstruidaDisponivelMetroQuadrado" label="Area em m²" decorator="gov.pr.celepar.abi.util.Area"/>
		        <ch:campo atributo="ocupacao" label="Ocupação" />
		        <ch:action imagem="${icon_ocupacao_imovel}" link="javascript:ocupacao(%1)" label="Ocupação" width="40" align="center"/>
			    <ch:action imagem="${icon_alterar}"  link="javascript:editar(%1);" label="Alterar" width="40" align="center" />
		        <ch:action imagem="${icon_excluir}"  link="javascript:excluir(%1);" label="Excluir" width="40" align="center" />
		        <ch:painel pagina="${link_editar_edificacao}" classe="painel" atributoIndice="indice" />
		  	</ch:table>  
	  	</div>
  	</c:if>
  	
    </div>
    </html:form>
    </div>
 </div>