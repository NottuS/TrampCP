package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.pojo.ParametroVistoria;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class CheckBoxParametroVistoriaDecorator implements Decorator {

	public final String decorate(Object obj){
		ParametroVistoria parametroVistoria = (ParametroVistoria) obj;
		StringBuffer retorno = new StringBuffer();
		retorno.append("<input name='parametroVistoria_").append(parametroVistoria.getCodParametroVistoria()).append("' id='parametroVistoria_").append(parametroVistoria.getCodParametroVistoria()).append("' type='checkbox' checked='true'>");
		return retorno.toString();
    }

}