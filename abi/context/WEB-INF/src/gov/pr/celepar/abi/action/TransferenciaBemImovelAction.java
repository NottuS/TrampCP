package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.ItemComboDTO;
import gov.pr.celepar.abi.dto.ItemDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.facade.OperacaoFacade;
import gov.pr.celepar.abi.form.TransferenciaBemImovelForm;
import gov.pr.celepar.abi.pojo.AssinaturaTransferencia;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.ItemTransferencia;
import gov.pr.celepar.abi.pojo.Orgao;
import gov.pr.celepar.abi.pojo.StatusTermo;
import gov.pr.celepar.abi.pojo.Transferencia;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.pojo.UsuarioOrgao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.mensagem.Mensagem;
import gov.pr.celepar.framework.util.Data;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.Sessao;
import gov.pr.celepar.framework.util.Valores;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;
import gov.pr.celepar.sentinela.comunicacao.SentinelaParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TransferenciaBemImovelAction extends BaseDispatchAction {

	private static final String PILHA_FORM_SESSAO = "pilhaFormSessao";
	private boolean localizarBemImovel = false;
	
	/**
	 * Carregar a interface Inicial do Caso de Uso.<br>
	 * @author vanessak
	 * @since 11/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward carregarPgListTransferenciaBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {

		log.info("Método carregarPgListTransferenciaBemImovel processando...");
		setActionForward(mapping.findForward("pgConsultaTransferencia"));
		try {
			saveToken(request);

			localizarBemImovel = false;
			request.getSession().setAttribute("bemImovelSimplificado", null);
			request.getSession().setAttribute("execBuscaBemImovel", null);
			request.getSession().setAttribute("cedidoPara", null);
			request.getSession().setAttribute("listaPesquisaInstituicao", CadastroFacade.listarComboInstituicao());	
			request.getSession().setAttribute("listaSituacaoPesquisa", Util.htmlEncodeCollection(OperacaoFacade.listarStatusTermoCombo()));
			
			TransferenciaBemImovelForm localFormSession = this.desempilharFormSession(request);
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm)form;
			
			verificarGrupoUsuarioLogado(localForm, request);

			if (localFormSession != null) {
				localForm.setActionType(localFormSession.getActionType());
				localForm.setPesqExec(localFormSession.getPesqExec());
				localForm.setConNrTermo(localFormSession.getConNrTermo());
				localForm.setConNrBemImovel(localFormSession.getConNrBemImovel());
				localForm.setConCodBemImovel(localFormSession.getConCodBemImovel());
				localForm.setConProtocolo(localFormSession.getConProtocolo());
				localForm.setConSituacao(localFormSession.getConSituacao());
				localForm.setConOrgaoCessionario(localFormSession.getConOrgaoCessionario());
				localForm.setUf(localFormSession.getUf());
				localForm.setCodMunicipio(localFormSession.getCodMunicipio());
				localForm.setConInstituicao(localFormSession.getConInstituicao());
			}
			localForm.setActionType("pesquisar");
			if (localForm.getPesqExec() == null || localForm.getPesqExec().length() == 0) {
				request.getSession().setAttribute("pagina", null);
				localForm.setUf("PR");
			}
			//Verifica se usuário logado é operador de orgão 
			if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo())){
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
				if (usuario == null){
					throw new ApplicationException("AVISO.97");
				}
				if (usuario.getListaUsuarioOrgao() == null || usuario.getListaUsuarioOrgao().size() < 1){
					setActionForward(mapping.findForward("pgErroTransferenciaBI"));
					throw new ApplicationException("AVISO.96");
				}
				for (UsuarioOrgao orgao: usuario.getListaUsuarioOrgao()){
					usuario.getListaUsuarioOrgao().add(orgao);
				}
				localForm.setIndOperadorOrgao(1);
				request.getSession().setAttribute("orgaosPesquisar", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosUsuarioLogadoCombo(usuario, request)));
			}else{
				localForm.setIndOperadorOrgao(2);
				request.getSession().setAttribute("orgaosPesquisar", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Dominios.administracaoImovel.ADMINISTRACAO_DIRETA.getIndex(), SentinelaComunicacao.getInstance(request).getCodUsuario())));
			}
			//

			if (localForm.getPesqExec() != null && localForm.getPesqExec().equals("S")) {
				this.pesquisarTransferenciaBemImovel(mapping, localForm, request, response);
			}
			return getActionForward();
		} catch (ApplicationException appEx) {
			if (appEx.getMessage().equalsIgnoreCase(Mensagem.getInstance().getMessage("AVISO.96"))){
				setActionForward(mapping.findForward("pgErroTransferenciaBI"));
			} else {
				setActionForward(mapping.findForward("pgConsultaTransferencia"));
			}
			log.error(appEx);
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgConsultaTransferencia"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".carregarPgListTransferenciaBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
	}
	
	/**
	 * Carrega o combobox de orgãos de acordo com a Administração selecionada
	 * @param form
	 */
	public ActionForward carregarComboOrgaoPesquisa (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboOrgaoPesquisa processando...");
		try {
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;

			verificarGrupoUsuarioLogado(localForm, request);
			String instituicao = (StringUtils.isNotBlank(localForm.getConInstituicao()))?localForm.getConInstituicao():null;
			
			request.getSession().setAttribute("orgaosPesquisar", null);
			if (instituicao != null) {
				request.getSession().setAttribute("orgaosPesquisar", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Dominios.administracaoImovel.ADMINISTRACAO_DIRETA.getIndex(), Integer.valueOf(instituicao))));
			} else {
				request.getSession().setAttribute("orgaosPesquisar", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Dominios.administracaoImovel.ADMINISTRACAO_DIRETA.getIndex(), SentinelaComunicacao.getInstance(request).getCodUsuario())));
			}
			
			return mapping.findForward("pgComboOrgaoPesquisarTransferencia");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboOrgaoPesquisarTransferencia"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboOrgaoPesquisarTransferencia"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".carregarComboOrgaoPesquisa"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Carrega o combobox de orgãos de acordo com a Administração selecionada
	 * @param form
	 */
	public ActionForward carregarComboOrgao (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboOrgao processando...");
		try {
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);

			String instituicao = (StringUtils.isNotBlank(localForm.getInstituicao()))?localForm.getInstituicao():null;
			
			request.getSession().setAttribute("orgaosCessionarios", null);
			if (instituicao != null) {
				request.getSession().setAttribute("orgaosCessionarios", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Dominios.administracaoImovel.ADMINISTRACAO_DIRETA.getIndex(), Integer.valueOf(instituicao))));
			} else {
				request.getSession().setAttribute("orgaosCessionarios", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Dominios.administracaoImovel.ADMINISTRACAO_DIRETA.getIndex(), SentinelaComunicacao.getInstance(request).getCodUsuario())));
			}
			
			return mapping.findForward("pgComboOrgaoTransferencia");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboOrgaoTransferencia"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboOrgaoTransferencia"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".carregarComboOrgao"}, e, ApplicationException.ICON_ERRO);
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
	public ActionForward pesquisarTransferenciaBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {
		log.info("Método pesquisarTransferenciaBemImovel processando...");
		try{
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm)form;
			verificarGrupoUsuarioLogado(localForm, request);
			localForm.setPesqExec("S");

			if (localForm.getConNrBemImovel().trim().length() == 0) {
				request.getSession().setAttribute("bemImovelSimplificado", null);
			}
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setConCodBemImovel(aux.getCodBemImovel().toString());
			}
			
			Integer numPagina = (request.getParameter("indice") == null ? 1 : Integer.valueOf(request.getParameter("indice")));
			Integer totalRegistros = request.getParameter("totalRegistros") == null ? 0 : Integer.valueOf(request.getParameter("totalRegistros"));
			
			Transferencia objPesquisa = new Transferencia();
			objPesquisa.setCodTransferencia((localForm.getConNrTermo() == null || localForm.getConNrTermo().trim().length() == 0) ? null: Integer.valueOf(localForm.getConNrTermo().trim()));
			objPesquisa.setBemImovel(new BemImovel());
			objPesquisa.getBemImovel().setCodBemImovel((localForm.getConCodBemImovel() == null || localForm.getConCodBemImovel().trim().length() == 0) ? null: Integer.valueOf(localForm.getConCodBemImovel().trim()));
			objPesquisa.getBemImovel().setNrBemImovel((localForm.getConNrBemImovel() == null || localForm.getConNrBemImovel().trim().length() == 0) ? null: Integer.valueOf(localForm.getConNrBemImovel().trim()));
			objPesquisa.setInstituicao(new Instituicao());
			objPesquisa.getInstituicao().setCodInstituicao((localForm.getConInstituicao() == null || localForm.getConInstituicao().trim().length() == 0) ? null: Integer.valueOf(localForm.getConInstituicao().trim()));
			objPesquisa.setProtocolo(localForm.getConProtocolo() == null ? "" : localForm.getConProtocolo().trim());
			objPesquisa.setStatusTermo(new StatusTermo());
			objPesquisa.getStatusTermo().setCodStatusTermo((localForm.getConSituacao() == null || localForm.getConSituacao().trim().length() == 0)? null: Integer.valueOf(localForm.getConSituacao().trim()));
			objPesquisa.setOrgaoCessionario(new Orgao());
			objPesquisa.getOrgaoCessionario().setCodOrgao((localForm.getConOrgaoCessionario() == null || localForm.getConOrgaoCessionario().trim().length() == 0)? null: Integer.valueOf(localForm.getConOrgaoCessionario().trim()));
			objPesquisa.getBemImovel().setUf(localForm.getUf() == null ? "": localForm.getUf().trim());
			objPesquisa.getBemImovel().setCodMunicipio(localForm.getCodMunicipio() == null ? null: Integer.valueOf(localForm.getCodMunicipio().trim()));
			
			Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), numPagina, totalRegistros);

			Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
			
			pagina = OperacaoFacade.listarTransferencia(pagina, objPesquisa, usuario, localForm.getIndOperadorOrgao(), request);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.getSession().setAttribute("pagina", pagina);
			if (pagina.getTotalRegistros()==0){
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			return mapping.findForward("pgConsultaTransferencia");		
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgConsultaTransferencia"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgConsultaTransferencia"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".pesquisarTransferenciaBemImovel"}, e, ApplicationException.ICON_ERRO);
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
		request.getSession().setAttribute("bemImovelSimplificado", null);
		request.getSession().setAttribute("execBuscaBemImovel", null);
		request.getSession().setAttribute("cedidoPara", null);
		request.getSession().setAttribute("listItemTransferencia", null);
		request.getSession().setAttribute("listAssinatura", null);
		request.getSession().setAttribute("orgaosCessionarios", null);
		request.getSession().setAttribute("edificacoes", null);
		request.getSession().setAttribute("orgaosAssinatura", null);
		request.getSession().setAttribute("cargosAssinatura", null);
		request.getSession().setAttribute("nomesAssinatura", null);

		TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;
		localForm.limparCampos();
		localForm.setCodTransferencia(null);
		return this.carregarPgListTransferenciaBemImovel(mapping, form, request, response);
	}

	/**
	 * Obtem o retorno do UC Localizar Bem Imóvel Simplificado.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward retornoLocalizarBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		log.info("Método retornoLocalizarBemImovel processando...");
		try {
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;

			verificarGrupoUsuarioLogado(localForm, request);
			Integer codBemImovelSimpl = (request.getParameter("codBemImovelSimpl") == null ? 1 : Integer.valueOf(request.getParameter("codBemImovelSimpl")));
			Integer nrBemImovel = (request.getParameter("nrBemImovel") == null ? 1 : Integer.valueOf(request.getParameter("nrBemImovel")));
			Integer codInstituicao = (request.getParameter("codInstituicao") == null ? 1 : Integer.valueOf(request.getParameter("codInstituicao")));
			
			String camposPesquisaUCOrigem = request.getParameter("camposPesquisaUCOrigem") != null ? request.getParameter("camposPesquisaUCOrigem").toString() : "";		
			String[] valores = camposPesquisaUCOrigem.split(";");
			localForm.setConNrTermo(valores[0]);
			localForm.setConProtocolo(valores[1]);
			localForm.setConSituacao(valores[2]);
			localForm.setConOrgaoCessionario(valores[3]);
			localForm.setUf(valores[4]);
			localForm.setCodMunicipio(valores[5]);
			localForm.setActionType(valores[7]);
			localizarBemImovel = true;

			if (valores[6].equalsIgnoreCase("P"))  {
				localForm.setConCodBemImovel(String.valueOf(codBemImovelSimpl));
				localForm.setConNrBemImovel(String.valueOf(nrBemImovel));
				localForm.setConInstituicao(String.valueOf(codInstituicao));
				return this.carregarPgListTransferenciaBemImovel(mapping, form, request, response);
			} else {
				localForm.setCodBemImovel(String.valueOf(codBemImovelSimpl));
				localForm.setNrBemImovel(String.valueOf(nrBemImovel));
				localForm.setInstituicao(String.valueOf(codInstituicao));

				localForm.setConInstituicao(valores[8]);
				localForm.setConCodBemImovel(valores[9].trim());
				localForm.setConNrBemImovel(valores[10].trim());

				return this.carregarPgEditTransferenciaBemImovel(mapping, form, request, response);
			}
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".retornoLocalizarBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
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
	public ActionForward carregarPgEditTransferenciaBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {

		log.info("Método carregarPgEditTransferenciaBemImovel processando...");
		try {
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm)form;

			if (localForm.getActionType().equals("incluir")) {
				request.getSession().setAttribute("bemImovelSimplificado", null);
				request.getSession().setAttribute("execBuscaBemImovel", null);
				request.getSession().setAttribute("cedidoPara", null);
				request.getSession().setAttribute("listItemTransferencia", null);
				request.getSession().setAttribute("listAssinatura", null);
				request.getSession().setAttribute("orgaosCessionarios", null);
				request.getSession().setAttribute("edificacoes", null);
				request.getSession().setAttribute("orgaosAssinatura", null);
				request.getSession().setAttribute("cargosAssinatura", null);
				request.getSession().setAttribute("nomesAssinatura", null);
				request.getSession().setAttribute("listaInstituicao", null);
				if (!localizarBemImovel) {
					localForm.limparCampos();
					localForm.setCodTransferencia(null);
					verificarGrupoUsuarioLogado(localForm, request);
					request.getSession().setAttribute("orgaosCessionarios", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Dominios.administracaoImovel.ADMINISTRACAO_DIRETA.getIndex(), SentinelaComunicacao.getInstance(request).getCodUsuario())));
				}
			} else {
				verificarGrupoUsuarioLogado(localForm, request);
			}

			request.getSession().setAttribute("listaInstituicao", CadastroFacade.listarComboInstituicao());	
			if (localForm.getActionType().equals("gerarNova")) {
				SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
				Transferencia transferencia = OperacaoFacade.gerarNovaTransferencia(Integer.valueOf(localForm.getCodTransferencia()), sentinelaInterface.getCpf());
				this.addMessage("SUCESSO.42", new String[]{"Transferência", localForm.getCodTransferencia(), transferencia.getCodTransferencia().toString(), Dominios.statusTermo.RASCUNHO.getLabel()}, request);
				localForm.setActionType("alterar");
				localForm.setCodTransferencia(transferencia.getCodTransferencia().toString());
				populaForm(localForm, request);
			} else if (localForm.getCodTransferencia() != null && localForm.getCodTransferencia().length() > 0) {
				populaForm(localForm, request);
			}

			return mapping.findForward("pgEditTransferencia");				
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgConsultaTransferencia"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgConsultaTransferencia"));
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".carregarPgEditTransferenciaBemImovel"}, e, ApplicationException.ICON_ERRO);
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
	public ActionForward carregarPgViewTransferenciaBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {

		log.info("Método carregarPgViewTransferenciaBemImovel processando...");
		try {
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm)form;

			if (localForm.getCodTransferencia() != null && localForm.getCodTransferencia().length() > 0) {
				populaForm(localForm, request);
			}

			return mapping.findForward("pgViewTransferencia");				
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgViewTransferencia"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgViewTransferencia"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".carregarPgViewTransferenciaBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private void populaForm(ActionForm form, HttpServletRequest request) throws ApplicationException {
		try {
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm)form;
			verificarGrupoUsuarioLogado(localForm, request);

			Transferencia transferencia = OperacaoFacade.obterTransferenciaCompleto((StringUtils.isNotBlank(localForm.getCodTransferencia()))?Integer.valueOf(localForm.getCodTransferencia()):0);
			
			this.carregarRealizadoPor(request, form, transferencia);
			localForm.setCodBemImovel(transferencia.getBemImovel().getCodBemImovel().toString());
			localForm.setNrBemImovel(transferencia.getBemImovel().getNrBemImovel().toString());
			if (transferencia.getInstituicao() != null && transferencia.getInstituicao().getCodInstituicao() > 0) {
				localForm.setInstituicaoDesc(CadastroFacade.obterInstituicao(transferencia.getInstituicao().getCodInstituicao()).getSiglaDescricao());
				localForm.setInstituicao(transferencia.getInstituicao().getCodInstituicao().toString());
			}
			carregarListaItemTransferenciaAssinatura(localForm, request);
			
			localForm.setCodTransferencia(transferencia.getCodTransferencia().toString());
			if (transferencia.getStatusTermo() != null) {
				localForm.setStatus(Dominios.statusTermo.getStatusTermoByIndex(transferencia.getStatusTermo().getCodStatusTermo()).getLabel());
			} else {
				localForm.setStatus(" --- ");
			}
			localForm.setDtInicioVigencia(Data.formataData(transferencia.getDtInicioVigencia()));
			localForm.setDtFimVigencia(Data.formataData(transferencia.getDtFimVigencia()));
			localForm.setProtocolo(transferencia.getProtocolo());

			if (localForm.getActionType().equalsIgnoreCase("exibir") || localForm.getActionType().equalsIgnoreCase("excluir") || 
					localForm.getActionType().equalsIgnoreCase("revogDev")) {
				if (transferencia.getOrgaoCedente() != null) {
					localForm.setOrgaoCedente(transferencia.getOrgaoCedente().getSiglaDescricao());
				}
				localForm.setOrgaoCessionario(transferencia.getOrgaoCessionario().getSiglaDescricao());			
			} else {
				if (transferencia.getOrgaoCedente() != null) {
					localForm.setOrgaoCedente(transferencia.getOrgaoCedente().getCodOrgao().toString());
				}
				localForm.setOrgaoCessionario(transferencia.getOrgaoCessionario().getCodOrgao().toString());			
				this.carregarCamposRequest(localForm, request);
			}
			if (localForm.getActionType().equalsIgnoreCase("revogDev")) {
 				localForm.setTipoRevogDev("0");
				if (transferencia.getNrOficio() != null && transferencia.getMotivoRevogacao() != null) {
					if (transferencia.getNrOficio() > 0 && transferencia.getMotivoRevogacao().length() == 0) {
						localForm.setTipoRevogDev("2");
					} else if (transferencia.getNrOficio() == 0 && transferencia.getMotivoRevogacao().length() > 0) {
						localForm.setTipoRevogDev("1");
					}
					localForm.setMotivo(transferencia.getMotivoRevogacao());
					localForm.setNrOficio(transferencia.getNrOficio().toString());
				}
			} else {
				if (transferencia.getStatusTermo().getCodStatusTermo() == Dominios.statusTermo.DEVOLVIDO.getIndex() ||
					transferencia.getStatusTermo().getCodStatusTermo() == Dominios.statusTermo.REVOGADO.getIndex()) {
					if (transferencia.getStatusTermo().getCodStatusTermo() == Dominios.statusTermo.DEVOLVIDO.getIndex()) {
						localForm.setTipoRevogDevDesc("Devolução");
					} else {
						localForm.setTipoRevogDevDesc("Revogação");
					}
					localForm.setMotivo(transferencia.getMotivoRevogacao());
					if (transferencia.getNrOficio() != null) {
						localForm.setNrOficio(transferencia.getNrOficio().toString());
					}
				}
			}
			localForm.setCodStatus(String.valueOf(transferencia.getStatusTermo().getCodStatusTermo()));
			if (transferencia.getStatusTermo().getCodStatusTermo().equals(Integer.valueOf(1))) {
				localForm.setTextoDocInformacao(transferencia.getTextoDocInformacao());
				Pagina pagina = new Pagina(null, null, null);
				request.getSession().setAttribute("listAssinaturaDoc", OperacaoFacade.listarAssinaturaDocTransferencia(pagina, transferencia));
			}
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".populaForm"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private void carregarRealizadoPor(HttpServletRequest request, ActionForm form, Transferencia transferencia) throws ApplicationException {
		try {
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm)form;
			SentinelaParam sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByCPF(transferencia.getCpfResponsavelInclusao());
			String aux = "";
			if (sentinelaParam != null) {
				aux = "Inclusão realizada por ";
				aux = aux.concat(sentinelaParam.getNome().trim());
				aux = aux.concat(" em ");
				aux = aux.concat(Data.formataData(transferencia.getTsInclusao(),"dd/MM/yyyy HH:mm"));
				localForm.setIncluidoPor(aux);
			}
			if (transferencia.getCpfResponsavelAlteracao() != null && transferencia.getCpfResponsavelAlteracao().length() > 0){
				sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByCPF(transferencia.getCpfResponsavelAlteracao());
				if (sentinelaParam != null) {
					aux = "Alteração realizada por ";
					aux = aux.concat(sentinelaParam.getNome().trim());
					aux = aux.concat(" em ");
					aux = aux.concat(Data.formataData(transferencia.getTsAtualizacao(),"dd/MM/yyyy HH:mm"));
					localForm.setAlteradoPor(aux);
				}
			}
			if (transferencia.getCpfResponsavelExclusao() != null && transferencia.getCpfResponsavelExclusao().length() > 0){
				sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByCPF(transferencia.getCpfResponsavelExclusao());
				if (sentinelaParam != null) {
					aux = "Exclusão realizada por ";
					aux = aux.concat(sentinelaParam.getNome().trim());
					aux = aux.concat(" em ");
					aux = aux.concat(Data.formataData(transferencia.getTsExclusao(),"dd/MM/yyyy HH:mm"));
					localForm.setExcluidoPor(aux);
				}
			}
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".carregarRealizadoPor"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Carrega o combobox de edificações de acordo com o Bem Imóvel selecionado
	 * @param form
	 */
	public ActionForward carregarComboEditEdificacaoBemImovel (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboEditEdificacaoBemImovel processando...");
		try {
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			String param = (StringUtils.isNotBlank(localForm.getCodBemImovel()))?localForm.getCodBemImovel():null;
			
			if (param != null){
				request.getSession().setAttribute("edificacoes", Util.htmlEncodeCollection(OperacaoFacade.listarEdificacaoComboParaOperacoes(Integer.valueOf(param))));
			} else {
				request.getSession().setAttribute("edificacoes", null);
			}

			return mapping.findForward("pgComboEditEdificacaoBemImovelTransferencia");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboEditEdificacaoBemImovelTransferencia"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboEditEdificacaoBemImovelTransferencia"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".carregarComboEditEdificacaoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Realiza o cálculo de Transferência de % e metros quadrados.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public ActionForward calcularTransferenciaBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método calcularTransferenciaBemImovel processando...");

		setActionForward(mapping.findForward("pgEditAreaTransferenciaBemImovel"));
		try {
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);

			String codBemImovel = "0";
			if (localForm.getCodTransferencia() != null && localForm.getCodTransferencia().length() > 0) {
				codBemImovel = OperacaoFacade.obterTransferenciaCompleto(Integer.valueOf(localForm.getCodTransferencia())).getBemImovel().getCodBemImovel().toString();
			} else {
				if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
					BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
					localForm.setCodBemImovel(aux.getCodBemImovel().toString());
				}
				codBemImovel = localForm.getCodBemImovel();
			}
			
			ItemDTO obj = OperacaoFacade.calcularPercentualMetros(localForm.getTipoTransferencia(), localForm.getTransferenciaMetros(), 
					localForm.getTransferenciaPercentual(), "Transferência", localForm.getEdificacao(), codBemImovel);
			
			localForm.setTransferenciaMetros(Valores.formatarParaDecimal(obj.getMetros(), 2));
			localForm.setTransferenciaPercentual(Valores.formatarParaDecimal(obj.getPercentual(), 5));

		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".calcularTransferencia"}, e, ApplicationException.ICON_ERRO);
		} 
		return getActionForward();
    }

	/**
	 * Carrega o combobox de orgãos de acordo com os órgãos envolvidos.
	 * @param form
	 */
	public ActionForward carregarComboOrgaoAssinatura (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboOrgaoAssinatura processando...");
		try {
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			Collection<ItemComboDTO> ret = montaComboOrgaoTransferenciaAssinatura(localForm, request);
			if (ret != null && ret.size() > 0) {
				request.getSession().setAttribute("orgaosAssinatura", Util.htmlEncodeCollection(ret));
			} else {
				request.getSession().setAttribute("orgaosAssinatura", null);
			}
			
			return mapping.findForward("pgComboOrgaoTransferenciaAssinatura");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboOrgaoTransferenciaAssinatura"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboOrgaoTransferenciaAssinatura"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".carregarComboOrgaoAssinatura"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private Collection<ItemComboDTO> montaComboOrgaoTransferenciaAssinatura(TransferenciaBemImovelForm localForm, HttpServletRequest request) throws ApplicationException {
		try {
			Collection<ItemComboDTO> listResult = new ArrayList<ItemComboDTO>(); 

			verificarGrupoUsuarioLogado(localForm, request);

			BemImovel bemImovel = CadastroFacade.obterBemImovelSimplificado(Integer.valueOf(localForm.getCodBemImovel()));
			
			//órgão responsável
			Orgao orgao1 = null;
			if (localForm.getOrgaoCessionario() != null && !localForm.getOrgaoCessionario().trim().isEmpty()) {
				orgao1 = CadastroFacade.obterOrgao(Integer.valueOf(localForm.getOrgaoCessionario().trim()));
			}

			//órgão proprietário
			Instituicao instituicao = null;
			if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())) {
				instituicao = CadastroFacade.obterInstituicao(Integer.valueOf(localForm.getInstituicao()));
			} else {
				instituicao = CadastroFacade.obterInstituicaoUsuario(SentinelaComunicacao.getInstance(request).getCodUsuario());
			}

			Orgao orgao2 = bemImovel.getOrgao();
			Collection<ItemComboDTO> list = null;
			if (bemImovel.getAdministracao().compareTo(Dominios.administracaoImovel.ADMINISTRACAO_DIRETA.getIndex()) == 0) {
				list = CadastroFacade.listarOrgaosAssinaturaComboPorTipoAdmEInstituicao(Dominios.administracaoImovel.ADMINISTRACAO_DIRETA.getIndex(), instituicao.getCodInstituicao());
			}
			
			if (orgao1 != null || orgao2 != null || (list != null && list.size() > 0)) {
				Collection<Orgao> lista = new ArrayList<Orgao>();
				if (orgao1 != null)
					lista.add(orgao1);
				if (orgao2 != null)
					lista.add(orgao2);
				if (list != null && list.size() > 0) {
					for (ItemComboDTO item : list) {
						listResult.add(item);
					}
				}
				
				for (Orgao orgao : lista) {
					ItemComboDTO obj = new ItemComboDTO();
					obj.setCodigo(String.valueOf(orgao.getCodOrgao()));
					obj.setDescricao(orgao.getSigla().concat( " - ").concat( orgao.getDescricao()));
					listResult.add(obj);
				}
			}
			return listResult;
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".montaComboOrgaoTransferenciaAssinatura"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Carrega o combobox de cargo de acordo com o Órgão da Assinatura selecionado
	 * @param form
	 */
	public ActionForward carregarComboCargoAssinatura (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboCargoAssinatura processando...");
		try {
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);

			String param = (StringUtils.isNotBlank(localForm.getOrgaoAssinatura()))?localForm.getOrgaoAssinatura():null;
			
			if (param != null){
				request.getSession().setAttribute("cargosAssinatura", Util.htmlEncodeCollection(CadastroFacade.listarComboCargoAssinaturaByInstituicao(Integer.valueOf(param), Integer.valueOf(localForm.getInstituicao()))));
			} else {
				request.getSession().setAttribute("cargosAssinatura", null);
			}

			return mapping.findForward("pgComboCargoTransferenciaAssinatura");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboCargoTransferenciaAssinatura"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboCargoTransferenciaAssinatura"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".carregarComboCargoAssinatura"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Carrega o combobox de Nome de acordo com o Cargo e Órgão da Assinatura selecionados
	 * @param form
	 */
	public ActionForward carregarComboNomeAssinatura (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboNomeAssinatura processando...");
		try {
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);

			String orgao = (StringUtils.isNotBlank(localForm.getOrgaoAssinatura()))?localForm.getOrgaoAssinatura():null;
			String cargo = (StringUtils.isNotBlank(localForm.getCargoAssinatura()))?localForm.getCargoAssinatura():null;
			String instituicao = (StringUtils.isNotBlank(localForm.getInstituicao()))?localForm.getInstituicao():null;
			
			if (orgao != null && cargo != null){
				request.getSession().setAttribute("nomesAssinatura", Util.htmlEncodeCollection(CadastroFacade.listarNomeAssinaturaCombo(Integer.valueOf(orgao), Integer.valueOf(cargo), Integer.valueOf(instituicao))));
			} else {
				request.getSession().setAttribute("nomesAssinatura", null);
			}

			return mapping.findForward("pgComboNomeTransferenciaAssinatura");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboNomeTransferenciaAssinatura"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboNomeTransferenciaAssinatura"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".carregarComboNomeAssinatura"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Realiza o encaminhamento necessário para salvar a Transferência.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward salvarTransferenciaBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método salvarTransferenciaBemImovel processando...");
		try	{
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;
			
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	

			// Aciona a validação do Form
			validaDadosForm(localForm, null, request);
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			
			Transferencia transferencia = new Transferencia();
			if (localForm.getCodTransferencia() != null && localForm.getCodTransferencia().length() > 0) {
				transferencia = OperacaoFacade.obterTransferenciaCompleto(Integer.valueOf(localForm.getCodTransferencia()));
			} else {
				transferencia.setBemImovel(CadastroFacade.obterBemImovelSimplificado(Integer.valueOf(localForm.getCodBemImovel())));
			}
			if (localForm.getCodTransferencia() != null && localForm.getCodTransferencia().length() > 0) {
				this.populaPojoRegistro(transferencia, "A", request);
			} else {
				this.populaPojoRegistro(transferencia, "I", request);
			}
			
			this.verificarTransferenciaByBemImovelStatusTermo(localForm.getCodTransferencia(), transferencia, "I");
			
			this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()));

			this.populaPojo(transferencia, localForm);

			transferencia = OperacaoFacade.salvarTransferencia(transferencia);
			
			saveToken(request);
			localForm.limparCampos();
			this.addMessage("SUCESSO.40", new String[]{"Transferência", transferencia.getCodTransferencia().toString(), ""}, request);	
			return this.carregarPgListTransferenciaBemImovel(mapping, form, request, response);
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditTransferencia"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditTransferencia"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".salvarTransferenciaBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	private void verificarCessaoTotalBemImovel(Integer codBemImovel) throws ApplicationException {
		CessaoDeUso cessaoDeUso = new CessaoDeUso();
		cessaoDeUso.setCodCessaoDeUso(Integer.valueOf(0));
		cessaoDeUso.setBemImovel(new BemImovel());
		cessaoDeUso.getBemImovel().setCodBemImovel(codBemImovel);
		String result = OperacaoFacade.verificarCessaoTotalBemImovel(cessaoDeUso);
		if (result != null && result.length() > 0) {
			throw new ApplicationException("AVISO.51",  new String[]{result}, ApplicationException.ICON_AVISO);
		}
		
	}

	// Verifica se já existe com mesmo Bem Imóvel com o Status Termo Rascunho
	private void verificarTransferenciaByBemImovelStatusTermo(String codTransferencia, Transferencia transferencia, String operacao) throws ApplicationException {
		if (codTransferencia == null || codTransferencia.trim().isEmpty()) {
			Collection<Transferencia> listDuplicidade = OperacaoFacade.verificarTransferenciaByBemImovelStatusTermo(transferencia); 
			Transferencia transferenciaAux = transferencia;
			boolean mesmoBI = false;
			if (listDuplicidade.size() > 0) {
				boolean rasc = false;
				String status = "";
				for (Transferencia transferenciaDB : listDuplicidade) {
					if (Dominios.statusTermo.RASCUNHO.getIndex() == transferenciaDB.getStatusTermo().getCodStatusTermo()) {
						rasc = true;
					}
					status = status.concat(Dominios.statusTermo.getStatusTermoByIndex(transferenciaDB.getStatusTermo().getCodStatusTermo()).getLabel());
					status = status.concat(" - ");
					if (!operacao.equalsIgnoreCase("I")) {
						if (transferenciaDB.getBemImovel().equals(transferenciaAux.getBemImovel())) {
							mesmoBI = true;
						}
					}
				}
				if (status.trim().length() > 2) {
					status = status.substring(0, status.length() - 3);
				}
				if (operacao.equalsIgnoreCase("I")) {
					if (rasc) {
						throw new ApplicationException("AVISO.58",  new String[]{"Transferência", status}, ApplicationException.ICON_AVISO);
					} else {
						throw new ApplicationException("AVISO.66",  new String[]{"Transferência", status}, ApplicationException.ICON_AVISO);
					}
				} else {
					if (mesmoBI) {
						if (rasc) {
							throw new ApplicationException("AVISO.58",  new String[]{"Transferência", status}, ApplicationException.ICON_AVISO);
						} else {
							throw new ApplicationException("AVISO.66",  new String[]{"Transferência", status}, ApplicationException.ICON_AVISO);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Realiza o encaminhamento necessário para alterar a Transferência.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward alterarTransferenciaBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método alterarTransferenciaBemImovel processando...");
		try	{			
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;
			
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	

			// Aciona a validação do Form
			validaDadosForm(localForm, null, request);
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			
			Transferencia transferencia = OperacaoFacade.obterTransferenciaCompleto(Integer.valueOf(localForm.getCodTransferencia()));

			this.populaPojoRegistro(transferencia, "A", request);

			this.verificarTransferenciaByBemImovelStatusTermo(localForm.getCodTransferencia(), transferencia, "A");

			this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			
			this.populaPojo(transferencia, localForm);

			transferencia = OperacaoFacade.salvarTransferencia(transferencia);
			
			saveToken(request);
			localForm.limparCampos();
			this.addMessage("SUCESSO.41", new String[]{"Transferência", transferencia.getCodTransferencia().toString(), transferencia.getStatusTermo().getDescricao(), ""}, request);	
			return this.carregarPgListTransferenciaBemImovel(mapping, form, request, response);

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditTransferencia"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditTransferencia"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".alterarTransferenciaBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	/**
	 * Realiza o encaminhamento necessário para alterar a Transferência.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward excluirTransferenciaBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método excluirTransferenciaBemImovel processando...");
		try	{			
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;
			
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	
			verificarGrupoUsuarioLogado(localForm, request);

			Transferencia transferencia = OperacaoFacade.obterTransferenciaCompleto(Integer.valueOf(localForm.getCodTransferencia()));

			this.populaPojoRegistro(transferencia, "E", request);
			String codTransferencia = transferencia.getCodTransferencia().toString();
			OperacaoFacade.excluirTransferencia(transferencia);
			saveToken(request);
			
			localForm.limparCampos();
			localForm.setCodTransferencia(null);
			this.addMessage("SUCESSO.39", new String[]{"Transferência", codTransferencia}, request);	
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgViewTransferencia"));
			populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgViewTransferencia"));
			populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".excluirTransferenciaBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
		return this.carregarPgListTransferenciaBemImovel(mapping, form, request, response);
	}

	private void validaDadosForm(TransferenciaBemImovelForm localForm, String escopo, HttpServletRequest request) throws ApplicationException {
		StringBuffer str = new StringBuffer();
		
		if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())) {
			if (localForm.getInstituicao() == null || localForm.getInstituicao().trim().length() == 0 || localForm.getInstituicao().trim().equals("0")) {
				str.append("Instituição");
			}
		} else {
			localForm.setInstituicao(CadastroFacade.obterInstituicaoUsuario(SentinelaComunicacao.getInstance(request).getCodUsuario()).getCodInstituicao().toString());
		}
		if ((localForm.getNrBemImovel() == null || localForm.getNrBemImovel().trim().length() == 0) &&
				(localForm.getCodBemImovel() == null || localForm.getCodBemImovel().trim().length() == 0)) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Bem Imóvel");
		}
		if (localForm.getOrgaoCessionario() == null || localForm.getOrgaoCessionario().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Órgão de Destino");
		}

		if (escopo != null && escopo.equalsIgnoreCase("Item")) {
			if (localForm.getTipoTransferencia() == null || localForm.getTipoTransferencia().trim().length() == 0) {
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Tipo de Transferência");
			}
			if (localForm.getCaracteristicas() == null || localForm.getCaracteristicas().trim().length() == 0) {
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Características");
			}
			if (localForm.getSituacaoDominial() == null || localForm.getSituacaoDominial().trim().length() == 0) {
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Situação Dominial");
			}
			if (localForm.getUtilizacao() == null || localForm.getUtilizacao().trim().length() == 0) {
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Utilização");
			}
		}

		if (escopo != null && escopo.equalsIgnoreCase("Assinatura")) {
			if (localForm.getOrgaoAssinatura() == null || localForm.getOrgaoAssinatura().trim().length() == 0) {
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Órgão da Assinatura");
			}

			if (localForm.getCargoAssinatura() == null || localForm.getCargoAssinatura().trim().length() == 0) {
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Cargo da Assinatura");
			}
			
			if (localForm.getNomeAssinatura() == null || localForm.getNomeAssinatura().trim().length() == 0) {
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Nome da Assinatura");
			}

			if (localForm.getOrdemAssinatura() == null || localForm.getOrdemAssinatura().trim().length() == 0) {
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Ordem");
			}
		}

		if (str.length() > 0) {
			str.append(".");
			throw new ApplicationException("AVISO.72", new String[]{str.toString()}, ApplicationException.ICON_AVISO);
		}

		Date dataAntiga = null;
		try {
			dataAntiga = Data.formataData("03/12/1889");
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar Data!"}, ApplicationException.ICON_AVISO);
		}
		if (localForm.getDtInicioVigencia() != null && localForm.getDtInicioVigencia().trim().length() > 0) {
			if (!Data.validarData(localForm.getDtInicioVigencia().trim())) {
				throw new ApplicationException("errors.date", new String[]{"A Data de Início de Vigência informada"}, ApplicationException.ICON_AVISO);
			}
			Date inicVig = null;
			try {
				inicVig = Data.formataData(localForm.getDtInicioVigencia().trim());
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar Data!"}, ApplicationException.ICON_AVISO);
			}
			if (Data.dataDiff(inicVig,dataAntiga) == 1) {
				throw new ApplicationException("errors.dataAntiga", new String[]{"A Data de Início de Vigência"}, ApplicationException.ICON_AVISO);
			}
		}

		if (localForm.getDtFimVigencia() != null && localForm.getDtFimVigencia().trim().length() > 0) {
			if (!Data.validarData(localForm.getDtFimVigencia().trim())) {
				throw new ApplicationException("errors.date", new String[]{"A Data de Fim de Vigência informada"}, ApplicationException.ICON_AVISO);
			}
			Date fimVig = null;
			try {
				fimVig = Data.formataData(localForm.getDtFimVigencia().trim());
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar Data!"}, ApplicationException.ICON_AVISO);
			}
			if (Data.dataDiff(fimVig,dataAntiga) == 1) {
				throw new ApplicationException("errors.dataAntiga", new String[]{"A Data de Fim de Vigência"}, ApplicationException.ICON_AVISO);
			}
			Date inicVig = null;
			try {
				inicVig = Data.formataData(localForm.getDtInicioVigencia().trim());
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar Data!"}, ApplicationException.ICON_AVISO);
			}
			if (Data.dataDiff(fimVig,inicVig) == 1) {
				throw new ApplicationException("mensagem.erro.3", new String[]{"Data de Fim de Vigência", "Data de Início de Vigência"}, ApplicationException.ICON_AVISO);
			}

		}
	}	

	private void validarRevogarDevolver(TransferenciaBemImovelForm localForm) throws ApplicationException {
		StringBuffer str = new StringBuffer();
		if (localForm.getTipoRevogDev().trim().length() == 0) {
			str.append("Tipo");
		}

		if (localForm.getTipoRevogDev().equals("1")) {
			if (localForm.getMotivo().trim().length() == 0) {
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Motivo");
			}
		}
		
		if (localForm.getTipoRevogDev().equals("2")) {
			if (localForm.getNrOficio().trim().length() == 0) {
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Nº do Ofício");
			}
		}

		if (str.length() > 0) {
			str.append(".");
			throw new ApplicationException("AVISO.72", new String[]{str.toString()}, ApplicationException.ICON_AVISO);
		}

	}

	private void populaPojo(Transferencia transferencia, TransferenciaBemImovelForm localForm) throws ApplicationException {
		try {
			BemImovel bemImovel = null;
			bemImovel = CadastroFacade.obterBemImovelSimplificado(Integer.valueOf(localForm.getCodBemImovel())); 
			transferencia.setBemImovel(bemImovel);
			transferencia.setInstituicao(CadastroFacade.obterInstituicao(Integer.valueOf(localForm.getInstituicao())));
			if (bemImovel.getOrgao() != null) {
				transferencia.setOrgaoCedente(bemImovel.getOrgao());
			}
			transferencia.setOrgaoCessionario(CadastroFacade.obterOrgao(Integer.valueOf(localForm.getOrgaoCessionario())));
			transferencia.setDtInicioVigencia(Data.formataData(localForm.getDtInicioVigencia()));
			transferencia.setDtFimVigencia(Data.formataData(localForm.getDtFimVigencia()));
			if (localForm.getProtocolo() != null && !localForm.getProtocolo().isEmpty()) {
				transferencia.setProtocolo(localForm.getProtocolo());
			}
			
			if (localForm.getActionType().equalsIgnoreCase("revogDev")) {
				transferencia.setMotivoRevogacao(localForm.getMotivo());
				transferencia.setNrOficio(Integer.valueOf(localForm.getNrOficio()));
			}
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".populaPojo"}, e, ApplicationException.ICON_ERRO);
		} 
	}
	
	private void populaPojoRegistro(Transferencia transferencia, String acao, HttpServletRequest request) throws ApplicationException {
		try {
			Date dataAtual = new Date();
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			
		    if ("I".equals(acao)) {
				transferencia.setTsInclusao(dataAtual);
				transferencia.setCpfResponsavelInclusao(sentinelaInterface.getCpf());
				transferencia.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.RASCUNHO.getIndex()));
			} else if ("A".equals(acao)) {
				transferencia.setTsAtualizacao(dataAtual);
				transferencia.setCpfResponsavelAlteracao(sentinelaInterface.getCpf());
			} else if ("E".equals(acao)) {
				transferencia.setTsExclusao(dataAtual);
				transferencia.setCpfResponsavelExclusao(sentinelaInterface.getCpf());
				transferencia.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.FINALIZADO.getIndex()));
			}
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".populaPojoRegistro"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private void carregarCamposRequest (ActionForm form, HttpServletRequest request) throws NumberFormatException, ApplicationException {
		try {
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm)form;
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}

			request.getSession().setAttribute("orgaosCessionarios", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Dominios.administracaoImovel.ADMINISTRACAO_DIRETA.getIndex(), Integer.valueOf(localForm.getInstituicao()))));
			request.getSession().setAttribute("edificacoes", Util.htmlEncodeCollection(OperacaoFacade.listarEdificacaoComboParaOperacoes(Integer.valueOf(localForm.getCodBemImovel()))));
			Collection<ItemComboDTO> ret = montaComboOrgaoTransferenciaAssinatura(localForm, request);
			if (ret != null && ret.size() > 0) {
				request.getSession().setAttribute("orgaosAssinatura", Util.htmlEncodeCollection(ret));
			} else {
				request.getSession().setAttribute("orgaosAssinatura", null);
			}
			if (localForm.getOrgaoAssinatura() != null && localForm.getOrgaoAssinatura().trim().length() > 0) {
				request.getSession().setAttribute("cargosAssinatura", Util.htmlEncodeCollection(CadastroFacade.listarComboCargoAssinaturaByInstituicao(Integer.valueOf(localForm.getOrgaoAssinatura()), Integer.valueOf(localForm.getInstituicao()))));
				if (localForm.getCargoAssinatura() != null && localForm.getCargoAssinatura().trim().length() > 0) {
					request.getSession().setAttribute("nomesAssinatura", Util.htmlEncodeCollection(CadastroFacade.listarNomeAssinaturaCombo(Integer.valueOf(localForm.getOrgaoAssinatura()), Integer.valueOf(localForm.getCargoAssinatura()), Integer.valueOf(localForm.getInstituicao()))));
				}
			}
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".carregarCamposRequest"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Realiza o encaminhamento necessário para adicionar um item a Transferência.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward adicionarItemTransferenciaBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método adicionarItemTransferenciaBemImovel processando...");
		try	{			
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;
			setActionForward(mapping.findForward("pgEditTransferencia"));

			// Aciona a validação do Form
			validaDadosForm(localForm, "Item", request);
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			
			Transferencia transferencia = new Transferencia();
			BemImovel bemImovel = CadastroFacade.obterBemImovelSimplificado(Integer.valueOf(localForm.getCodBemImovel()));
			if (localForm.getCodTransferencia() != null && localForm.getCodTransferencia().length() > 0) {
				transferencia = OperacaoFacade.obterTransferenciaCompleto(Integer.valueOf(localForm.getCodTransferencia()));
			} else {
				transferencia.setBemImovel(bemImovel);
			}
			if (localForm.getCodTransferencia() != null && localForm.getCodTransferencia().length() > 0) {
				this.populaPojoRegistro(transferencia, "A", request);
			} else {
				this.populaPojoRegistro(transferencia, "I", request);
			}

			this.verificarTransferenciaByBemImovelStatusTermo(localForm.getCodTransferencia(), transferencia, "A");

			this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			
			this.populaPojo(transferencia, localForm);

			transferencia = OperacaoFacade.salvarTransferencia(transferencia);

			ItemTransferencia itemTransferencia = new ItemTransferencia();
			itemTransferencia.setTipo(Integer.valueOf(localForm.getTipoTransferencia()));
			
			if (!localForm.getTipoTransferencia().equals("1")) {
				ItemDTO obj = OperacaoFacade.calcularPercentualMetros(localForm.getTipoTransferencia(), localForm.getTransferenciaMetros(), 
						localForm.getTransferenciaPercentual(), "Transferência", localForm.getEdificacao(), localForm.getCodBemImovel());
				itemTransferencia.setTransferenciaMetros(obj.getMetros());
				itemTransferencia.setTransferenciaPercentual(obj.getPercentual());
			} else if (localForm.getTipoTransferencia().equals("1")) {
				if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() != null) {
					itemTransferencia.setTransferenciaMetros(bemImovel.getAreaConstruida().add(bemImovel.getAreaTerreno()));
				} else if (bemImovel.getAreaConstruida() == null && bemImovel.getAreaTerreno() != null) {
					itemTransferencia.setTransferenciaMetros(bemImovel.getAreaTerreno());
				} else if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() == null) {
					itemTransferencia.setTransferenciaMetros(bemImovel.getAreaConstruida());
				}
				itemTransferencia.setTransferenciaPercentual(new BigDecimal(100));
			} 
			if (localForm.getTipoTransferencia().equals("2")) {
				itemTransferencia.setEdificacao(CadastroFacade.obterEdificacao(Integer.valueOf(localForm.getEdificacao())));
			}
			if (transferencia.getBemImovel().getSomenteTerreno().equalsIgnoreCase("S")) {
				itemTransferencia.setSomenteTerreno(Boolean.TRUE);
			} else {
				itemTransferencia.setSomenteTerreno(Boolean.FALSE);
			}
			itemTransferencia.setTransferencia(transferencia);
			if (localForm.getCaracteristicas().trim().length() > 0) {
				if (localForm.getCaracteristicas().trim().length() > 3000) {
					itemTransferencia.setCaracteristica(localForm.getCaracteristicas().trim().substring(0, 3000));
				} else {
					itemTransferencia.setCaracteristica(localForm.getCaracteristicas());
				}
			}
			if (localForm.getSituacaoDominial().trim().length() > 0) {
				if (localForm.getSituacaoDominial().trim().length() > 3000) {
					itemTransferencia.setSituacaoDominial(localForm.getSituacaoDominial().trim().substring(0, 3000));
				} else {
					itemTransferencia.setSituacaoDominial(localForm.getSituacaoDominial());
				}
			}
			if (localForm.getUtilizacao().trim().length() > 0) {
				if (localForm.getUtilizacao().trim().length() > 3000) {
					itemTransferencia.setUtilizacao(localForm.getUtilizacao().trim().substring(0, 3000));
				} else {
					itemTransferencia.setUtilizacao(localForm.getUtilizacao());
				}
			}
			if (localForm.getObservacao().trim().length() > 0) {
				if (localForm.getObservacao().trim().length() > 3000) {
					itemTransferencia.setObservacao(localForm.getObservacao().trim().substring(0, 3000));
				} else {
					itemTransferencia.setObservacao(localForm.getObservacao());
				}
			}
			boolean mesmoObjeto = false;
			// Verifica se já existe o mesmo item
			Collection<ItemTransferencia> listDuplicidadeAD = OperacaoFacade.verificarDuplicidadeItemTransferencia(itemTransferencia); 
			if (listDuplicidadeAD.size() > 0) {
				ItemTransferencia itemTransferenciaDB;
				for (Iterator<ItemTransferencia> iterator  = listDuplicidadeAD.iterator(); iterator.hasNext();) {
					itemTransferenciaDB = (ItemTransferencia) iterator .next();
					if ((itemTransferenciaDB.getEdificacao() != null && 
						itemTransferenciaDB.getEdificacao().getCodEdificacao().equals(itemTransferencia.getEdificacao().getCodEdificacao())) &&
						itemTransferenciaDB.getSomenteTerreno().equals(itemTransferencia.getSomenteTerreno())) {
						mesmoObjeto = true;
					}
				}
				if (mesmoObjeto) {
					throw new ApplicationException("AVISO.57", ApplicationException.ICON_AVISO);
				}
			}

			OperacaoFacade.salvarItemTransferencia(itemTransferencia);
			
			if (localForm.getCodTransferencia() != null && localForm.getCodTransferencia().length() > 0) {
				this.addMessage("SUCESSO.41", new String[]{"Transferência", transferencia.getCodTransferencia().toString(), transferencia.getStatusTermo().getDescricao(),
						Mensagem.getInstance().getMessage("SUCESSO.45")}, request);	
			} else {
				this.addMessage("SUCESSO.40", new String[]{"Transferência", transferencia.getCodTransferencia().toString(), Mensagem.getInstance().getMessage("SUCESSO.45")}, request);	
			}

			localForm.setCodTransferencia(transferencia.getCodTransferencia().toString());
			localForm.limparCamposItem();
			populaForm(localForm, request);

			return mapping.findForward("pgEditTransferencia");

		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".adicionarItemTransferenciaBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	/**
	 * Realiza o encaminhamento necessário para adicionar uma assinatura a Transferência.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward adicionarAssinaturaTransferenciaBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método adicionarAssinaturaTransferenciaBemImovel processando...");
		try	{			
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;

			// Aciona a validação do Form
			validaDadosForm(localForm, "assinatura", request);
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			
			Transferencia transferencia = new Transferencia();
			if (localForm.getCodTransferencia() != null && localForm.getCodTransferencia().length() > 0) {
				transferencia = OperacaoFacade.obterTransferenciaCompleto(Integer.valueOf(localForm.getCodTransferencia()));
			} else {
				transferencia.setBemImovel(CadastroFacade.obterBemImovelSimplificado(Integer.valueOf(localForm.getCodBemImovel())));
			}
			if (localForm.getCodTransferencia() != null && localForm.getCodTransferencia().length() > 0) {
				this.populaPojoRegistro(transferencia, "A", request);
			} else {
				this.populaPojoRegistro(transferencia, "I", request);
			}

			this.verificarTransferenciaByBemImovelStatusTermo(localForm.getCodTransferencia(), transferencia, "A");

			this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			
			this.populaPojo(transferencia, localForm);

			transferencia = OperacaoFacade.salvarTransferencia(transferencia);

			AssinaturaTransferencia assinaturaTransferencia = new AssinaturaTransferencia();
			assinaturaTransferencia.setTransferencia(transferencia);
			assinaturaTransferencia.setAssinatura(CadastroFacade.obterAssinatura(Integer.valueOf(localForm.getNomeAssinatura())));
			assinaturaTransferencia.setOrdem(Integer.valueOf(localForm.getOrdemAssinatura()));
			
			AssinaturaTransferencia assinaturaTransferenciaAux = assinaturaTransferencia;
			boolean mesmoObjeto = false;
			// Verifica se já existe com Transferencia | ordem e assinatura
			Collection<AssinaturaTransferencia> listDuplicidadeAD = OperacaoFacade.verificarDuplicidadeAssinaturaTransferencia(assinaturaTransferencia); 
			if (listDuplicidadeAD.size() > 0) {
				AssinaturaTransferencia assinaturaTransferenciaDB;
				for (Iterator<AssinaturaTransferencia> iterator  = listDuplicidadeAD.iterator(); iterator.hasNext();) {
					assinaturaTransferenciaDB = (AssinaturaTransferencia) iterator .next();
					if (assinaturaTransferenciaDB.getOrdem().equals(assinaturaTransferenciaAux.getOrdem())) {
						mesmoObjeto = true;
					}
				}
				if (mesmoObjeto) {
					throw new ApplicationException("AVISO.76", ApplicationException.ICON_AVISO);
				}
				mesmoObjeto = false;
				//verifica se é a mesma assinatura
				for (Iterator<AssinaturaTransferencia> iterator  = listDuplicidadeAD.iterator(); iterator.hasNext();) {
					assinaturaTransferenciaDB = (AssinaturaTransferencia) iterator .next();
					if (assinaturaTransferenciaDB.getAssinatura().getCodAssinatura().equals(assinaturaTransferenciaAux.getAssinatura().getCodAssinatura())) {
						mesmoObjeto = true;
					}
				}
				if (mesmoObjeto) {
					throw new ApplicationException("AVISO.55", ApplicationException.ICON_AVISO);
				}
				mesmoObjeto = false;
				//verifica se é do mesmo cargo
				for (Iterator<AssinaturaTransferencia> iterator  = listDuplicidadeAD.iterator(); iterator.hasNext();) {
					assinaturaTransferenciaDB = (AssinaturaTransferencia) iterator .next();
					if (assinaturaTransferenciaDB.getAssinatura().getCargoAssinatura().getCodCargoAssinatura().equals(assinaturaTransferenciaAux.getAssinatura().getCargoAssinatura().getCodCargoAssinatura())) {
						mesmoObjeto = true;
					}
				}
				if (mesmoObjeto) {
					throw new ApplicationException("AVISO.103", ApplicationException.ICON_AVISO);
				}
			}

			OperacaoFacade.salvarAssinaturaTransferencia(assinaturaTransferencia);
			
			if (localForm.getCodTransferencia() != null && localForm.getCodTransferencia().length() > 0) {
				this.addMessage("SUCESSO.41", new String[]{"Transferência", transferencia.getCodTransferencia().toString(), transferencia.getStatusTermo().getDescricao(),
						Mensagem.getInstance().getMessage("SUCESSO.43")}, request);	
			} else {
				this.addMessage("SUCESSO.40", new String[]{"Transferência", transferencia.getCodTransferencia().toString(), Mensagem.getInstance().getMessage("SUCESSO.43")}, request);	
			}

			localForm.setCodTransferencia(transferencia.getCodTransferencia().toString());
			localForm.limparCamposAssinatura();
			populaForm(localForm, request);

			return mapping.findForward("pgEditTransferencia");

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditTransferencia"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditTransferencia"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".adicionarAssinaturaTransferenciaBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	private void carregarListaItemTransferenciaAssinatura(ActionForm form, HttpServletRequest request) throws ApplicationException {
		try {
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm)form;
			String codTransferencia = localForm.getCodTransferencia();
			if (codTransferencia == null || codTransferencia.isEmpty() || codTransferencia.trim().length() < 1) {
				request.getSession().setAttribute("listItemTransferencia", null);
				request.getSession().setAttribute("listAssinatura", null);
			} else {
				Pagina pagina = new Pagina(null, null, null);
				request.getSession().setAttribute("listItemTransferencia", OperacaoFacade.listarItemTransferencia(pagina, Integer.valueOf(codTransferencia)));
				pagina = new Pagina(null, null, null);
				request.getSession().setAttribute("listAssinatura", OperacaoFacade.listarAssinaturaTransferencia(pagina, Integer.valueOf(codTransferencia)));
				
				ItemTransferencia itemTransferencia = new ItemTransferencia();
				itemTransferencia.setTransferencia(new Transferencia());
				itemTransferencia.getTransferencia().setCodTransferencia(Integer.valueOf(codTransferencia));
				Collection<ItemTransferencia> list = OperacaoFacade.verificarDuplicidadeItemTransferencia(itemTransferencia);
				for (ItemTransferencia item : list) {
					if (item.getTipo().compareTo(Integer.valueOf(1)) == 0) {
						localForm.setItemTotal(item.getTipo().toString());
					}
				}
				localForm.setQtdItens(String.valueOf(list.size()));
			}
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".carregarListaItemTransferenciaAssinatura"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Realiza o encaminhamento necessário para adicionar um item a Transferência.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward excluirItemTransferenciaBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método excluirItemTransferenciaBemImovel processando...");
		try	{			
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			
			Transferencia transferencia = OperacaoFacade.obterTransferenciaCompleto(Integer.valueOf(localForm.getCodTransferencia()));

			this.populaPojoRegistro(transferencia, "A", request);

			this.verificarTransferenciaByBemImovelStatusTermo(localForm.getCodTransferencia(), transferencia, "A");

			this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			
			this.populaPojo(transferencia, localForm);

			transferencia = OperacaoFacade.salvarTransferencia(transferencia);

			ItemTransferencia itemTransferencia = OperacaoFacade.obterItemTransferencia(Integer.valueOf(localForm.getCodItemTransferencia()));
			
			OperacaoFacade.excluirItemTransferencia(itemTransferencia);
			
			this.addMessage("SUCESSO.41", new String[]{"Transferência", transferencia.getCodTransferencia().toString(), transferencia.getStatusTermo().getDescricao(),
					Mensagem.getInstance().getMessage("SUCESSO.46")}, request);	

			localForm.setCodTransferencia(transferencia.getCodTransferencia().toString());
			localForm.limparCamposItem();
			localForm.setCodItemTransferencia(null);

			populaForm(localForm, request);

			return mapping.findForward("pgEditTransferencia");

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditTransferencia"));
			populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditTransferencia"));
			populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".excluirItemTransferenciaBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	/**
	 * Realiza o encaminhamento necessário para adicionar um item a Transferência.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward excluirAssinaturaTransferenciaBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método excluirAssinaturaTransferenciaBemImovel processando...");
		try	{			
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			
			Transferencia transferencia = OperacaoFacade.obterTransferenciaCompleto(Integer.valueOf(localForm.getCodTransferencia()));

			this.populaPojoRegistro(transferencia, "A", request);

			this.verificarTransferenciaByBemImovelStatusTermo(localForm.getCodTransferencia(), transferencia, "A");

			this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			
			this.populaPojo(transferencia, localForm);

			transferencia = OperacaoFacade.salvarTransferencia(transferencia);

			AssinaturaTransferencia assinaturaTransferencia = OperacaoFacade.obterAssinaturaTransferencia(Integer.valueOf(localForm.getCodAssinaturaTransferencia()));
			
			OperacaoFacade.excluirAssinaturaTransferencia(assinaturaTransferencia);
			
			this.addMessage("SUCESSO.41", new String[]{"Transferência", transferencia.getCodTransferencia().toString(), transferencia.getStatusTermo().getDescricao(),
					Mensagem.getInstance().getMessage("SUCESSO.44")}, request);	

			localForm.setCodTransferencia(transferencia.getCodTransferencia().toString());
			localForm.limparCamposAssinatura();
			localForm.setCodAssinaturaTransferencia(null);
			
			populaForm(localForm, request);
			
			return mapping.findForward("pgEditTransferencia");

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditTransferencia"));
			populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditTransferencia"));
			populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".excluirAssinaturaTransferenciaBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 

	}

	/**
	 * Realiza o encaminhamento necessário para alterar a Transferência.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward revogDevTransferenciaBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método revogDevTransferenciaBemImovel processando...");
		try	{			
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;
			
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	
			verificarGrupoUsuarioLogado(localForm, request);

			// Aciona a validação do Form
			validarRevogarDevolver(localForm);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			
			Transferencia transferencia = OperacaoFacade.obterTransferenciaCompleto(Integer.valueOf(localForm.getCodTransferencia()));

			if (localForm.getTipoRevogDev().equals("1")) {
				transferencia.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.REVOGADO.getIndex()));
				transferencia.setMotivoRevogacao(localForm.getMotivo());
			} else if (localForm.getTipoRevogDev().equals("2")) {
				transferencia.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.DEVOLVIDO.getIndex()));
				transferencia.setNrOficio(Integer.valueOf(localForm.getNrOficio()));
			}
			transferencia.setDtFimVigencia(new Date());
			this.populaPojoRegistro(transferencia, "A", request);

			transferencia = OperacaoFacade.salvarTransferencia(transferencia);
			
			saveToken(request);
			if (localForm.getTipoRevogDev().equals("1")) {
				this.addMessage("SUCESSO.47", new String[]{"Revogação", "Transferência", transferencia.getCodTransferencia().toString()}, request);	
			} else if (localForm.getTipoRevogDev().equals("2")) {
				this.addMessage("SUCESSO.47", new String[]{"Devolução", "Transferência", transferencia.getCodTransferencia().toString()}, request);	
			}
			localForm.limparCampos();
			return this.carregarPgListTransferenciaBemImovel(mapping, form, request, response);

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgViewTransferencia"));
			populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgViewTransferencia"));
			populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{TransferenciaBemImovelAction.class.getSimpleName()+".revogDevTransferenciaBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	// UC GERAR TERMO
	
	/**
	 * Redireciona para o UCS GerarTermoTransferenciaBemImovel.<br>
	 * @author ginaalmeida
	 * @since 29/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward redirecionarUCSGerarTermo (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException{
		try {
			
			TransferenciaBemImovelForm localForm = (TransferenciaBemImovelForm) form;
			localForm.setUcsDestino("ucsGerarTermoTransferenciaBemImovel");
			localForm.setUcsChamador("ucsTransferenciaBemImovel");
			
			request.setAttribute("ucsChamador", "ucsTransferenciaBemImovel");
			request.setAttribute("codTransferencia", localForm.getCodTransferencia());
			
			return acionarOutroCasoUso(mapping, localForm, request, response);
					
		} catch (ApplicationException ae) {
			throw ae;
		} catch (Exception e) {
			setActionForward(carregarPgListTransferenciaBemImovel(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{this.getClass().getSimpleName()+".redirecionarUCSGerarTermo()"}, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Aciona outro caso de uso, redirecionando o fluxo pra outra Action armazenando na sessao o form submetido e o caso de uso de retorno.<br>
	 * Copiado da BaseAction pois o caso de uso extende de outra Action.<BR>
	 * @author Daniel
	 * @since 19/03/2008
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 * @reuso Adaptado do Sistema GMS
	 */	
	public ActionForward acionarOutroCasoUso(ActionMapping mapping,	ActionForm form, HttpServletRequest request, HttpServletResponse response)
											throws ApplicationException {
		try{
			log.info("Iniciando o redirecionamento pra outra Action....");									
						
			TransferenciaBemImovelForm frm = (TransferenciaBemImovelForm) form;		
			frm.setUcsRetorno(frm.getUcsChamador()); 
			
			Transferencia transf = OperacaoFacade.obterTransferenciaCompleto(Integer.valueOf(frm.getCodTransferencia()));
			if(Integer.valueOf(Dominios.statusTermo.RASCUNHO.getIndex()).equals(transf.getStatusTermo().getCodStatusTermo())){
				this.armazenarFormSessao(request, frm);
			}
						
			return mapping.findForward(frm.getUcsDestino());
		
		}catch (ApplicationException ae) {						
			throw ae;
		}catch (Exception e) {
			log.error(e.getMessage(),e);			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".acionarOutroCasoUso()"}, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Armazenar form passado como parametro na sessao, verifica tambem se ha caso de uso de retorno.<br>
	 * Copiado da BaseAction pois o caso de uso extende de outra Action.<BR>
	 * @author Daniel
	 * @since 19/03/2009
	 * @param form : BaseForm
	 * @param request : HttpServletRequest
	 * @throws ApplicationException
	 * @reuso Adaptado do Sistema GMS
	 */
	public void armazenarFormSessao(HttpServletRequest request, TransferenciaBemImovelForm form) throws ApplicationException{

		try{
			Stack<TransferenciaBemImovelForm> pilhaForm = (Stack<TransferenciaBemImovelForm>)Sessao.obterAtributoSessao(request, PILHA_FORM_SESSAO); 
			
			if(pilhaForm == null){
				pilhaForm = new Stack<TransferenciaBemImovelForm>();
			}

			//Evitar que se carrege o mesmo form na pilha atraves do refresh
			TransferenciaBemImovelForm ultimoPilha = this.obterFormSessao(request);
			
			if(ultimoPilha == null || (ultimoPilha.getClass() != form.getClass())){
				pilhaForm.push(form);
			}			
			
			Sessao.adicionarAtributoSessao(request, PILHA_FORM_SESSAO, pilhaForm);
			
		}catch (ApplicationException ae) {			
			throw ae;
		}catch (Exception e) {			
			log.error(e.getMessage(),e);			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".armazenarFormSessao()"}, e, ApplicationException.ICON_ERRO);
		}
			
	}
	
	/**
	 * Obtem o primeiro form da pilha de forms armazenada na sessao do usuario, caso a pilha esteja vazia retira o atributo da sessao.<br>
	 * Copiado da BaseAction pois o caso de uso extende de outra Action.<BR>
	 * @author Daniel
	 * @since 19/03/2009
	 * @param request : HttpServletRequest
	 * @return BaseForm
	 * @throws ApplicationException
	 * @reuso Adaptado do Sistema GMS
	 */
	public TransferenciaBemImovelForm obterFormSessao(HttpServletRequest request) throws ApplicationException{

		try{
			Stack<TransferenciaBemImovelForm> pilhaForm = (Stack<TransferenciaBemImovelForm>)Sessao.obterAtributoSessao(request, PILHA_FORM_SESSAO); 
			if(pilhaForm == null){
				return null;
			}
			
			if(pilhaForm.isEmpty()){
				Sessao.removerAtributoDaSessao(request, PILHA_FORM_SESSAO);
				return null;
			}
			TransferenciaBemImovelForm bf = pilhaForm.pop();
			armazenarFormSessao(request, bf);
			return bf;
			
		}catch (Exception e) {			
			log.error(e.getMessage(),e);			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".obterFormSessao()"}, e, ApplicationException.ICON_ERRO);
		}
			
	}
	
	public TransferenciaBemImovelForm desempilharFormSession(HttpServletRequest request) throws ApplicationException{
		try{

			Object obj = Sessao.obterAtributoSessao(request, PILHA_FORM_SESSAO);
			if (obj == null) {
				return null;
			} 

			Stack<TransferenciaBemImovelForm> pilhaForm = (Stack<TransferenciaBemImovelForm>)obj; 
			if(pilhaForm == null){
				return null;
			}

			TransferenciaBemImovelForm manterSuperForm = null;
			if (pilhaForm.size() > 0) {
				if (pilhaForm.get(0) instanceof TransferenciaBemImovelForm) {
					manterSuperForm = pilhaForm.pop();
					manterSuperForm.setCodTransferencia("");
				}
			}
			Sessao.removerAtributoDaSessao(request, PILHA_FORM_SESSAO);
			
			return manterSuperForm;
		}catch (Exception e) {
			log.error(e.getMessage(),e);			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".desempilharFormSession()"}, e, ApplicationException.ICON_ERRO);
		}	
	}	

	private void verificarGrupoUsuarioLogado(TransferenciaBemImovelForm localForm, HttpServletRequest request) throws ApplicationException {
		boolean result = CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo());
		localForm.setIsGpAdmGeralUsuarioLogado("N");
		if (result) {
			localForm.setIsGpAdmGeralUsuarioLogado("S");
		} else {
			String codInstituicao = CadastroFacade.obterInstituicaoUsuario(SentinelaComunicacao.getInstance(request).getCodUsuario()).getCodInstituicao().toString();
			localForm.setConInstituicao(codInstituicao);
			localForm.setInstituicao(codInstituicao);
		}
	}
	
	
}