<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<html:select property="conOrgaoResp" name="bemImovelForm" styleId="conOrgaoResp">
		<html:option value="">-Qualquer-</html:option>
		<c:if test="${orgaosRespPesq != null}">
			<html:options collection="orgaosRespPesq" property="codigo" labelProperty="descricao" />						
		</c:if>
	</html:select>
