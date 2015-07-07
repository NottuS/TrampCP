package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class BotaoRevogarDevolverCessaoDeUsoDecorator implements Decorator {
	
	/**
	 * Metodo usado para exibir botao para revogar/devolver - habilitado /desabilitado . <br>
	 * @param obj : Object
	 * @return String
	 * @throws Exception
	 */
	public String decorate(Object obj) throws Exception {
		CessaoDeUso cessaoDeUso = (CessaoDeUso) obj;
		if (cessaoDeUso.getStatusTermo().getCodStatusTermo().equals(Dominios.statusTermo.VIGENTE.getIndex()) ||
			cessaoDeUso.getStatusTermo().getCodStatusTermo().equals(Dominios.statusTermo.FINALIZADO.getIndex())){
			return "<a href='javascript:revogarDevolver("+cessaoDeUso.getCodCessaoDeUso()+")'><img src='/abi/images/icon_desfazer.png' border='0' /></a> ";
		} else {
			return "<img src='/abi/images/icon_desfazer_desabilitado.png' title='Inativada' border='0' /> ";
		}
	}

}
