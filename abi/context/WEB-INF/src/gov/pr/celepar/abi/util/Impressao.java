package gov.pr.celepar.abi.util;

import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;



public class Impressao implements Decorator {

	@Override
	public String decorate(Object arg0) throws Exception {
		if (arg0 == null){
			return "";
		}
		if (arg0 instanceof Number){
			StringBuilder result = new StringBuilder();
			result.append("<a href=\"impressaoBemImovel.do?action=carregarPgEditImpressaoBemImovel&codBemImovel=");
			result.append(arg0.toString());
			result.append("&actionType=imprimir?keepThis=true&TB_iframe=true&height=380&width=500\" title=\"Impressão de Bem Imóvel\" class=\"thickbox\"><img src=\'/abi/images/icon_pdf.png\' align=\"center\" border=\"0\"></a>");
			return result.toString();
		}
		return "";
	}
}
