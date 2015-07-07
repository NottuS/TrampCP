package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.AnexoBemImovelForm;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Documentacao;
import gov.pr.celepar.abi.pojo.Edificacao;
import gov.pr.celepar.abi.pojo.Notificacao;
import gov.pr.celepar.abi.pojo.TipoDocumentacao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Data;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author pialarissi
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 20/01/2010
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class AnexoBemImovelAction extends BaseDispatchAction {
	
	private static Logger log4j = Logger.getLogger(AnexoBemImovelAction.class);
	
	/**
	 * Realiza carga da aba Anexo.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgEditAnexo(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException, Exception {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}

		saveToken(request);

		setActionForward(mapping.findForward("pgEditAnexo"));
		AnexoBemImovelForm localForm = (AnexoBemImovelForm) form;
		carregarCampos(localForm, request);
		try {
			if (request.getParameter("actionType")!= null && request.getParameter("actionType").equals("incluir")){
				localForm.limparForm();
				localForm.setActionType("incluir");
				localForm.setSelBemImovel("1");
			} else if (request.getParameter("actionType")!= null && request.getParameter("actionType").equals("alterar")){
				if (request.getParameter("codDocumentacao") != null){
					Documentacao documentacao = CadastroFacade.obterDocumentacaoComRelacionamentos(Integer.valueOf(request.getParameter("codDocumentacao")));
					if (documentacao.getTipoDocumentacao() != null){
						localForm.setTipoDocumentacaoAnexo(documentacao.getTipoDocumentacao().getCodTipoDocumentacao().toString());
					}
					localForm.setCodDocumentacao(documentacao.getCodDocumentacao().toString());
					localForm.setDescricaoAnexo(documentacao.getDescricaoAnexo());
					localForm.setActionType("alterar");
					if (documentacao.getEdificacao()!=null)	{
						localForm.setSelBemImovel("2");
						localForm.setEdificacao(documentacao.getEdificacao().getCodEdificacao().toString());
					}else {
						localForm.setSelBemImovel("1");
					}
					if (documentacao.getDocumentacao()!=null){
						localForm.setDocumentacao(documentacao.getDocumentacao().getCodDocumentacao().toString());
					}
					if (documentacao.getTipoDocumentacao()!= null && documentacao.getTipoDocumentacao().getCodTipoDocumentacao().intValue() == Dominios.TIPO_DOCUMENTACAO_NOTIFICACAO){
						if (documentacao.getNotificacaos()!= null){
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							Notificacao notificacao = documentacao.getNotificacaos().iterator().next();
							localForm.setCodNotificacao(notificacao.getCodNotificacao().toString());
							localForm.setTsNotificacao(sdf.format(notificacao.getDataNotificacao()));
							localForm.setTsPrazoNotificacao(sdf.format(notificacao.getPrazoNotificacao()));
							localForm.setDescricaoNotificacao(notificacao.getDescricao());
							if (notificacao.getDataSolucao()!=null)
							{
								localForm.setTsSolucao(sdf.format(notificacao.getDataSolucao()));
							}
							localForm.setMotivoSolucao(notificacao.getMotivo());
						}
					}
				}
			}
		} catch (ApplicationException appEx) {
			this.carregarCampos(form, request);
			throw appEx;

		} catch (Exception e) {
			this.carregarCampos(form, request);
			throw new ApplicationException("ERRO.200", new String[]{"edição","anexo"}, e);
		}	

		return getActionForward();
    }
	
	/**
	 * Carrega os campos da tela
	 * @param form
	 * @param request
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public void carregarCampos(ActionForm form, HttpServletRequest request) throws ApplicationException, Exception {
		AnexoBemImovelForm localForm = (AnexoBemImovelForm) form;

		Integer codBemImovel = (!Util.strEmBranco(request.getParameter("codBemImovel")))?Integer.valueOf(request.getParameter("codBemImovel")): null;
		BemImovel bemImovel = null;
		if (codBemImovel!=null){
			bemImovel = CadastroFacade.obterBemImovelComEdificacoes(codBemImovel);
			localForm.setSomenteTerreno(bemImovel.getSomenteTerreno());
			localForm.setAdministracao(String.valueOf(bemImovel.getAdministracao()));
			localForm.setCodBemImovel(String.valueOf(bemImovel.getCodBemImovel()));
			localForm.setNrBemImovel(bemImovel.getNrBemImovel().toString());

			if (bemImovel!= null){
				if (bemImovel.getClassificacaoBemImovel()!= null && bemImovel.getClassificacaoBemImovel().getCodClassificacaoBemImovel().equals(Dominios.classificacaoImovel.CLASSIFICACAO_RURAL.getIndex())){
					request.setAttribute("CLASSIFICACAO_RURAL", true);
				}else{
					request.setAttribute("CLASSIFICACAO_RURAL", false);
				}	
			}
			if (bemImovel.getDocumentacaos() != null){
				request.setAttribute("documentacaosOriginal", bemImovel.getDocumentacaos());
			}
			if (bemImovel.getEdificacaos() !=null){
				request.setAttribute("edificacoes", bemImovel.getEdificacaos());
			}
		}
		request.setAttribute("tiposDocumentacao", Util.htmlEncodeCollection(CadastroFacade.listarTipoDocumentacao()));
			
		if (localForm.getSelBemImovel() != null) {
			if (localForm.getSelBemImovel().equals("2")) { 
				if(!localForm.getEdificacao().equals("0")) {
					Edificacao edificacao=CadastroFacade.obterEdificacao(Integer.valueOf(localForm.getEdificacao()));
					if (edificacao.getDocumentacaos() != null){
						request.setAttribute("documentacaosOriginal", edificacao.getDocumentacaos());
					}
				}
			}
		}
		
		request.setAttribute("notificacao", Dominios.TIPO_DOCUMENTACAO_NOTIFICACAO);
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
				
		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));
		pagina = CadastroFacade.listarDocumentacaoSemOcorrencia(pagina, Integer.valueOf(localForm.getCodBemImovel()), true);
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
    }
		
	/**
	 * Adiciona anexo
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward adicionarAnexo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		try	{
			setActionForward(mapping.findForward("pgEditAnexo"));
			AnexoBemImovelForm localForm = (AnexoBemImovelForm) form;

			if (Util.strEmBranco(localForm.getTipoDocumentacaoAnexo()) && localForm.getAnexo().getFileSize() == 0){
				throw new ApplicationException("AVISO.45", ApplicationException.ICON_AVISO);
			}

			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				this.carregarCampos(localForm, request);
				throw new ApplicationException("AVISO.200");
			}	

			saveToken(request);
			// Aciona a validação do Form
			validaDadosForm(localForm);

			Documentacao documentacao = new Documentacao();
			StringBuffer flagNotificacao = new StringBuffer();
			populaPojo(localForm, documentacao, request);
			Notificacao notificacao = null;
			notificacao = populaPojoNotificacao(localForm, request, documentacao, notificacao, flagNotificacao); 
			
			CadastroFacade.salvarDocumentacao(documentacao, notificacao, localForm.getAnexo());

			//limpar formulario
			localForm.limparForm();

			addMessage("SUCESSO.19", request);	
			this.carregarCampos(form, request);

		} catch (ApplicationException appEx) {
			this.carregarCampos(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarCampos(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Anexo"}, e);
		}

		return getActionForward();
    }
	
	/**
	 * Popula POJO para cadastro
	 * @param localForm
	 * @param documentacao
	 * @param request
	 * @throws Exception
	 * @throws ApplicationException
	 */
	private void populaPojo(AnexoBemImovelForm localForm, Documentacao documentacao, HttpServletRequest request) throws Exception, ApplicationException {
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date data = new Date();
		StringBuffer descricao = new StringBuffer();

		if (!Util.strEmBranco(localForm.getTipoDocumentacaoAnexo())){
			TipoDocumentacao tipo = CadastroFacade.obterTipoDocumentacao(Integer.valueOf(localForm.getTipoDocumentacaoAnexo()));
			documentacao.setTipoDocumentacao(tipo);
			if(documentacao.getAnexo()!=null && !documentacao.getAnexo().equals("")){
				if (localForm.getActionType().equalsIgnoreCase("incluir")) {
					descricao.append("Anexo incluído em: ").append( sdf3.format(data));
				} else {
					descricao.append("Anexo alterado em: ").append( sdf3.format(data));
				}
				descricao.append("\n Por: ").append( sentinelaInterface.getNome()).append("\n Tipo de Documentação: ").append( tipo.getDescricao()).append("\n Anexo: ").append(documentacao.getAnexo());
			}else{
				if (localForm.getActionType().equalsIgnoreCase("incluir")) {
					descricao.append("Anexo incluído em: ").append( sdf3.format(data));
				} else {
					descricao.append("Anexo alterado em: ").append( sdf3.format(data));
				}
				descricao.append( "\n Por: ").append( sentinelaInterface.getNome()).append( "\n Tipo de Documentação: ").append( tipo.getDescricao()) ;
			}
		}else{
			if(documentacao.getAnexo()!=null && !documentacao.getAnexo().equals("")){
				if (localForm.getActionType().equalsIgnoreCase("incluir")) {
					descricao.append("Anexo incluído em: ").append( sdf3.format(data));
				} else {
					descricao.append("Anexo alterado em: ").append( sdf3.format(data));
				}
				descricao.append("\n Por: ").append( sentinelaInterface.getNome()).append( "\n Anexo: ").append(documentacao.getAnexo());
			}else{
				if (localForm.getActionType().equalsIgnoreCase("incluir")) {
					descricao.append("Anexo incluído em: ").append( sdf3.format(data));
				} else {
					descricao.append("Anexo alterado em: ").append( sdf3.format(data));
				}
				descricao.append("\n Por: ").append(sentinelaInterface.getNome()) ;
			}
		}
		
		documentacao.setDescricao(descricao.toString());
		documentacao.setBemImovel(CadastroFacade.obterBemImovel(Integer.valueOf(localForm.getCodBemImovel())));

		if(localForm.getSelBemImovel().equals("2")){ //documentacao por edificação
			if (localForm.getEdificacao()!= null && !localForm.getEdificacao().equals("0")) {
				Edificacao edificacao = CadastroFacade.obterEdificacao(Integer.valueOf(localForm.getEdificacao()));
				documentacao.setEdificacao(edificacao);
			}
		}
		documentacao.setCpfResponsavel(sentinelaInterface.getCpf());
		if (localForm.getDocumentacao()!= null && !localForm.getDocumentacao().equals("") ) {
			documentacao.setDocumentacao(CadastroFacade.obterDocumentacao(Integer.valueOf(localForm.getDocumentacao())));
		}
		if (localForm.getActionType().equalsIgnoreCase("incluir")) {
			documentacao.setAnexo(localForm.getAnexo().getFileName());
		} else {
			if (localForm.getAnexo() != null && localForm.getAnexo().getFileName().trim().length() > 0) {
				documentacao.setAnexo(localForm.getAnexo().getFileName());
			}
		}
		documentacao.setDescricaoAnexo(localForm.getDescricaoAnexo());
		if (localForm.getActionType().equalsIgnoreCase("incluir")) {
			documentacao.setTsInclusao(data);
		} else {
			documentacao.setTsAtualizacao(data);
		}
		documentacao.setResponsavelDocumentacao(sentinelaInterface.getNome());
	}

	private Notificacao populaPojoNotificacao(AnexoBemImovelForm localForm, HttpServletRequest request, Documentacao documentacao, Notificacao notificacao, StringBuffer flagNotificacao) throws Exception {
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date data = new Date();
		if(!Util.strEmBranco(localForm.getTipoDocumentacaoAnexo()) && Integer.parseInt(localForm.getTipoDocumentacaoAnexo()) == Dominios.TIPO_DOCUMENTACAO_NOTIFICACAO) {
			if (notificacao == null || notificacao.getCodNotificacao() == 0) {
				notificacao = new Notificacao(); 
			}
			notificacao.setCpfResponsavel(sentinelaInterface.getCpf());
			notificacao.setDataNotificacao(sdf.parse(localForm.getTsNotificacao()));
			notificacao.setPrazoNotificacao(sdf.parse(localForm.getTsPrazoNotificacao()));
			notificacao.setDescricao(localForm.getDescricaoNotificacao());
			notificacao.setDocumentacao(documentacao);
			if (localForm.getActionType().equalsIgnoreCase("incluir")) {
				notificacao.setTsInclusao(data);
				notificacao.setTsAlteracao(data);
			} else {
				notificacao.setTsAlteracao(data);
			}

			if(localForm.getTsSolucao() != null && !localForm.getTsSolucao().equals("")) {
				notificacao.setDataSolucao(sdf.parse(localForm.getTsSolucao()));
				notificacao.setMotivo(localForm.getMotivoSolucao());
			} else {
				notificacao.setDataSolucao(null);
				notificacao.setMotivo(null);
			}

		} else if (StringUtil.stringNotNull(localForm.getCodNotificacao())){// antes era uma notificação e agora não é mais. Notificação deve ser apagada.
			notificacao = CadastroFacade.obterNotificacao(Integer.valueOf(localForm.getCodNotificacao()));
			flagNotificacao.append("exclusao");
		}
		return notificacao;
	}

	/**
	 * Altera anexo
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward alterarAnexo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		
		try	{
			setActionForward(mapping.findForward("pgEditAnexo"));
			AnexoBemImovelForm localForm = (AnexoBemImovelForm) form;

			if (Util.strEmBranco(localForm.getTipoDocumentacaoAnexo()) && localForm.getAnexo().getFileSize() == 0){
				throw new ApplicationException("AVISO.45", ApplicationException.ICON_AVISO);
			}

			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				this.carregarCampos(localForm, request);
				throw new ApplicationException("AVISO.200");
			}	

			// Aciona a validação do Form
			saveToken(request);
			validaDadosForm(localForm);

			StringBuffer flagNotificacao = new StringBuffer();
			Documentacao documentacao = CadastroFacade.obterDocumentacao(Integer.valueOf(request.getParameter("codDocumentacao")));
			Notificacao notificacao = null;
			if (StringUtil.stringNotNull(localForm.getCodNotificacao())){
				notificacao = CadastroFacade.obterNotificacao(Integer.valueOf(localForm.getCodNotificacao()));
				flagNotificacao.append("alteracao");
			}else{
				notificacao = new Notificacao();
				flagNotificacao.append("inclusao");
			}
			
			populaPojo(localForm, documentacao, request);
			notificacao = populaPojoNotificacao(localForm, request, documentacao, notificacao, flagNotificacao);
			
			if (localForm.getAnexo() != null && localForm.getAnexo().getFileName().trim().length() > 0) {
				CadastroFacade.alterarDocumentacao(documentacao, notificacao, flagNotificacao.toString(), localForm.getAnexo());
			} else {
				CadastroFacade.alterarDocumentacao(documentacao, notificacao, flagNotificacao.toString(), null);
			}
			
			//limpar formulario
			localForm.limparForm();
			localForm.setActionType("incluir");
			localForm.setSelBemImovel("1");

			addMessage("SUCESSO.20", request);	
			this.carregarCampos(form, request);

		} catch (ApplicationException appEx) {
			this.carregarCampos(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarCampos(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Anexo"}, e);
		}

		return getActionForward();
    }
	
	/**
	 * Exclui anexo
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward excluirAnexo(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response)  throws ApplicationException, Exception {
		
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		setActionForward(mapping.findForward("pgEditAnexo"));
		AnexoBemImovelForm localForm = (AnexoBemImovelForm) form;
			
		try {
			if (StringUtil.stringNotNull(localForm.getCodDocumentacao())) {
				
				CadastroFacade.excluirDocumentacao(Integer.parseInt(localForm.getCodDocumentacao()));
				
				localForm.limparForm();
				localForm.setActionType("incluir");
				localForm.setSelBemImovel("1");
				addMessage("SUCESSO.25", request);
				saveToken(request);
			}
			
			
			this.carregarCampos(form, request);
		
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			this.carregarCampos(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarCampos(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Anexo"}, e);
		}
	}
	
	/**
	 * Carrega arquivo
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public void carregarAnexo(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException, Exception {

		try {
			if (StringUtil.stringNotNull(request.getParameter("codDocumentacao"))){
				
				Documentacao doc = CadastroFacade.obterDocumentacao(Integer.valueOf (request.getParameter("codDocumentacao")));
				String nomeArquivo = doc.getAnexo().replaceAll(" ", "_");
				byte [] bytes = CadastroFacade.obterAnexoDocumentacao(Integer.valueOf(request.getParameter("codDocumentacao")));
				
				response.setHeader("Content-disposition", "attachment;filename="+nomeArquivo);
				response.setContentType("text/plain");  
				response.setContentLength(bytes.length);  
				ServletOutputStream ouputStream;

				ouputStream = response.getOutputStream();

				ouputStream.write(bytes, 0, bytes.length);  
				ouputStream.flush();  
				ouputStream.close(); 
			}
		} catch (ApplicationException appEx) {
			this.carregarCampos(form, request);
			setActionForward(mapping.findForward("pgEditAnexo"));
			throw appEx;
		} catch (Exception e) {
			this.carregarCampos(form, request);
			setActionForward(mapping.findForward("pgEditAnexo"));
			throw new ApplicationException("ERRO.200", new String[]{"visualizar documento","anexo"}, e);
		}  

	}
		
	private void verificarTodasOperacoes( HttpServletRequest request ){
		SentinelaInterface com = SentinelaComunicacao.getInstance(request);
		String operacoes = com.getOperacoes();
		log4j.info("Operações do usuário "+com.getLogin()+ ": " + operacoes);
		verificarOperacao('A', operacoes, request); //alterar bem Imovel
	}
	
	private void verificarOperacao(char operacao, String operacoes, HttpServletRequest request){
		try {
			request.setAttribute(String.valueOf(operacao), Boolean.valueOf( operacoes.indexOf(operacao)!= -1 ) );
		}catch (Exception e) {
			request.setAttribute(String.valueOf(operacao), "false" );
			log4j.info("Não autorizado para a operação: "+ operacao, e );			
		}
	}

	private void validaDadosForm(AnexoBemImovelForm localForm) throws ApplicationException {
		StringBuffer str = new StringBuffer();
		
		if (localForm.getTipoDocumentacaoAnexo() == null || localForm.getTipoDocumentacaoAnexo().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Tipo de Documentação");
		}
		if (localForm.getDescricaoAnexo() == null || localForm.getDescricaoAnexo().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Descrição do Anexo");
		}
		if (localForm.getSelBemImovel() == null || localForm.getSelBemImovel().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Por (Bem Imóvel / Edificação)");
		}

		if (localForm.getTipoDocumentacaoAnexo() != null && localForm.getTipoDocumentacaoAnexo().equalsIgnoreCase(String.valueOf(Dominios.TIPO_DOCUMENTACAO_NOTIFICACAO))) {
			if (localForm.getTsNotificacao() == null || localForm.getTsNotificacao().trim().length() == 0) {
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Data da Notificação");
			}
			if (localForm.getTsPrazoNotificacao() == null || localForm.getTsPrazoNotificacao().trim().length() == 0) {
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Prazo da Notificação");
			}
			if (localForm.getDescricaoNotificacao() == null || localForm.getDescricaoNotificacao().trim().length() == 0) {
				if (str.length() > 0) {
					str.append(", ");
				}
				str.append("Descrição da Notificação");
			}
		}

		if (str.length() > 0) {
			str.append(".");
			throw new ApplicationException("AVISO.72", new String[]{str.toString()}, ApplicationException.ICON_AVISO);
		}

		Date dataAntiga = null;
		try {
			dataAntiga = Data.formataData("03/12/1600");
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar Data!"}, ApplicationException.ICON_AVISO);
		}
		if (localForm.getTsNotificacao() != null && localForm.getTsNotificacao().trim().length() > 0) {
			if (!Data.validarData(localForm.getTsNotificacao().trim())) {
				throw new ApplicationException("errors.date", new String[]{"A Data da Notificação informada"}, ApplicationException.ICON_AVISO);
			}
			Date inicVig = null;
			try {
				inicVig = Data.formataData(localForm.getTsNotificacao().trim());
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar Data!"}, ApplicationException.ICON_AVISO);
			}
			if (Data.dataDiff(inicVig,dataAntiga) == 1) {
				throw new ApplicationException("errors.dataAntiga", new String[]{"A Data da Notificação"}, ApplicationException.ICON_AVISO);
			}
		}

		if (localForm.getTsPrazoNotificacao() != null && localForm.getTsPrazoNotificacao().trim().length() > 0) {
			if (!Data.validarData(localForm.getTsPrazoNotificacao().trim())) {
				throw new ApplicationException("errors.date", new String[]{"A Data de Prazo da Notificação informada"}, ApplicationException.ICON_AVISO);
			}
			Date fimVig = null;
			try {
				fimVig = Data.formataData(localForm.getTsPrazoNotificacao().trim());
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar Data!"}, ApplicationException.ICON_AVISO);
			}
			if (Data.dataDiff(fimVig,dataAntiga) == 1) {
				throw new ApplicationException("errors.dataAntiga", new String[]{"A Data do Prazo da Notificação"}, ApplicationException.ICON_AVISO);
			}
			Date inicVig = null;
			try {
				inicVig = Data.formataData(localForm.getTsNotificacao().trim());
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao formatar Data!"}, ApplicationException.ICON_AVISO);
			}
			if (Data.dataDiff(fimVig,inicVig) == 1) {
				throw new ApplicationException("mensagem.erro.3", new String[]{"Data da Notificação", "Data do Prazo da Notificação"}, ApplicationException.ICON_AVISO);
			}

		}
	}	

}