<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_maisinfo" value="/js/generic/maisinfo.js" />
<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var="js_data" value="/js/generic/data.js" />

<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='icon_apagar' value='/images/icon_apagar.png' />
<c:url var='icon_adicionar' value='/images/icon_adicionar1.png' />
<c:url var='icon_localizar' value='/images/icon_localizar.png' />
<c:url var='icon_salvar' value='/images/icon_salvar.png' />
<c:url var='icon_calendar' value='/images/icon_calendario.png' />


<c:url var='parametroCodBemImovel' value='&codBemImovel=${leiBemImovelForm.codBemImovel}&administracao=${leiBemImovelForm.administracao}' />
<c:url var='link_aba_identificacao' value='/bemImovel.do?action=carregarPgEditBemImovel&actionType=alterar${parametroCodBemImovel}' />
<c:url var='link_aba_lei' value='/leiBemImovel.do?action=carregarPgEditLeiBemImovel${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_quadraLote' value='/quadraLote.do?action=carregarPgEditQuadraLote${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_confrontante' value='/confrontante.do?action=carregarPgEditConfrontante${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_avaliacao' value='/avaliacao.do?action=carregarPgEditAvaliacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_coordenadaUtm' value='/coordenadaUtm.do?action=carregarPgEditCoordenadaUtm${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_edificacao' value='/edificacao.do?action=carregarPgEditEdificacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_documentacao' value='/documentacao.do?action=carregarPgEditDocumentacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_anexo' value='/anexoBemImovel.do?action=carregarPgEditAnexo${parametroCodBemImovel}&actionType=incluir' />

<c:url var='link_editar_leiBemImovel' value='/leiBemImovel.do?action=carregarPgEditLeiBemImovel${parametroCodBemImovel}' />
<c:url var='link_pesquisar_leiBemImovel' value='/leiBemImovel.do?action=carregarPgEditLeiBemImovel${parametroCodBemImovel}&actionType=incluir' />

<jsp:useBean id="agora" class="java.util.Date" />

<script language="JavaScript" type="text/JavaScript" src="${js_maisinfo}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">
	
	function incluir() {
		document.leiBemImovelForm.action="leiBemImovel.do?action=salvarLeiBemImovel";
		if (validarCampos()){
			document.leiBemImovelForm.submit();
		}
	}
	
	function alterar() {
		document.leiBemImovelForm.action="leiBemImovel.do?action=alterarLeiBemImovel";
		if (validarCampos()){
			document.leiBemImovelForm.submit();
		}
	}
	
	function excluir(codigo){
		
		if(confirm('Tem certeza que deseja excluir?')) {
			document.leiBemImovelForm.action="leiBemImovel.do?action=excluirLeiBemImovel&codLeiBemImovel="+codigo+"&actionType=excluir";
			document.leiBemImovelForm.submit();
		}
		
	}
	function editar(codigo) {
		document.leiBemImovelForm.action="${link_editar_leiBemImovel}&actionType=alterar&codLeiBemImovel="+codigo;
		document.leiBemImovelForm.submit();
		
	}

	
	
	function apagar() {
		var form = document.leiBemImovelForm;
		form.numero.value="";
		form.tipoLeiBemImovel.value="";
		form.dataAssinatura.value="";
		form.dataPublicacao.value="";
		form.action="${link_aba_lei}&actionType=incluir";
		form.submit();
	
	}
	
	function validarCampos(){

		var form = document.leiBemImovelForm;
		dataAux= new Date();
		dataAtual=dataAux.toLocaleDateString();

		if (form.tipoLeiBemImovel.value == ""){
			alert("Campo 'Tipo da Lei/Decreto' é obrigatório!");
			form.tipoLeiBemImovel.focus();
			return false;
		}
		
		Trim(form.numero);
		if (form.numero.value == ""){
			alert("Campo 'Número' é obrigatório!");
			form.numero.focus();
			return false;
		}

		if (!ValidaData(form.dataAssinatura)){
			alert("Campo 'Data de Assinatura' deve ser uma data válida!");
			form.dataAssinatura.focus();
			return false;			
		}
		if (DataMaior(form.dataAssinatura.value, dataAtual)){
			alert("Campo 'Data da Assinatura' não pode ser maior que a data atual!");
			form.dataAssinatura.focus();
			return false;			
		}
		if (!DataMaior(form.dataAssinatura.value,'03/12/1889')){
			alert("Campo 'Data da Assinatura' deve ser igual ou maior que 04/12/1889!");
			form.dataAssinatura.focus();
			return false;			
		}

		if (!ValidaData(form.dataPublicacao)){
			alert("Campo 'Data de Publicação' deve ser uma data válida!");
			form.dataPublicacao.focus();
			return false;			
		}
		if (DataMaior(form.dataPublicacao.value, dataAtual )){
			alert("Campo 'Data da Publicação' não pode ser maior que a data atual!");
			form.dataAssinatura.focus();
			return false;			
		}
		if (!DataMaior(form.dataPublicacao.value,'03/12/1889')){
			alert("Campo 'Data da Publicação' deve ser igual ou maior que 04/12/1889!");
			form.dataAssinatura.focus();
			return false;			
		}		

		return true;
		
		
	}

	function trocarAba(aba){
		
		var form = document.leiBemImovelForm;
		document.leiBemImovelForm.action=aba;
		form.submit();
	}

	function habilitarAbas() {
		var form = document.leiBemImovelForm;
		if (form.somenteTerreno.value == 'S') {
			document.getElementById("abaEdificacao").style.display="none";
			document.getElementById("abaEdificacaoDesabilitada").style.display="block";
		} else if (form.somenteTerreno.value == 'N'){
			document.getElementById("abaEdificacao").style.display="block";
			document.getElementById("abaEdificacaoDesabilitada").style.display="none";
		}
	}

</script>

<body onload="habilitarAbas();">

<div id="conteudo">
	<div align="center">
	<h1>Alterar Bem Imóvel - Lei/Decreto</h1>

	<html:form action="leiBemImovel.do?action=salvarLeiBemImovel" method="post">
	<html:hidden property="codBemImovel" value="${leiBemImovelForm.codBemImovel}"/>
	<html:hidden property="actionType" value="${leiBemImovelForm.actionType}" />
	<html:hidden property="codLeiBemImovel" value="${leiBemImovelForm.codLeiBemImovel}" />
	<html:hidden property="somenteTerreno" value="${leiBemImovelForm.somenteTerreno}" />
	
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
					<c:out value="${leiBemImovelForm.nrBemImovel}"></c:out>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<span class="form_label_infBI">Administração: </span>
					<c:out value="${leiBemImovelForm.descricaoAdministracao}"></c:out>
				</td>
			</tr>
 		</table>
   </div>

    <a name="abas"></a> 
	<table class="abas" cellspacing="0">
      <tr> 
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_identificacao}')">Identificação</a></td>
         <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_documentacao}')">Documentação</a></td>
        <td class="aba_selecionada">Lei/Decreto</td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_quadraLote}')">Quadra/Lote</a></td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_confrontante}')">Confrontantes</a></td>
        <td class="aba_desselecionada">
        	<div id="abaEdificacao"> 
        		<a href="javascript:trocarAba('${link_aba_edificacao}')">Edificação</a>
        	</div>
        	<div id="abaEdificacaoDesabilitada" style="display:none">Edificação</div>
        </td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_avaliacao}')">Avaliação</a></td>
		<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_coordenadaUtm}')">Coordenada UTM</a></td>        
		<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_anexo}')">Anexos</a></td>        
      </tr>
	</table>

    <div id="conteudo_abas">
	 <table class="form_tabela" cellspacing="0">

	 
		<tr>
			<td class="form_label">*Tipo da Lei/ Decreto:</td>
				<td colspan="2">
					<html:select property="tipoLeiBemImovel">
						<html:option value="">-selecione-</html:option>
						<html:options collection="tipoLeiBemImovels" property="codTipoLeiBemImovel" labelProperty="descricao" />						
					</html:select>
				</td>

		</tr>

		<tr>
			<td class="form_label">*Nº da Lei :</td>
			<td><html:text  property="numero" maxlength="15" size="15" styleId="numero" onkeyup="javascript:DigitaNumero(document.getElementById('numero'))" /></td>
		</tr>
		
		<tr>

			<td class="form_label">*Data Assinatura:</td>
			<td>
				<html:text styleId="dataAssinatura" property="dataAssinatura" size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));" />
				<img src="${icon_calendar}" width="16" height="15" id="btnCalendar" onclick="Calendario(document.getElementById('dataAssinatura'),this.id);" />				
			</td>


			<td class="form_label">*Data Publicação:</td>
			<td>
				<html:text styleId="dataPublicacao" property="dataPublicacao" size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));" />
				<img src="${icon_calendar}" width="16" height="15" id="btnCalendar" onclick="Calendario(document.getElementById('dataPublicacao'),this.id);" />				
				Ex: <fmt:formatDate value="${agora}" type="both" pattern="dd/MM/yyyy"/>
			</td>
		</tr>
		<tr>
			<td class="form_label">Nº do Diário Oficial :</td>
			<td><html:text  property="nrDioe" maxlength="15" size="15" styleId="nrDioe" onkeyup="javascript:DigitaNumero(document.getElementById('nrDioe'))" /></td>
		</tr>
	 </table>
		
 	<div align="center">
		<table class="form_tabela" cellspacing="0" width="100%">
				<tr> 
				<td width="30%">&ensp;&ensp;&ensp;&ensp;</td>
				<c:choose> 
					<c:when test='${leiBemImovelForm.actionType == "incluir"}'>
						<td width="20%"><a href="javascript:incluir()"><img src="${icon_adicionar}" width="16" height="16" border="0"> Adicionar </a></td>
					</c:when>
					<c:otherwise>
						<td width="20%"><a href="javascript:alterar()"><img src="${icon_salvar}" width="16" height="16" border="0">Salvar</a> </td>
					</c:otherwise>
				</c:choose>
				<td width="3%"></td>
				<td width="*"><a href="javascript:apagar()"><img src="${icon_apagar}" width="16" height="16" border="0" >Limpar</a></td>
			</tr>
		</table>
	</div>
     
   	<hr>
  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
 	
	<c:if test="${!empty requestScope.pagina.registros}">
  	  	<div id="conteudo_corpo">
		  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
			    <ch:lista bean="${pagina}" atributoId="codLeiBemImovel" />
		        <ch:campo atributo="tipoLeiBemImovel.descricao" label="Tipo da Lei" />
		        <ch:campo atributo="numero" label="Nº da Lei" />
		        <ch:campo atributo="dataAssinatura" label="Data de Assinatura" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data"/>
		        <ch:campo atributo="dataPublicacao" label="Data de Publicação"  decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data"/>
		        <ch:campo atributo="nrDioe" label="Nº do Diário Oficial" />
		        <ch:action imagem="${icon_alterar}"  link="javascript:editar(%1);" label="Alterar" width="40" align="center" />
		        <ch:action imagem="${icon_excluir}"  link="javascript:excluir(%1);" label="Excluir" width="40" align="center" />
		        <ch:painel pagina="${link_pesquisar_leiBemImovel}" classe="painel" atributoIndice="indice" />
		  	</ch:table>  
	  	</div>
  	</c:if>
  </div>

  </html:form>

  </div>
</div>
