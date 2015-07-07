<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_prototype" value="/js/generic/prototype.js" />
<c:url var="js_data" value="/js/generic/data.js" />
<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var='icon_calendar' value='/images/icon_calendario.png' />

<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_prototype}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>

<c:url var="js_ajaxutil" value="/js/ajaxutil.js" />

<script language="JavaScript" type="text/JavaScript" src="${js_ajaxutil}"></script>

<c:url var='icon_localizar' value='/images/icon_localizar.png' />
<c:url var="icon_excluir" value="/images/icon_excluir.png" scope="request" />
<c:url var="icon_alterar" value="/images/icon_alterar.png" scope="request" />

<script language="javascript">
	function voltar(){	
		document.vistoriaBemImovelForm.tipoAcao.value = "voltar";
		document.vistoriaBemImovelForm.action="vistoriaBemImovel.do?action=concluir";
		document.vistoriaBemImovelForm.submit();
	}

	function pesquisar() {
		limpaMensagens();
        if (document.forms[0].indGrupoSentinela.value == document.forms[0].adm.value){
        	if (document.vistoriaBemImovelForm.conInstituicao.value == ""){
    			alert("Campo 'Instituição' é obrigatório!");
    			document.vistoriaBemImovelForm.conInstituicao.focus();
    			return false;
    		}
        }    
        
		submitAjax("vistoriaBemImovel.do?action=consultar", document.forms[0], 'pesquisaAjax', true, true);
	}

	function gerarNovaVistoria(){
		document.vistoriaBemImovelForm.tipoAcao.value = "incluir";
		document.vistoriaBemImovelForm.action="vistoriaBemImovel.do?action=carregarInterfaceIncluir";
		document.vistoriaBemImovelForm.submit();
	}
	
	function exibir(id) {
		document.vistoriaBemImovelForm.tipoAcao.value = "exibir";
	    document.vistoriaBemImovelForm.action="vistoriaBemImovel.do?action=carregarInterfaceExibirExcluir";
	    document.vistoriaBemImovelForm.codVistoria.value = id;
	    document.vistoriaBemImovelForm.submit();
	}
	
	function alterar(id) {
		document.vistoriaBemImovelForm.tipoAcao.value = "alterar";
	    document.vistoriaBemImovelForm.action="vistoriaBemImovel.do?action=carregarInterfaceAlterar";
	    document.vistoriaBemImovelForm.codVistoria.value = id;
	    document.vistoriaBemImovelForm.submit();
	}
	
	function excluir(id) {
		document.vistoriaBemImovelForm.tipoAcao.value = "excluir";
	    document.vistoriaBemImovelForm.action="vistoriaBemImovel.do?action=carregarInterfaceExibirExcluir";
	    document.vistoriaBemImovelForm.codVistoria.value = id;
	    document.vistoriaBemImovelForm.submit();
	}

	function imprimir(id) {
		document.vistoriaBemImovelForm.tipoAcao.value = "imprimir";
	    document.vistoriaBemImovelForm.action="vistoriaBemImovel.do?action=redirecionarUCSGerarRelatorio";
	    document.vistoriaBemImovelForm.codVistoria.value = id;
	    document.vistoriaBemImovelForm.submit();
	}

	function buscaBemImovel() {
		var form = document.vistoriaBemImovelForm;
		var codInstituicao = '';
		if (document.forms[0].indGrupoSentinela.value == document.forms[0].adm.value){
        	codInstituicao = document.vistoriaBemImovelForm.conInstituicao.value;
		}
            	
		if (form.conNumeroBemImovel.value != ''){
			form.conNumeroBemImovel.disabled = false;
			form.conDataInicialVistoria.disabled = false;
			form.conDataFinalVistoria.disabled = false;
			form.conSituacao.disabled = false;
			submitAjax('/abi/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.conNumeroBemImovel.value + '&codInstituicao='+codInstituicao, document.forms[0], "dadosBemImovel",true);
		} else {
			ajaxHTMLLoad('dadosBemImovel','/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.conNumeroBemImovel.value,true);
		}
	}

	function localizarBemImovel() {
		var form = document.vistoriaBemImovelForm;

		var camposPesquisaUCOrigem = "";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conNumeroBemImovel.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conDataInicialVistoria.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conDataFinalVistoria.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conSituacao.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + "P;";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.tipoAcao.value + ";";

		var strParametros="dialogHeight: 1000px; dialogWidth: 700px; dialogleft: 100px; dialogtop: 100px; Help:no; Status:no; Center:yes;";
		window.showModalDialog ('/abi/localizarBemImovelSimplificado.do?action=carregarInterfaceInicial&camposPesquisaUCOrigem='+camposPesquisaUCOrigem+"&actionUCOrigem=vistoriaBemImovel",strParametros);
	}

	window.onload = function(){
		var form = document.vistoriaBemImovelForm;
		if (form.conNumeroBemImovel.value != ''){
			buscaBemImovel();
		}
		if (document.forms[0].indGrupoSentinela.value == document.forms[0].adm.value){
			form.conNumeroBemImovel.disabled = true;
			form.conDataInicialVistoria.disabled = true;
			form.conDataFinalVistoria.disabled = true;
			form.conSituacao.disabled = true;
		}
	};

	function limpaMensagens() {
		if(document.getElementById("__msgVerificador")!=null)
		 	document.getElementById("__msgVerificador").innerHTML = "";
		if(document.getElementById("msg")!=null) 
			document.getElementById("msg").innerHTML = "";
		if(document.getElementById("msgs_servidor")!=null)
			document.getElementById("msgs_servidor").innerHTML = "";
	}

	function habilitaCampos() {
		var form = document.vistoriaBemImovelForm;			
		if(form.conInstituicao.value == "") {
			form.conNumeroBemImovel.disabled = true;
			form.conDataInicialVistoria.disabled = true;
			form.conDataFinalVistoria.disabled = true;
			form.conSituacao.disabled = true;
		}
		else{
			form.conNumeroBemImovel.disabled = false;
			form.conDataInicialVistoria.disabled = false;
			form.conDataFinalVistoria.disabled = false;
			form.conSituacao.disabled = false;
			buscaBemImovel();
		}
	}

</script>

<body>

<div id="conteudo">
	<div align="center">
	<h1 >Vistoria de Bem Imóvel </h1>
		
		<html:form action="vistoriaBemImovel.do?action=carregarInterfaceInicial">
		<html:hidden property="codVistoria"/>
		<html:hidden property="tipoAcao"/>
		<html:hidden property="indOperadorOrgao"/>
		<html:hidden property="indGrupoSentinela"/>
		<html:hidden property="adm"/>
		
		
		<div id="conteudo_corpo">
			
		  <table cellspacing="0" class="form_tabela" width="100%">
           <c:if test="${vistoriaBemImovelForm.indGrupoSentinela == vistoriaBemImovelForm.adm}">
				<tr>
					<td class="form_label" align="right">* Instituição:</td>
					<td colspan="2">
						<html:select property="conInstituicao" onchange="habilitaCampos()">
					 	 <html:option value="">-Qualquer-</html:option>
						 <html:options collection="listaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
					    </html:select>
					</td>	
				</tr>
			</c:if>
			<tr>
				<td class="form_label" align="right" width="130">Bem Imóvel:</td>
				<td> 
					<html:text property="conNumeroBemImovel" styleId="conNumeroBemImovel" maxlength="10" size="10" onblur="buscaBemImovel();" onkeyup="DigitaNumero(this);"/>
					<a href="javascript:localizarBemImovel();"><img src="${icon_localizar}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:localizarBemImovel();">Localizar</a>
				</td>
			</tr>
			<tr>
				<td class="form_label" align="right"></td>
				<td> 
					<div id="dadosBemImovel"> 
						<c:if test="${bemImovelSimplificado != null}">
							<tiles:insert definition="viewDadosBemImovelSimplificadoAjaxDef"/>
						</c:if>
					</div>
				</td>
			</tr>
            <tr>
				<td class="form_label">Data da Vistoria:</td>
				<td>
					<html:text property="conDataInicialVistoria" styleId="conDataInicialVistoria" maxlength="10" size="10" onkeypress="javascript:return(MascaraData(this,event));" /> 
					<img src="${icon_calendar}" width="16" height="15" id="btnCalendar" onclick="javascript:Calendario(document.getElementById('conDataInicialVistoria'),this.id);" />
					até 
					<html:text property="conDataFinalVistoria" styleId="conDataFinalVistoria" maxlength="10" size="10"  onkeypress="javascript:return(MascaraData(this,event));" />
					<img src="${icon_calendar}" width="16" height="15" id="btnCalendar" onclick="javascript:Calendario(document.getElementById('conDataFinalVistoria'),this.id);" />
				</td>				
			</tr>
			
			<tr>
				<td class="form_label">Situação:</td>
				<td>
					<html:select property="conSituacao">
						<html:option value=''>- Todas -</html:option>
						<html:options collection="listaSituacaoVistoria" property="id" labelProperty="descricao" />						
					</html:select>
				</td>				
			</tr>
           
          </table>
          
		   	<hr>
           <c:if test="${vistoriaBemImovelForm.indGrupoSentinela == vistoriaBemImovelForm.adm}">
			  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
			</c:if>    
			<div align="center">
			  <html:button property="btnPesquisar" styleClass="form_botao" styleId="btnPesquisar" value="Pesquisar" onclick="pesquisar()"/>
			  <html:button property="btnGerar" styleClass="form_botao" value="Gerar Nova Vistoria" onclick="gerarNovaVistoria();" />	
			  <html:button property="btnVoltar" styleClass="form_botao" value="Voltar" onclick="voltar();" />
		    </div>
    		
		</div>
		</html:form>
		<div id="pesquisaAjax"> 
			<c:if test="${vistoriaBemImovelForm.tipoAcao=='voltar'}"> 
				<tiles:insert page="/pages/vistoriaBemImovel/_con_vistoriaBemImovel.jsp"/>
			</c:if>
		</div>

	</div>
</div>

</body>