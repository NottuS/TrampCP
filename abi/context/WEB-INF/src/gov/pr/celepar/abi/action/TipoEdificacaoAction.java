package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.TipoEdificacaoForm;
import gov.pr.celepar.abi.pojo.TipoEdificacao;
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
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 10/02/2010
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class TipoEdificacaoAction extends BaseDispatchAction {
	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de Tipo de Edificação. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */

	public ActionForward pesquisarTipoEdificacao (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		TipoEdificacaoForm tipoEdificacaoForm = (TipoEdificacaoForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = tipoEdificacaoForm.getDescricao() ==null ? "": StringUtil.tiraAcento(tipoEdificacaoForm.getDescricao().trim()) ;

		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina), Integer.parseInt(totalRegistros));

		try {
			pagina = CadastroFacade.listarTipoEdificacao(pagina, descricao);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.setAttribute("pagina", pagina);
			if (pagina.getTotalRegistros()==0){
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			return mapping.findForward("pgListTipoEdificacao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListTipoEdificacao"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListTipoEdificacao"));
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa","tipo de edificação"}, e);
		}
	}
	
	/**
	 * Carrega pagina para alteração com os dados do tipo de edificacao selecionado.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditTipoEdificacao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		

		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		TipoEdificacaoForm tipoEdificacaoForm = (TipoEdificacaoForm) form;
	
		try {
			if (tipoEdificacaoForm.getCodTipoEdificacao() != null) {
				
				TipoEdificacao tipoEdificacao = CadastroFacade.obterTipoEdificacao(Integer.parseInt(tipoEdificacaoForm.getCodTipoEdificacao()));
				tipoEdificacaoForm.setDescricao(tipoEdificacao.getDescricao());
				
			}
			return mapping.findForward("pgEditTipoEdificacao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListTipoEdificacao"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListTipoEdificacao"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","tipo de edificação"}, e);
		}		
	}
	
	/**
	 * Realiza o encaminhamento necessário para salvar o tipo de edificação.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvarTipoEdificacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgEditTipoEdificacao"));
		TipoEdificacaoForm tipoEdificacaoForm = (TipoEdificacaoForm) form;
		String descricaoNova = tipoEdificacaoForm.getDescricao().trim();
		
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditTipoEdificacao");
		}		
		
		// Verifica se já existe descrição com mesmo nome
		if (CadastroFacade.verificarTipoEdificacaoDuplicado(descricaoNova,Integer.parseInt("0"))){			
			throw new ApplicationException("AVISO.35",  new String[]{descricaoNova}, ApplicationException.ICON_AVISO);
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			throw new ApplicationException("AVISO.200");
		}	
		
		try	{			
			TipoEdificacao tipoEdificacao = new TipoEdificacao();
			tipoEdificacao.setDescricao(tipoEdificacaoForm.getDescricao());

			CadastroFacade.salvarTipoEdificacao(tipoEdificacao);
			tipoEdificacaoForm.setDescricao("");
			addMessage("SUCESSO.25", request);	
			this.carregarListaTipoEdificacao(form, request);
			
		} catch (ApplicationException appEx) {
		
			throw appEx;
		} catch (Exception e) {
			
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Tipo de Edificação"}, e);
		}
		
		return mapping.findForward("pgListTipoEdificacao");
	}	
	
	/**
	 * Realiza carga da página de listagem de tipo de edificação, fazendo pesquisa e trazendo todos os registros da tabela (termo da pesquisa é vazio).
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void carregarListaTipoEdificacao(ActionForm form, HttpServletRequest request ) throws ApplicationException {
			
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
			
		Pagina pagina = new Pagina( Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina), Integer.parseInt(totalRegistros));

		pagina = CadastroFacade.listarTipoEdificacao(pagina, "");
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
		if (pagina.getTotalRegistros()==0){
			throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
		}	
	
    }
	
	/**
	 * Realiza o encaminhamento necessário para atualizar os dados da situação do imóvel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterarTipoEdificacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgEditTipoEdificacao"));
		TipoEdificacaoForm tipoEdificacaoForm = (TipoEdificacaoForm) form;
		String descricaoNova = tipoEdificacaoForm.getDescricao().trim();
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditTipoEdificacao");
		}		
		
		// Verifica se já existe descrição com mesmo nome
		if (CadastroFacade.verificarTipoEdificacaoDuplicado(descricaoNova,Integer.parseInt(tipoEdificacaoForm.getCodTipoEdificacao()))){			
			throw new ApplicationException("AVISO.35",  new String[]{descricaoNova}, ApplicationException.ICON_AVISO);
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			throw new ApplicationException("AVISO.200");
		}	
		
		
		try	{
			TipoEdificacao tipoEdificacao  = CadastroFacade.obterTipoEdificacao(Integer.valueOf(tipoEdificacaoForm.getCodTipoEdificacao()));

			tipoEdificacao.setDescricao(tipoEdificacaoForm.getDescricao());
			CadastroFacade.alterarTipoEdificacao(tipoEdificacao);
			tipoEdificacaoForm.setDescricao("");
			addMessage("SUCESSO.25", request);
			this.carregarListaTipoEdificacao(form, request);
			return mapping.findForward("pgListTipoEdificacao");
			
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Tipo de Edificação"}, e);
		}		
	}
	
	/**
	 * Realiza o encaminhamento para exclusão de tipo de edificacao.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward excluirTipoEdificacao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgListTipoEdificacao"));
		TipoEdificacaoForm tipoEdificacaoForm = (TipoEdificacaoForm) form;
			
		try {
			if (tipoEdificacaoForm.getCodTipoEdificacao() != null) {
				
				CadastroFacade.excluirTipoEdificacao(Integer.parseInt(tipoEdificacaoForm.getCodTipoEdificacao()));
				tipoEdificacaoForm.setDescricao("");
				addMessage("SUCESSO.36", request);
			}
		
			this.carregarListaTipoEdificacao(form, request);
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			this.carregarListaTipoEdificacao(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarListaTipoEdificacao(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Tipo de Edificação"}, e);
		}
	}

}
