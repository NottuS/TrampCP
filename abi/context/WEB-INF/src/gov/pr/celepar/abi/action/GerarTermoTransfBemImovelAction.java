package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.TransferenciaDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.facade.OperacaoFacade;
import gov.pr.celepar.abi.form.GerarTermoTransfBemImovelForm;
import gov.pr.celepar.abi.generico.action.BaseAction;
import gov.pr.celepar.abi.pojo.AssinaturaDocTransferencia;
import gov.pr.celepar.abi.pojo.AssinaturaTransferencia;
import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.abi.pojo.Documentacao;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.ItemTransferencia;
import gov.pr.celepar.abi.pojo.Transferencia;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Dominios.statusTermo;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.mensagem.Mensagem;
import gov.pr.celepar.framework.util.Data;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
 * Classe responsavel por permitir a emissao de Termos de Transferencia de Bens Imoveis.<BR>
 * @author ginaalmeida
 * @version 1.0
 * @since 01/08/2011
 */
public class GerarTermoTransfBemImovelAction extends BaseAction {

	/**
	 * Carrega a tela inicial do respectivo Caso de Uso.<br>
	 * @author ginaalmeida
	 * @since 02/08/2011
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
			
			GerarTermoTransfBemImovelForm localForm = (GerarTermoTransfBemImovelForm)form;
			verificarGrupoUsuarioLogado(localForm, request);
			
			if(StringUtils.isBlank(localForm.getUcsChamador())){
				String ucsChamador = request.getAttribute("ucsChamador") != null ? (String)request.getAttribute("ucsChamador") : "";			
				localForm.setUcsChamador(ucsChamador);
				localForm.setUcsRetorno(ucsChamador);
				request.setAttribute("ucsChamador", ucsChamador);	
			}
			
			String codTransferencia = null;
			if(StringUtils.isBlank(localForm.getCodTransferencia())){
				codTransferencia = request.getAttribute("codTransferencia") != null ? (String)request.getAttribute("codTransferencia") : "";
			} else{
				codTransferencia = localForm.getCodTransferencia();
			}
			
			Transferencia transferencia = null;
			if(StringUtils.isNotBlank(codTransferencia)){
				transferencia = OperacaoFacade.obterTransferenciaCompleto(Integer.valueOf(codTransferencia));
			}
			
			if(transferencia != null){
				if(Integer.valueOf(statusTermo.RASCUNHO.getIndex()).equals(transferencia.getStatusTermo().getCodStatusTermo())){
					populaForm(localForm, transferencia, request);
				} else if(Integer.valueOf(statusTermo.VIGENTE.getIndex()).equals(transferencia.getStatusTermo().getCodStatusTermo()) || 
						Integer.valueOf(statusTermo.FINALIZADO.getIndex()).equals(transferencia.getStatusTermo().getCodStatusTermo())){
					
					localForm.setCodTransferencia(transferencia.getCodTransferencia().toString());
					localForm.setImprimir("1");
					gerarRelatorio(mapping, localForm, request, response);
					setActionForward(null);
					
				} else if(Integer.valueOf(statusTermo.DEVOLVIDO.getIndex()).equals(transferencia.getStatusTermo().getCodStatusTermo()) || 
						Integer.valueOf(statusTermo.REVOGADO.getIndex()).equals(transferencia.getStatusTermo().getCodStatusTermo())){
					
					localForm.setCodTransferencia(transferencia.getCodTransferencia().toString());
					localForm.setImprimir("2");
					gerarRelatorio(mapping, localForm, request, response);
					setActionForward(null);
					
				}
			}
			
		} catch (ApplicationException ae) {
			throw ae; 
		}catch (Exception e) {
			setActionForward(concluir(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.geral", new String[]{GerarTermoTransfBemImovelAction.class.getSimpleName() + ".carregarInterfaceInicial()"}, e, ApplicationException.ICON_ERRO);
		}	
		return getActionForward();
	}
	
	private String validarConfirmacaoTermo(Transferencia transferencia, Pagina pagItem, Pagina pagAssinatura) {
		boolean test = false;
		if (transferencia.getBemImovel() != null && transferencia.getBemImovel().getCodBemImovel() > 0 &&
				transferencia.getOrgaoCessionario() != null && transferencia.getOrgaoCessionario().getCodOrgao() > 0 &&
				transferencia.getDtInicioVigencia() != null && transferencia.getProtocolo() != null && 
				transferencia.getProtocolo().trim().length() > 0) {
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
	 * @since 02/08/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward gerarRelatorio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		GerarTermoTransfBemImovelForm localForm = (GerarTermoTransfBemImovelForm)form;

		try {
			verificarGrupoUsuarioLogado(localForm, request);
			String codTransferencia = StringUtils.isNotBlank(localForm.getCodTransferencia()) ? localForm.getCodTransferencia() : null;
			String tipoRelatorio = null;
			Transferencia transferencia = null;
			if(StringUtils.isNotBlank(codTransferencia)){
				transferencia = OperacaoFacade.obterTransferenciaCompleto(Integer.valueOf(codTransferencia));
			}
			if(Integer.valueOf(statusTermo.VIGENTE.getIndex()).equals(transferencia.getStatusTermo().getCodStatusTermo()) ||
					Integer.valueOf(statusTermo.FINALIZADO.getIndex()).equals(transferencia.getStatusTermo().getCodStatusTermo()) ||
					Integer.valueOf(statusTermo.RASCUNHO.getIndex()).equals(transferencia.getStatusTermo().getCodStatusTermo())){
					tipoRelatorio = "1";
			} else if(Integer.valueOf(statusTermo.DEVOLVIDO.getIndex()).equals(transferencia.getStatusTermo().getCodStatusTermo()) ||
					Integer.valueOf(statusTermo.REVOGADO.getIndex()).equals(transferencia.getStatusTermo().getCodStatusTermo())){
				tipoRelatorio = "2";
			}
			
			Map<String, Object> parametros = new HashMap<String, Object>();

			//popula objeto para ser impresso o relatorio
			List<TransferenciaDTO> listaTransferencia = OperacaoFacade.listarTermoTransferencia(transferencia);
			
			String path = request.getSession().getServletContext().getRealPath("");
			String image1 = null;
			if (transferencia.getInstituicao()!= null){
				image1 = path + File.separator + "images" + File.separator +"logo" + File.separator+transferencia.getInstituicao().getCodInstituicao()+transferencia.getInstituicao().getLogoInstituicao();
				parametros.put("descricaoInstituicao", transferencia.getInstituicao().getDescricaoRelatorio());
			}else{
				parametros.put("descricaoInstituicao", "Geral");
			}
			String image2 = path + File.separator + "images" + File.separator + "logo_sistema_relatorio.jpg";

			//Termo Vigente/Renovado/Finalizado/Rascunho/Em Renovacao
			if(StringUtils.isNotBlank(tipoRelatorio) && ("1").equals(tipoRelatorio)){
				parametros.put("tituloRelatorio", " TERMO DE TRANSFERÊNCIA DE IMÓVEL Nº " + listaTransferencia.get(0).getNumeroAnoTermo());
			//Termo Revogacao/Devolucao
			} else if(StringUtils.isNotBlank(tipoRelatorio) && ("2").equals(tipoRelatorio)){
				parametros.put("tituloRelatorio", "TERMO DE " + 
					Dominios.statusTermoRel.getStatusTermoByIndex(transferencia.getStatusTermo().getCodStatusTermo()).getLabel().toUpperCase() + 
					" DE TRANSFERÊNCIA DE IMÓVEL Nº " + listaTransferencia.get(0).getNumeroAnoTermo());
			}

			parametros.put("nomeRelatorioJasper", "GerarTermoTransferenciaBemImovel.jasper");
			parametros.put("pathSubRelatorio", Dominios.PATH_RELATORIO +"SubGerarTermoTransferenciaBemImovel.jasper");
			parametros.put("pathSubRelatorioAssinaturas", Dominios.PATH_RELATORIO +"SubGerarTermoTransferenciaBemImovelAssinaturas.jasper");
			parametros.put("pathSubRelatorioAssinaturasDoc", Dominios.PATH_RELATORIO +"SubGerarTermoTransferenciaBemImovelAssinaturasDoc.jasper");
			parametros.put("pathSubRelatorioDocInf", Dominios.PATH_RELATORIO +"SubGerarDocInformacaoTransferenciaBI.jasper");
			parametros.put("pathCabecalho", Dominios.PATH_RELATORIO + "CabecalhoPadraoRetrato2.jasper");
			parametros.put("tipoRelatorio", Dominios.statusTermo.getStatusTermoByIndex(transferencia.getStatusTermo().getCodStatusTermo()).getLabel());
			parametros.put("image1", image1);
			parametros.put("image2", image2); 
			
			RelatorioIReportAction.processarRelatorio(listaTransferencia, parametros, form, request, mapping, response);
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward(localForm.getUcsRetorno()));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward(localForm.getUcsRetorno()));
			throw new ApplicationException("ERRO.201", new String[]{"na geração do Termo de Transferência do Bem Imóvel"}, e);
		}		
		return getActionForward();

	}
	
	/**
	 * Realiza a confirmacao do Termo de Transferencia Definitivo.<BR>
	 * @author ginaalmeida
	 * @since 02/08/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward confirmarTermoTransferenciaDefinitivo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		GerarTermoTransfBemImovelForm localForm = (GerarTermoTransfBemImovelForm)form;
		setActionForward(mapping.findForward(PG_VIEW));
		
		Transferencia transferencia = null;
		try {
			verificarGrupoUsuarioLogado(localForm, request);
			String codTransferencia = StringUtils.isNotBlank(localForm.getCodTransferencia()) ? localForm.getCodTransferencia() : null;
			
			if(StringUtils.isNotBlank(codTransferencia)){
				transferencia = OperacaoFacade.obterTransferenciaCompleto(Integer.valueOf(codTransferencia));
			}
			
			if(transferencia != null){
				Integer qtdCpe = obtemQtdChefeCpeListaAssinatura(transferencia.getListaAssinaturaTransferencia());
				if (qtdCpe.intValue() < 1) {
					throw new ApplicationException("AVISO.101", ApplicationException.ICON_AVISO);
				}
				if (qtdCpe.intValue() > 1) {
					throw new ApplicationException("AVISO.102", ApplicationException.ICON_AVISO);
				}
				Boolean valido = CadastroFacade.validaValoresInformados(transferencia.getBemImovel().getCodBemImovel(), transferencia.getCodTransferencia(), Integer.valueOf(3));
				if (!valido) {
					throw new ApplicationException("AVISO.67", ApplicationException.ICON_AVISO);
				}

				AssinaturaDocTransferencia aux = new AssinaturaDocTransferencia();
				aux.setTransferencia(transferencia);
				Integer qtd = OperacaoFacade.obtemQtdResponsavelMaximoListaAssinatura(aux);
				if (qtd.intValue() < 1) {
					throw new ApplicationException("AVISO.101", ApplicationException.ICON_AVISO);
				}
				if (qtd.intValue() > 1) {
					throw new ApplicationException("AVISO.102", ApplicationException.ICON_AVISO);
				}

				String retorno = OperacaoFacade.validaDisponibilidade(transferencia.getBemImovel().getCodBemImovel(), transferencia.getCodTransferencia(), Integer.valueOf(3));
				if (retorno != null && retorno.length() > 0) {
					throw new ApplicationException("AVISO.68", new String[]{retorno}, ApplicationException.ICON_AVISO);
				}

				if(transferencia.getDtInicioVigencia() != null && transferencia.getDtInicioVigencia().after(new Date())){  //dataInicioVigencia > dataAtual
					throw new ApplicationException("AVISO.64", new String[]{"Transferência", Data.formataData(transferencia.getDtInicioVigencia(), "dd/MM/yyyy")}, ApplicationException.ICON_AVISO);
				}

				String result = null;
				if(transferencia.getBemImovel() != null && StringUtils.isNotBlank(transferencia.getBemImovel().getNumero())){
					CessaoDeUso cessaoDeUso = new CessaoDeUso();
					cessaoDeUso.setCodCessaoDeUso(Integer.valueOf(0));
					cessaoDeUso.setBemImovel(transferencia.getBemImovel());
					cessaoDeUso.setInstituicao(transferencia.getInstituicao());
					result = OperacaoFacade.verificarCessaoTotalBemImovel(cessaoDeUso);
				}
				if (result != null && result.length() > 0) {
					throw new ApplicationException("AVISO.51",  new String[]{result}, ApplicationException.ICON_AVISO);
				}
				
				SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
				
				transferencia.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.VIGENTE.getIndex()));
				transferencia.setTsAtualizacao(new Date());
				transferencia.setDataRegistro(new Date());
				transferencia.setCpfResponsavelAlteracao(sentinelaInterface.getCpf());
				transferencia.setTextoDocInformacao(localForm.getTextoDocInformacao());

				OperacaoFacade.salvarTransferencia(transferencia);
				
			}
			
			addMessage("SUCESSO.38", new String[]{"Transferência", transferencia.getCodTransferencia().toString()}, request);
			
			setActionForward(mapping.findForward(localForm.getUcsRetorno()));
		} catch (ApplicationException ae) {
			populaForm(localForm, transferencia, request);
			throw ae; 
		}catch (Exception e) {
			populaForm(localForm, transferencia, request);
			throw new ApplicationException("mensagem.erro.geral", new String[]{GerarTermoTransfBemImovelAction.class.getSimpleName() + ".confirmarTermoTransferenciaDefinitivo()"}, e, ApplicationException.ICON_ERRO);
		}	
		return getActionForward();

	}
	
	private Integer obtemQtdChefeCpeListaAssinatura(Set<AssinaturaTransferencia> listaAssinaturaTransferencia) {
		int qtd = 0;
		for (AssinaturaTransferencia assinatura : listaAssinaturaTransferencia) {
			if(assinatura.getAssinatura().getCargoAssinatura().getDescricao().contains("CPE")){
				qtd++;
			}
		}
		return qtd;
	}

	private void populaForm(GerarTermoTransfBemImovelForm localForm, Transferencia transferencia, HttpServletRequest request) throws ApplicationException {
		try {
			localForm.setCodTransferencia(transferencia.getCodTransferencia().toString());
			localForm.setStatus(Dominios.statusTermo.getStatusTermoByIndex(transferencia.getStatusTermo().getCodStatusTermo()).getLabel());
			localForm.setCodTransferencia(transferencia.getCodTransferencia().toString());
			localForm.setCodBemImovel(transferencia.getBemImovel().getCodBemImovel().toString());
			localForm.setNrBemImovel(transferencia.getBemImovel().getNrBemImovel().toString());
			localForm.setInstituicao(transferencia.getInstituicao().getCodInstituicao().toString());
			localForm.setInstituicaoDesc(transferencia.getInstituicao().getSiglaDescricao());
			localForm.setNumTermo(transferencia.getNumeroTermo().toString());
			if (transferencia.getOrgaoCessionario() != null)
				localForm.setOrgao(transferencia.getOrgaoCessionario().getSiglaDescricao());

			localForm.setDtInicioVigencia(transferencia.getDtInicioVigencia() != null ? Data.formataData(transferencia.getDtInicioVigencia()) : "");
			localForm.setDtFimVigencia(transferencia.getDtFimVigencia() != null ? Data.formataData(transferencia.getDtFimVigencia()) : "");
			localForm.setProtocolo(StringUtils.isNotBlank(transferencia.getProtocolo()) ? transferencia.getProtocoloFormatado() : "");

			StringBuffer texto = new StringBuffer();
			texto.append("Senhor Secretário,");
			texto.append("\n \n");
			texto.append("Tendo em vista a solicitação da ");
			texto.append(transferencia.getOrgaoCessionario().getSigla());
			texto.append(" de transferência de uso do imóvel situado");
			if (transferencia.getBemImovel().getBairroDistrito() != null && transferencia.getBemImovel().getBairroDistrito().trim().length() > 0) {
				texto.append(" no lugar denominado ");
				texto.append(transferencia.getBemImovel().getBairroDistrito());
			}
			texto.append(" no município de ");
			texto.append(transferencia.getBemImovel().getMunicipio().trim());
			if (transferencia.getBemImovel().getAreaTerreno() != null && !transferencia.getBemImovel().getAreaTerreno().equals(new BigDecimal(0)) && 
				transferencia.getBemImovel().getAreaConstruida() != null && !transferencia.getBemImovel().getAreaConstruida().equals(new BigDecimal(0))) {
				texto.append(", com área do terreno de ");
				texto.append(transferencia.getBemImovel().getAreaTerrenoFormatado());
				texto.append(" m²  e com área construída de ");
				texto.append(transferencia.getBemImovel().getAreaConstruidaFormatado());
				texto.append(" m²");
			} else {
				if (transferencia.getBemImovel().getAreaTerreno() != null && !transferencia.getBemImovel().getAreaTerreno().equals(new BigDecimal(0))) {
					texto.append(", com área do terreno de ");
					texto.append(transferencia.getBemImovel().getAreaTerrenoFormatado());
					texto.append(" m²");
				}
				if (transferencia.getBemImovel().getAreaConstruida() != null && !transferencia.getBemImovel().getAreaConstruida().equals(new BigDecimal(0))) {
					texto.append(", com área construída de ");
					texto.append(transferencia.getBemImovel().getAreaConstruidaFormatado());
					texto.append(" m²");
				}
			}
			texto.append(".");
			
			Collection<ItemTransferencia> list = OperacaoFacade.listarItemTransferencia(transferencia.getCodTransferencia());
			boolean total = false;
			texto.append(" Sendo realizada a Transferência ");
			for (ItemTransferencia item : list) {
				if (item.getTipo().equals(Dominios.tipoOperacaoBemImovel.TOTAL.getIndex())) {
					total = true;
					texto.append("Total do Bem Imóvel. ");
				} 
				if (item.getTipo().equals(Dominios.tipoOperacaoBemImovel.TERRENO.getIndex())){
					texto.append("da área do terreno de ");
					texto.append(item.getTransferenciaMetrosFormatado());
					texto.append(" m², correspondendo à ");
					texto.append(item.getTransferenciaPercentualFormatado());
					texto.append(" % do total. ");
				} 
				if (item.getTipo().equals(Dominios.tipoOperacaoBemImovel.EDIFICACAO.getIndex())){
					texto.append("da área da edificação de ");
					texto.append(item.getTransferenciaMetrosFormatado());
					texto.append(" m², correspondendo à ");
					texto.append(item.getTransferenciaPercentualFormatado());
					texto.append(" % do total. ");
				}
			}
			
			texto.append("\n \n");
			texto.append("Propomos a formalização do respectivo Termo de Transferência do Imóvel àquela pasta. ");
			texto.append("\n \n");
			texto.append("Por tratar-se de unidade da administração direta, a carga patrimonial é simplesmente transferida, ");
			texto.append("mediante o Termo de Transferência n° ");
			texto.append(transferencia.getNumeroTermo());
			texto.append(" que, em três vias, segue para as devidas assinaturas. ");
			texto.append("\n \n");
			texto.append("O imóvel ora transferido");
			
			List<Documentacao> listDoc = CadastroFacade.listarDocumentacaoSemOcorrencia(transferencia.getBemImovel().getCodBemImovel());
			if (listDoc != null && listDoc.size() > 0) {
				texto.append(" e ");
			} else {
				texto.append(", ");
			}

			for (Documentacao doc : listDoc) {
				if (doc.getCartorio() != null && doc.getCartorio().getCodCartorio() > 0) {
					texto.append("descrito na ");
					texto.append(doc.getNumeroDocumentoCartorial());
					texto.append(" registrada em ");
					texto.append(doc.getCartorio().getDescricao()).append(", ");
				}
				if (doc.getTabelionato() != null && doc.getTabelionato().getCodTabelionato() > 0) {
					texto.append("descrito na ");
					texto.append(doc.getNumeroDocumentoTabelional());
					texto.append(" registrada em ");
					texto.append(doc.getTabelionato().getDescricao()).append(", ");
				}
			}
			
			texto.append("destina-se ");
			if (total) {
				texto.append("exclusivamente ");
			}
			texto.append("para uso de ");
			texto.append(transferencia.getOrgaoCessionario().getSigla());
			texto.append(".");
			texto.append("\n \n");
			texto.append("Solicitamos o retorno posterior do processo, para as providências finais.");
			
			localForm.setTextoDocInformacao(texto.toString());
			
			Pagina pagItem = new Pagina(null, null, null);
			request.getSession().setAttribute("listaItemTransferencia", OperacaoFacade.listarItemTransferencia(pagItem, transferencia.getCodTransferencia()));
			Pagina pagAssinatura = new Pagina(null, null, null);
			request.getSession().setAttribute("listaAssinatura", OperacaoFacade.listarAssinaturaTransferencia(pagAssinatura, transferencia.getCodTransferencia()));
			Pagina pagAssinaturaDoc = new Pagina(null, null, null);
			request.getSession().setAttribute("listAssinaturaDoc", OperacaoFacade.listarAssinaturaDocTransferencia(pagAssinaturaDoc, transferencia));
			
			Instituicao instituicao = CadastroFacade.obterInstituicao(Integer.valueOf(localForm.getInstituicao()));

			request.getSession().setAttribute("nomesAssinatura", Util.htmlEncodeCollection(CadastroFacade.listarAssinaturasComboByInstituicao(instituicao))); 

			localForm.setActionType(validarConfirmacaoTermo(transferencia, pagItem, pagAssinatura));
		} catch (ApplicationException ae) {
			throw ae; 
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.geral", new String[]{GerarTermoTransfBemImovelAction.class.getSimpleName() + ".populaForm()"}, e, ApplicationException.ICON_ERRO);
		}
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
	public ActionForward concluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException{
		
		try{
			GerarTermoTransfBemImovelForm localForm = (GerarTermoTransfBemImovelForm)form;
			return mapping.findForward(localForm.getUcsRetorno());
			
		}catch (Exception e) {			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".concluir()"}, e, ApplicationException.ICON_ERRO);
		}
	}

	private void verificarGrupoUsuarioLogado(GerarTermoTransfBemImovelForm localForm, HttpServletRequest request) throws ApplicationException {
		boolean result = CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo());
		localForm.setIsGpAdmGeralUsuarioLogado("N");
		if (result) {
			localForm.setIsGpAdmGeralUsuarioLogado("S");
		} else {
			String codInstituicao = CadastroFacade.obterInstituicaoUsuario(SentinelaComunicacao.getInstance(request).getCodUsuario()).getCodInstituicao().toString();
			localForm.setInstituicao(codInstituicao);
		}
	}

	/**
	 * Realiza o encaminhamento necessário para adicionar uma assinatura.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward adicionarAssinatura(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método adicionarAssinatura processando...");
		GerarTermoTransfBemImovelForm localForm = (GerarTermoTransfBemImovelForm)form;
		setActionForward(mapping.findForward(PG_VIEW));
		
		Transferencia transferencia = null;
		try {
			verificarGrupoUsuarioLogado(localForm, request);
			String codTransferencia = StringUtils.isNotBlank(localForm.getCodTransferencia()) ? localForm.getCodTransferencia() : null;
			
			if(StringUtils.isNotBlank(codTransferencia)){
				transferencia = OperacaoFacade.obterTransferenciaCompleto(Integer.valueOf(codTransferencia));
			}
			
			if(transferencia != null){
				AssinaturaDocTransferencia assinatura = new AssinaturaDocTransferencia();
				assinatura.setTransferencia(transferencia);
				assinatura.setAssinatura(CadastroFacade.obterAssinatura(Integer.valueOf(localForm.getCodAssinatura())));
				
				AssinaturaDocTransferencia assinaturaAux = assinatura;
				boolean mesmoObjeto = false;
				// Verifica se já existe 
				Collection<AssinaturaDocTransferencia> listDuplicidadeAD = OperacaoFacade.verificarDuplicidadeAssinaturaDocTransferencia(assinatura); 
				if (listDuplicidadeAD.size() > 0) {
					AssinaturaDocTransferencia assinaturaDB;
					//verifica se é a mesma assinatura
					for (Iterator<AssinaturaDocTransferencia> iterator  = listDuplicidadeAD.iterator(); iterator.hasNext();) {
						assinaturaDB = (AssinaturaDocTransferencia) iterator .next();
						if (assinaturaDB.getAssinatura().getCodAssinatura().equals(assinaturaAux.getAssinatura().getCodAssinatura())) {
							mesmoObjeto = true;
						}
					}
					if (mesmoObjeto) {
						throw new ApplicationException("AVISO.55", ApplicationException.ICON_AVISO);
					}
				}

				OperacaoFacade.salvarAssinaturaDocTransferencia(assinatura);
				
			}
			
			localForm.setCodAssinatura(null);
			populaForm(localForm, transferencia, request);
			addMessage("SUCESSO.25", request);
			
		} catch (ApplicationException ae) {
			populaForm(localForm, transferencia, request);
			throw ae; 
		}catch (Exception e) {
			populaForm(localForm, transferencia, request);
			throw new ApplicationException("mensagem.erro.geral", new String[]{GerarTermoTransfBemImovelAction.class.getSimpleName() + ".confirmarTermoTransferenciaDefinitivo()"}, e, ApplicationException.ICON_ERRO);
		}	
		return getActionForward();
		
	}

	/**
	 * Realiza o encaminhamento necessário para excluir uma assinatura.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward excluirAssinatura(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		log.info("Método excluirAssinatura processando...");
		GerarTermoTransfBemImovelForm localForm = (GerarTermoTransfBemImovelForm)form;
		setActionForward(mapping.findForward(PG_VIEW));
		
		Transferencia transferencia = null;
		try {
			verificarGrupoUsuarioLogado(localForm, request);
			String codTransferencia = StringUtils.isNotBlank(localForm.getCodTransferencia()) ? localForm.getCodTransferencia() : null;
			
			if(StringUtils.isNotBlank(codTransferencia)){
				transferencia = OperacaoFacade.obterTransferenciaCompleto(Integer.valueOf(codTransferencia));
			}

			AssinaturaDocTransferencia assinatura = OperacaoFacade.obterAssinaturaDocTransferencia(Integer.valueOf(localForm.getCodAssinatura()));
			
			OperacaoFacade.excluirAssinaturaDocTransferencia(assinatura);
			
			this.addMessage("SUCESSO.41", new String[]{"Transferência", transferencia.getCodTransferencia().toString(), transferencia.getStatusTermo().getDescricao(),
					Mensagem.getInstance().getMessage("SUCESSO.44")}, request);	

			localForm.setCodTransferencia(transferencia.getCodTransferencia().toString());
			localForm.setCodAssinatura(null);
			populaForm(localForm, transferencia, request);
			
			return getActionForward();

		} catch (ApplicationException ae) {
			populaForm(localForm, transferencia, request);
			throw ae; 
		}catch (Exception e) {
			populaForm(localForm, transferencia, request);
			throw new ApplicationException("mensagem.erro.geral", new String[]{GerarTermoTransfBemImovelAction.class.getSimpleName() + ".excluirAssinatura()"}, e, ApplicationException.ICON_ERRO);
		}	

	}
	
}