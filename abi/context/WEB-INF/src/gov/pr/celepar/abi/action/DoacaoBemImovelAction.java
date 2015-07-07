package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.ItemComboDTO;
import gov.pr.celepar.abi.dto.ItemDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.facade.OperacaoFacade;
import gov.pr.celepar.abi.form.DoacaoBemImovelForm;
import gov.pr.celepar.abi.pojo.AssinaturaDoacao;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.abi.pojo.Doacao;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.ItemDoacao;
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

public class DoacaoBemImovelAction extends BaseDispatchAction {

	private static final String PILHA_FORM_SESSAO = "pilhaFormSessao";
	private static final Integer EXCETO_TIPO_LEI = Integer.valueOf(2);
	private static final Integer TIPO_LEI = Integer.valueOf(4);
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
	public ActionForward carregarPgListDoacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {

		log.info("Método carregarPgListDoacaoBemImovel processando...");

		try {
			saveToken(request);

			localizarBemImovel = false;
			localizarLeiBemImovel = false;
			request.getSession().setAttribute("bemImovelSimplificado", null);
			request.getSession().setAttribute("execBuscaBemImovel", null);
			request.getSession().setAttribute("cedidoPara", null);
			request.getSession().setAttribute("listaSituacaoPesquisa", Util.htmlEncodeCollection(OperacaoFacade.listarStatusTermoCombo()));
			request.getSession().setAttribute("listaPesquisaInstituicao", CadastroFacade.listarComboInstituicao());	

			DoacaoBemImovelForm localFormSession = this.desempilharFormSession(request);
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm)form;

			verificarGrupoUsuarioLogado(localForm, request);
			
			if (localFormSession != null) {
				localForm.setActionType(localFormSession.getActionType());
				localForm.setPesqExec(localFormSession.getPesqExec());
				localForm.setConNrTermo(localFormSession.getConNrTermo());
				localForm.setConNrBemImovel(localFormSession.getConNrBemImovel());
				localForm.setConCodBemImovel(localFormSession.getConCodBemImovel());
				localForm.setConProtocolo(localFormSession.getConProtocolo());
				localForm.setConSituacao(localFormSession.getConSituacao());
				localForm.setConAdministracao(localFormSession.getConAdministracao());
				localForm.setConInstituicao(localFormSession.getConInstituicao());
				localForm.setConOrgaoResponsavel(localFormSession.getConOrgaoResponsavel());
				localForm.setUf(localFormSession.getUf());
				localForm.setCodMunicipio(localFormSession.getCodMunicipio());
			}

			localForm.setActionType("pesquisar");
			if (localForm.getPesqExec() != null && localForm.getPesqExec().equals("S")) {
				this.pesquisarDoacaoBemImovel(mapping, localForm, request, response);
			} else {
				request.getSession().setAttribute("pagina", null);
				localForm.setUf("PR");
			}
			return mapping.findForward("pgConsultaDoacao");
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgConsultaDoacao"));
			log.error(appEx);
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgConsultaDoacao"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".carregarPgListDoacaoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
	}
	
	/**
	 * Carrega o combobox de orgãos de acordo com a Administração selecionada
	 * @param form
	 */
	public ActionForward carregarComboOrgaoPesquisa (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboOrgaoPesquisa processando...");
		try {
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
			String param = (StringUtils.isNotBlank(localForm.getConAdministracao()))?localForm.getConAdministracao():null;
			verificarGrupoUsuarioLogado(localForm, request);
			String instituicao = (StringUtils.isNotBlank(localForm.getConInstituicao()))?localForm.getConInstituicao():null;
			
			if (param != null){
				if (instituicao != null) {
					request.getSession().setAttribute("orgaosPesquisar", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(param), Integer.valueOf(instituicao))));
				} else {
					request.getSession().setAttribute("orgaosPesquisar", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Integer.valueOf(param), SentinelaComunicacao.getInstance(request).getCodUsuario())));
				}
			} else {
				request.getSession().setAttribute("orgaosPesquisar", null);
			}
			
			return mapping.findForward("pgComboOrgaoPesquisarDoacao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboOrgaoPesquisarDoacao"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboOrgaoPesquisarDoacao"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".carregarComboOrgaoPesquisa"}, e, ApplicationException.ICON_ERRO);
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
	public ActionForward pesquisarDoacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {
		log.info("Método pesquisarDoacaoBemImovel processando...");
		try{
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm)form;

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
			
			Doacao objPesquisa = new Doacao();
			objPesquisa.setCodDoacao((localForm.getConNrTermo() == null || localForm.getConNrTermo().trim().length() == 0) ? null: Integer.valueOf(localForm.getConNrTermo().trim()));
			objPesquisa.setBemImovel(new BemImovel());
			objPesquisa.getBemImovel().setNrBemImovel((localForm.getConNrBemImovel() == null || localForm.getConNrBemImovel().trim().length() == 0) ? null: Integer.valueOf(localForm.getConNrBemImovel().trim()));
			objPesquisa.getBemImovel().setCodBemImovel((localForm.getConCodBemImovel() == null || localForm.getConCodBemImovel().trim().length() == 0) ? null: Integer.valueOf(localForm.getConCodBemImovel().trim()));
			objPesquisa.setInstituicao(new Instituicao());
			objPesquisa.getInstituicao().setCodInstituicao((localForm.getConInstituicao() == null || localForm.getConInstituicao().trim().length() == 0) ? null: Integer.valueOf(localForm.getConInstituicao().trim()));
			objPesquisa.setProtocolo(localForm.getConProtocolo() == null ? "" : localForm.getConProtocolo().trim());
			objPesquisa.setStatusTermo(new StatusTermo());
			objPesquisa.getStatusTermo().setCodStatusTermo((localForm.getConSituacao() == null || localForm.getConSituacao().trim().length() == 0)? null: Integer.valueOf(localForm.getConSituacao().trim()));
			objPesquisa.setAdministracao((localForm.getConAdministracao() == null || localForm.getConAdministracao().trim().length() == 0)? null : Integer.valueOf(localForm.getConAdministracao().trim()));
			objPesquisa.setOrgaoResponsavel(new Orgao());
			objPesquisa.getOrgaoResponsavel().setCodOrgao((localForm.getConOrgaoResponsavel() == null || localForm.getConOrgaoResponsavel().trim().length() == 0)? null: Integer.valueOf(localForm.getConOrgaoResponsavel().trim()));
			objPesquisa.getBemImovel().setUf(localForm.getUf() == null ? "": localForm.getUf().trim());
			objPesquisa.getBemImovel().setCodMunicipio(localForm.getCodMunicipio() == null ? null: Integer.valueOf(localForm.getCodMunicipio().trim()));
			
			Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), numPagina, totalRegistros);

			pagina = OperacaoFacade.listarDoacao(pagina, objPesquisa);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.getSession().setAttribute("pagina", pagina);
			if (pagina.getTotalRegistros()==0){
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			return mapping.findForward("pgConsultaDoacao");		
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgConsultaDoacao"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgConsultaDoacao"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".pesquisarDoacaoBemImovel"}, e, ApplicationException.ICON_ERRO);
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
		request.getSession().setAttribute("listItemDoacao", null);
		request.getSession().setAttribute("listAssinatura", null);
		request.getSession().setAttribute("orgaos", null);
		request.getSession().setAttribute("edificacoes", null);
		request.getSession().setAttribute("orgaosAssinatura", null);
		request.getSession().setAttribute("cargosAssinatura", null);
		request.getSession().setAttribute("nomesAssinatura", null);

		DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
		localForm.limparCampos();
		localForm.setCodDoacao(null);
		return this.carregarPgListDoacaoBemImovel(mapping, form, request, response);
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
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);
			Integer codBemImovelSimpl = (request.getParameter("codBemImovelSimpl") == null ? 1 : Integer.valueOf(request.getParameter("codBemImovelSimpl")));
			Integer nrBemImovel = (request.getParameter("nrBemImovel") == null ? 1 : Integer.valueOf(request.getParameter("nrBemImovel")));
			Integer codInstituicao = (request.getParameter("codInstituicao") == null ? 1 : Integer.valueOf(request.getParameter("codInstituicao")));
						
			String camposPesquisaUCOrigem = request.getParameter("camposPesquisaUCOrigem") != null ? request.getParameter("camposPesquisaUCOrigem").toString() : "";		
			String[] valores = camposPesquisaUCOrigem.split(";");
			localForm.setConNrTermo(valores[0]);
			localForm.setConProtocolo(valores[1]);
			localForm.setConSituacao(valores[2]);
			localForm.setConAdministracao(valores[3]);
			if (localForm.getConAdministracao().equals("2") || localForm.getConAdministracao().equals("3")) {
				request.getSession().setAttribute("orgaosPesquisar", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(valores[3]), Integer.valueOf(valores[9]))));
			}
			localForm.setConOrgaoResponsavel(valores[4]);
			localForm.setUf(valores[5]);
			localForm.setCodMunicipio(valores[6]);
			localForm.setActionType(valores[8]);
			
			localizarBemImovel = true;
			
			if (valores[7].equalsIgnoreCase("P"))  {
				localForm.setConCodBemImovel(String.valueOf(codBemImovelSimpl));
				localForm.setConNrBemImovel(String.valueOf(nrBemImovel));
				localForm.setConInstituicao(String.valueOf(codInstituicao));
				return this.carregarPgListDoacaoBemImovel(mapping, form, request, response);
			} else {
				localForm.setCodBemImovel(String.valueOf(codBemImovelSimpl));
				localForm.setNrBemImovel(String.valueOf(nrBemImovel));
				localForm.setInstituicao(String.valueOf(codInstituicao));

				localForm.setConInstituicao(valores[9]);
				localForm.setConCodBemImovel(valores[10].trim());
				localForm.setConNrBemImovel(valores[11].trim());

				return this.carregarPgEditDoacaoBemImovel(mapping, form, request, response);
			}
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".retornoLocalizarBemImovel"}, e, ApplicationException.ICON_ERRO);
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
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
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
			localForm.setConAdministracao(valores[3]);
			
			if (localForm.getConAdministracao().equals("2") || localForm.getConAdministracao().equals("3")) {
				request.getSession().setAttribute("orgaosPesquisar", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(valores[3]), Integer.valueOf(valores[9]))));
			}
			
			localForm.setConOrgaoResponsavel(valores[4]);
			localForm.setUf(valores[5]);
			localForm.setCodMunicipio(valores[6]);
			localForm.setActionType(valores[8]);
			localForm.setCodBemImovel(valores[9]);
			localForm.setCodDoacao(valores[10]);
			localForm.setConInstituicao(valores[11]);
			localForm.setInstituicao(valores[12]);
			localForm.setNrBemImovel(valores[13]);

			localizarLeiBemImovel = true;
			return this.carregarPgEditDoacaoBemImovel(mapping, localForm, request, response);

		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".retornoLocalizarLeiBemImovel"}, e, ApplicationException.ICON_ERRO);
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
	public ActionForward carregarPgEditDoacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {

		log.info("Método carregarPgEditDoacaoBemImovel processando...");
		try {
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm)form;

			if (localForm.getActionType().equals("incluir")) {
				request.getSession().setAttribute("bemImovelSimplificado", null);
				request.getSession().setAttribute("execBuscaBemImovel", null);
				request.getSession().setAttribute("cedidoPara", null);
				request.getSession().setAttribute("listItemDoacao", null);
				request.getSession().setAttribute("listAssinatura", null);
				request.getSession().setAttribute("orgaos", null);
				request.getSession().setAttribute("edificacoes", null);
				request.getSession().setAttribute("orgaosAssinatura", null);
				request.getSession().setAttribute("cargosAssinatura", null);
				request.getSession().setAttribute("nomesAssinatura", null);
				request.getSession().setAttribute("listaInstituicao", null);
				if (!localizarBemImovel && !localizarLeiBemImovel) {
					localForm.limparCampos();
					localForm.setCodDoacao(null);
					verificarGrupoUsuarioLogado(localForm, request);
				}
			} else {
				verificarGrupoUsuarioLogado(localForm, request);
			}

			request.getSession().setAttribute("listaInstituicao", CadastroFacade.listarComboInstituicao());	
			if (localForm.getActionType().equals("gerarNova")) {
				SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
				Doacao doacao = OperacaoFacade.gerarNovaDoacao(Integer.valueOf(localForm.getCodDoacao()), sentinelaInterface.getCpf());
				this.addMessage("SUCESSO.42", new String[]{"Doação", localForm.getCodDoacao(), doacao.getCodDoacao().toString(), Dominios.statusTermo.RASCUNHO.getLabel()}, request);
				localForm.setActionType("alterar");
				localForm.setCodDoacao(doacao.getCodDoacao().toString());
				populaForm(localForm, request);
			} else if (localForm.getCodDoacao() != null && localForm.getCodDoacao().length() > 0) {
				populaForm(localForm, request);
			}

			return mapping.findForward("pgEditDoacao");				
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgConsultaDoacao"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgConsultaDoacao"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".carregarPgEditDoacaoBemImovel"}, e, ApplicationException.ICON_ERRO);
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
	public ActionForward carregarPgViewDoacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {

		log.info("Método carregarPgViewDoacaoBemImovel processando...");
		try {
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm)form;

			if (localForm.getCodDoacao() != null && localForm.getCodDoacao().length() > 0) {
				populaForm(localForm, request);
			}

			return mapping.findForward("pgViewDoacao");				
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgViewDoacao"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgViewDoacao"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".carregarPgViewDoacaoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private void populaForm(ActionForm form, HttpServletRequest request) throws ApplicationException {
		try {
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm)form;
			verificarGrupoUsuarioLogado(localForm, request);

			Doacao doacao = OperacaoFacade.obterDoacaoCompleto((StringUtils.isNotBlank(localForm.getCodDoacao()))?Integer.valueOf(localForm.getCodDoacao()):0);
			
			this.carregarRealizadoPor(request, form, doacao);
			localForm.setAdministracao(doacao.getAdministracao().toString());
			localForm.setCodBemImovel(doacao.getBemImovel().getCodBemImovel().toString());
			localForm.setNrBemImovel(doacao.getBemImovel().getNrBemImovel().toString());
			if (doacao.getInstituicao() != null && doacao.getInstituicao().getCodInstituicao() > 0) {
				localForm.setInstituicaoDesc(CadastroFacade.obterInstituicao(doacao.getInstituicao().getCodInstituicao()).getSiglaDescricao());
				localForm.setInstituicao(doacao.getInstituicao().getCodInstituicao().toString());
			}
			carregarListaItemDoacaoAssinatura(localForm, request);
			
			localForm.setCodDoacao(doacao.getCodDoacao().toString());
			if (doacao.getStatusTermo() != null) {
				localForm.setStatus(Dominios.statusTermo.getStatusTermoByIndex(doacao.getStatusTermo().getCodStatusTermo()).getLabel());
			} else {
				localForm.setStatus(" --- ");
			}
			localForm.setOrgaoResponsavel(doacao.getOrgaoResponsavel().getCodOrgao().toString());
			localForm.setDtInicioVigencia(Data.formataData(doacao.getDtInicioVigencia()));
			localForm.setDtFimVigencia(Data.formataData(doacao.getDtFimVigencia()));
			localForm.setProtocolo(doacao.getProtocolo());
			if (doacao.getNrProjetoLei() != null && doacao.getNrProjetoLei() > 0) {
				localForm.setProjetoLei("S");
				localForm.setProjetoLeiDesc("Sim");
				localForm.setNumeroLei(doacao.getNrProjetoLei().toString());
			} else {
				if (doacao.getLeiBemImovel() != null) {
					LeiBemImovel lei = CadastroFacade.obterLeiBemImovel(doacao.getLeiBemImovel().getCodLeiBemImovel());
					localForm.setProjetoLei("N");
					localForm.setProjetoLeiDesc("Não");
					localForm.setCodLei(lei.getCodLeiBemImovel().toString());
					localForm.setNumeroLei(Long.toString(lei.getNumero()));
					localForm.setDataAssinaturaLei(Data.formataData(lei.getDataAssinatura()));
					localForm.setDataPublicacaoLei(Data.formataData(lei.getDataPublicacao()));
					localForm.setNrDioeLei(Long.toString(lei.getNrDioe()));
				}
			}

			localForm.setAdministracaoDesc(Dominios.administracaoImovel.getAdministracaoImovelByIndex(doacao.getAdministracao()).getLabel());			
			localForm.setOrgaoResponsavelDesc(doacao.getOrgaoResponsavel().getSiglaDescricao());			
			if (localForm.getActionType().equalsIgnoreCase("revogDev")) {
				localForm.setTipoRevogDev("0");
				if (doacao.getNrOficio() != null && doacao.getMotivoRevogacao() != null) {
					if (doacao.getNrOficio() > 0 && doacao.getMotivoRevogacao().length() == 0) {
						localForm.setTipoRevogDev("2");
					} else if (doacao.getNrOficio() == 0 && doacao.getMotivoRevogacao().length() > 0) {
						localForm.setTipoRevogDev("1");
					}
					localForm.setMotivo(doacao.getMotivoRevogacao());
					localForm.setNrOficio(doacao.getNrOficio().toString());
				}
			} else {
				if (doacao.getStatusTermo().getCodStatusTermo() == Dominios.statusTermo.DEVOLVIDO.getIndex() ||
					doacao.getStatusTermo().getCodStatusTermo() == Dominios.statusTermo.REVOGADO.getIndex()) {
					if (doacao.getStatusTermo().getCodStatusTermo() == Dominios.statusTermo.DEVOLVIDO.getIndex()) {
						localForm.setTipoRevogDevDesc("Devolução");
					} else {
						localForm.setTipoRevogDevDesc("Revogação");
					}
					localForm.setMotivo(doacao.getMotivoRevogacao());
					if (doacao.getNrOficio() != null) {
						localForm.setNrOficio(doacao.getNrOficio().toString());
					}
				}
			}
			this.carregarCamposRequest(localForm, request);
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".populaForm"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private void carregarRealizadoPor(HttpServletRequest request, ActionForm form, Doacao doacao) throws ApplicationException {
		try {
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm)form;
			SentinelaParam sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByCPF(doacao.getCpfResponsavelInclusao());
			String aux = "";
			if (sentinelaParam != null) {
				aux = "Inclusão realizada por ";
				aux = aux.concat(sentinelaParam.getNome().trim());
				aux = aux.concat(" em ");
				aux = aux.concat(Data.formataData(doacao.getTsInclusao(),"dd/MM/yyyy HH:mm"));
				localForm.setIncluidoPor(aux);
			}
			if (doacao.getCpfResponsavelAlteracao() != null && doacao.getCpfResponsavelAlteracao().length() > 0){
				sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByCPF(doacao.getCpfResponsavelAlteracao());
				if (sentinelaParam != null) {
					aux = "Alteração realizada por ";
					aux = aux.concat(sentinelaParam.getNome().trim());
					aux = aux.concat(" em ");
					aux = aux.concat(Data.formataData(doacao.getTsAtualizacao(),"dd/MM/yyyy HH:mm"));
					localForm.setAlteradoPor(aux);
				}
			}
			if (doacao.getCpfResponsavelExclusao() != null && doacao.getCpfResponsavelExclusao().length() > 0){
				sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByCPF(doacao.getCpfResponsavelExclusao());
				if (sentinelaParam != null) {
					aux = "Exclusão realizada por ";
					aux = aux.concat(sentinelaParam.getNome().trim());
					aux = aux.concat(" em ");
					aux = aux.concat(Data.formataData(doacao.getTsExclusao(),"dd/MM/yyyy HH:mm"));
					localForm.setExcluidoPor(aux);
				}
			}
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".carregarRealizadoPor"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Carrega o combobox de orgãos de acordo com a Administração selecionada
	 * @param form
	 */
	public ActionForward carregarComboOrgao (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboOrgao processando...");
		try {
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);

			String param = (StringUtils.isNotBlank(localForm.getAdministracao()))?localForm.getAdministracao():null;
			String instituicao = (StringUtils.isNotBlank(localForm.getInstituicao()))?localForm.getInstituicao():null;
			
			if (param != null){
				if (instituicao != null) {
					request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(param), Integer.valueOf(instituicao))));
				} else {
					request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Integer.valueOf(param), SentinelaComunicacao.getInstance(request).getCodUsuario())));
				}
			} else {
				request.getSession().setAttribute("orgaos", null);
			}
			
			return mapping.findForward("pgComboOrgaoDoacao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboOrgaoDoacao"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboOrgaoDoacao"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".carregarComboOrgao"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Carrega o combobox de edificações de acordo com o Bem Imóvel selecionado
	 * @param form
	 */
	public ActionForward carregarComboEditEdificacaoBemImovel (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboEditEdificacaoBemImovel processando...");
		try {
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
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

			return mapping.findForward("pgComboEditEdificacaoBemImovelDoacao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboEditEdificacaoBemImovelDoacao"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboEditEdificacaoBemImovelDoacao"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".carregarComboEditEdificacaoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Realiza o cálculo de doação de % e metros quadrados.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public ActionForward calcularDoacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método calcularDoacaoBemImovel processando...");

		setActionForward(mapping.findForward("pgEditAreaDoacaoBemImovelDoacao"));
		try {
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);

			String codBemImovel = "0";
			if (localForm.getCodDoacao() != null && localForm.getCodDoacao().length() > 0) {
				codBemImovel = OperacaoFacade.obterDoacaoCompleto(Integer.valueOf(localForm.getCodDoacao())).getBemImovel().getCodBemImovel().toString();
			} else {
				if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
					BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
					localForm.setCodBemImovel(aux.getCodBemImovel().toString());
				}
				codBemImovel = localForm.getCodBemImovel();
			}
			
			ItemDTO obj = OperacaoFacade.calcularPercentualMetros(localForm.getTipoDoacao(), localForm.getDoacaoMetros(), 
					localForm.getDoacaoPercentual(), "Doação", localForm.getEdificacao(), codBemImovel);
			
			localForm.setDoacaoMetros(Valores.formatarParaDecimal(obj.getMetros(), 2));
			localForm.setDoacaoPercentual(Valores.formatarParaDecimal(obj.getPercentual(), 5));

		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".calcularDoacao"}, e, ApplicationException.ICON_ERRO);
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
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}

			Collection<ItemComboDTO> ret = montaComboOrgaoDoacaoAssinatura(localForm, request);
			if (ret != null && ret.size() > 0) {
				request.getSession().setAttribute("orgaosAssinatura", Util.htmlEncodeCollection(ret));
			} else {
				request.getSession().setAttribute("orgaosAssinatura", null);
			}
			
			return mapping.findForward("pgComboOrgaoDoacaoAssinatura");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboOrgaoDoacaoAssinatura"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboOrgaoDoacaoAssinatura"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".carregarComboOrgaoAssinatura"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private Collection<ItemComboDTO> montaComboOrgaoDoacaoAssinatura(DoacaoBemImovelForm localForm, HttpServletRequest request) throws ApplicationException {
		try {
			Collection<ItemComboDTO> listResult = new ArrayList<ItemComboDTO>();
			verificarGrupoUsuarioLogado(localForm, request);

			BemImovel bemImovel = CadastroFacade.obterBemImovelSimplificado(Integer.valueOf(localForm.getCodBemImovel()));

			//órgão responsável
			Orgao orgao1 = null;
			if (localForm.getOrgaoResponsavel() != null && !localForm.getOrgaoResponsavel().trim().isEmpty()) {
				orgao1 = CadastroFacade.obterOrgao(Integer.valueOf(localForm.getOrgaoResponsavel().trim()));
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
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".montaComboOrgaoDoacaoAssinatura"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Carrega o combobox de cargo de acordo com o Órgão da Assinatura selecionado
	 * @param form
	 */
	public ActionForward carregarComboCargoAssinatura (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboCargoAssinatura processando...");
		try {
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);

			String param = (StringUtils.isNotBlank(localForm.getOrgaoAssinatura()))?localForm.getOrgaoAssinatura():null;

			if (param != null){
				request.getSession().setAttribute("cargosAssinatura", Util.htmlEncodeCollection(CadastroFacade.listarComboCargoAssinaturaByInstituicao(Integer.valueOf(param), Integer.valueOf(localForm.getInstituicao()))));
			} else {
				request.getSession().setAttribute("cargosAssinatura", null);
			}

			return mapping.findForward("pgComboCargoDoacaoAssinatura");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboCargoDoacaoAssinatura"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboCargoDoacaoAssinatura"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".carregarComboCargoAssinatura"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Carrega o combobox de Nome de acordo com o Cargo e Órgão da Assinatura selecionados
	 * @param form
	 */
	public ActionForward carregarComboNomeAssinatura (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboNomeAssinatura processando...");
		try {
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);

			String orgao = (StringUtils.isNotBlank(localForm.getOrgaoAssinatura()))?localForm.getOrgaoAssinatura():null;
			String cargo = (StringUtils.isNotBlank(localForm.getCargoAssinatura()))?localForm.getCargoAssinatura():null;
			String instituicao = (StringUtils.isNotBlank(localForm.getInstituicao()))?localForm.getInstituicao():null;
			
			if (orgao != null && cargo != null){
				request.getSession().setAttribute("nomesAssinatura", Util.htmlEncodeCollection(CadastroFacade.listarNomeAssinaturaCombo(Integer.valueOf(orgao), Integer.valueOf(cargo), Integer.valueOf(instituicao))));
			} else {
				request.getSession().setAttribute("nomesAssinatura", null);
			}

			return mapping.findForward("pgComboNomeDoacaoAssinatura");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgComboNomeDoacaoAssinatura"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgComboNomeDoacaoAssinatura"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".carregarComboNomeAssinatura"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Realiza o encaminhamento necessário para salvar a Doação.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward salvarDoacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método salvarDoacaoBemImovel processando...");
		try	{
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
			
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
			Doacao doacao = new Doacao();
			if (localForm.getCodDoacao() != null && localForm.getCodDoacao().length() > 0) {
				doacao = OperacaoFacade.obterDoacaoCompleto(Integer.valueOf(localForm.getCodDoacao()));
			} else {
				doacao.setBemImovel(CadastroFacade.obterBemImovelSimplificado(Integer.valueOf(localForm.getCodBemImovel())));
			}
			if (localForm.getCodDoacao() != null && localForm.getCodDoacao().length() > 0) {
				this.populaPojoRegistro(doacao, "A", request);
			} else {
				this.populaPojoRegistro(doacao, "I", request);
			}
			
			this.verificarDoacaoByBemImovelStatusTermo(localForm.getCodDoacao(), doacao, "I");
			
			this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			
			this.populaPojo(doacao, localForm, request);

			if (localForm.getProjetoLei() != null && localForm.getProjetoLei().equalsIgnoreCase("N")) {
				validarLeiBemImovel(doacao.getLeiBemImovel(), localForm);
				doacao = OperacaoFacade.salvarDoacaoLeiBemImovel(doacao, doacao.getLeiBemImovel());
			} else {
				doacao = OperacaoFacade.salvarDoacao(doacao);
			}
			
			saveToken(request);
			localForm.limparCampos();
			this.addMessage("SUCESSO.40", new String[]{"Doação", doacao.getCodDoacao().toString(), ""}, request);	
			return this.carregarPgListDoacaoBemImovel(mapping, form, request, response);
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditDoacao"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditDoacao"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".salvarDoacaoBemImovel"}, e, ApplicationException.ICON_ERRO);
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
	private void verificarDoacaoByBemImovelStatusTermo(String codDoacao, Doacao doacao, String operacao) throws ApplicationException {
		if (codDoacao == null || codDoacao.trim().isEmpty()) {
			Collection<Doacao> listDuplicidade = OperacaoFacade.verificarDoacaoByBemImovelStatusTermo(doacao); 
			Doacao doacaoAux = doacao;
			boolean mesmoBI = false;
			if (listDuplicidade.size() > 0) {
				boolean rasc = false;
				String status = "";
				for (Doacao doacaoDB : listDuplicidade) {
					if (Dominios.statusTermo.RASCUNHO.getIndex() == doacaoDB.getStatusTermo().getCodStatusTermo()) {
						rasc = true;
					}
					status = status.concat(Dominios.statusTermo.getStatusTermoByIndex(doacaoDB.getStatusTermo().getCodStatusTermo()).getLabel());
					status = status.concat(" - ");
					if (!operacao.equalsIgnoreCase("I")) {
						if (doacaoDB.getBemImovel().equals(doacaoAux.getBemImovel())) {
							mesmoBI = true;
						}
					}
				}
				if (status.trim().length() > 2) {
					status = status.substring(0, status.length() - 3);
				}
				if (operacao.equalsIgnoreCase("I")) {
					if (rasc) {
						throw new ApplicationException("AVISO.58",  new String[]{"Doação", status}, ApplicationException.ICON_AVISO);
					} else {
						throw new ApplicationException("AVISO.66",  new String[]{"Doação", status}, ApplicationException.ICON_AVISO);
					}
				} else {
					if (mesmoBI) {
						if (rasc) {
							throw new ApplicationException("AVISO.58",  new String[]{"Doação", status}, ApplicationException.ICON_AVISO);
						} else {
							throw new ApplicationException("AVISO.66",  new String[]{"Doação", status}, ApplicationException.ICON_AVISO);
						}
					}
				}
			}
		}
	}

	private void validarLeiBemImovel(LeiBemImovel leiBemImovel, DoacaoBemImovelForm localForm) throws ApplicationException {
		if (leiBemImovel != null && leiBemImovel.getCodLeiBemImovel() != null && leiBemImovel.getCodLeiBemImovel() > 0 ){
			LeiBemImovel leiBemImovelBD = CadastroFacade.obterLeiBemImovelCompleto(leiBemImovel.getCodLeiBemImovel());
			if ((leiBemImovelBD.getTipoLeiBemImovel().getCodTipoLeiBemImovel().equals(EXCETO_TIPO_LEI))) {
				throw new ApplicationException("AVISO.63", new String[]{"Cessão de Uso"}, ApplicationException.ICON_AVISO);
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
	 * Realiza o encaminhamento necessário para alterar a Doação.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward alterarDoacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método alterarDoacaoBemImovel processando...");
		try	{			
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
			
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
			
			Doacao doacao = OperacaoFacade.obterDoacaoCompleto(Integer.valueOf(localForm.getCodDoacao()));

			this.populaPojoRegistro(doacao, "A", request);

			this.verificarDoacaoByBemImovelStatusTermo(localForm.getCodDoacao(), doacao, "A");

			this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			
			this.populaPojo(doacao, localForm, request);

			if (localForm.getProjetoLei() != null && localForm.getProjetoLei().equalsIgnoreCase("N")) {
				validarLeiBemImovel(doacao.getLeiBemImovel(), localForm);
				doacao = OperacaoFacade.salvarDoacaoLeiBemImovel(doacao, doacao.getLeiBemImovel());
			} else {
				doacao = OperacaoFacade.salvarDoacao(doacao);
			}
			
			saveToken(request);
			localForm.limparCampos();
			this.addMessage("SUCESSO.41", new String[]{"Doação", doacao.getCodDoacao().toString(), doacao.getStatusTermo().getDescricao(), ""}, request);	
			return this.carregarPgListDoacaoBemImovel(mapping, form, request, response);

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditDoacao"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditDoacao"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".alterarDoacaoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	/**
	 * Realiza o encaminhamento necessário para alterar a Doação.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward excluirDoacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método excluirDoacaoBemImovel processando...");
		try	{			
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
			
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	
			verificarGrupoUsuarioLogado(localForm, request);

			Doacao doacao = OperacaoFacade.obterDoacaoCompleto(Integer.valueOf(localForm.getCodDoacao()));
			this.populaPojoRegistro(doacao, "E", request);
			String codDoacao = doacao.getCodDoacao().toString(); 
			OperacaoFacade.excluirDoacao(doacao);
			saveToken(request);
			
			localForm.limparCampos();
			localForm.setCodDoacao(null);
			this.addMessage("SUCESSO.39", new String[]{"Doação", codDoacao}, request);	
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgViewDoacao"));
			populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgViewDoacao"));
			populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".excluirDoacaoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
		return this.carregarPgListDoacaoBemImovel(mapping, form, request, response);
	}

	private void validaDadosForm(DoacaoBemImovelForm localForm, String escopo, HttpServletRequest request) throws ApplicationException {
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
		if (localForm.getAdministracao() == null || localForm.getAdministracao().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Administração");
		}
		if (localForm.getOrgaoResponsavel() == null || localForm.getOrgaoResponsavel().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Órgão");
		}

		if (escopo != null && escopo.equalsIgnoreCase("Item")) {
			if (localForm.getTipoDoacao() == null || localForm.getTipoDoacao().trim().length() == 0) {
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Tipo de Doação");
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
		if (localForm.getDataAssinaturaLei() != null && localForm.getDataAssinaturaLei().trim().length() > 0) {
			if (!Data.validarData(localForm.getDataAssinaturaLei().trim())) {
				throw new ApplicationException("errors.date", new String[]{"A Data de Assinatura da Lei informada"}, ApplicationException.ICON_AVISO);
			}
			Date dtAssinat = null;
			try {
				dtAssinat = Data.formataData(localForm.getDataAssinaturaLei().trim());
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar Data!"}, ApplicationException.ICON_AVISO);
			}
			if (Data.dataDiff(dtAssinat,dataAntiga) == 1) {
				throw new ApplicationException("errors.dataAntiga", new String[]{"A Data de Assinatura da Lei"}, ApplicationException.ICON_AVISO);
			}
			Date inicVig = null;
			try {
				inicVig = Data.formataData(localForm.getDtInicioVigencia().trim());
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar Data!"}, ApplicationException.ICON_AVISO);
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
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar Data!"}, ApplicationException.ICON_AVISO);
			}
			if (Data.dataDiff(dtPub,dataAntiga) == 1) {
				throw new ApplicationException("errors.dataAntiga", new String[]{"A Data de Publicação da Lei"}, ApplicationException.ICON_AVISO);
			}
		}
		
	}	

	private void validarRevogarDevolver(DoacaoBemImovelForm localForm) throws ApplicationException {
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


	private void populaPojo(Doacao doacao, DoacaoBemImovelForm localForm, HttpServletRequest request) throws ApplicationException {
		try {
			BemImovel bemImovel = null;
			bemImovel = CadastroFacade.obterBemImovelSimplificado(Integer.valueOf(localForm.getCodBemImovel())); 
			if (localForm.getProjetoLei() != null && localForm.getProjetoLei().equalsIgnoreCase("N")) {
				LeiBemImovel leiBemImovel = CadastroFacade.obterLeiBemImovelPorNumero(new Long(localForm.getNumeroLei()));
				if (leiBemImovel != null && leiBemImovel.getCodLeiBemImovel() > 0) {
					doacao.setNrProjetoLei(null);
					doacao.setLeiBemImovel(leiBemImovel);
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
						doacao.setNrProjetoLei(null);
						doacao.setLeiBemImovel(leiBemImovel);
					} else {
						throw new ApplicationException("AVISO.62", ApplicationException.ICON_AVISO);
					}
				}
			} else {
				if (localForm.getNumeroLei() != null && !localForm.getNumeroLei().isEmpty())
					doacao.setNrProjetoLei(Integer.valueOf(localForm.getNumeroLei()));
			}
			doacao.setInstituicao(CadastroFacade.obterInstituicao(Integer.valueOf(localForm.getInstituicao())));
			doacao.setBemImovel(bemImovel);
			if (bemImovel.getOrgao() != null) {
				doacao.setOrgaoProprietario(bemImovel.getOrgao());
			}
			doacao.setAdministracao(Integer.valueOf(localForm.getAdministracao()));
			doacao.setOrgaoResponsavel(CadastroFacade.obterOrgao(Integer.valueOf(localForm.getOrgaoResponsavel())));
			doacao.setDtInicioVigencia(Data.formataData(localForm.getDtInicioVigencia()));
			doacao.setDtFimVigencia(Data.formataData(localForm.getDtFimVigencia()));
			if (localForm.getProtocolo() != null && !localForm.getProtocolo().isEmpty()) {
				doacao.setProtocolo(localForm.getProtocolo());
			}
			
			if (localForm.getActionType().equalsIgnoreCase("revogDev")) {
				doacao.setMotivoRevogacao(localForm.getMotivo());
				doacao.setNrOficio(Integer.valueOf(localForm.getNrOficio()));
			}
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".populaPojo"}, e, ApplicationException.ICON_ERRO);
		} 
	}
	
	private void populaPojoRegistro(Doacao doacao, String acao, HttpServletRequest request) throws ApplicationException {
		try {
			Date dataAtual = new Date();
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			
		    if ("I".equals(acao)) {
				doacao.setTsInclusao(dataAtual);
				doacao.setCpfResponsavelInclusao(sentinelaInterface.getCpf());
				doacao.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.RASCUNHO.getIndex()));
			} else if ("A".equals(acao)) {
				doacao.setTsAtualizacao(dataAtual);
				doacao.setCpfResponsavelAlteracao(sentinelaInterface.getCpf());
			} else if ("E".equals(acao)) {
				doacao.setTsExclusao(dataAtual);
				doacao.setCpfResponsavelExclusao(sentinelaInterface.getCpf());
				doacao.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.FINALIZADO.getIndex()));
			}
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".populaPojoRegistro"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	private void carregarCamposRequest (ActionForm form, HttpServletRequest request) throws NumberFormatException, ApplicationException {
		try {
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm)form;
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}

			request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(localForm.getAdministracao()), Integer.valueOf(localForm.getInstituicao()))));

			request.getSession().setAttribute("edificacoes", Util.htmlEncodeCollection(OperacaoFacade.listarEdificacaoComboParaOperacoes(Integer.valueOf(localForm.getCodBemImovel()))));
			Collection<ItemComboDTO> ret = montaComboOrgaoDoacaoAssinatura(localForm, request);
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
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".carregarCamposRequest"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Realiza o encaminhamento necessário para adicionar um item a Doação.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward adicionarItemDoacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método adicionarItemDoacaoBemImovel processando...");
		try	{			
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;

			// Aciona a validação do Form
			validaDadosForm(localForm, "Item", request);
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			
			Doacao doacao = new Doacao();
			BemImovel bemImovel = CadastroFacade.obterBemImovelSimplificado(Integer.valueOf(localForm.getCodBemImovel()));
			if (localForm.getCodDoacao() != null && localForm.getCodDoacao().length() > 0) {
				doacao = OperacaoFacade.obterDoacaoCompleto(Integer.valueOf(localForm.getCodDoacao()));
			} else {
				doacao.setBemImovel(bemImovel);
			}
			if (localForm.getCodDoacao() != null && localForm.getCodDoacao().length() > 0) {
				this.populaPojoRegistro(doacao, "A", request);
			} else {
				this.populaPojoRegistro(doacao, "I", request);
			}

			this.verificarDoacaoByBemImovelStatusTermo(localForm.getCodDoacao(), doacao, "A");

			this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			
			this.populaPojo(doacao, localForm, request);

			if (localForm.getProjetoLei() != null && localForm.getProjetoLei().equalsIgnoreCase("N")) {
				validarLeiBemImovel(doacao.getLeiBemImovel(), localForm);
				doacao = OperacaoFacade.salvarDoacaoLeiBemImovel(doacao, doacao.getLeiBemImovel());
			} else {
				doacao = OperacaoFacade.salvarDoacao(doacao);
			}

			ItemDoacao itemDoacao = new ItemDoacao();
			itemDoacao.setTipo(Integer.valueOf(localForm.getTipoDoacao()));
			
			if (!localForm.getTipoDoacao().equals("1")) {
				ItemDTO obj = OperacaoFacade.calcularPercentualMetros(localForm.getTipoDoacao(), localForm.getDoacaoMetros(), 
						localForm.getDoacaoPercentual(), "Doação", localForm.getEdificacao(), localForm.getCodBemImovel());
				itemDoacao.setDoacaoMetros(obj.getMetros());
				itemDoacao.setDoacaoPercentual(obj.getPercentual());
			} else if (localForm.getTipoDoacao().equals("1")) {
				if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() != null) {
					itemDoacao.setDoacaoMetros(bemImovel.getAreaConstruida().add(bemImovel.getAreaTerreno()));
				} else if (bemImovel.getAreaConstruida() == null && bemImovel.getAreaTerreno() != null) {
					itemDoacao.setDoacaoMetros(bemImovel.getAreaTerreno());
				} else if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() == null) {
					itemDoacao.setDoacaoMetros(bemImovel.getAreaConstruida());
				}
				itemDoacao.setDoacaoPercentual(new BigDecimal(100));
			} 
			if (localForm.getTipoDoacao().equals("2")) {
				itemDoacao.setEdificacao(CadastroFacade.obterEdificacao(Integer.valueOf(localForm.getEdificacao())));
			}
			if (doacao.getBemImovel().getSomenteTerreno().equalsIgnoreCase("S")) {
				itemDoacao.setSomenteTerreno(Boolean.TRUE);
			} else {
				itemDoacao.setSomenteTerreno(Boolean.FALSE);
			}
			itemDoacao.setDoacao(doacao);
			if (localForm.getUtilizacao().trim().length() > 0) {
				if (localForm.getUtilizacao().trim().length() > 3000) {
					itemDoacao.setUtilizacao(localForm.getUtilizacao().trim().substring(0, 3000));
				} else {
					itemDoacao.setUtilizacao(localForm.getUtilizacao());
				}
			}
			if (localForm.getObservacao().trim().length() > 0) {
				if (localForm.getObservacao().trim().length() > 3000) {
					itemDoacao.setObservacao(localForm.getObservacao().trim().substring(0, 3000));
				} else {
					itemDoacao.setObservacao(localForm.getObservacao());
				}
			}
			
			boolean mesmoObjeto = false;
			// Verifica se já existe o mesmo item
			Collection<ItemDoacao> listDuplicidadeAD = OperacaoFacade.verificarDuplicidadeItemDoacao(itemDoacao); 
			if (listDuplicidadeAD.size() > 0) {
				ItemDoacao itemDoacaoDB;
				for (Iterator<ItemDoacao> iterator  = listDuplicidadeAD.iterator(); iterator.hasNext();) {
					itemDoacaoDB = (ItemDoacao) iterator .next();
					if ((itemDoacaoDB.getEdificacao() != null && 
						itemDoacaoDB.getEdificacao().getCodEdificacao().equals(itemDoacao.getEdificacao().getCodEdificacao())) &&
						itemDoacaoDB.getSomenteTerreno().equals(itemDoacao.getSomenteTerreno())) {
						mesmoObjeto = true;
					}
				}
				if (mesmoObjeto) {
					throw new ApplicationException("AVISO.57", ApplicationException.ICON_AVISO);
				}
			}

			OperacaoFacade.salvarItemDoacao(itemDoacao);
			
			if (localForm.getCodDoacao() != null && localForm.getCodDoacao().length() > 0) {
				this.addMessage("SUCESSO.41", new String[]{"Doação", doacao.getCodDoacao().toString(), doacao.getStatusTermo().getDescricao(),
						Mensagem.getInstance().getMessage("SUCESSO.45")}, request);	
			} else {
				this.addMessage("SUCESSO.40", new String[]{"Doação", doacao.getCodDoacao().toString(), Mensagem.getInstance().getMessage("SUCESSO.45")}, request);	
			}

			localForm.setCodDoacao(doacao.getCodDoacao().toString());
			localForm.limparCamposItem();
			populaForm(localForm, request);

			return mapping.findForward("pgEditDoacao");

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditDoacao"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditDoacao"));
			log.error(e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".adicionarItemDoacaoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	/**
	 * Realiza o encaminhamento necessário para adicionar uma assinatura a Doação.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward adicionarAssinaturaDoacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método adicionarAssinaturaDoacaoBemImovel processando...");
		try	{			
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;

			// Aciona a validação do Form
			validaDadosForm(localForm, "assinatura", request);
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			
			Doacao doacao = new Doacao();
			if (localForm.getCodDoacao() != null && localForm.getCodDoacao().length() > 0) {
				doacao = OperacaoFacade.obterDoacaoCompleto(Integer.valueOf(localForm.getCodDoacao()));
			} else {
				doacao.setBemImovel(CadastroFacade.obterBemImovelSimplificado(Integer.valueOf(localForm.getCodBemImovel())));
			}
			if (localForm.getCodDoacao() != null && localForm.getCodDoacao().length() > 0) {
				this.populaPojoRegistro(doacao, "A", request);
			} else {
				this.populaPojoRegistro(doacao, "I", request);
			}

			this.verificarDoacaoByBemImovelStatusTermo(localForm.getCodDoacao(), doacao, "A");

			this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			
			this.populaPojo(doacao, localForm, request);

			if (localForm.getProjetoLei() != null && localForm.getProjetoLei().equalsIgnoreCase("N")) {
				validarLeiBemImovel(doacao.getLeiBemImovel(), localForm);
				doacao = OperacaoFacade.salvarDoacaoLeiBemImovel(doacao, doacao.getLeiBemImovel());
			} else {
				doacao = OperacaoFacade.salvarDoacao(doacao);
			}

			AssinaturaDoacao assinaturaDoacao = new AssinaturaDoacao();
			assinaturaDoacao.setDoacao(doacao);
			assinaturaDoacao.setAssinatura(CadastroFacade.obterAssinatura(Integer.valueOf(localForm.getNomeAssinatura())));
			assinaturaDoacao.setOrdem(Integer.valueOf(localForm.getOrdemAssinatura()));
			
			AssinaturaDoacao assinaturaDoacaoAux = assinaturaDoacao;
			boolean mesmoObjeto = false;
			// Verifica se já existe com Doacao | ordem e assinatura
			Collection<AssinaturaDoacao> listDuplicidadeAD = OperacaoFacade.verificarDuplicidadeAssinaturaDoacao(assinaturaDoacao); 
			if (listDuplicidadeAD.size() > 0) {
				AssinaturaDoacao assinaturaDoacaoDB;
				for (Iterator<AssinaturaDoacao> iterator  = listDuplicidadeAD.iterator(); iterator.hasNext();) {
					assinaturaDoacaoDB = (AssinaturaDoacao) iterator .next();
					if (assinaturaDoacaoDB.getOrdem().equals(assinaturaDoacaoAux.getOrdem())) {
						mesmoObjeto = true;
					}
				}
				if (mesmoObjeto) {
					throw new ApplicationException("AVISO.76", ApplicationException.ICON_AVISO);
				}
				mesmoObjeto = false;
				//verifica se é a mesma assinatura
				for (Iterator<AssinaturaDoacao> iterator  = listDuplicidadeAD.iterator(); iterator.hasNext();) {
					assinaturaDoacaoDB = (AssinaturaDoacao) iterator .next();
					if (assinaturaDoacaoDB.getAssinatura().getCodAssinatura().equals(assinaturaDoacaoAux.getAssinatura().getCodAssinatura())) {
						mesmoObjeto = true;
					}
				}
				if (mesmoObjeto) {
					throw new ApplicationException("AVISO.55", ApplicationException.ICON_AVISO);
				}
				mesmoObjeto = false;
				//verifica se é do mesmo cargo
				for (Iterator<AssinaturaDoacao> iterator  = listDuplicidadeAD.iterator(); iterator.hasNext();) {
					assinaturaDoacaoDB = (AssinaturaDoacao) iterator .next();
					if (assinaturaDoacaoDB.getAssinatura().getCargoAssinatura().getCodCargoAssinatura().equals(assinaturaDoacaoAux.getAssinatura().getCargoAssinatura().getCodCargoAssinatura())) {
						mesmoObjeto = true;
					}
				}
				if (mesmoObjeto) {
					throw new ApplicationException("AVISO.103", ApplicationException.ICON_AVISO);
				}
			}

			OperacaoFacade.salvarAssinaturaDoacao(assinaturaDoacao);
			
			if (localForm.getCodDoacao() != null && localForm.getCodDoacao().length() > 0) {
				this.addMessage("SUCESSO.41", new String[]{"Doação", doacao.getCodDoacao().toString(), doacao.getStatusTermo().getDescricao(),
						Mensagem.getInstance().getMessage("SUCESSO.43")}, request);	
			} else {
				this.addMessage("SUCESSO.40", new String[]{"Doação", doacao.getCodDoacao().toString(), Mensagem.getInstance().getMessage("SUCESSO.43")}, request);	
			}

			localForm.setCodDoacao(doacao.getCodDoacao().toString());
			localForm.limparCamposAssinatura();
			populaForm(localForm, request);

			return mapping.findForward("pgEditDoacao");

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditDoacao"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditDoacao"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".adicionarAssinaturaDoacaoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	private void carregarListaItemDoacaoAssinatura(ActionForm form, HttpServletRequest request) throws ApplicationException {
		try {
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm)form;
			String codDoacao = localForm.getCodDoacao();
			if (codDoacao == null || codDoacao.isEmpty() || codDoacao.trim().length() < 1) {
				request.getSession().setAttribute("listItemDoacao", null);
				request.getSession().setAttribute("listAssinatura", null);
			} else {
				Pagina pagina = new Pagina(null, null, null);
				request.getSession().setAttribute("listItemDoacao", OperacaoFacade.listarItemDoacao(pagina, Integer.valueOf(codDoacao)));
				pagina = new Pagina(null, null, null);
				request.getSession().setAttribute("listAssinatura", OperacaoFacade.listarAssinaturaDoacao(pagina, Integer.valueOf(codDoacao)));

				ItemDoacao itemDoacao = new ItemDoacao();
				itemDoacao.setDoacao(new Doacao());
				itemDoacao.getDoacao().setCodDoacao(Integer.valueOf(codDoacao));
				Collection<ItemDoacao> list = OperacaoFacade.verificarDuplicidadeItemDoacao(itemDoacao);
				for (ItemDoacao item : list) {
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
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".carregarListaItemDoacaoAssinatura"}, e, ApplicationException.ICON_ERRO);
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
				DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
				LeiBemImovel lei = CadastroFacade.obterLeiBemImovelPorNumero(new Long(localForm.getNumeroLei()));
				localForm.setProjetoLei("N");
				if (lei != null) {
					localForm.setCodLei(lei.getCodLeiBemImovel().toString());
					localForm.setNumeroLei(Long.toString(lei.getNumero()));
					localForm.setDataAssinaturaLei(Data.formataData(lei.getDataAssinatura()));
					localForm.setDataPublicacaoLei(Data.formataData(lei.getDataPublicacao()));
					localForm.setNrDioeLei(Long.toString(lei.getNrDioe()));
				} else {
					throw new ApplicationException("AVISO.61", ApplicationException.ICON_AVISO);
				}
			}
			
			return mapping.findForward("pgEditLeiBemImovelDoacao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditLeiBemImovelDoacao"));
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditLeiBemImovelDoacao"));
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".carregarDadosLeiBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
	}

	/**
	 * Realiza o encaminhamento necessário para adicionar um item a Doação.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward excluirItemDoacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método excluirItemDoacaoBemImovel processando...");
		try	{			
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			
			Doacao doacao = OperacaoFacade.obterDoacaoCompleto(Integer.valueOf(localForm.getCodDoacao()));
			this.populaPojoRegistro(doacao, "A", request);

			this.verificarDoacaoByBemImovelStatusTermo(localForm.getCodDoacao(), doacao, "A");

			this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			
			this.populaPojo(doacao, localForm, request);

			if (localForm.getProjetoLei() != null && localForm.getProjetoLei().equalsIgnoreCase("N")) {
				validarLeiBemImovel(doacao.getLeiBemImovel(), localForm);
				doacao = OperacaoFacade.salvarDoacaoLeiBemImovel(doacao, doacao.getLeiBemImovel());
			} else {
				doacao = OperacaoFacade.salvarDoacao(doacao);
			}

			ItemDoacao itemDoacao = OperacaoFacade.obterItemDoacao(Integer.valueOf(localForm.getCodItemDoacao()));
			OperacaoFacade.excluirItemDoacao(itemDoacao);
			
			this.addMessage("SUCESSO.41", new String[]{"Doação", doacao.getCodDoacao().toString(), doacao.getStatusTermo().getDescricao(),
					Mensagem.getInstance().getMessage("SUCESSO.46")}, request);	
			localForm.setCodDoacao(doacao.getCodDoacao().toString());
			localForm.limparCamposItem();
			localForm.setCodItemDoacao(null);

			populaForm(localForm, request);

			return mapping.findForward("pgEditDoacao");

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditDoacao"));
			populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditDoacao"));
			populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".excluirItemDoacaoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	/**
	 * Realiza o encaminhamento necessário para adicionar um item a Doação.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward excluirAssinaturaDoacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método excluirAssinaturaDoacaoBemImovel processando...");
		try	{			
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
			verificarGrupoUsuarioLogado(localForm, request);
			if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
				BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
				localForm.setCodBemImovel(aux.getCodBemImovel().toString());
			}
			
			Doacao doacao = new Doacao();
			doacao = OperacaoFacade.obterDoacaoCompleto(Integer.valueOf(localForm.getCodDoacao()));
			this.populaPojoRegistro(doacao, "A", request);

			this.verificarDoacaoByBemImovelStatusTermo(localForm.getCodDoacao(), doacao, "A");

			this.verificarCessaoTotalBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			
			this.populaPojo(doacao, localForm, request);

			if (localForm.getProjetoLei() != null && localForm.getProjetoLei().equalsIgnoreCase("N")) {
				validarLeiBemImovel(doacao.getLeiBemImovel(), localForm);
				doacao = OperacaoFacade.salvarDoacaoLeiBemImovel(doacao, doacao.getLeiBemImovel());
			} else {
				doacao = OperacaoFacade.salvarDoacao(doacao);
			}
			AssinaturaDoacao assinaturaDoacao = OperacaoFacade.obterAssinaturaDoacao(Integer.valueOf(localForm.getCodAssinaturaDoacao()));
			
			OperacaoFacade.excluirAssinaturaDoacao(assinaturaDoacao);
			
			this.addMessage("SUCESSO.41", new String[]{"Doação", doacao.getCodDoacao().toString(), doacao.getStatusTermo().getDescricao(),
					Mensagem.getInstance().getMessage("SUCESSO.44")}, request);	

			localForm.setCodDoacao(doacao.getCodDoacao().toString());
			localForm.limparCamposAssinatura();
			localForm.setCodAssinaturaDoacao(null);

			populaForm(localForm, request);
			
			return mapping.findForward("pgEditDoacao");

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditDoacao"));
			populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditDoacao"));
			populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".excluirAssinaturaDoacaoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 

	}

	/**
	 * Realiza o encaminhamento necessário para alterar a Doação.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward revogDevDoacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método revogDevDoacaoBemImovel processando...");
		try	{			
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
			
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	
			verificarGrupoUsuarioLogado(localForm, request);

			// Aciona a validação do Form
			validarRevogarDevolver(localForm);
			
			Doacao doacao = OperacaoFacade.obterDoacaoCompleto(Integer.valueOf(localForm.getCodDoacao()));
			if (localForm.getTipoRevogDev().equals("1")) {
				doacao.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.REVOGADO.getIndex()));
				doacao.setMotivoRevogacao(localForm.getMotivo());
			} else if (localForm.getTipoRevogDev().equals("2")) {
				doacao.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.DEVOLVIDO.getIndex()));
				doacao.setNrOficio(Integer.valueOf(localForm.getNrOficio()));
			}
			doacao.setDtFimVigencia(new Date());
			this.populaPojoRegistro(doacao, "A", request);

			doacao = OperacaoFacade.salvarDoacao(doacao);
			
			saveToken(request);
			if (localForm.getTipoRevogDev().equals("1")) {
				this.addMessage("SUCESSO.47", new String[]{"Revogação", "Doação", doacao.getCodDoacao().toString()}, request);	
			} else if (localForm.getTipoRevogDev().equals("2")) {
				this.addMessage("SUCESSO.47", new String[]{"Devolução", "Doação", doacao.getCodDoacao().toString()}, request);	
			}
			localForm.limparCampos();
			return this.carregarPgListDoacaoBemImovel(mapping, form, request, response);

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgViewDoacao"));
			populaForm(form, request);
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgViewDoacao"));
			populaForm(form, request);
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".revogDevDoacaoBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
		
	}

	// UC GERAR TERMO
	
	/**
	 * Redireciona para o UCS GerarTermoDoacaoBemImovel.<br>
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
			
			DoacaoBemImovelForm localForm = (DoacaoBemImovelForm) form;
			localForm.setUcsDestino("ucsGerarTermoDoacaoBemImovel");
			localForm.setUcsChamador("ucsDoacaoBemImovel");
			
			request.setAttribute("ucsChamador", "ucsDoacaoBemImovel");
			request.setAttribute("codDoacao", localForm.getCodDoacao());
			
			return acionarOutroCasoUso(mapping, localForm, request, response);
					
		} catch (ApplicationException ae) {
			throw ae;
		} catch (Exception e) {
			setActionForward(carregarPgListDoacaoBemImovel(mapping, form, request, response));
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
						
			DoacaoBemImovelForm frm = (DoacaoBemImovelForm) form;		
			frm.setUcsRetorno(frm.getUcsChamador()); 
			
			Doacao doacao = OperacaoFacade.obterDoacaoCompleto(Integer.valueOf(frm.getCodDoacao()));
			if(Integer.valueOf(Dominios.statusTermo.RASCUNHO.getIndex()).equals(doacao.getStatusTermo().getCodStatusTermo())){
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
	public void armazenarFormSessao(HttpServletRequest request, DoacaoBemImovelForm form) throws ApplicationException{

		try{
			Stack<DoacaoBemImovelForm> pilhaForm = (Stack<DoacaoBemImovelForm>)Sessao.obterAtributoSessao(request, PILHA_FORM_SESSAO); 
			
			if(pilhaForm == null){
				pilhaForm = new Stack<DoacaoBemImovelForm>();
			}

			//Evitar que se carrege o mesmo form na pilha atraves do refresh
			DoacaoBemImovelForm ultimoPilha = this.obterFormSessao(request);
			
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
	public DoacaoBemImovelForm obterFormSessao(HttpServletRequest request) throws ApplicationException{

		try{
			Stack<DoacaoBemImovelForm> pilhaForm = (Stack<DoacaoBemImovelForm>)Sessao.obterAtributoSessao(request, PILHA_FORM_SESSAO); 
			if(pilhaForm == null){
				return null;
			}
			
			if(pilhaForm.isEmpty()){
				Sessao.removerAtributoDaSessao(request, PILHA_FORM_SESSAO);
				return null;
			}
			DoacaoBemImovelForm bf = pilhaForm.pop();
			armazenarFormSessao(request, bf);
			return bf;
			
		}catch (Exception e) {			
			log.error(e.getMessage(),e);			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".obterFormSessao()"}, e, ApplicationException.ICON_ERRO);
		}
			
	}
	
	public DoacaoBemImovelForm desempilharFormSession(HttpServletRequest request) throws ApplicationException{
		try{

			Object obj = Sessao.obterAtributoSessao(request, PILHA_FORM_SESSAO);
			if (obj == null) {
				return null;
			} 

			Stack<DoacaoBemImovelForm> pilhaForm = (Stack<DoacaoBemImovelForm>)obj; 
			if(pilhaForm == null){
				return null;
			}

			DoacaoBemImovelForm manterSuperForm = null;
			if (pilhaForm.size() > 0) {
				if (pilhaForm.get(0) instanceof DoacaoBemImovelForm) {
					manterSuperForm = pilhaForm.pop();
					manterSuperForm.setCodDoacao("");
				}
			}
			Sessao.removerAtributoDaSessao(request, PILHA_FORM_SESSAO);

			return manterSuperForm;
		}catch (Exception e) {		
			log.error(e.getMessage(),e);			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".desempilharFormSession()"}, e, ApplicationException.ICON_ERRO);
		}	
	}	

	private void verificarGrupoUsuarioLogado(DoacaoBemImovelForm localForm, HttpServletRequest request) throws ApplicationException {
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