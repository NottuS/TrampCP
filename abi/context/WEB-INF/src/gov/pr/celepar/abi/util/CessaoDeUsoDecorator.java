package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;



public class CessaoDeUsoDecorator implements Decorator {

	@Override
	public String decorate(Object arg0) throws Exception {
		if (arg0 == null){
			return "";
		}
		if (arg0 instanceof Number){
			String cessao = CadastroFacade.obterDescricaoCessaoDeUso((Integer) arg0);
			return cessao;
		}
		return "";
	}
}
