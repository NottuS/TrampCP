package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.pojo.ParametroVistoria;
import gov.pr.celepar.abi.pojo.ParametroVistoriaDominio;

import java.util.List;

public class ExibirDominioParametroVistoriaDecorator {

		public final String decorate(Object obj){
			ParametroVistoria parametroVistoria = (ParametroVistoria) obj;
			StringBuffer retorno = new StringBuffer();
			if (Integer.valueOf(1).equals(parametroVistoria.getIndTipoParametro())) { //Se parametroVistoria.indTipoParametro = 1 //texto
				retorno.append(parametroVistoria.getDescricao());
			} else { // Outras	
				List<ParametroVistoriaDominio> listaParametroVistoriaDominio = Util.setToList(parametroVistoria.getListaParametroVistoriaDominio());
				for (ParametroVistoriaDominio item : listaParametroVistoriaDominio) {
					retorno.append(item.getDescricao()).append("<br/>");
				}
			}
			return retorno.toString();
	    }

	}