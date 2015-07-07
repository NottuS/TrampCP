<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch"%>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<c:url var="js_util" value="/js/generic/util.js" />
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">

	function selecionarTodos(objListBoxName) {
		var s = document.getElementsByTagName("select");			
		var s1;
		if (s.length > 0) {
			window.select_current = new Array();
			for (var i = 0, select; select = s[i]; i++) {
				if(select.name == objListBoxName){
				  	s1 = select;
			      	for (var intLoop=0; intLoop < s1.length; intLoop++) {															      
			         	s1[intLoop].selected = true;
			      	}
												
				}
			}
		}			
	}
	
	function desmarcarTodos(objListBoxName) {
		var s = document.getElementsByTagName("select");			
		var s1;
		if (s.length > 0) {
			window.select_current = new Array();
			for (var i = 0, select; select = s[i]; i++) {
				if(select.name == objListBoxName){
				  	s1 = select;
			      	for (var intLoop=0; intLoop < s1.length; intLoop++) {															      
			         	s1[intLoop].selected = false;
			      	}
												
				}
			}
		}			
	}

	function disponiveis(arg){	
		for(i = 0; i < document.forms[0].listaTipoEdificacaoDisponivel.length; i++)
			document.forms[0].listaTipoEdificacaoDisponivel[i].selected = arg;
	}

	function selecionadas(arg){
		for(i = 0; i < document.forms[0].listaTipoEdificacaoSelecionada.length; i++)
			document.forms[0].listaTipoEdificacaoSelecionada[i].selected = arg;
	}

	function exibirFiltroOcupacao(exibir) {
		var display = (exibir ? '' : 'none');
		var i, tr;
		for(i = 0; (tr = document.getElementById('filtroOcupacao' + i.toString())) != null; i++) {
			tr.style.display = display;
		}
	}

	function voltar() {	
		document.imprimirEdificacaoBemImovelForm.action="entrada.do";
		document.imprimirEdificacaoBemImovelForm.submit();
	}

	function carregarCamposPesquisaPadrao() {

		var form = document.imprimirEdificacaoBemImovelForm;			

		if (form.uf.value=='0'){
			form.uf.value='PR';
		}
	}

	function imprimir () {
		disponiveis(true);    
	  	selecionadas(true);
		var form = document.imprimirEdificacaoBemImovelForm;
		form.municipioDescricao.value = form.codMunicipio.options[form.codMunicipio.selectedIndex].text;
		form.tipoConstrucaoDescricao.value = form.codTipoConstrucao.options[form.codTipoConstrucao.selectedIndex].text;
		form.orgaoSiglaDescricao.value = form.codOrgao.options[form.codOrgao.selectedIndex].text;
		form.situacaoOcupacaoDescricao.value = form.codSituacaoOcupacao.options[form.codSituacaoOcupacao.selectedIndex].text;
		form.submit();
	}
		
</script>

<body onload="carregarCamposPesquisaPadrao()">
	

<div id="conteudo">
	<div align="center">
	<h1>Imprimir Edificação do Bem Imóvel </h1>

		<div id="conteudo_corpo"><h2>Opções de Impressão</h2>
		<html:form action="imprimirEdificacaoBemImovel.do?action=gerarRelatorio" onsubmit="return imprimir();">
		  <html:hidden property="indOperadorOrgao"/>
		  <cep:main>
		  <cep:form findOnType="true" codificacao="C" textoBusca="procurando...">
		  <div style="display:none">
		    <html:hidden name="municipioDescricao" property="municipioDescricao" value="${imprimirEdificacaoBemImovelForm.municipioDescricao}"/>	
			<html:hidden name="tipoConstrucaoDescricao" property="tipoConstrucaoDescricao" value="${imprimirEdificacaoBemImovelForm.tipoConstrucaoDescricao}"/>	
			<html:hidden name="tipoEdificacaoDescricao" property="tipoEdificacaoDescricao" value="${imprimirEdificacaoBemImovelForm.tipoEdificacaoDescricao}"/>
			<html:hidden name="orgaoSiglaDescricao" property="orgaoSiglaDescricao" value="${imprimirEdificacaoBemImovelForm.orgaoSiglaDescricao}"/>
			<html:hidden name="situacaoOcupacaoDescricao" property="situacaoOcupacaoDescricao" value="${imprimirEdificacaoBemImovelForm.situacaoOcupacaoDescricao}"/>
			<cep:cep name="cep" value="" readonly="true"/>
			<cep:endereco name="endereco" value=""/>
			<cep:bairro name="bairro" value=""/>
		  </div>
		  <table cellspacing="0" class="form_tabela">
		   <c:if test="${!empty listaInstituicao}">
		  <tr>
				<td class="form_label" >* Instituição:</td>
				<td>
					<html:select styleId="instituicao"  property="codInstituicao">
						<html:option value="">- Selecione -</html:option>
						<html:options collection="listaInstituicao"	property="codInstituicao" labelProperty="nome" />
					</html:select>
				</td>
			</tr>
			</c:if>
			<c:if test="${empty listaInstituicao}">
				<html:hidden property="codInstituicao" />
			</c:if>
            <tr>
				<td class="form_label" style="width: 140px;">Estado: </td>
				<td><cep:uf name="uf"/></td>
			</tr>
			<tr>
				<td class="form_label" style="width: 140px;">Município: </td>
				<td>
					<cep:municipio name="codMunicipio" value="${imprimirEdificacaoBemImovelForm.codMunicipio}"/>			
				</td>
			</tr>
			
            <tr>
				<td class="form_label" style="width: 140px;">Tipo de Construção: </td>
				<td>
					<html:select property="codTipoConstrucao">
						<html:option value="">- Todos -</html:option>
						<html:options collection="tipoConstrucaos" property="codTipoConstrucao" labelProperty="descricao" />						
					</html:select>
				</td>				
			</tr>
            <tr>
				<td class="form_label" style="width: 140px;">Tipo de Edificação: </td>
				<td>
					<div align="center">
						<ch:select classe="form_tabela" ordenada="true">
							<ch:esquerda combo="listaTipoEdificacaoDisponivel" label="Tipos de Edificações NÃO Associadas" onError="alert('Selecione um Tipo de Edificação!');" classe="lista_select">
								<c:if test="${listaTipoEdificacaoDisponivel != null}">
									<ch:opcoes colecao="listaTipoEdificacaoDisponivel" nome="descricao" valor="codTipoEdificacao"/>
								</c:if>
							</ch:esquerda>
							<ch:direita  combo="listaTipoEdificacaoSelecionada" label="Tipos de Edificações Associadas" onError="alert('Selecione um Tipo de Edificação!');" classe="lista_select">
								<c:if test="${listaTipoEdificacaoSelecionada != null}">
									<ch:opcoes colecao="listaTipoEdificacaoSelecionada" nome="descricao" valor="codTipoEdificacao"/>
								</c:if>
							</ch:direita>
						</ch:select>
					</div>
				</td>				
			</tr>
            <tr>
				<td class="form_label" style="width: 140px;">Incluir Ocupações: </td>
				<td>
					<html:radio property="radIncluirOcupacoes" styleId="radIncluirOcupacoes1" value="1" onchange="javascript: exibirFiltroOcupacao(true);">Sim</html:radio>
					<html:radio property="radIncluirOcupacoes" styleId="radIncluirOcupacoes2" value="2" onchange="javascript: exibirFiltroOcupacao(false);">Não</html:radio>
				</td>
            </tr>
            <tr id="filtroOcupacao0">
				<td class="form_label" style="width: 140px;">Orgão: </td>
				<td>
					<html:select property="codOrgao">
						<html:option value="">- Todos -</html:option>
						<html:options collection="orgaos" property="codigo" labelProperty="descricao"/>
					</html:select>
				</td>
			</tr>
			<tr id="filtroOcupacao1">
				<td class="form_label" style="width: 140px;">Situação da Ocupação: </td>
				<td>
					<html:select property="codSituacaoOcupacao">
						<html:option value="">- Todos -</html:option>
						<html:options collection="situacaoOcupacaos" property="codSituacaoOcupacao" labelProperty="descricao" />						
					</html:select>
				</td>				
			</tr>
			<tr id="filtroOcupacao2">
				<td class="form_label" style="width: 140px;">Descrição da Ocupação: </td>
				<td>
					<html:text property="descricaoOcupacao" size="50" maxlength="100"/>
				</td>				
			</tr>
            <tr>
              <td class="form_label" style="width: 140px;">Averbação: </td>
              <td>
              	<html:radio property="radAverbacao" styleId="radAverbacao1" value="1">Ambos</html:radio>
	          	<html:radio property="radAverbacao" styleId="radAverbacao2" value="2">Averbado</html:radio>
	          	<html:radio property="radAverbacao" styleId="radAverbacao3" value="3">Não Averbado</html:radio>
              </td>
            </tr>
             <tr>
				<td class="form_label">Administração: </td>
				<td colspan="2">			
					<html:radio value="" property="radAdministracao" styleId ="radAdministracaoDiretaA"  >Ambos</html:radio>	
					<html:radio value="1" property="radAdministracao" styleId ="radAdministracaoDireta1" >Direta</html:radio>
					<html:radio value="2" property="radAdministracao" styleId ="radAdministracaoDireta2" >Indireta</html:radio>
					<html:radio value="3" property="radAdministracao" styleId ="radAdministracaoDireta3" >Terceiros</html:radio>
				</td>
			</tr>
            <tr>
              <td class="form_label" style="width: 140px;"> Mostrar Relat&oacute;rio: </td>
              <td>
              	<html:radio property="radRelatorio" styleId="radRelatorio1" value="1">Ambos</html:radio>
	          	<html:radio property="radRelatorio" styleId="radRelatorio2" value="2">Operacional</html:radio>
	          	<html:radio property="radRelatorio" styleId="radRelatorio3" value="3">Gerencial</html:radio>
             </td>
             </tr>
            <tr>
              <td class="form_label" style="width: 140px;"> Área da Edificação: </td>
              <td>
	             <html:text maxlength="19" size="20" property="areaMin" styleId="areaMin" onkeyup="javascript:DigitaNumMascara(this, -1);" />
	             &nbsp;&nbsp;&nbsp; até &nbsp;&nbsp;&nbsp;
	             <html:text maxlength="19" size="20" property="areaMax" styleId="areaMax" onkeyup="javascript:DigitaNumMascara(this, -1);" />
             </td>
             </tr>
          </table>
		   	<hr>
		   <c:if test="${!empty listaInstituicao}">
			  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
			</c:if>    
		  <div align="center">
			  <html:submit property="btnSalvar" styleClass="form_botao" value="Imprimir" onclick="imprimir();"/>
			  <html:button property="btnCancelar" styleClass="form_botao" value="Cancelar" onclick="voltar();"/>
		  </div>
		  <cep:box/>
		  </cep:form>
		  </cep:main>
		</html:form>
		</div>
	</div>
</div>
</body>