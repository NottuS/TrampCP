package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.ItemComboDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.ManterUsuarioForm;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.Orgao;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.pojo.UsuarioGrupoSentinela;
import gov.pr.celepar.abi.pojo.UsuarioOrgao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.CPF;
import gov.pr.celepar.framework.util.Data;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ManterUsuarioAction extends BaseDispatchAction {

	/**
	 * Carregar a interface Inicial do Caso de Uso.<br>
	 * @author vanessak
	 * @since 21/10/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward carregarPgListManterUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {

		log.info("Método carregarPgListManterUsuario processando...");

		try {
			setActionForward(mapping.findForward("pgConsultaUsuario"));
			

			request.getSession().setAttribute("listaGrupoPesquisa", Util.htmlEncodeCollection(CadastroFacade.listarGrupoSentinelaCombo(null)));
			
			Collection<Instituicao> listaInstituicao;
			if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())){
				listaInstituicao = CadastroFacade.listarInstituicao();
			}else{
				listaInstituicao = new ArrayList <Instituicao>();
				listaInstituicao.add(CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario()).getInstituicao());
			}
			
			request.getSession().setAttribute("listaInstituicao", Util.htmlEncodeCollection(listaInstituicao));

			ManterUsuarioForm localForm = (ManterUsuarioForm)form;
			localForm.setDesabilitaOrgao("true");
			localForm.setActionType("pesquisar");

			if (localForm.getPesqExec() != null && localForm.getPesqExec().equals("S")) {
				this.pesquisarManterUsuario(mapping, localForm, request, response);
			} else {
				request.getSession().setAttribute("pagina", null);
				request.getSession().setAttribute("grupos", Util.htmlEncodeCollection(CadastroFacade.listarGrupoSentinelaCombo(null)));
				localForm.setConSituacao("1");
			}
			return getActionForward();
		} catch (ApplicationException appEx) {
			log.error(appEx);
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".carregarPgListManterUsuario"}, e, ApplicationException.ICON_ERRO);
		} 
	}
	
	/**
	 * Realiza a consulta de Doações.<br>
	 * @author vanessak
	 * @since  11/07/2011
	 * @param  mapping : ActionMapping
	 * @param  form : ActionForm
	 * @param  request : HttpServletRequest
	 * @param  response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward pesquisarManterUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {
		log.info("Método pesquisarManterUsuario processando...");
		try{
			setActionForward(mapping.findForward("pgConsultaUsuario"));
			ManterUsuarioForm localForm = (ManterUsuarioForm)form;
			localForm.setPesqExec("S");

			Integer numPagina = (request.getParameter("indice") == null ? 1 : Integer.valueOf(request.getParameter("indice")));
			Integer totalRegistros = request.getParameter("totalRegistros") == null ? 0 : Integer.valueOf(request.getParameter("totalRegistros"));
			
			Usuario objPesquisa = new Usuario();
			if (localForm.getConOrgao() != null && localForm.getConOrgao().trim().length() > 0) {
				Set<UsuarioOrgao> lista = new HashSet<UsuarioOrgao>();
				UsuarioOrgao usuarioOrgao = new UsuarioOrgao();
				Orgao orgao = new Orgao();
				orgao.setCodOrgao(localForm.getConOrgao() == null ? null: Integer.valueOf(localForm.getConOrgao().trim()));
				usuarioOrgao.setOrgao(orgao);
				lista.add(usuarioOrgao);
				objPesquisa.setListaUsuarioOrgao(lista);
			}
			if (localForm.getConGrupo() != null && localForm.getConGrupo().trim().length() > 0) {
				UsuarioGrupoSentinela obj = new UsuarioGrupoSentinela();
				obj.setGrupoSentinela(CadastroFacade.obterGrupoSentinelaByDescSentinela(localForm.getConGrupo().trim()));
				Set<UsuarioGrupoSentinela> lista = new HashSet<UsuarioGrupoSentinela>();
				lista.add(obj);
				objPesquisa.setListaUsuarioGrupoSentinela(lista);
			}
			if(StringUtil.stringNotNull(localForm.getConInstituicao())){
				Instituicao i = new Instituicao();
				i.setCodInstituicao(Integer.valueOf(localForm.getConInstituicao()));
				objPesquisa.setInstituicao(i);
			}else{
				if (!CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())){
					objPesquisa.setInstituicao(CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario()).getInstituicao());
				}
			}
			
			objPesquisa.setCpf(localForm.getConCpf() == null ? "" : Util.removerFormatacaoCPF(localForm.getConCpf().trim()));
			objPesquisa.setNome(localForm.getConNome() == null ? "" : localForm.getConNome().trim());
			objPesquisa.setSituacao((localForm.getConSituacao() == null || localForm.getConSituacao().trim().equals("")) ? null: Integer.valueOf(localForm.getConSituacao().trim()));
			

			Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), numPagina, totalRegistros);

			pagina = CadastroFacade.listarUsuario(pagina, objPesquisa);
			if (pagina.getTotalRegistros()==0){
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.setAttribute("pagina", pagina);
			
			return getActionForward();		
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".pesquisarManterUsuario"}, e, ApplicationException.ICON_ERRO);
		} 

	}
	
	/**
	 * Retorna para a página anterior.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward voltar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		log.info("Método voltar processando...");
		request.getSession().setAttribute("grupos", null);
		request.getSession().setAttribute("listaGrupoPesquisa", null);
		request.getSession().setAttribute("orgaos", null);
		request.getSession().setAttribute("listGrupos", null);
		request.getSession().setAttribute("listOrgaos", null);

		ManterUsuarioForm localForm = (ManterUsuarioForm) form;
		localForm.limparCampos();
		localForm.setCodUsuario(null);
		return this.carregarPgListManterUsuario(mapping, form, request, response);
	}

	/**
	 * Carregar a interface de Inclusão e Alteração do Caso de Uso.<br>
	 * @author vanessak
	 * @since 11/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward carregarPgEditManterUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {

		log.info("Método carregarPgEditManterUsuario processando...");
		try {
			ManterUsuarioForm localForm = (ManterUsuarioForm)form;
			
			saveToken(request);
			
			localForm.limparCampos();
			localForm.setDesabilitaCampo("false");
			request.getSession().setAttribute("grupos", null);
			request.getSession().setAttribute("orgaos", null);
			request.getSession().setAttribute("listGrupos", null);
			request.getSession().setAttribute("listOrgaos", null);
			request.getSession().setAttribute("grupos", Util.htmlEncodeCollection(CadastroFacade.listarGrupoSentinelaCombo(null)));

			if (localForm.getActionType()!= null && localForm.getActionType().equals("incluir")) {
				localForm.setSituacao("1");
				localForm.setCodUsuario(null);
			} else {
				localForm.setDesabilitaCampo("true");
				populaForm(localForm, request);
			}

			return mapping.findForward("pgEditUsuario");				
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgConsultaUsuario"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgConsultaUsuario"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".carregarPgEditManterUsuario"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Carregar a interface de Exibir, Excluir, Revogar/Devolver do Caso de Uso.<br>
	 * @author vanessak
	 * @since 11/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward carregarPgViewManterUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {

		log.info("Método carregarPgViewManterUsuario processando...");
		try {
			saveToken(request);
			setActionForward(mapping.findForward("pgViewUsuario"));
			ManterUsuarioForm localForm = (ManterUsuarioForm)form;

			if (localForm.getCodUsuario() != null && localForm.getCodUsuario().length() > 0) {
				populaForm(localForm, request);
			}

			return getActionForward();				
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".carregarPgViewManterUsuario"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Realiza o encaminhamento necessário para salvar o Usuário.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward salvarManterUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método salvarManterUsuario processando...");
		try	{
			setActionForward(mapping.findForward("pgEditUsuario"));
			ManterUsuarioForm localForm = (ManterUsuarioForm) form;
						
			//verifica se esse login ja é utilizado no sentinela para os novos usuarios
			if(StringUtil.stringNotNull(localForm.getLogin())){
				SentinelaParam sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByLogin(localForm.getLogin());

				if (sentinelaParam != null && sentinelaParam.getCodigo() > 0 && !sentinelaParam.getParamAux()[2].equals(Util.removerFormatacaoCPF(localForm.getCpf()))) {
					throw new ApplicationException("AVISO.10", ApplicationException.ICON_AVISO);
				}
			}
			
			validaDadosForm(localForm, request, "I");
			
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	

			
			Usuario usuario = new Usuario();
			
			
			this.populaPojoRegistro(usuario, "I", SentinelaComunicacao.getInstance(request).getCpf());
			
			
			this.populaPojo(usuario, localForm, request);
			
			SentinelaParam sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByCPF(Util.removerFormatacaoCPF(localForm.getCpf()));
			if (sentinelaParam != null && sentinelaParam.getCodigo() > 0) {
				usuario.setIdSentinela(sentinelaParam.getCodigo());
			}

			usuario = CadastroFacade.salvarUsuario(usuario, request);
			
			if (usuario.getSituacao().equals(Integer.valueOf(0))) {
				CadastroFacade.excluirGruposUsuarioInativo(usuario, request);
			}

			
			localForm.limparCampos();
			request.getSession().setAttribute("grupos", null);
			request.getSession().setAttribute("orgaos", null);
			request.getSession().setAttribute("listGrupos", null);
			request.getSession().setAttribute("listOrgaos", null);
			this.addMessage("SUCESSO.48", new String[]{"do Usuário"}, request);	
			
		} catch (ApplicationException appEx) {
			//populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			//populaForm(form, request);
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".salvarManterUsuario"}, e, ApplicationException.ICON_ERRO);
		} 
		return this.carregarPgListManterUsuario(mapping, form, request, response);
		
	}

	/**
	 * Realiza o encaminhamento necessário para alterar o Usuário.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward alterarManterUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método alterarManterUsuario processando...");
		try	{			
			setActionForward(mapping.findForward("pgEditUsuario"));
			ManterUsuarioForm localForm = (ManterUsuarioForm) form;
	
			String acao = "A";
			// Aciona a validação do Form
			if (localForm.getCodUsuario() == null || localForm.getCodUsuario().trim().length() == 0) {
				SentinelaParam sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByLogin(localForm.getLogin());
				if (sentinelaParam != null && sentinelaParam.getCodigo() > 0 && !localForm.getIdSentinela().equals(Long.toString(sentinelaParam.getCodigo()))) {
					throw new ApplicationException("AVISO.10", ApplicationException.ICON_AVISO);
				}
				
			}
			validaDadosForm(localForm, request, acao);
			
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	

			Usuario usuario = CadastroFacade.obterUsuario(Integer.valueOf(localForm.getCodUsuario()));
			
			this.populaPojoRegistro(usuario, acao, SentinelaComunicacao.getInstance(request).getCpf());
			
			
			this.populaPojo(usuario, localForm, request);

			
			
			usuario = CadastroFacade.salvarUsuario(usuario, request);


			if (usuario.getSituacao().equals(Integer.valueOf(0))) {
				CadastroFacade.excluirGruposUsuarioInativo(usuario, request);
			}

			saveToken(request);
			localForm.limparCampos();
			request.getSession().setAttribute("grupos", null);
			request.getSession().setAttribute("orgaos", null);
			request.getSession().setAttribute("listGrupos", null);
			request.getSession().setAttribute("listOrgaos", null);
			this.addMessage("SUCESSO.49", new String[]{"do Usuário"}, request);	

		} catch (ApplicationException appEx) {
			//populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			//populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".alterarManterUsuario"}, e, ApplicationException.ICON_ERRO);
		} 
		return this.carregarPgListManterUsuario(mapping, form, request, response);
		
	}

	/**
	 * Realiza o encaminhamento necessário para alterar o Usuário.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward excluirManterUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método excluirManterUsuario processando...");
		try	{			
			setActionForward(mapping.findForward("pgListUsuario"));
			ManterUsuarioForm localForm = (ManterUsuarioForm) form;
			
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	

			Usuario usuario = CadastroFacade.obterUsuario(Integer.valueOf(localForm.getCodUsuario()));
			this.populaPojoRegistro(usuario, "E", SentinelaComunicacao.getInstance(request).getCpf());
			CadastroFacade.excluirUsuario(usuario, request);
			
			
			localForm.limparCampos();
			localForm.setCodUsuario(null);
			this.addMessage("SUCESSO.50", new String[]{"do Usuário"}, request);	
			
		} catch (ApplicationException appEx) {
			populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".excluirManterUsuario"}, e, ApplicationException.ICON_ERRO);
		} 
		
		return this.carregarPgListManterUsuario(mapping, form, request, response);
	}

	private void validaDadosForm(ManterUsuarioForm localForm, HttpServletRequest request, String acao) throws ApplicationException {
		StringBuffer str = new StringBuffer();
		
		if (localForm.getCpf() == null || localForm.getCpf().trim().length() == 0) {
			str.append("CPF");
		}
		if (localForm.getNome() == null || localForm.getNome().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Nome");
		}
		if (localForm.getLogin() == null || localForm.getLogin().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Login");
		}
		if (localForm.getMail() == null || localForm.getMail().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("E-Mail");
		}
		if (localForm.getSituacao() == null || localForm.getSituacao().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Situação");
		}

		Pagina p = (Pagina) request.getSession().getAttribute("listGrupos");
		if (p== null || p.getRegistros()== null || p.getRegistros().size()<1){
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Grupo");
		}
		
		UsuarioGrupoSentinela usuarioGrupo = new UsuarioGrupoSentinela();
		
		usuarioGrupo.setGrupoSentinela(CadastroFacade.obterGrupoSentinelaByDescSentinela(GrupoSentinela.ADM_GERAL.getDescricao()));
		
		boolean temAdmGeral = p.getRegistros().contains(usuarioGrupo);
		
		usuarioGrupo.setGrupoSentinela(CadastroFacade.obterGrupoSentinelaByDescSentinela(GrupoSentinela.OPE_ORG_ABI.getDescricao()));
		
		boolean temOpeOrg = p.getRegistros().contains(usuarioGrupo);
		
		if (!temAdmGeral && !StringUtil.stringNotNull( localForm.getInstituicao())){
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Instituição");
		}
		
		
		
		if (temOpeOrg){
			Pagina o = (Pagina) request.getSession().getAttribute("listOrgaos");
			if (o== null || o.getRegistros()== null || o.getRegistros().size()<1){
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Órgão");
			}
		}
		

		if (str.length() > 0) {
			str.append(".");
			throw new ApplicationException("AVISO.72", new String[]{str.toString()}, ApplicationException.ICON_AVISO);
		}

		//valida CPF
		if (localForm.getCpf() != null && localForm.getCpf().trim().length() > 0) {
			String cpf = Util.removerFormatacaoCPF(localForm.getCpf());
			boolean result = CPF.validarCPF(cpf);
			if (!result) {
				throw new ApplicationException("errors.cpf", ApplicationException.ICON_AVISO);
			}
			if (acao.equalsIgnoreCase("I")) {
				Usuario usuario = CadastroFacade.obterUsuarioByCPF(cpf);
				if (usuario != null) {
					if (usuario.getSituacao().equals(Integer.valueOf(1))) { // ATIVO
						throw new ApplicationException("AVISO.79", new String[]{localForm.getCpf(), ""}, ApplicationException.ICON_AVISO);
					} else { // INATIVO
						throw new ApplicationException("AVISO.79", new String[]{localForm.getCpf(), " com status Inativo, favor realizar a operação de Ativação"}, ApplicationException.ICON_AVISO);
					}
				}
			}
		}

		//valida Mail
		if (localForm.getMail() != null && localForm.getMail().trim().length() > 0) {
			boolean result = false;
			try {
				result = Util.validarEMAIL(localForm.getMail());
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao validar E-mail!"}, ApplicationException.ICON_AVISO);
			}
			if (!result) {
				throw new ApplicationException("AVISO.21", ApplicationException.ICON_AVISO);
			}
		}
		
		
		
	}	

	/**
	 * Realiza o encaminhamento necessário para adicionar um Órgão ao Usuário.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward adicionarOrgaoManterUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método adicionarOrgaoManterUsuario processando...");
		try	{	
			
			
			setActionForward(mapping.findForward("pgListGruposUsuario"));
			ManterUsuarioForm localForm = (ManterUsuarioForm) form;
			Set<UsuarioOrgao> listaOrgaos;
			
			if (!StringUtil.stringNotNull(localForm.getOrgao())){
				throw new ApplicationException("AVISO.72", new String[]{"Órgão"}, ApplicationException.ICON_AVISO);
			}
				
			Pagina p = (Pagina) request.getSession().getAttribute("listOrgaos");
			if (p!= null && p.getRegistros()!= null && p.getRegistros().size()>0){
				listaOrgaos = (Set<UsuarioOrgao>) p.getRegistros();
			}else{
				p = new Pagina();
				listaOrgaos = new HashSet<UsuarioOrgao>();
			}
			

			UsuarioOrgao usuarioOrgao = new UsuarioOrgao();
			

			usuarioOrgao.setOrgao(CadastroFacade.obterOrgao(Integer.valueOf(localForm.getOrgao())));
			//usuarioOrgao.setUsuario(null);
			
			listaOrgaos.add(usuarioOrgao);
			
			p.setRegistros(listaOrgaos);
			p.setTotalRegistros(listaOrgaos.size());
			
			request.getSession().setAttribute("listOrgaos", p);
			
			
			List<ItemComboDTO> orgaos = (List<ItemComboDTO>) request.getSession().getAttribute("orgaos");
			ItemComboDTO aux = new ItemComboDTO();
			aux.setCodigo(localForm.getOrgao());
			orgaos.remove(aux);
			request.getSession().setAttribute("orgaos", orgaos);
			
			
			
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".adicionarOrgaoManterUsuario"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	/**
	 * Realiza o encaminhamento necessário para excluir um Órgão do Usuário.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward excluirOrgaoManterUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método excluirOrgaoManterUsuario processando...");
		try	{			
			setActionForward(mapping.findForward("pgListGruposUsuario"));
			ManterUsuarioForm localForm = (ManterUsuarioForm) form;
			Set<UsuarioOrgao> listaOrgaos;
			
			if (!StringUtil.stringNotNull(localForm.getOrgao())){
				throw new ApplicationException("AVISO.72", new String[]{"Órgão"}, ApplicationException.ICON_AVISO);
			}
				
			Pagina p = (Pagina) request.getSession().getAttribute("listOrgaos");
			if (p!= null && p.getRegistros()!= null && p.getRegistros().size()>0){
				listaOrgaos = (Set<UsuarioOrgao>) p.getRegistros();
			}else{
				p = new Pagina();
				listaOrgaos = new HashSet<UsuarioOrgao>();
			}
			
			Orgao orgao = CadastroFacade.obterOrgao(Integer.valueOf(localForm.getOrgao()));
			
			for(UsuarioOrgao usuarioOrgao : listaOrgaos){
				if(usuarioOrgao.getOrgao().getCodOrgao().equals(orgao.getCodOrgao()))
				{
					listaOrgaos.remove(usuarioOrgao);
					break;
				}
			}
		
			p.setRegistros(listaOrgaos);
			p.setTotalRegistros(listaOrgaos.size());
			
			request.getSession().setAttribute("listOrgaos", p);
			
			
			List<ItemComboDTO> orgaos = (List<ItemComboDTO>) request.getSession().getAttribute("orgaos");
			ItemComboDTO aux = new ItemComboDTO();
			aux.setCodigo(orgao.getCodOrgao().toString());
			aux.setDescricao(orgao.getSiglaDescricao());
			orgaos.add(aux);
			request.getSession().setAttribute("orgaos", orgaos);
				
			return getActionForward();

		} catch (ApplicationException appEx) {
			//populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			//populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".excluirOrgaoManterUsuario"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	/**
	 * Realiza o encaminhamento necessário para adicionar um Grupo ao Usuário.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward adicionarGrupoManterUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método adicionarGrupoManterUsuario processando...");
		try	{			
			setActionForward(mapping.findForward("pgListGruposUsuario"));
			ManterUsuarioForm localForm = (ManterUsuarioForm) form;
			Set<UsuarioGrupoSentinela> listaGrupos;
			
			if (!StringUtil.stringNotNull(localForm.getGrupo())){
				throw new ApplicationException("AVISO.72", new String[]{"Grupo"}, ApplicationException.ICON_AVISO);
			}
			
			//ja é Administrador Geral
			if (localForm.getIsAdmGeral()!= null &&  localForm.getIsAdmGeral().equalsIgnoreCase("true")){
				throw new ApplicationException("AVISO.100", ApplicationException.ICON_AVISO);
			}
			
			
			Pagina p = (Pagina) request.getSession().getAttribute("listGrupos");
			if (p!= null && p.getRegistros()!= null && p.getRegistros().size()>0){
				listaGrupos = (Set<UsuarioGrupoSentinela>) p.getRegistros();
			}else{
				p = new Pagina();
				listaGrupos = new HashSet<UsuarioGrupoSentinela>();
			}
			
			//Esta cadastrando como AdmGeral deve ficar somente esse grupo
			if (localForm.getGrupo().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
				localForm.setIsAdmGeral("true");
				localForm.setDesabilitaOrgao("true");
				localForm.setDesabilitaInstituicao("true");
				listaGrupos = new HashSet<UsuarioGrupoSentinela>();
				Set<UsuarioOrgao> listaOrgaos = new HashSet<UsuarioOrgao>();
				Pagina o = new Pagina();
				o.setRegistros(listaOrgaos);
				o.setTotalRegistros(listaOrgaos.size());
				request.getSession().setAttribute("listOrgaos", o);
				request.getSession().setAttribute("grupos", Util.htmlEncodeCollection(CadastroFacade.listarGrupoSentinelaCombo(null)));
			}
			
			//Esta cadastrando como AdmGeral deve ficar somente esse grupo
			if (localForm.getGrupo().equals(GrupoSentinela.OPE_ORG_ABI.getDescricao())){
				localForm.setDesabilitaOrgao("false");
			}
			
			UsuarioGrupoSentinela usuarioGrupo = new UsuarioGrupoSentinela();

			usuarioGrupo.setGrupoSentinela(CadastroFacade.obterGrupoSentinelaByDescSentinela(localForm.getGrupo()));
			usuarioGrupo.setUsuario(null);
			
			listaGrupos.add(usuarioGrupo);
			
			p.setRegistros(listaGrupos);
			p.setTotalRegistros(listaGrupos.size());
			
			request.getSession().setAttribute("listGrupos", p);
			
			if (localForm.getIsAdmGeral()== null ||  !localForm.getIsAdmGeral().equalsIgnoreCase("true")){

				List<ItemComboDTO> grupos = (List<ItemComboDTO>) request.getSession().getAttribute("grupos");
				ItemComboDTO aux = new ItemComboDTO();
				aux.setCodigo(localForm.getGrupo());
				grupos.remove(aux);
				request.getSession().setAttribute("grupos", grupos);
			}
			
			return getActionForward();

		} catch (ApplicationException appEx) {
			
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".adicionarAssinaturaManterUsuario"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	/**
	 * Realiza o encaminhamento necessário para excluir um Grupo do Usuário.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward excluirGrupoManterUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método excluirGrupoManterUsuario processando...");
		try	{			
			
			
			setActionForward(mapping.findForward("pgListGruposUsuario"));
			ManterUsuarioForm localForm = (ManterUsuarioForm) form;
			
			if (!StringUtil.stringNotNull(localForm.getGrupo())){
				throw new ApplicationException("AVISO.72", new String[]{"Grupo"}, ApplicationException.ICON_AVISO);
			}
			
			
			gov.pr.celepar.abi.pojo.GrupoSentinela grupo = CadastroFacade.obterGrupoSentinelaByCodigo(Integer.valueOf(localForm.getGrupo()));
			
			Set<UsuarioGrupoSentinela> listaGrupos;
			Pagina p = (Pagina) request.getSession().getAttribute("listGrupos");
			if (p!= null && p.getRegistros()!= null && p.getRegistros().size()>0){
				listaGrupos = (Set<UsuarioGrupoSentinela>) p.getRegistros();
			}else{
				listaGrupos = new HashSet<UsuarioGrupoSentinela>();
			}
			
			//Esta cadastrando como AdmGeral deve ficar somente esse grupo
			if (grupo.getDescricaoSentinela().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
				localForm.setIsAdmGeral("false");
				localForm.setDesabilitaInstituicao("false");
			}
			
			//Esta cadastrando como AdmGeral deve ficar somente esse grupo
			if (grupo.getDescricaoSentinela().equals(GrupoSentinela.OPE_ORG_ABI.getDescricao())){
				Set<UsuarioOrgao> listaOrgaos = new HashSet<UsuarioOrgao>();
				Pagina o = new Pagina();
				o.setRegistros(listaOrgaos);
				o.setTotalRegistros(listaOrgaos.size());
				request.getSession().setAttribute("listOrgaos", o);
				localForm.setDesabilitaOrgao("true");
			}
			
			for(UsuarioGrupoSentinela usuarioGrupo : listaGrupos){
				if(usuarioGrupo.getGrupoSentinela().getCodGrupoSentinela().equals(grupo.getCodGrupoSentinela()))
				{
					listaGrupos.remove(usuarioGrupo);
					break;
				}
			}

			
			
			p.setRegistros(listaGrupos);
			request.getSession().setAttribute("listGrupos", p);
			
			List<ItemComboDTO> grupos = (List<ItemComboDTO>) request.getSession().getAttribute("grupos");
			ItemComboDTO aux = new ItemComboDTO();
			aux.setCodigo(grupo.getDescricaoSentinela());
			aux.setDescricao(grupo.getDescricaoGrupo());
			grupos.add(aux);
			
			request.getSession().setAttribute("grupos", grupos);
			
			return getActionForward();
			
			
		} catch (ApplicationException appEx) {
			//populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			//populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".excluirGrupoManterUsuario"}, e, ApplicationException.ICON_ERRO);
		} 

	}

	/**
	 * Carrega o combobox de orgãos de acordo com a Administração selecionada
	 * @param form
	 */
	public ActionForward carregarComboOrgaoPesquisa (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboOrgaoPesquisa processando...");
		try {
			setActionForward(mapping.findForward("pgComboOrgaoUsuarioPesq"));
			ManterUsuarioForm localForm = (ManterUsuarioForm) form;
			String tipoAdministracao = (StringUtils.isNotBlank(localForm.getConAdministracao()))?localForm.getConAdministracao():null;
			String codInstituicao = (StringUtils.isNotBlank(localForm.getConInstituicao()))?localForm.getConInstituicao():null;
			
			if (tipoAdministracao != null && codInstituicao!= null ){
				request.getSession().setAttribute("orgaosPesquisar", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf( tipoAdministracao),Integer.valueOf(codInstituicao))));
			} else {
				request.getSession().setAttribute("orgaosPesquisar", null);
			}
			
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".carregarComboOrgaoPesquisa"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Carrega o combobox de orgãos de acordo com a Administração selecionada
	 * @param form
	 */
	public ActionForward carregarComboOrgao (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboOrgao processando...");
		try {
			setActionForward(mapping.findForward("pgComboOrgaoUsuario"));
			ManterUsuarioForm localForm = (ManterUsuarioForm) form;
			String tipoAdministracao = (StringUtils.isNotBlank(localForm.getAdministracao()))?localForm.getAdministracao():null;
			String codInstituicao = (StringUtils.isNotBlank(localForm.getInstituicao()))?localForm.getInstituicao():null;
			
			if (tipoAdministracao != null && codInstituicao!= null ){
				request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf( tipoAdministracao),Integer.valueOf(codInstituicao))));
			} else {
				request.getSession().setAttribute("orgaos", null);
			}
			
			
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".carregarComboOrgao"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Verifica o usuário pelo CPF informado
	 * @param form
	 */
	public ActionForward verificaUsuario (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método verificaUsuario processando...");
		try {
			setActionForward(mapping.findForward("pgEditUsuario"));

			ManterUsuarioForm localForm = (ManterUsuarioForm) form;

			localForm.setDesabilitaCampo("false");
			localForm.setNome("");
			localForm.setIdSentinela("");
			localForm.setLogin("");
			localForm.setMail("");
			verificaGrupoUsuarioCadastrado(request, localForm);
			
			String cpf = Util.removerFormatacaoCPF(localForm.getCpf());
			boolean result = CPF.validarCPF(cpf);
			if (!result) {
				throw new ApplicationException("errors.cpf", ApplicationException.ICON_AVISO);
			}
			Usuario usuario = CadastroFacade.obterUsuarioByCPF(cpf);
			
			if (usuario != null) {
				localForm.setDesabilitaCampo("true");
				if (usuario.getSituacao().equals(Integer.valueOf(1))) { // ATIVO
					throw new ApplicationException("AVISO.79", new String[]{localForm.getCpf(), ""}, ApplicationException.ICON_AVISO);
				} else { // INATIVO
					throw new ApplicationException("AVISO.79", new String[]{localForm.getCpf(), " com status Inativo, favor realizar a operação de Ativação"}, ApplicationException.ICON_AVISO);
				}
			}
			
			SentinelaParam sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByCPF(cpf);
			if (sentinelaParam != null && sentinelaParam.getCodigo() > 0) {
				localForm.setDesabilitaCampo("true");
				localForm.setNome(sentinelaParam.getNome());
				localForm.setIdSentinela(String.valueOf(sentinelaParam.getCodigo()));
				localForm.setLogin(sentinelaParam.getParamAux()[0]);
				localForm.setMail(sentinelaParam.getParamAux()[2]);
			}
			
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".verificaUsuario"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Verifica se o login informado já está cadastrado no Sentinela
	 * @param form
	 */
	public ActionForward verificaLoginUsuario (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método verificaLoginUsuario processando...");
		try {
			setActionForward(mapping.findForward("pgEditUsuario"));

			ManterUsuarioForm localForm = (ManterUsuarioForm) form;

			verificaGrupoUsuarioCadastrado(request, localForm);
			
			SentinelaParam sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByLogin(localForm.getLogin());
			if (sentinelaParam != null && sentinelaParam.getCodigo() > 0) {
				throw new ApplicationException("AVISO.10", ApplicationException.ICON_AVISO);
			}
			
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".verificaLoginUsuario"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private void populaPojo(Usuario usuario, ManterUsuarioForm localForm,HttpServletRequest request) throws ApplicationException {
		log.info("Método populaPojo processando...");
		try {
			usuario.setNome(localForm.getNome());
			usuario.setCpf(Util.removerFormatacaoCPF(localForm.getCpf()));
			usuario.setLoginSistema(localForm.getLogin());
			if (localForm.getIdSentinela() != null && localForm.getIdSentinela().trim().length() > 0) {
				usuario.setIdSentinela(Long.valueOf(localForm.getIdSentinela()));
			}
			usuario.setSituacao(Integer.valueOf(localForm.getSituacao()));
			usuario.setMail(localForm.getMail());
			
			Pagina p = (Pagina) request.getSession().getAttribute("listGrupos");
			if (p!= null && p.getRegistros()!= null && p.getRegistros().size()>0){
				usuario.setListaUsuarioGrupoSentinela( (Set<UsuarioGrupoSentinela>) p.getRegistros());
			}else{
				usuario.setListaUsuarioGrupoSentinela(null);
			}
			if (StringUtil.stringNotNull(localForm.getInstituicao())){
				usuario.setInstituicao(CadastroFacade.obterInstituicao(Integer.valueOf(localForm.getInstituicao())));
			}else{
				usuario.setInstituicao(null);
			}
			Pagina o = (Pagina) request.getSession().getAttribute("listOrgaos");
			if (o!= null && o.getRegistros()!= null && o.getRegistros().size()>0){
				usuario.setListaUsuarioOrgao ((Set<UsuarioOrgao>) o.getRegistros());
			}else{
				usuario.setListaUsuarioOrgao(null);
			}
			
			
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".populaPojo"}, e, ApplicationException.ICON_ERRO);
		} 
	}
	
	private void populaPojoRegistro(Usuario usuario, String acao, String cpf) throws ApplicationException {
		log.info("Método populaPojoRegistro processando...");
		try {
			Date dataAtual = new Date();
			
		    if ("I".equals(acao)) {
		    	usuario.setTsInclusao(dataAtual);
		    	usuario.setCpfResponsavelInclusao(cpf);
			} else if ("A".equals(acao)) {
				usuario.setTsAtualizacao(dataAtual);
				usuario.setCpfResponsavelAlteracao(cpf);
			} else if ("E".equals(acao)) {
				usuario.setTsExclusao(dataAtual);
				usuario.setCpfResponsavelExclusao(cpf);
			}
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".populaPojoRegistro"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private void populaForm(ActionForm form, HttpServletRequest request) throws ApplicationException {
		log.info("Método populaForm processando...");
		try {
			ManterUsuarioForm localForm = (ManterUsuarioForm)form;
			if (localForm.getCodUsuario() != null && localForm.getCodUsuario().trim().length() > 0) {
				Usuario usuario = CadastroFacade.obterUsuario((StringUtils.isNotBlank(localForm.getCodUsuario()))?Integer.valueOf(localForm.getCodUsuario()):0);
				
				localForm.setCodUsuario(String.valueOf(usuario.getCodUsuario()));
				localForm.setNome(usuario.getNome());
				localForm.setCpf(CPF.formataCPF(usuario.getCpf()));
				localForm.setLogin(usuario.getLoginSistema());
				localForm.setIdSentinela(String.valueOf(usuario.getIdSentinela()));
				localForm.setSituacao(String.valueOf(usuario.getSituacao()));
				localForm.setMail(usuario.getMail());
				localForm.setSituacaoDesc(usuario.getSituacaoDescricao());
				if (usuario.getInstituicao()!=null){
					localForm.setInstituicao(usuario.getInstituicao().getCodInstituicao().toString());
					localForm.setInstituicaoDesc(usuario.getInstituicao().getSiglaDescricao());
				}

				this.carregarRealizadoPor(request, form, usuario);

				this.carregarCamposRequest(localForm, request);
				this.verificaGrupoUsuarioCadastrado(request, localForm);
			}
			this.carregarListasManterUsuario(localForm, request);
			
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".populaForm"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private void carregarRealizadoPor(HttpServletRequest request, ActionForm form, Usuario usuario) throws ApplicationException {
		log.info("Método carregarRealizadoPor processando...");
		try {
			ManterUsuarioForm localForm = (ManterUsuarioForm)form;
			SentinelaParam sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByCPF(usuario.getCpfResponsavelInclusao());
			String aux = "";
			if (sentinelaParam != null) {
				aux = "Inclusão realizada por ";
				aux = aux.concat(sentinelaParam.getNome().trim());
				aux = aux.concat(" em ");
				aux = aux.concat(Data.formataData(usuario.getTsInclusao(),"dd/MM/yyyy HH:mm"));
				localForm.setIncluidoPor(aux);
			}
			if (usuario.getCpfResponsavelAlteracao() != null && usuario.getCpfResponsavelAlteracao().length() > 0){
				sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByCPF(usuario.getCpfResponsavelAlteracao());
				if (sentinelaParam != null) {
					aux = "Alteração realizada por ";
					aux = aux.concat(sentinelaParam.getNome().trim());
					aux = aux.concat(" em ");
					aux = aux.concat(Data.formataData(usuario.getTsAtualizacao(),"dd/MM/yyyy HH:mm"));
					localForm.setAlteradoPor(aux);
				}
			}
			if (usuario.getCpfResponsavelExclusao() != null && usuario.getCpfResponsavelExclusao().length() > 0){
				sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByCPF(usuario.getCpfResponsavelExclusao());
				if (sentinelaParam != null) {
					aux = "Exclusão realizada por ";
					aux = aux.concat(sentinelaParam.getNome().trim());
					aux = aux.concat(" em ");
					aux = aux.concat(Data.formataData(usuario.getTsExclusao(),"dd/MM/yyyy HH:mm"));
					localForm.setExcluidoPor(aux);
				}
			}
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".carregarRealizadoPor"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private void carregarCamposRequest (ActionForm form, HttpServletRequest request) throws NumberFormatException, ApplicationException {
		log.info("Método carregarCamposRequest processando...");
		try {
			ManterUsuarioForm localForm = (ManterUsuarioForm)form;
			if (localForm.getAdministracao() != null && localForm.getAdministracao().trim().length() > 0) {
				//verifica se usuario logado é AdmGeral
				if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())){
					if (localForm.getInstituicao() != null){
					request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(localForm.getAdministracao()), Integer.valueOf(localForm.getInstituicao()))));
					}
				}else{
					request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(localForm.getAdministracao()), CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario()).getInstituicao().getCodInstituicao())));
				}
			} else {
				request.getSession().setAttribute("orgaos", null);
			}
			request.getSession().setAttribute("grupos", Util.htmlEncodeCollection(CadastroFacade.listarGrupoSentinelaCombo(Integer.valueOf(localForm.getCodUsuario()))));
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".carregarCamposRequest"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private void carregarListasManterUsuario(ActionForm form, HttpServletRequest request) throws ApplicationException {
		log.info("Método carregarListasManterUsuario processando...");
		try {
			ManterUsuarioForm localForm = (ManterUsuarioForm)form;
			if (localForm.getCodUsuario() == null || localForm.getCodUsuario().isEmpty() || localForm.getCodUsuario().trim().length() < 1) {
				request.getSession().setAttribute("listGrupos", null);
				request.getSession().setAttribute("listOrgaos", null);
			} else {
				Pagina pagina = new Pagina(null, null, null);
				request.getSession().setAttribute("listGrupos", CadastroFacade.listarGruposByUsuario(pagina, Integer.valueOf(localForm.getCodUsuario())));
				pagina = new Pagina(null, null, null);
				request.getSession().setAttribute("listOrgaos", CadastroFacade.listarOrgaosByUsuario(pagina, Integer.valueOf(localForm.getCodUsuario())));
			}
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".carregarListasManterUsuario"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Verifica se o grupo informado permite habilitar o órgão para filtro
	 * @param form
	 */
	public ActionForward verificaGrupoSelecionado (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método verificaGrupoSelecionado processando...");
		try {
			setActionForward(mapping.findForward("pgConsultaUsuario"));

			ManterUsuarioForm localForm = (ManterUsuarioForm)form;
			localForm.setDesabilitaOrgao("true");

			if (localForm.getConGrupo().trim().equalsIgnoreCase(gov.pr.celepar.abi.enumeration.GrupoSentinela.OPE_ORG_ABI.getDescricao())) {
				localForm.setDesabilitaOrgao("false");
			} else {
				localForm.setConAdministracao("");
				localForm.setConOrgao("");
			}
			
			return getActionForward();
			
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterUsuarioAction.class.getSimpleName()+".verificaGrupoSelecionado"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private void verificaGrupoUsuarioCadastrado(HttpServletRequest request, ManterUsuarioForm localForm) throws  ApplicationException {
		log.info("Método verificaGrupoUsuarioCadastrado processando...");
		localForm.setDesabilitaOrgao("true");
		localForm.setDesabilitaInstituicao("false");
		localForm.setIsAdmGeral("false");
		
		if (StringUtil.stringNotNull( localForm.getCodUsuario())) {
			Collection<UsuarioGrupoSentinela> listaGrupos = CadastroFacade.obterUsuario(Integer.valueOf(localForm.getCodUsuario())).getListaUsuarioGrupoSentinela();
			if (listaGrupos != null){
				for (UsuarioGrupoSentinela usuarioGrupoSentinela : listaGrupos) {
					if (usuarioGrupoSentinela.getGrupoSentinela().getDescricaoSentinela().equalsIgnoreCase(gov.pr.celepar.abi.enumeration.GrupoSentinela.ADM_GERAL.getDescricao())) {
						localForm.setDesabilitaInstituicao("true");
						localForm.setIsAdmGeral("true");
					}
					if (usuarioGrupoSentinela.getGrupoSentinela().getDescricaoSentinela().equalsIgnoreCase(gov.pr.celepar.abi.enumeration.GrupoSentinela.OPE_ORG_ABI.getDescricao())) {
						localForm.setDesabilitaOrgao("false");
					}
				}
			}
		}
		
		
//		if (localForm.getIdSentinela() != null && localForm.getIdSentinela().trim().length() > 0) {
//			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
//			SentinelaParam[] sentinelaParamGrupos = sentinelaInterface.getGruposByUsuario(Long.valueOf(localForm.getIdSentinela()));
//			if (sentinelaParamGrupos != null){
//				for (SentinelaParam sentinelaParam : sentinelaParamGrupos) {
//					if (sentinelaParam.getNome().trim().equalsIgnoreCase(gov.pr.celepar.abi.enumeration.GrupoSentinela.ADM_GERAL.getDescricao())) {
//						localForm.setDesabilitaInstituicao("true");
//						localForm.setIsAdmGeral("true");
//					}
//					if (sentinelaParam.getNome().trim().equalsIgnoreCase(gov.pr.celepar.abi.enumeration.GrupoSentinela.OPE_ORG_ABI.getDescricao())) {
//						localForm.setDesabilitaOrgao("false");
//					}
//				}
//			}
//		}
	}

}