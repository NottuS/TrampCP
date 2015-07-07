<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<html:select property="orgaoAssinatura" name="transferenciaBemImovelForm" styleId="orgaoAssinatura" onchange="carregarComboCargoAssinatura()" >
		<html:option value="">-Selecione-</html:option>
		<c:if test="${orgaosAssinatura != null}">
			<html:options collection="orgaosAssinatura" property="codigo" labelProperty="descricao" />
		</c:if>
	</html:select>
