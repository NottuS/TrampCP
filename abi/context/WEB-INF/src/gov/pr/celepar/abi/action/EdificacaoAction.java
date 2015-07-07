package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.dto.ItemComboDTO;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.EdificacaoForm;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Edificacao;
import gov.pr.celepar.abi.pojo.Lote;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.Valores;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Luciana R. Bélico
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class EdificacaoAction extends BaseDispatchAction {
	
	private static Logger log4j = Logger.getLogger(EdificacaoAction.class);
	private Collection<ItemComboDTO> lotes;
	
	private void verificarTodasOperacoes( HttpServletRequest request ){
		SentinelaInterface com = SentinelaComunicacao.getInstance(request);
		String operacoes = com.getOperacoes();
		log4j.info("Operações do usuário "+com.getLogin()+ ": " + operacoes);
		verificarOperacao('A', operacoes, request); //alterar bem Imovel
	}
	
	private void verificarOperacao(char operacao, String operacoes, HttpServletRequest request){
		try {
			request.setAttribute(String.valueOf(operacao), Boolean.valueOf(operacoes.indexOf(operacao)!= -1 ) );
		}catch (Exception e) {
			request.setAttribute(String.valueOf(operacao), "false");
			log4j.info("Não autorizado para a operação: "+ operacao, e);			
		}
	}
	
	/**
	 * Realiza carga da aba Edificacao.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgEditEdificacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException , Exception{
		verificarTodasOperacoes(request);
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		
		try {
			setActionForward(mapping.findForward("pgEditEdificacao"));
			Set<Lote> lotesEdificacao = null;
			
			if (request.getParameter("actionType").equals("incluir")) {
				EdificacaoForm edificacaoForm = (EdificacaoForm) form;
				lotesEdificacao = new HashSet <Lote>();
				request.getSession().setAttribute("lotesEdificacao", lotesEdificacao);
				lotes = Util.htmlEncodeCollection(CadastroFacade.listarLotesCombo(Integer.parseInt(request.getParameter("codBemImovel"))));
				this.carregarListaEdificacao(edificacaoForm, request);
				this.limparFormulario(edificacaoForm);
			}
			
			else if (request.getParameter("actionType").equals("alterar"))
			{
				EdificacaoForm edificacaoForm = (EdificacaoForm) form;
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Edificacao edificacao = CadastroFacade.obterEdificacao(Integer.parseInt(request.getParameter("codEdificacao")));
				lotes = Util.htmlEncodeCollection(CadastroFacade.listarLotesCombo(Integer.parseInt(request.getParameter("codBemImovel"))));
				lotesEdificacao=edificacao.getLotes();
				if (!lotesEdificacao.isEmpty()){
					for (Lote item: lotesEdificacao)
					{
						ItemComboDTO obj = new ItemComboDTO();
						obj.setCodigo(String.valueOf(item.getCodLote()));
						lotes.remove(obj);
					}
				}
				request.getSession().setAttribute("lotesEdificacao", lotesEdificacao);
				this.carregarListaEdificacao(edificacaoForm, request);
				edificacaoForm.setEspecificacao(edificacao.getEspecificacao());
				edificacaoForm.setActionType("alterar");
				edificacaoForm.setAreaConstruida(Valores.formatarParaDecimal(edificacao.getAreaConstruida(), 2));
				edificacaoForm.setAreaUtilizada(Valores.formatarParaDecimal(edificacao.getAreaUtilizada(), 2));
				edificacaoForm.setDataAverbacao(edificacao.getDataAverbacao() != null ? sdf.format(edificacao.getDataAverbacao()) : null);
				edificacaoForm.setLogradouro(edificacao.getLogradouro());
				edificacaoForm.setTipoConstrucao(edificacao.getTipoConstrucao() != null ? edificacao.getTipoConstrucao().getCodTipoConstrucao().toString() : null);
				edificacaoForm.setTipoEdificacao(edificacao.getTipoEdificacao() != null ? edificacao.getTipoEdificacao().getCodTipoEdificacao().toString() : null);
				edificacaoForm.setCodEdificacao(edificacao.getCodEdificacao().toString());
			}
			
			
		} catch (ApplicationException appEx) {
			this.carregarListaEdificacao(form, request);
			throw appEx;

		} catch (Exception e) {
			this.carregarListaEdificacao(form, request);
			throw new ApplicationException("ERRO.200", new String[]{"edição","edificação"}, e);
		}	

		
		return getActionForward();
    }
	
	
		
	public ActionForward incluirLote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		
		try {
			setActionForward(mapping.findForward("pgEditEdificacao"));
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	
			
			EdificacaoForm edificacaoForm = (EdificacaoForm) form;
						
			Lote loteAux = CadastroFacade.obterLoteComRelacionamentos(Integer.parseInt(edificacaoForm.getLote()));
			
			Set<Lote> lotesEdificacao = (Set<Lote>) request.getSession().getAttribute("lotesEdificacao");
			
			lotesEdificacao.add(loteAux);
			
			request.getSession().setAttribute("lotesEdificacao", lotesEdificacao);
			
			ItemComboDTO obj = new ItemComboDTO();
			obj.setCodigo(String.valueOf(loteAux.getCodLote()));
			obj.setDescricao("Quadra: "+ loteAux.getQuadra().getDescricao() + " /Lote: " + loteAux.getDescricao());
				
			lotes.remove(obj);
			
			this.carregarListaEdificacao(form, request);

		} catch (ApplicationException appEx) {
			this.carregarListaEdificacao(form, request);
			throw appEx;

		} catch (Exception e) {
			this.carregarListaEdificacao(form, request);
			throw new ApplicationException("ERRO.200", new String[]{"edição","edificação"}, e);
		}	

		
		return getActionForward();
    }
	
	
	
	
	
	public ActionForward excluirLote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException , Exception{
		
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		try {
			setActionForward(mapping.findForward("pgEditEdificacao"));
			// Verifica se o TOKEN existe para evitar duplo submit
			if(isTokenValid(request)) {
				resetToken(request);
			} else {
				throw new ApplicationException("AVISO.200");
			}	
			
			Lote loteAux = CadastroFacade.obterLoteComRelacionamentos(Integer.parseInt(request.getParameter("codLote")));
			
			Set<Lote> lotesEdificacao = (Set<Lote>) request.getSession().getAttribute("lotesEdificacao");
			lotesEdificacao.remove(loteAux);
			request.getSession().setAttribute("lotesEdificacao", lotesEdificacao);
			
			ItemComboDTO obj = new ItemComboDTO();
			obj.setCodigo(String.valueOf(loteAux.getCodLote()));
			obj.setDescricao("Quadra: "+ loteAux.getQuadra().getDescricao() + " /Lote: " + loteAux.getDescricao());
				
			lotes.add(obj);
			this.carregarListaEdificacao(form, request);

		} catch (ApplicationException appEx) {
			this.carregarListaEdificacao(form, request);
			throw appEx;

		} catch (Exception e) {
			this.carregarListaEdificacao(form, request);
			throw new ApplicationException("ERRO.200", new String[]{"edição","edificação"}, e);
		}	

		
		return getActionForward();
    }
	

	
	
	
	public ActionForward salvarEdificacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		
		EdificacaoForm edificacaoForm = (EdificacaoForm) form;
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dataAtual = new Date();
		setActionForward(mapping.findForward("pgEditEdificacao"));
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			this.carregarListaEdificacao(form, request);
			return getActionForward();
		}	

		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
					
			throw new ApplicationException("AVISO.200");
		}	
		//salva edificação
		try	{			
			Edificacao edificacao = new Edificacao();

			BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.parseInt(edificacaoForm.getCodBemImovel()));
			if (bemImovel.getSomenteTerreno().trim().equalsIgnoreCase("S")) {
				bemImovel.setSomenteTerreno("N");
				CadastroFacade.alterarBemImovel(bemImovel);
			}
			
			Set<Lote> lotesEdificacao = (Set<Lote>) request.getSession().getAttribute("lotesEdificacao");
			
			edificacao.setBemImovel(bemImovel);
			edificacao.setCpfResponsavel(sentinelaInterface.getCpf());
			edificacao.setDataAverbacao(!StringUtils.isBlank(edificacaoForm.getDataAverbacao()) ? sdf.parse(edificacaoForm.getDataAverbacao()) : null);
			edificacao.setAreaConstruida(Valores.converterStringParaBigDecimal(edificacaoForm.getAreaConstruida()));
			edificacao.setAreaUtilizada(Valores.converterStringParaBigDecimal(edificacaoForm.getAreaUtilizada()));
			edificacao.setTsInclusao(dataAtual);
			edificacao.setTsAtualizacao(dataAtual);
			edificacao.setEspecificacao(edificacaoForm.getEspecificacao());
			edificacao.setLogradouro(edificacaoForm.getLogradouro());
			edificacao.setTipoConstrucao(CadastroFacade.obterTipoConstrucao(Integer.parseInt (edificacaoForm.getTipoConstrucao())));
			edificacao.setTipoEdificacao(CadastroFacade.obterTipoEdificacao(Integer.parseInt(edificacaoForm.getTipoEdificacao())));
			edificacao.setLotes(lotesEdificacao);
						
			CadastroFacade.salvarEdificacao(edificacao);
			CadastroFacade.atualizarAreaBemImovel(bemImovel);
			if (bemImovel.getAreaDispoNivel().doubleValue() < 0)
			{
				addAlertMessage("AVISO.6", request);	
			}
			
			//limpar formulario
			lotesEdificacao = new HashSet <Lote>();
			
			request.getSession().setAttribute("lotesEdificacao", lotesEdificacao);
			
			this.limparFormulario(edificacaoForm);
						
			addMessage("SUCESSO.15", request);	
		
			this.carregarListaEdificacao(edificacaoForm, request);
			
		} catch (ApplicationException appEx) {
			this.carregarListaEdificacao(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarListaEdificacao(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Edificação"}, e);
		}
		
		return getActionForward();
    }
	
	public ActionForward alterarEdificacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		
		setActionForward(mapping.findForward("pgEditEdificacao"));
		EdificacaoForm edificacaoForm = (EdificacaoForm) form;
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dataAtual = new Date();
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			this.carregarListaEdificacao(form, request);
			return getActionForward();
		}	

		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			throw new ApplicationException("AVISO.200");
		}	
		//salva edificação
		try	{			
			Edificacao edificacao = CadastroFacade.obterEdificacao(Integer.parseInt(request.getParameter("codEdificacao")));
			
			Set<Lote> lotesEdificacao = (Set<Lote>) request.getSession().getAttribute("lotesEdificacao");
			
			edificacao.setCpfResponsavel(sentinelaInterface.getCpf());
			edificacao.setDataAverbacao(!StringUtils.isBlank(edificacaoForm.getDataAverbacao()) ? sdf.parse(edificacaoForm.getDataAverbacao()) : null);
			edificacao.setAreaConstruida(Valores.converterStringParaBigDecimal(edificacaoForm.getAreaConstruida()));
			edificacao.setAreaUtilizada(Valores.converterStringParaBigDecimal(edificacaoForm.getAreaUtilizada()));
			edificacao.setTsAtualizacao(dataAtual);
			edificacao.setEspecificacao(edificacaoForm.getEspecificacao());
			edificacao.setLogradouro(edificacaoForm.getLogradouro());
			edificacao.setTipoConstrucao(CadastroFacade.obterTipoConstrucao(Integer.parseInt (edificacaoForm.getTipoConstrucao())));
			edificacao.setTipoEdificacao(CadastroFacade.obterTipoEdificacao(Integer.parseInt(edificacaoForm.getTipoEdificacao())));
			edificacao.setLotes(lotesEdificacao);
						
			CadastroFacade.alterarEdificacao(edificacao);
			CadastroFacade.atualizarAreaBemImovel(edificacao.getBemImovel());
			
			
			//limpar formulario
			lotesEdificacao = new HashSet <Lote>();
			request.getSession().setAttribute("lotesEdificacao", lotesEdificacao);
			
			lotes = Util.htmlEncodeCollection(CadastroFacade.listarLotesCombo(Integer.parseInt(request.getParameter("codBemImovel"))));
			this.limparFormulario(edificacaoForm);
			this.carregarListaEdificacao(edificacaoForm, request);
			edificacaoForm.setActionType("incluir");
			
			if (edificacao.getBemImovel().getAreaDispoNivel().doubleValue()<0)
			{
				addAlertMessage("AVISO.6", request);	
			}
						
			addMessage("SUCESSO.16", request);	
					
		} catch (ApplicationException appEx) {
			this.carregarListaEdificacao(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarListaEdificacao(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Edificação"}, e);
		}
		
		return getActionForward();
    }
	
	public ActionForward excluirEdificacao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception  {
		
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		
		setActionForward(mapping.findForward("pgEditEdificacao"));
		EdificacaoForm edificacaoForm = (EdificacaoForm) form;
			
		try {
			if (edificacaoForm.getCodEdificacao() != null) {
				
				CadastroFacade.excluirEdificacao(Integer.parseInt(request.getParameter("codEdificacao")));
				CadastroFacade.atualizarAreaBemImovel(Integer.parseInt(edificacaoForm.getCodBemImovel()));
				
				addMessage("SUCESSO.25", request);
			}
			
			Set<Lote> lotesEdificacao = new HashSet <Lote>();
			
			request.getSession().setAttribute("lotesEdificacao", lotesEdificacao);
			lotes = Util.htmlEncodeCollection(CadastroFacade.listarLotesCombo(Integer.parseInt(request.getParameter("codBemImovel"))));
			this.limparFormulario(edificacaoForm);
			this.carregarListaEdificacao(edificacaoForm, request);
			edificacaoForm.setActionType("incluir");
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			this.carregarListaEdificacao(form, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarListaEdificacao(form, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Edificacao"}, e);
		}
	}

	@SuppressWarnings("unchecked")
	private void carregarListaEdificacao(ActionForm form, HttpServletRequest request ) throws ApplicationException {
	
		EdificacaoForm edificacaoForm = (EdificacaoForm) form;
		
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		if(lotes != null && lotes.size() > 0) {
			request.setAttribute("lotes", lotes);
		}

		request.setAttribute("tipoConstrucaos", Util.htmlEncodeCollection(CadastroFacade.listarTipoConstrucaos()));
		request.setAttribute("tipoEdificacaos", Util.htmlEncodeCollection(CadastroFacade.listarTipoEdificacaos()));
		request.setAttribute("situacaoOcupacaos", Util.htmlEncodeCollection(CadastroFacade.listarSituacaoOcupacaos()));

		edificacaoForm.setCodBemImovel(request.getParameter("codBemImovel"));
		edificacaoForm.setNrBemImovel(CadastroFacade.obterBemImovel(new Integer(request.getParameter("codBemImovel"))).getNrBemImovel().toString());

		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
				

		Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina),Integer.parseInt(totalRegistros));
		pagina = CadastroFacade.listarEdificacao(pagina, Integer.parseInt(edificacaoForm.getCodBemImovel()));
		Util.htmlEncodeCollection(pagina.getRegistros());
		request.setAttribute("pagina", pagina);
		Set<Lote> lotesEdificacao = (Set<Lote>) request.getSession().getAttribute("lotesEdificacao");
		
		if (lotesEdificacao != null && ! lotesEdificacao.isEmpty())
		{
			String indicePagina1 = request.getParameter("indice1") == null ? "1" : request.getParameter("indice1");		
			String totalRegistros1 = request.getParameter("totalRegistros1") == null ? "0" : request.getParameter("totalRegistros1");
			Pagina pagina1 = new Pagina (Integer.valueOf(Dominios.QTD_PAGINA),Integer.parseInt(indicePagina1), Integer.parseInt(totalRegistros1));
			pagina1.setQuantidade(lotesEdificacao.size());
			pagina1.setRegistros(lotesEdificacao);
			request.setAttribute("pagina1", pagina1);
		}
		
		BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.parseInt(edificacaoForm.getCodBemImovel()));
		edificacaoForm.setMunicipio(bemImovel.getMunicipio());
		edificacaoForm.setUf(bemImovel.getUf());
		
	}
	
	
	private void limparFormulario(ActionForm form){
		EdificacaoForm edificacaoForm = (EdificacaoForm) form;
		edificacaoForm.setAreaConstruida("");
		edificacaoForm.setAreaUtilizada("");
		edificacaoForm.setDataAverbacao("");
		edificacaoForm.setEspecificacao("");
		edificacaoForm.setLogradouro("");
		edificacaoForm.setLote("");
		edificacaoForm.setTipoConstrucao("");
		edificacaoForm.setTipoEdificacao("");
	}
}