package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.DenominacaoImovelForm;
import gov.pr.celepar.abi.pojo.DenominacaoImovel;
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
 * Respons�vel por manipular as requisi��es dos usu�rios
 */
public class DenominacaoImovelAction extends BaseDispatchAction {

	/**
	 * Realiza carga da p�gina de listagem de denominacao de im�vel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward carregarPgListDenominacaoImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return mapping.findForward("pgListDenominacaoImovel");

    }


	/**
	 * Realiza carga da p�gina de listagem de denominacao de im�vel, fazendo pesquisa e trazendo todos os registros da tabela (termo da pesquisa � vazio).
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void carregarListaDenominacaoImovel(ActionForm form, HttpServletRequest request ) throws ApplicationException {
	
		DenominacaoImovelForm denominacaoImovelForm = (DenominacaoImovelForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = denominacaoImovelForm.getDescricao() ==null ? "": StringUtil.tiraAcento(denominacaoImovelForm.getDescricao().trim()) ;
		
		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));

		pagina = CadastroFacade.listarDenominacaoImovel(pagina, descricao);
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
		if (pagina.getTotalRegistros()==0){
			throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
		}	
	
    }	

	
	/**
	 * Carrega pagina para altera��o com os dados da denomina��o de im�vel selecionada.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditDenominacaoImovel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		

		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		DenominacaoImovelForm denominacaoImovelForm = (DenominacaoImovelForm) form;
	
		try {
			if (denominacaoImovelForm.getCodDenominacaoImovel() != null) {
				
				DenominacaoImovel denominacaoImovel = CadastroFacade.obterDenominacaoImovel(Integer.valueOf(denominacaoImovelForm.getCodDenominacaoImovel()));
				denominacaoImovelForm.setDescricao(denominacaoImovel.getDescricao());
				
			}
			return mapping.findForward("pgEditDenominacaoImovel");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListDenominacaoImovel"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListDenominacaoImovel"));
			throw new ApplicationException("ERRO.200", new String[]{"edi��o","denomina��o de im�vel"}, e);
		}		
	}	

	
	/**
	 * Realiza o encaminhamento necess�rio para executar a listagem paginada de denominacaoImovels. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward pesquisarDenominacaoImovel (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		DenominacaoImovelForm denominacaoImovelForm = (DenominacaoImovelForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = denominacaoImovelForm.getDescricao() ==null ? "": StringUtil.tiraAcento(denominacaoImovelForm.getDescricao().trim());

		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));

		try {
			pagina = CadastroFacade.listarDenominacaoImovel(pagina, descricao);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.setAttribute("pagina", pagina);
			if(pagina.getTotalRegistros() == 0){
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			
			request.setAttribute("pagina", pagina);			
			return mapping.findForward("pgListDenominacaoImovel");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListDenominacaoImovel"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListDenominacaoImovel"));
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa","denomina��o de im�vel"}, e);
		}
	}

	
	/**
	 * Realiza o encaminhamento necess�rio para salvar o denomina��o de im�vel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvarDenominacaoImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		DenominacaoImovelForm denominacaoImovelForm = (DenominacaoImovelForm) form;
		String descricaoNovaDemoninacao = denominacaoImovelForm.getDescricao().trim();
		
		// Aciona a valida��o do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditDenominacaoImovel");
		}		
		
		// Verifica se j� existe descri��o com mesmo nome
		if (CadastroFacade.verificaDenominacaoImovelDuplicado(descricaoNovaDemoninacao)){			
			setActionForward(mapping.findForward("pgEditDenominacaoImovel"));
			throw new ApplicationException("AVISO.14",  new String[]{descricaoNovaDemoninacao}, ApplicationException.ICON_AVISO);
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			setActionForward(mapping.findForward("pgEditDenominacaoImovel"));
			throw new ApplicationException("AVISO.200");
		}	
		
		try	{			
			DenominacaoImovel denominacaoImovel = new DenominacaoImovel();
			denominacaoImovel.setDescricao(denominacaoImovelForm.getDescricao());

			CadastroFacade.salvarDenominacaoImovel(denominacaoImovel);
			denominacaoImovelForm.setDescricao("");
			addMessage("SUCESSO.25", request);	
			this.carregarListaDenominacaoImovel(form, request);
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditDenominacaoImovel"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditDenominacaoImovel"));
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Denomina��o do Im�vel"}, e);
		}
		
		return mapping.findForward("pgListDenominacaoImovel");
	}	
	
	/**
	 * Realiza o encaminhamento necess�rio para atualizar os dados do denominacaoImovel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterarDenominacaoImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		DenominacaoImovelForm denominacaoImovelForm = (DenominacaoImovelForm) form;	
		String descricaoNovaDemoninacao = denominacaoImovelForm.getDescricao().trim();
		
		// Aciona a valida��o do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditDenominacaoImovel");
		}

		// Valida se descri��o n�o vem vazia
		if ( descricaoNovaDemoninacao.length() == 0  ) {
			throw new ApplicationException("AVISO.201",ApplicationException.ICON_AVISO);
		}
		
		// Verifica se j� existe descri��o com mesmo nome
		if (CadastroFacade.verificaDenominacaoImovelDuplicado(descricaoNovaDemoninacao)){			
			setActionForward(mapping.findForward("pgEditDenominacaoImovel"));
			throw new ApplicationException("AVISO.14",  new String[]{descricaoNovaDemoninacao}, ApplicationException.ICON_AVISO);
		}
		
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			setActionForward(mapping.findForward("pgEditDenominacaoImovel"));
			throw new ApplicationException("AVISO.200");
		}
		
		try	{
			DenominacaoImovel denominacaoImovel = CadastroFacade.obterDenominacaoImovel(Integer.valueOf(denominacaoImovelForm.getCodDenominacaoImovel()));

			denominacaoImovel.setDescricao(denominacaoImovelForm.getDescricao());
			CadastroFacade.alterarDenominacaoImovel(denominacaoImovel);
			denominacaoImovelForm.setDescricao("");
			addMessage("SUCESSO.25", request);
			this.carregarListaDenominacaoImovel(form, request);
			return mapping.findForward("pgListDenominacaoImovel");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditDenominacaoImovel"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditDenominacaoImovel"));
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Denomina��o do Im�vel"}, e);
		}		
	}
	
	/**
	 * Realiza o encaminhamento para exclus�o de aluno.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward excluirDenominacaoImovel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		DenominacaoImovelForm denominacaoImovelForm = (DenominacaoImovelForm) form;
			
		try {
			if (denominacaoImovelForm.getCodDenominacaoImovel() != null) {
				
				CadastroFacade.excluirDenominacaoImovel(Integer.parseInt(denominacaoImovelForm.getCodDenominacaoImovel()));
				denominacaoImovelForm.setDescricao("");
				addMessage("SUCESSO.27", request);
			}
			
			
			this.carregarListaDenominacaoImovel(form, request);
			return mapping.findForward("pgListDenominacaoImovel");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListDenominacaoImovel"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListDenominacaoImovel"));
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Denomina��o do Im�vel"}, e);
		}
	}
		
}