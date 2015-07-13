package gov.pr.celepar.ucs_manterinstituicao.decorator;

import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

import java.util.Date;

public class DataDecorator implements Decorator{

	@Override
	public String decorate(Object value) throws Exception {
		return gov.pr.celepar.framework.util.Data.formataData((Date)value, "dd/MM/yy");
	}

}
