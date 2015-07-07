<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<script language="javascript">

	function voltar(){	
		
		document.imprimirEdificacaoOcupacaoForm.action="entrada.do";
		document.imprimirEdificacaoOcupacaoForm.submit();
	}
	
	function limpaMensagens() {
		if(document.getElementById("__msgVerificador")!=null)
		 	document.getElementById("__msgVerificador").innerHTML = "";
		if(document.getElementById("msg")!=null) 
			document.getElementById("msg").innerHTML = "";
		if(document.getElementById("msgs_servidor")!=null)
			document.getElementById("msgs_servidor").innerHTML = "";
	}
	
	function validarCampos(){
	var form = document.imprimirEdificacaoOcupacaoForm;
	form.municipio.value = form.codMunicipio.options[form.codMunicipio.selectedIndex].text;
	form.orgao.value = form.codOrgao.options[form.codOrgao.selectedIndex].text;
	limpaMensagens();

	form.submit();
}
	
	function carregarCamposPesquisaPadrao() {

		var form = document.imprimirEdificacaoOcupacaoForm;			

		if (form.uf.value=='0'){
			form.uf.value='PR';
		}
	}
		
</script>

<body onload="carregarCamposPesquisaPadrao()">

<div id="conteudo">
	<div align="center">
	<h1 >Imprimir Edificação com Ocupação do Bem Imóvel </h1>
	
		<div id="conteudo_corpo"><h2>Opções de Impressão</h2>
		<html:form action="imprimirEdificacaoOcupacao.do?action=gerarRelatorio" onsubmit="return validarCampos()">
			<html:hidden property="indOperadorOrgao"/>
			<cep:main>
			<cep:form findOnType="true" codificacao="C" textoBusca="procurando...">
			<div style="display:none" align="right">
				<cep:cep name="cep" readonly="true"/>
				<cep:endereco name="endereco"  maxlength="72" size="46"/>
				<cep:bairro name="bairro"  maxlength="72" size="25"/>
			</div>
		  <table cellspacing="0" class="form_tabela">
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
				<td class="form_label" align="right">Estado:</td>
				<td><cep:uf name="uf"/></td>
				
			</tr>
			<tr>
				<td class="form_label"  align="right">Município:</td>
				<td>
					<cep:municipio name="codMunicipio" value="${imprimirEdificacaoOcupacaoForm.codMunicipio}"/>
					<html:hidden name="municipio" property="municipio" value="${imprimirEdificacaoOcupacaoForm.municipio}"/>	
					<html:hidden name="orgao" property="orgao" value="${imprimirEdificacaoOcupacaoForm.orgao}"/>	
								
				</td>
			</tr>
			
            <tr>
				<td class="form_label">Orgão:</td>
				<td colspan="2">
					<html:select property="codOrgao">
						<html:option value="">- Todos -</html:option>
						<html:options collection="orgaos" property="codigo" labelProperty="descricao" />						
					</html:select>
				</td>				
			</tr>
            
            <tr>
              <td class="form_label"> Mostrar Relat&oacute;rio:</td>
              <td>
              	<html:radio property="radRelatorio" styleId="radRelatorio1" value="1" >Ambos</html:radio>
	          	<html:radio property="radRelatorio" styleId="radRelatorio2" value="2" >Operacional</html:radio>
	          	<html:radio property="radRelatorio" styleId="radRelatorio3" value="3" >Gerencial</html:radio>
             </td>
             </tr>
          </table>
          
		  <hr>
		   <c:if test="${!empty listaInstituicao}">
			  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
			</c:if>    
			<div align="center">
			  <html:submit property="btnSalvar" styleClass="form_botao" value="Imprimir" />
			  <html:button property="btnCancelar" styleClass="form_botao" value="Cancelar" onclick="voltar();" />
		  </div>
		    <cep:box />
		  	</cep:form>
    		</cep:main>
		  </html:form>
		</div>

  </div>
  </div>
</body>