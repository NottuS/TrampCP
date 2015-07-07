<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var="icon_exibir" value="/images/icon_exibir.png"/>

<c:url var="link_entrada" value="/entrada.do"/>
<c:url var="link_pesquisar" value="/assinatura.do?action=pesquisarAssinatura"/>
<c:url var="link_editar" value="/assinatura.do?action=carregarPgEditAssinatura"/>
<c:url var="link_view" value="/assinatura.do?action=carregarPgViewAssinatura"/>

<c:url var="js_cpf" value="/js/generic/cpf.js"/>
<c:url var="js_funcoes" value="/js/generic/funcoes.js"/>
<c:url var="js_util" value="/js/generic/util.js"/>

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_cpf}"></script>

<script language="JavaScript">

	function incluir() {
		document.assinaturaForm.action='${link_editar}&actionType=incluir';
		document.assinaturaForm.submit();
	}

	function alterar(codigo) {
		document.assinaturaForm.action='${link_editar}&actionType=alterar&codAssinatura='+codigo;
		document.assinaturaForm.submit();
	}

	function excluir(codigo) {
		document.assinaturaForm.action='${link_view}&actionType=excluir&codAssinatura='+codigo;
		document.assinaturaForm.submit();
	}
	function exibir(codigo) {
		document.assinaturaForm.action='${link_view}&actionType=exibir&codAssinatura='+codigo;
		document.assinaturaForm.submit();
	}
	function inativar(codigo) {
		document.assinaturaForm.action='${link_view}&actionType=inativar&codAssinatura='+codigo;
		document.assinaturaForm.submit();
	}
	function pesquisar() {
		var form = document.forms[0];
		Trim(form.conCpf);
		if(form.conCpf.value != '') {
			if (!ValidaCPF(form.conCpf)){
				alert ('CPF inválido!');
				return false;
			}
		}
		if (document.forms[0].isGpAdmGeralUsuarioLogado.value == 'S'){
			if (form.conInstituicao.value == '0'){
				alert ('Selecione uma Instituição!');
				return false;
			}
		}

		document.assinaturaForm.action="${link_pesquisar}";
		document.assinaturaForm.submit();
	}

	function voltar() {
		document.assinaturaForm.action='${link_entrada}';
		document.assinaturaForm.submit();
	}

</script>

<body>
<div id="conteudo">
	<div align="center">
	<h1>Pesquisar Assinatura</h1>
	
	    <div id="conteudo_corpo">    
		    <html:form action="/assinatura.do?action=pesquisarAssinatura">
				<html:hidden property="pesqExec" value="${assinaturaForm.pesqExec}"/>
				<html:hidden property="isGpAdmGeralUsuarioLogado" value="${assinaturaForm.isGpAdmGeralUsuarioLogado}"/>
				<table class="form_tabela" cellspacing="0">

					<tr>
						<c:choose> 
							<c:when test="${assinaturaForm.isGpAdmGeralUsuarioLogado == 'S'}">
								<td class="form_label">
									* Instituição: 
								</td>
								<td>
									<html:select property="conInstituicao">
									 	<html:options collection="listaPesquisaInstituicao" property="codInstituicao" labelProperty="siglaDescricao" />						
								    </html:select>
								</td>
							</c:when>
							<c:otherwise>
								<td colspan="2">
									<html:hidden property="conInstituicao" value="${assinaturaForm.conInstituicao}"/>
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr>
						<td class="form_label">CPF: </td>
						<td>
							<html:text property="conCpf" size="17" maxlength="14" onkeyup="DigitaNumero(this);MascaraCPF(this,event);" onblur="DigitaNumero(this);MascaraCPF(this,event);"/>
						</td>
					</tr>
					<tr>
						<td class="form_label">Nome: </td>
						<td>
							<html:text maxlength="150" size="100" property="conNome" />
						</td>
					</tr>
				</table>
				<hr>
				<c:if test="${assinaturaForm.isGpAdmGeralUsuarioLogado == 'S'}">
				  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
				</c:if>
				<div align="center">
					<html:button styleClass="form_botao" value="Pesquisar" property="" onclick="pesquisar();"/>
					<html:button styleClass="form_botao" value="Incluir" property="" onclick="incluir();"/>
					<html:button styleClass="form_botao" value="Voltar" property="" onclick="voltar();"/>
				</div>
		    </html:form>
		</div>
		<c:if test="${!empty pagina.registros}">
		    <div id="conteudo_corpo">    
				<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
				    <ch:lista bean="${pagina}" atributoId="codAssinatura" />
			        <ch:action imagem="${icon_exibir}" link="javascript:exibir(%1);" label="Exibir" width="40" align="center" />
			        
					<ch:campo atributo="cpf" decorator="gov.pr.celepar.abi.util.CPFDecorator" label="<center>CPF</center>" width="10%" align="left"/>
			        <ch:campo atributo="nome" label="Nome" />
			        <ch:campo atributo="indResponsavelMaximoDesc" label="Responsável" />
			        <ch:campo atributo="orgao.siglaDescricao" label="Órgão" />
			        <ch:campo atributo="cargoAssinatura.descricao" label="Cargo" />
			
					<ch:campo atributo="instanciaAtual" label="Alterar" align="center" width="3%" decorator="gov.pr.celepar.abi.util.BotaoAlterarAssinaturaDecorator" />
					<ch:campo atributo="instanciaAtual" label="Excluir" align="center" width="3%" decorator="gov.pr.celepar.abi.util.BotaoExcluirAssinaturaDecorator" />
					<ch:campo atributo="instanciaAtual" label="Inativar" align="center" width="3%" decorator="gov.pr.celepar.abi.util.BotaoInativarAssinaturaDecorator" />
			        <ch:painel pagina="${link_pesquisar}" classe="painel" atributoIndice="indice" />
				</ch:table>  
			</div>
		</c:if>
	</div>
</div>
</body>

