<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='link_pesquisar' value='/tipoLeiBemImovel.do?action=pesquisarTipoLeiBemImovel' />

<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>

<script language="javascript">

	function voltar(){	
		document.tipoLeiBemImovelForm.descricao.value = "";
		document.tipoLeiBemImovelForm.action="${link_pesquisar}";
		document.tipoLeiBemImovelForm.submit();
	}
	
	function validarCampos(){
	
		var form = document.tipoLeiBemImovelForm;
		Trim(form.descricao);

		form.submit();
	}
	
</script>

	<c:choose> 
		<c:when test='${tipoLeiBemImovelForm.actionType == "incluir"}'>
			<c:set var="acao" value="tipoLeiBemImovel.do?action=salvarTipoLeiBemImovel"></c:set>
			<c:set var="titulo" value="Incluir"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="acao" value="tipoLeiBemImovel.do?action=alterarTipoLeiBemImovel"></c:set>
			<c:set var="titulo" value="Alterar"></c:set>
		</c:otherwise>
	</c:choose>


<div id="conteudo">
	<div align="center">
	<h1>${titulo} Tipo de Lei de Bem Imóvel</h1>

	<html:form action="${acao}" onsubmit="return validarCampos()">
	<html:hidden property="codTipoLeiBemImovel" value="${tipoLeiBemImovelForm.codTipoLeiBemImovel}"/>
	<html:hidden property="actionType" value="${tipoLeiBemImovelForm.actionType}" />
	
     <div id="conteudo_corpo">	
	 <table class="form_tabela" cellspacing="0" width="100%">
		<tr>
			<td class="form_label">* Descrição:</td>
			<td><html:text  property="descricao" maxlength="60" size="60" styleId="descricao" /></td>
		</tr>

	 </table>
  
   	<hr>
  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
 	<div align="center">
		<html:button property="btnSalvar" styleClass="form_botao" value="${titulo}" onclick="validarCampos();" />
		<html:button property="btnCancelar" styleClass="form_botao" value="Voltar" onclick="voltar();" />
	</div>	
	
  </div>

  </html:form>

  </div>
</div>