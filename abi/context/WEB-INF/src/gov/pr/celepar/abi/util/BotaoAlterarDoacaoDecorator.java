package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.pojo.Doacao;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class BotaoAlterarDoacaoDecorator implements Decorator {
	
	/**
	 * Metodo usado para exibir botao para alterar - habilitado /desabilitado . <br>
	 * @param obj : Object
	 * @return String
	 * @throws Exception
	 */
	public String decorate(Object obj) throws Exception {
		Doacao doacao = (Doacao) obj;
		if (doacao.getStatusTermo().getCodStatusTermo().equals(Dominios.statusTermo.RASCUNHO.getIndex()) ||
			doacao.getStatusTermo().getCodStatusTermo().equals(Dominios.statusTermo.VIGENTE.getIndex())) {
			return "<a href='javascript:alterar("+doacao.getCodDoacao()+","+doacao.getStatusTermo().getCodStatusTermo()+")'><img src='/abi/images/icon_alterar.png' border='0' /></a> ";
		} else {
			return "<img src='/abi/images/icon_alterar_desabilitado.png' title='Inativada' border='0' /> ";
		}
	}

}
