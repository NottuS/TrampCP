package gov.pr.celepar.ucs_manterinstituicao.decorator;

import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;
import gov.pr.celepar.ucs_manterinstituicao.pojo.Instituicao;

public class PorteDecorator implements Decorator{

	@Override
	public String decorate(Object value) throws Exception {
		Integer porte = (Integer) value;
		if( porte == 1){
			return Instituicao.Porte.Pequeno.getDescricao();
		}
		if(porte == 2){
			return Instituicao.Porte.Medio.getDescricao();
		}
		if(porte == 3){
			return Instituicao.Porte.Grande.getDescricao();
		}
		return "";
	}
}
