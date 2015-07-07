package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.enumeration.SituacaoVistoria;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.facade.OperacaoFacade;
import gov.pr.celepar.abi.form.ManterVistoriaBemImovelForm;
import gov.pr.celepar.abi.generico.action.BaseAction;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Edificacao;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.ItemVistoria;
import gov.pr.celepar.abi.pojo.ItemVistoriaDominio;
import gov.pr.celepar.abi.pojo.ParametroVistoria;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.pojo.UsuarioOrgao;
import gov.pr.celepar.abi.pojo.Vistoria;
import gov.pr.celepar.abi.pojo.Vistoriador;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Enumerador;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Data;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ManterVistoriaBemImovelAction extends BaseAction {

	private boolean localizarBemImovel = false;

	/**
	 * Carregar a interface Inicial do Caso de Uso.<br>
	 * @author tatianapires
	 * @since 20/06/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward carregarInterfaceInicial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		ManterVistoriaBemImovelForm localForm = (ManterVistoriaBemImovelForm)form;
		
		localizarBemImovel = false;
		request.getSession().setAttribute("bemImovelSimplificado", null);
		
		
		if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo())){
			Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
			if (usuario == null){
				throw new ApplicationException("AVISO.97");
			}
			if (usuario.getListaUsuarioOrgao() == null || usuario.getListaUsuarioOrgao().size() < 1){
				setActionForward(mapping.findForward("pgErroManterVistoriaBI"));
				throw new ApplicationException("AVISO.96");
			}
			for (UsuarioOrgao orgao: usuario.getListaUsuarioOrgao()){
				usuario.getListaUsuarioOrgao().add(orgao);
			}
			localForm.setIndOperadorOrgao(1);
		}else{
			localForm.setIndOperadorOrgao(2);
		}
		
		return carregarInterfaceConsultar(mapping, form, request, response);
	}
	
	/**
	 * Carregar interface de consulta do caso de uso.<br>
	 * @author tatianapires
	 * @since  20/06/2011
	 * @param  mapping : ActionMapping
	 * @param  form : ActionForm
	 * @param  request : HttpServletRequest
	 * @param  response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */	
	public ActionForward carregarInterfaceConsultar(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {
		try {
			ManterVistoriaBemImovelForm localForm = (ManterVistoriaBemImovelForm)form;
			setActionForward(mapping.findForward(PG_CON));

			List<Enumerador> listaSituacaoVistoria = SituacaoVistoria.listar();
			request.setAttribute("listaSituacaoVistoria", listaSituacaoVistoria);
			request.getSession().setAttribute("bemImovelSimplificado", null);
			
			localForm.setIndGrupoSentinela(request.getSession().getAttribute("indGrupoSentinela").toString());
			localForm.setAdm(GrupoSentinela.ADM_GERAL.getDescricao());
			//lista instituicao
			if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
				request.setAttribute("listaInstituicao", Util.htmlEncodeCollection(CadastroFacade.listarInstituicao()));	
			}
			if (localForm.getTipoAcao() != null && localForm.getTipoAcao().equals(ACAO_VOLTAR)) {
				localForm.setTipoAcao(ACAO_VOLTAR);	
				carregarPaginaRequest(localForm, request);
			} else {
				if (!localizarBemImovel) {
					if ((localForm.getConNumeroBemImovel() != null || localForm.getConDataFinalVistoria() !=  null || localForm.getConDataInicialVistoria() !=  null || localForm.getConSituacao() !=  null)) {
						localForm.setTipoAcao(ACAO_VOLTAR);
						carregarPaginaRequest(localForm, request);
					} else {
						localForm.setTipoAcao(ACAO_CONSULTAR);					
					}
				}
			}	
			localizarBemImovel = false;

			return getActionForward();				

		} catch (ApplicationException ae) {
			throw ae; 
		}catch (Exception e) {
			setActionForward(concluir(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", e, ApplicationException.ICON_ERRO);
		}
	}
		
	/**
	 * Carregar a pagina de consulta no request.<br>
	 * @author tatianapires
	 * @since  20/06/2011
	 * @param  form : ActionForm
	 * @param  request : HttpServletRequest
	 * @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public void carregarPaginaRequest(ActionForm form, HttpServletRequest request) throws ApplicationException {
		try {			
			ManterVistoriaBemImovelForm localForm = (ManterVistoriaBemImovelForm)form;

			Integer numPagina = (request.getParameter("indice") == null ? 1 : Integer.valueOf(request.getParameter("indice")));
			Integer totalRegistros = request.getParameter("totalRegistros") == null ? null : Integer.valueOf(request.getParameter("totalRegistros"));
			
			Integer conNumeroBemImovel = StringUtils.isNotBlank(localForm.getConNumeroBemImovel()) ? Integer.valueOf(localForm.getConNumeroBemImovel()) : null;
			Date conDataInicialVistoria = StringUtils.isNotBlank(localForm.getConDataInicialVistoria()) ? Data.gerarData(localForm.getConDataInicialVistoria(),"dd/MM/yyyy") : null;
			Date conDataFinalVistoria = StringUtils.isNotBlank(localForm.getConDataFinalVistoria()) ? Data.gerarData(localForm.getConDataFinalVistoria(),"dd/MM/yyyy") : null;
			Integer conSituacaoVistoria = (localForm.getConSituacao() != null && (localForm.getConSituacao().equals("1") || localForm.getConSituacao().equals("2"))) ? Integer.valueOf(localForm.getConSituacao()) : null;
			
			if ((conDataInicialVistoria != null) && (conDataFinalVistoria != null)) {
				if (conDataInicialVistoria.after(conDataInicialVistoria)) {
					throw new ApplicationException("mensagem.erro.geral", new String[]{"Data Inicial maior que a Data Final"}, ApplicationException.ICON_AVISO); 
				} else if (conDataFinalVistoria.before(conDataInicialVistoria)) {
					throw new ApplicationException("mensagem.erro.geral", new String[]{"Data Final menor que a Data Inicial"}, ApplicationException.ICON_AVISO); 
				}
			}
			Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
			Integer codInstituicao = null;
			if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
					if (localForm.getConInstituicao().isEmpty()){
						codInstituicao = 0;
					}else{
						codInstituicao = Integer.valueOf(localForm.getConInstituicao());	
					}
			}else{
				//obtem a instituicao do usuario logado
				if (usuario == null){
					throw new ApplicationException("AVISO.97");
				}
				codInstituicao = usuario.getInstituicao().getCodInstituicao();
			}
			Pagina pagina = OperacaoFacade.listarVistoriaBemImovelPaginado(conNumeroBemImovel, conDataInicialVistoria, 
					conDataFinalVistoria, conSituacaoVistoria, Dominios.QTD_PAGINA, numPagina, totalRegistros, 
					localForm.getIndOperadorOrgao(), usuario, codInstituicao, request);

			if (pagina.getRegistros().isEmpty() && !codInstituicao.equals(0)){
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			
			List<Vistoria> listaVist = (List<Vistoria>) pagina.getRegistros();
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			Boolean admin = Util.validaPermissaoReativar(sentinelaInterface.getGrupos()); 
			for (Vistoria vistoria : listaVist) {
				vistoria.setPermissaoExclusaoVistoria(admin);
			}
			
			pagina.setRegistros(listaVist);
			
			request.setAttribute("pagina", pagina);

		}catch (ApplicationException ae) {
			throw ae;
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.geral", new String[]{ManterVistoriaBemImovelAction.class.getSimpleName()+".carregarPaginaRequest()"}, 
					e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Realiza a consulta de Vistorias.<br>
	 * @author tatianapires
	 * @since  20/06/2011
	 * @param  mapping : ActionMapping
	 * @param  form : ActionForm
	 * @param  request : HttpServletRequest
	 * @param  response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward consultar(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {
		try{

			setActionForward(mapping.findForward(PG_CON_AJAX));			

			carregarPaginaRequest(form, request);						

			return getActionForward();		

		}catch (ApplicationException ae) {
			throw ae;

		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.geral", new String[]{ManterVistoriaBemImovelAction.class.getSimpleName()+".consultar()"}, 
					e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Alterar dados do respectivo caso de uso.<br>
	 * @author tatianapires
	 * @since 04/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {
		try {
			ManterVistoriaBemImovelForm localForm = (ManterVistoriaBemImovelForm)form;
			
			alterarFluxo(mapping, localForm, request, response);
					
			this.addMessage("SUCESSO.1002", request);
			
			return carregarInterfaceConsultar(mapping, localForm, request, response);
		}catch (ApplicationException ae) {
			setActionForward(carregarInterfaceAlterar(mapping, form, request, response));
			throw ae;
		}catch (Exception e) {			
			setActionForward(carregarInterfaceAlterar(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{ManterVistoriaBemImovelAction.class.getSimpleName() 
					+ ".alterar()"}, e, ApplicationException.ICON_ERRO);
		}		
	}
	
	/**
	 * Metodo para  concluir a vistoria.<br>
	 * @author tatianapires
	 * @since 04/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward concluirVistoria(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {
		try {
			ManterVistoriaBemImovelForm localForm = (ManterVistoriaBemImovelForm)form;
			
			if (!validaForm(mapping, form, request)) {
				throw new ApplicationException("AVISO.201", ApplicationException.ICON_AVISO);
			}	
			
			Integer codVistoria = Integer.valueOf(localForm.getCodVistoria());
			Vistoria vistoria = OperacaoFacade.obterVistoriaCompleta(codVistoria);
			 
			//validar itens selecionados
			List<ItemVistoria> listaItemVistoria =Util.setToList(vistoria.getListaItemVistoria());
			List<ItemVistoria> listaItemVistoriaChecados = new ArrayList<ItemVistoria>();
			for (ItemVistoria itemVistoria : listaItemVistoria) {
				Boolean dominioPreenchido = Boolean.FALSE;
	
				// TEXTO
				if (itemVistoria.getIndTipoParametro() == 1) {
					String itemTela = request.getParameter(obterNomeCampoItemPagina(request, "t_itemVistoria_" + itemVistoria.getCodItemVistoria()));
					itemVistoria.setTextoDominio(itemTela);
					dominioPreenchido = Boolean.TRUE;
				}
				
				// COMBOBOX
				if (itemVistoria.getIndTipoParametro() == 2) {
					for (ItemVistoriaDominio itemVistoriaDominio : itemVistoria.getListaItemVistoriaDominio()) {
						String itemTela = request.getParameter(obterNomeCampoItemPagina(request, "co_itemVistoria_" + itemVistoria.getCodItemVistoria()));
						if (itemTela.equals(itemVistoriaDominio.getCodItemVistoriaDominio().toString())) {
							//se for selecionado
							itemVistoriaDominio.setIndSelecionado(Boolean.TRUE);
							dominioPreenchido = Boolean.TRUE;
						} else {	
							// senao
							itemVistoriaDominio.setIndSelecionado(Boolean.FALSE);
						}	
					}
				}
				
				// CHECKBOX
				if (itemVistoria.getIndTipoParametro() == 3) {
					for (ItemVistoriaDominio itemVistoriaDominio : itemVistoria.getListaItemVistoriaDominio()) {
						String itemTela = request.getParameter(obterNomeCampoItemPagina(request, "ch_itemVistoria_" + itemVistoriaDominio.getCodItemVistoriaDominio()));
						if ("on".equals(itemTela)) {
							itemVistoriaDominio.setIndSelecionado(Boolean.TRUE);
							dominioPreenchido = Boolean.TRUE;
						} else {
							itemVistoriaDominio.setIndSelecionado(Boolean.FALSE);
						}
					}
				}
				
				if (!dominioPreenchido) {
					throw new ApplicationException("AVISO.1002", new String[]{itemVistoria.getDescricao()}, ApplicationException.ICON_AVISO);
				}
				
				listaItemVistoriaChecados.add(itemVistoria);
			}
			
			Integer codEdificacao = StringUtils.isBlank(localForm.getEspecificacaoEdificacao()) ? null : Integer.valueOf(localForm.getEspecificacaoEdificacao());
			Integer codVistoriador = Integer.valueOf(localForm.getVistoriador());
			Date dataExecucao = Data.gerarData(localForm.getDataVistoria(),"dd/MM/yyyy");
			Integer idadeAparente = StringUtils.isBlank(localForm.getIdadeAparente()) ? null : Integer.valueOf(localForm.getIdadeAparente());
			String observacao = localForm.getObservacao();
			
			OperacaoFacade.salvarVistoria(codVistoria, codEdificacao, codVistoriador, dataExecucao, idadeAparente, observacao, listaItemVistoriaChecados, Boolean.TRUE);
					
			this.addMessage("SUCESSO.1003", request);
			
			return carregarInterfaceConsultar(mapping, localForm, request, response);
		}catch (ApplicationException ae) {
			setActionForward(carregarInterfaceAlterar(mapping, form, request, response));
			throw ae;
		}catch (Exception e) {			
			setActionForward(carregarInterfaceAlterar(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{ManterVistoriaBemImovelAction.class.getSimpleName() 
					+ ".concluirVistoria()"}, e, ApplicationException.ICON_ERRO);
		}		
	}
	
	/**
	 * Fluxo p/ Alterar dados do respectivo caso de uso.<br>
	 * @author tatianapires
	 * @since 04/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward alterarFluxo(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {
		try {
			ManterVistoriaBemImovelForm localForm = (ManterVistoriaBemImovelForm)form;
			
			if (!validaForm(mapping, form, request)) {
				throw new ApplicationException("AVISO.201", ApplicationException.ICON_AVISO);
			}	
			
			Integer codVistoria = Integer.valueOf(localForm.getCodVistoria());
			Vistoria vistoria = OperacaoFacade.obterVistoriaCompleta(codVistoria);
			 
			//validar itens selecionados
			List<ItemVistoria> listaItemVistoria =Util.setToList(vistoria.getListaItemVistoria());
			List<ItemVistoria> listaItemVistoriaChecados = new ArrayList<ItemVistoria>();
			for (ItemVistoria itemVistoria : listaItemVistoria) {
	
				// TEXTO
				if (itemVistoria.getIndTipoParametro() == 1) {
					String itemTela = request.getParameter(obterNomeCampoItemPagina(request, "t_itemVistoria_" + itemVistoria.getCodItemVistoria()));
					itemVistoria.setTextoDominio(itemTela);
				}
				
				// COMBOBOX
				if (itemVistoria.getIndTipoParametro() == 2) {
					for (ItemVistoriaDominio itemVistoriaDominio : itemVistoria.getListaItemVistoriaDominio()) {
						String itemTela = request.getParameter(obterNomeCampoItemPagina(request, "co_itemVistoria_" + itemVistoria.getCodItemVistoria()));
						if (itemTela.equals(itemVistoriaDominio.getCodItemVistoriaDominio().toString())) {
							//se for selecionado
							itemVistoriaDominio.setIndSelecionado(Boolean.TRUE);
						} else {	
							// senao
							itemVistoriaDominio.setIndSelecionado(Boolean.FALSE);
						}	
					}
				}
				
				// CHECKBOX
				if (itemVistoria.getIndTipoParametro() == 3) {
					for (ItemVistoriaDominio itemVistoriaDominio : itemVistoria.getListaItemVistoriaDominio()) {
						String itemTela = request.getParameter(obterNomeCampoItemPagina(request, "ch_itemVistoria_" + itemVistoriaDominio.getCodItemVistoriaDominio()));
						if ("on".equals(itemTela)) {
							itemVistoriaDominio.setIndSelecionado(Boolean.TRUE);
						} else {
							itemVistoriaDominio.setIndSelecionado(Boolean.FALSE);
						}
					}
				}
				
				listaItemVistoriaChecados.add(itemVistoria);
			}
			
			Integer codEdificacao = StringUtils.isBlank(localForm.getEspecificacaoEdificacao()) ? null : Integer.valueOf(localForm.getEspecificacaoEdificacao());
			Integer codVistoriador = Integer.valueOf(localForm.getVistoriador());
			Date dataExecucao = StringUtils.isBlank(localForm.getDataVistoria()) ? null : Data.gerarData(localForm.getDataVistoria(),"dd/MM/yyyy");
			Integer idadeAparente = StringUtils.isBlank(localForm.getIdadeAparente()) ? null : Integer.valueOf(localForm.getIdadeAparente());
			String observacao = localForm.getObservacao();
			
			OperacaoFacade.salvarVistoria(codVistoria, codEdificacao, codVistoriador, dataExecucao, idadeAparente, observacao, listaItemVistoriaChecados, Boolean.FALSE);
			
			return null;
		}catch (ApplicationException ae) {
			throw ae;
		}catch (Exception e) {			
			throw new ApplicationException("mensagem.erro.geral", new String[]{ManterVistoriaBemImovelAction.class.getSimpleName() 
					+ ".alterarFluxo()"}, e, ApplicationException.ICON_ERRO);
		}		
	}
	
	/**
	 * Adicionar itens de vistoria.<br>
	 * @author tatianapires
	 * @since 04/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward carregarInterfaceAdicionarItensVistoria(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {
		try {
			ManterVistoriaBemImovelForm localForm = (ManterVistoriaBemImovelForm)form;
			localForm.setTipoAcao("adicionar");
			Integer codVistoria = StringUtils.isNotBlank(localForm.getCodVistoria()) ? Integer.valueOf(localForm.getCodVistoria()) : null;
			
			if (codVistoria != null) {
				
				// Salva a vistoria
				alterarFluxo(mapping, localForm, request, response);
				Vistoria vistoria = OperacaoFacade.obterVistoriaCompleta(codVistoria);
				
				localForm.setCodVistoria(codVistoria.toString());
				
				List<Integer> listaCodParametroVistoria = new ArrayList<Integer>();
				for (ItemVistoria itemVistoria : vistoria.getListaItemVistoria()) {
					listaCodParametroVistoria.add(itemVistoria.getParametroVistoria().getCodParametroVistoria());
				}
				
				List<ParametroVistoria> listaParametroVistoria = OperacaoFacade.listarParametroVistoriaComDenominacaoBemImovelExceto(vistoria, listaCodParametroVistoria);
				
				if (!Util.validarLista(listaParametroVistoria)) {
					throw new ApplicationException("AVISO.1001", ApplicationException.ICON_AVISO);
				}
				
				Pagina pagina = new Pagina();
				pagina.setPaginaAtual(1);
				pagina.setRegistros(listaParametroVistoria);
				pagina.setTotalRegistros(listaParametroVistoria.size());
				pagina.setQuantidade(listaParametroVistoria.size());
				request.setAttribute("listaParametroVistoria", pagina);
			}
			
			return mapping.findForward("pgEditAdicionarItem");
		}catch (ApplicationException ae) {
			setActionForward(carregarInterfaceAlterar(mapping, form, request, response));
			throw ae;
		}catch (Exception e) {
			setActionForward(carregarInterfaceAlterar(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{ManterVistoriaBemImovelAction.class.getSimpleName()+".carregarInterfaceAdicionarItensVistoria()"}, 
					e, ApplicationException.ICON_ERRO);
		}		
	}
	
	/**
	 * Adicionar os itens de vistoria e retornar para a tela de edicao.<br>
	 * @author tatianapires
	 * @since 30/06/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward adicionarItensVistoria(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			ManterVistoriaBemImovelForm localForm = (ManterVistoriaBemImovelForm)form;
			
			Integer codVistoria = Integer.valueOf(localForm.getCodVistoria());
			Vistoria vistoria = OperacaoFacade.obterVistoriaCompleta(codVistoria);

			List<Integer> listaCodParametroVistoria = new ArrayList<Integer>();
			for (ItemVistoria itemVistoria : vistoria.getListaItemVistoria()) {
				listaCodParametroVistoria.add(itemVistoria.getParametroVistoria().getCodParametroVistoria());
			}
			
			//validar itens selecionados
			List<ParametroVistoria> listaParametroVistoria =OperacaoFacade.listarParametroVistoriaComDenominacaoBemImovelExceto(vistoria, listaCodParametroVistoria);
			List<ParametroVistoria> listaParametroVistoriaChecados = new ArrayList<ParametroVistoria>();
			for (ParametroVistoria parametroVistoria : listaParametroVistoria) {
				String parametroChecado = request.getParameter(obterNomeCampoItemPagina(request, "parametroVistoria_" + parametroVistoria.getCodParametroVistoria()));
				if ("on".equals(parametroChecado)) {
					parametroVistoria.setParametroChecado(Boolean.TRUE);
					listaParametroVistoriaChecados.add(parametroVistoria);
				}
			}
			
			if (!Util.validarLista(listaParametroVistoriaChecados)) {
				throw new ApplicationException("AVISO.1000", ApplicationException.ICON_AVISO);
			}
			
			OperacaoFacade.salvarParametroAdicionadoEmVistoria(vistoria, listaParametroVistoriaChecados);
			
			return carregarInterfaceAlterar(mapping, localForm, request, response);
		}catch (ApplicationException ae) {
			setActionForward(carregarInterfaceAdicionarItensVistoria(mapping, form, request, response));
			throw ae;
		}catch (Exception e) {
			setActionForward(carregarInterfaceAdicionarItensVistoria(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{ManterVistoriaBemImovelAction.class.getSimpleName()+".adicionarItensVistoria()"}, e, ApplicationException.ICON_ERRO);
		}		
	}
	
	/**
	 * Excluir os itens de vistoria e retornar para a tela de edicao.<br>
	 * @author tatianapires
	 * @since 30/06/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward excluirItemVistoria(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			ManterVistoriaBemImovelForm localForm = (ManterVistoriaBemImovelForm)form;
			
			Integer codVistoria = Integer.valueOf(localForm.getCodVistoria());
			Integer codItemVistoria = (request.getParameter("codItemVistoria") == null ? null : Integer.valueOf(request.getParameter("codItemVistoria"))); 
			
			if (codItemVistoria != null) {
				
				Vistoria vistoria = OperacaoFacade.obterVistoriaCompleta(codVistoria);
				List<ItemVistoria> listItemVistoria = Util.setToList(vistoria.getListaItemVistoria());
				for (ItemVistoria itemVistoria : listItemVistoria) {
					if (itemVistoria.getCodItemVistoria().equals(codItemVistoria)) {
						listItemVistoria.remove(itemVistoria);
						break;
					}
				}
				vistoria.setListaItemVistoria(Util.listToSet(listItemVistoria));
				
				OperacaoFacade.salvarVistoria(vistoria);

				Pagina pagina = new Pagina();
				pagina.setRegistros(vistoria.getListaItemVistoria());
				pagina.setPaginaAtual(1);
				pagina.setTotalRegistros(vistoria.getListaItemVistoria().size());
				pagina.setQuantidade(vistoria.getListaItemVistoria().size());
				request.setAttribute("listaItemVistoria", pagina);
			}
			
			return mapping.findForward("pgEditItensAjax");
		}catch (ApplicationException ae) {
			setActionForward(carregarInterfaceAlterar(mapping, form, request, response));
			throw ae;
		}catch (Exception e) {
			setActionForward(carregarInterfaceAlterar(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{ManterVistoriaBemImovelAction.class.getSimpleName()+".excluirItemVistoria()"}, e, ApplicationException.ICON_ERRO);
		}		
	}

	/**
	 * Carregar a interface Exibir do caso de uso.<br>
	 * @author tatianapires
	 * @since 04/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward carregarInterfaceExibirExcluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			ManterVistoriaBemImovelForm localForm = (ManterVistoriaBemImovelForm) form;

			request.getSession().setAttribute("bemImovelSimplificado", null);
			if(!(ACAO_EXIBIR).equals(localForm.getTipoAcao())){
				localForm.setTipoAcao(ACAO_EXCLUIR);
			} else{
				localForm.setTipoAcao(ACAO_EXIBIR);
			}

			Integer codVistoria = Integer.valueOf(localForm.getCodVistoria());

			if (codVistoria != null) {				
				Vistoria vistoria = OperacaoFacade.obterVistoriaCompleta(codVistoria);

				localForm.setNumeroBemImovel(vistoria.getBemImovel() == null ? "" : vistoria.getBemImovel().getNrBemImovel().toString());
				localForm.setSituacao(vistoria.getStatusVistoria() == null ? "" : SituacaoVistoria.getDescricao(vistoria.getStatusVistoria().getCodStatusVistoria()));
				localForm.setEspecificacaoEdificacao(vistoria.getEdificacao() == null ? "" : vistoria.getEdificacao().getEspecificacao());
				localForm.setVistoriador(vistoria.getVistoriador() == null ? "" : vistoria.getVistoriador().getNome());
				localForm.setDataVistoria(vistoria.getDataVistoria() == null ? "" : Data.formataData(vistoria.getDataVistoria()));
				localForm.setIdadeAparente(vistoria.getIdadeAparente() == null ? "" : vistoria.getIdadeAparente().toString());
				localForm.setObservacao(vistoria.getObservacao() == null ? "" : vistoria.getObservacao()); 
				localForm.setCodInstituicao(vistoria.getBemImovel().getInstituicao().getCodInstituicao().toString());
				localForm.setInstituicao(vistoria.getBemImovel().getInstituicao().getSiglaDescricao());
				Pagina pagina = new Pagina();
				pagina.setRegistros(vistoria.getListaItemVistoria());
				pagina.setPaginaAtual(1);
				pagina.setTotalRegistros(vistoria.getListaItemVistoria().size());
				pagina.setQuantidade(vistoria.getListaItemVistoria().size());
				request.setAttribute("listaItemVistoria", pagina);
				
				request.setAttribute("codVistoria", codVistoria);
			}

			return mapping.findForward(PG_VIEW);
		}catch (ApplicationException ae) {
			setActionForward(carregarInterfaceConsultar(mapping, form, request, response));
			throw ae;
		}catch (Exception e) {
			setActionForward(carregarInterfaceConsultar(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{ManterVistoriaBemImovelAction.class.getSimpleName()+".carregarInterfaceExibirExcluir()"}, 
					e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Carregar a interface Incluir do caso de uso.<br>
	 * @author tatianapires
	 * @since 29/06/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward carregarInterfaceIncluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {

			ManterVistoriaBemImovelForm localForm = (ManterVistoriaBemImovelForm)form;
			String tipoAcao = request.getAttribute("tipoAcao") == null ? null : request.getAttribute("tipoAcao").toString();
			localForm.setIndGrupoSentinela(request.getSession().getAttribute("indGrupoSentinela").toString());
			localForm.setAdm(GrupoSentinela.ADM_GERAL.getDescricao());
			
			request.getSession().setAttribute("bemImovelSimplificado", null);
			Integer codBemImovel = null;
			//lista instituicao
			if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
				request.setAttribute("listaInstituicao", Util.htmlEncodeCollection(CadastroFacade.listarInstituicao()));	
			}else{
				List<Instituicao> listaInstituicao = new ArrayList<Instituicao>();
				request.setAttribute("listaInstituicao", listaInstituicao);
			}
			if (localForm.getTipoAcao() != null || tipoAcao != null) {
				if ("voltar".equals(tipoAcao)) {
					localForm.setTipoAcao(ACAO_VOLTAR);
					codBemImovel = request.getParameter("codBemImovel") == null ? null : Integer.valueOf(request.getParameter("codBemImovel"));
					localForm.setNumeroBemImovel(codBemImovel.toString());
					
//					List<Edificacao> listaEdificacao = OperacaoFacade.listarEdificacaoComVinculoBemImovel(codBemImovel, codInstituicao);
//					request.setAttribute("listaEdificacao", listaEdificacao);
					
					
					Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
					BemImovel bemImovel = CadastroFacade.obterBemImovelInstituicaoExibir(codBemImovel, Integer.valueOf(localForm.getCodInstituicao()), usuario);
					List<ParametroVistoria> listaParametroVistoria = OperacaoFacade.listarParametroVistoriaComDenominacaoBemImovel(bemImovel);
					Pagina pagina = new Pagina();
					pagina.setPaginaAtual(1);
					if (Util.validarLista(listaParametroVistoria)) {
						pagina.setRegistros(listaParametroVistoria);
						pagina.setTotalRegistros(listaParametroVistoria.size());
						pagina.setQuantidade(listaParametroVistoria.size());
					}
					request.setAttribute("listaParametroVistoria", pagina);
					
					request.setAttribute("bemImovelAdministracao", bemImovel.getAdministracao() == null ? "" : bemImovel.getDescricaoAdministracao());
					request.setAttribute("bemImovelOrgaoProprietario", bemImovel.getOrgao() == null ? "" : bemImovel.getDescricaoOrgao());
					request.setAttribute("bemImovelClassificacaoImovel", bemImovel.getClassificacaoBemImovel() == null ? "" : bemImovel.getClassificacaoBemImovel().getDescricao());
					request.setAttribute("bemImovelSituacaoLocalizacao", bemImovel.getDescricaoSituacaoLocal() == null ? "" : bemImovel.getDescricaoSituacaoLocal());
					request.setAttribute("bemImovelSituacaoLegalCartorial", bemImovel.getSituacaoLegalCartorial() == null ? "" : bemImovel.getSituacaoLegalCartorial().getDescricao());
					request.setAttribute("bemImovelEstadoMunicipio", bemImovel.getMunicipio() == null ? "" : bemImovel.getMunicipio().concat(" / ".concat(bemImovel.getUf())));
					request.setAttribute("bemImovelLogradouroNum", bemImovel.getLogradouro() == null ? "" : bemImovel.getLogradouro());
					request.setAttribute("bemImovelBairroDistrito", bemImovel.getBairroDistrito() == null ? "" : bemImovel.getBairroDistrito());
					request.setAttribute("bemImovelAreaTerreno", bemImovel.getAreaTerreno() == null ? "" : Util.formataNumeroMonetario(bemImovel.getAreaTerreno()));
					request.setAttribute("bemImovelConstruida", bemImovel.getAreaConstruida() == null ? "" : Util.formataNumeroMonetario(bemImovel.getAreaConstruida()));
					request.setAttribute("bemImovelDisponivel", bemImovel.getAreaDispoNivel() == null ? "" : Util.formataNumeroMonetario(bemImovel.getAreaDispoNivel()));
					request.setAttribute("bemImovelSituacaoImovel", bemImovel.getSituacaoImovel() == null ? "" : bemImovel.getSituacaoImovel().getDescricao());
					request.setAttribute("bemImovelSomenteTerreno", bemImovel.getSomenteTerreno() == null ? "" : ("N".equals(bemImovel.getSomenteTerreno()) ? "Não" : "Sim"));
				} else {
					localForm.setTipoAcao(ACAO_INCLUIR);	
					
					List<Edificacao> listaEdificacao = new ArrayList<Edificacao>();
					request.setAttribute("listaEdificacao", listaEdificacao);

					Pagina pagina = new Pagina();
					pagina.setRegistros(new ArrayList<ParametroVistoria>());
					pagina.setPaginaAtual(1);
					pagina.setTotalRegistros(0);
					pagina.setQuantidade(0);
					request.setAttribute("listaParametroVistoria", pagina);
					
					request.setAttribute("bemImovelAdministracao", "");
					request.setAttribute("bemImovelOrgaoProprietario", "");
					request.setAttribute("bemImovelClassificacaoImovel", "");
					request.setAttribute("bemImovelSituacaoLocalizacao", "");
					request.setAttribute("bemImovelSituacaoLegalCartorial", "");
					request.setAttribute("bemImovelEstadoMunicipio", "");
					request.setAttribute("bemImovelLogradouroNum", "");
					request.setAttribute("bemImovelBairroDistrito", "");
					request.setAttribute("bemImovelAreaTerreno", "");
					request.setAttribute("bemImovelConstruida", "");
					request.setAttribute("bemImovelDisponivel", "");
					request.setAttribute("bemImovelSituacaoImovel", "");
					request.setAttribute("bemImovelSomenteTerreno", "");
				}
				if (localForm.getCodInstituicao() != null && !localForm.getCodInstituicao().isEmpty()){
					List<Vistoriador> listaVistoriador = OperacaoFacade.listarVistoriador(Integer.valueOf(localForm.getCodInstituicao()));
					request.setAttribute("listaVistoriador", listaVistoriador);	
				}else{
					List<Vistoriador> listaVistoriador = OperacaoFacade.listarVistoriador(null);
					request.setAttribute("listaVistoriador", listaVistoriador);
				}
				
				
			} 
			return mapping.findForward(PG_EDIT);

		}catch (ApplicationException ae) {
			setActionForward(carregarInterfaceConsultar(mapping, form, request, response));
			throw ae;
		}catch (Exception e) {
			setActionForward(carregarInterfaceConsultar(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{ManterVistoriaBemImovelAction.class.getSimpleName()+".carregarInterfaceIncluir()"}, 
					e, ApplicationException.ICON_ERRO);
		}			
	}
	
	/**
	 * Carregar a interface Alterar do caso de uso.<br>
	 * @author tatianapires
	 * @since 04/06/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward carregarInterfaceAlterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			ManterVistoriaBemImovelForm localForm = (ManterVistoriaBemImovelForm)form;
			
			request.getSession().setAttribute("bemImovelSimplificado", null);
			Integer codVistoria = request.getParameter("codVistoria") == null ? null : Integer.valueOf(request.getParameter("codVistoria"));
			localForm.setTipoAcao(ACAO_ALTERAR);	
			
			Vistoria vistoria = OperacaoFacade.obterVistoriaCompleta(codVistoria);
			localForm.setCodVistoria(vistoria.getCodVistoria().toString());
			localForm.setCodInstituicao(vistoria.getBemImovel().getInstituicao().getCodInstituicao().toString());
			localForm.setInstituicao(vistoria.getBemImovel().getInstituicao().getSiglaDescricao());
			localForm.setNumeroBemImovel(vistoria.getBemImovel().getNrBemImovel().toString());
			localForm.setEspecificacaoEdificacao(vistoria.getEdificacao() == null ? "" : vistoria.getEdificacao().getCodEdificacao().toString());
			localForm.setVistoriador(vistoria.getVistoriador().getCodVistoriador().toString());
			localForm.setDataVistoria(vistoria.getDataVistoria() == null ? "" : Data.formataData(vistoria.getDataVistoria()));
			localForm.setIdadeAparente(vistoria.getIdadeAparente() == null ? "" : vistoria.getIdadeAparente().toString());
			localForm.setObservacao(vistoria.getObservacao() == null ? "" : vistoria.getObservacao());
			
			
			List<Edificacao> listaEdificacao = OperacaoFacade.listarEdificacaoComVinculoBemImovel(vistoria.getBemImovel().getNrBemImovel(), vistoria.getBemImovel().getInstituicao().getCodInstituicao());
			request.setAttribute("listaEdificacao", listaEdificacao);
			
			List<Vistoriador> listaVistoriador = OperacaoFacade.listarVistoriador(vistoria.getBemImovel().getInstituicao().getCodInstituicao());
			request.setAttribute("listaVistoriador", listaVistoriador);
			
			Pagina pagina = new Pagina();
			pagina.setRegistros(vistoria.getListaItemVistoria());
			pagina.setPaginaAtual(1);
			pagina.setTotalRegistros(vistoria.getListaItemVistoria().size());
			pagina.setQuantidade(vistoria.getListaItemVistoria().size());
			request.setAttribute("listaItemVistoria", pagina);

			Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
			BemImovel bemImovel = CadastroFacade.obterBemImovelInstituicaoExibir(vistoria.getBemImovel().getNrBemImovel(), vistoria.getBemImovel().getInstituicao().getCodInstituicao(), usuario);
			
			request.setAttribute("bemImovelAdministracao", bemImovel.getAdministracao() == null ? "" : bemImovel.getDescricaoAdministracao());
			request.setAttribute("bemImovelOrgaoProprietario", bemImovel.getOrgao() == null ? "" : bemImovel.getDescricaoOrgao());
			request.setAttribute("bemImovelClassificacaoImovel", bemImovel.getClassificacaoBemImovel() == null ? "" : bemImovel.getClassificacaoBemImovel().getDescricao());
			request.setAttribute("bemImovelSituacaoLocalizacao", bemImovel.getDescricaoSituacaoLocal() == null ? "" : bemImovel.getDescricaoSituacaoLocal());
			request.setAttribute("bemImovelSituacaoLegalCartorial", bemImovel.getSituacaoLegalCartorial() == null ? "" : bemImovel.getSituacaoLegalCartorial().getDescricao());
			request.setAttribute("bemImovelEstadoMunicipio", bemImovel.getMunicipio() == null ? "" : bemImovel.getMunicipio().concat(" / ".concat(bemImovel.getUf())));
			request.setAttribute("bemImovelLogradouroNum", bemImovel.getLogradouro() == null ? "" : bemImovel.getLogradouro());
			request.setAttribute("bemImovelBairroDistrito", bemImovel.getBairroDistrito() == null ? "" : bemImovel.getBairroDistrito());
			request.setAttribute("bemImovelAreaTerreno", bemImovel.getAreaTerreno() == null ? "" : Util.formataNumeroMonetario(bemImovel.getAreaTerreno()));
			request.setAttribute("bemImovelConstruida", bemImovel.getAreaConstruida() == null ? "" : Util.formataNumeroMonetario(bemImovel.getAreaConstruida()));
			request.setAttribute("bemImovelDisponivel", bemImovel.getAreaDispoNivel() == null ? "" : Util.formataNumeroMonetario(bemImovel.getAreaDispoNivel()));
			request.setAttribute("bemImovelSituacaoImovel", bemImovel.getSituacaoImovel() == null ? "" : bemImovel.getSituacaoImovel().getDescricao());
			request.setAttribute("bemImovelSomenteTerreno", bemImovel.getSomenteTerreno() == null ? "" : ("N".equals(bemImovel.getSomenteTerreno()) ? "Não" : "Sim"));
			
			return mapping.findForward("pgEdit2");

		}catch (ApplicationException ae) {
			setActionForward(carregarInterfaceConsultar(mapping, form, request, response));
			throw ae;
		}catch (Exception e) {
			setActionForward(carregarInterfaceConsultar(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{ManterVistoriaBemImovelAction.class.getSimpleName()+".carregarInterfaceAlterar()"}, 
					e, ApplicationException.ICON_ERRO);
		}		
	}
	
	/**
	 * Retorna lista com Itens de Vistoria via Ajax.<br>
	 * @author tatianapires
	 * @since 30/06/2011
	 * @param  mapping : ActionMapping
	 * @param  form : ActionForm
	 * @param  request : HttpServletRequest
	 * @param  response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward ajaxListarItensVistoria(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			ManterVistoriaBemImovelForm localForm = (ManterVistoriaBemImovelForm)form;

			Integer codBemImovel = (request.getParameter("codBemImovel") == null ? null : Integer.valueOf(request.getParameter("codBemImovel")));
			
			Pagina pagina = new Pagina();
			pagina.setPaginaAtual(1);
			pagina.setRegistros(new ArrayList<ParametroVistoria>());
			pagina.setTotalRegistros(0);
			pagina.setQuantidade(0);
			if (codBemImovel != null) {
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
				if (localForm.getCodInstituicao() == null){ //usuario nao é adm geral
					if (usuario.getInstituicao() != null){
						localForm.setCodInstituicao(usuario.getInstituicao().getCodInstituicao().toString());
					}
				}
				BemImovel bemImovel = CadastroFacade.obterBemImovelInstituicaoExibir(codBemImovel, Integer.valueOf(localForm.getCodInstituicao()), usuario);
				List<ParametroVistoria> listaParametroVistoria =OperacaoFacade.listarParametroVistoriaComDenominacaoBemImovel(bemImovel);
				if (Util.validarLista(listaParametroVistoria)) {
					pagina.setRegistros(listaParametroVistoria);
					pagina.setTotalRegistros(listaParametroVistoria.size());
					pagina.setQuantidade(listaParametroVistoria.size());
				}
			}
			request.setAttribute("listaParametroVistoria", pagina);
			return mapping.findForward("pgEditAjax");
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.geral", new String[]{ManterVistoriaBemImovelAction.class.getSimpleName()+".ajaxListarItensVistoria()"}, 
					e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Retorna Bem Imovel via Ajax.<br>
	 * @author tatianapires
	 * @since 30/06/2011
	 * @param  mapping : ActionMapping
	 * @param  form : ActionForm
	 * @param  request : HttpServletRequest
	 * @param  response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward ajaxCarregarInformacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			
			Integer codBemImovel = (request.getParameter("codBemImovel") == null ? null : Integer.valueOf(request.getParameter("codBemImovel")));
			
			BemImovel bemImovel = CadastroFacade.obterBemImovelExibir(codBemImovel);
			
			request.setAttribute("bemImovelAdministracao", bemImovel.getAdministracao() == null ? "" : bemImovel.getDescricaoAdministracao());
			request.setAttribute("bemImovelOrgaoProprietario", bemImovel.getOrgao() == null ? "" : bemImovel.getDescricaoOrgao());
			request.setAttribute("bemImovelClassificacaoImovel", bemImovel.getClassificacaoBemImovel() == null ? "" : bemImovel.getClassificacaoBemImovel().getDescricao());
			request.setAttribute("bemImovelSituacaoLocalizacao", bemImovel.getDescricaoSituacaoLocal() == null ? "" : bemImovel.getDescricaoSituacaoLocal());
			request.setAttribute("bemImovelSituacaoLegalCartorial", bemImovel.getSituacaoLegalCartorial() == null ? "" : bemImovel.getSituacaoLegalCartorial().getDescricao());
			request.setAttribute("bemImovelEstadoMunicipio", bemImovel.getMunicipio() == null ? "" : bemImovel.getMunicipio().concat(" / ".concat(bemImovel.getUf())));
			request.setAttribute("bemImovelLogradouroNum", bemImovel.getLogradouro() == null ? "" : bemImovel.getLogradouro());
			request.setAttribute("bemImovelBairroDistrito", bemImovel.getBairroDistrito() == null ? "" : bemImovel.getBairroDistrito());
			request.setAttribute("bemImovelAreaTerreno", bemImovel.getAreaTerreno() == null ? "" : Util.formataNumeroMonetario(bemImovel.getAreaTerreno()));
			request.setAttribute("bemImovelConstruida", bemImovel.getAreaConstruida() == null ? "" : Util.formataNumeroMonetario(bemImovel.getAreaConstruida()));
			request.setAttribute("bemImovelDisponivel", bemImovel.getAreaDispoNivel() == null ? "" : Util.formataNumeroMonetario(bemImovel.getAreaDispoNivel()));
			request.setAttribute("bemImovelSituacaoImovel", bemImovel.getSituacaoImovel() == null ? "" : bemImovel.getSituacaoImovel().getDescricao());
			request.setAttribute("bemImovelSomenteTerreno", bemImovel.getSomenteTerreno() == null ? "" : ("N".equals(bemImovel.getSomenteTerreno()) ? "Não" : "Sim"));
			
			return mapping.findForward("pgEditConAjax");
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.geral", new String[]{ManterVistoriaBemImovelAction.class.getSimpleName()+".ajaxCarregarInformacaoBemImovel()"}, 
					e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Excluir os dados do respectivo caso de uso.<br>
	 * @author tatianapires
	 * @since 28/06/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {

			ManterVistoriaBemImovelForm localForm = (ManterVistoriaBemImovelForm)form;
			
			Integer codVistoria = Integer.valueOf(localForm.getCodVistoria());
			
			Vistoria vistoria = OperacaoFacade.obterVistoria(codVistoria);
			OperacaoFacade.excluirVistoria(vistoria);
			
			this.addMessage("SUCESSO.1001", request);
			
			return carregarInterfaceConsultar(mapping, localForm, request, response);
		}catch (ApplicationException ae) {
			setActionForward(carregarInterfaceConsultar(mapping, form, request, response));
			throw ae;
		}catch (Exception e) {
			setActionForward(carregarInterfaceConsultar(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{ManterVistoriaBemImovelAction.class.getSimpleName()+".excluir()"}, e, ApplicationException.ICON_ERRO);
		}		
	}

	/**
	 * Incluir os dados do respectivo caso de uso.<br>
	 * @author tatianapires
	 * @since 30/06/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward incluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			ManterVistoriaBemImovelForm localForm = (ManterVistoriaBemImovelForm)form;
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			
			if (!validaForm(mapping, form, request)) {
				return carregarInterfaceIncluir(mapping, form, request, response);
			}	
			//validar itens selecionados
			Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
			if (localForm.getCodInstituicao() == null){ //usuario nao é adm geral
				if (usuario.getInstituicao() != null){
					localForm.setCodInstituicao(usuario.getInstituicao().getCodInstituicao().toString());
				}
			}
			BemImovel bemImovel = CadastroFacade.obterBemImovelInstituicaoExibir(Integer.valueOf(localForm.getNumeroBemImovel()), Integer.valueOf(localForm.getCodInstituicao()), usuario);
			
			List<ParametroVistoria> listaParametroVistoria =OperacaoFacade.listarParametroVistoriaComDenominacaoBemImovel(bemImovel);
			List<ParametroVistoria> listaParametroVistoriaChecados = new ArrayList<ParametroVistoria>();
			for (ParametroVistoria parametroVistoria : listaParametroVistoria) {
				String parametroChecado = request.getParameter(obterNomeCampoItemPagina(request, "parametroVistoria_" + parametroVistoria.getCodParametroVistoria()));
				if ("on".equals(parametroChecado)) {
					parametroVistoria.setParametroChecado(Boolean.TRUE);
					listaParametroVistoriaChecados.add(parametroVistoria);
				}
			}
			
			if (!Util.validarLista(listaParametroVistoriaChecados)) {
				throw new ApplicationException("AVISO.1000", ApplicationException.ICON_AVISO);
			}
			
			Integer codEdificacao = StringUtils.isBlank(localForm.getEspecificacaoEdificacao()) ? null : Integer.valueOf(localForm.getEspecificacaoEdificacao());
			Integer codBemImovel = Integer.valueOf(localForm.getNumeroBemImovel());
			Integer codVistoriador = Integer.valueOf(localForm.getVistoriador());
			Integer codInstituicao = 0;
			if (localForm.getCodInstituicao() != null && !localForm.getCodInstituicao().isEmpty()){
				codInstituicao = Integer.valueOf(localForm.getCodInstituicao());	
			}
			
			OperacaoFacade.salvarVistoria(sentinelaInterface.getCpf(), codEdificacao, codVistoriador, codBemImovel, codInstituicao, listaParametroVistoriaChecados, usuario);
			
			this.addMessage("SUCESSO.1000", request);
			
			return carregarInterfaceConsultar(mapping, localForm, request, response);
		}catch (ApplicationException ae) {
			setActionForward(carregarInterfaceIncluir(mapping, form, request, response));
			throw ae;
		}catch (Exception e) {
			setActionForward(carregarInterfaceIncluir(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{ManterVistoriaBemImovelAction.class.getSimpleName()+".incluir()"}, e, ApplicationException.ICON_ERRO);
		}		
	}
	
	/**
	 * Obtem o nome do campos de acordo com parte do nome informada.<br>
	 * @author tatianapires
	 * @since 04/07/2011
	 * @param request : HttpServletRequest
	 * @param parteNome : String
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	private String obterNomeCampoItemPagina(HttpServletRequest request, String parteNome){
		// Obtendo os campos da tela
		Enumeration<String> nomesCampos = request.getParameterNames();

		while(nomesCampos.hasMoreElements()){
			String nomeCampo = nomesCampos.nextElement();
			if (nomeCampo.contains(parteNome)){
				return nomeCampo;
			}
		}
		return "";
	}
	
	/**
	 * Redireciona para outros UCS.<br>
	 * @author tatianapires
	 * @since 30/06/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward redirecionarUCS (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException{
		try {
			
			ManterVistoriaBemImovelForm sForm = (ManterVistoriaBemImovelForm) form;
			
			request.setAttribute("ucsChamador", "ucsManterVistoriaBemImovel");
			
			if(StringUtils.isNotBlank(request.getParameter("redireciona"))){
				String redireciona = request.getParameter("redireciona");
				
				if ("ucsLocalizarBemImovelSimplificado".equals(redireciona)){
					sForm.setUcsDestino("ucsLocalizarBemImovelSimplificado");
					sForm.setUcsChamador("ucsManterVistoriaBemImovel");
				}
				
				return acionarOutroCasoUso(mapping, sForm, request, response);
			} else {
				return null;
			}
					
		} catch (ApplicationException ae) {
			throw ae;
		} catch (Exception e) {
			setActionForward(carregarInterfaceIncluir(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{this.getClass().getSimpleName()+".redirecionarUCS()"}, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Redireciona para o UCS GerarRelatorioVistoriaBemImovel.<br>
	 * @author ginaalmeida
	 * @since 13/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward redirecionarUCSGerarRelatorio (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException{
		try {
			
			ManterVistoriaBemImovelForm sForm = (ManterVistoriaBemImovelForm) form;
			sForm.setUcsDestino("ucsGerarRelatorioVistoriaBemImovel");
			sForm.setUcsChamador("ucsManterVistoriaBemImovel");
			
			request.setAttribute("ucsChamador", "ucsManterVistoriaBemImovel");
			request.setAttribute("codVistoria", sForm.getCodVistoria());
			request.setAttribute("conInstituicao", sForm.getConInstituicao());
			
				
			return acionarOutroCasoUso(mapping, sForm, request, response);
					
		} catch (ApplicationException ae) {
			throw ae;
		} catch (Exception e) {
			setActionForward(carregarInterfaceIncluir(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{this.getClass().getSimpleName()+".redirecionarUCSGerarRelatorio()"}, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Continua o processo de chamada de outro caso de uso.<br>
	 * @author tatianapires
	 * @since 24/08/2010
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward continuarProcesso(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
		throws ApplicationException {	
		return carregarInterfaceIncluir(mapping, form, request, response);	
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
			ManterVistoriaBemImovelForm localForm = (ManterVistoriaBemImovelForm) form;

			Integer codBemImovelSimpl = (request.getParameter("codBemImovelSimpl") == null ? 1 : Integer.valueOf(request.getParameter("codBemImovelSimpl")));
			Integer codInstituicao = (request.getParameter("codInstituicao") == null ? 1 : Integer.valueOf(request.getParameter("codInstituicao")));
			
			String camposPesquisaUCOrigem = request.getParameter("camposPesquisaUCOrigem") != null ? request.getParameter("camposPesquisaUCOrigem").toString() : "";		
			String[] valores = camposPesquisaUCOrigem.split(";");
			localForm.setConNumeroBemImovel(valores[0]);
			localForm.setConDataInicialVistoria(valores[1]);
			localForm.setConDataInicialVistoria(valores[2]);
			localForm.setConSituacao(valores[3]);
			localForm.setTipoAcao(valores[5]);
			localizarBemImovel = true;
			
			if (valores[4].equalsIgnoreCase("P"))  {
				localForm.setConNumeroBemImovel(String.valueOf(codBemImovelSimpl));
				localForm.setConInstituicao(codInstituicao.toString());
				return this.carregarInterfaceConsultar(mapping, localForm, request, response);
			} else {
				localForm.setNumeroBemImovel(String.valueOf(codBemImovelSimpl));
				localForm.setConInstituicao(codInstituicao.toString());
				return this.carregarInterfaceIncluir(mapping, localForm, request, response);
			}
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{DoacaoBemImovelAction.class.getSimpleName()+".retornoLocalizarBemImovel"}, e, ApplicationException.ICON_ERRO);
		} 
	}

}
