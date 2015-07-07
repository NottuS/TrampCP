<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<html:select property="edificacao" name="cessaoDeUsoBemImovelForm" styleId="edificacao">
		<html:option value="">-Selecione-</html:option>
		<c:if test="${edificacoes != null}">
			<html:options collection="edificacoes" property="codigo" labelProperty="descricao" />
		</c:if>
	</html:select>
