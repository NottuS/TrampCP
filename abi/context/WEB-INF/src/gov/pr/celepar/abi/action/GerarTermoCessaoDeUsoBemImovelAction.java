package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.CessaoDeUsoDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.facade.OperacaoFacade;
import gov.pr.celepar.abi.form.GerarTermoCessaoDeUsoBemImovelForm;
import gov.pr.celepar.abi.generico.action.BaseAction;
import gov.pr.celepar.abi.pojo.AssinaturaCessaoDeUso;
import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.abi.pojo.LeiBemImovel;
import gov.pr.celepar.abi.util.Dominios;
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
 * Classe responsavel por permitir a emissao de Termos de Cessao De Uso de Bens Imoveis.<BR>
 * @author vanessak
 * @version 1.0
 */
public class GerarTermoCessaoDeUsoBemImovelAction extends BaseAction {

	/**
	 * Carrega a tela inicial do respectivo Caso de Uso.<br>
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
			GerarTermoCessaoDeUsoBemImovelForm localForm = (GerarTermoCessaoDeUsoBemImovelForm)form;
			verificarGrupoUsuarioLogado(localForm, request);
			
			if(StringUtils.isBlank(localForm.getUcsChamador())){
				String ucsChamador = request.getAttribute("ucsChamador") != null ? (String)request.getAttribute("ucsChamador") : "";			
				localForm.setUcsChamador(ucsChamador);
				localForm.setUcsRetorno(ucsChamador);
				request.setAttribute("ucsChamador", ucsChamador);	
			}
			
			String codCessaoDeUso = null;
			if(StringUtils.isBlank(localForm.getCodCessaoDeUso())){
				codCessaoDeUso = request.getAttribute("codCessaoDeUso") != null ? (String)request.getAttribute("codCessaoDeUso") : "";
			} else{
				codCessaoDeUso = localForm.getCodCessaoDeUso();
			}
			
			CessaoDeUso cessaoDeUso = null;
			if(StringUtils.isNotBlank(codCessaoDeUso)){
				cessaoDeUso = OperacaoFacade.obterCessaoDeUsoCompleto(Integer.valueOf(codCessaoDeUso));
			}
			
			if(cessaoDeUso != null){
				if(Integer.valueOf(statusTermo.RASCUNHO.getIndex()).equals(cessaoDeUso.getStatusTermo().getCodStatusTermo()) ||
					Integer.valueOf(statusTermo.EM_RENOVACAO.getIndex()).equals(cessaoDeUso.getStatusTermo().getCodStatusTermo())){
					populaForm (localForm, cessaoDeUso, request);
				} else {
					if(Integer.valueOf(statusTermo.VIGENTE.getIndex()).equals(cessaoDeUso.getStatusTermo().getCodStatusTermo()) ||
						Integer.valueOf(statusTermo.FINALIZADO.getIndex()).equals(cessaoDeUso.getStatusTermo().getCodStatusTermo())){
						
							localForm.setCodCessaoDeUso(cessaoDeUso.getCodCessaoDeUso().toString());
							localForm.setImprimir("1");
							gerarRelatorio(mapping, localForm, request, response);
							setActionForward(null);
							
					} else if(Integer.valueOf(statusTermo.DEVOLVIDO.getIndex()).equals(cessaoDeUso.getStatusTermo().getCodStatusTermo()) ||
							Integer.valueOf(statusTermo.REVOGADO.getIndex()).equals(cessaoDeUso.getStatusTermo().getCodStatusTermo())){
							
						localForm.setCodCessaoDeUso(cessaoDeUso.getCodCessaoDeUso().toString());
						localForm.setImprimir("2");
						gerarRelatorio(mapping, localForm, request, response);
						setActionForward(null);
					}
				}
			}
			
		} catch (ApplicationException ae) {
			throw ae; 
		}catch (Exception e) {
			setActionForward(concluir(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{GerarTermoCessaoDeUsoBemImovelAction.class.getSimpleName() + ".carregarInterfaceInicial()"}, e, ApplicationException.ICON_ERRO);
		}	
		return getActionForward();
	}
	
	private void populaForm(GerarTermoCessaoDeUsoBemImovelForm localForm, CessaoDeUso cessaoDeUso, HttpServletRequest request) throws ApplicationException {
		localForm.setCodCessaoDeUso(cessaoDeUso.getCodCessaoDeUso().toString());
		localForm.setStatus(Dominios.statusTermo.getStatusTermoByIndex(cessaoDeUso.getStatusTermo().getCodStatusTermo()).getLabel());
		localForm.setCodCessaoDeUso(cessaoDeUso.getCodCessaoDeUso().toString());
		localForm.setCodBemImovel(cessaoDeUso.getBemImovel().getCodBemImovel().toString());
		localForm.setNrBemImovel(cessaoDeUso.getBemImovel().getNrBemImovel().toString());
		localForm.setInstituicao(cessaoDeUso.getInstituicao().getCodInstituicao().toString());
		localForm.setInstituicaoDesc(cessaoDeUso.getInstituicao().getSiglaDescricao());
		localForm.setNumTermo(cessaoDeUso.getNumeroTermo());
		if (cessaoDeUso.getOrgaoCessionario() != null)
			localForm.setOrgao(cessaoDeUso.getOrgaoCessionario().getSiglaDescricao());
		
		localForm.setDtInicioVigencia(cessaoDeUso.getDataInicioVigencia() != null ? Data.formataData(cessaoDeUso.getDataInicioVigencia()) : "");
		localForm.setDtFimVigencia(cessaoDeUso.getDataFinalVigenciaPrevisao() != null ? Data.formataData(cessaoDeUso.getDataFinalVigenciaPrevisao()) : "");
		localForm.setProtocolo(StringUtils.isNotBlank(cessaoDeUso.getProtocolo()) ? cessaoDeUso.getProtocoloFormatado() : "");
		
		//dados da lei
		if (cessaoDeUso.getNumeroProjetoDeLei() != null && cessaoDeUso.getNumeroProjetoDeLei().trim().length() > 0) {
			localForm.setProjetoLei("Sim");
			localForm.setNumeroLei(cessaoDeUso.getNumeroProjetoDeLei().toString());
		} else {
			if (cessaoDeUso.getLeiBemImovel() != null) {
				LeiBemImovel lei = CadastroFacade.obterLeiBemImovel(cessaoDeUso.getLeiBemImovel().getCodLeiBemImovel());
				localForm.setProjetoLei("Não");
				localForm.setCodLei(lei.getCodLeiBemImovel().toString());
				localForm.setNumeroLei(Long.toString(lei.getNumero()));
				localForm.setDataAssinaturaLei(Data.formataData(lei.getDataAssinatura()));
				localForm.setDataPublicacaoLei(Data.formataData(lei.getDataPublicacao()));
				localForm.setNrDioeLei(Long.toString(lei.getNrDioe()));
			}
		}

		Pagina pagItem = new Pagina(null, null, null);
		request.getSession().setAttribute("listItemCessaoDeUso", OperacaoFacade.listarItemCessaoDeUso(pagItem, cessaoDeUso.getCodCessaoDeUso()));
		Pagina pagAssinatura = new Pagina(null, null, null);
		request.getSession().setAttribute("listAssinatura", OperacaoFacade.listarAssinaturaCessaoDeUso(pagAssinatura, cessaoDeUso.getCodCessaoDeUso()));
		
		localForm.setActionType(validarConfirmacaoTermo(cessaoDeUso, pagItem, pagAssinatura));
	}

	private String validarConfirmacaoTermo(CessaoDeUso cessaoDeUso, Pagina pagItem, Pagina pagAssinatura) {
		boolean test = false;
		if (cessaoDeUso.getBemImovel() != null && cessaoDeUso.getBemImovel().getCodBemImovel() > 0 &&
			cessaoDeUso.getOrgaoCessionario() != null && cessaoDeUso.getOrgaoCessionario().getCodOrgao() > 0 &&
			cessaoDeUso.getDataInicioVigencia() != null && cessaoDeUso.getDataFinalVigenciaPrevisao() != null &&
			cessaoDeUso.getProtocolo() != null && cessaoDeUso.getProtocolo().trim().length() > 0 &&
			cessaoDeUso.getLeiBemImovel() != null && cessaoDeUso.getLeiBemImovel().getCodLeiBemImovel() > 0 &&
			cessaoDeUso.getLeiBemImovel().getDataAssinatura() != null && cessaoDeUso.getLeiBemImovel().getDataPublicacao() != null &&
			cessaoDeUso.getLeiBemImovel().getNrDioe() > 0 && cessaoDeUso.getLeiBemImovel().getNumero() > 0) {
			test = true;
		}
		
		if (test && pagItem.getQuantidade() > 0 && pagAssinatura.getQuantidade() > 0) {
			return "confirmar";
		}
		return "visualizar";
	}

	/**
	 * Gera o relatorio.<BR>
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward gerarRelatorio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		GerarTermoCessaoDeUsoBemImovelForm localForm = (GerarTermoCessaoDeUsoBemImovelForm)form;
		setActionForward(mapping.findForward(PG_VIEW));

		try {
			verificarGrupoUsuarioLogado(localForm, request);
			String codCessaoDeUso = StringUtils.isNotBlank(localForm.getCodCessaoDeUso()) ? localForm.getCodCessaoDeUso() : null;
			String tipoRelatorio = null;
			CessaoDeUso cessaoDeUso = null;
			if(StringUtils.isNotBlank(codCessaoDeUso)){
				cessaoDeUso = OperacaoFacade.obterCessaoDeUsoCompleto(Integer.valueOf(codCessaoDeUso));
			}

			if(Integer.valueOf(statusTermo.VIGENTE.getIndex()).equals(cessaoDeUso.getStatusTermo().getCodStatusTermo()) ||
				Integer.valueOf(statusTermo.FINALIZADO.getIndex()).equals(cessaoDeUso.getStatusTermo().getCodStatusTermo()) ||
				Integer.valueOf(statusTermo.RASCUNHO.getIndex()).equals(cessaoDeUso.getStatusTermo().getCodStatusTermo()) ||
				Integer.valueOf(statusTermo.EM_RENOVACAO.getIndex()).equals(cessaoDeUso.getStatusTermo().getCodStatusTermo())){
				tipoRelatorio = "1";
			} else if(Integer.valueOf(statusTermo.DEVOLVIDO.getIndex()).equals(cessaoDeUso.getStatusTermo().getCodStatusTermo()) ||
					Integer.valueOf(statusTermo.REVOGADO.getIndex()).equals(cessaoDeUso.getStatusTermo().getCodStatusTermo())){
				tipoRelatorio = "2";
			}
			
			Map<String, Object> parametros = new HashMap<String, Object>();

			//popula objeto para ser impresso o relatorio
			List<CessaoDeUsoDTO> listaCessaoDeUso = OperacaoFacade.listarTermoCessaoDeUso(cessaoDeUso);
			
			String path = request.getSession().getServletContext().getRealPath("");
			String image1 =null;
			if (cessaoDeUso.getInstituicao()!= null){
				image1 = path + File.separator + "images" + File.separator +"logo" + File.separator+cessaoDeUso.getInstituicao().getCodInstituicao()+cessaoDeUso.getInstituicao().getLogoInstituicao();
				parametros.put("descricaoInstituicao", cessaoDeUso.getInstituicao().getDescricaoRelatorio());
			}else{
				parametros.put("descricaoInstituicao", "Geral");
			}
			String image2 = path + File.separator + "images" + File.separator + "logo_sistema_relatorio.jpg";

			//Termo Vigente/Renovado/Finalizado/Rascunho/Em Renovacao
			if(StringUtils.isNotBlank(tipoRelatorio) && ("1").equals(tipoRelatorio)){
				parametros.put("nomeRelatorioJasper", "GerarTermoCessaoDeUso1.jasper");
				parametros.put("pathSubRelatorio", Dominios.PATH_RELATORIO +"SubGerarTermoCessaoDeUsoItem.jasper");
				parametros.put("pathSubRelatorioAssinaturas", Dominios.PATH_RELATORIO +"SubGerarTermoCessaoDeUsoAssinatura.jasper");
				
				if(cessaoDeUso.getCessaoDeUsoOriginal() == null){
					parametros.put("tituloRelatorio", " TERMO DE CESSÃO DE USO DE IMÓVEL Nº " + listaCessaoDeUso.get(0).getNumeroTermo());
				}else{
					parametros.put("tituloRelatorio", " TERMO DE RENOVAÇÃO DE CESSÃO DE USO DE IMÓVEL Nº " + listaCessaoDeUso.get(0).getNumeroTermo());
				}
				parametros.put("txtAutRevog", "Autorização concedida pela Lei Estadual nº " + 
						(StringUtils.isNotBlank(listaCessaoDeUso.get(0).getNumeroLei()) ? listaCessaoDeUso.get(0).getNumeroLei() : "*") + 
						" de " + (StringUtils.isNotBlank(listaCessaoDeUso.get(0).getDataPublicacaoLei()) ? listaCessaoDeUso.get(0).getDataPublicacaoLei() : "*") + 
						" (DIOE nº " + (StringUtils.isNotBlank(listaCessaoDeUso.get(0).getNumeroDioe()) ? listaCessaoDeUso.get(0).getNumeroDioe() : "*") + 
						" de " + (StringUtils.isNotBlank(listaCessaoDeUso.get(0).getDataPublicacaoDioe()) ? listaCessaoDeUso.get(0).getDataPublicacaoDioe() : "*") + ").");
			//Termo Revogacao/Devolucao
			} else if(StringUtils.isNotBlank(tipoRelatorio) && ("2").equals(tipoRelatorio)){
				parametros.put("nomeRelatorioJasper", "GerarTermoCessaoDeUso2.jasper");
				parametros.put("pathSubRelatorio", Dominios.PATH_RELATORIO +"SubGerarTermoCessaoDeUsoItem.jasper");
				parametros.put("pathSubRelatorioAssinaturas", Dominios.PATH_RELATORIO +"SubGerarTermoCessaoDeUsoAssinatura.jasper");
				
				parametros.put("tituloRelatorio", " TERMO DE " + 
					Dominios.statusTermoRel.getStatusTermoByIndex(cessaoDeUso.getStatusTermo().getCodStatusTermo()).getLabel().toUpperCase() + 
					" DE CESSÃO DE BEM IMÓVEL Nº " + listaCessaoDeUso.get(0).getNumeroTermo());
				parametros.put("txtAutRevog", "Pelo presente termo torna-se revogada a cessão de bem imóvel concedida pela Lei Estadual n° " + 
					(StringUtils.isNotBlank(listaCessaoDeUso.get(0).getNumeroLei()) ? listaCessaoDeUso.get(0).getNumeroLei() : "*") + ".");
			}
			
			parametros.put("statusTermo", Dominios.statusTermo.getStatusTermoByIndex(cessaoDeUso.getStatusTermo().getCodStatusTermo()).getLabel());
			parametros.put("pathCabecalho", Dominios.PATH_RELATORIO + "CabecalhoPadraoRetrato.jasper");
			parametros.put("image1", image1);
			parametros.put("image2", image2); 
			
			setActionForward(null);
			RelatorioIReportAction.processarRelatorio(listaCessaoDeUso, parametros, form, request, mapping, response);

		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward(localForm.getUcsRetorno()));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward(localForm.getUcsRetorno()));
			throw new ApplicationException("ERRO.201", new String[]{"na geração do Termo de Cessão de Uso do Bem Imóvel"}, e);
		}		
		return getActionForward();

	}
	
	/**
	 * Realiza a confirmacao do Termo de CessaoDeUso Definitivo.<BR>
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward confirmarTermoCessaoDeUsoDefinitivo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		GerarTermoCessaoDeUsoBemImovelForm localForm = (GerarTermoCessaoDeUsoBemImovelForm)form;
		
		setActionForward(mapping.findForward(PG_VIEW));

		try {
			verificarGrupoUsuarioLogado(localForm, request);
			
			String codCessaoDeUso = StringUtils.isNotBlank(localForm.getCodCessaoDeUso()) ? localForm.getCodCessaoDeUso() : null;
			
			CessaoDeUso cessaoDeUso = null;
			if(StringUtils.isNotBlank(codCessaoDeUso)){
				cessaoDeUso = OperacaoFacade.obterCessaoDeUsoCompleto(Integer.valueOf(codCessaoDeUso));
			}
			populaForm(localForm, cessaoDeUso, request);
			if(cessaoDeUso != null){
				Integer qtd = obtemQtdChefeCpeListaAssinatura(cessaoDeUso.getListaAssinaturaCessaoDeUso());
				if (qtd.intValue() < 1) {
					throw new ApplicationException("AVISO.101", ApplicationException.ICON_AVISO);
				}
				if (qtd.intValue() > 1) {
					throw new ApplicationException("AVISO.102", ApplicationException.ICON_AVISO);
				}
				Boolean valido = CadastroFacade.validaValoresInformados(cessaoDeUso.getBemImovel().getCodBemImovel(), cessaoDeUso.getCodCessaoDeUso(), Integer.valueOf(1));
				if (!valido) {
					throw new ApplicationException("AVISO.67", ApplicationException.ICON_AVISO);
				}

				String retorno = OperacaoFacade.validaDisponibilidade(cessaoDeUso.getBemImovel().getCodBemImovel(), cessaoDeUso.getCodCessaoDeUso(), Integer.valueOf(1));
				if (retorno != null && retorno.length() > 0) {
					throw new ApplicationException("AVISO.68", new String[]{retorno}, ApplicationException.ICON_AVISO);
				}

				if(cessaoDeUso.getDataInicioVigencia() != null && cessaoDeUso.getDataInicioVigencia().after(new Date())){  //dataInicioVigencia > dataAtual
					throw new ApplicationException("AVISO.64", new String[]{"Cessão de Uso", Data.formataData(cessaoDeUso.getDataInicioVigencia(), "dd/MM/yyyy")}, ApplicationException.ICON_AVISO);
				}

				String result = OperacaoFacade.verificarCessaoTotalBemImovel(cessaoDeUso);
				if (result != null && result.length() > 0) {
					throw new ApplicationException("AVISO.51", new String[]{result}, ApplicationException.ICON_AVISO);
				}
				
				SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
				
				cessaoDeUso.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.VIGENTE.getIndex()));
				cessaoDeUso.setTsAlteracao(new Date());
				cessaoDeUso.setDataRegistro(new Date());
				cessaoDeUso.setCpfResponsavelAlteracao(sentinelaInterface.getCpf());
				
				OperacaoFacade.salvarCessaoDeUso(cessaoDeUso);
				
			}
			
			addMessage("SUCESSO.38", new String[]{"Cessão de Uso", cessaoDeUso.getCodCessaoDeUso().toString()}, request);
			
			setActionForward(mapping.findForward(localForm.getUcsRetorno()));

		} catch (ApplicationException ae) {
			throw ae; 
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.geral", new String[]{GerarTermoCessaoDeUsoBemImovelAction.class.getSimpleName() + ".confirmarTermoCessaoDeUsoDefinitivo()"}, e, ApplicationException.ICON_ERRO);
		}	
		return getActionForward();

	}
	
	private Integer obtemQtdChefeCpeListaAssinatura(Set<AssinaturaCessaoDeUso> listaAssinaturaCessaoDeUso) {
		int qtd = 0;
		for (AssinaturaCessaoDeUso assinatura : listaAssinaturaCessaoDeUso) {
			if(assinatura.getAssinatura().getCargoAssinatura().getDescricao().contains("CPE")){
				qtd++;
			}
		}
		return qtd;
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
			
			GerarTermoCessaoDeUsoBemImovelForm localForm = (GerarTermoCessaoDeUsoBemImovelForm)form;
			
			return mapping.findForward(localForm.getUcsRetorno());
			
		}catch (Exception e) {			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".concluir()"}, e, ApplicationException.ICON_ERRO);
		}
	}

	private void verificarGrupoUsuarioLogado(GerarTermoCessaoDeUsoBemImovelForm localForm, HttpServletRequest request) throws ApplicationException {
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