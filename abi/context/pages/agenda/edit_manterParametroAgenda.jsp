<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"   prefix="fmt"  %>

<script type="text/javascript" src="js/mensagens.js" ></script>
<script type="text/javascript" src="js/generic/data.js" ></script>
<script type="text/javascript" src="js/generic/util.js" ></script>
<script type="text/javascript" src="js/generic/funcoes.js" ></script>
<script type="text/JavaScript" src="js/generic/maisinfo.js"></script>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<c:url var="js_maisinfo" value="/js/generic/maisinfo.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_funcoes" value="/js/validacao/ver_email.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='icon_info' value='/images/icon_info.png' />
<c:url var='icon_adicionar' value='/images/icon_adicionar1.png' />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<c:url var="link_entrada" value="/entrada.do"/>
<script language="javascript">
	


	function Trim(obj)
	{	
		Ltrim(obj);
	    Rtrim(obj);
	}


	function Rtrim(obj) 
	{
		varx = obj.value;
		while (varx.substr(varx.length - 1,1) == " ") 
		{
			varx = varx.substr(0, varx.length -1);
		}   
		obj.value = varx;
	}


	function Ltrim(obj) 
	{
		varx = obj.value;
		while (varx.substr(0,1) == " ") 
	{
		varx = varx.substr(1, varx.length -1);
		}
		obj.value = varx;
	}
	
	function voltar() {
		document.manterParametroAgendaForm.action='${link_entrada}';
		document.manterParametroAgendaForm.submit();
	}
	
	function atualizarEmail(){
		var email = document.getElementById('email');
		if(email != ""){
			if(ValidaEmail(email)){
				submitAjax("manterParametroAgenda.do?action=atualizarEmail&email="+email.value, document.forms[0], 'pesquisaAjax', false);
				document.getElementById('email').value = "";
			} else{
				alert("E-mail inválido.");
			}
		}else{
			alert("Favor informar o e-mail!");
		}
	}		

	function incluirAlterar() {
		document.manterParametroAgendaForm.action="manterParametroAgenda.do?action=incluirAlterar";	
		document.manterParametroAgendaForm.submit();	
	}

	
	function excluirEmail(id){
		submitAjax("manterParametroAgenda.do?action=excluirEmail&email="+id, document.forms[0], 'pesquisaAjax', false);
	}		
	
</script>

<div id="conteudo">
<div align="center">
<h1>Manter parâmetros da Agenda</h1>

<html:form action="manterParametroAgenda.do?action=iniciarProcesso">
	<html:hidden styleId="codParametroAgenda" property="codParametroAgenda" value="${manterParametroAgendaForm.codParametroAgenda}"/>
	<html:hidden property="codInstituicao"/>
	
    <div id="conteudo_corpo">	
	 	<table class="form_tabela" cellspacing="0" width="100%">
	 		<tr>
				<td class="form_label" width="40%">Instituição:</td>
				<td>${manterParametroAgendaForm.instituicao}</td>
			</tr>
			<tr>
				<td class="form_label" width="40%">N.º de dias que o sistema avisará antecipadamente o vencimento de <b>Notificações</b>:</td>
				<td><html:text  property="numeroDiasVencimentoNotificacao" maxlength="3" size="3" styleId="numeroDiasVencimentoNotificacao" onkeyup="DigitaNumero(this);" onblur="DigitaNumero(this);"/></td>
			</tr>
			<tr>
				<td class="form_label" width="40%">N.º de dias que o sistema avisará antecipadamente o vencimento de <b>Cessões de Uso</b>:</td>
				<td><html:text  property="numeroDiasVencimentoCessaoDeUso" maxlength="3" size="3" styleId="numeroDiasVencimentoCessaoDeUso" onkeyup="DigitaNumero(this);" onblur="DigitaNumero(this);"/></td>
			</tr>
			<tr>
				<td class="form_label" width="40%">N.º de dias que o sistema avisará antecipadamente o vencimento de <b>Doações</b>:</td>
				<td><html:text  property="numeroDiasVencimentoDoacao" maxlength="3" size="3" styleId="numeroDiasVencimentoDoacao" onkeyup="DigitaNumero(this);" onblur="DigitaNumero(this);"/></td>
			</tr>
			<tr>
				<td class="form_label" width="40%">N.º de dias que uma <b>Vistorias</b> estará válida:</td>
				<td><html:text  property="numeroDiasVencimentoVistoria" maxlength="3" size="3" styleId="numeroDiasVencimentoVistoria" onkeyup="DigitaNumero(this);" onblur="DigitaNumero(this);"/></td>
			</tr>
			<tr>
				<td class="form_label" width="40%">Tempo de vigência de <b>Cessão de Uso</b> (em anos):</td>
				<td><html:text  property="tempoCessao" maxlength="3" size="3" styleId="tempoCessao" onkeyup="DigitaNumero(this);" onblur="DigitaNumero(this);"/></td>
			</tr>
			
			<tr>
				<td class="form_label"></td>
				<td><br></td>
			</tr>
			<tr>
				<td colspan="2"><h5>Obs. Os parâmetros com conteúdo zerado ou vazio serão desconsiderados no monitoramento da agenda.</h5></td>
			</tr>
			<tr>
				<td class=""></td>
				<td><br></td>
			</tr>
		</table>
		<h2>Lista de e-mails que receberão as informações monitoradas na Agenda</h2>
		<table class="form_tabela" cellspacing="0" width="100%">
			<tr>
				<td class="form_label" colspan="2">
						<table cellspacing="0" class="form_tabela" width=100% align="left">
							<tr>
								<td class="form_label" width="10%">* E-mail:</td>
								<td width="90%" align="left"><html:text  property="email" maxlength="150" size="100" styleId="email"/> <a href="javascript:atualizarEmail();"><img src="${icon_adicionar}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:atualizarEmail()">Adicionar</a></td>
							</tr>
							<tr>
								<td class="form_label"></td>
								<td>
									<div id="pesquisaAjax">
										<tiles:insert page="/pages/agenda/_edit_manterParametroAgenda.jsp" />
									</div>
								</td>
							</tr>
						</table>
				</td>
				<td class="form_label"></td>
			</tr>
		</table>
	
   		<hr>
  		<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
 		<div align="center">
			<html:button property="btnSalvar" styleClass="form_botao" value="Confirmar" onclick="incluirAlterar();" />
			<html:button property="btnCancelar" styleClass="form_botao" value="Voltar" onclick="voltar();" />
		</div>	
	</div>

</html:form>

</div>
</div>