package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.enumeration.SituacaoVistoria;
import gov.pr.celepar.abi.pojo.Vistoria;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class BotaoAlterarVistoriaDecorator implements Decorator {
	
	/**
	 * Metodo usado para exibir botao para alterar - habilitado /desabilitado . <br>
	 * @author tatianapires
	 * @param obj : Object
	 * @return String
	 * @throws Exception
	 */
	public String decorate(Object obj) throws Exception {
		Vistoria vistoria = (Vistoria) obj;
		if (SituacaoVistoria.ABERTA.getId().equals(vistoria.getStatusVistoria().getCodStatusVistoria())) {
			return "<a href='javascript:alterar("+vistoria.getCodVistoria()+")'><img src='/abi/images/icon_alterar.png' border='0' /></a> ";
		} else {
			return "<img src='/abi/images/icon_alterar_desabilitado.png' title='Finalizada' border='0' /> ";
		}
	}

}
