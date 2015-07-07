package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.facade.OperacaoFacade;
import gov.pr.celepar.abi.form.VistoriadorForm;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.pojo.Vistoriador;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.CPF;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno J. Fernandes	
 * @version 1.0
 * @since 04/07/2011
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class VistoriadorAction extends BaseDispatchAction {
	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de vistoriadores. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward pesquisarVistoriador (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		VistoriadorForm vistoriadorForm = (VistoriadorForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String cpf = (vistoriadorForm.getConCpf() ==null || vistoriadorForm.getConCpf().isEmpty()) ? null : StringUtil.tiraAcento(vistoriadorForm.getConCpf().trim());
		String nome = (vistoriadorForm.getConNome() ==null || vistoriadorForm.getConNome().isEmpty())  ? null : StringUtil.tiraAcento(vistoriadorForm.getConNome().trim());
		Integer codInstituicao = ((vistoriadorForm.getConInstituicao() == null || vistoriadorForm.getConInstituicao().isEmpty()) ? null : Integer.valueOf(vistoriadorForm.getConInstituicao()));
		
		Pagina pagina = new Pagina( Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina), Integer.parseInt(totalRegistros));

		try {
			vistoriadorForm.setIndGrupoSentinela(request.getSession().getAttribute("indGrupoSentinela").toString());
			vistoriadorForm.setAdm(GrupoSentinela.ADM_GERAL.getDescricao());
			
			//lista instituicao
			if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
				request.setAttribute("listaInstituicao", Util.htmlEncodeCollection(CadastroFacade.listarInstituicao()));	
			}else{
				//obtem a instituicao do usuario logado
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
				if (usuario == null){
					throw new ApplicationException("AVISO.97");
				}
				codInstituicao = usuario.getInstituicao().getCodInstituicao();
			}
			pagina = OperacaoFacade.listarVistoriador(pagina, Util.removerFormatacaoCPF(cpf), nome, codInstituicao);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.setAttribute("pagina", pagina);
			if(pagina.getTotalRegistros() == 0) {
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			return mapping.findForward("pgListVistoriador");
		}
		catch(ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListVistoriador"));
			throw appEx;
		}
		catch(Exception e) {
			setActionForward(mapping.findForward("pgListVistoriador"));
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa","vistoriador"}, e);
		}
	}
	
	/**
	 * Carrega pagina para alteração com os dados do Vistoriador selecionado.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditVistoriador(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgEditVistoriador"));
		VistoriadorForm vistoriadorForm = (VistoriadorForm) form;
		try {
			vistoriadorForm.setIndGrupoSentinela(request.getSession().getAttribute("indGrupoSentinela").toString());
			vistoriadorForm.setAdm(GrupoSentinela.ADM_GERAL.getDescricao());
			if (vistoriadorForm.getActionType().equals("alterar") && vistoriadorForm.getCodVistoriador()!= null) {
				Vistoriador vistoriador = OperacaoFacade.obterVistoriador(Integer.parseInt(vistoriadorForm.getCodVistoriador()));
				vistoriadorForm.setInstituicao(vistoriador.getInstituicao().getCodInstituicao().toString());
				vistoriadorForm.setInstituicaoDesc(vistoriador.getInstituicao().getSiglaDescricao());
				vistoriadorForm.setCpf(vistoriador.getCpf());
				vistoriadorForm.setNome(vistoriador.getNome());
			}else{//inclusão
				//lista instituicao
				if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
					request.setAttribute("listaInstituicao", Util.htmlEncodeCollection(CadastroFacade.listarInstituicao()));	
				}
				salvarVistoriador(mapping, vistoriadorForm, request, response);
			}
			return mapping.findForward("pgListVistoriador");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListVistoriador"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListVistoriador"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","vistoriador"}, e);
		}		
	}
	
	/**
	 * Realiza carga da página de listagem de vistoriadores.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgListVistoriador(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return mapping.findForward("pgListVistoriador");
    }
	
	public ActionForward carregarPgIncluirVistoriador(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		VistoriadorForm vistoriadorForm = (VistoriadorForm) form;
		vistoriadorForm.setActionType("incluir");
		vistoriadorForm.setIndGrupoSentinela(request.getSession().getAttribute("indGrupoSentinela").toString());
		vistoriadorForm.setAdm(GrupoSentinela.ADM_GERAL.getDescricao());
		if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
			request.setAttribute("listaInstituicao", CadastroFacade.listarComboInstituicao());	
		}
		return mapping.findForward("pgEditVistoriador");
    }
	
	public ActionForward carregarPgEditarVistoriador(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		VistoriadorForm vistoriadorForm = (VistoriadorForm) form;
		vistoriadorForm.setActionType("alterar");
		Integer codVistoriador = Integer.parseInt(request.getParameter("codVistoriador"));
		if (codVistoriador != null && codVistoriador > 0) {
			Vistoriador vistoriador = OperacaoFacade.obterVistoriador(codVistoriador);
			vistoriadorForm.setCpf(CPF.formataCPF(vistoriador.getCpf()));
			vistoriadorForm.setNome(vistoriador.getNome());
			vistoriadorForm.setInstituicao(vistoriador.getInstituicao().getCodInstituicao().toString());
			vistoriadorForm.setInstituicaoDesc(vistoriador.getInstituicao().getSiglaDescricao());
		}
		return mapping.findForward("pgEditVistoriador");
    }
	
	/**
	 * Realiza o encaminhamento necessário para salvar o vistoriador.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvarVistoriador(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		SentinelaInterface Si = (SentinelaInterface) SentinelaComunicacao.getInstance(request);
		VistoriadorForm vistoriadorForm = (VistoriadorForm) form;
		try	{	
			setActionForward(mapping.findForward("pgEditVistoriador"));
			String cpfNovoVistoriador = vistoriadorForm.getCpf();
			Date dataHoraAtual = new Date();
	
			// 	Aciona a validação do Form
			if(!validaForm(mapping,form,request)) {
				return getActionForward();
			}	

			Integer codInstituicao = null;
			if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
				codInstituicao = Integer.valueOf(vistoriadorForm.getInstituicao());
			}else{
				//	obtem a instituicao do usuario logado
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
				if (usuario == null){
					throw new ApplicationException("AVISO.97");
				}
				codInstituicao = usuario.getInstituicao().getCodInstituicao();
			}
			
			// 	Verifica se já existe vistoriador com mesmo cpf,o codVistoriador vai zerado para indicar que é uma inclusao
			if (OperacaoFacade.verificaVistoriadorDuplicado(Util.removerFormatacaoCPF(cpfNovoVistoriador), codInstituicao)){			
				throw new ApplicationException("AVISO.49",  new String[]{cpfNovoVistoriador}, ApplicationException.ICON_AVISO);
			}
				
			Vistoriador vistoriador = new Vistoriador();
			vistoriador.setCpf(Util.removerFormatacaoCPF(vistoriadorForm.getCpf()));
			vistoriador.setNome(vistoriadorForm.getNome());
			vistoriador.setTsInclusao(dataHoraAtual);
			vistoriador.setCpfResponsavel(Si.getCpf());
			Instituicao instituicao = new Instituicao();
			instituicao.setCodInstituicao(codInstituicao);
			vistoriador.setInstituicao(instituicao);
			OperacaoFacade.salvarVistoriador(vistoriador);
			vistoriadorForm.setConCpf("");
			vistoriadorForm.setConNome("");
			
			addMessage("SUCESSO.1000", request);	
			this.carregarListaVistoriador(form, request);
			
		} catch (ApplicationException appEx) {
			carregarPgIncluirVistoriador(mapping, vistoriadorForm, request, response);
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditVistoriador"));
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Vistoriador"}, e);
		}
		return mapping.findForward("pgListVistoriador");
	}	
	
	/**
	 * Realiza carga da página de listagem de vistoriadores, fazendo pesquisa e trazendo todos os registros da tabela (termo da pesquisa é vazio).
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void carregarListaVistoriador(ActionForm form, HttpServletRequest request) throws ApplicationException {
		VistoriadorForm vistoriadorForm = (VistoriadorForm) form;
		vistoriadorForm.setIndGrupoSentinela(request.getSession().getAttribute("indGrupoSentinela").toString());
		vistoriadorForm.setAdm(GrupoSentinela.ADM_GERAL.getDescricao());

		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina), Integer.parseInt(totalRegistros));
		
		String cpf = (vistoriadorForm.getConCpf() ==null || vistoriadorForm.getConCpf().isEmpty()) ? null : StringUtil.tiraAcento(vistoriadorForm.getConCpf().trim());
		String nome = (vistoriadorForm.getConNome() ==null || vistoriadorForm.getConNome().isEmpty())  ? null : StringUtil.tiraAcento(vistoriadorForm.getConNome().trim());
		Integer codInstituicao = ((vistoriadorForm.getConInstituicao() == null || vistoriadorForm.getConInstituicao().isEmpty()) ? null : Integer.valueOf(vistoriadorForm.getConInstituicao()));
		//lista instituicao
		if (request.getSession().getAttribute("indGrupoSentinela").toString().equals(GrupoSentinela.ADM_GERAL.getDescricao())){
			request.setAttribute("listaInstituicao", Util.htmlEncodeCollection(CadastroFacade.listarInstituicao()));	
		}else{
			//obtem a instituicao do usuario logado
			Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
			if (usuario == null){
				throw new ApplicationException("AVISO.97");
			}
			codInstituicao = usuario.getInstituicao().getCodInstituicao();
		}
		
		pagina = OperacaoFacade.listarVistoriador(pagina, cpf, nome, codInstituicao);
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
		if(pagina.getTotalRegistros() == 0) {
			throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
		}
    }
	
	/**
	 * Realiza o encaminhamento necessário para atualizar os dados do vistoriador.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterarVistoriador(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		VistoriadorForm vistoriadorForm = (VistoriadorForm) form;
		setActionForward(mapping.findForward("pgEditVistoriador"));
		Date dataHoraAtual = new Date();
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return getActionForward();
		}		
		try	{
			Vistoriador vistoriador = OperacaoFacade.obterVistoriador(Integer.valueOf(vistoriadorForm.getCodVistoriador()));
			vistoriador.setNome(vistoriadorForm.getNome());
			vistoriador.setTsAtualizacao(dataHoraAtual);
			OperacaoFacade.alterarVistoriador(vistoriador);
			vistoriadorForm.setConCpf("");
			vistoriadorForm.setConNome("");
			addMessage("SUCESSO.1002", request);
			this.carregarListaVistoriador(form, request);
			return mapping.findForward("pgListVistoriador");
			
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Vistoriador"}, e);
		}		
	}
	
	/**
	 * Realiza o encaminhamento para exclusão de vistoriador.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward excluirVistoriador(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		VistoriadorForm vistoriadorForm = (VistoriadorForm) form;
		try {
			if (vistoriadorForm.getCodVistoriador()!= null) {
				OperacaoFacade.excluirVistoriador(Integer.parseInt(vistoriadorForm.getCodVistoriador()));
				vistoriadorForm.setCpf("");
				vistoriadorForm.setNome("");
				addMessage("SUCESSO.1001", request);
			}
			this.carregarListaVistoriador(form, request);
			return pesquisarVistoriador(mapping, vistoriadorForm, request, response);
			
		} catch (ApplicationException appEx) {
			this.carregarListaVistoriador(form, request);
			setActionForward(mapping.findForward("pgListVistoriador"));
			throw appEx;
		} catch (Exception e) {
			this.carregarListaVistoriador(form, request);
			setActionForward(mapping.findForward("pgListVistoriador"));
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Vistoriador"}, e);
		}
	}	

}
