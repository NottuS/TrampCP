<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html:select property="orgaoCessionario" name="cessaoDeUsoBemImovelForm" styleId="orgaoCessionario" onchange="carregarComboOrgaoAssinatura()">
	<html:option value=" ">-Selecione-</html:option>
	<c:if test="${orgaosCessionarios != null}">
		<html:options collection="orgaosCessionarios" property="codigo" labelProperty="descricao" />
	</c:if>
</html:select>
