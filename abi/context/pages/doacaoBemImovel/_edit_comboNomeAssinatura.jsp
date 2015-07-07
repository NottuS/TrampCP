<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<html:select property="nomeAssinatura" name="doacaoBemImovelForm" styleId="nomeAssinatura">
		<html:option value="">-Selecione-</html:option>
		<c:if test="${nomesAssinatura != null}">
			<html:options collection="nomesAssinatura" property="codigo" labelProperty="descricao" />
		</c:if>
	</html:select>
