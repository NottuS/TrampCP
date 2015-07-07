package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class BotaoAlterarCessaoDeUsoDecorator implements Decorator {
	
	/**
	 * Metodo usado para exibir botao para alterar - habilitado /desabilitado . <br>
	 * @param obj : Object
	 * @return String
	 * @throws Exception
	 */
	public String decorate(Object obj) throws Exception {
		CessaoDeUso cessaoDeUso = (CessaoDeUso) obj;
		return "<a href='javascript:alterar("+cessaoDeUso.getCodCessaoDeUso()+","+cessaoDeUso.getStatusTermo().getCodStatusTermo()+")'><img src='/abi/images/icon_alterar.png' border='0' /></a> ";
	}

}
