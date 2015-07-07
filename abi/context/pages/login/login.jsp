<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/login.tld" prefix="sentinela" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
    <div id="login">
    	<div align="center">
        	<table id="caixa_login" cellpadding="0" cellspacing="0">
          		<tr>
            		<td height="36" background="images/cx_login_se.jpg">&nbsp;</td>
            		<td background="images/cx_login_sm.jpg" class="login_titulo">Dados para acesso</td>
            		<td width="20" background="images/cx_login_sd.jpg">&nbsp;</td>
          		</tr>
          		<tr>
            		<td width="20" background="images/cx_login_me.jpg">&nbsp;</td>
            		<td>
            			<table border="0" align="center" class="login_tabela" >
							<tr>
								<td>
									<div class="login_corpo" align="center"><br>
										<sentinela:login 
								            action						="entrada.do"
						        		    classe						= "login" 		
								   			classeTitulo				= "login_titulo"	
										    classeInterna				= "login_label" 
										    classeLabel					= "login_label"
										    classeAlternativa 			= "login_alternativo" 
											classeAlternativaInterna 	= "login_alternativo_interno" 
								   			classeAlternativaMensagem 	= "login_alternativo_mensagem"
								   			labelName					= "Usuário"
								   			labelPass					= "Senha"
								   			labelBotao					= "Entrar"		   		   
								   			classeBotao					= "login_botao" 
								   			classeOpcoes					= "login_funcoes"/>
									</div>
								</td>
							</tr>
	              		</table>
              		</td>
            		<td width="20" background="images/cx_login_md.jpg">&nbsp;</td>
          		</tr>
          		<tr>
					<td height="4" background="images/cx_login_ie.jpg">&nbsp;</td>
					<td background="images/cx_login_im.jpg"></td>
					<td background="images/cx_login_id.jpg">&nbsp;</td>
				</tr>
        	</table>
        	<table>
				<tr>
					<td class="form_label_version">Versão 01.01.03 - 09/08/2012 11:01 h</td>
				</tr>
        	</table>
      	</div>
    </div>
</html:html>