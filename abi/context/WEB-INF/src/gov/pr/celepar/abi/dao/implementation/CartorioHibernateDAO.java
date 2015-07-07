package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.CartorioDAO;
import gov.pr.celepar.abi.pojo.Cartorio;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Classe de manipulação de objetos da classe Cartorio.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009 
 *
 */
public class CartorioHibernateDAO extends GenericHibernateDAO<Cartorio, Integer> implements CartorioDAO {

	
	private static Logger log = Logger.getLogger(CartorioHibernateDAO.class);
	protected static final String LOGERROFECHARSESSAO = "Problema ao tentar fechar sessao do Hibernate";
	protected static final String LOGINICIO = "Abrindo Sessao Hibernate";
	protected static final String LOGFIM = "Fechando Sessao Hibernate";
	
	
	/**
	 * Obtem o cartorio atraves da descricao
	 * @param descCartorio
	 * @return Cartorio
	 * @throws ApplicationException 
	 */
	public Cartorio obterCartorioPorDescricao(String descCartorio) throws ApplicationException {
		log.info("Método obterCartorioPorDescricao processando...");
		
		try {
			Session session = HibernateUtil.currentSession();
			
			// Montando o SQL					
			StringBuffer sql = new StringBuffer();
			sql.append(" FROM Cartorio c ");
			sql.append(" WHERE UPPER(c.descricao) LIKE :descCartorio");
			
			
			// Montando Query e fazendo a busca
			Query q = session.createQuery(sql.toString());
			q.setMaxResults(1);
			q.setString("descCartorio", descCartorio.toUpperCase());

			Cartorio cartorio = (Cartorio) q.uniqueResult();
			
			return cartorio;

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"CartorioHibernateDAO.obterCartorioPorDescricao()"}, he, ApplicationException.ICON_ERRO);
		}  catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"CartorioHibernateDAO.obterCartorioPorDescricao()"}, e, ApplicationException.ICON_ERRO);	
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log.warn(LOGERROFECHARSESSAO);
			}
		}
	}

}
