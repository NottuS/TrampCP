<%@ taglib uri="http://celepar.pr.gov.br/taglibs/informacao.tld" prefix="info" %>

<div id="conteudo" align="center">
  <h1>Seguran&ccedil;a</h1>
  <div id="conteudo_corpo">
  	<div class="msg">
		<h2>Ocorreu um Erro</h2>
		<p class="msg">Mensagem de erro: </p>
		<h2><info:sentinelaErro/></h2>
		<br/>
		<hr/>
		<br/>
	</div>
	<div align="center">
		<script language="javascript">
			if(window.opener){
				document.write("<input type='button' class='form_botao' value='Fechar' onClick='javascript:window.close();' />");
			}else{
				document.write("<input type='button' class='form_botao' value='Voltar' onClick='javascript:history.back();' />");
			}
		</script>
	</div>
  </div>
</div>
<br/>