package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.facade.OperacaoFacade;
import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

import java.util.Collection;

public class BotaoRenovarCessaoDeUsoDecorator implements Decorator {
	
	/**
	 * Metodo usado para exibir botao para excluir - habilitado /desabilitado . <br>
	 * @param obj : Object
	 * @return String
	 * @throws Exception
	 */
	public String decorate(Object obj) throws Exception {
		CessaoDeUso cessaoDeUsoMem = (CessaoDeUso) obj;
		
		CessaoDeUso cessaoDeUso = new CessaoDeUso();
		cessaoDeUso.setCessaoDeUsoOriginal(cessaoDeUsoMem);

		cessaoDeUso.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.RASCUNHO.getIndex()));
		Collection<CessaoDeUso> listRascunho = OperacaoFacade.verificarCessaoDeUsoVinculadaByStatusTermo(cessaoDeUso); 
		int qtdListRascunho = listRascunho.size();

		cessaoDeUso.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.EM_RENOVACAO.getIndex()));
		Collection<CessaoDeUso> listRenovacao = OperacaoFacade.verificarCessaoDeUsoVinculadaByStatusTermo(cessaoDeUso); 
		int qtdListRenovacao = listRenovacao.size();

		if ((cessaoDeUsoMem.getStatusTermo().getCodStatusTermo().equals(Dominios.statusTermo.VIGENTE.getIndex()) ||
			cessaoDeUsoMem.getStatusTermo().getCodStatusTermo().equals(Dominios.statusTermo.FINALIZADO.getIndex())) &&
			qtdListRascunho == 0 && qtdListRenovacao == 0) {
			return "<a href='javascript:renovar("+cessaoDeUsoMem.getCodCessaoDeUso()+")'><img src='/abi/images/icon_salvar.png' border='0' /></a> ";
		} else if (qtdListRascunho > 0 || qtdListRenovacao > 0) {
			String msg = "O Bem Imóvel ".concat(String.valueOf(cessaoDeUsoMem.getBemImovel().getCodBemImovel()));
			msg = msg.concat(" possui Cessão com status de ");
			if (qtdListRascunho > 0 && qtdListRenovacao > 0) {
				msg = msg.concat(" Rascunho e com status de Renovação. N° da cessão com status de Rascunho: ");
				for (Object element : listRascunho) {
					CessaoDeUso cessaoDeUso2 = (CessaoDeUso) element;
					msg = msg.concat(String.valueOf(cessaoDeUso2.getCodCessaoDeUso())).concat(",");
				}
				msg = msg.substring(0, msg.length()-1).concat(".");
				msg = msg.concat(" N° da cessão com status de Renovação: ");
				for (Object element : listRenovacao) {
					CessaoDeUso cessaoDeUso2 = (CessaoDeUso) element;
					msg = msg.concat(String.valueOf(cessaoDeUso2.getCodCessaoDeUso())).concat(",");
				}
				msg = msg.substring(0, msg.length()-1).concat(".");
			} else if (qtdListRascunho > 0 && qtdListRenovacao == 0) {
				msg = msg.concat(" Rascunho. N° da cessão: ");
				for (Object element : listRascunho) {
					CessaoDeUso cessaoDeUso2 = (CessaoDeUso) element;
					msg = msg.concat(String.valueOf(cessaoDeUso2.getCodCessaoDeUso())).concat(",");
				}
				msg = msg.substring(0, msg.length()-1).concat(".");
			} else if (qtdListRascunho == 0 && qtdListRenovacao > 0) {
				msg = msg.concat(" Renovação. N° da cessão: ");
				for (Object element : listRenovacao) {
					CessaoDeUso cessaoDeUso2 = (CessaoDeUso) element;
					msg = msg.concat(String.valueOf(cessaoDeUso2.getCodCessaoDeUso())).concat(",");
				}
				msg = msg.substring(0, msg.length()-1).concat(".");
			}
			String ret = "<img src='/abi/images/icon_salvar_desabilitado.png' title='#msg#' border='0' /> ";
			ret = ret.replace("#msg#", msg);
			return ret;
		} else {
			return "<img src='/abi/images/icon_salvar_desabilitado.png' title='Renovar Inativada' border='0' /> ";
		}
	}

}
