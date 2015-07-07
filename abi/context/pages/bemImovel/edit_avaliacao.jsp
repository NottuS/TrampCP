<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_data" value="/js/generic/data.js" />
<c:url var="js_util" value="/js/generic/util.js" />

<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='icon_calendar' value='/images/icon_calendario.png' />
<c:url var='icon_apagar' value='/images/icon_apagar.png' />
<c:url var='icon_adicionar' value='/images/icon_adicionar1.png' />
<c:url var='icon_salvar' value='/images/icon_salvar.png' />

<c:url var='parametroCodBemImovel' value='&codBemImovel=${avaliacaoForm.codBemImovel}&administracao=${avaliacaoForm.administracao}' />
<c:url var='link_aba_identificacao' value='/bemImovel.do?action=carregarPgEditBemImovel&actionType=alterar${parametroCodBemImovel}' />
<c:url var='link_aba_lei' value='/leiBemImovel.do?action=carregarPgEditLeiBemImovel${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_quadraLote' value='/quadraLote.do?action=carregarPgEditQuadraLote${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_confrontante' value='/confrontante.do?action=carregarPgEditConfrontante${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_avaliacao' value='/avaliacao.do?action=carregarPgEditAvaliacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_coordenadaUtm' value='/coordenadaUtm.do?action=carregarPgEditCoordenadaUtm${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_edificacao' value='/edificacao.do?action=carregarPgEditEdificacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_documentacao' value='/documentacao.do?action=carregarPgEditDocumentacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_anexo' value='/anexoBemImovel.do?action=carregarPgEditAnexo${parametroCodBemImovel}&actionType=incluir' />

<c:url var='link_editar_avaliacao' value='/avaliacao.do?action=carregarPgEditAvaliacao${parametroCodBemImovel}' />
<c:url var='link_pesquisar_avaliacao' value='/avaliacao.do?action=carregarPgEditAvaliacao${parametroCodBemImovel}&actionType=incluir' />

<jsp:useBean id="agora" class="java.util.Date" />

<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">

	function incluir() {
		document.avaliacaoForm.action="avaliacao.do?action=salvarAvaliacao";
		if (validarCampos()){
			document.avaliacaoForm.submit();
		}
	}

	function alterar() {
		document.avaliacaoForm.action="avaliacao.do?action=alterarAvaliacao";
		if (validarCampos()){
			document.avaliacaoForm.submit();
		}
	}

	function editar(codigo) {
		document.avaliacaoForm.action="${link_editar_avaliacao}&actionType=alterar&codAvaliacao="+codigo;
		document.avaliacaoForm.submit();
		
	}

	

	function excluir(codigo,descricao){
		
		if(confirm('Tem certeza que deseja excluir?')) {
			document.avaliacaoForm.action="avaliacao.do?action=excluirAvaliacao&codAvaliacao="+codigo+"&actionType=excluir";
			document.avaliacaoForm.submit();
		}
		
	}

	function apagar() {	
		document.avaliacaoForm.dataAvaliacao.value="";
		document.avaliacaoForm.valor.value="";
		document.avaliacaoForm.action="avaliacao.do?action=carregarPgEditAvaliacao&codBemImovel=${avaliacaoForm.codBemImovel}&actionType=inlcuir";
		document.avaliacaoForm.submit();
	}
	
	function validarCampos() {
	
		var form = document.avaliacaoForm;
		dataAux= new Date();
		dataAtual=dataAux.toLocaleDateString();
	
		if(document.getElementById("selBemImovel1").checked) {
			if (!document.getElementById("indTipoAvaliacao1").checked && !document.getElementById("indTipoAvaliacao2").checked) {
				alert("Campo 'Tipo de Avaliação' deve ser informado!");
				return false;
			}			
		}

		if(!ValidaData(form.dataAvaliacao)) {
			alert("Campo 'Data da Avaliação' deve ser uma data válida!");
			form.dataAvaliacao.focus();
			return false;			
		}
		if (DataMaior(form.dataAvaliacao.value, dataAtual )){
			alert("Campo 'Data da Avaliação' não pode ser maior que a data atual!");
			form.dataAvaliacao.focus();
			return false;			
		}
		if (!DataMaior(form.dataAvaliacao.value,'03/12/1889')){
			alert("Campo 'Data da Notificação' deve ser igual ou maior que 04/12/1889!");
			form.dataAvaliacao.focus();
			return false;			
		}
		Trim(form.valor);
		if (form.valor.value == ""){
			alert("Campo 'Avaliação do Imóvel' é obrigatório!");
			form.valor.focus();
			return false;
		}
		return true;
	}

	function verificarVisibilidade(){
		habilitarAbas();
		if(document.getElementById("selBemImovel1").checked) {
			document.getElementById("lEdificacao").style.display="none";
			document.getElementById("lBemImovel").style.display="block";
			document.getElementById("cEdificacao").style.display="none";
			document.getElementById("cBemImovel").style.display="block";
		} else {
			document.getElementById("lEdificacao").style.display="block";
			document.getElementById("lBemImovel").style.display="none";
			document.getElementById("cEdificacao").style.display="block";
			document.getElementById("cBemImovel").style.display="none";
		}
	}
	function trocarAba(aba){
		
		var form = document.avaliacaoForm;
		document.avaliacaoForm.action=aba;
		form.submit();
	}

	function habilitarAbas() {
		var form = document.avaliacaoForm;
		if (form.somenteTerreno.value == 'S') {
			document.getElementById("abaEdificacao").style.display="none";
			document.getElementById("abaEdificacaoDesabilitada").style.display="block";
		} else if (form.somenteTerreno.value == 'N'){
			document.getElementById("abaEdificacao").style.display="block";
			document.getElementById("abaEdificacaoDesabilitada").style.display="none";
		}
	}
	
</script>
<body onLoad="verificarVisibilidade()">



<div id="conteudo">
	<div align="center">
	<h1>Alterar Bem Imóvel - Avaliação</h1>


	<html:form action="avaliacao.do?action=salvarAvaliacao" method="post">
	
		<html:hidden property="codBemImovel" value="${avaliacaoForm.codBemImovel}"/>
		<html:hidden property="actionType" value="${avaliacaoForm.actionType}" />
		<html:hidden property="codAvaliacao" value="${avaliacaoForm.codAvaliacao}" />
	<html:hidden property="somenteTerreno" value="${avaliacaoForm.somenteTerreno}" />
		
			
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
	 		<table class="form_tabela" cellspacing="0" width="100%" style="">
				<tr>
					<td style="text-align: center;">
						<span class="form_label_infBI">Identificação do Imóvel CPE: </span>
						<c:out value="${avaliacaoForm.nrBemImovel}"></c:out>
					</td>
				</tr>

				<tr>
					<td style="text-align: center;">
						<span class="form_label_infBI">Administração: </span>
						<c:out value="${avaliacaoForm.descricaoAdministracao}"></c:out>
					</td>
				</tr>
	 		</table>
   		</div>

    	<a name="abas"></a> 
		<table class="abas" cellspacing="0">
		     <tr> 
				<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_identificacao}')">Identificação</a></td>
		       	<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_documentacao}')">Documentação</a> </td>
		      	<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_lei}')">Lei/Decreto</a></td>
	          	<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_quadraLote}')">Quadra/Lote</a></td>
		      	<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_confrontante}')">Confrontantes</a></td>
	          	<td class="aba_desselecionada">
	        	  <div id="abaEdificacao"> 
	        		<a href="javascript:trocarAba('${link_aba_edificacao}')">Edificação</a>
	        	  </div>
	        	  <div id="abaEdificacaoDesabilitada" style="display:none">Edificação</div>
	          	</td>
		      	<td class="aba_selecionada">Avaliação</td>
		      	<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_coordenadaUtm}')">Coordenada UTM </a></td>
 			  	<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_anexo}')">Anexos</a></td>        
		     </tr>
		</table>
     
    	<div id="conteudo_abas">
		<table class="form_tabela" cellspacing="0">
			<tr> 
	          <td class="form_label">* Por:</td>
	          <td>
	    	 	<html:radio property="selBemImovel" styleId="selBemImovel1" value="1" onchange="verificarVisibilidade()" >Bem Im&oacute;vel</html:radio>
	          	<html:radio property="selBemImovel" styleId="selBemImovel2" value="2" onchange="verificarVisibilidade()">Edifica&ccedil;&atilde;o</html:radio>
	          </td>
	        </tr>

			<tr>
				<td class="form_label">
					<div id="lBemImovel" style="display:none;">* Tipo de Avaliação:</div> 
					<div id="lEdificacao" style="display:none;">Edificação:</div>
				</td>
				<td>
					<div id="cBemImovel" style="display:none;">
		    	 		<html:radio property="indTipoAvaliacao" styleId="indTipoAvaliacao1" value="1">Valor Total do Bem Imóvel</html:radio>
		          		<html:radio property="indTipoAvaliacao" styleId="indTipoAvaliacao2" value="2">Valor do Terreno do Bem Imóvel</html:radio>
					</div> 
					<div id="cEdificacao" style="display:none;"> 
						<html:select property="edificacao">
							<html:option value="">-selecione-</html:option>
							<logic:present name="edificacoes">
								<html:options collection="edificacoes" property="codEdificacao" labelProperty="especificacao" />
							</logic:present>
						</html:select>
					</div>
				</td>
			</tr>
			<tr> 
	          <td class="form_label">Data da Avaliação: </td>
	          <td>
					<html:text styleId="dataAvaliacao" property="dataAvaliacao"  size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));" />
					<img src="${icon_calendar}" width="16" height="15" id="btnCalendar" onclick="javascript:Calendario(document.getElementById('dataAvaliacao'),this.id);" />
				</td>
	        </tr>
	        <tr>
	          <td class="form_label">Avaliação do Imóvel (R$):
	          </td>
	          <td><html:text  property="valor" maxlength="19" size="30" styleId="valor" onkeyup="javascript:DigitaNumMascara(this, -1);"/></td>
	        </tr>
		</table>

	 	<div align="center">
			<table class="form_tabela" cellspacing="0" width="100%">
					<tr> 
					<td width="30%">&ensp;&ensp;&ensp;&ensp;</td>
					<c:choose> 
						<c:when test='${avaliacaoForm.actionType == "incluir"}'>
							<td width="20%"><a href="javascript:incluir();"><img src="${icon_adicionar}" width="16" height="16" border="0"> Adicionar </a></td>
						</c:when>
						<c:otherwise>
							<td width="20%"><a href="javascript:alterar();"><img src="${icon_salvar}" width="16" height="16" border="0">Salvar</a> </td>
						</c:otherwise>
					</c:choose>
					<td width="3%"></td>
					<td width="*"><a href="javascript:apagar();"><img src="${icon_apagar}" width="16" height="16" border="0" >Limpar</a></td>
				</tr>
			</table>
		</div>

  	 	<hr>
  		<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
 	
	</div>
	<c:if test="${!empty requestScope.pagina.registros}">
  	  	<div id="conteudo_corpo">
		  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
			    <ch:lista bean="${pagina}" atributoId="codAvaliacao" />
		        <ch:campo atributo="dataAvaliacao" label="Data da Avaliação" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data"/>
		        <ch:campo atributo="valor" label="Avaliaçao do Imóvel (R$)" decorator="gov.pr.celepar.abi.util.Area"/>
		        <ch:campo atributo="edificacao.especificacao" label="Edificação" />
		        <ch:campo atributo="indTipoAvaliacao" label="Tipo de Avaliacao" decorator="gov.pr.celepar.abi.util.AvaliacaoImovelDecor"/>
			    <ch:action imagem="${icon_alterar}" link="javascript:editar(%1);" label="Alterar" width="40" align="center" />
		        <ch:action imagem="${icon_excluir}" link="javascript:excluir(%1);" label="Excluir" width="40" align="center" />
		        <ch:painel pagina="${link_pesquisar_avaliacao}" classe="painel" atributoIndice="indice" />
		  	</ch:table>  
	  	</div>
  	</c:if>
	<hr>
		
	</html:form> 
	
  </div>
</div>
</body> 