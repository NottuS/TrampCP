package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.enumeration.SituacaoVistoria;
import gov.pr.celepar.abi.pojo.Vistoria;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class BotaoExcluirVistoriaDecorator implements Decorator {
	
	/**
	 * Metodo usado para exibir botao para excluir - habilitado /desabilitado . <br>
	 * @author tatianapires
	 * @param obj : Object
	 * @return String
	 * @throws Exception
	 */
	public String decorate(Object obj) throws Exception {
		Vistoria vistoria = (Vistoria) obj;
		
		if (SituacaoVistoria.ABERTA.getId().equals(vistoria.getStatusVistoria().getCodStatusVistoria())  || (SituacaoVistoria.FINALIZADA.getId().equals(vistoria.getStatusVistoria().getCodStatusVistoria())) && vistoria.getPermissaoExclusaoVistoria()) { 
			return "<a href='javascript:excluir("+vistoria.getCodVistoria()+")'><img src='/abi/images/icon_excluir.png' border='0' /></a> ";
		} else {
			return "<img src='/abi/images/icon_excluir_desabilitado.png' title='Finalizada' border='0' /> ";
		}
	}

}
