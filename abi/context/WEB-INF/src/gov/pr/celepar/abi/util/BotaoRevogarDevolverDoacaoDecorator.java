package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.pojo.Doacao;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class BotaoRevogarDevolverDoacaoDecorator implements Decorator {
	
	/**
	 * Metodo usado para exibir botao para revogar/devolver - habilitado /desabilitado . <br>
	 * @param obj : Object
	 * @return String
	 * @throws Exception
	 */
	public String decorate(Object obj) throws Exception {
		Doacao doacao = (Doacao) obj;
		if (doacao.getStatusTermo().getCodStatusTermo().equals(Dominios.statusTermo.VIGENTE.getIndex())) {
			return "<a href='javascript:revogarDevolver("+doacao.getCodDoacao()+")'><img src='/abi/images/icon_desfazer.png' border='0' /></a> ";
		} else {
			return "<img src='/abi/images/icon_desfazer_desabilitado.png' title='Inativada' border='0' /> ";
		}
	}

}
