<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id='cabecalho'>
	<table cellspacing='0' cellpadding='0' width='100%'>
		<tr>
			<td width='160' align='center'><a href='http://www.pr.gov.br' ><img src='http://www.pr.gov.br/logos/brasao_135x50.png' width='135' height='50'  border='0'/></a> </td>
			<td align='left'><img src='images/secretaria_peq.png' width='500' height='58' /> </td>
			<td>&nbsp;</td>
		</tr>	
	</table>
</div>

<c:url var="img_meio_cabec" value="images/back_barra_acao_top_mei.png" />

<table align='right' cellspacing='0'>
	<tr>
		<td><img src='images/back_barra_acao_top_esq.png'/></td>
		<td background="${img_meio_cabec}"><a href='javascript:history.back()'>voltar</a> </td>
		<td background="${img_meio_cabec}"><a href='javascript:history.back()'><img src='images/icon_voltar.png' width='16' height='16' border='0' /></a></td>
		<td><img src='images/back_barra_acao_top_dir.png'/></td>
	</tr>
</table>
