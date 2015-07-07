<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<html:select property="conOrgao" name="bemImovelForm" styleId="conOrgao">
		<html:option value="">-Qualquer-</html:option>
		<c:if test="${orgaosPesq != null}">
			<html:options collection="orgaosPesq" property="codigo" labelProperty="descricao" />						
		</c:if>
	</html:select>
