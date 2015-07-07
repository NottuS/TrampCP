package gov.pr.celepar.abi.util;



import gov.pr.celepar.abi.pojo.ParametroVistoria;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class ExcluirParametroVistoriaDecorator implements Decorator {
	
	public String decorate(Object object) throws Exception {
		StringBuffer campo = new StringBuffer();
		String  aux = "";
		if(object == null){
			campo.append("");
		}else {
			ParametroVistoria parametroVistoria = (ParametroVistoria)object;
			if (parametroVistoria.getListaItemVistoria() == null || parametroVistoria.getListaItemVistoria().isEmpty()){
				campo.append("<a href=\"JavaScript:carregarInterfaceExibir("+parametroVistoria.getCodParametroVistoria()+",'excluir')\"><img src=\"images/icon_excluir.png\" border=\"0\" /></a>");
			}else{
				campo.append("<img src=\"images/icon_excluir_desabilitado.png\" onmouseover=\"maisinfo('Parâmetro não pode ser excluído, está sendo utilizado em vistoria.','right');\" onMouseOut=\"menosinfo();\" border=\"0\" />");
			}
		}
		campo.append(aux);			
		return campo.toString();
	}

}