<%@ taglib uri="http://celepar.pr.gov.br/taglibs/login.tld" prefix="sentinela" %>

<div class="login_corpo" align="center"><br>
		<sentinela:login 
           action="entrada.do"
		   classeInterna="login_label" 
		   classeLabel="login_label"
		   classeAlternativaMensagem= "login_mensagem"
		   labelName="Usuário"
		   labelPass="Senha"
		   labelBotao="Entrar"		   		   
		   classeBotao="login_botao" 
		   classeOpcoes="login_funcoes"/>
</div>