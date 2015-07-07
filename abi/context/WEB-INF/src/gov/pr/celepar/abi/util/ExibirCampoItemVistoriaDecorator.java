package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.pojo.ItemVistoria;
import gov.pr.celepar.abi.pojo.ItemVistoriaDominio;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class ExibirCampoItemVistoriaDecorator implements Decorator {

	public final String decorate(Object obj){
		ItemVistoria itemVistoria = (ItemVistoria) obj;
		StringBuffer retorno = new StringBuffer();
		if (Integer.valueOf(1).equals(itemVistoria.getIndTipoParametro())) { //Se itemVistoria.indTipoParametro = 1 //texto
			if (itemVistoria.getTextoDominio() == null){
				itemVistoria.setTextoDominio(" ");
			}
			retorno.append("<input type='text' maxlength='150' size='100' name='t_itemVistoria_").append(itemVistoria.getCodItemVistoria());
			retorno.append("' id='t_itemVistoria_").append(itemVistoria.getCodItemVistoria()).append("' value='");
			retorno.append(itemVistoria.getTextoDominio()).append("' />");
			
		} else if (Integer.valueOf(2).equals(itemVistoria.getIndTipoParametro())) { //Se itemVistoria.indTipoParametro = 2 //combo
			
			retorno.append("<select name='co_itemVistoria_").append(itemVistoria.getCodItemVistoria());
			retorno.append("' id='co_itemVistoria_").append(itemVistoria.getCodItemVistoria()).append("' >");
			
			retorno.append("<option value=''>-- Selecione --</option>");
			
			for (ItemVistoriaDominio itemVistoriaDominio : itemVistoria.getListaItemVistoriaDominio()) {
				if (itemVistoriaDominio.getIndSelecionado() != null && itemVistoriaDominio.getIndSelecionado()) {
					retorno.append("<option value='").append(itemVistoriaDominio.getCodItemVistoriaDominio()).append("' ");
					retorno.append("selected='selected'>").append(itemVistoriaDominio.getDescricao()).append("</option>");
				}	
				else {
					retorno.append("<option value='").append(itemVistoriaDominio.getCodItemVistoriaDominio()).append("'> ");
					retorno.append(itemVistoriaDominio.getDescricao()).append("</option>");
				}
				retorno.append("<br />");
					
			}
			
			retorno.append("</select>");

		} else if (Integer.valueOf(3).equals(itemVistoria.getIndTipoParametro())) { //Se itemVistoria.indTipoParametro = 3 //check

			for (ItemVistoriaDominio itemVistoriaDominio : itemVistoria.getListaItemVistoriaDominio()) {
				if (itemVistoriaDominio.getIndSelecionado() != null && itemVistoriaDominio.getIndSelecionado()) {
					retorno.append("<input name='ch_itemVistoria_").append(itemVistoriaDominio.getCodItemVistoriaDominio()).append("' id='ch_itemVistoria_");
					retorno.append(itemVistoriaDominio.getCodItemVistoriaDominio()).append("' type='checkbox' checked='true' /> &nbsp; ");
					retorno.append(itemVistoriaDominio.getDescricao());
				}	
				else {
					retorno.append("<input name='ch_itemVistoria_").append(itemVistoriaDominio.getCodItemVistoriaDominio()).append("' id='ch_itemVistoria_");
					retorno.append(itemVistoriaDominio.getCodItemVistoriaDominio()).append("' type='checkbox' /> &nbsp; ");
					retorno.append(itemVistoriaDominio.getDescricao());
				}
				retorno.append("<br />");
					
			}
			
		}
		return retorno.toString();
    }

}