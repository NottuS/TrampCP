package gov.pr.celepar.ucs_manterinstituicao.action;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.exception.ConstraintViolationException;

import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.ucs_manterinstituicao.decorator.CPFDecorator;
import gov.pr.celepar.ucs_manterinstituicao.facade.ManterColaboradorFacade;
import gov.pr.celepar.ucs_manterinstituicao.facade.ManterInstituicaoFacade;
import gov.pr.celepar.ucs_manterinstituicao.form.ManterColaboradorForm;
import gov.pr.celepar.ucs_manterinstituicao.pojo.Colaborador;
import gov.pr.celepar.ucs_manterinstituicao.pojo.Instituicao;
import gov.pr.celepar.ucs_manterinstituicao.util.Dominios;

public class ManterColaboradorAction extends BaseDispatchAction{
	public ActionForward carregarPgConColaboradores(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		
		return mapping.findForward("pgConColaborador");
    }
	
	public ActionForward  pesquisarColaboradores(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		ManterColaboradorForm  mcf = (ManterColaboradorForm) form;
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		
		Pagina pagina = new Pagina(new Integer(Dominios.QTD_PAGINA), new Integer(indicePagina), new Integer(totalRegistros));

		try {
			Colaborador colaborador = mcf.convertToColaborador(null);
			SimpleDateFormat smf = new SimpleDateFormat("dd/MM/yy");
			Date dataInicial = null;
			Date dataFinal = null;
			if (mcf.getDataInicial() != null && !mcf.getDataInicial().isEmpty() ) {
				dataInicial = smf.parse(mcf.getDataInicial());
			}
			if (mcf.getDataFinal() != null && !mcf.getDataFinal().isEmpty() ) {
				dataFinal = smf.parse(mcf.getDataFinal());
			}
			System.out.println(dataFinal);
			System.out.println(dataInicial);
			pagina = ManterColaboradorFacade.listarColaborador(pagina, colaborador, dataInicial, dataFinal, mcf.getSituacao());
			request.setAttribute("pagina", pagina);
			if(pagina.getTotalRegistros() == 0){
				addAlertMessage("AVISO.15", request);
			}

			return mapping.findForward("pgConColaborador");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgConColaborador"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgConColaborador"));
			throw new ApplicationException("mensagem.erro.matricula.listarAluno", e);
		}
	}
	
	public ActionForward carregarPgViewColaborador(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		ManterColaboradorForm  mcf = (ManterColaboradorForm) form;
		
		try {
			Colaborador colaborador = ManterColaboradorFacade.obterColaborador(Integer.parseInt(mcf.getIdColaborador()));
			mcf.preencheColaborador(colaborador);
			mcf.setInstituicao(colaborador.getInstituicao().getRazaoSocial());
			mcf.setCpf(new CPFDecorator().decorate(mcf.getCpf()));
			return mapping.findForward("pgViewColaborador");
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgConColaborador"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgConColaborador"));
			throw new ApplicationException("mensagem.erro.colaborador.carregarPgViewColaborador", e);
		}
	}
	
	public ActionForward excluirColaborador(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		ManterColaboradorForm  mcf = (ManterColaboradorForm) form;
			
		try {
			if (mcf.getIdColaborador() != null) {
				Colaborador colaborador = ManterColaboradorFacade
						.obterColaborador(Integer.parseInt(mcf.getIdColaborador()));
				ManterColaboradorFacade.excluirColaborador(colaborador);
			}
			
			addMessage("mensagem.sucesso.colaborador.excluir", request);			

			return mapping.findForward("pgConColaborador");
			
		} catch (ApplicationException appEx) {
			if(appEx.getCausaRaiz() instanceof ConstraintViolationException){
				setActionForward(carregarPgViewColaborador(mapping, mcf, request, response));
			}
			throw appEx;
		} catch (Exception e) {
			setActionForward(carregarPgViewColaborador(mapping, mcf, request, response));
			throw new ApplicationException("mensagem.erro.instituicao.excluir", e);
		}
	}
	
	public ActionForward carregarPgEditColaborador(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		ManterColaboradorForm  mcf = (ManterColaboradorForm) form;
		Colaborador colaborador = null;
		try {
			
			if(mcf.getActionType().compareTo("incluir") != 0) {
				colaborador = ManterColaboradorFacade.obterColaborador(Integer.parseInt(mcf.getIdColaborador()));
				mcf.preencheColaborador(colaborador);
			}
			Collection<Instituicao> instituicoes = ManterInstituicaoFacade.listarInstituicao();
			request.setAttribute("instituicoes", instituicoes);
			return mapping.findForward("pgEditColaborador");
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgConColaborador"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgConColaborador"));
			throw new ApplicationException("mensagem.erro.colaborador.carregarPgViewInstituicao", e);
		}
	}
	
	public ActionForward salvarColaborador(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		ManterColaboradorForm mcf = (ManterColaboradorForm) form;
		Colaborador colaborador = null;
		try {
			if( mcf.getIdColaborador() == null || mcf.getIdColaborador().isEmpty()){
				colaborador = mcf.convertToColaborador(null);
				Collection<Colaborador> colaboradores = ManterColaboradorFacade.listarColaborador();
				System.out.println(colaboradores.size());
				for (Colaborador colaborador2 : colaboradores) {
					if(colaborador.getCpf().compareTo(colaborador2.getCpf()) == 0){
						if(colaborador2.getDataDEmissao() == null){
							addAlertMessage("ERRO.002", request);		
							Collection<Instituicao> instituicoes = ManterInstituicaoFacade.listarInstituicao();
							request.setAttribute("instituicoes", instituicoes);
							return mapping.findForward("pgEditColaborador");
						}
					}
				}
				colaborador.setDataDEmissao(null);
				colaborador.setInstituicao(ManterInstituicaoFacade
						.obterInstituicao(Integer.parseInt(mcf.getInstituicao())));
				ManterColaboradorFacade.incluirColaborador(colaborador);
				
			} else {
				colaborador = ManterColaboradorFacade.obterColaborador(
						Integer.parseInt(mcf.getIdColaborador()));
				if(mcf.getActionType().compareTo("encerrarVinculo") == 0){
					SimpleDateFormat smf = new SimpleDateFormat("dd/MM/yy");
					colaborador.setDataDEmissao(smf.parse(mcf.getDataDemissao()));
				} else {
					mcf.convertToColaborador(colaborador);
					if(colaborador.getInstituicao().getCodInstituicao()
							.toString().compareTo(mcf.getInstituicao()) != 0 ) {
						colaborador.setInstituicao(ManterInstituicaoFacade
								.obterInstituicao(Integer.parseInt(mcf.getInstituicao())));
					}
				}
				ManterColaboradorFacade.alterarColaborador(colaborador);
			}
			
			addMessage("mensagem.sucesso.colaborador.salvar", request);			
			return mapping.findForward("pgConColaborador");
		} catch (ApplicationException appEx) {
				setActionForward(carregarPgViewColaborador(mapping, mcf, request, response));
		//	}
			throw appEx;
		} catch (Exception e) {
			setActionForward(carregarPgViewColaborador(mapping, mcf, request, response));
			throw new ApplicationException("mensagem.erro.colaborador.salvar", e);
		}
	}
}
