package gov.pr.celepar.abi.util;

import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;
import gov.pr.celepar.framework.util.Valores;



public class Area implements Decorator {

	@Override
	public String decorate(Object arg0) throws Exception {
		if (arg0 == null){
			return "";
		}
		if (arg0 instanceof Number){
			return Valores.formatarParaDecimal((Number) arg0, 2);
		}
		return "";
	}
}
