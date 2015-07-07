package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.FiltroRelatorioBemImovelDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.ImprimirBemImovelForm;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Edificacao;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.Ocupacao;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.pojo.UsuarioOrgao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.mensagem.Mensagem;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 12/03/2010
 * 
 * Classe Action:
 * Responsavel por manipular as requisicoes dos usuarios
 */

public class ImprimirBemImovelAction extends BaseDispatchAction {

	/**
	 * Carrega pagina inicial do caso de uso.<br>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward carregarPgEditImprimirBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgEditImprimirBemImovel"));
		try {
			ImprimirBemImovelForm imprimirBemImovelForm = (ImprimirBemImovelForm)form;
			imprimirBemImovelForm.setRadTerreno("1");
			imprimirBemImovelForm.setRadRelatorio("1");
			imprimirBemImovelForm.setRadAdministracao("");
			imprimirBemImovelForm.setRadAdministracaoOrgao("");
			imprimirBemImovelForm.setTipoMunicipio("1");
			// Testar se o usuário logado possui o grupo Sentinela "OPEORG - ABI", 
			if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo())){
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
				if (usuario == null){
					throw new ApplicationException("AVISO.97");
				}
				if (usuario.getListaUsuarioOrgao() == null || usuario.getListaUsuarioOrgao().size() < 1){
					throw new ApplicationException("AVISO.96");
				}
				for (UsuarioOrgao orgao: usuario.getListaUsuarioOrgao()){
					usuario.getListaUsuarioOrgao().add(orgao);
				}
				imprimirBemImovelForm.setIndOperadorOrgao(1);
			}else{
				imprimirBemImovelForm.setIndOperadorOrgao(2);
			}
			//
		} catch (Exception e) {
			if (e.getMessage().equalsIgnoreCase(Mensagem.getInstance().getMessage("AVISO.96"))){
				setActionForward(mapping.findForward("pgErroImpBI"));
			}
			throw new ApplicationException("mensagem.erro.9001", e, ApplicationException.ICON_ERRO);
		} 
		carregarPgEditCampos(mapping, form, request, response);
		return getActionForward();
	}
	
	/**
	 * Carrega pagina para filtro dos campos do relatorio de Bem Imovel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public void carregarPgEditCampos(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			ImprimirBemImovelForm imprimirBemImovelForm = (ImprimirBemImovelForm)form;
			request.setAttribute("classificacaoBemImovels", Util.htmlEncodeCollection(CadastroFacade.listarClassificacaoBemImovels()));
			request.setAttribute("situacaoImovels", Util.htmlEncodeCollection(CadastroFacade.listarSituacaoImovels()));
			request.setAttribute("situacaoLegalCartorials", Util.htmlEncodeCollection(CadastroFacade.listarSituacaoLegalCartorials()));
			imprimirBemImovelForm.setTipoMunicipio(imprimirBemImovelForm.getTipoMunicipio());
			
			Long codUsuarioSentinela = SentinelaComunicacao.getInstance(request).getCodUsuario();
			Collection<Instituicao> listaInstituicao;
			if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())){
				listaInstituicao = CadastroFacade.listarInstituicao();

				request.setAttribute("listaInstituicao", Util.htmlEncodeCollection(listaInstituicao));
			}else{
				imprimirBemImovelForm.setCodInstituicao(CadastroFacade.obterInstituicaoUsuario(codUsuarioSentinela).getCodInstituicao().toString());
			}
			
			
			this.carregarComboOrgaoSemAjax(mapping, form, request, response);
			
		} catch (ApplicationException ae) {
			throw ae; 
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", e, ApplicationException.ICON_ERRO);
		}	
	}
	
	/**
	 * Carrega o combobox de orgaos de acordo com a Administracao selecionada via Ajax.<br>
	 * @since 24/05/2011
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward carregarComboOrgao (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboOrgao processando...");
		try {
			setActionForward(mapping.findForward("comboOrgaoAjaxImprimirBemImovel"));
			ImprimirBemImovelForm imprimirBemImovelForm = (ImprimirBemImovelForm)form;
			
			Integer param = (StringUtils.isNotBlank(imprimirBemImovelForm.getRadAdministracaoOrgao()))? Integer.valueOf(imprimirBemImovelForm.getRadAdministracaoOrgao()):null;
			if (param != null){
				if (Dominios.administracaoImovel.getAdministracaoImovelByIndex(param) != null) {
					// Testar se o usuário logado possui o grupo Sentinela "ADM - ABI", 
					Long codUsuarioSentinela = SentinelaComunicacao.getInstance(request).getCodUsuario();
					if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())){
						if (StringUtil.stringNotNull(imprimirBemImovelForm.getCodInstituicao())){
							request.setAttribute("listaOrgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(param, Integer.valueOf(imprimirBemImovelForm.getCodInstituicao()))));
						}else{
							request.setAttribute("listaOrgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboByTipoAdm(param)));
						}
					}
					else{
						//listar orgão vinculados a instituição do usuário
						request.setAttribute("listaOrgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(param, codUsuarioSentinela)));
					}
				} else {
					request.setAttribute("listaOrgaos", null);
				}
			} else {
				request.setAttribute("listaOrgaos", null);
			}
					
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			log.error(appEx);
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{"ImprimirBemImovelAction.carregarComboOrgao"}, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Carrega o combobox de orgaos de acordo com a Administracao selecionada.<br>
	 * @since 24/05/2011
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public void carregarComboOrgaoSemAjax (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboOrgao processando...");
		try {
			ImprimirBemImovelForm imprimirBemImovelForm = (ImprimirBemImovelForm)form;
			
			Integer param = (StringUtils.isNotBlank(imprimirBemImovelForm.getRadAdministracaoOrgao()))? Integer.valueOf(imprimirBemImovelForm.getRadAdministracaoOrgao()):null;
			if (param != null){
				if (Dominios.administracaoImovel.getAdministracaoImovelByIndex(param) != null) {
					request.setAttribute("listaOrgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboByTipoAdm(param)));
				} else {
					request.setAttribute("listaOrgaos", null);
				}
			} else {
				request.setAttribute("listaOrgaos", null);
			}
					
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{"ImprimirBemImovelAction.carregarComboOrgaoSemAjax"}, e, ApplicationException.ICON_ERRO);
		}
	}

	public ActionForward gerarRelatorio(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		ImprimirBemImovelForm imprimirBemImovelForm = (ImprimirBemImovelForm)form;
		setActionForward(mapping.findForward("processaRelatorioBemImovel"));

		try {
			
			// Aciona a validação do Form
			if(!validaForm(mapping,form,request)) {
			//	setActionForward(carregarPgEditImprimirBemImovel(mapping, imprimirBemImovelForm, request, response));
				return getActionForward();
			}	
			
			if (!imprimirBemImovelForm.getRadRelatorio().equals("3") && 
				((Util.strEmBranco(imprimirBemImovelForm.getCodMunicipio()) || imprimirBemImovelForm.getCodMunicipio().equals("0")) && 
				(Util.strEmBranco(imprimirBemImovelForm.getCodFaixaMunicipio()) || imprimirBemImovelForm.getCodFaixaMunicipio().equals("0")))){
				throw new ApplicationException("mensagem.erro.2", ApplicationException.ICON_AVISO);
			}
			FiltroRelatorioBemImovelDTO rbiDTO = new FiltroRelatorioBemImovelDTO();

			if (StringUtil.stringNotNull(  imprimirBemImovelForm.getCodSituacaoImovel() )) {
				rbiDTO.setCodSituacao(imprimirBemImovelForm.getCodSituacaoImovel());
			}
			if (StringUtil.stringNotNull(imprimirBemImovelForm.getCodSituacaoImovel()) && StringUtil.stringNotNull(imprimirBemImovelForm.getSituacaoImovel()))  {
				rbiDTO.setFiltroSituacao( imprimirBemImovelForm.getSituacaoImovel());
			}
			if (StringUtil.stringNotNull(  imprimirBemImovelForm.getCodSituacaoLegalCartorial() )) {
				rbiDTO.setCodSituacaoLegalCartorial(imprimirBemImovelForm.getCodSituacaoLegalCartorial());
			}
			if (StringUtil.stringNotNull(  imprimirBemImovelForm.getCodSituacaoLegalCartorial() ) && StringUtil.stringNotNull(imprimirBemImovelForm.getSituacaoLegalCartorial()) ) {
				rbiDTO.setFiltroSituacaoLegalCartorial(imprimirBemImovelForm.getSituacaoLegalCartorial());
			}
			if (StringUtil.stringNotNull(  imprimirBemImovelForm.getCodClassificacaoBemImovel() )) {
				rbiDTO.setCodClassificacao(imprimirBemImovelForm.getCodClassificacaoBemImovel());
			}
			if (StringUtil.stringNotNull(  imprimirBemImovelForm.getCodClassificacaoBemImovel() ) && StringUtil.stringNotNull(imprimirBemImovelForm.getClassificacaoBemImovel() )) {
				rbiDTO.setFiltroClassificacao(imprimirBemImovelForm.getClassificacaoBemImovel());
			}
			if (StringUtil.stringNotNull(imprimirBemImovelForm.getRadTerreno()))  {
				rbiDTO.setFiltroTerreno(imprimirBemImovelForm.getRadTerreno());
			}
			if (StringUtil.stringNotNull(imprimirBemImovelForm.getUf())) {
				rbiDTO.setUf(imprimirBemImovelForm.getUf());
			}
			if (StringUtil.stringNotNull(imprimirBemImovelForm.getCodMunicipio()) && !imprimirBemImovelForm.getCodMunicipio().equals("0")){
				rbiDTO.setCodMunicipio(imprimirBemImovelForm.getCodMunicipio());
				rbiDTO.setFiltroMunicipio(imprimirBemImovelForm.getMunicipio());
			} 
			if (StringUtil.stringNotNull(imprimirBemImovelForm.getCodFaixaMunicipio()) && !imprimirBemImovelForm.getCodFaixaMunicipio().equals("0")){
				rbiDTO.setCodMunicipio(imprimirBemImovelForm.getCodFaixaMunicipio());
				rbiDTO.setFiltroMunicipio(Dominios.faixasPesquisasMunicipios.getFaixasPesquisasMunicipiosByIndex(Integer.valueOf(imprimirBemImovelForm.getCodFaixaMunicipio())).getLabel());
			}
			if (StringUtil.stringNotNull(imprimirBemImovelForm.getRadRelatorio())) {
				rbiDTO.setFiltroRelatorio(imprimirBemImovelForm.getRadRelatorio());
			}
			if (StringUtil.stringNotNull(imprimirBemImovelForm.getRadAdministracao())) {
				rbiDTO.setFiltroAdministracao(imprimirBemImovelForm.getRadAdministracao());
			}else{
				rbiDTO.setFiltroAdministracao("");
			}
			if(!Util.strEmBranco(imprimirBemImovelForm.getConOcupacao())) {
				rbiDTO.setOrgaoOcupante(imprimirBemImovelForm.getConOcupacao());
			}
			if(!Util.strEmBranco(imprimirBemImovelForm.getOrgao())) {
				rbiDTO.setOrgao(imprimirBemImovelForm.getOrgao());
			}
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			rbiDTO.setUsuario(sentinelaInterface.getNome());
			
			List<BemImovel> listaBens = new ArrayList<BemImovel>();

			Map<String, Object> parametros = new HashMap<String, Object>();

			//Usuário orgao
			Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
			rbiDTO.setUsuarioS(usuario);
			rbiDTO.setIndOperadorOrgao(imprimirBemImovelForm.getIndOperadorOrgao());
			rbiDTO.setAdmGeral(CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo()));
			//
			if (StringUtil.stringNotNull(imprimirBemImovelForm.getCodInstituicao())){
				rbiDTO.setCodInstituicao(imprimirBemImovelForm.getCodInstituicao());
			}
			
			if (!imprimirBemImovelForm.getRadRelatorio().equals("3")){
				listaBens = (List<BemImovel>)CadastroFacade.listarRelatorioBensImoveis(rbiDTO);
			} else {
				listaBens = (List<BemImovel>)CadastroFacade.listarRelatorioBensImoveis(rbiDTO);
			}
			//Atualização especificação de ocupação
			for (BemImovel b: listaBens){
				for (Edificacao e: b.getEdificacaos()){
					if (e.getEspecificacao() == null || e.getEspecificacao().isEmpty()){
						String orgao = "";
						for (Ocupacao o : e.getOcupacaos()){
							if (o.getOrgao() != null){
								orgao = orgao.concat(o.getOrgao().getSigla().concat(" "));	
							}
						}
						e.setEspecificacao(orgao);
					}
				}
			}
						
			rbiDTO.setListaBensImoveis(listaBens);
			List<FiltroRelatorioBemImovelDTO> listaRelatorio = new ArrayList<FiltroRelatorioBemImovelDTO>();
			listaRelatorio.add(rbiDTO);

			if (listaBens == null || listaBens.size() < 1){
				throw new ApplicationException("AVISO.2", ApplicationException.ICON_AVISO);
			}
			
			String path = request.getSession().getServletContext().getRealPath("");
			String image1 =null;
			if (usuario.getInstituicao()!= null){
				image1 = Dominios.PATH_LOGO.concat(File.separator).concat(usuario.getInstituicao().getCodInstituicao().toString()).concat(usuario.getInstituicao().getLogoInstituicao());
				parametros.put("descricaoInstituicao", usuario.getInstituicao().getDescricaoRelatorio());
			}else{
				image1 = path + File.separator + "images" + File.separator + "logo_parana.png";
				parametros.put("descricaoInstituicao", "Geral");
			}

			String image2 = path + File.separator + "images" + File.separator + "logo_sistema_relatorio.jpg";
			
			parametros.put("nomeRelatorioJasper", "BemImovel.jasper");
			parametros.put("tituloRelatorio", "Relatório de Bens Imóveis");
			parametros.put("pathCabecalho", Dominios.PATH_RELATORIO + "CabecalhoPadraoRetrato.jasper");
			parametros.put("pathSubRelatorioBensImoveis", Dominios.PATH_RELATORIO +"ImpressaoBensImoveis.jasper");
			parametros.put("pathSubRelatorioGerencial", Dominios.PATH_RELATORIO +"SubRelatorioGerencialBemImovel.jasper");
			parametros.put("image1", image1);
			parametros.put("image2", image2); 
			
			parametros.put("pathSubRelatorioLeis", Dominios.PATH_RELATORIO +"SubRelatorioLeisB.jasper");
			parametros.put("pathSubRelatorioQuadras", Dominios.PATH_RELATORIO +"SubRelatorioQuadras.jasper");
			parametros.put("pathSubRelatorioConfrontantes", Dominios.PATH_RELATORIO +"SubRelatorioConfrontantes.jasper");				
			parametros.put("pathSubRelatorioAvaliacoes", Dominios.PATH_RELATORIO +"SubRelatorioAvaliacoes.jasper");
			parametros.put("pathSubRelatorioCoordenadasUtm", Dominios.PATH_RELATORIO +"SubRelatorioCoordenadasUtm.jasper");
			parametros.put("pathSubRelatorioEdificacoes", Dominios.PATH_RELATORIO +"SubRelatorioImpressaoEdificacaoBensImoveis.jasper");
			parametros.put("pathSubRelatorioOcupacao", Dominios.PATH_RELATORIO +"SubRelatorioImpressaoEdificacaoBensImoveisOcupacoes.jasper");				
			parametros.put("pathSubRelatorioDocumentacaoNotificacao", Dominios.PATH_RELATORIO +"SubRelatorioDocumentacaoNotificacao.jasper");
			parametros.put("pathSubRelatorioDocumentacaoSemNotificacao", Dominios.PATH_RELATORIO +"SubRelatorioDocumentacaoSemNotificacao.jasper");
			parametros.put("pathSubRelatorioOcorrencias", Dominios.PATH_RELATORIO +"SubRelatorioOcorrencias.jasper");
			parametros.put("pathSubRelatorioOcupacaoTerreno", Dominios.PATH_RELATORIO +"SubRelatorioOcupacaoTerreno.jasper");

			RelatorioIReportAction.processarRelatorioRetorno(listaRelatorio, parametros, form, request, mapping, response);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"na geração do relatório de Bem Imóvel"}, e);
		}		
		return getActionForward();

	}
	
	public ActionForward carregarArquivo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarArquivo processando...");
		RelatorioIReportAction.imprimirRelatorio(mapping, form, request, response);
		return null;
	}

}
