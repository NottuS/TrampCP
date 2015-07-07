package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.CartorioForm;
import gov.pr.celepar.abi.pojo.Cartorio;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Enderecamento;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
public class CartorioAction extends BaseDispatchAction {
	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de cartorios. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward pesquisarCartorio (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		try {
			CartorioForm cartorioForm = (CartorioForm) form;
			setActionForward(mapping.findForward("pgListCartorio"));
			String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
			String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
				
			Cartorio cartorio = new Cartorio();
			if (StringUtil.stringNotNull(cartorioForm.getConDescricao())){
				cartorio.setDescricao("%"+cartorioForm.getConDescricao());
			}
			if (StringUtil.stringNotNull(cartorioForm.getConUf()) && ! cartorioForm.getConUf().equals("0")){
				cartorio.setUf(cartorioForm.getConUf());
				cartorioForm.setUf(cartorioForm.getConUf());
			}
			if (StringUtil.stringNotNull(cartorioForm.getConCodMunicipio()) && !cartorioForm.getConCodMunicipio().equals("0") ){
				cartorio.setCodMunicipio(Integer.parseInt(cartorioForm.getConCodMunicipio()));
				cartorioForm.setCodMunicipio(cartorioForm.getConCodMunicipio());
			}
		
			
			
			
			Pagina pagina = new Pagina( Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina), Integer.parseInt(totalRegistros));
			pagina = CadastroFacade.listarCartorio(pagina, cartorio);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.setAttribute("pagina", pagina);
			if (pagina.getTotalRegistros()==0){
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			
			
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			throw appEx;
			
		} catch (Exception e) {
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa","cartório"}, e);
		}
	}
	
	/**
	 * Carrega pagina para alteração/inclusão do cartorio.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditCartorio(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgListCartorio"));
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		CartorioForm cartorioForm = (CartorioForm)form;	
		try {
			
			if (cartorioForm.getActionType().equals("alterar") && cartorioForm.getCodCartorio()!= null) {
				
				Cartorio cartorio = CadastroFacade.obterCartorio(Integer.parseInt(cartorioForm.getCodCartorio()));
				cartorioForm.setActionType("alterar");
				cartorioForm.setBairro(cartorio.getBairro());
				cartorioForm.setCep(cartorio.getCep());
				cartorioForm.setCodCartorio(cartorio.getCodCartorio().toString());
				cartorioForm.setCodMunicipio(cartorio.getCodMunicipio().toString());
				cartorioForm.setComplemento(cartorio.getComplemento());
				cartorioForm.setDescricao(cartorio.getDescricao());
				cartorioForm.setLogradouro(cartorio.getLogradouro());
				cartorioForm.setNumero(cartorio.getNumero());
				cartorioForm.setUf(cartorio.getUf());
				cartorioForm.setNomeContato(cartorio.getNomeContato());
				cartorioForm.setTelDdd(cartorio.getTelDdd() != null ? cartorio.getTelDdd().toString() : null);
				cartorioForm.setTelNumero(cartorio.getTelNumero() != null ? cartorio.getTelNumero().trim() : null);
			}
			setActionForward(mapping.findForward("pgEditCartorio"));
			
		} catch (ApplicationException appEx) {			
			throw appEx;
			
		} catch (Exception e) {
			throw new ApplicationException("ERRO.200", new String[]{"edição","cartório"}, e);
		}		
		return getActionForward();
	}
	
	/**
	 * Realiza carga da página de listagem de cartórios.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgListCartorio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return mapping.findForward("pgListCartorio");

    }
	
	/**
	 * Realiza carga da página de visualização de cartórios.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgViewCartorio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		if (request.getParameter("codCartorio") != null){
			Cartorio cartorio =  CadastroFacade.obterCartorio(Integer.parseInt(request.getParameter("codCartorio") ));
			cartorio.setCep(Enderecamento.formataCEP(cartorio.getCep()));
			request.setAttribute("cartorio", cartorio);
		}
		
		return mapping.findForward("pgViewCartorio");

    }
	
	/**
	 * Realiza o encaminhamento necessário para salvar o cartorio.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvarCartorio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		CartorioForm cartorioForm = (CartorioForm) form;
		setActionForward(mapping.findForward("pgEditCartorio"));
		try	{
			// Aciona a validação do Form
			if(!validaForm(mapping,form,request)) {
				return getActionForward();
			}		
			if(StringUtils.isBlank(cartorioForm.getTelDdd()) != StringUtils.isBlank(cartorioForm.getTelNumero())) {			
				throw new ApplicationException("errors.invalid",  new String[]{"Telefone"}, ApplicationException.ICON_AVISO);
			}
			// Verifica se ja existe cartorio com a mesma descricao na mesma cidade
			if(CadastroFacade.verificarCartorioDuplicado(cartorioForm.getDescricao(), cartorioForm.getCodMunicipio(), Integer.parseInt("0"))){			
				throw new ApplicationException("AVISO.39",  new String[]{cartorioForm.getDescricao()}, ApplicationException.ICON_AVISO);
			}
	
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}
			
			Cartorio cartorio = new Cartorio();
			cartorio.setBairro(cartorioForm.getBairro());
			cartorio.setCep(cartorioForm.getCep());
			cartorio.setCodMunicipio(Integer.parseInt(cartorioForm.getCodMunicipio()));
			cartorio.setComplemento(cartorioForm.getComplemento());
			cartorio.setDescricao(cartorioForm.getDescricao());
			cartorio.setLogradouro(cartorioForm.getLogradouro());
			cartorio.setMunicipio(cartorioForm.getMunicipio());
			cartorio.setNumero(cartorioForm.getNumero());
			cartorio.setUf(cartorioForm.getUf());
			cartorio.setNomeContato(cartorioForm.getNomeContato());
			cartorio.setTelDdd(!StringUtils.isBlank(cartorioForm.getTelDdd()) ? Integer.valueOf(cartorioForm.getTelDdd()) : null);
			cartorio.setTelNumero(cartorioForm.getTelNumero());

			CadastroFacade.salvarCartorio(cartorio);
			
			cartorioForm.setBairro("");
			cartorioForm.setCep("");
			cartorioForm.setUf("PR");
			cartorioForm.setCodMunicipio("0");
			cartorioForm.setComplemento("");
			cartorioForm.setDescricao("");
			cartorioForm.setLogradouro("");
			cartorioForm.setNumero("");
			cartorioForm.setNomeContato("");
			cartorioForm.setTelDdd("");
			cartorioForm.setTelNumero("");
			
			addMessage("SUCESSO.25", request);	
			this.carregarListaCartorio(form, request);
			
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Cartório"}, e);
		}
		
		return mapping.findForward("pgListCartorio");
	}	
	
	/**
	 * Realiza carga da página de listagem de cartorios fazendo pesquisa e trazendo todos os registros da tabela (termo da pesquisa é vazio).
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void carregarListaCartorio(ActionForm form, HttpServletRequest request) throws ApplicationException {
	
		CartorioForm cartorioForm = (CartorioForm) form;
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		
		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina), Integer.parseInt(totalRegistros));

		
		Cartorio cartorio = new Cartorio();
		if (StringUtil.stringNotNull(cartorioForm.getConDescricao())){
			cartorio.setDescricao("%"+cartorioForm.getConDescricao());
		}
		if (StringUtil.stringNotNull(cartorioForm.getConUf()) && ! cartorioForm.getConUf().equals("0")){
			cartorio.setUf(cartorioForm.getConUf());
			cartorioForm.setUf(cartorioForm.getConUf());
		}
		if (StringUtil.stringNotNull(cartorioForm.getConCodMunicipio()) && !cartorioForm.getConCodMunicipio().equals("0") ){
			cartorio.setCodMunicipio(Integer.parseInt(cartorioForm.getConCodMunicipio()));
			cartorioForm.setCodMunicipio(cartorioForm.getConCodMunicipio());
		}
			
		
		pagina = CadastroFacade.listarCartorio(pagina, cartorio);
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
		if(pagina.getTotalRegistros() == 0) {
			throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
		}	
	
    }
	
	/**
	 * Realiza o encaminhamento necessário para atualizar os dados do cartorio.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterarCartorio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		CartorioForm cartorioForm = (CartorioForm) form;
		setActionForward(mapping.findForward("pgEditCartorio"));
		try	{
			// Aciona a validação do Form
			if(!validaForm(mapping,form,request)) {
				return getActionForward();
			}		
			
			// Verifica se ja existe cartorio com a mesma descricao na mesma cidade
			if (CadastroFacade.verificarCartorioDuplicado(cartorioForm.getDescricao(), cartorioForm.getCodMunicipio(), Integer.parseInt(cartorioForm.getCodCartorio()))){			
				throw new ApplicationException("AVISO.39",  new String[]{cartorioForm.getDescricao()}, ApplicationException.ICON_AVISO);
			}
	
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}
			
			Cartorio cartorio = CadastroFacade.obterCartorio(Integer.parseInt(cartorioForm.getCodCartorio()));
			cartorio.setBairro(cartorioForm.getBairro());
			cartorio.setCep(cartorioForm.getCep());
			cartorio.setCodMunicipio(Integer.parseInt(cartorioForm.getCodMunicipio()));
			cartorio.setComplemento(cartorioForm.getComplemento());
			cartorio.setDescricao(cartorioForm.getDescricao());
			cartorio.setLogradouro(cartorioForm.getLogradouro());
			cartorio.setMunicipio(cartorioForm.getMunicipio());
			cartorio.setNumero(cartorioForm.getNumero());
			cartorio.setUf(cartorioForm.getUf());
			cartorio.setNomeContato(cartorioForm.getNomeContato());
			cartorio.setTelDdd(!StringUtils.isBlank(cartorioForm.getTelDdd()) ? Integer.valueOf(cartorioForm.getTelDdd()) : null);
			cartorio.setTelNumero(cartorioForm.getTelNumero());

			CadastroFacade.alterarCartorio(cartorio);
			
			cartorioForm.setBairro("");
			cartorioForm.setCep("");
			cartorioForm.setUf("PR");
			cartorioForm.setCodMunicipio("0");
			cartorioForm.setComplemento("");
			cartorioForm.setDescricao("");
			cartorioForm.setLogradouro("");
			cartorioForm.setNumero("");
			cartorioForm.setNomeContato("");
			cartorioForm.setTelDdd("");
			cartorioForm.setTelNumero("");
			
			addMessage("SUCESSO.25", request);	
			this.carregarListaCartorio(form, request);
			
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Cartório"}, e);
		}
		
		return mapping.findForward("pgListCartorio");
	}
	
	/**
	 * Realiza o encaminhamento para exclusão de cartorio.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward excluirCartorio(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		CartorioForm cartorioForm = (CartorioForm) form;
		setActionForward(mapping.findForward("pgListCartorio"));	
		try {
			if (cartorioForm.getCodCartorio()!= null) {
				
				CadastroFacade.excluirCartorio(Integer.parseInt(cartorioForm.getCodCartorio()));
				
				cartorioForm.setBairro("");
				cartorioForm.setCep("");
				cartorioForm.setUf("PR");
				cartorioForm.setCodMunicipio("0");
				cartorioForm.setComplemento("");
				cartorioForm.setDescricao("");
				cartorioForm.setLogradouro("");
				cartorioForm.setNumero("");
				cartorioForm.setNomeContato("");
				cartorioForm.setTelDdd("");
				cartorioForm.setTelNumero("");
				
				addMessage("SUCESSO.33", request);
			}
			
			
			this.carregarListaCartorio(form, request);
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			this.carregarListaCartorio(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarListaCartorio(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Cartório"}, e);
		}
	}
}