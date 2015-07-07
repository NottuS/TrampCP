<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>

	<c:if test="${!empty pagina.registros}">
	<div id="conteudo_corpo">
	
		<ch:table classTable="list_tabela" classLinha1="list_cor_sim" classLinha2="list_cor_nao">
	    	<ch:lista bean="${pagina}" atributoId="codParametroVistoria" />
        	<ch:campo atributo="descricao" label="Descrição" width="30%" /> 
        	<ch:campo atributo="instituicao.siglaDescricao" label="Instituição" width="30%" />
        	<ch:campo atributo="descricao" label="Denominação" width="30%" />
        	<ch:campo atributo="indTipoParametro" label="Tipo do Domínio" width="10%"/>
        	<ch:campo atributo="indAtivo" label="Ativo" width="10%"/> 
        	<ch:action imagem="${icon_anotacao2}" link="javascript:ativarDesativar(%1)" label="Ativar/Desativar" width="3%" align="center"/>
        	<ch:action imagem="${icon_alterar}" link="javascript:alterar(%1)" label="Alterar" width="3%" align="center"/>
        	<ch:action imagem="${icon_excluir}" link="javascript:carregarInterfaceExibir(%1,'excluir')" label="Excluir2" width="3%" align="center"/>
		</ch:table>
	</div>
	</c:if>