package gov.pr.celepar.abi.generico.action;

import gov.pr.celepar.abi.generico.form.BaseForm;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Sessao;
import gov.pr.celepar.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SuppressWarnings("unchecked")
public abstract class BaseAction extends BaseDispatchAction{
	
	private static final String PILHA_FORM_SESSAO = "pilhaFormSessao";
	
	protected static final String LOGINICIO = "Inicio";
	protected static final String LOGFIM = "Fim";
	
	protected static final String ACAO_INCLUIR = "incluir";
	protected static final String ACAO_ALTERAR = "alterar";
	protected static final String ACAO_EXCLUIR = "excluir";
	protected static final String ACAO_EXIBIR = "exibir";
	protected static final String ACAO_DESATIVAR = "desativar";
	protected static final String ACAO_CONSULTAR = "consultar";
	protected static final String ACAO_COMPLEMENTAR = "complementar";
	protected static final String ACAO_SALVAR_RASCUNHO = "salvarRascunho";
	protected static final String ACAO_VOLTAR = "voltar";
	
	protected static final String PG_CON = "pgCon";
	protected static final String PG_CON_AJAX = "pgConAjax";
	protected static final String PG_EDIT = "pgEdit";
	protected static final String PG_VIEW = "pgView";
	
	public abstract ActionForward carregarInterfaceInicial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException;
		
	/**
	 * Carrega a pagina inicial do respectivo Caso de Uso.<br>
	 * @author Daniel
	 * @since 22/01/2008
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward iniciar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		List<String> listaExcecoes = new ArrayList<String>();
		listaExcecoes.add("SENTINELA_LOGIN");
		listaExcecoes.add("SENTINELA_SECURITY_CODE");
		listaExcecoes.add("usuarioDTO");
		Sessao.limparSessao(request, listaExcecoes);
		
		return continuar(mapping, form, request, response);	
	}
	
	/**
	 * Apresenta a pagina inicial do respectivo Caso de Uso, retorno de chamada ou redirecionamento.<br>
	 * @author Daniel
	 * @since 22/01/2008
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public ActionForward continuar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		return carregarInterfaceInicial(mapping, form, request, response);	
	}
	
	/**
	 * Finaliza o respectivo Caso de Uso e retorna para a tela inicial do Sistema.<br>
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
			BaseForm frm = this.obterFormSessao(request); 
			if(frm == null){
				return mapping.findForward("inicial");
			}else{
				return mapping.findForward(frm.getUcsRetorno());
			}
			
		}catch (Exception e) {			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".concluir()"}, e, ApplicationException.ICON_ERRO);
		}
	}
	
	
	/**
	 * Finaliza um fluxo do UCS e verifica se deve voltar para um fluxo do mesmo UCS ou de outro UCS (UCS Chamador).<br>
	 * @author Arildo
	 * @since 31/03/2010
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @param forwardName : String
	 * @return ActionForward
	 * @throws ApplicationException
	 * @reuso Adaptado do Sistema GMS
	 */
	public ActionForward concluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String forwardName)
											throws ApplicationException{
		
		if(StringUtils.isBlank(forwardName)){
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".concluir()"}, ApplicationException.ICON_ERRO);
		}
		
		try{
			BaseForm frm = this.obterFormSessao(request); 
			if(frm == null){
				return mapping.findForward(forwardName);
			}else{
				return mapping.findForward(frm.getUcsRetorno());
			}
			
		}catch (Exception e) {			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".concluir()"}, e, ApplicationException.ICON_ERRO);
		}
	}
	
	
	/**
	 * Armazena o id de um objeto que foi incluido/alterado/excluido pelo UCS destino no form do UCS Chamador.
	 * @author arildogueno, dpgilli
	 * @param request HttpServletRequest
	 * @param id String
	 * @throws ApplicationException
	 */
	public void adicionarIdObjeto(HttpServletRequest request, String id) throws ApplicationException{
		BaseForm frm = obterFormSessao(request);
		List<String> idObjetos = frm.getListaIdObjetos();
		idObjetos.add(id);
		frm.setListaIdObjetos(idObjetos);
		armazenarFormSessao(request, frm);
		
	}
	
	
	/**
	 * Armazenar form passado como parametro na sessao, verifica tambem se há caso de uso de retorno.<br>
	 * @author Daniel
	 * @since 19/03/2009
	 * @param form : BaseForm
	 * @param request : HttpServletRequest
	 * @throws ApplicationException
	 * @reuso Adaptado do Sistema GMS
	 */
	public void armazenarFormSessao(HttpServletRequest request, BaseForm form) throws ApplicationException{

		try{
			Stack<BaseForm> pilhaForm = (Stack<BaseForm>)Sessao.obterAtributoSessao(request, PILHA_FORM_SESSAO); 
			
			if(pilhaForm == null){
				pilhaForm = new Stack<BaseForm>();
			}

			//Evitar que se carrege o mesmo form na pilha atraves do refresh
			BaseForm ultimoPilha = this.obterFormSessao(request);
			
			if(ultimoPilha == null || (ultimoPilha.getClass() != form.getClass())){
				pilhaForm.push(form);
			}			
			
			Sessao.adicionarAtributoSessao(request, PILHA_FORM_SESSAO, pilhaForm);
			
		}catch (ApplicationException ae) {			
			throw ae;
		}catch (Exception e) {			
			log.error(e.getMessage(),e);			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".armazenarFormSessao()"}, e, ApplicationException.ICON_ERRO);
		}
			
	}	

	/**
	 * Obtem o primeiro form da pilha de forms armazenada na sessao do usuario, caso a pilha esteja vazia retira o atributo da sessao.<br>
	 * @author Daniel
	 * @since 19/03/2009
	 * @param request : HttpServletRequest
	 * @return BaseForm
	 * @throws ApplicationException
	 * @reuso Adaptado do Sistema GMS
	 */
	public BaseForm obterFormSessao(HttpServletRequest request) throws ApplicationException{

		try{
			Stack<BaseForm> pilhaForm = (Stack<BaseForm>)Sessao.obterAtributoSessao(request, PILHA_FORM_SESSAO); 
			if(pilhaForm == null){
				return null;
			}
			
			if(pilhaForm.isEmpty()){
				Sessao.removerAtributoDaSessao(request, PILHA_FORM_SESSAO);
				return null;
			}
			BaseForm bf = pilhaForm.pop();
			armazenarFormSessao(request, bf);
			return bf;
			
		}catch (Exception e) {			
			log.error(e.getMessage(),e);			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".obterFormSessao()"}, e, ApplicationException.ICON_ERRO);
		}
			
	}

	
	/**
	 * Obtem o primeiro form da pilha de forms armazenada na sessao do usuario, caso a pilha esteja vazia retira o atributo da sessao.<br>
	 * @author Daniel
	 * @since 19/03/2009
	 * @param request : HttpServletRequest
	 * @return BaseForm
	 * @throws ApplicationException
	 * @reuso Adaptado do Sistema GMS
	 */
	public BaseForm removerFormSessao(HttpServletRequest request) throws ApplicationException{

		try{
			Stack<BaseForm> pilhaForm = (Stack<BaseForm>)Sessao.obterAtributoSessao(request, PILHA_FORM_SESSAO); 
			if(pilhaForm == null){
				return null;
			}
			
			if(pilhaForm.isEmpty()){
				Sessao.removerAtributoDaSessao(request, PILHA_FORM_SESSAO);
				return null;
			}
			return pilhaForm.pop();
			
		}catch (Exception e) {			
			log.error(e.getMessage(),e);			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".removerFormSessao()"}, e, ApplicationException.ICON_ERRO);
		}
			
	}

	
	/**
	 * Verifica se o fluxo do UCS foi chamada por outro UCS.<br>
	 * @author arildogueno
	 * @param request : HttpServletRequest
	 * @return BaseForm
	 * @throws ApplicationException
	 * @reuso Adaptado do Sistema GMS
	 */
	public Boolean verificarExistenciaUCSChamador(HttpServletRequest request) throws ApplicationException{

		try{
			Stack<BaseForm> pilhaForm = (Stack<BaseForm>)Sessao.obterAtributoSessao(request, PILHA_FORM_SESSAO); 
			if(pilhaForm == null){
				return false;
			}
			
			if(pilhaForm.isEmpty()){
				return false;
			}
			return true;
			
		}catch (Exception e) {			
			log.error(e.getMessage(),e);			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".verificarExistenciaUCSChamador()"}, e, ApplicationException.ICON_ERRO);
		}
			
	}
	
	
	/**
	 * Aciona outro caso de uso, redirecionando o fluxo pra outra Action armazenando na sessao o form submetido e o caso de uso de retorno.<br>
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
	public ActionForward acionarOutroCasoUso(ActionMapping mapping,	ActionForm form, HttpServletRequest request, HttpServletResponse response)
											throws ApplicationException {
		try{
			log.info("Iniciando o redirecionamento pra outra Action....");									
						
			BaseForm frm = (BaseForm) form;		
			frm.setUcsRetorno(frm.getUcsChamador()); 
			
			this.armazenarFormSessao(request, frm);
						
			return mapping.findForward(frm.getUcsDestino());
		
		}catch (ApplicationException ae) {						
			throw ae;
		}catch (Exception e) {
			log.error(e.getMessage(),e);			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".acionarOutroCasoUso()"}, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Metodo que trata o retorno de uma chamada feita a outro caso de uso, obtendo o form e adicionando-o ao request e retornando ao caso de uso de origem.
	 * @author Daniel
	 * @author arildogueno
	 * @since 19/03/2008
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response :HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 * @reuso Adaptado do Sistema GMS
	 */
	public ActionForward tratarRetornoOutroCasoUso(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) 
												   throws ApplicationException {
		try{
			log.info("Iniciando retorno para Action de origem...");	
			BaseForm frm = (BaseForm) this.removerFormSessao(request);

			//Usa a classe para obter o nome do form
			request.setAttribute(StringUtil.primeiraLetraToLowerCase(frm.getClass().getSimpleName()), frm);
			
			return mapping.findForward(frm.getUcsRetorno());
			
		}catch (ApplicationException ae) {
			throw ae;
		}catch (Exception e) {
			log.error(e.getMessage(),e);			
			throw new ApplicationException("mensagem.erro.geral", new String[]{getClass().getSimpleName()+".tratarRetornoOutroCasoUso()"}, e, ApplicationException.ICON_ERRO);
		}
	}

}
