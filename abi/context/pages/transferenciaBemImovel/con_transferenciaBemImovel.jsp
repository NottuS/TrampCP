<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_prototype" value="/js/generic/prototype.js" />

<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_prototype}"></script>

<c:url var="js_ajaxutil" value="/js/ajaxutil.js" />
<script language="JavaScript" type="text/JavaScript" src="${js_ajaxutil}"></script>

<c:url var='icon_localizar' value='/images/icon_localizar.png' />

<script language="javascript">
	function limpaMensagens() {
		if(document.getElementById("__msgVerificador")!=null)
		 	document.getElementById("__msgVerificador").innerHTML = "";
		if(document.getElementById("msg")!=null) 
			document.getElementById("msg").innerHTML = "";
		if(document.getElementById("msgs_servidor")!=null)
			document.getElementById("msgs_servidor").innerHTML = "";
	}

	function buscarOrgao(){
		var form = document.transferenciaBemImovelForm;
		if (form.conInstituicao.value != '' && form.conInstituicao.value != '0'){
			form.conOrgaoCessionario.disabled=false;
			submitAjax('/abi/transferenciaBemImovel.do?action=carregarComboOrgaoPesquisa', document.forms[0], 'divComboOrgao',true);
		}else{
			form.conOrgaoCessionario.disabled=true;
			form.conOrgaoCessionario.value="";
		}
	}

	function validarCampos() {
		limpaMensagens();
		var form = document.transferenciaBemImovelForm;
		var erro = '';

		if (document.forms[0].isGpAdmGeralUsuarioLogado.value == 'S'){
			if (form.conInstituicao.value == '' || form.conInstituicao.value == '0'){
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Institui��o\".';
			}
		}
		if (form.conSituacao.value == ''){
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Situa��o\".';
		}
		if(form.uf.value == 0) {
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Estado\".';
		}
		
		if(erro != '') {
			msg = "Os seguintes campos s�o obrigat�rios: \n";
			msg = msg + erro;  
			alert(msg);
			return false;
		}

		return true;

	}

	function pesquisar() {
		if (validarCampos()) {
			document.transferenciaBemImovelForm.actionType.value = "pesquisar";
			document.transferenciaBemImovelForm.action="transferenciaBemImovel.do?action=pesquisarTransferenciaBemImovel";
			document.transferenciaBemImovelForm.submit();
		}	
	}

	function incluir(){
		document.transferenciaBemImovelForm.actionType.value = "incluir";
		document.transferenciaBemImovelForm.action="transferenciaBemImovel.do?action=carregarPgEditTransferenciaBemImovel";
		document.transferenciaBemImovelForm.submit();
	}
	
	function voltar() {
		document.transferenciaBemImovelForm.action='entrada.do';
		document.transferenciaBemImovelForm.submit();
	}

	function exibir(id) {
		document.transferenciaBemImovelForm.actionType.value = "exibir";
		document.transferenciaBemImovelForm.action="transferenciaBemImovel.do?action=carregarPgViewTransferenciaBemImovel";
	    document.transferenciaBemImovelForm.codTransferencia.value = id;
	    document.transferenciaBemImovelForm.submit();
	}
	
	function alterar(id, status) {
		if (status == 1) {
			if(confirm('Confirma a finaliza��o desta Transfer�ncia e gera��o de uma nova Transfer�ncia com base nestas informa��es?')) {
				document.transferenciaBemImovelForm.actionType.value = "gerarNova";
				document.transferenciaBemImovelForm.action="transferenciaBemImovel.do?action=carregarPgEditTransferenciaBemImovel";
			    document.transferenciaBemImovelForm.codTransferencia.value = id;
			} else {
				document.transferenciaBemImovelForm.actionType.value = "exibir";
				document.transferenciaBemImovelForm.action="transferenciaBemImovel.do?action=carregarPgViewTransferenciaBemImovel";
			    document.transferenciaBemImovelForm.codTransferencia.value = id;
			}
		} else {
			document.transferenciaBemImovelForm.actionType.value = "alterar";
			document.transferenciaBemImovelForm.action="transferenciaBemImovel.do?action=carregarPgEditTransferenciaBemImovel";
		    document.transferenciaBemImovelForm.codTransferencia.value = id;
		}
	    document.transferenciaBemImovelForm.submit();
	}
	
	function excluir(id) {
		document.transferenciaBemImovelForm.actionType.value = "excluir";
		document.transferenciaBemImovelForm.action="transferenciaBemImovel.do?action=carregarPgViewTransferenciaBemImovel";
	    document.transferenciaBemImovelForm.codTransferencia.value = id;
	    document.transferenciaBemImovelForm.submit();
	}

	function revogarDevolver(id) {
		document.transferenciaBemImovelForm.actionType.value = "revogDev";
		document.transferenciaBemImovelForm.action="transferenciaBemImovel.do?action=carregarPgViewTransferenciaBemImovel";
	    document.transferenciaBemImovelForm.codTransferencia.value = id;
	    document.transferenciaBemImovelForm.submit();
	}

	function gerarTermo(id) {
		document.transferenciaBemImovelForm.codTransferencia.value = id;
	    document.transferenciaBemImovelForm.action="transferenciaBemImovel.do?action=redirecionarUCSGerarTermo";
	    document.transferenciaBemImovelForm.submit();
	}

	function buscaBemImovel() {
		var form = document.transferenciaBemImovelForm;
		if (form.conNrBemImovel.value != '' && form.conInstituicao.value > 0){
			submitAjax('/abi/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.conNrBemImovel.value+'&codInstituicao='+form.conInstituicao.value, document.forms[0], "dadosBemImovel",true);
		} else {
			ajaxHTMLLoad('dadosBemImovel','/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.conNrBemImovel.value+'&codInstituicao='+form.conInstituicao.value,true);
		}
	}

	function localizarBemImovel() {
		var form = document.transferenciaBemImovelForm;

		if (form.conInstituicao.value == '' || form.conInstituicao.value == '0') {
			alert ("Informe a Institui��o!");
			return;
		}

		var camposPesquisaUCOrigem = "";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conNrTermo.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conProtocolo.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conSituacao.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conOrgaoCessionario.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.uf.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.codMunicipio.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + "P;";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.actionType.value + ";";

		var strParametros="dialogHeight: 1000px; dialogWidth: 700px; dialogleft: 100px; dialogtop: 100px; Help:no; Status:no; Center:yes;";
		window.showModalDialog ('/abi/localizarBemImovelSimplificado.do?action=carregarInterfaceInicial&camposPesquisaUCOrigem='+camposPesquisaUCOrigem+'&instituicao='+form.conInstituicao.value+'&actionUCOrigem=transferenciaBemImovel',strParametros);

	}

	function recarregarDados() {
		var form = document.transferenciaBemImovelForm;
		if (form.conNrBemImovel.value != '' && form.conInstituicao.value > 0){
			buscaBemImovel();
		}
	}

</script>

<body onload="recarregarDados();">

<div id="conteudo">
	<div align="center">
	<h1> Transfer�ncia de Uso de Bem Im�vel </h1>
		
	<div id="conteudo_corpo">
		<html:form action="transferenciaBemImovel.do?action=carregarPgListTransferenciaBemImovel">
			<html:hidden property="isGpAdmGeralUsuarioLogado" value="${transferenciaBemImovelForm.isGpAdmGeralUsuarioLogado}"/>
			<html:hidden property="codTransferencia"/>
			<html:hidden property="actionType"/>
			<html:hidden property="pesqExec"/>
			<html:hidden property="indOperadorOrgao"/>
			<html:hidden property="conCodBemImovel"/>

		    <cep:main>
				<cep:form findOnType="false" codificacao="C" textoBusca="procurando...">
						<div style="display:none">
							<cep:cep name="cep" value="" readonly="true"/>
							<cep:endereco name="endereco" value=""/>
							<cep:bairro name="bairro" value=""/>
						</div>
				
						  <table cellspacing="0" class="form_tabela" width="100%">
				           
							<tr>
								<td class="form_label" align="right" width="130">N� do Termo:</td>
								<td> 
									<html:text property="conNrTermo" styleId="conNrTermo" maxlength="10" size="10" onkeyup="DigitaNumero(this);"/>
								</td>
							</tr>
										
				           
							<c:if test="${transferenciaBemImovelForm.isGpAdmGeralUsuarioLogado == 'S'}">
								<tr>
									<td class="form_label" align="right">* Institui��o:</td>
									<td colspan="2">
										<html:select property="conInstituicao" onchange="javascript:buscarOrgao();">
										 	<html:options collection="listaPesquisaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
									    </html:select>
									</td>	
								</tr>
							</c:if>
							<c:if test="${transferenciaBemImovelForm.isGpAdmGeralUsuarioLogado != 'S'}">
								<html:hidden property="conInstituicao" value="${transferenciaBemImovelForm.conInstituicao}"/>
							</c:if>

							<tr>
								<td class="form_label" align="right">Bem Im�vel:</td>
								<td> 
									<html:text property="conNrBemImovel" styleId="conNrBemImovel" maxlength="10" size="10" onblur="buscaBemImovel();" onkeyup="DigitaNumero(this);"/>
									<a href="javascript:localizarBemImovel();"><img src="${icon_localizar}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:localizarBemImovel();">Localizar</a>
								</td>
							</tr>
							<tr>
								<td class="form_label" align="right"></td>
								<td> 
									<div id="dadosBemImovel"> 
										<c:if test="${bemImovelSimplificado != null}">
											<jsp:include page="/pages/bemImovel/_view_bemImovelSimplificado.jsp"></jsp:include>
										</c:if>
									</div>
								</td>
							</tr>
							<tr>
								<td class="form_label" align="right">Protocolo:</td>
								<td> 
									<html:text property="conProtocolo" styleId="conProtocolo" maxlength="15" size="15" onkeyup="DigitaNumero(this);" onblur="DigitaNumero(this);"/>
								</td>
							</tr>
										
							<tr>
								<td class="form_label">* Situa��o:</td>
								<td>
									<html:select property="conSituacao">
										<html:option value=" ">- Todas -</html:option>
										<html:options collection="listaSituacaoPesquisa" property="codigo" labelProperty="descricao" />						
									</html:select>
								</td>				
							</tr>
				           
							<tr>
							 	<td class="form_label">�rg�o de Destino: </td>
							 	<td>
									<div id="divComboOrgao">
										<jsp:include page="/pages/transferenciaBemImovel/_con_comboOrgaoPesquisa.jsp"/>
									</div>
								</td>
							</tr>
				
							<tr>
								<td class="form_label">* Estado:</td>
								<td><cep:uf name="uf" value="${transferenciaBemImovelForm.uf}" /><cep:box/> </td>
							</tr>
							<tr>
								<td class="form_label">Munic�pio:</td>
								<td>
									<cep:municipio name="codMunicipio" value="${transferenciaBemImovelForm.codMunicipio}" onchange="capturarDesMunicipio(this)"/>
								</td>
							</tr>
	
				          </table>
		          
						   	<hr>
						  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigat�rio.</p>
							<div align="center">
							  <html:button property="" styleClass="form_botao" styleId="btnPesquisar" value="Pesquisar" onclick="pesquisar()"/>	
							  <html:button property="" styleClass="form_botao" value="Incluir" onclick="incluir();" />
							  <html:button property="" styleClass="form_botao" value="Voltar" onclick="voltar();" />
						    </div>
		    		
					</cep:form>
			    </cep:main>
			</html:form>
		</div>
		<div id="pesquisaAjax"> 
			<c:if test="${!empty pagina && pagina.totalRegistros > 0}">
				<div id="conteudo_corpo">    
					<c:url var="icon_exibir" 		value="/images/icon_exibir.png"/>
					<c:url var="icon_gerarTermo" 	value="/images/icon_print.png"/>
					<c:url var="link_parametros1" 	value="&conNrTermo=${transferenciaBemImovelForm.conNrTermo}&conNrBemImovel=${transferenciaBemImovelForm.conNrBemImovel}" />
					<c:url var="link_parametros2" 	value="&conProtocolo=${transferenciaBemImovelForm.conProtocolo}&conSituacao=${transferenciaBemImovelForm.conSituacao}" />
					<c:url var="link_parametros3" 	value="&conOrgaoCessionario=${transferenciaBemImovelForm.conOrgaoCessionario}&pesqExec=${transferenciaBemImovelForm.pesqExec}"/>
					<c:url var="link_parametros4" 	value="&uf=${transferenciaBemImovelForm.uf}&codMunicipio=${transferenciaBemImovelForm.codMunicipio}" />
					<c:url var="link_parametros5" 	value="&conInstituicao=${transferenciaBemImovelForm.conInstituicao}" /> 
					<c:url var="link_pesquisar"		value="/transferenciaBemImovel.do?action=pesquisarTransferenciaBemImovel${link_parametros1}${link_parametros2}${link_parametros3}${link_parametros4}${link_parametros5}" />		
			
					<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
						<ch:lista bean="${pagina}" atributoId="codTransferencia" />
						<ch:action imagem="${icon_exibir}" link="javascript:exibir(%1);" label="Exibir" width="3%" align="center" />
						<ch:campo atributo="numeroTermo" label="N.� do Termo" align="left"/>
						<ch:campo atributo="bemImovel.nrBemImovel" label="Bem Im�vel" align="left"/>
						<ch:campo atributo="protocolo" label="Protocolo" align="left"/>
						<ch:campo atributo="bemImovel.municipioEstado" label="Munic�pio / UF" align="left"/>
						<ch:campo atributo="orgaoCedente.siglaDescricao" label="�rg�o Cedente" align="left"/>
						<ch:campo atributo="orgaoCessionario.siglaDescricao" label="�rg�o de Destino" align="left"/>
						<ch:campo atributo="dtInicioVigencia" label="Data da Transfer�ncia" align="left" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" />
						<ch:campo atributo="statusTermo.descricao" label="Situa��o" align="left" />		
						<ch:campo atributo="instanciaAtual" label="Alterar" align="center" width="3%" decorator="gov.pr.celepar.abi.util.BotaoAlterarTransferenciaDecorator" />
						<ch:campo atributo="instanciaAtual" label="Excluir" align="center" width="3%" decorator="gov.pr.celepar.abi.util.BotaoExcluirTransferenciaDecorator" />
						<ch:campo atributo="instanciaAtual" label="Revogar/Devolver" align="center" width="3%" decorator="gov.pr.celepar.abi.util.BotaoRevogarDevolverTransferenciaDecorator" />
					    <ch:action imagem="${icon_gerarTermo}"  link="javascript:gerarTermo(%1);" label="Gerar Termo" width="3%" align="center" />
						<ch:painel pagina="${link_pesquisar}" classe="painel" atributoIndice="indice" />
					</ch:table>			  
				</div>
			</c:if>
		</div>
	</div>
</div>
   
</body>
