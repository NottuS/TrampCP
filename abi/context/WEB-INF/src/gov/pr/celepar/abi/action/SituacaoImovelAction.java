package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.SituacaoImovelForm;
import gov.pr.celepar.abi.pojo.SituacaoImovel;
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
 * @author Luciana R. B�lico
 * @version 1.0
 * @since 05/02/2010
 * 
 * Classe Action:
 * Respons�vel por manipular as requisi��es dos usu�rios
 */

public class SituacaoImovelAction extends BaseDispatchAction {
	
	/**
	 * Realiza o encaminhamento necess�rio para executar a listagem paginada de Situa��o Im�vel. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward pesquisarSituacaoImovel (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		SituacaoImovelForm situacaoImovelForm = (SituacaoImovelForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = situacaoImovelForm.getDescricao() ==null ? "": StringUtil.tiraAcento(situacaoImovelForm.getDescricao().trim()) ;

		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA),  Integer.parseInt(indicePagina),  Integer.parseInt(totalRegistros));

		try {
			pagina = CadastroFacade.listarSituacaoImovel(pagina, descricao);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.setAttribute("pagina", pagina);
			if (pagina.getTotalRegistros()==0){
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			return mapping.findForward("pgListSituacaoImovel");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListSituacaoImovel"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListSituacaoImovel"));
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa","situa��o do im�vel"}, e);
		}
	}
	
	/**
	 * Carrega pagina para altera��o com os dados da situa��o do im�vel selecionada.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditSituacaoImovel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		

		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		SituacaoImovelForm situacaoImovelForm = (SituacaoImovelForm) form;
	
		try {
			if (situacaoImovelForm.getCodSituacaoImovel() != null) {
				
				SituacaoImovel situacao = CadastroFacade.obterSituacaoImovel(Integer.parseInt(situacaoImovelForm.getCodSituacaoImovel()));
				situacaoImovelForm.setDescricao(situacao.getDescricao());
				
			}
			return mapping.findForward("pgEditSituacaoImovel");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListSituacaoImovel"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListSituacaoImovel"));
			throw new ApplicationException("ERRO.200", new String[]{"edi��o","situa��o do im�vel"}, e);
		}		
	}
	
	/**
	 * Realiza o encaminhamento necess�rio para salvar a situa��o do im�vel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvarSituacaoImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		setActionForward(mapping.findForward("pgEditSituacaoImovel"));
		SituacaoImovelForm situacaoImovelForm = (SituacaoImovelForm) form;
		String descricaoNovaSituacaoImovel = situacaoImovelForm.getDescricao().trim();
		
		// Aciona a valida��o do Form
		if(!validaForm(mapping,form,request)) {
			return getActionForward();
		}		
		
		// Verifica se j� existe descri��o com mesmo nome
		if (CadastroFacade.verificarSituacaoImovelDuplicado(descricaoNovaSituacaoImovel, Integer.parseInt("0"))){			
			throw new ApplicationException("AVISO.23",  new String[]{descricaoNovaSituacaoImovel}, ApplicationException.ICON_AVISO);
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			throw new ApplicationException("AVISO.200");
		}	
		
		try	{			
			SituacaoImovel situacaoImovel = new SituacaoImovel();
			situacaoImovel.setDescricao(situacaoImovelForm.getDescricao());

			CadastroFacade.salvarSituacaoImovel(situacaoImovel);
			situacaoImovelForm.setDescricao("");
			addMessage("SUCESSO.25", request);	
			this.carregarListaSituacaoImovel(form, request);
			
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Situa��o do Im�vel"}, e);
		}
		
		return mapping.findForward("pgListSituacaoImovel");
	}	
	
	/**
	 * Realiza carga da p�gina de listagem de situa��o do im�vel, fazendo pesquisa e trazendo todos os registros da tabela (termo da pesquisa � vazio).
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void carregarListaSituacaoImovel(ActionForm form, HttpServletRequest request ) throws ApplicationException {
			
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
			
		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina), Integer.parseInt(totalRegistros));

		pagina = CadastroFacade.listarSituacaoImovel(pagina, "");
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
		if (pagina.getTotalRegistros()==0){
			throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
		}	
	
    }
	
	/**
	 * Realiza o encaminhamento necess�rio para atualizar os dados da situa��o do im�vel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterarSituacaoImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		setActionForward(mapping.findForward("pgEditSituacaoImovel"));
		SituacaoImovelForm situacaoImovelForm = (SituacaoImovelForm) form;
		String descricaoNovaSituacaoImovel = situacaoImovelForm.getDescricao().trim();
		
		// Aciona a valida��o do Form
		if(!validaForm(mapping,form,request)) {
			return getActionForward();
		}		
		
		// Verifica se j� existe descri��o com mesmo nome
		if (CadastroFacade.verificarSituacaoImovelDuplicado(descricaoNovaSituacaoImovel, Integer.parseInt(situacaoImovelForm.getCodSituacaoImovel()))){			
			throw new ApplicationException("AVISO.23",  new String[]{descricaoNovaSituacaoImovel}, ApplicationException.ICON_AVISO);
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			throw new ApplicationException("AVISO.200");
		}	
		
		try	{
			SituacaoImovel situacaoImovel  = CadastroFacade.obterSituacaoImovel(Integer.parseInt(situacaoImovelForm.getCodSituacaoImovel()));

			situacaoImovel.setDescricao(situacaoImovelForm.getDescricao());
			CadastroFacade.alterarSituacaoImovel(situacaoImovel);
			situacaoImovelForm.setDescricao("");
			addMessage("SUCESSO.25", request);
			this.carregarListaSituacaoImovel(form, request);
			return mapping.findForward("pgListSituacaoImovel");
			
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Situa��o do Im�vel"}, e);
		}		
	}
	
	/**
	 * Realiza o encaminhamento para exclus�o de situa��o do im�vel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward excluirSituacaoImovel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		SituacaoImovelForm situacaoImovelForm = (SituacaoImovelForm) form;
			
		try {
			if (situacaoImovelForm.getCodSituacaoImovel() != null) {
				
				CadastroFacade.excluirSituacaoImovel(Integer.parseInt(situacaoImovelForm.getCodSituacaoImovel()));
				situacaoImovelForm.setDescricao("");
				addMessage("SUCESSO.30", request);
			}
		
			this.carregarListaSituacaoImovel(form, request);
			return mapping.findForward("pgListSituacaoImovel");
			
		} catch (ApplicationException appEx) {
			this.carregarListaSituacaoImovel(form, request);
			setActionForward(mapping.findForward("pgListSituacaoImovel"));
			throw appEx;
		} catch (Exception e) {
			this.carregarListaSituacaoImovel(form, request);
			setActionForward(mapping.findForward("pgListSituacaoImovel"));
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Situa��o do Im�vel"}, e);
		}
	}

}
