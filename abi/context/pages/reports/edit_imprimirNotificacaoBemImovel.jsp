<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/cep-1.0" prefix="cep" %>

<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var="js_data" value="/js/generic/data.js" />

<c:url var='icon_calendar' value='/images/icon_calendario.png' />

<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>

<script language="javascript">

	function voltar(){	
		
		document.imprimirNotificacaoBemImovelForm.action="entrada.do";
		document.imprimirNotificacaoBemImovelForm.submit();
	}

	function validarCampos(){
		var form = document.imprimirNotificacaoBemImovelForm;
	
		
		dataAtual=new Date().toLocaleDateString();
		 
	
		if (!ValidaData(form.tsNotificacaoDe)){
			alert("Campo 'Data da Notificação De' deve ser uma data válida!");
			form.tsNotificacaoDe.focus();
			return false;			
		}
		if (DataMaior(form.tsNotificacaoDe.value, dataAtual )){
			alert("Campo 'Data da Notificação De' não pode ser maior que a data atual!");
			form.tsNotificacaoDe.focus();
			return false;			
		}
		if (!ValidaData(form.tsNotificacaoAte)){
			alert("Campo 'Data da Notificação Até' deve ser uma data válida!");
			form.tsNotificacaoAte.focus();
			return false;			
		}
		if (DataMaior(form.tsNotificacaoAte.value, dataAtual )){
			alert("Campo 'Data da Notificação Até' não pode ser maior que a data atual!");
			form.tsNotificacaoAte.focus();
			return false;			
		}
	
		if (!DataMaior(form.tsNotificacaoAte.value,form.tsNotificacaoDe.value)){
			alert("Campo 'Data de Notificação Até' deve ser maior que 'Data de Notificação De'");
			form.tsNotificacaoAte.focus();
			return false ;			
		}
	
		
		form.municipio.value = form.codMunicipio.options[form.codMunicipio.selectedIndex].text;
		form.situacaoImovel.value = form.codSituacao.options[form.codSituacao.selectedIndex].text;
		form.classificacaoBemImovel.value = form.codClassificacao.options[form.codClassificacao.selectedIndex].text;
	
		form.submit();
	}
	
	
	function carregarCamposPesquisaPadrao() {

		var form = document.imprimirNotificacaoBemImovelForm;			

		if (form.uf.value=='0'){
			form.uf.value='PR';
		}
	}

		
	
		
</script>

<body onload="carregarCamposPesquisaPadrao()">


<div id="conteudo">
	<div align="center">
	<h1 >Imprimir  Notificação do Bem Imóvel </h1>
	
		<div id="conteudo_corpo"><h2>Opções de Impressão</h2>
		<html:form action="imprimirNotificacaoBemImovel.do?action=gerarRelatorio" onsubmit="return validarCampos()">
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
					<cep:municipio name="codMunicipio" value="${imprimirNotificacaoBemImovelForm.codMunicipio}"/>
					<html:hidden name="municipio" property="municipio" value="${imprimirNotificacaoBemImovelForm.municipio}"/>	
					<html:hidden name="situacaoImovel" property="situacaoImovel" value="${imprimirNotificacaoBemImovelForm.situacaoImovel}"/>	
					<html:hidden name="classificacaoBemImovel" property="classificacaoBemImovel" value="${imprimirNotificacaoBemImovelForm.classificacaoBemImovel}"/>	
									
				</td>
			</tr>
            <tr>
            <td class="form_label">Data de Notificação </td>
			<td > 
				De:
				<html:text styleId="tsNotificacaoDe" property="tsNotificacaoDe"  size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));" />
				<img src="${icon_calendar}" width="16" height="15" id="btnCalendar" onclick="javascript:Calendario(document.getElementById('tsNotificacaoDe'),this.id);"/>
			</td>
			<td >	
				Até:
				<html:text styleId="tsNotificacaoAte" property="tsNotificacaoAte"  size="10" maxlength="10" onkeypress="javascript:return(MascaraData(this,event));" />
				<img src="${icon_calendar}" width="16" height="15" id="btnCalendar1" onclick="javascript:Calendario(document.getElementById('tsNotificacaoAte'),this.id);" />
			</td>
            </tr>
            <tr>
				<td class="form_label">Classificação do imóvel:</td>
				<td colspan="2">
					<html:select property="codClassificacao">
						<html:option value="">- Todos -</html:option>
						<html:options collection="classificacaoBemImovels" property="codClassificacaoBemImovel" labelProperty="descricao" />						
					</html:select>
				</td>				
			</tr>
            <tr>
				<td class="form_label">Situação do Imóvel:</td>
				<td colspan="2">
					<html:select property="codSituacao">
						<html:option value="">- Todos -</html:option>
						<html:options collection="situacaoImovels" property="codSituacaoImovel" labelProperty="descricao" />						
					</html:select>
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