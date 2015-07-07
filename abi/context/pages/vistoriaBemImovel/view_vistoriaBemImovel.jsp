<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<script language="javascript">
	function voltar(){	
		document.vistoriaBemImovelForm.tipoAcao.value = "voltar";
		document.vistoriaBemImovelForm.action="vistoriaBemImovel.do?action=carregarInterfaceConsultar";
		document.vistoriaBemImovelForm.submit();
	}

	function excluir(id) {
	    document.vistoriaBemImovelForm.tipoAcao.value = "excluir";
		document.vistoriaBemImovelForm.action="vistoriaBemImovel.do?action=excluir&codVistoria=" + id;
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

<body>

<div id="conteudo">
	<div>
		<h1>
			<c:if test="${vistoriaBemImovelForm.tipoAcao == 'excluir'}">
				Exclus&atilde;o de Vistoria
			</c:if>
			<c:if test="${vistoriaBemImovelForm.tipoAcao == 'exibir'}">
				Visualiza&ccedil;&atilde;o de Vistoria
			</c:if>
		</h1>
		
		<html:form action="vistoriaBemImovel.do?action=iniciar">
		<html:hidden property="codVistoria"/>
		<html:hidden property="tipoAcao"/>
		<html:hidden property="conNumeroBemImovel"/>
		<html:hidden property="conDataInicialVistoria"/>
		<html:hidden property="conDataFinalVistoria" />
		<html:hidden property="conSituacao" />
		<html:hidden property="indOperadorOrgao"/>
		<html:hidden property="numeroBemImovel" />
		<html:hidden property="codInstituicao" />
		<html:hidden property="indGrupoSentinela"/>
		<html:hidden property="adm"/>
		<html:hidden property="conInstituicao"/>
		
		<div id="conteudo_corpo">
			
			<table cellspacing="0" class="form_tabela" width="100%">
			    <tr>
					<td class="form_label" align="right" width="20%">Instituição:</td>
					<td width="80%"> 
						${vistoriaBemImovelForm.instituicao} 
					</td>
				</tr>
				<tr>
					<td class="form_label" align="right" width="20%">Bem Imóvel:</td>
					<td width="80%"> 
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
				
				<tr>
					<td class="form_label">Situação:</td>
					<td> 
						${vistoriaBemImovelForm.situacao} 
					</td>				
				</tr>

				<c:if test="${vistoriaBemImovelForm.especificacaoEdificacao != '' }">
				<tr>
					<td class="form_label" align="right">Edificação Específica:</td>
					<td> 
						${vistoriaBemImovelForm.especificacaoEdificacao} 
					</td>
				</tr>
				</c:if>
				
				<tr>
					<td class="form_label" align="right">Vistoriador:</td>
					<td> 
					   ${vistoriaBemImovelForm.vistoriador} 
					</td>
				</tr>
				
				<tr>
					<td class="form_label" align="right">Data da Vistoria:</td>
					<td> 
						${vistoriaBemImovelForm.dataVistoria} 
					</td>
				</tr>
				
				<c:if test="${!empty listaItemVistoria.registros}"> 
				<tr>
					<td class="form_label" align="right"><b>Itens da Vistoria:</b></td>
					<td> 
						<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
							<ch:lista bean="${listaItemVistoria}" atributoId="codItemVistoria" />
							<ch:campo atributo="descricao" label="Descrição" align="left"/>
							<ch:campo atributo="instanciaAtual" label="Domínio" align="left" decorator="gov.pr.celepar.abi.util.ExibirDominioVistoriaDecorator" />
						</ch:table>							
					</td>
				</tr>
				</c:if>
				
				<tr>
					<td class="form_label" align="right">Idade Aparente:</td>
					<td> 
						${vistoriaBemImovelForm.idadeAparente} 
					</td>
				</tr>
				
				<tr>
					<td class="form_label" align="right">Observação:</td>
					<td> 
						${vistoriaBemImovelForm.observacao} 
					</td>
				</tr>        
			</table>
			       
			<hr/>
			
			<div align="center">
				<c:if test="${vistoriaBemImovelForm.tipoAcao == 'excluir'}"> 				
				  	<input name="button" type="button" class="form_botao" onclick="excluir(${codVistoria})" value="Excluir" />
				</c:if>
			  	<input type="button" class="form_botao" value="Voltar" onclick="voltar();" />
	  		</div>
    		
		</div>
		</html:form>

	</div>
</div>

</body>
<script language="javascript">
window.onload = function(){
	var form = document.vistoriaBemImovelForm;
	if (form.numeroBemImovel.value.length > 0){
		buscaBemImovel();
	}
};
</script>   
