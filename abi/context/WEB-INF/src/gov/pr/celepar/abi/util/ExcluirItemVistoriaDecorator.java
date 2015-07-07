package gov.pr.celepar.abi.util;

import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class ExcluirItemVistoriaDecorator implements Decorator {

	public final String decorate(Object obj){
		Integer codItemVistoria = (Integer) obj;
		StringBuffer retorno = new StringBuffer();
		retorno.append("<a href='JavaScript:excluirItem(");
		retorno.append(codItemVistoria);
		retorno.append(");'><img border='0' src='/abi/images/icon_excluir.png'/></a>");
		return retorno.toString();
    }

}