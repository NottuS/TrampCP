package gov.pr.celepar.ucs_manterinstituicao.action;

import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.Sessao;
import gov.pr.celepar.ucs_manterinstituicao.decorator.CNPJ;
import gov.pr.celepar.ucs_manterinstituicao.facade.ManterInstituicaoFacade;
import gov.pr.celepar.ucs_manterinstituicao.form.ManterInstituicaoForm;
import gov.pr.celepar.ucs_manterinstituicao.pojo.AreaInteresse;
import gov.pr.celepar.ucs_manterinstituicao.pojo.Instituicao;
import gov.pr.celepar.ucs_manterinstituicao.pojo.NaturezaJuridica;
import gov.pr.celepar.ucs_manterinstituicao.pojo.Telefone;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.exception.ConstraintViolationException;



public class ManterInstituicaAction extends BaseDispatchAction {
	
	
	public ActionForward carregarPgListInstituicoes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		List<NaturezaJuridica> njs = ManterInstituicaoFacade.listarNaturezaJuridica();
		//TODO passar esses requests sempre
		Sessao.adicionarAtributoSessao(request, "portes", Instituicao.Porte.values());
		//request.setAttribute("portes", Instituicao.Porte.values());
		request.setAttribute("naturezaJuridicas", njs);
		request.setAttribute("njSelected", "0");

		return mapping.findForward("pgListInstituicao");
    }
	
	public ActionForward  pesquisarInstituicoes(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		ManterInstituicaoForm mif = (ManterInstituicaoForm) form;
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
		
		Pagina pagina = new Pagina(new Integer(5), new Integer(indicePagina), new Integer(totalRegistros));

		try {
			List<NaturezaJuridica> njs = ManterInstituicaoFacade.listarNaturezaJuridica();
			//request.setAttribute("portes", Instituicao.Porte.values());
			request.setAttribute("naturezaJuridicas", njs);
			request.setAttribute("njSelected", mif.getNaturezaJuridica());
			pagina = ManterInstituicaoFacade.listarInstituicao(pagina, mif);
			request.setAttribute("pagina", pagina);
			if(pagina.getTotalRegistros() == 0){
				addAlertMessage("AVISO.15", request);
			}

			return mapping.findForward("pgListInstituicao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListInstituicao"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListInstituicao"));
			throw new ApplicationException("mensagem.erro.matricula.listarAluno", e);
		}
	}
	
	public ActionForward carregarPgViewInstituicao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		ManterInstituicaoForm mif = (ManterInstituicaoForm) form;
		
		try {
			Instituicao instituicao = ManterInstituicaoFacade.obterInstituicao(Integer.parseInt(mif.getCodInstituicao()));
			mif.preencheInstituicao(instituicao);
			mif.setCnpj(new CNPJ().decorate(instituicao.getCnpj()));
			mif.setNaturezaJuridica(instituicao.getNaturezaJuridica().getMascara());
			request.setAttribute("portes", Instituicao.Porte.values());
			return mapping.findForward("pgViewInstituicao");
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListInstituicao"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListInstituicao"));
			throw new ApplicationException("mensagem.erro.instituicao.carregarPgViewInstituicao", e);
		}
	}
	
	public ActionForward excluirInstituicao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		ManterInstituicaoForm mif = (ManterInstituicaoForm) form;
			
		try {
			if (mif.getCodInstituicao() != null) {
				Instituicao instituicao = ManterInstituicaoFacade
						.obterInstituicao(Integer.parseInt(mif.getCodInstituicao()));
				ManterInstituicaoFacade.excluirInstituicao(instituicao);
			}
			
			addMessage("mensagem.sucesso.instituicao.excluir", request);			
			List<NaturezaJuridica> njs = ManterInstituicaoFacade.listarNaturezaJuridica();
			request.setAttribute("portes", Instituicao.Porte.values());
			request.setAttribute("naturezaJuridicas", njs);
			return mapping.findForward("pgListInstituicao");
			
		} catch (ApplicationException appEx) {
			if(appEx.getCausaRaiz() instanceof ConstraintViolationException){
				setActionForward(carregarPgViewInstituicao(mapping, mif, request, response));
			}
			throw appEx;
		} catch (Exception e) {
			setActionForward(carregarPgViewInstituicao(mapping, mif, request, response));
			throw new ApplicationException("mensagem.erro.instituicao.excluir", e);
		}
	}
	
	public ActionForward carregarPgEditInstituicao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		ManterInstituicaoForm mif = (ManterInstituicaoForm) form;
		Instituicao instituicao = new Instituicao();
		Collection<AreaInteresse> aiDisponiveis =  ManterInstituicaoFacade.listarAreaInteresse();
		Collection<AreaInteresse> aiSelecionadas = null; 
		try {
			
			if(mif.getCodInstituicao() == null || mif.getCodInstituicao().isEmpty()){
				mif.setActionType("incluir");
				aiSelecionadas = new ArrayList<AreaInteresse>();
				List<String> CNPJs = ManterInstituicaoFacade.listarCNPJ();
				request.setAttribute("cnpjs", CNPJs);
			} else if(mif.getActionType().compareTo("alterar")  == 0) {
				instituicao = ManterInstituicaoFacade.obterInstituicao(Integer.parseInt(mif.getCodInstituicao()));
				mif.preencheInstituicao(instituicao);
				mif.setCnpj(new CNPJ().decorate(instituicao.getCnpj()));
				aiSelecionadas = instituicao.getAreaInteresses();
			}
			List<NaturezaJuridica> njs = ManterInstituicaoFacade.listarNaturezaJuridica();
			request.setAttribute("portes", Instituicao.Porte.values());
			request.setAttribute("naturezaJuridicas", njs);
			request.setAttribute("telefones", instituicao.getInstituicaoTelefones());
			
			//Remove as areas de interesse ja selecionada das disponiveis
			for(AreaInteresse selecionado: aiSelecionadas){
				for (Iterator<AreaInteresse> aiIt = aiDisponiveis.iterator() ; aiIt.hasNext();) {
					AreaInteresse disponivel = aiIt.next();
					if(disponivel.getCodAreaInteresse().intValue() == selecionado.getCodAreaInteresse().intValue()){
						aiIt.remove();
						break;
					}
				}				
			}
			request.setAttribute("areasInteresseSelecionadas", aiSelecionadas);
			request.setAttribute("areasInteresseDisponiveis", aiDisponiveis);
			
			return mapping.findForward("pgEditInstituicao");
		} catch (ApplicationException appEx) {
			List<NaturezaJuridica> njs = ManterInstituicaoFacade.listarNaturezaJuridica();
			request.setAttribute("portes", Instituicao.Porte.values());
			request.setAttribute("naturezaJuridicas", njs);
			setActionForward(mapping.findForward("pgListInstituicao"));
			throw appEx;
		} catch (Exception e) {
			List<NaturezaJuridica> njs = ManterInstituicaoFacade.listarNaturezaJuridica();
			request.setAttribute("portes", Instituicao.Porte.values());
			request.setAttribute("naturezaJuridicas", njs);
			setActionForward(mapping.findForward("pgListInstituicao"));
			throw new ApplicationException("mensagem.erro.instituicao.carregarPgViewInstituicao", e);
		}
	}
	
	public ActionForward salvarInstituicao(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		ManterInstituicaoForm mif = (ManterInstituicaoForm) form;
		Instituicao instituicao = null;
		try {
			mif.setTelefones(mif.getTelefones()[0].split(","));
			if (mif.getCodInstituicao() == null || mif.getCodInstituicao().isEmpty()) {
				instituicao = mif.convertToInstituicao();
				instituicao.setInstituicaoTelefones(new HashSet<Telefone>());
				for (String telefone : mif.getTelefones()) {
					Telefone t = new Telefone();
					t.setTelefone(telefone);
					t.setInstituicao(instituicao);
					instituicao.getInstituicaoTelefones().add(t);
				}
				instituicao.setNaturezaJuridica(ManterInstituicaoFacade
						.obterNaturezaJuridica(Integer.parseInt(mif.getNaturezaJuridica())));
				instituicao.setAreaInteresses(ManterInstituicaoFacade.listarAreaInteresseSeleciondas(mif));
				ManterInstituicaoFacade.incluirInstituicao(instituicao);
			} else {
				instituicao = ManterInstituicaoFacade.obterInstituicao(Integer.parseInt(mif.getCodInstituicao()));
				
				if(instituicao.getPorte() != Integer.parseInt(mif.getPorte())){
					instituicao.setPorte(Integer.parseInt(mif.getPorte()));
				}
					
				if(instituicao.getRazaoSocial().compareTo(mif.getRazaoSocial()) != 0){
					instituicao.setRazaoSocial(mif.getRazaoSocial());
				}
				//Add novos telefones
				for(String telefone: mif.getTelefones()){
					Boolean found = false;
					if(!telefone.isEmpty()){
						Telefone t = new Telefone();
						t.setTelefone(telefone);
						t.setInstituicao(instituicao);
						for(Telefone it : instituicao.getInstituicaoTelefones()){
							if(it.getTelefone().compareTo(telefone) == 0){
								found = true;
								break;
							}
						}
						if(!found){
							instituicao.getInstituicaoTelefones().add(t);
						}
						/*if(!instituicao.getInstituicaoTelefones().contains(t)){
							instituicao.getInstituicaoTelefones().add(t);
						}*/
					}
				}
				//Remove telefones
				Collection<Telefone> telefones = new ArrayList<Telefone>();
				for(Iterator<Telefone> i = instituicao.getInstituicaoTelefones().iterator(); i.hasNext();){
					Boolean found = false;
					Telefone t = i.next();
					for (String telefone : mif.getTelefones()) {
						if(telefone.compareTo(t.getTelefone()) == 0 && !telefone.isEmpty()){
							found = true;
							break;
						} 
					}
					if(!found){
						telefones.add(t);
						i.remove();
					}
				}
				
				if(instituicao.getNaturezaJuridica().getCodNaturezaJuridica() 
						!= Integer.parseInt(mif.getNaturezaJuridica())){
					instituicao.setNaturezaJuridica(ManterInstituicaoFacade
							.obterNaturezaJuridica(Integer.parseInt(mif.getNaturezaJuridica())));
				}
				instituicao.setAreaInteresses(ManterInstituicaoFacade.listarAreaInteresseSeleciondas(mif));
				ManterInstituicaoFacade.alterarInstituicao(instituicao);
				ManterInstituicaoFacade.deleteTelefones(telefones);
			}			
			addMessage("mensagem.sucesso.instituicao.salvar", request);			
			
			List<NaturezaJuridica> njs = ManterInstituicaoFacade.listarNaturezaJuridica();
			request.setAttribute("portes", Instituicao.Porte.values());
			request.setAttribute("naturezaJuridicas", njs);
			request.setAttribute("njSelected", mif.getNaturezaJuridica());
			return mapping.findForward("pgListInstituicao");
		} catch (ApplicationException appEx) {
			//if(appEx.getCausaRaiz() instanceof ConstraintViolationException){
			List<NaturezaJuridica> njs = ManterInstituicaoFacade.listarNaturezaJuridica();
			request.setAttribute("portes", Instituicao.Porte.values());
			request.setAttribute("naturezaJuridicas", njs);
				setActionForward(carregarPgViewInstituicao(mapping, mif, request, response));
		//	}
			throw appEx;
		} catch (Exception e) {
			List<NaturezaJuridica> njs = ManterInstituicaoFacade.listarNaturezaJuridica();
			request.setAttribute("portes", Instituicao.Porte.values());
			request.setAttribute("naturezaJuridicas", njs);
			setActionForward(carregarPgViewInstituicao(mapping, mif, request, response));
			throw new ApplicationException("mensagem.erro.instituicao.salvar", e);
		}
	}
	
	public ActionForward validateCNPJ(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		//ManterInstituicaoForm mif = (ManterInstituicaoForm) form;
		String cnpj = request.getParameter("cnpj");
		try {
			response.setContentType("text/text;charset=utf-8");
		    response.setHeader("cache-control", "no-cache");
		    PrintWriter out = response.getWriter();
			List<String> CNPJs = ManterInstituicaoFacade.listarCNPJ();
			for(String c : CNPJs) {
				if(c.compareTo(cnpj) == 0){
					out.print("false");
				    out.flush();
				    return null;
				}
			}
		    out.print("true");
		    out.flush();
		    return null;
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListInstituicao"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListInstituicao"));
			throw new ApplicationException("mensagem.erro.instituicao.carregarPgViewInstituicao", e);
		}
	}
}
