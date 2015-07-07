package gov.pr.celepar.abi.util;



import gov.pr.celepar.abi.pojo.ParametroVistoria;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class AtivarDesativarParametroVistoriaDecorator implements Decorator {
	
	public String decorate(Object object) throws Exception {
		StringBuffer campo = new StringBuffer();
		String  aux = "";
		if(object == null){
			campo.append("");
		}else {
			ParametroVistoria parametroVistoria = (ParametroVistoria)object;
			if (parametroVistoria.getIndAtivo()){
				campo.append("<a href=\"JavaScript:carregarInterfaceExibir("+parametroVistoria.getCodParametroVistoria()+",'inativar')\"><img src=\"images/icon_desligar.png\" border=\"0\" /></a>");
			}else{
				campo.append("<a href=\"JavaScript:carregarInterfaceExibir("+parametroVistoria.getCodParametroVistoria()+",'ativar')\"><img src=\"images/icon_selecionar.png\" border=\"0\" /></a>");
			}			
		}
		campo.append(aux);			
		return campo.toString();
	}

}