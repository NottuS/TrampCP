package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.DocumentacaoForm;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Documentacao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.text.SimpleDateFormat;
import java.util.Date;

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

public class DocumentacaoAction extends BaseDispatchAction {
	
	private static Logger log4j = Logger.getLogger(DocumentacaoAction.class);
	
	/**
	 * Realiza carga da aba Documentacao.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgEditDocumentacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException, Exception {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}

		setActionForward(mapping.findForward("pgEditDocumentacao"));
		DocumentacaoForm localForm = (DocumentacaoForm) form;
		Integer codBemImovel = (!Util.strEmBranco(request.getParameter("codBemImovel")))?Integer.valueOf(request.getParameter("codBemImovel")): null;
		localForm.setCodBemImovel(String.valueOf(codBemImovel));
		localForm.setNrBemImovel(CadastroFacade.obterBemImovel(codBemImovel).getNrBemImovel().toString());
		carregarCampos(request, localForm);
		
		saveToken(request);
		
		try {
			if (codBemImovel!=null){
				BemImovel bemImovel = CadastroFacade.obterBemImovel(codBemImovel);
				localForm.setAdministracao(String.valueOf(bemImovel.getAdministracao()));
				if (bemImovel!= null){
					if (bemImovel.getClassificacaoBemImovel()!= null && bemImovel.getClassificacaoBemImovel().getCodClassificacaoBemImovel().equals(Dominios.classificacaoImovel.CLASSIFICACAO_RURAL.getIndex())){
						request.setAttribute("CLASSIFICACAO_RURAL", true);
					}else{
						request.setAttribute("CLASSIFICACAO_RURAL", false);
					}	
				}
			}

			if (request.getParameter("actionType")!= null && request.getParameter("actionType").equals("alterar")){
				if (request.getParameter("codDocumentacao")!=null){
					Documentacao documentacao = CadastroFacade.obterDocumentacaoComRelacionamentos(Integer.valueOf(request.getParameter("codDocumentacao")));
					localForm.setCodDocumentacao(documentacao.getCodDocumentacao().toString());
					localForm.setActionType("alterar");
					if (documentacao.getCartorio() != null){
						localForm.setCartorio(documentacao.getCartorio().getCodCartorio().toString());
					} else {
						localForm.setCartorio("");
					}
					localForm.setNumeroDocumentoCartorial(documentacao.getNumeroDocumentoCartorial());
					localForm.setNiif(documentacao.getNiif());
					localForm.setNirf(documentacao.getNirf());
					localForm.setIncra(documentacao.getIncra());
					if (documentacao.getTabelionato()!= null){
						localForm.setTabelionato(documentacao.getTabelionato().getCodTabelionato().toString());
					} else {
						localForm.setTabelionato("");
					}
					localForm.setNumeroDocumentoTabelional(documentacao.getNumeroDocumentoTabelional());
				}
				
			}
			
		} catch (ApplicationException appEx) {
			carregarCampos(request, localForm);
			throw appEx;
		} catch (Exception e) {
			carregarCampos(request, localForm);
			throw new ApplicationException("ERRO.200", new String[]{"edição","documentação"}, e);
		}	

		return getActionForward();
    }
	
	/**
	 * Adiciona uma documentação
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward adicionarDocumentacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		DocumentacaoForm localForm = (DocumentacaoForm) form;
		try	{
			setActionForward(mapping.findForward("pgEditDocumentacao"));

			// Aciona a validação do Form
			if(!validaForm(mapping,form,request)) {
				return getActionForward();
			}	

			if (Util.strEmBranco(localForm.getCartorio()) && Util.strEmBranco(localForm.getTabelionato()) 
					&& Util.strEmBranco(localForm.getIncra()) && Util.strEmBranco(localForm.getNiif()) && 
					Util.strEmBranco(localForm.getNirf()) ){
				throw new ApplicationException("AVISO.45", ApplicationException.ICON_AVISO);
			}

			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	

			Documentacao documentacao = new Documentacao();
			populaPojo(localForm, documentacao, request);
			
			CadastroFacade.salvarDocumentacao(documentacao, null, null);

			localForm.limparForm();

			addMessage("SUCESSO.19", request);	
			carregarCampos(request, localForm);

		} catch (ApplicationException appEx) {
			carregarCampos(request, localForm);
			throw appEx;
		} catch (Exception e) {
			carregarCampos(request, localForm);
			throw new ApplicationException("ERRO.201", new String[]{"ao adicionar uma Documentação"}, e);
		}

		return getActionForward();
    }

	/**
	 * Altera uma documentação
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward alterarDocumentacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		
		DocumentacaoForm localForm = (DocumentacaoForm) form;
		try	{
			setActionForward(mapping.findForward("pgEditDocumentacao"));

			// Aciona a validação do Form
			if(!validaForm(mapping,form,request)) {
				carregarCampos(request, localForm);
				return getActionForward();
			}	

			if (Util.strEmBranco(localForm.getCartorio()) && Util.strEmBranco(localForm.getTabelionato()) 
					&& Util.strEmBranco(localForm.getIncra()) && Util.strEmBranco(localForm.getNiif()) 
					&& Util.strEmBranco(localForm.getNirf()) ){
				throw new ApplicationException("AVISO.45", ApplicationException.ICON_AVISO);
			}

			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				carregarCampos(request, localForm);
				throw new ApplicationException("AVISO.200");
			}	

			Documentacao documentacao = CadastroFacade.obterDocumentacao(Integer.valueOf(request.getParameter("codDocumentacao")));
			populaPojo(localForm, documentacao, request);

			CadastroFacade.alterarDocumentacao(documentacao, null, null, null);

			localForm.limparForm();
			localForm.setActionType("incluir");

			addMessage("SUCESSO.20", request);	
			carregarCampos(request, localForm);

		} catch (ApplicationException appEx) {
			carregarCampos(request, localForm);
			throw appEx;
		} catch (Exception e) {
			carregarCampos(request, localForm);
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Documentação"}, e);
		}

		return getActionForward();
    }
	
	/**
	 * Exclui a documentação
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward excluirDocumentacao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response)  throws ApplicationException, Exception {
		
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		setActionForward(mapping.findForward("pgEditDocumentacao"));
		DocumentacaoForm localForm = (DocumentacaoForm) form;
			
		try {
			if (StringUtil.stringNotNull(localForm.getCodDocumentacao())) {
				CadastroFacade.excluirDocumentacao(Integer.parseInt(localForm.getCodDocumentacao()));
				localForm.limparForm();
				localForm.setActionType("incluir");
				addMessage("SUCESSO.25", request);
			}
			carregarCampos(request, localForm);
			return getActionForward();
		} catch (ApplicationException appEx) {
			carregarCampos(request, localForm);
			throw appEx;
		} catch (Exception e) {
			carregarCampos(request, localForm);
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Documentação"}, e);
		}
	}

	/**
	 * Popula POJO
	 * @param documentacaoForm
	 * @param documentacao
	 * @param request
	 * @throws Exception
	 * @throws ApplicationException
	 */
	private void populaPojo(DocumentacaoForm localForm, Documentacao documentacao, HttpServletRequest request) throws Exception, ApplicationException {
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		Date data = new Date();
		StringBuffer descricao = new StringBuffer();
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		if (localForm.getActionType().equalsIgnoreCase("incluir")) {
			descricao.append("Documentação incluída em: ").append(sdf3.format(data));
			documentacao.setTsInclusao(data);
		} else {
			descricao.append("Documentação alterada em: ").append(sdf3.format(data));
			documentacao.setTsAtualizacao(data);
		}
		descricao.append("\n Por: ").append(sentinelaInterface.getNome());
		
		BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(localForm.getCodBemImovel()));
		documentacao.setBemImovel(bemImovel);
		documentacao.setCpfResponsavel(sentinelaInterface.getCpf());
		documentacao.setDescricao(descricao.toString());
		documentacao.setResponsavelDocumentacao(sentinelaInterface.getNome());
		if (localForm.getCartorio()!= null && !localForm.getCartorio().equals("")){
			documentacao.setCartorio(CadastroFacade.obterCartorio(Integer.valueOf(localForm.getCartorio())));
		}else{
			documentacao.setCartorio(null);
		}
		documentacao.setNumeroDocumentoCartorial(localForm.getNumeroDocumentoCartorial());
		documentacao.setNiif(localForm.getNiif());
		documentacao.setNirf(localForm.getNirf());
		documentacao.setIncra(localForm.getIncra());
		if (localForm.getTabelionato()!= null && !localForm.getTabelionato().equals("")){
			documentacao.setTabelionato(CadastroFacade.obterTabelionato(Integer.valueOf(localForm.getTabelionato())));
		}else{
			documentacao.setTabelionato(null);
		}
		documentacao.setNumeroDocumentoTabelional(localForm.getNumeroDocumentoTabelional());
	}
	
	/**
	 * Carrega combos e lista da tela
	 * @param request
	 * @param localForm
	 * @throws ApplicationException
	 * @throws Exception
	 */
	private void carregarCampos(HttpServletRequest request, DocumentacaoForm localForm) throws ApplicationException, Exception {
		request.setAttribute("cartorios", Util.htmlEncodeCollection(CadastroFacade.listarCartorios()));
		request.setAttribute("tabelionatos", Util.htmlEncodeCollection(CadastroFacade.listarTabelionatos()));

		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
				
		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));
		pagina = CadastroFacade.listarDocumentacaoSemOcorrencia(pagina, Integer.valueOf(localForm.getCodBemImovel()), false);
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
	}

	private void verificarTodasOperacoes( HttpServletRequest request ){
		SentinelaInterface com = SentinelaComunicacao.getInstance(request);
		String operacoes = com.getOperacoes();
		log4j.info("Operações do usuário "+com.getLogin()+ ": " + operacoes);
		verificarOperacao('A', operacoes, request);
	}
	
	private void verificarOperacao(char operacao, String operacoes, HttpServletRequest request){
		try {
			request.setAttribute(String.valueOf(operacao), Boolean.valueOf( operacoes.indexOf(operacao)!= -1 ) );
		}catch (Exception e) {
			request.setAttribute(String.valueOf(operacao), "false" );
			log4j.info("Não autorizado para a operação: "+ operacao, e );			
		}
	}

}