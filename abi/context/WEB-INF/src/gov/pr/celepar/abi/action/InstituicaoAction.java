package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.InstituicaoForm;
import gov.pr.celepar.abi.pojo.Cartorio;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Enderecamento;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 06/12/2011
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */
public class InstituicaoAction extends BaseDispatchAction {
	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de instituições. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward pesquisar (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		try {
			InstituicaoForm localForm = (InstituicaoForm) form;
			setActionForward(mapping.findForward("pgList"));
			
			this.carregarLista(localForm, request);
			
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
	
	public ActionForward carregarPgEdit(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgList"));
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		InstituicaoForm localForm = (InstituicaoForm)form;	
		try {
			
			if (localForm.getActionType().equals("alterar") && localForm.getCodInstituicao()!= null) {
				Instituicao i= CadastroFacade.obterInstituicao(Integer.valueOf(localForm.getCodInstituicao()));
				localForm.setCodInstituicao(localForm.getCodInstituicao());
				localForm.setBairro(i.getBairroDistrito());
				localForm.setCep(i.getCep());
				localForm.setCodInstituicao(i.getCodInstituicao().toString());
				localForm.setComplemento(i.getComplemento());
				localForm.setContato(i.getContato());
				localForm.setDescricaoRelatorio(i.getDescricaoRelatorio());
				localForm.setEmail(i.getEmail());
				localForm.setLogradouro(i.getLogradouro());
				localForm.setCodMunicipio(i.getCodMunicipio().toString());
				localForm.setNome(i.getNome());
				localForm.setNumero(i.getNumero());
				localForm.setSigla(i.getSigla());
				localForm.setTelDdd(i.getTelefoneDdd());
				if (StringUtil.stringNotNull(i.getTelefoneNumero())){
				localForm.setTelNumero(StringUtil.formatarTelefone(i.getTelefoneNumero()));
				}
				localForm.setUf(i.getUf());
				byte [] bytes = CadastroFacade.obterLogoInstituicao(localForm.getCodInstituicao()+i.getLogoInstituicao());
				request.getSession().setAttribute("imagemSessao", bytes);
				request.getSession().setAttribute("tipoImagem", i.getLogoInstituicao());
				
				
			}
			setActionForward(mapping.findForward("pgEdit"));
			
		} catch (ApplicationException appEx) {			
			throw appEx;
			
		} catch (Exception e) {
			throw new ApplicationException("ERRO.200", new String[]{"edição","instituição"}, e);
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
	 * Realiza o encaminhamento necessário para salvar a instituição.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		InstituicaoForm localForm = (InstituicaoForm) form;
		setActionForward(mapping.findForward("pgEdit"));
		boolean erroToken = false;
		try	{
			
			// Aciona a validação do Form
			if(!validaForm(mapping,form,request)) {
				return getActionForward();
			}		
			if(StringUtils.isBlank(localForm.getTelDdd()) != StringUtils.isBlank(localForm.getTelNumero())) {			
				throw new ApplicationException("errors.invalid",  new String[]{"Telefone"}, ApplicationException.ICON_AVISO);
			}
			
			// Verifica se ja existe instituição com a mesma descricao ou com a mesma silga
			Instituicao i = new Instituicao();
			i.setSigla(localForm.getSigla());
			i.setNome(localForm.getNome());
			if (!CadastroFacade.verificaInstituicaoDuplicado(null, i)){
				//verificar tipo e tamanho do arquivo de logo
//				int tam = localForm.getLogotipo().getFileSize();
//				if (tam> (20*1024)){
//					throw new ApplicationException("AVISO.5", new String[]{"Logotipo da Instituição maior que 20 Kb"}, ApplicationException.ICON_AVISO);
//				}
//				if(!localForm.getLogotipo().getFileName().toUpperCase().contains(".JPG") && !localForm.getLogotipo().getFileName().toUpperCase().contains(".PNG")){
//					throw new ApplicationException("AVISO.5", new String[]{"Logotipo da Instituição deve ser tipo PNG ou JPG"}, ApplicationException.ICON_AVISO);
//				}
				
				// Verifica se o TOKEN existe para evitar duplo submit
				if(isTokenValid(request)) {
					resetToken(request);
				} else {
					erroToken=true;
					throw new ApplicationException("AVISO.200");
				}
				
				populaPojo(request, localForm, i);
				i.setCpfResponsavelCriacao(SentinelaComunicacao.getInstance(request).getCpf());
				i.setTsInclusao(new Date());
				
				CadastroFacade.salvarInstituicao(i, (byte[])request.getSession().getAttribute("imagemSessao"));
				
				localForm.reset(mapping, request);
				addMessage("SUCESSO.25", request);	
				this.carregarLista(localForm, request);
				if(request.getSession().getAttribute("imagemSessao") != null){
					request.getSession().removeAttribute("imagemSessao");
					request.getSession().removeAttribute("tipoImagem");
				}
				
			}
		
		} catch (ApplicationException appEx) {
			if (!erroToken){
				// Salva o TOKEN para evitar duplo submit
				saveToken(request);
			}
			throw appEx;
		} catch (Exception e) {
			if (!erroToken){
				// Salva o TOKEN para evitar duplo submit
				saveToken(request);
			}
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Instituição"}, e);
		}
		
		return mapping.findForward("pgList");
	}
	
	
	

	private void populaPojo(HttpServletRequest request,
			InstituicaoForm localForm, Instituicao i) {
		
		i.setSigla(localForm.getSigla());
		i.setNome(localForm.getNome());
		i.setBairroDistrito(localForm.getBairro());
		i.setCep(localForm.getCep());
		i.setCodMunicipio(Integer.valueOf(localForm.getCodMunicipio()));
		i.setComplemento(localForm.getComplemento());
		i.setContato(localForm.getContato());
		i.setDescricaoRelatorio(localForm.getDescricaoRelatorio());
		i.setEmail(localForm.getEmail());
//		StringBuffer nomeLogo = new StringBuffer();
//		if (localForm.getLogotipo().getFileName().toUpperCase().contains(".JPG")){
//			nomeLogo.append(".JPG");
//		}else{
//			nomeLogo.append(".PNG");
//		}
		i.setLogoInstituicao((String)request.getSession().getAttribute("tipoImagem"));
		i.setLogradouro(localForm.getLogradouro());
		i.setMunicipio(localForm.getMunicipio());
		i.setNumero(localForm.getNumero());
		i.setTelefoneDdd(localForm.getTelDdd());
		i.setTelefoneNumero(StringUtil.removeFormatacao(localForm.getTelNumero()));
		i.setUf(localForm.getUf());
		
	}	
	
	/**
	 * Realiza carga da página de listagem de instituições fazendo pesquisa 
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void carregarLista(ActionForm form, HttpServletRequest request) throws ApplicationException {
	
		InstituicaoForm localForm = (InstituicaoForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
			
		Instituicao aux = new Instituicao();
		if (StringUtil.stringNotNull(localForm.getConSigla())){
			aux.setSigla("%"+localForm.getConSigla()+"%");
		}
		if (StringUtil.stringNotNull(localForm.getConNome())){
			aux.setNome("%"+localForm.getConNome()+"%");
		}
		
		Pagina pagina = new Pagina( Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina), Integer.parseInt(totalRegistros));
		pagina = CadastroFacade.listarInstituicao(pagina, aux);
		
		Util.htmlEncodeCollection(pagina.getRegistros());
		
		request.setAttribute("pagina", pagina);
		if (pagina.getTotalRegistros()==0){
			throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
		}
		
	
    }
	
	/**
	 * Realiza o encaminhamento necessário para atualizar os dados da instituição.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		InstituicaoForm localForm = (InstituicaoForm) form;
		setActionForward(mapping.findForward("pgEdit"));
		boolean erroToken = false;
		try	{
						
			// Aciona a validação do Form
			if(!validaForm(mapping,form,request)) {
				return getActionForward();
			}		
			if(StringUtils.isBlank(localForm.getTelDdd()) != StringUtils.isBlank(localForm.getTelNumero())) {			
				throw new ApplicationException("errors.invalid",  new String[]{"Telefone"}, ApplicationException.ICON_AVISO);
			}
			
			// Verifica se ja existe instituição com a mesma descricao ou com a mesma silga
			Instituicao i = new Instituicao();
			i.setSigla(localForm.getSigla());
			i.setNome(localForm.getNome());
			if (!CadastroFacade.verificaInstituicaoDuplicado(Integer.valueOf(localForm.getCodInstituicao()), i)){
				//verificar tipo e tamanho do arquivo de logo
//				int tam = localForm.getLogotipo().getFileSize();
//				if (tam> (20*1024)){
//					throw new ApplicationException("AVISO.5", new String[]{"Logotipo da Instituição maior que 20 Kb"}, ApplicationException.ICON_AVISO);
//				}
//				if(!localForm.getLogotipo().getFileName().toUpperCase().contains(".JPG") && !localForm.getLogotipo().getFileName().toUpperCase().contains(".PNG")){
//					throw new ApplicationException("AVISO.5", new String[]{"Logotipo da Instituição deve ser tipo PNG ou JPG"}, ApplicationException.ICON_AVISO);
//				}
				
				// Verifica se o TOKEN existe para evitar duplo submit
				if(isTokenValid(request)) {
					resetToken(request);
				} else {
					erroToken=true;
					throw new ApplicationException("AVISO.200");
				}
				
				Instituicao old = CadastroFacade.obterInstituicao(Integer.valueOf(localForm.getCodInstituicao()));
				
				populaPojo(request, localForm, old);
				old.setCpfResponsavelAtualizacao(SentinelaComunicacao.getInstance(request).getCpf());
				old.setTsAtualizacao(new Date());
				
				CadastroFacade.alterarInstituicao(old, (byte[])request.getSession().getAttribute("imagemSessao"));
				
				localForm.reset(mapping, request);
				addMessage("SUCESSO.25", request);	
				this.carregarLista(localForm, request);
				if(request.getSession().getAttribute("imagemSessao") != null){
					request.getSession().removeAttribute("imagemSessao");
					request.getSession().removeAttribute("tipoImagem");
				}
				
			}
		
		} catch (ApplicationException appEx) {
			if (!erroToken){
				// Salva o TOKEN para evitar duplo submit
				saveToken(request);
			}
			throw appEx;
		} catch (Exception e) {
			if (!erroToken){
				// Salva o TOKEN para evitar duplo submit
				saveToken(request);
			}
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Instituição"}, e);
		}
		
		return mapping.findForward("pgList");
	}
	
	/**
	 * Realiza o encaminhamento para exclusão de instituição.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward excluir(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		InstituicaoForm localForm = (InstituicaoForm) form;
		setActionForward(mapping.findForward("pgList"));	
		try {
			if (localForm.getCodInstituicao()!= null) {
				
				CadastroFacade.excluirInstituicao(Integer.parseInt(localForm.getCodInstituicao()));
				
				localForm.reset(mapping, request);
				
				addMessage("SUCESSO.33", request);
			}
			
			
			this.carregarLista(form, request);
		return getActionForward();
			
		} catch (ApplicationException appEx) {
			this.carregarLista(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarLista(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Instituição"}, e);
		}
	}
	
	/**
	 * Mostra a imagem de assinatura escolhida.<br>
	 * @author radoski
	 * @since 22/12/2008
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return actionForward
	 * @throws ApplicationException
	 */
	public ActionForward atualizarImagemLogotipo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try{
			

			InstituicaoForm localForm = (InstituicaoForm) form;
			setActionForward(mapping.findForward("pgEdit"));

			if(localForm.getLogotipo().getFileSize() > (20 * 1024)){
				throw new ApplicationException("AVISO.5", new String[]{"Logotipo da Instituição maior que 20 Kb"}, ApplicationException.ICON_AVISO);
			}
			if(!localForm.getLogotipo().getFileName().toUpperCase().contains(".JPG") && !localForm.getLogotipo().getFileName().toUpperCase().contains(".PNG")){
				throw new ApplicationException("AVISO.5", new String[]{"Logotipo da Instituição deve ser tipo PNG ou JPG"}, ApplicationException.ICON_AVISO);
			}
			
			if(request.getSession().getAttribute("imagemSessao") != null){
				request.getSession().removeAttribute("imagemSessao");
				request.getSession().removeAttribute("tipoImagem");
			}
			request.getSession().setAttribute("imagemSessao", localForm.getLogotipo().getFileData());
			if (localForm.getLogotipo().getFileName().toUpperCase().contains(".JPG")){
				request.getSession().setAttribute("tipoImagem", ".JPG");
			}else{
				request.getSession().setAttribute("tipoImagem", ".PNG");
			}

			
			return getActionForward();
		}catch (ApplicationException ae){
			
			throw ae;
		}catch (Exception e) {
			
			throw new ApplicationException("ERRO.201", new String[]{"ao atualizar logotipo da Instituição"}, e);
		}
	}
}