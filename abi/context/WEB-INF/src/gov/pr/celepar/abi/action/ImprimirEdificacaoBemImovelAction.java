package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.FiltroRelatorioEdificacaoDTO;
import gov.pr.celepar.abi.dto.RelatorioEdificacaoDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.ImprimirEdificacaoBemImovelForm;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Cláudio
 * @version 1.0
 * @since 23/03/2010
 * 
 * Classe Action:
 * Responsavel por manipular as requisicoes dos usuarios
 */

public class ImprimirEdificacaoBemImovelAction  extends BaseDispatchAction {

	/**
	 * Carrega pagina para filtro dos campos do relatorio de Bem Imovel com Edificacao.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditImprimirEdificacaoBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		ImprimirEdificacaoBemImovelForm imprimirEdificacaoBemImovelForm = (ImprimirEdificacaoBemImovelForm)form;
		setActionForward(mapping.findForward("pgEditImprimirEdificacaoBemImovel"));
		request.setAttribute("tipoConstrucaos", Util.htmlEncodeCollection(CadastroFacade.listarTipoConstrucaos()));
		request.setAttribute("situacaoOcupacaos", Util.htmlEncodeCollection(CadastroFacade.listarSituacaoOcupacaos()));
		request.setAttribute("listaTipoEdificacaoSelecionada", null);
		request.setAttribute("listaTipoEdificacaoDisponivel", Util.htmlEncodeCollection(CadastroFacade.listarTipoEdificacaos()));
		
		//tratamento operador de orgao
		Long codUsuarioSentinela = SentinelaComunicacao.getInstance(request).getCodUsuario();

		if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())){
			request.setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosCombo()));
			request.setAttribute("listaInstituicao", Util.htmlEncodeCollection( CadastroFacade.listarInstituicao()));
		}
		else{
			
			imprimirEdificacaoBemImovelForm.setCodInstituicao(CadastroFacade.obterInstituicaoUsuario(codUsuarioSentinela).getCodInstituicao().toString());
			
			if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo())){	
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
				if (usuario == null){
					throw new ApplicationException("AVISO.97");
				}
				if (usuario.getListaUsuarioOrgao() == null || usuario.getListaUsuarioOrgao().size() < 1){
					setActionForward(mapping.findForward("pgErroImpEdifBI"));
					throw new ApplicationException("AVISO.96");
				}
				request.setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosUsuarioLogadoCombo(usuario, request)));
				imprimirEdificacaoBemImovelForm.setIndOperadorOrgao(true);
				
			}else{
				request.setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(null, codUsuarioSentinela)));
				imprimirEdificacaoBemImovelForm.setIndOperadorOrgao(false);
			}
		}
		
		
		return getActionForward();
	}
	
	public ActionForward gerarRelatorio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		ImprimirEdificacaoBemImovelForm imprimirEdificacaoBemImovelForm = (ImprimirEdificacaoBemImovelForm)form;
		String path = request.getSession().getServletContext().getRealPath("");

		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			setActionForward(carregarPgEditImprimirEdificacaoBemImovel(mapping, imprimirEdificacaoBemImovelForm, request, response));
			return getActionForward();
		}	
		try {
			 Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
			
			
		    FiltroRelatorioEdificacaoDTO reDTO = new FiltroRelatorioEdificacaoDTO();
		    
			if(StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getUf())) {
				reDTO.setUf(imprimirEdificacaoBemImovelForm.getUf());
			}
			if(StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getCodMunicipio())) {
				reDTO.setCodMunicipio(imprimirEdificacaoBemImovelForm.getCodMunicipio());
			}
			if(imprimirEdificacaoBemImovelForm.getCodMunicipio() != null && !imprimirEdificacaoBemImovelForm.getCodMunicipio().equals("0") && StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getMunicipioDescricao())) {
				reDTO.setFiltroMunicipio(imprimirEdificacaoBemImovelForm.getMunicipioDescricao());
			}
			if(StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getCodTipoConstrucao())) {
				reDTO.setCodTipoConstrucao(imprimirEdificacaoBemImovelForm.getCodTipoConstrucao());
			}
			if(StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getCodTipoConstrucao()) && StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getTipoConstrucaoDescricao())) {
				reDTO.setFiltroTipoConstrucao(imprimirEdificacaoBemImovelForm.getTipoConstrucaoDescricao());
			}
			if(StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getRadIncluirOcupacoes())) {
				reDTO.setIncluirOcupacoes(imprimirEdificacaoBemImovelForm.getRadIncluirOcupacoes());
			}
			if(StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getCodOrgao())) {
				reDTO.setCodOrgao(imprimirEdificacaoBemImovelForm.getCodOrgao());
			}
			if(StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getCodOrgao()) && StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getOrgaoSiglaDescricao())) {
				reDTO.setFiltroOrgaoSiglaDescricao(imprimirEdificacaoBemImovelForm.getOrgaoSiglaDescricao());
			}
			if(StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getCodSituacaoOcupacao())) {
				reDTO.setCodSituacaoOcupacao(imprimirEdificacaoBemImovelForm.getCodSituacaoOcupacao());
			}
			if(StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getCodSituacaoOcupacao()) && StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getSituacaoOcupacaoDescricao()))  {
				reDTO.setFiltroSituacaoOcupacao(imprimirEdificacaoBemImovelForm.getSituacaoOcupacaoDescricao());
			}
			if(StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getRadAverbacao())) {
				reDTO.setFiltroAverbacao(imprimirEdificacaoBemImovelForm.getRadAverbacao());
			}
			if(StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getRadRelatorio())) {
				reDTO.setFiltroRelatorio(imprimirEdificacaoBemImovelForm.getRadRelatorio());
			}
			if(StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getDescricaoOcupacao())) {
				reDTO.setDescricaoOcupacao(imprimirEdificacaoBemImovelForm.getDescricaoOcupacao());
			}
			if (StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getRadAdministracao())) {
				reDTO.setFiltroAdministracao(imprimirEdificacaoBemImovelForm.getRadAdministracao());
			}else{
				reDTO.setFiltroAdministracao("");
			}

			List<Integer> listaCodEdificacao = new ArrayList<Integer>();
			String filtroTipoEdificacao = "";
			if (imprimirEdificacaoBemImovelForm.getListaTipoEdificacaoSelecionada() != null){
				for (Integer i = 0; (i < imprimirEdificacaoBemImovelForm.getListaTipoEdificacaoSelecionada().length);i++){
					Integer codigo = Integer.valueOf(imprimirEdificacaoBemImovelForm.getListaTipoEdificacaoSelecionada()[i]);
					listaCodEdificacao.add(codigo);
					filtroTipoEdificacao = filtroTipoEdificacao.concat(CadastroFacade.obterTipoEdificacao(codigo).getDescricao()).concat(", ");
				}					
			}
			reDTO.setListaCodTipoEdificacao(listaCodEdificacao);
			if (filtroTipoEdificacao != null && filtroTipoEdificacao.trim().length() > 0) {
				filtroTipoEdificacao = filtroTipoEdificacao.substring(0, filtroTipoEdificacao.length()-2);
			}
			reDTO.setFiltroTipoEdificacao(filtroTipoEdificacao);

			String filtroAreaEdificacao = "";
			if (imprimirEdificacaoBemImovelForm.getAreaMin() != null && imprimirEdificacaoBemImovelForm.getAreaMin().trim().length() > 0) {
				String area = Util.converteDecimal(imprimirEdificacaoBemImovelForm.getAreaMin()).toString();
				filtroAreaEdificacao = filtroAreaEdificacao.concat(area);
				reDTO.setAreaMin(Float.parseFloat(area));
			}
			if (imprimirEdificacaoBemImovelForm.getAreaMax() != null && imprimirEdificacaoBemImovelForm.getAreaMax().trim().length() > 0) {
				String area = Util.converteDecimal(imprimirEdificacaoBemImovelForm.getAreaMax()).toString();
				if (filtroTipoEdificacao != null && filtroTipoEdificacao.trim().length() > 0) {
					filtroAreaEdificacao = filtroAreaEdificacao.concat(" até ");
				}
				filtroAreaEdificacao = filtroAreaEdificacao.concat(area);
				reDTO.setAreaMax(Float.parseFloat(area));
			}
			reDTO.setFiltroAreaEdificacao(filtroAreaEdificacao);

			reDTO.setUsuario(sentinelaInterface.getNome());
			
			//Tratamento de operador de orgao
			if (imprimirEdificacaoBemImovelForm.getIndOperadorOrgao()){
				reDTO.setUsuarioS(usuario);
			}else{
				reDTO.setUsuarioS(null);
			}
	
			//tratamento instituição
			if(CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())){
				if (StringUtil.stringNotNull(imprimirEdificacaoBemImovelForm.getCodInstituicao())){
					reDTO.setInstituicao(CadastroFacade.obterInstituicao(Integer.valueOf(imprimirEdificacaoBemImovelForm.getCodInstituicao())));
				}else{
					reDTO.setInstituicao(null);
				}
			}else{
				reDTO.setInstituicao(usuario.getInstituicao());
			}
			
			reDTO.setListaBensImoveis((List<RelatorioEdificacaoDTO>) CadastroFacade.listarRelatorioEdificacaoBensImoveis(reDTO));
			
			if(reDTO.getListaBensImoveis() == null || reDTO.getListaBensImoveis().size() == 0) {
				throw new ApplicationException("AVISO.11");
			}
		
			List <FiltroRelatorioEdificacaoDTO> listaRelatorio = new ArrayList<FiltroRelatorioEdificacaoDTO>();
			
			listaRelatorio.add(reDTO);
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nomeRelatorioJasper", "EdificacaoBemImovel.jasper");
			parametros.put("tituloRelatorio", "Relatório de Bem Imóvel com Edificação");
			parametros.put("pathCabecalho", Dominios.PATH_RELATORIO + "CabecalhoPadraoPaisagem.jasper");
			parametros.put("pathSubRelatorio", Dominios.PATH_RELATORIO +"SubRelatorioEdificacaoBensImoveis.jasper");
			parametros.put("pathSubRelatorioOcupacao", Dominios.PATH_RELATORIO +"SubRelatorioEdificacaoBensImoveisOcupacoes.jasper");
			parametros.put("pathSubRelatorioGerencial", Dominios.PATH_RELATORIO +"SubRelatorioGerencialBemImovel.jasper");
			
			String image1 =null;
			if (reDTO.getInstituicao()!= null){
				image1 = Dominios.PATH_LOGO.concat(File.separator).concat(reDTO.getInstituicao().getCodInstituicao().toString()).concat(reDTO.getInstituicao().getLogoInstituicao());
				parametros.put("descricaoInstituicao", reDTO.getInstituicao().getDescricaoRelatorio());
			}else{
				image1 = path + File.separator + "images" + File.separator + "logo_parana.png";
				parametros.put("descricaoInstituicao", "Geral");
			}

		    String image2 = path + File.separator + "images" + File.separator + "logo_sistema_relatorio.jpg";
			
			parametros.put("image1", image1);
			parametros.put("image2", image2); 	
			parametros.put("comOcupacao", reDTO.getIncluirOcupacoes().equals("1")? new Boolean(true) : new Boolean(false)); 	

			RelatorioIReportAction.processarRelatorio(listaRelatorio, parametros, form, request, mapping, response);
			
			return getActionForward();		
		}
		catch(ApplicationException appEx) {
			setActionForward(carregarPgEditImprimirEdificacaoBemImovel(mapping, imprimirEdificacaoBemImovelForm, request, response));
			throw appEx;
		}
		catch(Exception e) {
			setActionForward(carregarPgEditImprimirEdificacaoBemImovel(mapping,form,request, response));
			throw new ApplicationException("ERRO.201", new String[]{"na geração do relatório de Bem Imóvel com Edificação"}, e);
		}		
		
	}
}
