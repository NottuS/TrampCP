<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<c:url var="js_maisinfo" value="/js/generic/maisinfo.js" />
<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_data" value="/js/generic/data.js" />
<c:url var="js_cpf" value="/js/generic/cpf.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='icon_info' value='/images/icon_info.png' />
<c:url var='icon_calendar' value='/images/icon_calendario.png' />
<c:url var='link_pesquisar' value='/matricula.do?action=carregarPgListAlunos' />

<jsp:useBean id="agora" class="java.util.Date" />

<script language="JavaScript" type="text/JavaScript" src="${js_maisinfo}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_cpf}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">
	function voltar(){	
		document.matriculaForm.action="${link_pesquisar}";
		document.matriculaForm.submit();
	}
	
	function validarCampos(){
	
		var form = document.matriculaForm;
	
		/*** Valida nome do Aluno ***/
		Trim(form.nomeAluno);
		if (form.nomeAluno.value == ""){
			alert("Campo 'Nome' é obrigatório!");
			form.nomeAluno.focus();
			return false;
		}

		
		/*** Valida data nascimento ***/
		Trim(form.dtNascAluno);
		if (form.dtNascAluno.value == ""){
			alert("Campo 'Data Nascimento' é obrigatório!");
			form.dtNascAluno.focus();
			return false;			
		}

		if (!ValidaData(form.dtNascAluno)){
			alert("Campo 'Data Nascimento' deve ser uma data válida!");
			form.dtNascAluno.focus();
			return false;			
		}


		/*** Valida nome da mãe ***/
		Trim(form.nomeMaeAluno);
		if (form.nomeMaeAluno.value == ""){
			alert("Campo 'Nome da Mãe' é obrigatório!");
			form.nomeMaeAluno.focus();
			return false;
		}


		/*** Valida CPF ***/
		Trim(form.cpfAluno);
		if (form.cpfAluno.value == ""){
			alert("Campo 'CPF' é obrigatório!");
			form.cpfAluno.focus();
			return false;			
		}
		if (!ValidaCPF(form.cpfAluno)){
			alert("CPF inválido!");
			form.cpfAluno.focus();
			return false;
			
		}

		
		/*** Valida Logradouro ***/
		Trim(form.logradouro);
		if (form.logradouro.value == ""){
			alert("Campo 'Logradouro' é obrigatório!");
			form.logradouro.focus();
			return false;			
		}

		
		/*** Valida Número ***/
		Trim(form.numero);
		if (form.numero.value == ""){
			alert("Campo 'Número' é obrigatório!");
			form.numero.focus();
			return false;			
		}

		/*** Valida UF ***/
		if (form.uf.value == "0"){
			alert("Campo 'UF' é obrigatório!");
			form.uf.focus();
			return false;			
		}

		/*** Valida Cidade ***/
		if (form.idLocalidade.value == "0"){
			alert("Campo 'Cidade' é obrigatório!");
			form.idLocalidade.focus();
			return false;			
		}

		/*** Valida CEP ***/
		Trim(form.cep);
		if (form.cep.value == ""){
			alert("Campo 'CEP' é obrigatório!");
			form.cep.focus();
			return false;			
		}

		/*** Insere o texto da combo selecionado no campo cidade ***/
		document.matriculaForm.cidade.value = document.matriculaForm.idLocalidade.options[document.matriculaForm.idLocalidade.selectedIndex].text;

		/*** Envia o formulário para o servidor ***/
		form.submit();
	}
	
</script>

	<c:choose> 
		<c:when test='${matriculaForm.actionType == "incluir"}'>
			<c:set var="acao" value="matricula.do?action=salvarAluno"></c:set>
			<c:set var="titulo" value="Incluir"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="acao" value="matricula.do?action=alterarAluno"></c:set>
			<c:set var="titulo" value="Alterar"></c:set>
		</c:otherwise>
	</c:choose>


<div id="conteudo">
	<div align="center">
	<h1>${titulo} Aluno</h1>

	<html:form action="${acao}" onsubmit="return validarCampos()">
	<!--html:form action="${acao}" -->	
	<html:hidden property="idAluno" value="${matriculaForm.idAluno}"/>
	<html:hidden property="cidade" value="" />
	<html:hidden property="actionType" value="${matriculaForm.actionType}" />
	
     <div id="conteudo_corpo">	
	 <table class="form_tabela" cellspacing="0" width="100%">
		<tr>
			<td class="form_label">*Nome</td>
			<td><html:text  property="nomeAluno" maxlength="128" size="50" styleId="nomeAluno" onkeyup="javascript:DigitaLetra(document.getElementById('nomeAluno'))" /></td>
		</tr>
		<tr>
			<td class="form_label">*Data Nascimento</td>
			<td>
				<html:text styleId="dtNascAluno" property="dtNascAluno" size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));" />
				<img src="${icon_calendar}" width="16" height="15" id="btnCalendar" onclick="Calendario(document.getElementById('dtNascAluno'),this.id);" />
				Ex: <fmt:formatDate value="${agora}" type="both" pattern="dd/MM/yyyy"/>
				<img src="${icon_info}" onMouseOver="maisinfo('Insira a data de nascimento no formato dd/mm/aaaa ou clique no calendário ao lado.');" onMouseOut="menosinfo();"> 
			</td>
		</tr>
		<tr>
			<td class="form_label">Nome do Pai</td>
			<td><html:text  property="nomePaiAluno" maxlength="128" size="50" styleId="nomePaiAluno" onkeyup="javascript:DigitaLetra(document.getElementById('nomePaiAluno'))" /></td>
		</tr>
		<tr>
			<td class="form_label">*Nome da Mãe</td>
			<td><html:text  property="nomeMaeAluno" maxlength="128" size="50" styleId="nomeMaeAluno" onkeyup="javascript:DigitaLetra(document.getElementById('nomeMaeAluno'))" /></td>
		</tr>
		<tr>
			<td class="form_label">*CPF</td>
			<td><html:text  property="cpfAluno" maxlength="15" size="15" onkeypress="javascript:return(MascaraCPF(this,event));" /></td>
		</tr>
	 </table>
   </div>
  
   <div id="conteudo_corpo">
   <cep:main>
   <cep:form findOnType="false">
    <table class="form_tabela" cellspacing="0" width="100%">
  	 <tr>
		<td class="form_label">*UF</td>
		<td><cep:uf name="uf" value="${matriculaForm.uf}" /></td>
	 </tr>
	 <tr>
		<td class="form_label">*Cidade</td>
		<td><cep:municipio name="idLocalidade" value="${matriculaForm.idLocalidade}" /></td>
	 </tr>
	 <tr>
		<td class="form_label">*CEP</td>
		<td><cep:cep name="cep" value="${matriculaForm.cep}" size="8" maxlength="8" /></td>
 	 </tr>
	 <tr>
		<td class="form_label">*Logradouro</td>
		<td><cep:endereco name="logradouro" value="${matriculaForm.logradouro}" size="50" maxlength="128" /></td>
	 </tr>

	 <tr>
		<td class="form_label">*Número</td>
		<td><cep:numero name="numero" value="${matriculaForm.numero}" size="16" maxlength="16" /></td>
	 </tr>
	 <tr>
		<td class="form_label">Complemento</td>
		<td><cep:complemento name="complemento" value="${matriculaForm.complemento}" size="16" maxlength="16" /></td>
	 </tr>
	 <tr>
		<td class="form_label">Bairro</td>
		<td><cep:bairro name="bairro" value="${matriculaForm.bairro}" size="50" maxlength="64" /></td>
	 </tr>
   </table>
  <cep:box />
  </cep:form>
  </cep:main>
  
   	<hr>
  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
 	<div align="center">
		 <!--<html:button property="btnSalvar" styleClass="form_botao" value="${titulo}" onclick="validarCampos();" />-->
		<html:submit property="btnSalvar" styleClass="form_botao" value="${titulo}" />
		<html:button property="btnCancelar" styleClass="form_botao" value="Cancelar" onclick="voltar();" />
	</div>	
	
  </div>

  </html:form>

  </div>
</div>