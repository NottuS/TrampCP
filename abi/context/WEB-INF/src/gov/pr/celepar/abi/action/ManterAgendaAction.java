package gov.pr.celepar.abi.action;


import gov.pr.celepar.abi.facade.OperacaoFacade;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Classe: ManterAgenda
 * Responsavel pelo UCS Monitorar Agenda
 * 
 * @author oksana
 * @version 0.1
 * @since 06/07/2011
 */
public class ManterAgendaAction extends BaseDispatchAction {

	/**
	 * Carrega a interface Inicial do respectivo Caso de Uso.<br>
	 * @author oksana
	 * @since 06/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward carregarInterfaceInicial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			String path = request.getSession().getServletContext().getRealPath("");
			OperacaoFacade.monitorarAgendaQuartz(path);
			this.addMessage("SUCESSO.2000", request);
 			setActionForward(new ActionForward("/entrada.do?action=carregarEntrada"));
			return getActionForward();	
		}catch (ApplicationException ae) {
			throw ae;
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterAgendaAction.class.getSimpleName()
					+".carregarInterfaceInicial()"}, e, ApplicationException.ICON_ERRO);
		}
	}
	


}