package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.OcupacaoForm;
import gov.pr.celepar.abi.pojo.Edificacao;
import gov.pr.celepar.abi.pojo.Ocupacao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.StringUtil;
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
 * @author Luciana R. Bélico
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class OcupacaoAction extends BaseDispatchAction {
	
	private static Logger log4j = Logger.getLogger(OcupacaoAction.class);
	
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
	 * Realiza carga da aba Edificacao - ocupação.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgEditOcupacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		verificarTodasOperacoes(request);

		if(!Boolean.valueOf(request.getAttribute("A").toString())) {
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}

		try {
			setActionForward(mapping.findForward("pgEditOcupacao"));
			
			if(request.getParameter("actionType").equals("incluir")) {
				OcupacaoForm ocupacaoForm = (OcupacaoForm)form;
				Edificacao edificacao = CadastroFacade.obterEdificacao(Integer.parseInt(request.getParameter("codEdificacao")));
				
				this.limparFormularioOcupacao(ocupacaoForm);
				verificarGrupoUsuarioLogado(ocupacaoForm, request);			
				if (ocupacaoForm.getIsGpAdmGeralUsuarioLogado().equalsIgnoreCase("S")) {
					request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(null, edificacao.getBemImovel().getInstituicao().getCodInstituicao())));
				} else {
					request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(null, SentinelaComunicacao.getInstance(request).getCodUsuario())));
				}

				ocupacaoForm.setCodEdificacao(edificacao.getCodEdificacao().toString());
				ocupacaoForm.setEspecificacao(edificacao.getEspecificacao());
				ocupacaoForm.setAreaConstruida(edificacao.getAreaConstruida() != null ? edificacao.getAreaConstruida().toString() : null);
				
				this.carregarListaOcupacao(ocupacaoForm, request);
				ocupacaoForm.setActionType("incluir");
			}
			else if(request.getParameter("actionType").equals("alterar")) {
				OcupacaoForm ocupacaoForm = (OcupacaoForm)form;
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Ocupacao ocupacao = CadastroFacade.obterOcupacao(Integer.parseInt(request.getParameter("codOcupacao")));
			
				this.limparFormularioOcupacao(ocupacaoForm);
				verificarGrupoUsuarioLogado(ocupacaoForm, request);			
				if (ocupacaoForm.getIsGpAdmGeralUsuarioLogado().equalsIgnoreCase("S")) {
					request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(null, ocupacao.getBemImovel().getInstituicao().getCodInstituicao())));
				} else {
					request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(null, SentinelaComunicacao.getInstance(request).getCodUsuario())));
				}

				ocupacaoForm.setCodEdificacao(ocupacao.getEdificacao().getCodEdificacao().toString());
				ocupacaoForm.setEspecificacao(ocupacao.getEdificacao().getEspecificacao());
				ocupacaoForm.setAreaConstruida(ocupacao.getEdificacao().getAreaConstruida() != null ? ocupacao.getEdificacao().getAreaConstruida().toString() : null);
				ocupacaoForm.setSituacaoOcupacao(ocupacao.getSituacaoOcupacao() != null ? ocupacao.getSituacaoOcupacao().getCodSituacaoOcupacao().toString() : null);

				this.carregarListaOcupacao(ocupacaoForm, request);
				
				ocupacaoForm.setCodOrgao(ocupacao.getOrgao() != null ? ocupacao.getOrgao().getCodOrgao().toString() : null);
				ocupacaoForm.setDescricao(ocupacao.getDescricao());
				ocupacaoForm.setOcupacaoMetroQuadrado(ocupacao.getOcupacaoMetroQuadrado() != null ? Valores.formatarParaDecimal(ocupacao.getOcupacaoMetroQuadrado(), 2) : null);
				ocupacaoForm.setOcupacaoPercentual(ocupacao.getOcupacaoPercentual() != null ? Valores.formatarParaDecimal(ocupacao.getOcupacaoPercentual(), 2) : null);
				ocupacaoForm.setDataOcupacao(ocupacao.getDataOcupacao() != null ? sdf.format(ocupacao.getDataOcupacao()) : null);
				ocupacaoForm.setTermoTransferencia(ocupacao.getTermoTransferencia());
				ocupacaoForm.setNumeroLei(ocupacao.getNumeroLei());
				ocupacaoForm.setDataLei(ocupacao.getDataLei() != null ? sdf.format(ocupacao.getDataLei()) : null);
				ocupacaoForm.setVigenciaLei(ocupacao.getVigenciaLei());
				ocupacaoForm.setNumeroNotificacao(ocupacao.getNumeroNotificacao());
				ocupacaoForm.setPrazoNotificacao(ocupacao.getPrazoNotificacao());
				ocupacaoForm.setProtocoloNotificacaoSpi(ocupacao.getProtocoloNotificacaoSpi() != null ? ocupacao.getProtocoloNotificacaoSpi().toString() : null);
				ocupacaoForm.setActionType("alterar");
			}
			
		} catch (ApplicationException appEx) {
			this.carregarListaOcupacao(form, request);
			throw appEx;

		} catch (Exception e) {
			this.carregarListaOcupacao(form, request);
			throw new ApplicationException("ERRO.200", new String[]{"edição","ocupação"}, e);
		}	

		
		return getActionForward();
    }
	
	public ActionForward calcularOcupacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			setActionForward(mapping.findForward("pgEditOcupacao"));
			OcupacaoForm ocupacaoForm = (OcupacaoForm) form;
		
						
			if (! StringUtil.stringNotNull(ocupacaoForm.getOcupacaoMetroQuadrado()) && ! StringUtil.stringNotNull(ocupacaoForm.getOcupacaoPercentual()))
			{			
				throw new ApplicationException("AVISO.7");
			}
			else if (StringUtil.stringNotNull(ocupacaoForm.getOcupacaoMetroQuadrado()))
			{
				
				Double ocupacaoMetroQuadrado = new Double(0);				
				if (!ocupacaoForm.getOcupacaoMetroQuadrado().isEmpty()){
					ocupacaoMetroQuadrado =  (Valores.converterStringParaBigDecimal(ocupacaoForm.getOcupacaoMetroQuadrado())).doubleValue();	
				}
				
				Double areaConstruida=  new Double(0);				
				if (!ocupacaoForm.getAreaConstruida().isEmpty()){
					areaConstruida=  Double.parseDouble(ocupacaoForm.getAreaConstruida());
				}
				
				
				if(ocupacaoMetroQuadrado.compareTo(areaConstruida)>0)
				{
					throw new ApplicationException("AVISO.9");
				}
				else{
					Double ocupacaoPercentual =  new Double(0);	
					if ((ocupacaoMetroQuadrado != null && ocupacaoMetroQuadrado != 0) && (areaConstruida != null && areaConstruida != 0)){
						ocupacaoPercentual = ocupacaoMetroQuadrado/areaConstruida;						
					}					
					ocupacaoPercentual = Valores.arredondar((ocupacaoPercentual * 100), 2);
					ocupacaoForm.setOcupacaoPercentual(Valores.formatarParaDecimal(ocupacaoPercentual, 2));
				}

			}else if (StringUtil.stringNotNull(ocupacaoForm.getOcupacaoPercentual()))
			{
				Double ocupacaoPercentual =  new Double(0);	
				if  (!ocupacaoForm.getOcupacaoPercentual().isEmpty()){
					ocupacaoPercentual =  (Valores.converterStringParaBigDecimal(ocupacaoForm.getOcupacaoPercentual())).doubleValue();	
				}
				
				Double areaConstruida= new Double(0);
				if (!ocupacaoForm.getAreaConstruida().isEmpty()){
					areaConstruida= Double.parseDouble(ocupacaoForm.getAreaConstruida());
				}
				
				if(ocupacaoPercentual.doubleValue()>100)
				{
					throw new ApplicationException("AVISO.8");
				}
				else{
					Double ocupacaoMetroQuadrado = new Double(0);
					if (ocupacaoPercentual!=null){
						ocupacaoMetroQuadrado = ocupacaoPercentual/100;
						ocupacaoMetroQuadrado = Valores.arredondar((ocupacaoMetroQuadrado * areaConstruida), 2);
					}
					
					ocupacaoForm.setOcupacaoMetroQuadrado(Valores.formatarParaDecimal(ocupacaoMetroQuadrado, 2));
				}
				
			}
			
			this.carregarListaOcupacao(ocupacaoForm, request);

		} catch (ApplicationException appEx) {
			log4j.error("ERRO",appEx.getCausaRaiz());
			this.carregarListaOcupacao(form, request);
			throw appEx;

		} catch (Exception e) {
			log4j.error("ERRO",e);
			this.carregarListaOcupacao(form, request);
			throw new ApplicationException("ERRO.200", new String[]{"edição","ocupação"}, e);
		}

		return getActionForward();
    }
	
	public ActionForward salvarOcupacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("A").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		
		setActionForward(mapping.findForward("pgEditOcupacao"));
		OcupacaoForm ocupacaoForm = (OcupacaoForm) form;
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dataAtual = new Date();
	

		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			this.carregarListaOcupacao(ocupacaoForm, request);
			ocupacaoForm.setActionType("incluir");
			return getActionForward();
		}	

		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			throw new ApplicationException("AVISO.200");
		}	
		//salva ocupação
		try	{			
			Ocupacao ocupacao = new Ocupacao();

			Edificacao edificacao = CadastroFacade.obterEdificacao(Integer.parseInt(ocupacaoForm.getCodEdificacao()));
			
			ocupacao.setOrgao(CadastroFacade.obterOrgao(Integer.valueOf(ocupacaoForm.getCodOrgao())));
			ocupacao.setDescricao(ocupacaoForm.getDescricao());
			ocupacao.setCpfResponsavel(sentinelaInterface.getCpf());
			ocupacao.setDataLei(StringUtil.stringNotNull(ocupacaoForm.getDataLei()) ? sdf.parse(ocupacaoForm.getDataLei()) : null);
			ocupacao.setDataOcupacao(StringUtil.stringNotNull(ocupacaoForm.getDataOcupacao()) ? sdf.parse(ocupacaoForm.getDataOcupacao()) : null);
			ocupacao.setTermoTransferencia(ocupacaoForm.getTermoTransferencia());
			ocupacao.setEdificacao(edificacao);
			ocupacao.setNumeroLei(ocupacaoForm.getNumeroLei());
			ocupacao.setNumeroNotificacao(ocupacaoForm.getNumeroNotificacao());
			ocupacao.setSituacaoOcupacao(CadastroFacade.obterSituacaoOcupacao(Integer.parseInt(ocupacaoForm.getSituacaoOcupacao())));

			if(StringUtil.stringNotNull(ocupacaoForm.getOcupacaoMetroQuadrado())) {
				Double ocupacaoMetroQuadrado =  (Valores.converterStringParaBigDecimal(ocupacaoForm.getOcupacaoMetroQuadrado())).doubleValue();
				Double areaConstruida = Double.parseDouble(ocupacaoForm.getAreaConstruida());
				if(ocupacaoMetroQuadrado.compareTo(areaConstruida) > 0) {
					throw new ApplicationException("AVISO.9");
				}
				if(!StringUtil.stringNotNull(ocupacaoForm.getOcupacaoPercentual())) {
					Double ocupacaoPercentual = ocupacaoMetroQuadrado/areaConstruida;
					ocupacaoPercentual = Valores.arredondar((ocupacaoPercentual * 100), 2);
					ocupacao.setOcupacaoPercentual(BigDecimal.valueOf(ocupacaoPercentual));
				}
				ocupacao.setOcupacaoMetroQuadrado(BigDecimal.valueOf(ocupacaoMetroQuadrado));
			}
			if(StringUtil.stringNotNull(ocupacaoForm.getOcupacaoPercentual())) {
				Double ocupacaoPercentual = (Valores.converterStringParaBigDecimal(ocupacaoForm.getOcupacaoPercentual())).doubleValue();
				Double areaConstruida = Double.parseDouble(ocupacaoForm.getAreaConstruida());
				if(ocupacaoPercentual.doubleValue() > 100) {
					throw new ApplicationException("AVISO.8");
				}
				if(!StringUtil.stringNotNull(ocupacaoForm.getOcupacaoMetroQuadrado())) {
					Double ocupacaoMetroQuadrado = ocupacaoPercentual/100;
					ocupacaoMetroQuadrado = Valores.arredondar((ocupacaoMetroQuadrado * areaConstruida), 2);
					ocupacao.setOcupacaoMetroQuadrado(BigDecimal.valueOf(ocupacaoMetroQuadrado));
				}
				ocupacao.setOcupacaoPercentual(BigDecimal.valueOf(ocupacaoPercentual));
			}

			ocupacao.setProtocoloNotificacaoSpi(StringUtil.stringNotNull(ocupacaoForm.getProtocoloNotificacaoSpi()) ? Integer.parseInt(ocupacaoForm.getProtocoloNotificacaoSpi()) : null);
			ocupacao.setPrazoNotificacao(ocupacaoForm.getPrazoNotificacao());
			ocupacao.setTsAtualizacao(dataAtual);
			ocupacao.setTsInclusao(dataAtual);
			ocupacao.setVigenciaLei(ocupacaoForm.getVigenciaLei());
					
			CadastroFacade.salvarOcupacao(ocupacao);
			
			//limpar formulario
			this.limparFormularioOcupacao(ocupacaoForm);
			verificarGrupoUsuarioLogado(ocupacaoForm, request);			
			if (ocupacaoForm.getIsGpAdmGeralUsuarioLogado().equalsIgnoreCase("S")) {
				request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(null, edificacao.getBemImovel().getInstituicao().getCodInstituicao())));
			} else {
				request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(null, SentinelaComunicacao.getInstance(request).getCodUsuario())));
			}
						
			addMessage("SUCESSO.17", request);	
			
			this.carregarListaOcupacao(ocupacaoForm, request);
			ocupacaoForm.setActionType("incluir");
			
		} catch (ApplicationException appEx) {
			log4j.error("ERRO",appEx.getCausaRaiz());
			this.carregarListaOcupacao(ocupacaoForm, request);
			ocupacaoForm.setActionType("incluir");
			throw appEx;
		} catch (Exception e) {
			log4j.error("ERRO",e);
			this.carregarListaOcupacao(ocupacaoForm, request);
			ocupacaoForm.setActionType("incluir");
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Ocupação"}, e);
		}
		
		return getActionForward();
    }

	
	
	public ActionForward alterarOcupacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		verificarTodasOperacoes(request);

		if(!Boolean.valueOf(request.getAttribute("A").toString())) {
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		OcupacaoForm ocupacaoForm = (OcupacaoForm)form;
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		// Aciona a validação do Form
		if(!validaForm(mapping, form, request)) {
			this.carregarListaOcupacao(ocupacaoForm, request);
			return mapping.findForward("pgEditOcupacao");
		}

		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		}
		else {
			this.carregarListaOcupacao(ocupacaoForm, request);
			setActionForward(mapping.findForward("pgEditOcupacao"));
			throw new ApplicationException("AVISO.200");
		}	
		//salva ocupação
		try	{			
			Ocupacao ocupacao = CadastroFacade.obterOcupacao(Integer.parseInt(request.getParameter("codOcupacao")));

			Date dataAtual = new Date(); 

			ocupacao.setOrgao(CadastroFacade.obterOrgao(Integer.valueOf(ocupacaoForm.getCodOrgao())));
			ocupacao.setDescricao(ocupacaoForm.getDescricao());
			ocupacao.setCpfResponsavel(sentinelaInterface.getCpf());
			ocupacao.setDataLei(StringUtil.stringNotNull(ocupacaoForm.getDataLei()) ? sdf.parse(ocupacaoForm.getDataLei()) : null);
			ocupacao.setDataOcupacao(StringUtil.stringNotNull(ocupacaoForm.getDataOcupacao()) ? sdf.parse(ocupacaoForm.getDataOcupacao()) : null);
			ocupacao.setTermoTransferencia(ocupacaoForm.getTermoTransferencia());
			ocupacao.setNumeroLei(ocupacaoForm.getNumeroLei());
			ocupacao.setNumeroNotificacao(ocupacaoForm.getNumeroNotificacao());
			ocupacao.setSituacaoOcupacao(CadastroFacade.obterSituacaoOcupacao(Integer.parseInt(ocupacaoForm.getSituacaoOcupacao())));

			if(StringUtil.stringNotNull(ocupacaoForm.getOcupacaoMetroQuadrado())) {
				Double ocupacaoMetroQuadrado =  (Valores.converterStringParaBigDecimal(ocupacaoForm.getOcupacaoMetroQuadrado())).doubleValue();
				Double areaConstruida=  Double.parseDouble(ocupacaoForm.getAreaConstruida());
				if(ocupacaoMetroQuadrado.compareTo(areaConstruida) > 0) {
					throw new ApplicationException("AVISO.9");
				}
				if(!StringUtil.stringNotNull(ocupacaoForm.getOcupacaoPercentual())) {
					Double ocupacaoPercentual = ocupacaoMetroQuadrado/areaConstruida;
					ocupacaoPercentual = Valores.arredondar((ocupacaoPercentual * 100), 2);
					ocupacao.setOcupacaoPercentual(BigDecimal.valueOf(ocupacaoPercentual));
				}
				ocupacao.setOcupacaoMetroQuadrado(BigDecimal.valueOf(ocupacaoMetroQuadrado));
			}
			if(StringUtil.stringNotNull(ocupacaoForm.getOcupacaoPercentual())) {
				Double ocupacaoPercentual =  (Valores.converterStringParaBigDecimal(ocupacaoForm.getOcupacaoPercentual())).doubleValue();
				Double areaConstruida= Double.parseDouble(ocupacaoForm.getAreaConstruida());
				if(ocupacaoPercentual.doubleValue() > 100) {
					throw new ApplicationException("AVISO.8");
				}
				if(!StringUtil.stringNotNull(ocupacaoForm.getOcupacaoMetroQuadrado())) {
					Double ocupacaoMetroQuadrado = ocupacaoPercentual/100;
					ocupacaoMetroQuadrado = Valores.arredondar((ocupacaoMetroQuadrado * areaConstruida), 2);
					ocupacao.setOcupacaoMetroQuadrado(BigDecimal.valueOf(ocupacaoMetroQuadrado));
				}
				ocupacao.setOcupacaoPercentual(BigDecimal.valueOf(ocupacaoPercentual));
			}

			ocupacao.setProtocoloNotificacaoSpi(StringUtil.stringNotNull(ocupacaoForm.getProtocoloNotificacaoSpi()) ? Integer.parseInt(ocupacaoForm.getProtocoloNotificacaoSpi()) : null);
			ocupacao.setPrazoNotificacao(ocupacaoForm.getPrazoNotificacao());
			ocupacao.setTsAtualizacao(dataAtual);
			ocupacao.setVigenciaLei(ocupacaoForm.getVigenciaLei());
					
			CadastroFacade.alterarOcupacao(ocupacao);
			
			//limpar formulario
			this.limparFormularioOcupacao(ocupacaoForm);
			verificarGrupoUsuarioLogado(ocupacaoForm, request);			
			if (ocupacaoForm.getIsGpAdmGeralUsuarioLogado().equalsIgnoreCase("S")) {
				request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(null, ocupacao.getBemImovel().getInstituicao().getCodInstituicao())));
			} else {
				request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(null, SentinelaComunicacao.getInstance(request).getCodUsuario())));
			}
						
			addMessage("SUCESSO.18", request);	
			
			this.carregarListaOcupacao(ocupacaoForm, request);
			ocupacaoForm.setActionType("incluir");
			
		} catch (ApplicationException appEx) {
			this.carregarListaOcupacao(ocupacaoForm, request);
			setActionForward(mapping.findForward("pgEditOcupacao"));
			throw appEx;
		} catch (Exception e) {
			this.carregarListaOcupacao(ocupacaoForm, request);
			setActionForward(mapping.findForward("pgEditOcupacao"));
			throw new ApplicationException("ERRO.201", new String[]{"ao alterar Ocupação"}, e);
		}
		
		return mapping.findForward("pgEditOcupacao");
    }

	

	private void carregarListaOcupacao(ActionForm form, HttpServletRequest request) throws ApplicationException {

		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		OcupacaoForm ocupacaoForm = (OcupacaoForm)form;
		String indicePagina2 = request.getParameter("indice2") == null ? "1" : request.getParameter("indice2");		
		String totalRegistros2 = request.getParameter("totalRegistros2") == null ? "0" : request.getParameter("totalRegistros2");
				
		Pagina pagina2 = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.parseInt(indicePagina2), Integer.parseInt(totalRegistros2));
		pagina2 = CadastroFacade.listarOcupacao(pagina2, Integer.parseInt(ocupacaoForm.getCodEdificacao()));
		Util.htmlEncodeCollection(pagina2.getRegistros());
		request.setAttribute("pagina2", pagina2);
		
		request.setAttribute("SITUACAO_OCUPACAO_ADMINISTRACAO_ESTADUAL", Dominios.SITUACAO_OCUPACAO_ADMINISTRACAO_ESTADUAL);
		request.setAttribute("SITUACAO_OCUPACAO_ADMINISTRACAO_FEDERAL", Dominios.SITUACAO_OCUPACAO_ADMINISTRACAO_FEDERAL);
		request.setAttribute("SITUACAO_OCUPACAO_ADMINISTRACAO_MUNICIPAL", Dominios.SITUACAO_OCUPACAO_ADMINISTRACAO_MUNICIPAL);
		request.setAttribute("SITUACAO_OCUPACAO_TERCEIRO_AUTORIZADO", Dominios.SITUACAO_OCUPACAO_TERCEIRO_AUTORIZADO);
		request.setAttribute("SITUACAO_OCUPACAO_TERCEIRO_NAO_AUTORIZADO", Dominios.SITUACAO_OCUPACAO_TERCEIRO_NAO_AUTORIZADO);
		request.setAttribute("situacaoOcupacaos", Util.htmlEncodeCollection(CadastroFacade.listarSituacaoOcupacaos()));
		
	}
	
	public ActionForward excluirOcupacao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		verificarTodasOperacoes(request);
		
		if(!Boolean.valueOf(request.getAttribute("A").toString())) {
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		setActionForward(mapping.findForward("pgEditOcupacao"));
		OcupacaoForm ocupacaoForm = (OcupacaoForm) form;
			
		try {
			Ocupacao ocupacao = null;
			if(ocupacaoForm.getCodOcupacao() != null) {
				ocupacao = CadastroFacade.obterOcupacao(Integer.parseInt(request.getParameter("codOcupacao")));
				CadastroFacade.excluirOcupacao(Integer.parseInt(request.getParameter("codOcupacao")));
				addMessage("SUCESSO.25", request);
			}

			this.limparFormularioOcupacao(ocupacaoForm);
			if (ocupacao != null) {
				verificarGrupoUsuarioLogado(ocupacaoForm, request);			
				if (ocupacaoForm.getIsGpAdmGeralUsuarioLogado().equalsIgnoreCase("S")) {
					request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmECodInstituicao(null, ocupacao.getBemImovel().getInstituicao().getCodInstituicao())));
				} else {
					request.getSession().setAttribute("orgaos", Util.htmlEncodeCollection(CadastroFacade.listarOrgaosComboPorTipoAdmEUsuarioSentinela(null, SentinelaComunicacao.getInstance(request).getCodUsuario())));
				}
			}

			this.carregarListaOcupacao(ocupacaoForm, request);
			ocupacaoForm.setActionType("incluir");
			return getActionForward();
			
		} catch (ApplicationException appEx) {
			this.carregarListaOcupacao(ocupacaoForm, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarListaOcupacao(ocupacaoForm, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao excluir Ocupação"}, e);
		}
	}
	
	private void limparFormularioOcupacao(ActionForm form){
		OcupacaoForm ocupacaoForm = (OcupacaoForm) form;
		ocupacaoForm.setCodOrgao("");
		ocupacaoForm.setDescricao("");
		ocupacaoForm.setOcupacaoMetroQuadrado("");
		ocupacaoForm.setOcupacaoPercentual("");
		ocupacaoForm.setDataOcupacao("");
		ocupacaoForm.setTermoTransferencia("");
		ocupacaoForm.setDataLei("");
		ocupacaoForm.setNumeroLei("");
		ocupacaoForm.setVigenciaLei("");
		ocupacaoForm.setNumeroNotificacao("");
		ocupacaoForm.setPrazoNotificacao("");
		ocupacaoForm.setProtocoloNotificacaoSpi("");
		ocupacaoForm.setSituacaoOcupacao("");
	}

	private void verificarGrupoUsuarioLogado(OcupacaoForm localForm, HttpServletRequest request) throws ApplicationException {
		boolean result = CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.ADM_GERAL.getCodigo());
		localForm.setIsGpAdmGeralUsuarioLogado("N");
		if (result) {
			localForm.setIsGpAdmGeralUsuarioLogado("S");
		}
	}

}