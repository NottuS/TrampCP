package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.BemImovelPesquisaDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.LocalizarBemImovelSimplificadoForm;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.pojo.UsuarioOrgao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.mensagem.Mensagem;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Oksana
 * @version 1.0
 * @since 17/06/2011
 * 
 * Classe Action:
 * Responsavel por manipular a localização de Bem Imovel
 */

public class LocalizarBemImovelSimplificadoAction extends BaseDispatchAction {

	public ActionForward carregarInterfaceInicial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			LocalizarBemImovelSimplificadoForm localizarBemImovelSimplificadoForm = (LocalizarBemImovelSimplificadoForm)form;
			
			String camposPesquisaUCOrigem = (request.getParameter("camposPesquisaUCOrigem") != null ? request.getParameter("camposPesquisaUCOrigem").toString() : "");			
			localizarBemImovelSimplificadoForm.setCamposPesquisaUCOrigem(camposPesquisaUCOrigem);
			request.setAttribute("camposPesquisaUCOrigem", camposPesquisaUCOrigem);
			
			String instituicao = (request.getParameter("instituicao") != null ? request.getParameter("instituicao").toString() : "");			
			if (instituicao != null) {
				localizarBemImovelSimplificadoForm.setConInstituicao(instituicao);
			}
			
			String actionUCOrigem = (request.getParameter("actionUCOrigem") != null ? request.getParameter("actionUCOrigem").toString() : "");			
			localizarBemImovelSimplificadoForm.setActionUCOrigem(actionUCOrigem);
			request.setAttribute("actionUCOrigem", actionUCOrigem);
			
			localizarBemImovelSimplificadoForm.setUf("PR");
			localizarBemImovelSimplificadoForm.setConOcupacao(Integer.toString(3)); //Todas as ocupacoes
			
			localizarBemImovelSimplificadoForm.setIndGrupoSentinela(request.getSession().getAttribute("indGrupoSentinela").toString());
			localizarBemImovelSimplificadoForm.setAdm(GrupoSentinela.ADM_GERAL.getDescricao());
			
			request.setAttribute("denominacaoImovels", Util.htmlEncodeCollection(CadastroFacade.listarDenominacaoImovels()));
			//lista instituicao
			if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
				request.setAttribute("listaInstituicao", Util.htmlEncodeCollection(CadastroFacade.listarInstituicao()));	
			}
			
			
		} catch (Exception e) {
			throw new ApplicationException("ERRO.1", e, ApplicationException.ICON_ERRO);
		} 	
		return mapping.findForward("pgConsBemImovelSimplificado");
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
	
	public ActionForward pesquisarBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		try{
			setActionForward(mapping.findForward("pgListBemImovelSimplificadoAjax"));			

			if (!validaForm(mapping, form, request)) {
				return getActionForward();
			}
 
			carregarPaginaRequest(form, request);						
			return getActionForward();
		
		}catch (ApplicationException ae) {
			if (ae.getMessage().equalsIgnoreCase(Mensagem.getInstance().getMessage("AVISO.96"))){
				setActionForward(mapping.findForward("pgErroLocalizarBI"));
			}
			throw ae;
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{LocalizarBemImovelSimplificadoAction.class.getSimpleName() +".pesquisarBemImovel()"}, e, ApplicationException.ICON_ERRO);
		}

	}
	
	
		/**
	 * Carregar a pagina de consulta no request.<br>
	 * @author oksana
	 * @since 22/06/2011
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @throws ApplicationException
	 */	
	public void carregarPaginaRequest(ActionForm form, HttpServletRequest request) throws ApplicationException {

		try{
		
			LocalizarBemImovelSimplificadoForm localizarBemImovelSimplificadoForm = (LocalizarBemImovelSimplificadoForm)form;
			
			BemImovelPesquisaDTO bemDTO = new BemImovelPesquisaDTO();

			String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");
			String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");

			Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));		

			// Se informado o Código do Imóvel, pesquisar imóvel somente por ele
			Boolean flagBemDTONula = Boolean.TRUE;
			if(!Util.strEmBranco(localizarBemImovelSimplificadoForm.getConCodUf()) && !"0".equals(localizarBemImovelSimplificadoForm.getConCodUf())) {
				bemDTO.setUf(localizarBemImovelSimplificadoForm.getConCodUf());
				localizarBemImovelSimplificadoForm.setUf(localizarBemImovelSimplificadoForm.getConCodUf());
				flagBemDTONula = Boolean.FALSE;
			}
			if(!Util.strEmBranco(localizarBemImovelSimplificadoForm.getConCodMunicipio()) && !"0".equals(localizarBemImovelSimplificadoForm.getConCodMunicipio())) {
				bemDTO.setCodMunicipio(Integer.parseInt(localizarBemImovelSimplificadoForm.getConCodMunicipio()) );
				localizarBemImovelSimplificadoForm.setCodMunicipio(localizarBemImovelSimplificadoForm.getConCodMunicipio());
				flagBemDTONula = Boolean.FALSE;
			}
			if(!Util.strEmBranco(localizarBemImovelSimplificadoForm.getConDenominacaoImovel())) {
				bemDTO.setCodDenominacaoImovel(Integer.parseInt(localizarBemImovelSimplificadoForm.getConDenominacaoImovel()));
				flagBemDTONula = Boolean.FALSE;
			}
			if(!Util.strEmBranco(localizarBemImovelSimplificadoForm.getConOcupacao())) {
				bemDTO.setOcupante(localizarBemImovelSimplificadoForm.getConOcupacao());
				flagBemDTONula = Boolean.FALSE;
			}
			if(!Util.strEmBranco(localizarBemImovelSimplificadoForm.getConObservacao())) {
				bemDTO.setObservacao(localizarBemImovelSimplificadoForm.getConObservacao());
				flagBemDTONula = Boolean.FALSE;
			}
			
			if (Boolean.FALSE.equals(flagBemDTONula)) { // Correcao do bug - Trazia todos os resultados do BD
				// Testar se o usuário logado possui o grupo Sentinela "OPEORG - GPI", 
				// neste caso deverá obter as notificacoes relativas as orgaos associados ao usuario
				if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo())){
					Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
					if (usuario == null){
						throw new ApplicationException("AVISO.97");
					}
					if (usuario.getListaUsuarioOrgao() == null || usuario.getListaUsuarioOrgao().size() < 1){
						throw new ApplicationException("AVISO.96");
					}
					for (UsuarioOrgao orgao: usuario.getListaUsuarioOrgao()){
						bemDTO.getListaOrgao().add(orgao.getOrgao());
					}
					bemDTO.setIndOperadorOrgao(true);
				}
				if (!request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
					//instituicao do usuario logado
					Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
					if (usuario == null){
						throw new ApplicationException("AVISO.97");
					}
					if (usuario.getInstituicao() != null){
						bemDTO.setCodInstituicao(usuario.getInstituicao().getCodInstituicao());
					}					
				}else{
					//instituicao informada em tela
					if (localizarBemImovelSimplificadoForm.getConInstituicao().isEmpty()){
						throw new ApplicationException("AVISO.88");
					}
					bemDTO.setCodInstituicao(Integer.valueOf(localizarBemImovelSimplificadoForm.getConInstituicao()));
				}
				pagina = CadastroFacade.listarBemImovelSimplificado(pagina, bemDTO);
			} else {
				pagina.setRegistros(new ArrayList<BemImovel>());
			}	
			Util.htmlEncodeCollection(pagina.getRegistros());
			
			request.setAttribute("paginaLocBemImovel", pagina);
			if(pagina.getTotalRegistros() == 0) {
				throw new ApplicationException("AVISO.2", ApplicationException.ICON_AVISO);
			}
			
		}catch (ApplicationException ae) {
			throw ae;
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{LocalizarBemImovelSimplificadoAction.class.getSimpleName() +".carregarPesquisaRequest()"}, e, ApplicationException.ICON_ERRO);
		}
	}
	
}
