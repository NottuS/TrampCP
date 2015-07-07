<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_ajaxutil" value="/js/ajaxutil.js" />
<c:url var="js_prototype" value="/js/generic/prototype.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_data" value="/js/generic/data.js" />
<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />

<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_ajaxutil}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_prototype}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>

<c:url var='icon_calendar' value='/images/icon_calendario.png' />
<c:url var='icon_adicionar' value='/images/icon_adicionar1.png'/>
<c:url var='icon_excluir' value='/images/icon_excluir.png'/>
<c:url var='icon_localizar' value='/images/icon_localizar.png' />
<c:url var='icon_limpar' value='/images/icon_apagar.png' />

<script language="javascript">
	function limpaMensagens() {
		if(document.getElementById("__msgVerificador")!=null)
		 	document.getElementById("__msgVerificador").innerHTML = "";
		if(document.getElementById("msg")!=null) 
			document.getElementById("msg").innerHTML = "";
		if(document.getElementById("msgs_servidor")!=null)
			document.getElementById("msgs_s").innerHTML = "";
	}

	function carregarComboOrgao(){
		var form = document.transferenciaBemImovelForm;
		if (form.instituicao.value != '' && form.instituicao.value != '0'){
			form.orgaoCessionario.disabled=false;
			submitAjax('/abi/transferenciaBemImovel.do?action=carregarComboOrgao&instituicao='+form.instituicao.value, document.forms[0], 'divComboOrgao',true);
		}else{
			form.orgaoCessionario.disabled=true;
			form.orgaoCessionario.value="";
		}
	}

	function habilitaCampos() {
		var form = document.transferenciaBemImovelForm;
		document.getElementById("localizarBI").style.display="none";
		document.getElementById("addAssinatura").style.display="block";
		form.nrBemImovel.disabled=true;
		if (document.forms[0].isGpAdmGeralUsuarioLogado.value == 'S' && document.forms[0].actionType.value == "incluir"){
			form.instituicao.disabled=true;
		}
		form.orgaoCessionario.disabled=false;
		form.dtInicioVigencia.disabled=false;
		form.dtFimVigencia.disabled=false;
		form.protocolo.disabled=false;
		form.orgaoAssinatura.disabled=false;
		form.cargoAssinatura.disabled=false;
		form.nomeAssinatura.disabled=false;
		form.ordemAssinatura.disabled=false;
		if (form.actionType.value == "incluir" && form.nrBemImovel.value.length > 0 && form.instituicao.value.length > 0 && form.itemTotal.value != "1") {
			document.getElementById("tipoTransferencia1").checked = true;
		}
		if (form.itemTotal.value.length == 0 || form.itemTotal.value != "1"){
			document.getElementById("tipoTransferencia1").disabled=false;
			document.getElementById("tipoTransferencia2").disabled=false;
			document.getElementById("tipoTransferencia3").disabled=false;
		}
		habilitarDesabilitarCamposTransferencia();
	}
	
	function desabilitaCampos() {
		var form = document.transferenciaBemImovelForm;
		document.getElementById("localizarBI").style.display="block";
		document.getElementById("addItemTransferencia").style.display="none";
		document.getElementById("addAssinatura").style.display="none";
		document.getElementById("calcTransferencia").style.display="none";
		form.nrBemImovel.disabled=false;
		if (document.forms[0].isGpAdmGeralUsuarioLogado.value == 'S' && document.forms[0].actionType.value == "incluir"){
			form.instituicao.disabled=false;
		}
		form.orgaoCessionario.disabled=true;
		form.dtInicioVigencia.disabled=true;
		form.dtFimVigencia.disabled=true;
		form.protocolo.disabled=true;
		form.orgaoAssinatura.disabled=true;
		form.cargoAssinatura.disabled=true;
		form.nomeAssinatura.disabled=true;
		form.ordemAssinatura.disabled=true;

		document.getElementById("tipoTransferencia1").disabled=true;
		document.getElementById("tipoTransferencia2").disabled=true;
		document.getElementById("tipoTransferencia3").disabled=true;
		form.edificacao.disabled=true;
		form.transferenciaMetros.disabled=true;
		form.transferenciaPercentual.disabled=true;
		form.caracteristicas.disabled=true;
		form.situacaoDominial.disabled=true;
		form.utilizacao.disabled=true;
		form.observacao.disabled=true;
	}

	function habilitarDesabilitarCamposTransferencia() {
		var form = document.transferenciaBemImovelForm;
		form.edificacao.disabled=true;
		form.transferenciaMetros.disabled=true;
		form.transferenciaPercentual.disabled=true;
		form.caracteristicas.disabled=true;
		form.situacaoDominial.disabled=true;
		form.utilizacao.disabled=true;
		form.observacao.disabled=true;

		if(document.getElementById("tipoTransferencia1").checked || document.getElementById("tipoTransferencia2").checked || document.getElementById("tipoTransferencia3").checked){
			document.getElementById("addItemTransferencia").style.display="block";
			document.getElementById("calcTransferencia").style.display="block";
			form.caracteristicas.disabled=false;
			form.situacaoDominial.disabled=false;
			form.utilizacao.disabled=false;
			form.observacao.disabled=false;
			if(document.getElementById("tipoTransferencia1").checked){
				form.edificacao.value="";
				form.transferenciaMetros.value="";
				form.transferenciaPercentual.value="";
			} 
			if(document.getElementById("tipoTransferencia2").checked){
				form.edificacao.disabled=false;
				form.transferenciaMetros.disabled=false;
				form.transferenciaPercentual.disabled=false;
				carregarComboEdificacoes();
			}
			if(document.getElementById("tipoTransferencia3").checked){
				form.edificacao.value="";
				form.transferenciaMetros.disabled=false;
				form.transferenciaPercentual.disabled=false;
			}
			form.caracteristicas.focus();
		}
	}
	
	function voltar() {
		document.transferenciaBemImovelForm.action="transferenciaBemImovel.do?action=voltar";
		document.transferenciaBemImovelForm.submit();
	}

	function buscaBemImovel() {
		var form = document.transferenciaBemImovelForm;
		if (form.instituicao.value == '' || form.instituicao.value == '0') {
			alert ("Informe a Institui��o!");
			return;
		}
		if (form.nrBemImovel.value != '' && form.nrBemImovel.value > 0 && form.instituicao.value != '' && form.instituicao.value > 0){
			submitAjax('/abi/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.nrBemImovel.value+"&uc=transferencia&codInstituicao="+form.instituicao.value, document.forms[0], "dadosBemImovel",true);
			habilitaCampos();
		} else {
			if (document.getElementById("bemImovelSimplificado") != null) {
				ajaxHTMLLoad('dadosBemImovel','/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.nrBemImovel.value+"&uc=transferencia&codInstituicao="+form.instituicao.value,true);
			}
			desabilitaCampos();
		}
	}

	function localizarBemImovel() {
		var form = document.transferenciaBemImovelForm;

		if (form.instituicao.value == '' || form.instituicao.value == '0') {
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
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + "M;";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.actionType.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conInstituicao.value + ";";
		if (form.conCodBemImovel.value != '' && form.conCodBemImovel.value != null) {
			camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conCodBemImovel.value + ";";
		} else {
			camposPesquisaUCOrigem = camposPesquisaUCOrigem + " ;";
		}
		if (form.conNrBemImovel.value != '' && form.conNrBemImovel.value != null) {
			camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conNrBemImovel.value + ";";
		} else {
			camposPesquisaUCOrigem = camposPesquisaUCOrigem + " ;";
		}

		var strParametros="dialogHeight: 1000px; dialogWidth: 700px; dialogleft: 100px; dialogtop: 100px; Help:no; Status:no; Center:yes;";
		window.showModalDialog ('/abi/localizarBemImovelSimplificado.do?action=carregarInterfaceInicial&camposPesquisaUCOrigem='+camposPesquisaUCOrigem+"&uc=transferencia&instituicao="+form.instituicao.value+"&actionUCOrigem=transferenciaBemImovel",strParametros);
	}

	function validarCamposItemTransferencia() {
		if(validarCampos()) {
			var form = document.transferenciaBemImovelForm;
			var erro = '';
			
			if(!document.getElementById("tipoTransferencia1").checked && !document.getElementById("tipoTransferencia2").checked
					 && !document.getElementById("tipoTransferencia3").checked){
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Tipo de Transfer�ncia\".';
			}
			Trim(form.transferenciaMetros);
			Trim(form.transferenciaPercentual);
			if(document.getElementById("tipoTransferencia2").checked && form.edificacao.value == ""){
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Edifica��o\".';
			} 
			if((document.getElementById("tipoTransferencia2").checked || document.getElementById("tipoTransferencia3").checked) 
					&& (form.transferenciaMetros.value == "" && form.transferenciaPercentual.value == "")){
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Transfer�ncia em m� ou Transfer�ncia em %\".';
			} 
			if(form.caracteristicas.value == '') {
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Caracter�sticas\".';
			}
			if(form.situacaoDominial.value == '') {
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Situa��o Dominial\".';
			}
			if(form.utilizacao.value == '') {
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Utiliza��o\".';
			}
			
			if(erro != '') {
				msg = "Os seguintes campos s�o obrigat�rios: \n";
				msg = msg + erro;  
				alert(msg);
				return false;
			}
	
			return true;
		}

	}
	
	function validarCamposAssinatura() {
		if(validarCampos()) {
			var form = document.transferenciaBemImovelForm;
			var erro = '';
			
			if(form.orgaoAssinatura.value == '') {
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"�rg�o da Assinatura\".';
			}
			if(form.cargoAssinatura.value == '') {
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Cargo\".';
			}
			if(form.nomeAssinatura.value == '') {
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Nome\".';
			}
			if(form.ordemAssinatura.value == '') {
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Ordem\".';
			}
			
			if(erro != '') {
				msg = "Os seguintes campos s�o obrigat�rios: \n";
				msg = msg + erro;  
				alert(msg);
				return false;
			}
			return true;
		}
	}
	
	function validarCampos() {
		limpaMensagens();
		var form = document.transferenciaBemImovelForm;
		var erro = '';
		
		if (document.forms[0].isGpAdmGeralUsuarioLogado.value == 'S'){
			if (form.instituicao.value == "" || form.instituicao.value == '0'){
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Institui��o\".';
			}
		}
		if(form.nrBemImovel.value == '' && form.codBemImovel.value == '') {
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Bem Im�vel\".';
		}
		Trim(form.orgaoCessionario);
		if(form.orgaoCessionario.value == " ") {
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"�rg�o de Destino\".';
		}
		
		if(erro != '') {
			msg = "Os seguintes campos s�o obrigat�rios: \n";
			msg = msg + erro;  
			alert(msg);
			return false;
		}

		if(form.dtInicioVigencia.value != '') {
			//data v�lida
			if(!ValidaData(form.dtInicioVigencia)) {
				alert("Campo 'Data de In�cio de Vig�ncia' deve ser uma data v�lida!");
				form.dtInicioVigencia.focus();
				return false;			
			}
			//dtInicioVigencia = ou > 04/12/1889
			if (DataMaior('04/12/1889', form.dtInicioVigencia.value)){
				alert("Campo 'Data de In�cio de Vig�ncia' deve ser igual ou maior que 04/12/1889!");
				form.dtInicioVigencia.focus();
				return false;			
			}
		}

		if(form.dtFimVigencia.value != '') {
			//data v�lida
			if(!ValidaData(form.dtFimVigencia)) {
				alert("Campo 'Data de Fim de Vig�ncia' deve ser uma data v�lida!");
				form.dtFimVigencia.focus();
				return false;			
			}
			//dtFimVigencia = ou > 04/12/1889
			if (DataMaior('04/12/1889', form.dtFimVigencia.value)){
				alert("Campo 'Data de Fim de Vig�ncia' deve ser igual ou maior que 04/12/1889!");
				form.dtFimVigencia.focus();
				return false;			
			}
			//dtFimVigencia > dtInicioVigencia
			if (!DataMaior(form.dtFimVigencia.value,form.dtInicioVigencia.value)){
				alert("Campo 'Data de Fim de Vig�ncia' deve ser maior que a Data de In�cio de Vig�ncia!");
				form.dtFimVigencia.focus();
				return false;			
			}
		}

		return true;

	}

	function carregarComboEdificacoes() {
		var form = document.transferenciaBemImovelForm;
		if (form.nrBemImovel.value != '' && form.nrBemImovel.value > 0 && form.instituicao.value != '' && form.instituicao.value > 0){
			submitAjax('/abi/transferenciaBemImovel.do?action=carregarComboEditEdificacaoBemImovel&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value, document.forms[0], "divComboEdificacao",true);
		} else {
			ajaxHTMLLoad('divComboEdificacao','/transferenciaBemImovel.do?action=carregarComboEditEdificacaoBemImovel&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value,true);
		}
	}

	function calcularTransferencia() {
		var form = document.transferenciaBemImovelForm;
		if (form.nrBemImovel.value.length > 0) {
			if (form.nrBemImovel.value != '' && form.nrBemImovel.value > 0 && form.instituicao.value != '' && form.instituicao.value > 0){
				alert("Para efetuar o c�lculo a Transfer�ncia em m� ou a Transfer�ncia em % dever� ser preenchida!");
				form.transferenciaMetros.focus();
			} else {
				submitAjax('/abi/transferenciaBemImovel.do?action=calcularTransferenciaBemImovel&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value, document.forms[0], 'divAreaTransferencia',false);
			}
		} else {
			alert ("Informe um Bem Im�vel!");
		}
	}

	function adicionarItemTransferencia() {
		if (validarCamposItemTransferencia()){
			var form = document.transferenciaBemImovelForm;
			var qtd = parseInt(form.qtdItens.value,10);
			if (qtd > 0 && document.getElementById("tipoTransferencia1").checked) {
				if(confirm('Confirma a exclus�o dos outros itens?')) {
					document.transferenciaBemImovelForm.action='transferenciaBemImovel.do?action=adicionarItemTransferenciaBemImovel&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value;
					document.transferenciaBemImovelForm.submit();
				}
			} else {
				document.transferenciaBemImovelForm.action='transferenciaBemImovel.do?action=adicionarItemTransferenciaBemImovel&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value;
				document.transferenciaBemImovelForm.submit();
			}
		}
	}
	
	function excluirItemTransferencia(codigo){
		if (codigo != "") {
			if(confirm('Tem certeza que deseja excluir?')) {
				var form = document.transferenciaBemImovelForm;
				document.transferenciaBemImovelForm.action='transferenciaBemImovel.do?action=excluirItemTransferenciaBemImovel&codItemTransferencia='+codigo+'&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value;
				document.transferenciaBemImovelForm.submit();
			}
		} else {
			alert ('Selecione um item para excluir!');
		}
	}
	
	function carregarComboOrgaoAssinatura() {
		var form = document.transferenciaBemImovelForm;
		if (form.nrBemImovel.value != '' && form.nrBemImovel.value > 0 && form.instituicao.value != '' && form.instituicao.value > 0){
			submitAjax('/abi/transferenciaBemImovel.do?action=carregarComboOrgaoAssinatura&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value, document.forms[0], "divComboOrgaoAssinatura",true);
		} else {
			ajaxHTMLLoad('divComboCargoAssinatura','/transferenciaBemImovel.do?action=carregarComboCargoAssinatura&codOrgao='+form.orgaoAssinatura.value,true);
			ajaxHTMLLoad('divComboNomeAssinatura','/transferenciaBemImovel.do?action=carregarComboNomeAssinatura&cargoAssinatura='+form.cargoAssinatura.value+"&nrBemImovel="+form.nrBemImovel.value+"&instituicao="+form.instituicao.value,true);
		}
	}
	

	function carregarComboCargoAssinatura() {
		var form = document.transferenciaBemImovelForm;
		if (form.orgaoAssinatura.value != '' && form.orgaoAssinatura.value > 0 && form.nrBemImovel.value != '' && form.nrBemImovel.value > 0 && form.instituicao.value != '' && form.instituicao.value > 0){
			submitAjax('/abi/transferenciaBemImovel.do?action=carregarComboCargoAssinatura&codOrgao='+form.orgaoAssinatura.value+'&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value, document.forms[0], "divComboCargoAssinatura",true);
		} else {
			ajaxHTMLLoad('divComboCargoAssinatura','/transferenciaBemImovel.do?action=carregarComboCargoAssinatura&codOrgao='+form.orgaoAssinatura.value+'&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value,true);
			ajaxHTMLLoad('divComboNomeAssinatura','/transferenciaBemImovel.do?action=carregarComboNomeAssinatura&cargoAssinatura='+form.cargoAssinatura.value+'&nrBemImovel='+form.nrBemImovel.value+"&instituicao="+form.instituicao.value,true);
		}
	}
	
	function carregarComboNomeAssinatura() {
		var form = document.transferenciaBemImovelForm;
		if (form.cargoAssinatura.value != '' && form.cargoAssinatura.value > 0 && form.nrBemImovel.value != '' && form.nrBemImovel.value > 0 && form.instituicao.value != '' && form.instituicao.value > 0){
			submitAjax('/abi/transferenciaBemImovel.do?action=carregarComboNomeAssinatura&cargoAssinatura='+form.cargoAssinatura.value+'&nrBemImovel='+form.nrBemImovel.value+"&instituicao="+form.instituicao.value, document.forms[0], "divComboNomeAssinatura",true);
		} else {
			ajaxHTMLLoad('divComboNomeAssinatura','/transferenciaBemImovel.do?action=carregarComboNomeAssinatura&cargoAssinatura='+form.cargoAssinatura.value+'&nrBemImovel='+form.nrBemImovel.value+"&instituicao="+form.instituicao.value,true);
		}
	}

	function adicionarAssinatura() {
		if (validarCamposAssinatura()){
			var form = document.transferenciaBemImovelForm;
			document.transferenciaBemImovelForm.action='transferenciaBemImovel.do?action=adicionarAssinaturaTransferenciaBemImovel&nrBemImovel='+form.nrBemImovel.value+"&instituicao="+form.instituicao.value;
			document.transferenciaBemImovelForm.submit();
		}
	}

	function excluirAssinaturaTransferencia(codigo){
		if (codigo != "") {
			if(confirm('Tem certeza que deseja excluir?')) {
				var form = document.transferenciaBemImovelForm;
				document.transferenciaBemImovelForm.action='transferenciaBemImovel.do?action=excluirAssinaturaTransferenciaBemImovel&codAssinaturaTransferencia='+codigo+'&nrBemImovel='+form.nrBemImovel.value+"&instituicao="+form.instituicao.value;
				document.transferenciaBemImovelForm.submit();
			}
		} else {
			alert ('Selecione um item para excluir!');
		}
	}

	function salvarTransferencia() {
		if (validarCampos()){
			if (validarCamposDesnecPreenchidos()) {
				var form = document.transferenciaBemImovelForm;
				document.transferenciaBemImovelForm.action='transferenciaBemImovel.do?action=salvarTransferenciaBemImovel&nrBemImovel='+form.nrBemImovel.value+"&instituicao="+form.instituicao.value;
				document.transferenciaBemImovelForm.submit();
			}
		}
	}

	function alterarTransferencia() {
		if (validarCampos()){
			if (validarCamposDesnecPreenchidos()) {
				var form = document.transferenciaBemImovelForm;
				document.transferenciaBemImovelForm.action='transferenciaBemImovel.do?action=alterarTransferenciaBemImovel&nrBemImovel='+form.nrBemImovel.value+"&instituicao="+form.instituicao.value;
				document.transferenciaBemImovelForm.submit();

			}
		}
	}

	function validarCamposDesnecPreenchidos() {
		var form = document.transferenciaBemImovelForm;

		if(document.getElementById("tipoTransferencia1").checked || document.getElementById("tipoTransferencia2").checked
			|| document.getElementById("tipoTransferencia3").checked || form.caracteristicas.value.length > 0 || 
			form.situacaoDominial.value.length > 0 || form.utilizacao.value.length > 0 || form.observacao.value.length > 0) {
			alert("Foram detectados campos preenchidos na se��o 'Item de Transfer�ncia', favor adicion�-lo ou limpar os campos preenchidos!");
			return false;
		}
		if(form.orgaoAssinatura.value.length > 0 || form.cargoAssinatura.value.length > 0 || form.nomeAssinatura.value.length > 0 
			|| form.ordemAssinatura.value.length > 0) {
			alert("Foram detectados campos preenchidos na se��o 'Assinaturas', favor adicion�-la ou limpar os campos preenchidos!");
			return false;
		}

		return true;		
	}

	function limparCamposItem() {
		var form = document.transferenciaBemImovelForm;
		document.getElementById("tipoTransferencia1").checked = false;
		document.getElementById("tipoTransferencia2").checked = false;
		document.getElementById("tipoTransferencia2").checked = false;
		form.edificacao.value="";
		form.transferenciaMetros.value="";
		form.transferenciaPercentual.value="";
		form.caracteristicas.value="";
		form.situacaoDominial.value="";
		form.utilizacao.value="";
		form.observacao.value="";
	}
	
	function limparCamposAssinatura() {
		var form = document.transferenciaBemImovelForm;
		form.orgaoAssinatura.value="";
		form.cargoAssinatura.value="";
		form.nomeAssinatura.value="";
		form.ordemAssinatura.value="";
		ajaxHTMLLoad('divComboCargoAssinatura','/transferenciaBemImovel.do?action=carregarComboCargoAssinatura&codOrgao='+form.orgaoAssinatura.value,true);
		ajaxHTMLLoad('divComboNomeAssinatura','/transferenciaBemImovel.do?action=carregarComboNomeAssinatura&cargoAssinatura='+form.cargoAssinatura.value,true);
	}

</script>

<c:choose> 
	<c:when test='${transferenciaBemImovelForm.actionType == "incluir"}'>
		<c:set var="acao" value="transferenciaBemImovel.do?action=salvarTransferenciaBemImovel"></c:set>
		<c:set var="titulo" value="Incluir"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="acao" value="transferenciaBemImovel.do?action=alterarTransferenciaBemImovel"></c:set>
		<c:set var="titulo" value="Alterar"></c:set>
	</c:otherwise>
</c:choose>

<div id="conteudo">
	<div align="center">
	<h1 >${titulo} Transfer�ncia de Uso de Bem Im�vel </h1>
		
	<div id="conteudo_corpo">
		<html:form action="${acao}" onsubmit="return validarCampos()">
			<html:hidden property="codTransferencia" value="${transferenciaBemImovelForm.codTransferencia}"/>
			<html:hidden property="actionType" value="${transferenciaBemImovelForm.actionType}"/>
			<html:hidden property="itemTotal" value="${transferenciaBemImovelForm.itemTotal}"/>
			<html:hidden property="qtdItens" value="${transferenciaBemImovelForm.qtdItens}"/>
			<html:hidden property="bemImovelSimplificado" value="${bemImovelSimplificado}"/>
			<html:hidden property="isGpAdmGeralUsuarioLogado" value="${transferenciaBemImovelForm.isGpAdmGeralUsuarioLogado}"/>
			<html:hidden property="pesqExec"/>
			<html:hidden property="conNrTermo"/>
			<html:hidden property="conCodBemImovel"/>
			<html:hidden property="conNrBemImovel"/>
			<html:hidden property="conInstituicao"/>
			<html:hidden property="conProtocolo"/>
			<html:hidden property="conSituacao"/>
			<html:hidden property="conOrgaoCessionario"/>
			<html:hidden property="uf"/>
			<html:hidden property="codMunicipio"/>
			<html:hidden property="indOperadorOrgao" />
			<html:hidden property="codBemImovel"/>

			<table cellspacing="0" class="form_tabela" width="100%">
				<tr>
					<td colspan="2" width="100%">
						<table width="100%">
							<c:choose> 
								<c:when test='${transferenciaBemImovelForm.actionType == "alterar"}'>
									<tr>
										<td class="form_label">N� do Termo:</td>
										<td> <c:out value="${transferenciaBemImovelForm.codTransferencia}"></c:out> </td>
									</tr>
								</c:when>
							</c:choose>
							<c:if test="${transferenciaBemImovelForm.status != null}">
								<tr>
									<td class="form_label">Status:</td>
									<td> <c:out value="${transferenciaBemImovelForm.status}"></c:out> </td>
								</tr>
							</c:if>
							<c:if test="${transferenciaBemImovelForm.isGpAdmGeralUsuarioLogado == 'S'}">
								<tr>
									<td class="form_label" align="right">
										<c:choose> 
											<c:when test='${transferenciaBemImovelForm.actionType == "incluir"}'>
												* Institui��o: 
											</c:when>
											<c:otherwise>
												Institui��o: 
											</c:otherwise>
										</c:choose>
									</td>
									<td colspan="2">
										<c:choose> 
											<c:when test='${transferenciaBemImovelForm.actionType == "incluir"}'>
												<html:select property="instituicao" onchange="javascript:carregarComboOrgao();">
												 	<html:options collection="listaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
											    </html:select>
											</c:when>
											<c:otherwise>
												<c:out value="${transferenciaBemImovelForm.instituicaoDesc}"></c:out>
												<html:hidden property="instituicao" value="${transferenciaBemImovelForm.instituicao}"/>
											</c:otherwise>
										</c:choose>
									</td>	
								</tr>
							</c:if>
							<c:if test="${transferenciaBemImovelForm.isGpAdmGeralUsuarioLogado != 'S'}">
								<html:hidden property="instituicao" value="${transferenciaBemImovelForm.instituicao}"/>
							</c:if>
							<tr>
								<td class="form_label" width="260">* Bem Im�vel:</td>
								<td> 
									<table>
										<tr>
											<td>
												<html:text property="nrBemImovel" styleId="nrBemImovel" maxlength="10" size="10" onkeyup="DigitaNumero(this);" onblur="buscaBemImovel();" />
											</td>
											<td>
												<div id="localizarBI"> 
													<a href="javascript:localizarBemImovel();"><img src="${icon_localizar}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:localizarBemImovel();">Localizar</a>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="form_label"></td>
								<td> 
									<div id="dadosBemImovel"> 
										<c:if test="${bemImovelSimplificado != null}">
											<tiles:insert definition="viewDadosBemImovelSimplificadoAjaxDef"/>
										</c:if>
									</div>
								</td>
							</tr>
							<tr>
							 	<td class="form_label">* �rg�o de Destino: </td>
							 	<td>
									<div id="divComboOrgao">
										<tiles:insert page="/pages/transferenciaBemImovel/_edit_comboOrgao.jsp"/>
									</div>
								</td>
							</tr>
							<tr> 
					          	<td class="form_label">Data de In�cio de Vig�ncia: </td>
					          	<td>
									<html:text styleId="dtInicioVigencia" property="dtInicioVigencia"  size="10" maxlength="10" onkeyup="javascript:return(MascaraData(this,event));"/>
									<img src="${icon_calendar}" width="16" height="15" id="btnCalendarInicVing" onclick="javascript:Calendario(document.getElementById('dtInicioVigencia'),this.id);" />
								</td>
						   	</tr>
							<tr> 
					          	<td class="form_label">Data de Fim de Vig�ncia (Previs�o): </td>
					          	<td>
									<html:text styleId="dtFimVigencia" property="dtFimVigencia"  size="10" maxlength="10" onkeyup="javascript:return(MascaraData(this,event));"/>
									<img src="${icon_calendar}" width="16" height="15" id="btnCalendarFimVig" onclick="javascript:Calendario(document.getElementById('dtFimVigencia'),this.id);" />
								</td>
						   	</tr>
							<tr>
								<td class="form_label">Protocolo:</td>
								<td> 
									<html:text property="protocolo" styleId="protocolo" maxlength="15" size="15" onkeyup="DigitaNumero(this);" onblur="DigitaNumero(this);"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>						
				<tr>
					<td colspan="2" width="100%">
					    <div id="divItemTransferencia"> 
							<h2>Item da Transfer�ncia</h2>
							<table width="100%">
								<tr>
								 	<td class="form_label" width="260">Tipo de Transfer�ncia: </td>
							 		<td>
										<html:radio name="transferenciaBemImovelForm" value="1" property="tipoTransferencia" styleId ="tipoTransferencia1" onchange="javascript:habilitarDesabilitarCamposTransferencia();">Total do Bem Im�vel</html:radio>
										<html:radio name="transferenciaBemImovelForm" value="2" property="tipoTransferencia" styleId ="tipoTransferencia2" onchange="javascript:habilitarDesabilitarCamposTransferencia();">Edifica��o</html:radio>
										<html:radio name="transferenciaBemImovelForm" value="3" property="tipoTransferencia" styleId ="tipoTransferencia3" onchange="javascript:habilitarDesabilitarCamposTransferencia();">Terreno</html:radio>
									</td>
								</tr>
								<tr>
								 	<td class="form_labelAjust" height="45">Edifica��o: </td>
								 	<td>
										<p class="obs_obrigatorio">Tipo de Constru��o - Tipo de Edifica��o - Especifica��o - �rea constru�da dispon�vel em m� - �rea constru�da dispon�vel em %</p>
										<div id="divComboEdificacao">
											<tiles:insert definition="editComboEdificacaoBemImovelAjaxTransferenciaDef"/>
										</div>
									</td>
								</tr>
								<tr>
									<td class="form_label">Caracter�sticas:</td>
									<td> 
										<html:textarea  property="caracteristicas" cols="100" rows="3"  styleId="caracteristicas" onkeyup="TamanhoMax(this, 3000)"/>
									</td>
								</tr>
								<tr>
									<td class="form_label">Situa��o Dominial:</td>
									<td> 
										<html:textarea  property="situacaoDominial" cols="100" rows="3"  styleId="situacaoDominial" onkeyup="TamanhoMax(this, 3000)"/>
									</td>
								</tr>
								<tr>
									<td class="form_label">Utiliza��o:</td>
									<td> 
										<html:textarea  property="utilizacao" cols="100" rows="3"  styleId="utilizacao" onkeyup="TamanhoMax(this, 3000)"/>
									</td>
								</tr>
								<tr>
								 	<td colspan="2">
										<div id="divAreaTransferencia">
											<tiles:insert definition="editAreaTransferenciaBemImovelAjaxTransferenciaDef"/>
										</div>
									</td>
								</tr>
								<tr>
									<td class="form_label">Observa��o:</td>
									<td> 
										<table>
											<tr>
												<td>
													<html:textarea  property="observacao" cols="100" rows="3"  styleId="observacao" onkeyup="TamanhoMax(this, 3000)"/>
												</td>
												<td>
													<div id="addItemTransferencia"> 
														<a href="javascript:adicionarItemTransferencia();" id="linkAddItemTransferencia"><img src="${icon_adicionar}" width="16" height="16" id="iconAddItemTransferencia" border="0"></a>&nbsp;<a href="javascript:adicionarItemTransferencia()">Adicionar</a>						
														<a href="javascript:limparCamposItem();"><img src="${icon_limpar}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:limparCamposItem();">Limpar</a>
													</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td colspan="2"> 
										<div id="divListaItemTransferencia">
											<tiles:insert definition="editListItemTransferenciaBemImovelAjaxDef"/>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%">
					    <div id="divAssinaturas"> 
							<h2>Assinaturas</h2>
							<table width="100%">
								<tr>
								 	<td class="form_label" width="260">�rg�o: </td>
								 	<td>
										<div id="divComboOrgaoAssinatura">
											<tiles:insert definition="editComboOrgaoAssinaturaAjaxTransferenciaDef"/>
										</div>
									</td>
								</tr>
								<tr>
								 	<td class="form_label">Cargo: </td>
								 	<td>
										<div id="divComboCargoAssinatura">
											<tiles:insert definition="editComboCargoAssinaturaAjaxTransferenciaDef"/>
										</div>
									</td>
								</tr>
								<tr>
								 	<td class="form_label">Nome: </td>
								 	<td>
										<div id="divComboNomeAssinatura">
											<tiles:insert definition="editComboNomeAssinaturaAjaxTransferenciaDef"/>
										</div>
									</td>
								</tr>
								<tr>
									<td class="form_label">Ordem:</td>
									<td> 
										<table>
											<tr>
												<td>
													<html:text property="ordemAssinatura" styleId="ordemAssinatura" maxlength="15" size="15"/>
												</td>
												<td>
													<div id="addAssinatura"> 
														<a href="javascript:adicionarAssinatura();" id="linkAddAssinatura"><img src="${icon_adicionar}" width="16" id="iconAddAssinatura" height="16" border="0"></a>&nbsp;<a href="javascript:adicionarAssinatura();">Adicionar</a>						
														<a href="javascript:limparCamposAssinatura();"><img src="${icon_limpar}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:limparCamposAssinatura();">Limpar</a>
													</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td colspan="2"> 
										<div id="divListAssinatura">
											<tiles:insert definition="editListAssinaturasTransferenciaBemImovelAjaxDef"/>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<c:if test="${transferenciaBemImovelForm.incluidoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${transferenciaBemImovelForm.incluidoPor}"></c:out></td>
					</tr>
				</c:if>
				<c:if test="${transferenciaBemImovelForm.alteradoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${transferenciaBemImovelForm.alteradoPor}"></c:out></td>
					</tr>
				</c:if>
				<c:if test="${transferenciaBemImovelForm.excluidoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${transferenciaBemImovelForm.excluidoPor}"></c:out></td>
					</tr>
				</c:if>
			</table>
        
		   	<hr>
		  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigat�rio.</p>
		 	<div align="center">
				<c:choose> 
					<c:when test='${transferenciaBemImovelForm.actionType == "incluir"}'>
			 			<html:button property="incluir" value="Confirmar" styleClass="form_botao" onclick="salvarTransferencia();" />
					</c:when>
					<c:when test='${transferenciaBemImovelForm.actionType == "alterar"}'>
			 			<html:button property="alterar" value="Confirmar" styleClass="form_botao" onclick="alterarTransferencia();" />
					</c:when>
				</c:choose>
				<html:button property="btnCancelar" styleClass="form_botao" value="Voltar" onclick="voltar();"/>
			</div>	
			</html:form>
		</div>
	</div>
</div>
<script language="javascript">
window.onload = function(){
	var form = document.transferenciaBemImovelForm;
	desabilitaCampos();
	if (form.nrBemImovel.value.length > 0 && form.instituicao.value.length > 0){
		buscaBemImovel();
		carregarComboOrgao();
		form.dtInicioVigencia.focus();
	}
	if (form.itemTotal.value.length > 0 && form.itemTotal.value == "1"){
		document.getElementById("addItemTransferencia").style.display="none";
	}
};
</script>   
