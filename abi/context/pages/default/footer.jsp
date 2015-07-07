<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="img_meio_rod" value="/images/back_barra_acao_bot_mei.png" />

<div id="barra_acao_bottom">
	<table align='right' cellspacing='0'>
	<tr>
		<td><img src='images/back_barra_acao_bot_esq.png'/></td>
			<td background="${img_meio_rod}"><a href='javascript: window.scrollTo(0, 0); void 0'>topo</a></td>
			<td background="${img_meio_rod}"><a href='javascript: window.scrollTo(0, 0); void 0'><img src='images/icon_topo.gif' width='16' height='16' border='0' /></a></td>
			<td><img src='images/back_barra_acao_bot_dir.png'/></td>
		</tr>
	</table>
</div>

<div id='rodape_interno'>
  <table border='0' width='100%'>
    <tr> <td  width='700' class='text_rodape'>Melhor Visualizado pelos navegadores Mozilla Firefox 3.0+, com resolução de tela 1024x768.<br>Central de Atendimento Celepar - 3200-5007</td>
      <td>&nbsp;</td>
      <td><a href='http://www.celepar.pr.gov.br/' target='_blank'><img src='http://www.pr.gov.br/logos/footer_celepar_cinza.png' width='54' height='49' border='0' align='right' /></a></td>
    </tr>
  </table>
</div>
