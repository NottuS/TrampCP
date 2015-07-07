package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.FiltroRelatorioNotificacaoBemImovelDTO;
import gov.pr.celepar.abi.dto.RelatorioNotificacaoDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.ImprimirNotificacaoBemImovelForm;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.pojo.UsuarioOrgao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.mensagem.Mensagem;
import gov.pr.celepar.framework.util.Data;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
 * @since 11/02/2010
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class ImprimirNotificacaoBemImovelAction extends BaseDispatchAction {

	/**
	 * Carrega pagina para filtro dos campos do relatório de Notificação de Bem Imóvel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgImprimirNotificacaoBemImovel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		request.setAttribute("classificacaoBemImovels", Util.htmlEncodeCollection(CadastroFacade.listarClassificacaoBemImovels()));
		request.setAttribute("situacaoImovels", Util.htmlEncodeCollection(CadastroFacade.listarSituacaoImovels()));
		ImprimirNotificacaoBemImovelForm imprimirNotificacaoBemImovelForm = (ImprimirNotificacaoBemImovelForm) form;
		imprimirNotificacaoBemImovelForm.setRadTerreno("1");
		imprimirNotificacaoBemImovelForm.setRadRelatorio("1");
		imprimirNotificacaoBemImovelForm.setRadAdministracao("");
		String dataAtual= Data.dataAtual();
		Date dataAnterior = Data.addMeses(-1, new Date());
		imprimirNotificacaoBemImovelForm.setTsNotificacaoAte(dataAtual);
		imprimirNotificacaoBemImovelForm.setTsNotificacaoDe(Data.formataData(dataAnterior, "dd/MM/yyyy"));
		
		Long codUsuarioSentinela = SentinelaComunicacao.getInstance(request).getCodUsuario();
		if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())){
			request.setAttribute("listaInstituicao", Util.htmlEncodeCollection( CadastroFacade.listarInstituicao()));
		}else{
			imprimirNotificacaoBemImovelForm.setCodInstituicao(CadastroFacade.obterInstituicaoUsuario(codUsuarioSentinela).getCodInstituicao().toString());
		}
		
		return mapping.findForward("pgEditImprimirNotificacaoBemImovel");
	}
	
	public ActionForward gerarRelatorio(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		setActionForward(mapping.findForward("pgEditImprimirNotificacaoBemImovel"));
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		ImprimirNotificacaoBemImovelForm imprimirNotificacaoBemImovelForm = (ImprimirNotificacaoBemImovelForm)form;
		String path = request.getSession().getServletContext().getRealPath("");
		
		
		request.setAttribute("classificacaoBemImovels", Util.htmlEncodeCollection(CadastroFacade.listarClassificacaoBemImovels()));
		request.setAttribute("situacaoImovels", Util.htmlEncodeCollection(CadastroFacade.listarSituacaoImovels()));
		if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())){
			request.setAttribute("listaInstituicao", Util.htmlEncodeCollection( CadastroFacade.listarInstituicao()));
		}
		setActionForward(mapping.findForward("pgEditImprimirNotificacaoBemImovel"));
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			
			return getActionForward();
		}	
		try {
			Date dataDe = Data.formataData(imprimirNotificacaoBemImovelForm.getTsNotificacaoDe());
			Date dataAte = Data.formataData(imprimirNotificacaoBemImovelForm.getTsNotificacaoAte());
			if (Data.dataDiff(dataAte,dataDe )>0){
				throw new ApplicationException("imprimirNotificacaoBemImovelForm.tsNotificacaoAteMenortsNotificacaoDe");
			}


			Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());

			FiltroRelatorioNotificacaoBemImovelDTO rnbiDTO = new FiltroRelatorioNotificacaoBemImovelDTO();
			
			if (StringUtil.stringNotNull(imprimirNotificacaoBemImovelForm.getCodSituacao() )) {
				rnbiDTO.setCodSituacao(imprimirNotificacaoBemImovelForm.getCodSituacao());
			}
			if (StringUtil.stringNotNull(imprimirNotificacaoBemImovelForm.getCodSituacao() ) && StringUtil.stringNotNull(imprimirNotificacaoBemImovelForm.getSituacaoImovel())) {
				rnbiDTO.setFiltroSituacao(imprimirNotificacaoBemImovelForm.getSituacaoImovel());
			}
			if (StringUtil.stringNotNull(imprimirNotificacaoBemImovelForm.getCodClassificacao() )) {
				rnbiDTO.setCodClassificacao(imprimirNotificacaoBemImovelForm.getCodClassificacao());
			}
			if (StringUtil.stringNotNull(imprimirNotificacaoBemImovelForm.getCodClassificacao() ) &&  StringUtil.stringNotNull(imprimirNotificacaoBemImovelForm.getClassificacaoBemImovel())) {
				rnbiDTO.setFiltroClassificacao(imprimirNotificacaoBemImovelForm.getClassificacaoBemImovel());
			}
			if (StringUtil.stringNotNull(imprimirNotificacaoBemImovelForm.getRadTerreno()))  {
				rnbiDTO.setFiltroTerreno(imprimirNotificacaoBemImovelForm.getRadTerreno());
			}
			if (StringUtil.stringNotNull(imprimirNotificacaoBemImovelForm.getUf())) {
				rnbiDTO.setUf(imprimirNotificacaoBemImovelForm.getUf());
			}
			if (StringUtil.stringNotNull(imprimirNotificacaoBemImovelForm.getCodMunicipio())) {
				rnbiDTO.setCodMunicipio(imprimirNotificacaoBemImovelForm.getCodMunicipio());
			}
			if (rnbiDTO.getCodMunicipio()!= null &&!rnbiDTO.getCodMunicipio().equals("0") && StringUtil.stringNotNull( imprimirNotificacaoBemImovelForm.getMunicipio())) {
				rnbiDTO.setFiltroMunicipio(imprimirNotificacaoBemImovelForm.getMunicipio());
			}
			if (StringUtil.stringNotNull(imprimirNotificacaoBemImovelForm.getTsNotificacaoDe())) {
				rnbiDTO.setFiltroDataNotificacaoDe(imprimirNotificacaoBemImovelForm.getTsNotificacaoDe());
			}
			if (StringUtil.stringNotNull(imprimirNotificacaoBemImovelForm.getTsNotificacaoAte())){
				rnbiDTO.setFiltroDataNotificacaoAte(imprimirNotificacaoBemImovelForm.getTsNotificacaoAte());
			}	
			if (StringUtil.stringNotNull(imprimirNotificacaoBemImovelForm.getRadRelatorio() )) {
				rnbiDTO.setFiltroRelatorio(imprimirNotificacaoBemImovelForm.getRadRelatorio());
			}
			if (StringUtil.stringNotNull(imprimirNotificacaoBemImovelForm.getRadAdministracao())) {
				rnbiDTO.setFiltroAdministracao(imprimirNotificacaoBemImovelForm.getRadAdministracao());
			}else{
				rnbiDTO.setFiltroAdministracao("");
			}
			rnbiDTO.setUsuario(sentinelaInterface.getNome());
			
			// Testar se o usuário logado possui o grupo Sentinela "OPEORG - GPI", 
			// neste caso deverá obter as notificacoes relativas as orgaos associados ao usuario
			
			if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo())){
				if (usuario == null){
					throw new ApplicationException("AVISO.97");
				}
				if (usuario.getListaUsuarioOrgao() == null || usuario.getListaUsuarioOrgao().size() < 1){
					setActionForward(mapping.findForward("pgErroImpNotifBI"));
					throw new ApplicationException("AVISO.96");
				}
				for (UsuarioOrgao orgao: usuario.getListaUsuarioOrgao()){
					rnbiDTO.getListaOrgao().add(orgao.getOrgao());
				}
				rnbiDTO.setIndOperadorOrgao(true);
			}
			// Fim teste
			
			//tratamento instituição
			if(CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())){
				if (StringUtil.stringNotNull(imprimirNotificacaoBemImovelForm.getCodInstituicao())){
					rnbiDTO.setInstituicao(CadastroFacade.obterInstituicao(Integer.valueOf(imprimirNotificacaoBemImovelForm.getCodInstituicao())));
				}else{
					rnbiDTO.setInstituicao(null);
				}
			}else{
				rnbiDTO.setInstituicao(usuario.getInstituicao());
			}
			
			rnbiDTO.setListaNotificacoes((List<RelatorioNotificacaoDTO>)CadastroFacade.listarNotificacaoParaRelatorio(rnbiDTO));
			
			if (rnbiDTO.getListaNotificacoes() == null || rnbiDTO.getListaNotificacoes().size() == 0){
				
				throw new ApplicationException("AVISO.11");
			}
			
			List <FiltroRelatorioNotificacaoBemImovelDTO> listaRelatorio = new ArrayList<FiltroRelatorioNotificacaoBemImovelDTO>();
			
			listaRelatorio.add(rnbiDTO);
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nomeRelatorioJasper", "NotificacaoBemImovel.jasper");
			parametros.put("tituloRelatorio", "Relatório de Notificação de Bens Imóveis");
			parametros.put("pathCabecalho", Dominios.PATH_RELATORIO + "CabecalhoPadraoRetrato.jasper");
			parametros.put("pathSubRelatorioNotificacoes", Dominios.PATH_RELATORIO +"SubRelatorioNotificacoes.jasper");
			parametros.put("pathSubRelatorioGerencial", Dominios.PATH_RELATORIO +"SubRelatorioGerencial.jasper");
			
			String image1 =null;
			if (rnbiDTO.getInstituicao()!= null){
				image1 = Dominios.PATH_LOGO.concat(File.separator).concat(rnbiDTO.getInstituicao().getCodInstituicao().toString()).concat(rnbiDTO.getInstituicao().getLogoInstituicao());
				parametros.put("descricaoInstituicao", rnbiDTO.getInstituicao().getDescricaoRelatorio());
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
			if (appEx.getMessage().equalsIgnoreCase(Mensagem.getInstance().getMessage("AVISO.96"))){
				setActionForward(mapping.findForward("pgErroImpNotifBI"));
			}
			setActionForward(this.carregarPgImprimirNotificacaoBemImovel(mapping, imprimirNotificacaoBemImovelForm, request, response));
			throw appEx;
		} catch (Exception e) {
			setActionForward(this.carregarPgImprimirNotificacaoBemImovel(mapping, imprimirNotificacaoBemImovelForm, request, response));
			throw new ApplicationException("ERRO.201", new String[]{"na geração do relatório de Notificação de Bem Imóvel"}, e);
		}		
		
	}
}
