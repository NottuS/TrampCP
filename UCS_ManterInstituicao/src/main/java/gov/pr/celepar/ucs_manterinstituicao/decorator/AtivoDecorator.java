package gov.pr.celepar.ucs_manterinstituicao.decorator;

import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class AtivoDecorator implements Decorator{

	@Override
	public String decorate(Object value) throws Exception {
		Boolean ativo = (Boolean) value;
		
		if (ativo){
			return "<img borde=\"0\" src=\"/UCS_ManterInstituicao/images/icon_msg_sucesso.png\"></img>";
		} 
		return "<img borde=\"0\" src=\"/UCS_ManterInstituicao/images/icon_msg_erro.png\"></img>";
	}

}
