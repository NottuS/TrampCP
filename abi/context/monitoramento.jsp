<%@page import="gov.pr.celepar.framework.util.Data"%>
<%@page import="gov.pr.celepar.framework.database.HibernateUtil"%>
<%@page import="gov.pr.celepar.framework.exception.ApplicationException"%>

<%
response.setContentType("text/xml");
response.setHeader("Cache-Control", "no-cache");
response.setHeader("pragma","no-cache");
%>
<gopmp>
        <titulo>Gest�o Patrimonial de Im�veis do Paran�</titulo>
        <teste>
                 <nome>Teste de Conex�o com o Banco de Dados PostgreSQL</nome>
                 <descricao>Verifica��o da conex�o com a base: pgprod04.celepar.parana:5432/abi</descricao>                 
                 <%
                 	try{
	             		HibernateUtil.currentSession();
	             		out.println("<status>OK</status>");
	             	}catch(Exception e){
	             		out.println("<status>ERRO</status>");
	             		out.println("<msgerro>"+e.getMessage()+"</msgerro>");
	             	}finally{
	             		HibernateUtil.closeSession();
	             	}
                 %>
        </teste>
</gopmp>
