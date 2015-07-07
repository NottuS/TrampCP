package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.generico.action.BaseAction;
import gov.pr.celepar.framework.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LoginUsuarioAction extends BaseAction {

	/**
	 * Carrega a interface Inicial do respectivo Caso de Uso.<br>
	 * @author rodrigoalbani 
	 * @since  01/03/2009
	 * @param  mapping : ActionMapping
	 * @param  form : ActionForm
	 * @param  request : HttpServletRequest
	 * @param  response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward carregarInterfaceInicial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			request.getSession().setAttribute("indGrupoSentinela", null);
			setActionForward(new ActionForward("/entrada.do?action=carregarEntrada"));
			
			//Verifica se o usuário logado é Administrador geral do sistema: sem restrição de acesso
			if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())){
				request.getSession().setAttribute("indGrupoSentinela", GrupoSentinela.ADM_GERAL.getDescricao());
			}else{
				if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_INST.getCodigo())){
					request.getSession().setAttribute("indGrupoSentinela", GrupoSentinela.ADM_INST.getDescricao());
				} else {
					if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo())){
						request.getSession().setAttribute("indGrupoSentinela", GrupoSentinela.OPE_ORG_ABI.getDescricao());
					} else {
						if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ABI.getCodigo())){
							request.getSession().setAttribute("indGrupoSentinela", GrupoSentinela.OPE_ABI.getDescricao());
						} else {
							if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.LEI_ABI.getCodigo())){
								request.getSession().setAttribute("indGrupoSentinela", GrupoSentinela.LEI_ABI.getDescricao());
							}
						}
					}
				}
			}
			return getActionForward();
		}catch (ApplicationException ae) {
			throw ae;
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[] { LoginUsuarioAction.class.getSimpleName() + ".carregarInterfaceInicial()" }, e, ApplicationException.ICON_ERRO);
		}
	}

	
}
