<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_prototype" value="/js/generic/prototype.js" />

<c:url var="js_ajaxutil" value="/js/ajaxutil.js" />
<script language="JavaScript" type="text/JavaScript" src="${js_ajaxutil}"></script>

<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_prototype}"></script>

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
		var form = document.doacaoBemImovelForm;
		if(document.getElementById("administracaoDireta2").checked || document.getElementById("administracaoDireta3").checked){
			if (form.conInstituicao.value != '' && form.conInstituicao.value != '0'){
				form.conOrgaoResponsavel.disabled=false;
				submitAjax('/abi/doacaoBemImovel.do?action=carregarComboOrgaoPesquisa', document.forms[0], 'divComboOrgao',true);
			}else{
				form.conOrgaoResponsavel.disabled=true;
				form.conOrgaoResponsavel.value="";
			}
		}else{
			form.conOrgaoResponsavel.disabled=true;
			form.conOrgaoResponsavel.value="";
		}
	}
	
	function validarCampos() {
		limpaMensagens();
		var form = document.doacaoBemImovelForm;
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
		if(!document.getElementById("administracaoDireta2").checked && !document.getElementById("administracaoDireta3").checked
			 && !document.getElementById("administracaoDireta4").checked){
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Administração\".';
		}
		if(form.uf.value == 0) {
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Estado\".';
		}
		
		if(erro != '') {
			msg = "Os seguintes campos são obrigatórios: \n";
			msg = msg + erro;  
			alert(msg);
			return false;
		}

		return true;

	}

	function pesquisar() {
		if (validarCampos()) {
			document.doacaoBemImovelForm.actionType.value = "pesquisar";
			document.doacaoBemImovelForm.action="doacaoBemImovel.do?action=pesquisarDoacaoBemImovel";
			document.doacaoBemImovelForm.submit();
		}	
	}

	function incluir(){
		document.doacaoBemImovelForm.actionType.value = "incluir";
		document.doacaoBemImovelForm.action="doacaoBemImovel.do?action=carregarPgEditDoacaoBemImovel";
		document.doacaoBemImovelForm.submit();
	}
	
	function voltar() {
		document.doacaoBemImovelForm.action='entrada.do';
		document.doacaoBemImovelForm.submit();
	}

	function exibir(id) {
		document.doacaoBemImovelForm.actionType.value = "exibir";
		document.doacaoBemImovelForm.action="doacaoBemImovel.do?action=carregarPgViewDoacaoBemImovel";
	    document.doacaoBemImovelForm.codDoacao.value = id;
	    document.doacaoBemImovelForm.submit();
	}
	
	function alterar(id, status) {
		if (status == 1) {
			if(confirm('Confirma a finalização desta Doação e geração de uma nova Doação com base nestas informações?')) {
				document.doacaoBemImovelForm.actionType.value = "gerarNova";
				document.doacaoBemImovelForm.action="doacaoBemImovel.do?action=carregarPgEditDoacaoBemImovel";
			    document.doacaoBemImovelForm.codDoacao.value = id;
			} else {
				document.doacaoBemImovelForm.actionType.value = "exibir";
				document.doacaoBemImovelForm.action="doacaoBemImovel.do?action=carregarPgViewDoacaoBemImovel";
			    document.doacaoBemImovelForm.codDoacao.value = id;
			}
		} else {
			document.doacaoBemImovelForm.actionType.value = "alterar";
			document.doacaoBemImovelForm.action="doacaoBemImovel.do?action=carregarPgEditDoacaoBemImovel";
		    document.doacaoBemImovelForm.codDoacao.value = id;
		}
	    document.doacaoBemImovelForm.submit();
	}
	
	function excluir(id) {
		document.doacaoBemImovelForm.actionType.value = "excluir";
		document.doacaoBemImovelForm.action="doacaoBemImovel.do?action=carregarPgViewDoacaoBemImovel";
	    document.doacaoBemImovelForm.codDoacao.value = id;
	    document.doacaoBemImovelForm.submit();
	}

	function revogarDevolver(id) {
		document.doacaoBemImovelForm.actionType.value = "revogDev";
		document.doacaoBemImovelForm.action="doacaoBemImovel.do?action=carregarPgViewDoacaoBemImovel";
	    document.doacaoBemImovelForm.codDoacao.value = id;
	    document.doacaoBemImovelForm.submit();
	}

	function gerarTermo(id) {
		document.doacaoBemImovelForm.codDoacao.value = id;
	    document.doacaoBemImovelForm.action="doacaoBemImovel.do?action=redirecionarUCSGerarTermo";
	    document.doacaoBemImovelForm.submit();
	}

	function buscaBemImovel() {
		var form = document.doacaoBemImovelForm;
		if (form.conNrBemImovel.value != '' && form.conInstituicao.value > 0){
			submitAjax('/abi/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.conNrBemImovel.value+'&codInstituicao='+form.conInstituicao.value, document.forms[0], "dadosBemImovel",true);
		} else {
			ajaxHTMLLoad('dadosBemImovel','/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.conNrBemImovel.value+'&codInstituicao='+form.conInstituicao.value,true);
		}
	}

	function localizarBemImovel() {
		var form = document.doacaoBemImovelForm;

		if (form.conInstituicao.value == '' || form.conInstituicao.value == '0') {
			alert ("Informe a Instituição!");
			return;
		}

		var camposPesquisaUCOrigem = "";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conNrTermo.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conProtocolo.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conSituacao.value + ";";

		if(document.getElementById("administracaoDireta2").checked){
			camposPesquisaUCOrigem = camposPesquisaUCOrigem + "2;";
		}
		if(document.getElementById("administracaoDireta3").checked){
			camposPesquisaUCOrigem = camposPesquisaUCOrigem + "3;";
		}
		if(document.getElementById("administracaoDireta4").checked){
			camposPesquisaUCOrigem = camposPesquisaUCOrigem + ";";
		}

		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conOrgaoResponsavel.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.uf.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.codMunicipio.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + "P;";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.actionType.value + ";";

		var strParametros="dialogHeight: 1000px; dialogWidth: 700px; dialogleft: 100px; dialogtop: 100px; Help:no; Status:no; Center:yes;";
		window.showModalDialog ('/abi/localizarBemImovelSimplificado.do?action=carregarInterfaceInicial&camposPesquisaUCOrigem='+camposPesquisaUCOrigem+'&instituicao='+form.conInstituicao.value+'&actionUCOrigem=doacaoBemImovel',strParametros);
	}

	function recarregarDados() {
		var form = document.doacaoBemImovelForm;
		if (form.conNrBemImovel.value != '' && form.conInstituicao.value > 0){
			buscaBemImovel();
		}
		if(document.getElementById("administracaoDireta2").checked || document.getElementById("administracaoDireta3").checked){
			form.conOrgaoResponsavel.disabled=false;
		} else {
			form.conOrgaoResponsavel.disabled=true;
			form.conOrgaoResponsavel.value="";
		}
	}

</script>

<body onload="recarregarDados();">

<div id="conteudo">
	<div align="center">
	<h1 >Doação de Bem Imóvel </h1>
		
	<div id="conteudo_corpo">
		<html:form action="doacaoBemImovel.do?action=carregarPgListDoacaoBemImovel">
			<html:hidden property="isGpAdmGeralUsuarioLogado" value="${doacaoBemImovelForm.isGpAdmGeralUsuarioLogado}"/>
			<html:hidden property="codDoacao"/>
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
								<td class="form_label" align="right" width="130">Nº do Termo:</td>
								<td> 
									<html:text property="conNrTermo" styleId="conNrTermo" maxlength="10" size="10" onkeyup="DigitaNumero(this);"/>
								</td>
							</tr>
				           
							<c:if test="${doacaoBemImovelForm.isGpAdmGeralUsuarioLogado == 'S'}">
								<tr>
									<td class="form_label" align="right">* Instituição:</td>
									<td colspan="2">
										<html:select property="conInstituicao" onchange="javascript:buscarOrgao();">
										 	<html:options collection="listaPesquisaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
									    </html:select>
									</td>	
								</tr>
							</c:if>
							<c:if test="${doacaoBemImovelForm.isGpAdmGeralUsuarioLogado != 'S'}">
								<html:hidden property="conInstituicao" value="${doacaoBemImovelForm.conInstituicao}"/>
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
									<html:text property="conProtocolo" styleId="conProtocolo" maxlength="15" size="15" onkeyup="DigitaNumero(this);" onblur="DigitaNumero(this);"/>
								</td>
							</tr>
										
							<tr>
								<td class="form_label">* Situação:</td>
								<td>
									<html:select property="conSituacao">
										<html:option value=" ">- Todas -</html:option>
										<html:options collection="listaSituacaoPesquisa" property="codigo" labelProperty="descricao" />						
									</html:select>
								</td>				
							</tr>
				           
						 	<tr>
						 		<td class="form_label">* Administração: </td>
						 		<td>
									<html:radio value="2" property="conAdministracao" styleId ="administracaoDireta2" onchange="javascript:buscarOrgao();">Indireta</html:radio>
									<html:radio value="3" property="conAdministracao" styleId ="administracaoDireta3" onchange="javascript:buscarOrgao();">Terceiros</html:radio>
									<html:radio value="" property="conAdministracao" styleId ="administracaoDireta4" onchange="javascript:buscarOrgao();">Todos</html:radio>
								</td>
							</tr>
							<tr>
							 	<td class="form_label">Órgão Responsável: </td>
							 	<td>
									<div id="divComboOrgao">
										<jsp:include page="/pages/doacaoBemImovel/_con_comboOrgaoPesquisa.jsp"/>
									</div>
								</td>
							</tr>
				
							<tr>
								<td class="form_label">* Estado:</td>
								<td><cep:uf name="uf" value="${doacaoBemImovelForm.uf}" /><cep:box/> </td>
							</tr>
							<tr>
								<td class="form_label">Município:</td>
								<td>
									<cep:municipio name="codMunicipio" value="${doacaoBemImovelForm.codMunicipio}" onchange="capturarDesMunicipio(this)"/>
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
					<c:url var="link_parametros1" 	value="&conNrTermo=${doacaoBemImovelForm.conNrTermo}&conNrBemImovel=${doacaoBemImovelForm.conNrBemImovel}" />
					<c:url var="link_parametros2" 	value="&conProtocolo=${doacaoBemImovelForm.conProtocolo}&conSituacao=${doacaoBemImovelForm.conSituacao}" />
					<c:url var="link_parametros3" 	value="&conAdministracao=${doacaoBemImovelForm.conAdministracao}&conOrgaoResponsavel=${doacaoBemImovelForm.conOrgaoResponsavel}"/>
					<c:url var="link_parametros4" 	value="&uf=${doacaoBemImovelForm.uf}&codMunicipio=${doacaoBemImovelForm.codMunicipio}&pesqExec=${doacaoBemImovelForm.pesqExec}" /> 
					<c:url var="link_parametros5" 	value="&conInstituicao=${doacaoBemImovelForm.conInstituicao}" /> 
					<c:url var="link_pesquisar"		value="/doacaoBemImovel.do?action=pesquisarDoacaoBemImovel${link_parametros1}${link_parametros2}${link_parametros3}${link_parametros4}${link_parametros5}" />		
			
					<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
						<ch:lista bean="${pagina}" atributoId="codDoacao" />
						<ch:action imagem="${icon_exibir}" link="javascript:exibir(%1);" label="Exibir" width="3%" align="center" />
						<ch:campo atributo="numeroTermo" label="N.º do Termo" align="left"/>
						<ch:campo atributo="bemImovel.nrBemImovel" label="Bem Imóvel" align="left"/>
						<ch:campo atributo="protocolo" label="Protocolo" align="left"/>
						<ch:campo atributo="bemImovel.municipioEstado" label="Município / UF" align="left"/>
						<ch:campo atributo="orgaoProprietario.siglaDescricao" label="Órgão Proprietário" align="left"/>
						<ch:campo atributo="orgaoResponsavel.siglaDescricao" label="Órgão Responsável" align="left"/>
						<ch:campo atributo="dtInicioVigencia" label="Data da Doação" align="left" decorator="gov.pr.celepar.framework.taglib.grid.decorator.Data" />
						<ch:campo atributo="statusTermo.descricao" label="Situação" align="left" />		
						<ch:campo atributo="instanciaAtual" label="Alterar" align="center" width="3%" decorator="gov.pr.celepar.abi.util.BotaoAlterarDoacaoDecorator" />
						<ch:campo atributo="instanciaAtual" label="Excluir" align="center" width="3%" decorator="gov.pr.celepar.abi.util.BotaoExcluirDoacaoDecorator" />
						<ch:campo atributo="instanciaAtual" label="Revogar/Devolver" align="center" width="3%" decorator="gov.pr.celepar.abi.util.BotaoRevogarDevolverDoacaoDecorator" />
					    <ch:action imagem="${icon_gerarTermo}"  link="javascript:gerarTermo(%1);" label="Gerar Termo" width="3%" align="center" />
						<ch:painel pagina="${link_pesquisar}" classe="painel" atributoIndice="indice" />
					</ch:table>			  
				</div>
			</c:if>
		</div>
	</div>
</div>

</body>
