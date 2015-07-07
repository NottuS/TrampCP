<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var='icon_calculadora' value='/images/icon_calculadora.png' />

<table class="form_tabela" cellspacing="0">
       <tr>
           <td class="form_label" width="264">Doação em m²: </td>
           <td><html:text maxlength="19" size="20" name="doacaoBemImovelForm" property="doacaoMetros" onkeyup="javascript:DigitaNumMascara(this, -1);"/></td>
		</tr>
		<tr>
           <td class="form_label">Doação em %: </td>
           <td>
				<table>
					<tr>
						<td>
			           		<html:text maxlength="9" size="20" name="doacaoBemImovelForm" property="doacaoPercentual" onkeyup="javascript:DigitaNumeroMascaraCincoDig(this, 100);" onblur="javascript:FormataNumeroCincoDig(this);"/>
						</td>
						<td>
							<div id="calcDoacao"> 
				           		<a href="javascript:calcularDoacao()" id="linkCalcular"><img src="${icon_calculadora}" width="16" height="16" border="0" id="iconCalcular">Calcular</a>
							</div>
						</td>
					</tr>
				</table>
           	</td>
         </tr>
   </table>
       
