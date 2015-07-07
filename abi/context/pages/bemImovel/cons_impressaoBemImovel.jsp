<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script language="javascript">

	function validarCampos(){
		var form = document.impressaoBemImovelForm;
		form.submit();
	}
	
	function personalizado_on() {
		document.getElementById("personalizados").style.display="block";
	 }

	 function personalizado_off() {
		document.getElementById("personalizados").style.display="none";		
	 }
	
</script>
 

<div id="conteudo">
	<div align="center">
		<div id="conteudo_corpo">
		
		<html:form action="impressaoBemImovel.do?action=gerarRelatorio" onsubmit="return validarCampos()">
   		  <html:hidden property="codBemImovel" value="${impressaoBemImovelForm.codBemImovel}"/>
   		  <html:hidden property="instituicao" value="${impressaoBemImovelForm.instituicao}"/>
   		  	
   		  
		  <table cellspacing="0" class="form_tabela">

		<tr>
		<td nowrap class="form_label">Identificação na CPE :</td>
		<td colspan="2">${impressaoBemImovelForm.nrBemImovel}</td>
		</tr>
		<tr>
		<td nowrap class="form_label">Instituição:</td>
		<td colspan="2">${impressaoBemImovelForm.instituicaoDesc}</td>
		</tr>
      <tr>
        
      
        <td nowrap class="form_label"> Relat&oacute;rio :</td>
        
        
        
        <html:hidden property="codBemImovel" value=""/>
        <td colspan="3">
        	<input name="tipoRelatorio" type="radio" value="completo"      onClick="javascript:personalizado_off();" checked>Completo 
        	<br>
			<input name="tipoRelatorio" type="radio" value="personalizado" onClick="javascript:personalizado_on();"> Personalizado
          <div id="personalizados" style="display:none;">
		  
		  <h2>Identificação</h2>
		  <table width="100%" border="0">
            <tr>
              <td><input type="checkbox" name="leis"    value="${impressaoBemImovelForm.leis}">Leis/Decretos</td>
              <td><input type="checkbox" name="quadras" value="${impressaoBemImovelForm.quadras}">Quadras </td>
              </tr>
            <tr>
              <td><input type="checkbox" name="confrontante" value="${impressaoBemImovelForm.confrontante}">Confrontantes</td>
              <td><input type="checkbox" name="avaliacao" value="${impressaoBemImovelForm.avaliacao}">Avalia&ccedil;&atilde;o</td>
              </tr>
            <tr>
              <td><input type="checkbox" name="coordenadaUTM" value="${impressaoBemImovelForm.coordenadaUTM}">Coordenada UTM </td>
              <td><input type="checkbox" name="edificacao" value="${impressaoBemImovelForm.edificacao}">Dados Edifica&ccedil;&atilde;o/Ocupa&ccedil;&atilde;o </td>
            </tr>
            <tr>
              <td><input type="checkbox" name="ocupacaoTerreno" value="${impressaoBemImovelForm.ocupacaoTerreno}">Responsável pelo Terreno</td>
            </tr>
            <tr>
            </tr>
              
          </table>		  
		  
		  <h2>Documenta&ccedil;&atilde;o</h2>
		  <table width="100%" border="0">
            <tr>
            	<td><input type="checkbox" name="documentacaoNotificacao" value="${impressaoBemImovelForm.documentacaoNotificacao}">Documenta&ccedil;&atilde;o com Notificação</td>
				<td><input type="checkbox" name="documentacaoSemNotificacao" value="${impressaoBemImovelForm.documentacaoSemNotificacao}">Documenta&ccedil;&atilde;o sem notificação</td>            	
            </tr>
            <tr><td><input type="checkbox" name="ocorrencia" value="${impressaoBemImovelForm.ocorrencia}">Ocorr&ecirc;ncias de Movimenta&ccedil;&atilde;o </td> </tr>            
          </table>
		  

          </div></td>
      </tr>
          </table>
          
		  <hr>
			<div align="center">
			  <html:submit property="btnSalvar" styleClass="form_botao" value="Imprimir" />
		    </div>

		  </html:form>
		</div>

  </div>
  </div>
  
