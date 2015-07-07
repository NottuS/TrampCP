package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.ItemComboDTO;
import gov.pr.celepar.abi.dto.ItemDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.facade.OperacaoFacade;
import gov.pr.celepar.abi.form.CessaoDeUsoBemImovelForm;
import gov.pr.celepar.abi.pojo.AssinaturaCessaoDeUso;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.ItemCessaoDeUso;
import gov.pr.celepar.abi.pojo.LeiBemImovel;
import gov.pr.celepar.abi.pojo.Orgao;
import gov.pr.celepar.abi.pojo.StatusTermo;
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

public class CessaoDeUsoBemImovelAction extends BaseDispatchAction {

	private static final String PILHA_FORM_SESSAO = "pilhaFormSessao";
	private static final Integer EXCETO_TIPO_LEI = Integer.valueOf(4);
	private static final Integer TIPO_LEI = Integer.valueOf(2);
	private boolean localizarBemImovel = false;
	private boolean localizarLeiBemImovel = false;
	
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
	public ActionForward carregarPgListCessaoDeUsoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {

		log.info("Método carregarPgListCessaoDeUsoBemImovel processando...");

		try {
			saveToken(request);

			localizarBemImovel = false;
			localizarLeiBemImovel = false;
			request.getSession().setAttribute("bemImovelSimplificado", null);
			request.getSession().setAttribute("execBuscaBemImovel", null);
			request.getSession().setAttribute("cedidoPara", null);
			request.getSession().setAttribute("listaSituacaoPesquisa", Util.htmlEncodeCollection(OperacaoFacade.listarStatusTermoCombo()));
			request.getSession().setAttribute("orgaosPesquisar", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Dominios.administracaoImovel.ADMINISTRACAO_TERCEIROS.getIndex(), SentinelaComunicacao.getInstance(request).getCodUsuario())));
			request.getSession().setAttribute("listaPesquisaInstituicao", CadastroFacade.listarComboInstituicao());	

			CessaoDeUsoBemImovelForm localFormSession = this.desempilharFormSession(request);
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm)form;

			verificarGrupoUsuarioLogado(localForm, request);
			
			if (localFormSession != null) {
				localForm.setActionType(localFormSession.getActionType());
				localForm.setPesqExec(localFormSession.getPesqExec());
				localForm.setConNrTermo(localFormSession.getConNrTermo());
				localForm.setConInstituicao(localFormSession.getConInstituicao());
				localForm.setConNrBemImovel(localFormSession.getConNrBemImovel());
				localForm.setConCodBemImovel(localFormSession.getConCodBemImovel());
				localForm.setConProtocolo(localFormSession.getConProtocolo());
				localForm.setConSituacao(localFormSession.getConSituacao());
				localForm.setConOrgaoCessionario(localFormSession.getConOrgaoCessionario());
				localForm.setUf(localFormSession.getUf());
				localForm.setCodMunicipio(localFormSession.getCodMunicipio());
				localForm.setConDtInicioVigencia(localFormSession.getConDtInicioVigencia());
				localForm.setConDtFimVigencia(localFormSession.getConDtFimVigencia());
			}
			localForm.setActionType("pesquisar");
			if (localForm.getPesqExec() != null && localForm.getPesqExec().equals("S")) {
				this.pesquisarCessaoDeUsoBemImovel(mapping, localForm, request, response);
			} else {
				request.getSession().setAttribute("pagina", null);
				localForm.setUf("PR");
			}
			return mapping.findForward("pgConsultaCessaoDeUso");
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgConsultaCessaoDeUso"));
			log.error(appEx);
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgConsultaCessaoDeUso"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".carregarPgListCessaoDeUsoBemImovel"}, e, ApplicationException.ICON_ERRO);
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
	public ActionForward pesquisarCessaoDeUsoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {
		log.info("Método pesquisarCessaoDeUsoBemImovel processando...");
		try{
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm)form;
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
			
			CessaoDeUso objPesquisa = new CessaoDeUso();
			objPesquisa.setCodCessaoDeUso((localForm.getConNrTermo() == null || localForm.getConNrTermo().trim().length() == 0) ? null: Integer.valueOf(localForm.getConNrTermo().trim()));
			objPesquisa.setBemImovel(new BemImovel());
			objPesquisa.getBemImovel().setNrBemImovel((localForm.getConNrBemImovel() == null || localForm.getConNrBemImovel().trim().length() == 0) ? null: Integer.valueOf(localForm.getConNrBemImovel().trim()));
			objPesquisa.getBemImovel().setCodBemImovel((localForm.getConCodBemImovel() == null || localForm.getConCodBemImovel().trim().length() == 0) ? null: Integer.valueOf(localForm.getConCodBemImovel().trim()));
			objPesquisa.setInstituicao(new Instituicao());
			objPesquisa.getInstituicao().setCodInstituicao((localForm.getConInstituicao() == null || localForm.getConInstituicao().trim().length() == 0) ? null: Integer.valueOf(localForm.getConInstituicao().trim()));
			objPesquisa.setProtocolo(localForm.getConProtocolo() == null ? "" : localForm.getConProtocolo().trim());
			objPesquisa.setStatusTermo(new StatusTermo());
			objPesquisa.getStatusTermo().setCodStatusTermo((localForm.getConSituacao() == null || localForm.getConSituacao().trim().length() == 0)? null: Integer.valueOf(localForm.getConSituacao().trim()));
			objPesquisa.setOrgaoCessionario(new Orgao());
			objPesquisa.getOrgaoCessionario().setCodOrgao((localForm.getConOrgaoCessionario() == null || localForm.getConOrgaoCessionario().trim().length() == 0)? null: Integer.valueOf(localForm.getConOrgaoCessionario().trim()));
			objPesquisa.getBemImovel().setUf(localForm.getUf() == null ? "": localForm.getUf().trim());
			objPesquisa.getBemImovel().setCodMunicipio(localForm.getCodMunicipio() == null ? null: Integer.valueOf(localForm.getCodMunicipio().trim()));
			objPesquisa.setDataInicioVigencia(Data.formataData(localForm.getConDtInicioVigencia()));
			objPesquisa.setDataFinalVigencia(Data.formataData(localForm.getConDtFimVigencia()));
			
			Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), numPagina, totalRegistros);

			pagina = OperacaoFacade.listarCessaoDeUso(pagina, objPesquisa);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.getSession().setAttribute("pagina", pagina);
			if (pagina.getTotalRegistros()==0){
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			return mapping.findForward("pgConsultaCessaoDeUso");		
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgConsultaCessaoDeUso"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgConsultaCessaoDeUso"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".pesquisarCessaoDeUsoBemImovel"}, e, ApplicationException.ICON_ERRO);
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
		request.getSession().setAttribute("listItemCessaoDeUso", null);
		request.getSession().setAttribute("listAssinatura", null);
		request.getSession().setAttribute("orgaosCessionarios", null);
		request.getSession().setAttribute("edificacoes", null);
		request.getSession().setAttribute("orgaosAssinatura", null);
		request.getSession().setAttribute("cargosAssinatura", null);
		request.getSession().setAttribute("nomesAssinatura", null);

		CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
		localForm.limparCampos();
		localForm.setCodCessaoDeUso(null);
		return this.carregarPgListCessaoDeUsoBemImovel(mapping, form, request, response);
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
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
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
			localForm.setConDtInicioVigencia(valores[6]);
			localForm.setConDtFimVigencia(valores[7]);
			localForm.setActionType(valores[9]);
			localizarBemImovel = true;

			if (valores[8].equalsIgnoreCase("P"))  {
				localForm.setConCodBemImovel(String.valueOf(codBemImovelSimpl));
				localForm.setConNrBemImovel(String.valueOf(nrBemImovel));
				localForm.setConInstituicao(String.valueOf(codInstituicao));
				return this.carregarPgListCessaoDeUsoBemImovel(mapping, form, request, response);
			} else {
				localForm.setCodBemImovel(String.valueOf(codBemImovelSimpl));
				localForm.setNrBemImovel(String.valueOf(nrBemImovel));
				localForm.setInstituicao(String.valueOf(codInstituicao));

				localForm.setConInstituicao(valores[10]);
				localForm.setConCodBemImovel(valores[11].trim());
				localForm.setConNrBemImovel(valores[12].trim());

				return this.carregarPgEditCessaoDeUsoBemImovel(mapping, form, request, response);
			}

		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".retornoLocalizarBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Obtem o retorno de Localizar Lei Bem Imóvel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward retornoLocalizarLeiBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		log.info("Método retornoLocalizarLeiBemImovel processando...");
		try {
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);

			Integer codLeiBemImovel = (request.getParameter("codLeiBemImovel") == null ? 1 : Integer.valueOf(request.getParameter("codLeiBemImovel")));
			LeiBemImovel lei = CadastroFacade.obterLeiBemImovel(codLeiBemImovel);
			if (lei != null) {
				localForm.setCodLei(String.valueOf(codLeiBemImovel));
				localForm.setNumeroLei(Long.toString(lei.getNumero()));
				localForm.setDataAssinaturaLei(Data.formataData(lei.getDataAssinatura()));
				localForm.setDataPublicacaoLei(Data.formataData(lei.getDataPublicacao()));
				localForm.setProjetoLei("N");
				localForm.setNrDioeLei(Long.toString(lei.getNrDioe()));
			}

			String camposPesquisaUCOrigem = request.getParameter("camposPesquisaUCOrigem") != null ? request.getParameter("camposPesquisaUCOrigem").toString() : "";		
			String[] valores = camposPesquisaUCOrigem.split(";");
			localForm.setConNrTermo(valores[0]);
			localForm.setConProtocolo(valores[1]);
			localForm.setConSituacao(valores[2]);
			localForm.setConOrgaoCessionario(valores[3]);
			localForm.setUf(valores[4]);
			localForm.setCodMunicipio(valores[5]);
			localForm.setActionType(valores[6]);
			localForm.setCodBemImovel(valores[7]);
			localForm.setCodCessaoDeUso(valores[8]);
			localForm.setConInstituicao(valores[9]);
			localForm.setInstituicao(valores[10]);
			localForm.setNrBemImovel(valores[11]);

			localizarLeiBemImovel = true;
			return this.carregarPgEditCessaoDeUsoBemImovel(mapping, localForm, request, response);

		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".retornoLocalizarLeiBemImovel"}, e, ApplicationException.ICON_ERRO);
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
	public ActionForward carregarPgEditCessaoDeUsoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {

		log.info("Método carregarPgEditCessaoDeUsoBemImovel processando...");
		try {
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm)form;
			
			if (localForm.getActionType().equals("incluir")) {
				request.getSession().setAttribute("bemImovelSimplificado", null);
				request.getSession().setAttribute("execBuscaBemImovel", null);
				request.getSession().setAttribute("cedidoPara", null);
				request.getSession().setAttribute("listItemCessaoDeUso", null);
				request.getSession().setAttribute("listAssinatura", null);
				request.getSession().setAttribute("orgaosCessionarios", null);
				request.getSession().setAttribute("edificacoes", null);
				request.getSession().setAttribute("orgaosAssinatura", null);
				request.getSession().setAttribute("cargosAssinatura", null);
				request.getSession().setAttribute("nomesAssinatura", null);
				request.getSession().setAttribute("listaInstituicao", null);
				if (!localizarBemImovel && !localizarLeiBemImovel) {
					localForm.limparCampos();
					verificarGrupoUsuarioLogado(localForm, request);
					if (localForm.getInstituicao() != null && localForm.getInstituicao().trim().equals("0") && localForm.getInstituicao().trim().equals("")) {
						Integer tempo = OperacaoFacade.obterParametroAgendaUnico(Integer.valueOf(localForm.getInstituicao())).getTempoCessao();
						localForm.setParametroAgendaTempoCessao(String.valueOf(tempo));
					} else {
						String tempo = OperacaoFacade.obterListaFormatadaParametroAgenda();
						localForm.setParametroAgendaTempoCessao(String.valueOf(tempo));
					}
					request.getSession().setAttribute("orgaosCessionarios", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Dominios.administracaoImovel.ADMINISTRACAO_TERCEIROS.getIndex(), SentinelaComunicacao.getInstance(request).getCodUsuario())));
					localForm.setCodCessaoDeUso(null);
				}
			} else {
				verificarGrupoUsuarioLogado(localForm, request);
			}

			request.getSession().setAttribute("listaInstituicao", CadastroFacade.listarComboInstituicao());	
			if (localForm.getActionType().equals("gerarNova")) {
				SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
				CessaoDeUso cessaoDeUso = OperacaoFacade.gerarNovaCessaoDeUso(Integer.valueOf(localForm.getCodCessaoDeUso()), sentinelaInterface.getCpf());
				this.addMessage("SUCESSO.42", new String[]{"Cessão de Uso", localForm.getCodCessaoDeUso(), cessaoDeUso.getCodCessaoDeUso().toString(), Dominios.statusTermo.RASCUNHO.getLabel()}, request);
				localForm.setActionType("alterar");
				localForm.setCodCessaoDeUso(cessaoDeUso.getCodCessaoDeUso().toString());
				populaForm(localForm, request);
			} else if (localForm.getCodCessaoDeUso() != null && localForm.getCodCessaoDeUso().length() > 0) {
				populaForm(localForm, request);
			}

			return mapping.findForward("pgEditCessaoDeUso");				
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgConsultaCessaoDeUso"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgConsultaCessaoDeUso"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".carregarPgEditCessaoDeUsoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Carrega o combobox de orgãos de acordo com a Instituição selecionada
	 * @param form
	 */
	public ActionForward carregarComboOrgaoPesquisa (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboOrgaoPesquisa processando...");
		try {
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;

			verificarGrupoUsuarioLogado(localForm, request);
			String instituicao = (StringUtils.isNotBlank(localForm.getConInstituicao()))?localForm.getConInstituicao():null;
			
			request.getSession().setAttribute("orgaosPesquisar", null);
			if (instituicao != null) {
				request.getSession().setAttribute("orgaosPesquisar", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Dominios.administracaoImovel.ADMINISTRACAO_TERCEIROS.getIndex(), Integer.valueOf(instituicao))));
			} else {
				request.getSession().setAttribute("orgaosPesquisar", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Dominios.administracaoImovel.ADMINISTRACAO_TERCEIROS.getIndex(), SentinelaComunicacao.getInstance(request).getCodUsuario())));
			}
			
			return mapping.findForward("pgComboOrgaoPesquisarCessao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboOrgaoPesquisarCessao"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboOrgaoPesquisarCessao"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".carregarComboOrgaoPesquisa"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Carrega o combobox de orgãos de acordo com a Instituição selecionada
	 * @param form
	 */
	public ActionForward carregarComboOrgao (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboOrgao processando...");
		try {
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);

			String instituicao = (StringUtils.isNotBlank(localForm.getInstituicao()))?localForm.getInstituicao():null;
			
			request.getSession().setAttribute("orgaosCessionarios", null);
			if (instituicao != null) {
				request.getSession().setAttribute("orgaosCessionarios", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Dominios.administracaoImovel.ADMINISTRACAO_TERCEIROS.getIndex(), Integer.valueOf(instituicao))));
				localForm.setParametroAgendaTempoCessao(String.valueOf(OperacaoFacade.obterParametroAgendaUnico(Integer.valueOf(instituicao)).getTempoCessao()));
			} else {
				request.getSession().setAttribute("orgaosCessionarios", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Dominios.administracaoImovel.ADMINISTRACAO_TERCEIROS.getIndex(), SentinelaComunicacao.getInstance(request).getCodUsuario())));
			}
			
			return mapping.findForward("pgComboOrgaoCessao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboOrgaoCessao"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboOrgaoCessao"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".carregarComboOrgao"}, e, ApplicationException.ICON_ERRO);
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
	public ActionForward carregarPgViewCessaoDeUsoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {

		log.info("Método carregarPgViewCessaoDeUsoBemImovel processando...");
		try {
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm)form;

			if (localForm.getCodCessaoDeUso() != null && localForm.getCodCessaoDeUso().length() > 0) {
				populaForm(localForm, request);
			}
			if (localForm.getActionType().isEmpty()){
				localForm.setActionType("exibir");
			}
			return mapping.findForward("pgViewCessaoDeUso");				
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgViewCessaoDeUso"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgViewCessaoDeUso"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".carregarPgViewCessaoDeUsoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private void populaForm(ActionForm form, HttpServletRequest request) throws ApplicationException {
		try {
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm)form;
			verificarGrupoUsuarioLogado(localForm, request);

			CessaoDeUso cessaoDeUso = OperacaoFacade.obterCessaoDeUsoCompleto((StringUtils.isNotBlank(localForm.getCodCessaoDeUso()))?Integer.valueOf(localForm.getCodCessaoDeUso()):0);
			
			this.carregarRealizadoPor(request, form, cessaoDeUso);
			localForm.setCodBemImovel(cessaoDeUso.getBemImovel().getCodBemImovel().toString());
			localForm.setNrBemImovel(cessaoDeUso.getBemImovel().getNrBemImovel().toString());
			if (cessaoDeUso.getInstituicao() != null && cessaoDeUso.getInstituicao().getCodInstituicao() > 0) {
				localForm.setInstituicaoDesc(CadastroFacade.obterInstituicao(cessaoDeUso.getInstituicao().getCodInstituicao()).getSiglaDescricao());
				localForm.setInstituicao(cessaoDeUso.getInstituicao().getCodInstituicao().toString());
			}
			carregarListaItemCessaoDeUsoAssinatura(localForm, request);
			
			localForm.setCodCessaoDeUso(cessaoDeUso.getCodCessaoDeUso().toString());
			if (cessaoDeUso.getStatusTermo() != null) {
				localForm.setStatus(Dominios.statusTermo.getStatusTermoByIndex(cessaoDeUso.getStatusTermo().getCodStatusTermo()).getLabel());
				localForm.setCodStatus(cessaoDeUso.getStatusTermo().getCodStatusTermo().toString());
			} else {
				localForm.setStatus(" --- ");
				localForm.setCodStatus("0");
			}
			localForm.setDtInicioVigencia(Data.formataData(cessaoDeUso.getDataInicioVigencia()));
			localForm.setDtFimVigencia(Data.formataData(cessaoDeUso.getDataFinalVigenciaPrevisao()));
			localForm.setProtocolo(cessaoDeUso.getProtocolo());
			localForm.setParametroAgendaTempoCessao(String.valueOf(OperacaoFacade.obterParametroAgendaUnico(Integer.valueOf(localForm.getInstituicao())).getTempoCessao()));
			
			if (cessaoDeUso.getCessaoDeUsoOriginal() != null && cessaoDeUso.getCessaoDeUsoOriginal().getCodCessaoDeUso() > 0) {
				localForm.setCodCessaoDeUsoOrginal(cessaoDeUso.getCessaoDeUsoOriginal().getCodCessaoDeUso().toString());
			}
			
			if (localForm.getActionType().equalsIgnoreCase("exibir") || localForm.getActionType().equalsIgnoreCase("renovar") || 
					localForm.getActionType().equalsIgnoreCase("excluir") || localForm.getActionType().equalsIgnoreCase("revogDev")) {
				if (cessaoDeUso.getOrgaoCedente() != null) {
					localForm.setOrgaoCedente(cessaoDeUso.getOrgaoCedente().getSiglaDescricao());
				}
				localForm.setOrgaoCessionario(cessaoDeUso.getOrgaoCessionario().getSiglaDescricao());			
			} else {
				if (cessaoDeUso.getStatusTermo().getCodStatusTermo() == Dominios.statusTermo.EM_RENOVACAO.getIndex()) {
					localForm.setOrgaoCessionario(cessaoDeUso.getOrgaoCessionario().getSiglaDescricao());
					localForm.setCodOrgaoCessionario(cessaoDeUso.getOrgaoCessionario().getCodOrgao().toString());
				} else {
					request.getSession().setAttribute("orgaosCessionarios", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboByTipoAdm(Dominios.administracaoImovel.ADMINISTRACAO_TERCEIROS.getIndex())));
					if (cessaoDeUso.getOrgaoCedente() != null) {
						localForm.setOrgaoCedente(cessaoDeUso.getOrgaoCedente().getCodOrgao().toString());
					}
					localForm.setOrgaoCessionario(cessaoDeUso.getOrgaoCessionario().getCodOrgao().toString());			
				}
				this.carregarCamposRequest(localForm, request);
			}

			if (localForm.getActionType().equalsIgnoreCase("revogDev")) {
				localForm.setTipoRevogDev("0");
				if (cessaoDeUso.getStatusTermo().getCodStatusTermo() == Dominios.statusTermo.DEVOLVIDO.getIndex()) {
					localForm.setTipoRevogDev("2");
				} else {
					localForm.setTipoRevogDev("1");
				}
				localForm.setMotivo(cessaoDeUso.getMotivoRevogacaoDevolucao());
			} else {
				if (cessaoDeUso.getStatusTermo().getCodStatusTermo() == Dominios.statusTermo.DEVOLVIDO.getIndex() ||
					cessaoDeUso.getStatusTermo().getCodStatusTermo() == Dominios.statusTermo.REVOGADO.getIndex()) {
					if (cessaoDeUso.getStatusTermo().getCodStatusTermo() == Dominios.statusTermo.DEVOLVIDO.getIndex()) {
						localForm.setTipoRevogDevDesc("Devolução");
					} else {
						localForm.setTipoRevogDevDesc("Revogação");
					}
					localForm.setMotivo(cessaoDeUso.getMotivoRevogacaoDevolucao());
				}
			}

			if (cessaoDeUso.getNumeroProjetoDeLei() != null && cessaoDeUso.getNumeroProjetoDeLei().trim().length() > 0) {
				localForm.setProjetoLei("S");
				localForm.setProjetoLeiDesc("Sim");
				localForm.setNumeroLei(cessaoDeUso.getNumeroProjetoDeLei().toString());
			} else {
				if (cessaoDeUso.getLeiBemImovel() != null) {
					LeiBemImovel lei = CadastroFacade.obterLeiBemImovel(cessaoDeUso.getLeiBemImovel().getCodLeiBemImovel());
					localForm.setProjetoLei("N");
					localForm.setProjetoLeiDesc("Não");
					localForm.setCodLei(lei.getCodLeiBemImovel().toString());
					localForm.setNumeroLei(Long.toString(lei.getNumero()));
					localForm.setDataAssinaturaLei(Data.formataData(lei.getDataAssinatura()));
					localForm.setDataPublicacaoLei(Data.formataData(lei.getDataPublicacao()));
					localForm.setNrDioeLei(Long.toString(lei.getNrDioe()));
				}
			}
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".populaForm"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private void carregarRealizadoPor(HttpServletRequest request, ActionForm form, CessaoDeUso cessaoDeUso) throws ApplicationException {
		try {
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm)form;
			SentinelaParam sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByCPF(cessaoDeUso.getCpfResponsavelInclusao());
			String aux = "";
			if (sentinelaParam != null) {
				aux = "Inclusão realizada por ";
				aux = aux.concat(sentinelaParam.getNome().trim());
				aux = aux.concat(" em ");
				aux = aux.concat(Data.formataData(cessaoDeUso.getTsInclusao(),"dd/MM/yyyy HH:mm"));
				localForm.setIncluidoPor(aux);
			}
			if (cessaoDeUso.getCpfResponsavelAlteracao() != null && cessaoDeUso.getCpfResponsavelAlteracao().length() > 0){
				sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByCPF(cessaoDeUso.getCpfResponsavelAlteracao());
				if (sentinelaParam != null) {
					aux = "Alteração realizada por ";
					aux = aux.concat(sentinelaParam.getNome().trim());
					aux = aux.concat(" em ");
					aux = aux.concat(Data.formataData(cessaoDeUso.getTsAlteracao(),"dd/MM/yyyy HH:mm"));
					localForm.setAlteradoPor(aux);
				}
			}
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".carregarRealizadoPor"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Carrega o combobox de edificações de acordo com o Bem Imóvel selecionado
	 * @param form
	 */
	public ActionForward carregarComboEditEdificacaoBemImovel (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboEditEdificacaoBemImovel processando...");
		try {
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
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

			return mapping.findForward("pgComboEditEdificacaoBemImovelCessaoDeUso");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboEditEdificacaoBemImovelCessaoDeUso"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboEditEdificacaoBemImovelCessaoDeUso"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".carregarComboEditEdificacaoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Realiza o cálculo de Cessão de Uso de % e metros quadrados.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public ActionForward calcularCessaoDeUsoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método calcularCessaoDeUsoBemImovel processando...");

		setActionForward(mapping.findForward("pgEditAreaCessaoDeUsoBemImovel"));
		try {
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);

			String codBemImovel = "0";
			if (localForm.getCodCessaoDeUso() != null && localForm.getCodCessaoDeUso().length() > 0) {
				codBemImovel = OperacaoFacade.obterCessaoDeUsoCompleto(Integer.valueOf(localForm.getCodCessaoDeUso())).getBemImovel().getCodBemImovel().toString();
			} else {
				if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
					BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
					localForm.setCodBemImovel(aux.getCodBemImovel().toString());
				}
				codBemImovel = localForm.getCodBemImovel();
			}
			
			ItemDTO obj = OperacaoFacade.calcularPercentualMetros(localForm.getTipoCessaoDeUso(), localForm.getCessaoDeUsoMetros(), 
					localForm.getCessaoDeUsoPercentual(), "Cessão de Uso", localForm.getEdificacao(), codBemImovel);
			
			localForm.setCessaoDeUsoMetros(Valores.formatarParaDecimal(obj.getMetros(), 2));
			localForm.setCessaoDeUsoPercentual(Valores.formatarParaDecimal(obj.getPercentual(), 5));

		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".calcularCessaoDeUso"}, e, ApplicationException.ICON_ERRO);
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
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			Collection<ItemComboDTO> ret = montaComboOrgaoCessaoDeUsoAssinatura(localForm, request);
			if (ret != null && ret.size() > 0) {
				request.getSession().setAttribute("orgaosAssinatura", Util.htmlEncodeCollection(ret));
			} else {
				request.getSession().setAttribute("orgaosAssinatura", null);
			}
			
			return mapping.findForward("pgComboOrgaoCessaoDeUsoAssinatura");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboOrgaoCessaoDeUsoAssinatura"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboOrgaoCessaoDeUsoAssinatura"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".carregarComboOrgaoAssinatura"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private Collection<ItemComboDTO> montaComboOrgaoCessaoDeUsoAssinatura(CessaoDeUsoBemImovelForm localForm, HttpServletRequest request) throws ApplicationException {
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
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".montaComboOrgaoCessaoDeUsoAssinatura"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Carrega o combobox de cargo de acordo com o Órgão da Assinatura selecionado
	 * @param form
	 */
	public ActionForward carregarComboCargoAssinatura (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboCargoAssinatura processando...");
		try {
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);

			String param = (StringUtils.isNotBlank(localForm.getOrgaoAssinatura()))?localForm.getOrgaoAssinatura():null;
			
			if (param != null){
				request.getSession().setAttribute("cargosAssinatura", Util.htmlEncodeCollection(CadastroFacade.listarComboCargoAssinaturaByInstituicao(Integer.valueOf(param), Integer.valueOf(localForm.getInstituicao()))));
			} else {
				request.getSession().setAttribute("cargosAssinatura", null);
			}

			return mapping.findForward("pgComboCargoCessaoDeUsoAssinatura");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboCargoCessaoDeUsoAssinatura"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboCargoCessaoDeUsoAssinatura"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".carregarComboCargoAssinatura"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Carrega o combobox de Nome de acordo com o Cargo e Órgão da Assinatura selecionados
	 * @param form
	 */
	public ActionForward carregarComboNomeAssinatura (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboNomeAssinatura processando...");
		try {
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);

			String orgao = (StringUtils.isNotBlank(localForm.getOrgaoAssinatura()))?localForm.getOrgaoAssinatura():null;
			String cargo = (StringUtils.isNotBlank(localForm.getCargoAssinatura()))?localForm.getCargoAssinatura():null;
			String instituicao = (StringUtils.isNotBlank(localForm.getInstituicao()))?localForm.getInstituicao():null;
			
			if (orgao != null && cargo != null){
				request.getSession().setAttribute("nomesAssinatura", Util.htmlEncodeCollection(CadastroFacade.listarNomeAssinaturaCombo(Integer.valueOf(orgao), Integer.valueOf(cargo), Integer.valueOf(instituicao))));
			} else {
				request.getSession().setAttribute("nomesAssinatura", null);
			}

			return mapping.findForward("pgComboNomeCessaoDeUsoAssinatura");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboNomeCessaoDeUsoAssinatura"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboNomeCessaoDeUsoAssinatura"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".carregarComboNomeAssinatura"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Realiza o encaminhamento necessário para salvar a Cessão de Uso.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward salvarCessaoDeUsoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método salvarCessaoDeUsoBemImovel processando...");
		try	{
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
			
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
			
			CessaoDeUso cessaoDeUso = new CessaoDeUso();
			if (localForm.getCodCessaoDeUso() != null && localForm.getCodCessaoDeUso().length() > 0) {
				cessaoDeUso = OperacaoFacade.obterCessaoDeUsoCompleto(Integer.valueOf(localForm.getCodCessaoDeUso()));
			} else {
				cessaoDeUso.setBemImovel(CadastroFacade.obterBemImovelSimplificado(Integer.valueOf(localForm.getCodBemImovel())));
			}
			if (localForm.getCodCessaoDeUso() != null && localForm.getCodCessaoDeUso().length() > 0) {
				this.populaPojoRegistro(cessaoDeUso, "A", request);
			} else {
				this.populaPojoRegistro(cessaoDeUso, "I", request);
			}
			
			// Verifica se já existe com mesmo Bem Imóvel com o Status Termo Rascunho
			this.verificarCessaoDeUsoByBemImovelStatusTermo(localForm.getCodCessaoDeUso(), cessaoDeUso, "I");
			
			if (localForm.getCodCessaoDeUso() != null && localForm.getCodCessaoDeUso().trim().length() > 0) {
				this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()), Integer.valueOf(localForm.getCodCessaoDeUso()));
			} else {
				this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()), Integer.valueOf(0));
			}
			
			this.populaPojo(cessaoDeUso, localForm, request);

			if (localForm.getProjetoLei() != null && localForm.getProjetoLei().equalsIgnoreCase("N")) {
				validarLeiBemImovel(cessaoDeUso.getLeiBemImovel(), localForm);
				cessaoDeUso = OperacaoFacade.salvarCessaoDeUsoLeiBemImovel(cessaoDeUso, cessaoDeUso.getLeiBemImovel());
			} else {
				cessaoDeUso = OperacaoFacade.salvarCessaoDeUso(cessaoDeUso);
			}
			
			saveToken(request);
			localForm.limparCampos();
			this.addMessage("SUCESSO.40", new String[]{"Cessão de Uso", cessaoDeUso.getCodCessaoDeUso().toString(), ""}, request);	
			return this.carregarPgListCessaoDeUsoBemImovel(mapping, form, request, response);
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditCessaoDeUso"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditCessaoDeUso"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".salvarCessaoDeUsoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	private void verificarCessaoTotalBemImovel(Integer codBemImovel, Integer codCessao) throws ApplicationException {
		CessaoDeUso cessaoDeUso = new CessaoDeUso();
		cessaoDeUso.setCodCessaoDeUso(codCessao);
		cessaoDeUso.setBemImovel(new BemImovel());
		cessaoDeUso.getBemImovel().setCodBemImovel(codBemImovel);
		String result = OperacaoFacade.verificarCessaoTotalBemImovel(cessaoDeUso);
		if (result != null && result.length() > 0) {
			throw new ApplicationException("AVISO.51",  new String[]{result}, ApplicationException.ICON_AVISO);
		}
	}

	// Verifica se já existe com mesmo Bem Imóvel com o Status Termo Rascunho
	private void verificarCessaoDeUsoByBemImovelStatusTermo(String codCessaoDeUso, CessaoDeUso cessaoDeUso, String operacao) throws ApplicationException {
		if (codCessaoDeUso == null || codCessaoDeUso.trim().isEmpty()) {
			Collection<CessaoDeUso> listDuplicidade = OperacaoFacade.verificarCessaoDeUsoByBemImovelStatusTermo(cessaoDeUso); 
			CessaoDeUso cessaoDeUsoAux = cessaoDeUso;
			boolean mesmoBI = false;
			if (listDuplicidade.size() > 0) {
				boolean rasc = false;
				String status = "";
				for (CessaoDeUso cessaoDeUsoDB : listDuplicidade) {
					if (Dominios.statusTermo.RASCUNHO.getIndex() == cessaoDeUsoDB.getStatusTermo().getCodStatusTermo()) {
						rasc = true;
					}
					status = status.concat(Dominios.statusTermo.getStatusTermoByIndex(cessaoDeUsoDB.getStatusTermo().getCodStatusTermo()).getLabel());
					status = status.concat(" - ");
					if (!operacao.equalsIgnoreCase("I")) {
						if (cessaoDeUsoDB.getBemImovel().equals(cessaoDeUsoAux.getBemImovel())) {
							mesmoBI = true;
						}
					}
				}
				if (status.trim().length() > 2) {
					status = status.substring(0, status.length() - 3);
				}
				if (operacao.equalsIgnoreCase("I")) {
					if (rasc) {
						throw new ApplicationException("AVISO.58",  new String[]{"Cessão de Uso", status}, ApplicationException.ICON_AVISO);
					} else {
						throw new ApplicationException("AVISO.66",  new String[]{"Cessão de Uso", status}, ApplicationException.ICON_AVISO);
					}
				} else {
					if (mesmoBI) {
						if (rasc) {
							throw new ApplicationException("AVISO.58",  new String[]{"Cessão de Uso", status}, ApplicationException.ICON_AVISO);
						} else {
							throw new ApplicationException("AVISO.66",  new String[]{"Cessão de Uso", status}, ApplicationException.ICON_AVISO);
						}
					}
				}
			}
		}
	}
	
	private void validarLeiBemImovel(LeiBemImovel leiBemImovel, CessaoDeUsoBemImovelForm localForm) throws ApplicationException {
		if (leiBemImovel != null && leiBemImovel.getCodLeiBemImovel() != null && leiBemImovel.getCodLeiBemImovel() > 0 ){
			LeiBemImovel leiBemImovelBD = CadastroFacade.obterLeiBemImovelCompleto(leiBemImovel.getCodLeiBemImovel());
			if ((leiBemImovelBD.getTipoLeiBemImovel().getCodTipoLeiBemImovel().equals(EXCETO_TIPO_LEI))) {
				throw new ApplicationException("AVISO.63", new String[]{"Doação"}, ApplicationException.ICON_AVISO);
			}
			if (!(leiBemImovelBD.getBemImovel().equals(leiBemImovel.getBemImovel()) && 
				leiBemImovelBD.getDataAssinatura().compareTo(leiBemImovel.getDataAssinatura()) == 0 && 
				leiBemImovelBD.getDataPublicacao().compareTo(leiBemImovel.getDataPublicacao()) == 0 && 
				leiBemImovelBD.getNrDioe().equals(leiBemImovel.getNrDioe()) &&
				leiBemImovelBD.getTipoLeiBemImovel().equals(leiBemImovel.getTipoLeiBemImovel()))) {
				throw new ApplicationException("AVISO.60", ApplicationException.ICON_AVISO);
			}
		}
		StringBuffer str = new StringBuffer();
		
		if (localForm.getProjetoLei() == null || localForm.getProjetoLei().trim().length() == 0) {
			str.append("Projeto de lei");
		}
		if (localForm.getNumeroLei() == null || localForm.getNumeroLei().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Número da Lei");
		}
		if (localForm.getCodLei() == null || localForm.getCodLei().trim().length() == 0) {
			if (localForm.getProjetoLei() != null && localForm.getProjetoLei().trim().equals("N")) {
				if (localForm.getDataAssinaturaLei() == null || localForm.getDataAssinaturaLei().trim().length() == 0) {
					if (str.length() > 0) {
						str.append(", ");
					}
					str.append("Data da Assinatura da Lei");
				}
				if (localForm.getDataPublicacaoLei() == null || localForm.getDataPublicacaoLei().trim().length() == 0) {
					if (str.length() > 0) {
						str.append(", ");
					}
					str.append("Data da Publicação da Lei");
				}
				if (localForm.getNrDioeLei() == null || localForm.getNrDioeLei().trim().length() == 0) {
					if (str.length() > 0) {
						str.append(", ");
					}
					str.append("Número do DIOE");
				}
			}
		}

		if (str.length() > 0) {
			str.append(".");
			throw new ApplicationException("AVISO.72", new String[]{str.toString()}, ApplicationException.ICON_AVISO);
		}
		
	}

	/**
	 * Realiza o encaminhamento necessário para alterar a Cessão de Uso.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward alterarCessaoDeUsoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método alterarCessaoDeUsoBemImovel processando...");
		try	{			
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
			
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
			
			CessaoDeUso cessaoDeUso = OperacaoFacade.obterCessaoDeUsoCompleto(Integer.valueOf(localForm.getCodCessaoDeUso()));

			this.populaPojoRegistro(cessaoDeUso, "A", request);

			this.verificarCessaoDeUsoByBemImovelStatusTermo(localForm.getCodCessaoDeUso(), cessaoDeUso, "A");

			this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()), Integer.valueOf(localForm.getCodCessaoDeUso()));
			
			this.populaPojo(cessaoDeUso, localForm, request);

			if (localForm.getProjetoLei() != null && localForm.getProjetoLei().equalsIgnoreCase("N")) {
				validarLeiBemImovel(cessaoDeUso.getLeiBemImovel(), localForm);
				cessaoDeUso = OperacaoFacade.salvarCessaoDeUsoLeiBemImovel(cessaoDeUso, cessaoDeUso.getLeiBemImovel());
			} else {
				cessaoDeUso = OperacaoFacade.salvarCessaoDeUso(cessaoDeUso);
			}
			
			saveToken(request);
			localForm.limparCampos();
			this.addMessage("SUCESSO.41", new String[]{"Cessão de Uso", cessaoDeUso.getCodCessaoDeUso().toString(), cessaoDeUso.getStatusTermo().getDescricao(), ""}, request);	
			return this.carregarPgListCessaoDeUsoBemImovel(mapping, form, request, response);

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditCessaoDeUso"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditCessaoDeUso"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".alterarCessaoDeUsoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	/**
	 * Realiza o encaminhamento necessário para excluir a Cessão de Uso.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward excluirCessaoDeUsoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método excluirCessaoDeUsoBemImovel processando...");
		try	{			
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
			
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	
			verificarGrupoUsuarioLogado(localForm, request);

			CessaoDeUso cessaoDeUso = OperacaoFacade.obterCessaoDeUsoCompleto(Integer.valueOf(localForm.getCodCessaoDeUso()));

			this.populaPojoRegistro(cessaoDeUso, "E", request);

			OperacaoFacade.excluirCessaoDeUso(cessaoDeUso);
			saveToken(request);
			
			localForm.limparCampos();
			localForm.setCodCessaoDeUso(null);
			this.addMessage("SUCESSO.39", new String[]{"Cessão de Uso", cessaoDeUso.getCodCessaoDeUso().toString()}, request);	
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgViewCessaoDeUso"));
			populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgViewCessaoDeUso"));
			populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".excluirCessaoDeUsoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
		return this.carregarPgListCessaoDeUsoBemImovel(mapping, form, request, response);
	}

	/**
	 * Realiza o encaminhamento necessário para renovar a Cessão de Uso.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward renovarCessaoDeUsoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método renovarCessaoDeUsoBemImovel processando...");
		CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
		try	{			
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}

			CessaoDeUso cessaoDeUso = new CessaoDeUso();
			CessaoDeUso cessaoDeUsoRenovacao = new CessaoDeUso();
			if (localForm.getCodCessaoDeUso() != null && localForm.getCodCessaoDeUso().length() > 0) {
				cessaoDeUso = OperacaoFacade.obterCessaoDeUsoCompleto(Integer.valueOf(localForm.getCodCessaoDeUso()));
			}
			cessaoDeUsoRenovacao.setCessaoDeUsoOriginal(cessaoDeUso);
			cessaoDeUsoRenovacao.setInstituicao(cessaoDeUso.getInstituicao());
			cessaoDeUsoRenovacao.setBemImovel(cessaoDeUso.getBemImovel());
			cessaoDeUsoRenovacao.setOrgaoCedente(cessaoDeUso.getOrgaoCedente());
			cessaoDeUsoRenovacao.setOrgaoCessionario(cessaoDeUso.getOrgaoCessionario());
			cessaoDeUsoRenovacao.setDataInicioVigencia(cessaoDeUso.getDataFinalVigenciaPrevisao());
			cessaoDeUsoRenovacao.setLeiBemImovel(cessaoDeUso.getLeiBemImovel());
			cessaoDeUsoRenovacao.setListaAssinaturaCessaoDeUso(cessaoDeUso.getListaAssinaturaCessaoDeUso());
			cessaoDeUsoRenovacao.setListaItemCessaoDeUso(cessaoDeUso.getListaItemCessaoDeUso());
			
			this.populaPojoRegistro(cessaoDeUsoRenovacao, "R", request);
			cessaoDeUsoRenovacao = OperacaoFacade.salvarCessaoDeUsoComFilhos(cessaoDeUsoRenovacao);

			this.populaPojoRegistro(cessaoDeUso, "E", request);
			cessaoDeUso= OperacaoFacade.salvarCessaoDeUso(cessaoDeUso);

			saveToken(request);
			
			localForm.setActionType("alterar");
			localForm.setCodCessaoDeUso(cessaoDeUsoRenovacao.getCodCessaoDeUso().toString());
			this.addMessage("SUCESSO.42", new String[]{"Cessão de Uso", cessaoDeUso.getCodCessaoDeUso().toString(), cessaoDeUsoRenovacao.getCodCessaoDeUso().toString(), Dominios.statusTermo.EM_RENOVACAO.getLabel()}, request);
			localForm.limparCampos();
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgViewCessaoDeUso"));
			populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgViewCessaoDeUso"));
			populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".renovarCessaoDeUsoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
		return this.carregarPgEditCessaoDeUsoBemImovel(mapping, localForm, request, response);
	}

	private void validaDadosForm(CessaoDeUsoBemImovelForm localForm, String escopo, HttpServletRequest request) throws ApplicationException {
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
			str.append("Órgão Cessionário");
		}

		if (escopo != null && escopo.equalsIgnoreCase("Item")) {
			if (localForm.getTipoCessaoDeUso() == null || localForm.getTipoCessaoDeUso().trim().length() == 0) {
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Tipo de Cessão de Uso");
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
			throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar data!"}, ApplicationException.ICON_AVISO);
		}
		if (localForm.getDtInicioVigencia() != null && localForm.getDtInicioVigencia().trim().length() > 0) {
			if (!Data.validarData(localForm.getDtInicioVigencia().trim())) {
				throw new ApplicationException("errors.date", new String[]{"A Data de Início de Vigência informada"}, ApplicationException.ICON_AVISO);
			}
			Date inicVig = null;
			try {
				inicVig = Data.formataData(localForm.getDtInicioVigencia().trim());
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar data!"}, ApplicationException.ICON_AVISO);
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
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar data!"}, ApplicationException.ICON_AVISO);
			}
			if (Data.dataDiff(fimVig,dataAntiga) == 1) {
				throw new ApplicationException("errors.dataAntiga", new String[]{"A Data de Fim de Vigência"}, ApplicationException.ICON_AVISO);
			}
			Date inicVig = null;
			try {
				inicVig = Data.formataData(localForm.getDtInicioVigencia().trim());
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar data!"}, ApplicationException.ICON_AVISO);
			}
			if (Data.dataDiff(fimVig,inicVig) == 1) {
				throw new ApplicationException("mensagem.erro.3", new String[]{"Data de Fim de Vigência", "Data de Início de Vigência"}, ApplicationException.ICON_AVISO);
			}

		}
		if (localForm.getDataAssinaturaLei() != null && localForm.getDataAssinaturaLei().trim().length() > 0) {
			if (!Data.validarData(localForm.getDataAssinaturaLei().trim())) {
				throw new ApplicationException("errors.date", new String[]{"A Data de Assinatura da Lei informada"}, ApplicationException.ICON_AVISO);
			}
			Date dtAssinat = null;
			try {
				dtAssinat = Data.formataData(localForm.getDataAssinaturaLei().trim());
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar data!"}, ApplicationException.ICON_AVISO);
			}
			if (Data.dataDiff(dtAssinat,dataAntiga) == 1) {
				throw new ApplicationException("errors.dataAntiga", new String[]{"A Data de Assinatura da Lei"}, ApplicationException.ICON_AVISO);
			}
			Date inicVig = null;
			try {
				inicVig = Data.formataData(localForm.getDtInicioVigencia().trim());
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar data!"}, ApplicationException.ICON_AVISO);
			}
			if (Data.dataDiff(dtAssinat,inicVig) == 1) {
				throw new ApplicationException("mensagem.erro.3", new String[]{"Data de Início de Vigência", "Data de Assinatura da Lei"}, ApplicationException.ICON_AVISO);
			}
		}
		if (localForm.getDataPublicacaoLei() != null && localForm.getDataPublicacaoLei().trim().length() > 0) {
			if (!Data.validarData(localForm.getDataPublicacaoLei().trim())) {
				throw new ApplicationException("errors.date", new String[]{"A Data de Publicação da Lei informada"}, ApplicationException.ICON_AVISO);
			}
			Date dtPub = null;
			try {
				dtPub = Data.formataData(localForm.getDataPublicacaoLei().trim());
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar data!"}, ApplicationException.ICON_AVISO);
			}
			if (Data.dataDiff(dtPub,dataAntiga) == 1) {
				throw new ApplicationException("errors.dataAntiga", new String[]{"A Data de Publicação da Lei"}, ApplicationException.ICON_AVISO);
			}
		}
		
	}	

	private void validarRevogarDevolver(CessaoDeUsoBemImovelForm localForm) throws ApplicationException {
		StringBuffer str = new StringBuffer();
		if (localForm.getTipoRevogDev().trim().length() == 0) {
			str.append("Tipo");
		}

		if (localForm.getTipoRevogDev().equals("1") || localForm.getTipoRevogDev().equals("2")) {
			if (localForm.getMotivo().trim().length() == 0) {
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Motivo");
			}
		}
		
		if (str.length() > 0) {
			str.append(".");
			throw new ApplicationException("AVISO.72", new String[]{str.toString()}, ApplicationException.ICON_AVISO);
		}

	}

	private void populaPojo(CessaoDeUso cessaoDeUso, CessaoDeUsoBemImovelForm localForm, HttpServletRequest request) throws ApplicationException {
		try {
			BemImovel bemImovel = null;
			bemImovel = CadastroFacade.obterBemImovelSimplificado(Integer.valueOf(localForm.getCodBemImovel())); 
			if (localForm.getProjetoLei() != null && localForm.getProjetoLei().equalsIgnoreCase("N")) {
				LeiBemImovel leiBemImovel = CadastroFacade.obterLeiBemImovelPorNumero(new Long(localForm.getNumeroLei()));
				if (leiBemImovel != null && leiBemImovel.getCodLeiBemImovel() > 0) {
					cessaoDeUso.setNumeroProjetoDeLei(null);
					cessaoDeUso.setLeiBemImovel(leiBemImovel);
				} else {
					if (localForm.getDataAssinaturaLei() != null && localForm.getDataPublicacaoLei() != null && localForm.getNrDioeLei() != null) {
						Date dataAtual = new Date();
						SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
						leiBemImovel = new LeiBemImovel(); 
						leiBemImovel.setBemImovel(bemImovel);
						leiBemImovel.setNumero(Long.parseLong(localForm.getNumeroLei()));			
						leiBemImovel.setDataAssinatura(Data.formataData(localForm.getDataAssinaturaLei()));
						leiBemImovel.setDataPublicacao(Data.formataData(localForm.getDataPublicacaoLei()));
						if (localForm.getNrDioeLei() != null && localForm.getNrDioeLei().trim().length() > 0) {
							leiBemImovel.setNrDioe(Long.parseLong(localForm.getNrDioeLei()));
						}
						leiBemImovel.setCpfResponsavel(sentinelaInterface.getCpf());
						leiBemImovel.setTsAtualizacao(dataAtual);
						leiBemImovel.setTsInclusao(dataAtual);
						leiBemImovel.setTipoLeiBemImovel(CadastroFacade.obterTipoLeiBemImovel(TIPO_LEI));
						cessaoDeUso.setNumeroProjetoDeLei(null);
						cessaoDeUso.setLeiBemImovel(leiBemImovel);
					} else {
						throw new ApplicationException("AVISO.62", ApplicationException.ICON_AVISO);
					}
				}
			} else {
				if (localForm.getNumeroLei() != null && !localForm.getNumeroLei().isEmpty())
					cessaoDeUso.setNumeroProjetoDeLei(localForm.getNumeroLei().trim());
			}
			cessaoDeUso.setBemImovel(bemImovel);
			cessaoDeUso.setInstituicao(CadastroFacade.obterInstituicao(Integer.valueOf(localForm.getInstituicao())));
			if (bemImovel.getOrgao() != null) {
				cessaoDeUso.setOrgaoCedente(bemImovel.getOrgao());
			}
			cessaoDeUso.setOrgaoCessionario(CadastroFacade.obterOrgao(Integer.valueOf(localForm.getOrgaoCessionario())));
			cessaoDeUso.setDataInicioVigencia(Data.formataData(localForm.getDtInicioVigencia()));
			cessaoDeUso.setDataFinalVigenciaPrevisao(Data.formataData(localForm.getDtFimVigencia()));
			if (localForm.getProtocolo() != null && !localForm.getProtocolo().isEmpty()) {
				cessaoDeUso.setProtocolo(localForm.getProtocolo());
			}
			
			if (localForm.getActionType().equalsIgnoreCase("revogDev")) {
				cessaoDeUso.setMotivoRevogacaoDevolucao(localForm.getMotivo());
			}
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".populaPojo"}, e, ApplicationException.ICON_ERRO);
		} 
	}
	
	private void populaPojoRegistro(CessaoDeUso cessaoDeUso, String acao, HttpServletRequest request) throws ApplicationException {
		try {
			Date dataAtual = new Date();
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			
		    if ("I".equals(acao)) {
				cessaoDeUso.setTsInclusao(dataAtual);
				cessaoDeUso.setCpfResponsavelInclusao(sentinelaInterface.getCpf());
				cessaoDeUso.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.RASCUNHO.getIndex()));
			} else if ("A".equals(acao)) {
				cessaoDeUso.setTsAlteracao(dataAtual);
				cessaoDeUso.setCpfResponsavelAlteracao(sentinelaInterface.getCpf());
			} else if ("E".equals(acao)) {
				cessaoDeUso.setTsAlteracao(dataAtual);
				cessaoDeUso.setCpfResponsavelAlteracao(sentinelaInterface.getCpf());
				cessaoDeUso.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.FINALIZADO.getIndex()));
			} else if ("R".equals(acao)) {
				cessaoDeUso.setTsInclusao(dataAtual);
				cessaoDeUso.setCpfResponsavelInclusao(sentinelaInterface.getCpf());
				cessaoDeUso.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.EM_RENOVACAO.getIndex()));
			}
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".populaPojoRegistro"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private void carregarCamposRequest (ActionForm form, HttpServletRequest request) throws NumberFormatException, ApplicationException {
		try {
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm)form;
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}

			request.getSession().setAttribute("orgaosCessionarios", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Dominios.administracaoImovel.ADMINISTRACAO_TERCEIROS.getIndex(), Integer.valueOf(localForm.getInstituicao()))));
			request.getSession().setAttribute("edificacoes", Util.htmlEncodeCollection(OperacaoFacade.listarEdificacaoComboParaOperacoes(Integer.valueOf(localForm.getCodBemImovel()))));
			Collection<ItemComboDTO> ret = montaComboOrgaoCessaoDeUsoAssinatura(localForm, request);
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
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".carregarCamposRequest"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Realiza o encaminhamento necessário para adicionar um item a Cessão de Uso.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward adicionarItemCessaoDeUsoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método adicionarItemCessaoDeUsoBemImovel processando...");
		try	{			
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;

			// Aciona a validação do Form
			validaDadosForm(localForm, "Item", request);
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			
			CessaoDeUso cessaoDeUso = new CessaoDeUso();
			BemImovel bemImovel = CadastroFacade.obterBemImovelSimplificado(Integer.valueOf(localForm.getCodBemImovel()));
			if (localForm.getCodCessaoDeUso() != null && localForm.getCodCessaoDeUso().length() > 0) {
				cessaoDeUso = OperacaoFacade.obterCessaoDeUsoCompleto(Integer.valueOf(localForm.getCodCessaoDeUso()));
			} else {
				cessaoDeUso.setBemImovel(bemImovel);
			}
			if (localForm.getCodCessaoDeUso() != null && localForm.getCodCessaoDeUso().length() > 0) {
				this.populaPojoRegistro(cessaoDeUso, "A", request);
			} else {
				this.populaPojoRegistro(cessaoDeUso, "I", request);
			}

			this.verificarCessaoDeUsoByBemImovelStatusTermo(localForm.getCodCessaoDeUso(), cessaoDeUso, "A");

			if (localForm.getCodCessaoDeUso() != null && localForm.getCodCessaoDeUso().trim().length() > 0) {
				this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()), Integer.valueOf(localForm.getCodCessaoDeUso()));
			} else {
				this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()), Integer.valueOf(0));
			}
			
			this.populaPojo(cessaoDeUso, localForm, request);

			if (localForm.getProjetoLei() != null && localForm.getProjetoLei().equalsIgnoreCase("N")) {
				validarLeiBemImovel(cessaoDeUso.getLeiBemImovel(), localForm);
				cessaoDeUso = OperacaoFacade.salvarCessaoDeUsoLeiBemImovel(cessaoDeUso, cessaoDeUso.getLeiBemImovel());
			} else {
				cessaoDeUso = OperacaoFacade.salvarCessaoDeUso(cessaoDeUso);
			}

			ItemCessaoDeUso itemCessaoDeUso = new ItemCessaoDeUso();
			itemCessaoDeUso.setTipo(Integer.valueOf(localForm.getTipoCessaoDeUso()));
			
			if (!localForm.getTipoCessaoDeUso().equals("1")) {
				ItemDTO obj = OperacaoFacade.calcularPercentualMetros(localForm.getTipoCessaoDeUso(), localForm.getCessaoDeUsoMetros(), 
						localForm.getCessaoDeUsoPercentual(), "Cessão de Uso", localForm.getEdificacao(), localForm.getCodBemImovel());
				itemCessaoDeUso.setAreaMetroQuadrado(obj.getMetros());
				itemCessaoDeUso.setAreaPercentual(obj.getPercentual());
			} else if (localForm.getTipoCessaoDeUso().equals("1")) {
				if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() != null) {
					itemCessaoDeUso.setAreaMetroQuadrado(bemImovel.getAreaConstruida().add(bemImovel.getAreaTerreno()));
				} else if (bemImovel.getAreaConstruida() == null && bemImovel.getAreaTerreno() != null) {
					itemCessaoDeUso.setAreaMetroQuadrado(bemImovel.getAreaTerreno());
				} else if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() == null) {
					itemCessaoDeUso.setAreaMetroQuadrado(bemImovel.getAreaConstruida());
				}
				itemCessaoDeUso.setAreaPercentual(new BigDecimal(100));
			} 
			if (localForm.getTipoCessaoDeUso().equals("2")) {
				itemCessaoDeUso.setEdificacao(CadastroFacade.obterEdificacao(Integer.valueOf(localForm.getEdificacao())));
			}
			if (cessaoDeUso.getBemImovel().getSomenteTerreno().equalsIgnoreCase("S")) {
				itemCessaoDeUso.setIndTerreno(Boolean.TRUE);
			} else {
				itemCessaoDeUso.setIndTerreno(Boolean.FALSE);
			}
			itemCessaoDeUso.setCessaoDeUso(cessaoDeUso);
			
			if (localForm.getCaracteristicas().trim().length() > 0) {
				if (localForm.getCaracteristicas().trim().length() > 3000) {
					itemCessaoDeUso.setCaracteristica(localForm.getCaracteristicas().trim().substring(0, 3000));
				} else {
					itemCessaoDeUso.setCaracteristica(localForm.getCaracteristicas());
				}
			}
			if (localForm.getSituacaoDominial().trim().length() > 0) {
				if (localForm.getSituacaoDominial().trim().length() > 3000) {
					itemCessaoDeUso.setSituacaoDominial(localForm.getSituacaoDominial().trim().substring(0, 3000));
				} else {
					itemCessaoDeUso.setSituacaoDominial(localForm.getSituacaoDominial());
				}
			}
			if (localForm.getUtilizacao().trim().length() > 0) {
				if (localForm.getUtilizacao().trim().length() > 3000) {
					itemCessaoDeUso.setUtilizacao(localForm.getUtilizacao().trim().substring(0, 3000));
				} else {
					itemCessaoDeUso.setUtilizacao(localForm.getUtilizacao());
				}
			}
			if (localForm.getObservacao().trim().length() > 0) {
				if (localForm.getObservacao().trim().length() > 3000) {
					itemCessaoDeUso.setObservacao(localForm.getObservacao().trim().substring(0, 3000));
				} else {
					itemCessaoDeUso.setObservacao(localForm.getObservacao());
				}
			}
			
			boolean mesmoObjeto = false;
			// Verifica se já existe o mesmo item
			Collection<ItemCessaoDeUso> listDuplicidadeAD = OperacaoFacade.verificarDuplicidadeItemCessaoDeUso(itemCessaoDeUso); 
			if (listDuplicidadeAD.size() > 0) {
				ItemCessaoDeUso itemCessaoDeUsoDB;
				for (Iterator<ItemCessaoDeUso> iterator  = listDuplicidadeAD.iterator(); iterator.hasNext();) {
					itemCessaoDeUsoDB = (ItemCessaoDeUso) iterator .next();
					if ((itemCessaoDeUsoDB.getEdificacao() != null && itemCessaoDeUso.getEdificacao()!= null &&
						itemCessaoDeUsoDB.getEdificacao().getCodEdificacao().equals(itemCessaoDeUso.getEdificacao().getCodEdificacao())) &&
						itemCessaoDeUsoDB.getIndTerreno().equals(itemCessaoDeUso.getIndTerreno())) {
						mesmoObjeto = true;
					}
				}
				if (mesmoObjeto) {
					throw new ApplicationException("AVISO.57", ApplicationException.ICON_AVISO);
				}
			}
			OperacaoFacade.salvarItemCessaoDeUso(itemCessaoDeUso);
			
			if (localForm.getCodCessaoDeUso() != null && localForm.getCodCessaoDeUso().length() > 0) {
				this.addMessage("SUCESSO.41", new String[]{"Cessão de Uso", cessaoDeUso.getCodCessaoDeUso().toString(), cessaoDeUso.getStatusTermo().getDescricao(),
						Mensagem.getInstance().getMessage("SUCESSO.45")}, request);	
			} else {
				this.addMessage("SUCESSO.40", new String[]{"Cessão de Uso", cessaoDeUso.getCodCessaoDeUso().toString(), Mensagem.getInstance().getMessage("SUCESSO.45")}, request);	
			}

			localForm.setCodCessaoDeUso(cessaoDeUso.getCodCessaoDeUso().toString());
			localForm.limparCamposItem();
			populaForm(localForm, request);

			return mapping.findForward("pgEditCessaoDeUso");

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditCessaoDeUso"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditCessaoDeUso"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".adicionarItemCessaoDeUsoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	/**
	 * Realiza o encaminhamento necessário para adicionar uma assinatura a Cessão de Uso.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward adicionarAssinaturaCessaoDeUsoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método adicionarAssinaturaCessaoDeUsoBemImovel processando...");
		try	{			
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;

			// Aciona a validação do Form
			validaDadosForm(localForm, "assinatura", request);
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			
			CessaoDeUso cessaoDeUso = new CessaoDeUso();
			if (localForm.getCodCessaoDeUso() != null && localForm.getCodCessaoDeUso().length() > 0) {
				cessaoDeUso = OperacaoFacade.obterCessaoDeUsoCompleto(Integer.valueOf(localForm.getCodCessaoDeUso()));
			} else {
				cessaoDeUso.setBemImovel(CadastroFacade.obterBemImovelSimplificado(Integer.valueOf(localForm.getCodBemImovel())));
			}
			if (localForm.getCodCessaoDeUso() != null && localForm.getCodCessaoDeUso().length() > 0) {
				this.populaPojoRegistro(cessaoDeUso, "A", request);
			} else {
				this.populaPojoRegistro(cessaoDeUso, "I", request);
			}

			this.verificarCessaoDeUsoByBemImovelStatusTermo(localForm.getCodCessaoDeUso(), cessaoDeUso, "A");

			if (localForm.getCodCessaoDeUso() != null && localForm.getCodCessaoDeUso().trim().length() > 0) {
				this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()), Integer.valueOf(localForm.getCodCessaoDeUso()));
			} else {
				this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()), Integer.valueOf(0));
			}
			
			this.populaPojo(cessaoDeUso, localForm, request);

			if (localForm.getProjetoLei() != null && localForm.getProjetoLei().equalsIgnoreCase("N")) {
				validarLeiBemImovel(cessaoDeUso.getLeiBemImovel(), localForm);
				cessaoDeUso = OperacaoFacade.salvarCessaoDeUsoLeiBemImovel(cessaoDeUso, cessaoDeUso.getLeiBemImovel());
			} else {
				cessaoDeUso = OperacaoFacade.salvarCessaoDeUso(cessaoDeUso);
			}

			AssinaturaCessaoDeUso assinaturaCessaoDeUso = new AssinaturaCessaoDeUso();
			assinaturaCessaoDeUso.setCessaoDeUso(cessaoDeUso);
			assinaturaCessaoDeUso.setAssinatura(CadastroFacade.obterAssinatura(Integer.valueOf(localForm.getNomeAssinatura())));
			assinaturaCessaoDeUso.setOrdem(Integer.valueOf(localForm.getOrdemAssinatura()));
			
			AssinaturaCessaoDeUso assinaturaCessaoDeUsoAux = assinaturaCessaoDeUso;
			boolean mesmoObjeto = false;
			// Verifica se já existe com CessaoDeUso | ordem e assinatura
			Collection<AssinaturaCessaoDeUso> listDuplicidadeAD = OperacaoFacade.verificarDuplicidadeAssinaturaCessaoDeUso(assinaturaCessaoDeUso); 
			if (listDuplicidadeAD.size() > 0) {
				AssinaturaCessaoDeUso assinaturaCessaoDeUsoDB;
				for (Iterator<AssinaturaCessaoDeUso> iterator  = listDuplicidadeAD.iterator(); iterator.hasNext();) {
					assinaturaCessaoDeUsoDB = (AssinaturaCessaoDeUso) iterator .next();
					if (assinaturaCessaoDeUsoDB.getOrdem().equals(assinaturaCessaoDeUsoAux.getOrdem())) {
						mesmoObjeto = true;
					}
				}
				if (mesmoObjeto) {
					throw new ApplicationException("AVISO.76", ApplicationException.ICON_AVISO);
				}
				mesmoObjeto = false;
				//verifica se é a mesma assinatura
				for (Iterator<AssinaturaCessaoDeUso> iterator  = listDuplicidadeAD.iterator(); iterator.hasNext();) {
					assinaturaCessaoDeUsoDB = (AssinaturaCessaoDeUso) iterator .next();
					if (assinaturaCessaoDeUsoDB.getAssinatura().getCodAssinatura().equals(assinaturaCessaoDeUsoAux.getAssinatura().getCodAssinatura())) {
						mesmoObjeto = true;
					}
				}
				if (mesmoObjeto) {
					throw new ApplicationException("AVISO.55", ApplicationException.ICON_AVISO);
				}
				mesmoObjeto = false;
				//verifica se é do mesmo cargo
				for (Iterator<AssinaturaCessaoDeUso> iterator  = listDuplicidadeAD.iterator(); iterator.hasNext();) {
					assinaturaCessaoDeUsoDB = (AssinaturaCessaoDeUso) iterator .next();
					if (assinaturaCessaoDeUsoDB.getAssinatura().getCargoAssinatura().getCodCargoAssinatura().equals(assinaturaCessaoDeUsoAux.getAssinatura().getCargoAssinatura().getCodCargoAssinatura())) {
						mesmoObjeto = true;
					}
				}
				if (mesmoObjeto) {
					throw new ApplicationException("AVISO.103", ApplicationException.ICON_AVISO);
				}
			}

			OperacaoFacade.salvarAssinaturaCessaoDeUso(assinaturaCessaoDeUso);
			
			if (localForm.getCodCessaoDeUso() != null && localForm.getCodCessaoDeUso().length() > 0) {
				this.addMessage("SUCESSO.41", new String[]{"Cessão de Uso", cessaoDeUso.getCodCessaoDeUso().toString(), cessaoDeUso.getStatusTermo().getDescricao(),
						Mensagem.getInstance().getMessage("SUCESSO.43")}, request);	
			} else {
				this.addMessage("SUCESSO.40", new String[]{"Cessão de Uso", cessaoDeUso.getCodCessaoDeUso().toString(), Mensagem.getInstance().getMessage("SUCESSO.43")}, request);	
			}

			localForm.setCodCessaoDeUso(cessaoDeUso.getCodCessaoDeUso().toString());
			localForm.limparCamposAssinatura();
			populaForm(localForm, request);

			return mapping.findForward("pgEditCessaoDeUso");

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditCessaoDeUso"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditCessaoDeUso"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".adicionarAssinaturaCessaoDeUsoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	private void carregarListaItemCessaoDeUsoAssinatura(ActionForm form, HttpServletRequest request) throws ApplicationException {
		try {
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm)form;
			String codCessaoDeUso = localForm.getCodCessaoDeUso();
			if (codCessaoDeUso == null || codCessaoDeUso.isEmpty() || codCessaoDeUso.trim().length() < 1) {
				request.getSession().setAttribute("listItemCessaoDeUso", null);
				request.getSession().setAttribute("listAssinatura", null);
			} else {
				Pagina pagina = new Pagina(null, null, null);
				request.getSession().setAttribute("listItemCessaoDeUso", OperacaoFacade.listarItemCessaoDeUso(pagina, Integer.valueOf(codCessaoDeUso)));
				pagina = new Pagina(null, null, null);
				request.getSession().setAttribute("listAssinatura", OperacaoFacade.listarAssinaturaCessaoDeUso(pagina, Integer.valueOf(codCessaoDeUso)));

				ItemCessaoDeUso itemCessaoDeUso = new ItemCessaoDeUso();
				itemCessaoDeUso.setCessaoDeUso(new CessaoDeUso());
				itemCessaoDeUso.getCessaoDeUso().setCodCessaoDeUso(Integer.valueOf(codCessaoDeUso));
				Collection<ItemCessaoDeUso> list = OperacaoFacade.verificarDuplicidadeItemCessaoDeUso(itemCessaoDeUso);
				for (ItemCessaoDeUso item : list) {
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
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".carregarListaItemCessaoDeUsoAssinatura"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Carrega os dados da Lei Bem Imóvel informado
	 * @param form
	 */
	public ActionForward carregarDadosLeiBemImovel (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarDadosLeiBemImovel processando...");
		try {
			Long numeroLei = ((request.getParameter("numeroLei") == null || request.getParameter("numeroLei").isEmpty())
					? 0 : Long.valueOf(request.getParameter("numeroLei")));

			if (numeroLei != null && numeroLei > 0){
				CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
				LeiBemImovel leiBemImovel = CadastroFacade.obterLeiBemImovelPorNumero(new Long(localForm.getNumeroLei()));
				localForm.setProjetoLei("N");
				if (leiBemImovel != null) {
					localForm.setCodLei(leiBemImovel.getCodLeiBemImovel().toString());
					localForm.setNumeroLei(Long.toString(leiBemImovel.getNumero()));
					localForm.setDataAssinaturaLei(Data.formataData(leiBemImovel.getDataAssinatura()));
					localForm.setDataPublicacaoLei(Data.formataData(leiBemImovel.getDataPublicacao()));
					localForm.setNrDioeLei(Long.toString(leiBemImovel.getNrDioe()));
				} else {
					throw new ApplicationException("AVISO.61", ApplicationException.ICON_AVISO);
				}
			}
			
			return mapping.findForward("pgEditLeiBemImovelCessaoDeUso");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditLeiBemImovelCessaoDeUso"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditLeiBemImovelCessaoDeUso"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".carregarDadosLeiBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Realiza o encaminhamento necessário para adicionar um item a Cessão de Uso.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward excluirItemCessaoDeUsoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método excluirItemCessaoDeUsoBemImovel processando...");
		try	{			
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			
			CessaoDeUso cessaoDeUso = OperacaoFacade.obterCessaoDeUsoCompleto(Integer.valueOf(localForm.getCodCessaoDeUso()));

			this.populaPojoRegistro(cessaoDeUso, "A", request);

			this.verificarCessaoDeUsoByBemImovelStatusTermo(localForm.getCodCessaoDeUso(), cessaoDeUso, "A");

			this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()), Integer.valueOf(localForm.getCodCessaoDeUso()));
			
			this.populaPojo(cessaoDeUso, localForm, request);

			if (localForm.getProjetoLei() != null && localForm.getProjetoLei().equalsIgnoreCase("N")) {
				validarLeiBemImovel(cessaoDeUso.getLeiBemImovel(), localForm);
				cessaoDeUso = OperacaoFacade.salvarCessaoDeUsoLeiBemImovel(cessaoDeUso, cessaoDeUso.getLeiBemImovel());
			} else {
				cessaoDeUso = OperacaoFacade.salvarCessaoDeUso(cessaoDeUso);
			}

			ItemCessaoDeUso itemCessaoDeUso = OperacaoFacade.obterItemCessaoDeUso(Integer.valueOf(localForm.getCodItemCessaoDeUso()));
			
			OperacaoFacade.excluirItemCessaoDeUso(itemCessaoDeUso);
			
			this.addMessage("SUCESSO.41", new String[]{"Cessão de Uso", cessaoDeUso.getCodCessaoDeUso().toString(), cessaoDeUso.getStatusTermo().getDescricao(),
					Mensagem.getInstance().getMessage("SUCESSO.46")}, request);	

			localForm.setCodCessaoDeUso(cessaoDeUso.getCodCessaoDeUso().toString());
			localForm.limparCamposItem();
			localForm.setCodItemCessaoDeUso(null);

			populaForm(localForm, request);

			return mapping.findForward("pgEditCessaoDeUso");

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditCessaoDeUso"));
			populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditCessaoDeUso"));
			populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".excluirItemCessaoDeUsoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	/**
	 * Realiza o encaminhamento necessário para adicionar um item a Cessão de Uso.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward excluirAssinaturaCessaoDeUsoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método excluirAssinaturaCessaoDeUsoBemImovel processando...");
		try	{			
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			
			CessaoDeUso cessaoDeUso = OperacaoFacade.obterCessaoDeUsoCompleto(Integer.valueOf(localForm.getCodCessaoDeUso()));

			this.populaPojoRegistro(cessaoDeUso, "A", request);

			this.verificarCessaoDeUsoByBemImovelStatusTermo(localForm.getCodCessaoDeUso(), cessaoDeUso, "A");

			this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()), Integer.valueOf(localForm.getCodCessaoDeUso()));
			
			this.populaPojo(cessaoDeUso, localForm, request);

			if (localForm.getProjetoLei() != null && localForm.getProjetoLei().equalsIgnoreCase("N")) {
				validarLeiBemImovel(cessaoDeUso.getLeiBemImovel(), localForm);
				cessaoDeUso = OperacaoFacade.salvarCessaoDeUsoLeiBemImovel(cessaoDeUso, cessaoDeUso.getLeiBemImovel());
			} else {
				cessaoDeUso = OperacaoFacade.salvarCessaoDeUso(cessaoDeUso);
			}
			AssinaturaCessaoDeUso assinaturaCessaoDeUso = OperacaoFacade.obterAssinaturaCessaoDeUso(Integer.valueOf(localForm.getCodAssinaturaCessaoDeUso()));
			
			OperacaoFacade.excluirAssinaturaCessaoDeUso(assinaturaCessaoDeUso);
			
			this.addMessage("SUCESSO.41", new String[]{"Cessão de Uso", cessaoDeUso.getCodCessaoDeUso().toString(), cessaoDeUso.getStatusTermo().getDescricao(),
					Mensagem.getInstance().getMessage("SUCESSO.44")}, request);	

			localForm.setCodCessaoDeUso(cessaoDeUso.getCodCessaoDeUso().toString());
			localForm.limparCamposAssinatura();
			localForm.setCodAssinaturaCessaoDeUso(null);

			populaForm(localForm, request);
			
			return mapping.findForward("pgEditCessaoDeUso");

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditCessaoDeUso"));
			populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditCessaoDeUso"));
			populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".excluirAssinaturaCessaoDeUsoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 

	}

	/**
	 * Realiza o encaminhamento necessário para alterar a Cessão de Uso.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward revogDevCessaoDeUsoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método revogDevCessaoDeUsoBemImovel processando...");
		try	{			
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
			
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
			
			CessaoDeUso cessaoDeUso = new CessaoDeUso();
			if (localForm.getCodCessaoDeUso() != null && localForm.getCodCessaoDeUso().length() > 0) {
				cessaoDeUso = OperacaoFacade.obterCessaoDeUsoCompleto(Integer.valueOf(localForm.getCodCessaoDeUso()));
			} else {
				cessaoDeUso.setBemImovel(CadastroFacade.obterBemImovelSimplificado(Integer.valueOf(localForm.getCodBemImovel())));
			}
			if (localForm.getTipoRevogDev().equals("1")) {
				cessaoDeUso.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.REVOGADO.getIndex()));
			} else if (localForm.getTipoRevogDev().equals("2")) {
				cessaoDeUso.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.DEVOLVIDO.getIndex()));
			}
			cessaoDeUso.setMotivoRevogacaoDevolucao(localForm.getMotivo());
			cessaoDeUso.setDataFinalVigencia(new Date());
			this.populaPojoRegistro(cessaoDeUso, "A", request);
			Integer codCessaoDeUso = cessaoDeUso.getCodCessaoDeUso();
			cessaoDeUso = OperacaoFacade.salvarCessaoDeUso(cessaoDeUso);
			
			OperacaoFacade.excluirCessaoDeUsoBemImovelRascunhoRenovacao(cessaoDeUso);
			
			saveToken(request);
			if (localForm.getTipoRevogDev().equals("1")) {
				this.addMessage("SUCESSO.47", new String[]{"Revogação", "Cessão de Uso", codCessaoDeUso.toString()}, request);	
			} else if (localForm.getTipoRevogDev().equals("2")) {
				this.addMessage("SUCESSO.47", new String[]{"Devolução", "Cessão de Uso", codCessaoDeUso.toString()}, request);	
			}
			localForm.limparCampos();
			return this.carregarPgListCessaoDeUsoBemImovel(mapping, form, request, response);

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgViewCessaoDeUso"));
			populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgViewCessaoDeUso"));
			populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{CessaoDeUsoBemImovelAction.class.getSimpleName()+".revogDevCessaoDeUsoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	// UC GERAR TERMO
	
	/**
	 * Redireciona para o UCS GerarTermoCessaoDeUsoBemImovel.<br>
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
			
			CessaoDeUsoBemImovelForm localForm = (CessaoDeUsoBemImovelForm) form;
			localForm.setUcsDestino("ucsGerarTermoCessaoDeUsoBemImovel");
			localForm.setUcsChamador("ucsCessaoDeUsoBemImovel");
			
			request.setAttribute("ucsChamador", "ucsCessaoDeUsoBemImovel");
			request.setAttribute("codCessaoDeUso", localForm.getCodCessaoDeUso());
			
			return acionarOutroCasoUso(mapping, localForm, request, response);
					
		} catch (ApplicationException ae) {
			throw ae;
		} catch (Exception e) {
			setActionForward(carregarPgListCessaoDeUsoBemImovel(mapping, form, request, response));
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
						
			CessaoDeUsoBemImovelForm frm = (CessaoDeUsoBemImovelForm) form;		
			frm.setUcsRetorno(frm.getUcsChamador()); 
			
			CessaoDeUso cessao = OperacaoFacade.obterCessaoDeUsoCompleto(Integer.valueOf(frm.getCodCessaoDeUso()));
			if(Integer.valueOf(Dominios.statusTermo.RASCUNHO.getIndex()).equals(cessao.getStatusTermo().getCodStatusTermo())){
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
	public void armazenarFormSessao(HttpServletRequest request, CessaoDeUsoBemImovelForm form) throws ApplicationException{

		try{
			Stack<CessaoDeUsoBemImovelForm> pilhaForm = (Stack<CessaoDeUsoBemImovelForm>)Sessao.obterAtributoSessao(request, PILHA_FORM_SESSAO); 
			
			if(pilhaForm == null){
				pilhaForm = new Stack<CessaoDeUsoBemImovelForm>();
			}

			//Evitar que se carrege o mesmo form na pilha atraves do refresh
			CessaoDeUsoBemImovelForm ultimoPilha = this.obterFormSessao(request);
			
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
	public CessaoDeUsoBemImovelForm obterFormSessao(HttpServletRequest request) throws ApplicationException{

		try{
			Stack<CessaoDeUsoBemImovelForm> pilhaForm = (Stack<CessaoDeUsoBemImovelForm>)Sessao.obterAtributoSessao(request, PILHA_FORM_SESSAO); 
			if(pilhaForm == null){
				return null;
			}
			
			if(pilhaForm.isEmpty()){
				Sessao.removerAtributoDaSessao(request, PILHA_FORM_SESSAO);
				return null;
			}
			CessaoDeUsoBemImovelForm bf = pilhaForm.pop();
			armazenarFormSessao(request, bf);
			return bf;
			
		}catch (Exception e) {	
			log.error(e.getMessage(),e);			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".obterFormSessao()"}, e, ApplicationException.ICON_ERRO);
		}
			
	}
	
	public CessaoDeUsoBemImovelForm desempilharFormSession(HttpServletRequest request) throws ApplicationException{
		try{

			Object obj = Sessao.obterAtributoSessao(request, PILHA_FORM_SESSAO);
			if (obj == null) {
				return null;
			} 

			Stack<CessaoDeUsoBemImovelForm> pilhaForm = (Stack<CessaoDeUsoBemImovelForm>)obj; 
			if(pilhaForm == null){
				return null;
			}

			CessaoDeUsoBemImovelForm manterSuperForm = null;
			if (pilhaForm.size() > 0) {
				if (pilhaForm.get(0) instanceof CessaoDeUsoBemImovelForm) {
					manterSuperForm = pilhaForm.pop();
					manterSuperForm.setCodCessaoDeUso("");
				}
			}
			Sessao.removerAtributoDaSessao(request, PILHA_FORM_SESSAO);

			return manterSuperForm;
		}catch (Exception e) {			
			log.error(e.getMessage(),e);			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".desempilharFormSession()"}, e, ApplicationException.ICON_ERRO);
		}
	}	

	private void verificarGrupoUsuarioLogado(CessaoDeUsoBemImovelForm localForm, HttpServletRequest request) throws ApplicationException {
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