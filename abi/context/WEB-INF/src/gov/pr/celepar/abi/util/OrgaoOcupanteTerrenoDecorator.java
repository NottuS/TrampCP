package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;


public class OrgaoOcupanteTerrenoDecorator implements Decorator {
	
	public String decorate(Object object) throws Exception {
		StringBuffer campo = new StringBuffer();
		if(object == null){
			campo.append("");
		}else if(object instanceof BemImovel){
			BemImovel obj = (BemImovel) object;
			if (obj.getSomenteTerreno().equals("S")){
				campo.append("<img src=\"images/icon_msg_sucesso.png\"></img>");
			}
				
		}
		return campo.toString();
	}

}