package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.pojo.Assinatura;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class BotaoAlterarAssinaturaDecorator implements Decorator {
	
	/**
	 * Metodo usado para exibir botao para alterar - habilitado /desabilitado . <br>
	 * @author tatianapires
	 * @param obj : Object
	 * @return String
	 * @throws Exception
	 */
	public String decorate(Object obj) throws Exception {
		Assinatura assinatura = (Assinatura) obj;
		if (assinatura.getIndAtivo()) {
			return "<a href='javascript:alterar("+assinatura.getCodAssinatura()+")'><img src='/abi/images/icon_alterar.png' border='0' /></a> ";
		} else {
			return "<img src='/abi/images/icon_alterar_desabilitado.png' title='Inativada' border='0' /> ";
		}
	}

}
