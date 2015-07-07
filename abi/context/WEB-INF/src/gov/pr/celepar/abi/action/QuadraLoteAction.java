package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.QuadraLoteForm;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Lote;
import gov.pr.celepar.abi.pojo.Quadra;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author vanessa
 * @version 1.0
 * @since 11/10/2011
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class QuadraLoteAction extends BaseDispatchAction {

	private static Logger log4j = Logger.getLogger(QuadraLoteAction.class);

	/**
	 * Realiza carga da aba.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgEditQuadraLote(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		if (request.getParameter("codBemImovel")!=null && !request.getParameter("codBemImovel").equals("") ){
			this.carregarCampos(form, request);

			if (request.getParameter("actionType").equals("alterar")){
				QuadraLoteForm localForm = (QuadraLoteForm)form;
				if (request.getParameter("codLote")!= null && request.getParameter("codLote").toString().length() > 0){
					Lote lote = CadastroFacade.obterLote(Integer.valueOf((request.getParameter("codLote"))));
					Quadra quadra = CadastroFacade.obterQuadra(lote.getQuadra().getCodQuadra());
					localForm.setCodLote(lote.getCodLote().toString());
					localForm.setDescricaoLote(lote.getDescricao());
					localForm.setDescricaoQuadra(quadra.getDescricao());
					localForm.setCodQuadra(quadra.toString());
					localForm.setActionType("alterar");
				}
				if (request.getParameter("codQuadra")!= null && request.getParameter("codQuadra").toString().length() > 0){
					Quadra quadra = CadastroFacade.obterQuadra(Integer.valueOf((request.getParameter("codQuadra"))));
					localForm.setCodQuadra(quadra.getCodQuadra().toString());
					localForm.setDescricaoQuadra(quadra.getDescricao());
					localForm.setActionType("alterar");
				}
			}
			return mapping.findForward("pgEditQuadraLote");
		}
		else
		{
			setActionForward(mapping.findForward("error"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","quadra/lote"});
		}
		
    }

	/**
	 * Carrega os campos da tela
	 * @param form
	 * @param request
	 * @throws ApplicationException
	 */
	public void carregarCampos(ActionForm form, HttpServletRequest request ) throws ApplicationException {
		
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		//recuperar codigo bem imovel
		QuadraLoteForm localForm = (QuadraLoteForm) form;		
		localForm.setActionType("incluir");		
		localForm.setCodBemImovel(request.getParameter("codBemImovel"));		
		localForm.setNrBemImovel(CadastroFacade.obterBemImovel(new Integer(request.getParameter("codBemImovel"))).getNrBemImovel().toString());

		BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(request.getParameter("codBemImovel")));
		localForm.setSomenteTerreno(bemImovel.getSomenteTerreno());
		localForm.setAdministracao(bemImovel.getAdministracao().toString());
		
		request.setAttribute("quadras", Util.htmlEncodeCollection(CadastroFacade.listarQuadrasPorBemImovel(Integer.valueOf((request.getParameter("codBemImovel"))))));
		
		Pagina paginaLote = new Pagina();
		paginaLote = CadastroFacade.listarLote(paginaLote,Integer.valueOf((request.getParameter("codBemImovel"))));
		Util.htmlEncodeCollection(paginaLote.getRegistros());
		request.setAttribute("paginaLote", paginaLote);

		Pagina paginaQuadra = new Pagina();
		paginaQuadra = CadastroFacade.listarQuadrasSemLote(paginaQuadra, bemImovel);
		Util.htmlEncodeCollection(paginaQuadra.getRegistros());
		request.setAttribute("paginaQuadra", paginaQuadra);
	}

	/**
	 * Salva a quadra/lote
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public ActionForward salvarQuadraLote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		setActionForward(mapping.findForward("pgEditQuadraLote"));

		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		QuadraLoteForm localForm = (QuadraLoteForm) form;
		
		validaDadosForm(localForm);
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			this.carregarCampos(form, request);
			throw new ApplicationException("AVISO.200");
		}	

		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		Date dataAtual = new Date();
		String descricaoNovoLote = localForm.getDescricaoLote().trim();
		String descricaoNovaQuadra = "";
		if (localForm.getDescricaoQuadra() != null && localForm.getDescricaoQuadra().trim().length() > 0) {
			descricaoNovaQuadra = localForm.getDescricaoQuadra().trim();
		}
		Integer codQuadra = null;
		BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(localForm.getCodBemImovel()));

		if (localForm.getCodQuadra() != null && localForm.getCodQuadra().length() > 0) {
			codQuadra = Integer.valueOf((localForm.getCodQuadra()));
		}

		try	{				
			Quadra quadra = new Quadra();
			if (codQuadra == null || codQuadra == 0) {
				// Verifica se descrição da quadra já existe
				if (CadastroFacade.verificaQuadraDuplicado(descricaoNovaQuadra, Integer.valueOf(localForm.getCodBemImovel()))){			
					this.carregarCampos(form, request);
					throw new ApplicationException("AVISO.41",  new String[]{descricaoNovaQuadra}, ApplicationException.ICON_AVISO);
				}

				quadra.setBemImovel(bemImovel);
				quadra.setDescricao(descricaoNovaQuadra);			
				quadra.setCpfResponsavel(sentinelaInterface.getCpf());
				quadra.setTsAtualizacao(dataAtual);
				quadra.setTsInclusao(dataAtual);
				
				quadra = CadastroFacade.salvarQuadra(quadra);
				localForm.setCodQuadra(quadra.getCodQuadra().toString());
				codQuadra = quadra.getCodQuadra();
			} else {
				quadra = CadastroFacade.obterQuadra(Integer.valueOf (localForm.getCodQuadra()));
			}
			
			if (descricaoNovoLote != null && descricaoNovoLote.length() > 0) {
				// Verifica se descrição do lote já existe para a quadra
				if (CadastroFacade.verificaLoteDuplicado(descricaoNovoLote, codQuadra)){			
					this.carregarCampos(form, request);
					throw new ApplicationException("AVISO.43",  new String[]{descricaoNovoLote}, ApplicationException.ICON_AVISO);
				}

				Lote lote = new Lote();
				lote.setQuadra(quadra);
				lote.setDescricao(localForm.getDescricaoLote());			
				lote.setCpfResponsavel(sentinelaInterface.getCpf());
				lote.setTsAtualizacao(dataAtual);
				lote.setTsInclusao(dataAtual);
				
				CadastroFacade.salvarLote(lote);
			}

			//limpar formulario
			localForm.limparCampos();
			localForm.setActionType("incluir");
			
			if (codQuadra == null || codQuadra == 0) {
				addMessage("SUCESSO.48", new String[]{"Quadra"}, request);	
			} 
			if (descricaoNovoLote != null && descricaoNovoLote.length() > 0) {
				addMessage("SUCESSO.48", new String[]{"Quadra e Lote"}, request);	
			}
			
			this.carregarCampos(form, request);
			
		} catch (ApplicationException appEx) {
			this.carregarCampos(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarCampos(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Lote"}, e);
		}
	
		return getActionForward();
	}
	
	private void validaDadosForm(QuadraLoteForm localForm) throws ApplicationException {
		StringBuffer str = new StringBuffer();
		
		if ((localForm.getCodQuadra() == null || localForm.getCodQuadra().trim().length() == 0) && 
				(localForm.getDescricaoQuadra() == null || localForm.getDescricaoQuadra().trim().length() == 0)) {
			str.append("Quadra");
		}
		/*if (localForm.getDescricaoLote() == null || localForm.getDescricaoLote().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Lote");
		}*/

		if (str.length() > 0) {
			str.append(".");
			throw new ApplicationException("AVISO.72", new String[]{str.toString()}, ApplicationException.ICON_AVISO);
		}

	}

	/**
	 * Altera a quadra/lote
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward alterarQuadraLote(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException, Exception {
		setActionForward(mapping.findForward("pgEditQuadraLote"));

		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}

		QuadraLoteForm localForm = (QuadraLoteForm) form;

		validaDadosForm(localForm);

		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			this.carregarCampos(form, request);
			throw new ApplicationException("AVISO.200");
		}	

		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		Date dataAtual = new Date();
		String descricaoNovoLote = localForm.getDescricaoLote().trim();
		String descricaoNovaQuadra = localForm.getDescricaoQuadra().trim();
		Integer codQuadra = Integer.valueOf((localForm.getCodQuadra()));

		try	{				
			Quadra quadra = CadastroFacade.obterQuadra(Integer.valueOf (localForm.getCodQuadra()));
			if (descricaoNovaQuadra != null && descricaoNovaQuadra.length() > 0) {
				if (!quadra.getDescricao().equalsIgnoreCase(descricaoNovaQuadra)) {
					// Verifica se descrição da quadra já existe
					if (CadastroFacade.verificaQuadraDuplicado(descricaoNovaQuadra, Integer.valueOf(localForm.getCodBemImovel()))){			
						this.carregarCampos(form, request);
						throw new ApplicationException("AVISO.41",  new String[]{descricaoNovaQuadra}, ApplicationException.ICON_AVISO);
					}
		
					quadra.setDescricao(descricaoNovaQuadra);			
					quadra.setCpfResponsavel(sentinelaInterface.getCpf());
					quadra.setTsAtualizacao(dataAtual);
					
					CadastroFacade.alterarQuadra(quadra);
				}
			}
			
			if (descricaoNovoLote != null && descricaoNovoLote.length() > 0) {
				// Verifica se descrição do lote já existe para a quadra
				if (CadastroFacade.verificaLoteDuplicado(descricaoNovoLote, codQuadra)){			
					this.carregarCampos(form, request);
					throw new ApplicationException("AVISO.43",  new String[]{descricaoNovoLote}, ApplicationException.ICON_AVISO);
				}

				Lote lote = new Lote();
				if (localForm.getCodLote() != null && localForm.getCodLote().trim().length() > 0) {
					lote = CadastroFacade.obterLote(Integer.valueOf (localForm.getCodLote()));
				}
				lote.setQuadra(quadra);
				lote.setDescricao(localForm.getDescricaoLote());			
				lote.setCpfResponsavel(sentinelaInterface.getCpf());
				lote.setTsAtualizacao(dataAtual);
				
				if (localForm.getCodLote() != null && localForm.getCodLote().trim().length() > 0) {
					CadastroFacade.alterarLote(lote);
				} else {
					lote.setTsInclusao(dataAtual);
					CadastroFacade.salvarLote(lote);
				}
			}

			//limpar formulario
			localForm.limparCampos();
			localForm.setActionType("incluir");
			
			if (descricaoNovaQuadra != null && descricaoNovaQuadra.length() > 0) {
				addMessage("SUCESSO.49", new String[]{"Quadra"}, request);	
			} 
			if (descricaoNovoLote != null && descricaoNovoLote.length() > 0) {
				addMessage("SUCESSO.49", new String[]{"Quadra e Lote"}, request);	
			}
			this.carregarCampos(form, request);
			
		} catch (ApplicationException appEx) {
			this.carregarCampos(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarCampos(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Quadra/Lote"}, e);
		}

		return getActionForward();
	}
	
	/**
	 * Excluir o lote
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public ActionForward excluirLote(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgEditQuadraLote"));
		
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		QuadraLoteForm localForm = (QuadraLoteForm) form;
			
		try {
			if (localForm.getCodLote() != null) {
				
				CadastroFacade.excluirLote(Integer.parseInt(localForm.getCodLote()));
				localForm.limparCampos();
				localForm.setActionType("incluir");
				
				addMessage("SUCESSO.50", new String[]{"Lote"}, request);	
			}
			
			this.carregarCampos(form, request);
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Quadra/Lote"}, e);
		}
	}
	
	/**
	 * Exclui a quadra e os seus lotes
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public ActionForward excluirQuadra(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgEditQuadraLote"));
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		QuadraLoteForm localForm = (QuadraLoteForm) form;
			
		try {
			if (localForm.getCodQuadra() != null) {
				
				CadastroFacade.excluirQuadra(Integer.parseInt(localForm.getCodQuadra()));
				//limpar formulario
				localForm.limparCampos();
				localForm.setActionType("incluir");
				
				addMessage("SUCESSO.50", new String[]{"Quadra"}, request);	
			}
			
			
			this.carregarCampos(form, request);
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Quadra"}, e);
		}
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