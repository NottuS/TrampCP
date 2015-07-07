package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.TabelionatoPesquisaDTO;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.TabelionatoForm;
import gov.pr.celepar.abi.pojo.Tabelionato;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author claudiofain
 * @version 1.0
 * @since 06/01/2010
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class TabelionatoAction extends BaseDispatchAction {

	private void transfereFormPesqDTO(TabelionatoForm tabelionatoForm, TabelionatoPesquisaDTO tabelPesqDTO) {
		tabelPesqDTO.setDescricao(tabelionatoForm.getConDescricao().trim());
		tabelPesqDTO.setUf(tabelionatoForm.getUf().trim());
		tabelPesqDTO.setMunicipio(tabelionatoForm.getMunicipio().trim());
	}

	private void transfereFormPojo(TabelionatoForm tabelionatoForm, Tabelionato tabelionato) {
		tabelionato.setDescricao(tabelionatoForm.getDescricao().trim());
		tabelionato.setCep(tabelionatoForm.getCep().trim());
		tabelionato.setUf(tabelionatoForm.getUf().trim());
		tabelionato.setMunicipio(tabelionatoForm.getMunicipio().trim());
		tabelionato.setLogradouro(tabelionatoForm.getLogradouro().trim());
		tabelionato.setNumero(tabelionatoForm.getNumero().trim());
		tabelionato.setBairro(tabelionatoForm.getBairro().trim());
		tabelionato.setComplemento(tabelionatoForm.getComplemento().trim());
		tabelionato.setNomeContato(tabelionatoForm.getNomeContato().trim());
		tabelionato.setTelDdd(!StringUtils.isBlank(tabelionatoForm.getTelDdd()) ? Integer.valueOf(tabelionatoForm.getTelDdd()) : null);
		tabelionato.setCodMunicipio(!StringUtils.isBlank(tabelionatoForm.getCodMunicipio()) ? Integer.valueOf(tabelionatoForm.getCodMunicipio()) : null);
		tabelionato.setTelNumero(tabelionatoForm.getTelNumero().trim());
	}

	private void transferePojoForm(Tabelionato tabelionato, TabelionatoForm tabelionatoForm) {
		tabelionatoForm.setDescricao(tabelionato.getDescricao());
		tabelionatoForm.setCep(tabelionato.getCep());
		tabelionatoForm.setUf(tabelionato.getUf());
		tabelionatoForm.setMunicipio(tabelionato.getMunicipio());
		tabelionatoForm.setLogradouro(tabelionato.getLogradouro());
		tabelionatoForm.setNumero(tabelionato.getNumero());
		tabelionatoForm.setBairro(tabelionato.getBairro());
		tabelionatoForm.setComplemento(tabelionato.getComplemento());
		tabelionatoForm.setNomeContato(tabelionato.getNomeContato());
		tabelionatoForm.setTelDdd(tabelionato.getTelDdd() != null ? tabelionato.getTelDdd().toString() : null);
		tabelionatoForm.setTelNumero(tabelionato.getTelNumero() != null ? tabelionato.getTelNumero().trim() : null);
		tabelionatoForm.setCodMunicipio(tabelionato.getCodMunicipio() != null ? tabelionato.getCodMunicipio().toString() : null);
	}
	/**
	 * Realiza carga da página de listagem de tabelionato.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgListTabelionato(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return mapping.findForward("pgListTabelionato");
    }

	/**
	 * Realiza a carga da página de visualização de tabelionato.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgViewTabelionato(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		TabelionatoForm tabelionatoForm = (TabelionatoForm)form;
		
		try {
			if(tabelionatoForm.getCodTabelionato() != null) {
				Tabelionato tabelionato = CadastroFacade.obterTabelionato(Integer.valueOf(tabelionatoForm.getCodTabelionato()));
				this.transferePojoForm(tabelionato, tabelionatoForm);
			}
			return mapping.findForward("pgViewTabelionato");
		}
		catch(ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListTabelionato"));
			throw appEx;
		}
		catch(Exception e) {
			setActionForward(mapping.findForward("pgListTabelionato"));
			throw new ApplicationException("ERRO.200", new String[]{"visualização", "tabelionato"}, e);
		}
	}
	
	/**
	 * Carrega página para alteração com os dados do tabelionato selecionado.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditTabelionato(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		TabelionatoForm tabelionatoForm = (TabelionatoForm)form;
	
		try {
			if(tabelionatoForm.getCodTabelionato() != null) {
				Tabelionato tabelionato = CadastroFacade.obterTabelionato(Integer.valueOf(tabelionatoForm.getCodTabelionato()));
				this.transferePojoForm(tabelionato, tabelionatoForm);
			}
			else {
				tabelionatoForm.setUf("PR");
			}
			return mapping.findForward("pgEditTabelionato");
		}
		catch(ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListTabelionato"));
			throw appEx;
		}
		catch(Exception e) {
			setActionForward(mapping.findForward("pgListTabelionato"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","denominação de imóvel"}, e);
		}		
	}	
	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de tabelionatos. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward pesquisarTabelionato(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		TabelionatoForm tabelionatoForm = (TabelionatoForm)form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");

		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina), Integer.valueOf(totalRegistros));

		TabelionatoPesquisaDTO tabelPesqDTO = new TabelionatoPesquisaDTO();

		try {

			
			
			if(!Util.strEmBranco(tabelionatoForm.getConDescricao())) {
				tabelPesqDTO.setDescricao("%" + tabelionatoForm.getConDescricao() + "%");
			}
			if(!"0".equals(tabelionatoForm.getConUF())) {
				tabelPesqDTO.setUf(tabelionatoForm.getConUF());
				tabelionatoForm.setUf(tabelionatoForm.getConUF());
			}
			if(!Util.strEmBranco(tabelionatoForm.getConMunicipio()) && !tabelionatoForm.getConMunicipio().equals("0")) {
				tabelPesqDTO.setMunicipio(tabelionatoForm.getConMunicipio());
				tabelionatoForm.setCodMunicipio(tabelionatoForm.getConMunicipio());
			}
			
			pagina = CadastroFacade.listarTabelionato(pagina, tabelPesqDTO);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.setAttribute("pagina", pagina);
			if(pagina.getTotalRegistros() == 0) {
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			return mapping.findForward("pgListTabelionato");
		}
		catch(ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListTabelionato"));
			throw appEx;
		}
		catch(Exception e) {
			setActionForward(mapping.findForward("pgListTabelionato"));
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa", "tabelionato"}, e);
		}
	}
	
	/**
	 * Realiza o encaminhamento necessário para salvar o tabelionato.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvarTabelionato(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		TabelionatoForm tabelionatoForm = (TabelionatoForm)form;
		TabelionatoPesquisaDTO tabelPesqDTO = new TabelionatoPesquisaDTO();
		this.transfereFormPesqDTO(tabelionatoForm, tabelPesqDTO);
		
		// Aciona a validação do Form
		if(!validaForm(mapping, form, request)) {
			return mapping.findForward("pgEditTabelionato");
		}
		
		// Verifica se já existe mesma descrição com mesmo município
		if(CadastroFacade.verificaTabelionatoDuplicado(0, tabelPesqDTO)) {
			setActionForward(mapping.findForward("pgEditTabelionato"));
			throw new ApplicationException("AVISO.29", new String[]{tabelionatoForm.getDescricao()}, ApplicationException.ICON_AVISO);
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		}
		else {
			setActionForward(mapping.findForward("pgEditTabelionato"));
			throw new ApplicationException("AVISO.200");
		}	
		
		try	{			
			Tabelionato tabelionato = new Tabelionato();
			this.transfereFormPojo(tabelionatoForm, tabelionato);
			CadastroFacade.salvarTabelionato(tabelionato);

			tabelionatoForm.setBairro("");
			tabelionatoForm.setCep("");
			tabelionatoForm.setUf("PR");
			tabelionatoForm.setMunicipio("");
			tabelionatoForm.setComplemento("");
			tabelionatoForm.setDescricao("");
			tabelionatoForm.setLogradouro("");
			tabelionatoForm.setNumero("");
			tabelionatoForm.setNomeContato("");
			tabelionatoForm.setTelDdd("");
			tabelionatoForm.setTelNumero("");

			
			addMessage("SUCESSO.25", request);
		}
		catch(ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditTabelionato"));
			throw appEx;
		}
		catch(Exception e) {
			setActionForward(mapping.findForward("pgEditTabelionato"));
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Tabelionato"}, e);
		}
		return pesquisarTabelionato(mapping, form, request, response);
	}	
	
	/**
	 * Realiza o encaminhamento necessário para atualizar os dados do tabelionato.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterarTabelionato(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		TabelionatoForm tabelionatoForm = (TabelionatoForm)form;
		TabelionatoPesquisaDTO tabelPesqDTO = new TabelionatoPesquisaDTO();
		this.transfereFormPesqDTO(tabelionatoForm, tabelPesqDTO);
		
		// Aciona a validação do Form
		if(!validaForm(mapping, form, request)) {
			return mapping.findForward("pgEditTabelionato");
		}
		
		// Verifica se já existe mesma descrição com mesmo município
		if(CadastroFacade.verificaTabelionatoDuplicado(Integer.parseInt(tabelionatoForm.getCodTabelionato()), tabelPesqDTO)) {
			setActionForward(mapping.findForward("pgEditTabelionato"));
			throw new ApplicationException("AVISO.29", new String[]{tabelionatoForm.getDescricao()}, ApplicationException.ICON_AVISO);
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		}
		else {
			setActionForward(mapping.findForward("pgEditTabelionato"));
			throw new ApplicationException("AVISO.200");
		}
		
		try	{
			Tabelionato tabelionato = CadastroFacade.obterTabelionato(Integer.valueOf(tabelionatoForm.getCodTabelionato()));
			this.transfereFormPojo(tabelionatoForm, tabelionato);
			CadastroFacade.alterarTabelionato(tabelionato);
			tabelionatoForm.setBairro("");
			tabelionatoForm.setCep("");
			tabelionatoForm.setUf("PR");
			tabelionatoForm.setMunicipio("");
			tabelionatoForm.setCodMunicipio("0");
			tabelionatoForm.setComplemento("");
			tabelionatoForm.setDescricao("");
			tabelionatoForm.setLogradouro("");
			tabelionatoForm.setNumero("");
			tabelionatoForm.setNomeContato("");
			tabelionatoForm.setTelDdd("");
			tabelionatoForm.setTelNumero("");			

			addMessage("SUCESSO.25", request);
		}
		catch(ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditTabelionato"));
			throw appEx;
		}
		catch(Exception e) {
			setActionForward(mapping.findForward("pgEditTabelionato"));
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Tabelionato"}, e);
		}
		return pesquisarTabelionato(mapping, form, request, response);
	}
	
	/**
	 * Realiza o encaminhamento para exclusão de tabelionato.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward excluirTabelionato(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		TabelionatoForm tabelionatoForm = (TabelionatoForm)form;

		try {
			if(tabelionatoForm.getCodTabelionato() != null) {
				CadastroFacade.excluirTabelionato(Integer.parseInt(tabelionatoForm.getCodTabelionato()));
			}

			addMessage("SUCESSO.51", request);
		}
		catch(ApplicationException appEx) {
			setActionForward(mapping.findForward("pgViewTabelionato"));
			throw appEx;
		}
		catch(Exception e) {
			setActionForward(mapping.findForward("pgViewTabelionato"));
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Tabelionato"}, e);
		}
		return pesquisarTabelionato(mapping, form, request, response);
	}
}