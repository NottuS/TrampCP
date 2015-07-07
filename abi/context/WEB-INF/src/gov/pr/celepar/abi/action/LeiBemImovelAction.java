package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.LeiBemImovelForm;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.LeiBemImovel;
import gov.pr.celepar.abi.pojo.TipoLeiBemImovel;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Data;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.text.ParseException;
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
 * @version 1.0
 * @since 20/01/2010
 * 
 *        Classe Action: Responsável por manipular as requisições dos usuários
 */
public class LeiBemImovelAction extends BaseDispatchAction {

	private static Logger log4j = Logger.getLogger(LeiBemImovelAction.class);

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
	 * Realiza carga da aba Lei.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgEditLeiBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ApplicationException,Exception {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		if (request.getParameter("codBemImovel")!=null && !request.getParameter("codBemImovel").equals("") ){
			this.carregarListaLeiBemImovel(form, request);
			
			if (request.getParameter("actionType").equals("alterar")){
				if (request.getParameter("codLeiBemImovel")!= null){
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	
					LeiBemImovelForm leiBemImovelForm = (LeiBemImovelForm)form;
					LeiBemImovel leiBemImovel = CadastroFacade.obterLeiBemImovel(Integer.valueOf(request.getParameter("codLeiBemImovel")));
					leiBemImovelForm.setTipoLeiBemImovel(leiBemImovel.getTipoLeiBemImovel().getCodTipoLeiBemImovel().toString());
					leiBemImovelForm.setNumero(Long.toString(leiBemImovel.getNumero()));
					leiBemImovelForm.setDataAssinatura(sdf.format(leiBemImovel.getDataAssinatura()));					
					leiBemImovelForm.setDataPublicacao(sdf.format(leiBemImovel.getDataPublicacao()));
					leiBemImovelForm.setNrDioe(Long.toString(leiBemImovel.getNrDioe()));
					leiBemImovelForm.setActionType("alterar");
					leiBemImovelForm.setCodLeiBemImovel(request.getParameter("codLeiBemImovel"));
				}
			}else{
				LeiBemImovelForm leiBemImovelForm = (LeiBemImovelForm)form;
				leiBemImovelForm.setNumero("");
			}
				return mapping.findForward("pgEditLeiBemImovel");
		}
		else
		{
			setActionForward(mapping.findForward("error"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","Lei de Bem Imóvel"});
		}

	}

	public void carregarListaLeiBemImovel(ActionForm form, HttpServletRequest request) throws ApplicationException {

		// Salva o TOKEN para evitar duxplo submit
		saveToken(request);

		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		
		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));

		// recuperar codigo bem imovel
		LeiBemImovelForm leiBemImovelForm = (LeiBemImovelForm) form;		
		leiBemImovelForm.setActionType("incluir");		
		leiBemImovelForm.setCodBemImovel(request.getParameter("codBemImovel"));	
		leiBemImovelForm.setNrBemImovel(CadastroFacade.obterBemImovel(new Integer(request.getParameter("codBemImovel"))).getNrBemImovel().toString());
		
		BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(request.getParameter("codBemImovel")));
		leiBemImovelForm.setSomenteTerreno(bemImovel.getSomenteTerreno());
		request.setAttribute("tipoLeiBemImovels", Util.htmlEncodeCollection(CadastroFacade.listarTipoLeiBemImovels()));
		pagina = CadastroFacade.listarLeiBemImovel(pagina, bemImovel);
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);

	}
	

	public ActionForward salvarLeiBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		LeiBemImovelForm leiBemImovelForm = (LeiBemImovelForm) form;
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			this.carregarListaLeiBemImovel(form, request);
			return mapping.findForward("pgEditLeiBemImovel");
		}	
		
		//valida se data de assinatura menor que data atual
		try{
			Date data = sdf.parse(leiBemImovelForm.getDataAssinatura());
			if (data.after(new Date()))
			{
				this.carregarListaLeiBemImovel(form, request);
				setActionForward(mapping.findForward("pgEditLeiBemImovel"));
				throw new ApplicationException("ERRO.ocorrenciaDocumentacao.data", new String[]{"Data de Assinatura"});
			}
		}catch (ParseException e)
		{
			this.carregarListaLeiBemImovel(form, request);
			setActionForward(mapping.findForward("pgEditLeiBemImovel"));
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Lei do Bem Imóvel"}, e);
		}

		// Valida se data de publicação é menor que data atual
		try{
			
			Date data = Data.formataData(leiBemImovelForm.getDataPublicacao());  

			if (Data.isFuturo(data))
			{
				this.carregarListaLeiBemImovel(form, request);
				setActionForward(mapping.findForward("pgEditLeiBemImovel"));
				throw new ApplicationException("ERRO.ocorrenciaDocumentacao.data", new String[]{"Data de Publicação"});
			}
		}catch (ParseException e)
		{
			this.carregarListaLeiBemImovel(form, request);
			setActionForward(mapping.findForward("pgEditLeiBemImovel"));
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Lei do Bem Imóvel"}, e);
		} catch (Exception e) {
			this.carregarListaLeiBemImovel(form, request);
			setActionForward(mapping.findForward("pgEditLeiBemImovel"));
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Lei do Bem Imóvel"}, e);
		}

		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			this.carregarListaLeiBemImovel(form, request);
			setActionForward(mapping.findForward("pgEditLeiBemImovel"));
			throw new ApplicationException("AVISO.200");
		}	
		
		try	{			
			
			LeiBemImovel leiBemImovel= new LeiBemImovel();
			BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(leiBemImovelForm.getCodBemImovel()));
			Date dataAtual = new Date();
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			leiBemImovel.setBemImovel(bemImovel);
			leiBemImovel.setNumero(Long.parseLong(leiBemImovelForm.getNumero()));			
			leiBemImovel.setDataAssinatura(sdf.parse(leiBemImovelForm.getDataAssinatura()));
			leiBemImovel.setDataPublicacao(sdf.parse(leiBemImovelForm.getDataPublicacao()));
			leiBemImovel.setNrDioe(Long.parseLong(leiBemImovelForm.getNrDioe()));			
			leiBemImovel.setCpfResponsavel(sentinelaInterface.getCpf());
			leiBemImovel.setTsAtualizacao(dataAtual);
			leiBemImovel.setTsInclusao(dataAtual);

			if (leiBemImovelForm.getTipoLeiBemImovel()!= null && !leiBemImovelForm.getTipoLeiBemImovel().equals(""))
			{
				TipoLeiBemImovel tipoLeiBemImovel = CadastroFacade.obterTipoLeiBemImovel(Integer.valueOf(leiBemImovelForm.getTipoLeiBemImovel()));
				leiBemImovel.setTipoLeiBemImovel(tipoLeiBemImovel);
			}
			
			CadastroFacade.salvarLeiBemImovel(leiBemImovel);
			
			//limpar formulario
			leiBemImovelForm.setActionType("");
			leiBemImovelForm.setTipoLeiBemImovel("");
			leiBemImovelForm.setNumero("");
			leiBemImovelForm.setDataAssinatura("");
			leiBemImovelForm.setDataPublicacao("");			
			leiBemImovelForm.setNrDioe("");			
			leiBemImovelForm.setActionType("incluir");
			
			addMessage("SUCESSO.3", request);	
			this.carregarListaLeiBemImovel(form, request);
			
		} catch (ApplicationException appEx) {
			this.carregarListaLeiBemImovel(form, request);
			setActionForward(mapping.findForward("pgEditLeiBemImovel"));
			throw appEx;
		} catch (Exception e) {
			this.carregarListaLeiBemImovel(form, request);
			setActionForward(mapping.findForward("pgEditLeiBemImovel"));
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Lei do Bem Imóvel"}, e);
		}
	
	return mapping.findForward("pgEditLeiBemImovel");
	}
	
	
	public ActionForward alterarLeiBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException, Exception {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		LeiBemImovelForm leiBemImovelForm = (LeiBemImovelForm) form;
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);

		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditLeiBemImovel");
		}	

		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			this.carregarListaLeiBemImovel(form, request);
			setActionForward(mapping.findForward("pgEditLeiBemImovel"));
			throw new ApplicationException("AVISO.200");
		}	

		try	{			
			LeiBemImovel leiBemImovel= CadastroFacade.obterLeiBemImovel(Integer.valueOf (leiBemImovelForm.getCodLeiBemImovel()));

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		
			BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(leiBemImovelForm.getCodBemImovel()));

			Date dataAtual = new Date();
			leiBemImovel.setBemImovel(bemImovel);
			leiBemImovel.setNumero(Long.parseLong(leiBemImovelForm.getNumero()));			
			leiBemImovel.setDataAssinatura(sdf.parse(leiBemImovelForm.getDataAssinatura()));
			leiBemImovel.setDataPublicacao(sdf.parse(leiBemImovelForm.getDataPublicacao()));
			leiBemImovel.setNrDioe(Long.parseLong(leiBemImovelForm.getNrDioe()));			
			leiBemImovel.setCpfResponsavel(sentinelaInterface.getCpf());
			leiBemImovel.setTsAtualizacao(dataAtual);


			if (leiBemImovelForm.getTipoLeiBemImovel()!= null && !leiBemImovelForm.getTipoLeiBemImovel().equals(""))
			{
				TipoLeiBemImovel tipoLeiBemImovel = CadastroFacade.obterTipoLeiBemImovel(Integer.valueOf(leiBemImovelForm.getTipoLeiBemImovel()));
				leiBemImovel.setTipoLeiBemImovel(tipoLeiBemImovel);
			}
						
			
			
			
			CadastroFacade.alterarLeiBemImovel(leiBemImovel);

			//limpar formulario
			leiBemImovelForm.setActionType("");
			leiBemImovelForm.setTipoLeiBemImovel("");
			leiBemImovelForm.setNumero("");
			leiBemImovelForm.setDataAssinatura("");
			leiBemImovelForm.setDataPublicacao("");
			leiBemImovelForm.setNrDioe("");
			leiBemImovelForm.setActionType("incluir");

			addMessage("SUCESSO.4", request);	
			this.carregarListaLeiBemImovel(form, request);

		} catch (ApplicationException appEx) {
			this.carregarListaLeiBemImovel(form, request);
			setActionForward(mapping.findForward("pgEditLeiBemImovel"));
			throw appEx;
		} catch (Exception e) {
			this.carregarListaLeiBemImovel(form, request);
			setActionForward(mapping.findForward("pgEditLeiBemImovel"));
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Lei do Bem Imóvel"}, e);
		}

		return mapping.findForward("pgEditLeiBemImovel");
	}
	
	
	public ActionForward excluirLeiBemImovel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		LeiBemImovelForm leiBemImovelForm = (LeiBemImovelForm) form;
			
		try {
			if (leiBemImovelForm.getCodLeiBemImovel() != null) {
				
				CadastroFacade.excluirLeiBemImovel(Integer.parseInt(leiBemImovelForm.getCodLeiBemImovel()));

				//limpar formulario
				leiBemImovelForm.setActionType("");
				leiBemImovelForm.setTipoLeiBemImovel("");
				leiBemImovelForm.setNumero("");
				leiBemImovelForm.setDataAssinatura("");
				leiBemImovelForm.setDataPublicacao("");
				leiBemImovelForm.setActionType("incluir");
				leiBemImovelForm.setNrDioe("");
				addMessage("SUCESSO.25", request);
			}
			
			
			this.carregarListaLeiBemImovel(form, request);
			return mapping.findForward("pgEditLeiBemImovel");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditLeiBemImovel"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditLeiBemImovel"));
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Lei de Bem Imóvel"}, e);
		}
	}
		
	/**
	 * Carrega a página para selecionar uma lei
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public ActionForward carregarListaLeiBemImovelLocalizar(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		setActionForward(mapping.findForward("pgListLeiBemImovelLocalizar"));

		String codBemImovel = "0";
		String nrBemImovel = "0";
		// recuperar codigo bem imovel
		if (request.getSession().getAttribute("bemImovelSimplificado") != null) {
			BemImovel aux = (BemImovel) request.getSession().getAttribute("bemImovelSimplificado");
			codBemImovel = aux.getCodBemImovel().toString();
			nrBemImovel = aux.getNrBemImovel().toString();
		}

		LeiBemImovelForm localForm = (LeiBemImovelForm) form;		
		String camposPesquisaUCOrigem = (request.getParameter("camposPesquisaUCOrigem") != null ? request.getParameter("camposPesquisaUCOrigem").toString() : "");			
		String codInstituicao = (request.getParameter("codInstituicao") != null ? request.getParameter("codInstituicao").toString() : "");			
		localForm.setCamposPesquisaUCOrigem(camposPesquisaUCOrigem);
		request.getSession().setAttribute("camposPesquisaUCOrigem", camposPesquisaUCOrigem);
		
		String actionUCOrigem = (request.getParameter("actionUCOrigem") != null ? request.getParameter("actionUCOrigem").toString() : "");			
		localForm.setActionUCOrigem(actionUCOrigem);
		request.getSession().setAttribute("actionUCOrigem", actionUCOrigem);

		localForm.setCodBemImovel(codBemImovel);
		localForm.setCodInstituicao(codInstituicao);
		
		LeiBemImovel lei = new LeiBemImovel();
		lei.setTipoLeiBemImovel(new TipoLeiBemImovel());
		if ("doacaoBemImovel".equals(actionUCOrigem)) {
			lei.getTipoLeiBemImovel().setCodTipoLeiBemImovel(Integer.valueOf(2));
		} else if ("cessaoDeUsoBemImovel".equals(actionUCOrigem)) {
			lei.getTipoLeiBemImovel().setCodTipoLeiBemImovel(Integer.valueOf(4));
		}
		lei.setBemImovel(new BemImovel());
		lei.getBemImovel().setCodBemImovel(Integer.valueOf(codBemImovel));
		
		Pagina pagina = new Pagina(0,0,0);
		pagina = CadastroFacade.listarLeiBemImovelExcetoTipoLei(pagina, lei);
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.getSession().setAttribute("pagina", pagina);
		
		if (pagina.getTotalRegistros() == 0){
			throw new ApplicationException("AVISO.65", new String[]{nrBemImovel}, ApplicationException.ICON_AVISO);
		}

		return getActionForward();

	}

}