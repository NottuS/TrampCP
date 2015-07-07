package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.SituacaoOcupacaoForm;
import gov.pr.celepar.abi.pojo.SituacaoOcupacao;
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
public class SituacaoOcupacaoAction extends BaseDispatchAction {

	/**
	 * Realiza carga da página de listagem de situacao de ocupação.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward carregarPgListSituacaoOcupacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return mapping.findForward("pgListSituacaoOcupacao");
    }


	/**
	 * Realiza carga da página de listagem de denominacao de imóvel, fazendo pesquisa e trazendo todos os registros da tabela (termo da pesquisa é vazio).
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void carregarListaSituacaoOcupacao(ActionForm form, HttpServletRequest request ) throws ApplicationException {
	
		SituacaoOcupacaoForm situacaoOcupacaoForm = (SituacaoOcupacaoForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = situacaoOcupacaoForm.getDescricao() ==null ? "": StringUtil.tiraAcento(situacaoOcupacaoForm.getDescricao().trim()) ;
		
		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA),Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));

		pagina = CadastroFacade.listarSituacaoOcupacao(pagina, descricao);
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
		if(pagina.getTotalRegistros() == 0) {
			throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
		}	
	
    }	

	
	/**
	 * Carrega pagina para alteração com os dados da denominação de imóvel selecionada.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditSituacaoOcupacao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		

		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		SituacaoOcupacaoForm situacaoOcupacaoForm = (SituacaoOcupacaoForm) form;
	
		try {
			if (situacaoOcupacaoForm.getCodSituacaoOcupacao() != null) {
				
				SituacaoOcupacao situacaoOcupacao = CadastroFacade.obterSituacaoOcupacao(Integer.valueOf(situacaoOcupacaoForm.getCodSituacaoOcupacao()));
				situacaoOcupacaoForm.setDescricao(situacaoOcupacao.getDescricao());
				
			}
			return mapping.findForward("pgEditSituacaoOcupacao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListSituacaoOcupacao"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListSituacaoOcupacao"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","situação de ocupação"}, e);
		}		
	}	

	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de situacaoOcupacaos. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward pesquisarSituacaoOcupacao (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		SituacaoOcupacaoForm situacaoOcupacaoForm = (SituacaoOcupacaoForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = situacaoOcupacaoForm.getDescricao() ==null ? "": StringUtil.tiraAcento(situacaoOcupacaoForm.getDescricao().trim());

		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));

		try {
			pagina = CadastroFacade.listarSituacaoOcupacao(pagina, descricao);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.setAttribute("pagina", pagina);
			if(pagina.getTotalRegistros() == 0) {
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			
			request.setAttribute("pagina", pagina);			
			return mapping.findForward("pgListSituacaoOcupacao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListSituacaoOcupacao"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListSituacaoOcupacao"));
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa","situacao de ocupacao"}, e);
		}
	}

	
	/**
	 * Realiza o encaminhamento necessário para salvar a siutacao de ocupacap.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvarSituacaoOcupacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		SituacaoOcupacaoForm situacaoOcupacaoForm = (SituacaoOcupacaoForm) form;
		String descricaoNovaDemoninacao = situacaoOcupacaoForm.getDescricao().trim();
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditSituacaoOcupacao");
		}		
		
		// Verifica se já existe descrição com mesmo nome
		if (CadastroFacade.verificaSituacaoOcupacaoDuplicado(descricaoNovaDemoninacao)){			
			setActionForward(mapping.findForward("pgEditSituacaoOcupacao"));
			throw new ApplicationException("AVISO.27",  new String[]{descricaoNovaDemoninacao}, ApplicationException.ICON_AVISO);
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			setActionForward(mapping.findForward("pgEditSituacaoOcupacao"));
			throw new ApplicationException("AVISO.200");
		}	
		
		try	{			
			SituacaoOcupacao situacaoOcupacao = new SituacaoOcupacao();
			situacaoOcupacao.setDescricao(situacaoOcupacaoForm.getDescricao());

			CadastroFacade.salvarSituacaoOcupacao(situacaoOcupacao);
			situacaoOcupacaoForm.setDescricao("");
			addMessage("SUCESSO.25", request);	
			this.carregarListaSituacaoOcupacao(form, request);
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditSituacaoOcupacao"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditSituacaoOcupacao"));
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Situação da Ocupação"}, e);
		}
		
		return mapping.findForward("pgListSituacaoOcupacao");
	}	
	
	/**
	 * Realiza o encaminhamento necessário para atualizar os dados do situacaoOcupacao.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterarSituacaoOcupacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		SituacaoOcupacaoForm situacaoOcupacaoForm = (SituacaoOcupacaoForm) form;	
		String descricaoNovaDemoninacao = situacaoOcupacaoForm.getDescricao().trim();
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditSituacaoOcupacao");
		}

		// Valida se descrição não vem vazia
		if ( descricaoNovaDemoninacao.length() == 0  ) {
			throw new ApplicationException("AVISO.201",ApplicationException.ICON_AVISO);
		}
		
		// Verifica se já existe descrição com mesmo nome
		if (CadastroFacade.verificaSituacaoOcupacaoDuplicado(descricaoNovaDemoninacao)){			
			setActionForward(mapping.findForward("pgEditSituacaoOcupacao"));
			throw new ApplicationException("AVISO.27",  new String[]{descricaoNovaDemoninacao}, ApplicationException.ICON_AVISO);
		}
		
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			setActionForward(mapping.findForward("pgEditSituacaoOcupacao"));
			throw new ApplicationException("AVISO.200");
		}
		
		try	{
			SituacaoOcupacao situacaoOcupacao = CadastroFacade.obterSituacaoOcupacao(Integer.valueOf(situacaoOcupacaoForm.getCodSituacaoOcupacao()));

			situacaoOcupacao.setDescricao(situacaoOcupacaoForm.getDescricao());
			CadastroFacade.alterarSituacaoOcupacao(situacaoOcupacao);
			situacaoOcupacaoForm.setDescricao("");
			addMessage("SUCESSO.25", request);
			this.carregarListaSituacaoOcupacao(form, request);
			return mapping.findForward("pgListSituacaoOcupacao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditSituacaoOcupacao"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditSituacaoOcupacao"));
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Situação da Ocupação"}, e);
		}		
	}
	
	/**
	 * Realiza o encaminhamento para exclusão de situacao de ocupacao.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward excluirSituacaoOcupacao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		SituacaoOcupacaoForm situacaoOcupacaoForm = (SituacaoOcupacaoForm) form;
			
		try {
			if (situacaoOcupacaoForm.getCodSituacaoOcupacao() != null) {
				
				CadastroFacade.excluirSituacaoOcupacao(Integer.parseInt(situacaoOcupacaoForm.getCodSituacaoOcupacao()));
				situacaoOcupacaoForm.setDescricao("");
				addMessage("SUCESSO.32", request);
			}
			
			
			this.carregarListaSituacaoOcupacao(form, request);
			return mapping.findForward("pgListSituacaoOcupacao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListSituacaoOcupacao"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListSituacaoOcupacao"));
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Situação da Ocupação"}, e);
		}
	}
		
}