<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

<c:url var='icon_calculadora' value='/images/icon_calculadora.png' />

<table class="form_tabela" cellspacing="0">
       <tr>
           <td class="form_label" width="280">Ocupação em m²: </td>
           <td><html:text maxlength="19" size="20" name="bemImovelForm" property="or_ocupacaoMetroQuadrado" onkeyup="javascript:DigitaNumMascara(this, -1);"/></td>
           <td class="form_label">Ocupa&ccedil;&atilde;o em %: </td>
           <td><html:text maxlength="6" size="20" name="bemImovelForm" property="or_ocupacaoPercentual" onkeyup="javascript:DigitaNumMascara(this, 100);"/>
           <a href="javascript:calcularOcupacao()"><img src="${icon_calculadora}" width="16" height="16" border="0">Calcular Ocupa&ccedil;&atilde;o</a></td>
         </tr>
   </table>
       
