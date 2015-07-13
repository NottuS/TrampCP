<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var="js_data" value="/js/generic/data.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_cpf" value="/js/generic/cpf.js" />
<c:url var="js_prototype" value="/js/generic/prototype-1.6.0.3.js" />
<c:url var='icon_calendar' value='/images/icon_calendario.png' />
<c:url var='icon_simbolo_vermelho' value='/images/icon_msg_erro.png' />
<c:url var='icon_simbolo_verde' value='/images/icon_msg_sucesso.png' />
<c:url var='icon_exibir' value='/images/icon_exibir.png' />
<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='icon_info' value='/images/icon_info.png' />
<c:url var="js_maisinfo" value="/js/generic/maisinfo.js" />
<c:url var='link_entrada' value='/entrada.do' />
<c:url var='parametroPesquisarColaborador' value="&cpf=${manterColaboradorForm.cpf}&nome=${manterColaboradorForm.nome}&situacao=${manterColaboradorForm.situacao}&dataInicial=${manterColaboradorForm.dataInicial}&dataFinal=${manterColaboradorForm.dataFinal}"></c:url>
<c:url var='link_pesquisar_colaborador' value='/manterColaborador.do?action=pesquisarColaboradores${parametroPesquisarColaborador}' />
<c:url var='link_editar_colaborador' value='/manterColaborador.do?action=carregarPgEditColaborador' />
<c:url var='link_view_colaborador' value='/manterColaborador.do?action=carregarPgViewColaborador' />

<script language="JavaScript" type="text/JavaScript" src="${js_maisinfo}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_cpf}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_prototype}"></script>

<script language="JavaScript">
	function incluir() {
		document.manterColaboradorForm.action="${link_editar_colaborador}&actionType=incluir";
		document.manterColaboradorForm.submit();
	}
	
	function limpa() {	
		var Form = document.getElementsByName('manterColaboradorForm')[0];
		Form.cpf.value = "";
		console.log(Form.cpf.value);
		Form.nome.value = "";
		Form.dataInicial.value = "";
		Form.dataFinal.value = "";
		Form.situacao.selectedIndex = 0;
		//Form.reset();
	}

	function validarCampos(){
		var form = document.manterColaboradorForm;
		Trim(form.cpf);
		DesmascaraCPF(form.cpf);
		Trim(form.nome);
		form.submit();
	}
	
</script>

<div id="conteudo">
	<div align="center">
	<h1 align="left">Cadastro de Instituição</h1>
	
    <div id="conteudo_corpo">    
    <html:form action="/manterColaborador.do?action=pesquisarColaboradores" method="post" onsubmit="return validarCampos()">
		<table class="form_tabela" cellspacing="0">
			<tr>
				<td class="form_label">CPF:</td>
				<!-- TODO Crtl C here -->
				<td>
					<html:text  size="14" maxlength="14" property="cpf" styleId="cpf" onblur="javascript:return(MascaraCPF(this, event));" onkeyup="javascript:return(MascaraCPF(this, event));" />
				</td>
			</tr>
			<tr>
				<td class="form_label">Nome:</td>
				<td>
					<html:text size="100" maxlength="150" property="nome" styleId="nome" onkeyup="javascript:DigitaLetra(document.getElementById('nome'));" />
				</td>
			</tr>
			<tr>
				<!-- TODO essa joça n vem selecionada na tela de resultado -->
				<td class="form_label">Situação:</td>
				<td>
					<html:select property="situacao" styleId="situacao">
						<option value="" >Todos</option>
						<option value="Ativos" ${manterColaboradorForm.situacao eq 'Ativos' ? 'selected' : '' } >Somente Ativos</option>
						<option value="Inativos" ${manterColaboradorForm.situacao eq 'Inativos' ? 'selected' : '' } >Somente Inativos</option>
					</html:select> 
				</td>
			</tr>
			<tr>
				<td></td>
				<td>		
					<fieldset >
						<legend>Período de Admissão</legend>
						Data Inicial:
						<html:text styleId="dataInicial" property="dataInicial" size="8" maxlength="8" onkeypress="javascript:return(MascaraData(this,event));" />
						<img src="${icon_calendar}" width="16" height="15" id="btnCalendarInicio" onclick="Calendario(document.getElementById('dataInicial'),this.id);" />
						Ex: <fmt:formatDate value="${agora}" type="both" pattern="dd/MM/YY"/>
						<img src="${icon_info}" onMouseOver="maisinfo('Insira a data no formato dd/mm/aa ou clique no calendário ao lado.');" onMouseOut="menosinfo();"/> 
						Data Final:
						<html:text styleId="dataFinal" property="dataFinal" size="8" maxlength="8" onkeypress="javascript:return(MascaraData(this,event));" />
						<img src="${icon_calendar}" width="16" height="15" id="btnCalendarFim" onclick="Calendario(document.getElementById('dataFinal'),this.id);" />
						Ex: <fmt:formatDate value="${agora}" type="both" pattern="dd/MM/yy"/>
						<img src="${icon_info}" onMouseOver="maisinfo('Insira a data no formato dd/mm/aa ou clique no calendário ao lado.');" onMouseOut="menosinfo();"/>
					</fieldset>
				</td>
			</tr>
		</table>

		<hr>
		<div align="center">
			<html:button styleClass="form_botao" value="Limpar" property="" onclick="limpa();"/>
			<html:button styleClass="form_botao" value="Novo" property="" onclick="incluir();"/>
			<html:submit styleClass="form_botao" value="Pesquisar" property="" />
		</div>  
    </html:form>
    </div>
  
  <c:if test="${not empty pagina.registros}">
	  <div id="conteudo_corpo">
	  <ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
		    <ch:lista bean="${pagina}" atributoId="idColaborador,cpf" />
	        <ch:action imagem="${icon_exibir}" link="${link_view_colaborador}&idColaborador=%1&actionType=exibir" label="Exibir" width="40" align="center" />
	        <ch:campo atributo="cpf" label="CPF" align="center" decorator="gov.pr.celepar.ucs_manterinstituicao.decorator.CPFDecorator"/>
	        <ch:campo atributo="nome" label="Nome" width="100" align="center" />
	        <ch:campo atributo="instituicao.razaoSocial" label="Instituição" align="center" />
			<ch:campo atributo="dataAdmissao" label="Data Admissao" align="center" decorator="gov.pr.celepar.ucs_manterinstituicao.decorator.DataDecorator" />
			<ch:campo atributo="dataDemissao" label="Data Demissao" align="center" decorator="gov.pr.celepar.ucs_manterinstituicao.decorator.DataDecorator"/>
			<ch:campo atributo="ativo" label="Ativo" align="center"  decorator="gov.pr.celepar.ucs_manterinstituicao.decorator.AtivoDecorator"/>
	        <ch:action imagem="${icon_alterar}"  link="${link_editar_colaborador}&idColaborador=%1&actionType=alterar" label="Alterar" width="40" align="center" />
	        <ch:action imagem="${icon_excluir}" link="${link_editar_colaborador}&idColaborador=%1&actionType=encerrarVinculo" label="Encerrar Vinculo" width="40" align="center" />
	        <ch:painel pagina="${link_pesquisar_colaborador}" classe="painel" atributoIndice="indice" />
	  </ch:table>  
	  </div>
  </c:if>
</div>
</div>



