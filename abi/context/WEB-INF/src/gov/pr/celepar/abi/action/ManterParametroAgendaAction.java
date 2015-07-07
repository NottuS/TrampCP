package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.facade.OperacaoFacade;
import gov.pr.celepar.abi.form.ManterParametroAgendaForm;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.ParametroAgenda;
import gov.pr.celepar.abi.pojo.ParametroAgendaEmail;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Oksana
 * @version 1.0
 * @since 05/07/2011
 * 
 * Classe Action:
 * Responsavel por manipular os parametros da agenda
 */

public class ManterParametroAgendaAction extends BaseDispatchAction {

	
	/**
	 * Carrega pagina para alteração/inclusão de parâmetro agenda.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarInterfaceInicial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException { 
		
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		ManterParametroAgendaForm localForm = (ManterParametroAgendaForm)form;	
		try {
			
			localForm.setIndGrupoSentinela(request.getSession().getAttribute("indGrupoSentinela").toString());	
			localForm.setAdm(GrupoSentinela.ADM_GERAL.getDescricao());
		
			
			//lista instituicao
			Integer codInstituicao = 0;
			if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
				request.setAttribute("listaInstituicao", Util.htmlEncodeCollection(CadastroFacade.listarInstituicao()));
				setActionForward(mapping.findForward("pgConManterParametroAgenda"));
			}else{
				//Obter codInstituicao do usuario logado
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
				if (usuario == null){
					throw new ApplicationException("AVISO.97");
				}
				codInstituicao = usuario.getInstituicao().getCodInstituicao();
				localForm.setCodInstituicao(codInstituicao.toString());
				////localForm.setCodParametroAgenda(String.valueOf(1));
				ParametroAgenda parametroAgenda = OperacaoFacade.obterParametroAgendaUnico(codInstituicao);
				Instituicao instituicao = CadastroFacade.obterInstituicao(codInstituicao);
				localForm.setInstituicao(instituicao.getSiglaDescricao());
				
				Pagina pagina = new Pagina();
				if (parametroAgenda == null){
					localForm.setCodParametroAgenda(null);
					localForm.setNumeroDiasVencimentoCessaoDeUso(String.valueOf(0));
					localForm.setNumeroDiasVencimentoDoacao(String.valueOf(0));
					localForm.setNumeroDiasVencimentoNotificacao(String.valueOf(0));
					localForm.setNumeroDiasVencimentoVistoria(String.valueOf(0));
					localForm.setTempoCessao(String.valueOf(0));
					List<ParametroAgendaEmail> listaParametroAgendaEmail = new ArrayList<ParametroAgendaEmail>();
					pagina.setRegistros(listaParametroAgendaEmail);
					request.setAttribute("pagina", pagina);
					request.getSession().setAttribute("pagina", pagina);
				}else{
					localForm.setCodParametroAgenda(parametroAgenda.getCodParametroAgenda().toString());
					localForm.setNumeroDiasVencimentoCessaoDeUso(String.valueOf(parametroAgenda.getNumeroDiasVencimentoCessaoDeUso()));
					localForm.setNumeroDiasVencimentoDoacao(String.valueOf(parametroAgenda.getNumeroDiasVencimentoDoacao()));
					localForm.setNumeroDiasVencimentoNotificacao(String.valueOf(parametroAgenda.getNumeroDiasVencimentoNotificacao()));
					localForm.setNumeroDiasVencimentoVistoria(String.valueOf(parametroAgenda.getNumeroDiasVencimentoVistoria()));
					localForm.setTempoCessao(String.valueOf(parametroAgenda.getTempoCessao()));
					List<ParametroAgendaEmail> listaParametroAgendaEmail = Util.setToList(parametroAgenda.getListaParametroAgendaEmail());
					pagina.setRegistros(listaParametroAgendaEmail);
					request.setAttribute("pagina", pagina);
					request.getSession().setAttribute("pagina", pagina);
				}
				setActionForward(mapping.findForward("pgEditManterParametroAgenda"));
			}
			
		} catch (ApplicationException appEx) {			
			throw appEx;			
		} catch (Exception e) {
			throw new ApplicationException("ERRO.200", new String[]{"edição","carregarInterfaceInicial"}, e);
		}		
		return getActionForward();
	}
	
	
	public ActionForward carregarInterfaceEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException { 
		
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		ManterParametroAgendaForm localForm = (ManterParametroAgendaForm)form;	
		try {
			
			localForm.setIndGrupoSentinela(request.getSession().getAttribute("indGrupoSentinela").toString());	
			localForm.setAdm(GrupoSentinela.ADM_GERAL.getDescricao());
		
			//lista instituicao
			Integer codInstituicao = 0;
			codInstituicao = Integer.valueOf(localForm.getCodInstituicao());
			localForm.setCodInstituicao(codInstituicao.toString());
			ParametroAgenda parametroAgenda = OperacaoFacade.obterParametroAgendaUnico(codInstituicao);
			Instituicao instituicao = CadastroFacade.obterInstituicao(codInstituicao);
			localForm.setInstituicao(instituicao.getSiglaDescricao());
			
			Pagina pagina = new Pagina();
			if (parametroAgenda == null){
				localForm.setCodParametroAgenda(null);
				localForm.setNumeroDiasVencimentoCessaoDeUso(String.valueOf(0));
				localForm.setNumeroDiasVencimentoDoacao(String.valueOf(0));
				localForm.setNumeroDiasVencimentoNotificacao(String.valueOf(0));
				localForm.setNumeroDiasVencimentoVistoria(String.valueOf(0));
				localForm.setTempoCessao(String.valueOf(0));
				List<ParametroAgendaEmail> listaParametroAgendaEmail = new ArrayList<ParametroAgendaEmail>();
				pagina.setRegistros(listaParametroAgendaEmail);
				request.setAttribute("pagina", pagina);
				request.getSession().setAttribute("pagina", pagina);
			}else{
				localForm.setCodParametroAgenda(parametroAgenda.getCodParametroAgenda().toString());
				localForm.setNumeroDiasVencimentoCessaoDeUso(String.valueOf(parametroAgenda.getNumeroDiasVencimentoCessaoDeUso()));
				localForm.setNumeroDiasVencimentoDoacao(String.valueOf(parametroAgenda.getNumeroDiasVencimentoDoacao()));
				localForm.setNumeroDiasVencimentoNotificacao(String.valueOf(parametroAgenda.getNumeroDiasVencimentoNotificacao()));
				localForm.setNumeroDiasVencimentoVistoria(String.valueOf(parametroAgenda.getNumeroDiasVencimentoVistoria()));
				localForm.setTempoCessao(String.valueOf(parametroAgenda.getTempoCessao()));
				List<ParametroAgendaEmail> listaParametroAgendaEmail = Util.setToList(parametroAgenda.getListaParametroAgendaEmail());
				pagina.setRegistros(listaParametroAgendaEmail);
				request.setAttribute("pagina", pagina);
				request.getSession().setAttribute("pagina", pagina);
			}
			setActionForward(mapping.findForward("pgEditManterParametroAgenda"));
		} catch (ApplicationException appEx) {			
			throw appEx;			
		} catch (Exception e) {
			throw new ApplicationException("ERRO.200", new String[]{"edição","carregarInterfaceEditar"}, e);
		}		
		return getActionForward();
	}
	

	/**
	 * Efetua a Inclusao/Alterar dos dados do respectivo Caso de Uso no Banco de Dados.<br>
	 * @author Oksana
	 * @since 05/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward incluirAlterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		ManterParametroAgendaForm localForm = (ManterParametroAgendaForm) form;			
		setActionForward(mapping.findForward("pgEditManterParametroAgenda"));
		try{

			if (!validaForm(mapping, form, request)) {
				return carregarInterfaceInicial(mapping, form, request, response);
			}
			//executa as validações/tratamentos
			if (localForm.getNumeroDiasVencimentoCessaoDeUso().isEmpty()){
				localForm.setNumeroDiasVencimentoCessaoDeUso("0");
			}
			if (localForm.getNumeroDiasVencimentoDoacao().isEmpty()){
				localForm.setNumeroDiasVencimentoDoacao("0");
			}
			if (localForm.getNumeroDiasVencimentoNotificacao().isEmpty()){
				localForm.setNumeroDiasVencimentoNotificacao("0");
			}
			if (localForm.getNumeroDiasVencimentoVistoria().isEmpty()){
				localForm.setNumeroDiasVencimentoVistoria("0");
			}
			if (localForm.getTempoCessao().isEmpty()){
				localForm.setTempoCessao("0");
			}

			//prepara para a inclusão
			ParametroAgenda parametroAgenda = new ParametroAgenda();
			if (localForm.getCodParametroAgenda() != null && !localForm.getCodParametroAgenda().isEmpty()){
				parametroAgenda = OperacaoFacade.obterParametroAgendaUnico(Integer.valueOf(localForm.getCodInstituicao()));
				parametroAgenda.setTsAtualizacao(new Date());
			}else{
				parametroAgenda.setTsInclusao(new Date());
				Instituicao instituicao = new Instituicao();
				instituicao = CadastroFacade.obterInstituicao(Integer.valueOf(localForm.getCodInstituicao()));
				parametroAgenda.setInstituicao(instituicao);
			}
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			parametroAgenda.setCpfResponsavel(sentinelaInterface.getCpf());

			parametroAgenda.setNumeroDiasVencimentoCessaoDeUso(Integer.valueOf(localForm.getNumeroDiasVencimentoCessaoDeUso()));
			parametroAgenda.setNumeroDiasVencimentoDoacao(Integer.valueOf(localForm.getNumeroDiasVencimentoDoacao()));
			parametroAgenda.setNumeroDiasVencimentoNotificacao(Integer.valueOf(localForm.getNumeroDiasVencimentoNotificacao()));
			parametroAgenda.setNumeroDiasVencimentoVistoria(Integer.valueOf(localForm.getNumeroDiasVencimentoVistoria()));
			parametroAgenda.setTempoCessao(Integer.valueOf(localForm.getTempoCessao()));
			
			Pagina pagina = (Pagina) request.getSession().getAttribute("pagina");
			List<ParametroAgendaEmail> listaParametroAgendaEmailNovo =  new ArrayList<ParametroAgendaEmail>();
			if (pagina != null && !pagina.getRegistros().isEmpty()){
				List<ParametroAgendaEmail> listaParametroAgendaEmail = (List<ParametroAgendaEmail>) pagina.getRegistros();
				
				for (ParametroAgendaEmail parametroAgendaEmail: listaParametroAgendaEmail){
					ParametroAgendaEmail parametroAgendaEmailNovo = new ParametroAgendaEmail();
					parametroAgendaEmailNovo.setParametroAgenda(parametroAgenda);
					parametroAgendaEmailNovo.setEmail(parametroAgendaEmail.getEmail());
					listaParametroAgendaEmailNovo.add(parametroAgendaEmailNovo);
				}
			}
			parametroAgenda.setListaParametroAgendaEmail(Util.listToSet(listaParametroAgendaEmailNovo));
			
			OperacaoFacade.salvarParametroAgenda(parametroAgenda, listaParametroAgendaEmailNovo);
			
			this.addMessage("SUCESSO.63", request);
		}catch (ApplicationException ae) {
			setActionForward(carregarInterfaceInicial(mapping, form, request, response));
			throw ae;
		}catch (Exception e) {
			setActionForward(carregarInterfaceInicial(mapping, form, request, response));
			throw new ApplicationException("mensagem.erro.9001", new String[]{ManterParametroAgendaAction.class.getSimpleName()+".incluirAlterar()"}, e, ApplicationException.ICON_ERRO);
		}
		return carregarInterfaceInicial(mapping, localForm, request, response);
	}

		
	
	/**
	 * @author Oksana
	 * @since 06/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward atualizarEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			ManterParametroAgendaForm localForm = (ManterParametroAgendaForm)form;	
			
			setActionForward(mapping.findForward("tabelaAjax"));
			Pagina pagina = (Pagina) request.getSession().getAttribute("pagina");
			List<ParametroAgendaEmail> listaParametroAgendaEmail = (List<ParametroAgendaEmail>) pagina.getRegistros();
			String email = localForm.getEmail();
			if (email != null && !email.isEmpty()){
				Boolean existe = false;
				for (ParametroAgendaEmail pae: listaParametroAgendaEmail){
					if (pae.getEmail().equals(email)){
						existe = true;
						break;
					}
				}
				if (!existe){
					ParametroAgendaEmail pae = new ParametroAgendaEmail();
					pae.setEmail(email);
					listaParametroAgendaEmail.add(pae);
					pagina.setRegistros(listaParametroAgendaEmail);
					request.setAttribute("pagina", pagina);
					request.getSession().setAttribute("pagina", pagina);
					localForm.setEmail(null);
				}else{
					addMessage("AVISO.80", request);
				}
			}
			return getActionForward();
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[] { ManterParametroAgendaAction.class.getSimpleName()+ ".atualizarEmail()" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * @author Oksana
	 * @since 06/07/2011
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward excluirEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			ManterParametroAgendaForm localForm = (ManterParametroAgendaForm)form;	
			setActionForward(mapping.findForward("tabelaAjax"));
			Pagina pagina = (Pagina) request.getSession().getAttribute("pagina");
			List<ParametroAgendaEmail> listaParametroAgendaEmail = (List<ParametroAgendaEmail>) pagina.getRegistros();
			List<ParametroAgendaEmail> listaParametroAgendaEmailNovo =  new ArrayList<ParametroAgendaEmail>();
			String email = localForm.getEmail();
			if (email != null && !email.isEmpty()){
				for (ParametroAgendaEmail pae: listaParametroAgendaEmail){
					if (!pae.getEmail().equals(email)){
						listaParametroAgendaEmailNovo.add(pae);
					}
				} 
				pagina.setRegistros(listaParametroAgendaEmailNovo);
				request.setAttribute("pagina", pagina);
				request.getSession().setAttribute("pagina", pagina);
			}
			return getActionForward();
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[] { ManterParametroAgendaAction.class.getSimpleName()+ ".excluirEmail()" }, e, ApplicationException.ICON_ERRO);
		}
	}

}
