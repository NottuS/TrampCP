<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var='icon_calculadora' value='/images/icon_calculadora.png' />

<table class="form_tabela" cellspacing="0">
       <tr>
           <td class="form_label" width="264">Transfer�ncia em m�: </td>
           <td><html:text maxlength="19" size="20" name="transferenciaBemImovelForm" property="transferenciaMetros" onkeyup="javascript:DigitaNumMascara(this, -1);"/></td>
		</tr>
		<tr>
           <td class="form_label">Transfer�ncia em %: </td>
           <td>
				<table>
					<tr>
						<td>
			           		<html:text maxlength="9" size="20" name="transferenciaBemImovelForm" property="transferenciaPercentual" onkeyup="javascript:DigitaNumeroMascaraCincoDig(this, 100);" onblur="javascript:FormataNumeroCincoDig(this);"/>
						</td>
						<td>
							<div id="calcTransferencia"> 
				           		<a href="javascript:calcularTransferencia()" id="linkCalcular"><img src="${icon_calculadora}" width="16" height="16" border="0" id="iconCalcular">Calcular</a>
							</div>
						</td>
					</tr>
				</table>
           	</td>
         </tr>
   </table>
       
