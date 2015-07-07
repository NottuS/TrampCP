<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html:select property="orgao" styleId="orgao" name="imprimirBemImovelForm">
	<html:option value="">-Selecione-</html:option>
	<c:if test="${listaOrgaos != null}">
		<html:options collection="listaOrgaos" property="codigo" labelProperty="descricao" />
	</c:if>
</html:select>
