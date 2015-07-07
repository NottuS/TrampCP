package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.pojo.Transferencia;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class BotaoAlterarTransferenciaDecorator implements Decorator {
	
	/**
	 * Metodo usado para exibir botao para alterar - habilitado /desabilitado . <br>
	 * @param obj : Object
	 * @return String
	 * @throws Exception
	 */
	public String decorate(Object obj) throws Exception {
		Transferencia transferencia = (Transferencia) obj;
		if (transferencia.getStatusTermo().getCodStatusTermo().equals(Dominios.statusTermo.RASCUNHO.getIndex()) ||
				transferencia.getStatusTermo().getCodStatusTermo().equals(Dominios.statusTermo.VIGENTE.getIndex())) {
			return "<a href='javascript:alterar("+transferencia.getCodTransferencia()+","+transferencia.getStatusTermo().getCodStatusTermo()+")'><img src='/abi/images/icon_alterar.png' border='0' /></a> ";
		} else {
			return "<img src='/abi/images/icon_alterar_desabilitado.png' title='Inativada' border='0' /> ";
		}
	}

}
