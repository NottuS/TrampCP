package gov.pr.celepar.abi.util;



import gov.pr.celepar.abi.pojo.ParametroVistoria;
import gov.pr.celepar.abi.pojo.ParametroVistoriaDenominacaoImovel;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class DenominacaoImovelDecorator implements Decorator {
	
	public String decorate(Object object) throws Exception {
		StringBuffer campo = new StringBuffer();
		String  aux = "";
		if(object == null){
			campo.append("");
		}else {
			ParametroVistoria parametroVistoria = (ParametroVistoria)object;
			for (ParametroVistoriaDenominacaoImovel pvdi : parametroVistoria.getListaParametroVistoriaDenominacaoImovel()){
				if ("".equals(aux)){
					aux = pvdi.getDenominacaoImovel().getDescricao();
				}else{
					aux = aux.concat(", ").concat(pvdi.getDenominacaoImovel().getDescricao());	
				}
			}
		}
		campo.append(aux);			
		return campo.toString();
	}

}