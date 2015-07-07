<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>


<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='icon_apagar' value='/images/icon_apagar.png' />
<c:url var='icon_adicionar' value='/images/icon_adicionar1.png' />
<c:url var='icon_salvar' value='/images/icon_salvar.png' />


<c:url var='parametroCodBemImovel' value='&codBemImovel=${coordenadaUtmForm.codBemImovel}&administracao=${coordenadaUtmForm.administracao}' />
<c:url var='link_aba_identificacao' value='/bemImovel.do?action=carregarPgEditBemImovel&actionType=alterar${parametroCodBemImovel}' />
<c:url var='link_aba_lei' value='/leiBemImovel.do?action=carregarPgEditLeiBemImovel${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_quadraLote' value='/quadraLote.do?action=carregarPgEditQuadraLote${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_confrontante' value='/confrontante.do?action=carregarPgEditConfrontante${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_avaliacao' value='/avaliacao.do?action=carregarPgEditAvaliacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_coordenadaUtm' value='/coordenadaUtm.do?action=carregarPgEditCoordenadaUtm${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_edificacao' value='/edificacao.do?action=carregarPgEditEdificacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_documentacao' value='/documentacao.do?action=carregarPgEditDocumentacao${parametroCodBemImovel}&actionType=incluir' />
<c:url var='link_aba_anexo' value='/anexoBemImovel.do?action=carregarPgEditAnexo${parametroCodBemImovel}&actionType=incluir' />

<c:url var='link_editar_coordenada' value='/coordenadaUtm.do?action=carregarPgEditCoordenadaUtm${parametroCodBemImovel}' />
<c:url var='link_pesquisar_coordenada' value='/coordenadaUtm.do?action=carregarPgEditCoordenadaUtm${parametroCodBemImovel}&actionType=incluir' />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">
	
	function incluir() {
		document.coordenadaUtmForm.action="coordenadaUtm.do?action=salvarCoordenadas";
		if (validarCampos()){
			document.coordenadaUtmForm.submit();
		}
	}
	
	function alterar() {
		document.coordenadaUtmForm.action="coordenadaUtm.do?action=alterarCoordenadas";
		if (validarCampos()){
			document.coordenadaUtmForm.submit();
		}
	}

	function editar(codigo) {
		document.coordenadaUtmForm.action="${link_editar_coordenada}&actionType=alterar&codCoordenadaUtm="+codigo;
		document.coordenadaUtmForm.submit();
		
	}


	
	
	function excluir(codigo,descricao){
		
		if(confirm('Tem certeza que deseja excluir?')) {
			document.coordenadaUtmForm.action="coordenadaUtm.do?action=excluirCoordendas&codCoordenadaUtm="+codigo+"&actionType=excluir";
			document.coordenadaUtmForm.submit();
		}
		
	}
	
	function apagar() {
		var form = document.coordenadaUtmForm;
		form.coordenadaX.value="";
		form.coordenadaY.value="";	
		form.action="${link_aba_coordenadaUtm}&actionType=incluir";
		form.submit();
	
	}
	
	function validarCampos(){
		var form = document.coordenadaUtmForm;
		Trim(form.coordenadaX);
		if (form.coordenadaX.value == ""){
			alert("Campo 'Coordenada X' é obrigatório!");
			form.coordenadaX.focus();
			return false;
		}
		Trim(form.coordenadaY);
		if (form.coordenadaY.value == ""){
			alert("Campo 'Coordenada Y' é obrigatório!");
			form.coordenadaY.focus();
			return false;
		}
		return true;
		
		
	}

	function trocarAba(aba){
		
		var form = document.coordenadaUtmForm;
		document.coordenadaUtmForm.action=aba;
		form.submit();
	}

	function MascaraCoordenada(campo,w) {
		// mascara = '########,#######'
		var Coordenada = SoNumero(campo.value);
		   var CoordenadaAux = ''; 
		   var campo1 = campo.value;

		   if (w.keyCode == 8) {      
		     if (campo1.length == 8 ) {
		    	 Coordenada = Coordenada.substr(0,Coordenada.length-1);
		       } 
		    }
		    
		   if (Coordenada.length < 16) {
		     for (var i=0; i < Coordenada.length; i++) {
		       CoordenadaAux = CoordenadaAux + Coordenada.substr(i,1);     
		       if (i==7 ) {
		    	   CoordenadaAux = CoordenadaAux + ".";
		        }
		      }  
		     campo.value = CoordenadaAux;
		    }
		   else {
		      campo.value = campo.value.substr(0,10);
		     }    
		
	}

	function habilitarAbas() {
		var form = document.coordenadaUtmForm;
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
	<h1>Alterar Bem Imóvel - Coordenadas UTM</h1>

	<html:form action="coordenadaUtm.do?action=salvarCoordenadas" method="post">
	<html:hidden property="codBemImovel" value="${coordenadaUtmForm.codBemImovel}"/>
	<html:hidden property="actionType" value="${coordenadaUtmForm.actionType}" />
	<html:hidden property="codCoordenadaUtm" value="${coordenadaUtmForm.codCoordenadaUtm}" />
	<html:hidden property="somenteTerreno" value="${coordenadaUtmForm.somenteTerreno}" />
		
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
					<c:out value="${coordenadaUtmForm.nrBemImovel}"></c:out>
				</td>
			</tr>

			<tr>
				<td style="text-align: center;">
					<span class="form_label_infBI">Administração: </span>
					<c:out value="${coordenadaUtmForm.descricaoAdministracao}"></c:out>
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
        <td class="aba_desselecionada">
        	<div id="abaEdificacao"> 
        		<a href="javascript:trocarAba('${link_aba_edificacao}')">Edificação</a>
        	</div>
        	<div id="abaEdificacaoDesabilitada" style="display:none">Edificação</div>
        </td>
        <td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_avaliacao}')">Avaliação</a></td>
        <td class="aba_selecionada">Coordenada UTM</td>
		<td class="aba_desselecionada"><a href="javascript:trocarAba('${link_aba_anexo}')">Anexos</a></td>        
      </tr>
	</table>

    <div id="conteudo_abas">
	 <table class="form_tabela" cellspacing="0">
		<tr>
			<td class="form_label">*Coordenada X: </td>
			<td><html:text  property="coordenadaX" maxlength="16" size="30" styleId="coordenadaX" onkeyup="javascript: MascaraCoordenada(document.getElementById('coordenadaX'),event)"/></td>
		</tr>
		<tr>
			<td class="form_label">*Coordenada Y: </td>
			<td><html:text  property="coordenadaY" maxlength="16" size="30" styleId="coordenadaY" onkeyup="javascript: MascaraCoordenada(document.getElementById('coordenadaY'),event)"/></td>
        </tr>
	 </table>
		
	 	<div align="center">
			<table class="form_tabela" cellspacing="0" width="100%">
					<tr> 
					<td width="30%">&ensp;&ensp;&ensp;&ensp;</td>
					<c:choose> 
						<c:when test='${coordenadaUtmForm.actionType == "incluir"}'>
							<td width="20%"><a href="javascript:incluir()"><img src="${icon_adicionar}" width="16" height="16" border="0"> Adicionar </a></td>
						</c:when>
						<c:otherwise>
							<td width="20%"><a href="javascript:alterar()"><img src="${icon_salvar}" width="16" height="16" border="0">Salvar</a> </td>
						</c:otherwise>
					</c:choose>
					<td width="3%"></td>
					<td width="*"><a href="javascript:apagar();"><img src="${icon_apagar}" width="16" height="16" border="0" >Limpar</a></td>
				</tr>
			</table>
		</div>

   	<hr>
  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
 	
	<c:if test="${!empty requestScope.pagina.registros}">
  	  	<div id="conteudo_corpo">
		  	<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
			    <ch:lista bean="${pagina}" atributoId="codCoordenadaUtm" />
		        <ch:campo atributo="coordenadaX" label="Coordenada X" />
		        <ch:campo atributo="coordenadaY" label="Coordenada Y" />
		        <ch:action imagem="${icon_alterar}"  link="javascript:editar(%1);" label="Alterar" width="40" align="center" />
		        <ch:action imagem="${icon_excluir}"  link="javascript:excluir(%1);" label="Excluir" width="40" align="center" />
		        <ch:painel pagina="${link_pesquisar_coordenada}" classe="painel" atributoIndice="indice" />
		  	</ch:table>  
	  	</div>
  	</c:if>
  </div>

  </html:form>

  </div>
</div>
