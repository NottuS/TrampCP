package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.enumeration.TipoAdministracao;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.OrgaoForm;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.Orgao;
import gov.pr.celepar.abi.pojo.Usuario;
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
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 02/02/2010
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class OrgaoAction extends BaseDispatchAction {
	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de orgaos. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward pesquisarOrgao (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		OrgaoForm orgaoForm = (OrgaoForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = orgaoForm.getDescricao() ==null ? "" : StringUtil.tiraAcento(orgaoForm.getDescricao().trim());
		String sigla = orgaoForm.getSigla() ==null ? "" : StringUtil.tiraAcento(orgaoForm.getSigla().trim());
		Integer codInstituicao = ((orgaoForm.getConInstituicao() == null || orgaoForm.getConInstituicao().isEmpty()) ? null : Integer.valueOf(orgaoForm.getConInstituicao()));
		
		Pagina pagina = new Pagina( Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina), Integer.parseInt(totalRegistros));

		try {
			orgaoForm.setIndGrupoSentinela(request.getSession().getAttribute("indGrupoSentinela").toString());
			orgaoForm.setAdm(GrupoSentinela.ADM_GERAL.getDescricao());
			
			//lista instituicao
			if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
				request.setAttribute("listaInstituicao", Util.htmlEncodeCollection(CadastroFacade.listarInstituicao()));	
			}else{
				//obtem a instituicao do usuario logado
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
				if (usuario == null){
					throw new ApplicationException("AVISO.97");
				}
				codInstituicao = usuario.getInstituicao().getCodInstituicao();
			}
			
			pagina = CadastroFacade.listarOrgao(pagina, descricao, sigla, codInstituicao);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.setAttribute("pagina", pagina);
			if(pagina.getTotalRegistros() == 0) {
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			return mapping.findForward("pgListOrgao");
		}
		catch(ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListOrgao"));
			throw appEx;
		}
		catch(Exception e) {
			setActionForward(mapping.findForward("pgListOrgao"));
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa","órgão"}, e);
		}
	}
	
	/**
	 * Carrega pagina para alteração com os dados do orgao selecionado.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditOrgao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		OrgaoForm orgaoForm = (OrgaoForm) form;
	
		try {
			orgaoForm.setIndGrupoSentinela(request.getSession().getAttribute("indGrupoSentinela").toString());
			orgaoForm.setAdm(GrupoSentinela.ADM_GERAL.getDescricao());
			if (orgaoForm.getActionType().equals("alterar") && orgaoForm.getCodOrgao()!= null) {
				
				Orgao orgao = CadastroFacade.obterOrgao(Integer.parseInt(orgaoForm.getCodOrgao()));
				orgaoForm.setSigla(orgao.getSigla());
				orgaoForm.setDescricao(orgao.getDescricao());
				orgaoForm.setConInstituicao(orgao.getInstituicao().getCodInstituicao().toString());
				orgaoForm.setInstituicao(orgao.getInstituicao().getSiglaDescricao());
				if (orgao.getIndTipoAdministracao() != null){
					orgaoForm.setIndTipoAdministracao(orgao.getIndTipoAdministracao().toString());	
				}
			}else{//inclusão
				//lista instituicao
				if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
					request.setAttribute("listaInstituicao", Util.htmlEncodeCollection(CadastroFacade.listarInstituicao()));	
				}
				orgaoForm.setIndTipoAdministracao(TipoAdministracao.ADM_DIRETA.getCodigo().toString());
			}
			return mapping.findForward("pgEditOrgao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListOrgao"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListOrgao"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","órgão"}, e);
		}		
	}
	
	/**
	 * Realiza carga da página de listagem de órgãos.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgListOrgao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return mapping.findForward("pgListOrgao");
    }
	
	/**
	 * Realiza o encaminhamento necessário para salvar o orgao.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvarOrgao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try	{	
		OrgaoForm orgaoForm = (OrgaoForm) form;
		setActionForward(mapping.findForward("pgEditOrgao"));
		String descricaoNovoOrgao = orgaoForm.getDescricao().trim();
		String siglaNovoOrgao = orgaoForm.getSigla().trim();
	
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return getActionForward();
		}		
		
		Integer codInstituicao = null;
		if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
			codInstituicao = Integer.valueOf(orgaoForm.getConInstituicao());
		}else{
			//obtem a instituicao do usuario logado
			Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
			if (usuario == null){
				throw new ApplicationException("AVISO.97");
			}
			codInstituicao = usuario.getInstituicao().getCodInstituicao();
		}
		
		// Verifica se já existe orgão com mesma sigla,o codOrgao vai zerado para indicar que é uma inclusao
		if (CadastroFacade.verificaOrgaoDuplicado(null, siglaNovoOrgao, null, codInstituicao)){		
			throw new ApplicationException("AVISO.20",  new String[]{siglaNovoOrgao}, ApplicationException.ICON_AVISO);
		}
		// Verifica se já existe orgão com mesma descricao,o codOrgao vai zerado para indicar que é uma inclusao
		if (CadastroFacade.verificaOrgaoDuplicado(descricaoNovoOrgao, null, null, codInstituicao)){			
			throw new ApplicationException("AVISO.20",  new String[]{descricaoNovoOrgao}, ApplicationException.ICON_AVISO);
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			throw new ApplicationException("AVISO.200");
		}	
		
				
			Orgao orgao = new Orgao();
			orgao.setSigla(orgaoForm.getSigla());
			orgao.setDescricao(orgaoForm.getDescricao());
			Instituicao instituicao = new Instituicao();
			instituicao.setCodInstituicao(codInstituicao);
			orgao.setInstituicao(instituicao);
			if (orgaoForm.getIndTipoAdministracao() != null){
				orgao.setIndTipoAdministracao(Integer.valueOf(orgaoForm.getIndTipoAdministracao()));
			}
			CadastroFacade.salvarOrgao(orgao);
			orgaoForm.setSigla("");
			orgaoForm.setDescricao("");
			addMessage("SUCESSO.25", request);	
			this.carregarListaOrgao(form, request);
			
		} catch (ApplicationException appEx) {
			carregarPgEditOrgao(mapping, form, request, response);
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditOrgao"));
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Órgão"}, e);
		}
		
		return mapping.findForward("pgListOrgao");
	}	
	
	/**
	 * Realiza carga da página de listagem de órgãos, fazendo pesquisa e trazendo todos os registros da tabela (termo da pesquisa é vazio).
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void carregarListaOrgao(ActionForm form, HttpServletRequest request) throws ApplicationException {
		OrgaoForm orgaoForm = (OrgaoForm) form;	
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		Integer codInstituicao = null;
		if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
			request.setAttribute("listaInstituicao", Util.htmlEncodeCollection(CadastroFacade.listarInstituicao()));	
		}else{
			//obtem a instituicao do usuario logado
			Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
			if (usuario == null){
				throw new ApplicationException("AVISO.97");
			}
			codInstituicao = usuario.getInstituicao().getCodInstituicao();
			orgaoForm.setConInstituicao(codInstituicao.toString());
		}
		
		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina), Integer.parseInt(totalRegistros));
		//TODO verificar parametro instituicao
		if (orgaoForm.getConInstituicao() != null && !orgaoForm.getConInstituicao().isEmpty()){
			pagina = CadastroFacade.listarOrgao(pagina, "", "", Integer.valueOf(orgaoForm.getConInstituicao()));	
		}else{
			pagina = CadastroFacade.listarOrgao(pagina, "", "", null);
		}
		

		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
		if(pagina.getTotalRegistros() == 0) {
			throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
		}
    }
	
	/**
	 * Realiza o encaminhamento necessário para atualizar os dados do orgão.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterarOrgao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		try	{
			
		OrgaoForm orgaoForm = (OrgaoForm) form;	
		setActionForward(mapping.findForward("pgEditOrgao"));
		String descricaoNovoOrgao = orgaoForm.getDescricao().trim();
		String siglaNovoOrgao = orgaoForm.getSigla().trim();
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return getActionForward();
		}		
		
		Orgao orgao = CadastroFacade.obterOrgao(Integer.valueOf(orgaoForm.getCodOrgao()));
		
		// Verifica se já existe orgão com mesma sigla,o codOrgao vai zerado para indicar que é uma inclusao
		if (CadastroFacade.verificaOrgaoDuplicado(null, siglaNovoOrgao, orgao.getCodOrgao(), orgao.getInstituicao().getCodInstituicao())){		
			throw new ApplicationException("AVISO.20",  new String[]{siglaNovoOrgao}, ApplicationException.ICON_AVISO);
		}
		// Verifica se já existe orgão com mesma descricao,o codOrgao vai zerado para indicar que é uma inclusao
		if (CadastroFacade.verificaOrgaoDuplicado(descricaoNovoOrgao, null, orgao.getCodOrgao(), orgao.getInstituicao().getCodInstituicao())){			
			throw new ApplicationException("AVISO.20",  new String[]{descricaoNovoOrgao}, ApplicationException.ICON_AVISO);
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			throw new ApplicationException("AVISO.200");
		}
		
			
			orgao.setSigla(orgaoForm.getSigla());
			orgao.setDescricao(orgaoForm.getDescricao());
			if (orgaoForm.getIndTipoAdministracao() != null){
				orgao.setIndTipoAdministracao(Integer.valueOf(orgaoForm.getIndTipoAdministracao()));
			}
			CadastroFacade.alterarOrgao(orgao);
			orgaoForm.setSigla("");
			orgaoForm.setDescricao("");
			addMessage("SUCESSO.25", request);
			this.carregarListaOrgao(form, request);
			return mapping.findForward("pgListOrgao");
			
		} catch (ApplicationException appEx) {
			carregarPgEditOrgao(mapping, form, request, response);
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Órgão"}, e);
		}		
	}
	
	/**
	 * Realiza o encaminhamento para exclusão de orgão.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward excluirOrgao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		OrgaoForm orgaoForm = (OrgaoForm) form;	
			
		try {
			if (orgaoForm.getCodOrgao()!= null) {
				
				CadastroFacade.excluirOrgao(Integer.parseInt(orgaoForm.getCodOrgao()));
				orgaoForm.setSigla("");
				orgaoForm.setDescricao("");
				orgaoForm.setIndTipoAdministracao("");
				addMessage("SUCESSO.29", request);
			}
			
			
			this.carregarListaOrgao(form, request);
			return pesquisarOrgao(mapping, orgaoForm, request, response);
			
		} catch (ApplicationException appEx) {
			this.carregarListaOrgao(form, request);
			setActionForward(mapping.findForward("pgListOrgao"));
			throw appEx;
		} catch (Exception e) {
			this.carregarListaOrgao(form, request);
			setActionForward(mapping.findForward("pgListOrgao"));
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Órgão"}, e);
		}
	}

}
