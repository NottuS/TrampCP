package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.TipoConstrucaoForm;
import gov.pr.celepar.abi.pojo.TipoConstrucao;
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
 * @since 06/01/2010
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class TipoConstrucaoAction extends BaseDispatchAction {

	/**
	 * Realiza carga da página de listagem de tipo de construção.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward carregarPgListTipoConstrucao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return mapping.findForward("pgListTipoConstrucao");
    }

	/**
	 * Realiza carga da página de listagem de tipo de construção, fazendo pesquisa e trazendo todos os registros da tabela (termo da pesquisa é vazio).
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void carregarListaTipoConstrucao(ActionForm form, HttpServletRequest request ) throws ApplicationException {
	
		TipoConstrucaoForm tipoConstrucaoForm = (TipoConstrucaoForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = tipoConstrucaoForm.getDescricao() ==null ? "": StringUtil.tiraAcento(tipoConstrucaoForm.getDescricao().trim()) ;
		
		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));

		pagina = CadastroFacade.listarTipoConstrucao(pagina, descricao);
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
		if (pagina.getTotalRegistros()==0){
			throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
		}	
	
    }	

	
	/**
	 * Carrega pagina para alteração com os dados da tipo de construção de imóvel selecionada.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditTipoConstrucao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		

		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		TipoConstrucaoForm tipoConstrucaoForm = (TipoConstrucaoForm) form;
	
		try {
			if (tipoConstrucaoForm.getCodTipoConstrucao() != null) {
				
				TipoConstrucao tipoConstrucao = CadastroFacade.obterTipoConstrucao(Integer.valueOf(tipoConstrucaoForm.getCodTipoConstrucao()));
				tipoConstrucaoForm.setDescricao(tipoConstrucao.getDescricao());
				
			}
			return mapping.findForward("pgEditTipoConstrucao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListTipoConstrucao"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListTipoConstrucao"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","tipo de construção"}, e);
		}		
	}	

	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de tipoConstrucaos. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward pesquisarTipoConstrucao (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		TipoConstrucaoForm tipoConstrucaoForm = (TipoConstrucaoForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = tipoConstrucaoForm.getDescricao() ==null ? "": StringUtil.tiraAcento(tipoConstrucaoForm.getDescricao().trim());

		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));

		try {
			pagina = CadastroFacade.listarTipoConstrucao(pagina, descricao);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.setAttribute("pagina", pagina);
			if (pagina.getTotalRegistros()==0){
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			
			request.setAttribute("pagina", pagina);			
			return mapping.findForward("pgListTipoConstrucao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListTipoConstrucao"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListTipoConstrucao"));
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa","tipo de construção"}, e);
		}
	}

	
	/**
	 * Realiza o encaminhamento necessário para salvar o tipo de construção.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvarTipoConstrucao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		TipoConstrucaoForm tipoConstrucaoForm = (TipoConstrucaoForm) form;
		String descricaoNovaDemoninacao = tipoConstrucaoForm.getDescricao().trim();
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditTipoConstrucao");
		}		
		
		// Verifica se já existe descrição com mesmo nome
		if (CadastroFacade.verificaTipoConstrucaoDuplicado(descricaoNovaDemoninacao)){			
			setActionForward(mapping.findForward("pgEditTipoConstrucao"));
			throw new ApplicationException("AVISO.31",  new String[]{descricaoNovaDemoninacao}, ApplicationException.ICON_AVISO);
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			setActionForward(mapping.findForward("pgEditTipoConstrucao"));
			throw new ApplicationException("AVISO.200");
		}	
		
		try	{			
			TipoConstrucao tipoConstrucao = new TipoConstrucao();
			tipoConstrucao.setDescricao(tipoConstrucaoForm.getDescricao());

			CadastroFacade.salvarTipoConstrucao(tipoConstrucao);
			tipoConstrucaoForm.setDescricao("");
			addMessage("SUCESSO.25", request);	
			this.carregarListaTipoConstrucao(form, request);
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditTipoConstrucao"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditTipoConstrucao"));
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Tipo de Construção"}, e);
		}
		
		return mapping.findForward("pgListTipoConstrucao");
	}	
	
	/**
	 * Realiza o encaminhamento necessário para atualizar os dados do tipoConstrucao.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterarTipoConstrucao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		TipoConstrucaoForm tipoConstrucaoForm = (TipoConstrucaoForm) form;	
		String descricaoNovaDemoninacao = tipoConstrucaoForm.getDescricao().trim();
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditTipoConstrucao");
		}

		// Valida se descrição não vem vazia
		if ( descricaoNovaDemoninacao.length() == 0  ) {
			throw new ApplicationException("AVISO.201",ApplicationException.ICON_AVISO);
		}
		
		// Verifica se já existe descrição com mesmo nome
		if (CadastroFacade.verificaTipoConstrucaoDuplicado(descricaoNovaDemoninacao)){			
			setActionForward(mapping.findForward("pgEditTipoConstrucao"));
			throw new ApplicationException("AVISO.31",  new String[]{descricaoNovaDemoninacao}, ApplicationException.ICON_AVISO);
		}
		
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			setActionForward(mapping.findForward("pgEditTipoConstrucao"));
			throw new ApplicationException("AVISO.200");
		}
		
		try	{
			TipoConstrucao tipoConstrucao = CadastroFacade.obterTipoConstrucao(Integer.valueOf(tipoConstrucaoForm.getCodTipoConstrucao()));

			tipoConstrucao.setDescricao(tipoConstrucaoForm.getDescricao());
			CadastroFacade.alterarTipoConstrucao(tipoConstrucao);
			tipoConstrucaoForm.setDescricao("");
			addMessage("SUCESSO.25", request);
			this.carregarListaTipoConstrucao(form, request);
			return mapping.findForward("pgListTipoConstrucao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditTipoConstrucao"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditTipoConstrucao"));
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Tipo de Construção"}, e);
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
	
	public ActionForward excluirTipoConstrucao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		TipoConstrucaoForm tipoConstrucaoForm = (TipoConstrucaoForm) form;
			
		try {
			if (tipoConstrucaoForm.getCodTipoConstrucao() != null) {
				
				CadastroFacade.excluirTipoConstrucao(Integer.parseInt(tipoConstrucaoForm.getCodTipoConstrucao()));
				tipoConstrucaoForm.setDescricao("");
				addMessage("SUCESSO.27", request);
			}
			
			
			this.carregarListaTipoConstrucao(form, request);
			return mapping.findForward("pgListTipoConstrucao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListTipoConstrucao"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListTipoConstrucao"));
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Tipo de Construção"}, e);
		}
	}
		
}