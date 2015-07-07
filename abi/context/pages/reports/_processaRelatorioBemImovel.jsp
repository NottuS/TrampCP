<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="js_util" value="/js/generic/util.js" />
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<c:url var="js_prototype" value="/js/generic/prototype.js" />
<script language="JavaScript" type="text/JavaScript" src="${js_prototype}"></script>

<script language="javascript">
	function habilitaBotoes() {
 		desbloqueio(); 
		if('${relatorioProcessado}' != null && '${relatorioProcessado}' != ''){
			var form = document.imprimirBemImovelForm;
			form.action = "imprimirBemImovel.do?action=carregarArquivo";
			form.submit();
		}
	}
	habilitaBotoes();
</script>
	