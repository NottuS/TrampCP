package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.CargoAssinaturaForm;
import gov.pr.celepar.abi.pojo.CargoAssinatura;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Vanessak
 * @version 1.0
 * @since 29/06/2011
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class CargoAssinaturaAction extends BaseDispatchAction {
	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de Cargo da Assinatura. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward pesquisarCargoAssinatura (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		CargoAssinaturaForm cargoAssinaturaForm = (CargoAssinaturaForm) form;
		verificarGrupoUsuarioLogado(cargoAssinaturaForm, request);
		
		Integer codInstituicao = null;
		if (cargoAssinaturaForm.getConInstituicao() != null && !cargoAssinaturaForm.getConInstituicao().isEmpty()){
			codInstituicao = Integer.valueOf(cargoAssinaturaForm.getConInstituicao());
		}

		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = cargoAssinaturaForm.getDescricao() ==null ? "": StringUtil.tiraAcento(cargoAssinaturaForm.getDescricao().trim()) ;

		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA),  Integer.parseInt(indicePagina),  Integer.parseInt(totalRegistros));

		try {
			pagina = CadastroFacade.listarCargoAssinatura(pagina, descricao, codInstituicao);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.getSession().setAttribute("pagina", pagina);
			if (pagina.getTotalRegistros()==0){
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListCargoAssinatura"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListCargoAssinatura"));
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa","Cargo da Assinatura"}, e);
		}
		return mapping.findForward("pgListCargoAssinatura");
	}
	
	/**
	 * Carrega pagina para alteração com os dados do Cargo da Assinatura.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditCargoAssinatura(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		setActionForward(mapping.findForward("pgListCargoAssinatura"));
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		CargoAssinaturaForm cargoAssinaturaForm = (CargoAssinaturaForm) form;
		verificarGrupoUsuarioLogado(cargoAssinaturaForm, request);
	
		try {
			if("incluir".equals(cargoAssinaturaForm.getActionType())) {
				cargoAssinaturaForm.limparFormCadastro();
			}
			if (cargoAssinaturaForm.getCodCargoAssinatura() != null) {
				CargoAssinatura cargoAssinatura = CadastroFacade.obterCargoAssinatura(Integer.parseInt(cargoAssinaturaForm.getCodCargoAssinatura()));
				cargoAssinaturaForm.setDescricao(cargoAssinatura.getDescricao());
				cargoAssinaturaForm.setInstituicao(cargoAssinatura.getInstituicao().getCodInstituicao().toString());
				cargoAssinaturaForm.setInstituicaoDesc(cargoAssinatura.getInstituicao().getSiglaDescricao());
			}
			
		} catch (ApplicationException appEx) {
			throw appEx;
			
		} catch (Exception e) {
			throw new ApplicationException("ERRO.200", new String[]{"edição","Cargo da Assinatura"}, e);
		}		
		return mapping.findForward("pgEditCargoAssinatura");
	}

	/**
	 * Realiza o encaminhamento necessário para salvar o Cargo da Assinatura.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvarCargoAssinatura(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		setActionForward(mapping.findForward("pgEditCargoAssinatura"));
		CargoAssinaturaForm cargoAssinaturaForm = (CargoAssinaturaForm) form;
		verificarGrupoUsuarioLogado(cargoAssinaturaForm, request);
		
		try	{
			// Aciona a validação do Form
			if(!validaDadosForm(cargoAssinaturaForm)) {
				return getActionForward();
			}
			
			// Verifica se já existe descrição com mesmo nome
			Integer codInstituicao = Integer.valueOf(cargoAssinaturaForm.getInstituicao());;
			String descricaoNova = cargoAssinaturaForm.getDescricao().trim();
			if (CadastroFacade.verificarCargoAssinaturaDuplicado(descricaoNova,Integer.parseInt("0"), codInstituicao)){			
				if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
					request.setAttribute("listaInstituicao", Util.htmlEncodeCollection(CadastroFacade.listarInstituicao()));
				}
				throw new ApplicationException("AVISO.70",  new String[]{descricaoNova}, ApplicationException.ICON_AVISO);
			}
			
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	
			CargoAssinatura cargoAssinatura = new CargoAssinatura();
			cargoAssinatura.setDescricao(cargoAssinaturaForm.getDescricao());
			cargoAssinatura.setInstituicao(CadastroFacade.obterInstituicao(codInstituicao));
					
			CadastroFacade.salvarCargoAssinatura(cargoAssinatura);
			cargoAssinaturaForm.limparFormCadastro();

			addMessage("SUCESSO.25", request);	
			
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Cargo da Assinatura"}, e);
		}
		
		return pesquisarCargoAssinatura(mapping, cargoAssinaturaForm, request, response);
	}	
	
	/**
	 * Realiza o encaminhamento necessário para atualizar os dados do Cargo da Assinatura.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterarCargoAssinatura(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		setActionForward(mapping.findForward("pgEditCargoAssinatura"));
		CargoAssinaturaForm cargoAssinaturaForm = (CargoAssinaturaForm) form;
		verificarGrupoUsuarioLogado(cargoAssinaturaForm, request);
		
		try	{			
			// Aciona a validação do Form
			if(!validaDadosForm(cargoAssinaturaForm)) {
				return mapping.findForward("pgEditCargoAssinatura");
			}		
			
			// Verifica se já existe descrição com mesmo nome
			CargoAssinatura ca = CadastroFacade.obterCargoAssinatura(Integer.parseInt(cargoAssinaturaForm.getCodCargoAssinatura()));
			Instituicao instituicao = CadastroFacade.obterInstituicao(ca.getInstituicao().getCodInstituicao());
			String descricaoNova = cargoAssinaturaForm.getDescricao().trim();
			if (CadastroFacade.verificarCargoAssinaturaDuplicado(descricaoNova,Integer.parseInt(cargoAssinaturaForm.getCodCargoAssinatura()), instituicao.getCodInstituicao())){			
				throw new ApplicationException("AVISO.70",  new String[]{descricaoNova}, ApplicationException.ICON_AVISO);
			}
			
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	

			CargoAssinatura cargoAssinatura  = CadastroFacade.obterCargoAssinatura(Integer.parseInt(cargoAssinaturaForm.getCodCargoAssinatura()));

			cargoAssinatura.setDescricao(cargoAssinaturaForm.getDescricao());
			CadastroFacade.alterarCargoAssinatura(cargoAssinatura);

			cargoAssinaturaForm.limparFormCadastro();
			addMessage("SUCESSO.25", request);
			
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Cargo da Assinatura"}, e);
		}		
		return pesquisarCargoAssinatura(mapping, cargoAssinaturaForm, request, response);
	}
	
	/**
	 * Realiza o encaminhamento para exclusão de Cargo da Assinatura.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward excluirCargoAssinatura(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgListCargoAssinatura"));
		CargoAssinaturaForm cargoAssinaturaForm = (CargoAssinaturaForm) form;
		verificarGrupoUsuarioLogado(cargoAssinaturaForm, request);
		try {
			if (cargoAssinaturaForm.getCodCargoAssinatura() != null) {
				CadastroFacade.excluirCargoAssinatura(Integer.parseInt(cargoAssinaturaForm.getCodCargoAssinatura()));
				cargoAssinaturaForm.limparFormCadastro();
				addMessage("SUCESSO.25", request);
			}
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Cargo da Assinatura"}, e);
		}
		return pesquisarCargoAssinatura(mapping, cargoAssinaturaForm, request, response);
	}

	private boolean validaDadosForm(ActionForm form) throws ApplicationException, Exception {
		CargoAssinaturaForm cargoAssinaturaForm = (CargoAssinaturaForm) form;
		StringBuffer str = new StringBuffer();
		
		if (cargoAssinaturaForm.getInstituicao() == null || cargoAssinaturaForm.getInstituicao().trim().length() == 0) {
			str.append("Instituição");
		}

		if (cargoAssinaturaForm.getDescricao() == null || cargoAssinaturaForm.getDescricao().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Descrição");
		}

		if (str.length() > 0) {
			str.append(".");
			throw new ApplicationException("AVISO.72", new String[]{str.toString()}, ApplicationException.ICON_AVISO);
		}

		if (cargoAssinaturaForm.getDescricao().length() > 0 && cargoAssinaturaForm.getDescricao().length() < 5) {
			throw new ApplicationException("AVISO.71", new String[]{"Descrição"}, ApplicationException.ICON_AVISO);
		}

		return true;
	}

	private void verificarGrupoUsuarioLogado(CargoAssinaturaForm localForm, HttpServletRequest request) throws ApplicationException {
		boolean result = CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo());
		if (!result) {
			String codInstituicao = CadastroFacade.obterInstituicaoUsuario(SentinelaComunicacao.getInstance(request).getCodUsuario()).getCodInstituicao().toString();
			localForm.setConInstituicao(codInstituicao);
			localForm.setInstituicao(codInstituicao);
		}
		localForm.setIndGrupoSentinela(request.getSession().getAttribute("indGrupoSentinela").toString());
		localForm.setAdm(GrupoSentinela.ADM_GERAL.getDescricao());
		if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
			request.setAttribute("listaInstituicao", Util.htmlEncodeCollection(CadastroFacade.listarComboInstituicao()));
		}

	}

}
