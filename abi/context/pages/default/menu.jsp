<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/menu.tld" prefix="menu" %>

<!-- Mapeamento dos Arquivos Externos -->
<c:url var="link_js" value="/js/menu/" />

<menu:menu path="${link_js}"/>