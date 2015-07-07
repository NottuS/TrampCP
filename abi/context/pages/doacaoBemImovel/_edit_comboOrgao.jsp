<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<html:select property="orgaoResponsavel" name="doacaoBemImovelForm" styleId="orgaoResponsavel" onchange="carregarComboOrgaoAssinatura()">
		<html:option value=" ">-Selecione-</html:option>
		<c:if test="${orgaos != null}">
			<html:options collection="orgaos" property="codigo" labelProperty="descricao" />
		</c:if>
	</html:select>
