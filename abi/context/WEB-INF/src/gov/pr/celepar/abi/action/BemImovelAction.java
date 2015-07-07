package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.BemImovelPesquisaDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.facade.OperacaoFacade;
import gov.pr.celepar.abi.form.BemImovelForm;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.abi.pojo.Doacao;
import gov.pr.celepar.abi.pojo.Documentacao;
import gov.pr.celepar.abi.pojo.Ocupacao;
import gov.pr.celepar.abi.pojo.Orgao;
import gov.pr.celepar.abi.pojo.Transferencia;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.pojo.UsuarioOrgao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Dominios.classificacaoImovel;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.mensagem.Mensagem;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.framework.util.Valores;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;
import gov.pr.celepar.sentinela.comunicacao.SentinelaParam;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author pialarissi
 * @version 1.0
 * @since 19/01/2010
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class BemImovelAction extends BaseDispatchAction {

	private static Logger log4j = Logger.getLogger(BemImovelAction.class);
	private static String ADD_ORGAO_RESP = "adicionado";
	private static String RMV_ORGAO_RESP = "removido";
	
	/**
	 * Realiza carga da página de listagem de bens imóveis.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgListBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		try {
			setActionForward(mapping.findForward("pgListBemImovel"));
			saveToken(request);

			verificarTodasOperacoes(request);
			verificarGrupoUsuarioLogado((BemImovelForm) form, request);
			
			this.inicializar(request, form);
		} catch (ApplicationException appEx) {
			if (appEx.getMessage().equalsIgnoreCase(Mensagem.getInstance().getMessage("AVISO.96"))){
				setActionForward(mapping.findForward("pgErroBI"));
			}
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{"BemImovelAction.carregarPgListBemImovel"}, e, ApplicationException.ICON_ERRO);
		}
	
		return getActionForward();
    }

	/**
	 * Carrega página de visualização dos dados da denominação de imóvel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgViewBemImovel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		verificarTodasOperacoes(request);
		
		BemImovelForm bemImovelForm = (BemImovelForm) form;
	
		try {
			verificarGrupoUsuarioLogado(bemImovelForm, request);
			if(StringUtil.stringNotNull(bemImovelForm.getCodBemImovel())) {
				Integer codBemImovel = Integer.valueOf(((bemImovelForm.getCodBemImovel())));
				request.setAttribute("bemImovel", CadastroFacade.obterBemImovelExibir(codBemImovel));					
				request.setAttribute("leiBemImovels", new Pagina(null, null, null, Util.htmlEncodeCollection(CadastroFacade.listarLeiBemImovelsExibir(codBemImovel))));
				request.setAttribute("lotes", new Pagina(null, null, null, Util.htmlEncodeCollection(CadastroFacade.listarLotesExibir(codBemImovel))));
				request.setAttribute("confrontantes", new Pagina(null, null, null, Util.htmlEncodeCollection(CadastroFacade.listarConfrontantesExibir(codBemImovel))));
				request.setAttribute("coordenadaUtms", new Pagina(null, null, null, Util.htmlEncodeCollection(CadastroFacade.listarCoordenadaUtmsExibir(codBemImovel))));
				request.setAttribute("avaliacaos", new Pagina(null, null, null, Util.htmlEncodeCollection(CadastroFacade.listarAvaliacaosExibir(codBemImovel))));
				request.setAttribute("ocupacaos", new Pagina(null, null, null, Util.htmlEncodeCollection(CadastroFacade.listarOcupacaosExibir(codBemImovel))));
				request.setAttribute("edificacaoOcupacaos", CadastroFacade.listarImpressaoEdificacaoBensImoveis(codBemImovel));
				request.setAttribute("documentacaoNotificacaos", new Pagina(null, null, null, Util.htmlEncodeCollection(CadastroFacade.listarDocumentacaoNotificacaosExibir(codBemImovel))));
				request.setAttribute("documentacaoSemNotificacaos", new Pagina(null, null, null, Util.htmlEncodeCollection(CadastroFacade.listarDocumentacaoSemNotificacaosExibir(codBemImovel))));
				request.setAttribute("ocorrenciaDocumentacaos", new Pagina(null, null, null, Util.htmlEncodeCollection(CadastroFacade.listarOcorrenciaDocumentacaosExibir(codBemImovel))));
				request.setAttribute("rural", classificacaoImovel.CLASSIFICACAO_RURAL.getIndex());
				request.setAttribute("urbano", classificacaoImovel.CLASSIFICACAO_URBANO.getIndex());
				carregarListaOcupacaoOrgaoResponsavel(request, codBemImovel.toString());
			}
			return mapping.findForward("pgViewBemImovel");
		}
		catch(ApplicationException appEx) {
			this.inicializar(request, form);
			setActionForward(mapping.findForward("pgListBemImovel"));
			throw appEx;
		} catch(Exception e) {
			this.inicializar(request, form);
			setActionForward(mapping.findForward("pgListBemImovel"));
			throw new ApplicationException("ERRO.200", new String[]{"visualização","bem imóvel"}, e);
		}	
	}


	public ActionForward carregarPgViewEdificacao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		verificarTodasOperacoes( request );

		BemImovelForm bemImovelForm = (BemImovelForm) form;
	
		try {
			verificarGrupoUsuarioLogado(bemImovelForm, request);
			if(StringUtil.stringNotNull(request.getParameter("codEdificacao"))) {					
				Integer codEdificacao = Integer.parseInt(request.getParameter("codEdificacao"));
				request.setAttribute("edificacao", CadastroFacade.obterEdificacaoExibir(codEdificacao));
			}

			return mapping.findForward("pgViewEdificacao");
		} catch(ApplicationException appEx) {
			this.inicializar(request, form);
			setActionForward(mapping.findForward("pgListBemImovel"));
			throw appEx;
		} catch(Exception e) {
			this.inicializar(request, form);
			setActionForward(mapping.findForward("pgListBemImovel"));
			throw new ApplicationException("ERRO.200", new String[]{"visualização","bem imóvel"}, e);
		}	
	}


	private void verificarTodasOperacoes( HttpServletRequest request ){
		SentinelaInterface com = SentinelaComunicacao.getInstance(request);
		String operacoes = com.getOperacoes();
		log4j.info("Operações do usuário "+com.getLogin()+ ": " + operacoes);
		verificarOperacao('I', operacoes, request); //incluir bem imovel
		verificarOperacao('B', operacoes, request); //baixar bem imovel 
		verificarOperacao('E', operacoes, request); //excluir bem imovel
		verificarOperacao('A', operacoes, request); //alterar bem imovel
		verificarOperacao('O', operacoes, request); //incluir ocorrencia
	}
	
	private void verificarOperacao(char operacao, String operacoes, HttpServletRequest request){
		try {
			request.setAttribute(String.valueOf(operacao), Boolean.valueOf( operacoes.indexOf(operacao)!= -1 ) );
		}catch (Exception e) {
			request.setAttribute(String.valueOf(operacao), "false" );
			log4j.info("Não autorizado para a operação: "+ operacao, e );			
		}
	}
	
	/**
	 * Carrega pagina para alteração com os dados da denominação de imóvel selecionada.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward carregarPgEditBemImovel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		verificarTodasOperacoes( request );
		BemImovelForm bemImovelForm = (BemImovelForm) form;
		try {
			setActionForward(mapping.findForward("pgListBemImovel"));
			
			verificarGrupoUsuarioLogado(bemImovelForm, request);
			
			SentinelaInterface sentinelaComunicacao = SentinelaComunicacao.getInstance(request);
			SentinelaParam[] sentinelaParam = sentinelaComunicacao.getGruposByUsuario(sentinelaComunicacao.getCodUsuario());
			boolean ret = Util.validaPermissaoReativar(sentinelaParam);

			bemImovelForm.setPermReativar(String.valueOf(ret));
			
			this.inicializar(request, form);
			
			if(request.getParameter("actionType").equals("incluir")){
				if (! Boolean.valueOf(request.getAttribute("I").toString())){
					setActionForward(mapping.findForward("pgEntrada"));
					this.inicializar(request, form);
					throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);		
				}
				bemImovelForm.setSomenteTerreno("N"); //Não
				bemImovelForm.setAdministracao("1");
				bemImovelForm.setQtdRegResponsavelAtivo("0");
				bemImovelForm.setQtdRegResponsavelInativo("0");
				bemImovelForm.setQtdRegEdificacao("0");
			} else if (request.getParameter("actionType").equals("alterar")){
				if (! Boolean.valueOf(request.getAttribute("A").toString())){
					setActionForward(mapping.findForward("pgEntrada"));
					this.inicializar(request, form);
					throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
				}
				if(StringUtil.stringNotNull(bemImovelForm.getCodBemImovel())) {
					this.preencherFormularioBemImovel(bemImovelForm, Integer.valueOf((bemImovelForm.getCodBemImovel())));
					carregarListaOcupacaoOrgaoResponsavel(request, bemImovelForm.getCodBemImovel());
				}
			}
			bemImovelForm.setOr_situacaoOcupacao(Mensagem.getInstance().getMessage("CODIGO_SITUACAO_OCUPACAO_TERRENO_NU"));

			return mapping.findForward("pgEditBemImovel");
		}
		catch(ApplicationException appEx) {
			this.inicializar(request, form);
			throw appEx;
		}
		catch(Exception e) {
			this.inicializar(request, form);
			throw new ApplicationException("ERRO.200", new String[]{"edição","bem imóvel"}, e);
		}		
	}
	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de denominações de imóveis. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	private void inicializar(HttpServletRequest request, ActionForm form) throws ApplicationException {
		BemImovelForm bemImovelForm = (BemImovelForm) form;
		
		if (bemImovelForm.getIsGpAdmGeralUsuarioLogado() == null || bemImovelForm.getIsGpAdmGeralUsuarioLogado().trim().equals("")) {
			verificarGrupoUsuarioLogado(bemImovelForm, request);
		}

		request.setAttribute("classificacaoBemImovels", Util.htmlEncodeCollection(CadastroFacade.listarClassificacaoBemImovels()));
		request.setAttribute("situacaoLegalCartorials", Util.htmlEncodeCollection(CadastroFacade.listarSituacaoLegalCartorials()));
		request.setAttribute("cartorios", Util.htmlEncodeCollection(CadastroFacade.listarCartorios()));
		request.setAttribute("tabelionatos", Util.htmlEncodeCollection(CadastroFacade.listarTabelionatos()));
		request.setAttribute("formaIncorporacaos", Util.htmlEncodeCollection(CadastroFacade.listarFormaIncorporacaos()));
		request.setAttribute("tipoConstrucaos", Util.htmlEncodeCollection(CadastroFacade.listarTipoConstrucaos()));
		request.setAttribute("tipoEdificacaos", Util.htmlEncodeCollection(CadastroFacade.listarTipoEdificacaos()));
		request.setAttribute("tipoDocumentacaos", Util.htmlEncodeCollection(CadastroFacade.listarTipoDocumentacaos()));
		request.setAttribute("situacaoOcupacaos", Util.htmlEncodeCollection(CadastroFacade.listarSituacaoOcupacaos()));
		request.setAttribute("denominacaoImovels", Util.htmlEncodeCollection(CadastroFacade.listarDenominacaoImovels()));
		request.setAttribute("situacaoImovels", Util.htmlEncodeCollection(CadastroFacade.listarSituacaoImovels()));
		request.setAttribute("listaPesquisaInstituicao", CadastroFacade.listarComboInstituicao());	
		request.setAttribute("listaInstituicao", CadastroFacade.listarComboInstituicao());	
		request.setAttribute("rural", classificacaoImovel.CLASSIFICACAO_RURAL.getIndex());
		request.setAttribute("urbano", classificacaoImovel.CLASSIFICACAO_URBANO.getIndex());

		//Operador de Orgao
		if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo())){
			bemImovelForm.setIndOperadorOrgao(true);
		}else{
			bemImovelForm.setIndOperadorOrgao(false);
		}

		carregarComboOrgaoSemAjax(bemImovelForm, request);
		
	}

	/**
	 * Faz a pesquisa de bens imóveis, segundo os parametros selecionados
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward pesquisarBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		verificarTodasOperacoes(request);		

		BemImovelForm bemImovelForm = (BemImovelForm) form;
		try {
			verificarGrupoUsuarioLogado(bemImovelForm, request);

			this.inicializar(request, form);
			
			String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");
			String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");

			Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));		
			
			BemImovelPesquisaDTO bemDTO = new BemImovelPesquisaDTO();
			
			// Se informado o Código do Imóvel, pesquisar imóvel somente por ele
			if (!Util.strEmBranco(bemImovelForm.getConNrBemImovel()) && bemImovelForm.getConNrBemImovel().matches("^[0-9]*$")) {
				bemDTO.setNrBemImovel(Integer.parseInt(bemImovelForm.getConNrBemImovel()));
				bemDTO.setCodInstituicao(Integer.parseInt(bemImovelForm.getConInstituicao()));
				//verificar se bem Imóvel existe porém não está acessível ao usuário logado
				if (bemImovelForm.getIndOperadorOrgao()){
					Long codUsuarioSentinela = SentinelaComunicacao.getInstance(request).getCodUsuario();
					if (CadastroFacade.existeBemImovel(bemDTO.getNrBemImovel(), bemDTO.getCodInstituicao()) && !CadastroFacade.verificarOperadorOrgaoBemImovel(bemDTO.getNrBemImovel(), codUsuarioSentinela, request)){
						throw new ApplicationException("AVISO.95", ApplicationException.ICON_AVISO);
					}
				}
				pagina = CadastroFacade.listarBemImovel(pagina, bemDTO);
				Util.htmlEncodeCollection(pagina.getRegistros());
				request.setAttribute("pagina", pagina);
				if(pagina.getTotalRegistros() == 0) {
					throw new ApplicationException("AVISO.2", ApplicationException.ICON_AVISO);
				}
			} else {
				if (bemImovelForm.getConInstituicao() != null && !bemImovelForm.getConInstituicao().trim().equals("")
						&& !bemImovelForm.getConInstituicao().trim().equals("0")) {
					Boolean flagBemDTONula = Boolean.TRUE;
					if(!Util.strEmBranco( bemImovelForm.getConInstituicao())) {
						bemDTO.setCodInstituicao(Integer.valueOf( bemImovelForm.getConInstituicao()));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco( bemImovelForm.getConAdministracao())) {
						bemDTO.setAdministracao(Integer.valueOf( bemImovelForm.getConAdministracao()));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConOrgao())) {
						bemDTO.setCodOrgao(Integer.parseInt(bemImovelForm.getConOrgao()));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConOrgaoResp())) {
						bemDTO.setCodOrgaoResponsavel(Integer.parseInt(bemImovelForm.getConOrgaoResp()));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConClassificacaoBemImovel())) {
						bemDTO.setCodClassificacaoBemImovel(Integer.parseInt(bemImovelForm.getConClassificacaoBemImovel()));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConNirf())) {
						bemDTO.setNirf("%"+bemImovelForm.getConNirf()+"%");
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConNiif())) {
						bemDTO.setNiif("%"+bemImovelForm.getConNiif()+"%");
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConIncra())) {
						bemDTO.setIncra("%"+bemImovelForm.getConIncra()+"%");
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConCodUf()) && !"0".equals(bemImovelForm.getConCodUf())) {
						bemDTO.setUf(bemImovelForm.getConCodUf());
						bemImovelForm.setUf(bemImovelForm.getConCodUf());
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConCodMunicipio()) && !"0".equals(bemImovelForm.getConCodMunicipio())) {
						bemDTO.setCodMunicipio(Integer.parseInt(bemImovelForm.getConCodMunicipio()) );
						bemImovelForm.setCodMunicipio(bemImovelForm.getConCodMunicipio());
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConLogradouro())) {
						bemDTO.setLogradouro("%"+bemImovelForm.getConLogradouro()+"%");
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConBairroDistrito())) {
						bemDTO.setBairroDistrito("%"+bemImovelForm.getConBairroDistrito()+"%");
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConSituacaoLegalCartorial())) {
						bemDTO.setCodSituacaoLegalCartorial(Integer.parseInt(bemImovelForm.getConSituacaoLegalCartorial()));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConCartorio())) {
						bemDTO.setCodCartorio(Integer.parseInt(bemImovelForm.getConCartorio()));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConNumeroDocumentoCartorial())) {
						bemDTO.setNumeroDocumentoCartorial("%" + bemImovelForm.getConNumeroDocumentoCartorial() + "%");
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConTabelionato())) {
						bemDTO.setCodTabelionato(Integer.parseInt(bemImovelForm.getConTabelionato()));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConNumeroDocumentoTabelional())) {
						bemDTO.setNumeroDocumentoTabelional("%" + bemImovelForm.getConNumeroDocumentoTabelional() + "%");
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConOcupante())) {
						bemDTO.setOcupante("%" + bemImovelForm.getConOcupante() + "%");
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConFormaIncorporacao())) {
						bemDTO.setCodFormaIncorporacao(Integer.parseInt(bemImovelForm.getConFormaIncorporacao()));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConAreaTerrenoIni())) {
						bemDTO.setAreaTerrenoIni(BigDecimal.valueOf(Util.converteDecimal(bemImovelForm.getConAreaTerrenoIni())));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConAreaTerrenoFim())) {
						bemDTO.setAreaTerrenoFim(BigDecimal.valueOf(Util.converteDecimal(bemImovelForm.getConAreaTerrenoFim())));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConTipoConstrucao())) {
						bemDTO.setCodTipoConstrucao(Integer.parseInt(bemImovelForm.getConTipoConstrucao()));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConTipoEdificacao())) {
						bemDTO.setCodTipoEdificacao(Integer.parseInt(bemImovelForm.getConTipoEdificacao()));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConTipoDocumentacao())) {
						bemDTO.setCodTipoDocumentacao(Integer.parseInt(bemImovelForm.getConTipoDocumentacao()));		
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConSituacaoOcupacao())) {
						bemDTO.setCodSituacaoOcupacao(Integer.parseInt(bemImovelForm.getConSituacaoOcupacao()));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConLote())) {
						bemDTO.setLote("%"+bemImovelForm.getConLote()+"%");
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConQuadra())) {
						bemDTO.setQuadra("%"+bemImovelForm.getConQuadra()+"%");
						flagBemDTONula = Boolean.FALSE;
					}				
					if(!Util.strEmBranco(bemImovelForm.getConDenominacaoImovel())) {
						bemDTO.setCodDenominacaoImovel(Integer.parseInt(bemImovelForm.getConDenominacaoImovel()));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco( bemImovelForm.getConAverbado())) {
						bemDTO.setAverbado( Boolean.parseBoolean( bemImovelForm.getConAverbado()));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConSituacaoImovel())) {
						bemDTO.setCodSituacaoImovel(Integer.parseInt(bemImovelForm.getConSituacaoImovel()));
						flagBemDTONula = Boolean.FALSE;
					}
					if(!Util.strEmBranco(bemImovelForm.getConOcupacao())) {
						bemDTO.setOrgaoOcupante(bemImovelForm.getConOcupacao());
						flagBemDTONula = Boolean.FALSE;
					}
					
					if (Boolean.FALSE.equals(flagBemDTONula)) { // Correcao do bug - Trazia todos os resultados do BD
						if (bemImovelForm.getIndOperadorOrgao()){//tratamento de operador de órgão
							Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
							List<Orgao> lista = new ArrayList<Orgao>();
							for (UsuarioOrgao item : usuario.getListaUsuarioOrgao()) {
								lista.add(item.getOrgao());
							}
							bemDTO.setListaOrgao(lista);
						}else{
							bemDTO.setListaOrgao(null);	
						}
						pagina = CadastroFacade.listarBemImovel(pagina, bemDTO);
					} else {
						pagina.setRegistros(new ArrayList<BemImovel>());
					}	
					Util.htmlEncodeCollection(pagina.getRegistros());
					request.setAttribute("pagina", pagina);
					if(pagina.getTotalRegistros() == 0) {
						throw new ApplicationException("AVISO.2", ApplicationException.ICON_AVISO);
					}
				}
			}
			
			return mapping.findForward("pgListBemImovel");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListBemImovel"));
			this.inicializar(request, form);
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListBemImovel"));
			this.inicializar(request, form);
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa","bem imóvel"}, e);
		}
	}
	
	/**
	 * Realiza o encaminhamento necessário para salvar o bem imóvel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvarBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		BemImovelForm bemImovelForm = (BemImovelForm) form;
		try	{
			setActionForward(mapping.findForward("pgEditBemImovel")); 
			
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				this.inicializar(request, form);
				throw new ApplicationException("AVISO.200");
			}	
			
			verificarTodasOperacoes( request );
			verificarGrupoUsuarioLogado(bemImovelForm, request);
			
			if (! Boolean.valueOf(request.getAttribute("I").toString())){
				setActionForward( mapping.findForward("pgEntrada") );
				this.inicializar(request,form);
				throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
			}
			
			// Aciona a validação do Form
			if(!validaForm(mapping,form,request)) {
				this.inicializar(request, form);
				return getActionForward();
			}		
			
			// verificar codBemImovel Duplicado
			Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
			if (CadastroFacade.obterBemImovelInstituicaoExibir(Integer.valueOf(bemImovelForm.getNrBemImovel()), 
					Integer.valueOf(bemImovelForm.getInstituicao()), usuario) != null){
				this.inicializar(request,form);			
				throw new ApplicationException("AVISO.3");
			}
			
			BemImovel bemImovel = new BemImovel();
			this.preencherBemImovel(bemImovel, bemImovelForm, request);
			bemImovel.setTsInclusao(new Date());
			bemImovel.setTsAtualizacao(new Date());
			
			BemImovel bemImovelAux = CadastroFacade.salvarBemImovel(bemImovel);
			bemImovelForm.setCodBemImovel(bemImovelAux.getCodBemImovel().toString());
			CadastroFacade.atualizarAreaBemImovel(Integer.valueOf(bemImovelForm.getCodBemImovel()));
			
			// Salva o TOKEN para evitar duplo submit
			saveToken(request);
			addMessage("SUCESSO.1",new String[]{bemImovelForm.getNrBemImovel()}, request);
			bemImovelForm.setActionType("alterar");
			this.inicializar(request, form);
			this.preencherFormularioBemImovel(bemImovelForm,Integer.valueOf(bemImovelForm.getCodBemImovel()));
			
			return getActionForward();
			
		} catch (ApplicationException appEx) {			
			this.inicializar(request,form);
			throw appEx;
		} catch (Exception e) {
			this.inicializar(request,form);
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Bem Imóvel"}, e);
		}
		
		
	}	
	
	/**
	 * Carrega combo de Orgaos. <br>
	 * @since 18/05/2011
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @throws ApplicationException
	 */
	public void carregarComboOrgaoSemAjax(BemImovelForm bemImovelForm, HttpServletRequest request) throws ApplicationException {
		verificarGrupoUsuarioLogado(bemImovelForm, request);
		
		request.setAttribute("orgaosRespPesq", null);
		request.setAttribute("orgaosPesq", null);
		request.setAttribute("orgaosResp", null);
		request.setAttribute("orgaos", null);

		if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())) {
			//ADM-GERAL
			if (bemImovelForm.getAdministracao() != null && !bemImovelForm.getAdministracao().trim().equals("")) {
				if (bemImovelForm.getInstituicao() != null && !bemImovelForm.getInstituicao().trim().equals("")){
					request.setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(bemImovelForm.getAdministracao()), Integer.valueOf(bemImovelForm.getInstituicao()))));
					request.setAttribute("orgaosResp", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(bemImovelForm.getAdministracao()), Integer.valueOf(bemImovelForm.getInstituicao()))));
				}else {
					request.setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Integer.valueOf(bemImovelForm.getAdministracao()), SentinelaComunicacao.getInstance(request).getCodUsuario())));
					request.setAttribute("orgaosResp", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Integer.valueOf(bemImovelForm.getAdministracao()), SentinelaComunicacao.getInstance(request).getCodUsuario())));
				}
			}
			if (bemImovelForm.getConAdministracao() != null && !bemImovelForm.getConAdministracao().trim().equals("")) {
				if (bemImovelForm.getConInstituicao() != null && !bemImovelForm.getConInstituicao().trim().equals("")){
					request.setAttribute("orgaosPesq", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(bemImovelForm.getConAdministracao()), Integer.valueOf(bemImovelForm.getConInstituicao()))));
					request.setAttribute("orgaosRespPesq", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(bemImovelForm.getConAdministracao()), Integer.valueOf(bemImovelForm.getConInstituicao()))));
				}else {
					request.setAttribute("orgaosPesq", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Integer.valueOf(bemImovelForm.getConAdministracao()), SentinelaComunicacao.getInstance(request).getCodUsuario())));
					request.setAttribute("orgaosRespPesq", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Integer.valueOf(bemImovelForm.getConAdministracao()), SentinelaComunicacao.getInstance(request).getCodUsuario())));
				}
			}
		} else {
			//NÃO É ADM-GERAL
			if (bemImovelForm.getAdministracao() != null && !bemImovelForm.getAdministracao().trim().equals("")) {
				if (bemImovelForm.getInstituicao() != null && !bemImovelForm.getInstituicao().trim().equals("")){
					request.setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(bemImovelForm.getAdministracao()), Integer.valueOf(bemImovelForm.getInstituicao()))));
				}else {
					request.setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Integer.valueOf(bemImovelForm.getAdministracao()), SentinelaComunicacao.getInstance(request).getCodUsuario())));
				}
			}
			if (bemImovelForm.getConAdministracao() != null && !bemImovelForm.getConAdministracao().trim().equals("")) {
				if (bemImovelForm.getConInstituicao() != null && !bemImovelForm.getConInstituicao().trim().equals("")){
					request.setAttribute("orgaosPesq", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(bemImovelForm.getConAdministracao()), Integer.valueOf(bemImovelForm.getConInstituicao()))));
				}else {
					request.setAttribute("orgaosPesq", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Integer.valueOf(bemImovelForm.getConAdministracao()), SentinelaComunicacao.getInstance(request).getCodUsuario())));
				}
			}
			
			Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
			if (usuario == null){
				throw new ApplicationException("AVISO.97");
			}
			boolean pertence = CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo());
			if (pertence) {
				if (usuario.getListaUsuarioOrgao() == null || usuario.getListaUsuarioOrgao().size() < 1){
					throw new ApplicationException("AVISO.96");
				}
			}
			Usuario usuarioAux = new Usuario();
			usuarioAux.setListaUsuarioOrgao(new HashSet<UsuarioOrgao>());

			if (bemImovelForm.getIndOperadorOrgao()){
				//Operador de Orgao
				for (UsuarioOrgao usuarioOrgao: usuario.getListaUsuarioOrgao()){
					usuarioAux.getListaUsuarioOrgao().add(usuarioOrgao);
				}
				request.setAttribute("orgaosRespPesq", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosUsuarioLogadoCombo(usuarioAux, request)));
				request.setAttribute("orgaosResp", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosUsuarioLogadoCombo(usuarioAux, request)));
			} else {
				//Não é Operador de Orgao
				for (UsuarioOrgao usuarioOrgao: usuario.getListaUsuarioOrgao()){
					if (bemImovelForm.getConAdministracao() != null && bemImovelForm.getConInstituicao() != null
							 && !bemImovelForm.getConAdministracao().trim().equals("") && !bemImovelForm.getConInstituicao().trim().equals("")){
						if (usuarioOrgao.getOrgao().getIndTipoAdministracao().equals(Integer.valueOf(bemImovelForm.getConAdministracao())) && 
								usuarioOrgao.getOrgao().getInstituicao().getCodInstituicao().equals(Integer.valueOf(bemImovelForm.getConInstituicao()))) {
							usuarioAux.getListaUsuarioOrgao().add(usuarioOrgao);
						}
					}
				}
				if (pertence) {
					request.setAttribute("orgaosRespPesq", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosUsuarioLogadoCombo(usuarioAux, request)));
				}
				usuarioAux = new Usuario();
				usuarioAux.setListaUsuarioOrgao(new HashSet<UsuarioOrgao>());
				for (UsuarioOrgao usuarioOrgao: usuario.getListaUsuarioOrgao()){
					if (bemImovelForm.getAdministracao() != null && bemImovelForm.getInstituicao() != null){
						if (usuarioOrgao.getOrgao().getIndTipoAdministracao().equals(Integer.valueOf(bemImovelForm.getAdministracao())) && 
								usuarioOrgao.getOrgao().getInstituicao().getCodInstituicao().equals(Integer.valueOf(bemImovelForm.getInstituicao()))) {
							usuarioAux.getListaUsuarioOrgao().add(usuarioOrgao);
						}
					}
				}
				if (pertence) {
					request.setAttribute("orgaosResp", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosUsuarioLogadoCombo(usuarioAux, request)));
				}
			}
		}
	}
	
	/**
	 * Realiza o encaminhamento necessário para atualizar os dados da denominação do imóvel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterarBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		BemImovelForm bemImovelForm = (BemImovelForm) form;
		try	{
			setActionForward( mapping.findForward("pgEditBemImovel") );
				
			if (!(bemImovelForm.getActionTypeOcupResp().equals(ADD_ORGAO_RESP) || bemImovelForm.getActionTypeOcupResp().equals(RMV_ORGAO_RESP))) {
				// Verifica se o TOKEN existe para evitar duplo submit
				if(isTokenValid(request)) {
					resetToken(request);
				} else {
					carregarListaOcupacaoOrgaoResponsavel(request, bemImovelForm.getCodBemImovel());
					this.inicializar(request, form);
					throw new ApplicationException("AVISO.200");
				}
			}
		
			verificarTodasOperacoes( request );
			verificarGrupoUsuarioLogado(bemImovelForm, request);
			
			if (! Boolean.valueOf(request.getAttribute("A").toString())){
				setActionForward( mapping.findForward("pgEntrada") );
				this.inicializar(request, form);
				throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
			}
			
			// Aciona a validação do Form
			if(!validaForm(mapping,form,request)) {
				this.inicializar(request, form);
				return getActionForward();
			}
	
			BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(bemImovelForm.getCodBemImovel()));
			
			this.preencherBemImovel(bemImovel, bemImovelForm, request);
			
			bemImovel.setTsAtualizacao(new Date());
			CadastroFacade.alterarBemImovel(bemImovel);
			CadastroFacade.atualizarAreaBemImovel(Integer.valueOf(bemImovelForm.getCodBemImovel()));
			// Salva o TOKEN para evitar duplo submit
			saveToken(request);
			addMessage("SUCESSO.2",new String[]{bemImovelForm.getNrBemImovel()}, request);
			this.inicializar(request, form);
			this.preencherFormularioBemImovel(bemImovelForm, Integer.valueOf(bemImovelForm.getCodBemImovel()));
			carregarListaOcupacaoOrgaoResponsavel(request, bemImovelForm.getCodBemImovel());
			
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			this.inicializar(request, form);
			throw appEx;
		} catch (Exception e) {
			this.inicializar(request, form);
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Bem Imóvel"}, e);
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
	
	public ActionForward excluirBemImovel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		BemImovelForm bemImovelForm = (BemImovelForm) form;
		try {
			setActionForward(mapping.findForward("pgListBemImovel"));			
			verificarTodasOperacoes( request );
			verificarGrupoUsuarioLogado(bemImovelForm, request);
			this.inicializar(request, form);

			if (! Boolean.valueOf(request.getAttribute("E").toString())){
				setActionForward( mapping.findForward("pgEntrada") );
				this.inicializar(request, form);
				throw new ApplicationException("AVISO.44", new String[]{"Excluir"}, ApplicationException.ICON_AVISO);				
			}

			setActionForward(mapping.findForward("pgViewBemImovel"));

			if(StringUtil.stringNotNull(bemImovelForm.getCodBemImovel())) {				
				request.setAttribute("bemImovel", CadastroFacade.obterBemImovelExibir(Integer.valueOf(bemImovelForm.getCodBemImovel())));				
			}

			if (bemImovelForm.getCodBemImovel() != null) {				
				CadastroFacade.excluirBemImovel(Integer.parseInt(bemImovelForm.getCodBemImovel()));
				bemImovelForm.setCodBemImovel("");
				addMessage("SUCESSO.21", request);
			}		
			this.inicializar(request,form);
			return mapping.findForward("pgListBemImovel");
		} catch (ApplicationException appEx) {
			this.inicializar(request, form);
			throw appEx;
		} catch (Exception e) {
			this.inicializar(request, form);
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Bem Imóvel"}, e);
		}
	}
	
	
	private void preencherBemImovel(BemImovel bemImovel, BemImovelForm bemImovelForm, HttpServletRequest request) throws ApplicationException, ParseException 
	{
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
		bemImovel.setInstituicao(CadastroFacade.obterInstituicao(Integer.valueOf(bemImovelForm.getInstituicao())));
		bemImovel.setNrBemImovel(Integer.valueOf(bemImovelForm.getNrBemImovel()));
		bemImovel.setAdministracao(Integer.valueOf(bemImovelForm.getAdministracao()));
		bemImovel.setAreaTerreno(Valores.converterStringParaBigDecimal(bemImovelForm.getAreaTerreno()));
		bemImovel.setObservacoesMigracao(bemImovelForm.getObservacoesMigracao());
		bemImovel.setBairroDistrito(bemImovelForm.getBairroDistrito());
		bemImovel.setCep(bemImovelForm.getCep());
		if (StringUtil.stringNotNull(bemImovelForm.getClassificacaoBemImovel())){
			bemImovel.setClassificacaoBemImovel(CadastroFacade.obterClassificacaoBemImovel(Integer.valueOf(bemImovelForm.getClassificacaoBemImovel())));
		}else{
			bemImovel.setClassificacaoBemImovel(null);
		}
		bemImovel.setComplemento(bemImovelForm.getComplemento());
		bemImovel.setCpfResponsavel(sentinelaInterface.getCpf());
		if(StringUtil.stringNotNull(bemImovelForm.getDataIncorporacao())){
			bemImovel.setDataIncorporacao(sdf.parse(bemImovelForm.getDataIncorporacao()));
		}else{
			bemImovel.setDataIncorporacao(null);
		}
		if(StringUtil.stringNotNull(bemImovelForm.getDenominacaoImovel())){
			bemImovel.setDenominacaoImovel(CadastroFacade.obterDenominacaoImovel( Integer.valueOf(bemImovelForm.getDenominacaoImovel())));
		}else{
			bemImovel.setDenominacaoImovel(null);
		}
		bemImovel.setObservacoesMigracao(bemImovelForm.getObservacoesMigracao());
		if(StringUtil.stringNotNull(bemImovelForm.getFormaIncorporacao())){
			bemImovel.setFormaIncorporacao(CadastroFacade.obterFormaIncorporacao(Integer.valueOf(bemImovelForm.getFormaIncorporacao())));
		}else{
			bemImovel.setFormaIncorporacao(null);
		}
		bemImovel.setLogradouro(bemImovelForm.getLogradouro());
		bemImovel.setMunicipio(bemImovelForm.getMunicipio());
		bemImovel.setCodMunicipio(Integer.parseInt(bemImovelForm.getCodMunicipio()));
		bemImovel.setNumero(bemImovelForm.getNumero());
		if (StringUtil.stringNotNull(bemImovelForm.getNumeroProcessoSpi())){
			bemImovel.setNumeroProcessoSpi(Long.parseLong(bemImovelForm.getNumeroProcessoSpi()));
		}else{
			bemImovel.setNumeroProcessoSpi(null);
		}
		if (StringUtil.stringNotNull(bemImovelForm.getOrgao())){
			bemImovel.setOrgao(CadastroFacade.obterOrgao(Integer.parseInt(bemImovelForm.getOrgao())));
		}else{
			bemImovel.setOrgao(null);
		}
		if (StringUtil.stringNotNull(bemImovelForm.getSituacaoImovel())){
			bemImovel.setSituacaoImovel(CadastroFacade.obterSituacaoImovel(Integer.valueOf(bemImovelForm.getSituacaoImovel())));
		}else{
			bemImovel.setSituacaoImovel(null);
		}
		if (StringUtil.stringNotNull(bemImovelForm.getSituacaoLegalCartorial())){
			bemImovel.setSituacaoLegalCartorial(CadastroFacade.obterSituacaoLegalCartorial(Integer.valueOf (bemImovelForm.getSituacaoLegalCartorial())));
		}else{
			bemImovel.setSituacaoLegalCartorial(null);
		}
		bemImovel.setSituacaoLocal(Integer.valueOf(bemImovelForm.getSituacaoLocal()));
		bemImovel.setUf(bemImovelForm.getUf());
		bemImovel.setSomenteTerreno(bemImovelForm.getSomenteTerreno());

	}
	
	private void preencherFormularioBemImovel(BemImovelForm bemImovelForm, Integer codBemImovel) throws ApplicationException, Exception
	{
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	
		
		BemImovel bemImovel = CadastroFacade.obterBemImovel(codBemImovel);
		bemImovelForm.setNrBemImovel(bemImovel.getNrBemImovel().toString());
		if (bemImovel.getInstituicao() != null && bemImovel.getInstituicao().getCodInstituicao() > 0) {
			bemImovelForm.setInstituicaoDesc(CadastroFacade.obterInstituicao(bemImovel.getInstituicao().getCodInstituicao()).getSiglaDescricao());
			bemImovelForm.setInstituicao(bemImovel.getInstituicao().getCodInstituicao().toString());
		}
		bemImovelForm.setAdministracao(bemImovel.getAdministracao().toString());
		if(bemImovel.getOrgao()!=null){
			bemImovelForm.setOrgao(bemImovel.getOrgao().getCodOrgao().toString());
		}
		if(bemImovel.getClassificacaoBemImovel() != null){
			bemImovelForm.setClassificacaoBemImovel(bemImovel.getClassificacaoBemImovel().getCodClassificacaoBemImovel().toString());
		}
		bemImovelForm.setSituacaoLocal(bemImovel.getSituacaoLocal().toString());
		if (bemImovel.getSituacaoLegalCartorial()!=null){
			bemImovelForm.setSituacaoLegalCartorial(bemImovel.getSituacaoLegalCartorial().getCodSituacaoLegalCartorial().toString());
		}
		if (bemImovel.getNumeroProcessoSpi()!= null){
			bemImovelForm.setNumeroProcessoSpi(bemImovel.getNumeroProcessoSpi().toString());
		}
		bemImovelForm.setCep(bemImovel.getCep());
		bemImovelForm.setUf(bemImovel.getUf());
		bemImovelForm.setCodMunicipio(bemImovel.getCodMunicipio().toString());
		bemImovelForm.setMunicipio(bemImovel.getMunicipio());
		bemImovelForm.setBairroDistrito(bemImovel.getBairroDistrito());
		bemImovelForm.setLogradouro(bemImovel.getLogradouro());
		bemImovelForm.setNumero(bemImovel.getNumero());
		bemImovelForm.setComplemento(bemImovel.getComplemento());
		bemImovelForm.setObservacoesMigracao(bemImovel.getObservacoesMigracao());
		if (bemImovel.getFormaIncorporacao() != null){
			bemImovelForm.setFormaIncorporacao(bemImovel.getFormaIncorporacao().getCodFormaIncorporacao().toString());
		}
		if (bemImovel.getDataIncorporacao()!= null){
			bemImovelForm.setDataIncorporacao(sdf.format(bemImovel.getDataIncorporacao()));
		}
		if (bemImovel.getSituacaoImovel() != null){
			bemImovelForm.setSituacaoImovel(bemImovel.getSituacaoImovel().getCodSituacaoImovel().toString());
		}
		bemImovelForm.setComplemento(bemImovel.getComplemento());
		if (bemImovel.getDenominacaoImovel()!= null){
			bemImovelForm.setDenominacaoImovel(bemImovel.getDenominacaoImovel().getCodDenominacaoImovel().toString());
		}
		if (bemImovel.getAreaTerreno() != null){
			bemImovelForm.setAreaTerreno(Valores.formatarParaDecimal(bemImovel.getAreaTerreno(), 2));
		}
		if (bemImovel.getAreaConstruida()!=null ){
			bemImovelForm.setAreaConstruida(Valores.formatarParaDecimal(bemImovel.getAreaConstruida(), 2));
		}
		if (bemImovel.getAreaDispoNivel()!=null ){
			bemImovelForm.setAreaDispoNivel(Valores.formatarParaDecimal(bemImovel.getAreaDispoNivel(), 2));
		}
		
		bemImovelForm.setSomenteTerreno(bemImovel.getSomenteTerreno());

		int result = CadastroFacade.obterQtdOcupacaoOrgaoResponsavelPorBemImovel(bemImovelForm.getCodBemImovel(), Boolean.TRUE);
		bemImovelForm.setQtdRegResponsavelAtivo(String.valueOf(result));
		result = CadastroFacade.obterQtdOcupacaoOrgaoResponsavelPorBemImovel(bemImovelForm.getCodBemImovel(), Boolean.FALSE);
		bemImovelForm.setQtdRegResponsavelInativo(String.valueOf(result));
		result = CadastroFacade.obterQtdEdificacaoPorBemImovel(bemImovelForm.getCodBemImovel());
		bemImovelForm.setQtdRegEdificacao(String.valueOf(result));

		bemImovelForm.setOr_administracao("");
		bemImovelForm.setOr_codBemImovel(codBemImovel.toString());
		bemImovelForm.setOr_codOcupacao("");
		bemImovelForm.setOr_codOrgao("");
		bemImovelForm.setOr_dataLei("");
		bemImovelForm.setOr_dataOcupacao("");
		bemImovelForm.setOr_descricao("");
		bemImovelForm.setOr_especificacao("");
		bemImovelForm.setOr_numeroLei("");
		bemImovelForm.setOr_numeroNotificacao("");
		bemImovelForm.setOr_ocupacaoMetroQuadrado("");
		bemImovelForm.setOr_ocupacaoPercentual("");
		bemImovelForm.setOr_prazoNotificacao("");
		bemImovelForm.setOr_protocoloNotificacaoSpi("");
		bemImovelForm.setOr_situacaoOcupacao("");
		bemImovelForm.setOr_termoTransferencia("");
		bemImovelForm.setOr_vigenciaLei("");
		bemImovelForm.setOr_situacaoOcupacao(Mensagem.getInstance().getMessage("CODIGO_SITUACAO_OCUPACAO_TERRENO_NU"));

	}

	/**
	 * Realiza o encaminhamento para exclusão de aluno de bem imóvel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward baixarBemImovel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		BemImovelForm bemImovelForm = (BemImovelForm) form;
		try {
			verificarTodasOperacoes( request );
			verificarGrupoUsuarioLogado(bemImovelForm, request);
			
			if (! Boolean.valueOf(request.getAttribute("B").toString())){
				setActionForward( mapping.findForward("pgEntrada") );
				this.inicializar(request, form);
				throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
			}
			
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			setActionForward(mapping.findForward("pgViewBemImovel"));

			if (bemImovelForm.getCodBemImovel() != null) {
				BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(bemImovelForm.getCodBemImovel()));
				bemImovel.setTsAtualizacao(new Date());
				bemImovel.setCpfResponsavel(sentinelaInterface.getCpf());
				bemImovel.setSituacaoImovel( CadastroFacade.obterSituacaoImovel(2) );				
				CadastroFacade.alterarBemImovel(bemImovel);				

				bemImovelForm.setCodBemImovel("");
				addMessage("SUCESSO.22", request);
			}
			this.inicializar(request, form);
			return mapping.findForward("pgListBemImovel");
			
		} catch (ApplicationException appEx) {
			this.inicializar(request, form);
			throw appEx;
		} catch (Exception e) {
			this.inicializar(request, form);
			throw new ApplicationException("ERRO.201", new String[]{"ao realizar a baixa do Bem Imóvel"}, e);
		}
	}
	
	public void carregarAnexoDocumentacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException, Exception {

		try {
			if (StringUtil.stringNotNull(request.getParameter("codDocumentacao"))){
				
				Documentacao doc = CadastroFacade.obterDocumentacao(Integer.valueOf (request.getParameter("codDocumentacao")));
				String nomeArquivo = doc.getAnexo().replaceAll(" ", "_");
				byte [] bytes = CadastroFacade.obterAnexoDocumentacao(Integer.valueOf (request.getParameter("codDocumentacao")));
				
				response.setHeader("Content-disposition", "attachment;filename="+nomeArquivo);
				response.setContentType("text/plain");  
				response.setContentLength(bytes.length);  
				ServletOutputStream ouputStream;

				ouputStream = response.getOutputStream();

				ouputStream.write(bytes, 0, bytes.length);  
				ouputStream.flush();  
				ouputStream.close(); 
			}
		} catch (ApplicationException appEx) {
			setActionForward(carregarPgViewBemImovel(mapping,form,request,response));
			this.inicializar(request, form);
			throw appEx;
		} catch (Exception e) {
			setActionForward(carregarPgViewBemImovel(mapping,form,request,response));
			this.inicializar(request, form);
			throw new ApplicationException("ERRO.200", new String[]{"visualizar documento"," bem imóvel"}, e);
		}  

	}
	
	public ActionForward transformarMetroQuadrado(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		BemImovelForm bemImovelForm = (BemImovelForm) form;
		try{
			verificarGrupoUsuarioLogado(bemImovelForm, request);
			if (! StringUtil.stringNotNull(bemImovelForm.getAreaOriginal()) || ! StringUtil.stringNotNull(bemImovelForm.getMedidaOriginal()))
			{			
				this.inicializar(request, form);
				throw new ApplicationException("AVISO.4");
			}
			Double areaOriginal =  (Valores.converterStringParaBigDecimal(bemImovelForm.getAreaOriginal())).doubleValue();
			Double areaTerreno = null;
			switch (Integer.parseInt(bemImovelForm.getMedidaOriginal())){
				case 0: //metro quadrado
					areaTerreno = areaOriginal;
					break;
				case 1: //alqueire paulista
					areaTerreno = areaOriginal * 24200;
					break;
				case 2: //are
					areaTerreno = areaOriginal*100;
					break;
				case 3://hectare
					areaTerreno = areaOriginal*10000;
					break;
				case 4://litro
					areaTerreno = areaOriginal*605;
					break;
				default:
					areaTerreno = areaOriginal;
			}
			bemImovelForm.setAreaTerreno(Valores.formatarParaDecimal(areaTerreno, 2));
			return mapping.findForward("pgEditAreaAjaxBemImovel");
		
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditAreaAjaxBemImovel"));
			this.inicializar(request, form);
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditAreaAjaxBemImovel"));
			this.inicializar(request, form);
			throw new ApplicationException("ERRO.201", new String[]{"ao realizar o cálculo da área terreno do Bem Imóvel"}, e);
		}
		
	}
		
	/**
	 * Carrega o combobox de orgãos de acordo com a Administração selecionada
	 * @param form
	 */
	public ActionForward carregarComboOrgaoPesq (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboOrgaoPesq processando...");
		BemImovelForm localForm = (BemImovelForm) form;
		try {
			
			setActionForward(mapping.findForward("comboOrgaoPesq"));

			verificarGrupoUsuarioLogado(localForm, request);
			String administ = (StringUtils.isNotBlank(localForm.getConAdministracao()))?localForm.getConAdministracao():null;
			String instituicao = (StringUtils.isNotBlank(localForm.getConInstituicao()))?localForm.getConInstituicao():null;
			
			localForm.setConOrgao(null);
			request.setAttribute("orgaosPesq", null);
			
			if (localForm.getIsGpAdmGeralUsuarioLogado().equalsIgnoreCase("S")) {
				if (administ != null && instituicao != null) {
					request.setAttribute("orgaosPesq", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(administ), Integer.valueOf(instituicao))));
				}
			} else {
				if (administ != null){
					if (instituicao != null) {
						request.setAttribute("orgaosPesq", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(administ), Integer.valueOf(instituicao))));
					} else {
						request.setAttribute("orgaosPesq", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Integer.valueOf(administ), SentinelaComunicacao.getInstance(request).getCodUsuario())));
					}
				}
			}
			
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{"BemImovelAction.carregarComboOrgaoPesq"}, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Carrega o combobox de orgãos responsáveis de acordo com a Administração selecionada
	 * @param form
	 */
	public ActionForward carregarComboOrgaoRespPesq (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboOrgaoRespPesq processando...");
		BemImovelForm localForm = (BemImovelForm) form;
		try {
			
			setActionForward(mapping.findForward("comboOrgaoRespPesq"));

			verificarGrupoUsuarioLogado(localForm, request);
			String administ = (StringUtils.isNotBlank(localForm.getConAdministracao()))?localForm.getConAdministracao():null;
			String instituicao = (StringUtils.isNotBlank(localForm.getConInstituicao()))?localForm.getConInstituicao():null;
			
			localForm.setConOrgaoResp(null);
			request.setAttribute("orgaosRespPesq", null);
			
			if (localForm.getIsGpAdmGeralUsuarioLogado().equalsIgnoreCase("S")) {
				//ADM-GERAL
				if (administ != null && instituicao != null){
					request.setAttribute("orgaosRespPesq", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(administ), Integer.valueOf(instituicao))));
				}
			} else {
				//NÃO É ADM-GERAL
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
				if (usuario == null){
					throw new ApplicationException("AVISO.97");
				}
				if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo())) {
					if (usuario.getListaUsuarioOrgao() == null || usuario.getListaUsuarioOrgao().size() < 1){
						throw new ApplicationException("AVISO.96");
					}
				}
				
				Usuario usuarioAux = new Usuario();
				usuarioAux.setListaUsuarioOrgao(new HashSet<UsuarioOrgao>());

				if (localForm.getIndOperadorOrgao()){
					//Operador de Orgao
					for (UsuarioOrgao usuarioOrgao: usuario.getListaUsuarioOrgao()){
						usuarioAux.getListaUsuarioOrgao().add(usuarioOrgao);
					}
					request.setAttribute("orgaosRespPesq", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosUsuarioLogadoCombo(usuarioAux, request)));
				} else {
					//Não é Operador de Orgao
					for (UsuarioOrgao usuarioOrgao: usuario.getListaUsuarioOrgao()){
						if (administ != null && instituicao != null){
							if (usuarioOrgao.getOrgao().getIndTipoAdministracao().equals(Integer.valueOf(administ)) && 
									usuarioOrgao.getOrgao().getInstituicao().getCodInstituicao().equals(Integer.valueOf(instituicao))) {
								usuarioAux.getListaUsuarioOrgao().add(usuarioOrgao);
							}
						}
					}
					request.setAttribute("orgaosRespPesq", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosUsuarioLogadoCombo(usuarioAux, request)));
				}
			}

			return getActionForward();
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{"BemImovelAction.carregarComboOrgaoPesq"}, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Carrega o combobox de orgãos de acordo com a Administração selecionada
	 * @param form
	 */
	public ActionForward carregarComboOrgao (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboOrgao processando...");
		BemImovelForm localForm = (BemImovelForm) form;
		try {
			
			setActionForward(mapping.findForward("comboOrgao"));
			
			verificarGrupoUsuarioLogado(localForm, request);
			String administ = (StringUtils.isNotBlank(localForm.getAdministracao()))?localForm.getAdministracao():null;
			String instituicao = (StringUtils.isNotBlank(localForm.getInstituicao()))?localForm.getInstituicao():null;
			
			localForm.setOrgao(null);
			request.setAttribute("orgaos", null);

			if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())) {
				if (administ != null && instituicao != null) {
					request.setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(administ), Integer.valueOf(instituicao))));
				}
			} else {
				if (administ != null){
					if (instituicao != null) {
						request.setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(administ), Integer.valueOf(instituicao))));
					} else {
						request.setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Integer.valueOf(administ), SentinelaComunicacao.getInstance(request).getCodUsuario())));
					}
				}
			}
			
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{"BemImovelAction.carregarComboOrgao"}, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Carrega o combobox de orgãos responsáveis de acordo com a Administração selecionada
	 * @param form
	 */
	public ActionForward carregarComboOrgaoResp (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboOrgaoResp processando...");
		BemImovelForm localForm = (BemImovelForm) form;
		try {
			
			setActionForward(mapping.findForward("comboOrgaoResp"));

			verificarGrupoUsuarioLogado(localForm, request);
			String administ = (StringUtils.isNotBlank(localForm.getAdministracao()))?localForm.getAdministracao():null;
			String instituicao = (StringUtils.isNotBlank(localForm.getInstituicao()))?localForm.getInstituicao():null;
			
			localForm.setOrgaoResp(null);
			request.setAttribute("orgaosResp", null);
			
			if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())) {
				//ADM-GERAL
				if (administ != null && instituicao != null){
					request.setAttribute("orgaosResp", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(administ), Integer.valueOf(instituicao))));
				}
			} else {
				//NÃO É ADM-GERAL
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
				if (usuario == null){
					throw new ApplicationException("AVISO.97");
				}
				if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo())) {
					if (usuario.getListaUsuarioOrgao() == null || usuario.getListaUsuarioOrgao().size() < 1){
						throw new ApplicationException("AVISO.96");
					}
				}
				
				Usuario usuarioAux = new Usuario();
				usuarioAux.setListaUsuarioOrgao(new HashSet<UsuarioOrgao>());

				if (localForm.getIndOperadorOrgao()){
					//Operador de Orgao
					for (UsuarioOrgao usuarioOrgao: usuario.getListaUsuarioOrgao()){
						usuarioAux.getListaUsuarioOrgao().add(usuarioOrgao);
					}
					request.setAttribute("orgaosResp", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosUsuarioLogadoCombo(usuarioAux, request)));
				} else {
					//Não é Operador de Orgao
					for (UsuarioOrgao usuarioOrgao: usuario.getListaUsuarioOrgao()){
						if (administ != null && instituicao != null){
							if (usuarioOrgao.getOrgao().getIndTipoAdministracao().equals(Integer.valueOf(administ)) && 
									usuarioOrgao.getOrgao().getInstituicao().getCodInstituicao().equals(Integer.valueOf(instituicao))) {
								usuarioAux.getListaUsuarioOrgao().add(usuarioOrgao);
							}
						}
					}
					request.setAttribute("orgaosResp", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosUsuarioLogadoCombo(usuarioAux, request)));
				}
			}

			return getActionForward();
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{"BemImovelAction.carregarComboOrgaoPesq"}, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Salva a ocupação e o orgão responsável
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response 
	 */
	public ActionForward adicionarOcupacaoOrgaoResponsavel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método adicionarOcupacaoOrgaoResponsavel processando...");
		BemImovelForm localForm = (BemImovelForm) form;
		try {
			verificarGrupoUsuarioLogado(localForm, request);
			
			setActionForward(mapping.findForward("pgEditBemImovel")); 
			
			verificarTodasOperacoes( request );
			if (!localForm.getActionType().trim().equalsIgnoreCase("incluir") && !localForm.getActionType().trim().equalsIgnoreCase("alterar")){  //Boolean.valueOf(request.getAttribute("I").toString())){
				setActionForward( mapping.findForward("pgEntrada") );
				this.inicializar(request, form);			
				throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
			}
			
			// Aciona a validação do Form
			if(!validaForm(mapping,form,request)) {
				this.inicializar(request, form);
				return getActionForward();
			}		

			// Aciona a validação do Form
			if(!validaFormOcupacaoResponsavel(form)) {
				this.inicializar(request, form);
				return getActionForward();
			}		

			BemImovel auxBemImovel = null;

			if (localForm.getActionType().trim().equalsIgnoreCase("incluir")){  //Boolean.valueOf(request.getAttribute("I").toString())){
				// verificar codBemImovel Duplicado
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
				if (CadastroFacade.obterBemImovelInstituicaoExibir(Integer.valueOf(localForm.getNrBemImovel()), 
						Integer.valueOf(localForm.getInstituicao()), usuario) != null){
					this.inicializar(request, form);			
					throw new ApplicationException("AVISO.3");
				}
				BemImovel bemImovel = new BemImovel();
				this.preencherBemImovel(bemImovel, localForm, request);
				bemImovel.setTsInclusao(new Date());
				bemImovel.setTsAtualizacao(new Date());
				
				auxBemImovel = CadastroFacade.salvarBemImovel(bemImovel);
				localForm.setCodBemImovel(auxBemImovel.getCodBemImovel().toString());
				CadastroFacade.atualizarAreaBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			} else {
				BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
				auxBemImovel = bemImovel;
				this.preencherBemImovel(bemImovel, localForm, request);
				bemImovel.setTsAtualizacao(new Date());
				CadastroFacade.alterarBemImovel(bemImovel);
				CadastroFacade.atualizarAreaBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			}
			
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date dataAtual = new Date();

			Ocupacao ocupacao = new Ocupacao();
			Orgao orgao = CadastroFacade.obterOrgao(Integer.valueOf(localForm.getOr_codOrgao()));

			CadastroFacade.verificarDuplicidadeOrgaoOcupacaoTerreno(orgao, auxBemImovel);
			
			ocupacao.setBemImovel(auxBemImovel);
			ocupacao.setOrgao(orgao);
			ocupacao.setDescricao(localForm.getOr_descricao());
			ocupacao.setCpfResponsavel(sentinelaInterface.getCpf());
			ocupacao.setDataLei(StringUtil.stringNotNull(localForm.getOr_dataLei()) ? sdf.parse(localForm.getOr_dataLei()) : null);
			ocupacao.setDataOcupacao(StringUtil.stringNotNull(localForm.getOr_dataOcupacao()) ? sdf.parse(localForm.getOr_dataOcupacao()) : null);
			ocupacao.setTermoTransferencia(localForm.getOr_termoTransferencia());
			ocupacao.setEdificacao(null);
			ocupacao.setNumeroLei(localForm.getOr_numeroLei());
			ocupacao.setNumeroNotificacao(localForm.getOr_numeroNotificacao());
			ocupacao.setSituacaoOcupacao(CadastroFacade.obterSituacaoOcupacao(Integer.parseInt(localForm.getOr_situacaoOcupacao())));

			if(StringUtil.stringNotNull(localForm.getOr_ocupacaoMetroQuadrado())) {
				Double ocupacaoMetroQuadrado =  (Valores.converterStringParaBigDecimal(localForm.getOr_ocupacaoMetroQuadrado())).doubleValue();
				Double areaTerreno=  new Double(0);				
				if (localForm.getAreaTerreno() != null && !localForm.getAreaTerreno().isEmpty()){
					areaTerreno=  Double.parseDouble(Util.converteDecimal(localForm.getAreaTerreno()).toString());
				} else {
					this.inicializar(request, form);			
					throw new ApplicationException("AVISO.46");
				}
				if(!StringUtil.stringNotNull(localForm.getOr_ocupacaoPercentual())) {
					Double ocupacaoPercentual = ocupacaoMetroQuadrado/areaTerreno;
					ocupacaoPercentual = Valores.arredondar((ocupacaoPercentual * 100), 2);
					ocupacao.setOcupacaoPercentual(BigDecimal.valueOf(ocupacaoPercentual));
				}
				ocupacao.setOcupacaoMetroQuadrado(BigDecimal.valueOf(ocupacaoMetroQuadrado));
			}
			if(StringUtil.stringNotNull(localForm.getOr_ocupacaoPercentual())) {
				Double ocupacaoPercentual = (Valores.converterStringParaBigDecimal(localForm.getOr_ocupacaoPercentual())).doubleValue();
				Double areaTerreno=  Double.parseDouble(Util.converteDecimal(localForm.getAreaTerreno()).toString());
				if(ocupacaoPercentual.doubleValue() > 100) {
					this.inicializar(request, form);			
					throw new ApplicationException("AVISO.8");
				}
				if(!StringUtil.stringNotNull(localForm.getOr_ocupacaoMetroQuadrado())) {
					Double ocupacaoMetroQuadrado = ocupacaoPercentual/100;
					ocupacaoMetroQuadrado = Valores.arredondar((ocupacaoMetroQuadrado * areaTerreno), 2);
					ocupacao.setOcupacaoMetroQuadrado(BigDecimal.valueOf(ocupacaoMetroQuadrado));
				}
				ocupacao.setOcupacaoPercentual(BigDecimal.valueOf(ocupacaoPercentual));
			}

			ocupacao.setProtocoloNotificacaoSpi(StringUtil.stringNotNull(localForm.getOr_protocoloNotificacaoSpi()) ? Integer.parseInt(localForm.getOr_protocoloNotificacaoSpi()) : null);
			ocupacao.setPrazoNotificacao(localForm.getOr_prazoNotificacao());
			ocupacao.setTsAtualizacao(dataAtual);
			ocupacao.setTsInclusao(dataAtual);
			ocupacao.setVigenciaLei(localForm.getOr_vigenciaLei());

			CadastroFacade.salvarOcupacao(ocupacao);
			
			carregarListaOcupacaoOrgaoResponsavel(request, localForm.getCodBemImovel());
			this.carregarComboOrgaoSemAjax(localForm, request);
			
			if (localForm.getActionType().trim().equalsIgnoreCase("incluir")){  //Boolean.valueOf(request.getAttribute("I").toString())){
				addMessage("SUCESSO.52",new String[]{localForm.getNrBemImovel(), orgao.getDescricao()}, request);
			} else {
				addMessage("SUCESSO.53",new String[]{localForm.getNrBemImovel(), orgao.getDescricao()}, request);
			}
			localForm.setActionType("alterar");
			localForm.setActionTypeOcupResp(ADD_ORGAO_RESP);
			this.inicializar(request, form);
			this.preencherFormularioBemImovel(localForm,Integer.valueOf(localForm.getCodBemImovel()));

			return getActionForward();
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			carregarListaOcupacaoOrgaoResponsavel(request, localForm.getCodBemImovel());
			this.inicializar(request, form);
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			carregarListaOcupacaoOrgaoResponsavel(request, localForm.getCodBemImovel());
			this.inicializar(request, form);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"BemImovelAction.adicionarOcupacaoOrgaoResponsavel"}, e, ApplicationException.ICON_ERRO);
		}
	}

	private boolean validaFormOcupacaoResponsavel(ActionForm form) throws ApplicationException {
		log.info("Procedimento de validar dados do Form processando...");
		BemImovelForm localForm = (BemImovelForm) form;
		StringBuffer str = new StringBuffer();

		if (StringUtil.stringNotNull(localForm.getOr_descricao()) && localForm.getOr_descricao().trim().equals("")) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Descrição");
		}

		if (StringUtil.stringNotNull(localForm.getOr_ocupacaoMetroQuadrado()) && localForm.getOr_ocupacaoMetroQuadrado().trim().equals("")) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Ocupação em m²");
		}

		if (StringUtil.stringNotNull(localForm.getOr_ocupacaoPercentual()) && localForm.getOr_ocupacaoPercentual().trim().equals("")) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Ocupação em percentual");
		}

		if (StringUtil.stringNotNull(localForm.getOr_codOrgao()) && localForm.getOr_codOrgao().trim().equals("")) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Órgão responsável");
		}
		
		if (str.length() > 0) {
			str.append(".");
			throw new ApplicationException("mensagem.erro.1", new String[]{str.toString()}, ApplicationException.ICON_AVISO);
		}			
	
		return true;
	}

	/**
	 * Remover a ocupação e o orgão responsável
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response 
	 */
	public ActionForward removerOcupacaoOrgaoResponsavel (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método removerOcupacaoOrgaoResponsavel processando...");
		BemImovelForm localForm = (BemImovelForm) form;
		try {
			setActionForward(mapping.findForward("pgEditBemImovel"));

			verificarTodasOperacoes( request );
			if (! Boolean.valueOf(request.getAttribute("A").toString())){
				setActionForward( mapping.findForward("pgEntrada") );
				carregarListaOcupacaoOrgaoResponsavel(request, localForm.getCodBemImovel());
				this.inicializar(request, form);			
				throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
			}
			verificarGrupoUsuarioLogado(localForm, request);

			// Aciona a validação do Form
			if(!validaForm(mapping,form,request)) {
				carregarListaOcupacaoOrgaoResponsavel(request, localForm.getCodBemImovel());
				this.inicializar(request, form);
				return getActionForward();
			}		

			BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			this.preencherBemImovel(bemImovel, localForm, request);
			bemImovel.setTsAtualizacao(new Date());
			CadastroFacade.alterarBemImovel(bemImovel);
			CadastroFacade.atualizarAreaBemImovel(Integer.valueOf(localForm.getCodBemImovel()));

			CadastroFacade.excluirOcupacao(Integer.valueOf(localForm.getOr_codOcupacao()));
			localForm.setOr_codOcupacao("");
			
			carregarListaOcupacaoOrgaoResponsavel(request, localForm.getCodBemImovel());
			
			addMessage("SUCESSO.54",new String[]{localForm.getNrBemImovel()}, request);
			localForm.setActionTypeOcupResp(RMV_ORGAO_RESP);

			this.inicializar(request, form);
			this.preencherFormularioBemImovel(localForm,Integer.valueOf(localForm.getCodBemImovel()));

			return getActionForward();
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			this.inicializar(request, form);			
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			this.inicializar(request, form);			
			throw new ApplicationException("mensagem.erro.9001", new String[]{"BemImovelAction.removerOcupacaoOrgaoResponsavel"}, e, ApplicationException.ICON_ERRO);
		}
	}

	private void carregarListaOcupacaoOrgaoResponsavel(HttpServletRequest request, String codBemImovel) throws ApplicationException {
		Pagina pagina = new Pagina(null, null, null);
		if (codBemImovel == null || codBemImovel.isEmpty() || codBemImovel.trim().length() < 1) {
			request.setAttribute("listOcupOrgaoResponsavel", null);
		} else {
			request.setAttribute("listOcupOrgaoResponsavel", CadastroFacade.listarOcupacaoPorBemImovelTerreno(pagina, Integer.valueOf(codBemImovel)));
		}
	}

	public ActionForward calcularOcupacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		BemImovelForm localForm = (BemImovelForm) form;

		try {
			verificarGrupoUsuarioLogado(localForm, request);
			setActionForward(mapping.findForward("pgEditOcupacaoOrgaoResponsavel"));
		
			if (! StringUtil.stringNotNull(localForm.getOr_ocupacaoMetroQuadrado()) && ! StringUtil.stringNotNull(localForm.getOr_ocupacaoPercentual())) {			
				this.inicializar(request, form);			
				throw new ApplicationException("AVISO.7");
			} else if (StringUtil.stringNotNull(localForm.getOr_ocupacaoMetroQuadrado())) {
				
				Double ocupacaoMetroQuadrado = new Double(0);				
				if (localForm.getOr_ocupacaoMetroQuadrado() != null && !localForm.getOr_ocupacaoMetroQuadrado().isEmpty()){
					ocupacaoMetroQuadrado =  (Valores.converterStringParaBigDecimal(localForm.getOr_ocupacaoMetroQuadrado())).doubleValue();	
				}
				
				Double areaTerreno = new Double(0);				
				if (localForm.getAreaTerreno() != null && !localForm.getAreaTerreno().isEmpty()){
					areaTerreno=  Double.parseDouble(Util.converteDecimal(localForm.getAreaTerreno()).toString());
				} else {
					this.inicializar(request, form);			
					throw new ApplicationException("AVISO.46");
				}
				
				if(ocupacaoMetroQuadrado.compareTo(areaTerreno)>0) {
					this.inicializar(request, form);			
					throw new ApplicationException("AVISO.9");
				} else {
					Double ocupacaoPercentual =  new Double(0);	
					if ((ocupacaoMetroQuadrado != null) && (areaTerreno != null) || (ocupacaoMetroQuadrado != new Double(0) ) || (areaTerreno != new Double(0))){
						ocupacaoPercentual = ocupacaoMetroQuadrado/areaTerreno;						
					}					
					ocupacaoPercentual = Valores.arredondar((ocupacaoPercentual * 100), 2);
					localForm.setOr_ocupacaoPercentual(Valores.formatarParaDecimal(ocupacaoPercentual, 2));
				}

			} else if (StringUtil.stringNotNull(localForm.getOr_ocupacaoPercentual())) {
				Double ocupacaoPercentual =  new Double(0);	
				if  (!localForm.getOr_ocupacaoPercentual().isEmpty()){
					ocupacaoPercentual =  (Valores.converterStringParaBigDecimal(localForm.getOr_ocupacaoPercentual())).doubleValue();	
				}
				
				if(ocupacaoPercentual.doubleValue()>100) {
					this.inicializar(request, form);			
					throw new ApplicationException("AVISO.8");
				} else {
					Double areaTerreno=  new Double(0);				
					if (localForm.getAreaTerreno() != null && !localForm.getAreaTerreno().isEmpty()){
						areaTerreno=  Double.parseDouble(Util.converteDecimal(localForm.getAreaTerreno()).toString());
					} else {
						this.inicializar(request, form);			
						throw new ApplicationException("AVISO.46");
					}
					Double ocupacaoMetroQuadrado = new Double(0);
					if (ocupacaoPercentual!=null){
						ocupacaoMetroQuadrado = ocupacaoPercentual/100;
						ocupacaoMetroQuadrado = Valores.arredondar((ocupacaoMetroQuadrado * areaTerreno), 2);
					}
					
					localForm.setOr_ocupacaoMetroQuadrado(Valores.formatarParaDecimal(ocupacaoMetroQuadrado, 2));
				}
				
			}
			carregarListaOcupacaoOrgaoResponsavel(request, localForm.getCodBemImovel());
			this.inicializar(request, form);

		} catch (ApplicationException appEx) {
			log4j.error("ERRO",appEx.getCausaRaiz());
			this.inicializar(request, form);
			throw appEx;

		} catch (Exception e) {
			log4j.error("ERRO",e);
			this.inicializar(request, form);
			throw new ApplicationException("ERRO.200", new String[]{"edição","ocupação"}, e);
		}

		return getActionForward();
    }

	/**
	 * Remover a(s) edificação (ões) do bem imóvel
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response 
	 */
	public ActionForward removerEdificacoesDoBemImovel (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método removerEdificacoesDoBemImovel processando...");
		BemImovelForm localForm = (BemImovelForm) form;
		try {
			setActionForward(mapping.findForward("pgEditBemImovel"));
			verificarTodasOperacoes( request );
			verificarGrupoUsuarioLogado(localForm, request);

			//atualiza bem Imovel
			BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			this.preencherBemImovel(bemImovel, localForm, request);
			bemImovel.setTsAtualizacao(new Date());
			CadastroFacade.alterarBemImovel(bemImovel);
			CadastroFacade.atualizarAreaBemImovel(Integer.valueOf(localForm.getCodBemImovel()));

			CadastroFacade.excluirEdificacoesDoBemImovel(Integer.valueOf(localForm.getCodBemImovel()));

			addMessage("SUCESSO.55",new String[]{localForm.getNrBemImovel()}, request);

			this.inicializar(request, form);
			carregarListaOcupacaoOrgaoResponsavel(request, localForm.getCodBemImovel());
			this.preencherFormularioBemImovel(localForm,Integer.valueOf(localForm.getCodBemImovel()));

			return getActionForward();
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			this.inicializar(request, form);			
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			this.inicializar(request, form);			
			throw new ApplicationException("mensagem.erro.9001", new String[]{"BemImovelAction.removerEdificacoesDoBemImovel"}, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Inativa o(s) órgão ocupante (ões) do bem imóvel
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response 
	 */
	public ActionForward inativarOrgaoOcupanteDoBemImovel (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método inativarOrgaoOcupanteDoBemImovel processando...");
		BemImovelForm localForm = (BemImovelForm) form;
		try {
			setActionForward(mapping.findForward("pgEditBemImovel"));
			verificarTodasOperacoes( request );
			verificarGrupoUsuarioLogado(localForm, request);
			
			//atualiza bem Imovel
			BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			this.preencherBemImovel(bemImovel, localForm, request);
			bemImovel.setTsAtualizacao(new Date());
			CadastroFacade.alterarBemImovel(bemImovel);
			CadastroFacade.atualizarAreaBemImovel(Integer.valueOf(localForm.getCodBemImovel()));

			CadastroFacade.inativarOrgaoOcupanteDoBemImovel(Integer.valueOf(localForm.getCodBemImovel()));

			addMessage("SUCESSO.56",new String[]{localForm.getNrBemImovel()}, request);

			this.inicializar(request, form);
			carregarListaOcupacaoOrgaoResponsavel(request, localForm.getCodBemImovel());
			this.preencherFormularioBemImovel(localForm,Integer.valueOf(localForm.getCodBemImovel()));

			return getActionForward();
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			this.inicializar(request, form);			
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			this.inicializar(request, form);			
			throw new ApplicationException("mensagem.erro.9001", new String[]{"BemImovelAction.inativarOrgaoOcupanteDoBemImovel"}, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Inativa o(s) órgão ocupante (ões) do bem imóvel
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response 
	 */
	public ActionForward reativarOrgaoOcupanteDoBemImovel (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método reativarOrgaoOcupanteDoBemImovel processando...");
		BemImovelForm localForm = (BemImovelForm) form;
		try {
			setActionForward(mapping.findForward("pgEditBemImovel"));
			
			verificarTodasOperacoes( request );
			verificarGrupoUsuarioLogado(localForm, request);
			
			//atualiza bem Imovel
			BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
			this.preencherBemImovel(bemImovel, localForm, request);
			bemImovel.setTsAtualizacao(new Date());
			CadastroFacade.alterarBemImovel(bemImovel);
			CadastroFacade.atualizarAreaBemImovel(Integer.valueOf(localForm.getCodBemImovel()));

			CadastroFacade.reativarOrgaoOcupanteDoBemImovel(Integer.valueOf(localForm.getCodBemImovel()));

			addMessage("SUCESSO.57",new String[]{localForm.getNrBemImovel()}, request);

			this.inicializar(request, form);
			carregarListaOcupacaoOrgaoResponsavel(request, localForm.getCodBemImovel());
			this.preencherFormularioBemImovel(localForm,Integer.valueOf(localForm.getCodBemImovel()));

			return getActionForward();
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			this.inicializar(request, form);			
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			this.inicializar(request, form);			
			throw new ApplicationException("mensagem.erro.9001", new String[]{"BemImovelAction.reativarOrgaoOcupanteDoBemImovel"}, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Carrega os dados do Bem Imóvel informado
	 * @param form
	 */
	public ActionForward carregarDadosBemImovelSimplificado (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarDadosBemImovelSimplificado processando...");
		try {
			request.getSession().setAttribute("bemImovelSimplificado", null);
			request.getSession().setAttribute("execBuscaBemImovel", 0);
			request.getSession().setAttribute("cedidoPara", null);
			request.getSession().setAttribute("msgValidacao", null);
			setActionForward(mapping.findForward("pgViewDadosBemImovel"));

			Integer nrBemImovelSimpl = ((request.getParameter("nrBemImovelSimpl") == null || request.getParameter("nrBemImovelSimpl").equals(""))? 0 : Integer.valueOf(request.getParameter("nrBemImovelSimpl")));
			Integer codInstituicao = ((request.getParameter("codInstituicao") == null || request.getParameter("codInstituicao").equals(""))? 0 : Integer.valueOf(request.getParameter("codInstituicao")));
			
			String ucOrigem = ((request.getParameter("uc") == null || request.getParameter("uc").equals("")) ? null : String.valueOf(request.getParameter("uc")));
			Integer codCessaoUso = ((request.getParameter("codCessaoUso") == null || request.getParameter("codCessaoUso").equals("")) ? 0 : Integer.valueOf(request.getParameter("codCessaoUso")));
			Integer codTransferencia = ((request.getParameter("codTransferencia") == null || request.getParameter("codTransferencia").equals("")) ? 0 : Integer.valueOf(request.getParameter("codTransferencia")));
			Integer codDoacao = ((request.getParameter("codDoacao") == null || request.getParameter("codDoacao").equals("")) ? 0 : Integer.valueOf(request.getParameter("codDoacao")));

			request.getSession().setAttribute("execBuscaBemImovel", nrBemImovelSimpl);

			if (nrBemImovelSimpl != null && nrBemImovelSimpl > 0){
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
				BemImovel bemImovel = CadastroFacade.obterBemImovelInstituicaoExibir(nrBemImovelSimpl, codInstituicao, usuario);
				request.getSession().setAttribute("bemImovelSimplificado", bemImovel);
				Long codUsuarioSentinela = SentinelaComunicacao.getInstance(request).getCodUsuario();
				
				//verifica se o usuário logado possui acesso ao bem Imóvel
				if (bemImovel != null && !CadastroFacade.verificarOperadorOrgaoBemImovel(bemImovel.getCodBemImovel(), codUsuarioSentinela, request)){
					StringBuffer strb = new StringBuffer();
					strb.append("O Bem imóvel informado possui não está acessível para o usuário logado.");
					strb.append(" \n ");
					if (strb != null && strb.toString().trim().length() > 0) {
						request.getSession().setAttribute("bemImovelSimplificado", null);
						request.getSession().setAttribute("msgValidacao", strb.toString());
					}
				}else{
					if (ucOrigem != null) {
						CessaoDeUso cessaoDeUso = new CessaoDeUso();
						cessaoDeUso.setBemImovel(bemImovel);
						if (codCessaoUso != null && codCessaoUso > 0) {
							cessaoDeUso.setCodCessaoDeUso(codCessaoUso);
							String result = OperacaoFacade.verificarCessaoTotalBemImovel(cessaoDeUso);
							if (result != null && result.length() > 0) {
								request.getSession().setAttribute("bemImovelSimplificado", null);
								request.getSession().setAttribute("cedidoPara", result);
							}
						}
		
						StringBuffer strb = new StringBuffer();
						if (ucOrigem.equalsIgnoreCase("cessao")) {
							cessaoDeUso.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.RASCUNHO.getIndex()));
							// Verifica se já existe com mesmo Bem Imóvel com o Status Termo Rascunho
							if (OperacaoFacade.verificarCessaoDeUsoByBemImovelStatusTermo(cessaoDeUso).size() > 0) {
								strb.append("O Bem imóvel informado possui Cessão de Uso com status ");
								strb.append(Dominios.statusTermo.RASCUNHO.getLabel());
								strb.append(", favor entrar na operação de alteração.");
								strb.append(" \n ");
							}
							cessaoDeUso.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.EM_RENOVACAO.getIndex()));
							// Verifica se já existe com mesmo Bem Imóvel com o Status Termo EM_RENOVACAO
							if (OperacaoFacade.verificarCessaoDeUsoByBemImovelStatusTermo(cessaoDeUso).size() > 0) {
								strb.append("O Bem imóvel informado possui Cessão de Uso com status ");
								strb.append(Dominios.statusTermo.EM_RENOVACAO.getLabel());
								strb.append(", favor entrar na operação de alteração.");
								strb.append(" \n ");
							}
							cessaoDeUso.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.VIGENTE.getIndex()));
							// Verifica se já existe com mesmo Bem Imóvel com o Status Termo EM_RENOVACAO
							if (OperacaoFacade.verificarCessaoDeUsoByBemImovelStatusTermo(cessaoDeUso).size() > 0) {
								strb.append("O Bem imóvel informado possui Cessão de Uso com status ");
								strb.append(Dominios.statusTermo.VIGENTE.getLabel());
								strb.append(".");
								strb.append(" \n ");
							}
						}
						Doacao doacao = new Doacao();
						doacao.setCodDoacao(Integer.valueOf(codDoacao));
						doacao.setBemImovel(bemImovel);
						doacao.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.VIGENTE.getIndex()));
						// Verifica se já existe Doacao com mesmo Bem Imóvel com o Status Termo VIGENTE
						if (OperacaoFacade.verificarDoacaoByBemImovelStatusTermo(doacao).size() > 0) {
							strb.append("O Bem imóvel informado possui Doação com status ");
							strb.append(Dominios.statusTermo.VIGENTE.getLabel());
							strb.append(", favor revogar a Doação para gerar uma nova Cessão de Uso.");
							strb.append(" \n ");
						}

						Transferencia transferencia = new Transferencia();
						transferencia.setCodTransferencia(Integer.valueOf(codTransferencia));
						transferencia.setBemImovel(bemImovel);
						transferencia.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.VIGENTE.getIndex()));
						// Verifica se já existe Transferencia com mesmo Bem Imóvel com o Status Termo VIGENTE
						if (OperacaoFacade.verificarTransferenciaByBemImovelStatusTermo(transferencia).size() > 0) {
							strb.append("O Bem imóvel informado possui Transferência com status ");
							strb.append(Dominios.statusTermo.VIGENTE.getLabel());
							strb.append(", favor revogar a Transferência para gerar uma nova Cessão de Uso.");
							strb.append(" \n ");
						}

						if (strb != null && strb.toString().trim().length() > 0) {
							request.getSession().setAttribute("bemImovelSimplificado", null);
							request.getSession().setAttribute("msgValidacao", strb.toString());
						}
					}
				}
			}
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{BemImovelAction.class.getSimpleName()+".carregarDadosBemImovelSimplificado"}, e, ApplicationException.ICON_ERRO);
		}
	}

	private void verificarGrupoUsuarioLogado(BemImovelForm localForm, HttpServletRequest request) throws ApplicationException {
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