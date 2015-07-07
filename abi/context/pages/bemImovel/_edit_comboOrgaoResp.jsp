<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html:select property="or_codOrgao" name="bemImovelForm" styleId="or_codOrgao">
	<html:option value="">-Selecione-</html:option>
	<c:if test="${orgaosResp != null}">
		<html:options collection="orgaosResp" property="codigo" labelProperty="descricao"/>
	</c:if>
</html:select>
