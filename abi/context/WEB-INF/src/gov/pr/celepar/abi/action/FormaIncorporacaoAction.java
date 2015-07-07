package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.FormaIncorporacaoForm;
import gov.pr.celepar.abi.pojo.FormaIncorporacao;
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
 * @since 19/01/2010
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class FormaIncorporacaoAction  extends BaseDispatchAction {
	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de FormaIncorporacao. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */

	public ActionForward pesquisarFormaIncorporacao (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		FormaIncorporacaoForm formaIncorporacaoForm = (FormaIncorporacaoForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = formaIncorporacaoForm.getDescricao() ==null ? "": StringUtil.tiraAcento(formaIncorporacaoForm.getDescricao().trim()) ;

		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));

		try {
			pagina = CadastroFacade.listarFormaIncorporacao(pagina, descricao);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.setAttribute("pagina", pagina);
			if(pagina.getTotalRegistros() == 0) {
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			return mapping.findForward("pgListFormaIncorporacao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListFormaIncorporacao"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListFormaIncorporacao"));
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa","forma de incorporação"}, e);
		}
	}
	
	/**
	 * Carrega pagina para alteração com os dados da forma de incorporacao selecionada.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditFormaIncorporacao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		

		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		FormaIncorporacaoForm formaIncorporacaoForm = (FormaIncorporacaoForm) form;
	
		try {
			if (formaIncorporacaoForm.getCodFormaIncorporacao() != null) {
				
				FormaIncorporacao formaIncorporacao = CadastroFacade.obterFormaIncorporacao(Integer.valueOf(formaIncorporacaoForm.getCodFormaIncorporacao()));
				formaIncorporacaoForm.setDescricao(formaIncorporacao.getDescricao());
				
			}
			return mapping.findForward("pgEditFormaIncorporacao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListFormaIncorporacao"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListFormaIncorporacao"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","forma de incorporação"}, e);
		}		
	}
	
	/**
	 * Realiza carga da página de listagem de forma de incorporação.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgListFormaIncorporacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return mapping.findForward("pgListFormaIncorporacao");

    }
	
	/**
	 * Realiza o encaminhamento necessário para salvar a forma de incorporacao.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvarFormaIncorporacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		FormaIncorporacaoForm formaIncorporacaoForm = (FormaIncorporacaoForm) form;
		String descricaoNovaFormaIncorporacao = formaIncorporacaoForm.getDescricao().trim();
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditFormaIncorporacao");
		}		
		
		// Verifica se já existe descrição com mesmo nome
		if (CadastroFacade.verificaFormaIncorporacaoDuplicado(descricaoNovaFormaIncorporacao, Integer.valueOf(0))){			
			setActionForward(mapping.findForward("pgEditFormaIncorporacao"));
			throw new ApplicationException("AVISO.18",  new String[]{descricaoNovaFormaIncorporacao}, ApplicationException.ICON_AVISO);
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			setActionForward(mapping.findForward("pgEditFormaIncorporacao"));
			throw new ApplicationException("AVISO.200");
		}	
		
		try	{			
			FormaIncorporacao formaIncorporacao = new FormaIncorporacao();
			formaIncorporacao.setDescricao(formaIncorporacaoForm.getDescricao());

			CadastroFacade.salvarFormaIncorporacao(formaIncorporacao);
			formaIncorporacaoForm.setDescricao("");
			addMessage("SUCESSO.25", request);	
			this.carregarListaFormaIncorporacao(form, request);
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditFormaIncorporacao"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditFormaIncorporacao"));
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Forma de Incorporação"}, e);
		}
		
		return mapping.findForward("pgListFormaIncorporacao");
	}	
	
	/**
	 * Realiza carga da página de listagem de forma de incorporação, fazendo pesquisa e trazendo todos os registros da tabela (termo da pesquisa é vazio).
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void carregarListaFormaIncorporacao(ActionForm form, HttpServletRequest request ) throws ApplicationException {
	
		FormaIncorporacaoForm formaIncorporacaoForm = (FormaIncorporacaoForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = formaIncorporacaoForm.getDescricao() ==null ? "": StringUtil.tiraAcento(formaIncorporacaoForm.getDescricao().trim()) ;
		
		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));

		pagina = CadastroFacade.listarFormaIncorporacao(pagina, descricao);
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
		if (pagina.getTotalRegistros()==0){
			throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
		}	
	
    }
	
	/**
	 * Realiza o encaminhamento necessário para atualizar os dados da forma de incorporaçao.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterarFormaIncorporacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		FormaIncorporacaoForm formaIncorporacaoForm = (FormaIncorporacaoForm) form;	
		String descricaoNovaFormaIncorporacao = formaIncorporacaoForm.getDescricao().trim();
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditFormaIncorporacao");
		}

		// Valida se descrição não vem vazia
		if ( descricaoNovaFormaIncorporacao.length() == 0  ) {
			throw new ApplicationException("AVISO.201",ApplicationException.ICON_AVISO);
		}
		
		// Verifica se já existe descrição com mesmo nome
		if (CadastroFacade.verificaFormaIncorporacaoDuplicado(descricaoNovaFormaIncorporacao, Integer.valueOf(formaIncorporacaoForm.getCodFormaIncorporacao()))){			
			setActionForward(mapping.findForward("pgEditFormaIncorporacao"));
			throw new ApplicationException("AVISO.18",  new String[]{descricaoNovaFormaIncorporacao}, ApplicationException.ICON_AVISO);
		}
		
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			throw new ApplicationException("AVISO.200");
		}
		
		try	{
			FormaIncorporacao formaIncorporacao = CadastroFacade.obterFormaIncorporacao(Integer.valueOf(formaIncorporacaoForm.getCodFormaIncorporacao()));

			formaIncorporacao.setDescricao(formaIncorporacaoForm.getDescricao());
			CadastroFacade.alterarFormaIncorporacao(formaIncorporacao);
			formaIncorporacaoForm.setDescricao("");
			addMessage("SUCESSO.25", request);
			this.carregarListaFormaIncorporacao(form, request);
			return mapping.findForward("pgListFormaIncorporacao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditFormaIncorporacao"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditFormaIncorporacao"));
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Forma de Incorporação"}, e);
		}		
	}
	
	/**
	 * Realiza o encaminhamento para exclusão de forma de incorporacao.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward excluirFormaIncorporacao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		FormaIncorporacaoForm formaIncorporacaoForm = (FormaIncorporacaoForm) form;
			
		try {
			if (formaIncorporacaoForm.getCodFormaIncorporacao() != null) {
				
				CadastroFacade.excluirFormaIncorporacao(Integer.parseInt(formaIncorporacaoForm.getCodFormaIncorporacao()));
				formaIncorporacaoForm.setDescricao("");
				addMessage("SUCESSO.28", request);
			}
			
			
			this.carregarListaFormaIncorporacao(form, request);
			return mapping.findForward("pgListFormaIncorporacao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListFormaIncorporacao"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListFormaIncorporacao"));
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Forma de Incorporação"}, e);
		}
	}

}
