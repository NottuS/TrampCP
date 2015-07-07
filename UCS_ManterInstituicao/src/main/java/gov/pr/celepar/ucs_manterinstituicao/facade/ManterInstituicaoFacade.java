package gov.pr.celepar.ucs_manterinstituicao.facade;

import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.ucs_manterinstituicao.dao.factory.DAOFactory;
import gov.pr.celepar.ucs_manterinstituicao.dao.factory.HibernateDAOFactory;
import gov.pr.celepar.ucs_manterinstituicao.form.ManterInstituicaoForm;
import gov.pr.celepar.ucs_manterinstituicao.pojo.AreaInteresse;
import gov.pr.celepar.ucs_manterinstituicao.pojo.Instituicao;
import gov.pr.celepar.ucs_manterinstituicao.pojo.NaturezaJuridica;
import gov.pr.celepar.ucs_manterinstituicao.pojo.Telefone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class ManterInstituicaoFacade {

	private static Logger log = Logger.getLogger(ManterInstituicaoFacade.class);

	public static NaturezaJuridica obterNaturezaJuridica(Integer codNaturezaJuridica) throws ApplicationException {
		NaturezaJuridica naturezaJuridica = null;
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		
		try {
			naturezaJuridica = hibernateFactory.getNaturezaJuridicaDAO().obter(codNaturezaJuridica);
		} catch (ApplicationException ae){
			throw ae;
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.instituicao.obter", e);
		}
		
		return naturezaJuridica;
	}
	
	public static List<NaturezaJuridica> listarNaturezaJuridica() throws ApplicationException {
		List<NaturezaJuridica> njs = new ArrayList<NaturezaJuridica>();
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		try {
			njs = (List<NaturezaJuridica>) hibernateFactory.getNaturezaJuridicaDAO().listar();
		} catch (ApplicationException ae){
			throw ae;
		} catch (Exception e) {
			throw new ApplicationException("mensasem.erro.natureza.listar", e);
		}
		return njs;
	}
	
	public static List<AreaInteresse> listarAreaInteresse() throws ApplicationException {
		List<AreaInteresse> ais = new ArrayList<AreaInteresse>();
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		try {
			ais = (List<AreaInteresse>) hibernateFactory.getAreaInteresseDAO().listar();
		} catch (ApplicationException ae){
			throw ae;
		} catch (Exception e) {
			throw new ApplicationException("mensasem.erro.AreaInteresse.listar", e);
		}
		return ais;
	}
	
	public static Set<AreaInteresse> listarAreaInteresseSeleciondas(ManterInstituicaoForm mif) throws ApplicationException {
		Set<AreaInteresse> ais = new HashSet<AreaInteresse>();
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		try {
			ais = hibernateFactory.getAreaInteresseDAO().listarSeleciondas(mif.getAreasInteresseSelecionadas());
		} catch (ApplicationException ae){
			throw ae;
		} catch (Exception e) {
			throw new ApplicationException("mensasem.erro.AreaInteresse.listar", e);
		}
		return ais;
	}
	
	public static Pagina listarInstituicao(Pagina pagina,
			ManterInstituicaoForm mif) throws ApplicationException{
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		try {
			if (pagina.getTotalRegistros() == 0) {
			 	pagina.setTotalRegistros(hibernateFactory.getInstituicaoDAO().buscarQtdLista(mif).intValue());
			}
			pagina.setRegistros(hibernateFactory.getInstituicaoDAO().listar(mif, pagina.getQuantidade(), pagina.getPaginaAtual()));
		} catch(ApplicationException ae) {
			throw ae;
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.instituicao.listar", e);
		}
		return pagina;
	}
	
	public static Instituicao obterInstituicao(Integer codInstituicao) throws ApplicationException {
		Instituicao instituicao = null;
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		
		try {
			instituicao = hibernateFactory.getInstituicaoDAO().obter(codInstituicao);
		} catch (ApplicationException ae){
			throw ae;
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.instituicao.obter", e);
		}
		return instituicao;
	}
	
	public static void excluirInstituicao(Instituicao instituicao) throws ApplicationException {
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		try {		
			hibernateFactory.getInstituicaoDAO().excluir(instituicao);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new ApplicationException("mensagem.erro.matricula.servico.excluirAluno", ex, ApplicationException.ICON_ERRO);						
		}
	}
	
	public static void deleteTelefones(Collection<Telefone> telefones) throws ApplicationException {
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		try {		
			hibernateFactory.getTelefoneDAO().delete(telefones);;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new ApplicationException("mensagem.erro.matricula.servico.excluirAluno", ex, ApplicationException.ICON_ERRO);						
		}
	}
	
	public static void alterarInstituicao(Instituicao instituicao) throws ApplicationException {
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		
		try {
			HibernateUtil.currentTransaction(); //Abre sessão e transação
			
			hibernateFactory.getInstituicaoDAO().alterar(instituicao);;
			
			HibernateUtil.commitTransaction(); //Fecha sessão e transação
		
		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				log.error("Problema ao tentar realizar rollback na transacao: alterarInstituicao", e);
			}
			throw appEx;
			
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				log.error("Problema ao tentar realizar rollback na transacao: alterarInstituicao", e);
			}
			throw new ApplicationException("mensagem.erro.instituicao.alterar", ex, ApplicationException.ICON_ERRO);						
		}
	}
	
	public static void incluirInstituicao(Instituicao instituicao) throws ApplicationException {
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		
		try {
			HibernateUtil.currentTransaction(); //Abre sessão e transação
			
			hibernateFactory.getInstituicaoDAO().salvar(instituicao);
			
			HibernateUtil.commitTransaction(); //Fecha sessão e transação
		
		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				log.error("Problema ao tentar realizar rollback na transacao: incluirInstituicao", e);
			}
			throw appEx;
			
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				log.error("Problema ao tentar realizar rollback na transacao: incluirInstituicao", e);
			}
			throw new ApplicationException("mensagem.erro.instituicao.alterar", ex, ApplicationException.ICON_ERRO);						
		}
	}
	
	public static List<String> listarCNPJ() throws ApplicationException{
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		List<String> CNPJs = new ArrayList<String>();
		try {
			CNPJs = (List<String>) hibernateFactory.getInstituicaoDAO().listarCNPJ();
		} catch(ApplicationException ae) {
			throw ae;
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.instituicao.listar", e);
		}
		return CNPJs;
	}
}
