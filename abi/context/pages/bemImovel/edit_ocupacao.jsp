<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_calendario" value="/js/generic/calendarpopup.js"/>
<c:url var="js_data" value="/js/generic/data.js"/>
<c:url var="js_funcoes" value="/js/generic/funcoes.js"/>
<c:url var="js_util" value="/js/generic/util.js"/>

<c:url var='icon_alterar' value='/images/icon_alterar.png'/>
<c:url var='icon_excluir' value='/images/icon_excluir.png'/>
<c:url var='icon_calendar' value='/images/icon_calendario.png'/>
<c:url var='icon_apagar' value='/images/icon_apagar.png'/>
<c:url var='icon_adicionar' value='/images/icon_adicionar1.png'/>
<c:url var='icon_salvar' value='/images/icon_salvar.png'/>
<c:url var='icon_calculadora' value='/images/icon_calculadora.png'/>

<c:url var='parametroCodBemImovel' value='&codBemImovel=${ocupacaoForm.codBemImovel}&administracao=${ocupacaoForm.administracao}' />
<c:url var='link_editar_edificacao' value='/edificacao.do?action=carregarPgEditEdificacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_editar_ocupacao' value='/ocupacao.do?action=carregarPgEditOcupacao${parametroCodBemImovel}'/>

<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript" >


	function verificarVisibilidade() {
		
		var form = document.ocupacaoForm;
		
		if(form.situacaoOcupacao.value == ${SITUACAO_OCUPACAO_ADMINISTRACAO_ESTADUAL}) {
			document.getElementById("Estadual").style.display = "block";
			document.getElementById("Municipal").style.display = "none";
			document.getElementById("Terceiros").style.display = "none";			
			
		}
		else if(form.situacaoOcupacao.value == ${SITUACAO_OCUPACAO_ADMINISTRACAO_MUNICIPAL} || 
				form.situacaoOcupacao.value == ${SITUACAO_OCUPACAO_ADMINISTRACAO_FEDERAL} ||
				form.situacaoOcupacao.value == ${SITUACAO_OCUPACAO_TERCEIRO_AUTORIZADO}) {
			document.getElementById("Municipal").style.display = "block";
			document.getElementById("Estadual").style.display = "none";
			document.getElementById("Terceiros").style.display = "none";			
			
		}
		else if(form.situacaoOcupacao.value == ${SITUACAO_OCUPACAO_TERCEIRO_NAO_AUTORIZADO}) {
			document.getElementById("Terceiros").style.display = "block";
			document.getElementById("Estadual").style.display = "none";
			document.getElementById("Municipal").style.display = "none";			
			
		}
	} 

	function validarCampos() {
		var form = document.ocupacaoForm;

		if (form.situacaoOcupacao.value == ""){
			alert("Campo 'Situa��o da Ocupa��o' � obrigat�rio!");
			form.situacaoOcupacao.focus();
			return false;
		}
		
		if(form.codOrgao.value == '') {
			alert('Campo \'�rg�o\' � obrigat�rio!');
			form.descricao.focus();
			return false;
		}
		Trim(form.descricao);
		if(form.descricao.value == "") {
			alert("Campo 'Descri��o' � obrigat�rio!");
			form.descricao.focus();
			return false;
		}
		if(form.ocupacaoMetroQuadrado.value == "" && form.ocupacaoPercentual.value == "") {
			alert("A Ocupa��o em m2 ou a Ocupa��o em % dever� ser preenchida!");
			form.ocupacaoMetroQuadrado.focus();
			return false;
		}
		
		if(form.situacaoOcupacao.value == ${SITUACAO_OCUPACAO_ADMINISTRACAO_ESTADUAL}) {
			Trim(form.dataOcupacao);
			Trim(form.termoTransferencia);
		}
		else if(form.situacaoOcupacao.value == ${SITUACAO_OCUPACAO_ADMINISTRACAO_MUNICIPAL} || 
				form.situacaoOcupacao.value == ${SITUACAO_OCUPACAO_ADMINISTRACAO_FEDERAL} ||
				form.situacaoOcupacao.value == ${SITUACAO_OCUPACAO_TERCEIRO_AUTORIZADO}) {

			Trim(form.numeroLei);
			if(form.numeroLei.value == "") {
				alert("Campo 'N� da Lei Autorizat�ria' � obrigat�rio!");
				form.numeroLei.focus();
				return false;
			}
			Trim(form.dataLei);
			if(form.dataLei.value == "") {
				alert("Campo 'Data da Lei' � obrigat�rio!");
				form.dataLei.focus();
				return false;
			}
			Trim(form.vigenciaLei);
			if(form.vigenciaLei.value == "") {
				alert("Campo 'Vig�ncia da Lei' � obrigat�rio!");
				form.vigenciaLei.focus();
				return false;
			}
		
		}
		else if(form.situacaoOcupacao.value == ${SITUACAO_OCUPACAO_TERCEIRO_NAO_AUTORIZADO}) {
			Trim(form.numeroNotificacao);
			if(form.numeroNotificacao.value == "") {
				alert("Campo 'N� da Notifica��o de Desocupa��o' � obrigat�rio!");
				form.numeroNotificacao.focus();
				return false;
			}
			Trim(form.prazoNotificacao);
			if(form.prazoNotificacao.value == "") {
				alert("Campo 'Prazo da Notifica��o' � obrigat�rio!");
				form.prazoNotificacao.focus();
				return false;
			}
			Trim(form.protocoloNotificacaoSpi);
			if(form.protocoloNotificacaoSpi.value == "") {
				alert("Campo 'N� do Protocolo' � obrigat�rio!");
				form.protocoloNotificacaoSpi.focus();
				return false;
			}
		}

		form.submit();
	}
	
	function incluirOcupacao() {
		document.ocupacaoForm.action="ocupacao.do?action=salvarOcupacao&actionType=incluirOcupacao";
		if (validarCampos()){
			document.ocupacaoForm.submit();
		}
	}

	function alterarOcupacao() {
		document.ocupacaoForm.action="ocupacao.do?action=alterarOcupacao";
		if (validarCampos()){
			document.ocupacaoForm.submit();
		}
	}

	function editar(codigo) {
		document.ocupacaoForm.action="${link_editar_ocupacao}&actionType=alterar&codOcupacao="+codigo;
		document.ocupacaoForm.submit();	
	}

	
	function excluir(codigo){
		if(confirm('Tem certeza que deseja excluir?')) {
			document.ocupacaoForm.action="ocupacao.do?action=excluirOcupacao&codOcupacao="+codigo+"&actionType=excluir";
			document.ocupacaoForm.submit();
		}
	}

	function voltar() {
		document.ocupacaoForm.action="edificacao.do?action=carregarPgEditEdificacao${parametroCodBemImovel}&actionType=incluir";
		document.ocupacaoForm.submit();	
	}
	
	function limpar() {	
		document.ocupacaoForm.action='${link_editar_ocupacao}&codEdificacao=${ocupacaoForm.codEdificacao}&actionType=incluir';
		document.ocupacaoForm.submit();	
	}

	function calcularOcupacao() {
		var form = document.ocupacaoForm;
		if(form.ocupacaoMetroQuadrado.value == "" && form.ocupacaoPercentual.value == "") {
			alert("Para efetuar o c�lculo a Ocupa��o em m2 ou a Ocupa��o em % dever� ser preenchida!");
			form.ocupacaoMetroQuadrado.focus();
		}
		else {
			document.ocupacaoForm.action = "ocupacao.do?action=calcularOcupacao";
			document.ocupacaoForm.submit();	
		}
	}

	window.onload = verificarVisibilidade; 

</script>

<div id="conteudo"  >
	<div align="center">

	  <html:form action="ocupacao.do?action=salvarOcupacao">
	  <html:hidden property="codBemImovel" value="${ocupacaoForm.codBemImovel}"/>
	  <html:hidden property="actionType" value="${ocupacaoForm.actionType}"/>
	  
	  	
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
	 

      <div id="conteudo_corpo">
        <h1>Ocupa&ccedil;&atilde;o</h1>
        <table cellspacing="0" class="form_tabela">
          <tr> 
            <td class="form_label" width="220">Edifica&ccedil;&atilde;o: </td>
            <td colspan="4">${ocupacaoForm.especificacao}
            <html:hidden property="codEdificacao" value="${ocupacaoForm.codEdificacao}"/>
            <html:hidden property="especificacao" value="${ocupacaoForm.especificacao}"/>
            <html:hidden property="areaConstruida" value="${ocupacaoForm.areaConstruida}"/>            
          </tr>
        <tr> 
			  <td class="form_label">* Situa&ccedil;&atilde;o da Ocupa&ccedil;&atilde;o: </td>
	          <td colspan="2">
				<html:select property="situacaoOcupacao"  styleId="situacaoOcupacao" onchange="verificarVisibilidade()">
					<html:option value="">-selecione-</html:option>
					<html:options collection="situacaoOcupacaos" property="codSituacaoOcupacao" labelProperty="descricao" />						
				</html:select>
			</td>
	        </tr>

          <tr>
		    <td class="form_label" width="220">�rg�o: </td>
	        <td colspan="4">
		      <html:select property="codOrgao" styleId="codOrgao">
			    <html:option value="">-selecione-</html:option>
			    <logic:present name="orgaos">
		          <html:options collection="orgaos" property="codigo" labelProperty="descricao"/>
			    </logic:present>
			  </html:select>
		    </td>
	      </tr>
	      
          <tr>
            <td class="form_label">* Descri��o: </td>
            <td colspan="4"><html:text maxlength="120" size="80" property="descricao" styleId="descricao"  /></td>
          </tr>
          <tr>
            <td class="form_label">Ocupa&ccedil;&atilde;o em m�: </td>
            <td><html:text maxlength="19" size="20" property="ocupacaoMetroQuadrado" onkeyup="javascript:DigitaNumMascara(this, -1);"/></td>
            <td class="form_label">&nbsp;&nbsp;&nbsp;Ocupa&ccedil;&atilde;o em %: </td>
            <td><html:text maxlength="6" size="20" property="ocupacaoPercentual" onkeyup="javascript:DigitaNumMascara(this, 100);"/></td>
            <td class="form_label"><a href="javascript:calcularOcupacao()"><img src="${icon_calculadora}" width="16" height="16" border="0">Calcular Ocupa&ccedil;&atilde;o</a></td>
          </tr>
        </table>
        <div id="Estadual" style="display:none">
	      <table cellspacing="0" class="form_tabela">
		    <tr> 
			  <td class="form_label" width="220">Data da Ocupa��o: </td>
			  <td>
			    <html:text styleId="dataOcupacao" property="dataOcupacao"  size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));"/>
	            <img src="${icon_calendar}" width="16" height="15" id="btnCalendar" onclick="javascript:Calendario(document.getElementById('dataOcupacao'),this.id);"/>
			  </td>
			</tr>
		    <tr> 
		      <td class="form_label">Termo de Transfer�ncia: </td>
		      <td><html:text maxlength="255" size="100" property="termoTransferencia" styleId="termoTransferencia" /></td>
		    </tr>
	      </table>
        </div>
        <div id="Municipal" style="display:none">
	      <table  cellspacing="0" class="form_tabela">
		    <tr> 
		      <td class="form_label" width="220">* N� da Lei Autorizat�ria </td>
		      <td><html:text maxlength="15" size="15" property="numeroLei" styleId="numeroLei" /></td>
		    </tr>
		    <tr> 
			  <td class="form_label">* Data da Lei: </td>
			  <td>
				<html:text styleId="dataLei" property="dataLei"  size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));"/>
			    <img src="${icon_calendar}" width="16" height="15" id="btnCalendar1" onclick="javascript:Calendario(document.getElementById('dataLei'),this.id);"/>
			  </td>
			</tr>
			<tr> 
		      <td class="form_label">* Vig�ncia da Lei: </td>
		      <td><html:text maxlength="15" size="15" property="vigenciaLei" styleId="vigenciaLei" /></td>
		    </tr>
	      </table>
        </div>
        <div id="Terceiros" style="display:none">
	      <table  cellspacing="0" class="form_tabela" >
		    <tr> 
		      <td class="form_label" width="220">* N� da Notifica��o de Desocupa��o: </td>
		      <td><html:text maxlength="15" size="15" property="numeroNotificacao" styleId="numeroNotificacao" /></td>
		    </tr>
		    <tr> 
		      <td class="form_label">* Prazo da Notifica��o: </td>
		      <td><html:text maxlength="15" size="15" property="prazoNotificacao" styleId="prazoNotificacao" /></td>
		    </tr>
		    <tr> 
		      <td class="form_label">* N� do Protocolo: </td>
		      <td><html:text maxlength="15" size="15" property="protocoloNotificacaoSpi" styleId="protocoloNotificacaoSpi" onkeyup="javascript:DigitaNumero(this)" /></td>
		    </tr>
	      </table>
        </div>
        <table cellspacing="0" class="form_tabela" style="width: 50%;">
	      <tr><td colspan="2">&nbsp;</td></tr>
	      <tr>
            <c:choose> 
			<c:when test='${ocupacaoForm.actionType == "incluir"}'>
		 	  <td align="center"><a href="javascript:incluirOcupacao()"><img src="${icon_adicionar}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:incluirOcupacao()">Adicionar</a></td>
			</c:when>
			<c:otherwise>
			  <td align="center"><html:hidden property="codOcupacao" value="${ocupacaoForm.codOcupacao}"/><a href="javascript:alterarOcupacao()"><img src="${icon_salvar}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:alterarOcupacao()">Salvar</a></td>
		    </c:otherwise>
			</c:choose>
			<td><a href="javascript:limpar()"><img src="${icon_apagar}" width="16" height="16" border="0" ></a>&nbsp;<a href="javascript:limpar()">Limpar</a></td>
	      </tr>
        </table>
		<hr>
  		<p class="obs_obrigatorio">(*) Campo de preenchimento obrigat�rio.</p>
		<c:if test="${!empty requestScope.pagina2.registros}">
 			<h2>Ocupa��es</h2>
	  	  	<div id="conteudo_ocupacoes">
			  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
				    <ch:lista bean="${pagina2}" atributoId="codOcupacao"/>
			        <ch:campo atributo="orgaoSiglaDescricao" label="�rg�o"/>
			        <ch:campo atributo="descricao" label="Descri��o"/>
			        <ch:campo atributo="ocupacaoMetroQuadrado" label="Ocupa��o em m�" decorator="gov.pr.celepar.abi.util.Area" />
			        <ch:campo atributo="ocupacaoPercentual" label="Ocupa��o em %" decorator="gov.pr.celepar.abi.util.Area"/>
				    <ch:action imagem="${icon_alterar}" link="javascript:editar(%1);" label="Alterar" width="40" align="center" />
			        <ch:action imagem="${icon_excluir}" link="javascript:excluir(%1);" label="Excluir" width="40" align="center" />
			        <ch:painel pagina="${link_editar_ocupacao}" classe="painel" atributoIndice="indice2"/>
			  	</ch:table>
		  	</div>
	  	</c:if>
        <div align="center">
			<html:button property="btnVoltar" styleClass="form_botao" value="Voltar" onclick="voltar();"/>
		</div>	  

	</div>
	    
    </html:form>
    </div>
    </div>