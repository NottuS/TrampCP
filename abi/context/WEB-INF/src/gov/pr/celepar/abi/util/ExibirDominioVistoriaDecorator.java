package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.pojo.ItemVistoria;
import gov.pr.celepar.abi.pojo.ItemVistoriaDominio;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

import java.util.List;

public class ExibirDominioVistoriaDecorator implements Decorator {

	public final String decorate(Object obj){
		ItemVistoria itemVistoria = (ItemVistoria) obj;
		StringBuffer retorno = new StringBuffer();
		if (Integer.valueOf(1).equals(itemVistoria.getIndTipoParametro())) { //Se itemVistoria.indTipoParametro = 1 //texto
			retorno.append(itemVistoria.getTextoDominio() == null ? "" : itemVistoria.getTextoDominio());
		} else { // Outras	
			List<ItemVistoriaDominio> listaItemVistoriaDominio = Util.setToList(itemVistoria.getListaItemVistoriaDominio());
			for (ItemVistoriaDominio item : listaItemVistoriaDominio) {
				if (item.getIndSelecionado() != null && item.getIndSelecionado()) {
					retorno.append(item.getDescricao()).append("<br/>");
				}
			}
		}
		return retorno.toString();
    }

}