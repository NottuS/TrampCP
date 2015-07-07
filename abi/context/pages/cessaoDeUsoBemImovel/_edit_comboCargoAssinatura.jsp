<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<html:select property="cargoAssinatura" name="cessaoDeUsoBemImovelForm" styleId="cargoAssinatura" onchange="carregarComboNomeAssinatura()" >
		<html:option value="">-Selecione-</html:option>
		<c:if test="${cargosAssinatura != null}">
			<html:options collection="cargosAssinatura" property="codigo" labelProperty="descricao" />
		</c:if>
	</html:select>
