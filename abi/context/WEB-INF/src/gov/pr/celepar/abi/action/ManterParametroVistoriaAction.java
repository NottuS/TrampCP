package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.ParametroVistoriaDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.facade.OperacaoFacade;
import gov.pr.celepar.abi.form.ManterParametroVistoriaForm;
import gov.pr.celepar.abi.pojo.DenominacaoImovel;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.ParametroVistoria;
import gov.pr.celepar.abi.pojo.ParametroVistoriaDenominacaoImovel;
import gov.pr.celepar.abi.pojo.ParametroVistoriaDominio;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Oksana
 * @version 1.0
 * @since 22/06/2011
 * 
 * Classe Action:
 * Responsavel por manipular a localização de Bem Imovel
 */

public class ManterParametroVistoriaAction extends BaseDispatchAction {

	public ActionForward carregarInterfaceInicial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			ManterParametroVistoriaForm localForm = (ManterParametroVistoriaForm)form;
			if (localForm.getConAtivo() == null || Util.strEmBranco(localForm.getConAtivo())){
				localForm.setConAtivo("1");
			}
			request.getSession().setAttribute("denominacaoImovels", Util.htmlEncodeCollection(CadastroFacade.listarDenominacaoImovels()));
			request.getSession().setAttribute("pagina", null);
			request.setAttribute("listaDenominacaoImovelSelecionada", null);
			request.setAttribute("listaDenominacaoImovelDisponivel", null);
			request.getSession().setAttribute("paginaDominio", null);
			localForm.setIndGrupoSentinela(request.getSession().getAttribute("indGrupoSentinela").toString());	
			localForm.setAdm(GrupoSentinela.ADM_GERAL.getDescricao());
			
			//lista instituicao
			if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
				request.setAttribute("listaInstituicao", Util.htmlEncodeCollection(CadastroFacade.listarInstituicao()));	
			}
			if (localForm.getActionType() != null && localForm.getActionType().equals("pesquisar")){
				this.pesquisar(mapping, localForm, request, response);
			}
		} catch (Exception e) {
			throw new ApplicationException("ERRO.1", e, ApplicationException.ICON_ERRO);
		} 	
		return mapping.findForward("pgConManterParametroVistoria");
	}
	
	
	/**
	 * Carrega a tabela de pesquisa (via Ajax) na tela de Consulta do respectivo Caso de Uso.<br>
	 * @author oksana
	 * @since 22/06/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */	
	
	public ActionForward pesquisar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		try{
			ManterParametroVistoriaForm localForm = (ManterParametroVistoriaForm)form;
			setActionForward(mapping.findForward("pgConManterParametroVistoria"));
			request.getSession().setAttribute("denominacaoImovels", Util.htmlEncodeCollection(CadastroFacade.listarDenominacaoImovels()));
			ParametroVistoriaDTO parametroVistoriaDTO = new ParametroVistoriaDTO();
			if (!Util.strEmBranco(localForm.getConDescricao())){
				parametroVistoriaDTO.setDescricao(localForm.getConDescricao());
			}
			if (!Util.strEmBranco(localForm.getConDenominacaoImovel())){
				parametroVistoriaDTO.setCodDenominacaoImovel(Integer.valueOf(localForm.getConDenominacaoImovel()));
			}
			if (!Util.strEmBranco(localForm.getConAtivo()) && (localForm.getConAtivo().equals("1") || localForm.getConAtivo().equals("2")) ){
				parametroVistoriaDTO.setIndAtivo(Integer.valueOf(localForm.getConAtivo()));
			}
			
			//lista instituicao
			Integer codInstituicao = null;
			if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
				request.setAttribute("listaInstituicao", Util.htmlEncodeCollection(CadastroFacade.listarInstituicao()));
				if (localForm.getConInstituicao() != null && !localForm.getConInstituicao().isEmpty()){
					codInstituicao = Integer.valueOf(localForm.getConInstituicao());
				}
			}else{
				//obtem a instituicao do usuario logado
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
				if (usuario == null){
					throw new ApplicationException("AVISO.97");
				}
				codInstituicao = usuario.getInstituicao().getCodInstituicao();
			}
			parametroVistoriaDTO.setCodInstituicao(codInstituicao);
			Pagina pagina = new Pagina(); 
			pagina = CadastroFacade.listarParametroVistoria(pagina, parametroVistoriaDTO);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.getSession().setAttribute("pagina", pagina);
			if(pagina.getTotalRegistros() == null || pagina.getTotalRegistros() == 0) {
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			return getActionForward();
		}catch (ApplicationException ae) {
			throw ae;
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{LocalizarBemImovelSimplificadoAction.class.getSimpleName() +".pesquisarBemImovel()"}, e, ApplicationException.ICON_ERRO);
		}

	}
	
	/**
	 * Carrega pagina para alteração/inclusão de parâmetro vistoria.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarInterfaceIncluirAlterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException { 
		
		setActionForward(mapping.findForward("pgConManterParametroVistoria"));
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		ManterParametroVistoriaForm localForm = (ManterParametroVistoriaForm)form;	
		try {
			if (localForm.getCodParametroVistoria().isEmpty()){
				//inclusao
				localForm.setActionType("incluir");
				if (localForm.getAcao() == null || !localForm.getAcao().equals("voltar")){
					localForm.setDominioPreenchido(Boolean.FALSE.toString());
					request.setAttribute("listaDenominacaoImovelDisponivel", CadastroFacade.listarDenominacaoImovels());
					request.setAttribute("listaDenominacaoImovelSelecionada", new ArrayList<DenominacaoImovel>());
					
					Pagina paginaDominio = new Pagina();
					List<ParametroVistoriaDominio> listaParametroVistoriaDominio = new ArrayList<ParametroVistoriaDominio>();
					paginaDominio.setRegistros(listaParametroVistoriaDominio);
					request.getSession().setAttribute("paginaDominio", paginaDominio);
					localForm.setAcao(null);
					localForm.setIndTipoParametro(String.valueOf(Integer.valueOf(1)));
					//lista instituicao
					if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
						request.setAttribute("listaInstituicao", Util.htmlEncodeCollection(CadastroFacade.listarInstituicao()));	
					}
				}
			}else{
				//alteracao
				localForm.setActionType("alterar");
				ParametroVistoria parametroVistoria = OperacaoFacade.obterParametroVistoria(Integer.valueOf(localForm.getCodParametroVistoria()));
				localForm.setCodParametroVistoria(parametroVistoria.getCodParametroVistoria().toString());
				localForm.setInstituicao(parametroVistoria.getInstituicao().getSiglaDescricao());

				if (localForm.getAcao() == null || !localForm.getAcao().equals("voltar")){
					localForm.setDescricao(parametroVistoria.getDescricao());
					if (parametroVistoria.getOrdemApresentacao() != null){
						localForm.setOrdemApresentacao(parametroVistoria.getOrdemApresentacao().toString());	
					}
					if (!parametroVistoria.getIndTipoParametro().equals(1)){
						localForm.setDominioPreenchido(Boolean.TRUE.toString());	
					}else{
						localForm.setDominioPreenchido(Boolean.FALSE.toString());
					}
					List<Integer> listaCodDenominacaoImovel = new ArrayList<Integer>();
					for (ParametroVistoriaDenominacaoImovel pvdi: parametroVistoria.getListaParametroVistoriaDenominacaoImovel()){
						listaCodDenominacaoImovel.add(pvdi.getDenominacaoImovel().getCodDenominacaoImovel());
					}
					
					request.setAttribute("listaDenominacaoImovelSelecionada", CadastroFacade.listarDenominacaoImovelsContenha(listaCodDenominacaoImovel));
					request.setAttribute("listaDenominacaoImovelDisponivel", CadastroFacade.listarDenominacaoImovelsExceto(listaCodDenominacaoImovel));
					
					Pagina paginaDominio = new Pagina();
					List<ParametroVistoriaDominio> listaParametroVistoriaDominioAtual = Util.setToList(parametroVistoria.getListaParametroVistoriaDominio());
					List<ParametroVistoriaDominio> listaParametroVistoriaDominio = new ArrayList<ParametroVistoriaDominio>();
					for (ParametroVistoriaDominio pvd: listaParametroVistoriaDominioAtual){
						ParametroVistoriaDominio pvdNovo = new ParametroVistoriaDominio();
						pvdNovo.setDescricao(pvd.getDescricao());
						listaParametroVistoriaDominio.add(pvdNovo);	
					}
					paginaDominio.setRegistros(listaParametroVistoriaDominio);
					request.getSession().setAttribute("paginaDominio", paginaDominio);
					localForm.setAcao(null);
					localForm.setIndTipoParametro(String.valueOf(Integer.valueOf(parametroVistoria.getIndTipoParametro())));
				}
			}
			
			setActionForward(mapping.findForward("pgEditManterParametroVistoria"));
			
		} catch (ApplicationException appEx) {			
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.200", new String[]{"edição","carregarInterfaceIncluirAlterar"}, e);
		}		
		return getActionForward();
	}
	
	
	/**
	 * @author Oksana
	 * @since 29/06/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward atualizarDominio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			ManterParametroVistoriaForm localForm = (ManterParametroVistoriaForm)form;	
			
			setActionForward(mapping.findForward("tabelaAjax"));
			Pagina paginaDominio = (Pagina) request.getSession().getAttribute("paginaDominio");
			List<ParametroVistoriaDominio> listaParametroVistoriaDominio = (List<ParametroVistoriaDominio>) paginaDominio.getRegistros();
			String dominio = localForm.getDominio();
			if (dominio != null && !dominio.isEmpty()){
				Boolean existe = false;
				for (ParametroVistoriaDominio pvd: listaParametroVistoriaDominio){
					if (pvd.getDescricao().equals(dominio)){
						existe = true;
						break;
					}
				}
				if (!existe){
					ParametroVistoriaDominio pvd = new ParametroVistoriaDominio();
					pvd.setDescricao(dominio);
					listaParametroVistoriaDominio.add(pvd);
					paginaDominio.setRegistros(listaParametroVistoriaDominio);
					request.getSession().setAttribute("paginaDominio", paginaDominio);
					localForm.setDominioPreenchido(Boolean.TRUE.toString());
				}else{
					addMessage("AVISO.80", request);
				}
			}
			return getActionForward();
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[] { ManterParametroVistoriaAction.class.getSimpleName()+ ".atualizarDominio()" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Efetua a Inclusao dos dados do respectivo Caso de Uso no Banco de Dados.<br>
	 * @author Oksana
	 * @since 30/06/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward incluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try{
			ManterParametroVistoriaForm localForm = (ManterParametroVistoriaForm) form;			

			if (!validaForm(mapping, form, request)) {
				return carregarInterfaceIncluirAlterar(mapping, form, request, response);
			}
			//executa as validações
			Boolean erro = false;
			String mensagem = "";
			
			Integer codInstituicao = null;
			if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
				codInstituicao = Integer.valueOf(localForm.getConInstituicao());
			}else{
				//	obtem a instituicao do usuario logado
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
				if (usuario == null){
					throw new ApplicationException("AVISO.97");
				}
				codInstituicao = usuario.getInstituicao().getCodInstituicao();
			}
			
			if (localForm.getDescricao() == null || localForm.getDescricao().isEmpty()){
				erro = true;
				mensagem = "AVISO.81";
			}else{
				if (OperacaoFacade.existeDescricaoParametroVistoria(null, localForm.getDescricao(), codInstituicao)){
					erro = true;
					mensagem = "AVISO.85";
				}else{
					if (localForm.getListaDenominacaoImovelSelecionada().length < 1){
						erro = true;
						mensagem = "AVISO.83";
					}else{
						if (localForm.getIndTipoParametro() == null || localForm.getIndTipoParametro().isEmpty()){
							erro = true;
							mensagem = "AVISO.82";
						}else{
							if (!localForm.getIndTipoParametro().equals("1")){
								Pagina paginaDominio = (Pagina) request.getSession().getAttribute("paginaDominio");
								if (paginaDominio == null || paginaDominio.getRegistros().isEmpty()){
									erro = true;
									mensagem = "AVISO.84";
								}
							}
						}	
					}	
				}
			}
			if (erro){
				List<Integer> listaCodDenominacaoImovel = new ArrayList<Integer>();
				if (localForm.getListaDenominacaoImovelSelecionada() != null){
					for (Integer i = 0; (i < localForm.getListaDenominacaoImovelSelecionada().length);i++){
						Integer codigo = Integer.valueOf(localForm.getListaDenominacaoImovelSelecionada()[i]);
						listaCodDenominacaoImovel.add(codigo);
					}					
				}

				request.setAttribute("listaDenominacaoImovelSelecionada", CadastroFacade.listarDenominacaoImovelsContenha(listaCodDenominacaoImovel));
				request.setAttribute("listaDenominacaoImovelDisponivel", CadastroFacade.listarDenominacaoImovelsExceto(listaCodDenominacaoImovel));
				localForm.setAcao("voltar");
				throw new ApplicationException(mensagem, ApplicationException.ICON_AVISO);
			}
			
			//prepara para a inclusão
			
			ParametroVistoria parametroVistoriaNovo = new ParametroVistoria();
			parametroVistoriaNovo.setTsInclusao(new Date());
			
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			parametroVistoriaNovo.setCpfResponsavel(sentinelaInterface.getCpf());
			
			parametroVistoriaNovo.setDescricao(localForm.getDescricao());
			if (localForm.getOrdemApresentacao() != null && !localForm.getOrdemApresentacao().isEmpty()){
				parametroVistoriaNovo.setOrdemApresentacao(Integer.valueOf(localForm.getOrdemApresentacao()));
			}
			if (localForm.getListaDenominacaoImovelSelecionada() != null){
				for (Integer i = 0; (i < localForm.getListaDenominacaoImovelSelecionada().length);i++){
					Integer codigo = Integer.valueOf(localForm.getListaDenominacaoImovelSelecionada()[i]);
					DenominacaoImovel denominacaoImovel = CadastroFacade.obterDenominacaoImovel(codigo);
					ParametroVistoriaDenominacaoImovel parametroVistoriaDenominacaoImovelNovo = new ParametroVistoriaDenominacaoImovel();
					parametroVistoriaDenominacaoImovelNovo.setParametroVistoria(parametroVistoriaNovo);
					parametroVistoriaDenominacaoImovelNovo.setDenominacaoImovel(denominacaoImovel);
					parametroVistoriaNovo.getListaParametroVistoriaDenominacaoImovel().add(parametroVistoriaDenominacaoImovelNovo);
				}					
			}
			parametroVistoriaNovo.setIndAtivo(Boolean.TRUE);
			parametroVistoriaNovo.setIndTipoParametro(Integer.valueOf(localForm.getIndTipoParametro()));
			Pagina paginaDominio = (Pagina) request.getSession().getAttribute("paginaDominio");
			List<ParametroVistoriaDominio> listaParametroVistoriaDominioNovo =  new ArrayList<ParametroVistoriaDominio>();
			if (paginaDominio != null && !paginaDominio.getRegistros().isEmpty()){
				List<ParametroVistoriaDominio> listaParametroVistoriaDominio = (List<ParametroVistoriaDominio>) paginaDominio.getRegistros();
				
				for (ParametroVistoriaDominio parametroVistoriaDominio: listaParametroVistoriaDominio){
					ParametroVistoriaDominio parametroVistoriaDominioNovo = new ParametroVistoriaDominio();
					parametroVistoriaDominioNovo.setParametroVistoria(parametroVistoriaNovo);
					parametroVistoriaDominioNovo.setDescricao(parametroVistoriaDominio.getDescricao());
					listaParametroVistoriaDominioNovo.add(parametroVistoriaDominioNovo);
				}
			}
			
			//instituicao
			Instituicao instituicao = new Instituicao();
			instituicao.setCodInstituicao(codInstituicao);
			parametroVistoriaNovo.setInstituicao(instituicao);
			OperacaoFacade.salvarParametroVistoria(parametroVistoriaNovo, listaParametroVistoriaDominioNovo);
			
			if (parametroVistoriaNovo.getIndTipoParametro().equals(Integer.valueOf(1))) {
				OperacaoFacade.excluirDominioParametroVistoria(parametroVistoriaNovo);
			}

			this.addMessage("SUCESSO.58", request);
			localForm.setActionType("pesquisar");
			return carregarInterfaceInicial(mapping, localForm, request, response);		
		}catch (ApplicationException ae) {
			setActionForward(carregarInterfaceIncluirAlterar(mapping, form, request, response));
			throw ae;
		}catch (Exception e) {
			setActionForward(carregarInterfaceIncluirAlterar(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterParametroVistoriaAction.class.getSimpleName()+".incluir()"}, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Efetua a Inclusao dos dados do respectivo Caso de Uso no Banco de Dados.<br>
	 * @author Oksana
	 * @since 30/06/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try{
			ManterParametroVistoriaForm localForm = (ManterParametroVistoriaForm) form;			

			if (!validaForm(mapping, form, request)) { 
				return carregarInterfaceIncluirAlterar(mapping, form, request, response);
			}
			//executa as validações
			Boolean erro = false;
			String mensagem = "";
			
			if (localForm.getDescricao() == null || localForm.getDescricao().isEmpty()){
				erro = true;
				mensagem = "AVISO.81";
			}else{
				if (OperacaoFacade.existeDescricaoParametroVistoria(Integer.valueOf(localForm.getCodParametroVistoria()), localForm.getDescricao(), null)){
					erro = true;
					mensagem = "AVISO.85";
				}else{
					if (localForm.getListaDenominacaoImovelSelecionada().length < 1){
						erro = true;
						mensagem = "AVISO.83";
					}else{
						if (localForm.getIndTipoParametro() == null || localForm.getIndTipoParametro().isEmpty()){
							erro = true;
							mensagem = "AVISO.82";
						}else{
							if (!localForm.getIndTipoParametro().equals("1")){
								Pagina paginaDominio = (Pagina) request.getSession().getAttribute("paginaDominio");
								if (paginaDominio == null || paginaDominio.getRegistros().isEmpty()){
									erro = true;
									mensagem = "AVISO.84";
								}
							}
						}	
					}	
				}	
			}
			if (erro){
				List<Integer> listaCodDenominacaoImovel = new ArrayList<Integer>();
				if (localForm.getListaDenominacaoImovelSelecionada() != null){
					for (Integer i = 0; (i < localForm.getListaDenominacaoImovelSelecionada().length);i++){
						Integer codigo = Integer.valueOf(localForm.getListaDenominacaoImovelSelecionada()[i]);
						listaCodDenominacaoImovel.add(codigo);
					}					
				}

				request.setAttribute("listaDenominacaoImovelSelecionada", CadastroFacade.listarDenominacaoImovelsContenha(listaCodDenominacaoImovel));
				request.setAttribute("listaDenominacaoImovelDisponivel", CadastroFacade.listarDenominacaoImovelsExceto(listaCodDenominacaoImovel));
				localForm.setAcao("voltar");
				throw new ApplicationException(mensagem, ApplicationException.ICON_AVISO);
			}
			
			//prepara para a alteracao
			
			ParametroVistoria parametroVistoria = OperacaoFacade.obterParametroVistoria(Integer.valueOf(localForm.getCodParametroVistoria()));
			parametroVistoria.setTsAtualizacao(new Date());
			
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			parametroVistoria.setCpfResponsavel(sentinelaInterface.getCpf());
			
			parametroVistoria.setDescricao(localForm.getDescricao());
			if (localForm.getOrdemApresentacao() != null && !localForm.getOrdemApresentacao().isEmpty()){
				parametroVistoria.setOrdemApresentacao(Integer.valueOf(localForm.getOrdemApresentacao()));
			}
			parametroVistoria.setListaParametroVistoriaDenominacaoImovel(new HashSet<ParametroVistoriaDenominacaoImovel>(0));
			if (localForm.getListaDenominacaoImovelSelecionada() != null){
				for (Integer i = 0; (i < localForm.getListaDenominacaoImovelSelecionada().length);i++){
					Integer codigo = Integer.valueOf(localForm.getListaDenominacaoImovelSelecionada()[i]);
					// verifica se já existe
					Boolean achou = false;
					for (ParametroVistoriaDenominacaoImovel pvdi: parametroVistoria.getListaParametroVistoriaDenominacaoImovel()){
						if (pvdi.getDenominacaoImovel().getCodDenominacaoImovel().equals(codigo)){
							achou = true;
						break;
						}
					}
					//
					if (!achou){
						DenominacaoImovel denominacaoImovel = CadastroFacade.obterDenominacaoImovel(codigo);
						ParametroVistoriaDenominacaoImovel parametroVistoriaDenominacaoImovelNovo = new ParametroVistoriaDenominacaoImovel();
						parametroVistoriaDenominacaoImovelNovo.setParametroVistoria(parametroVistoria);
						parametroVistoriaDenominacaoImovelNovo.setDenominacaoImovel(denominacaoImovel);
						parametroVistoria.getListaParametroVistoriaDenominacaoImovel().add(parametroVistoriaDenominacaoImovelNovo);	
					}
					
				}					
			}
			parametroVistoria.setIndAtivo(Boolean.TRUE);
			parametroVistoria.setIndTipoParametro(Integer.valueOf(localForm.getIndTipoParametro()));
			Pagina paginaDominio = (Pagina) request.getSession().getAttribute("paginaDominio");
			List<ParametroVistoriaDominio> listaParametroVistoriaDominioNovo =  new ArrayList<ParametroVistoriaDominio>();
			if (paginaDominio != null && !paginaDominio.getRegistros().isEmpty()){
				List<ParametroVistoriaDominio> listaParametroVistoriaDominio = (List<ParametroVistoriaDominio>) paginaDominio.getRegistros();
				
				for (ParametroVistoriaDominio parametroVistoriaDominio: listaParametroVistoriaDominio){
					ParametroVistoriaDominio parametroVistoriaDominioNovo = new ParametroVistoriaDominio();
					parametroVistoriaDominioNovo.setParametroVistoria(parametroVistoria);
					parametroVistoriaDominioNovo.setDescricao(parametroVistoriaDominio.getDescricao());
					listaParametroVistoriaDominioNovo.add(parametroVistoriaDominioNovo);
				}
			}
			
			OperacaoFacade.alterarParametroVistoria(parametroVistoria, listaParametroVistoriaDominioNovo);
			
			if (parametroVistoria.getIndTipoParametro().equals(Integer.valueOf(1))) {
				OperacaoFacade.excluirDominioParametroVistoria(parametroVistoria);
			}
			
			this.addMessage("SUCESSO.59", request);
			return carregarInterfaceInicial(mapping, localForm, request, response);		
		}catch (ApplicationException ae) {
			setActionForward(carregarInterfaceIncluirAlterar(mapping, form, request, response));
			throw ae;
		}catch (Exception e) {
			setActionForward(carregarInterfaceIncluirAlterar(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterParametroVistoriaAction.class.getSimpleName()+".alterar()"}, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * @author Oksana
	 * @since 29/06/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward excluirDominio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			ManterParametroVistoriaForm localForm = (ManterParametroVistoriaForm)form;	
			setActionForward(mapping.findForward("tabelaAjax"));
			Pagina paginaDominio = (Pagina) request.getSession().getAttribute("paginaDominio");
			List<ParametroVistoriaDominio> listaParametroVistoriaDominio = (List<ParametroVistoriaDominio>) paginaDominio.getRegistros();
			List<ParametroVistoriaDominio> listaParametroVistoriaDominioNovo =  new ArrayList<ParametroVistoriaDominio>();
			String dominio = localForm.getDominio();
			if (dominio != null && !dominio.isEmpty()){
				for (ParametroVistoriaDominio pvd: listaParametroVistoriaDominio){
					if (!pvd.getDescricao().equals(dominio)){
						listaParametroVistoriaDominioNovo.add(pvd);
					}
				}
				paginaDominio.setRegistros(listaParametroVistoriaDominioNovo);
				request.getSession().setAttribute("paginaDominio", paginaDominio);
			}
			return getActionForward();
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[] { ManterParametroVistoriaAction.class.getSimpleName()+ ".atualizarDominio()" }, e, ApplicationException.ICON_ERRO);
		}
	}

	
	/**
	 * Carrega pagina para exibição de parâmetro vistoria.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarInterfaceExibir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException { 
		
		setActionForward(mapping.findForward("pgConManterParametroVistoria"));
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		ManterParametroVistoriaForm localForm = (ManterParametroVistoriaForm)form;	
		try {
			
			ParametroVistoria parametroVistoria = OperacaoFacade.obterParametroVistoria(Integer.valueOf(localForm.getCodParametroVistoria()));
			localForm.setCodParametroVistoria(parametroVistoria.getCodParametroVistoria().toString());
			localForm.setDescricao(parametroVistoria.getDescricao());
			localForm.setInstituicao(parametroVistoria.getInstituicao().getSiglaDescricao());
			if (parametroVistoria.getOrdemApresentacao() != null){
				localForm.setOrdemApresentacao(parametroVistoria.getOrdemApresentacao().toString());	
			}
			if (!parametroVistoria.getIndTipoParametro().equals(1)){
				localForm.setDominioPreenchido(Boolean.TRUE.toString());	
			}else{
				localForm.setDominioPreenchido(Boolean.FALSE.toString());
			}
			List<Integer> listaCodDenominacaoImovel = new ArrayList<Integer>();
			for (ParametroVistoriaDenominacaoImovel pvdi: parametroVistoria.getListaParametroVistoriaDenominacaoImovel()){
				listaCodDenominacaoImovel.add(pvdi.getDenominacaoImovel().getCodDenominacaoImovel());
			}
			request.getSession().setAttribute("listaDenominacaoImovelSelecionada", CadastroFacade.listarDenominacaoImovelsContenha(listaCodDenominacaoImovel));
			Pagina paginaDenominacao = new Pagina();
			paginaDenominacao.setRegistros(CadastroFacade.listarDenominacaoImovelsContenha(listaCodDenominacaoImovel));
			request.getSession().setAttribute("paginaDenominacao", paginaDenominacao);
			
			
			Pagina paginaDominio = new Pagina();
			List<ParametroVistoriaDominio> listaParametroVistoriaDominioAtual = Util.setToList(parametroVistoria.getListaParametroVistoriaDominio());
			paginaDominio.setRegistros(listaParametroVistoriaDominioAtual);
			request.getSession().setAttribute("paginaDominio", paginaDominio);
			if (Integer.valueOf(parametroVistoria.getIndTipoParametro()).equals(1)){
				localForm.setIndTipoParametro("Texto");	
			}
			if (Integer.valueOf(parametroVistoria.getIndTipoParametro()).equals(2)){
				localForm.setIndTipoParametro("Domínio unitário <i>(ex.Padrão de acabamento: Luxo OU Normal)</i>");	
			}
			if (Integer.valueOf(parametroVistoria.getIndTipoParametro()).equals(3)){
				localForm.setIndTipoParametro("Domínio múltiplo <i>(ex.Revestimento interno: Cerâmico E PVA)</i>");
			}
			
			setActionForward(mapping.findForward("pgViewManterParametroVistoria"));
			
		} catch (ApplicationException appEx) {			
			throw appEx;
			
		} catch (Exception e) {
			throw new ApplicationException("ERRO.200", new String[]{"edição","carregarInterfaceExibir"}, e);
		}		
		return getActionForward();
	}

	
	/**
	 * Efetua a ativação do parâmetro vistoria.<br>
	 * @author Oksana
	 * @since 04/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward ativar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try{
			ManterParametroVistoriaForm localForm = (ManterParametroVistoriaForm) form;			

			if (!validaForm(mapping, form, request)) {
				return carregarInterfaceIncluirAlterar(mapping, form, request, response);
			}
			ParametroVistoria parametroVistoria = OperacaoFacade.obterParametroVistoria(Integer.valueOf(localForm.getCodParametroVistoria()));
			parametroVistoria.setTsAtualizacao(new Date());
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			parametroVistoria.setCpfResponsavel(sentinelaInterface.getCpf());
			
			OperacaoFacade.ativarDesativarParametroVistoria(parametroVistoria, Boolean.TRUE);
			
			this.addMessage("SUCESSO.60", request);
			return carregarInterfaceInicial(mapping, localForm, request, response);		
		}catch (ApplicationException ae) {
			setActionForward(carregarInterfaceExibir(mapping, form, request, response));
			throw ae;
		}catch (Exception e) {
			setActionForward(carregarInterfaceExibir(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterParametroVistoriaAction.class.getSimpleName()+".ativar()"}, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Efetua a desativação do parâmetro vistoria.<br>
	 * @author Oksana
	 * @since 04/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward desativar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try{
			ManterParametroVistoriaForm localForm = (ManterParametroVistoriaForm) form;			

			if (!validaForm(mapping, form, request)) {
				return carregarInterfaceIncluirAlterar(mapping, form, request, response);
			}
			ParametroVistoria parametroVistoria = OperacaoFacade.obterParametroVistoria(Integer.valueOf(localForm.getCodParametroVistoria()));
			parametroVistoria.setTsAtualizacao(new Date());
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			parametroVistoria.setCpfResponsavel(sentinelaInterface.getCpf());
			
			OperacaoFacade.ativarDesativarParametroVistoria(parametroVistoria, Boolean.FALSE);
			
			this.addMessage("SUCESSO.61", request);
			return carregarInterfaceInicial(mapping, localForm, request, response);		
		}catch (ApplicationException ae) {
			setActionForward(carregarInterfaceExibir(mapping, form, request, response));
			throw ae;
		}catch (Exception e) {
			setActionForward(carregarInterfaceExibir(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterParametroVistoriaAction.class.getSimpleName()+".desativar()"}, e, ApplicationException.ICON_ERRO);
		}
	}

	
	/**
	 * Efetua a desativação do parâmetro vistoria.<br>
	 * @author Oksana
	 * @since 04/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try{
			ManterParametroVistoriaForm localForm = (ManterParametroVistoriaForm) form;			

			if (!validaForm(mapping, form, request)) {
				return carregarInterfaceIncluirAlterar(mapping, form, request, response);
			}
			ParametroVistoria parametroVistoria = OperacaoFacade.obterParametroVistoria(Integer.valueOf(localForm.getCodParametroVistoria()));
			OperacaoFacade.excluirParametroVistoria(parametroVistoria);
			
			this.addMessage("SUCESSO.62", request);
			return carregarInterfaceInicial(mapping, localForm, request, response);		
		}catch (ApplicationException ae) {
			setActionForward(carregarInterfaceExibir(mapping, form, request, response));
			throw ae;
		}catch (Exception e) {
			setActionForward(carregarInterfaceExibir(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterParametroVistoriaAction.class.getSimpleName()+".desativar()"}, e, ApplicationException.ICON_ERRO);
		}
	}

}
