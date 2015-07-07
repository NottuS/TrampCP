<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var='icon_calendar' value='/images/icon_calendario.png' />
<c:url var="js_data" value="/js/generic/data.js" />

<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var='icon_calendar' value='/images/icon_calendario.png' />
<c:url var="icon_adicionar1" value="/images/icon_adicionar1.png" scope="request" />

<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>

<script language="javascript">
	function voltar() {
		document.vistoriaBemImovelForm.action="vistoriaBemImovel.do?action=carregarInterfaceConsultar";
		document.vistoriaBemImovelForm.tipoAcao.value = "voltar";
		document.vistoriaBemImovelForm.submit();
	}

	function alterar() {
		if (verificador.noEnvio()) {
			document.vistoriaBemImovelForm.action="vistoriaBemImovel.do?action=alterar&numeroBemImovel="+document.vistoriaBemImovelForm.numeroBemImovel.value;
			document.vistoriaBemImovelForm.tipoAcao.value = "alterar";
			document.vistoriaBemImovelForm.submit();
		}
	}

	function excluirItem(id) {
		if(confirm('Confirma a exclusão do item?')) {
			submitAjax('vistoriaBemImovel.do?action=excluirItemVistoria&codItemVistoria='+id, document.forms[0], 'divItensVistoria',false);
		}
	}

	function concluirVistoria() {
		if (verificador.noEnvio()) {
			document.vistoriaBemImovelForm.action="vistoriaBemImovel.do?action=concluirVistoria";
			document.vistoriaBemImovelForm.tipoAcao.value = "alterar";
			document.vistoriaBemImovelForm.submit();
		}
	}

	function adicionarItens() {
		document.vistoriaBemImovelForm.tipoAcao.value = "adicionar";
   	 	document.vistoriaBemImovelForm.action="vistoriaBemImovel.do?action=carregarInterfaceAdicionarItensVistoria";
   	 	document.vistoriaBemImovelForm.submit();
	}

	function buscaBemImovel() {
		var form = document.vistoriaBemImovelForm;
		if (form.numeroBemImovel.value.length > 0) {
			submitAjax('/abi/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.numeroBemImovel.value, document.forms[0], "dadosBemImovel",true);
		} else {
			if (document.getElementById("bemImovelSimplificado") != null) {
				ajaxHTMLLoad('dadosBemImovel','/bemImovel.do?action=carregarDadosBemImovelSimplificado&nrBemImovelSimpl='+form.numeroBemImovel.value,true);
			}
		}
	}
</script>

<div id="conteudo">
	<div align="center">
	<h1>Alterar Vistoria de Bem Imóvel</h1>

	<html:form action="vistoriaBemImovel.do?action=iniciar">
		<html:hidden property="codVistoria"/>
		<html:hidden property="tipoAcao"/>
		<html:hidden property="conNumeroBemImovel"/>
		<html:hidden property="conDataInicialVistoria"/>
		<html:hidden property="conDataFinalVistoria" />
		<html:hidden property="conSituacao" />
		<html:hidden property="indOperadorOrgao"/>
		<html:hidden property="numeroBemImovel" />
		<html:hidden property="conInstituicao"/>
		<html:hidden property="codInstituicao"/>
		
		<div id="conteudo_corpo">	
			 <table class="form_tabela" cellspacing="0" width="100%">
			 	<tr>
					<td class="form_label" width="10%">Instituição:</td>
					<td width="90%">
						${vistoriaBemImovelForm.instituicao}
					</td>
				</tr>
				<tr>
					<td class="form_label" width="10%">Bem Imóvel:</td>
					<td width="90%">
						${vistoriaBemImovelForm.numeroBemImovel}
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
			</table>
			
			<br/>
			<table class="form_tabela" cellspacing="0" width="100%">
				<tr>
					<td class="form_label" width="10%">Edificação Específica:</td>
					<td width="90%">
						<html:select property="especificacaoEdificacao" styleId="especificacaoEdificacao">
							<html:option value="">- Todas -</html:option>
							<html:options collection="listaEdificacao" property="codEdificacao" labelProperty="especificacao" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="form_label">* Vistoriador: </td>
					<td>
						<html:select property="vistoriador" styleId="vistoriador">
							<html:option value="">- Selecione -</html:option>
							<html:options collection="listaVistoriador" property="codVistoriador" labelProperty="nome" />	
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="form_label">* Data de Execução da Vistoria: </td>
					<td>
						<html:text property="dataVistoria" styleId="dataVistoria" maxlength="10" size="10" onkeypress="javascript:return(MascaraData(this,event));" />
						<img src="${icon_calendar}" width="16" height="15" id="btnCalendar" onclick="javascript:Calendario(document.getElementById('dataVistoria'),this.id);" /> 
					</td>
				</tr>
				<tr>
					<td colspan="2" class="form_label">
						<a href="javascript:adicionarItens();"><img src="${icon_adicionar1}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:adicionarItens();">(Adicionar novos itens para a Vistoria)</a>
					</td>
				</tr>
				<tr>
					<td class="form_label">Itens da Vistoria: </td>
					<td> 
						<div id="divItensVistoria"> <tiles:insert page="/pages/vistoriaBemImovel/_edit_vistoriaBemImovelItens.jsp" />	</div>
					</td>
				</tr>
				<tr>
					<td class="form_label">Idade Aparente: </td>
					<td>
						<html:text property="idadeAparente" styleId="idadeAparente" maxlength="5" size="5" />
					</td>
				</tr>
				<tr>
					<td class="form_label">Observação: </td>
					<td>
						<textarea name="observacao" id="observacao" rows="5" cols="80">${vistoriaBemImovelForm.observacao}</textarea>
					</td>
				</tr>
			 </table>
		  
		   	<hr />
		  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
		 	<div align="center">
				<html:button property="btnSalvar" styleClass="form_botao" value="Salvar" onclick="alterar();" />
				<html:button property="btnSalvar" styleClass="form_botao" value="Concluir Vistoria" onclick="concluirVistoria();" />
				<html:button property="btnCancelar" styleClass="form_botao" value="Voltar" onclick="voltar();" />
			</div>	
		
		</div>

  </html:form>

  </div>
</div>
<script language="javascript">
window.onload = function(){
	var form = document.vistoriaBemImovelForm;
	if (form.numeroBemImovel.value.length > 0){
		buscaBemImovel();
	}
};
</script>   
