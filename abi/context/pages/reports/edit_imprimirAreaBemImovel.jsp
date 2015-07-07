<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">

	function voltar(){	
		
		document.imprimirAreaBemImovelForm.action="entrada.do";
		document.imprimirAreaBemImovelForm.submit();
	}

	function validarCampos(){
	var form = document.imprimirAreaBemImovelForm;
	form.municipio.value = form.codMunicipio.options[form.codMunicipio.selectedIndex].text;
	
	form.submit();
}

	function MascaraArea(campo) {
		   var Area = SoNumero(campo.value);
		   var AreaAux = ''; 
		   var campo1 = campo.value;
		   var ponto = 3;

		   
		     for (var i=Area.length; i > 0; i--) {
		     
		       if ((i == Area.length - 3) && (Area.length > 3)){
		         
		    	 AreaAux =  "," + AreaAux;
		         ponto = 3;
		        }
		       if ((ponto == 0) && (Area.length > 6)){
		    	   AreaAux =  '.' + AreaAux;
		         ponto = 3;
		        }               
		       AreaAux = Area.substr(i-1,1) + AreaAux;     
		       ponto --;
		      }  
		     campo.value = AreaAux;
		}
	
	function carregarCamposPesquisaPadrao() {

		var form = document.imprimirAreaBemImovelForm;			

		if (form.uf.value=='0'){
			form.uf.value='PR';
		}
	}

</script>

<body onload="carregarCamposPesquisaPadrao()">
	
<div id="conteudo">
	<div align="center">
	<h1 >Imprimir Área do Bem Imóvel  </h1>
	
		<div id="conteudo_corpo"><h2>Opções de Impressão</h2>
		<html:form action="imprimirAreaBemImovel.do?action=gerarRelatorio" onsubmit="return validarCampos()">
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
				<td class="form_label" align="right">Estado:</td>
				<td><cep:uf name="uf"/></td>
				
			</tr>
			<tr>
				<td class="form_label"  align="right">Município:</td>
				<td>
					<cep:municipio name="codMunicipio" value="${imprimirBemImovelForm.codMunicipio}"/>
					<html:hidden name="municipio" property="municipio" value="${imprimirBemImovelForm.municipio}"/>	
														
				</td>
			</tr>
			
            
			 <tr>
            	<td class="form_label">Área do Terreno: </td>
				<td > 
					De:
					<html:text styleId="areaDe" property="areaDe"  size="20" maxlength="19" onkeyup="javascript:MascaraArea(document.getElementById('areaDe'))" />
					
				</td>
				<td >	
					Até:
					<html:text styleId="areaAte" property="areaAte"  size="20" maxlength="19" onkeyup="javascript:MascaraArea(document.getElementById('areaAte'))" />
					
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
				<td colspan="2">			
					<html:radio value="" property="radAdministracao" styleId ="radAdministracaoDiretaA"  >Ambos</html:radio>	
					<html:radio value="1" property="radAdministracao" styleId ="radAdministracaoDireta1" >Direta</html:radio>
					<html:radio value="2" property="radAdministracao" styleId ="radAdministracaoDireta2" >Indireta</html:radio>
					<html:radio value="3" property="radAdministracao" styleId ="radAdministracaoDireta3" >Terceiros</html:radio>
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