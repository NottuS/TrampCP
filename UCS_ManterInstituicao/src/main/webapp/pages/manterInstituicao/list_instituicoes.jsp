<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var="js_data" value="/js/generic/data.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_cnpj" value="/js/generic/cnpj.js" />
<c:url var='icon_calendar' value='/images/icon_calendario.png' />
<c:url var='icon_exibir' value='/images/icon_exibir.png' />
<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='icon_info' value='/images/icon_info.png' />
<c:url var="js_maisinfo" value="/js/generic/maisinfo.js" />
<c:url var='link_entrada' value='/entrada.do' />
<c:url var='parametroPesquisarIntituicao' value="&cnpj=${manterInstituicaoForm.cnpj}&razaoSocial=${manterInstituicaoForm.razaoSocial}&naturezaJuridica=${manterInstituicaoForm.naturezaJuridica}&porte=${manterInstituicaoForm.porte}&dataCadastroInicio=${manterInstituicaoForm.dataCadastroInicio}&dataCadastroFim=${manterInstituicaoForm.dataCadastroFim}"></c:url>
<c:url var='link_pesquisar_instituicoes' value='/manterInstituicao.do?action=pesquisarInstituicoes${parametroPesquisarIntituicao}' />
<c:url var='link_editar_instituicao' value='/manterInstituicao.do?action=carregarPgEditInstituicao' />
<c:url var='link_view_instituicao' value='/manterInstituicao.do?action=carregarPgViewInstituicao' />

<script language="JavaScript" type="text/JavaScript" src="${js_maisinfo}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_cnpj}"></script>


<script language="JavaScript">
	
	function incluir() {
		document.manterInstituicaoForm.action="${link_editar_instituicao}&actionType=incluir";
		document.manterInstituicaoForm.submit();
	}
	
	function cancelar() {	
		document.manterInstituicaoForm.action="${link_entrada}";
		document.manterInstituicaoForm.submit();
	}


	function validarCampos(){

		var form = document.manterInstituicaoForm;
		var peloMenosUm = false;
		
		Trim(form.cnpj);
		if (form.cnpj.value.length !== 0 && !ValidaCNPJ(form.cnpj)){
			alert("CNPJ inválido!");
			form.cnpj.focus();
			return false;
		}
		if(form.cnpj.value.length !== 0){
			peloMenosUm = true;
		}
			
		
		Trim(form.razaoSocial);
		if (form.razaoSocial.value.length !== 0 && form.razaoSocial.value.length < 4){
			alert("Favor informar no mínimo (4) letras para efetuar a pesquisa por Razão Social.");
			form.razaoSocial.focus();
			return false;			
		}
		if (form.razaoSocial.value.length !== 0){
			peloMenosUm = true;
		}
		
		if(form.naturezaJuridica.value !== "0"){
			peloMenosUm = true;
		} /*else {
			document.manterInstituicaoForm.naturezaJuridica.value = "";
		}*/
		
		
		if(form.porte.value.length !== 0){
			peloMenosUm = true;
		}
		/*if(form.porte.value === "0"){
			document.manterInstituicaoForm.porte.value = "";	
		}*/
		
		Trim(form.dataCadastroInicio);
		if (form.dataCadastroInicio.value.length !== 0 && !ValidaData(form.dataCadastroInicio)){
			alert("Campo 'Data Cadastro inicio' deve ser uma data válida!");
			form.dataCadastroInicio.focus();
			return false;			
		}
		if(form.dataCadastroInicio.value.length !== 0){
			peloMenosUm = true;
		}
		
		Trim(form.dataCadastroFim);
		if (form.dataCadastroFim.value.length !== 0 && !ValidaData(form.dataCadastroFim)){
			alert("Campo 'Data Cadastro fim' deve ser uma data válida!");
			form.dataCadastroFim.focus();
			return false;			
		}
		if(form.dataCadastroFim.value.length !== 0){
			peloMenosUm = true;
		}
		
		if(peloMenosUm){
			form.submit();
			//return true;
		} else {
			alert("Atenção, favor selecionar um filtro de pesquisa.");
			return false;
		}
	}
	
</script>

<div id="conteudo">
	<div align="center">
	<h1>Cadastro de Instituição</h1>
	
    <div id="conteudo_corpo">    
    <html:form action="/manterInstituicao.do?action=pesquisarInstituicoes" method="post" onsubmit="return validarCampos()">
		<table class="form_tabela" cellspacing="0">
			<tr>
				<td class="form_label">CNPJ:</td>
				<!-- TODO Crtl C here -->
				<td>
					<html:text size="18" maxlength="18" property="cnpj" styleId="cnpj" onblur="javascript:return(MascaraCNPJ(this, event));" onchange="javascript:return(MascaraCNPJ(this, event));" />
				</td>
			</tr>
			<tr>
				<td class="form_label">Razão social:</td>
				<td>
					<html:text size="100" maxlength="100" property="razaoSocial" styleId="razaoSocial" onkeyup="javascript:DigitaLetra(document.getElementById('razaoSocial'));" />
				</td>
			</tr>
			<tr>
				<!-- TODO essa joça n vem selecionada na tela de resultado -->
				<td class="form_label">Natureza Jurídica:</td>
				<td>
					<html:select property="naturezaJuridica" styleId="naturezaJuridica">
					<option value="0" >Todas</option>
					<c:forEach var="nj" items="${naturezaJuridicas}">
				        <option value="${nj.codNaturezaJuridica}"  ${njSelected eq nj.codNaturezaJuridica ? 'selected' : ''}>
				            <!-- ${nj.codNaturezaJuridica}-${nj.descricao} -->
				            ${nj.mascara} 
				        </option>
				    </c:forEach>
					</html:select> 
				</td>
			</tr>
			<tr>
				<td class="form_label">Porte:</td>
				<td>
					<html:radio property="porte" styleId="porte" value="0">Todos</html:radio>
					<c:forEach var="porte" items="${sessionScope.portes}">
				        <html:radio  property="porte" styleId="porte" value="${porte.codigo}" >
				            ${porte.descricao} 
				        </html:radio>
				    </c:forEach>
				</td>
			</tr>
			<tr>
				<td class="form_label">Data Cadastro de:</td>
				<td>
					<html:text styleId="dataCadastroInicio" property="dataCadastroInicio" size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));" />
					<img src="${icon_calendar}" width="16" height="15" id="btnCalendarInicio" onclick="Calendario(document.getElementById('dataCadastroInicio'),this.id);" />
					Ex: <fmt:formatDate value="${agora}" type="both" pattern="dd/MM/yyyy"/>
					<img src="${icon_info}" onMouseOver="maisinfo('Insira a data de nascimento no formato dd/mm/aaaa ou clique no calendário ao lado.');" onMouseOut="menosinfo();"> 
					até
					<html:text styleId="dataCadastroFim" property="dataCadastroFim" size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));" />
					<img src="${icon_calendar}" width="16" height="15" id="btnCalendarFim" onclick="Calendario(document.getElementById('dataCadastroFim'),this.id);" />
					Ex: <fmt:formatDate value="${agora}" type="both" pattern="dd/MM/yyyy"/>
					<img src="${icon_info}" onMouseOver="maisinfo('Insira a data de nascimento no formato dd/mm/aaaa ou clique no calendário ao lado.');" onMouseOut="menosinfo();">
				</td>
			</tr>
		</table>
		<hr>
		<div align="center">
			<html:submit styleClass="form_botao" value="Pesquisar" property="" />
			<html:button styleClass="form_botao" value="Incluir" property="" onclick="incluir();"/>
			<html:button styleClass="form_botao" value="Cancelar" property="" onclick="cancelar();"/>
		</div>  
    </html:form>
    </div>
  
  <c:if test="${not empty pagina.registros}">
	  <div id="conteudo_corpo">
	  <ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
		    <ch:lista bean="${pagina}" atributoId="codInstituicao,cnpj" />
	        <ch:action imagem="${icon_exibir}" link="${link_view_instituicao}&codInstituicao=%1&actionType=exibir" label="Exibir" width="40" align="center" />
	        <ch:campo atributo="cnpj" label="CNPJ" align="center" decorator="gov.pr.celepar.ucs_manterinstituicao.decorator.CNPJ"/>
	        <ch:campo atributo="razaoSocial" label="Razão Social" width="100" align="center" />
	        <ch:campo atributo="naturezaJuridica" label="Natureza Jurídica" align="center" decorator="gov.pr.celepar.ucs_manterinstituicao.decorator.NaturezaJuridicaDecorator"/>
			<ch:campo atributo="porte" label="Porte" align="center" decorator="gov.pr.celepar.ucs_manterinstituicao.decorator.PorteDecorator"/>
	        <ch:action imagem="${icon_alterar}"  link="${link_editar_instituicao}&codInstituicao=%1&actionType=alterar" label="Alterar" width="40" align="center" />
	        <ch:action imagem="${icon_excluir}" link="${link_view_instituicao}&codInstituicao=%1&actionType=excluir" label="Excluir" width="40" align="center" />
	        <ch:painel pagina="${link_pesquisar_instituicoes}" classe="painel" atributoIndice="indice" />
	  </ch:table>  
	  </div>
  </c:if>
</div>
</div>



