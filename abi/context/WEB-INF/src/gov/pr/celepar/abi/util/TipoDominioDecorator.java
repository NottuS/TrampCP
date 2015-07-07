package gov.pr.celepar.abi.util;



import gov.pr.celepar.abi.pojo.ParametroVistoria;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class TipoDominioDecorator implements Decorator {
	
	public String decorate(Object object) throws Exception {
		StringBuffer campo = new StringBuffer();
		String  aux = "";
		if(object == null){
			campo.append("");
		}else {
			ParametroVistoria parametroVistoria = (ParametroVistoria)object;
			if (parametroVistoria.getIndTipoParametro().equals(1)){
				aux = "Texto";
			}
			if (parametroVistoria.getIndTipoParametro().equals(2)){
				aux = "Valor unit�rio (apenas um ser� selecionado)";
			}
			if (parametroVistoria.getIndTipoParametro().equals(3)){
				aux = "M�ltiplos valores (mais de um poder� ser selecionado)";
			}
		}
		campo.append(aux);			
		return campo.toString();
	}

}