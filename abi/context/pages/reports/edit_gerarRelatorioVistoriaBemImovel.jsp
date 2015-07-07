<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<script language="javascript">
	function voltar(){	
		document.gerarRelatorioVistoriaBemImovelForm.tipoAcao.value = "voltar";
		document.gerarRelatorioVistoriaBemImovelForm.action="gerarRelatorioVistoriaBemImovel.do?action=concluir";
		document.gerarRelatorioVistoriaBemImovelForm.submit();
	}

	function confirmar(){
		document.gerarRelatorioVistoriaBemImovelForm.tipoAcao.value = "imprimir";
		document.gerarRelatorioVistoriaBemImovelForm.action="gerarRelatorioVistoriaBemImovel.do?action=confirmar";
		document.gerarRelatorioVistoriaBemImovelForm.submit();
	}
	
</script>

<body>

<div id="conteudo">
	<div align="center">
	<h1 >Vistoria de Bem Imóvel </h1>
		
		<html:form action="gerarRelatorioVistoriaBemImovel.do?action=carregarInterfaceInicial">
		<html:hidden property="tipoAcao"/>
		<html:hidden property="codVistoria"/>
		<html:hidden property="conInstituicao"/>
		
		<div id="conteudo_corpo">
			
		  <table cellspacing="0" class="form_tabela" >
			<tr>
				<td class="form_label">Bem Imóvel:</td>
				<td>${gerarRelatorioVistoriaBemImovelForm.bemImovel}</td>
			</tr>
			<tr>
		 		<td class="form_label">* Imprimir:</td>
		 		<td>
					<html:radio value="1" property="imprimir">Formulário para realizar a vistoria</html:radio> <BR>
					<html:radio value="2" property="imprimir">Vistoria cadastrada</html:radio>
				</td>
			</tr>
          </table>
		  <hr>
		  <div align="center">
			  <html:button property="btnGerar" styleClass="form_botao" value="Confirmar" onclick="confirmar();" />
			  <html:button property="btnVoltar" styleClass="form_botao" value="Voltar" onclick="voltar();" />
		  </div>
    		
		</div>
		</html:form>

	</div>
</div>

</body>