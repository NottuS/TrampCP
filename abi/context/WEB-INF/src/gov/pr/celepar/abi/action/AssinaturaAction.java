package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.AssinaturaForm;
import gov.pr.celepar.abi.pojo.Assinatura;
import gov.pr.celepar.abi.pojo.CargoAssinatura;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.CPF;
import gov.pr.celepar.framework.util.Data;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;
import gov.pr.celepar.sentinela.comunicacao.SentinelaParam;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Vanessak
 * @version 1.0
 * @since 29/06/2011
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class AssinaturaAction extends BaseDispatchAction {
	
	/**
	 * Carrega a página de consulta do UC. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward carregarPgListAssinatura (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		request.getSession().setAttribute("pagina", null);
		request.getSession().setAttribute("listaPesquisaInstituicao", CadastroFacade.listarComboInstituicao());	
		verificarGrupoUsuarioLogado((AssinaturaForm) form, request);
		return mapping.findForward("pgListAssinatura");
	}
	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de Assinatura. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward pesquisarAssinatura (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		try {
			setActionForward(mapping.findForward("pgListAssinatura"));
			carregarListaAssinatura(form, request);
			return getActionForward();
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa","Assinatura"}, e);
		}
	}
	
	/**
	 * Carrega pagina para alteração com os dados da Assinatura.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward carregarPgViewAssinatura(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		AssinaturaForm assinaturaForm = (AssinaturaForm) form;
		try {
			verificarGrupoUsuarioLogado(assinaturaForm, request);
			if (assinaturaForm.getCodAssinatura() != null) {
				this.popularForm(assinaturaForm, request);
			}
			return mapping.findForward("pgViewAssinatura");
		} catch (ApplicationException appEx) {
			carregarListaAssinatura(assinaturaForm, request);
			setActionForward(mapping.findForward("pgListAssinatura"));
			throw appEx;
		} catch (Exception e) {
			carregarListaAssinatura(assinaturaForm, request);
			setActionForward(mapping.findForward("pgListAssinatura"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","Assinatura"}, e);
		}		
	}

	/**
	 * Retorna para a página anterior.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward voltar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		AssinaturaForm assinaturaForm = (AssinaturaForm) form;
		if (assinaturaForm.getPesqExec().equalsIgnoreCase("S")) {
			setActionForward(this.pesquisarAssinatura(mapping, assinaturaForm, request, response));
		} else {
			setActionForward(this.carregarPgListAssinatura(mapping, assinaturaForm, request, response));
		}
		return getActionForward();
	}

	private void popularForm(AssinaturaForm assinaturaForm, HttpServletRequest request) throws NumberFormatException, ApplicationException {

		Assinatura assinatura = CadastroFacade.obterAssinatura(Integer.parseInt(assinaturaForm.getCodAssinatura()));
		assinaturaForm.setCodAssinatura(assinatura.getCodAssinatura().toString());
		assinaturaForm.setCpf(CPF.formataCPF(assinatura.getCpf()));
		assinaturaForm.setNome(assinatura.getNome());
		assinaturaForm.setCargo(assinatura.getCargoAssinatura().getCodCargoAssinatura().toString());
		assinaturaForm.setAdministracao(assinatura.getAdministracao().toString());
		assinaturaForm.setOrgao(assinatura.getOrgao().getCodOrgao().toString());
		if (assinatura.getInstituicao() != null && assinatura.getInstituicao().getCodInstituicao() > 0) {
			assinaturaForm.setInstituicaoDesc(assinatura.getInstituicao().getSiglaDescricao());
			assinaturaForm.setInstituicao(assinatura.getInstituicao().getCodInstituicao().toString());
		}
		if (assinatura.getIndResponsavelMaximo() != null) {
			if (assinatura.getIndResponsavelMaximo()) {
				assinaturaForm.setIndRespMaximo("1");
				assinaturaForm.setIndRespMaximoDesc("Sim");
			} else {
				assinaturaForm.setIndRespMaximo("2");
				assinaturaForm.setIndRespMaximoDesc("Não");
			}
		}
		assinaturaForm.setCargoDescricao(assinatura.getCargoAssinatura().getDescricao());
		assinaturaForm.setOrgaoDescricao(assinatura.getOrgao().getSiglaDescricao());
		assinaturaForm.setAdministracaoDescricao(Dominios.administracaoImovel.getAdministracaoImovelByIndex(assinatura.getAdministracao()).getLabel());

		this.carregarRealizadoPor(request, assinaturaForm, assinatura);

		verificarGrupoUsuarioLogado(assinaturaForm, request);
		request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(assinaturaForm.getAdministracao()), Integer.valueOf(assinaturaForm.getInstituicao()))));
		CargoAssinatura cargo = new CargoAssinatura();
		cargo.setInstituicao(new Instituicao());
		cargo.getInstituicao().setCodInstituicao(Integer.valueOf(assinaturaForm.getInstituicao()));
		request.getSession().setAttribute("cargos", Util.htmlEncodeCollection(CadastroFacade.listarCargoAssinaturaCombo(cargo)));
	}

	/**
	 * Carrega pagina para alteração com os dados da Assinatura.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward carregarPgEditAssinatura(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		AssinaturaForm assinaturaForm = (AssinaturaForm) form;
	
		try {
			verificarGrupoUsuarioLogado(assinaturaForm, request);
			if (assinaturaForm.getCodAssinatura() != null) {
				this.popularForm(assinaturaForm, request);
			} else {
				request.getSession().setAttribute("listaInstituicao", CadastroFacade.listarComboInstituicao());	
			}
			return mapping.findForward("pgEditAssinatura");
		} catch (ApplicationException appEx) {
			carregarListaAssinatura(assinaturaForm, request);
			setActionForward(mapping.findForward("pgListAssinatura"));
			throw appEx;
		} catch (Exception e) {
			carregarListaAssinatura(assinaturaForm, request);
			setActionForward(mapping.findForward("pgListAssinatura"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","Assinatura"}, e);
		}		
	}

	/**
	 * Realiza o encaminhamento necessário para salvar a Assinatura.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvarAssinatura(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgEditAssinatura"));
		AssinaturaForm assinaturaForm = (AssinaturaForm) form;
		
		try	{			
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	

			// Aciona a validação do Form
			validaDadosForm(assinaturaForm, request);
			verificarGrupoUsuarioLogado(assinaturaForm, request);
			
			Assinatura assinatura = new Assinatura();
			this.populaPojo(assinatura,assinaturaForm, request);
			this.populaPojoRegistro(assinatura,"I", request);

			// Verifica se já existe com mesmo CPF, Órgáo e Cargo
			Collection<Assinatura> listDuplicidade = CadastroFacade.verificarAssinaturaDuplicada(assinatura, Integer.valueOf(assinaturaForm.getInstituicao())); 
			
			if (listDuplicidade.size() > 0) {
				String assinaturaStr = assinatura.getCpf().concat(" - ").concat(assinatura.getCargoAssinatura().getDescricao()).
					concat(" - ").concat(assinatura.getOrgao().getSigla().concat(" - ").concat(assinatura.getOrgao().getDescricao()));
				saveToken(request);
				throw new ApplicationException("AVISO.73",  new String[]{assinaturaStr}, ApplicationException.ICON_AVISO);
			}
			
			CadastroFacade.salvarAssinatura(assinatura);
			assinaturaForm.limparCampos();
			addMessage("SUCESSO.25", request);	
			this.carregarListaAssinatura(form, request);
			
		} catch (ApplicationException appEx) {
			carregarCamposRequest(form, request);
			throw appEx;
		} catch (Exception e) {
			carregarCamposRequest(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Assinatura"}, e);
		}
		
		return mapping.findForward("pgListAssinatura");
	}	
	
	private void carregarCamposRequest (ActionForm form, HttpServletRequest request) throws NumberFormatException, ApplicationException {
		AssinaturaForm localForm = (AssinaturaForm)form;
		verificarGrupoUsuarioLogado(localForm, request);
		request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(localForm.getAdministracao()), Integer.valueOf(localForm.getInstituicao()))));

		CargoAssinatura cargo = new CargoAssinatura();
		cargo.setInstituicao(new Instituicao());
		cargo.getInstituicao().setCodInstituicao(Integer.valueOf(localForm.getInstituicao()));
		request.getSession().setAttribute("cargos", Util.htmlEncodeCollection(CadastroFacade.listarCargoAssinaturaCombo(cargo)));
	}

	/**
	 * Realiza o encaminhamento necessário para atualizar os dados da Assinatura.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterarAssinatura(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgEditAssinatura"));
		AssinaturaForm assinaturaForm = (AssinaturaForm) form;
		Assinatura assinatura = new Assinatura (); 
		try	{			
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	

			// Aciona a validação do Form
			validaDadosForm(assinaturaForm, request);
			verificarGrupoUsuarioLogado(assinaturaForm, request);
			
			assinatura = CadastroFacade.obterAssinatura(Integer.valueOf(assinaturaForm.getCodAssinatura()));
			Assinatura assinaturaAux = assinatura;
			this.populaPojo(assinatura,assinaturaForm, request);
			boolean mesmoObjeto = false;
			
			// Verifica se já existe com mesmo CPF, Órgáo e Cargo
			Collection<Assinatura> listDuplicidade = CadastroFacade.verificarAssinaturaDuplicada(assinatura, Integer.valueOf(assinaturaForm.getInstituicao())); 

			if (listDuplicidade.size() > 0) {
				saveToken(request);
				Assinatura assinaturaDB;
				for (Iterator<Assinatura> iterator  = listDuplicidade.iterator(); iterator.hasNext();) {
					assinaturaDB = (Assinatura) iterator .next();
					if (!assinaturaDB.getCodAssinatura().equals(assinaturaAux.getCodAssinatura())) {
						mesmoObjeto = true;
					}
				}
				if (mesmoObjeto) {
					String assinaturaStr = assinatura.getCpf().concat(" - ").concat(assinatura.getCargoAssinatura().getDescricao()).
						concat(" - ").concat(assinatura.getOrgao().getSigla().concat(" - ").concat(assinatura.getOrgao().getDescricao()));
					throw new ApplicationException("AVISO.73",  new String[]{assinaturaStr}, ApplicationException.ICON_AVISO);
				}
			}
			
			assinatura = CadastroFacade.obterAssinatura(Integer.valueOf(assinaturaForm.getCodAssinatura()));
			if ((assinatura.getListaAssinaturaDoacao() != null && assinatura.getListaAssinaturaDoacao().size() > 0) ||
				(assinatura.getListaAssinaturaTransferencia() != null && assinatura.getListaAssinaturaTransferencia().size() > 0)) {
				//inativa a assinatura obtida 
				this.populaPojoRegistro(assinatura,"E", request);
				CadastroFacade.alterarInativarAssinatura(assinatura);
				
				//cria uma nova assinatura
				assinatura.setCodAssinatura(null);
				assinatura.setTsAtualizacao(null);
				assinatura.setCpfResponsavelAlteracao(null);
				assinatura.setCpfResponsavelExclusao(null);
				assinatura.setTsExclusao(null);
				this.populaPojo(assinatura,assinaturaForm, request);
				this.populaPojoRegistro(assinatura,"I", request);
				CadastroFacade.salvarAssinatura(assinatura);
			} else {
				//atualiza a assinatura
				this.populaPojo(assinatura,assinaturaForm, request);
				this.populaPojoRegistro(assinatura,"A", request);
				CadastroFacade.alterarInativarAssinatura(assinatura);
			}
			assinaturaForm.limparCampos();
			addMessage("SUCESSO.25", request);	
			this.carregarListaAssinatura(form, request);
			setActionForward(mapping.findForward("pgListAssinatura"));
			
		} catch (ApplicationException appEx) {
			this.carregarRealizadoPor(request, assinaturaForm, assinatura);
			carregarCamposRequest(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarRealizadoPor(request, assinaturaForm, assinatura);
			carregarCamposRequest(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Assinatura"}, e);
		}
		
		return getActionForward();
	}
	
	/**
	 * Realiza o encaminhamento para exclusão da Assinatura.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward excluirAssinatura(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgEditAssinatura"));
		AssinaturaForm assinaturaForm = (AssinaturaForm) form;
		Assinatura assinatura = new Assinatura();
		try {
			if (assinaturaForm.getCodAssinatura() != null) {
				assinatura = CadastroFacade.obterAssinatura(Integer.valueOf(assinaturaForm.getCodAssinatura()));
				this.populaPojoRegistro(assinatura, "E", request);
				CadastroFacade.excluirAssinatura(assinatura);
				assinaturaForm.limparCampos();
				addMessage("SUCESSO.25", request);
				this.carregarListaAssinatura(form, request);
			}
		} catch (ApplicationException appEx) {
			this.carregarRealizadoPor(request, assinaturaForm, assinatura);
			carregarCamposRequest(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarRealizadoPor(request, assinaturaForm, assinatura);
			carregarCamposRequest(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Assinatura"}, e);
		}
		return mapping.findForward("pgListAssinatura");
	}

	/**
	 * Realiza o encaminhamento para inativação da Assinatura.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward inativarAssinatura(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgEditAssinatura"));
		AssinaturaForm assinaturaForm = (AssinaturaForm) form;
		Assinatura assinatura = new Assinatura();
		try {
			if (assinaturaForm.getCodAssinatura() != null) {
				assinatura = CadastroFacade.obterAssinatura(Integer.valueOf(assinaturaForm.getCodAssinatura()));
				this.populaPojoRegistro(assinatura, "E", request);
				CadastroFacade.alterarInativarAssinatura(assinatura);
				assinaturaForm.limparCampos();
				addMessage("SUCESSO.25", request);
				this.carregarListaAssinatura(form, request);
			}
		} catch (ApplicationException appEx) {
			this.carregarRealizadoPor(request, assinaturaForm, assinatura);
			carregarCamposRequest(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarRealizadoPor(request, assinaturaForm, assinatura);
			carregarCamposRequest(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao inativar Assinatura"}, e);
		}
		return mapping.findForward("pgListAssinatura");
	}

	private void validaDadosForm(ActionForm form, HttpServletRequest request) throws ApplicationException, Exception {
		AssinaturaForm assinaturaForm = (AssinaturaForm) form;
		StringBuffer str = new StringBuffer();
		
		if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo())) {
			if (assinaturaForm.getInstituicao() == null || assinaturaForm.getInstituicao().trim().length() == 0) {
				str.append("Instituição");
			}
		}

		if (assinaturaForm.getNome() == null || assinaturaForm.getNome().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Nome");
		}
		if (assinaturaForm.getIndRespMaximo() == null || assinaturaForm.getIndRespMaximo().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Responsável Máximo");
		}
		if (assinaturaForm.getAdministracao() == null || assinaturaForm.getAdministracao().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Administração");
		}
		if (assinaturaForm.getOrgao() == null || assinaturaForm.getOrgao().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Órgão");
		}
		if (assinaturaForm.getCargo() == null || assinaturaForm.getCargo().trim().length() == 0) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append("Cargo");
		}

		if (str.length() > 0) {
			str.append(".");
			throw new ApplicationException("AVISO.72", new String[]{str.toString()}, ApplicationException.ICON_AVISO);
		}

		if (assinaturaForm.getNome().length() > 0 && assinaturaForm.getNome().length() < 5) {
			throw new ApplicationException("AVISO.71", new String[]{"Nome"}, ApplicationException.ICON_AVISO);
		}

		//valida CPF
		if (assinaturaForm.getCpf() != null && assinaturaForm.getCpf().trim().length() > 0) {
			boolean result = CPF.validarCPF((Util.removerFormatacaoCPF(assinaturaForm.getCpf())));
			if (!result) {
				throw new ApplicationException("errors.cpf", ApplicationException.ICON_AVISO);
			}
		}

	}

	/**
	 * Realiza carga da página de listagem de Assinatura, fazendo pesquisa e trazendo todos os registros da tabela (termo da pesquisa é vazio).
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void carregarListaAssinatura(ActionForm form, HttpServletRequest request ) throws ApplicationException {

		AssinaturaForm assinaturaForm = (AssinaturaForm) form;
		assinaturaForm.setPesqExec("S");
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String nome = assinaturaForm.getConNome() ==null ? "": StringUtil.tiraAcento(assinaturaForm.getConNome().trim()) ;
		String cpf = assinaturaForm.getConCpf() ==null ? "": StringUtil.tiraAcento(assinaturaForm.getConCpf().trim()) ;
		
		verificarGrupoUsuarioLogado(assinaturaForm, request);
		String codInstituicao = assinaturaForm.getConInstituicao() == null ? null: assinaturaForm.getConInstituicao().trim();
		
		Assinatura assinatura = new Assinatura();
		assinatura.setCpf(Util.removerFormatacaoCPF(cpf));
		assinatura.setNome(nome);
		
		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA),  Integer.parseInt(indicePagina),  Integer.parseInt(totalRegistros));

		try {
			Instituicao instituicao = CadastroFacade.obterInstituicao(Integer.valueOf(codInstituicao));
			assinatura.setInstituicao(instituicao);
			pagina = CadastroFacade.listarAssinatura(pagina, assinatura);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.getSession().setAttribute("pagina", pagina);
			if (pagina.getTotalRegistros()==0){
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa","Assinatura"}, e);
		}
    }
	
	/**
	 * Carrega o combobox de orgãos de acordo com a Administração selecionada
	 * @param form
	 */
	public ActionForward carregarComboOrgao (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		log.info("Método carregarComboOrgao processando...");
		try {
			
			setActionForward(mapping.findForward("comboOrgaoAssinatura"));
			
			AssinaturaForm localForm = (AssinaturaForm) form;
			verificarGrupoUsuarioLogado(localForm, request);
			
			String param = (StringUtils.isNotBlank(localForm.getAdministracao()))?localForm.getAdministracao():null;
			String instituicao = (StringUtils.isNotBlank(localForm.getInstituicao()))?localForm.getInstituicao():null;
			
			if (instituicao != null){
				CargoAssinatura cargo = new CargoAssinatura();
				cargo.setInstituicao(new Instituicao());
				cargo.getInstituicao().setCodInstituicao(Integer.valueOf(instituicao));
				request.getSession().setAttribute("cargos", Util.htmlEncodeCollection(CadastroFacade.listarCargoAssinaturaCombo(cargo)));
			} else {
				request.getSession().setAttribute("cargos", null);
			}

			if (param != null){
				if (instituicao != null) {
					request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(Integer.valueOf(param), Integer.valueOf(instituicao))));
				} else {
					request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(Integer.valueOf(param), SentinelaComunicacao.getInstance(request).getCodUsuario())));
				}
			} else {
				request.getSession().setAttribute("orgaos", null);
			}
			
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			log.error(appEx);	
			throw appEx;
		} catch (Exception e) {
			log.error(e);	
			throw new ApplicationException("mensagem.erro.9001", new String[]{"AssinaturaAction.carregarComboOrgao"}, e, ApplicationException.ICON_ERRO);
		}
	}

	private void carregarRealizadoPor(HttpServletRequest request, AssinaturaForm assinaturaForm, Assinatura assinatura) {
		SentinelaParam sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByCPF(assinatura.getCpfResponsavelInclusao());
		String aux = "";
		if (sentinelaParam != null) {
			aux = "Inclusão realizada por ";
			aux = aux.concat(sentinelaParam.getNome().trim());
			aux = aux.concat(" em ");
			aux = aux.concat(Data.formataData(assinatura.getTsInclusao(),"dd/MM/yyyy HH:mm"));
			assinaturaForm.setIncluidoPor(aux);
		}
		if (assinatura.getCpfResponsavelAlteracao() != null && assinatura.getCpfResponsavelAlteracao().length() > 0){
			sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByCPF(assinatura.getCpfResponsavelAlteracao());
			if (sentinelaParam != null) {
				aux = "Alteração realizada por ";
				aux = aux.concat(sentinelaParam.getNome().trim());
				aux = aux.concat(" em ");
				aux = aux.concat(Data.formataData(assinatura.getTsAtualizacao(),"dd/MM/yyyy HH:mm"));
				assinaturaForm.setAlteradoPor(aux);
			}
		}
		if (assinatura.getCpfResponsavelExclusao() != null && assinatura.getCpfResponsavelExclusao().length() > 0){
			sentinelaParam = SentinelaComunicacao.getInstance(request).getUsuarioByCPF(assinatura.getCpfResponsavelExclusao());
			if (sentinelaParam != null) {
				aux = "Exclusão realizada por ";
				aux = aux.concat(sentinelaParam.getNome().trim());
				aux = aux.concat(" em ");
				aux = aux.concat(Data.formataData(assinatura.getTsExclusao(),"dd/MM/yyyy HH:mm"));
				assinaturaForm.setExcluidoPor(aux);
			}
		}
	}

	private void populaPojo(Assinatura assinatura, AssinaturaForm assinaturaForm, HttpServletRequest request) throws NumberFormatException, ApplicationException {
		assinatura.setNome(assinaturaForm.getNome());
		assinatura.setCpf(Util.removerFormatacaoCPF(assinaturaForm.getCpf()));
		assinatura.setAdministracao(Integer.valueOf(assinaturaForm.getAdministracao()));
		assinatura.setOrgao(CadastroFacade.obterOrgao(Integer.valueOf(assinaturaForm.getOrgao())));
		assinatura.setCargoAssinatura(CadastroFacade.obterCargoAssinatura(Integer.valueOf(assinaturaForm.getCargo())));

		verificarGrupoUsuarioLogado(assinaturaForm, request);
		assinatura.setInstituicao(CadastroFacade.obterInstituicao(Integer.valueOf(assinaturaForm.getInstituicao())));

		if (assinaturaForm.getIndRespMaximo().equals("1")) {
			assinatura.setIndResponsavelMaximo(Boolean.TRUE);
		} else {
			assinatura.setIndResponsavelMaximo(Boolean.FALSE);
		}
		
	}
	
	private void populaPojoRegistro(Assinatura assinatura, String acao, HttpServletRequest request) {
		Date dataAtual = new Date();
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		if ("I".equals(acao)) {
			assinatura.setTsInclusao(dataAtual);
			assinatura.setCpfResponsavelInclusao(sentinelaInterface.getCpf());
			assinatura.setIndAtivo(Boolean.TRUE);
		} else if ("A".equals(acao)) {
			assinatura.setTsAtualizacao(dataAtual);
			assinatura.setCpfResponsavelAlteracao(sentinelaInterface.getCpf());
			assinatura.setIndAtivo(Boolean.TRUE);
		} else if ("E".equals(acao)) {
			assinatura.setTsExclusao(dataAtual);
			assinatura.setCpfResponsavelExclusao(sentinelaInterface.getCpf());
			assinatura.setIndAtivo(Boolean.FALSE);
		}
	}

	private void verificarGrupoUsuarioLogado(AssinaturaForm localForm, HttpServletRequest request) throws ApplicationException {
		boolean result = CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo());
		localForm.setIsGpAdmGeralUsuarioLogado("N");
		if (result) {
			localForm.setIsGpAdmGeralUsuarioLogado("S");
		} else {
			String codInstituicao = CadastroFacade.obterInstituicaoUsuario(SentinelaComunicacao.getInstance(request).getCodUsuario()).getCodInstituicao().toString();
			localForm.setConInstituicao(codInstituicao);
			localForm.setInstituicao(codInstituicao);
		}
	}

}
