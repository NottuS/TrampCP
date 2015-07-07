package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.FiltroRelatorioEdificacaoOcupacaoDTO;
import gov.pr.celepar.abi.dto.RelatorioEdificacaoOcupacaoDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.ImprimirEdificacaoOcupacaoForm;
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
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 23/03/2010
 * 
 * Classe Action:
 * Responsavel por manipular as requisicoes dos usuarios
 */

public class ImprimirEdificacaoOcupacaoAction extends BaseDispatchAction {

	/**
	 * Carrega pagina para filtro dos campos do relatorio de Bem Imovel com Edificacao com Ocupacao.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditImprimirEdificacaoOcupacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		ImprimirEdificacaoOcupacaoForm imprimirEdificacaoOcupacaoForm = (ImprimirEdificacaoOcupacaoForm)form;
		setActionForward(mapping.findForward("pgEditImprimirEdificacaoOcupacao"));
		//tratamento operador de orgao
		Long codUsuarioSentinela = SentinelaComunicacao.getInstance(request).getCodUsuario();
		
		
		if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())){
			request.setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosCombo()));
			request.setAttribute("listaInstituicao", Util.htmlEncodeCollection( CadastroFacade.listarInstituicao()));
		}
		else{
			
			imprimirEdificacaoOcupacaoForm.setCodInstituicao(CadastroFacade.obterInstituicaoUsuario(codUsuarioSentinela).getCodInstituicao().toString());
			if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo())){
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
				if (usuario == null){
					throw new ApplicationException("AVISO.97");
				}
				if (usuario.getListaUsuarioOrgao() == null || usuario.getListaUsuarioOrgao().size() < 1){
					setActionForward(mapping.findForward("pgErroImpOcupBI"));
					throw new ApplicationException("AVISO.96");
				}
				request.setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosUsuarioLogadoCombo(usuario, request)));
				imprimirEdificacaoOcupacaoForm.setIndOperadorOrgao(true);
				
			}else{
				request.setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(null, codUsuarioSentinela)));
				imprimirEdificacaoOcupacaoForm.setIndOperadorOrgao(false);
			}
		}
		
				
		imprimirEdificacaoOcupacaoForm.setRadRelatorio("1");

		return getActionForward();
	}
	
	public ActionForward gerarRelatorio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		ImprimirEdificacaoOcupacaoForm imprimirEdificacaoOcupacaoForm = (ImprimirEdificacaoOcupacaoForm)form;
		String path = request.getSession().getServletContext().getRealPath("");
		
		if(!validaForm(mapping,form,request)) {
			setActionForward(carregarPgEditImprimirEdificacaoOcupacao(mapping, form, request, response));
			return getActionForward();
		}	
		
		try {
			
		    FiltroRelatorioEdificacaoOcupacaoDTO reDTO = new FiltroRelatorioEdificacaoOcupacaoDTO();
			
			if(StringUtil.stringNotNull(imprimirEdificacaoOcupacaoForm.getCodOrgao())) {
				reDTO.setCodOrgao(imprimirEdificacaoOcupacaoForm.getCodOrgao());
			}
			if(StringUtil.stringNotNull(imprimirEdificacaoOcupacaoForm.getCodOrgao()) && StringUtil.stringNotNull(imprimirEdificacaoOcupacaoForm.getOrgao())) {
				reDTO.setFiltroOrgao(imprimirEdificacaoOcupacaoForm.getOrgao());
			}
			if(StringUtil.stringNotNull(imprimirEdificacaoOcupacaoForm.getUf())) {
				reDTO.setUf(imprimirEdificacaoOcupacaoForm.getUf());
			}
			if(StringUtil.stringNotNull(imprimirEdificacaoOcupacaoForm.getCodMunicipio())) {
				reDTO.setCodMunicipio(imprimirEdificacaoOcupacaoForm.getCodMunicipio());
			}
			if(imprimirEdificacaoOcupacaoForm.getCodMunicipio()!=null && !imprimirEdificacaoOcupacaoForm.getCodMunicipio().equals("0") &&  StringUtil.stringNotNull(imprimirEdificacaoOcupacaoForm.getMunicipio())) {
				reDTO.setFiltroMunicipio(imprimirEdificacaoOcupacaoForm.getMunicipio());
			}
			if(StringUtil.stringNotNull(imprimirEdificacaoOcupacaoForm.getRadRelatorio())) {
				reDTO.setFiltroRelatorio(imprimirEdificacaoOcupacaoForm.getRadRelatorio());
			}
			reDTO.setUsuario(sentinelaInterface.getNome());
			
			//Tratamento de operador de orgao
			if (imprimirEdificacaoOcupacaoForm.getIndOperadorOrgao()){
				reDTO.setUsuarioS(CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario()));
			}else{
				reDTO.setUsuarioS(null);
			}
			//
			
			//tratamento instituição
			if(CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())){
				if (StringUtil.stringNotNull(imprimirEdificacaoOcupacaoForm.getCodInstituicao())){
					reDTO.setInstituicao(CadastroFacade.obterInstituicao(Integer.valueOf(imprimirEdificacaoOcupacaoForm.getCodInstituicao())));
				}else{
					reDTO.setInstituicao(null);
				}
			}else{
				reDTO.setInstituicao(CadastroFacade.obterInstituicaoUsuario(SentinelaComunicacao.getInstance(request).getCodUsuario()));
			}
			reDTO.setListaBensImoveis((List<RelatorioEdificacaoOcupacaoDTO>)CadastroFacade.listarRelatorioEdificacaoOcupacao(reDTO));	
			
			if(reDTO.getListaBensImoveis() == null || reDTO.getListaBensImoveis().size() == 0) {
				throw new ApplicationException("AVISO.11");
			}

			List <FiltroRelatorioEdificacaoOcupacaoDTO> listaRelatorio = new ArrayList<FiltroRelatorioEdificacaoOcupacaoDTO>();
			
			listaRelatorio.add(reDTO);
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nomeRelatorioJasper", "EdificacaoOcupacao.jasper");
			parametros.put("tituloRelatorio", "Relatório de Bens Imóveis - Edificação com Ocupação");
			parametros.put("pathCabecalho", Dominios.PATH_RELATORIO + "CabecalhoPadraoPaisagem.jasper");
			parametros.put("pathSubRelatorio", Dominios.PATH_RELATORIO +"SubRelatorioEdificacaoOcupacao.jasper");
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

			RelatorioIReportAction.processarRelatorio(listaRelatorio, parametros, form, request, mapping, response);
			
			return getActionForward();
		} catch (ApplicationException appEx) {
			
			setActionForward(carregarPgEditImprimirEdificacaoOcupacao(mapping, form, request, response));
			throw appEx;
		} catch (Exception e) {
			setActionForward(carregarPgEditImprimirEdificacaoOcupacao(mapping, form, request, response));
			throw new ApplicationException("ERRO.201", new String[]{"na geração do relatório de Bem Imóvel com Edificação"}, e);
		}		
		
	}
}
