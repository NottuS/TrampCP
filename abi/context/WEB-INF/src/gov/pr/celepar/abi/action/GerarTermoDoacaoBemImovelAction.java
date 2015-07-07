package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.DoacaoDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.facade.OperacaoFacade;
import gov.pr.celepar.abi.form.GerarTermoDoacaoBemImovelForm;
import gov.pr.celepar.abi.generico.action.BaseAction;
import gov.pr.celepar.abi.pojo.AssinaturaDoacao;
import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.abi.pojo.Doacao;
import gov.pr.celepar.abi.pojo.LeiBemImovel;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Dominios.administracaoImovel;
import gov.pr.celepar.abi.util.Dominios.statusTermo;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Data;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Classe responsavel por permitir a emissao de Termos de Doacao de Bens Imoveis.<BR>
 * @author ginaalmeida
 * @version 1.0
 * @since 28/07/2011
 */
public class GerarTermoDoacaoBemImovelAction extends BaseAction {

	/**
	 * Carrega a tela inicial do respectivo Caso de Uso.<br>
	 * @author ginaalmeida
	 * @since 28/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */	
	public ActionForward carregarInterfaceInicial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward(PG_VIEW));
		try {
			GerarTermoDoacaoBemImovelForm localForm = (GerarTermoDoacaoBemImovelForm)form;
			verificarGrupoUsuarioLogado(localForm, request);
			
			if(StringUtils.isBlank(localForm.getUcsChamador())){
				String ucsChamador = request.getAttribute("ucsChamador") != null ? (String)request.getAttribute("ucsChamador") : "";			
				localForm.setUcsChamador(ucsChamador);
				localForm.setUcsRetorno(ucsChamador);
				request.setAttribute("ucsChamador", ucsChamador);	
			}
			
			String codDoacao = null;
			if(StringUtils.isBlank(localForm.getCodDoacao())){
				codDoacao = request.getAttribute("codDoacao") != null ? (String)request.getAttribute("codDoacao") : "";
			} else{
				codDoacao = localForm.getCodDoacao();
			}
			
			Doacao doacao = null;
			if(StringUtils.isNotBlank(codDoacao)){
				doacao = OperacaoFacade.obterDoacaoCompleto(Integer.valueOf(codDoacao));
			}
			
			if(doacao != null){
				if(Integer.valueOf(statusTermo.RASCUNHO.getIndex()).equals(doacao.getStatusTermo().getCodStatusTermo())){
					populaForm(localForm, doacao, request);
				} else if(Integer.valueOf(statusTermo.VIGENTE.getIndex()).equals(doacao.getStatusTermo().getCodStatusTermo()) || 
						Integer.valueOf(statusTermo.FINALIZADO.getIndex()).equals(doacao.getStatusTermo().getCodStatusTermo())){
					
					localForm.setCodDoacao(doacao.getCodDoacao().toString());
					localForm.setImprimir("1");
					gerarRelatorio(mapping, localForm, request, response);
					setActionForward(null);
					
				} else if(Integer.valueOf(statusTermo.DEVOLVIDO.getIndex()).equals(doacao.getStatusTermo().getCodStatusTermo()) || 
						Integer.valueOf(statusTermo.REVOGADO.getIndex()).equals(doacao.getStatusTermo().getCodStatusTermo())){
					
					localForm.setCodDoacao(doacao.getCodDoacao().toString());
					localForm.setImprimir("2");
					gerarRelatorio(mapping, localForm, request, response);
					setActionForward(null);
					
				}
			}
			
		} catch (ApplicationException ae) {
			throw ae; 
		}catch (Exception e) {
			setActionForward(concluir(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{GerarTermoDoacaoBemImovelAction.class.getSimpleName() + ".carregarInterfaceInicial()"}, e, ApplicationException.ICON_ERRO);
		}	
		return getActionForward();
	}
	
	private String validarConfirmacaoTermo(Doacao doacao, Pagina pagItem, Pagina pagAssinatura) {
		boolean test = false;
		if (doacao.getBemImovel() != null && doacao.getBemImovel().getCodBemImovel() > 0 &&
				doacao.getOrgaoResponsavel() != null && doacao.getOrgaoResponsavel().getCodOrgao() > 0 &&
				doacao.getDtInicioVigencia() != null && doacao.getDtFimVigencia() != null &&
				doacao.getProtocolo() != null && doacao.getProtocolo().trim().length() > 0 &&
				doacao.getLeiBemImovel() != null && doacao.getLeiBemImovel().getCodLeiBemImovel() > 0 &&
				doacao.getLeiBemImovel().getDataAssinatura() != null && doacao.getLeiBemImovel().getDataPublicacao() != null &&
				doacao.getLeiBemImovel().getNrDioe() > 0 && doacao.getLeiBemImovel().getNumero() > 0) {
			test = true;
		}
		
		if (test && pagItem.getQuantidade() > 0 && pagAssinatura.getQuantidade() > 0) {
			return "confirmar";
		}
		return "visualizar";
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
	public ActionForward gerarRelatorio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		GerarTermoDoacaoBemImovelForm localForm = (GerarTermoDoacaoBemImovelForm)form;

		try {
			verificarGrupoUsuarioLogado(localForm, request);
			String codDoacao = StringUtils.isNotBlank(localForm.getCodDoacao()) ? localForm.getCodDoacao() : null;
			String tipoRelatorio = null;
			Doacao doacao = null;
			if(StringUtils.isNotBlank(codDoacao)){
				doacao = OperacaoFacade.obterDoacaoCompleto(Integer.valueOf(codDoacao));
			}
			if(Integer.valueOf(statusTermo.VIGENTE.getIndex()).equals(doacao.getStatusTermo().getCodStatusTermo()) ||
					Integer.valueOf(statusTermo.FINALIZADO.getIndex()).equals(doacao.getStatusTermo().getCodStatusTermo()) ||
					Integer.valueOf(statusTermo.RASCUNHO.getIndex()).equals(doacao.getStatusTermo().getCodStatusTermo())){
					tipoRelatorio = "1";
			} else if(Integer.valueOf(statusTermo.DEVOLVIDO.getIndex()).equals(doacao.getStatusTermo().getCodStatusTermo()) ||
					Integer.valueOf(statusTermo.REVOGADO.getIndex()).equals(doacao.getStatusTermo().getCodStatusTermo())){
				tipoRelatorio = "2";
			}
			
			Map<String, Object> parametros = new HashMap<String, Object>();

			//popula objeto para ser impresso o relatorio
			List<DoacaoDTO> listaDoacao = OperacaoFacade.listarTermoDoacao(doacao);
			
			String path = request.getSession().getServletContext().getRealPath("");
			String image1 = null;
			if (doacao.getInstituicao()!= null){
				image1 = path + File.separator + "images" + File.separator +"logo" + File.separator+doacao.getInstituicao().getCodInstituicao()+doacao.getInstituicao().getLogoInstituicao();
				parametros.put("descricaoInstituicao", doacao.getInstituicao().getDescricaoRelatorio());
			}else{
				parametros.put("descricaoInstituicao", "Geral");
			}
			String image2 = path + File.separator + "images" + File.separator + "logo_sistema_relatorio.jpg";

			//Termo Vigente/Renovado/Finalizado/Rascunho/Em Renovacao
			if(StringUtils.isNotBlank(tipoRelatorio) && ("1").equals(tipoRelatorio)){
				parametros.put("tituloRelatorio", " TERMO DE DOAÇÃO DE IMÓVEL Nº " + listaDoacao.get(0).getNumeroAnoTermo());
				parametros.put("txtAutRevog", "Autorização concedida pela Lei Estadual nº " + 
					(StringUtils.isNotBlank(listaDoacao.get(0).getNumeroLei()) ? listaDoacao.get(0).getNumeroLei() : "*") + 
					" de " + (StringUtils.isNotBlank(listaDoacao.get(0).getDataPublicacaoLei()) ? listaDoacao.get(0).getDataPublicacaoLei() : "*") + 
					" (DIOE nº " + (StringUtils.isNotBlank(listaDoacao.get(0).getNumeroDioe()) ? listaDoacao.get(0).getNumeroDioe() : "*") + 
					" de " + (StringUtils.isNotBlank(listaDoacao.get(0).getDataPublicacaoDioe()) ? listaDoacao.get(0).getDataPublicacaoDioe() : "*") + ").");
			//Termo Revogacao/Devolucao
			} else if(StringUtils.isNotBlank(tipoRelatorio) && ("2").equals(tipoRelatorio)){
				parametros.put("tituloRelatorio", "TERMO DE " + 
					Dominios.statusTermoRel.getStatusTermoByIndex(doacao.getStatusTermo().getCodStatusTermo()).getLabel().toUpperCase() + 
					" DE DOAÇÃO DE IMÓVEL Nº " + listaDoacao.get(0).getNumeroAnoTermo());
				parametros.put("txtAutRevog", "Pelo presente termo torna-se revogada a doação de bem imóvel concedida pela Lei Estadual n° " + 
					(StringUtils.isNotBlank(listaDoacao.get(0).getNumeroLei()) ? listaDoacao.get(0).getNumeroLei() : "*") + ".");
			}
			
			parametros.put("nomeRelatorioJasper", "GerarTermoDoacaoBemImovel.jasper");
			parametros.put("pathSubRelatorio", Dominios.PATH_RELATORIO +"SubGerarTermoDoacaoBemImovel.jasper");
			parametros.put("pathSubRelatorioAssinaturas", Dominios.PATH_RELATORIO +"SubGerarTermoDoacaoBemImovelAssinaturas.jasper");
			parametros.put("pathCabecalho", Dominios.PATH_RELATORIO + "CabecalhoPadraoRetrato.jasper");
			parametros.put("tipoRelatorio", Dominios.statusTermo.getStatusTermoByIndex(doacao.getStatusTermo().getCodStatusTermo()).getLabel());
			parametros.put("image1", image1);
			parametros.put("image2", image2); 
			
			setActionForward(null);
			RelatorioIReportAction.processarRelatorio(listaDoacao, parametros, form, request, mapping, response);

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward(localForm.getUcsRetorno()));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward(localForm.getUcsRetorno()));
			throw new ApplicationException("ERRO.201", new String[]{"na geração do Termo de Doação do Bem Imóvel"}, e);
		}		
		return getActionForward();

	}
	
	/**
	 * Realiza a confirmacao do Termo de Doacao Definitivo.<BR>
	 * @author ginaalmeida
	 * @since 28/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward confirmarTermoDoacaoDefinitivo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		GerarTermoDoacaoBemImovelForm localForm = (GerarTermoDoacaoBemImovelForm)form;
		setActionForward(mapping.findForward(PG_VIEW));

		try {
			verificarGrupoUsuarioLogado(localForm, request);
			String codDoacao = StringUtils.isNotBlank(localForm.getCodDoacao()) ? localForm.getCodDoacao() : null;
			
			Doacao doacao = null;
			if(StringUtils.isNotBlank(codDoacao)){
				doacao = OperacaoFacade.obterDoacaoCompleto(Integer.valueOf(codDoacao));
			}
			populaForm(localForm, doacao, request);
			if(doacao != null){
				Integer qtd = obtemQtdChefeCpeListaAssinatura(doacao.getListaAssinaturaDoacao());
				if (qtd.intValue() < 1) {
					throw new ApplicationException("AVISO.101", ApplicationException.ICON_AVISO);
				}
				if (qtd.intValue() > 1) {
					throw new ApplicationException("AVISO.102", ApplicationException.ICON_AVISO);
				}
				Boolean valido = CadastroFacade.validaValoresInformados(doacao.getBemImovel().getCodBemImovel(), doacao.getCodDoacao(), Integer.valueOf(2));
				if (!valido) {
					throw new ApplicationException("AVISO.67", ApplicationException.ICON_AVISO);
				}

				String retorno = OperacaoFacade.validaDisponibilidade(doacao.getBemImovel().getCodBemImovel(), doacao.getCodDoacao(), Integer.valueOf(2));
				if (retorno != null && retorno.length() > 0) {
					throw new ApplicationException("AVISO.68", new String[]{retorno}, ApplicationException.ICON_AVISO);
				}

				if(doacao.getDtInicioVigencia() != null && doacao.getDtInicioVigencia().after(new Date())){  //dataInicioVigencia > dataAtual
					throw new ApplicationException("AVISO.64", new String[]{"Doação", Data.formataData(doacao.getDtInicioVigencia(), "dd/MM/yyyy")}, ApplicationException.ICON_AVISO);
				}

				CessaoDeUso cessaoDeUso = new CessaoDeUso();
				cessaoDeUso.setCodCessaoDeUso(Integer.valueOf(0));
				cessaoDeUso.setBemImovel(doacao.getBemImovel());
				cessaoDeUso.setInstituicao(doacao.getInstituicao());
				String result = OperacaoFacade.verificarCessaoTotalBemImovel(cessaoDeUso);
				if (result != null && result.length() > 0) {
					throw new ApplicationException("AVISO.51",  new String[]{result}, ApplicationException.ICON_AVISO);
				}
				
				SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
				
				doacao.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.VIGENTE.getIndex()));
				doacao.setTsAtualizacao(new Date());
				doacao.setDataRegistro(new Date());
				doacao.setCpfResponsavelAlteracao(sentinelaInterface.getCpf());
				
				OperacaoFacade.salvarDoacao(doacao);
				
			}
			
			addMessage("SUCESSO.38", new String[]{"Doação", doacao.getCodDoacao().toString()}, request);
			
			setActionForward(mapping.findForward(localForm.getUcsRetorno()));
			
		} catch (ApplicationException ae) {
			throw ae; 
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.geral", new String[]{GerarTermoDoacaoBemImovelAction.class.getSimpleName() + ".confirmarTermoDoacaoDefinitivo()"}, e, ApplicationException.ICON_ERRO);
		}	
		return getActionForward();

	}
	
	private Integer obtemQtdChefeCpeListaAssinatura(Set<AssinaturaDoacao> listaAssinaturaDoacao) {
		int qtd = 0;
		for (AssinaturaDoacao assinatura : listaAssinaturaDoacao) {
			if(assinatura.getAssinatura().getCargoAssinatura().getDescricao().contains("CPE")){
				qtd++;
			}
		}
		return qtd;
	}

	private void populaForm(GerarTermoDoacaoBemImovelForm localForm, Doacao doacao, HttpServletRequest request) throws ApplicationException {
		localForm.setCodDoacao(doacao.getCodDoacao().toString());
		localForm.setStatus(Dominios.statusTermo.getStatusTermoByIndex(doacao.getStatusTermo().getCodStatusTermo()).getLabel());
		localForm.setCodDoacao(doacao.getCodDoacao().toString());
		localForm.setCodBemImovel(doacao.getBemImovel().getCodBemImovel().toString());
		localForm.setNrBemImovel(doacao.getBemImovel().getNrBemImovel().toString());
		localForm.setInstituicao(doacao.getInstituicao().getCodInstituicao().toString());
		localForm.setInstituicaoDesc(doacao.getInstituicao().getSiglaDescricao());
		localForm.setNumTermo(doacao.getNumeroTermo());
		localForm.setAdministracao(administracaoImovel.getAdministracaoImovelByIndex(doacao.getAdministracao()).getLabel());
		if (doacao.getOrgaoResponsavel() != null)
			localForm.setOrgao(doacao.getOrgaoResponsavel().getSiglaDescricao());

		localForm.setDtInicioVigencia(doacao.getDtInicioVigencia() != null ? Data.formataData(doacao.getDtInicioVigencia()) : "");
		localForm.setDtFimVigencia(doacao.getDtFimVigencia() != null ? Data.formataData(doacao.getDtFimVigencia()) : "");
		localForm.setProtocolo(StringUtils.isNotBlank(doacao.getProtocolo()) ? doacao.getProtocoloFormatado() : "");
		
		//dados da lei
		if (doacao.getNrProjetoLei() != null && doacao.getNrProjetoLei() > 0) {
			localForm.setProjetoLei("Sim");
			localForm.setNumeroLei(doacao.getNrProjetoLei().toString());
		} else {
			if (doacao.getLeiBemImovel() != null) {
				LeiBemImovel lei = CadastroFacade.obterLeiBemImovel(doacao.getLeiBemImovel().getCodLeiBemImovel());
				localForm.setProjetoLei("Não");
				localForm.setCodLei(lei.getCodLeiBemImovel().toString());
				localForm.setNumeroLei(Long.toString(lei.getNumero()));
				localForm.setDataAssinaturaLei(Data.formataData(lei.getDataAssinatura()));
				localForm.setDataPublicacaoLei(Data.formataData(lei.getDataPublicacao()));
				localForm.setNrDioeLei(Long.toString(lei.getNrDioe()));
			}
		}

		Pagina pagItem = new Pagina(null, null, null);
		request.getSession().setAttribute("listItemDoacao", OperacaoFacade.listarItemDoacao(pagItem, doacao.getCodDoacao()));
		Pagina pagAssinatura = new Pagina(null, null, null);
		request.getSession().setAttribute("listAssinatura", OperacaoFacade.listarAssinaturaDoacao(pagAssinatura, doacao.getCodDoacao()));
		
		localForm.setActionType(validarConfirmacaoTermo(doacao, pagItem, pagAssinatura));
	}

	/**
	 * Finaliza o respectivo Caso de Uso e retorna para o ucs chamador.<br>
	 * Adaptado da BaseAction pois o caso de uso chamador extende de outra Action.<BR>
	 * @author Daniel
	 * @since 19/03/2008
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 * @reuso Adaptado do Sistema GMS
	 */
	public ActionForward concluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
											throws ApplicationException{
		try{
			
			GerarTermoDoacaoBemImovelForm localForm = (GerarTermoDoacaoBemImovelForm)form;
			
			return mapping.findForward(localForm.getUcsRetorno());
			
		}catch (Exception e) {			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".concluir()"}, e, ApplicationException.ICON_ERRO);
		}
	}

	private void verificarGrupoUsuarioLogado(GerarTermoDoacaoBemImovelForm localForm, HttpServletRequest request) throws ApplicationException {
		boolean result = CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo());
		localForm.setIsGpAdmGeralUsuarioLogado("N");
		if (result) {
			localForm.setIsGpAdmGeralUsuarioLogado("S");
		} else {
			String codInstituicao = CadastroFacade.obterInstituicaoUsuario(SentinelaComunicacao.getInstance(request).getCodUsuario()).getCodInstituicao().toString();
			localForm.setInstituicao(codInstituicao);
		}
	}
	
}