package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.dto.OcupacaoOrgaoResponsavelListaDTO;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;


public class ExcluirOrgaoOcupanteTerrenoDecorator implements Decorator {
	
	public String decorate(Object object) throws Exception {
		StringBuffer campo = new StringBuffer();
		if(object == null){
			campo.append("");
		}else if(object instanceof OcupacaoOrgaoResponsavelListaDTO){
			OcupacaoOrgaoResponsavelListaDTO obj = (OcupacaoOrgaoResponsavelListaDTO) object;
			if (obj.getAtivo()){
				campo.append("<a href=\"javascript:removerOcupacaoOrgaoResponsavel(\'" + obj.getCodOcupacao() + "')\"><img src=\"images/icon_excluir.png\" border=\"0\"></img></a>");
			}else {
				campo.append("<img src=\"images/icon_excluir_desabilitado.png\"></img>");
			}
				
		}
		return campo.toString();
	}

}