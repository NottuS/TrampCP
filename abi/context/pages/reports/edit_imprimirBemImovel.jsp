<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<c:url var="js_util" value="/js/generic/util.js" />
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<c:url var="js_prototype" value="/js/generic/prototype.js" />
<script language="JavaScript" type="text/JavaScript" src="${js_prototype}"></script>

<script language="javascript">

	function limpaMensagens() {
		if(document.getElementById("__msgVerificador")!=null)
		 	document.getElementById("__msgVerificador").innerHTML = "";
		if(document.getElementById("msg")!=null) 
			document.getElementById("msg").innerHTML = "";
		if(document.getElementById("msgs_servidor")!=null)
			document.getElementById("msgs_servidor").innerHTML = "";
	}

	function voltar(){	
		
		document.imprimirBemImovelForm.action="entrada.do";
		document.imprimirBemImovelForm.submit();
	}

	function carregarCamposPesquisaPadrao() {

		var form = document.imprimirBemImovelForm;			

		if (form.uf.value=='0'){
			form.uf.value='PR';
		}

		var tipoPesquisa0 = document.getElementsByName("tipoMunicipio")[0].checked;
		var tipoPesquisa1 = document.getElementsByName("tipoMunicipio")[1].checked;
		if (tipoPesquisa0) {
			document.getElementsByName("codMunicipio")[0].style.display="block";
			document.getElementById("faixaMunicipios").style.display="none";
			document.getElementById("faixaMunicipios").value="0";
		} else if (tipoPesquisa1) {
			document.getElementsByName("codMunicipio")[0].style.display="none";
			document.getElementById("faixaMunicipios").style.display="block";
			document.getElementsByName("codMunicipio")[0].value="0";
		}

		<c:if test="${listaOrgaos != null}">
			document.getElementById("tableCombo").style.display = "block";
		</c:if>
	}

	function carregarComboOrgao(tipoAdm) {
		if (tipoAdm != "") {
			document.getElementById("tableCombo").style.display="block";
			submitAjax('/abi/imprimirBemImovel.do?action=carregarComboOrgao', document.forms[0], 'divComboOrgao',false);
		}	
	}	

	function carregarComboMunicipio(valor) {
		if (valor == "1") {
			document.getElementsByName("codMunicipio")[0].style.display="block";
			document.getElementById("faixaMunicipios").style.display="none";
			document.getElementById("faixaMunicipios").value="0";
		} else if (valor == "2") {
			document.getElementsByName("codMunicipio")[0].style.display="none";
			document.getElementById("faixaMunicipios").style.display="block";
			document.getElementsByName("codMunicipio")[0].value="0";
		}
	}

	function validarCampos(){
		limpaMensagens();
		var form = document.imprimirBemImovelForm;
		var faixaMunicipios = document.getElementById("faixaMunicipios").value;
		if ((form.codMunicipio.value == "0") && (faixaMunicipios == "0") && !(form.radRelatorio3.checked)){
			alert("Campo Município obrigatório!");
			return false;
		}
			
		form.municipio.value = form.codMunicipio.options[form.codMunicipio.selectedIndex].text;
		form.situacaoImovel.value = form.codSituacaoImovel.options[form.codSituacaoImovel.selectedIndex].text;
		form.classificacaoBemImovel.value = form.codClassificacaoBemImovel.options[form.codClassificacaoBemImovel.selectedIndex].text;
		form.situacaoLegalCartorial.value = form.codSituacaoLegalCartorial.options[form.codSituacaoLegalCartorial.selectedIndex].text;
	
		gerar();
	}

	function gerar() {
		bloqueio();
		submitAjax("/abi/imprimirBemImovel.do?action=gerarRelatorio", document.forms[0], 'divPesquisaAjax', true);
	}

</script>

<body onload="carregarCamposPesquisaPadrao()">
	
<div id="_msg"></div>

<div id="conteudo">
	<div align="center">
	<h1>Imprimir Bem Imóvel </h1>

		<div id="divPesquisaAjax">
			<tiles:insert definition="processaRelatorioBemImovelDef"></tiles:insert>
		</div>

		<div id="conteudo_corpo"><h2>Opções de Impressão</h2>
		<html:form action="imprimirBemImovel.do?action=gerarRelatorio">
			<html:hidden property="indOperadorOrgao"/>
			<cep:main>
			<cep:form findOnType="true" codificacao="C" textoBusca="procurando...">
			<div style="display:none" align="right">
				<cep:cep name="cep" readonly="true"/>
				<cep:endereco name="endereco"  maxlength="72" size="46"/>
				<cep:bairro name="bairro"  maxlength="72" size="25"/>
			</div>
		  <table cellspacing="0" class="form_tabela" width="100%">
		   <c:if test="${!empty listaInstituicao}">
		  <tr>
				<td class="form_label" width="200">* Instituição:</td>
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
				<td class="form_label" align="right" style="width:11%;">Estado:</td>
				<td colspan="2"><cep:uf name="uf"/></td>
				
			</tr>
			<tr>
				<td class="form_label" align="right">* Município:</td>
				<td colspan="2">
					<html:radio property="tipoMunicipio" styleId="tipoMunicipio1" value="1" onclick="carregarComboMunicipio(this.value);">Específico</html:radio>
	          		<html:radio property="tipoMunicipio" styleId="tipoMunicipio2" value="2" onclick="carregarComboMunicipio(this.value);">Por Faixa</html:radio>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td colspan="2">
					<cep:municipio name="codMunicipio" value="${imprimirBemImovelForm.codMunicipio}" />
					<html:hidden name="municipio" property="municipio" value="${imprimirBemImovelForm.municipio}"/>	
					<html:hidden name="situacaoImovel" property="situacaoImovel" value="${imprimirBemImovelForm.situacaoImovel}"/>	
					<html:hidden name="classificacaoBemImovel" property="classificacaoBemImovel" value="${imprimirBemImovelForm.classificacaoBemImovel}"/>	
					<html:hidden name="situacaoLegalCartorial" property="situacaoLegalCartorial" value="${imprimirBemImovelForm.situacaoLegalCartorial}"/>	

					<html:select property="codFaixaMunicipio" styleId="faixaMunicipios" style="display:none">
						<html:option value="0"> < selecione > </html:option>
						<html:option value="-1"> < ...até C... > </html:option>
						<html:option value="-2"> < ...Curitiba... > </html:option>
						<html:option value="-3"> < de D... até F... > </html:option>
						<html:option value="-4"> < de G... até I... > </html:option>
						<html:option value="-5"> < de J... até M... > </html:option>
						<html:option value="-6"> < de N... até P... > </html:option>
						<html:option value="-7"> < de Q... até S... > </html:option>
						<html:option value="-8"> < de T... até Z... > </html:option>
					</html:select>
				</td>
			</tr>
			
            <tr>
				<td class="form_label">Classificação do imóvel:</td>
				<td colspan="2">
					<html:select property="codClassificacaoBemImovel">
						<html:option value="">- Todos -</html:option>
						<html:options collection="classificacaoBemImovels" property="codClassificacaoBemImovel" labelProperty="descricao" />						
					</html:select>
				</td>				
			</tr>
            <tr>
				<td class="form_label">Situação Legal - Cartorial:</td>
				<td colspan="2">
					<html:select property="codSituacaoLegalCartorial">
						<html:option value="">- Todos -</html:option>
						<html:options collection="situacaoLegalCartorials" property="codSituacaoLegalCartorial" labelProperty="descricao" />						
					</html:select>
				</td>				
			</tr>
			<tr>
				<td class="form_label">Situação do Imóvel:</td>
				<td colspan="2">
					<html:select property="codSituacaoImovel">
						<html:option value="">- Todos -</html:option>
						<html:options collection="situacaoImovels" property="codSituacaoImovel" labelProperty="descricao" />						
					</html:select>
				</td>				
			</tr>
			<tr>
				<td class="form_label">Ocupação:</td>
				<td colspan="2">
					<html:radio value="" property="conOcupacao" styleId ="conOcupacaoA" >Ambos</html:radio>
					<html:radio value="T" property="conOcupacao" styleId ="conOcupacaoT" >Terreno</html:radio>
					<html:radio value="E" property="conOcupacao" styleId ="conOcupacaoE" >Edificação</html:radio>
				</td>
			</tr>
            <tr>
              <td class="form_label">Terreno:</td>
              <td>
              	<html:radio property="radTerreno" styleId="radTerreno1" value="1" >Ambos</html:radio>
	          	<html:radio property="radTerreno" styleId="radTerreno2" value="2" >Com Edificação</html:radio>
	          	<html:radio property="radTerreno" styleId="radTerreno3" value="3" >Sem Edificação</html:radio>
              </td>
            </tr>
             <tr>
				<td class="form_label">Administração: </td>
				<td>			
					<html:radio value="" property="radAdministracao" styleId ="radAdministracaoDiretaA">Ambos</html:radio>	
					<html:radio value="1" property="radAdministracao" styleId ="radAdministracaoDireta1">Direta</html:radio>
					<html:radio value="2" property="radAdministracao" styleId ="radAdministracaoDireta2">Indireta</html:radio>
					<html:radio value="3" property="radAdministracao" styleId ="radAdministracaoDireta3">Terceiros</html:radio>
				</td>
			 </tr>
            <tr>
              <td class="form_label">Mostrar Relat&oacute;rio:</td>
              <td colspan="2">
              	<html:radio property="radRelatorio" styleId="radRelatorio1" value="1" >Ambos</html:radio>
	          	<html:radio property="radRelatorio" styleId="radRelatorio2" value="2" >Operacional</html:radio>
	          	<html:radio property="radRelatorio" styleId="radRelatorio3" value="3" >Gerencial</html:radio>
             </td>
             </tr>
             <c:if test="${imprimirBemImovelForm.indOperadorOrgao != 1}">
             <tr>
				<td colspan="3" width="100%">
					<fieldset dir="ltr">
					    <legend>
					    	<font class="form_label"> Órgão Responsável/Ocupante: </font></legend>
							<table id="tableCombo" class="form_tabela" width="100%">
								<tr>
									<td class="form_label" width="160">Administração:</td>
									<td>
										<html:radio value="" property="radAdministracaoOrgao" styleId ="radAdministracaoDiretaA" onclick="carregarComboOrgao();" >Ambos</html:radio>	
										<html:radio value="1" property="radAdministracaoOrgao" styleId ="radAdministracaoDireta1" onclick="carregarComboOrgao();">Direta</html:radio>
										<html:radio value="2" property="radAdministracaoOrgao" styleId ="radAdministracaoDireta2" onclick="carregarComboOrgao()">Indireta</html:radio>
										<html:radio value="3" property="radAdministracaoOrgao" styleId ="radAdministracaoDireta3" onclick="carregarComboOrgao()">Terceiros</html:radio>
									</td>
								</tr>
								<tr>
									<td class="form_label">Órgão: </td>
									<td id="colunaComboOrgao"> 
										<div id="divComboOrgao">
											<jsp:include page="/pages/reports/_edit_comboOrgaoTipoAdm.jsp"/>
										</div>
									</td>
								</tr>
							</table>
					  </fieldset>             
				</td>
			</tr>
             </c:if>
             
          </table>
          
		  <hr>
		  <p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
			<div align="center">
			  <html:button styleId="btnImprimir" property="btnImprimir" styleClass="form_botao" value="Imprimir" onclick="validarCampos()"/>	
			  <html:button styleId="btnVoltar" property="btnVoltar" styleClass="form_botao" value="Voltar" onclick="voltar();" />
		    </div>
		    <cep:box />
		  	</cep:form>
    		</cep:main>
		  </html:form>
		</div>

  </div>
  </div>
  
</body>