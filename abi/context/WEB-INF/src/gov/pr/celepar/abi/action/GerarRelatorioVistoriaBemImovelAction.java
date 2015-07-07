package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.enumeration.SituacaoVistoria;
import gov.pr.celepar.abi.facade.OperacaoFacade;
import gov.pr.celepar.abi.form.GerarRelatorioVistoriaBemImovelForm;
import gov.pr.celepar.abi.generico.action.BaseAction;
import gov.pr.celepar.abi.pojo.Vistoria;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.io.File;
import java.util.ArrayList;
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
 * Classe responsavel permitir a impressao de vistorias de bens imoveis (finalizadas e abertas).<BR>
 * @author ginaalmeida
 * @version 1.0
 * @since 13/07/2011
 */
public class GerarRelatorioVistoriaBemImovelAction extends BaseAction {

	/**
	 * Carrega a tela inicial do respectivo Caso de Uso.<br>
	 * @author ginaalmeida
	 * @since 13/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */	
	public ActionForward carregarInterfaceInicial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			setActionForward(mapping.findForward(PG_EDIT));
			
			GerarRelatorioVistoriaBemImovelForm localForm = (GerarRelatorioVistoriaBemImovelForm)form;
			
			String ucsChamador = request.getAttribute("ucsChamador") != null ? (String)request.getAttribute("ucsChamador") : "";			
			localForm.setUcsChamador(ucsChamador);
			request.setAttribute("ucsChamador", ucsChamador);	
			String conInstituicao = request.getAttribute("conInstituicao") != null ? (String)request.getAttribute("conInstituicao") : "";			
			localForm.setConsIntituicao(conInstituicao);
			
			String codVistoria = request.getAttribute("codVistoria") != null ? (String)request.getAttribute("codVistoria") : "";
			Vistoria vistoria = null;
			if(StringUtils.isNotBlank(codVistoria)){
				vistoria = OperacaoFacade.obterVistoriaCompleta(Integer.valueOf(codVistoria));
			}
			
			if(vistoria != null){
				
				localForm.setCodVistoria(codVistoria);
				
				if(SituacaoVistoria.FINALIZADA.getId().equals(vistoria.getStatusVistoria().getCodStatusVistoria())){ 
					localForm.setImprimir("2");
					confirmar(mapping, localForm, request, response);
					setActionForward(null);
				} else{
					
					if(StringUtils.isBlank(localForm.getImprimir())){
						localForm.setImprimir("1");
					}
					localForm.setBemImovel(vistoria.getBemImovel().getNrBemImovel().toString());
					
					
				}
			}
			
		} catch (ApplicationException ae) {
			throw ae; 
		}catch (Exception e) {
			setActionForward(concluir(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{GerarRelatorioVistoriaBemImovelAction.class.getSimpleName() + ".carregarInterfaceInicial()"}, e, ApplicationException.ICON_ERRO);
		}	
		return getActionForward();
	}
	
	/**
	 * Gera o relatorio.<BR>
	 * @author ginaalmeida
	 * @since 13/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward confirmar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		GerarRelatorioVistoriaBemImovelForm localForm = (GerarRelatorioVistoriaBemImovelForm)form;

		try {
			
			String codVistoria = StringUtils.isNotBlank(localForm.getCodVistoria()) ? localForm.getCodVistoria() : null;
			String tipoRelatorio = StringUtils.isNotBlank(localForm.getImprimir()) ? localForm.getImprimir() : null;
			
			Vistoria vistoria = OperacaoFacade.obterVistoriaCompleta(Integer.valueOf(codVistoria));
			
			Map<String, Object> parametros = new HashMap<String, Object>();

			List<Vistoria> listaVistoria = new ArrayList<Vistoria>();
			listaVistoria.add(vistoria);
			
			
			
			String path = request.getSession().getServletContext().getRealPath("");
			String image1 = null;
			
			
			image1 = path + File.separator + "images" + File.separator +"logo" + File.separator+vistoria.getBemImovel().getInstituicao().getCodInstituicao()+vistoria.getBemImovel().getInstituicao().getLogoInstituicao();
			parametros.put("descricaoInstituicao", vistoria.getBemImovel().getInstituicao().getDescricaoRelatorio());
			
			
		
			String image2 = path + File.separator + "images" + File.separator + "logo_sistema_relatorio.jpg";

			
			//Imprimir Vistoria
			if(StringUtils.isNotBlank(tipoRelatorio) && ("2").equals(tipoRelatorio)){
				parametros.put("nomeRelatorioJasper", "GerarRelatorioVistoriaBemImovel.jasper");
				parametros.put("pathSubRelatorioItemVistoria", Dominios.PATH_RELATORIO +"SubGerarRelatorioVistoriaBemImovelItemVistoria.jasper");
				parametros.put("pathSubRelatorioItemVistoriaDominio", Dominios.PATH_RELATORIO +"SubGerarRelatorioVistoriaBemImovelItemVistoriaDominio.jasper");
				
			//Imprimir Formulario Vistoria
			} else if(StringUtils.isNotBlank(tipoRelatorio) && ("1").equals(tipoRelatorio)){
				parametros.put("nomeRelatorioJasper", "GerarFormularioVistoriaBemImovel.jasper");
				parametros.put("pathSubRelatorioItemVistoria", Dominios.PATH_RELATORIO +"SubGerarFormularioVistoriaBemImovelItemVistoria.jasper");
				parametros.put("pathSubRelatorioItemVistoriaDominio", Dominios.PATH_RELATORIO +"SubGerarFormularioVistoriaBemImovelItemVistoriaDominio.jasper");
			}
			parametros.put("tituloRelatorio", "Vistoria de Bem Imóvel");
			parametros.put("pathCabecalho", Dominios.PATH_RELATORIO + "CabecalhoPadraoRetrato.jasper");
			parametros.put("image1", image1);
			parametros.put("image2", image2); 
			
			RelatorioIReportAction.processarRelatorio(listaVistoria, parametros, form, request, mapping, response);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"na geração da Vistoria de Bem Imóvel"}, e);
		}		
		return getActionForward();

	}

}