package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.ClassificacaoBemImovelForm;
import gov.pr.celepar.abi.pojo.ClassificacaoBemImovel;
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
 * @author claudiofain
 * @version 1.0
 * @since 06/01/2010
 *
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */
public class ClassificacaoBemImovelAction extends BaseDispatchAction {

	/**
	 * Realiza carga da página de listagem de classificação de bem imóvel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward carregarPgListClassificacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return mapping.findForward("pgListClassificacaoBemImovel");
	}


	/**
	 * Realiza carga da página de listagem de classificação de bem imóvel, fazendo pesquisa e trazendo todos os registros da tabela (termo da pesquisa é vazio).
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void carregarListaClassificacaoBemImovel(ActionForm form, HttpServletRequest request) throws ApplicationException {

		ClassificacaoBemImovelForm classificacaoBemImovelForm = (ClassificacaoBemImovelForm) form;

		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = classificacaoBemImovelForm.getDescricao() == null ? "" : StringUtil.tiraAcento(classificacaoBemImovelForm.getDescricao().trim());

		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));

		pagina = CadastroFacade.listarClassificacaoBemImovel(pagina, descricao);
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
		if(pagina.getTotalRegistros() == 0) {
			throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
		}

	}


	/**
	 * Carrega pagina para alteração com os dados da classificação de bem imóvel selecionada.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */

	public ActionForward carregarPgEditClassificacaoBemImovel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		// Salva o TOKEN para evitar duplo submit
		saveToken(request);

		ClassificacaoBemImovelForm classificacaoBemImovelForm = (ClassificacaoBemImovelForm)form;

		try {
			if(classificacaoBemImovelForm.getCodClassificacaoBemImovel() != null) {

				ClassificacaoBemImovel classificacaoBemImovel = CadastroFacade.obterClassificacaoBemImovel(Integer.valueOf(classificacaoBemImovelForm.getCodClassificacaoBemImovel()));
				classificacaoBemImovelForm.setDescricao(classificacaoBemImovel.getDescricao());

			}
			return mapping.findForward("pgEditClassificacaoBemImovel");

		}
		catch(ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListClassificacaoBemImovel"));
			throw appEx;

		}
		catch(Exception e) {
			setActionForward(mapping.findForward("pgListClassificacaoBemImovel"));
			throw new ApplicationException("ERRO.200", new String[]{"edição", "classificação de bem imóvel"}, e);
		}
	}


	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de classificacaoBemImovels.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */

	public ActionForward pesquisarClassificacaoBemImovel (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		ClassificacaoBemImovelForm classificacaoBemImovelForm = (ClassificacaoBemImovelForm)form;

		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = classificacaoBemImovelForm.getDescricao() == null ? "" : StringUtil.tiraAcento(classificacaoBemImovelForm.getDescricao().trim());

		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));

		try {
			pagina = CadastroFacade.listarClassificacaoBemImovel(pagina, descricao);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.setAttribute("pagina", pagina);
			if(pagina.getTotalRegistros() == 0) {
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			return mapping.findForward("pgListClassificacaoBemImovel");
		}
		catch(ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListClassificacaoBemImovel"));
			throw appEx;
		}
		catch(Exception e) {
			setActionForward(mapping.findForward("pgListClassificacaoBemImovel"));
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa", "classificação de bem imóvel"}, e);
		}
	}


	/**
	 * Realiza o encaminhamento necessário para salvar a classificação de bem imóvel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */

	public ActionForward salvarClassificacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		ClassificacaoBemImovelForm classificacaoBemImovelForm = (ClassificacaoBemImovelForm)form;
		String descricaoNovaClassificacao = classificacaoBemImovelForm.getDescricao().trim();

		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditClassificacaoBemImovel");
		}

		// Verifica se já existe descrição com mesmo nome
		if(CadastroFacade.verificaClassificacaoBemImovelDuplicado(descricaoNovaClassificacao)) {
			setActionForward(mapping.findForward("pgEditClassificacaoBemImovel"));
			throw new ApplicationException("AVISO.14",  new String[]{descricaoNovaClassificacao}, ApplicationException.ICON_AVISO);
		}

		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		}
		else {
			setActionForward(mapping.findForward("pgEditClassificacaoBemImovel"));
			throw new ApplicationException("AVISO.200");
		}

		try	{
			ClassificacaoBemImovel classificacaoBemImovel = new ClassificacaoBemImovel();
			classificacaoBemImovel.setDescricao(classificacaoBemImovelForm.getDescricao());

			CadastroFacade.salvarClassificacaoBemImovel(classificacaoBemImovel);
			classificacaoBemImovelForm.setDescricao("");
			addMessage("SUCESSO.25", request);
			this.carregarListaClassificacaoBemImovel(form, request);

		}
		catch(ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditClassificacaoBemImovel"));
			throw appEx;
		}
		catch(Exception e) {
			setActionForward(mapping.findForward("pgEditClassificacaoBemImovel"));
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Classificação do Bem Imóvel"}, e);
		}

		return mapping.findForward("pgListClassificacaoBemImovel");
	}

	/**
	 * Realiza o encaminhamento necessário para atualizar os dados do classificacaoBemImovel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward alterarClassificacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		ClassificacaoBemImovelForm classificacaoBemImovelForm = (ClassificacaoBemImovelForm)form;
		String descricaoNovaClassificacao = classificacaoBemImovelForm.getDescricao().trim();

		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditClassificacaoBemImovel");
		}

		// Valida se descrição não vem vazia
		if(descricaoNovaClassificacao.length() == 0) {
			throw new ApplicationException("AVISO.201",ApplicationException.ICON_AVISO);
		}

		// Verifica se já existe descrição com mesmo nome
		if(CadastroFacade.verificaClassificacaoBemImovelDuplicado(descricaoNovaClassificacao)) {
			setActionForward(mapping.findForward("pgEditClassificacaoBemImovel"));
			throw new ApplicationException("AVISO.14", new String[]{descricaoNovaClassificacao}, ApplicationException.ICON_AVISO);
		}


		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		}
		else {
			setActionForward(mapping.findForward("pgEditClassificacaoBemImovel"));
			throw new ApplicationException("AVISO.200");
		}

		try	{
			ClassificacaoBemImovel classificacaoBemImovel = CadastroFacade.obterClassificacaoBemImovel(Integer.valueOf(classificacaoBemImovelForm.getCodClassificacaoBemImovel()));

			classificacaoBemImovel.setDescricao(classificacaoBemImovelForm.getDescricao());
			CadastroFacade.alterarClassificacaoBemImovel(classificacaoBemImovel);
			classificacaoBemImovelForm.setDescricao("");
			addMessage("SUCESSO.25", request);
			this.carregarListaClassificacaoBemImovel(form, request);
			return mapping.findForward("pgListClassificacaoBemImovel");

		}
		catch(ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditClassificacaoBemImovel"));
			throw appEx;
		}
		catch(Exception e) {
			setActionForward(mapping.findForward("pgEditClassificacaoBemImovel"));
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Classificação do Bem Imóvel"}, e);
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

	public ActionForward excluirClassificacaoBemImovel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		ClassificacaoBemImovelForm classificacaoBemImovelForm = (ClassificacaoBemImovelForm)form;

		try {
			if(classificacaoBemImovelForm.getCodClassificacaoBemImovel() != null) {

				CadastroFacade.excluirClassificacaoBemImovel(Integer.parseInt(classificacaoBemImovelForm.getCodClassificacaoBemImovel()));
				classificacaoBemImovelForm.setDescricao("");
				addMessage("SUCESSO.26", request);
			}


			this.carregarListaClassificacaoBemImovel(form, request);
			return mapping.findForward("pgListClassificacaoBemImovel");

		}
		catch(ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListClassificacaoBemImovel"));
			throw appEx;
		}
		catch(Exception e) {
			setActionForward(mapping.findForward("pgListClassificacaoBemImovel"));
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Classificação do Bem Imóvel"}, e);
		}
	}
}
