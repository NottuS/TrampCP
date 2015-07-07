 package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.ImpressaoBemImovelDTO;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.ImpressaoBemImovelForm;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Data;
import gov.pr.celepar.framework.util.Enderecamento;
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

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Pialarissi
 * @version 1.0
 * @since 12/03/2010
 * 
 * Classe Action:
 * Responsavel por manipular as requisicoes dos usuarios
 */

public class ImpressaoBemImovelAction extends BaseDispatchAction {

	private static Logger log4j = Logger.getLogger(OcupacaoAction.class);
	/**
	 * Carrega pagina para filtro dos campos do relatorio de Bem Imovel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditImpressaoBemImovel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		ImpressaoBemImovelForm impressaoBemImovelForm = (ImpressaoBemImovelForm)form;
		
		String codBemImovel = "";
		if((request.getParameter("codBemImovel") !=null) || (request.getParameter("codBemImovel") !="")){
			codBemImovel = request.getParameter("codBemImovel") ;
		}

		impressaoBemImovelForm.setCodBemImovel(codBemImovel);
		
		return mapping.findForward("pgConsImpressaoBemImovel");
	}
	
	public ActionForward gerarRelatorio(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		ImpressaoBemImovelForm impressaoBemImovelForm = (ImpressaoBemImovelForm)form;
		String path = request.getSession().getServletContext().getRealPath("");
		
		try {
			ImpressaoBemImovelDTO ibiDTO = new ImpressaoBemImovelDTO();
			
			// Pega código do Bem Imóvel
			Integer codBemImovel = Integer.valueOf (0);
			if (impressaoBemImovelForm.getCodBemImovel()!=null){
				codBemImovel = Integer.parseInt(request.getParameter("codBemImovel"));
			}
			if (StringUtil.stringNotNull(impressaoBemImovelForm.getCodBemImovel())){
				ibiDTO.setCodBemImovel(Integer.parseInt(impressaoBemImovelForm.getCodBemImovel()));
			}

			if (impressaoBemImovelForm.getTipoRelatorio().equals("personalizado")) {

				if (impressaoBemImovelForm.getLeis()!=null) {
					ibiDTO.setFiltroLeis("T");
				}

				if (impressaoBemImovelForm.getQuadras()!=null) {					
					ibiDTO.setFiltroQuadra("T");
				}				

				if (impressaoBemImovelForm.getConfrontante()!=null) {
					ibiDTO.setFiltroConfrontantes("T");
				}
				
				if (impressaoBemImovelForm.getAvaliacao()!=null) {				
					ibiDTO.setFiltroAvaliacoes("T");
				}
				
				if (impressaoBemImovelForm.getCoordenadaUTM()!=null) {					
					ibiDTO.setFiltroCoordenadasUtm("T");
				}
				
				if (impressaoBemImovelForm.getEdificacao()!=null) {					
					ibiDTO.setFiltroEdificacoes("T");
				}
				
				if (impressaoBemImovelForm.getDocumentacaoNotificacao()!=null) {					
					ibiDTO.setFiltroDocumentacoesNotificacao("T");
				}
				
				if (impressaoBemImovelForm.getDocumentacaoSemNotificacao()!=null) {					
					ibiDTO.setFiltroDocumentacoesSemNotificacao("T");
				}
				
				if (impressaoBemImovelForm.getOcorrencia()!=null) {					
					ibiDTO.setFiltroOcorrencias("T");
				}
					
				if (impressaoBemImovelForm.getOcupacaoTerreno()!=null) {					
					ibiDTO.setFiltroOcupacaoTerreno("T");		
				}
			}else {
				ibiDTO.setFiltroLeis("T");
				ibiDTO.setFiltroQuadra("T");
				ibiDTO.setFiltroConfrontantes("T");				
				ibiDTO.setFiltroAvaliacoes("T");
				ibiDTO.setFiltroCoordenadasUtm("T");
				ibiDTO.setFiltroEdificacoes("T");				
				ibiDTO.setFiltroDocumentacoesNotificacao("T");
				ibiDTO.setFiltroDocumentacoesSemNotificacao("T");
				ibiDTO.setFiltroOcorrencias("T");		
				ibiDTO.setFiltroOcupacaoTerreno("T");		
			}

			
			// popula DTO
			BemImovel bemImovel = CadastroFacade.listarRelatorioBemImovel(ibiDTO, Integer.valueOf(codBemImovel));

			// Seta parâmetros dos dados de identificação do imóvel
			ibiDTO.setCodBemImovel(bemImovel.getCodBemImovel());
			ibiDTO.setAdministracaoDireta(bemImovel.getDescricaoAdministracao());
			ibiDTO.setAdministracao(bemImovel.getAdministracao());
			
			if ((bemImovel.getOrgao() != null) && (StringUtil.stringNotNull(bemImovel.getOrgao().getSigla()))  ) {
				ibiDTO.setOrgao(bemImovel.getOrgao().getSigla().concat(" - ").concat(bemImovel.getOrgao().getDescricao()));
			}			
			if (bemImovel.getClassificacaoBemImovel() != null && StringUtil.stringNotNull(bemImovel.getClassificacaoBemImovel().getDescricao())) {
				ibiDTO.setClassificacaoBemImovel(bemImovel.getClassificacaoBemImovel().getDescricao());
			}else{
				ibiDTO.setClassificacaoBemImovel("");
			}
						
			if ((bemImovel.getSituacaoLocal() !=null)&&(bemImovel.getSituacaoLocal().intValue()==1)) {
				ibiDTO.setSituacaoLocal("Localizado");
			}else{
				ibiDTO.setSituacaoLocal("Não Localizado");
			}
			if ((bemImovel.getSituacaoLegalCartorial() != null) && (StringUtil.stringNotNull(bemImovel.getSituacaoLegalCartorial().getDescricao()))  ) {
				ibiDTO.setSituacaoLegalCartorial(bemImovel.getSituacaoLegalCartorial().getDescricao());
			}else{
				ibiDTO.setSituacaoLegalCartorial("");
			}
			if (bemImovel.getNumeroProcessoSpi() != null) {
				ibiDTO.setNumeroProcessoSpi(bemImovel.getNumeroProcessoSpi().toString());
			}else{
				ibiDTO.setNumeroProcessoSpi("");
			}
			if (StringUtil.stringNotNull(bemImovel.getCep())) {
				ibiDTO.setCep(Enderecamento.formataCEP(bemImovel.getCep()));
			}else{
				ibiDTO.setCep("");
			}
			if (StringUtil.stringNotNull(bemImovel.getMunicipio())) {
				ibiDTO.setCodMunicipio(bemImovel.getMunicipio());
			}else{
				ibiDTO.setCodMunicipio("");
			}
			if (StringUtil.stringNotNull(bemImovel.getUf())) {
				ibiDTO.setUf(bemImovel.getUf());
			}else{
				ibiDTO.setUf("");
			}
			if (StringUtil.stringNotNull(bemImovel.getBairroDistrito())) {
				ibiDTO.setBairroDistrito(bemImovel.getBairroDistrito());
			}else{
				ibiDTO.setBairroDistrito("");
			}
	
			if ((bemImovel.getFormaIncorporacao() != null) && (StringUtil.stringNotNull(bemImovel.getFormaIncorporacao().getDescricao()))  ) {
				ibiDTO.setFormaIncorporacao(bemImovel.getFormaIncorporacao().getDescricao());
			}else{
				ibiDTO.setFormaIncorporacao("");
			}
			if (bemImovel.getDataIncorporacao() != null) {
				ibiDTO.setDataIncorporacao(Data.formataData(bemImovel.getDataIncorporacao()));
			}else{
				ibiDTO.setDataIncorporacao("");
			}
			if ((bemImovel.getSituacaoImovel() != null) && (StringUtil.stringNotNull(bemImovel.getSituacaoImovel().getDescricao()))  ) {
				ibiDTO.setSituacaoImovel(bemImovel.getSituacaoImovel().getDescricao());
			}else{
				ibiDTO.setSituacaoImovel("");
			}
			if ((StringUtil.stringNotNull(bemImovel.getLogradouro()))) {
				ibiDTO.setLogradouro(bemImovel.getLogradouro());
			}else{
				ibiDTO.setLogradouro("");
			}
			if ((StringUtil.stringNotNull(bemImovel.getNumero()))) {
				ibiDTO.setNumero(bemImovel.getNumero());
			}else{
				ibiDTO.setNumero("");
			}
			if ((StringUtil.stringNotNull(bemImovel.getComplemento()))) {
				ibiDTO.setComplemento(bemImovel.getComplemento());
			}else{
				ibiDTO.setComplemento("");
			}
			if ((bemImovel.getDenominacaoImovel() != null) && (StringUtil.stringNotNull(bemImovel.getDenominacaoImovel().getDescricao()))  ) {
				ibiDTO.setDenominacaoImovel(bemImovel.getDenominacaoImovel().getDescricao());
			}else{
				ibiDTO.setDenominacaoImovel("");
			}
			if (bemImovel.getAreaTerreno() != null) {
				ibiDTO.setAreaTerreno(bemImovel.getAreaTerreno());				
			}			
			if ((bemImovel.getSomenteTerreno() != null) && (StringUtil.stringNotNull(bemImovel.getSomenteTerreno()))  ) {
				ibiDTO.setSomenteTerreno(bemImovel.getSomenteTerreno());
			}else{
				ibiDTO.setSomenteTerreno("");
			}
			if (StringUtil.stringNotNull(bemImovel.getObservacoesMigracao())) {
				ibiDTO.setObservacoesMigracao(bemImovel.getObservacoesMigracao());
			}else
			{
				ibiDTO.setObservacoesMigracao("");
			}

			ibiDTO.setCoordenadaUtms(bemImovel.getCoordenadaUtms());
			ibiDTO.setConfrontantes(bemImovel.getConfrontantes());
			ibiDTO.setAvaliacaos(bemImovel.getAvaliacaos());
			ibiDTO.setEdificacaos(bemImovel.getEdificacaos());
			ibiDTO.setLeiBemImovels(bemImovel.getLeiBemImovels());
			ibiDTO.setOcupacaosTerreno(bemImovel.getOcupacaosTerreno());
			ibiDTO.setListaDocumentacaoNotificacao(bemImovel.getListaDocumentacaoNotificacao());
			ibiDTO.setListaOcorrencias(bemImovel.getListaOcorrencias());
			ibiDTO.setListaDocumentacaoSemNotificacao(bemImovel.getListaDocumentacaoSemNotificacao());
			ibiDTO.setListaQuadrasLotes(bemImovel.getListaQuadrasLotes());
			
			ibiDTO.setUsuario(sentinelaInterface.getNome());
			List <ImpressaoBemImovelDTO> listaRelatorio = new ArrayList<ImpressaoBemImovelDTO>();
			listaRelatorio.add(ibiDTO);
			
			Map<String, Object> parametros = new HashMap<String, Object>();

			String image1 = path + File.separator + "images" + File.separator + "logo_parana.png";
		    String image2 = path + File.separator + "images" + File.separator + "logo_sistema_relatorio.jpg";

			parametros.put("tituloRelatorio", "Bem Imóvel");
			parametros.put("pathCabecalho", Dominios.PATH_RELATORIO + "CabecalhoPadraoRetrato.jasper");
			parametros.put("nomeRelatorioJasper", "ImpressaoBemImovel.jasper");
			parametros.put("pathSubRelatorioBensImoveis", Dominios.PATH_RELATORIO +"SubRelatorioBensImoveis.jasper");			
			parametros.put("image1", image1);
			parametros.put("image2", image2);
			parametros.put("comOcupacao", new Boolean(true));
			

			if (ibiDTO.getFiltroLeis().equalsIgnoreCase("T")) {
				parametros.put("pathSubRelatorioLeis", Dominios.PATH_RELATORIO +"SubRelatorioLeisB.jasper");
			}

			if (ibiDTO.getFiltroQuadra().equalsIgnoreCase("T")) {
				parametros.put("pathSubRelatorioQuadras", Dominios.PATH_RELATORIO +"SubRelatorioQuadras.jasper");
			}				

			if (ibiDTO.getFiltroConfrontantes().equalsIgnoreCase("T")) {
				parametros.put("pathSubRelatorioConfrontantes", Dominios.PATH_RELATORIO +"SubRelatorioConfrontantes.jasper");					
			}
			
			if (ibiDTO.getFiltroAvaliacoes().equalsIgnoreCase("T")) {
				parametros.put("pathSubRelatorioAvaliacoes", Dominios.PATH_RELATORIO +"SubRelatorioAvaliacoes.jasper");
			}
			
			if (ibiDTO.getFiltroCoordenadasUtm().equalsIgnoreCase("T")) {
				parametros.put("pathSubRelatorioCoordenadasUtm", Dominios.PATH_RELATORIO +"SubRelatorioCoordenadasUtm.jasper");
			}
			
			if (ibiDTO.getFiltroEdificacoes().equalsIgnoreCase("T")) {
				parametros.put("pathSubRelatorioEdificacoes", Dominios.PATH_RELATORIO +"SubRelatorioImpressaoEdificacaoBensImoveis.jasper");
				parametros.put("pathSubRelatorioOcupacao", Dominios.PATH_RELATORIO +"SubRelatorioImpressaoEdificacaoBensImoveisOcupacoes.jasper");				
			}
			
			if (ibiDTO.getFiltroDocumentacoesNotificacao().equalsIgnoreCase("T")) {
				parametros.put("pathSubRelatorioDocumentacaoNotificacao", Dominios.PATH_RELATORIO +"SubRelatorioDocumentacaoNotificacao.jasper");
			}
			
			if (ibiDTO.getFiltroDocumentacoesSemNotificacao().equalsIgnoreCase("T")) {
				parametros.put("pathSubRelatorioDocumentacaoSemNotificacao", Dominios.PATH_RELATORIO +"SubRelatorioDocumentacaoSemNotificacao.jasper");
			}
			
			if (ibiDTO.getFiltroOcorrencias().equalsIgnoreCase("T")) {
				parametros.put("pathSubRelatorioOcorrencias", Dominios.PATH_RELATORIO +"SubRelatorioOcorrencias.jasper");				
			}
				
			if (ibiDTO.getFiltroOcupacaoTerreno().equalsIgnoreCase("T")) {
				parametros.put("pathSubRelatorioOcupacaoTerreno", Dominios.PATH_RELATORIO +"SubRelatorioOcupacaoTerreno.jasper");
			}
			
			RelatorioIReportAction.processarRelatorio(listaRelatorio, parametros, form, request, mapping, response);			
			return getActionForward();		
			
		} catch (ApplicationException appEx) {
			log4j.error("ERRO",appEx.getCausaRaiz());			
			throw appEx;
			
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"na geração do relatório de Bem Imóvel"}, e);
		}		
		
	}
}
