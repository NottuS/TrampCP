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
			document.getElementById("msgs_servidor").innerHTML = "";
	}

	function habilitaCampos() {
		var form = document.doacaoBemImovelForm;
		document.getElementById("localizarBI").style.display="none";
		document.getElementById("addAssinatura").style.display="block";
		document.getElementById("locLeiBemImovel").style.display="block";
		form.nrBemImovel.disabled=true;
		if (document.forms[0].isGpAdmGeralUsuarioLogado.value == 'S' && document.forms[0].actionType.value == "incluir"){
			form.instituicao.disabled=true;
		}
		document.getElementById("administracaoDireta2").disabled=false;
		document.getElementById("administracaoDireta3").disabled=false;
		form.orgaoResponsavel.disabled=false;
		form.dtInicioVigencia.disabled=false;
		form.dtFimVigencia.disabled=false;
		form.protocolo.disabled=false;
		document.getElementById("projetoLeiS").disabled=false;
		document.getElementById("projetoLeiN").disabled=false;
		form.numeroLei.disabled=false;
		form.dataAssinaturaLei.disabled=false;
		form.dataPublicacaoLei.disabled=false;
		form.nrDioeLei.disabled=false;
		form.orgaoAssinatura.disabled=false;
		form.cargoAssinatura.disabled=false;
		form.nomeAssinatura.disabled=false;
		form.ordemAssinatura.disabled=false;
		carregarComboOrgao();
		if (form.actionType.value == "incluir" && form.nrBemImovel.value.length > 0 && form.instituicao.value.length > 0 && form.itemTotal.value != "1") {
			document.getElementById("tipoDoacao1").checked = true;
		}
		if (form.itemTotal.value.length == 0 || form.itemTotal.value != "1"){
			document.getElementById("tipoDoacao1").disabled=false;
			document.getElementById("tipoDoacao2").disabled=false;
			document.getElementById("tipoDoacao3").disabled=false;
		}
		habilitarDesabilitarCamposDoacao();
	}
	
	function desabilitaCampos() {
		var form = document.doacaoBemImovelForm;
		document.getElementById("localizarBI").style.display="block";
		document.getElementById("addItemDoacao").style.display="none";
		document.getElementById("addAssinatura").style.display="none";
		document.getElementById("calcDoacao").style.display="none";
		document.getElementById("locLeiBemImovel").style.display="none";
		form.nrBemImovel.disabled=false;
		if (document.forms[0].isGpAdmGeralUsuarioLogado.value == 'S' && document.forms[0].actionType.value == "incluir"){
			form.instituicao.disabled=false;
		}
		document.getElementById("administracaoDireta2").disabled=true;
		document.getElementById("administracaoDireta3").disabled=true;
		form.orgaoResponsavel.disabled=true;
		form.dtInicioVigencia.disabled=true;
		form.dtFimVigencia.disabled=true;
		form.protocolo.disabled=true;
		document.getElementById("projetoLeiS").disabled=true;
		document.getElementById("projetoLeiN").disabled=true;
		form.numeroLei.disabled=true;
		form.dataAssinaturaLei.disabled=true;
		form.dataPublicacaoLei.disabled=true;
		form.nrDioeLei.disabled=true;
		form.orgaoAssinatura.disabled=true;
		form.cargoAssinatura.disabled=true;
		form.nomeAssinatura.disabled=true;
		form.ordemAssinatura.disabled=true;

		document.getElementById("tipoDoacao1").disabled=true;
		document.getElementById("tipoDoacao2").disabled=true;
		document.getElementById("tipoDoacao3").disabled=true;
		form.edificacao.disabled=true;
		form.doacaoMetros.disabled=true;
		form.doacaoPercentual.disabled=true;
		form.utilizacao.disabled=true;
		form.observacao.disabled=true;
	}

	function habilitarDesabilitarCamposDoacao() {
		var form = document.doacaoBemImovelForm;
		form.edificacao.disabled=true;
		form.doacaoMetros.disabled=true;
		form.doacaoPercentual.disabled=true;
		form.utilizacao.disabled=true;
		form.observacao.disabled=true;

		if(document.getElementById("tipoDoacao1").checked || document.getElementById("tipoDoacao2").checked || document.getElementById("tipoDoacao3").checked){
			document.getElementById("addItemDoacao").style.display="block";
			document.getElementById("calcDoacao").style.display="block";
			form.utilizacao.disabled=false;
			form.observacao.disabled=false;
			if(document.getElementById("tipoDoacao1").checked){
				form.edificacao.value="";
				form.doacaoMetros.value="";
				form.doacaoPercentual.value="";
			} 
			if(document.getElementById("tipoDoacao2").checked){
				form.edificacao.disabled=false;
				form.doacaoMetros.disabled=false;
				form.doacaoPercentual.disabled=false;
				carregarComboEdificacoes();
			}
			if(document.getElementById("tipoDoacao3").checked){
				form.edificacao.value="";
				form.doacaoMetros.disabled=false;
				form.doacaoPercentual.disabled=false;
			}
			form.utilizacao.focus();
		}
	}
	
	function voltar() {
		document.doacaoBemImovelForm.action="doacaoBemImovel.do?action=voltar";
		document.doacaoBemImovelForm.submit();
	}

	function buscaBemImovel() {
		var form = document.doacaoBemImovelForm;
		if (form.instituicao.value == '' || form.instituicao.value == '0') {
			alert ("Informe a Instituição!");
			return;
		}
		if (form.nrBemImovel.value != '' && form.nrBemImovel.value > 0 && form.instituicao.value != '' && form.instituicao.value > 0){
			submitAjax('/abi/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.nrBemImovel.value+"&uc=doacao&codInstituicao="+form.instituicao.value, document.forms[0], "dadosBemImovel",true);
			habilitaCampos();
		} else {
			if (document.getElementById("bemImovelSimplificado") != null) {
				ajaxHTMLLoad('dadosBemImovel','/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.nrBemImovel.value+"&uc=doacao&codInstituicao="+form.instituicao.value,true);
			}
			desabilitaCampos();
		}
	}

	function localizarBemImovel() {
		var form = document.doacaoBemImovelForm;

		if (form.instituicao.value == '' || form.instituicao.value == '0') {
			alert ("Informe a Instituição!");
			return;
		}
		
		var camposPesquisaUCOrigem = "";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conNrTermo.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conProtocolo.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conSituacao.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conAdministracao.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conOrgaoResponsavel.value + ";";
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
		window.showModalDialog ('/abi/localizarBemImovelSimplificado.do?action=carregarInterfaceInicial&camposPesquisaUCOrigem='+camposPesquisaUCOrigem+"&uc=doacao&instituicao="+form.instituicao.value+"&actionUCOrigem=doacaoBemImovel",strParametros);
	}

	function localizarLei() {
		var form = document.doacaoBemImovelForm;
		Trim(form.codDoacao);
		Trim(form.nrBemImovel);
		if (form.nrBemImovel.value.length == 0) {
			alert ("Informe um Bem Imóvel!");
			return;
		}
		if (form.instituicao.value.length == 0) {
			alert ("Informe uma Instituição!");
			return;
		}
		if (form.codDoacao.value.length == 0){
			alert ("Salve a doação antes de selecionar uma lei para o Bem Imóvel informado!");
			return;
		}

		var camposPesquisaUCOrigem = "";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conNrTermo.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conProtocolo.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conSituacao.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conAdministracao.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conOrgaoResponsavel.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.uf.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.codMunicipio.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + "M;";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.actionType.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.codBemImovel.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.codDoacao.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.conInstituicao.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.instituicao.value + ";";
		camposPesquisaUCOrigem = camposPesquisaUCOrigem + form.nrBemImovel.value + ";";

		var strParametros="dialogHeight: 1000px; dialogWidth: 700px; dialogleft: 100px; dialogtop: 100px; Help:no; Status:no; Center:yes;";
		window.showModalDialog ('/abi/localizarLeiBemImovel.do?action=carregarListaLeiBemImovelLocalizar&camposPesquisaUCOrigem='+camposPesquisaUCOrigem+"&actionUCOrigem=doacaoBemImovel&nrBemImovel="+form.nrBemImovel.value+'&codInstituicao='+form.instituicao.value,strParametros);
	}

	function buscaLeiBemImovel() {
		var form = document.doacaoBemImovelForm;
		if(document.getElementById("projetoLeiN").checked && form.numeroLei.value.length > 0) {
			submitAjax('/abi/doacaoBemImovel.do?action=carregarDadosLeiBemImovel&numeroLei='+form.numeroLei.value, document.forms[0], "divDadosDaLei",true);
		} else {
			form.dataAssinaturaLei.value="";
			form.dataPublicacaoLei.value="";
			form.nrDioeLei.value="";
		}
	}

	function carregarComboOrgao(){
		var form = document.doacaoBemImovelForm;
		if(document.getElementById("administracaoDireta2").checked || document.getElementById("administracaoDireta3").checked){
			if (form.instituicao.value != '' && form.instituicao.value != '0'){
				form.orgaoResponsavel.disabled=false;
				submitAjax('/abi/doacaoBemImovel.do?action=carregarComboOrgao&instituicao='+form.instituicao.value, document.forms[0], 'divComboOrgao',true);
			}else{
				form.orgaoResponsavel.disabled=true;
				form.orgaoResponsavel.value="";
			}
		}else{
			form.orgaoResponsavel.disabled=true;
			form.orgaoResponsavel.value="";
		}
	}

	function validarCamposItemDoacao() {
		if(validarCampos()) {
			var form = document.doacaoBemImovelForm;
			var erro = '';
			
			if(!document.getElementById("tipoDoacao1").checked && !document.getElementById("tipoDoacao2").checked
					 && !document.getElementById("tipoDoacao3").checked){
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Tipo de Doação\".';
			}
			Trim(form.doacaoMetros);
			Trim(form.doacaoPercentual);
			if(document.getElementById("tipoDoacao2").checked && form.edificacao.value == ""){
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Edificação\".';
			} 
			if((document.getElementById("tipoDoacao2").checked || document.getElementById("tipoDoacao3").checked) 
					&& (form.doacaoMetros.value == "" && form.doacaoPercentual.value == "")){
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Doação em m² ou Doação em %\".';
			} 
			
			if(form.utilizacao.value == '') {
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Utilização\".';
			}
			
			if(erro != '') {
				msg = "Os seguintes campos são obrigatórios: \n";
				msg = msg + erro;  
				alert(msg);
				return false;
			}
	
			return true;
		}

	}
	
	function validarCamposAssinatura() {
		if(validarCampos()) {
			var form = document.doacaoBemImovelForm;
			var erro = '';
			
			if(form.orgaoAssinatura.value == '') {
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Órgão\".';
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
				msg = "Os seguintes campos são obrigatórios: \n";
				msg = msg + erro;  
				alert(msg);
				return false;
			}
			return true;
		}
	}
	
	function validarCampos() {
		limpaMensagens();
		var form = document.doacaoBemImovelForm;
		var erro = '';
		
		if (document.forms[0].isGpAdmGeralUsuarioLogado.value == 'S'){
			if (form.instituicao.value == "" || form.instituicao.value == '0'){
				if (erro != '') {
					erro = erro + "\n";
				} 
				erro = erro + '- \"Instituição\".';
			}
		}
		if(form.nrBemImovel.value == '' && form.codBemImovel.value == '') {
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Bem Imóvel\".';
		}
		if(!document.getElementById("administracaoDireta2").checked && !document.getElementById("administracaoDireta3").checked){
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Administração\".';
		}
		Trim(form.orgaoResponsavel);
		if(form.orgaoResponsavel.value == " ") {
			if (erro != '') {
				erro = erro + "\n";
			} 
			erro = erro + '- \"Órgão\".';
		}
		
		if(erro != '') {
			msg = "Os seguintes campos são obrigatórios: \n";
			msg = msg + erro;  
			alert(msg);
			return false;
		}

		if(form.dtInicioVigencia.value != '') {
			//data válida
			if(!ValidaData(form.dtInicioVigencia)) {
				alert("Campo 'Data de Início de Vigência' deve ser uma data válida!");
				form.dtInicioVigencia.focus();
				return false;			
			}
			//dtInicioVigencia = ou > 04/12/1889
			if (DataMaior('04/12/1889', form.dtInicioVigencia.value)){
				alert("Campo 'Data de Início de Vigência' deve ser igual ou maior que 04/12/1889!");
				form.dtInicioVigencia.focus();
				return false;			
			}
		}

		if(form.dtFimVigencia.value != '') {
			//data válida
			if(!ValidaData(form.dtFimVigencia)) {
				alert("Campo 'Data de Fim de Vigência' deve ser uma data válida!");
				form.dtFimVigencia.focus();
				return false;			
			}
			//dtFimVigencia = ou > 04/12/1889
			if (DataMaior('04/12/1889', form.dtFimVigencia.value)){
				alert("Campo 'Data de Fim de Vigência' deve ser igual ou maior que 04/12/1889!");
				form.dtFimVigencia.focus();
				return false;			
			}
			//dtFimVigencia > dtInicioVigencia
			if (!DataMaior(form.dtFimVigencia.value,form.dtInicioVigencia.value)){
				alert("Campo 'Data de Fim de Vigência' deve ser maior que a Data de Início de Vigência!");
				form.dtFimVigencia.focus();
				return false;			
			}
		}

		if(form.dataAssinaturaLei.value != '') {
			//data válida
			if(!ValidaData(form.dataAssinaturaLei)) {
				alert("Campo 'Data de Assinatura da Lei' deve ser uma data válida!");
				form.dataAssinaturaLei.focus();
				return false;			
			}
			//dataAssinaturaLei = ou > 04/12/1889
			if (DataMaior('04/12/1889', form.dataAssinaturaLei.value)){
				alert("Campo 'Data de Assinatura da Lei' deve ser igual ou maior que 04/12/1889!");
				form.dataAssinaturaLei.focus();
				return false;			
			}
		}

		if(form.dtInicioVigencia.value != '' && form.dataAssinaturaLei.value != '') {
			//dtInicioVigencia = ou > dataAssinaturaLei
			if (DataMaior(form.dataAssinaturaLei.value, form.dtInicioVigencia.value)){
				alert('Data de Início de Vigência deve ser igual ou maior que a Data de Assinatura da Lei!');
				return false;
			}
		} else if(form.dtInicioVigencia.value == '' && form.dataAssinaturaLei.value != '') {
			alert('Data de Início de Vigência deve ser informada e esta deve ser igual ou maior que a Data de Assinatura da Lei!');
			return false;
		}
		

		if(form.dataPublicacaoLei.value != '') {
			//data válida
			if(!ValidaData(form.dataPublicacaoLei)) {
				alert("Campo 'Data de Publicação da Lei' deve ser uma data válida!");
				form.dataPublicacaoLei.focus();
				return false;			
			}
			//dataPublicacaoLei = ou > 04/12/1889
			if (DataMaior('04/12/1889', form.dataPublicacaoLei.value)){
				alert("Campo 'Data de Publicação da Lei' deve ser igual ou maior que 04/12/1889!");
				form.dataPublicacaoLei.focus();
				return false;			
			}
			if(form.dataAssinaturaLei.value != '') {
				//dataPublicacaoLei = ou > dataAssinaturaLei
				if (DataMaior(form.dataAssinaturaLei.value, form.dataPublicacaoLei.value)){
					alert("Campo 'Data de Publicação da Lei' deve ser igual ou maior que a 'Data da Assinatura Lei'!");
					form.dataPublicacaoLei.focus();
					return false;			
				}
			}
		}

		return true;

	}

	function atribuirFocoNrLei () {
		var form = document.doacaoBemImovelForm;
		form.numeroLei.focus();
		if(document.getElementById("projetoLeiN").checked){
			form.dataAssinaturaLei.disabled=true;
			form.dataPublicacaoLei.disabled=true;
			form.nrDioeLei.disabled=true;
		} else {
			form.dataAssinaturaLei.disabled=false;
			form.dataPublicacaoLei.disabled=false;
			form.nrDioeLei.disabled=false;
		}
	}
	
	function carregarComboEdificacoes() {
		var form = document.doacaoBemImovelForm;
		if (form.nrBemImovel.value != '' && form.nrBemImovel.value > 0 && form.instituicao.value != '' && form.instituicao.value > 0){
			submitAjax('/abi/doacaoBemImovel.do?action=carregarComboEditEdificacaoBemImovel&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value, document.forms[0], "divComboEdificacao",true);
		} else {
			ajaxHTMLLoad('divComboEdificacao','/doacaoBemImovel.do?action=carregarComboEditEdificacaoBemImovel&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value,true);
		}
	}

	function calcularDoacao() {
		var form = document.doacaoBemImovelForm;
		if (form.nrBemImovel.value != '' && form.nrBemImovel.value > 0 && form.instituicao.value != '' && form.instituicao.value > 0){
			if(form.doacaoMetros.value == "" && form.doacaoPercentual.value == "") {
				alert("Para efetuar o cálculo a Doação em m² ou a Doação em % deverá ser preenchida!");
				form.doacaoMetros.focus();
			} else {
				submitAjax('/abi/doacaoBemImovel.do?action=calcularDoacaoBemImovel&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value, document.forms[0], 'divAreaDoacao',false);
			}
		} else {
			alert ("Informe um Bem Imóvel!");
		}
	}

	function adicionarItemDoacao() {
		if (validarCamposItemDoacao()){
			var form = document.doacaoBemImovelForm;
			var qtd = parseInt(form.qtdItens.value,10);
			if (qtd > 0 && document.getElementById("tipoDoacao1").checked) {
				if(confirm('Confirma a exclusão dos outros itens?')) {
					document.doacaoBemImovelForm.action='doacaoBemImovel.do?action=adicionarItemDoacaoBemImovel&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value;
					document.doacaoBemImovelForm.submit();
				}
			} else {
				document.doacaoBemImovelForm.action='doacaoBemImovel.do?action=adicionarItemDoacaoBemImovel&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value;
				document.doacaoBemImovelForm.submit();
			}
		}
	}
	
	function excluirItemDoacao(codigo){
		if (codigo != "") {
			if(confirm('Tem certeza que deseja excluir?')) {
				var form = document.doacaoBemImovelForm;
				document.doacaoBemImovelForm.action='doacaoBemImovel.do?action=excluirItemDoacaoBemImovel&codItemDoacao='+codigo+'&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value;
				document.doacaoBemImovelForm.submit();
			}
		} else {
			alert ('Selecione um item para excluir!');
		}
	}
	
	function carregarComboOrgaoAssinatura() {
		var form = document.doacaoBemImovelForm;
		if (form.nrBemImovel.value != '' && form.nrBemImovel.value > 0 && form.instituicao.value != '' && form.instituicao.value > 0){
			submitAjax('/abi/doacaoBemImovel.do?action=carregarComboOrgaoAssinatura&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value, document.forms[0], "divComboOrgaoAssinatura",true);
		} else {
			ajaxHTMLLoad('divComboCargoAssinatura','/doacaoBemImovel.do?action=carregarComboCargoAssinatura&codOrgao='+form.orgaoAssinatura.value,true);
			ajaxHTMLLoad('divComboNomeAssinatura','/doacaoBemImovel.do?action=carregarComboNomeAssinatura&cargoAssinatura='+form.cargoAssinatura.value+"&nrBemImovel="+form.nrBemImovel.value+"&instituicao="+form.instituicao.value,true);
		}
	}
	

	function carregarComboCargoAssinatura() {
		var form = document.doacaoBemImovelForm;
		if (form.orgaoAssinatura.value != '' && form.orgaoAssinatura.value > 0 && form.nrBemImovel.value != '' && form.nrBemImovel.value > 0 && form.instituicao.value != '' && form.instituicao.value > 0){
			submitAjax('/abi/doacaoBemImovel.do?action=carregarComboCargoAssinatura&codOrgao='+form.orgaoAssinatura.value+'&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value, document.forms[0], "divComboCargoAssinatura",true);
		} else {
			ajaxHTMLLoad('divComboCargoAssinatura','/doacaoBemImovel.do?action=carregarComboCargoAssinatura&codOrgao='+form.orgaoAssinatura.value+'&nrBemImovel='+form.nrBemImovel.value+'&instituicao='+form.instituicao.value,true);
			ajaxHTMLLoad('divComboNomeAssinatura','/doacaoBemImovel.do?action=carregarComboNomeAssinatura&cargoAssinatura='+form.cargoAssinatura.value+'&nrBemImovel='+form.nrBemImovel.value+"&instituicao="+form.instituicao.value,true);
		}
	}
	
	function carregarComboNomeAssinatura() {
		var form = document.doacaoBemImovelForm;
		if (form.cargoAssinatura.value != '' && form.cargoAssinatura.value > 0 && form.nrBemImovel.value != '' && form.nrBemImovel.value > 0 && form.instituicao.value != '' && form.instituicao.value > 0){
			submitAjax('/abi/doacaoBemImovel.do?action=carregarComboNomeAssinatura&cargoAssinatura='+form.cargoAssinatura.value+'&nrBemImovel='+form.nrBemImovel.value+"&instituicao="+form.instituicao.value, document.forms[0], "divComboNomeAssinatura",true);
		} else {
			ajaxHTMLLoad('divComboNomeAssinatura','/doacaoBemImovel.do?action=carregarComboNomeAssinatura&cargoAssinatura='+form.cargoAssinatura.value+'&nrBemImovel='+form.nrBemImovel.value+"&instituicao="+form.instituicao.value,true);
		}
	}

	function adicionarAssinatura() {
		if (validarCamposAssinatura()){
			var form = document.doacaoBemImovelForm;
			document.doacaoBemImovelForm.action='doacaoBemImovel.do?action=adicionarAssinaturaDoacaoBemImovel&nrBemImovel='+form.nrBemImovel.value+"&instituicao="+form.instituicao.value;
			document.doacaoBemImovelForm.submit();
		}
	}

	function excluirAssinaturaDoacao(codigo){
		if (codigo != "") {
			if(confirm('Tem certeza que deseja excluir?')) {
				var form = document.doacaoBemImovelForm;
				document.doacaoBemImovelForm.action='doacaoBemImovel.do?action=excluirAssinaturaDoacaoBemImovel&codAssinaturaDoacao='+codigo+'&nrBemImovel='+form.nrBemImovel.value+"&instituicao="+form.instituicao.value;
				document.doacaoBemImovelForm.submit();
			}
		} else {
			alert ('Selecione um item para excluir!');
		}
	}

	function salvarDoacao() {
		if (validarCampos()){
			if (validarCamposDesnecPreenchidos()){
				var form = document.doacaoBemImovelForm;
				document.doacaoBemImovelForm.action='doacaoBemImovel.do?action=salvarDoacaoBemImovel&nrBemImovel='+form.nrBemImovel.value+"&instituicao="+form.instituicao.value;
				document.doacaoBemImovelForm.submit();
			}
		}
	}

	function alterarDoacao() {
		if (validarCampos()){
			if (validarCamposDesnecPreenchidos()){
				var form = document.doacaoBemImovelForm;
				document.doacaoBemImovelForm.action='doacaoBemImovel.do?action=alterarDoacaoBemImovel&nrBemImovel='+form.nrBemImovel.value+"&instituicao="+form.instituicao.value;
				document.doacaoBemImovelForm.submit();
			}
		}
	}

	function validarCamposDesnecPreenchidos() {
		var form = document.doacaoBemImovelForm;

		if(document.getElementById("tipoDoacao1").checked || document.getElementById("tipoDoacao2").checked
			|| document.getElementById("tipoDoacao3").checked || form.utilizacao.value.length > 0 || form.observacao.value.length > 0) {
			alert("Foram detectados campos preenchidos na seção 'Item de Doação', favor adicioná-lo ou limpar os campos preenchidos!");
			return false;
		}
		if(form.orgaoAssinatura.value.length > 0 || form.cargoAssinatura.value.length > 0 || form.nomeAssinatura.value.length > 0 
			|| form.ordemAssinatura.value.length > 0) {
			alert("Foram detectados campos preenchidos na seção 'Assinaturas', favor adicioná-la ou limpar os campos preenchidos!");
			return false;
		}

		return true;		
	}

	function limparCamposItem() {
		var form = document.doacaoBemImovelForm;
		document.getElementById("tipoDoacao1").checked = false;
		document.getElementById("tipoDoacao2").checked = false;
		document.getElementById("tipoDoacao2").checked = false;
		form.edificacao.value="";
		form.doacaoMetros.value="";
		form.doacaoPercentual.value="";
		form.utilizacao.value="";
		form.observacao.value="";
	}
	
	function limparCamposAssinatura() {
		var form = document.doacaoBemImovelForm;
		form.orgaoAssinatura.value="";
		form.cargoAssinatura.value="";
		form.nomeAssinatura.value="";
		form.ordemAssinatura.value="";
		ajaxHTMLLoad('divComboCargoAssinatura','/doacaoBemImovel.do?action=carregarComboCargoAssinatura&codOrgao='+form.orgaoAssinatura.value,true);
		ajaxHTMLLoad('divComboNomeAssinatura','/doacaoBemImovel.do?action=carregarComboNomeAssinatura&cargoAssinatura='+form.cargoAssinatura.value,true);
	}

</script>

<c:choose> 
	<c:when test='${doacaoBemImovelForm.actionType == "incluir"}'>
		<c:set var="acao" value="doacaoBemImovel.do?action=salvarDoacaoBemImovel"></c:set>
		<c:set var="titulo" value="Incluir"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="acao" value="doacaoBemImovel.do?action=alterarDoacaoBemImovel"></c:set>
		<c:set var="titulo" value="Alterar"></c:set>
	</c:otherwise>
</c:choose>

<div id="conteudo">
	<div align="center">
	<h1 >${titulo} Doação de Bem Imóvel </h1>
		
	<div id="conteudo_corpo">
		<html:form action="${acao}" onsubmit="return validarCampos()">
			<html:hidden property="codDoacao" value="${doacaoBemImovelForm.codDoacao}"/>
			<html:hidden property="itemTotal" value="${doacaoBemImovelForm.itemTotal}"/>
			<html:hidden property="qtdItens" value="${doacaoBemImovelForm.qtdItens}"/>
			<html:hidden property="actionType" value="${doacaoBemImovelForm.actionType}"/>
			<html:hidden property="isGpAdmGeralUsuarioLogado" value="${doacaoBemImovelForm.isGpAdmGeralUsuarioLogado}"/>
			<html:hidden property="bemImovelSimplificado" value="${bemImovelSimplificado}"/>
			<html:hidden property="pesqExec"/>
			<html:hidden property="conNrTermo"/>
			<html:hidden property="conCodBemImovel"/>
			<html:hidden property="conNrBemImovel"/>
			<html:hidden property="conInstituicao"/>
			<html:hidden property="conProtocolo"/>
			<html:hidden property="conSituacao"/>
			<html:hidden property="conAdministracao"/>
			<html:hidden property="conOrgaoResponsavel"/>
			<html:hidden property="uf"/>
			<html:hidden property="codMunicipio"/>
			<html:hidden property="codBemImovel"/>

			<table cellspacing="0" class="form_tabela" width="100%">
				<tr>
					<td colspan="2" width="100%">
						<table width="100%">
							<c:choose> 
								<c:when test='${doacaoBemImovelForm.actionType == "alterar"}'>
									<tr>
										<td class="form_label">Nº do Termo:</td>
										<td> <c:out value="${doacaoBemImovelForm.codDoacao}"></c:out> </td>
									</tr>
								</c:when>
							</c:choose>
							<c:if test="${doacaoBemImovelForm.status != null}">
									<tr>
										<td class="form_label">Status:</td>
										<td> <c:out value="${doacaoBemImovelForm.status}"></c:out> </td>
									</tr>
							</c:if>
							<c:if test="${doacaoBemImovelForm.isGpAdmGeralUsuarioLogado == 'S'}">
								<tr>
									<td class="form_label" align="right">
										<c:choose> 
											<c:when test='${doacaoBemImovelForm.actionType == "incluir"}'>
												* Instituição: 
											</c:when>
											<c:otherwise>
												Instituição: 
											</c:otherwise>
										</c:choose>
									</td>
									<td colspan="2">
										<c:choose> 
											<c:when test='${doacaoBemImovelForm.actionType == "incluir"}'>
												<html:select property="instituicao" onchange="javascript:carregarComboOrgao();">
												 	<html:options collection="listaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
											    </html:select>
											</c:when>
											<c:otherwise>
												<c:out value="${doacaoBemImovelForm.instituicaoDesc}"></c:out>
												<html:hidden property="instituicao" value="${doacaoBemImovelForm.instituicao}"/>
											</c:otherwise>
										</c:choose>
									</td>	
								</tr>
							</c:if>
							<c:if test="${doacaoBemImovelForm.isGpAdmGeralUsuarioLogado != 'S'}">
								<html:hidden property="instituicao" value="${doacaoBemImovelForm.instituicao}"/>
							</c:if>
							<tr>
								<td class="form_label" width="260">* Bem Imóvel:</td>
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
						 		<td class="form_label">* Administração: </td>
						 		<td>
									<html:radio value="2" property="administracao" styleId ="administracaoDireta2" onchange="javascript:carregarComboOrgao();">Indireta</html:radio>
									<html:radio value="3" property="administracao" styleId ="administracaoDireta3" onchange="javascript:carregarComboOrgao();">Terceiros</html:radio>
								</td>
							</tr>
							<tr>
							 	<td class="form_label">* Órgão: </td>
							 	<td>
									<div id="divComboOrgao">
										<tiles:insert page="/pages/doacaoBemImovel/_edit_comboOrgao.jsp"/>
									</div>
								</td>
							</tr>
							<tr> 
					          	<td class="form_label">Data de Início de Vigência: </td>
					          	<td>
									<html:text styleId="dtInicioVigencia" property="dtInicioVigencia"  size="10" maxlength="10" onkeyup="javascript:return(MascaraData(this,event));"/>
									<img src="${icon_calendar}" width="16" height="15" id="btnCalendarInicVing" onclick="javascript:Calendario(document.getElementById('dtInicioVigencia'),this.id);" />
								</td>
						   	</tr>
							<tr> 
					          	<td class="form_label">Data de Fim de Vigência (Previsão): </td>
					          	<td>
									<html:text styleId="dtFimVigencia" property="dtFimVigencia"  size="10" maxlength="10" onkeyup="javascript:return(MascaraData(this,event));"/>
									<img src="${icon_calendar}" width="16" height="15" id="btnCalendarFimVig" onclick="javascript:Calendario(document.getElementById('dtFimVigencia'),this.id);" />
								</td>
						   	</tr>
							<tr>
								<td class="form_label">Protocolo:</td>
								<td> 
									<html:text property="protocolo" styleId="protocolo" maxlength="15" size="15" onkeyup="DigitaNumero(this);" onblur="DigitaNumero(this);" />
								</td>
							</tr>
						</table>
					</td>
				</tr>						
				<tr>
					<td colspan="2" width="100%">
					    <div id="divItemDoacao"> 
							<h2>Item da Doação</h2>
							<table width="100%">
								<tr>
								 	<td class="form_label" width="260">Tipo de Doação: </td>
							 		<td>
										<html:radio name="doacaoBemImovelForm" value="1" property="tipoDoacao" styleId ="tipoDoacao1" onchange="javascript:habilitarDesabilitarCamposDoacao();">Total do Bem Imóvel</html:radio>
										<html:radio name="doacaoBemImovelForm" value="2" property="tipoDoacao" styleId ="tipoDoacao2" onchange="javascript:habilitarDesabilitarCamposDoacao();">Edificação</html:radio>
										<html:radio name="doacaoBemImovelForm" value="3" property="tipoDoacao" styleId ="tipoDoacao3" onchange="javascript:habilitarDesabilitarCamposDoacao();">Terreno</html:radio>
									</td>
								</tr>
								<tr>
								 	<td class="form_labelAjust" height="45">Edificação: </td>
								 	<td>
										<p class="obs_obrigatorio">Tipo de Construção - Tipo de Edificação - Especificação - Área construída disponível em m² - Área construída disponível em %</p>
										<div id="divComboEdificacao">
											<tiles:insert page="/pages/doacaoBemImovel/_edit_comboEdificacaoBemImovel.jsp"/>
										</div>
									</td>
								</tr>
								<tr>
									<td class="form_label">Utilização:</td>
									<td> 
										<html:textarea  property="utilizacao" cols="100" rows="3"  styleId="utilizacao" onkeyup="TamanhoMax(this, 3000)"/>
									</td>
								</tr>
								<tr>
								 	<td colspan="2">
										<div id="divAreaDoacao">
											<tiles:insert page="/pages/doacaoBemImovel/_edit_doacaoMetrosPercentual.jsp"/>
										</div>
									</td>
								</tr>
								<tr>
									<td class="form_label">Observação:</td>
									<td> 
										<table>
											<tr>
												<td>
													<html:textarea  property="observacao" cols="100" rows="3"  styleId="observacao" onkeyup="TamanhoMax(this, 3000)"/>
												</td>
												<td>
													<div id="addItemDoacao"> 
														<a href="javascript:adicionarItemDoacao();" id="linkAddItemDoacao"><img src="${icon_adicionar}" width="16" height="16" id="iconAddItemDoacao" border="0"></a>&nbsp;<a href="javascript:adicionarItemDoacao()">Adicionar</a>						
														<a href="javascript:limparCamposItem();"><img src="${icon_limpar}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:limparCamposItem();">Limpar</a>
													</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td colspan="2"> 
										<div id="divListaItemDoacao">
											<tiles:insert page="/pages/doacaoBemImovel/_edit_listItemDoacaoBemImovel.jsp"/>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%">
					    <div id="divDadosDaLei"> 
							<tiles:insert page="/pages/doacaoBemImovel/_edit_leiBemImovel.jsp"/>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%">
					    <div id="divAssinaturas"> 
							<h2>Assinaturas</h2>
							<table width="100%">
								<tr>
								 	<td class="form_label" width="260">Órgão: </td>
								 	<td>
										<div id="divComboOrgaoAssinatura">
											<tiles:insert page="/pages/doacaoBemImovel/_edit_comboOrgaoAssinatura.jsp"/>
										</div>
									</td>
								</tr>
								<tr>
								 	<td class="form_label">Cargo: </td>
								 	<td>
										<div id="divComboCargoAssinatura">
											<tiles:insert page="/pages/doacaoBemImovel/_edit_comboCargoAssinatura.jsp"/>
										</div>
									</td>
								</tr>
								<tr>
								 	<td class="form_label">Nome: </td>
								 	<td>
										<div id="divComboNomeAssinatura">
											<tiles:insert page="/pages/doacaoBemImovel/_edit_comboNomeAssinatura.jsp"/>
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
											<tiles:insert page="/pages/doacaoBemImovel/_edit_listAssinaturasDoacaoBemImovel.jsp"/>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<c:if test="${doacaoBemImovelForm.incluidoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${doacaoBemImovelForm.incluidoPor}"></c:out></td>
					</tr>
				</c:if>
				<c:if test="${doacaoBemImovelForm.alteradoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${doacaoBemImovelForm.alteradoPor}"></c:out></td>
					</tr>
				</c:if>
				<c:if test="${doacaoBemImovelForm.excluidoPor != null}">
					<tr>
			        	<td align="left" colspan="2" class="msg_descricao"><c:out value="${doacaoBemImovelForm.excluidoPor}"></c:out></td>
					</tr>
				</c:if>
			</table>
        
		   	<hr>
		  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
		 	<div align="center">
				<c:choose> 
					<c:when test='${doacaoBemImovelForm.actionType == "incluir"}'>
			 			<html:button property="incluir" value="Confirmar" styleClass="form_botao" onclick="salvarDoacao();" />
					</c:when>
					<c:when test='${doacaoBemImovelForm.actionType == "alterar"}'>
			 			<html:button property="alterar" value="Confirmar" styleClass="form_botao" onclick="alterarDoacao();" />
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
	var form = document.doacaoBemImovelForm;
	desabilitaCampos();
	if (form.nrBemImovel.value.length > 0 && form.instituicao.value.length > 0){
		buscaBemImovel();
		carregarComboOrgao();
		buscaLeiBemImovel();
		form.dtInicioVigencia.focus();
	}
	if (form.itemTotal.value.length > 0 && form.itemTotal.value == "1"){
		document.getElementById("addItemDoacao").style.display="none";
	}
};
</script>   
