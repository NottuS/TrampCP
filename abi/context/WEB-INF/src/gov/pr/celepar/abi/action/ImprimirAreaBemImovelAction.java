package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.FiltroRelatorioAreaBemImovelDTO;
import gov.pr.celepar.abi.dto.RelatorioBemImovelDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.ImprimirAreaBemImovelForm;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.framework.util.Valores;
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
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 17/03/2010
 * 
 * Classe Action:
 * Responsavel por manipular as requisicoes dos usuarios
 */

public class ImprimirAreaBemImovelAction  extends BaseDispatchAction {

	/**
	 * Carrega pagina para filtro dos campos do relatorio de Area de  Bem Imovel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditImprimirAreaBemImovel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
			
		ImprimirAreaBemImovelForm imprimirForm = (ImprimirAreaBemImovelForm) form;
		setActionForward(mapping.findForward("pgEditImprimirAreaBemImovel"));
		
		//tratamento operador de orgao
		Long codUsuarioSentinela = SentinelaComunicacao.getInstance(request).getCodUsuario();
		if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())){
			request.setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosCombo()));
			request.setAttribute("listaInstituicao", Util.htmlEncodeCollection( CadastroFacade.listarInstituicao()));
		}
		else{
			imprimirForm.setCodInstituicao(CadastroFacade.obterInstituicaoUsuario(codUsuarioSentinela).getCodInstituicao().toString());
		
			if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo())){
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
				if (usuario == null){
					throw new ApplicationException("AVISO.97");
				}
				if (usuario.getListaUsuarioOrgao() == null || usuario.getListaUsuarioOrgao().size() < 1){
					setActionForward(mapping.findForward("pgErroImpAreaBI"));
					throw new ApplicationException("AVISO.96");
				}
				imprimirForm.setIndOperadorOrgao(true);
			}else{
				imprimirForm.setIndOperadorOrgao(false);
			}
		}
		//
		
		imprimirForm.setRadTerreno("1");
		imprimirForm.setRadRelatorio("1");
	
		return getActionForward();
	}
	
	public ActionForward gerarRelatorio(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		ImprimirAreaBemImovelForm imprimirForm = (ImprimirAreaBemImovelForm)form;
		String path = request.getSession().getServletContext().getRealPath("");
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			setActionForward(carregarPgEditImprimirAreaBemImovel(mapping, imprimirForm, request, response));
			return getActionForward();
		}	
		
		
		try {
			
			 Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
			
			
		    FiltroRelatorioAreaBemImovelDTO rbiDTO = new FiltroRelatorioAreaBemImovelDTO();
		    
		   

			if (StringUtil.stringNotNull(imprimirForm.getRadTerreno()))  {
				rbiDTO.setFiltroTerreno(imprimirForm.getRadTerreno());
			}
			if (StringUtil.stringNotNull(imprimirForm.getUf())) {
				rbiDTO.setUf(imprimirForm.getUf());
			}
			if (StringUtil.stringNotNull(imprimirForm.getCodMunicipio())){
				rbiDTO.setCodMunicipio(imprimirForm.getCodMunicipio());
			}
			if (imprimirForm.getCodMunicipio()!=null && !imprimirForm.getCodMunicipio().equals("0") &&  StringUtil.stringNotNull(imprimirForm.getMunicipio())) {
				rbiDTO.setFiltroMunicipio(imprimirForm.getMunicipio());
			}
			if (StringUtil.stringNotNull(imprimirForm.getRadRelatorio())) {
				rbiDTO.setFiltroRelatorio(imprimirForm.getRadRelatorio());
			}
			if (StringUtil.stringNotNull(imprimirForm.getAreaDe())) {
				rbiDTO.setFiltroAreaDe(Valores.converterStringParaBigDecimal(imprimirForm.getAreaDe()));
			}
			if (StringUtil.stringNotNull(imprimirForm.getAreaAte())) {
				rbiDTO.setFiltroAreaAte(Valores.converterStringParaBigDecimal(imprimirForm.getAreaAte()));
			}
			if (StringUtil.stringNotNull(imprimirForm.getRadAdministracao())) {
				rbiDTO.setFiltroAdministracao(imprimirForm.getRadAdministracao());
			}else{
				rbiDTO.setFiltroAdministracao("");
			}
			rbiDTO.setUsuario(sentinelaInterface.getNome());
	
			//Tratamento de operador de orgao
			if (imprimirForm.getIndOperadorOrgao()){
				rbiDTO.setUsuarioS(usuario);
			}else{
				rbiDTO.setUsuarioS(null);
			}
			//
			
			//tratamento instituição
			if(CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())){
				if (StringUtil.stringNotNull(imprimirForm.getCodInstituicao())){
					rbiDTO.setInstituicao(CadastroFacade.obterInstituicao(Integer.valueOf(imprimirForm.getCodInstituicao())));
				}else{
					rbiDTO.setInstituicao(null);
				}
			}else{
				rbiDTO.setInstituicao(usuario.getInstituicao());
			}
			
			rbiDTO.setListaBensImoveis((List<RelatorioBemImovelDTO>)CadastroFacade.listarRelatorioAreaBensImoveis(rbiDTO));
			
			if (rbiDTO.getListaBensImoveis() == null || rbiDTO.getListaBensImoveis().size() == 0){	
				throw new ApplicationException("AVISO.11");
			}
		
			List <FiltroRelatorioAreaBemImovelDTO> listaRelatorio = new ArrayList<FiltroRelatorioAreaBemImovelDTO>();
			
			listaRelatorio.add(rbiDTO);
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nomeRelatorioJasper", "AreaBemImovel.jasper");
			parametros.put("tituloRelatorio", "Relatório de Bens Imóveis por Área de Terreno ");
			parametros.put("pathCabecalho", Dominios.PATH_RELATORIO + "CabecalhoPadraoRetrato.jasper");
			parametros.put("pathSubRelatorioBensImoveis", Dominios.PATH_RELATORIO +"SubRelatorioAreaBensImoveis.jasper");
			parametros.put("pathSubRelatorioGerencial", Dominios.PATH_RELATORIO +"SubRelatorioGerencialBemImovel.jasper");
			
			String image1 =null;
			if (rbiDTO.getInstituicao()!= null){
				image1 = Dominios.PATH_LOGO.concat(File.separator).concat(rbiDTO.getInstituicao().getCodInstituicao().toString()).concat(rbiDTO.getInstituicao().getLogoInstituicao());
				parametros.put("descricaoInstituicao", rbiDTO.getInstituicao().getDescricaoRelatorio());
			}else{
				image1 = path + File.separator + "images" + File.separator + "logo_parana.png";
				parametros.put("descricaoInstituicao", "Geral");
			}
		    String image2 = path + File.separator + "images" + File.separator + "logo_sistema_relatorio.jpg";
			
			parametros.put("image1", image1);
			parametros.put("image2", image2); 	 

			RelatorioIReportAction.processarRelatorio(listaRelatorio, parametros, form, request, mapping, response);
			
			return getActionForward();		
		} catch (ApplicationException appEx) {
			setActionForward(carregarPgEditImprimirAreaBemImovel(mapping, imprimirForm, request, response));
			throw appEx;
		} catch (Exception e) {
			setActionForward(carregarPgEditImprimirAreaBemImovel(mapping, imprimirForm, request, response));
			throw new ApplicationException("ERRO.201", new String[]{"na geração do relatório de Bem Imóvel por Área"}, e);
		}		
		
	}
}
