package gov.pr.celepar.abi.util;



import gov.pr.celepar.abi.pojo.ParametroVistoria;
import gov.pr.celepar.abi.pojo.ParametroVistoriaDominio;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class DominioDecorator implements Decorator {
	
	public String decorate(Object object) throws Exception {
		StringBuffer campo = new StringBuffer();
		String  aux = "";
		if(object == null){
			campo.append("");
		}else {
			ParametroVistoria parametroVistoria = (ParametroVistoria)object;
			for (ParametroVistoriaDominio pvd : parametroVistoria.getListaParametroVistoriaDominio()){
				if ("".equals(aux)){
					aux = pvd.getDescricao();
				}else{
					aux = aux.concat(", ").concat(pvd.getDescricao());	
				}
			}
		}
		campo.append(aux);			
		return campo.toString();
	}

}