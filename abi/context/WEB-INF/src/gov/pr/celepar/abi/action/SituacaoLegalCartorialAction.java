package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.SituacaoLegalCartorialForm;
import gov.pr.celepar.abi.pojo.SituacaoLegalCartorial;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author pialarissi
 * @version 1.0
 * @since 26/03/2010
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */
public class SituacaoLegalCartorialAction extends BaseDispatchAction {

	/**
	 * Realiza carga da página de listagem de situacao legal cartorial.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward carregarPgListSituacaoLegalCartorial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return mapping.findForward("pgListSituacaoLegalCartorial");
    }

	/**
	 * Realiza carga da página de listagem de situacao legal cartorial, fazendo pesquisa e trazendo todos os registros da tabela (termo da pesquisa é vazio).
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void carregarListaSituacaoLegalCartorial(ActionForm form, HttpServletRequest request ) throws ApplicationException {
	
		SituacaoLegalCartorialForm situacaoLegalCartorialForm = (SituacaoLegalCartorialForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = situacaoLegalCartorialForm.getDescricao() ==null ? "": StringUtil.tiraAcento(situacaoLegalCartorialForm.getDescricao().trim()) ;
		
		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));

		pagina = CadastroFacade.listarSituacaoLegalCartorial(pagina, descricao);
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
		if (pagina.getTotalRegistros()==0){
			throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
		}	
	
    }	

	
	/**
	 * Carrega pagina para alteração com os dados da situacao legal cartorial.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditSituacaoLegalCartorial(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		

		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		SituacaoLegalCartorialForm situacaoLegalCartorialForm = (SituacaoLegalCartorialForm) form;
	
		try {
			if (situacaoLegalCartorialForm.getCodSituacaoLegalCartorial() != null) {
				
				SituacaoLegalCartorial situacaoLegalCartorial = CadastroFacade.obterSituacaoLegalCartorial(Integer.valueOf(situacaoLegalCartorialForm.getCodSituacaoLegalCartorial()));
				situacaoLegalCartorialForm.setDescricao(situacaoLegalCartorial.getDescricao());
				
			}
			return mapping.findForward("pgEditSituacaoLegalCartorial");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListSituacaoLegalCartorial"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListSituacaoLegalCartorial"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","situacao legal cartorial"}, e);
		}		
	}	

	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de situacaoLegalCartorials. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward pesquisarSituacaoLegalCartorial (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		SituacaoLegalCartorialForm situacaoLegalCartorialForm = (SituacaoLegalCartorialForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = situacaoLegalCartorialForm.getDescricao() ==null ? "": StringUtil.tiraAcento(situacaoLegalCartorialForm.getDescricao().trim());

		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));

		try {
			pagina = CadastroFacade.listarSituacaoLegalCartorial(pagina, descricao);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.setAttribute("pagina", pagina);
			if(pagina.getTotalRegistros() == 0) {
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			
			request.setAttribute("pagina", pagina);			
			return mapping.findForward("pgListSituacaoLegalCartorial");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListSituacaoLegalCartorial"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListSituacaoLegalCartorial"));
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa","situacao legal cartorial"}, e);
		}
	}

	
	/**
	 * Realiza o encaminhamento necessário para salvar o situacao legal cartorial.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvarSituacaoLegalCartorial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		SituacaoLegalCartorialForm situacaoLegalCartorialForm = (SituacaoLegalCartorialForm) form;
		String descricaoNovaDemoninacao = situacaoLegalCartorialForm.getDescricao().trim();
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditSituacaoLegalCartorial");
		}		
		
		// Verifica se já existe descrição com mesmo nome
		if (CadastroFacade.verificaSituacaoLegalCartorialDuplicado(descricaoNovaDemoninacao)){			
			setActionForward(mapping.findForward("pgEditSituacaoLegalCartorial"));
			throw new ApplicationException("AVISO.24",  new String[]{descricaoNovaDemoninacao}, ApplicationException.ICON_AVISO);
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			setActionForward(mapping.findForward("pgEditSituacaoLegalCartorial"));
			throw new ApplicationException("AVISO.200");
		}	
		
		try	{			
			SituacaoLegalCartorial situacaoLegalCartorial = new SituacaoLegalCartorial();
			situacaoLegalCartorial.setDescricao(situacaoLegalCartorialForm.getDescricao());

			CadastroFacade.salvarSituacaoLegalCartorial(situacaoLegalCartorial);
			situacaoLegalCartorialForm.setDescricao("");
			addMessage("SUCESSO.25", request);	
			this.carregarListaSituacaoLegalCartorial(form, request);
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditSituacaoLegalCartorial"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditSituacaoLegalCartorial"));
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Situação Legal Cartorial"}, e);
		}
		
		return mapping.findForward("pgListSituacaoLegalCartorial");
	}	
	
	/**
	 * Realiza o encaminhamento necessário para atualizar os dados do situacaoLegalCartorial.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterarSituacaoLegalCartorial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		SituacaoLegalCartorialForm situacaoLegalCartorialForm = (SituacaoLegalCartorialForm) form;	
		String descricaoNovaDemoninacao = situacaoLegalCartorialForm.getDescricao().trim();
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditSituacaoLegalCartorial");
		}

		// Valida se descrição não vem vazia
		if ( descricaoNovaDemoninacao.length() == 0  ) {
			throw new ApplicationException("AVISO.201",ApplicationException.ICON_AVISO);
		}
		
		// Verifica se já existe descrição com mesmo nome
		if (CadastroFacade.verificaSituacaoLegalCartorialDuplicado(descricaoNovaDemoninacao)){			
			setActionForward(mapping.findForward("pgEditSituacaoLegalCartorial"));
			throw new ApplicationException("AVISO.24",  new String[]{descricaoNovaDemoninacao}, ApplicationException.ICON_AVISO);
		}
		
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			setActionForward(mapping.findForward("pgEditSituacaoLegalCartorial"));
			throw new ApplicationException("AVISO.200");
		}
		
		try	{
			SituacaoLegalCartorial situacaoLegalCartorial = CadastroFacade.obterSituacaoLegalCartorial(Integer.valueOf(situacaoLegalCartorialForm.getCodSituacaoLegalCartorial()));

			situacaoLegalCartorial.setDescricao(situacaoLegalCartorialForm.getDescricao());
			CadastroFacade.alterarSituacaoLegalCartorial(situacaoLegalCartorial);
			situacaoLegalCartorialForm.setDescricao("");
			addMessage("SUCESSO.25", request);
			this.carregarListaSituacaoLegalCartorial(form, request);
			return mapping.findForward("pgListSituacaoLegalCartorial");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditSituacaoLegalCartorial"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditSituacaoLegalCartorial"));
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Situação Legal Cartorial"}, e);
		}		
	}
	
	/**
	 * Realiza o encaminhamento para exclusão de aluno.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward excluirSituacaoLegalCartorial(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		SituacaoLegalCartorialForm situacaoLegalCartorialForm = (SituacaoLegalCartorialForm) form;
			
		try {
			if (situacaoLegalCartorialForm.getCodSituacaoLegalCartorial() != null) {
				
				CadastroFacade.excluirSituacaoLegalCartorial(Integer.parseInt(situacaoLegalCartorialForm.getCodSituacaoLegalCartorial()));
				situacaoLegalCartorialForm.setDescricao("");
				addMessage("SUCESSO.31", request);
			}
			
			
			this.carregarListaSituacaoLegalCartorial(form, request);
			return mapping.findForward("pgListSituacaoLegalCartorial");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListSituacaoLegalCartorial"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListSituacaoLegalCartorial"));
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Situação Legal Cartorial"}, e);
		}
	}
		
}