package gov.pr.celepar.ucs_manterinstituicao.decorator;

import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class CPFDecorator implements Decorator{

	@Override
	public String decorate(Object value) throws Exception {
		return gov.pr.celepar.framework.util.CPF.formataCPF((String) value);
	}
}
