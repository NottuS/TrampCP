<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var='icon_localizarLei' value='/images/icon_localizar.png' />
<c:url var='icon_calendarLei' value='/images/icon_calendario.png' />

<html:hidden property="codLei" value="${doacaoBemImovelForm.codLei}"/>
<h2>Dados da Lei</h2>
<table width="100%">
 	<tr>
 		<td class="form_label" width="260">Projeto de Lei: </td>
 		<td>
			<html:radio value="S" property="projetoLei" name="doacaoBemImovelForm" styleId="projetoLeiS" onclick="javascript:atribuirFocoNrLei();">Sim</html:radio>
			<html:radio value="N" property="projetoLei" name="doacaoBemImovelForm" styleId="projetoLeiN" onclick="javascript:atribuirFocoNrLei();">Não</html:radio>
		</td>
	</tr>
	<tr>
		<td class="form_label">Nº da Lei:</td>
		<td> 
			<table>
				<tr>
					<td>
						<html:text property="numeroLei" name="doacaoBemImovelForm" styleId="numeroLei" maxlength="10" size="10" onkeyup="DigitaNumero(this);" onblur="buscaLeiBemImovel();atribuirFocoNrLei();" />
					</td>
					<td>
						<div id="locLeiBemImovel"> 
							<a href="javascript:localizarLei();"><img src="${icon_localizarLei}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:localizarLei();">Localizar</a>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr> 
        <td class="form_label">Data da Assinatura: </td>
        <td>
			<html:text styleId="dataAssinaturaLei" property="dataAssinaturaLei" name="doacaoBemImovelForm" size="10" maxlength="10" onkeyup="javascript:return(MascaraData(this,event));"/>
			<img src="${icon_calendarLei}" width="16" height="15" id="btnCalendarDtAssinatura" onclick="javascript:Calendario(document.getElementById('dataAssinaturaLei'),this.id);" />
		</td>
   	</tr>
	<tr> 
        <td class="form_label">Data da Publicação: </td>
        <td>
			<html:text styleId="dataPublicacaoLei" property="dataPublicacaoLei" name="doacaoBemImovelForm" size="10" maxlength="10" onkeyup="javascript:return(MascaraData(this,event));" />
			<img src="${icon_calendarLei}" width="16" height="15" id="btnCalendarDtPublicacao" onclick="javascript:Calendario(document.getElementById('dataPublicacaoLei'),this.id);" />
		</td>
   	</tr>
	<tr>
		<td class="form_label">Nº do Diário Oficial:</td>
		<td> 
			<html:text property="nrDioeLei" name="doacaoBemImovelForm" styleId="nrDioeLei" maxlength="15" size="15" onkeyup="DigitaNumero(this);"/>
		</td>
	</tr>
</table>
<script language="javascript">
	if(document.getElementById("projetoLeiN").checked && (form.dataAssinaturaLei.value != '' 
		|| form.dataPublicacaoLei.value != '' || form.nrDioeLei.value != '')){
		form.dataAssinaturaLei.disabled=true;
		form.dataPublicacaoLei.disabled=true;
		form.nrDioeLei.disabled=true;
	} else {
		form.dataAssinaturaLei.disabled=false;
		form.dataPublicacaoLei.disabled=false;
		form.nrDioeLei.disabled=false;
	}
</script>   
