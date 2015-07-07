package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.ConfrontanteForm;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Confrontante;
import gov.pr.celepar.abi.util.Dominios;
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
 * @author pialarissi
 * @version 1.0
 * @since 20/01/2010
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */
public class ConfrontanteAction extends BaseDispatchAction {
	
	private static Logger log4j = Logger.getLogger(ConfrontanteAction.class);
	
	private void verificarTodasOperacoes( HttpServletRequest request ){
		SentinelaInterface com = SentinelaComunicacao.getInstance(request);
		String operacoes = com.getOperacoes();
		log4j.info("Operações do usuário "+com.getLogin()+ ": " + operacoes);
		verificarOperacao('A', operacoes, request); //alterar bem Imovel
	}
	
	private void verificarOperacao(char operacao, String operacoes, HttpServletRequest request){
		try {
			request.setAttribute(String.valueOf(operacao), Boolean.valueOf( operacoes.indexOf(operacao)!= -1 ));
		}catch (Exception e) {
			request.setAttribute(String.valueOf(operacao), "false" );
			log4j.info("Não autorizado para a operação: "+ operacao, e );			
		}
	}
	/**
	 * Realiza carga da aba Confrontante.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgEditConfrontante(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException, Exception {
		
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		if (request.getParameter("codBemImovel")!=null && !request.getParameter("codBemImovel").equals("") ){

			this.carregarConfrontantes(form, request);

			if (request.getParameter("actionType").equals("alterar")){
				
				if (request.getParameter("codConfrontante")!= null){
					ConfrontanteForm confrontanteForm = (ConfrontanteForm)form;
					Confrontante confrontante = CadastroFacade.obterConfrontante(Integer.valueOf(request.getParameter("codConfrontante")));
					confrontanteForm.setDescricao(confrontante.getDescricao());
					confrontanteForm.setActionType("alterar");
					confrontanteForm.setCodConfrontante(request.getParameter("codConfrontante"));

				}
			}
			return mapping.findForward("pgEditConfrontante");
		}
		else
		{
			setActionForward(mapping.findForward("error"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","confrontante"});
		}
		
    }

	
	public void carregarConfrontantes(ActionForm form, HttpServletRequest request ) throws ApplicationException {
		
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
				
		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));

		//recuperar codigo bem imovel
		ConfrontanteForm confrontanteForm = (ConfrontanteForm) form;
		confrontanteForm.setCodBemImovel(request.getParameter("codBemImovel"));		
		confrontanteForm.setNrBemImovel(CadastroFacade.obterBemImovel(new Integer(request.getParameter("codBemImovel"))).getNrBemImovel().toString());
		
		BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(request.getParameter("codBemImovel")));
		confrontanteForm.setSomenteTerreno(bemImovel.getSomenteTerreno());
						
		pagina = CadastroFacade.listarConfrontantes(pagina, bemImovel);
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
			
	}


	public ActionForward salvarConfrontante(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		ConfrontanteForm confrontanteForm = (ConfrontanteForm) form;
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		String descricaoNovoConfrontante = confrontanteForm.getDescricao().trim();
		Integer codBemImovel =  Integer.valueOf(confrontanteForm.getCodBemImovel());
		
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditConfrontante");
		}	
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			this.carregarConfrontantes(form, request);
			setActionForward(mapping.findForward("pgEditConfrontante"));
			throw new ApplicationException("AVISO.200");
		}	
		// Verifica se descrição da confrontante já existe
		if (CadastroFacade.verificaConfrontanteDuplicado(descricaoNovoConfrontante, codBemImovel)){			
			setActionForward(mapping.findForward("pgEditConfrontante"));
			throw new ApplicationException("AVISO.42",  new String[]{descricaoNovoConfrontante}, ApplicationException.ICON_AVISO);
		}

		
		try	{			
			Confrontante confrontante = new Confrontante();
	
			BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(confrontanteForm.getCodBemImovel()));
			
			Date dataAtual = new Date();
			confrontante.setBemImovel(bemImovel);
			confrontante.setDescricao(descricaoNovoConfrontante);
			confrontante.setCpfResponsavel(sentinelaInterface.getCpf());
			confrontante.setTsAtualizacao(dataAtual);
			confrontante.setTsInclusao(dataAtual);
			
			CadastroFacade.salvarConfrontante(confrontante);
			
			//limpar formulario
			confrontanteForm.setActionType("");
			confrontanteForm.setDescricao("");
			confrontanteForm.setActionType("incluir");
			
			addMessage("SUCESSO.9", request);	
			this.carregarConfrontantes(form, request);
			
		} catch (ApplicationException appEx) {
			this.carregarConfrontantes(form, request);
			setActionForward(mapping.findForward("pgEditConfrontante"));
			throw appEx;
		} catch (Exception e) {
			this.carregarConfrontantes(form, request);
			setActionForward(mapping.findForward("pgEditConfrontante"));
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Confrontante"}, e);
		}
	
	return mapping.findForward("pgEditConfrontante");
	}
	
	
	public ActionForward alterarConfrontante(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException, Exception {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		ConfrontanteForm confrontanteForm = (ConfrontanteForm) form;
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		String descricaoNovoConfrontante = confrontanteForm.getDescricao().trim();
		Integer codBemImovel = Integer.valueOf(confrontanteForm.getCodBemImovel());
		

		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditConfrontante");
		}	

		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			this.carregarConfrontantes(form, request);
			setActionForward(mapping.findForward("pgEditConfrontante"));
			throw new ApplicationException("AVISO.200");
		}
		
		// Verifica se descrição da confrontante já existe
		if (CadastroFacade.verificaConfrontanteDuplicado(descricaoNovoConfrontante, codBemImovel)){	
			this.carregarConfrontantes(form, request);
			setActionForward(mapping.findForward("pgEditConfrontante"));
			throw new ApplicationException("AVISO.42",  new String[]{descricaoNovoConfrontante}, ApplicationException.ICON_AVISO);
		}
		

		try	{			
			Confrontante confrontante = CadastroFacade.obterConfrontante(Integer.valueOf (confrontanteForm.getCodConfrontante()));

			BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(confrontanteForm.getCodBemImovel()));

			Date dataAtual = new Date();
			confrontante.setBemImovel(bemImovel);
			confrontante.setDescricao(confrontanteForm.getDescricao());
			confrontante.setCpfResponsavel(sentinelaInterface.getCpf());
			confrontante.setTsAtualizacao(dataAtual);
			
			CadastroFacade.alterarConfrontante(confrontante);

			//limpar formulario
			confrontanteForm.setActionType("");
			confrontanteForm.setDescricao("");
			confrontanteForm.setActionType("incluir");

			addMessage("SUCESSO.10", request);	
			this.carregarConfrontantes(form, request);

		} catch (ApplicationException appEx) {
			this.carregarConfrontantes(form, request);
			setActionForward(mapping.findForward("pgEditConfrontante"));
			throw appEx;
		} catch (Exception e) {
			this.carregarConfrontantes(form, request);
			setActionForward(mapping.findForward("pgEditConfrontante"));
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Confrontante"}, e);
		}

		return mapping.findForward("pgEditConfrontante");
	}
	
	
	public ActionForward excluirConfrontante(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		ConfrontanteForm confrontanteForm = (ConfrontanteForm) form;
			
		try {
			if (confrontanteForm.getCodConfrontante() != null) {
				
				CadastroFacade.excluirConfrontante(Integer.parseInt(confrontanteForm.getCodConfrontante()));
				//limpar formulario
				confrontanteForm.setActionType("");
				confrontanteForm.setDescricao("");
				confrontanteForm.setActionType("incluir");
				
				addMessage("SUCESSO.25", request);
			}
			
			
			this.carregarConfrontantes(form, request);
			return mapping.findForward("pgEditConfrontante");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditConfrontante"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditConfrontante"));
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Confrontante"}, e);
		}
	}
	
		
}