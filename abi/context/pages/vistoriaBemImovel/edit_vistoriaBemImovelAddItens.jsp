<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<script language="javascript">
	function voltar(){	
		document.vistoriaBemImovelForm.tipoAcao.value = "voltar";
		document.vistoriaBemImovelForm.action="vistoriaBemImovel.do?action=carregarInterfaceAlterar&codVistoria="+document.vistoriaBemImovelForm.codVistoria.value;
		document.vistoriaBemImovelForm.submit();
	}

	function adicionar() {
	    document.vistoriaBemImovelForm.tipoAcao.value = "excluir";
		document.vistoriaBemImovelForm.action="vistoriaBemImovel.do?action=adicionarItensVistoria";
		document.vistoriaBemImovelForm.submit();
	}
</script>

<body>

<div id="conteudo">
	<div>
		<h1>
			Incluir itens de vistoria de Bem Imóvel
		</h1>
		
		<html:form action="vistoriaBemImovel.do?action=iniciar">
		<html:hidden property="codVistoria" />
		<html:hidden property="tipoAcao"/>
		<html:hidden property="conNumeroBemImovel"/>
		<html:hidden property="conDataInicialVistoria"/>
		<html:hidden property="conDataFinalVistoria" />
		<html:hidden property="conSituacao" />
		<html:hidden property="indOperadorOrgao"/>
		<html:hidden property="indGrupoSentinela"/>
		<html:hidden property="adm"/>
		<html:hidden property="conInstituicao"/>
		
		<div id="conteudo_corpo">
			
			<h2>Itens da Vistoria disponíveis:</h2>

			<table width="100%">
				<tr>
					<td>
						<c:if test="${!empty listaParametroVistoria.registros}">				
						    <ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
								<ch:lista bean="${listaParametroVistoria}" atributoId="codParametroVistoria" />
								<ch:campo atributo="instanciaAtual" label="Selecionar" align="center" decorator="gov.pr.celepar.abi.util.CheckBoxParametroVistoriaDecorator" width="8%"/>
								<ch:campo atributo="descricao" label="Descrição" align="left"/>
								<ch:campo atributo="instanciaAtual" label="Domínio" align="left" decorator="gov.pr.celepar.abi.util.ExibirDominioParametroVistoriaDecorator" />
							</ch:table>		
						</c:if>	
						<c:if test="${empty listaParametroVistoria.registros}">				
							Nenhum item disponível para ser adicionado.	
						</c:if>	
					</td>
				</tr>
			</table>					
			       
			<hr/>
			
			<div align="center">
				<c:if test="${!empty listaParametroVistoria.registros}">				
					<input name="button" type="button" class="form_botao" onclick="adicionar();" value="Adicionar" />
				</c:if>	
			  	<input type="button" class="form_botao" value="Voltar" onclick="voltar();" />
	  		</div>
    		
		</div>
		</html:form>

	</div>
</div>

</body>