<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_prototype" value="/js/generic/prototype.js" />
<c:url var="js_data" value="/js/generic/data.js" />
<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />

<c:url var="js_ajaxutil" value="/js/ajaxutil.js" />
<script language="JavaScript" type="text/JavaScript" src="${js_ajaxutil}"></script>

<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_prototype}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>

<c:url var='icon_calendar' value='/images/icon_calendario.png' />
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
		var form = document.cessaoDeUsoBemImovelForm;
		if (form.conInstituicao.value != '' && form.conInstituicao.value != '0'){
			form.conOrgaoCessionario.disabled=false;
			submitAjax('/abi/cessaoDeUsoBemImovel.do?action=carregarComboOrgaoPesquisa', document.forms[0], 'divComboOrgao',true);
		}else{
			form.conOrgaoCessionario.disabled=true;
			form.conOrgaoCessionario.value="";
		}
	}

	function validarCampos() {
		limpaMensagens();
		var form = document.cessaoDeUsoBemImovelForm;
		var erro = '';
		
		if (document.forms[0].isGpAdmGeralUsuarioLogado.value == 'S'){
			if (form.conInstituicao.value == '' || form.conInstituicao.value == '0'){
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Instituição\".';
			}
		}
		if (form.conSituacao.value == ''){
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Situação\".';
		}
		if(form.uf.value == 0) {
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Estado\".';
		}
		if (form.conDtInicioVigencia.value != '' && form.conDtFimVigencia.value == '' ){
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Data final do período de Vigência\" .';
		}
		if (form.conDtInicioVigencia.value == '' && form.conDtFimVigencia.value != '' ){
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Data inicial do período de Vigência\" .';
		}
		if (form.conDtInicioVigencia.value != '' && !ValidaData(form.conDtInicioVigencia)){
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Data Final de Vigência\" inválida.';
		}
		if (form.conDtFimVigencia.value != '' && !ValidaData(form.conDtFimVigencia)){
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Data Final de Vigência\" inválida.';
		}
		if (form.conDtInicioVigencia.value != '' && form.conDtFimVigencia.value != '' && !DataMenor(form.conDtInicioVigencia.value, form.conDtFimVigencia.value)){
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- Data final do período de Vigência deve ser maior que a data inicial.';
		}
		if(erro != '') {
			msg = "Os seguintes campos são obrigatórios: \n";
			msg = msg + erro;  
			alert(msg);
			return false;
		}

		return true;

	}

	function validaData(campo){
		var form = document.cessaoDeUsoBemImovelForm;
		if (campo.value != '' && !ValidaData(campo)){
			alert("\"Data Final de Vigência\" inválida.");
		}
		campo.focus();
	}

	function pesquisar() {
		if (validarCampos()) {
			document.cessaoDeUsoBemImovelForm.actionType.value = "pesquisar";
			document.cessaoDeUsoBemImovelForm.action="cessaoDeUsoBemImovel.do?action=pesquisarCessaoDeUsoBemImovel";
			document.cessaoDeUsoBemImovelForm.submit();
		}	
	}

	function incluir(){
		document.cessaoDeUsoBemImovelForm.actionType.value = "incluir";
		document.cessaoDeUsoBemImovelForm.action="cessaoDeUsoBemImovel.do?action=carregarPgEditCessaoDeUsoBemImovel";
		document.cessaoDeUsoBemImovelForm.submit();
	}
	
	function voltar() {
		document.cessaoDeUsoBemImovelForm.action='entrada.do';
		document.cessaoDeUsoBemImovelForm.submit();
	}

	function exibir(id) {
		document.cessaoDeUsoBemImovelForm.actionType.value = "exibir";
		document.cessaoDeUsoBemImovelForm.action="cessaoDeUsoBemImovel.do?action=carregarPgViewCessaoDeUsoBemImovel";
	    document.cessaoDeUsoBemImovelForm.codCessaoDeUso.value = id;
	    document.cessaoDeUsoBemImovelForm.submit();
	}
	
	function alterar(id, status) {
		if (status == 1) {
			if(confirm('Confirma a finalização desta Cessão De Uso e geração de uma nova Cessão De Uso com base nestas informações?')) {
				document.cessaoDeUsoBemImovelForm.actionType.value = "gerarNova";
				document.cessaoDeUsoBemImovelForm.action="cessaoDeUsoBemImovel.do?action=carregarPgEditCessaoDeUsoBemImovel";
			    document.cessaoDeUsoBemImovelForm.codCessaoDeUso.value = id;
			} else {
				document.cessaoDeUsoBemImovelForm.actionType.value = "exibir";
				document.cessaoDeUsoBemImovelForm.action="cessaoDeUsoBemImovel.do?action=carregarPgViewCessaoDeUsoBemImovel";
			    document.cessaoDeUsoBemImovelForm.codCessaoDeUso.value = id;
			}
		} else {
			document.cessaoDeUsoBemImovelForm.actionType.value = "alterar";
			document.cessaoDeUsoBemImovelForm.action="cessaoDeUsoBemImovel.do?action=carregarPgEditCessaoDeUsoBemImovel";
		    document.cessaoDeUsoBemImovelForm.codCessaoDeUso.value = id;
		}
	    document.cessaoDeUsoBemImovelForm.submit();
	}
	
	function excluir(id) {
		document.cessaoDeUsoBemImovelForm.actionType.value = "excluir";
		document.cessaoDeUsoBemImovelForm.action="cessaoDeUsoBemImovel.do?action=carregarPgViewCessaoDeUsoBemImovel";
	    document.cessaoDeUsoBemImovelForm.codCessaoDeUso.value = id;
	    document.cessaoDeUsoBemImovelForm.submit();
	}

	function renovar(id) {
		document.cessaoDeUsoBemImovelForm.actionType.value = "renovar";
		document.cessaoDeUsoBemImovelForm.action="cessaoDeUsoBemImovel.do?action=carregarPgViewCessaoDeUsoBemImovel";
	    document.cessaoDeUsoBemImovelForm.codCessaoDeUso.value = id;
	    document.cessaoDeUsoBemImovelForm.submit();
	}

	function revogarDevolver(id) {
		document.cessaoDeUsoBemImovelForm.actionType.value = "revogDev";
		document.cessaoDeUsoBemImovelForm.action="cessaoDeUsoBemImovel.do?action=carregarPgViewCessaoDeUsoBemImovel";
	    document.cessaoDeUsoBemImovelForm.codCessaoDeUso.value = id;
	    document.cessaoDeUsoBemImovelForm.submit();
	}

	function gerarTermo(id) {
		document.cessaoDeUsoBemImovelForm.codCessaoDeUso.value = id;
	    document.cessaoDeUsoBemImovelForm.action="cessaoDeUsoBemImovel.do?action=redirecionarUCSGerarTermo";
	    document.cessaoDeUsoBemImovelForm.submit();
	}

	function buscaBemImovel() {
		var form = document.cessaoDeUsoBemImovelForm;
		if (form.conNrBemImovel.value != '' && form.conInstituicao.value > 0){
			submitAjax('/abi/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.conNrBemImovel.value+'&codInstituicao='+form.conInstituicao.value, document.forms[0], "dadosBemImovel",true);
		} else {
			ajaxHTMLLoad('dadosBemImovel','/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.conNrBemImovel.value+'&codInstituicao='+form.conInstituicao.value,true);
		}
	}

	function localizarBemImovel() {
		var form = document.cessaoDeUsoBemImovelForm;

		if (form.conInstituicao.value == '' || form.conInstituicao.value == '0') {
			alert ("Informe a Instituição!");
			return;
		}

		var camposPesquisaUCOrigem = "";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conNrTermo.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conProtocolo.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conSituacao.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conOrgaoCessionario.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.uf.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.codMunicipio.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conDtInicioVigencia.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conDtFimVigencia.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + "P;";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.actionType.value + ";";

		var strParametros="dialogHeight: 1000px; dialogWidth: 700px; dialogleft: 100px; dialogtop: 100px; Help:no; Status:no; Center:yes;";
		window.showModalDialog ('/abi/localizarBemImovelSimplificado.do?action=carregarInterfaceInicial&camposPesquisaUCOrigem='+camposPesquisaUCOrigem+'&instituicao='+form.conInstituicao.value+'&actionUCOrigem=cessaoDeUsoBemImovel',strParametros);
	}

	function recarregarDados() {
		var form = document.cessaoDeUsoBemImovelForm;
		if (form.conNrBemImovel.value != '' && form.conInstituicao.value > 0){
			buscaBemImovel();
		}
	}

</script>

<body onload="recarregarDados();">

<div id="conteudo">
	<div align="center">
	<h1> Cessão De Uso de Bem Imóvel </h1>
		
	<div id="conteudo_corpo">
		<html:form action="cessaoDeUsoBemImovel.do?action=carregarPgListCessaoDeUsoBemImovel">
			<html:hidden property="isGpAdmGeralUsuarioLogado" value="${cessaoDeUsoBemImovelForm.isGpAdmGeralUsuarioLogado}"/>
			<html:hidden property="codCessaoDeUso"/>
			<html:hidden property="actionType"/>
			<html:hidden property="pesqExec"/>
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
								<td class="form_label" align="right" width="210">Nº do Termo:</td>
								<td> 
									<html:text property="conNrTermo" styleId="conNrTermo" maxlength="10" size="10" onkeyup="DigitaNumero(this);"/>
								</td>
							</tr>
							<c:if test="${cessaoDeUsoBemImovelForm.isGpAdmGeralUsuarioLogado == 'S'}">
								<tr>
									<td class="form_label" align="right">* Instituição:</td>
									<td colspan="2">
										<html:select property="conInstituicao" onchange="javascript:buscarOrgao();">
										 	<html:options collection="listaPesquisaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
									    </html:select>
									</td>	
								</tr>
							</c:if>
							<c:if test="${cessaoDeUsoBemImovelForm.isGpAdmGeralUsuarioLogado != 'S'}">
								<html:hidden property="conInstituicao" value="${cessaoDeUsoBemImovelForm.conInstituicao}"/>
							</c:if>
							<tr>
								<td class="form_label" align="right">Bem Imóvel:</td>
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
									<html:text property="conProtocolo" styleId="conProtocolo" maxlength="30" size="30" onkeyup="DigitaNumero(this);" onblur="DigitaNumero(this);"/>
								</td>
							</tr>
										
							<tr>
								<td class="form_label">Situação:</td>
								<td>
									<html:select property="conSituacao">
										<html:option value=" ">- Todas -</html:option>
										<html:options collection="listaSituacaoPesquisa" property="codigo" labelProperty="descricao" />						
									</html:select>
								</td>				
							</tr>
							<tr>
							 	<td class="form_label">Órgão Cessionário: </td>
							 	<td>
									<div id="divComboOrgao">
										<jsp:include page="/pages/cessaoDeUsoBemImovel/_con_comboOrgaoPesquisa.jsp"/>
									</div>
								</td>
							</tr>
				
							<tr>
								<td class="form_label">* Estado:</td>
								<td><cep:uf name="uf" value="${cessaoDeUsoBemImovelForm.uf}" /><cep:box/> </td>
							</tr>
							<tr>
								<td class="form_label">Município:</td>
								<td>
									<cep:municipio name="codMunicipio" value="${cessaoDeUsoBemImovelForm.codMunicipio}" onchange="capturarDesMunicipio(this)"/>
								</td>
							</tr>
							<tr> 
					          	<td class="form_label">Data Final de Vigência entre: </td>
					          	<td>
									<html:text styleId="conDtInicioVigencia" property="conDtInicioVigencia"  size="10" maxlength="10" onkeyup="javascript:return(MascaraData(this,event));" onblur="javascript:validaData(this);"/>
									<img src="${icon_calendar}" width="16" id="dtInicio" height="15" onclick="javascript:Calendario(document.getElementById('conDtInicioVigencia'),this.id);" />
									&nbsp;&nbsp;&nbsp; e  &nbsp;&nbsp;&nbsp; 
									<html:text styleId="conDtFimVigencia" property="conDtFimVigencia"  size="10" maxlength="10" onkeyup="javascript:return(MascaraData(this,event));" onblur="javascript:validaData(this);"/>
									<img src="${icon_calendar}" width="16" id="dtFim" height="15" onclick="javascript:Calendario(document.getElementById('conDtFimVigencia'),this.id);" />
								</td>
						   	</tr>
				          </table>
		          
						   	<hr>
						  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
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
					<c:url var="link_parametros1" 	value="&conNrTermo=${cessaoDeUsoBemImovelForm.conNrTermo}&conNrBemImovel=${cessaoDeUsoBemImovelForm.conNrBemImovel}" />
					<c:url var="link_parametros2" 	value="&conProtocolo=${cessaoDeUsoBemImovelForm.conProtocolo}&conSituacao=${cessaoDeUsoBemImovelForm.conSituacao}" />
					<c:url var="link_parametros3" 	value="&conOrgaoCessionario=${cessaoDeUsoBemImovelForm.conOrgaoCessionario}&pesqExec=${cessaoDeUsoBemImovelForm.pesqExec}"/>
					<c:url var="link_parametros4" 	value="&uf=${cessaoDeUsoBemImovelForm.uf}&codMunicipio=${cessaoDeUsoBemImovelForm.codMunicipio}" />
					<c:url var="link_parametros5" 	value="&conDtInicioVigencia=${cessaoDeUsoBemImovelForm.conDtInicioVigencia}&conDtFimVigencia=${cessaoDeUsoBemImovelForm.conDtFimVigencia}" />
					<c:url var="link_parametros6" 	value="&conInstituicao=${transferenciaBemImovelForm.conInstituicao}" /> 
					<c:url var="link_pesquisar"		value="/cessaoDeUsoBemImovel.do?action=pesquisarCessaoDeUsoBemImovel${link_parametros1}${link_parametros2}${link_parametros3}${link_parametros4}${link_parametros5}${link_parametros6}" />		

					<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
						<ch:lista bean="${pagina}" atributoId="codCessaoDeUso" />
						<ch:action imagem="${icon_exibir}" link="javascript:exibir(%1);" label="Exibir" width="3%" align="center" />
						<ch:campo atributo="numeroTermo" label="N.º do Termo" align="left"/>
						<ch:campo atributo="bemImovel.nrBemImovel" label="Bem Imóvel" align="left"/>
						<ch:campo atributo="protocolo" label="Protocolo" align="left"/>
						<ch:campo atributo="bemImovel.municipioEstado" label="Município / UF" align="left"/>
						<ch:campo atributo="orgaoCedente.siglaDescricao" label="Órgão Cedente" align="left"/>
						<ch:campo atributo="orgaoCessionario.siglaDescricao" label="Órgão Cessionário" align="left"/>
						<ch:campo atributo="dataInicioVigencia" label="Data da Cessão De Uso" align="left" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" />
						<ch:campo atributo="statusTermo.descricao" label="Situação" align="left" />		
						<ch:campo atributo="instanciaAtual" label="Alterar" align="center" width="3%" decorator="gov.pr.celepar.abi.util.BotaoAlterarCessaoDeUsoDecorator" />
						<ch:campo atributo="instanciaAtual" label="Excluir" align="center" width="3%" decorator="gov.pr.celepar.abi.util.BotaoExcluirCessaoDeUsoDecorator" />
						<ch:campo atributo="instanciaAtual" label="Revogar Devolver" align="center" width="3%" decorator="gov.pr.celepar.abi.util.BotaoRevogarDevolverCessaoDeUsoDecorator" />
						<ch:campo atributo="instanciaAtual" label="Renovar" align="center" width="3%" decorator="gov.pr.celepar.abi.util.BotaoRenovarCessaoDeUsoDecorator" />
					    <ch:action imagem="${icon_gerarTermo}"  link="javascript:gerarTermo(%1);" label="Gerar Termo" width="3%" align="center" />
						<ch:painel pagina="${link_pesquisar}" classe="painel" atributoIndice="indice" />
					</ch:table>			  
				</div>
			</c:if>
		</div>
	</div>
</div>
   
</body>
