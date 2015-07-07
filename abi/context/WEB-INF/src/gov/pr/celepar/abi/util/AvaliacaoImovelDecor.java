package gov.pr.celepar.abi.util;

import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;



public class AvaliacaoImovelDecor implements Decorator {

	@Override
	public String decorate(Object object) throws Exception {
		
		if(object == null){
			return  "Valor da Edificação";
		}
		else{
			Integer indTipoAvaliacao = (Integer)object;
			if(Integer.valueOf(1).equals(indTipoAvaliacao)){
				return "Valor Total";
			}
			else{
				return "Valor do Terreno";
			}
		}
	}
}
