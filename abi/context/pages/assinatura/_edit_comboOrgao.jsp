<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table width="100%">
	<tr>
	 	<td class="form_label" width="15%">* Órgão: </td>
	 	<td>
			<html:select property="orgao" name="assinaturaForm" styleId="orgao">
				<html:option value="">-Selecione-</html:option>
				<c:if test="${orgaos != null}">
					<html:options collection="orgaos" property="codigo" labelProperty="descricao" />
				</c:if>
			</html:select>
		</td>
	</tr>
	<tr>
	 	<td class="form_label">* Cargo: </td>
	 	<td>
			<html:select property="cargo" name="assinaturaForm" styleId="cargo">
				<html:option value="">-Selecione-</html:option>
				<c:if test="${cargos != null}">
					<html:options collection="cargos" property="codigo" labelProperty="descricao" />
				</c:if>
			</html:select>
	</tr>
</table>

