package gov.pr.celepar.ucs_manterinstituicao.decorator;

import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;
import gov.pr.celepar.ucs_manterinstituicao.pojo.NaturezaJuridica;

public class NaturezaJuridicaDecorator implements Decorator{

	@Override
	public String decorate(Object value) throws Exception {
		NaturezaJuridica nj = (NaturezaJuridica)value;
		StringBuilder sb = new StringBuilder();
		sb.append(nj.getCodNaturezaJuridica());
		sb.insert(sb.length()-1, '-');	
		sb.append(" ");
		sb.append(nj.getDescricao());
		return sb.toString();
	}
}
