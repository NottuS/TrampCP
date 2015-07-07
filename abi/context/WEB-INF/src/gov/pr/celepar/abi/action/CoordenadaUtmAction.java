package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.CoordenadaUtmForm;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.CoordenadaUtm;
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

import org.apache.commons.lang.math.NumberUtils;
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
public class CoordenadaUtmAction extends BaseDispatchAction {
	
	private static Logger log4j = Logger.getLogger(CoordenadaUtmAction.class);
	
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
	 * Realiza carga da aba Coordenada UTM.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgEditCoordenadaUtm(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException, Exception {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		
		if (request.getParameter("codBemImovel")!=null && !request.getParameter("codBemImovel").equals("") ){

			this.carregarCoordenadas(form, request);

			if (request.getParameter("actionType").equals("alterar")){
				if (request.getParameter("codCoordenadaUtm")!= null){
					CoordenadaUtmForm coordenadaUtmForm = (CoordenadaUtmForm)form;
					CoordenadaUtm coordenadaUtm = CadastroFacade.obterCoordenadasUTM(Integer.valueOf(request.getParameter("codCoordenadaUtm")));
					coordenadaUtmForm.setCoordenadaX(coordenadaUtm.getCoordenadaX().toString());
					coordenadaUtmForm.setCoordenadaY(coordenadaUtm.getCoordenadaY().toString());
					coordenadaUtmForm.setActionType("alterar");
					coordenadaUtmForm.setCodCoordenadaUtm(request.getParameter("codCoordenadaUtm"));

				}
			}
			return mapping.findForward("pgEditCoordenadaUtm");
		}
		else
		{
			setActionForward(mapping.findForward("pgEditCoordenadaUtm"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","coordenadas UTM"});

		}
    }

	
	public void carregarCoordenadas(ActionForm form, HttpServletRequest request ) throws ApplicationException {
		
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
				
		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina), Integer.parseInt(totalRegistros));

		//recuperar codigo bem imovel
		CoordenadaUtmForm coordenadaUtmForm = (CoordenadaUtmForm) form;
		coordenadaUtmForm.setCodBemImovel(request.getParameter("codBemImovel"));
		coordenadaUtmForm.setNrBemImovel(CadastroFacade.obterBemImovel(new Integer(request.getParameter("codBemImovel"))).getNrBemImovel().toString());

		BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.parseInt(request.getParameter("codBemImovel")));
		coordenadaUtmForm.setSomenteTerreno(bemImovel.getSomenteTerreno());
								
		pagina = CadastroFacade.listarCoordenadasUTM(pagina, bemImovel);
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
	}
		

	public ActionForward salvarCoordenadas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		CoordenadaUtmForm coordenadaUtmForm = (CoordenadaUtmForm) form;
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		setActionForward(mapping.findForward("pgEditCoordenadaUtm"));
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return getActionForward();
		}	
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
					
			throw new ApplicationException("AVISO.200");
		}	
		
		try	{			
			CoordenadaUtm coordenadaUtm = new CoordenadaUtm();
	
			BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.parseInt(coordenadaUtmForm.getCodBemImovel()));
			
			Date dataAtual = new Date();
			coordenadaUtm.setBemImovel(bemImovel);
			coordenadaUtm.setCoordenadaX(NumberUtils.createBigDecimal(coordenadaUtmForm.getCoordenadaX()));
			coordenadaUtm.setCoordenadaY(NumberUtils.createBigDecimal(coordenadaUtmForm.getCoordenadaY()));
			coordenadaUtm.setCpfResponsavel(sentinelaInterface.getCpf());
			coordenadaUtm.setTsAtualizacao(dataAtual);
			coordenadaUtm.setTsInclusao(dataAtual);
			
			CadastroFacade.salvarCoordenadasUTM(coordenadaUtm);
			
			//limpar formulario
			
			coordenadaUtmForm.setCoordenadaX("");
			coordenadaUtmForm.setCoordenadaY("");
			coordenadaUtmForm.setActionType("incluir");
			
			addMessage("SUCESSO.13", request);	
			this.carregarCoordenadas(form, request);
			
		} catch (ApplicationException appEx) {
			this.carregarCoordenadas(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarCoordenadas(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Coordenadas UTM"}, e);
		}
	
	return getActionForward();
	}
	
	
	public ActionForward alterarCoordenadas(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException, Exception {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		CoordenadaUtmForm coordenadaUtmForm = (CoordenadaUtmForm) form;
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		setActionForward(mapping.findForward("pgEditCoordenadaUtm"));

		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return getActionForward();
		}	

		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			
			throw new ApplicationException("AVISO.200");
		}	

		try	{			
			CoordenadaUtm coordenadaUtm = CadastroFacade.obterCoordenadasUTM(Integer.parseInt(coordenadaUtmForm.getCodCoordenadaUtm()));

			Date dataAtual = new Date();
			coordenadaUtm.setCoordenadaX(NumberUtils.createBigDecimal(coordenadaUtmForm.getCoordenadaX()));
			coordenadaUtm.setCoordenadaY(NumberUtils.createBigDecimal(coordenadaUtmForm.getCoordenadaY()));
			coordenadaUtm.setCpfResponsavel(sentinelaInterface.getCpf());
			coordenadaUtm.setTsAtualizacao(dataAtual);
			
			if (!CadastroFacade.verificaCoordenadaUTMDuplicado(coordenadaUtm)){
				CadastroFacade.alterarCoordenadasUTM(coordenadaUtm);
			}else{
				throw new ApplicationException("AVISO.40",  new String[]{coordenadaUtm.getCoordenadaX().toString(), coordenadaUtm.getCoordenadaY().toString()}, ApplicationException.ICON_AVISO);
			}
			
			

			//limpar formulario
			coordenadaUtmForm.setActionType("");
			coordenadaUtmForm.setCoordenadaX("");
			coordenadaUtmForm.setCoordenadaY("");
			coordenadaUtmForm.setActionType("incluir");

			addMessage("SUCESSO.14", request);	
			this.carregarCoordenadas(form, request);

		} catch (ApplicationException appEx) {
			this.carregarCoordenadas(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarCoordenadas(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Coordenadas UTM"}, e);
		}

		return getActionForward();
	}
	
	
	public ActionForward excluirCoordendas(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws  ApplicationException, Exception{
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		CoordenadaUtmForm coordenadaUtmForm = (CoordenadaUtmForm) form;
		setActionForward(mapping.findForward("pgEditCoordenadaUtm"));
			
		try {
			if (coordenadaUtmForm.getCodCoordenadaUtm() != null) {
				
				CadastroFacade.excluirCoordenadasUTM(Integer.parseInt(coordenadaUtmForm.getCodCoordenadaUtm()));
				//limpar formulario
				coordenadaUtmForm.setActionType("incluir");
				coordenadaUtmForm.setCoordenadaX("");
				coordenadaUtmForm.setCoordenadaY("");
				addMessage("SUCESSO.25", request);
			}
			
			
			this.carregarCoordenadas(form, request);
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			this.carregarCoordenadas(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarCoordenadas(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Coordenadas UTM"}, e);
		}
	}
	
	
}