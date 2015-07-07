package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.TipoDocumentacaoForm;
import gov.pr.celepar.abi.pojo.TipoDocumentacao;
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
 * @since 20/01/2010
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class TipoDocumentacaoAction extends BaseDispatchAction {
	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de Tipos de Documentacao. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward pesquisarTipoDocumentacao (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		
		TipoDocumentacaoForm tipoDocumentacaoForm = (TipoDocumentacaoForm) form;

		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = tipoDocumentacaoForm.getDescricao() == null ? "": StringUtil.tiraAcento(tipoDocumentacaoForm.getDescricao().trim()) ;

		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina), Integer.parseInt(totalRegistros));

		try {
			pagina = CadastroFacade.listarTipoDocumentacao(pagina, descricao);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.setAttribute("pagina", pagina);
			if(pagina.getTotalRegistros() == 0) {
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			return mapping.findForward("pgListTipoDocumentacao");

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListTipoDocumentacao"));
			throw appEx;

		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListTipoDocumentacao"));
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa","tipo de documentação"}, e);
		}
	}

	/**
	 * Carrega pagina para alteração/inclusão com os dados do tipo de documento selecionado.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditTipoDocumentacao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		

		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		TipoDocumentacaoForm tipoDocumentacaoForm = (TipoDocumentacaoForm) form;
	
		try {
			if (tipoDocumentacaoForm.getCodTipoDocumentacao() != null) {
				
				TipoDocumentacao tipoDocumentacao = CadastroFacade.obterTipoDocumentacao(Integer.parseInt(tipoDocumentacaoForm.getCodTipoDocumentacao()));
				tipoDocumentacaoForm.setDescricao(tipoDocumentacao.getDescricao());
				
			}
			return mapping.findForward("pgEditTipoDocumentacao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListTipoDocumentacao"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListTipoDocumentacao"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","tipo de documentação"}, e);
		}		
	}
	
	/**
	 * Realiza carga da página de listagem de tipos de documentacao.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgListTipoDocumentacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return mapping.findForward("pgListTipoDocumentacao");

    }
	
	/**
	 * Realiza o encaminhamento necessário para salvar o tipo de documentacao
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvarTipoDocumentacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgEditTipoDocumentacao"));
		TipoDocumentacaoForm tipoDocumentacaoForm = (TipoDocumentacaoForm) form;
		String descricaoNovoTipoDocumentacao = tipoDocumentacaoForm.getDescricao().trim();
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditTipoDocumentacao");
		}		
		
		// Verifica se já existe descrição com mesmo nome
		if (CadastroFacade.verificaTipoDocumentacaoDuplicado(descricaoNovoTipoDocumentacao, Integer.parseInt("0"))){			
			throw new ApplicationException("AVISO.33",  new String[]{descricaoNovoTipoDocumentacao}, ApplicationException.ICON_AVISO);
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			throw new ApplicationException("AVISO.200");
		}	
		
		try	{			
			TipoDocumentacao tipoDocumentacao = new TipoDocumentacao();
			tipoDocumentacao.setDescricao(tipoDocumentacaoForm.getDescricao());

			CadastroFacade.salvarTipoDocumentacao(tipoDocumentacao);
			tipoDocumentacaoForm.setDescricao("");
			addMessage("SUCESSO.25", request);	
			this.carregarListaTipoDocumentacao(form, request);
			
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Tipo de Documentação"}, e);
		}
		
		return mapping.findForward("pgListTipoDocumentacao");
	}	
	
	/**
	 * Realiza carga da página de listagem de tipos de documentação, fazendo pesquisa e trazendo todos os registros da tabela (termo da pesquisa é vazio).
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void carregarListaTipoDocumentacao(ActionForm form, HttpServletRequest request ) throws ApplicationException {
	
		TipoDocumentacaoForm tipoDocumentacaoForm = (TipoDocumentacaoForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = tipoDocumentacaoForm.getDescricao() == null ? "": StringUtil.tiraAcento(tipoDocumentacaoForm.getDescricao().trim()) ;
		
		Pagina pagina = new Pagina( Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina), Integer.parseInt(totalRegistros));

		pagina = CadastroFacade.listarTipoDocumentacao(pagina, descricao);
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
		if(pagina.getTotalRegistros() == 0) {
			throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
		}	
	
    }
	
	/**
	 * Realiza o encaminhamento necessário para atualizar os dados do tipo documentação.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterarTipoDocumentacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgEditTipoDocumentacao"));
		TipoDocumentacaoForm tipoDocumentacaoForm = (TipoDocumentacaoForm) form;	
		String descricaoNovoTipoDocumentacao = tipoDocumentacaoForm.getDescricao().trim();
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditTipoDocumentacao");
		}

		// Valida se descrição não vem vazia
		if ( descricaoNovoTipoDocumentacao.length() == 0  ) {
			throw new ApplicationException("AVISO.201",ApplicationException.ICON_AVISO);
		}
		
		// Verifica se já existe descrição com mesmo nome
		if (CadastroFacade.verificaTipoDocumentacaoDuplicado(descricaoNovoTipoDocumentacao, Integer.parseInt(tipoDocumentacaoForm.getCodTipoDocumentacao()))){			
			throw new ApplicationException("AVISO.33",  new String[]{descricaoNovoTipoDocumentacao}, ApplicationException.ICON_AVISO);
		}
		
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			throw new ApplicationException("AVISO.200");
		}
		
		try	{
			TipoDocumentacao tipoDocumentacao = CadastroFacade.obterTipoDocumentacao(Integer.parseInt(tipoDocumentacaoForm.getCodTipoDocumentacao()));

			tipoDocumentacao.setDescricao(tipoDocumentacaoForm.getDescricao());
			CadastroFacade.alterarTipoDocumentacao(tipoDocumentacao);
			tipoDocumentacaoForm.setDescricao("");
			addMessage("SUCESSO.25", request);
			this.carregarListaTipoDocumentacao(form, request);
			return mapping.findForward("pgListTipoDocumentacao");
			
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Tipo de Documentação"}, e);
		}		
	}

	/**
	 * Realiza o encaminhamento para exclusão de tipo de documentação.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */

	public ActionForward excluirTipoDocumentacao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgListTipoDocumentacao"));
		TipoDocumentacaoForm tipoDocumentacaoForm = (TipoDocumentacaoForm) form;

		try {
			if (tipoDocumentacaoForm.getCodTipoDocumentacao() != null) {

				CadastroFacade.excluirTipoDocumentacao(Integer.parseInt(tipoDocumentacaoForm.getCodTipoDocumentacao()));
				tipoDocumentacaoForm.setDescricao("");
				addMessage("SUCESSO.35", request);
			}

			this.carregarListaTipoDocumentacao(form, request);
			return getActionForward();

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Tipo de Documentação"}, e);
		}
	}

}
