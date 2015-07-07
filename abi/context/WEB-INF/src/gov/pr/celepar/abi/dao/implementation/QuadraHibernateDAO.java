package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.QuadraDAO;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Quadra;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Classe de manipulação de objetos da classe Quadra.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009 
 *
 */
public class QuadraHibernateDAO extends GenericHibernateDAO<Quadra, Integer> implements QuadraDAO {
	private static Logger log4j = Logger.getLogger(QuadraHibernateDAO.class);
	
	@SuppressWarnings("unchecked")
	public Collection<Quadra> listarPorBemImovel(Integer bemImovel)
			throws ApplicationException {
		List<Quadra> quadras = new ArrayList<Quadra>();
		try {
			Session session = HibernateUtil.currentSession();
			Query q = session.createQuery("from Quadra as quadra where quadra.bemImovel.codBemImovel=:codBemImovel ORDER BY descricao");
			q.setInteger("codBemImovel", bemImovel);
			quadras = q.list();
			

		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.genericDAO.listar", new String[]{"quadras por bem imóvel"}, e, ApplicationException.ICON_ERRO);		
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarPorBemImovel", e);
			}
		}
		return quadras;
	}

	@Override
	public Quadra salvarQuadra(Quadra quadra) throws ApplicationException {
		Quadra retorno = new Quadra();
		// Validando parâmetro
		if (quadra == null){
			throw new ApplicationException("mensagem.erro.9002", new String[]{"QuadraHibernateDAO.salvarQuadra()"}, ApplicationException.ICON_ERRO);
		}
		
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			session.clear();
			Serializable chave = session.save(quadra);
			session.flush();
			log4j.info("SALVAR:" + quadra.getClass().getName() + " salvo.");
			retorno = (Quadra) session.get(Quadra.class, chave);
			log4j.info("GET:" + quadra.getClass().getName() + " q acabou de ser salvo.");
						
		} catch (ConstraintViolationException cve) {
			log4j.error("QuadraHibernateDAO.salvarQuadra()" + cve);
			throw new ApplicationException("mensagem.erro.9003", new String[]{quadra.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
		} catch (HibernateException he) {
			log4j.error("QuadraHibernateDAO.salvarQuadra()" + he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"QuadraHibernateDAO.salvarQuadra()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("QuadraHibernateDAO.salvarQuadra()" + e);
			throw new ApplicationException("mensagem.erro.9001",new String[]{"QuadraHibernateDAO.salvarQuadra()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				log4j.debug("Fechando sessao corrente.");
				HibernateUtil.closeSession();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{quadra.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
		
		return retorno;
	}
	/**
	 * Fazer o merge dos objetos.<br>
	 * @author pozzatti
	 * @since 21/01/2009
	 * @param Quadra : Quadra
	 * @return Quadra
	 * @throws ApplicationException
	 */	
	public Quadra merge(Quadra quadra) throws ApplicationException{
		try{
			Session session = HibernateUtil.currentSession();
			quadra = (Quadra) session.merge(quadra);
			return quadra;
		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage());
			throw new ApplicationException("mensagem.erro.9001", new String[]{QuadraHibernateDAO.class.getSimpleName().concat(".merge()")}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();	
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{quadra.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
	}

	@Override
	public Collection<Quadra> listarQuadraSemLote(BemImovel bemImovel) throws ApplicationException {
		List<Quadra> quadras = new ArrayList<Quadra>();
		try {
			Session session = HibernateUtil.currentSession();
			Query q = session.createQuery("from Quadra as quadra where quadra.lotes IS EMPTY AND quadra.bemImovel.codBemImovel=:codBemImovel");
			q.setInteger("codBemImovel", bemImovel.getCodBemImovel());
			quadras = q.list();
			

		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.genericDAO.listar", new String[]{"quadras sem lote por bem imóvel"}, e, ApplicationException.ICON_ERRO);		
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarQuadraSemLote", e);
			}
		}
		return quadras;
	}
	 

}
