<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="js_util" value="/js/generic/util.js" />
<c:url var="js_prototype" value="/js/generic/prototype.js" />
<c:url var="js_cpf" value="/js/generic/cpf.js"/>
<c:url var="js_ajaxutil" value="/js/ajaxutil.js" />

<script language="JavaScript" type="text/JavaScript" src="${js_ajaxutil}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_cpf}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_prototype}"></script>

<c:url var='icon_alterar' value='/images/icon_alterar.png' />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />

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
		var form = document.manterUsuarioForm;
		if((document.getElementById("administracao1").checked || 
				document.getElementById("administracao2").checked || 
				document.getElementById("administracao3").checked) && form.conInstituicao.value!='' ){
			form.conOrgao.disabled=false;
			submitAjax('/abi/manterUsuario.do?action=carregarComboOrgaoPesquisa', document.forms[0], 'divComboOrgao',true);
		}else{
			form.conOrgao.disabled=true;
			form.conOrgao.value="";
		}
	}
	
	function pesquisar() {
		if(form.conCpf.value != "") {
			if (!ValidaCPF(form.conCpf)){
				form.conCpf.focus();
				alert ('CPF inválido!');
				return false;
			}
		}
		document.manterUsuarioForm.actionType.value = "pesquisar";
		document.manterUsuarioForm.action="manterUsuario.do?action=pesquisarManterUsuario";
		document.manterUsuarioForm.submit();
	}

	function validarCPF() {
		var form = document.manterUsuarioForm;
		if(form.conCpf.value != "") {
			if (!ValidaCPF(form.conCpf)){
				alert ('CPF inválido!');
			}
		}
	}

	function incluir(){
		document.manterUsuarioForm.actionType.value = "incluir";
		document.manterUsuarioForm.action="manterUsuario.do?action=carregarPgEditManterUsuario";
		document.manterUsuarioForm.submit();
	}
	
	function voltar() {
		document.manterUsuarioForm.action='entrada.do';
		document.manterUsuarioForm.submit();
	}

	function alterar(id) {
		document.manterUsuarioForm.actionType.value = "alterar";
		document.manterUsuarioForm.action="manterUsuario.do?action=carregarPgEditManterUsuario";
	    document.manterUsuarioForm.codUsuario.value = id;
	    document.manterUsuarioForm.submit();
	}
	
	function excluir(id) {
		document.manterUsuarioForm.actionType.value = "excluir";
		document.manterUsuarioForm.action="manterUsuario.do?action=carregarPgViewManterUsuario";
	    document.manterUsuarioForm.codUsuario.value = id;
	    document.manterUsuarioForm.submit();
	}

	function habilitaOrgao(){
		var form = document.manterUsuarioForm;
		if(form.conGrupo.value != ''){
			document.manterUsuarioForm.action="manterUsuario.do?action=verificaGrupoSelecionado";
		    document.manterUsuarioForm.submit();
		}
	}

	window.onload = function(){
		var form = document.manterUsuarioForm;
		if (form.desabilitaOrgao.value == 'false') {
			document.getElementById("administracao1").disabled=false;
			document.getElementById("administracao2").disabled=false;
			document.getElementById("administracao3").disabled=false;
			form.conOrgao.disabled=false;
		} else {
			document.getElementById("administracao1").disabled=true;
			document.getElementById("administracao2").disabled=true;
			document.getElementById("administracao3").disabled=true;
			form.conOrgao.disabled=true;
		}
	};

</script>

<body>

<div id="conteudo">
	<div align="center">
	<h1 >Pesquisar Usuário </h1>
		
	<div id="conteudo_corpo">
		<html:form action="manterUsuario.do?action=carregarPgListManterUsuario">
			<html:hidden property="codUsuario"/>
			<html:hidden property="actionType"/>
			<html:hidden property="pesqExec"/>
			<html:hidden property="desabilitaCampo"/>
			<html:hidden property="desabilitaOrgao"/>
			<table cellspacing="0" class="form_tabela" width="100%">
				<tr>
					<td class="form_label">Instituição:</td>
					<td>
						<html:select property="conInstituicao" onchange="javascript:buscarOrgao();">
							<html:option value="">-Todos-</html:option>
							<html:options collection="listaInstituicao" property="codInstituicao" labelProperty="nome"  />						
						</html:select>
					</td>				
				</tr>
				
				<tr>
					<td class="form_label">Grupo:</td>
					<td>
						<html:select property="conGrupo" onchange="javascript:habilitaOrgao();">
							<html:option value="">-Todos-</html:option>
							<html:options collection="listaGrupoPesquisa" property="codigo" labelProperty="descricao" />						
						</html:select>
					</td>				
				</tr>
			        
				<tr>
					<td class="form_label">Administração: </td>
					<td>
						<html:radio value="1" property="conAdministracao" styleId ="administracao1" onchange="javascript:buscarOrgao();">Direta</html:radio>
						<html:radio value="2" property="conAdministracao" styleId ="administracao2" onchange="javascript:buscarOrgao();">Indireta</html:radio>
						<html:radio value="3" property="conAdministracao" styleId ="administracao3" onchange="javascript:buscarOrgao();">Terceiros</html:radio>
					</td>
				</tr>
				<tr>
			 		<td class="form_label">Órgão: </td>
			 		<td>
						<div id="divComboOrgao">
							<jsp:include page="/pages/usuario/_con_comboOrgaoPesquisa.jsp"/>
						</div>
					</td>
				</tr>
			
				<tr>
					<td class="form_label">CPF: </td>
					<td>
						<html:text property="conCpf" size="17" maxlength="14" onkeyup="DigitaNumero(this);MascaraCPF(this,event);" onblur="DigitaNumero(this);MascaraCPF(this,event);" onchange="validarCPF();"/>
					</td>
				</tr>
			
				<tr>
					<td class="form_label">Nome: </td>
					<td>
						<html:text maxlength="100" size="100" property="conNome" />
					</td>
				</tr>
			
				<tr>
					<td class="form_label">Situação: </td>
					<td>
						<html:radio value="1" property="conSituacao" styleId ="conSituacao1">Ativo</html:radio>
						<html:radio value="0" property="conSituacao" styleId ="conSituacao2">Inativo</html:radio>
						<html:radio value="" property="conSituacao" styleId ="conSituacao3">Ambos</html:radio>
					</td>
				</tr>

			</table>
		  	<hr>
		 	<div align="center">
				<html:button property="" styleClass="form_botao" styleId="btnPesquisar" value="Pesquisar" onclick="pesquisar()"/>	
				<html:button property="" styleClass="form_botao" value="Incluir" onclick="incluir();" />
				<html:button property="" styleClass="form_botao" value="Voltar" onclick="voltar();" />
			 </div>
			</html:form>
		</div>
		<div id="pesquisaAjax"> 
			<c:if test="${!empty pagina && pagina.totalRegistros > 0}">
				<div id="conteudo_corpo">    
					<c:url var="link_parametros1" 	value="&conGrupo=${manterUsuarioForm.conGrupo}&conAdministracao=${manterUsuarioForm.conAdministracao}" />
					<c:url var="link_parametros2" 	value="&conOrgao=${manterUsuarioForm.conOrgao}&conCpf=${manterUsuarioForm.conCpf}" />
					<c:url var="link_parametros3" 	value="&conNome=${manterUsuarioForm.conNome}&conSituacao=${manterUsuarioForm.conSituacao}"/>
					<c:url var="link_pesquisar"		value="/manterUsuario.do?action=pesquisarManterUsuario${link_parametros1}${link_parametros2}${link_parametros3}" />		
			
					<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
						<ch:lista bean="${pagina}" atributoId="codUsuario" />
						<ch:campo atributo="instituicao" label="Instituição" align="left"/>
						<ch:campo atributo="usuario" label="Usuário" align="left"/>
						<ch:campo atributo="grupo" label="Grupo" align="left"/>
						<ch:campo atributo="orgao" label="Órgão" align="left"/>
						<ch:campo atributo="situacao" label="Situação" align="left"/>
					    <ch:action imagem="${icon_alterar}"  link="javascript:alterar(%1);" label="Alterar" width="3%" align="center" />
					    <ch:action imagem="${icon_excluir}"  link="javascript:excluir(%1);" label="Excluir" width="3%" align="center" />
						<ch:painel pagina="${link_pesquisar}" classe="painel" atributoIndice="indice" />
					</ch:table>			  
				</div>
			</c:if>
		</div>
	</div>
</div>
   
</body>