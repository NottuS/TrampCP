<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<html:select property="conOrgaoResponsavel" name="doacaoBemImovelForm" styleId="conOrgaoResponsavel">
		<html:option value="">-Todos-</html:option>
		<c:if test="${orgaosPesquisar != null}">
			<html:options collection="orgaosPesquisar" property="codigo" labelProperty="descricao" />
		</c:if>
	</html:select>
