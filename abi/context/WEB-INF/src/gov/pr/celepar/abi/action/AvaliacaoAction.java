package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.AvaliacaoForm;
import gov.pr.celepar.abi.pojo.Avaliacao;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Edificacao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.Valores;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.math.BigDecimal;
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
 * @author Sr. Fain
 * @version 1.0
 * @since 20/01/2010
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */
public class AvaliacaoAction extends BaseDispatchAction {
	
	private static Logger log4j = Logger.getLogger(AvaliacaoAction.class);
	
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
	/**
	 * Realiza carga da aba Avaliacao.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgEditAvaliacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		
		if (request.getParameter("codBemImovel")!=null && !request.getParameter("codBemImovel").equals("") ){
			this.carregarListaAvalicao(form, request);
			if (request.getParameter("actionType")!= null && request.getParameter("actionType").equals("alterar")){
				if (request.getParameter("codAvaliacao")!= null)
				{
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	
					AvaliacaoForm avaliacaoform = (AvaliacaoForm) form;
					Avaliacao avaliacao = CadastroFacade.obterAvaliacao(Integer.parseInt(request.getParameter("codAvaliacao")));
					avaliacaoform.setActionType("alterar");
					avaliacaoform.setCodAvaliacao(avaliacao.getCodAvaliacao().toString());
					avaliacaoform.setDataAvaliacao(sdf.format(avaliacao.getDataAvaliacao()));
					avaliacaoform.setValor(Valores.formatarParaDecimal(avaliacao.getValor(), 2));
					if (avaliacao.getIndTipoAvaliacao() != null){
						avaliacaoform.setIndTipoAvaliacao(avaliacao.getIndTipoAvaliacao().toString());	
					}
					if (avaliacao.getEdificacao()!= null){
						avaliacaoform.setEdificacao(avaliacao.getEdificacao().getCodEdificacao().toString());
						avaliacaoform.setSelBemImovel("2");
					}
					else{
						avaliacaoform.setSelBemImovel("1");
					}
				}
				else{
					AvaliacaoForm avaliacaoform = (AvaliacaoForm) form;
					avaliacaoform.setActionType("incluir");
					avaliacaoform.setSelBemImovel("1");
				}
			}
			else
			{
				AvaliacaoForm avaliacaoform = (AvaliacaoForm) form;
				avaliacaoform.setActionType("incluir");
				avaliacaoform.setSelBemImovel("1");
			}

			return mapping.findForward("pgEditAvaliacao");
		}
		else{
			setActionForward(mapping.findForward("pgEditAvaliacao"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","avaliação"});

		}
    }

	
	public ActionForward salvarAvaliacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		
		verificarTodasOperacoes(request);
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		
		AvaliacaoForm avaliacaoForm = (AvaliacaoForm) form;
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		setActionForward(mapping.findForward("pgEditAvaliacao"));

		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			this.carregarListaAvalicao(form, request);
			return mapping.findForward("pgEditAvaliacao");
		}	
		
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
						
			throw new ApplicationException("AVISO.200");
		}	

		//salva avaliação
		try	{			
			Avaliacao avaliacao = new Avaliacao();

			BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf((avaliacaoForm.getCodBemImovel())));
			sdf = new SimpleDateFormat("dd/MM/yyyy");	
			Date dataAtual = new Date();
			BigDecimal bValor = Valores.converterStringParaBigDecimal(avaliacaoForm.getValor());

			avaliacao.setBemImovel(bemImovel);
			avaliacao.setCpfResponsavel(sentinelaInterface.getCpf());
			avaliacao.setDataAvaliacao(sdf.parse(avaliacaoForm.getDataAvaliacao()));
			avaliacao.setValor(bValor);
			avaliacao.setTsInclusao(dataAtual);
			avaliacao.setTsAtualizacao(dataAtual);
			if (avaliacaoForm.getIndTipoAvaliacao() != null && !avaliacaoForm.getIndTipoAvaliacao().isEmpty()){
				avaliacao.setIndTipoAvaliacao(Integer.valueOf(avaliacaoForm.getIndTipoAvaliacao()));	
			}
			if(avaliacaoForm.getSelBemImovel().equals("2")){ //avaliação por edificação
				if (avaliacaoForm.getEdificacao()!= null && !avaliacaoForm.getEdificacao().equals(""))
				{
					Edificacao edificacao = CadastroFacade.obterEdificacao(Integer.valueOf((avaliacaoForm.getEdificacao())));
					avaliacao.setEdificacao(edificacao);
				}
			}
			CadastroFacade.salvarAvaliacao(avaliacao);
			
			//limpar formulario
			avaliacaoForm.setActionType("incluir");
			avaliacaoForm.setSelBemImovel("1");// setar radio Bem Imovel no formulario
			avaliacaoForm.setDataAvaliacao("");
			avaliacaoForm.setEdificacao("");
			avaliacaoForm.setValor("");
			avaliacaoForm.setIndTipoAvaliacao("");
						
			addMessage("SUCESSO.11", request);	
			this.carregarListaAvalicao(form, request);
			
		} catch (ApplicationException appEx) {
			this.carregarListaAvalicao(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarListaAvalicao(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Avaliação"}, e);
		}
		
		return getActionForward();
    }
	
	public void carregarListaAvalicao(ActionForm form, HttpServletRequest request ) throws ApplicationException, Exception {
		
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
				
		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina), Integer.parseInt(totalRegistros));

		//recuperar codigo bem imovel
		AvaliacaoForm avaliacaoform = (AvaliacaoForm) form;
		avaliacaoform.setCodBemImovel(request.getParameter("codBemImovel"));
		avaliacaoform.setNrBemImovel(CadastroFacade.obterBemImovel(new Integer(request.getParameter("codBemImovel"))).getNrBemImovel().toString());
				
		BemImovel bemImovel = CadastroFacade.obterBemImovelComEdificacoes(Integer.parseInt(request.getParameter("codBemImovel")));
		avaliacaoform.setSomenteTerreno(bemImovel.getSomenteTerreno());
						
		request.setAttribute("edificacoes", bemImovel.getEdificacaos());
		
		pagina = CadastroFacade.listarAvaliacao(pagina, bemImovel);
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
			
	}
	
	
	public ActionForward alterarAvaliacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		
		AvaliacaoForm avaliacaoForm = (AvaliacaoForm) form;
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	
		setActionForward(mapping.findForward("pgEditAvaliacao"));

		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			this.carregarListaAvalicao(form, request);
			return mapping.findForward("pgEditAvaliacao");
		}	
				
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			throw new ApplicationException("AVISO.200");
		}	
		
		//altera avaliação
		
		try	{			
			Avaliacao avaliacao = CadastroFacade.obterAvaliacao( Integer.parseInt(avaliacaoForm.getCodAvaliacao()));
			
			Date dataAtual = new Date();
			BigDecimal bValor = Valores.converterStringParaBigDecimal(avaliacaoForm.getValor());
			
			avaliacao.setCpfResponsavel(sentinelaInterface.getCpf());
			avaliacao.setDataAvaliacao(sdf.parse(avaliacaoForm.getDataAvaliacao()));
			avaliacao.setValor(bValor);
			avaliacao.setTsAtualizacao(dataAtual);
			if (avaliacaoForm.getIndTipoAvaliacao() != null && !avaliacaoForm.getIndTipoAvaliacao().isEmpty()){
				avaliacao.setIndTipoAvaliacao(Integer.valueOf(avaliacaoForm.getIndTipoAvaliacao()));	
			}
			
			if(avaliacaoForm.getSelBemImovel().equals("2")){ //avaliação por edificação
				if (avaliacaoForm.getEdificacao()!= null && !avaliacaoForm.getEdificacao().equals(""))
				{
					Edificacao edificacao = CadastroFacade.obterEdificacao(Integer.parseInt(avaliacaoForm.getEdificacao()));
					avaliacao.setEdificacao(edificacao);
					avaliacao.setIndTipoAvaliacao(null);
				}
			}
			else {
				avaliacao.setEdificacao(null);
			}
			CadastroFacade.alterarAvaliacao(avaliacao);
			
			//limpar formulario
			avaliacaoForm.setActionType("incluir");
			avaliacaoForm.setSelBemImovel("1");// setar radio Bem Imovel no formulario
			avaliacaoForm.setDataAvaliacao("");
			avaliacaoForm.setEdificacao("");
			avaliacaoForm.setValor("");
						
			addMessage("SUCESSO.12", request);	
			this.carregarListaAvalicao(form, request);
			
		} catch (ApplicationException appEx) {
			this.carregarListaAvalicao(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarListaAvalicao(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Avaliação"}, e);
		}
		
		return getActionForward();
    }
	
	
	public ActionForward excluirAvaliacao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		
		setActionForward(mapping.findForward("pgEditAvaliacao"));
		AvaliacaoForm avaliacaoForm = (AvaliacaoForm) form;
			
		try {
			if (avaliacaoForm.getCodAvaliacao() != null) {
				
				CadastroFacade.excluirAvaliacao(Integer.parseInt(avaliacaoForm.getCodAvaliacao()));
				avaliacaoForm.setActionType("incluir");
				avaliacaoForm.setSelBemImovel("1");// setar radio Bem Imovel no formulario
				avaliacaoForm.setDataAvaliacao("");
				avaliacaoForm.setEdificacao("");
				avaliacaoForm.setValor("");
				avaliacaoForm.setIndTipoAvaliacao("");
				addMessage("SUCESSO.25", request);
			}

			this.carregarListaAvalicao(form, request);
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			this.carregarListaAvalicao(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarListaAvalicao(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Avaliação"}, e);
		}
	}
		
}