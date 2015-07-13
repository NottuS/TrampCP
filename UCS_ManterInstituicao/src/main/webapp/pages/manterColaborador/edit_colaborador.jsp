<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>


<c:url var="js_maisinfo" value="/js/generic/maisinfo.js" />
<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_data" value="/js/generic/data.js" />
<c:url var="js_cpf" value="/js/generic/cpf.js" />
<c:url var="js_fone" value="/js/generic/fone.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='icon_info' value='/images/icon_info.png' />
<c:url var='icon_calendar' value='/images/icon_calendario.png' />
<c:url var='link_pesquisar' value='/manterColaborador.do?action=pesquisarColaboradores' />
<c:url var="js_prototype" value="/js/generic/prototype-1.6.0.3.js" />

<script language="JavaScript" type="text/JavaScript" src="${js_prototype}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_maisinfo}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_cpf}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_fone}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">

	document.observe("dom:loaded", function() {
		console.log(document.manterColaboradorForm.actionType.value);
		if(document.manterColaboradorForm.actionType.value === "encerrarVinculo"){
			var Form = document.getElementsByName('manterColaboradorForm')[0];
			for(var i = 0; i < Form.elements.length; i++){
				if(Form.elements[i].id !== "dataDemissao" && Form.elements[i].type !== "button" && Form.elements[i].type !== "hidden" ){
					Form.elements[i].disabled = true;
				}
			}
		}
	});
	
	function voltar(){	
		document.manterColaboradorForm.action="${link_pesquisar}";
		document.manterColaboradorForm.submit();
	}

	function encerraVinculo(){
		var dataDemissao = document.manterColaboradorForm.dataDemissao.value;
		if(dataDemissao.length === 0){
			alert("Data de demissão não informada!");
			return false;
		}
		var dataAtual = new Date();
		var parts = dataDemissao.split('/');
		dataDemissao = new Date(parts[2], parts[1] - 1, parts[0], 23, 59, 59);
		var dataLimite = new Date();
		dataLimite.setDate(dataAtual.getDate() + 10);
		if(dataDemissao.valueOf() < dataAtual.valueOf() || dataDemissao.valueOf() > dataLimite.valueOf() ){
			alert("A data de demissão deve ser maio que a data atual e igual ou inferior a data atual + 10 dias.");
			return false;
		}
		
		document.manterColaboradorForm.submit();
	}
	
	function validarCampos(){
		
		var form = document.manterColaboradorForm;

		Trim(form.cpf);
		if(form.cpf.value.length === 0){
			return false;
		} 

		Trim(form.rg);
		if(form.rg.value.length === 0){
			return false;
		}

		if(form.sexo.value.length <= 0){
			return false;
		}

		Trim(form.telefone);
		if(form.telefone.value.length === 0){
			return false;
		}

		Trim(form.celular);
		if(form.celular.value.length === 0){
			return false;
		}

		Trim(form.email);
		if(form.email.value.length === 0){
			return false;
		}
		if(form.instituicao.value === ""){
			return false;
		}

		Trim(form.dataNascimento);
		if(form.dataNascimento.value.length === 0){
			return false;
		}

		Trim(form.dataAdmissao);
		if(form.dataAdmissao.value.length === 0){
			return false;
		}
		
		return true;
	}

	function gravar() {
		if(!validarCampos()){
			alert("Os campos marcados com * são de preenchimento obrigatórios");
			return false;
		}
		DesmascaraCPF(document.manterColaboradorForm.cpf);
		document.manterColaboradorForm.submit();
	}
</script>

<div id="conteudo">
	
	<div align="center">
	<h1 align="left">Manter Colaborador</h1>

	<html:form action="/manterColaborador.do?action=salvarColaborador" onsubmit="return gravar();">
	<html:hidden property="idColaborador" value="${manterColaboradorForm.idColaborador}"/>
	<html:hidden property="actionType" value="${manterColaboradorForm.actionType}" />
	
    <div id="conteudo_corpo">	
	<table class="form_tabela" cellspacing="0">
		<tr>
			<td class="form_label">*CPF:</td>
			<!-- TODO Crtl C here -->
			<td>
				<html:text size="14" maxlength="14" property="cpf" styleId="cpf" onblur="javascript:return(MascaraCPF(this, event));" onkeyup="javascript:return(MascaraCPF(this, event));" />
			</td>
			<td class="form_label">*RG:</td>
			<!-- TODO Crtl C here -->
			<td>
				<html:text size="20" maxlength="20" property="rg" styleId="rg" onblur="javascript:DigitaNumero(document.getElementById('rg'));" onkeyup="javascript:DigitaNumero(document.getElementById('rg'));" />
			</td>
		</tr>
		<tr>
			<td class="form_label">*Nome:</td>
			<td>
				<html:text size="100" maxlength="150" property="nome" styleId="nome" onkeyup="javascript:DigitaLetra(document.getElementById('nome'));" />
			</td>
		</tr>
		<tr>
			<td class="form_label">*Sexo:</td>
			<td>
		        <html:radio property="sexo" styleId="sexo" value="true" >
		            Masculino
		        </html:radio>
		        <html:radio property="sexo" styleId="sexo" value="false" >
		        	Feminino
		        </html:radio>
			</td>
			
			<td class="form_label">*Data Nascimento:</td>
			<td>
				<html:text styleId="dataNascimento" property="dataNascimento" size="8" maxlength="8" onkeypress="javascript:return(MascaraData(this,event));" />
				<img src="${icon_calendar}" width="16" height="15" id="btnCalendarNascimento" onclick="Calendario(document.getElementById('dataNascimento'),this.id);" />
				Ex: <fmt:formatDate value="${agora}" type="both" pattern="dd/MM/YY"/>
				<img src="${icon_info}" onMouseOver="maisinfo('Insira a data no formato dd/mm/aa ou clique no calendário ao lado.');" onMouseOut="menosinfo();"> 
			</td>	
		</tr>
		<tr>
			<td class="form_label">*Telefone:</td>
			<td>
				<html:text size="20" maxlength="20" property="telefone" styleId="telefone" onkeyup="javascript:return(MascaraFone(this, event));" />
			</td>
		</tr>
		<tr>
			<td class="form_label">*Celular:</td>
			<td>
				<html:text size="20" maxlength="20" property="celular" styleId="celular" onkeyup="javascript:return(MascaraFone(this, event));" />
			</td>
		</tr>
		<tr>
			<td class="form_label">*E-mail:</td>
			<td>
				<html:text size="100" maxlength="150" property="email" styleId="email"  />
			</td>
		</tr>
		<tr>
			<td class="form_label">*Instituição:</td>
			<td>
				<html:select property="instituicao" styleId="instituicao">
					<option value="" selected>Selecione</option>
					<c:forEach var="instituicao" items="${instituicoes}">
						<option value="${instituicao.codInstituicao}"  ${instituicao.codInstituicao eq manterColaboradorForm.instituicao ? 'selected' : ''} > 
							${instituicao.razaoSocial}
						</option>
					</c:forEach>
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="form_label">*Data Admissão:</td>
			<td>
				<html:text styleId="dataAdmissao" property="dataAdmissao" size="8" maxlength="8" onkeypress="javascript:return(MascaraData(this,event));" />
				<img src="${icon_calendar}" width="16" height="15" id="btnCalendarAdmissao" onclick="Calendario(document.getElementById('dataAdmissao'),this.id);" />
				Ex: <fmt:formatDate value="${agora}" type="both" pattern="dd/MM/YY"/>
				<img src="${icon_info}" onMouseOver="maisinfo('Insira a data no formato dd/mm/aa ou clique no calendário ao lado.');" onMouseOut="menosinfo();"> 
			</td>	
		</tr>
		<tr>
			<td class="form_label">Data Demissão:</td>
			<td>
				<html:text readonly="${manterColaboradorForm.actionType ne 'encerrarVinculo' ? true : false}" styleId="dataDemissao" property="dataDemissao" size="8" maxlength="8" onkeypress="javascript:return(MascaraData(this,event));" />
				<c:if test="${manterColaboradorForm.actionType eq 'encerrarVinculo'}">
					<img src="${icon_calendar}" width="16" height="15" id="btnCalendarDemissao" onclick="Calendario(document.getElementById('dataDemissao'),this.id);" />
					Ex: <fmt:formatDate value="${agora}" type="both" pattern="dd/MM/YY"/>
					<img src="${icon_info}" onMouseOver="maisinfo('Insira a data no formato dd/mm/aa ou clique no calendário ao lado.');" onMouseOut="menosinfo();">
				</c:if> 
			</td>	
		</tr>
	</table>
	</div>
   	<hr>
  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
 	<div align="center">
		 <!--<html:button property="btnSalvar" styleClass="form_botao" value="${titulo}" onclick="validarCampos();" />-->
		<c:if test="${manterColaboradorForm.actionType ne 'encerrarVinculo'}">
			<html:submit property="btnSalvar" styleId="btnSalvar" styleClass="form_botao" value="Gravar" />
		</c:if>
		<c:if test="${manterColaboradorForm.actionType eq 'encerrarVinculo'}"> 
			<html:button  property="btnEV" styleId="btnEV" styleClass="form_botao" value="Encerrar Vínculo" onclick="encerraVinculo();"/>
		</c:if>
		<html:button property="btnCancelar" styleClass="form_botao" value="Cancelar" onclick="voltar();" />
	</div>	
	
  </html:form>

  </div>
</div>