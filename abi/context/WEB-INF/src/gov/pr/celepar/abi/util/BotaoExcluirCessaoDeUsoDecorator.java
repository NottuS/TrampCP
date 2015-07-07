package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class BotaoExcluirCessaoDeUsoDecorator implements Decorator {
	
	/**
	 * Metodo usado para exibir botao para excluir - habilitado /desabilitado . <br>
	 * @param obj : Object
	 * @return String
	 * @throws Exception
	 */
	public String decorate(Object obj) throws Exception {
		CessaoDeUso cessaoDeUso = (CessaoDeUso) obj;
		if (cessaoDeUso.getStatusTermo().getCodStatusTermo().equals(Dominios.statusTermo.RASCUNHO.getIndex()) ||
			cessaoDeUso.getStatusTermo().getCodStatusTermo().equals(Dominios.statusTermo.EM_RENOVACAO.getIndex())) {
			return "<a href='javascript:excluir("+cessaoDeUso.getCodCessaoDeUso()+")'><img src='/abi/images/icon_excluir.png' border='0' /></a> ";
		} else {
			return "<img src='/abi/images/icon_excluir_desabilitado.png' title='Inativada' border='0' /> ";
		}
	}

}
