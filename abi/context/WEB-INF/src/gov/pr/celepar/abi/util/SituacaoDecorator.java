package gov.pr.celepar.abi.util;



import gov.pr.celepar.abi.pojo.ParametroVistoria;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class SituacaoDecorator implements Decorator {
	
	public String decorate(Object object) throws Exception {
		StringBuffer campo = new StringBuffer();
		String  aux = "";
		if(object == null){
			campo.append("");
		}else {
			ParametroVistoria parametroVistoria = (ParametroVistoria)object;
			if (parametroVistoria.getIndAtivo()){
				aux = "Ativo";
			}else{
				aux = "Inativo";
			}
		}
		campo.append(aux);			
		return campo.toString();
	}

}