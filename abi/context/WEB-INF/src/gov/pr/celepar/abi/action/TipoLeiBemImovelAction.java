package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.TipoLeiBemImovelForm;
import gov.pr.celepar.abi.pojo.TipoLeiBemImovel;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 10/02/2010
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class TipoLeiBemImovelAction extends BaseDispatchAction {
	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de Tipo de Lei de Bem Imovel. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward pesquisarTipoLeiBemImovel (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {		
		TipoLeiBemImovelForm tipoLeiBemImovelForm = (TipoLeiBemImovelForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		String descricao = tipoLeiBemImovelForm.getDescricao() ==null ? "": StringUtil.tiraAcento(tipoLeiBemImovelForm.getDescricao().trim()) ;

		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA),  Integer.parseInt(indicePagina),  Integer.parseInt(totalRegistros));

		try {
			pagina = CadastroFacade.listarTipoLeiBemImovel(pagina, descricao);
			Util.htmlEncodeCollection(pagina.getRegistros());
			request.setAttribute("pagina", pagina);
			if (pagina.getTotalRegistros()==0){
				throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
			}
			return mapping.findForward("pgListTipoLeiBemImovel");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListTipoLeiBemImovel"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListTipoLeiBemImovel"));
			throw new ApplicationException("ERRO.200", new String[]{"de pesquisa","tipo de lei de bem imóvel"}, e);
		}
	}
	
	/**
	 * Carrega pagina para alteração com os dados do tipo de lei de bem imovel selecionado.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward carregarPgEditTipoLeiBemImovel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		

		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		TipoLeiBemImovelForm tipoLeiBemImovelForm = (TipoLeiBemImovelForm) form;
	
		try {
			if (tipoLeiBemImovelForm.getCodTipoLeiBemImovel() != null) {
				
				TipoLeiBemImovel tipoLeiBemImovel = CadastroFacade.obterTipoLeiBemImovel(Integer.parseInt(tipoLeiBemImovelForm.getCodTipoLeiBemImovel()));
				tipoLeiBemImovelForm.setDescricao(tipoLeiBemImovel.getDescricao());
				
			}
			return mapping.findForward("pgEditTipoLeiBemImovel");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListTipoLeiBemImovel"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListTipoLeiBemImovel"));
			throw new ApplicationException("ERRO.200", new String[]{"edição","tipo de lei de bem imóvel"}, e);
		}		
	}

	/**
	 * Realiza o encaminhamento necessário para salvar o tipo de lei de bem imovel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward salvarTipoLeiBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgEditTipoLeiBemImovel"));
		TipoLeiBemImovelForm tipoLeiBemImovelForm = (TipoLeiBemImovelForm) form;
		String descricaoNova = tipoLeiBemImovelForm.getDescricao().trim();
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return getActionForward();
		}		
		
		// Verifica se já existe descrição com mesmo nome
		if (CadastroFacade.verificarTipoLeiBemImovelDuplicado(descricaoNova,Integer.parseInt("0"))){			
			throw new ApplicationException("AVISO.37",  new String[]{descricaoNova}, ApplicationException.ICON_AVISO);
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			throw new ApplicationException("AVISO.200");
		}	
		
		try	{			
			TipoLeiBemImovel tipoLeiBemImovel = new TipoLeiBemImovel();
			tipoLeiBemImovel.setDescricao(tipoLeiBemImovelForm.getDescricao());

			CadastroFacade.salvarTipoLeiBemImovel(tipoLeiBemImovel);
			tipoLeiBemImovelForm.setDescricao("");
			addMessage("SUCESSO.25", request);	
			this.carregarListaTipoLeiBemImovel(form, request);
			
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Tipo de Lei de Bem Imóvel"}, e);
		}
		
		return mapping.findForward("pgListTipoLeiBemImovel");
	}	
	
	/**
	 * Realiza carga da página de listagem de tipo de lei de bem imovel, fazendo pesquisa e trazendo todos os registros da tabela (termo da pesquisa é vazio).
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void carregarListaTipoLeiBemImovel(ActionForm form, HttpServletRequest request ) throws ApplicationException {
			
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
			
		Pagina pagina = new Pagina( Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina),  Integer.parseInt(totalRegistros));

		pagina = CadastroFacade.listarTipoLeiBemImovel(pagina, "");
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
		if(pagina.getTotalRegistros() == 0) {
			throw new ApplicationException("AVISO.11", ApplicationException.ICON_AVISO);
		}	
	
    }
	
	/**
	 * Realiza o encaminhamento necessário para atualizar os dados do tipo de lei de bem imovel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */	
	public ActionForward alterarTipoLeiBemImovel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgEditTipoLeiBemImovel"));
		TipoLeiBemImovelForm tipoLeiBemImovelForm = (TipoLeiBemImovelForm) form;
		String descricaoNova = tipoLeiBemImovelForm.getDescricao().trim();
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditTipoLeiBemImovel");
		}		
		
		// Verifica se já existe descrição com mesmo nome
		if (CadastroFacade.verificarTipoLeiBemImovelDuplicado(descricaoNova,Integer.parseInt(tipoLeiBemImovelForm.getCodTipoLeiBemImovel()))){			
			throw new ApplicationException("AVISO.37",  new String[]{descricaoNova}, ApplicationException.ICON_AVISO);
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			throw new ApplicationException("AVISO.200");
		}	
		try	{			
			TipoLeiBemImovel tipoLeiBemImovel  = CadastroFacade.obterTipoLeiBemImovel(Integer.parseInt(tipoLeiBemImovelForm.getCodTipoLeiBemImovel()));

			tipoLeiBemImovel.setDescricao(tipoLeiBemImovelForm.getDescricao());
			CadastroFacade.alterarTipoLeiBemImovel(tipoLeiBemImovel);
			tipoLeiBemImovelForm.setDescricao("");
			addMessage("SUCESSO.25", request);
			this.carregarListaTipoLeiBemImovel(form, request);
			return mapping.findForward("pgListTipoLeiBemImovel");
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Tipo de Lei de Bem Imóvel"}, e);
		}		
	}
	
	/**
	 * Realiza o encaminhamento para exclusão de tipo de lei de bem imovel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward excluirTipoLeiBemImovel(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		setActionForward(mapping.findForward("pgListTipoLeiBemImovel"));
		TipoLeiBemImovelForm tipoLeiBemImovelForm = (TipoLeiBemImovelForm) form;
			
		try {
			if (tipoLeiBemImovelForm.getCodTipoLeiBemImovel() != null) {
				
				CadastroFacade.excluirTipoLeiBemImovel(Integer.parseInt(tipoLeiBemImovelForm.getCodTipoLeiBemImovel()));
				tipoLeiBemImovelForm.setDescricao("");
				addMessage("SUCESSO.37", request);
			}
		
			this.carregarListaTipoLeiBemImovel(form, request);
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			this.carregarListaTipoLeiBemImovel(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarListaTipoLeiBemImovel(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Tipo de Lei de Bem Imóvel"}, e);
		}
	}

}
