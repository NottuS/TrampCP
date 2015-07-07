<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

 
<div id="conteudo"  >
	<div align="center">
	<h1 >Exibir Bem Im�vel</h1>

    
    <div id="conteudo_corpo">
		<h2>Bem Im�vel - Edifica��o</h2>
		<table class="form_tabela" cellspacing="0" width="80%" >
			<tr> 
	          <td class="form_label">Identifica��o do Im�vel CPE:</td>
	          <td colspan="2"><c:out value="${edificacao.bemImovel.codBemImovel}"></c:out></td>
	        </tr>
			<tr> 
	          <td class="form_label">Tipo de Constru��o:</td>
	          <td colspan="2"><c:out value="${edificacao.tipoConstrucao.descricao}"></c:out></td>
	        </tr>
	        <tr> 
	          <td class="form_label">Tipo de Edifica��o: </td>
	          <td colspan="2"><c:out value="${edificacao.tipoEdificacao.descricao}"></c:out></td>
	        </tr>
	        <tr> 
	          <td class="form_label">Especifica��o:</td>
	          <td colspan="3"><c:out value="${edificacao.especificacao}"></c:out></td>
	        </tr>
	        <tr> 
	          <td class="form_label">Logradouro:</td>
	          <td colspan="2"><c:out value="${edificacao.logradouro}"></c:out></td>	
	        </tr>
	        <tr> 
	          <td class="form_label">�rea constru�da (m2) : </td>
	          <td><fmt:formatNumber value="${edificacao.areaConstruida}" minFractionDigits="2"/></td>
	        </tr>
	        <tr> 
	          <td class="form_label"> �rea utilizada (m2) :</td>
	          <td ><fmt:formatNumber value="${edificacao.areaUtilizada}" minFractionDigits="2"/>  </td>
	        </tr>
	        <tr> 
	          <td class="form_label">Data de Averba��o: </td>
	          <td > <fmt:formatDate value="${edificacao.dataAverbacao}" type="both" pattern="dd/MM/yyyy"/></td>
	        </tr>
	        <tr> 
	          <td class="form_label">Lotes: </td>
	          <td >
	          <c:forEach var="lote" items="${edificacao.lotes}">
	          	<c:out value="${lote.descricao}"></c:out> <br>
	          </c:forEach>
					
			  </td>
			</tr>
			
			</table>
			<table class="form_tabela" cellspacing="0" width="80%">
			<tr>
	          <td  align="center"><a href="javascript:history.back(1)">Voltar</a></td>
	        </tr>	
		</table>
    </div>

    </div>
 </div>

